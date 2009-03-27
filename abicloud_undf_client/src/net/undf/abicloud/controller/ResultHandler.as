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

package net.undf.abicloud.controller
{
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.resources.ResourceBundle;
	import mx.resources.ResourceManager;
	
	import net.undf.abicloud.business.managers.notification.Notification;
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.view.common.AbiCloudAlert;
	import net.undf.abicloud.vo.result.BasicResult;
	
	public class ResultHandler
	{
		/* ------- Constructor ------ */
		public function ResultHandler()
		{
			
		}
		
		[ResourceBundle("Common")]
		private var rb:ResourceBundle;
		
		//Standard method to notify user how last remote call has ended
		public function handleResult(basicResult:BasicResult):void
		{
			switch(basicResult.resultCode)
			{
				case BasicResult.USER_INVALID:
					//Invalid user or password				   
					AbiCloudAlert.showError(ResourceManager.getInstance().getString("Common", "ALERT_ERROR_TITLE_LABEL"),
								 ResourceManager.getInstance().getString("Common", "ALERT_ERROR_SERVER_RESPONSE_HEADER"),
								 ResourceManager.getInstance().getString("Common", "ALERT_MESSAGE_USERINVALID"),
								 Alert.OK);						   
					break;
					
				case BasicResult.SESSION_INVALID:
					//Invalid Session
					AbiCloudAlert.showError(ResourceManager.getInstance().getString("Common", "ALERT_ERROR_TITLE_LABEL"),
								 ResourceManager.getInstance().getString("Common", "ALERT_ERROR_SERVER_RESPONSE_HEADER"),
								 ResourceManager.getInstance().getString("Common", "ALERT_MESSAGE_SESSIONINVALID"),
								 Alert.OK,
								 onSessionInvalid);
					break;
					
				case BasicResult.SESSION_TIMEOUT:
					//The session has time out
					AbiCloudAlert.showError(ResourceManager.getInstance().getString("Common", "ALERT_ERROR_TITLE_LABEL"),
								 ResourceManager.getInstance().getString("Common", "ALERT_ERROR_SERVER_RESPONSE_HEADER"),
								 ResourceManager.getInstance().getString("Common", "ALERT_MESSAGE_SESSIONTIMEOUT"),
								 Alert.OK,
								 onSessionInvalid);
					break;
				
				case BasicResult.SESSION_MAX_NUM_REACHED:
					//The maximum number of simultaneous sessions has been reached
					AbiCloudAlert.showError(ResourceManager.getInstance().getString("Common", "ALERT_ERROR_TITLE_LABEL"),
								 ResourceManager.getInstance().getString("Common", "ALERT_ERROR_SERVER_RESPONSE_HEADER"),
								 ResourceManager.getInstance().getString("Common", "ALERT_MESSAGE_SESSION_MAX_NUM"),
								 Alert.OK);
					break;
					
				case BasicResult.AUTHORIZATION_NEEDED:
					//Not used yet
					break;
					
				case BasicResult.NOT_AUTHORIZED:
					//Not used yet
					break;
				
				case BasicResult.VIRTUAL_IMAGE_IN_USE:
					AbiCloudAlert.showError(ResourceManager.getInstance().getString("Common", "ALERT_ERROR_TITLE_LABEL"),
								 ResourceManager.getInstance().getString("Common", "ALERT_ERROR_SERVER_RESPONSE_HEADER"),
								 ResourceManager.getInstance().getString("Common", "ALERT_MESSAGE_SESSION_VIRTUAL_IMAGE_IN_USE"),
								 Alert.OK);
					break;
					
				default:
					//Default response -> BasicResult.STANDARD_RESULT
					AbiCloudAlert.showError(ResourceManager.getInstance().getString("Common", "ALERT_ERROR_TITLE_LABEL"),
								 ResourceManager.getInstance().getString("Common", "ALERT_ERROR_SERVER_RESPONSE_HEADER"),
								 basicResult.message,
								 Alert.OK);
			}
			
		}
		
		
		//Same as function above, but in case of error, handles server's response using the background notification system
		public function handleResultInBackground(basicResult:BasicResult):void
		{
			if(! basicResult.success)
			{
				var notification:Notification = new Notification(ResourceManager.getInstance().getString("Common", "ALERT_ERROR_SERVER_RESPONSE_HEADER"),
																 basicResult.message);
				AbiCloudModel.getInstance().notificationManager.addNotification(notification);																 
			}
		}
		
		
		private function onSessionInvalid(closeEvent:CloseEvent):void
		{
			//Provisional logout
			navigateToURL(new URLRequest("javascript:location.reload(true)"), "_self");
		}
	}
}