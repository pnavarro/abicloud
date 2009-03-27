package com.abiquo.abicloud.appliancemanager.test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import com.abiquo.appliancemanager.filesystems.FileFTP;
import com.abiquo.appliancemanager.filesystems.FileFactory;
import com.abiquo.appliancemanager.filesystems.FileHttp;
import com.abiquo.appliancemanager.filesystems.FileLocal;
import com.abiquo.appliancemanager.filesystems.FileS3;
import com.abiquo.appliancemanager.filesystems.IFileSystem;

public class FileDownloadTest extends TestCase
{

    public void setUp()
    {
    }

    public void tearDown()
    {
    }

    public final static String urlHttp =
        "http://ftp.udc.es/apache-dist/hadoop/core/hadoop-0.19.1/hadoop-0.19.1.tar.gz";

    public final static String urlLocal = "file://home/apuig/test/hadoop-0.19.1.tar.gz";

    public final static String urlFtp = "ftp:/XXXXXXXXX TODO XXXXXXXXX"; // TODO

    public final static String urlS3 = "s3:///XXXXXXXXX TODO XXXXXXXXX"; // TODO

    final static BigInteger fileSize = BigInteger.valueOf(55745146);

    // private IFileSystem file;

    private URL fileLocation;

    public void testLocalFile() throws MalformedURLException
    {
        fileLocation = new URL(urlLocal);
        fileTest();
    }

    public void testHttpFile() throws MalformedURLException
    {
        fileLocation = new URL(urlHttp);
        fileTest();
    }

    public void testFtpFile() throws MalformedURLException
    {
        fileLocation = new URL(urlFtp);
        fileTest();
    }

    public void testS3File() throws MalformedURLException
    {
        fileLocation = new URL(urlS3);
        fileTest();
    }

    private void fileTest()
    {
        InputStream is;

        try
        {
            is = FileFactory.open(fileLocation);

            assertEquals(fileSize.longValue(), countStreamBytes(is).longValue());
        }
        catch (Exception e) // DownloadException or MalformedURLException
        {
            fail("can not get the inputStream from the http source");
        }
    }

    public BigInteger countStreamBytes(InputStream is)
    {
        long current = 0;
        byte[] buffer = new byte[1024 * 2];
        int nRead = 0;

        try
        {
            while (nRead != -1)
            {
                nRead = is.read(buffer);

                if (nRead != -1)
                {
                    current += nRead;
                }
            }
        }
        catch (IOException e)
        {
            fail("Exception reading input stream source");
        }

        return BigInteger.valueOf(current);
    }

}
