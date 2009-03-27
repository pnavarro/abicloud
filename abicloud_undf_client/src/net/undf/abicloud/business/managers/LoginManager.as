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
	import flash.events.EventDispatcher;
	
	import net.undf.abicloud.vo.authentication.Session;
	import net.undf.abicloud.vo.user.User;
	
	/**
	 * Manager for Login process, a component from AbiCloud application
	 * 
	 * This class stores data from the user who has logged in AbiCloud
	 * It can also inform the view when a user has logged in to the application
	 **/
	 
	[Bindable]
	public class LoginManager
	{
		
		/* ------------- Private attributes ------------- */
		
		//User's session who has logged in AbiCloud
		private var _session:Session;
		
		//User's information who has logged in AbiCloud
		private var _user:User;
		
		//True if a user has logged in
		private var _userLogged:Boolean
		
		
		/* ------------- Setters & Getters ------------- */
		public function set session(userSession:Session):void
		{
			this._session = userSession;
		}
		public function get session():Session
		{
			return this._session;
		}
		
		public function set user(usr:User):void
		{
			this._user = usr;
		}
		public function get user():User
		{
			return this._user;
		}
		
		public function set userLogged(logged:Boolean):void
		{
			this._userLogged = logged;
		}
		public function get userLogged():Boolean
		{
			return this._userLogged;
		}

		/* ------------- Constructor ------------- */
		public function LoginManager()
		{
			this._session = null;
			this._user = null;
			this._userLogged = false;
		}
		
		
		
		/* ------------- Public methods ------------- */ 
	}
}