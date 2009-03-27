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
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	
	import mx.controls.Image;
	
	/**
	 * This class extends from Image
	 * If it is unable to load the image set in source argument, or with method load(), it will
	 * load the image found in defaultImagePath property
	 *  
	 * @author Oliver
	 * 
	 */	
	
	public class ImageDefault extends Image
	{
		/**
		 *@private 
		 */		
		private var _defaultImagePath:String = "";
		
		[Bindable(event="defaultImagePathChanged")]
		
		/**
		 * Path to the image that will be loaded when a load attempt fails
		 * @default null
		 */
		
		public function get defaultImagePath():String
		{
			return this._defaultImagePath;
		}
				
		public function set defaultImagePath(value:String):void
		{
			this._defaultImagePath = value;
			
			dispatchEvent(new Event("defaultImagePathChanged"));
		}
		
		private var _loadSuccess:Boolean = true;
		
				
		[Bindable(event="loadSuccessChanged")]
		
		/**
		 * Boolean indicating if the last attempt of load an image had success or not 
		 * @default true
		 */		
		public function get loadSuccess():Boolean
		{
			return this._loadSuccess;
		}
		
		/**
		 * Constructor 
		 * Uses default Image class constructor, and adds listeners to be able to check when the load attempt
		 * is successful
		 */		
		public function ImageDefault() 
		{
			super();
			
			addEventListener(IOErrorEvent.IO_ERROR, loadImageError_handler);	
			addEventListener(Event.COMPLETE, loadImageComplete_handler);
		}
		
		/**
		 * Load error handler 
		 * @param ioErrorEvent
		 * @private
		 */		
		private function loadImageError_handler(ioErrorEvent:IOErrorEvent):void
		{
			this._loadSuccess = false;
			this.source = this._defaultImagePath;
			
			dispatchEvent(new Event("loadSuccessChanged"));
		}
		
		/**
		 * Load success handler 
		 * @param event
		 * @private
		 */		
		private function loadImageComplete_handler(event:Event):void
		{
			this._loadSuccess = true;
			
			dispatchEvent(new Event("loadSuccessChanged"));
		}
	}
}