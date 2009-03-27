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

package assets
{
	import flash.display.Loader;
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.ProgressEvent;
	import flash.net.URLRequest;
	import flash.text.TextField;
	
	import mx.events.FlexEvent;
	import mx.preloaders.DownloadProgressBar;
	import mx.preloaders.IPreloaderDisplay;
	
	public class CustomPreloader extends DownloadProgressBar implements IPreloaderDisplay
	{
		private var loadBar:Shape;
		private var dpbImageControl:Loader;
		
		private var _barTotalWidth:Number = 187;
		private var _barLoaded:Number = 0;
		private var _barHeight:Number = 4;
		
		public function CustomPreloader()
		{
			super();
		}
		
		protected function draw():void
		{
			
		}
		
		override public function set preloader(preloader:Sprite):void 
		{
			// Listening for relevant events
			preloader.addEventListener( ProgressEvent.PROGRESS, handleProgress );
			preloader.addEventListener( Event.COMPLETE, handleComplete );
			preloader.addEventListener( FlexEvent.INIT_PROGRESS, handleInitProgress );
			preloader.addEventListener( FlexEvent.INIT_COMPLETE, handleInitComplete );
		}
		
		//Initialize Loader control 
		override public function initialize():void 
		{	
			loadBar = new Shape();
			dpbImageControl = new Loader();
			
			dpbImageControl.contentLoaderInfo.addEventListener( Event.COMPLETE, loader_completeHandler );
			dpbImageControl.load(new URLRequest("assets/CustomPreloaderLogo.png"));
			
		}
		
		// Once the SWF is loaded
		private function loader_completeHandler(event:Event):void 
		{	
			addChild( dpbImageControl );
			dpbImageControl.x = this.stage.stageWidth/2 - (dpbImageControl.width / 2);
			dpbImageControl.y = this.stage.stageHeight/2 - (dpbImageControl.height);
			
			addChild( loadBar );
			loadBar.scaleX = 3;
			loadBar.x = this.stage.stageWidth/2 - (_barTotalWidth * 3) / 2;
			loadBar.y = this.stage.stageHeight / 2;
		}
		
		// On bar progress
		private function handleProgress(event:ProgressEvent):void 
		{
			var relacion:Number = event.bytesLoaded / event.bytesTotal;
			
			_barLoaded = relacion * _barTotalWidth;
			loadBar.graphics.clear();
			loadBar.graphics.beginFill( 0xFFD200 );
			loadBar.graphics.drawRoundRect( 0,0,_barLoaded,_barHeight,4,20 );
			loadBar.graphics.endFill();
		}
		
		private function handleComplete(event:Event):void 
		{
		}
		private function handleInitProgress(event:Event):void 
		{
		}
		
		private function handleInitComplete(event:Event):void 
		{
			dispatchEvent(new Event(Event.COMPLETE));
		}
	}
}