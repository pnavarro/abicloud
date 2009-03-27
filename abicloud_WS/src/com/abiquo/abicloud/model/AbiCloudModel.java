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
package com.abiquo.abicloud.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.model.config.ConfigurationManager;

/**
 * Class representing AbiCloud's Model It is a Singleton call
 * 
 * @author pnavarro
 */
public class AbiCloudModel
{
    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(AbiCloudModel.class);

    /** The singleton instance for this class. */
    private static AbiCloudModel singleton;

    private ConfigurationManager configManager;

    /**
     * Default Constructor
     */
    public AbiCloudModel()
    {
        configManager = new ConfigurationManager();
    }

    /**
     * Singleton accessor.
     * 
     * @return the model
     */
    public static AbiCloudModel getInstance()
    {
        if (singleton == null)
        {
            singleton = new AbiCloudModel();
        }

        return singleton;
    }

    /**
     * Gets the configuration manager
     * 
     * @return the configuration manager
     */
    public ConfigurationManager getConfigManager()
    {
        return configManager;
    }

    /**
     * Sets the configuration manager
     * 
     * @param configManager the configuration manager
     */
    public void setConfigManager(ConfigurationManager configManager)
    {
        this.configManager = configManager;
    }
}
