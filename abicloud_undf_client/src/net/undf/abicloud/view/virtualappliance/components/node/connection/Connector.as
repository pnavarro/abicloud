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
	import flash.events.EventDispatcher;
	import flash.geom.Point;
	
	public class Connector extends EventDispatcher
	{	
		public function Connector(position:Point, data:Object = null)
		{	
			this._position = position;
			this._data = data;
		}
		
		/**
		 * The position of a Connector
		 * 
		 * This value must be a valid position in the Connection Surface
		 */
		private var _position:Point;
		public function get position():Point
		{
			return this._position;
		}
		
		public function set position(value:Point):void
		{
			this._position = value;
			
			var connectionEvent:ConnectionEvent = new ConnectionEvent(ConnectionEvent.CONNECTOR_MOVED);
			connectionEvent.connector = this;
			dispatchEvent(connectionEvent);
		}
		
		/**
		 * The data associated to this Connector
		 */		
		private var _data:Object;
		public function get data():Object
		{
			return this._data;
		}
		
		public function set data(value:Object):void
		{
			this._data = value;
		}
	}
}