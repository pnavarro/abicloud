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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;
import java.util.logging.Logger;

import com.abiquo.abicloud.exception.PluginException;
import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;
import com.abiquo.abicloud.plugin.HypervisorManager;

/**
 * The Class VirtualSystemModel represents a mini model to maintain a list of virtual systems.
 */
public class VirtualSystemModel extends Observable
{

    /** The logger. */
    private Logger logger = Logger.getLogger(VirtualSystemModel.class.getName());

    /** All VirutalMachines indexed by id. */
    private static Map<UUID, AbsVirtualMachine> virtualMachines =
        new HashMap<UUID, AbsVirtualMachine>();

    /**
     * Controller where obtain hypervisor singleton instances (identified by hypervisor type +
     * address).
     */
    private static final HypervisorManager hypervisors = new HypervisorManager();

    /** The singleton instance for this class. */
    private static VirtualSystemModel singleton;

    /**
     * Instantiates a new virtual system model.
     */
    private VirtualSystemModel()
    {
        super();
    }

    /**
     * Singleton accessor.
     * 
     * @return the model
     */
    public static VirtualSystemModel getModel()
    {
        if (singleton == null)
        {
            singleton = new VirtualSystemModel();
        }

        return singleton;
    }

    /**
     * Gets a list of all available virtual machines.
     * 
     * @return the machines
     */
    public List<AbsVirtualMachine> getMachines()
    {
        return new ArrayList<AbsVirtualMachine>(virtualMachines.values());
    }

    /**
     * Gets a virtual machine by id.
     * 
     * @param machineId the id of the machine to get
     * @return the machine
     * @throws VirtualMachineException the virtual machine exception
     * @throw VirtualMachineException if there is any machine with the required machineId
     */
    public AbsVirtualMachine getMachine(UUID machineId) throws VirtualMachineException
    {

        if (virtualMachines.containsKey(machineId))
        {
            return virtualMachines.get(machineId);
        }
        else
        {
            return null;
            // throw new VrtualMachineException("There is not any machine with ID " +
            // machineId.toString());
        }
    }

    /**
     * Finds a virtual machine using its virtual machine name.
     * 
     * @param machineName desired machine name (case sensitive) to get
     * @return the abs virtual machine
     * @throws VirtualMachineException the virtual machine exception
     * @throw VirtualMachineException if there is any machine with the required machineName
     * @deprecated better use getMachine by its UUID
     */
    public AbsVirtualMachine findMachine(String machineName) throws VirtualMachineException
    {
        AbsVirtualMachine machine;
        Iterator<AbsVirtualMachine> machinesIte;
        boolean found;

        machine = null;
        found = false;
        machinesIte = virtualMachines.values().iterator();

        while (!found && machinesIte.hasNext())
        {
            machine = machinesIte.next();

            if (machineName.equals(machine.getConfiguration().getMachineName()))
            {
                found = true;
            }
        }

        if (found)
        {
            logger.info("Find machine name " + machineName + " \t ID:"
                + machine.getConfiguration().getMachineId().toString());

            return machine;
        }
        else
        {
            throw new VirtualMachineException("There is not any machine with name " + machineName);
        }

    }

    /**
     * Deletes the virtual machine.
     * 
     * @param machineId the id of the machine to delete
     * @throws Exception
     * @throw VirtualMachineException if there is any machine with the required machineId
     */
    public void deleteMachine(UUID machineId) throws Exception
    {

        if (virtualMachines.containsKey(machineId))
        {

            // TODO
            AbsVirtualMachine machineTarget = virtualMachines.get(machineId);
            machineTarget.deleteMachine();

            virtualMachines.remove(machineId);

            logger.info("Removed machine " + machineId.toString());
        }
        else
        {
            throw new VirtualMachineException("There is not any machine with ID "
                + machineId.toString());
        }

        setChanged();
    }

    /**
     * Creates a new virtual machine instance using the Hypervisor type located on address.
     * 
     * @param type the Hypervisor type
     * @param address the Hypervisor address
     * @param config the config
     * @return the abs virtual machine
     * @throws VirtualMachineException it there is any plugin to instantiate an hypervisor for the
     *             given type
     */
    public AbsVirtualMachine createVirtualMachine(String type, URL address,
        VirtualMachineConfiguration config) throws VirtualMachineException // String
                                                                           // virtualSystemId,
                                                                           // String
    // machineName
    {
        IHypervisor hyper;
        AbsVirtualMachine machine;
        UUID machineId;

        try
        {
            hyper = hypervisors.getHypervisor(type, address);

            logger.info("Got hypervisor type: " + type + "\t at address:" + address.toString());

            config.setHypervisor(hyper);

            machineId = config.getMachineId();// UUID.fromString(virtualSystemId);
            machine = hyper.createMachine(config);// machineName, machineId);
            // TODO machine.init(machineName, UUID.fromString(virtualSystemId));

            logger.info("Created machine name:" + config.getMachineName() + "\t : "
                + config.getMachineId().toString());

            virtualMachines.put(machineId, machine);
        }
        catch (PluginException e)
        {
            throw new VirtualMachineException(e);
        }

        setChanged();

        return machine;
    }
}
