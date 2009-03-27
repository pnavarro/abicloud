package com.abiquo.abicloud.appliancemanager.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import com.abiquo.appliancemanager.exceptions.XMLException;
import com.abiquo.appliancemanager.util.RepositoryUtils;
import com.abiquo.appliancemanager.xml.OVFSerializer;
import com.abiquo.ovfindex.OVFPackage;
import com.abiquo.ovfindex.RepositorySpace;

import junit.framework.TestCase;

public class RepositorySpaceTest extends TestCase
{

    RepositorySpace repository;

    public RepositorySpaceTest()
    {
        super();
    }

    private final static String repositoryURI = "file://ovfindex.xml";

    private final static String categroyp1 = "A";

    private final static String categroyp2 = "B";

    /**
     * p1 category A p2 category B p3 category A and B
     */
    public static RepositorySpace createTestReposiotrySpace(String repoURI)
    {
        RepositorySpace repo;
        OVFPackage p1, p2, p3;

        repo = new RepositorySpace();
        p1 = new OVFPackage();
        p2 = new OVFPackage();
        p3 = new OVFPackage();

        repo.setRepositoriURI(repoURI);

        p1.setOVFDescription("description p1");
        p1.setOVFFile("file p1");
        p1.setOVFIcon("icon p1");
        p1.setRepositoryURI(repoURI);
        p1.getOVFCategory().add(categroyp1);

        p2.setOVFDescription("description p2");
        p2.setOVFFile("file p2");
        p2.setOVFIcon("icon p2");
        p2.setRepositoryURI(repoURI);
        p2.getOVFCategory().add(categroyp2);

        p3.setOVFDescription("description p3");
        p3.setOVFFile("file p3");
        p3.setOVFIcon("icon p3");
        p3.setRepositoryURI(repoURI);
        p3.getOVFCategory().add(categroyp1);
        p3.getOVFCategory().add(categroyp2);

        repo.getOVFPackage().add(p1);
        repo.getOVFPackage().add(p2);
        repo.getOVFPackage().add(p3);

        return repo;
    }

    public void testConstruction()
    {
        assertEquals(repositoryURI, repository.getRepositoriURI());

        assertEquals(3, repository.getOVFPackage().size());

        for (OVFPackage pack : repository.getOVFPackage())
        {
            assertEquals(repositoryURI, pack.getRepositoryURI());
        }
    }

    public void testPurge() throws URISyntaxException
    {

        int initialSize = repository.getOVFPackage().size();

        RepositoryUtils
            .purgeRepositorySpace(repository.getOVFPackage(), "file://otherOVFIndex.xml");

        assertEquals(initialSize, repository.getOVFPackage().size());

        RepositoryUtils.purgeRepositorySpace(repository.getOVFPackage(), repositoryURI);

        assertEquals(0, repository.getOVFPackage().size());

    }

    public void testFilter()
    {
        List<OVFPackage> filtred;

        filtred = RepositoryUtils.filterRepositorySpace(repository.getOVFPackage(), categroyp1);

        assertEquals(2, filtred.size());

        filtred = RepositoryUtils.filterRepositorySpace(repository.getOVFPackage(), categroyp2);

        assertEquals(2, filtred.size());

        filtred =
            RepositoryUtils.filterRepositorySpace(repository.getOVFPackage(), "otherCategory");

        assertEquals(0, filtred.size());
    }

    public void testWriteAndRead()
    {
        File repoFile;
        RepositorySpace readRepository;

        try
        {
            repoFile = File.createTempFile("repository", "xml");

            try
            {
                OVFSerializer.getInstance().writeXML(repository, new FileOutputStream(repoFile));
            }
            catch (XMLException e)
            {
                e.printStackTrace();

                assertNull("can not write repository ", e);
            }

            try
            {
                readRepository =
                    OVFSerializer.getInstance().readXMLRepositorySpace(
                        new FileInputStream(repoFile));

                assertRepositorySpaceEquals(repository, readRepository);

            }
            catch (XMLException e)
            {
                assertNull("can not read repository ", e);
            }

        }
        catch (IOException e)
        {
            assertNull("can create tmp file", e);
        }
    }

    public void setUp()
    {
        repository = createTestReposiotrySpace(repositoryURI);
    }

    public void tearDown()
    {
        repository = null;
    }

    public static void assertRepositorySpaceEquals(RepositorySpace repo1, RepositorySpace repo2)
    {
        assertEquals(repo1.getRepositoriURI(), repo2.getRepositoriURI());

        assertEquals(repo1.getOVFPackage().size(), repo2.getOVFPackage().size());

        Iterator<OVFPackage> itPack1 = repo1.getOVFPackage().iterator();
        Iterator<OVFPackage> itPack2 = repo2.getOVFPackage().iterator();

        while (itPack1.hasNext() && itPack2.hasNext())
        {
            assertPackageEquals(itPack1.next(), itPack2.next());
        }
    }

    public static void assertPackageEquals(OVFPackage p1, OVFPackage p2)
    {
        assertEquals(p1.getOVFDescription(), p2.getOVFDescription());
        assertEquals(p1.getOVFFile(), p2.getOVFFile());
        assertEquals(p1.getOVFIcon(), p2.getOVFIcon());
        assertEquals(p1.getRepositoryURI(), p2.getRepositoryURI());
        assertEquals(p1.getOVFCategory().size(), p2.getOVFCategory().size());

        Iterator<String> itC1 = p1.getOVFCategory().iterator();
        Iterator<String> itC2 = p2.getOVFCategory().iterator();

        while (itC1.hasNext() && itC2.hasNext())
        {
            assertEquals(itC1.next(), itC2.next());
        }
    }

}
