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

import com.abiquo.abiserver.business.locators.resource.ResourceLocator;
import com.abiquo.abiserver.commands.InfrastructureCommand;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.infrastructure.DataCenter;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisor;
import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;
import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachineCreation;
import com.abiquo.abiserver.pojo.infrastructure.Rack;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.result.BasicResult;

/**
 * This class defines all services related to Infrastructure management
 * 
 * @author Oliver
 */

public class InfrastructureService
{

    /* ______________________________ DATA CENTER _______________________________ */

    /**
     * Returns the infrastructure stored in a Data Center
     * 
     * @param session
     * @param dataCenter The Data Center we want to recover the infrastructure from
     * @return Returns a DataResult, containing an ArrayList of InfrastructureElement
     */

    public BasicResult getInfrastructureByDataCenter(UserSession session, DataCenter dataCenter)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = dataCenter;

        return infrastructureCommand.execute(session, ResourceLocator.DATACENTER_GETINFRASTRUCTURE,
            args);
    }

    /**
     * Returns all Data Centers
     * 
     * @param session
     * @param user
     * @return a DataResult object, with an ArrayList of DataCenter
     */
    public BasicResult getDataCenters(UserSession session)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();

        return infrastructureCommand.execute(session, ResourceLocator.DATACENTER_GETDATACENTERS,
            null);
    }

    /**
     * Creates a new Data Center
     * 
     * @param session
     * @param dataCenter
     * @return a DataResult, with the Data Center created
     */
    public BasicResult createDataCenter(UserSession session, DataCenter dataCenter)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];
        args[0] = session;
        args[1] = dataCenter;

        return infrastructureCommand.execute(session, ResourceLocator.DATACENTER_CREATE, args);
    }

    /**
     * Edits in the Data Base the information of the Data Center
     * 
     * @param session
     * @param dataCenter
     * @return
     */
    public BasicResult editDataCenter(UserSession session, DataCenter dataCenter)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = dataCenter;

        return infrastructureCommand.execute(session, ResourceLocator.DATACENTER_EDIT, args);
    }

    /**
     * Deletes from the Data Base the Data Center
     * 
     * @param session
     * @param dataCenter
     * @return
     */
    public BasicResult deleteDataCenter(UserSession session, DataCenter dataCenter)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = dataCenter;

        return infrastructureCommand.execute(session, ResourceLocator.DATACENTER_DELETE, args);
    }

    /* ______________________________ RACKS _______________________________ */
    /**
     * Creates a new rack in the data base
     * 
     * @return a DataResult, with the Rack created
     */
    public BasicResult createRack(UserSession session, Rack rack)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];
        args[0] = session;
        args[1] = rack;

        return infrastructureCommand.execute(session, ResourceLocator.RACK_CREATE, args);
    }

    /**
     * Deletes the rack from the data base
     * 
     * @param sessionKey
     * @param rack
     * @return
     */
    public BasicResult deleteRack(UserSession session, Rack rack)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = rack;

        return infrastructureCommand.execute(session, ResourceLocator.RACK_DELETE, args);
    }

    /**
     * Edits rack's information
     * 
     * @param sessionKey
     * @param rack
     * @return
     */
    public BasicResult editRack(UserSession session, Rack rack)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = rack;

        return infrastructureCommand.execute(session, ResourceLocator.RACK_EDIT, args);
    }

    /* ______________________________ PHYSICAL MACHINES _______________________________ */
    /**
     * Creates a new Physical Machine in the Data Base, and its hypervisors, if there is any
     * 
     * @param session The user's session that called this method
     * @param physicalMachineCreation A PhysicalMachineCreation object containing the
     *            PhysicalMachine that will be created, and an ArrayList of hypervisors assigned to
     *            this physical machine, that will be created too
     * @return a DataResult, with a PhysicalMachineCreation object containing the Physical Machine
     *         and Hypervisors created
     */
    public BasicResult createPhysicalMachine(UserSession session,
        PhysicalMachineCreation physicalMachineCreation)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];
        args[0] = session;
        args[1] = physicalMachineCreation;

        return infrastructureCommand.execute(session, ResourceLocator.PHYSICALMACHINE_CREATE, args);
    }

    /**
     * Deletes the physical machine from the data base
     * 
     * @param sessionKey
     * @param physicalMachine
     * @return
     */
    public BasicResult deletePhysicalMachine(UserSession session, PhysicalMachine physicalMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = physicalMachine;

        return infrastructureCommand.execute(session, ResourceLocator.PHYSICALMACHINE_DELETE, args);
    }

    /**
     * Edits the physical machines's information
     * 
     * @param sessionKey
     * @param physicalMachine
     * @return
     */
    public BasicResult editPhysicalMachine(UserSession session, PhysicalMachine physicalMachine)
    {

        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = physicalMachine;

        return infrastructureCommand.execute(session, ResourceLocator.PHYSICALMACHINE_EDIT, args);
    }

    /**
     * Moves a physical machine from one rack to another. The "assignedTo" attribute in the
     * physicalMachine parameter, contains the new Rack
     * 
     * @param session
     * @param physicalMachine
     * @return
     */
    public BasicResult movePhysicalMachine(UserSession session, PhysicalMachine physicalMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = physicalMachine;

        return infrastructureCommand.execute(session, ResourceLocator.PHYSICALMACHINE_MOVE, args);
    }

    /* ______________________________ HYPERVISORS _______________________________ */

    /**
     * Creates a new Hypervisor
     * 
     * @param userSession
     * @param hypervisor
     * @return A DataResult object containing the Hypervisor created
     */
    public BasicResult createHypervisor(UserSession userSession, HyperVisor hypervisor)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];

        args[0] = userSession;
        args[1] = hypervisor;

        return infrastructureCommand.execute(userSession, ResourceLocator.HYPERVISOR_CREATE, args);
    }

    /**
     * Edits an existing Hypervisor
     * 
     * @param session
     * @param hypervisor
     * @return a BasicResult object with success = true if the edition was successful
     */
    public BasicResult editHypervisor(UserSession userSession, HyperVisor hypervisor)
    {

        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];

        args[0] = userSession;
        args[1] = hypervisor;

        return infrastructureCommand.execute(userSession, ResourceLocator.HYPERVISOR_EDIT, args);
    }

    /**
     * Deletes the hypervisor from the data base
     * 
     * @param session
     * @param hypervisor
     * @return A BasicResult object with the result of the deletion
     */
    public BasicResult deleteHypervisor(UserSession session, HyperVisor hypervisor)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = hypervisor;

        return infrastructureCommand.execute(session, ResourceLocator.HYPERVISOR_DELETE, args);
    }

    /* ______________________________ VIRTUAL MACHINES _______________________________ */

    /**
     * Creates a new Virtual Machine in the Data Base
     * 
     * @param session UserSession object containing the UserSession that is calling this method
     * @param virtualMachine A VirtualMachine object containing the necessary information to create
     *            a new Virtual Machine. UUID and State fields will be ignored, since they will be
     *            generated.
     * @return a DataResult object containing a VirtualMachine object with the Virtual Machine
     *         created
     */
    public BasicResult createVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = virtualMachine;

        return infrastructureCommand.execute(session, ResourceLocator.VIRTUALMACHINE_CREATE, args);
    }

    /**
     * Deletes the virtual machine
     * 
     * @param sessionKey
     * @param virtualMachine
     * @return
     */
    public BasicResult deleteVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = virtualMachine;

        return infrastructureCommand.execute(session, ResourceLocator.VIRTUALMACHINE_DELETE, args);
    }

    /**
     * Edits virtual machine's information
     * 
     * @param sessionKey
     * @param virtualMachine
     * @return
     */
    public BasicResult editVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = virtualMachine;

        return infrastructureCommand.execute(session, ResourceLocator.VIRTUALMACHINE_EDIT, args);
    }

    /**
     * Moves a Virtual Machine from a Physical Machine to another. virtualMachine's "assignedTo"
     * attribute will contain the new HyperVisor, to which the virtual machine will be assigned
     * 
     * @param session
     * @param virtualMachine
     * @return
     */
    public BasicResult moveVirtualMachine(UserSession session, VirtualMachine virtualMachine)
    {
        InfrastructureCommand infrastructureCommand = new InfrastructureCommand();
        Object[] args = new Object[1];
        args[0] = virtualMachine;

        return infrastructureCommand.execute(session, ResourceLocator.VIRTUALMACHINE_MOVE, args);
    }
}
