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

package com.abiquo.abicloud.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice.virtualapplianceresource.impl.VirtualapplianceresourceHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.db.DB;
import com.abiquo.abicloud.model.VirtualAppliance;
import com.sun.ws.management.Management;
import com.sun.ws.management.client.ResourceFactory;
import com.sun.ws.management.xml.XmlBinding;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Web application lifecycle listener.
 * 
 * @author aodachi
 */

public class ContextListener implements ServletContextListener
{

    public static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {

        this.servletContext = sce.getServletContext();

        String contextPath = servletContext.getRealPath("");

        if (!contextPath.endsWith("/"))
            contextPath += "/";

        // Create the database
        DB.createDatabaseAndTables(contextPath + "WEB-INF/classes/db");

        logger.info("Initializing the context [" + sce.getServletContext().getServletContextName()
            + " ...");

        // Retrieve the virtual applications from the database
        VirtualapplianceresourceHandlerImpl.restoreVirtualAppsFromDB();

        logger.info("The context " + sce.getServletContext().getServletContextName()
            + " initialized!");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {

        logger.info("Destroying the context  [" + this.servletContext.getServletContextName()
            + "] ... ");

        // Shut down the database
        DB.shutdownDatabase();

        logger.info("The context [" + sce.getServletContext().getServletContextName()
            + "] has been destroyed");
    }
}
