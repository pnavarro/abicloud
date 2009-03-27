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

package net.undf.abicloud.business.managers
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.events.VirtualApplianceEvent;
	import net.undf.abicloud.vo.infrastructure.State;
	import net.undf.abicloud.vo.virtualappliance.Node;
	import net.undf.abicloud.vo.virtualappliance.VirtualAppliance;
	import net.undf.abicloud.vo.virtualappliance.VirtualDataCenter;
	
	/**
	 * Manager for Virtual Appliances
	 * Stores user's virtual appliances, as well another useful information for virtual appliances creation and management
	 **/
	public class VirtualApplianceManager extends EventDispatcher
	{
		/* ------------- Constants------------- */
		public static const VIRTUAL_APPLIANCES_UPDATED:String = "virtualAppliancesUpdated_VirtualApplianceManager";
		public static const VIRTUALDATACENTERS_UPDATED:String = "virtualDataCentersUpdated_VirtualApplianceManager";
		
		
		/* ------------- Constructor ------------- */
		public function VirtualApplianceManager()
		{
			this._virtualAppliances = new ArrayCollection();
			this._virtualDataCenters = new ArrayCollection();
		}
		
		
		/* ------------- Public methods ------------- */
		
		
		//////////////////////////////////////////////
		//Virtual Data Centers
		private var _virtualDataCenters:ArrayCollection;
		/**
		 * ArrayCollection containing the list of Virtual Datacenters
		 * that belongs to a given Enterprise
		 */
		[Bindable(event="virtualDataCentersUpdated_VirtualApplianceManager")]
		public function get virtualDataCenters():ArrayCollection
		{
			return this._virtualDataCenters;
		}
		
		public function set virtualDataCenters(value:ArrayCollection):void
		{
			this._virtualDataCenters = value;
			dispatchEvent(new Event(VIRTUALDATACENTERS_UPDATED));
		}
		
		/**
		 * Adds a new VirtualDataCenter to the VirtualDataCenter list 
		 * Usually, the VirtualDataCenter is first created in server, before calling this method,
		 * to ensure data consistency
		 * @param virtualDataCenter The VirtualDataCenter that will be added to the list
		 * 
		 */		
		public function addVirtualDataCenter(virtualDataCenter:VirtualDataCenter):void
		{
			this._virtualDataCenters.addItem(virtualDataCenter);
			dispatchEvent(new Event(VIRTUALDATACENTERS_UPDATED));
		}
		
		/**
		 * Removes a VirtualDataCenter from the virtualDataCenters list.
		 * Usually, the VirtualDataCenter is first removed from the server, before calling this method 
		 * @param virtualDataCenter The VirtualDataCenter that will be removed from the list
		 * 
		 */		
		public function deleteVirtualDataCenter(virtualDataCenter:VirtualDataCenter):void
		{
			var index:int = this._virtualDataCenters.getItemIndex(virtualDataCenter);
			if(index > -1)
			{
				this._virtualDataCenters.removeItemAt(index);
				dispatchEvent(new Event(VIRTUALDATACENTERS_UPDATED));
			}
		}
		
		/**
		 * Updated a VirtualDataCenter from the model with new values 
		 * @param oldVirtualDataCenter The VirtualDataCenter that will be updated
		 * @param newVirtualDataCenter A VirtualDataCenter object containing the new values
		 * 
		 */		
		public function editVirtualDataCenter(oldVirtualDataCenter:VirtualDataCenter, newVirtualDataCenter:VirtualDataCenter):void
		{
			//Updating the virtual data center without modifying its memory address
			oldVirtualDataCenter.id = newVirtualDataCenter.id;
			oldVirtualDataCenter.name = newVirtualDataCenter.name;
			
			//Announcing that this virtual data center has been updated
			var event:VirtualApplianceEvent = new VirtualApplianceEvent(VirtualApplianceEvent.VIRTUAL_DATACENTER_EDITED);
			event.virtualDataCenter = oldVirtualDataCenter;
			dispatchEvent(event);	
		}
		
		//////////////////////////////////////////////
		//Virtual Appliances
		
		
		private var _virtualAppliances:ArrayCollection;
		/**
		 * ArrayCollection containing a list of Virtual Appliances
		 * This list may contain all virtual appliances that belongs to an Enterprise,
		 * or only Virtual Appliances that belong to a given VirtualDataCenter 
		 **/
		[Bindable(event="virtualAppliancesChange")]
		public function get virtualAppliances():ArrayCollection
		{
			return this._virtualAppliances;
		}
		
		public function set virtualAppliances(array:ArrayCollection):void
		{
			this._virtualAppliances = array;
			dispatchEvent(new Event("virtualAppliancesChange"));
		}
		
		
		/**
		 * Sets the node list of a virtual appliance 
		 * @param virtualAppliance The VirtualAppliance, that exists in virtualAppliance list, to set its list of nodes
		 * @param nodes ArrayCollection containing the nodes for the virtualAppliance
		 * 
		 */		
		public function setVirtualApplianceNodes(virtualAppliance:VirtualAppliance, nodes:ArrayCollection):void
		{
			virtualAppliance.nodes = nodes;
			
			//Announcing that this VirtualAppliance has ready its list of nodes
			var virtualApplianceEvent:VirtualApplianceEvent = new VirtualApplianceEvent(VirtualApplianceEvent.VIRTUAL_APPLIANCE_NODES_RETRIEVED, false);
			virtualApplianceEvent.virtualAppliance = virtualAppliance;
			dispatchEvent(virtualApplianceEvent);
		}
		
		/**
		 * Adds a new virtual appliance to the list of virtual appliances
		 **/
		public function addVirtualAppliance(newVirtualAppliance:VirtualAppliance):void
		{
			if(!this._virtualAppliances.contains(newVirtualAppliance))
			{
				this._virtualAppliances.addItem(newVirtualAppliance);
				dispatchEvent(new Event(VIRTUAL_APPLIANCES_UPDATED));
				
				var virtualApplianceEvent:VirtualApplianceEvent = new VirtualApplianceEvent(VirtualApplianceEvent.VIRTUAL_APPLIANCE_CREATED, false);
				virtualApplianceEvent.virtualAppliance = newVirtualAppliance;
				dispatchEvent(virtualApplianceEvent);
			}
		}
		
		
		/**
		 * Deletes the given virtual appliance from the virtual appliances list
		 **/
		public function deleteVirtualAppliance(virtualAppliance:VirtualAppliance):void
		{
			var index:int = this._virtualAppliances.getItemIndex(virtualAppliance);
			if(index >= 0)
			{
				this._virtualAppliances.removeItemAt(index);
				dispatchEvent(new Event(VIRTUAL_APPLIANCES_UPDATED));
			}
		}
		
		
		/**
		 * Refreshes the list of virtual appliances when one has been edited
		 * For the Virtual Appliance that has been edited, refreshes its Nodes list
		 **/
		public function editVirtualAppliance(oldVirtualAppliance:VirtualAppliance, newVirtualAppliance:VirtualAppliance):void
		{
			//Since this can be called from a non blocking service, the instance of oldVirtualAppliance may has changed
		 	//We get the current instance from the model
		 	var length:int = this._virtualAppliances.length;
		 	var i:int;
		 	for(i = 0; i < length; i++)
		 	{
		 		if(oldVirtualAppliance.id == VirtualAppliance(this._virtualAppliances.getItemAt(i)).id)
		 		{
		 			oldVirtualAppliance = this._virtualAppliances.getItemAt(i) as VirtualAppliance;
		 			break;
		 		}
		 	}
			
			//Updating the virtualAppliance with the new values, without modifying its memory address
			oldVirtualAppliance.id = newVirtualAppliance.id;
			oldVirtualAppliance.enterprise = newVirtualAppliance.enterprise;
			oldVirtualAppliance.error = newVirtualAppliance.error;
			oldVirtualAppliance.highDisponibility = newVirtualAppliance.highDisponibility;
			oldVirtualAppliance.isPublic = newVirtualAppliance.isPublic;
			oldVirtualAppliance.logs = newVirtualAppliance.logs;
			oldVirtualAppliance.name = newVirtualAppliance.name;
			oldVirtualAppliance.nodeConnections = newVirtualAppliance.nodeConnections;
			oldVirtualAppliance.state = newVirtualAppliance.state;
			oldVirtualAppliance.virtualDataCenter = newVirtualAppliance.virtualDataCenter;
			
			if(newVirtualAppliance.nodes != null)
			{
				//Setting all nodes to not modified
				length =  newVirtualAppliance.nodes.length;
				for(i = 0; i < length; i++)
				{
					Node(newVirtualAppliance.nodes.getItemAt(i)).modified = Node.NODE_NOT_MODIFIED;
				}
				
				//Updating the Virtual Appliance's list of nodes
				oldVirtualAppliance.nodes = newVirtualAppliance.nodes;
			}
			
			
			//Announcing that this virtual appliance has been updated
		 	var virtualApplianceEvent:VirtualApplianceEvent = new VirtualApplianceEvent(VirtualApplianceEvent.VIRTUAL_APPLIANCE_EDITED);
		 	virtualApplianceEvent.virtualAppliance = oldVirtualAppliance;
		 	dispatchEvent(virtualApplianceEvent);
		}
		
		/**
		 * Changes the state of a Virtual Appliance, to a new State
		 * @param oldVirtualAppliance The Virtual Appliance that was sent to the server to change the state
		 * @param newVirtualAppliance The new virtual appliance that the server returned, with the state changes and, maybe, more changes
		 */
		 public function changeVirtualApplianceState(oldVirtualAppliance:VirtualAppliance, newVirtualAppliance:VirtualAppliance):void
		 {
		 	//Since this can be called from a non blocking service, the instance of oldVirtualAppliance may has changed
		 	//We get the current instance from the model
		 	var length:int = this._virtualAppliances.length;
		 	var i:int;
		 	for(i = 0; i < length; i++)
		 	{
		 		if(oldVirtualAppliance.id == VirtualAppliance(this._virtualAppliances.getItemAt(i)).id)
		 		{
		 			oldVirtualAppliance = this._virtualAppliances.getItemAt(i) as VirtualAppliance;
		 			break;
		 		}
		 	}
		 	
		 	oldVirtualAppliance.nodes = newVirtualAppliance.nodes;
		 	oldVirtualAppliance.state = newVirtualAppliance.state;
		 	oldVirtualAppliance.error = newVirtualAppliance.error;
		 	
		 	//Announcing that this virtual appliance has been updated
		 	var virtualApplianceEvent:VirtualApplianceEvent = new VirtualApplianceEvent(VirtualApplianceEvent.VIRTUAL_APPLIANCE_EDITED);
		 	virtualApplianceEvent.virtualAppliance = oldVirtualAppliance;
		 	dispatchEvent(virtualApplianceEvent);
		 }
		 
		 /**
		 * Changes a Virtual Appliances state to State.IN_PROGRESS
		 */
		 public function setVirtualApplianceInProgress(virtualAppliance:VirtualAppliance):void
		 {
		 	virtualAppliance.state = new State(State.IN_PROGRESS);
		 }
		 
		 /**
		 * Updates Log list of a Virtual Appliance
		 */
		 public function setVirtualApplianceUpdatedLogs(virtualAppliance:VirtualAppliance, logs:ArrayCollection):void
		 {
		 	virtualAppliance.logs = logs;
		 }
	
		 /**
		  * Updates the whole list of model's Virtual Appliances
		  * @param virtualMachinesToCheck
		  * @param virtualMachinesWithNewState
		  * 
		  */		 
		 public function checkVirtualAppliances(virtualAppliancesChecked:ArrayCollection):void
		 {
		 	this._virtualAppliances = virtualAppliancesChecked;
		 	
		 	//Announcing that Virtual Appliances list has been updated
		 	dispatchEvent(new Event(VIRTUAL_APPLIANCES_UPDATED));
		 }
		 
		 /**
		 * Updates a given Virtual Appliance with new values just retrieved from the server
		 */
		 public function checkVirtualAppliance(virtualApplianceToCheck:VirtualAppliance, virtualApplianceChecked:VirtualAppliance):void
		 {
		 	//Proceed to update the virtual appliance with the most updated values
 			virtualApplianceToCheck.id = virtualApplianceChecked.id;
			virtualApplianceToCheck.enterprise = virtualApplianceChecked.enterprise;
			virtualApplianceToCheck.error = virtualApplianceChecked.error;
			virtualApplianceToCheck.highDisponibility = virtualApplianceChecked.highDisponibility;
			virtualApplianceToCheck.isPublic = virtualApplianceChecked.isPublic;
			virtualApplianceToCheck.logs = virtualApplianceChecked.logs;
			virtualApplianceToCheck.name = virtualApplianceChecked.name;
			virtualApplianceToCheck.nodeConnections = virtualApplianceChecked.nodeConnections;
			virtualApplianceToCheck.state = virtualApplianceChecked.state;
			virtualApplianceToCheck.virtualDataCenter = virtualApplianceChecked.virtualDataCenter;
			virtualApplianceToCheck.nodes = virtualApplianceChecked.nodes;
 			
 			//Announcing that this VirtualAppliance has been checked
 			var virtualApplianceEvent:VirtualApplianceEvent = new VirtualApplianceEvent(VirtualApplianceEvent.VIRTUAL_APPLIANCE_CHECKED);
 			virtualApplianceEvent.virtualAppliance = virtualApplianceToCheck;
 			dispatchEvent(virtualApplianceEvent);
		 }
	}
}