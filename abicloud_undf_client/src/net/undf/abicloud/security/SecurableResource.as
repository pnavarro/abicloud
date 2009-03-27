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

package net.undf.abicloud.security
{
	
	/**
	 * Resource represents a resource in server or client.
	 * A resource is an entity that can be secured server side. If we associated a resource to a client component (like a Flex component),
	 * we can give security to that component from the server
	 *
	 * A Resource is identified by its name and the name of the group which it belongs to
	 **/
	public class SecurableResource
	{
		
		/* ------------- Public atributes ------------- */
		private var _name:String;
		private var _group:String;
		
		/* ------------- Constructor ------------- */
		public function SecurableResource(name:String, group:String)
		{
			_name = name;
			_group = group;
		}
		
		public function get name():String
		{
			return this._name;
		}
		
		public function get group():String
		{
			return this._group;
		}
		
		public function applyAuthorization(authorizationManager:IAuthorizationManager):Boolean
		{
			return authorizationManager.isAuthorized(this);
		}

	}
}