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
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.security.IAuthorizationManager;
	import net.undf.abicloud.security.SecurableResource;
	import net.undf.abicloud.vo.authorization.Resource;
	
	public class AuthorizationManager implements IAuthorizationManager
	{
		
		/* ------------- Private attributes ------------- */
		
		//Where the Resources are saved
		private var _authorizedResources:ArrayCollection;
		
		
		/* ------------- Setters & Getters ------------- */
		public function set authorizedResources(array:ArrayCollection):void
		{
			this._authorizedResources = array;
		}
		
		
		/* ------------- Constructor ------------- */
		public function AuthorizationManager()
		{
			_authorizedResources = new ArrayCollection();
		}


		/* ------------- Public methods ------------- */ 
		
		/**
		 * Implementation of method isAuthorized, described in IAthorizationManager interface
		 * Since this AuthorizationManager saves Resources (received from a server), we need to 
		 * transform a Resource into a SecurableResource to perform an authorization check
		 * @param securableResourceToCheck
		 * @return 
		 * 
		 */		
		public function isAuthorized(securableResourceToCheck:SecurableResource):Boolean
		{
			var resourcesLength:int = _authorizedResources.length;
			var i:int;
			var resource:Resource;
			
			for(i = 0; i<resourcesLength; i++)
			{
				resource = _authorizedResources.getItemAt(i) as Resource;
				if(securableResourceToCheck.name == resource.name && securableResourceToCheck.group == resource.group.name)
					return true;
				
			}
			return false;
		}
	}
}