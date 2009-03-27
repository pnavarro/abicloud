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

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.hypervisor.impl.KVMHypervisor;
import com.abiquo.abicloud.model.AbsVirtualMachine;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;

/**
 * The virtualBox machine representation.
 * 
 * @author abiquo
 */
public class KVMMachine extends AbsVirtualMachine
{

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(VirtualBoxMachine.class);

    /** The KVM hyper. */
    private KVMHypervisor KVMHyper;

    /** The machine name. */
    private String machineName;

    /** The domain, aka the virtual machine handler **/
    private Domain dom;

    /**
     * Instantiates a new KVM machine.
     * 
     * @param config the config
     * @throws VirtualMachineException the virtual machine exception
     */
    public KVMMachine(VirtualMachineConfiguration config) throws VirtualMachineException
    {
        super(config);

        if (config.isSetHypervisor() & config.getHyper() instanceof KVMHypervisor)
        {
            KVMHyper = (KVMHypervisor) config.getHyper();
        }
        else
        {
            throw new VirtualMachineException("KVMMachine requires a KVMHypervisor "
                + "on VirtualMachineConfiguration, not a "
                + config.getHyper().getClass().getCanonicalName());
        }

        machineName = config.getMachineName();
    }

    /**
     * Deploys the machine.
     */
    protected void deployMachine()
    {
        KVMHyper.reconnect();
        Connect conn = KVMHyper.getConnection();

        try
        {
            // Get the domain handler
            dom = conn.domainLookupByName(machineName);
        }
        catch (LibvirtException e)
        {
            logger.error("Virtual Machine lookup exception caught:" + e);
        }

    }

    /**
     * Starts the virtual machine execution.
     */
    public void powerOnMachine()
    {
        try
        {
            // Create the domain
            dom.create();
        }
        catch (LibvirtException e)
        {
            logger.error("creat exception caught:" + e);
        }
    }

    /**
     * Stops the virtual machine execution.
     */
    public void powerOffMachine()
    {
        try
        {
            // Create the domain
            dom.destroy();
        }
        catch (LibvirtException e)
        {
            logger.error("creat exception caught:" + e);
        }
    }

    /**
     * Pauses the virtual machine execution.
     */
    public void pauseMachine()
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
    }

    /**
     * Resumes the virtual machine execution.
     */
    public void resumeMachine()
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
    }

    /**
     * Resets the virtual machine.
     */
    public void resetMachine()
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
    }

    // ///////////////////////////////////

    /*
     * (non-Javadoc)
     * @see com.abiquo.abicloud.model.AbsVirtualMachine#findSnapshot(java.lang.String )
     */
    public void findSnapshot(String name)
    {
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
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);
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
