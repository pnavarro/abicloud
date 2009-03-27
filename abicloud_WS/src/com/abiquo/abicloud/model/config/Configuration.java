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
     * NAS remote path
     */
    private String remotePath;

    /**
     * NAS remote hot
     */
    private String remoteHost;

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

    /**
     * Gest the NAS remote Path
     * 
     * @return the remotePath
     */
    public String getRemotePath()
    {
        return remotePath;
    }

    /**
     * Sets the NAS remote path
     * 
     * @param remotePath the remotePath to set
     */
    public void setRemotePath(String remotePath)
    {
        this.remotePath = remotePath;
    }

    /**
     * Gets the NAS remote host
     * 
     * @return the remotehost
     */
    public String getRemoteHost()
    {
        return remoteHost;
    }

    /**
     * Sets the NAS remote host
     * 
     * @param remotehost the remotehost to set
     */
    public void setRemoteHost(String remotehost)
    {
        this.remoteHost = remotehost;
    }

}
