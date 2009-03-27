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

package net.undf.abicloud.utils.customtree
{
	import flash.events.MouseEvent;
	
	import mx.controls.listClasses.ListData;
	import mx.controls.listClasses.ListItemRenderer;
	import mx.core.DragSource;
	import mx.core.IFlexDisplayObject;
	import mx.events.DragEvent;
	import mx.events.ItemClickEvent;
	import mx.managers.DragManager;
	
	//Dispatched when user clicks on a CustomTreeNode
	[Event(name="customTreeNodeClick", type="mx.events.ItemClickEvent")]
	
	public class CustomTreeNodeRenderer extends ListItemRenderer
	{
		public function CustomTreeNodeRenderer()
		{
			super();
			
			this.addEventListener(MouseEvent.CLICK, clickHandler);
			this.addEventListener(MouseEvent.MOUSE_DOWN, mouseDownHandler);
			this.addEventListener(DragEvent.DRAG_ENTER, dragEnterHandler);
			this.addEventListener(DragEvent.DRAG_DROP, dragDropHandler);
		}
	
		
		//The CustomTreeNode that is drawing this CustomTreeNodeRenderer
		private var _customTreeNode:CustomTreeNode;
			
		override public function set data(object:Object):void
		{
			if(object is CustomTreeNode)
			{	
				super.data = object;
				this._customTreeNode = object as CustomTreeNode;
				
				//If the this is a Branch, we add interaction so user can open and close the branch
				if(this._customTreeNode.customTreeDataDescriptor.isBranch(this._customTreeNode.item))
				{
					//this.useHandCursor = true;
					//this.buttonMode = true;
				}
				else
				{
					//this.useHandCursor = false;
					//this.buttonMode = false;
				}
			}
		}
		
		
		
		/**
		 * To draw our CustomTreeNode properly
		 **/
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			if(this._customTreeNode != null)
			{
				//Setting the proper Label text
				super.label.text = this._customTreeNode.labelText;
				
				//Displacing 10px or more to the right depending on the level inside the Tree
				super.icon.x = super.icon.x + 10 * this._customTreeNode.customTreeDataDescriptor.getNodeLevel(this._customTreeNode.item);
				super.label.x = super.label.x + 10 * this._customTreeNode.customTreeDataDescriptor.getNodeLevel(this._customTreeNode.item);
				
			}
		}

		private function clickHandler(mouseEvent:MouseEvent):void
		{
			//Check if the user has clicked in the icon
			if(mouseEvent.target is CustomTreeNodeRenderer)
			{
				var itemClickEvent:ItemClickEvent = new ItemClickEvent("customTreeNodeClick", true);
				itemClickEvent.item = this._customTreeNode;
				dispatchEvent(itemClickEvent);
			}
			
			//There was no Drag operation. We can remove this handler
			this.removeEventListener(MouseEvent.MOUSE_MOVE, mouseMoveHandler);
		}
		
		/**
		 * Handler to detect when user wants to initate a drag operation
		 */
		private function mouseDownHandler(mouseEvent:MouseEvent):void
		{
			this.addEventListener(MouseEvent.MOUSE_MOVE, mouseMoveHandler);
		}
		
		private function mouseMoveHandler(mouseEvent:MouseEvent):void
		{
			//We don't need this handler until user initiates another Drag operation
			this.removeEventListener(MouseEvent.MOUSE_MOVE, mouseMoveHandler);
			
			if(this._customTreeNode.customTreeDataDescriptor.isNodeDraggable(this._customTreeNode.item))
			{
				var dragSource:DragSource = new DragSource();
				dragSource.addData(this._customTreeNode, "CustomTreeNode");
				
				var iconClass:Class = ListData(super.listData).icon;
				var dragProxy:IFlexDisplayObject = new iconClass();
				
				DragManager.doDrag(this, dragSource, mouseEvent, dragProxy, -mouseEvent.localX, -mouseEvent.localY, 0.9);
			}
		}
		
		private function dragEnterHandler(dragEvent:DragEvent):void
		{
			//Check that the Data being dropped is valid
			if(dragEvent.dragSource.hasFormat("CustomTreeNode"))
			{
				var customTreeNodeDragged:CustomTreeNode = dragEvent.dragSource.dataForFormat("CustomTreeNode") as CustomTreeNode;
				
				//Check if this Node can accept drops
				if(dragEvent.altKey)
				{
					//Copy operation
					if(this._customTreeNode.customTreeDataDescriptor.isCopyAllowed(this._customTreeNode.item, customTreeNodeDragged.item))
					{
						DragManager.acceptDragDrop(this);
						DragManager.showFeedback(DragManager.COPY);
					}
				}
				
				else if(this._customTreeNode.customTreeDataDescriptor.isMoveAllowed(this._customTreeNode.item, customTreeNodeDragged.item))
				{
					//Move operation
					DragManager.acceptDragDrop(this);
					DragManager.showFeedback(DragManager.MOVE);
				}
			}			
		}
		
		private function dragDropHandler(dragEvent:DragEvent):void
		{
			if(dragEvent.dragSource.hasFormat("CustomTreeNode"))
			{
				var customTreeNodeDragged:CustomTreeNode = dragEvent.dragSource.dataForFormat("CustomTreeNode") as CustomTreeNode;
				
				if(dragEvent.altKey)
					this._customTreeNode.customTreeDataDescriptor.copyChild(this._customTreeNode.item, customTreeNodeDragged.item);
				else
					this._customTreeNode.customTreeDataDescriptor.moveChild(this._customTreeNode.item, customTreeNodeDragged.item);
			}
		}
	}
}