package com.abiquo.abicloud.appliancemanager.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.abiquo.appliancemanager.OVFIndex;
import com.abiquo.appliancemanager.OVFIndexPersist;
import com.abiquo.appliancemanager.exceptions.InvalidRepositorySpace;
import com.abiquo.appliancemanager.exceptions.XMLException;
import com.abiquo.appliancemanager.xml.OVFSerializer;
import com.abiquo.ovfindex.OVFPackage;
import com.abiquo.ovfindex.RepositorySpace;

import junit.framework.TestCase;

public class OVFIndexTest extends TestCase
{

    private final static URI repositoryPath = new File("resources/test/OVFRepository/").toURI();

    private final static URI indexURI =
        URI.create(repositoryPath.toASCIIString() + "/ovfindex.xml");

    private final static URI rsUri1 = URI.create(repositoryPath.toASCIIString() + "/rs1.xml");

    private final static URI rsUri2 = URI.create(repositoryPath.toASCIIString() + "/rs2.xml");

    private final static URI rsUri3 = URI.create(repositoryPath.toASCIIString() + "/rs3.xml");

    private RepositorySpace rs1;

    private RepositorySpace rs2;

    private RepositorySpace rs3;

    OVFIndex index;

    public OVFIndexTest()
    {
        super();
    }

    public void attachRepositories()
    {
        try
        {
            index.addRespositorySpace(rsUri1);
            index.addRespositorySpace(rsUri2);
            index.addRespositorySpace(rsUri3);
        }
        catch (InvalidRepositorySpace e)
        {
            assertNull("InvalidRepository ", e);
            e.printStackTrace();
        }
    }

    public void testSaveRestore()
    {

        attachRepositories();

        OVFIndexPersist.saveRepository(index.getRepositorySpace(), indexURI, index
            .getAttachedRepositorySpaces());

        try
        {
            index = new OVFIndex(indexURI);

            index = OVFIndexPersist.restoreRepository(false, indexURI);

            assertList();

            index = new OVFIndex(indexURI);

            index = OVFIndexPersist.restoreRepository(true, indexURI);

            assertList();
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
            assertNull("Can create JAXBcontext", e);
        }
        catch (InvalidRepositorySpace e)
        {
            e.printStackTrace();
            assertNull("InvalidRepository ", e);
        }

    }

    public void testList()
    {
        attachRepositories();
        assertList();
    }

    public void assertList()
    {
        List<OVFPackage> lstPackag;

        try
        {
            lstPackag = index.listRepository(URI.create("file:/lalala"), null);

            fail("expected invalid repository");
        }
        catch (InvalidRepositorySpace e)
        {
            assertNotNull("Must be an invalid repository", e);
        }

        int allPackages =
            rs1.getOVFPackage().size() + rs2.getOVFPackage().size() + rs3.getOVFPackage().size();

        try
        {
            lstPackag = index.listRepository(null, null);

            assertEquals(allPackages, lstPackag.size());

            // lstPackag = index.listRepository(null, "A");

            // assertEquals((3*2), lstPackag.size());

            lstPackag = index.listRepository(rsUri1, null);

            assertEquals(rs1.getOVFPackage().size(), lstPackag.size());

            lstPackag = index.listRepository(rsUri1, "A");

            assertEquals(2, lstPackag.size());

        }
        catch (InvalidRepositorySpace e)
        {
            assertNotNull("An invalid repository", e);
        }
    }

    public void testAddAndUpdateRepossitory()
    {
        try
        {
            index.addRespositorySpace(rsUri1);

            assertEquals(rs1.getOVFPackage().size(), index.getRepositorySpace().getOVFPackage()
                .size());

            index.addRespositorySpace(rsUri2);

            assertEquals(rs1.getOVFPackage().size() + rs2.getOVFPackage().size(), index
                .getRepositorySpace().getOVFPackage().size());

            index.addRespositorySpace(rsUri3);

            int allPackages =
                rs1.getOVFPackage().size() + rs2.getOVFPackage().size()
                    + rs3.getOVFPackage().size();

            assertEquals(allPackages, index.getRepositorySpace().getOVFPackage().size());

            index.updateIndex(rsUri1);

            assertEquals(allPackages, index.getRepositorySpace().getOVFPackage().size());

            index.updateIndex(rsUri2);

            assertEquals(allPackages, index.getRepositorySpace().getOVFPackage().size());

            index.updateIndex(rsUri3);

            assertEquals(allPackages, index.getRepositorySpace().getOVFPackage().size());

            try
            {
                rs1.getOVFPackage().remove(0);

                OVFSerializer.getInstance().writeXML(rs1, new FileOutputStream(new File(rsUri1)));

                index.updateIndex(rsUri1);

                assertEquals(allPackages - 1, index.getRepositorySpace().getOVFPackage().size());

            }
            catch (FileNotFoundException e)
            {
                assertNull("can not found repository file ", e);
                e.printStackTrace();
            }
            catch (XMLException e)
            {
                assertNull("XML error writing repository file ", e);
                e.printStackTrace();
            }

        }
        catch (InvalidRepositorySpace e)
        {
            assertNull("InvalidRepository ", e);
            e.printStackTrace();
        }

    }

    public void createReposiories()// throws IOException
    {
        rs1 = RepositorySpaceTest.createTestReposiotrySpace(rsUri1.toASCIIString());
        rs2 = RepositorySpaceTest.createTestReposiotrySpace(rsUri2.toASCIIString());
        rs3 = RepositorySpaceTest.createTestReposiotrySpace(rsUri3.toASCIIString());

        try
        {
            OVFSerializer.getInstance().writeXML(rs1, new FileOutputStream(new File(rsUri1)));
            OVFSerializer.getInstance().writeXML(rs2, new FileOutputStream(new File(rsUri2)));
            OVFSerializer.getInstance().writeXML(rs3, new FileOutputStream(new File(rsUri3)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            assertNull("Invalid respository space URI", e);
        }
        catch (XMLException e)
        {
            e.printStackTrace();
            assertNull("Can not write reposiotry", e);
        }
    }

    public void setUp()
    {
        File fR = new File(repositoryPath);
        fR.mkdirs();
        createReposiories();

        try
        {
            index = new OVFIndex(indexURI);
        }
        catch (JAXBException e)
        {
            assertNotNull("can create context", e);
        }

    }

    public void tearDown()
    {
        File fR = new File(repositoryPath);

        for (File f : fR.listFiles())
        {
            f.delete();
        }

        fR.delete();

        index = null;
    }
}
