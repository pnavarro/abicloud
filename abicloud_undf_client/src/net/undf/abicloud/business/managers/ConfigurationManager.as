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
	import flash.events.IOErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.controls.Alert;
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	import mx.messaging.channels.SecureAMFChannel;
	
	[Bindable]
	public class ConfigurationManager extends EventDispatcher
	{
		
		
		public function ConfigurationManager()
		{
			this._config = new Object();
			loadXMLConfigFile();
		}
		
		/**
		 * Loads the client-config.xml file, containing all the parameters
		 * for the application, and stores the parameters in _config associative array
		 * for further access 
		 */		
		private function loadXMLConfigFile():void
		{
			var loader:URLLoader = new URLLoader();
			loader.addEventListener(Event.COMPLETE, loadXMLConfigFileCompleteHandler);
			loader.addEventListener(IOErrorEvent.IO_ERROR, loadXMLConfigFileIOErrorHandler);
			loader.load(new URLRequest("config/client-config.xml"));
		}
		
		/**
		 * Handler when the xml file finishes to load
		 */
		private function loadXMLConfigFileCompleteHandler(event:Event):void
		{
			var xmlFile:XML = XML(URLLoader(event.currentTarget).data);
			var xmlList:XMLList = xmlFile.child("param");
			
			var length:int = xmlList.length();
			var i:int;
			for(i = 0; i < length; i++)
			{
				this._config[xmlList[i].name] = xmlList[i].value;
			}
			
			//Once the xml file is properly loaded, we can load specific configuration
			loadChannelSet();
		}
		
		/**
		 * Handler when it fails to load the xml file
		 */
		private function loadXMLConfigFileIOErrorHandler(ioErrorEvent:IOErrorEvent):void
		{
			Alert.show("Unable to load client-config.xml. The application will not start correctly",
					   "Error");
		}
		
		
		private function loadChannelSet():void
		{
			var channelList:XMLList = this._config.channels.channels.channel;
			
			var length:int = channelList.length();
			var i:int;
			this._channelSet = new ChannelSet();
			this._secureChannelSet = new ChannelSet();
			for(i = 0; i < length; i++)
			{
				//Checking the channel type
				if(channelList[i].type == "amf")
				{
					//Creating the AMF channel and adding it to the Application's Channel Set
					var amfChannel:AMFChannel = new AMFChannel(channelList[i].id, channelList[i].endpoint);
					this._channelSet.addChannel(amfChannel);
				}
				else if(channelList[i].type == "amfsecure")
				{
					//Creating the Secure AMF Channel and adding it to the Application's Channel Set
					var secureAmfChannel:SecureAMFChannel = new SecureAMFChannel(channelList[i].id, channelList[i].endpoint);
					this._secureChannelSet.addChannel(secureAmfChannel);
				}
			}
			
			dispatchEvent(new Event("channelSetUpdated"));
		}
		
		/**
		 * Returns an associative array contaning key - value pairs
		 * where a key is the name of a param node in client-config.xml
		 * and value is the value of a param node in the same file
		 * 
		 * @return the associative array containing all parameters for the application to run
		 */	
		private var _config:Object;
		public function get config():Object
		{
			return this._config;
		}
		
		/**
		 * The ChannelSet with the channels available to the application
		 */
		private var _channelSet:ChannelSet;
		[Bindable(event="channelSetUpdated")]
		public function get channelSet():ChannelSet
		{
			return this._channelSet;
		}
		
		private var _secureChannelSet:ChannelSet;
		[Bindable(event="channelSetUpdated")]
		public function get secureChannelSet():ChannelSet
		{
			return this._secureChannelSet;
		}
	}
}