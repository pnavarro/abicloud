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
	import mx.collections.ICollectionView;
	
	/**
	 * ICustomTreeDataDescriptor extends ITreeDataDescriptor adding a new method
	 **/
	public interface ICustomTreeDataDescriptor
	{
		
	    /**
	     *  Provides access to a node's children, returning a collection
	     *  view of children if they exist.
	     *  A node can return any object in the collection as its children;
	     *  children need not be nested.
	     *  It is best-practice to return the same collection view for a
	     *  given node.
	     *
	     *  @param node The node object currently being evaluated.
	     *
	     *  @param model The entire collection that this node is a part of.
	     *
	     *  @return An collection view containing the child nodes.
	     */
	    function getChildren(node:Object, model:Object = null):ICollectionView;
	
	    /**
	     *  Tests for the existence of children in a non-terminating node.
	     *  
	     *  @param node The current node.
	     *  
	     *  @param model The entire collection that this node is a part of.
	     *  
	     *  @return <code>true</code> if the node has at least one child.
	     */
	    function hasChildren(node:Object, model:Object = null):Boolean;
	
	    /**
	     *  Tests a node for termination.
	     *  Branches are non-terminating but are not required
	     *  to have any leaf nodes.
	     *
	     *  @param node The node object currently being evaluated.
	     *
	     *  @param model The entire collection that this node is a part of.
	     *
	     *  @return A Boolean indicating if this node is non-terminating.
	     */
	    function isBranch(node:Object, model:Object = null):Boolean;
		
		/**
		 * Test if a move operation is allowed
		 * @param parent the parent node where a child node will be moved
		 * @param newChild the child node being moved
		 * @return A Boolean indicating if the move operation is allowed (but the operation will actually not be performed) 
		 * 
		 */		
		function isMoveAllowed(parent:Object, newChild:Object):Boolean;
		
		/**
		 * Test if a copy operation is allowed
		 * @param parent the parent node where a child will be copied
		 * @param newChild the child node being copied
		 * @return A Boolean indicating if the copy operation is allowed (but the operation will actually not be performed)
		 * 
		 */		
		function isCopyAllowed(parent:Object, newChild:Object):Boolean;
		
	    /**
	     *  Copies a child node to a node at the specified index.
	     *
	     *  @param node The node object that will parent the child.
	     *
	     *  @param child The node object that will be parented by the node.
	     *
	     *  @param index The 0-based index of where to put the child node.
	     *
	     *  @param model The entire collection that this node is a part of.
	     *  
	     *  @return <code>true</code> if successful.
	     */
	    function copyChild(parent:Object, newChild:Object):Boolean;
		
		
		/**
	     *  Moves a child node to a node at the specified index.
	     *
	     *  @param node The node object that will parent the child.
	     *
	     *  @param child The node object that will be parented by the node.
	     *
	     *  @param index The 0-based index of where to put the child node.
	     *
	     *  @param model The entire collection that this node is a part of.
	     *  
	     *  @return <code>true</code> if successful.
	     */
		function moveChild(parent:Object, newChild:Object):Boolean;
		
		/**
		 * Gets the level of a given node inside the Tree
		 * 
		 * @param node The node object that we want to know in which level inside the Tree ist
		 * 
		 * @return The requested level
		 */
		function getNodeLevel(node:Object):int;
		
		
		/**
		 * Tests if a node can be dragged as part of a Drag & Drop operation
		 * 
		 * @param node The node to test
		 * 
		 * @return <code>true</code> if node can be dragged
		 */
		function isNodeDraggable(node:Object):Boolean;
		
	}
}