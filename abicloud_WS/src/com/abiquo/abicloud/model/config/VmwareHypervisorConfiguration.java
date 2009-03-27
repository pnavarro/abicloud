/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is available at http://www.abiquo.com/.....
 * 
 * The Initial Developer of the Original Code is Soluciones Grid, S.L. (www.abiquo.com),
 * Consell de Cent 296 principal 2�, 08007 Barcelona, Spain.
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License",
 * available at http://cpal.abiquo.com), in which case the provisions of CPAL
 * License are applicable instead of those above. In relation of this portions 
 * of the Code, a Legal Notice according to Exhibits A and B of CPAL Licence 
 * should be provided in any distribution of the corresponding Code to Graphical
 * User Interface.
 */
package com.abiquo.abicloud.model.config;

/**
 * Class representing a vmware hypervisor configuration
 * 
 * @author pnavarro
 */
public class VmwareHypervisorConfiguration
{
    /**
     * Datacenter name
     */
    private String datacenterName;

    /**
     * SAN Datastore name
     */
    private String datastoreSanName;

    /**
     * VMFS Datastore name
     */
    private String datastoreVmfsName;

    /**
     * The administrator user
     */
    private String user;

    /**
     * The administrator password
     */
    private String password;

    /**
     * This flag represents if the hypervisor server certificate should be ignored when connecting
     * to the hypervisor
     */
    private Boolean ignorecert;

    /**
     * Gets the datacenter name
     * 
     * @return the datacenterName
     */
    public String getDatacenterName()
    {
        return datacenterName;
    }

    /**
     * Sets the datacenter name
     * 
     * @param datacenterName the datacenterName to set
     */
    public void setDatacenterName(String datacenterName)
    {
        this.datacenterName = datacenterName;
    }

    /**
     * Gets the SAN datastore name
     * 
     * @return the datastore Name
     */
    public String getDatastoreSanName()
    {
        return datastoreSanName;
    }

    /**
     * Sets the SAN datastore name
     * 
     * @param datastoreName the datastore Name to set
     */
    public void setDatastoreSanName(String datastoreName)
    {
        this.datastoreSanName = datastoreName;
    }

    /**
     * Sets the vmfs datastore name
     * 
     * @param datastoreVmfsName
     */
    public void setDatastoreVmfsName(String datastoreVmfsName)
    {
        this.datastoreVmfsName = datastoreVmfsName;
    }

    /**
     * Gets the vmfs datastore name
     * 
     * @return
     */
    public String getDatastoreVmfsName()
    {
        return datastoreVmfsName;
    }

    /**
     * Gets the user
     * 
     * @return the user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * Sets the user
     * 
     * @param user the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * Gets the password
     * 
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password
     * 
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets the ignorecert flag
     * 
     * @return the ignorecert flag
     */
    public Boolean getIgnorecert()
    {
        return ignorecert;
    }

    /**
     * Sets the ignorecert flag
     * 
     * @param ignorecert the ignorecert to set
     */
    public void setIgnorecert(Boolean ignorecert)
    {
        this.ignorecert = ignorecert;
    }
}
