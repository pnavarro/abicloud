package com.abiquo.abicloud.appliancemanager.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;

import com.abiquo.appliancemanager.OVFDeployer;
import com.abiquo.appliancemanager.exceptions.XMLException;
import com.abiquo.appliancemanager.util.EnvelopeUtils;
import com.abiquo.appliancemanager.xml.OVFSerializer;

import junit.framework.TestCase;

public class OVFDeployerTest extends TestCase
{

    private OVFDeployer deployer;

    private EnvelopeType envelope;

    private URL packageURL;

    private final static String packageLocation = "file://home/apuig/test/";

    public void testRemoteDeploy()
    {
        setFileReferencesRemotes();
        deployPackageTest();
    }

    public void testRelativeDeploy()
    {
        setFileReferencesLocalRelative();
        deployPackageTest();
    }

    private void deployPackageTest()
    {
        try
        {
            deployer.deployOVFPackage(envelope, packageURL);

            // check all references are relatives
            for (FileType file : envelope.getReferences().getFile())
            {
                assertFalse("FileRef contains protocol ", file.getHref().contains(":"));
                /**
                 * TODO check really is (at least created) on the repository
                 */
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("change envelope");
        }
    }

    private void setFileReferencesRemotes()
    {

        List<FileType> files = envelope.getReferences().getFile();

        Iterator<FileType> itFile = files.iterator();

        // remove fileRef from the original XML doc
        while (itFile.hasNext())
        {
            itFile.next();
            itFile.remove();
        }

        // use files form FileDownloadTest
        files.add(EnvelopeUtils.createFileType(FileDownloadTest.fileSize, null, null,
            FileDownloadTest.urlLocal, "localFileID"));

        files.add(EnvelopeUtils.createFileType(FileDownloadTest.fileSize, null, null,
            FileDownloadTest.urlHttp, "httpFileID"));

        files.add(EnvelopeUtils.createFileType(FileDownloadTest.fileSize, null, null,
            FileDownloadTest.urlFtp, "ftpFileID"));

        files.add(EnvelopeUtils.createFileType(FileDownloadTest.fileSize, null, null,
            FileDownloadTest.urlS3, "s3FileID"));
    }

    private void setFileReferencesLocalRelative()
    {

        List<FileType> files = envelope.getReferences().getFile();

        Iterator<FileType> itFile = files.iterator();

        // remove fileRef from the original XML doc
        while (itFile.hasNext())
        {
            itFile.next();
            itFile.remove();
        }

        // use files form FileDownloadTest
        files.add(EnvelopeUtils.createFileType(FileDownloadTest.fileSize, null, null,
            "hadoop-0.19.1.tar.gz", "localFileID"));
    }

    public void setUp()
    {
        try
        {
            deployer = new OVFDeployer();
            envelope = loadTestOVFEnvelope();
            packageURL = new URL(packageLocation);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Can not load the OVFDeployer test");
        }
    }

    public EnvelopeType loadTestOVFEnvelope() throws FileNotFoundException, XMLException
    {
        return OVFSerializer.getInstance().readXMLEnvelope(
            new FileInputStream("resources/test/myservice.ovf"));
    }

}
