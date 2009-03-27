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

package net.undf.abicloud.controller.virtualappliance
{
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.controller.ResultHandler;
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.vo.result.BasicResult;
	import net.undf.abicloud.vo.result.DataResult;
	import net.undf.abicloud.vo.virtualappliance.VirtualAppliance;
	import net.undf.abicloud.vo.virtualappliance.VirtualDataCenter;
	
	/**
	 * Class to handle server responses when calling virtual appliance remote services defined in VirtualApplianceEventMap
	 **/
	
	public class VirtualApplianceResultHandler extends ResultHandler
	{
		public function VirtualApplianceResultHandler()
		{
			super();
		}
		
		
		////////////////////////////////////////////
		//Virtual Datacenters
		
		public function handleGetVirtualDataCentersByEnterprise(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the VirtualDataCenter list to the model
				AbiCloudModel.getInstance().virtualApplianceManager.virtualDataCenters = DataResult(result).data as ArrayCollection;
			}
			else
			{
				//There was a problem retrieving the VirtualDataCenter list
				super.handleResult(result);
			}
		}
		
		public function handleGetVirtualApplianceNodes(result:BasicResult, virtualAppliance:VirtualAppliance):void
		{
			if(result.success)
			{
				//Setting the list of nodes for this VirtualAppliance
				AbiCloudModel.getInstance().virtualApplianceManager.setVirtualApplianceNodes(virtualAppliance, DataResult(result).data as ArrayCollection);
			}	
			else
				//There was a problem retrieving the VirtualAppliance's nodes
				super.handleResult(result);
		}
		
		public function handleCreateVirtualDataCenter(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new VirtualDataCenter to the model
				AbiCloudModel.getInstance().virtualApplianceManager.addVirtualDataCenter(DataResult(result).data as VirtualDataCenter);
			}
			else
			{
				//There was a problem creating a new VirtualDataCenter
				super.handleResult(result);
			}
		}
		
		public function handleDeleteVirtualDataCenter(result:BasicResult, virtualDataCenter:VirtualDataCenter):void
		{
			if(result.success)
			{
				//Removing the VirtualDataCenter from the model
				AbiCloudModel.getInstance().virtualApplianceManager.deleteVirtualDataCenter(virtualDataCenter);
			}
			else
			{
				//There was a problem deleting the VirtualDataCenter from the server
				super.handleResult(result);
			}
		}
		
		public function handleEditVirtualDataCenter(result:BasicResult, oldVirtualDataCenter:VirtualDataCenter, newVirtualDataCenter:VirtualDataCenter):void
		{
			if(result.success)
			{
				//Updating the VirtualDataCenter in model
				AbiCloudModel.getInstance().virtualApplianceManager.editVirtualDataCenter(oldVirtualDataCenter, newVirtualDataCenter);
			}
			else
			{
				//There was a problem editing the VirtualDataCenter
				super.handleResult(result);
			}
		}
		
		////////////////////////////////////////////
		//Virtual Appliances
		public function handleGetVirtualAppliancesByEnterprise(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding to the model the list of virtual appliances
				AbiCloudModel.getInstance().virtualApplianceManager.virtualAppliances = DataResult(result).data as ArrayCollection;
			}
			else
			{
				//There was a problem retrieving the virtual appliances
				super.handleResult(result);
			}
		}
		
		public function handleGetVirtualApplianceUpdatedNodes(result:BasicResult, virtualAppliance:VirtualAppliance):void
		{
			if(result.success)
			{
				//Updating Log list for the Virtual Appliance
				AbiCloudModel.getInstance().virtualApplianceManager.setVirtualApplianceUpdatedLogs(virtualAppliance, DataResult(result).data as ArrayCollection);
			}
			else
			{
				//There was a problem retrieving the updated virtual appliance logs
				super.handleResult(result);
			}
		}
		public function handleCreateVirtualAppliance(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new virtual appliance to the model
				AbiCloudModel.getInstance().virtualApplianceManager.addVirtualAppliance(DataResult(result).data as VirtualAppliance);
			}
			else
			{
				//There was a problem creating the virtual appliance
				super.handleResult(result);
			}
		}
		
		public function handleEditVirtualAppliance(result:BasicResult, oldVirtualAppliance:VirtualAppliance, handleInBackground:Boolean = false):void
		{
			if(result.success)
			{
				//Announcing that a virtual appliance has been edited
				var newVirtualAppliance:VirtualAppliance = DataResult(result).data as VirtualAppliance;
				AbiCloudModel.getInstance().virtualApplianceManager.editVirtualAppliance(oldVirtualAppliance, newVirtualAppliance);
			}
			else
			{
				//There was a problem editing the VirtualAppliance
				if(result is DataResult && DataResult(result).data != null)
					//There was an error, but the server returned a new state for the virtual appliance
					AbiCloudModel.getInstance().virtualApplianceManager.changeVirtualApplianceState(oldVirtualAppliance, DataResult(result).data as VirtualAppliance);
					
				if(handleInBackground)
					super.handleResultInBackground(result);
				else
					super.handleResult(result);
			}
		}
		
		public function handleDeleteVirtualAppliance(result:BasicResult, deletedVirtualAppliance:VirtualAppliance, handleInBackground:Boolean = false):void
		{
			if(result.success)
			{
				//Announcing that a virtual appliance has been deleted
				AbiCloudModel.getInstance().virtualApplianceManager.deleteVirtualAppliance(deletedVirtualAppliance);
			}
			else
			{
				//There was a problem with the virtual appliance deletion
				if(handleInBackground)
					super.handleResultInBackground(result);
				else
					super.handleResult(result);
			}
		}
		
		public function handleVirtualApplianceStateChanged(result:BasicResult, oldVirtualAppliance:VirtualAppliance):void
		{
			if(result.success)
			{
				//Announcing that the state of a Virtual Appliance has been changed
				AbiCloudModel.getInstance().virtualApplianceManager.changeVirtualApplianceState(oldVirtualAppliance, DataResult(result).data as VirtualAppliance);
			}
			else
			{
				//There was a problem changing the state of a Virtual Appliance
				if(result is DataResult && DataResult(result).data != null)
					//There was an error, but the server returned a new state for the virtual appliance
					AbiCloudModel.getInstance().virtualApplianceManager.changeVirtualApplianceState(oldVirtualAppliance, DataResult(result).data as VirtualAppliance);
				
				super.handleResultInBackground(result);
			}
		}
		
		public function handleCheckVirtualAppliancesByEnterprise(result:BasicResult):void
		{
			if(result.success)
			{
				//Virtual Appliance's checked successfully
				var virtualAppliancesChecked:ArrayCollection = DataResult(result).data as ArrayCollection;
				AbiCloudModel.getInstance().virtualApplianceManager.checkVirtualAppliances(virtualAppliancesChecked);
			}
			else
			{
				//There was a problem checking the virtual appliance's state
				super.handleResultInBackground(result);
			}
		}
		
		public function handleCheckVirtualAppliance(result:BasicResult, virtualApplianceToCheck:VirtualAppliance):void
		{
			if(result.success)
			{
				//Virtual Appliance checked successfully
				var virtualApplianceChecked:VirtualAppliance = DataResult(result).data as VirtualAppliance;
				AbiCloudModel.getInstance().virtualApplianceManager.checkVirtualAppliance(virtualApplianceToCheck, virtualApplianceChecked);
			}
			else
			{
				//There was a problem checking the virtual appliance's state
				super.handleResultInBackground(result);
			}
		}
	}
}