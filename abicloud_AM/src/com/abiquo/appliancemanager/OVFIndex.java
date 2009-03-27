package com.abiquo.appliancemanager;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.appliancemanager.exceptions.DownloadException;
import com.abiquo.appliancemanager.exceptions.InvalidRepositorySpace;
import com.abiquo.appliancemanager.exceptions.XMLException;
import com.abiquo.appliancemanager.filesystems.FileFactory;
import com.abiquo.appliancemanager.util.RepositoryUtils;
import com.abiquo.appliancemanager.xml.OVFSerializer;
import com.abiquo.ovfindex.OVFPackage;
import com.abiquo.ovfindex.RepositorySpace;

/**
 * Maintain a RespositorySpace resuming all the attached RepositorySpaces.<br/>
 * 
 * @todo export OVFIndex XML document to be accessed (default http??).
 */
public class OVFIndex
{
    private final static Logger log = LoggerFactory.getLogger(OVFIndex.class);

    /** Allow read/write XML documents . */
    // private final OVFSerializer xmlOvf;
    /** Store all the OVFPackages on all RepositorySpaces . */
    private RepositorySpace repository;

    /** Index all the attached RepositorySpaces by its URI. */
    private Map<URI, RepositorySpace> allSpaces = new HashMap<URI, RepositorySpace>();

    /** Where the ovfindex.xml is persisted. */
    // TODO use the correct path to expose index on the repositoryURI used on constructor
    // private String repositoryUri = "state" + File.separatorChar + "ovfindex.xml";
    private final URI repositoryURI;

    /**
     * Creates a new ReposiorySpace.
     * 
     * @param repositoryURI, the repository URI to be used.
     * @throws JAXBException can create OVF XML serializer. TODO restore from ????
     */
    public OVFIndex(URI repositoryURI) throws JAXBException
    {
        this.repositoryURI = repositoryURI;

        repository = new RepositorySpace();
        repository.setRepositoriURI(repositoryURI.toASCIIString());
        // xmlOvf = OVFSerializer.getInstance();

        log.debug("Created repositor space on " + repositoryURI.toASCIIString());
    }

    /**
     * Returns the RepositorySpace.
     * 
     * @return the RepositorySpace resuming all the attached ReposiotrySpaces.
     */
    public RepositorySpace getRepositorySpace()
    {
        return repository;
    }

    public void setRepositorySpace(RepositorySpace repo)
    {
        repository = repo;
    }

    public Set<URI> getAttachedRepositorySpaces()
    {
        return allSpaces.keySet();
    }

    /**
     * Attach a new RepositorySpace, adding all its OVFPackages into the resume RS.
     * 
     * @param repoURI, where the ovfindex.xml is read (@TODO check known protocol)
     * @throws FileNotFoundException if the provided repositoryURI can not be reach.
     */
    public void addRespositorySpace(URI repoURI) throws InvalidRepositorySpace //
    {
        RepositorySpace repo;

        try
        {
            repo =
                OVFSerializer.getInstance().readXMLRepositorySpace(
                    FileFactory.open(repoURI.toURL()));
        }
        catch (XMLException e) // XMLStreamException or JAXBException
        {
            final String msg =
                "Invalid OVFIndex.xml on RepositorySpace: " + repoURI.toASCIIString();
            throw new InvalidRepositorySpace(msg, e);
        }
        catch (MalformedURLException e)
        {
            final String msg = "Invalid repository space identifier : " + repoURI.toASCIIString();
            throw new InvalidRepositorySpace(msg, e);
        }
        catch (DownloadException e)
        {
            final String msg =
                "the OVFIndex.xml can not be downloaded from : " + repoURI.toASCIIString();
            throw new InvalidRepositorySpace(msg, e);
        }

        allSpaces.put(repoURI, repo);

        // TODO check for duplicatets
        repository.getOVFPackage().addAll(repo.getOVFPackage());

        log.debug("Attached repository space " + repoURI.toASCIIString());
    }

    /**
     * Refresh the content on the RepositorySpace for the given repository URI.
     * 
     * @param repoURI, what to update.
     * @throws InvalidRepositorySpace, purge the ovfpackages (old OVFPackages can not be accessed
     *             anymore)
     */
    public void updateIndex(URI repoURI) throws InvalidRepositorySpace
    {
        // TODO check it was already on 'allSpaces'
        allSpaces.remove(repoURI);

        RepositoryUtils.purgeRepositorySpace(repository.getOVFPackage(), repoURI.toASCIIString());

        addRespositorySpace(repoURI);

        log.debug("Updated repository " + repoURI.toASCIIString());
    }

    /**
     * Get all the OVFPackages form the RepositorySpace.
     * 
     * @param repositoryURI, the RepositorySpace to use, if null, use the resume (master)
     *            RepositorySpace (all of it).
     * @param category, to filter the OVFPackages, if null, any filter.
     * @return all the OVFPackages with provided category an on the given repositoryURI.
     */
    public List<OVFPackage> listRepository(URI repositoryURI, String category)
        throws InvalidRepositorySpace
    {
        List<OVFPackage> packages = new LinkedList<OVFPackage>();

        if (repositoryURI == null)
        {
            packages = repository.getOVFPackage();
        }
        else if (this.repositoryURI.equals(repositoryURI))
        {
            packages = repository.getOVFPackage();
        }
        else if (allSpaces.keySet().contains(repositoryURI))
        {
            packages = allSpaces.get(repositoryURI).getOVFPackage();
        }
        else
        {
            final String msg =
                "The reposioryURI " + repositoryURI + " is not attached to this index";
            throw new InvalidRepositorySpace(msg);
        }

        if (category != null)
        {
            packages = RepositoryUtils.filterRepositorySpace(packages, category);
        }

        return packages;
    }
}
