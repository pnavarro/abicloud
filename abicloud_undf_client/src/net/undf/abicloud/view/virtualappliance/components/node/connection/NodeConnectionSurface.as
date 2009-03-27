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
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.core.Application;
	
	import net.undf.abicloud.vo.virtualappliance.Node;
	
	public class NodeConnectionSurface extends Canvas
	{
		
		private var _connectionInProgress:Connection = null;
		
		private var _registeredConnectors:ArrayCollection;
		
		private var _currentConnections:ArrayCollection;
		
		protected var _nodeConnections:XML;
		
		public function NodeConnectionSurface()
		{
			super();
			
			//Variables initialization
			this._connectionInProgress = null;
			this._registeredConnectors = new ArrayCollection();
			this._currentConnections = new ArrayCollection();
			
			//Registering events for Connectors
			addEventListener(ConnectionEvent.REGISTER_CONNECTOR, onRegisterConnector);			
		}
		
		/**
		 * Parses the _nodeConnections XML document to draw all the connections
		 * 
		 * This functions assumes that all Connectors that appear in the document are
		 * already registered
		 * 
		 * This function is specific to draw Node connections, since it assumes that data value
		 * in each Connector is a NodeRenderer component
		 */
		protected function buildNodeConnections():void
		{
			var xmlList:XMLList = this._nodeConnections.child("connection");
			var length:int = xmlList.length();
			var i:int;
			var connectors:Array;
			for(i = 0; i < length; i++)
			{
				connectors = getConnectorByNodeID(xmlList[i].@nodeFromID, xmlList[i].@nodeToID);
				if(connectors && connectors.length == 2)
				{
					//Both Connectors have been found for the connection
					buildConnection(connectors[0] as Connector, connectors[1] as Connector);
				}
			}
		}
		
		/**
		 * Looks for two Connectors that are assigned to a
		 * two different Nodes.
		 * 
		 * Since the XML document _nodeConnections, only contains the node ID
		 * to describe a Connection between Nodes, we need to find which Connectors among
		 * the registered connectors are assigned to those Nodes, so we can build the 
		 * Connection
		 * 
		 * The Connector assigned to the nodeFromID will be returned in the first position of the array
		 * 
		 * This function returns null if one or both Connectors have not been found
		 */
		private function getConnectorByNodeID(nodeFromID:int, nodeToID:int):Array
		{
			var length:int = this._registeredConnectors.length;
			var node:Node;
			var i:int;
			var result:Array = new Array();
			for(i = 0; i < length; i++)
			{
				node = Connector(this._registeredConnectors.getItemAt(i)).data as Node;
				
				if(node.id == nodeFromID)
				{
					result.unshift(this._registeredConnectors.getItemAt(i));
				}
				else if(node.id == nodeToID)
				{
					result.push(this._registeredConnectors.getItemAt(i));
				}
					
				//If both connectors have been found, we can break
				if(result.length == 2)
					break;
			}
			
			if(result.length == 2)
				return result;
			else return null;
		}
		
		/**
		 * Given to Connectors, builds a Connection between them, and adds the connection to 
		 * the current connections list
		 *  
		 * @param connectorFrom
		 * @param connectorTo
		 * 
		 */		
		private function buildConnection(connectorFrom:Connector, connectorTo:Connector):void
		{
			var connection:Connection = new Connection(this, connectorFrom, connectorTo);
			
			//Register events that the connection can dispatch
			connection.addEventListener(ConnectionEvent.CONNECTION_DELETED, onConnectionDeleted);
			
			//Adding the Connection to the Connection Surface and to current connections list
			this._currentConnections.addItem(connection);
			rawChildren.addChildAt(connection, 1);
		}
		
		/**
		 * Cleans securely the NodeConnectionSurface from any Connector and Connection
		 * This function does not delete the connections. It just cleans this NodeConnectionSurface
		 */
		protected function cleanAll():void
		{
			//Cleaning connectors
			while(this._registeredConnectors.length > 0)
			{
				cleanConnector(this._registeredConnectors.getItemAt(0) as Connector);
			}
			
			//Cleaning connections
			while(this._currentConnections.length > 0)
			{
				cleanConnection(this._currentConnections.getItemAt(0) as Connection);
			}
		}
		
		private function cleanConnector(connector:Connector):void
		{
			connector.removeEventListener(ConnectionEvent.BEGIN_CONNECTION, onConnectorBeginConnection);
			connector.removeEventListener(ConnectionEvent.ACCEPT_CONNECTION, onConnectorAcceptConnection);
			connector.removeEventListener(ConnectionEvent.UNREGISTER_CONNECTOR, onUnregisterConnector);
			
			var index:int = this._registeredConnectors.getItemIndex(connector);
			if(index > -1)
				this._registeredConnectors.removeItemAt(index);
		}
		
		private function cleanConnection(connection:Connection):void
		{
			connection.cleanConnection();
			
			connection.removeEventListener(ConnectionEvent.CONNECTION_DELETED, onConnectionDeleted);
			
			rawChildren.removeChild(connection);
			var index:int = this._currentConnections.getItemIndex(connection);
			if(index > -1)
				this._currentConnections.removeItemAt(index);
		}
		
		//////////////////////////////////////
		//Event handlers
		
		private function onMouseUp(mouseEvent:MouseEvent):void
		{
			//Removing event listeners
			Application.application.removeEventListener(MouseEvent.MOUSE_UP, onMouseUp);
			
			//If no Connector has been selected, we cancel the current Connection in progress
			this._connectionInProgress.cancelConnection();
			this._connectionInProgress = null;
		}
		
		private function onRegisterConnector(connectionEvent:ConnectionEvent):void
		{
			var connectorToRegister:Connector = connectionEvent.connector;
			
			connectorToRegister.addEventListener(ConnectionEvent.BEGIN_CONNECTION, onConnectorBeginConnection);
			connectorToRegister.addEventListener(ConnectionEvent.ACCEPT_CONNECTION, onConnectorAcceptConnection);
			connectorToRegister.addEventListener(ConnectionEvent.UNREGISTER_CONNECTOR, onUnregisterConnector);
			
			this._registeredConnectors.addItem(connectorToRegister);
		}
		
		private function onUnregisterConnector(connectionEvent:ConnectionEvent):void
		{
			var connectorToUnregister:Connector = connectionEvent.connector;
			cleanConnector(connectorToUnregister);
			
			//Announcing that this Connector has been deleted
			var connectorDeleteEvent:ConnectionEvent = new ConnectionEvent(ConnectionEvent.CONNECTOR_DELETED);
			connectorDeleteEvent.connector = connectorToUnregister;
			dispatchEvent(connectorDeleteEvent);
		}
		
		private function onConnectorBeginConnection(connectionEvent:ConnectionEvent):void
		{
			var connector:Connector = connectionEvent.connector;
			
			if(! this._connectionInProgress)
			{
				//Beginning a new connection
				this._connectionInProgress = new Connection(this, connector);
				rawChildren.addChildAt(this._connectionInProgress, 1);
				
				//Registering events for Surface interaction, in case user does not select
				//a valid Connector while is creating the new Connection
				Application.application.addEventListener(MouseEvent.MOUSE_UP, onMouseUp);
			}
		}
		
		private function onConnectorAcceptConnection(connectionEvent:ConnectionEvent):void
		{
			//Removing mouse listeners
			Application.application.removeEventListener(MouseEvent.MOUSE_UP, onMouseUp);
			
			var connector:Connector = connectionEvent.connector;
			
			if(this._connectionInProgress)
			{
				if(this._connectionInProgress.connectorFrom != connector)
				{
					//Confirming the Connection
					this._connectionInProgress.confirmConnection(connector);
					
					//Adding the connection to the connections list
					this._currentConnections.addItem(this._connectionInProgress);
					
					//Registering events for this connection
					this._connectionInProgress.addEventListener(ConnectionEvent.CONNECTION_DELETED, onConnectionDeleted);
					
					//Adding the new connection to connectionsXML
					var newConnectionXML:String = "<connection nodeFromID=\"" + Node(this._connectionInProgress.connectorFrom.data).id + "\"" +
															  " nodeToID=\""   + Node(this._connectionInProgress.connectorTo.data).id + "\"/>";
				
					this._nodeConnections.appendChild(newConnectionXML);
				}
				else
				{
					//A Connection can not have the same Connector as start and end point
					this._connectionInProgress.cancelConnection();
					rawChildren.removeChild(this._connectionInProgress);
				}
				
				//This Connection is no longer in progress
				this._connectionInProgress = null;
			}
		}
		
		private function onConnectionDeleted(connectionEvent:ConnectionEvent):void
		{
			var connectionDeleted:Connection = connectionEvent.connection;
			
			//Remove the Connection from the nodeConnections XML
			var xmlList:XMLList = this._nodeConnections.child("connection");
			var length:int = xmlList.length();
			var i:int;
			
			for(i = 0; i < length; i++)
			{
				if(xmlList[i].@nodeFromID == Node(connectionDeleted.connectorFrom.data).id &&
				   xmlList[i].@nodeToID == Node(connectionDeleted.connectorTo.data).id)
				{
					delete this._nodeConnections.connection[i];
					break;
				}
			}														

			//Cleaning the Connection from the surface
			cleanConnection(connectionDeleted);
		}

	}
}