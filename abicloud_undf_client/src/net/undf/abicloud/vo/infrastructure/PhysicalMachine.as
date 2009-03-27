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

package net.undf.abicloud.vo.infrastructure
{
	import mx.collections.ArrayCollection;
	
	
	[Bindable]
	[RemoteClass(alias="com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine")]
	public class PhysicalMachine extends InfrastructureElement
	{
		
		/* ------------- Public atributes ------------- */ 
		public var dataCenter:DataCenter;
		public var description:String;
		public var ram:int;
		public var cpu:int;
		public var hd:Number;
		public var hostSO:SO;
		public var networkModuleList:ArrayCollection;
		public var ramUsed:int;
		public var cpuUsed:int;
		public var hdUsed:Number;
		
		/* ------------- Constructor ------------- */		
		public function PhysicalMachine()
		{
			super();
			
			dataCenter = new DataCenter();
			description = "";
			ram = 0;
			cpu = 0;
			hd = 0;
			hostSO = new SO();
			networkModuleList = new ArrayCollection();
			ramUsed = 0;
			cpuUsed = 0;
			hdUsed = 0;
		}
		
		override public function set assignedTo(iE:InfrastructureElement):void
		{
			if(iE is Rack || iE == null)
				_assignedTo = iE;
			else
				throw Error("A physical machine can only be assigned to a rack");
		}
	}
}