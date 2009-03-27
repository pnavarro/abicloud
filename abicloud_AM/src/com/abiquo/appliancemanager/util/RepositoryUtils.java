package com.abiquo.appliancemanager.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.abiquo.ovfindex.OVFPackage;

/**
 * Manipulate objects on the OVFIndex name space. <br/>
 * Hide from other classes the use of JAXB. Provide functions to filter OVFPackage lists.
 */
public class RepositoryUtils
{
    /** Generated factory to XML elements on OVF name space creation. */
    // private final static com.abiquo.ovfindex.ObjectFactory repositoryFactory =
    // new com.abiquo.ovfindex.ObjectFactory();
    /**
     * Remove form the provided OVFPackages with ones with repository URI equals to the given one.
     * 
     * @param packages, an input/output list with the OVFPackages to check.
     * @param repositoryURI, the repository URI to remove all its OVFPackages.
     */
    public static void purgeRepositorySpace(List<OVFPackage> packages, final String repositoryURI)
    {
        Iterator<OVFPackage> itPackage = packages.iterator();

        while (itPackage.hasNext())
        {
            if (repositoryURI.equalsIgnoreCase(itPackage.next().getRepositoryURI()))
            {
                itPackage.remove();
            }
        }
    }

    /**
     * Return all the OVFPackages from the provided OVFPackages with category equals to the given
     * one.
     * 
     * @param packages, a list with the OVFPackages to check.
     * @param category, the category to maintains all its OVFPackages.
     * @return an OVFPackage list, all with category.
     */
    public static List<OVFPackage> filterRepositorySpace(List<OVFPackage> packages,
        final String category)
    {
        List<OVFPackage> filteredPackages = new LinkedList<OVFPackage>();

        for (OVFPackage currentPackage : packages)
        {
            if (currentPackage.getOVFCategory().contains(category))
            {
                filteredPackages.add(currentPackage);
            }
        }

        return filteredPackages;
    }
}
