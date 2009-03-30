package com.abiquo.appliancemanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.appliancemanager.exceptions.DownloadException;
import com.abiquo.appliancemanager.exceptions.IdNotFound;
import com.abiquo.appliancemanager.exceptions.RepositoryException;
import com.abiquo.appliancemanager.filesystems.FileFactory;
import com.abiquo.appliancemanager.util.EnvelopeUtils;
import com.abiquo.appliancemanager.xml.OVFSerializer;

/**
 * Take an OVF-Envelope and deploy all its references into a repository (the HDFS also exposed by
 * the NFS-Servers). Finally change the file references to make it repository relative.
 * 
 * @TDOO use the advanced NHttpClient (see org.apache.http.examples.nio.NHttpClient)
 */
public class OVFDeployer
{
    private final static Logger log = LoggerFactory.getLogger(OVFDeployer.class);

    /**
     * Where is the DFS repository (also mounted form NFS-Servers) TODO configurable
     */
    private final static URI repositoryURI = URI.create("dfs://apuig:9000");

    /** Where are the OVF Packages ready to be mounted by NFS-Servers. */
    private FileSystem repositoryFS;

    /** DfsTransfers threads running there. */
    private ExecutorService executorDeploy = Executors.newCachedThreadPool();

    /** DfsTransfers indexed by OVFPackage name. */
    private Map<String, List<DfsTransfer>> htTransfers =
        new ConcurrentHashMap<String, List<DfsTransfer>>();

    /**
     * Initialize the connection to the HDFS repository.
     * 
     * @throws RepositoryException if the connection to hdfs fails.
     */
    public OVFDeployer() throws RepositoryException
    {
        repositoryFS = new DistributedFileSystem();

        try
        {
            /**
             * TODO tune configuration (buffer size, replication ...)
             */
            repositoryFS.initialize(repositoryURI, new Configuration());
        }
        catch (IOException e)
        {
            final String msg =
                "The hadoop file system can not be accessed at " + repositoryURI.toASCIIString();
            throw new RepositoryException(msg, e);
        }

        log.debug("Created FileRefDownloader pointing to hdfs repository "
            + repositoryURI.toASCIIString());
    }

    /**
     * Create a new directory for the provided OVFPackage into the repository (using the OVF file
     * name), inspect the OVF-Envelope to download all its File References into the repository,
     * change the envelope to use relative paths and finally deploy the OVF XML description file
     * into the OVFPackage directory on the repository.
     * 
     * @param envelope, the OVF XML Description to be deployed.
     * @param OVFPackageLocation, where is the whole OVFPackage
     */
    public void deployOVFPackage(EnvelopeType envelope, URL ovfPackageLocation)
        throws DownloadException, RepositoryException
    {
        Map<String, String> relativePaths = new HashMap<String, String>();
        Path ovfpPath;

        // TODO assert URL is a directory !!!

        String packageDirectory = ovfPackageLocation.getFile();
        String[] packageDirElems = packageDirectory.split(File.separator); 
        String packageName = packageDirElems[packageDirElems.length-1];
        
        log.debug("deploing package directory "+packageDirectory+" pakcagename "+packageName);
        
        ovfpPath = new Path(packageDirectory);

        // create the OVF Package directory into the repository
        try
        {
            /*
             * TODO ADD this () if (repositoryFS.exists(ovfpPath)) { final String msg =
             * "OVF package name already deployed into the repository " +packageName;//+
             * envelope.getOVFPackageName(); throw new RepositoryException(msg); } comment
             */
            repositoryFS.mkdirs(ovfpPath);

            log.debug("Created OVFPackage directory " + packageDirectory);
        }
        catch (IOException e1)
        {
            final String msg = "The OVF Package directory can not be created on the reposiroy";
            throw new RepositoryException(msg, e1);
        }

        // download all the references into the OVFPackage directory
        for (FileType file : envelope.getReferences().getFile())
        {
            URL fileURL;
            String relativeFilePath;

            try
            {
                fileURL = new URL(file.getHref());
            }
            catch (MalformedURLException e1)
            {
                // its a relative path
                if (e1.getMessage().startsWith("no protocol"))
                {
                    try
                    {
                        fileURL =
                            new URL(ovfPackageLocation.getProtocol(), ovfPackageLocation.getHost(),
                                ovfPackageLocation.getPort(), ovfPackageLocation.getFile().concat(
                                    File.separatorChar + file.getHref()));
                    }
                    catch (MalformedURLException e)
                    {
                        final String msg =
                            "Invalid file reference " + ovfPackageLocation.toString()
                                + File.separatorChar + file.getHref();
                        throw new DownloadException(msg, e1);
                    }
                }
                else
                {
                    final String msg = "Invalid file reference " + file.getHref();
                    throw new DownloadException(msg, e1);
                }
            }

            relativeFilePath = deploy(fileURL, ovfpPath, file.getSize());

            log.debug("Deployed "+fileURL);
            
            relativePaths.put(file.getId(), relativeFilePath);
            
            
            
        }//for each fileRef


        for(String fileId : relativePaths.keySet())
        try
        {
            EnvelopeUtils.changeFileReference(envelope, fileId, relativePaths.get(fileId));    
        }
        catch (IdNotFound e) // MalformedURLException or
        {
            final String msg = "Invalid file reference " + fileId;
            throw new DownloadException(msg, e);
        }
        
        
        
        
        // Write the modified OVF XML description file into the OVF package directory on the
        // repository.
        try
        {
            FSDataOutputStream osOVFDesc =
                repositoryFS.create(new Path(ovfpPath, packageName + ".ovf"));

            OVFSerializer.getInstance().writeXML(envelope, osOVFDesc);            
            osOVFDesc.flush();
            osOVFDesc.sync();//TODO
            osOVFDesc.close();
            
        }
        catch (Exception e1) // IOException or XMLException
        {
            final String msg = "The OVF XML description can not be write into the repository";
            throw new RepositoryException(msg, e1);
        }
        
        log.debug("Downloaded OVFPackage "+packageDirectory);
    }

    /**
     * Download a provided file reference into the repository. <br/>
     * The referenced file can be on a HTTP, S3 or local file system.
     * 
     * @param target, the file reference to be downloaded.
     * @param ovfpPath, the OVF Package directory on the repository, used as destination of all file
     *            references.
     * @param expectedSize, file size in bytes (form the OVF XML description)
     * @return the relative path (to ovfpPath) to be used on the OVFPackage to point the target file
     *         deployed on the repository (relative as OVFPackage description also on the ovfpPath)
     */
    public String deploy(URL target, Path ovfpPath, BigInteger expectedSize)
        throws RepositoryException
    {

        InputStream isDownload;
        
        String fileRelatPath = target.getFile().replace(File.separatorChar, '_'); // TODO
        
        Path destinationPath = new Path(ovfpPath, fileRelatPath);

        log.debug("Deploing file " + target.toString() + " to " + destinationPath.toString()
            + " (relative: " + fileRelatPath + ")");

        isDownload = FileFactory.open(target);

        DfsTransfer tx = new DfsTransfer(isDownload, destinationPath, expectedSize);

        executorDeploy.submit(tx); // TODO use Future

        List<DfsTransfer> transfers;
        if (htTransfers.containsKey(ovfpPath.getName()))
        {
            transfers = htTransfers.get(ovfpPath.getName());
        }
        else
        {
            transfers = new LinkedList<DfsTransfer>();
        }
        transfers.add(tx);

        htTransfers.put(ovfpPath.getName(), transfers);

        return fileRelatPath;
    }

    /**
     * Pull data from an external file system into the repository. TODO consider use NIO to not have
     * a thread to read each connection (@see Apache MINA (AsychWeb))
     */
    class DfsTransfer implements Callable<Boolean>
    {
        /**
         * How many bytes read form source before to be write into repositoryFS. TODO configurable
         */
        private final static int bufferSize = 32 * 1024;

        /** An open stream to the source file . */
        private volatile InputStream source;

        /** Where is the file stored on the repository. */
        private volatile Path target;

        /** Expected file size (from OVF-envelope). */
        private volatile long expectedSize;

        /** Current bytes being download/deployed. */
        private volatile long currentSize = 0;

        /**
         * @throws RepositoryException if the targetPath already exist. (or can not be checked)
         */
        public DfsTransfer(InputStream isDownload, Path pathTarget, BigInteger size)
            throws RepositoryException
        {
            source = isDownload;
            target = pathTarget;
            // TODO check BigInteger is enough its long representation.
            expectedSize = size.longValue();
            /*
             * try { if (repositoryFS.exists(pathTarget)) { final String msg = "The target path " +
             * pathTarget.toString() + " already exist"; throw new RepositoryException(msg); } }
             * catch (IOException e) { final String msg = "The reposiotory can not be accessed";
             * throw new RepositoryException(msg, e); }
             */
        }

        /**
         * Get the name of the file being created (used as FileId).
         * 
         * @return only final component: relative path to OVFPackage directory.
         */
        public String getSource()
        {
            return target.getName();
        }

        /**
         * Get the transfer progress.
         * 
         * @return fileId and percent progress. TODO better use source fileId.
         */
        public FileDownloadStatus getProgress()
        {
            double progress = ((double) currentSize * 100/ expectedSize);
            return new FileDownloadStatus(getSource(), progress);
        }

        /**
         * Perform the data transmission. Read form the source stream (RepositorySpace) into the
         * target path (HDFS repository).
         * 
         * @return to check it is done.
         * @throws RepositoryException as cause of some IOException TODO tune the replication
         *             factor, bufferSize, progress on ''create'' operation TODO check expectedSize
         *             never higher currentSize
         */
        public Boolean call() throws RepositoryException
        {
            FSDataOutputStream osDest;
            byte[] buffer;

            try
            {
                log.debug("Start transmision ");

                osDest = repositoryFS.create(target, true); // TODO remove overwrite

                log.debug("Starting data transmision into " + target.toString());

                buffer = new byte[bufferSize];
                int nRead = 0;

                while (nRead != -1 && (currentSize < expectedSize))
                {
                    nRead = source.read(buffer);

                    osDest.write(buffer, 0, nRead);

                    osDest.flush();
                    osDest.sync();

                    if (nRead != -1)
                    {
                        currentSize += nRead;    
                    }
                    
                    log.debug("prgress: " + getProgress().getProgress() + "% nRead "+nRead); // TODO remove
                }

                log.debug("complet");
                
                source.close();
                osDest.close();

                log.debug("Completed download : " + target.toString());
            }
            catch (IOException e)
            {
                final String msg =
                    "The file can not be write into reposiory path " + target.toString();
                throw new RepositoryException(msg, e);
            }

            return Boolean.TRUE;
        }

    }// DfsTransfer

    /**
     * Gets progress information about all the File references on one OVF envelope that are being
     * deployed on the repository.
     * 
     * @return a list of fileId + progress
     */
    public List<FileDownloadStatus> getStatus(String ovf)
    {
        List<FileDownloadStatus> status = new LinkedList<FileDownloadStatus>();

        // TODO assert ovf exist
        for (DfsTransfer tx : htTransfers.get(ovf))
        {
            status.add(tx.getProgress());
        }

        return status;
    }

    /**
     * Structure to hold progress information for all the files being deployed into the repository.
     * 
     * @see getStatus
     */
    public class FileDownloadStatus
    {
        private final String fileID;

        private final double progress;

        public FileDownloadStatus(String fileID, double progress)
        {
            super();
            this.fileID = fileID;
            this.progress = progress;
        }

        public String getFileID()
        {
            return fileID;
        }

        public double getProgress()
        {
            return progress;
        }
    }
}
