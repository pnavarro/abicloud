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

package net.undf.abicloud.view.virtualappliance.components.virtualappliance
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.managers.CursorManager;
	
	/**
	 * This class manages all tool available in VirtualApplianceDrawTool
	 *  
	 * @author Oliver
	 * 
	 */	
	
	public class DrawToolsManager extends EventDispatcher
	{
		/* ------------- Private atributes ------------- */
		//Unique instance of this class 
		private static var instance:DrawToolsManager;
		
		private var _currentTool:int;
		
		public function DrawToolsManager(access:Private)
		{
			if(access == null)
				throw Error("Unable to access the constructor of a Singleton class");
			else
			{
				this._currentTool = NO_TOOL;
			}
		}
		
		public static function getInstance():DrawToolsManager
		{
			if(instance == null)
				instance = new DrawToolsManager( new Private());
				
			return instance;	
		}
		
		
		/////////////////////////////////
		//TOOL CURSORS (SELECTION_TOOL uses the default system cursor)
		[Embed(source="assets/cursors/LineTool.png")]
		private var LineToolCursor:Class;
		
		[Embed(source="assets/cursors/MoveTool.png")]
		private var MoveToolCursor:Class;
		
		[Embed(source="assets/cursors/Scissors.png")]
		private var ScissorsToolCursor:Class;
		
		/////////////////////////////////
		//AVAILABLE TOOLS
		public static const NO_TOOL:int = -1;
		public static const SELECTION_TOOL:int = 0;
		public static const DRAW_CONNECTION_TOOL:int = 1;
		public static const MOVE_NODE_TOOL:int = 2;
		public static const SCISSORS_TOOL:int = 3;
		
		
		/* ------------- Public methods ------------- */
		[Bindable(event="currentToolChange")]
		public function get currentTool():int
		{
			return this._currentTool;
		}
		
		public function set currentTool(value:int):void
		{
			this._currentTool = value;
			
			dispatchEvent(new Event("currentToolChange"));
		}
		
		/**
		 * Sets a visible mouse cursor for a given tool
		 * The visibility of the mouse cursor is totally independent from the current
		 * selected tool. This means that although a tool is currently selected, its mouse cursor
		 * should  not be showed until that tool can be used.
		 * 
		 * Changing the mouse cursor never changes the currentTool value, and changing the currenTool
		 * neither changes the current cursor.
		 * 
		 * @param tool The tool to show its cursor 
		 * 
		 */		
		public function setToolCursor(tool:int):void
		{
			CursorManager.removeAllCursors();
			switch(tool)
			{
				case NO_TOOL:
					//When NO_TOOL is given, the default system cursor is shown
					break;
					
				case SELECTION_TOOL:
					//Selection tool uses the default system cursor
					break;
					
				case DRAW_CONNECTION_TOOL:
					CursorManager.setCursor(LineToolCursor);
					break;
					
				case MOVE_NODE_TOOL:
					CursorManager.setCursor(MoveToolCursor);
					break;
					
				case SCISSORS_TOOL:
					CursorManager.setCursor(ScissorsToolCursor);
					break;
			}
		}
	}
}

/**
 * Inner class which restricts constructor access to Private
 */
class Private {}