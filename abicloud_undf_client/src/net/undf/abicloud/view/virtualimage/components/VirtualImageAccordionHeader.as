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

package net.undf.abicloud.view.virtualimage.components
{
	
	import flash.events.Event;
	
	import mx.containers.accordionClasses.AccordionHeader;
	import mx.events.DragEvent;
	import mx.managers.DragManager;
	import mx.utils.ObjectUtil;
	
	import net.undf.abicloud.events.VirtualImageEvent;
	import net.undf.abicloud.vo.virtualimage.VirtualImage;
	
	public class VirtualImageAccordionHeader  extends AccordionHeader
	{
		
			
		private var _acceptDrop:Boolean = false;
		
		public function VirtualImageAccordionHeader()
		{
			this.addEventListener(DragEvent.DRAG_ENTER, onDragEnter);
			this.addEventListener(DragEvent.DRAG_DROP, onDragDrop);
			this.addEventListener(DragEvent.DRAG_EXIT, onDragExit);
			this.addEventListener(Event.REMOVED, onRemove);
			
			super();
		}
		
		
		public function set acceptDrop(flag:Boolean):void
		{
			this._acceptDrop = flag;
		}
		
		private function onDragEnter(event:DragEvent):void
		{
			if(this._acceptDrop && event.dragSource.hasFormat("VirtualImage"))
			{
				drawFocus(true);
				
				//We only accept a drop if this CategoryRenderer does not already contain the Virtual Image that
				//the user wants to drop
				var virtualImage:VirtualImage = event.dragSource.dataForFormat("VirtualImage") as VirtualImage;
				
				if(!this.data.containsVirtualImage(virtualImage))
					DragManager.acceptDragDrop(this);
			}
		}
		
		private function onDragDrop(event:DragEvent):void
		{
			//Editing the Virtual Image being dragged with the new category
			//We do not modify the original one, until changes are made in server
			var virtualImage:VirtualImage = event.dragSource.dataForFormat("VirtualImage") as VirtualImage;
			var virtualImageCopy:VirtualImage = ObjectUtil.copy(virtualImage) as VirtualImage;
			virtualImageCopy.category = this.data.category;
			
			//Announcing that a VirtualImage has been edited
			var virtualImageEvent:VirtualImageEvent = new VirtualImageEvent(VirtualImageEvent.EDIT_VIRTUALIMAGE);
			virtualImageEvent.virtualImage = virtualImageCopy;
			virtualImageEvent.oldVirtualImage = virtualImage;
			dispatchEvent(virtualImageEvent);
			
			drawFocus(false);
		}
		
		private function onDragExit(event:DragEvent):void
		{
			drawFocus(false);
		}
		
		/**
		 * To properly destroy this component
		 */
		private function onRemove(event:Event):void
		{
			this.removeEventListener(DragEvent.DRAG_ENTER, onDragEnter);
			this.removeEventListener(DragEvent.DRAG_DROP, onDragDrop);
			this.removeEventListener(DragEvent.DRAG_EXIT, onDragExit);
			this.removeEventListener(Event.REMOVED, onRemove);
		}
	}
}