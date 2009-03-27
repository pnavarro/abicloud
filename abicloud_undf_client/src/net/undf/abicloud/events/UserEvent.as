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
	
	import net.undf.abicloud.vo.user.Enterprise;
	import net.undf.abicloud.vo.user.EnterpriseListOptions;
	import net.undf.abicloud.vo.user.User;
	import net.undf.abicloud.vo.user.UserListOptions;
	
	public class UserEvent extends Event
	{

		/* ------------- Constants------------- */
		public static const GET_USERS:String = "getUsersUserEvent";
		public static const CREATE_USER:String = "createUserUserEvent";
		public static const DELETE_USER:String = "deleteUserUserEvent";
		public static const EDIT_USERS:String = "editUsersUserEvent";
		public static const CLOSE_SESSION_USERS:String = "deleteSessionUsersUserEvent";
		
		public static const GET_ENTERPRISES:String = "getEnterprisesUserEvent";
		public static const CREATE_ENTERPRISE:String = "createEnterpriseUserEvent";
		public static const EDIT_ENTERPRISE:String = "editEnterpriseUserEvent";
		public static const DELETE_ENTERPRISE:String = "deleteEnterpriseUserEvent";
		
		public static const USERS_EDITED:String = "usersEditedUserEvent";
		public static const ENTERPRISE_EDITED:String = "enterpriseEditedUserEvent";
		public static const USERS_SESSION_CLOSED:String = "usersSessionClosedUserEvent";
		
		/* ------------- Public atributes ------------- */
		public var user:User;
		
		public var users:ArrayCollection;
		public var oldUsers:ArrayCollection;
		
		public var enterprise:Enterprise;
		public var oldEnterprise:Enterprise;
		
		public var userListOptions:UserListOptions = new UserListOptions();
		public var enterpriseListOptions:EnterpriseListOptions = new EnterpriseListOptions();
		
		/* ------------- Constructor ------------- */
		public function UserEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}

	}
}