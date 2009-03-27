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

CustomTree class, to display data in a Tree format.
	 
A CustomTree accepts any type of data in its customTreeDataProvider, since all data in the customTreeDataProvider will be
encapsulated in a CustomTreeNode, and displayed using a CustomTreeNodeRenderer.

An ITreeDataDescriptor describes how to build the Tree that represents the data present in the customTreeDataProvider

For Drag & Drop functionality, the CustomTreeNodeRenderers are the responsibles, using the ICustomTreeDataDescriptor.
Default Drag & Drop operations, inherited from the List component, are ignored

*/


package net.undf.abicloud.utils.customtree
{
	import mx.binding.utils.BindingUtils;
	import mx.collections.ArrayCollection;
	import mx.controls.List;
	import mx.core.ClassFactory;
	import mx.events.DragEvent;
	import mx.events.FlexEvent;
	import mx.events.ItemClickEvent;
	
	 
	public class CustomTree extends List
	{
		
		//The data provider for the inner List of this CustomTree. It will contain only CustomTreeNodes
		private var _innerListDataProvider:ArrayCollection;
		
		
		/**
		 * Constructor
		 **/
		public function CustomTree()
		{
			super();
			
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
			
		}
		
		
		/**
		 * Creation Complete event handler
		 **/
		private function creationCompleteHandler(flexEvent:FlexEvent):void
		{
			//Preparing the inner List
			super.itemRenderer = new ClassFactory(CustomTreeNodeRenderer);
			super.iconFunction = iconFunctionHandler;
			
			//Listening for changes in the data provider
			BindingUtils.bindSetter(buildTree, this, "customTreeDataProvider");
			
			//Listening for user interaction with CustomTree nodes
			addEventListener("customTreeNodeClick", customTreeNodeClickHandler);
		}
		
		
		/**
		 * The data descriptor for this CustomTree
		 **/
		private var _customTreeDataDescriptor:ICustomTreeDataDescriptor;
		public function set customTreeDataDescriptor(descriptor:ICustomTreeDataDescriptor):void
		{
			this._customTreeDataDescriptor = descriptor;
			
		}
		
		
		/**
		 * The data provider for this CustomTree. Must be an ArrayCollection
		 **/		
		private var _customTreeDataProvider:ArrayCollection;

		[Bindable]
		public function get customTreeDataProvider():Object
		{
			return this._customTreeDataProvider;
		}
		
		public function set customTreeDataProvider(arrayCollection:Object):void
		{
			//We only accept ArrayCollection as data provider
			if(arrayCollection is ArrayCollection)
				this._customTreeDataProvider = arrayCollection as ArrayCollection;
		}
		
		
		/**
		 * We override the setter dataProvider from the List component, to control external access to it
		 **/
		override public function set dataProvider(value:Object):void
		{
			if(value == this._innerListDataProvider)
				super.dataProvider = value;
		}
		
		
		/**
		 * We override the methods selectedItem and selectedItems from the inner List, to not return CustomTreeNode, but
		 * the item that it contains
		 **/
		override public function get selectedItem():Object
		{
			var listSelectedItem:Object = super.selectedItem;
			
			if(listSelectedItem != null)
			{
				return CustomTreeNode(listSelectedItem).item;
			}
			else
				return null;
		}
		
		override public function get selectedItems():Array
		{
			var listSelectedItems:Array = super.selectedItems;
			
			if(listSelectedItems.length > 0)
			{
				var i:int;
				var length:int = listSelectedItems.length;
				for(i = 0; i < length; i++)
				{
					listSelectedItems[i] = CustomTreeNode(listSelectedItems[i]).item;
				}
				
				return listSelectedItems;
			}
			else
				return listSelectedItems;
		}
		
		
		/**
		 * To properly draw the corresponding icon for each element of the inner List
		 **/
		[Embed("assets/infrastructure/branchClosed.png")]
		private var _iconBranchClosed:Class;
		
		[Embed("assets/infrastructure/branchOpened.png")]
		private var _iconBranchOpened:Class;
		
		[Embed("assets/infrastructure/physicalMachine_small.png")]
		private var _iconLeaf:Class;
		
		private function iconFunctionHandler(item:Object):Class
		{
			if(item is CustomTreeNode)
			{
				var customTreeNode:CustomTreeNode = item as CustomTreeNode;
				if(this._customTreeDataDescriptor.isBranch(customTreeNode.item))
				{
					if(customTreeNode.isBranchOpened)
						return _iconBranchOpened;
					else
						return _iconBranchClosed;
				}
				else
					return _iconLeaf;
			}
			
			return null;
		}
		
		/**
		 * Builds this CustomTree, using the inner List to draw the nodes
		 **/
		private function buildTree(arrayCollection:ArrayCollection):void
		{
			if(arrayCollection != null)
			{
				this._innerListDataProvider = new ArrayCollection();
				
				var length:int = arrayCollection.length;
				var i:int;
				var customTreeNode:CustomTreeNode;
				var object:Object;
				for(i = 0; i < length; i++)
				{
					object = arrayCollection.getItemAt(i);
					customTreeNode = new CustomTreeNode(object, object[super.labelField], this._customTreeDataDescriptor, false);
					this._innerListDataProvider.addItem(customTreeNode);
					
					//Check if this CustomTree is set to save the state and, if so, check if this branch was opened before
					if(this._saveState && this._openedBranches.contains(customTreeNode.item))
					{
						//Checking if this brach that was opened before, still contains any child
						var objects:ArrayCollection = this._customTreeDataDescriptor.getChildren(customTreeNode.item) as ArrayCollection;
						if(objects.length > 0)
						{
							customTreeNode.isBranchOpened = true;
							openBranch(customTreeNode);
						}
						else
						{
							//This branch no longer contains childs, so it is no necessary to open it
							var index:int = this._openedBranches.getItemIndex(customTreeNode.item);
							this._openedBranches.removeItemAt(index);
						}
					}
				}
				
				//Setting the inner List's dataProvider
				dataProvider = this._innerListDataProvider;
			}
		}
		
		/**
		 * Handler to control when user clicks in a CustomTreeNode
		 **/
		private function customTreeNodeClickHandler(itemClickEvent:ItemClickEvent):void
		{
			//Retrieving the CustomTreeNode that user has clicked
			var customTreeNodeClicked:CustomTreeNode = itemClickEvent.item as CustomTreeNode;
			
			if(this._customTreeDataDescriptor.isBranch(customTreeNodeClicked.item))
			{
				if(customTreeNodeClicked.isBranchOpened)
				{
					//If the branch is opened, we closed it and hide its children
					customTreeNodeClicked.isBranchOpened = false;
					closeBranch(customTreeNodeClicked);
							
					//Removing this branch from the list of opened branches
					if(this._saveState)
					{
						var itemIndex:int = this._openedBranches.getItemIndex(customTreeNodeClicked.item);
						this._openedBranches.removeItemAt(itemIndex);
					}
				}
				else
				{
					//If the branch is closed, we open it and show its children
					customTreeNodeClicked.isBranchOpened = true;
					openBranch(customTreeNodeClicked);
					
					//Saving the state of this branch
					if(this._saveState)
						this._openedBranches.addItem(customTreeNodeClicked.item);
				}
			}
		}
			
		/**
		 * Shows a opened branch's children in the inner list
		 */
		private function openBranch(branch:CustomTreeNode):void
		{
			//Getting the branch position
			var branchPosition:int = this._innerListDataProvider.getItemIndex(branch);
			
			//Getting the children items
			var objects:ArrayCollection = this._customTreeDataDescriptor.getChildren(branch.item) as ArrayCollection;
			
			//Building CustomTreeNodes for the children, and putting them in the right position in the inner List
			var object:Object;
			var customTreeNodeChildren:ArrayCollection = new ArrayCollection();
			var customTreeNodeChild:CustomTreeNode;
			var length:int = objects.length;
			var i:int;
			for(i = 0; i < length; i++)
			{
				object = objects.getItemAt(i);
				customTreeNodeChild = new CustomTreeNode(object, object[super.labelField], this._customTreeDataDescriptor, false);
				this._innerListDataProvider.addItemAt(customTreeNodeChild, branchPosition + 1 + i);
			}
		}
		
		private function closeBranch(branch:CustomTreeNode):void
		{
			//Getting the branch position
			var branchPosition:int = this._innerListDataProvider.getItemIndex(branch);
			
			//Getting the num of children that branch has
			var numChildren:int = ArrayCollection(this._customTreeDataDescriptor.getChildren(branch.item)).length;
			var i:int
			//Removing the children from the inner List's dataProvider
			for(i = 0; i < numChildren; i++)
			{
				this._innerListDataProvider.removeItemAt(branchPosition + 1);
			}			
		}
		
		
		/**
		 * Where the branches that are opened will be saved
		 * @private
		 */
		 private var _openedBranches:ArrayCollection = new ArrayCollection();
		
		/**
		 * This flag indicates that this CustomTree will save the branches state. If this
		 * CustomTree's data provider is refreshed, branches previously opened will remain opened.
		 * The CustomTreeNode.item property will be used to identify the branch, since new CustomTreeNodes
		 * are created when the data provider changes
		 */
		private var _saveState:Boolean = false;
		public function get saveState():Boolean
		{
			return this._saveState;
		}
		
		public function set saveState(value:Boolean):void
		{
			this._saveState = value;
		}
		
		public function cleanState():void
		{
			this._openedBranches = new ArrayCollection();
		}
		
		////////////////////////////////////////////////
		//DRAG & DROP Functionality
		
		//Drag & Drop operations, inherited from the List component, are overrided and ignored
		
		override protected function dragStartHandler(dragEvent:DragEvent):void {}
		
		override protected function dragCompleteHandler(event:DragEvent):void {}
		
		override protected function dragDropHandler(event:DragEvent):void {}
		
		override protected function dragEnterHandler(event:DragEvent):void {}
		
		override protected function dragExitHandler(event:DragEvent):void {}
		
		override protected function dragOverHandler(event:DragEvent):void {}
		
		override protected function dragScroll():void {}
	
	}
}