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
	
	import net.undf.abicloud.vo.authentication.Login;
	import net.undf.abicloud.vo.authentication.Session;
	
	public class LoginEvent extends Event
	{
		
		/* ------------- Constants------------- */
		public static const LOGIN:String = "loginLoginEvent";
		public static const LOGOUT:String = "logoutLoginEvent";
	
		
		/* ------------- Public atributes ------------- */
		//Necesary information to perform a login action
		public var login:Login;
		
		//Necessary information to perform a logout action
		public var logout:Session;
		
		
		/* ------------- Constructor ------------- */
		public function LoginEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}

	}
}