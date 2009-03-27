package com.abiquo.appliancemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.appliancemanager.exceptions.InvalidRepositorySpace;
import com.abiquo.appliancemanager.xml.OVFSerializer;
import com.abiquo.ovfindex.RepositorySpace;

public class OVFIndexPersist
{

    private final static Logger log = LoggerFactory.getLogger(OVFIndexPersist.class);

    /***
     * Save to an XML document file the content of the master RepositorySpace, also store the
     * attached URI form all its RepositorySpaces. TODO param
     * 
     * @see repositoryPath, where is save. TODO throws exception
     */
    public static void saveRepository(RepositorySpace repository, URI repositoryURI,
        Set<URI> attachedRepositories)
    {
        File fileRepo = new File(repositoryURI);
        File fileUris = new File(repositoryURI + ".attachedURI");

        FileOutputStream writeRepo;
        BufferedWriter writeUri;

        try
        {
            writeRepo = new FileOutputStream(fileRepo);

            OVFSerializer.getInstance().writeXML(repository, writeRepo);

            writeRepo.close();

        }
        catch (Exception e) // FileNotFoundException XMLException IOException
        {
            // TODO throw ???
            final String msg = "The ovfindex.xml can not be saved";
            log.error(msg);
        }

        try
        {
            writeUri = new BufferedWriter(new FileWriter(fileUris));

            for (URI uri : attachedRepositories)
            {
                try
                {
                    writeUri.write(uri.toASCIIString());
                    writeUri.newLine();
                }
                catch (IOException e)
                {
                    final String msg =
                        "Repository " + uri.toASCIIString() + " can not be persisted";
                    log.error(msg);
                }
            }

            writeUri.close();
        }
        catch (IOException e1) // can create w
        {
            // TODO throw ???
            final String msg = "The attachedURI file can not be created";
            log.debug(msg);
        }
    }

    /**
     * Load form an XML document file the content of the master RepositorySpace. Also update all the
     * attached RepositorySpaces.
     * 
     * @todo why is first reading the resume and then update ? .... simply update ?
     * @todo check the read repositoryURI
     */
    public static OVFIndex restoreRepository(boolean isUpdateFormURIs, URI repositoryURI)
        throws InvalidRepositorySpace
    {
        OVFIndex index;
        RepositorySpace repository;

        File fileRepo = new File(repositoryURI);
        File fileUris = new File(repositoryURI + ".attachedURI");

        FileInputStream readRepo;
        BufferedReader readUri;

        try
        {
            index = new OVFIndex(repositoryURI);

            readRepo = new FileInputStream(fileRepo);
            repository = OVFSerializer.getInstance().readXMLRepositorySpace(readRepo);

            index.setRepositorySpace(repository);

        }
        catch (Exception e) // FileNotFoundException XMLException
        {
            final String msg = "The repository can not be restored form " + repositoryURI;
            throw new InvalidRepositorySpace(msg, e);
        }

        if (isUpdateFormURIs)
        {
            List<URI> uris = new LinkedList<URI>();

            String line;
            URI repoURI;
            try
            {
                readUri = new BufferedReader(new FileReader(fileUris));

                line = readUri.readLine();

                while (line != null)
                {
                    repoURI = new URI(line);

                    uris.add(repoURI);

                    line = readUri.readLine();
                }
            }
            catch (Exception e) // IOException FileNotFoundException
            {
                final String msg =
                    "The attached repository URIs file can not be restored form " + repositoryURI
                        + ".attachedURI";
                log.error(msg); // TODO throws ?
            }

            for (URI uri : uris)
            {
                index.updateIndex(uri);
            }
        }// update from URI

        return index;
    }
}
