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

package net.undf.abicloud.vo.user
{
	import mx.collections.ArrayCollection;
	
	//This class is used to return the information when a list of users is retrieved from the server
	[RemoteClass(alias="com.abiquo.abiserver.pojo.user.UserListResult")]
	public class UserListResult
	{
		//The total number of users that matched the EnterprisesListOptions given to retrieve the list of Enterprises
		public var usersList:ArrayCollection;
		
		//The List of Enterprises (limited by a length) that match the EnterpriseListOptions given to retrieve the list of Enterprises
		public var totalUsers:int;
		
		public function UserListResult()
		{
			usersList = new ArrayCollection();
			totalUsers = 0;
		}

	}
}