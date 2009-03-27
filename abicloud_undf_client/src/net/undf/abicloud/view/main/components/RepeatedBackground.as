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

/*
    RepeatedBackground
    
    Repeated Background.....just like that!
    
    Created by Maikel Sibbald
    info@flexcoders.nl
    http://labs.flexcoders.nl
    
    Free to use.... just give me some credit
*/

package net.undf.abicloud.view.main.components
{
    import flash.display.Bitmap;
    import flash.display.BitmapData;
    import flash.display.Graphics;
    import flash.display.Loader;
    import flash.events.Event;
    import flash.events.IOErrorEvent;
    import flash.geom.Matrix;
    import flash.net.URLRequest;
    
    import mx.controls.Image;
    import mx.core.BitmapAsset;
    import mx.graphics.RectangularDropShadow;
    import mx.skins.RectangularBorder;

    public class RepeatedBackground extends RectangularBorder{
		private var tile:BitmapData;
		
		[Embed(source="/assets/general/main_background.png")]
        public var imgCls:Class;

		
		public function RepeatedBackground():void{
			var background:BitmapAsset = BitmapAsset(new imgCls());
            this.tile =  background.bitmapData;
		}
		
        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
            var transform: Matrix = new Matrix();

            // Finally, copy the resulting bitmap into our own graphic context.
            graphics.clear();
            graphics.beginBitmapFill(this.tile, transform, true);
            graphics.drawRect(0, 0, unscaledWidth, unscaledHeight);
        }
    }  
}