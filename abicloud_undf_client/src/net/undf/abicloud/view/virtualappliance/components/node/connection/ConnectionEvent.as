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

package net.undf.abicloud.view.virtualappliance.components.node.connection
{
	import flash.events.Event;
	
	public class ConnectionEvent extends Event
	{
		
		public static const REGISTER_CONNECTOR:String = "registerConnectorConnectionEvent";
		public static const UNREGISTER_CONNECTOR:String = "unregisterConnectorConnectionEvent";
		
		public static const CONNECTOR_DELETED:String = "connectorDeletedConnectionEvent";
		public static const CONNECTOR_MOVED:String = "connectorMovedConnectionEvent";
		
		public static const BEGIN_CONNECTION:String = "beginConnectionConnectionEvent";
		public static const ACCEPT_CONNECTION:String = "acceptConnectionConnectionEvent"
		
		public static const CONNECTION_DELETED:String = "connectionDeletedConnectionEvent";
		
		public var connector:Connector;
		public var connection:Connection;
		
		public function ConnectionEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}

	}
}