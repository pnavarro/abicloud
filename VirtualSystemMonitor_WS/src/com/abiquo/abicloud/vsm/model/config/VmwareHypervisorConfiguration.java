package com.abiquo.abicloud.vsm.model.config;

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
