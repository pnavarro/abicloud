package com.abiquo.abicloud.appliancemanager.test;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.URI;

import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;

import com.abiquo.appliancemanager.exceptions.IdAlreadyExists;
import com.abiquo.appliancemanager.exceptions.IdNotFound;
import com.abiquo.appliancemanager.util.EnvelopeUtils;
import com.abiquo.appliancemanager.xml.OVFSerializer;

import junit.framework.TestCase;

public class OVFEnvelopeTest extends TestCase
{
    private final static URI testOVFPath = new File("resources/test/myservice.ovf").toURI();

    private EnvelopeType envelope;

    public void testFileReferences()
    {
        assertEquals(5, EnvelopeUtils.getFileReferences(envelope).size());

        try
        {
            EnvelopeUtils.changeFileReference(envelope, "icon", "newIcon.png");
        }
        catch (IdNotFound e)
        {
            e.printStackTrace();
            fail("file references id not exist");
        }

        assertEquals(5, EnvelopeUtils.getFileReferences(envelope).size());

        assertTrue(EnvelopeUtils.getFileReferences(envelope).contains("newIcon.png"));
        assertFalse(EnvelopeUtils.getFileReferences(envelope).contains("icon.png"));

        try
        {
            EnvelopeUtils.addFile(envelope, BigInteger.valueOf(100000), null, null, "newFile.iso",
                "newFile");
        }
        catch (IdAlreadyExists e)
        {

            e.printStackTrace();
            fail("file references id already exist");
        }

        try
        {
            EnvelopeUtils.addFile(envelope, BigInteger.valueOf(100000), null, null, "newFile.iso",
                "newFile");
            fail("file id already exist");
        }
        catch (IdAlreadyExists e)
        {
            assertNotNull(e);
        }

        assertEquals(6, EnvelopeUtils.getFileReferences(envelope).size());

        assertTrue(EnvelopeUtils.getFileReferences(envelope).contains("newFile.iso"));
    }

    public void setUp()
    {
        File envFile = new File(testOVFPath);

        try
        {
            envelope = OVFSerializer.getInstance().readXMLEnvelope(new FileInputStream(envFile));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("can create envelope");
        }

    }

    public void tearDown()
    {
        envelope = null;
    }

    public static void main(String[] args)
    {
        OVFEnvelopeTest test = new OVFEnvelopeTest();
        test.setUp();
        test.testFileReferences();

    }
}
