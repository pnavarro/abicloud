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
package com.abiquo.abiserver.services.flex;

import java.util.ArrayList;

import flex.messaging.io.ArrayCollection;

import com.abiquo.abiserver.business.locators.resource.ResourceLocator;
import com.abiquo.abiserver.commands.InfrastructureCommand;
import com.abiquo.abiserver.commands.VirtualApplianceCommand;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.user.Enterprise;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualAppliance;

/**
 * This class defines a wide set of services that are considered "non-blocking services".
 * 
 * @author Oliver
 */

public class NonBlockingService
{
    /* ______________________________ INFRASTRUCTURE _______________________________ */

    /**
     * @param session
     * @param virtualMachine
     * @return A DataResult object, containing the new State for the virtualMachine
     */
    public BasicResult startVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = virtualMachine;

        return infrastructureCommand.execute(session, ResourceLocator.VIRTUALMACHINE_START, args);
    }

    /**
     * @param session
     * @param virtualMachine
     * @return A DataResult object, containing the new State for the virtualMachine
     */
    public BasicResult pauseVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = virtualMachine;

        return infrastructureCommand.execute(session, ResourceLocator.VIRTUALMACHINE_PAUSE, args);
    }

    /**
     * @param session
     * @param virtualMachine
     * @return A DataResult object, containing the new State for the virtualMachine
     */
    public BasicResult rebootVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = virtualMachine;

        return infrastructureCommand.execute(session, ResourceLocator.VIRTUALMACHINE_REBOOT, args);
    }

    /**
     * @param session
     * @param virtualMachine
     * @return A DataResult object, containing the new State for the virtualMachine
     */
    public BasicResult shutdownVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = virtualMachine;

        return infrastructureCommand
            .execute(session, ResourceLocator.VIRTUALMACHINE_SHUTDOWN, args);
    }

    /**
     * Checks the current state of a list of virtual machines
     * 
     * @param session
     * @param virtualMachinesToCheck
     * @return A DataResult object containing a list of the same virtual machines with their state
     *         updated
     */
    @SuppressWarnings("unchecked")
    public BasicResult checkVirtualMachinesState(UserSession session,
        ArrayCollection virtualMachinesToCheck)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];

        ArrayList<VirtualMachine> virtualMachines =
            new ArrayList<VirtualMachine>(virtualMachinesToCheck);
        args[0] = virtualMachines;

        return infrastructureCommand.execute(session, ResourceLocator.CHECK_VIRTUAL_MACHINES_STATE,
            args);
    }

    /* ______________________________ VIRTUAL APPLIANCE _______________________________ */

    /**
     * Performs a "Start" action in the Virtual Machine
     * 
     * @param session
     * @param virtualAppliance
     * @return a DataResult object, with a com.abiquo.abiserver.pojo.infrastructure.State object
     *         that represents the state "Running"
     */
    public BasicResult startVirtualAppliance(UserSession session, VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = virtualAppliance;

        return virtualApplianceCommand.execute(session, ResourceLocator.VIRTUALAPPLIANCE_START,
            args);
    }

    /**
     * Performs a "Shutdown" action in the Virtual Machine
     * 
     * @param session
     * @param virtualAppliance
     * @return a DataResult object, with a com.abiquo.abiserver.pojo.infrastructure.State object
     *         that represents the state "Powered Off"
     */
    public BasicResult shutdownVirtualAppliance(UserSession session,
        VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = virtualAppliance;

        return virtualApplianceCommand.execute(session, ResourceLocator.VIRTUALAPPLIANCE_SHUTDOWN,
            args);
    }

    /**
     * Modifies the information of a VirtualAppliance that already exists in the Data Base
     * 
     * @param session
     * @param virtualAppliance
     * @return A DataResult object, containing an ArrayList of Node, with the Virtual Appliance's
     *         Nodes updated
     */
    public BasicResult editVirtualAppliance(UserSession session, VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = virtualAppliance;

        return virtualApplianceCommand
            .execute(session, ResourceLocator.VIRTUALAPPLIANCE_EDIT, args);
    }

    /**
     * Deletes a VirtualAppliance that exists in the Data Base
     * 
     * @param session
     * @param virtualAppliance
     * @return a BasicResult object, containing success = true if the deletion was successful
     */
    public BasicResult deleteVirtualAppliance(UserSession session, VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = virtualAppliance;

        return virtualApplianceCommand.execute(session, ResourceLocator.VIRTUALAPPLIANCE_DELETE,
            args);
    }

    /**
     * Retrieves an updated list of Virtual Appliances that belong to the same Enterprise The
     * Virtual Appliances retrieved will not contain their list of nodes, for performance purposes
     * 
     * @param userSession
     * @param enterprise The Enterprise to retrieve the VirtualAppliance list
     * @return a DataResult<ArrayList<VirtualAppliance>> object with the VirtualAppliance that belong
     *         to the given enterprise
     */
    @SuppressWarnings("unchecked")
    public BasicResult checkVirtualAppliancesByEnterprise(UserSession userSession,
        Enterprise enterprise)
    {
        // This service will call the method getVirtualAppliancesByEnterprise from
        // VirtualApplianceCommand
        // since is just what we need
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = enterprise;

        return virtualApplianceCommand.execute(userSession,
            ResourceLocator.VIRTUALAPPLIANCE_GETBYENTERPRISE, args);
    }

    /**
     * Retrieves a VirtualAppliance, with the current values in DataBase. Since a client can have an
     * old version of a VirtualAppliance, this service is useful to get the updated state of a
     * Virtual Appliance
     * 
     * @param session
     * @param virtualAppliance The VirtualAppliance to check.
     * @return a DataResult<VirtualAppliance> object with the last updated values in DataBase The
     *         returned VirtualAppliance will contain its list of noded
     */
    public BasicResult checkVirtualAppliance(UserSession session, VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = virtualAppliance;

        return virtualApplianceCommand.execute(session, ResourceLocator.CHECK_VIRTUAL_APPLIANCE,
            args);
    }
}
