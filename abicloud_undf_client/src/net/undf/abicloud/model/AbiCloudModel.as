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

package net.undf.abicloud.model
{
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.business.managers.*;
	import net.undf.abicloud.business.managers.notification.NotificationManager;
	
	/**
	 * Class representing AbiCloud's Model.
	 * It is a Singleton class, since only one instance of the application can exist
	 * 
	 * AbiCloud's view can find here methods to access data and represent it.
	 **/
	[Bindable]
	public class AbiCloudModel
	{
		
		/* ------------- Private atributes ------------- */
		//Unique instance of this class 
		private static var instance:AbiCloudModel;
		
		/* ------------- Constructor ------------- */
		//Since this class implements the Singleton pattern, we define a private constructor
		public function AbiCloudModel(access:Private)
		{
			if(access == null)
				throw Error("Unable to access the constructor of a Singleton class");
			else
			{	
				operatingSystems = new ArrayCollection();
				
				configurationManager = new ConfigurationManager();
				countdownManager = new CountdownManager();
				notificationManager = new NotificationManager();
				loginManager = new LoginManager();
				authorizationManager = new AuthorizationManager();
				infrastructureManager = new InfrastructureManager();
				userManager = new UserManager();
				virtualApplianceManager = new VirtualApplianceManager();
				virtualImageManager = new VirtualImageManager();
			}
		}
		
		public static function getInstance():AbiCloudModel
		{
			if(instance == null)
				instance = new AbiCloudModel( new Private());
				
			return instance;	
		}
	

		/* ----------------- ABICLOUD'S MODEL ------------------- */
		
		//ArrayCollection with all Operating Systems available
		public var operatingSystems:ArrayCollection;
		
		//Managers. These managers store data, and define methods to manipulate it
			
			//Application configuration
			public var configurationManager:ConfigurationManager;
			
			//Countdown manager
			public var countdownManager:CountdownManager;
			
			//Notification manager
			public var notificationManager:NotificationManager;
			
			//Login
			public var loginManager:LoginManager;
			
			//Authorization
			public var authorizationManager:AuthorizationManager;
			
			//Infrastructure
			public var infrastructureManager:InfrastructureManager;
			
			//User
			public var userManager:UserManager;
			
			//Virtual Appliance
			public var virtualApplianceManager:VirtualApplianceManager;
			
			//Virtual Image
			public var virtualImageManager:VirtualImageManager;
			
		//Others
		public static const GB_TO_BYTES:Number = 1073741824;
		public static const MB_TO_BYTES:Number = 1048576;
		public static const KB_TO_BYTES:Number = 1024;
	}
}

/**
 * Inner class which restricts constructor access to Private
 */
class Private {}