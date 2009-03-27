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
 * Consell de Cent 296, Principal 2�, 08007 Barcelona, Spain.
 * 
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License", 
 * available at http://cpal.abiquo.com/), in which case the 
 * provisions of CPAL License are applicable instead of those above. In relation 
 * of this portions of the Code, a Legal Notice according to Exhibits A and B of 
 * CPAL Licence should be provided in any distribution of the corresponding Code 
 * to Graphical User Interface.
 */
package com.abiquo.abiserver;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.abiquo.abiserver.abicloudws.AbiCloudConstants;
import com.abiquo.util.resources.ResourceConstants;
import com.abiquo.util.resources.ResourceManager;

/**
 * Configuration of abiserver Loads the configuration parameters from the file abi.config.xml
 */
public class AbiConfiguration
{

    private static AbiConfiguration singletonObject;

    private static final Logger logger = Logger.getLogger(AbiConfiguration.class);

    private String dataCenterName;

    private String destination;

    private long timeout;

    private String dbUrl;

    private String dbUserName;

    private String dbPassword;

    private int sessionTimeout;

    private int maxNumSessions;

    private Integer virtualMachinesPerDatacenter;

    public static AbiConfiguration getAbiConfiguration()
    {

        if (AbiConfiguration.singletonObject == null)
            AbiConfiguration.singletonObject = new AbiConfiguration();

        return AbiConfiguration.singletonObject;
    }

    private AbiConfiguration()
    {

        Properties properties = new Properties();

        try
        {

            InputStream inStream =
                this.getClass().getClassLoader().getResourceAsStream("conf/abi.config.xml");
            // InputStream inStream =
            // this.getClass().getClassLoader().getResourceAsStream("conf/networking_config.xml");

            if (inStream != null)
                properties.loadFromXML(inStream);

            this.timeout = new Long(properties.getProperty("timeout"));
            this.dataCenterName = properties.getProperty("dataCenterName");
            this.dbPassword = properties.getProperty("db.password");
            this.dbUrl = properties.getProperty("db.url");
            this.dbUserName = properties.getProperty("db.username");
            this.destination = properties.getProperty("destination");
            this.sessionTimeout = new Integer(properties.getProperty("sessionTimeout").trim());
            this.maxNumSessions = new Integer(properties.getProperty("maxNumSessions").trim());
            this.virtualMachinesPerDatacenter =
                new Integer(properties.getProperty("virtualMachinesDatacenter").trim());

        }
        catch (Exception e)
        {
            logger.error(e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException("This is a singleton class and hence can only have one object");
    }

    /**
     * @return the dataCenterName
     */
    public String getDataCenterName()
    {
        return dataCenterName;
    }

    /**
     * @return the destination
     */
    public String getDestination()
    {
        return destination;
    }

    /**
     * @return the timeout
     */
    public long getTimeout()
    {
        return timeout;
    }

    public String getDbUserName()
    {
        return dbUserName;
    }

    public String getDbUrl()
    {
        return dbUrl;
    }

    public String getDbPassword()
    {
        return dbPassword;
    }

    /**
     * @return the sessionTimeout
     */
    public int getSessionTimeout()
    {
        return sessionTimeout;
    }

    /**
     * Retrieves the maximum number of sessions as entered in the configuration file. A value of 0
     * indicates no limit
     * 
     * @return the maxNumSessions
     */
    public int getMaxNumSessions()
    {
        return maxNumSessions;
    }

    /**
     * @return the virtualMachinesPerDatacenter
     */
    public Integer getVirtualMachinesPerDatacenter()
    {
        return virtualMachinesPerDatacenter;
    }

    /**
     * @param virtualMachinesPerDatacenter the virtualMachinesPerDatacenter to set
     */
    public void setVirtualMachinesPerDatacenter(Integer virtualMachinesPerDatacenter)
    {
        this.virtualMachinesPerDatacenter = virtualMachinesPerDatacenter;
    }

    public String toString()
    {
        return com.abiquo.util.ToString.toString(this);
    }

}
