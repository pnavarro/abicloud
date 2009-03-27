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
	
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.vo.infrastructure.DataCenter;
	import net.undf.abicloud.vo.infrastructure.InfrastructureElement;
	import net.undf.abicloud.vo.infrastructure.PhysicalMachineCreation;
	
	public class InfrastructureEvent extends Event
	{
		
		/* ------------- Constants------------- */
		public static const GET_DATACENTERS:String = "getDataCentersInfrastructureEvent";
		public static const CREATE_DATACENTER:String = "createDataCenterInfrastructureEvent";
		public static const EDIT_DATACENTER:String = "editDataCenterInfrastructureEvent";
		public static const DELETE_DATACENTER:String = "deleteDataCenterInfrastructureEvent";
		public static const GET_INFRASTRUCTURE_BY_DATACENTER:String = "getInfrastructureByDataCenterInfrastructureEvent";
		
		/* ------------------------------------ */
		
		//This event is dispatched when a data center has been edited
		public static const DATACENTER_EDITED:String = "dataCenterEditedInfrastructureEvent";
		
		//this event is dispatched when an infrastructure element has been edited (except an Hypervisor)
		public static const INFRASTRUCTURE_ELEMENT_EDITED:String = "infrastructureElementEditedInfrastructureEvent";
		
		/* ------------------------------------ */
		
		public static const CREATE_RACK:String = "createClusterInfrastructureEvent";
		public static const DELETE_RACK:String = "deleteClusterInfrastructureEvent";
		public static const EDIT_RACK:String = "saveRackInfrastructureEvent";
		
		/* ------------------------------------ */
		
		public static const CREATE_PHYSICALMACHINE:String = "createPhysicalMachineInfrastructureEvent";
		public static const DELETE_PHYSICALMACHINE:String = "deletePhysicalMachineInfrastructureEvent";
		public static const EDIT_PHYSICALMACHINE:String = "editPhysicalMachineInfrastructureEvent";
		
		/* ------------------------------------ */
		
		public static const CREATE_HYPERVISOR:String = "createHypervisorInfrastructureEvent";
		public static const EDIT_HYPERVISOR:String = "editHypervisorInfrastructureEvent";
		public static const DELETE_HYPERVISOR:String = "deleteHypervisorInfrastructureEvent";
		
		/* ------------------------------------ */
		
		public static const CREATE_VIRTUALMACHINE:String = "createVirtualMachineInfrastructureElementEvent";
		public static const EDIT_VIRTUALMACHINE:String = "editVirtualMachineInfrastructureElementEvent";
		
		public static const START_VIRTUALMACHINE:String = "startVirtualMachineInfrastructureElementEvent";
		public static const PAUSE_VIRTUALMACHINE:String = "pauseVirtualMachineInfrastructureElementEvent";
		public static const REBOOT_VIRTUALMACHINE:String = "rebootVirtualMachineInfrastructureElementEvent";
		public static const SHUTDOWN_VIRTUALMACHINE:String = "shutdownVirtualMachineInfrastructureElementEvent";
		
		public static const CHECK_VIRTUALMACHINES_STATE:String = "checkVirtualMachinesStateInfrastructureElementEvent";
		
		/* ------------- Public atributes ------------- */
		public var infrastructureElement:InfrastructureElement;
		public var dataCenter:DataCenter;
		
		public var oldInfrastructureElement:InfrastructureElement;
		public var oldDataCenter:DataCenter;
		
		public var infrastructureElementArray:ArrayCollection;
		
		public var physicalMachineCreation:PhysicalMachineCreation;
		
		/* ------------- Constructor ------------- */
		public function InfrastructureEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}
		
		
	}
}