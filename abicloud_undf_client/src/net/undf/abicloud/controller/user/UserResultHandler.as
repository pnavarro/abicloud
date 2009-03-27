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

package net.undf.abicloud.controller.user
{
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.controller.ResultHandler;
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.vo.result.BasicResult;
	import net.undf.abicloud.vo.result.DataResult;
	import net.undf.abicloud.vo.user.Enterprise;
	import net.undf.abicloud.vo.user.EnterpriseListResult;
	import net.undf.abicloud.vo.user.User;
	import net.undf.abicloud.vo.user.UserListResult;
	
	
	/**
	 * Class to handle server responses when calling user remote services defined in UserEventMap
	 **/
	public class UserResultHandler extends ResultHandler
	{
		
		/* ------------- Constructor --------------- */
		public function UserResultHandler()
		{
			super();
		}
		
		
		public function handleGetUsers(result:BasicResult):void
		{
			if(result.success)
			{
				var userListResult:UserListResult = DataResult(result).data as UserListResult;
				
				//Adding to the model the list of users and number of users
				AbiCloudModel.getInstance().userManager.totalUsers = userListResult.totalUsers;
				AbiCloudModel.getInstance().userManager.users = userListResult.usersList;
			}
			else
			{
				//There was a problem retrieving the list of data centers
				super.handleResult(result);
			}
		}
		
		
		public function handleCreateUser(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new user to the model
				var createdUser:User = DataResult(result).data as User;
				AbiCloudModel.getInstance().userManager.addUser(createdUser);
			}
			else
			{
				//There was a problem creating a new user
				super.handleResult(result);
			}
		}
		
		
		public function handleDeleteUser(result:BasicResult, deletedUser:User):void
		{
			if(result.success)
			{
				//Deleting the user in the model
				AbiCloudModel.getInstance().userManager.deleteUser(deletedUser);
			}
			else
			{
				//There was a problem deleting the user
				super.handleResult(result);
			}
		}
		
		
		public function handleEditUser(result:BasicResult, oldUsers:ArrayCollection, newUsers:ArrayCollection):void
		{
			if(result.success)
			{
				//Announcing the model that the user has been edited
				AbiCloudModel.getInstance().userManager.editUser(oldUsers, newUsers);
			}
			else
				//There was a problem editing the user
				super.handleResult(result);
		}
		
		public function handleCloseSessionUsers(result:BasicResult, users:ArrayCollection):void
		{
			if(result.success)
			{
				//Announcing the model that given user's session has been closed
				AbiCloudModel.getInstance().userManager.usersSessionClosed(users);
			}
			else
			{
				//There was a problem closing the session of the given users
				super.handleResult(result);
			}
		}
		
		//////////////////////////////////////////
		//ENTERPRISES
		
		public function handleGetEnterprises(result:BasicResult):void
		{
			if(result.success)
			{
				var enterpriseListResult:EnterpriseListResult = DataResult(result).data as EnterpriseListResult;
				
				//Adding the list of enterprises to the model, and the total number of enterprises in server
				AbiCloudModel.getInstance().userManager.totalEnterprises = enterpriseListResult.totalEnterprises;
				AbiCloudModel.getInstance().userManager.enterprises = enterpriseListResult.enterprisesList;
			}
			else
				//There was a problem retrieving the enterprises list
				super.handleResult(result);
		}
		
		public function handleCreateEnterprise(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new enterprise to the model
				AbiCloudModel.getInstance().userManager.addEnterprise(DataResult(result).data as Enterprise);
			}
			else
				//There as a problem creating the enterprise
				super.handleResult(result);
		}
		
		public function handleEditEnterprise(result:BasicResult, oldEnterprise:Enterprise, newEnterprise:Enterprise):void
		{
			if(result.success)
			{
				//Editing the old enterprise in model
				AbiCloudModel.getInstance().userManager.editEnterprise(oldEnterprise, newEnterprise);	
			}
			else
				//There as a problem editing the enterprise
				super.handleResult(result);
		}
		
		public function handleDeleteEnterprise(result:BasicResult, enterprise:Enterprise):void
		{
			if(result.success)
			{
				//Deleting the enterprise from the model
				AbiCloudModel.getInstance().userManager.deleteEnterprise(enterprise);
			}
			else
				//There as a problem deleting the enterprise
				super.handleResult(result);
		}
	}
}