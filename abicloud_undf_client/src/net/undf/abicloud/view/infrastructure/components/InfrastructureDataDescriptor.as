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

package net.undf.abicloud.view.infrastructure.components
{
	/**
	 * Data descriptor for the racks tree in Summary.mxml. Describes how racks, physicals and virtual machines are connected
	 * to be able to draw them in a Flex Tree component
	 **/
	 
	
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.controls.Alert;
	import mx.resources.ResourceBundle;
	import mx.resources.ResourceManager;
	import mx.utils.ObjectUtil;
	
	import net.undf.abicloud.events.InfrastructureEvent;
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.utils.customtree.ICustomTreeDataDescriptor;
	import net.undf.abicloud.view.common.AbiCloudAlert;
	import net.undf.abicloud.vo.infrastructure.HyperVisor;
	import net.undf.abicloud.vo.infrastructure.InfrastructureElement;
	import net.undf.abicloud.vo.infrastructure.PhysicalMachine;
	import net.undf.abicloud.vo.infrastructure.PhysicalMachineCreation;
	import net.undf.abicloud.vo.infrastructure.Rack;
	import net.undf.abicloud.vo.infrastructure.State;
	import net.undf.abicloud.vo.infrastructure.VirtualMachine;
	
	public class InfrastructureDataDescriptor extends EventDispatcher implements ICustomTreeDataDescriptor
	{
		[ResourceBundle("Infrastructure")]
		private var rb:ResourceBundle;
		[ResourceBundle("Common")]
		private var rb2:ResourceBundle;
		
		public function InfrastructureDataDescriptor()
		{
			
		}
		
		public function isCopyAllowed(parent:Object, newChild:Object):Boolean
		{
			if(parent is Rack && newChild is PhysicalMachine)
				return true;
			else if(parent is PhysicalMachine && newChild is VirtualMachine)
				return true;
			else 
				return false;
		}
		
		public function copyChild(parent:Object, newChild:Object):Boolean
		{
			var infrastructureEvent:InfrastructureEvent;
			
			if(parent is Rack && newChild is PhysicalMachine)
			{
				var parentRack:Rack = parent as Rack;
				var childPhysicalMachine:PhysicalMachine = newChild as PhysicalMachine;
				var copiedPhysicalMachine:PhysicalMachine;
				
				//Creating the copy
				copiedPhysicalMachine = ObjectUtil.copy(childPhysicalMachine) as PhysicalMachine;
				copiedPhysicalMachine.name = copiedPhysicalMachine.name + " (Clon)";
				if(copiedPhysicalMachine.name.length > 29)
					//We have to check if PhysicalMachine's name is not too long...
					copiedPhysicalMachine.name = copiedPhysicalMachine.name.substr(0, 29);
					
				copiedPhysicalMachine.assignedTo = parent as InfrastructureElement;
				//Network information is not cloned
				copiedPhysicalMachine.networkModuleList = new ArrayCollection();
				//Physical Machine state is not cloned
				copiedPhysicalMachine.cpuUsed = 0;
				copiedPhysicalMachine.ramUsed = 0;
				copiedPhysicalMachine.hdUsed = 0;
				
				//Saving the copied PhysicalMachine, without copying its hypervisors
				var physicalMachineCreation:PhysicalMachineCreation = new PhysicalMachineCreation();
				physicalMachineCreation.physicalMachine = copiedPhysicalMachine;
				physicalMachineCreation.hypervisors = new ArrayCollection();
				infrastructureEvent = new InfrastructureEvent(InfrastructureEvent.CREATE_PHYSICALMACHINE);
				infrastructureEvent.physicalMachineCreation = physicalMachineCreation;
				dispatchEvent(infrastructureEvent);
				
				return true;
			}
			else if(parent is PhysicalMachine && newChild is VirtualMachine)
			{
				var parentPhysicalMachine:PhysicalMachine = parent as PhysicalMachine;
				var childVirtualMachine:VirtualMachine = newChild as VirtualMachine;
				var copiedVirtualMachine:VirtualMachine = new VirtualMachine();
				
				//Getting the HyperVisors assigned to this PhysicalMachine
				var hyperVisors:Array = AbiCloudModel.getInstance().infrastructureManager.getHyperVisorsByPhysicalMachine(parentPhysicalMachine);
				if(hyperVisors.length == 0)
				{
					//There are no hypervisors assigned to this Physical Machine. Copy operation is not possible	   
					AbiCloudAlert.showAlert(ResourceManager.getInstance().getString("Common", "ALERT_TITLE_LABEL"),
									 ResourceManager.getInstance().getString("Infrastructure", "ALERT_CLONE_VIRTUALMACHINE_HEADER"),
									 ResourceManager.getInstance().getString("Infrastructure", "ALERT_MOVE_VIRTUALMACHINE_TEXT"),
									 Alert.OK);	   
						   
					return false;
				}
				else
				{
					//Making the copy of the virtual machine
					//State and UUID fields will be created by server
					copiedVirtualMachine.name = childVirtualMachine.name + " (Clon)";
					if(copiedVirtualMachine.name.length > 99)
						copiedVirtualMachine.name = copiedVirtualMachine.name.substr(0, 99);
					copiedVirtualMachine.virtualImage = childVirtualMachine.virtualImage;
					copiedVirtualMachine.description = childVirtualMachine.description;
					copiedVirtualMachine.ram = childVirtualMachine.ram;
					copiedVirtualMachine.cpu = childVirtualMachine.cpu;
					copiedVirtualMachine.hd = childVirtualMachine.hd;
					copiedVirtualMachine.highDisponibility = copiedVirtualMachine.highDisponibility;
					
					//Fact: assign the Virtual Machine to the first HyperVisor in the list
					copiedVirtualMachine.assignedTo = hyperVisors[0];
					
					//Anouncing the creation of the virtual machine's copy
					infrastructureEvent = new InfrastructureEvent(InfrastructureEvent.CREATE_VIRTUALMACHINE);
					infrastructureEvent.infrastructureElement = copiedVirtualMachine;
					dispatchEvent(infrastructureEvent);
					
					return true;
				}
			}
			else
				return false;
		}
		
		public function isMoveAllowed(parent:Object, newChild:Object):Boolean
		{
			if(parent is Rack && newChild is PhysicalMachine)
			{
				var physicalMachinesByRack:ArrayCollection = new ArrayCollection(AbiCloudModel.getInstance().infrastructureManager.getPhysicalMachinesByRack(parent as Rack));
				if(! physicalMachinesByRack.contains(newChild))
					return true;
				else 
					return false;
			}
			else if(parent is PhysicalMachine && newChild is VirtualMachine)
			{
				var virtualMachinesByPM:ArrayCollection = new ArrayCollection(AbiCloudModel.getInstance().infrastructureManager.getVirtualMachinesByPhysicalMachine(parent as PhysicalMachine));
				if(! virtualMachinesByPM.contains(newChild))
					return true;
				else
					return false;
			}
			else 
				return false;
		}
		
		public function moveChild(parent:Object, newChild:Object):Boolean
		{
			
			var elementParent:InfrastructureElement;
			var elementChild:InfrastructureElement;
			var infrastructureEvent:InfrastructureEvent;
			
			if(parent is Rack && newChild is PhysicalMachine)
			{
				elementParent = parent as Rack;
				var physicalMachineBackup:PhysicalMachine = newChild as PhysicalMachine;
				
				elementChild = ObjectUtil.copy(physicalMachineBackup) as PhysicalMachine;
				elementChild.assignedTo = elementParent;
				
				infrastructureEvent = new InfrastructureEvent(InfrastructureEvent.EDIT_PHYSICALMACHINE);
				infrastructureEvent.infrastructureElement = elementChild;
				infrastructureEvent.oldInfrastructureElement = physicalMachineBackup;
				dispatchEvent(infrastructureEvent);
				
				return true;
			}
			
			else if(parent is PhysicalMachine && newChild is VirtualMachine)
			{
				elementParent = parent as PhysicalMachine;
				var virtualMachineBackup:VirtualMachine = newChild as VirtualMachine;
				elementChild = ObjectUtil.copy(virtualMachineBackup) as VirtualMachine;
				
				//Getting the HyperVisors assigned to this PhysicalMachine
				var hyperVisors:Array = AbiCloudModel.getInstance().infrastructureManager.getHyperVisorsByPhysicalMachine(elementParent as PhysicalMachine);
				if(hyperVisors.length == 0)
				{
					//There are no hypervisors assigned to this Physical Machine. Move operation is not possible
					AbiCloudAlert.showAlert(ResourceManager.getInstance().getString("Common", "ALERT_TITLE_LABEL"),
									 ResourceManager.getInstance().getString("Infrastructure", "ALERT_MOVE_VIRTUALMACHINE_HEADER"),
									 ResourceManager.getInstance().getString("Infrastructure", "ALERT_MOVE_VIRTUALMACHINE_TEXT"),
									 Alert.OK);
						   
					return false;
				}
				else
				{
					//Fact: assign the Virtual Machine to the first HyperVisor in the list
					elementChild.assignedTo = hyperVisors[0] as HyperVisor;
					
					infrastructureEvent = new InfrastructureEvent(InfrastructureEvent.EDIT_VIRTUALMACHINE);
					infrastructureEvent.infrastructureElement = elementChild;
					infrastructureEvent.oldInfrastructureElement = virtualMachineBackup;
					if(virtualMachineBackup.state.id != State.IN_PROGRESS)
						dispatchEvent(infrastructureEvent);
					
					return true;
				}
			}
			else
				return false;
		}
		
		
		public function getChildren(node:Object, model:Object = null):ICollectionView
		{
			if(node is Rack)
			{
				var rack:Rack = node as Rack;
				return new ArrayCollection(AbiCloudModel.getInstance().infrastructureManager.getPhysicalMachinesByRack(rack));
			}
			else
				return null;
		}
		
		public function hasChildren(node:Object, model:Object = null):Boolean
		{
			if(node is Rack)
			{
				var rack:Rack = node as Rack;
				var rackPMAssigned:Array = AbiCloudModel.getInstance().infrastructureManager.getPhysicalMachinesByRack(rack);
				return (rackPMAssigned.length > 0);
			}
			else
				return false;
		}
		
		public function isBranch(node:Object, model:Object = null):Boolean
		{
			return node is Rack;
		}
		
		
		public function getNodeLevel(node:Object):int
		{
			if(node is Rack)
				return 0;
			else
				return 1;
		}
		
		public function isNodeDraggable(node:Object):Boolean
		{
			return node is PhysicalMachine;
		}
		
	}
}