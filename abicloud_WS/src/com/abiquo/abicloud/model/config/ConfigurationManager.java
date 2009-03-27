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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.model.AbiCloudModel;

/**
 * Class to manage and load the configuration file
 * 
 * @author pnavarro
 */
public class ConfigurationManager
{
    /**
     * The default abicloud config file location
     */
    private final static String DEFAULT_CONFIG_FILE = "conf/config.xml";

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

    /**
     * Main configuration file
     */
    private Configuration configuration;

    public ConfigurationManager()
    {
        configuration = new Configuration();
        try
        {
            loadXML();
        }
        catch (ConfigurationException e)
        {
            logger.error("An configuration erro was occured: {}", e.getMessage());
        }
    }

    /**
     * Loads the XML
     * 
     * @throws ConfigurationException
     */
    private void loadXML() throws ConfigurationException
    {
        URL configUrl = this.getClass().getClassLoader().getResource(DEFAULT_CONFIG_FILE);

        XMLConfiguration xmlConfig = new XMLConfiguration(configUrl);

        /**
         * Hypervisors
         */
        // Vmware hpervisor
        VmwareHypervisorConfiguration vmwareHyperConfig = new VmwareHypervisorConfiguration();
        vmwareHyperConfig.setDatastoreSanName(xmlConfig.getString(
            "hypervisors.vmware.SanDatastore[@name]", "nfsrepository"));
        vmwareHyperConfig.setDatastoreVmfsName(xmlConfig.getString(
            "hypervisors.vmware.VmfsDatastore[@name]", "datastore1"));
        vmwareHyperConfig.setDatacenterName(xmlConfig.getString("hypervisors.vmware.datacenter",
            "ha-datacenter"));
        vmwareHyperConfig.setUser(xmlConfig.getString("hypervisors.vmware.user", "root"));
        vmwareHyperConfig.setPassword(xmlConfig.getString("hypervisors.vmware.password", null));
        vmwareHyperConfig
            .setIgnorecert(xmlConfig.getBoolean("hypervisors.vmware.ignorecert", true));
        configuration.setVmwareHyperConfig(vmwareHyperConfig);

        /**
         * Repository
         */
        configuration.setRemoteHost(xmlConfig.getString("repository.remoteHost", null));
        configuration.setRemotePath(xmlConfig.getString("repository.remotePath",
            "/opt/vm_repository"));

    }

    /**
     * Gets the configuration
     * 
     * @return the configuration
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Sets the configuration
     * 
     * @param configuration the configuration to set
     */
    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

}
