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

package net.undf.abicloud.vo.authentication
{
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.vo.user.User;
	
	[RemoteClass(alias="com.abiquo.abiserver.pojo.authentication.LoginResult")]
	public class LoginResult
	{
		
		/* ------------- Public atributes ------------- */
		public var session:Session;
		public var user:User
		public var clientResources:ArrayCollection;
		
		/* ------------- Constructor ------------- */
		public function LoginResult()
		{
			session = new Session();
			user = new User();
			clientResources = new ArrayCollection();
		}

	}
}