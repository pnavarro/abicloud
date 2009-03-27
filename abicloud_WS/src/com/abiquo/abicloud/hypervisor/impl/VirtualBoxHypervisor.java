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
package com.abiquo.abicloud.hypervisor.impl;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.machine.impl.VirtualBoxMachine;
import com.abiquo.abicloud.model.AbsVirtualMachine;
import com.abiquo.abicloud.model.IHypervisor;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;
import com.sun.xml.ws.commons.virtualbox.IConsole;
import com.sun.xml.ws.commons.virtualbox.ISession;
import com.sun.xml.ws.commons.virtualbox.IVirtualBox;
import com.sun.xml.ws.commons.virtualbox.IWebsessionManager;

/**
 * The Class VirtualBoxHypervisor represents the VirtualBox hypervisor.
 */
public class VirtualBoxHypervisor implements IHypervisor
{

    /** The Constant logger. */
    private final static Logger logger =
        LoggerFactory.getLogger(VirtualBoxHypervisor.class.getName());

    /** The url. */
    private URL url;

    /** The mgr. */
    private IWebsessionManager mgr;

    /** The vbox. */
    private IVirtualBox vbox;

    /** The o session. */
    private ISession oSession;

    /**
     * virtual box specific function to VirtualBoxMachine.
     * 
     * @return the session
     */
    public ISession getSession()
    {
        /*
         * ISession remoteSession = mgr.getSessionObject(vbox); // reconnect(); String sessionRef =
         * remoteSession.getRef(); if (!oSession.getRef().equals(sessionRef)) { reconnect();
         * logger.info("Updating the session"); oSession = mgr.getSessionObject(vbox); }
         */
        return oSession;
        // return mgr.getSessionObject(vbox);

    }

    /**
     * virtual box specific function to VirtualBoxMachine.
     * 
     * @return the virtual box
     */
    public IVirtualBox getVirtualBox()
    {
        return vbox;
    }

    /**
     * Gets the console object
     * 
     * @return the console
     */
    public IConsole getConsole()
    {
        IConsole console = getSession().getConsole();
        return console;

    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.IHypervisor#connect(java.net.URL)
     */
    public void connect(URL url)
    {
        if (url == null)
        {
            logger.error("The url can not be null");
            throw new RuntimeException("The url to connect to the hypervisor can not be null");
        }

        this.url = url;

        mgr = new IWebsessionManager(url);
        vbox = mgr.logon("test", "test");
        oSession = mgr.getSessionObject(vbox);

        logger.info("Initialized connection to VirtualBox address:" + url.toString()
            + "\t version " + vbox.getVersion());
    }

    /**
     * Reconnects to the hypervisors
     */
    public void reconnect()
    {
        connect(this.url);
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.IHypervisor#logout()
     */
    public void logout()
    {
        mgr.logoff(vbox);

        logger.info("Logged out form VirtualBox at address:" + url.toString());

        vbox = null;
        mgr = null;
        oSession = null;
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.IHypervisor#login(java.lang.String, java.lang.String)
     */
    public void login(String user, String password)
    {
        vbox = mgr.logon(user, password);

        logger.info("Logged in with user " + user);

        oSession = mgr.getSessionObject(vbox);
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.IHypervisor#createMachine(com.abiquo.abicloud
     * .model.VirtualMachineConfiguration)
     */
    public AbsVirtualMachine createMachine(VirtualMachineConfiguration config)
        throws VirtualMachineException // String
    // machineName,
    // UUID
    // machineId
    {
        // config.setHypervisor(this); esto se hace en el model

        return new VirtualBoxMachine(config);
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.IHypervisor#getAddress()
     */
    public URL getAddress()
    {
        return url;
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.IHypervisor#getHypervisorType()
     */
    public String getHypervisorType()
    {
        return "vBox";
    }

    @Override
    public void init(URL url)
    {
        logger.info("VirtualBox Hypervisor initialized");

    }

}
