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
	
	import net.undf.abicloud.events.InfrastructureEvent;
	import net.undf.abicloud.vo.infrastructure.DataCenter;
	import net.undf.abicloud.vo.infrastructure.HyperVisor;
	import net.undf.abicloud.vo.infrastructure.InfrastructureElement;
	import net.undf.abicloud.vo.infrastructure.PhysicalMachine;
	import net.undf.abicloud.vo.infrastructure.Rack;
	import net.undf.abicloud.vo.infrastructure.State;
	import net.undf.abicloud.vo.infrastructure.VirtualMachine;
	
	/**
	 * Data manager for the Infrastructure Managment, a component from AbiCloud application.
	 * It can only be created and accessed through AbiCloudManager
	 * 
	 * This class allows to view and manipulate the user's infrastructure, as well the infrastructure elements.
	 * When editing infrastructure information, like add a new element or editing one, it is important that this
	 * information is saved server side, not only client side. To do so, the proper function must be used.
	 * 
	 * See each function documentation for more information
	 **/
	 
	public class InfrastructureManager extends EventDispatcher
	{
		
		/* ------------- Constants------------- */
		
			//Internal events to notify changes over the infrastructure
			public static const DATACENTERS_UPDATED:String = "dataCentersUpdated_InfrastructureManager";
			public static const INFRASTRUCTURE_UPDATED:String = "infrastructureUpdated_InfrastructureManager";
		
		/* ------------- Private attributes ------------- */
				
		//Represents the infrastructure, where infrastructure elements are stored.
		//For more information about the relations between infrastructure elements, see the relevant class
		//This array may contain any Infrastructure Element
		private var _infrastructure:ArrayCollection;
		
		
		//ArrayCollection with all Data Centers in the Data Base
		private var _dataCenters:ArrayCollection;	
		
		
		
		/* ------------- Constructor ------------- */
		public function InfrastructureManager()
		{
			_infrastructure = new ArrayCollection();
			_dataCenters = new ArrayCollection();
		}
		
		
		
		/* ------------- Public methods ------------- */ 
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		//Related to Infrastructure
		
		/**
		 * Sets the infrastructure that will be managed by this infrastructure manager
		 **/
		public function set infrastructure(infr:ArrayCollection):void
		{
			_infrastructure = infr;
			
			//Infrastructure has been updated
			this.dispatchEvent(new Event(INFRASTRUCTURE_UPDATED, true));
		}
		
		
		 [Bindable(event="infrastructureUpdated_InfrastructureManager")]
		 public function get infrastructure():ArrayCollection
		 {
		 	return _infrastructure;
		 }
		
		
		/** Adds a new infrastructure element to infrastructure
		 * 
		 * @return true if successful
		 **/
		public function addInfrastructureElement(iE:InfrastructureElement):Boolean
		{
			if(!_infrastructure.contains(iE))
			{
				_infrastructure.addItem(iE);
				//Announcing that infrastructure has been updated
				this.dispatchEvent(new Event(INFRASTRUCTURE_UPDATED,true));
				return true;
			}
			else
				return false;
		}
		
		
		
		/** Deletes the given infrastructure element from infrastructure
		 * 
		 * @return true if successful
		 **/
		public function deleteInfrastructureElement(iE:InfrastructureElement):Boolean
		{
			var index:int = _infrastructure.getItemIndex(iE);
			if(index >= 0)
			{
				_infrastructure.removeItemAt(index);
				
				//Announcing that infrastructure has been updated
				this.dispatchEvent(new Event(INFRASTRUCTURE_UPDATED,true));
				return true;
			}
			else
				return false;
		}
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Related to Data Centers
		
		/**
		 * Sets the arraycollection that contains all data centers
		 **/
		public function set dataCenters(dC:ArrayCollection):void
		{
			this._dataCenters = dC;
			
			//Data Centers list has been updated
			dispatchEvent(new Event(DATACENTERS_UPDATED, true));
		}
		
		[Bindable(event="dataCentersUpdated_InfrastructureManager")]
		public function get dataCenters():ArrayCollection
		{
			return this._dataCenters;
		}
		
		/**
		 * Adds a new Data Center to the data centers list
		 **/
		public function addDataCenter(dataCenter:DataCenter):void
		{
			if(!_dataCenters.contains(dataCenter))
			{
				this._dataCenters.addItem(dataCenter);
				
				//Data Centers list has been updated
				dispatchEvent(new Event(DATACENTERS_UPDATED, true));
			}
		}
		
		
		/**
		 * Deletes a data center from the data centers list
		 **/
		public function deleteDataCenter(dataCenter:DataCenter):void
		{
			var index:int = _dataCenters.getItemIndex(dataCenter);
			if(index > -1)
			{
				_dataCenters.removeItemAt(index);
				
				//Data Centers list has been updated
				dispatchEvent(new Event(DATACENTERS_UPDATED, true));
			}
		}
		
		/**
		 * When a Data Center has been edited
		 * It assumes that the data center has already been edited
		 **/
		public function editDataCenter(oldDataCenter:DataCenter, newDataCenter:DataCenter):void
		{
			//Updating the old Data Center with the new values, without modifying its memory address
			oldDataCenter.id = newDataCenter.id;
			oldDataCenter.name = newDataCenter.name;
			oldDataCenter.situation = newDataCenter.situation;
			
			//Announcing that a data center has been edited
			var infrastructureEvent:InfrastructureEvent = new InfrastructureEvent(InfrastructureEvent.DATACENTER_EDITED, false);
			infrastructureEvent.dataCenter = oldDataCenter;
			dispatchEvent(infrastructureEvent);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Related to Racks
		
		/**
		 * Returns all user's racks
		 **/
		 [Bindable(event="infrastructureUpdated_InfrastructureManager")]
		 public function get racks():Array
		 {
		 	var allRacks:Array = new Array();
		 	var infrastructureLength:int = _infrastructure.length;
		 	var i:int;
		 	var element:InfrastructureElement;
		 	
		 	for(i = 0; i < infrastructureLength; i++)
		 	{
		 		element = _infrastructure.getItemAt(i) as InfrastructureElement;
		 		if(element is Rack)
		 			allRacks.push(element);
		 	}
		 	
		 	return allRacks;
		 }

		
		/**
		 * Get all Physical Machines assigned to a given rack
		 **/
		public function getPhysicalMachinesByRack(rack:Rack):Array
		{
			var allPM:Array = physicalMachines;
			var i:int;
			var allPMLength:int = allPM.length;
			var pm:PhysicalMachine;
			var rackPhysicalMachines:Array = new Array();
			
			for(i = 0; i < allPMLength; i++)
			{
				pm = allPM[i] as PhysicalMachine;
				if(pm.assignedTo != null && pm.assignedTo.id == rack.id)
					rackPhysicalMachines.push(pm);
			}
			
			return rackPhysicalMachines;
		}
		
		/**
		 * Get all Virtual Machines assigned to the physical machines that are assigned to a given rack
		 **/
		 public function getVirtualMachinesByRack(rack:Rack):Array
		 {
		 	//First, we get the Physical Machines assigned to this rack
		 	var physicalMachinesByRack:ArrayCollection = new ArrayCollection(getPhysicalMachinesByRack(rack));
		
		 	var i:int;
		 	var length:int = physicalMachinesByRack.length;
		 	var virtualMachinesByRack:Array = new Array();
		 	var physicalMachine:PhysicalMachine;
		 	
		 	for(i = 0; i < length; i++)
		 	{
		 		physicalMachine = physicalMachinesByRack[i] as PhysicalMachine;
		 		virtualMachinesByRack = virtualMachinesByRack.concat(getVirtualMachinesByPhysicalMachine(physicalMachine));
		 	}
		 	
		 	return virtualMachinesByRack;
		 }
		 
		 /**
		  * Updates a Rack that has been edited 
		  * @param oldRack
		  * @param newRack
		  * 
		  */		 
		 public function rackEdited(oldRack:Rack, newRack:Rack):void
		 {
		 	//Updating the old Rack without modifying its memory address
		 	oldRack.id = newRack.id;
		 	oldRack.name = newRack.name;
		 	oldRack.shortDescription = newRack.shortDescription;
		 	oldRack.largeDescription = newRack.largeDescription;
		 	
		 	//Announcing that this rack has been edited successfully
		 	var infrastructureEvent:InfrastructureEvent = new InfrastructureEvent(InfrastructureEvent.INFRASTRUCTURE_ELEMENT_EDITED, false);
		 	infrastructureEvent.infrastructureElement = oldRack;
		 	dispatchEvent(infrastructureEvent);
		 }
		 
		 
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		//Related to Physical Machines
		
		/**
		 * Returns all physical machines present in the infrastructure
		 * 
		 **/
		 public function get physicalMachines():Array
		 {
		 	var allPM:Array = new Array();
		 	var infrastructureLength:int = _infrastructure.length;
		 	var element:InfrastructureElement;
		 	var i:int;
		 	
		 	
		 	for(i = 0; i < infrastructureLength; i++)
		 	{
		 		element = _infrastructure.getItemAt(i) as InfrastructureElement;
		 		if(element is PhysicalMachine)
		 			allPM.push(element);
		 	}
		 	
		 	return allPM;
		 }
		
		/**
		 * Returns all HyperVisors assigned to a given Physical Machine
		 */
		public function getHyperVisorsByPhysicalMachine(physicalMachine:PhysicalMachine):Array
		{
			var pmHyperVisors:Array = new Array();
			var length:int = this._infrastructure.length;
			var infrastructureElement:InfrastructureElement;
			var i:int;
			for(i = 0; i < length; i++)
			{
				infrastructureElement = this._infrastructure.getItemAt(i) as InfrastructureElement;
				if(infrastructureElement is HyperVisor && HyperVisor(infrastructureElement).assignedTo.id == physicalMachine.id)
					pmHyperVisors.push(infrastructureElement);
			}
			
			return pmHyperVisors;
		}

		/**
		 * Get all Virtual Machines assigned to a given Physical Machine
		 **/
		public function getVirtualMachinesByPhysicalMachine(physicalMachine:PhysicalMachine):Array
		{
			var allVM:Array = virtualMachines;
			var i:int;
			var allVMLength:int = allVM.length;
			var vm:VirtualMachine;
			var pmVirtualMachines:Array = new Array();
			
			for(i = 0; i < allVMLength; i++)
			{
				vm = allVM[i] as VirtualMachine;
				if(vm.assignedTo.assignedTo.id == physicalMachine.id)
					pmVirtualMachines.push(vm);
			}
			
			return pmVirtualMachines;
		}
		
		
		public function addPhysicalMachine(physicalMachine:PhysicalMachine, hypervisors:ArrayCollection):void
		{
			//Adding the physical machine created
			this._infrastructure.addItem(physicalMachine);
			
			//Adding the hypervisors created (if there is any)
			var length:int = hypervisors.length;
			var i:int;
			for(i = 0; i < length; i++)
			{
				this._infrastructure.addItem(hypervisors.getItemAt(i));
			}
			
			//Announcing that infrastructure has been updated
			this.dispatchEvent(new Event(INFRASTRUCTURE_UPDATED,true));
		}
		
		/**
		  * Updates a Physical Machine that has been edited 
		  * @param oldRack
		  * @param newRack
		  * 
		  */		 
		 public function physicalMachineEdited(oldPhysicalMachine:PhysicalMachine, newPhysicalMachine:PhysicalMachine):void
		 {
		 	//Updating the old Physical Machine without modifying its memory address
		 	oldPhysicalMachine.id = newPhysicalMachine.id;
		 	oldPhysicalMachine.name = newPhysicalMachine.name;
		 	oldPhysicalMachine.assignedTo = newPhysicalMachine.assignedTo;
		 	oldPhysicalMachine.description = newPhysicalMachine.description;
		 	oldPhysicalMachine.ram = newPhysicalMachine.ram;
		 	oldPhysicalMachine.cpu = newPhysicalMachine.cpu;
		 	oldPhysicalMachine.hd = newPhysicalMachine.hd;
		 	oldPhysicalMachine.hostSO = newPhysicalMachine.hostSO;
		 	oldPhysicalMachine.networkModuleList = newPhysicalMachine.networkModuleList;
		 	
		 	//Announcing that this physical machine has been edited successfully
		 	var infrastructureEvent:InfrastructureEvent = new InfrastructureEvent(InfrastructureEvent.INFRASTRUCTURE_ELEMENT_EDITED, false);
		 	infrastructureEvent.infrastructureElement = oldPhysicalMachine;
		 	dispatchEvent(infrastructureEvent);
		 }
		 
		 
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		//Related to HyperVisors
		
		/**
		 * ArrayCollection containing HypervisorType objects 
		 */		
		
		private var _hypervisorTypes:ArrayCollection;
		[Bindable]
		public function get hypervisorTypes():ArrayCollection
		{
			return this._hypervisorTypes;	
		}
		 
		public function set hypervisorTypes(value:ArrayCollection):void
		{
			this._hypervisorTypes = value;
		}
		
		
		 /**
		  * Updates an Hypervisor that has been edited
		  * @param oldPhysicalMachine
		  * @param newPhysicalMachine
		  * 
		  */				 
		 public function hypervisorEdited(oldHypervisor:HyperVisor, newHypervisor:HyperVisor):void
		 { 	
		 	//Updating the Hypervisor without modifying its memory address
		 	oldHypervisor.id = newHypervisor.id;
		 	oldHypervisor.name = newHypervisor.name;
		 	oldHypervisor.shortDescription = newHypervisor.shortDescription;
		 	oldHypervisor.type = newHypervisor.type;
		 	oldHypervisor.ip = newHypervisor.ip;
		 	oldHypervisor.port = newHypervisor.port;
		 	oldHypervisor.assignedTo = newHypervisor.assignedTo;
		 	
		 	//Announcing that this physical machine has been edited successfully
		 	var infrastructureEvent:InfrastructureEvent = new InfrastructureEvent(InfrastructureEvent.INFRASTRUCTURE_ELEMENT_EDITED, false);
		 	infrastructureEvent.infrastructureElement = oldHypervisor;
		 	dispatchEvent(infrastructureEvent);
		 }		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		//Related to Virtual Machines
		
		/**
		 * Returns all user's virtual machines
		 * 
		 **/
		 public function get virtualMachines():Array
		 {

		 	var allVM:Array = new Array();
		 	var infrastructureLength:int = _infrastructure.length;
		 	var element:InfrastructureElement;
		 	var i:int;
		 	
		 	
		 	for(i = 0; i < infrastructureLength; i++)
		 	{
		 		element = _infrastructure.getItemAt(i) as InfrastructureElement;
		 		if(element is VirtualMachine)
		 			allVM.push(element);
		 	}
		 	
		 	return allVM;
		 }		
		 
		 
		 /**
		 * Changes the state of a Virtual Machine, to a new State
		 **/
		 public function changeVirtualMachineState(virtualMachine:VirtualMachine, newState:State):void
		 {
		 	virtualMachine.state = newState;
		 }
		 
		 
		/**
		  * Updates a Virtual Machine that has been edited 
		  * @param oldVirtualMachine
		  * @param newVirtualMachine
		  * 
		  */		 
		 public function virtualMachineEdited(oldVirtualMachine:VirtualMachine, newVirtualMachine:VirtualMachine):void
		 {
		 	//Updating the old Virtual Machine without modifying its memory address
		 	oldVirtualMachine.id = newVirtualMachine.id;
		 	oldVirtualMachine.cpu = newVirtualMachine.cpu;
		 	oldVirtualMachine.description = newVirtualMachine.description;
		 	oldVirtualMachine.hd = newVirtualMachine.hd;
		 	oldVirtualMachine.highDisponibility = newVirtualMachine.highDisponibility;
		 	oldVirtualMachine.name = newVirtualMachine.name;
		 	oldVirtualMachine.ram = newVirtualMachine.ram;
		 	oldVirtualMachine.state = newVirtualMachine.state;
		 	oldVirtualMachine.UUID = newVirtualMachine.UUID;
		 	oldVirtualMachine.vdrpIP = newVirtualMachine.vdrpIP;
		 	oldVirtualMachine.vdrpPort = newVirtualMachine.vdrpPort;
		 	oldVirtualMachine.virtualImage = newVirtualMachine.virtualImage;
		 	oldVirtualMachine.assignedTo = newVirtualMachine.assignedTo;
		 	
		 	//Announcing that this virtual machine has been edited successfully
		 	var infrastructureEvent:InfrastructureEvent = new InfrastructureEvent(InfrastructureEvent.INFRASTRUCTURE_ELEMENT_EDITED, false);
		 	infrastructureEvent.infrastructureElement = oldVirtualMachine;
		 	dispatchEvent(infrastructureEvent);
		 }
		 
		 /**
		  * Updates a list of virtual machines with a new State 
		  * @param virtualMachinesToCheck
		  * @param virtualMachinesWithNewState
		  * 
		  */		 
		 public function virtualMachinesStateChecked(virtualMachinesToCheck:ArrayCollection, virtualMachinesChecked:ArrayCollection):void
		 {
		 	
		 	var length:int = virtualMachinesToCheck.length;
		 	var length2:int = virtualMachinesChecked.length;
		 	var i:int;
		 	var j:int;
		 	var virtualMachineToCheck:VirtualMachine;
		 	var virtualMachineChecked:VirtualMachine;
		 	var infrastructureNeedsUpdate:Boolean = false;
		 	var index:int;
		 	
		 	//We iterate over the Virtual Machines that need to be checked, and look for
		 	//its corresponding checked Virtual Machine
		 	for(i = 0; i < length; i++)
		 	{
		 		virtualMachineToCheck = virtualMachinesToCheck.getItemAt(i) as VirtualMachine;
		 		for(j = 0; j < length2; j++)
		 		{
		 			virtualMachineChecked = virtualMachinesChecked.getItemAt(j) as VirtualMachine;
		 			if(virtualMachineChecked.id == virtualMachineToCheck.id)
		 				break;
		 			else
		 				virtualMachineChecked = null; 
		 		}
		 		
		 		if (virtualMachineChecked)
		 			virtualMachineToCheck.state = virtualMachineChecked.state;
		 		else
		 		{
		 			//If we haven't found the virtualMachineChecked that corresponds to
		 			//the virtualMachineToCheck, means that the Virtual Machine no longer exists in Data Base
		 			index = this._infrastructure.getItemIndex(virtualMachineToCheck);
		 			if(index > -1)
		 				this._infrastructure.removeItemAt(index);
		 			infrastructureNeedsUpdate = true;
		 		}		
		 	}
		 	
		 	if(infrastructureNeedsUpdate)
		 		dispatchEvent(new Event(INFRASTRUCTURE_UPDATED));
		 }
	}
	
}