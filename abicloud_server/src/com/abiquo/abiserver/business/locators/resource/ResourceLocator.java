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
package com.abiquo.abiserver.business.locators.resource;

public class ResourceLocator
{

    // Static associations {'resourceName','methodName'}

    // MAIN RESOURCES
    static public String[] GET_COMMON_INFORMATION_RESOURCE =
        {"GET_COMMON_INFORMATION", "getCommonInformation"};

    // SESSION RESOURCES
    static public String[] LOGIN_RESOURCE = {"LOGIN", "login"};

    static public String[] LOGOUT_RESOURCE = {"LOGOUT", "logout"};

    // INFRAESTRUCTURE RESOURCES
    static public String[] DATACENTER_GETINFRASTRUCTURE =
        {"DATACENTER_GETINFRASTRUCTURE", "getInfrastructureByDataCenter"};

    static public String[] DATACENTER_GETDATACENTERS =
        {"DATACENTER_GETDATACENTERS", "getDataCenters"};

    static public String[] DATACENTER_CREATE = {"DATACENTER_CREATE", "createDataCenter"};

    static public String[] DATACENTER_EDIT = {"DATACENTER_CREATE", "editDataCenter"};

    static public String[] DATACENTER_DELETE = {"DATACENTER_DELETE", "deleteDataCenter"};

    static public String[] RACK_CREATE = {"RACK_CREATE", "createRack"};

    static public String[] RACK_DELETE = {"RACK_DELETE", "deleteRack"};

    static public String[] RACK_EDIT = {"RACK_EDIT", "editRack"};

    static public String[] PHYSICALMACHINE_CREATE =
        {"PHYSICALMACHINE_CREATE", "createPhysicalMachine"};

    static public String[] PHYSICALMACHINE_DELETE =
        {"PHYSICALMACHINE_DELETE", "deletePhysicalMachine"};

    static public String[] PHYSICALMACHINE_EDIT = {"PHYSICALMACHINE_EDIT", "editPhysicalMachine"};

    static public String[] PHYSICALMACHINE_MOVE = {"PHYSICALMACHINE_MOVE", "movePhysicalMachine"};

    static public String[] HYPERVISOR_CREATE = {"HYPERVISOR_CREATE", "createHypervisor"};

    static public String[] HYPERVISOR_EDIT = {"HYPERVISOR_EDIT", "editHypervisor"};

    static public String[] HYPERVISOR_DELETE = {"HYPERVISOR_DELETE", "deleteHypervisor"};

    static public String[] VIRTUALMACHINE_CREATE =
        {"VIRTUALMACHINE_CREATE", "createVirtualMachine"};

    static public String[] VIRTUALMACHINE_DELETE =
        {"VIRTUALMACHINE_DELETE", "deleteVirtualMachine"};

    static public String[] VIRTUALMACHINE_EDIT = {"VIRTUALMACHINE_EDIT", "editVirtualMachine"};

    static public String[] VIRTUALMACHINE_SET = {"VIRTUALMACHINE_SET", "setVirtualMachine"}; // <-
                                                                                             // DEPRECATED

    static public String[] VIRTUALMACHINE_MOVE = {"VIRTUALMACHINE_MOVE", "moveVirtualMachine"};

    static public String[] VIRTUALMACHINE_START = {"VIRTUALMACHINE_START", "startVirtualMachine"};

    static public String[] VIRTUALMACHINE_PAUSE = {"VIRTUALMACHINE_PAUSE", "pauseVirtualMachine"};

    static public String[] VIRTUALMACHINE_REBOOT =
        {"VIRTUALMACHINE_REBOOT", "rebootVirtualMachine"};

    static public String[] VIRTUALMACHINE_SHUTDOWN =
        {"VIRTUALMACHINE_SHUTDOWN", "shutdownVirtualMachine"};

    static public String[] CHECK_VIRTUAL_MACHINES_STATE =
        {"CHECK_VIRTUAL_MACHINES_STATE", "checkVirtualMachinesState"};

    // VIRTUAL APPLIANCE RESOURCES
    static public String[] VIRTUALAPPLIANCE_GETBYUSER =
        {"VIRTUALAPPLIANCE_GETBYUSER", "getVirtualAppliancesByUser"};

    static public String[] VIRTUALAPPLIANCE_GETBYENTERPRISE =
        {"VIRTUALAPPLIANCE_GETBYENTERPRISE", "getVirtualAppliancesByEnterprise"};

    static public String[] VIRTUALAPPLIANCE_GETNODES =
        {"VIRTUALAPPLIANCE_GETNODES", "getVirtualApplianceNodes"};

    static public String[] VIRTUALAPPLIANCE_CREATE =
        {"VIRTUALAPPLIANCE_CREATE", "createVirtualAppliance"};

    static public String[] VIRTUALAPPLIANCE_EDIT =
        {"VIRTUALAPPLIANCE_EDIT", "editVirtualAppliance"};

    static public String[] VIRTUALAPPLIANCE_DELETE =
        {"VIRTUALAPPLIANCE_DELETE", "deleteVirtualAppliance"};

    static public String[] VIRTUALAPPLIANCE_START =
        {"VIRTUALAPPLIANCE_START", "startVirtualAppliance"};

    static public String[] VIRTUALAPPLIANCE_SHUTDOWN =
        {"VIRTUALAPPLIANCE_SHUTDOWN", "shutdownVirtualAppliance"};

    static public String[] VIRTUALAPPLIANCE_GET_UPDATED_LOGS =
        {"VIRTUALAPPLIANCE_GET_UPDATED_LOGS", "getVirtualApplianceUpdatedLogs"};

    static public String[] CHECK_VIRTUAL_APPLIANCE =
        {"CHECK_VIRTUAL_APPLIANCE", "checkVirtualAppliance"};

    static public String[] VIRTUALDATACENTER_GETBYENTERPRISE =
        {"VIRTUALDATACENTER_GETBYENTERPRISE", "getVirtualDataCentersByEnterprise"};

    static public String[] VIRTUALDATACENTER_CREATE =
        {"VIRTUALDATACENTER_CREATE", "createVirtualDataCenter"};

    static public String[] VIRTUALDATACENTER_EDIT =
        {"VIRTUALDATACENTER_EDIT", "editVirtualDataCenter"};

    static public String[] VIRTUALDATACENTER_DELETE =
        {"VIRTUALDATACENTER_DELETE", "deleteVirtualDataCenter"};

    // VIRTUAL IMAGE RESOURCES
    static public String[] VIRTUALIMAGEINFORMATION_GET =
        {"VIRTUALIMAGEINFORMATION_GET", "getVirtualImagesInformation"};

    static public String[] VIRTUALIMAGE_GETBYUSER =
        {"VIRTUALIMAGE_GETBYUSER", "getVirtualImagesByUser"};

    static public String[] VIRTUALIMAGE_CREATE = {"VIRTUALIMAGE_CREATE", "createVirtualImage"};

    static public String[] VIRTUALIMAGE_EDIT = {"VIRTUALIMAGE_EDIT", "editVirtualImage"};

    static public String[] VIRTUALIMAGE_DELETE = {"VIRTUALIMAGE_DELETE", "deleteVirtualImage"};

    static public String[] CATEGORY_CREATE = {"CATEGORY_CREATE", "createCategory"};

    static public String[] CATEGORY_DELETE = {"CATEGORY_DELETE", "deleteCategory"};

    static public String[] REPOSITORY_EDIT = {"REPOSITORY_EDIT", "editRepository"};

    static public String[] ICON_CREATE = {"ICON_CREATE", "createIcon"};

    static public String[] ICON_DELETE = {"ICON_DELETE", "deleteIcon"};

    static public String[] ICON_EDIT = {"ICON_EDIT", "editIcon"};

    // USER RESOURCES
    static public String[] USER_GETUSERS = {"USER_GETUSERS", "getUsers"};

    static public String[] USER_GET_ALL_USERS = {"USER_GET_ALL_USERS", "getUsers"};

    static public String[] USER_CREATE = {"USER_CREATE", "createUser"};

    static public String[] USER_EDIT = {"USER_EDIT", "editUser"};

    static public String[] USER_DELETE = {"USER_DELETE", "deleteUser"};

    static public String[] USERS_CLOSE_SESSION = {"USERS_CLOSE_SESSION", "closeSessionUsers"};

    static public String[] ENTERPRISE_GET_ENTERPRISES =
        {"ENTERPRISE_GET_ENTERPRISES", "getEnterprises"};

    static public String[] ENTERPRISE_GET_ALL_ENTERPRISES =
        {"ENTERPRISE_GET_ALL_ENTERPRISES", "getEnterprises"};

    static public String[] ENTERPRISE_CREATE = {"ENTERPRISE_CREATE", "createEnterprise"};

    static public String[] ENTERPRISE_EDIT = {"ENTERPRISE_EDIT", "editEnterprise"};

    static public String[] ENTERPRISE_DELETE = {"ENTERPRISE_DELETE", "deleteEnterprise"};
}
