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

package net.undf.abicloud.view.virtualappliance.components.node
{
	import flash.events.Event;
	
	/**
	 * Event related to NodeRenderer operations
	 **/
	public class NodeRendererEvent extends Event
	{
		public static const NODERENDERER_ERASE:String = "nodeRendererErase_NodeRendererEvent";
		
		public static const NODERENDERER_ADDED:String = "nodeRendererAdded_NodeRendererEvent";
		public static const NODERENDERER_ERASED:String = "NodeRendererErased_NodeRendererEvent";
		public static const NODERENDERER_CHANGED:String = "nodeRendererMoved_NodeRendererEvent";
		public static const NODERENDERER_SELECTED:String = "nodeRendererSelected_NodeRendererEvent";
		public static const NODERENDERER_ANY_SELECTED:String = "nodeRendererAnySelected_NodeRendererEvent";
		
		public var nodeRenderer:NodeRenderer;
		
		public function NodeRendererEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}

	}
}