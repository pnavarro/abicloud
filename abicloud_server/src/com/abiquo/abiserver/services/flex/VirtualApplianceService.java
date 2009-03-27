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
import com.abiquo.abiserver.commands.VirtualApplianceCommand;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.user.Enterprise;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualAppliance;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualDataCenter;

/**
 * This class defines all services related to Virtual Appliances management
 * 
 * @author Oliver
 */

public class VirtualApplianceService
{

    // /////////////////////////
    // VirtualDataCenter

    /**
     * Retrieves a list of VirtualDataCenter that belongs to the same Enterprise
     * 
     * @param userSession The UserSession with the user that called this method
     * @param enterprise The Enterprise of which the VirtualDataCenter will be returned
     * @return a BasicResult object, containing an ArrayList<VirtualDataCenter>, with the
     *         VirtualDataCenter assigned to the enterprise
     */
    public BasicResult getVirtualDataCentersByEnterprise(UserSession userSession,
        Enterprise enterprise)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = enterprise;

        return virtualApplianceCommand.execute(userSession,
            ResourceLocator.VIRTUALDATACENTER_GETBYENTERPRISE, args);
    }

    /**
     * Creates a new VirtualDataCenter in the Data Base
     * 
     * @param userSession The UserSession with the user that called this method
     * @param virtualDataCenter The VirtualDataCenter that will be created in Data Base
     * @return a DataResult object containing the VirtualDataCenter that has been created
     */
    public BasicResult createVirtualDataCenter(UserSession userSession,
        VirtualDataCenter virtualDataCenter)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = virtualDataCenter;

        return virtualApplianceCommand.execute(userSession,
            ResourceLocator.VIRTUALDATACENTER_CREATE, args);
    }

    /**
     * Updates an existing VirtualDataCenter with new information
     * 
     * @param userSession The UserSession with the user that called this method
     * @param virtualDataCenter The VirtualDataCenter that will be updated
     * @return a BasicResult object, with the success of the edition
     */
    public BasicResult editVirtualDataCenter(UserSession userSession,
        VirtualDataCenter virtualDataCenter)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = virtualDataCenter;

        return virtualApplianceCommand.execute(userSession, ResourceLocator.VIRTUALDATACENTER_EDIT,
            args);
    }

    /**
     * Deletes a VirtualDataCenter from the DataBase. A VirtualDataCenter can only be deleted if any
     * of its Virtual Appliances are powered on
     * 
     * @param userSession The UserSession with the user that called this method
     * @param virtualDataCenter The VirtualDataCenter to be deleted
     * @return A BasicResult object with the success of the deletion. BasicResult.success = false
     *         will be returned if the VirtualDataCenter has any assigned VirtualAppliance powered
     *         on
     */
    public BasicResult deleteVirtualDataCenter(UserSession userSession,
        VirtualDataCenter virtualDataCenter)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = virtualDataCenter;

        return virtualApplianceCommand.execute(userSession,
            ResourceLocator.VIRTUALDATACENTER_DELETE, args);
    }

    // /////////////////////////
    // VirtualAppliance

    /**
     * Retrieves a list of Virtual Appliances that belong to the same Enterprise The
     * VirtualAppliance retrieved will not contain their Node list, for performance purposes It will
     * also return those Virtual Appliance marked as public
     * 
     * @param userSession The UserSession object with the user that called this method
     * @param enterprise The Enterprise to retrieve the VirtualAppliance list
     * @return a DataResult<ArrayList<VirtualAppliance>> object with the VirtualAppliance that
     *         belong to the given enterprise
     * @see getVirtualApplianceNodes
     */
    public BasicResult getVirtualAppliancesByEnterprise(UserSession userSession,
        Enterprise enterprise)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = enterprise;

        return virtualApplianceCommand.execute(userSession,
            ResourceLocator.VIRTUALAPPLIANCE_GETBYENTERPRISE, args);
    }

    /**
     * Given a VirtualAppliance, retrieves its node list
     * 
     * @param userSession The UserSession object with the user that called this method
     * @param virtualAppliance The VirtualAppliance to retrieve the nodes
     * @return a DataResult<ArrayList<Node>> object, containing the virtualAppliance's Nodes
     */
    public BasicResult getVirtualApplianceNodes(UserSession userSession,
        VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = virtualAppliance;

        return virtualApplianceCommand.execute(userSession,
            ResourceLocator.VIRTUALAPPLIANCE_GETNODES, args);
    }

    /**
     * Returns the Virtual Appliances that belongs to the user who called this method, or those that
     * are public (VirtualAppliance.isPublic)
     * 
     * @deprecated
     * @param session
     * @return A DataResult object, containing an ArraList of VirtualAppliance
     */
    public BasicResult getVirtualAppliancesByUser(UserSession session)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = session;

        return virtualApplianceCommand.execute(session, ResourceLocator.VIRTUALAPPLIANCE_GETBYUSER,
            args);
    }

    /**
     * Creates a new Virtual Appliance, that belongs to the user who called this method
     * 
     * @param session
     * @param virtualAppliance
     * @return A DataResult object containing the VirtualAppliance created in the Data Base
     */
    public BasicResult createVirtualAppliance(UserSession session, VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[2];
        args[0] = session;
        args[1] = virtualAppliance;

        return virtualApplianceCommand.execute(session, ResourceLocator.VIRTUALAPPLIANCE_CREATE,
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
     * Returns the a list with all Logs entries for a Virtual Appliance Useful to frequently update
     * the logs for a VirtualAppliance, without having to return the entire Virtual Appliance
     * 
     * @param session
     * @param virtualAppliance The VirtualAppliance which we want to return the list of logs
     * @return A DataResult object, containing an ArrayList<Log> with the list of logs for the
     *         virtualAppliance
     */
    public BasicResult getVirtualApplianceUpdatedLogs(UserSession session,
        VirtualAppliance virtualAppliance)
    {
        VirtualApplianceCommand virtualApplianceCommand = new VirtualApplianceCommand();
        Object[] args = new Object[1];
        args[0] = virtualAppliance;

        return virtualApplianceCommand.execute(session,
            ResourceLocator.VIRTUALAPPLIANCE_GET_UPDATED_LOGS, args);
    }
}
