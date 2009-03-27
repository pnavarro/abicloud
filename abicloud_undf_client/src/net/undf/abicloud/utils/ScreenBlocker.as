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

package net.undf.abicloud.utils
{
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	
	import mx.core.Application;
	import mx.managers.CursorManager;

	
	/**
	 * Blocks the screen, capturing all KeyBoard and MouseEvent in the Application,
	 * stopping their propagation
	 * 
	 * @author Oliver
	 * @
	 * 
	 */	
	public class ScreenBlocker
	{
		
		private static var numBlocks:int = 0;
		
		/**
		 * Blocks screen, if it has not been blocked yet 
		 * 
		 */		
		public static function blockScreen():void
		{
			numBlocks++;
			
			if(numBlocks == 1)
			{
				//Showing busy cursor
				CursorManager.setBusyCursor();
				
				//Block mouse interaction
				Application.application.systemManager.addEventListener(MouseEvent.CLICK, mouseHandler, true);
				Application.application.systemManager.addEventListener(MouseEvent.DOUBLE_CLICK, mouseHandler, true);
				Application.application.systemManager.addEventListener(MouseEvent.MOUSE_DOWN, mouseHandler, true);
				Application.application.systemManager.addEventListener(MouseEvent.MOUSE_UP, mouseHandler, true);
				Application.application.systemManager.addEventListener(MouseEvent.MOUSE_OVER, mouseHandler, true);
				Application.application.systemManager.addEventListener(MouseEvent.MOUSE_OUT, mouseHandler, true);
				Application.application.systemManager.addEventListener(MouseEvent.MOUSE_WHEEL, mouseHandler, true);
				
				//We block keyboard interaction too
				Application.application.systemManager.addEventListener(KeyboardEvent.KEY_DOWN, keyboardHandler, true);
				Application.application.systemManager.addEventListener(KeyboardEvent.KEY_UP, keyboardHandler, true);
			}
		}
		
		private static function keyboardHandler(keyboardEvent:KeyboardEvent):void
		{
			keyboardEvent.stopImmediatePropagation();
			keyboardEvent.stopPropagation();
		}
		
		private static function mouseHandler(mouseEvent:MouseEvent):void
		{
			mouseEvent.stopImmediatePropagation();
			mouseEvent.stopPropagation();
		}
		
		/**
		 * Unblocks screen if it is blocked
		 * 
		 */		
		public static function unblockScreen():void
		{
			if(numBlocks > 0)
			{
				numBlocks--;
				if(numBlocks == 0)
				{
					//Removing mouse blocking
					Application.application.systemManager.removeEventListener(MouseEvent.CLICK, mouseHandler, true);
					Application.application.systemManager.removeEventListener(MouseEvent.DOUBLE_CLICK, mouseHandler, true);
					Application.application.systemManager.removeEventListener(MouseEvent.MOUSE_DOWN, mouseHandler, true);
					Application.application.systemManager.removeEventListener(MouseEvent.MOUSE_UP, mouseHandler, true);
					Application.application.systemManager.removeEventListener(MouseEvent.MOUSE_OVER, mouseHandler, true);
					Application.application.systemManager.removeEventListener(MouseEvent.MOUSE_OUT, mouseHandler, true);
					Application.application.systemManager.removeEventListener(MouseEvent.MOUSE_WHEEL, mouseHandler, true);
					
					//Removing keyboard blocking too
					Application.application.systemManager.removeEventListener(KeyboardEvent.KEY_DOWN, keyboardHandler, true);
					Application.application.systemManager.removeEventListener(KeyboardEvent.KEY_UP, keyboardHandler, true);
					
					//Removing busy cursor
					CursorManager.removeBusyCursor();
				}
			}
		}
	}
}