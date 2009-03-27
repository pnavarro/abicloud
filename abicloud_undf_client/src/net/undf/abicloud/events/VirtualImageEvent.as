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

package net.undf.abicloud.events
{
	import flash.events.Event;
	
	import net.undf.abicloud.vo.virtualimage.Category;
	import net.undf.abicloud.vo.virtualimage.Icon;
	import net.undf.abicloud.vo.virtualimage.Repository;
	import net.undf.abicloud.vo.virtualimage.VirtualImage;
	
	public class VirtualImageEvent extends Event
	{
		
		/* ------------- Constants------------- */
		public static const GET_VIRTUALIMAGES_BY_USER:String = "getVirtualImagesByUserVirtualImageEvent";
		public static const CREATE_VIRTUAL_IMAGE:String = "createVirtualImageVirtualImageEvent";
		public static const DELETE_VIRTUALIMAGE:String = "deleteVirtualImageVirtualImageEvent";
		public static const VIRTUAL_IMAGE_SELECTED_TO_EDIT:String = "virtualImageSelectedToEditVirtualImageEvent";
		public static const EDIT_VIRTUALIMAGE:String = "editVirtualImageVirtualImageEvent";
		public static const VIRTUAL_IMAGE_EDITED:String = "virtualImageEditedVirtualImageEvent";
		
		public static const GET_VIRTUALIMAGES_INFORMATION:String = "getVirtualImagesInformationVirtualImageEvent";
		
		public static const CREATE_REPOSITORY:String = "createRepositoryVirtualImageEvent";
		public static const EDIT_REPOSITORY:String = "editRepositoryVirtualImageEvent";
		public static const DELETE_REPOSITORY:String = "deleteRepositoryVirtualImageEvent";
		public static const REPOSITORY_EDITED:String = "repositoryEditedVirtualImageEvent";
		
		public static const CREATE_CATEGORY:String = "createCategoryVirtualImageEvent";
		public static const DELETE_CATEGORY:String = "deleteCategoryVirtualImageEvent";
		
		public static const CREATE_ICON:String = "createIconVirtualImageEvent";
		public static const DELETE_ICON:String = "deleteIconVirtualImageEvent";
		public static const EDIT_ICON:String = "editIconVirtualImageEvent";
		
		/* ------------- Public atributes ------------- */
		public var virtualImage:VirtualImage;
		public var oldVirtualImage:VirtualImage;
		
		public var repository:Repository;
		public var oldRepository:Repository;
		
		public var category:Category;
		public var oldCategory:Category;
		
		public var icon:Icon;
		public var oldIcon:Icon;
		
		/* ------------- Constructor ------------- */
		public function VirtualImageEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}

	}
}