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

package net.undf.abicloud.vo.virtualappliance
{
	/**
	 * This class represents a Virtual Appliance's node
	 **/
	
	[RemoteClass(alias="com.abiquo.abiserver.pojo.virtualappliance.Node")]
	[Bindable]
	public class Node
	{
		
		/* ------------- Public constants ------------- */
		public static const NODE_NOT_MODIFIED:int = 0;
		public static const NODE_MODIFIED:int = 1;
		public static const NODE_ERASED:int = 2;
		public static const NODE_NEW:int = 3;
		
		/* ------------- Public atributes ------------- */
		public var id:int;
		public var name:String;
		public var idVirtualAppliance:int;
		public var nodeType:NodeType;
		
		//For drawing purposes
		public var posX:int;
		public var posY:int;
		
		//For performance purposes
		//To be set when a node has been modified, when we want to save changes on editing a virtual appliance
		public var modified:int;
		
		/* ------------- Constructor ------------- */
		public function Node()
		{
			id = 0;
			name = "";
			idVirtualAppliance = 0;
			nodeType = new NodeType();
			
			posX = 0;
			posY = 0;
			
			modified = NODE_NOT_MODIFIED;
		}

	}
}