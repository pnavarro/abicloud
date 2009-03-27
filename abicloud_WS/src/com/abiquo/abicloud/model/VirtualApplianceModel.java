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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.abiquo.abicloud.exception.VirtualMachineException;

/**
 * The Class VirtualApplianceModel represents a mini model to maintain a list of virtual appliances
 * .
 */
public class VirtualApplianceModel extends Observable
{

    /** The singleton instance for this class. */
    private static VirtualApplianceModel singleton;

    /** The virtual appliances. */
    private static Map<String, VirtualAppliance> virtualAppliances =
        new HashMap<String, VirtualAppliance>();

    /**
     * Instantiates a new virtual appliance model.
     */
    private VirtualApplianceModel()
    {
        super();
    }

    /**
     * Singleton accessor.
     * 
     * @return the model
     */
    public static VirtualApplianceModel getModel()
    {
        if (singleton == null)
        {
            singleton = new VirtualApplianceModel();
        }

        return singleton;
    }

    /**
     * Creates the virtual appliance.
     * 
     * @param virtualApplianceId the virtual appliance id
     * @return the virtual appliance
     */
    public VirtualAppliance createVirtualAppliance(String virtualApplianceId)
    {
        VirtualAppliance virtualAppliance = new VirtualAppliance();
        virtualAppliance.setVirtualApplianceId(virtualApplianceId);
        virtualAppliances.put(virtualApplianceId, virtualAppliance);
        return virtualAppliance;
    }

    /**
     * Gets the virtual appliance.
     * 
     * @param virtualApplianceId the virtual appliance id
     * @return the virtual appliance
     */
    public VirtualAppliance getVirtualAppliance(String virtualApplianceId)
    {
        return virtualAppliances.get(virtualApplianceId);
    }

    /**
     * Delete virtual appliance.
     * 
     * @param id the id
     * @throws Exception
     */
    public void deleteVirtualAppliance(String id) throws Exception
    {
        // Deleting all the machines
        VirtualAppliance virtualAppliance = virtualAppliances.get(id);
        for (AbsVirtualMachine machine : virtualAppliance.getMachines())
        {
            VirtualSystemModel.getModel().deleteMachine(machine.getConfiguration().getMachineId());
        }
        // Getting out the virtualAppliance from the virtualAppliance map
        virtualAppliances.remove(virtualAppliance);

    }

    /**
     * Gets the virtual appliances.
     * 
     * @return the virtual appliances
     */
    public Collection<VirtualAppliance> getVirtualAppliances()
    {
        return virtualAppliances.values();
    }
}
