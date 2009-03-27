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

package net.undf.abicloud.controller.infrastructure
{
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.controller.ResultHandler;
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.vo.infrastructure.DataCenter;
	import net.undf.abicloud.vo.infrastructure.HyperVisor;
	import net.undf.abicloud.vo.infrastructure.InfrastructureElement;
	import net.undf.abicloud.vo.infrastructure.PhysicalMachine;
	import net.undf.abicloud.vo.infrastructure.PhysicalMachineCreation;
	import net.undf.abicloud.vo.infrastructure.Rack;
	import net.undf.abicloud.vo.infrastructure.State;
	import net.undf.abicloud.vo.infrastructure.VirtualMachine;
	import net.undf.abicloud.vo.result.BasicResult;
	import net.undf.abicloud.vo.result.DataResult;
	
	/**
	 * Class to handle server responses when calling infrastructure remote services defined in InfrastructureEventMap
	 **/
	public class InfrastructureResultHandler extends ResultHandler
	{
		
		/* ------------- Constructor --------------- */
		public function InfrastructureResultHandler()
		{
			super();
		}
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		//DATA CENTER HANDLERS
		public function handleGetDataCenters(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding to the model the list of data centers
				var dataCenters:ArrayCollection = DataResult(result).data as ArrayCollection;
				AbiCloudModel.getInstance().infrastructureManager.dataCenters = dataCenters;
			}
			else
			{
				//There was a problem retrieving the list of data centers
				super.handleResult(result);
			}
		}
		
		
		public function handleCreateDataCenter(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new Data Center to the model
				var createdDataCenter:DataCenter = DataResult(result).data as DataCenter;
				AbiCloudModel.getInstance().infrastructureManager.addDataCenter(createdDataCenter);
			}
			else
			
			{
				//There was a problem creating the data center
				super.handleResult(result);
			}
		}
		
		
		public function handleDeleteDataCenter(result:BasicResult, deletedDataCenter:DataCenter):void
		{
			if(result.success)
			{
				//Deleting the data center from the model
				AbiCloudModel.getInstance().infrastructureManager.deleteDataCenter(deletedDataCenter);
			}
			else
			{
				//There was a problem deleting the data center
				super.handleResult(result);
			}
		}
		
		public function handleEditDataCenter(result:BasicResult, oldDataCenter:DataCenter, newDataCenter:DataCenter):void
		{
			if(result.success)
			{
				AbiCloudModel.getInstance().infrastructureManager.editDataCenter(oldDataCenter, newDataCenter);
			}
			else
			{
				//There was a problem editing the data center
				super.handleResult(result);
			}
		}
		
		public function handleGetInfrastructureByDataCenter(result:BasicResult):void
		{
			if(result.success)
			{
				//Infrastructure retrieved successfully
				AbiCloudModel.getInstance().infrastructureManager.infrastructure = DataResult(result).data as ArrayCollection;
			}
			else
			{
				//There was a problem retrieving the infrastructure
				super.handleResult(result);
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		// RACKS HANDLERS
		public function handleCreateRack(result:BasicResult):void
		{
			if(result.success)
			{
				//Rack created successfully
				AbiCloudModel.getInstance().infrastructureManager.addInfrastructureElement(DataResult(result).data as InfrastructureElement);
			}
			else
			{
				//There was a problem creating the rack
				super.handleResult(result);
			}
		}
		
		public function handleEditRack(result:BasicResult, oldRack:Rack, newRack:Rack):void
		{
			if(result.success)
			{
				//Rack edited successfully
				AbiCloudModel.getInstance().infrastructureManager.rackEdited(oldRack, newRack);
			}
			else
			{
				//There was a problem with the rack edition
				super.handleResult(result);
			}
		}
		
		public function handleDeleteRack(result:BasicResult, deletedRack:Rack):void
		{
			if(result.success)
			{
				//Deleting Rack from the model
				AbiCloudModel.getInstance().infrastructureManager.deleteInfrastructureElement(deletedRack);
			}
			else
			{
				//There was a problem deleting the Rack
				super.handleResult(result);
			}
		}
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		// PHYSICAL MACHINES HANDLERS
		public function handleCreatePhysicalMachine(result:BasicResult, originalPhysicalMachine:PhysicalMachine):void
		{
			if(result.success)
			{
				var physicalMachineCreation:PhysicalMachineCreation = DataResult(result).data as PhysicalMachineCreation;
				
				//This solves the bug of the new physical machine not appearing in the Infrastructure Tree
				var physicalMachineCreated:PhysicalMachine = physicalMachineCreation.physicalMachine;
				physicalMachineCreated.assignedTo = originalPhysicalMachine.assignedTo;
				
				var hypervisorsCreated:ArrayCollection = physicalMachineCreation.hypervisors;
				
				//Adding to the model the new Physical Machine
				AbiCloudModel.getInstance().infrastructureManager.addPhysicalMachine(physicalMachineCreated, hypervisorsCreated);
			}
			else
			{
				//There was a problem creating the Physical Machine
				super.handleResult(result);
			}
		}
		
		public function handleDeletePhysicalMachine(result:BasicResult, deletedPhysicalMachine:PhysicalMachine):void
		{
			if(result.success)
			{
				//Deleting the Physical Machine from the model
				AbiCloudModel.getInstance().infrastructureManager.deleteInfrastructureElement(deletedPhysicalMachine);
			}
			else
			{
				//There was a problem deleting the Physical Machine
				super.handleResult(result);
			}
		}
		
		public function handleEditPhysicalMachine(result:BasicResult, oldPhysicalMachine:PhysicalMachine, newPhysicalMachine:PhysicalMachine):void
		{
			if(result.success)
			{
				//Physical Machine edited successfully
				AbiCloudModel.getInstance().infrastructureManager.physicalMachineEdited(oldPhysicalMachine, newPhysicalMachine);
			}
			else
			{
				//There was a problem with the physical machine edition
				super.handleResult(result);
			}
		}
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		// HYPERVISOR HANDLERS
		
		public function handleCreateHypervisor(result:BasicResult):void
		{
			if(result.success)
			{
				//Hypervisor created successfully
				var createdHypervisor:HyperVisor = DataResult(result).data as HyperVisor;
				AbiCloudModel.getInstance().infrastructureManager.addInfrastructureElement(createdHypervisor);
			}
			else
			{
				//There was a problem with the Hypervisor creation
				super.handleResult(result);
			}
		}
		
		public function handleEditHypervisor(result:BasicResult, oldHypervisor:HyperVisor, newHypervisor:HyperVisor):void
		{
			if(result.success)
			{
				//Physical Machine edited successfully
				AbiCloudModel.getInstance().infrastructureManager.hypervisorEdited(oldHypervisor, newHypervisor);
			}
			else
			{
				//There was a problem with the physical machine edition
				super.handleResult(result);
			}
		}
		
		
		public function handleDeleteHypervisor(result:BasicResult, deletedHypervisor:HyperVisor):void
		{
			if(result.success)
			{
				//Deleting the Hypervisor from the model
				AbiCloudModel.getInstance().infrastructureManager.deleteInfrastructureElement(deletedHypervisor);
			}
			else
			{
				//There was a problem deleting the Physical Machine
				super.handleResult(result);
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		// VIRTUAL MACHINES HANDLERS
		
		public function handleCreateVirtualMachine(result:BasicResult):void
		{
			if(result.success)
			{
				//VirtualMachine was created successfully
				var createdVirtualMachine:VirtualMachine = DataResult(result).data as VirtualMachine;
				AbiCloudModel.getInstance().infrastructureManager.addInfrastructureElement(createdVirtualMachine);
			}
			else
			{
				//There was a problem with the Virtual Machine creation
				super.handleResult(result);
			}
		}
		
		public function handleEditVirtualMachine(result:BasicResult, oldVirtualMachine:VirtualMachine, newVirtualMachine:VirtualMachine):void
		{
			if(result.success)
			{
				//Virtual Machine edited successfully
				AbiCloudModel.getInstance().infrastructureManager.virtualMachineEdited(oldVirtualMachine, newVirtualMachine);
			}
			else
			{
				//There was a problem with the virtual machine edition
				super.handleResult(result);
			}
		}
		
		
		public function handleVirtualMachineStateChanged(result:BasicResult, virtualMachine:VirtualMachine):void
		{
			if(result.success)
			{
				//Virtual Machine's state changed successfully
				AbiCloudModel.getInstance().infrastructureManager.changeVirtualMachineState(virtualMachine, DataResult(result).data as State);
			}
			else
			{
				//There was a problem performing a action over a Virtual Machine
				if(result is DataResult && DataResult(result).data != null)
					//There was an error, but the server returned a new state for the virtual machine
					AbiCloudModel.getInstance().infrastructureManager.changeVirtualMachineState(virtualMachine, DataResult(result).data as State);
					
				super.handleResultInBackground(result);
			}
		}
		
		public function handleCheckVirtualMachinesState(result:BasicResult, virtualMachinesToCheck:ArrayCollection):void
		{
			if(result.success)
			{
				//Virtual Machine's checked successfully
				var virtualMachinesWithNewState:ArrayCollection = DataResult(result).data as ArrayCollection;
				AbiCloudModel.getInstance().infrastructureManager.virtualMachinesStateChecked(virtualMachinesToCheck, virtualMachinesWithNewState);
			}
			else
			{
				//There was a problem checking the virtual machine's state
				super.handleResultInBackground(result);
			}
		}
	}
}