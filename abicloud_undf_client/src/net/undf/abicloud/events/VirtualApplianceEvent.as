/*
* The contents of this file are subject to the Common Public Attribution License

* Version 1.0 (the "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at http://cpal.abiquo.com.



* The License is based on the Mozilla Public License Version 1.1 but Sections 14
* and 15 have been added to cover use of software over a computer network and
* provide for limited attribution for the Original Developer. In addition,



* Exhibit A has been modified to be consistent with Exhibit B.
*
* Software distributed under the License is distributed on an "AS IS" basis,
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License



* for the specific language governing rights and limitations under the License.
*
* The Original Code is abiquo,  14 Jul 2008. The Original Developer is the
* Initial Developer. The Initial Developer of the Original Code is Soluciones Grid,



* S.L.. All portions of the code are Copyright © Soluciones Grid, S.L.
* All Rights Reserved.
*/

package net.undf.abicloud.events
{
	import flash.events.Event;
	
	import net.undf.abicloud.vo.user.Enterprise;
	import net.undf.abicloud.vo.virtualappliance.VirtualAppliance;
	import net.undf.abicloud.vo.virtualappliance.VirtualDataCenter;
	
	public class VirtualApplianceEvent extends Event
	{
		/* ------------- Constants------------- */
		public static const GET_VIRTUALAPPLIANCES_BY_ENTERPRISE:String = "getVirtualAppliancesByEnterpriseVirtualApplianceEvent";
		public static const GET_VIRTUALAPPLIANCE_NODES:String = "getVirtualApplianceNodes_VirtualApplianceEvent";
		public static const CREATE_VIRTUALAPPLIANCE:String = "createVirtualApplianceVirtualApplianceEvent";
		public static const EDIT_VIRTUALAPPLIANCE:String = "editVirtualApplianceVirtualApplianceEvent";
		public static const EDIT_VIRTUALAPPLIANCE_NOT_BLOCKING:String = "editVirtualApplianceNotBlockingVirtualApplianceEvent";
		public static const DELETE_VIRTUALAPPLIANCE:String = "deleteVirtualApplianceVirtualApplianceEvent";
		public static const DELETE_VIRTUALAPPLIANCE_NON_BLOCKING:String = "deleteVirtualApplianceNonBlockingVirtualApplianceEvent";
		public static const START_VIRTUALAPPLIANCE:String = "startVirtualApplianceVirtualApplianceEvent";
		public static const SHUTDOWN_VIRTUALAPPLIANCE:String = "shutdownVirtualApplianceVirtualApplianceEvent";
		public static const GET_VIRTUAL_APPLIANCE_UPDATED_LOGS:String = "getVirtualApplianceUpdatedLogsVirtualApplianceEvent"
		
		public static const CHECK_VIRTUAL_APPLIANCES_BY_ENTERPRISE:String = "checkVirtualAppliancesByEnterpriseVirtualApplianceEvent";
		public static const CHECK_VIRTUAL_APPLIANCE:String = "checkVirtualApplianceVirtualApplianceEvent"
		
		public static const VIRTUAL_APPLIANCE_NODES_RETRIEVED:String = "virtualApplianceNodesRetrievedVirtualApplianceEvent";
		public static const VIRTUAL_APPLIANCE_CREATED:String = "virtualApplianceCreatedVirtualApplianceEvent"
		public static const VIRTUAL_APPLIANCE_EDITED:String = "virtualApplianceEditedVirtualApplianceEvent";
		public static const VIRTUAL_APPLIANCE_SELECTED:String = "virtualApplianceSelectedVirtualApplianceEvent";
		public static const VIRTUAL_APPLIANCE_CHECKED:String = "virtualApplianceCheckedVirtualApplianceEvent";
		
		
		public static const GET_VIRTUAL_DATACENTERS_BY_ENTERPRISE:String = "getVirtualDataCentersVirtualApplianceEvent";
		public static const CREATE_VIRTUAL_DATACENTER:String = "createVirtualDataCenterVirtualApplianceEvent";
		public static const DELETE_VIRTUAL_DATACENTER:String = "deleteVirtualDataCenterVirtualApplianceEvent";
		public static const EDIT_VIRTUAL_DATACENTER:String = "editVirtualDataCenterVirtualApplianceEvent";
		
		public static const VIRTUAL_DATACENTER_EDITED:String = "virtualDataCenterEditedVirtualApplianceEvent";
		
		/* ------------- Public atributes ------------- */
		public var virtualAppliance:VirtualAppliance;
		public var oldVirtualAppliance:VirtualAppliance;
		
		public var enterprise:Enterprise;
		
		public var virtualDataCenter:VirtualDataCenter;
		public var oldVirtualDataCenter:VirtualDataCenter;
		
		
		/* ------------- Constructor ------------- */
		public function VirtualApplianceEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}

	}
}