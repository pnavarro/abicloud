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
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.vo.infrastructure.State;
	import net.undf.abicloud.vo.user.Enterprise;
	
	/**
	 * This class represents a Virtual Appliance
	 **/
	
	[RemoteClass(alias="com.abiquo.abiserver.pojo.virtualappliance.VirtualAppliance")] 
	[Bindable]
	public class VirtualAppliance
	{
		
		/* ------------- Public atributes ------------- */
		public var id:int;
		public var name:String;
		public var isPublic:Boolean;
		public var state:State;
		public var highDisponibility:Boolean;
		
		//Array containing a list of nodes
		//It may be null, if no nodes exists or the nodes list has not been retrieved
		public var nodes:ArrayCollection;
		
		//XML Document containing the relations between nodes
		public var nodeConnections:String;
		
		//ArrayCollection containing the log entries list for this VirtualAppliance
		public var logs:ArrayCollection;
		
		//Flag that indicates if this Virtual Appliance had any error in the last operation
		public var error:Boolean;
		
		//The VirtualDataCenter to which this VirtualAppliance belongs
		public var virtualDataCenter:VirtualDataCenter;
		
		//The Enterprise to which this VirtualAppliance belongs
		//It may be null, if this VirtualAppliance is not assigned to any Enterprsie
		public var enterprise:Enterprise;
		
		/* ------------- Constructor ------------- */
		public function VirtualAppliance()
		{
			id = 0;
			name = "";
			isPublic = false;
			state = new State();
			highDisponibility = false;
			nodes = new ArrayCollection();
			nodeConnections = "<connections></connections>";
			logs = new ArrayCollection();
			error = false;
			virtualDataCenter = new VirtualDataCenter();
			enterprise = null;
		}

	}
}