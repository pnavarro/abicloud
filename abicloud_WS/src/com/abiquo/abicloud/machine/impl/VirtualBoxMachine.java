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
package com.abiquo.abicloud.machine.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualbox.StorageBus;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.hypervisor.impl.VirtualBoxHypervisor;
import com.abiquo.abicloud.model.AbsVirtualMachine;
import com.abiquo.abicloud.model.VirtualDisk;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;
import com.abiquo.abicloud.utils.FileUtils;
import com.sun.xml.ws.commons.virtualbox.IConsole;
import com.sun.xml.ws.commons.virtualbox.IHardDisk2;
import com.sun.xml.ws.commons.virtualbox.IHardDiskFormat;
import com.sun.xml.ws.commons.virtualbox.IMachine;
import com.sun.xml.ws.commons.virtualbox.IMedium;
import com.sun.xml.ws.commons.virtualbox.INetworkAdapter;
import com.sun.xml.ws.commons.virtualbox.IProgress;
import com.sun.xml.ws.commons.virtualbox.ISession;
import com.sun.xml.ws.commons.virtualbox.ISnapshot;
import com.sun.xml.ws.commons.virtualbox.IVRDPServer;
import com.sun.xml.ws.commons.virtualbox.IVirtualBox;

/**
 * The virtualBox machine representation.
 * 
 * @author abiquo
 */
public class VirtualBoxMachine extends AbsVirtualMachine
{

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(VirtualBoxMachine.class);

    /** The v box hyper. */
    private VirtualBoxHypervisor vBoxHyper;

    /** The machine. */
    private IMachine machine;

    /** The machine name. */
    private String machineName;

    /** the memory ram in Mbytes */
    private long memoryRam;

    /** The number of cpus */
    private int cpuNumbers;

    /** The machine id. */
    private UUID machineId;

    /** The remote desktop port */
    private int rdpPort;

    /**
     * Instantiates a new virtual box machine.
     * 
     * @param config the config
     * @throws VirtualMachineException the virtual machine exception
     */
    public VirtualBoxMachine(VirtualMachineConfiguration config) throws VirtualMachineException
    {
        super(config);

        if (config.isSetHypervisor() & config.getHyper() instanceof VirtualBoxHypervisor)
        {
            vBoxHyper = (VirtualBoxHypervisor) config.getHyper();
        }
        else
        {
            throw new VirtualMachineException("VirtualBoxMachiner requires a VirtualBoxHypervisor "
                + "on VirtualMachineConfiguration, not a "
                + config.getHyper().getClass().getCanonicalName());
        }

        machineName = config.getMachineName();
        machineId = config.getMachineId();
        rdpPort = config.getRdPort();
        cpuNumbers = config.getCpuNumber();
        memoryRam = config.getRamAllocationUnits() / (1024 * 1024);

        deployMachine();

        logger.info("Created virtual box machine name:" + config.getMachineName() + "\t ID:"
            + config.getMachineId().toString() + "\t " + "using hypervisor connection at "
            + config.getHyper().getAddress().toString());
    }

    /**
     * Deploys the machine.
     */
    protected void deployMachine()
    {
        vBoxHyper.reconnect();
        IMachine targetMachine;
        // Getting the virtualBox hypervisor
        IVirtualBox vbox = vBoxHyper.getVirtualBox();
        // Creating the machine implies 4 steps
        // 1. Creating the mutable machine, or opening if it was created
        // targetMachine =
        // vbox.openMachine("/root/.VirtualBox/Machines/XUbuntu/XUbuntu.xml");
        logger.info("Creating the virtual machine in the hypervisor");
        // TODO if the vbox WS is shutdown the connection shall be done
        targetMachine =
            vbox.createMachine(config.getMachineName(), "Other", null, config.getMachineId());
        // 2. Assigning the VDI to the virtualMachine
        VirtualDisk virtualDisk = config.getVirtualDiskBase();
        logger.info("Assigning the virtual disk:" + virtualDisk.getLocation());
        IHardDisk2 newVDI = null;
        /*
         * try { newVDI = vbox.openHardDisk2(virtualDisk.getLocation()); } catch (Exception e) {
         * newVDI = vbox.findHardDisk2(virtualDisk.getLocation()); }
         */
        /*
         * try { newVDI = copyVDI(virtualDisk.getLocation()); } catch (IOException e) {
         * logger.info("A problem was occured when copying the file"); }
         */
        newVDI = cloneVDI(virtualDisk.getLocation());
        // 3. Saving settings ¿? We do it below
        // targetMachine.saveSettings();
        // 4. Registering machine
        vbox.registerMachine(targetMachine);
        // Getting the session
        ISession oSession = vBoxHyper.getSession();
        vbox.openSession(oSession, targetMachine.getId());
        targetMachine = oSession.getMachine();

        // Definining RAM and CPU number
        targetMachine.setMemorySize(memoryRam);
        targetMachine.setCPUCount(new Long(cpuNumbers));
        // Attaching the network adapter
        long slot = 0;
        INetworkAdapter networkAdapter = targetMachine.getNetworkAdapter(slot);
        networkAdapter.setEnabled(true);
        /*
         * networkAdapter.attachToHostInterface(); networkAdapter.setHostInterface("eth0");
         */
        networkAdapter.attachToNAT();
        // System.out.println(diskVDI.getId().toString());
        // Opening needed ports
        /*
         * logger.info("Opening web server ports");
         * targetMachine.setExtraData("VBoxInternal/Devices/pcnet/0/LUN#0/Config/web/HostPort",
         * "80");
         * targetMachine.setExtraData("VBoxInternal/Devices/pcnet/0/LUN#0/Config/web/GuestPort",
         * "80");
         * targetMachine.setExtraData("VBoxInternal/Devices/pcnet/0/LUN#0/Config/web/Protocol",
         * "TCP");
         */
        /*
         * targetMachine.setExtraData("VBoxInternal/Devices/pcnet/0/LUN#0/Config/mw/HostPort" ,
         * "60606");targetMachine.setExtraData(
         * "VBoxInternal/Devices/pcnet/0/LUN#0/Config/mw/GuestPort", "60606"); targetMachine
         * .setExtraData("VBoxInternal/Devices/pcnet/0/LUN#0/Config/mw/Protocol" , "TCP");
         */

        // logger.info("The ID is:{}", newVDI.getProperty("uuid"));
        IMedium newVDIMedium = IMedium.cast(newVDI);
        logger.info("Attaching the harddisk with the id: {}", newVDIMedium.getId());
        targetMachine.attachHardDisk2(newVDIMedium.getId(), StorageBus.IDE, 0, 0);
        targetMachine.saveSettings();
        // Closing the sessions
        if (targetMachine != null)
        {
            targetMachine.releaseRemote();
        }
        if (oSession != null)
        {
            oSession.close();
        }
    }

    /**
     * Clones a VDI
     * 
     * @param vditoClone the location of the vdi to clone
     * @return the new VDI cloned
     */
    private IHardDisk2 cloneVDI(String vditoClone)
    {
        IVirtualBox vbox = vBoxHyper.getVirtualBox();
        IHardDisk2 diskVDI;
        try
        {
            diskVDI = vbox.openHardDisk2(vditoClone);
        }
        catch (Exception e)
        {
            diskVDI = vbox.findHardDisk2(vditoClone);
        }
        logger.info("Creating the new hard disk with format: {} in the location: {}", diskVDI
            .getFormat(), vbox.getSystemProperties().getDefaultHardDiskFolder());
        UUID newVDIuuid = UUID.randomUUID();
        IHardDisk2 newVDI =
            vbox.createHardDisk2(diskVDI.getFormat(), vbox.getSystemProperties()
                .getDefaultHardDiskFolder()
                + File.separatorChar + newVDIuuid + ".vdi");
        logger.info("Cloning the disk");
        // IProgress progress = diskVDI.cloneTo(newVDI);
        IProgress progress = diskVDI.cloneTo(newVDI);
        progress.waitForCompletion(1000000);
        long rc = progress.getResultCode();
        if (rc != 0)
            logger.warn("Cloning failed!");
        return newVDI;
    }

    /**
     * Opens a remote session.
     */
    private void openRemoteSession()
    {

        String sessionType = "vrdp";
        String env = "DISPLAY=:0.0";

        logger.info("Opening the remote session");

        // openExistingSession();
        ISession oSession = vBoxHyper.getSession();
        IProgress oProgress =
            vBoxHyper.getVirtualBox().openRemoteSession(oSession, config.getMachineId(),
                sessionType, env);
        logger.info("Session for VM " + config.getMachineId().toString() + " is opened...");
        oProgress.waitForCompletion(10000);

        long rc = oProgress.getResultCode();
        if (rc != 0)
        {
            logger.warn("Session failed!");
        }

        if (oSession != null)
        {
            oSession.close();
        }

    }

    private void openExistingSession()
    {
        try
        {
            vBoxHyper.getVirtualBox().openExistingSession(vBoxHyper.getSession(), machineId);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    /**
     * Opens a session.
     * 
     * @return the i console
     */
    private IConsole openSession()
    {

        /*
         * if(!vBoxHyper.getSession().getState().equals(org.virtualbox.SessionState .OPEN)) {
         * logger.info("Opening a new session"); logger.info(vBoxHyper.getSession().getRef());
         * logger.info(machineId.toString());
         * vBoxHyper.getVirtualBox().openSession(vBoxHyper.getSession(), machineId); } else {
         * logger.info("Opening existing session");
         * vBoxHyper.getVirtualBox().openExistingSession(vBoxHyper.getSession(), machineId); }
         */
        vBoxHyper.getVirtualBox().openExistingSession(vBoxHyper.getSession(), machineId);
        return vBoxHyper.getConsole();
    }

    /**
     * Closes a session.
     */
    private void closeSession()
    {
        vBoxHyper.getSession().close();
    }

    /**
     * Starts the virtual machine execution.
     */
    public void powerOnMachine()
    {
        // TODO As the manual powerup doesn't work properly, we open a remote
        // session
        vBoxHyper.reconnect();
        // openRemoteSession();
        IVirtualBox vbox = vBoxHyper.getVirtualBox();
        ISession oSession = vBoxHyper.getSession();
        vbox.openSession(oSession, machineId);
        IMachine machine = vBoxHyper.getConsole().getMachine();
        IVRDPServer vrdpserver = machine.getVRDPServer();
        vrdpserver.setEnabled(true);
        logger.info("Activating the VRDP port: " + rdpPort);
        vrdpserver.setPort(new Long(rdpPort));
        machine.saveSettings();
        if (oSession != null)
        {
            oSession.close();
        }
        openRemoteSession();
        /*
         * vbox.openSession(oSession, machineId); IProgress oProgress =
         * vBoxHyper.getConsole().powerUp(); oProgress.waitForCompletion(200000);
         * logger.info("powerON"); long rc = oProgress.getResultCode(); if (rc != 0) {
         * logger.error("Session failed!"); } if (oSession != null) { oSession.close(); }
         */

        /*
         * //Opening a new session vBoxHyper.getVirtualBox().openSession(vBoxHyper.getSession(),
         * machineId); IProgress oProgress = vBoxHyper.getConsole().powerUp();
         * logger.info("powerON"); oProgress.waitForCompletion(20000); // TODO: getResultCode
         * closeSession();
         */
    }

    /**
     * Stops the virtual machine execution.
     */
    public void powerOffMachine()
    {
        // openSession().powerDown();
        // TODO: getResultCode
        vBoxHyper.reconnect();
        openExistingSession();
        vBoxHyper.getConsole().powerDown();

        logger.info("powered off");

        // closeSession();
    }

    /**
     * Pauses the virtual machine execution.
     */
    public void pauseMachine()
    {
        // openSession().pause();
        vBoxHyper.reconnect();
        openExistingSession();
        vBoxHyper.getConsole().pause();

        logger.info("paused");

        // closeSession();
    }

    /**
     * Resumes the virtual machine execution.
     */
    public void resumeMachine()
    {
        // openSession().resume();
        vBoxHyper.reconnect();
        openExistingSession();
        vBoxHyper.getConsole().resume();

        logger.info("resumed");

        // closeSession();
    }

    /**
     * Resets the virtual machine.
     */
    public void resetMachine()
    {
        // openSession().reset();
        vBoxHyper.reconnect();
        openExistingSession();
        vBoxHyper.getConsole().reset();

        logger.info("reset");

        // closeSession();
    }

    // ///////////////////////////////////

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.AbsVirtualMachine#findSnapshot(java.lang.String )
     */
    public void findSnapshot(String name)
    {
        ISnapshot snap;

        // openSession();

        snap = machine.findSnapshot(name);

        // TODO do something with it

        // closeSession();
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
    }

    // TODO
    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.AbsVirtualMachine#setCurrentSnapshot(java.util .UUID)
     */
    public void setCurrentSnapshot(UUID id)
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
    }

    // TODO
    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.AbsVirtualMachine#takeSnapshot(java.lang.String )
     */
    public void takeSnapshot(String name)
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
    }

    // TODO
    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.AbsVirtualMachine#populateEvent()
     */
    public void populateEvent()
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.AbsVirtualMachine#deleteMachine()
     */
    @Override
    public void deleteMachine()
    {
        vBoxHyper.reconnect();
        // Getting the virtualBox hypervisor
        IVirtualBox vbox = vBoxHyper.getVirtualBox();
        // Detaching Hard disk
        IMachine targetMachine;
        // Getting the session
        ISession oSession = vBoxHyper.getSession();
        targetMachine = vbox.findMachine(machineName);
        logger.info("The machine has the ID:" + targetMachine.getId());
        UUID machineId = targetMachine.getId();
        // TODO Waits for a seconds to be sure that the session is in the right state
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            logger.error("An error waiting the session to be closed was occurred: "
                + e.getMessage());
        }
        vbox.openSession(oSession, machineId);
        // Detaching hard disk
        targetMachine = oSession.getMachine();
        // TODO delete all the attached disks
        IHardDisk2 diskVDI = targetMachine.getHardDisk2(StorageBus.IDE, 0, 0);
        targetMachine.detachHardDisk2(StorageBus.IDE, 0, 0);
        targetMachine.saveSettings();
        IProgress progress = diskVDI.deleteStorage();
        progress.waitForCompletion(100000);
        long rc = progress.getResultCode();
        if (rc != 0)
        {
            logger.error("An eror was occurred deleting the storage");
        }
        // Closing the open session
        if (oSession != null)
        {
            oSession.close();
        }
        // Deregistering machine
        targetMachine = vbox.unregisterMachine(machineId);
        // Deleting machine settings
        // vbox.openSession(oSession,machineId);
        targetMachine.deleteSettings();
        // oSession.getMachine().deleteSettings();
        // Closing the sessions

    }

    @Override
    public void reconfigVM(VirtualMachineConfiguration newConfiguration)
        throws VirtualMachineException
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
        throw new VirtualMachineException(msg);

    }

}
