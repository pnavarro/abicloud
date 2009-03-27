package com.abiquo.abicloud.vsm.model.config;

/**
 * Main configuration class
 * 
 * @author pnavarro
 */
public class Configuration
{
    /**
     * Vmware hypervisor configuration
     */
    private VmwareHypervisorConfiguration vmwareHyperConfig;

    /**
     * Gets the vmware hypervisor configuration
     * 
     * @return the vmwareHyperConfig
     */
    public VmwareHypervisorConfiguration getVmwareHyperConfig()
    {
        return vmwareHyperConfig;
    }

    /**
     * Sets the vmware hypervisor configuration
     * 
     * @param vmwareHyperConfig the vmwareHyperConfig to set
     */
    public void setVmwareHyperConfig(VmwareHypervisorConfiguration vmwareHyperConfig)
    {
        this.vmwareHyperConfig = vmwareHyperConfig;
    }
}
