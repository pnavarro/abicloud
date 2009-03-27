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

package net.undf.abicloud.controller.login
{
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.managers.BrowserManager;
	
	import net.undf.abicloud.controller.ResultHandler;
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.vo.authentication.LoginResult;
	import net.undf.abicloud.vo.result.BasicResult;
	import net.undf.abicloud.vo.result.DataResult;
	
	public class LoginResultHandler extends ResultHandler
	{
		
		/* ------------- Constructor --------------- */
		public function LoginResultHandler()
		{
			super();
		}
		
		
		
		public function loginHandler(result:BasicResult):void
		{
			if(result.success)
			{
				var loginResult:LoginResult = DataResult(result).data as LoginResult;
				
				//Saving user's session
				AbiCloudModel.getInstance().loginManager.session = loginResult.session
				//Saving user's information
				AbiCloudModel.getInstance().loginManager.user = loginResult.user;
				//Saving user's authorized client resources
				AbiCloudModel.getInstance().authorizationManager.authorizedResources = loginResult.clientResources;
				
				
				//Notifying that a user has logged in
				AbiCloudModel.getInstance().loginManager.userLogged = true;
			}
			else
			{
				//There was a problem with the login process.
				//Most frequently, when user or password were incorrect
				super.handleResult(result);
			}
		}
		
		
		public function logoutHandler(result:BasicResult):void
		{
			if(result.success)
			{
				//AbiCloudModel.getInstance().loginManager.userLogged = false;
				
				//Provisional logout
				//var currentURL:String = BrowserManager.getInstance().url;
				navigateToURL(new URLRequest("javascript:location.reload(true)"), "_self");
			}
			else
			{
				//There was a problem with the logout process
				super.handleResult(result);
			}
		}

	}
}