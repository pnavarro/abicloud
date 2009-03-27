package com.abiquo.abicloud.vsm.model.config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * The configuration manager singleton
     */
    private static ConfigurationManager singleton;

    /**
     * Main configuration file
     */
    private Configuration configuration;

    /**
     * Default Constructor
     */
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
     * Singleton accessor.
     * 
     * @return the model
     */
    public static ConfigurationManager getInstance()
    {
        if (singleton == null)
        {
            singleton = new ConfigurationManager();
        }

        return singleton;
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
        vmwareHyperConfig.setDatacenterName(xmlConfig.getString("hypervisors.vmware.datacenter",
            "ha-datacenter"));
        vmwareHyperConfig.setUser(xmlConfig.getString("hypervisors.vmware.user", "root"));
        vmwareHyperConfig.setPassword(xmlConfig.getString("hypervisors.vmware.password", null));
        vmwareHyperConfig
            .setIgnorecert(xmlConfig.getBoolean("hypervisors.vmware.ignorecert", true));
        configuration.setVmwareHyperConfig(vmwareHyperConfig);
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
