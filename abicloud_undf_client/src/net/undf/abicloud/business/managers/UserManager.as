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
	
	import net.undf.abicloud.events.UserEvent;
	import net.undf.abicloud.vo.user.Enterprise;
	import net.undf.abicloud.vo.user.User;
	
	/**
	 * Manager for Users
	 * Stores information about users, and methods for manipulate this information
	 **/
	public class UserManager extends EventDispatcher
	{
		
		/* ------------- Constants------------- */
		public static const USERS_UPDATED:String = "usersUpdated_UserManager";
		public static const ROLES_UPDATED:String = "rolesUpdated_UserManager";
		public static const ENTERPRISES_UPDATED:String = "enterprisesUpdated_UserManager";
		
		/* ------------- Constructor ------------- */
		public function UserManager()
		{
			this._enterprises = new ArrayCollection();
			this._totalEnterprises = 0;
			
			this._users = new ArrayCollection();
			this._totalUsers = 0;
			
			this._roles = new ArrayCollection();
		}
		
		
		/* ------------- Public methods ------------- */
		
		///////////////////////////////////
		//RELATED TO USERS
		
		/**
		 * ArrayCollection containing only the users (limited by a length) that matched the UserOptionsList object that was given
		 * when the list of users was retrieved from the server
		 **/
		private var _users:ArrayCollection;
		[Bindable(event="usersUpdated_UserManager")]
		public function get users():ArrayCollection
		{
			return this._users;
		}
		
		public function set users(array:ArrayCollection):void
		{
			this._users = array;
			dispatchEvent(new Event(USERS_UPDATED, true));
		}
		
		/**
		 * Total number of users that matched the UserOptionsList object that was given when the list of users
		 * was retrieved from the server
		 */
		private var _totalUsers:int;
		[Bindable]
		public function get totalUsers():int
		{
			return this._totalUsers;
		}
		
		public function set totalUsers(value:int):void
		{
			this._totalUsers = value;
		}
		
		/**
		 * Adds a new user to the users list
		 * This new user must be first created in server
		 **/
		public function addUser(user:User):void
		{
			if(!this._users.contains(user))
			{
				this._users.addItem(user);
				dispatchEvent(new Event(USERS_UPDATED, true));
			}
		}
		
		
		/**
		 * Refreshes the users list when one has been edited
		 **/
		public function editUser(oldUsers:ArrayCollection, newUsers:ArrayCollection):void
		{
			var length:int = oldUsers.length;
			var i:int;
			var oldUser:User;
			var newUser:User;
			for(i = 0; i < length; i++)
			{
				oldUser = oldUsers.getItemAt(i) as User;
				newUser = newUsers.getItemAt(i) as User;
				
				//Updating the old user with the new values, without modifying its memory address
				oldUser.id = newUser.id;
				oldUser.role = newUser.role;
				oldUser.user = newUser.user;
				oldUser.name = newUser.name;
				oldUser.surname = newUser.surname;
				oldUser.description = newUser.description;
				oldUser.email = newUser.email;
				oldUser.pass = newUser.pass;
				oldUser.active = newUser.active;
				oldUser.locale = newUser.locale;
				oldUser.enterprise = newUser.enterprise;
			}

			
			//Announcing that users has been edited
			var userEvent:UserEvent = new UserEvent(UserEvent.USERS_EDITED, false);
			userEvent.users = oldUsers;
			dispatchEvent(userEvent);
		}
		
		
		/**
		 * Deletes a user from the users list
		 * The user must be first deleted in server
		 **/
		public function deleteUser(user:User):void
		{
			var index:int = this._users.getItemIndex(user);
			if(index >= 0)
			{
				this._users.removeItemAt(index);
				dispatchEvent(new Event(USERS_UPDATED, true));
			}	
		}
		
		/**
		 * Called when the session of one or more users has been closed 
		 * @param users The list of users whose session has been closed
		 * 
		 */		
		public function usersSessionClosed(users:ArrayCollection):void
		{
			//Announcing that the session of the given users has been successfully closed
			var userEvent:UserEvent = new UserEvent(UserEvent.USERS_SESSION_CLOSED);
			userEvent.users = users;
			dispatchEvent(userEvent);
		}
		
		///////////////////////////////////
		//RELATED TO ROLES
		
		
		/**
		 * ArrayCollection containing all possible Roles (stored in the server), that can be assigned to a user
		 **/
		private var _roles:ArrayCollection;
		[Bindable(event="rolesUpdated_UserManager")]
		public function get roles():ArrayCollection
		{
			return this._roles;
		}
		
		public function set roles(array:ArrayCollection):void
		{
			this._roles = array;
			dispatchEvent(new Event(ROLES_UPDATED, true));
		}
		
		///////////////////////////////////
		//RELATED TO ENTERPRISES
		
		/**
		 * ArrayCollection containing only the enterprises (limited by a length) that matched the EnterpriseOptionsList object that was given
		 * when the list of users was retrieved from the server
		 */
		private var _enterprises:ArrayCollection;
		[Bindable(event="enterprisesUpdated_UserManager")]
		public function get enterprises():ArrayCollection
		{
			return this._enterprises;
		}
		
		public function set enterprises(value:ArrayCollection):void
		{
			this._enterprises = value;
			dispatchEvent(new Event(ENTERPRISES_UPDATED));
		}
		
		/**
		 * Total number of enterprises that matched the UserOptionsList object that was given when the list of users
		 * was retrieved from the server
		 */
		private var _totalEnterprises:int;
		[Bindable]
		public function get totalEnterprises():int
		{
			return this._totalEnterprises;
		}
		
		public function set totalEnterprises(value:int):void
		{
			this._totalEnterprises = value;
		}
		
		/**
		 * Adds a new enterprise (already created in server) to the enterprises list 
		 * @param enterprise The enterprise to add
		 * 
		 */		
		public function addEnterprise(enterprise:Enterprise):void
		{
			this._enterprises.addItem(enterprise);
			dispatchEvent(new Event(ENTERPRISES_UPDATED));
		}
		
		/**
		 * Deletes an existing enterprise from the enterprises list, once the enterprise has been deleted from server
		 * @param enterprise The enterprise to be deleted
		 * 
		 */		
		public function deleteEnterprise(enterprise:Enterprise):void
		{
			var index:int = this._enterprises.getItemIndex(enterprise);
			if(index > -1)
			{
				this._enterprises.removeItemAt(index);
				dispatchEvent(new Event(ENTERPRISES_UPDATED));
			}
		}
		
		/**
		 * Updates an enterprise that has been changed in server with new values 
		 * @param oldEnterprise The Enterprise that exists in the enterprises list, to be updated
		 * @param newEnterprise An Enterprise object containing the new values for the enterprise that will be edited
		 * 
		 */		
		public function editEnterprise(oldEnterprise:Enterprise, newEnterprise:Enterprise):void
		{
			//Updating the old enterprise without modifying its memory address
			oldEnterprise.id = newEnterprise.id;
			oldEnterprise.name = newEnterprise.name;
			
			var userEvent:UserEvent = new UserEvent(UserEvent.ENTERPRISE_EDITED);
			userEvent.enterprise = oldEnterprise;
			dispatchEvent(userEvent);
		}
		
	}
}