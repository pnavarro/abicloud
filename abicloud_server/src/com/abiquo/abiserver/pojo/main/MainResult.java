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
package com.abiquo.abiserver.pojo.main;

import java.util.ArrayList;

import com.abiquo.abiserver.pojo.infrastructure.HyperVisorType;
import com.abiquo.abiserver.pojo.infrastructure.SO;
import com.abiquo.abiserver.pojo.user.Role;

/**
 * This class defines the objects that represents the common information for the whole application
 * 
 * @author Oliver
 */
public class MainResult
{
    private ArrayList<SO> operatingSystems;

    private ArrayList<Role> roles;

    private ArrayList<HyperVisorType> hypervisorTypes;

    public MainResult()
    {
        operatingSystems = new ArrayList<SO>();
        roles = new ArrayList<Role>();
    }

    public ArrayList<SO> getOperatingSystems()
    {
        return operatingSystems;
    }

    public void setOperatingSystems(ArrayList<SO> operatingSystems)
    {
        this.operatingSystems = operatingSystems;
    }

    public ArrayList<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles)
    {
        this.roles = roles;
    }

    public ArrayList<HyperVisorType> getHypervisorTypes()
    {
        return hypervisorTypes;
    }

    public void setHypervisorTypes(ArrayList<HyperVisorType> hypervisorTypes)
    {
        this.hypervisorTypes = hypervisorTypes;
    }
}
