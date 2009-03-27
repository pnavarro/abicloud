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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;

/**
 * This class represents a Virtual Appliance.
 */
public class VirtualAppliance
{

    /** The logger */
    private final static Logger logger = LoggerFactory.getLogger(VirtualAppliance.class);

    /** The machines. */
    private Collection<AbsVirtualMachine> machines;

    /** The virtual appliance id. */
    private String virtualApplianceId;

    /**
     * Instantiates a new virtual appliance.
     */
    public VirtualAppliance()
    {
        machines = new ArrayList<AbsVirtualMachine>();
    }

    /**
     * Gets the Virtual Appliance Id.
     * 
     * @return the Virtual Appliance Id
     */
    public String getVirtualApplianceId()
    {
        return virtualApplianceId;
    }

    /**
     * Sets the Virtual Appliance Id.
     * 
     * @param virtualApplianceId the new Virtual Appliance Id
     */
    public void setVirtualApplianceId(String virtualApplianceId)
    {
        this.virtualApplianceId = virtualApplianceId;
    }

    /**
     * Adds a virtual machine.
     * 
     * @param type the virtual machine type
     * @param address the hypervisor addres where the machine shall be deployed
     * @param config the configuration object with other parameters
     * @throws VirtualMachineException the virtual machine exception
     */
    public void addMachine(String type, URL address, VirtualMachineConfiguration config)
        throws VirtualMachineException
    {
        AbsVirtualMachine newMachine =
            VirtualSystemModel.getModel().createVirtualMachine(type, address, config);
        machines.add(newMachine);
    }

    /**
     * Gets the machines list.
     * 
     * @return the machines list
     */
    public Collection<AbsVirtualMachine> getMachines()
    {
        return machines;
    }

    /**
     * Updates the virtual appliance with the specific virtual machines list and removes the older
     * ones
     * 
     * @param vappMachines the updated list of virtual machines
     * @throws Exception
     */
    public void updateMachines(List<AbsVirtualMachine> vappMachines) throws Exception
    {
        List<AbsVirtualMachine> machinesToRemove = new ArrayList<AbsVirtualMachine>();
        machinesToRemove.addAll(machines);
        machinesToRemove.removeAll(vappMachines);
        // Deleting the removed machines
        for (AbsVirtualMachine virtualMachine : machinesToRemove)
        {
            logger.info("Removing the machine: {}", virtualMachine.getConfiguration()
                .getMachineId());
            virtualMachine.powerOffMachine();
            VirtualSystemModel.getModel().deleteMachine(
                virtualMachine.getConfiguration().getMachineId());
        }
        // Updating the machines list of the virtual appliance
        machines = vappMachines;

    }

}
