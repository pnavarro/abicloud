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
	import flash.display.DisplayObjectContainer;
	import flash.display.StageDisplayState;
	import flash.events.FullScreenEvent;
	
	import mx.containers.Box;
	import mx.core.Application;
	import mx.core.UIComponent;
	
	public class FullScreenManager
	{
		/* ------------- Private atributes ------------- */
		//Unique instance of this class 
		private static var instance:FullScreenManager;
		
		private var _fullScreenContainer:Box;
		
		private var _originalParent:DisplayObjectContainer;
		
		private var _component:UIComponent;
		private var _originaComponentPercentWidth:Number;
		private var _originalComponentPercentHeight:Number;
		
		public function FullScreenManager(access:Private)
		{
			if(access == null)
				throw Error("Unable to access the constructor of a Singleton class");
			else
			{
				this._fullScreenContainer = new Box();
				this._fullScreenContainer.percentWidth = 100;
				this._fullScreenContainer.percentHeight = 100;
			}
		}
		
		public static function getInstance():FullScreenManager
		{
			if(instance == null)
				instance = new FullScreenManager( new Private());
				
			return instance;	
		}
		
		private function fullScreenHandler(fullScreenEvent:FullScreenEvent):void
		{
			if(fullScreenEvent.fullScreen)
			{
				//Placing the component in the full screen container
                this._fullScreenContainer.addChild(this._component);
          		
          		//Saving the component properties that will be changed
          		this._originaComponentPercentWidth = this._component.percentWidth;
          		this._originalComponentPercentHeight = this._component.percentHeight;
          		
                //Setting the component to fill the whole screen
                this._component.percentWidth = 100;
                this._component.percentHeight = 100;
			}
			else
			{
				//Returning the _component to its original parent
                this._originalParent.addChild(this._component);
                
                //Setting the original properties for the component
                this._component.percentWidth = this._originaComponentPercentWidth;
                this._component.percentHeight = this._originalComponentPercentHeight;
                
                //Unregistering the full screen events
                Application.application.stage.removeEventListener(FullScreenEvent.FULL_SCREEN, fullScreenHandler);
			}
		}
		
		public function get fullScreenContainer():UIComponent
		{
			return this._fullScreenContainer;
		}
		
		public function makeFullScreen(component:UIComponent):void
		{
			this._component = component;
			
			this._originalParent = component.parent;
				
            try 
            {
            	//Registering full screen events
				Application.application.stage.addEventListener(FullScreenEvent.FULL_SCREEN, fullScreenHandler);
				
				//Calling for launch full screen
            	Application.application.stage.displayState = StageDisplayState.FULL_SCREEN;
            } 
            catch (err:SecurityError) 
            {
                //Unregistering the full screen events
                Application.application.stage.removeEventListener(FullScreenEvent.FULL_SCREEN, fullScreenHandler);
            }
		}
	}
}

/**
 * Inner class which restricts constructor access to Private
 */
class Private {}