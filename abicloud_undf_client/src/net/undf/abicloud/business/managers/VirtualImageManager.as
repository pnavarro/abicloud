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
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	import net.undf.abicloud.events.VirtualImageEvent;
	import net.undf.abicloud.vo.virtualimage.Category;
	import net.undf.abicloud.vo.virtualimage.Icon;
	import net.undf.abicloud.vo.virtualimage.Repository;
	import net.undf.abicloud.vo.virtualimage.VirtualImage;
	
	/**
	 * Manager for Virtual Images
	 * Stores user's virtual images, as well another useful information related to virtual images
	 **/
	public class VirtualImageManager extends EventDispatcher
	{
		/* ------------- Constants------------- */
		public static const VIRTUAL_IMAGES_INFORMATION_UPDATED:String = "virtualImagesInformationUpdated_VirtualImageManager";
		public static const VIRTUALIMAGES_UPDATED:String = "virtualImagesUpdated_VirtualImageManager";
		public static const REPOSITORIES_UPDATED:String = "repositoriesUpdated_VirtualImageManager";
		public static const CATEGORIES_UPDATED:String = "categoriesUpdated_VirtualImageManager";
		public static const ICONS_UPDATED:String = "iconsUpdated_VirtualImageManager";
		
		
		public static const DEFAULT_ICON_IMAGE_PATH:String = "assets/virtualimage/VirtualImage_defaultIcon.jpg";
		
		/* ------------- Private attributes ------------- */
		
		//ArrayCollection containing Virtual Images
		//It may contain virtual images from public repositories, as well as virtual images that belongs to the user
		//who has logged in the application
		private var _virtualImages:ArrayCollection;
		
		//ArrayCollection containing the list of repositories
		private var _repositories:ArrayCollection;
		
		//ArrayCollection containing the list of categories
		private var _categories:ArrayCollection;
		
		//ArrayCollection containig the list of available icons
		private var _icons:ArrayCollection;
		
		//ArrayCollection containing the list of virtual image types
		private var _virtualImageTypes:ArrayCollection;
		
		/* ------------- Constructor ------------- */
		public function VirtualImageManager()
		{
			_virtualImages = new ArrayCollection();
			_repositories = new ArrayCollection();
			_categories = new ArrayCollection();
			_icons = new ArrayCollection();
			_virtualImageTypes = new ArrayCollection();
		}
		
		
		/* ------------- Public methods ------------- */


		public function setVirtualImagesInformation(repositories:ArrayCollection, categories:ArrayCollection,
													icons:ArrayCollection, virtualImageTypes:ArrayCollection):void
		{
			this._repositories = repositories;
			this._categories = categories;
			this._icons = icons;
			this._virtualImageTypes = virtualImageTypes;
			
			dispatchEvent(new Event(VIRTUAL_IMAGES_INFORMATION_UPDATED));
		}
		
		/////////////////////////////////////////////
		// Related to Categories
		
		/**
		 * Returns the list of the different categories existing in the virtual images
		 **/
		[Bindable(event="categoriesUpdated_VirtualImageManager")]
		public function get categories():ArrayCollection
		{			
			return this._categories;
		}
		
		public function set categories(array:ArrayCollection):void
		{
			this._categories = array;
			dispatchEvent(new Event(CATEGORIES_UPDATED));
		}
		
		
		/**
		 * Adds a new Category to the categories list, that has just been created in server 
		 * @param newCategory
		 * 
		 */		
		public function createCategory(newCategory:Category):void
		{
			this._categories.addItem(newCategory);
			dispatchEvent(new Event(CATEGORIES_UPDATED));
		}
		
		/**
		 * Deletes a category from the categories list
		 */
		public function deleteCategory(category:Category):void
		{
			var index:int = this._categories.getItemIndex(category);
			if(index > -1)
			{
				this._categories.removeItemAt(index);
				dispatchEvent(new Event(CATEGORIES_UPDATED));
			}
		}
		
		/////////////////////////////////////////////
		// Related to Repositories
		
		/**
		 * Sets the list of repositories
		 **/
		public function set repositories(array:ArrayCollection):void
		{
			this._repositories = array;
			dispatchEvent(new Event(REPOSITORIES_UPDATED, true));
		}
		
		
		/**
		 * Returns the list of the Repositories
		 **/
		[Bindable(event="repositoriesUpdated_VirtualImageManager")]
		public function get repositories():ArrayCollection
		{	
			return this._repositories;
		}
		
		/**
		 * Updates a repository that has been edited
		 */
		public function editRepository(oldRepository:Repository, newRepository:Repository):void
		{
			//Updating the repository's values without modifying its memory address
			oldRepository.id = newRepository.id;
			oldRepository.name = newRepository.name;
			oldRepository.URL = newRepository.URL;
		}
		
		
		/////////////////////////////////////////////
		// Related to Icons
		
		/**
		 * Sets the list of icons
		 */
		public function set icons(value:ArrayCollection):void
		{
			this._icons = value;
			dispatchEvent(new Event(ICONS_UPDATED, true));
		}
		
		/**
		 * Returns the list of available icons
		 */
		[Bindable(event="iconsUpdated_VirtualImageManager")]
		public function get icons():ArrayCollection
		{
			return this._icons;
		}
		
		/**
		 * Adds a new icon already created in server, to the icons list
		 */
		public function createIcon(icon:Icon):void
		{
			this._icons.addItem(icon);
			dispatchEvent(new Event(ICONS_UPDATED, true));
		}
		
		/**
		 * Deletes an icon from the VirtualImageManager 
		 * @param icon
		 * 
		 */		
		public function deleteIcon(icon:Icon):void
		{
			var index:int = this._icons.getItemIndex(icon);
			if(index > -1)
			{
				this._icons.removeItemAt(index);
				dispatchEvent(new Event(ICONS_UPDATED, true));
			}
		}
		
		/**
		 * Edits an existing icon in the VirtualImageManager, with new values 
		 * @param oldIcon The icon in the virtualimagemanager that will be updated
		 * @param newIcon An Icon object with the new values
		 * 
		 */		
		public function editIcon(oldIcon:Icon, newIcon:Icon):void
		{
			//Updating the old icon without modifying its memory address
			oldIcon.id = newIcon.id;
			oldIcon.name = newIcon.name;
			oldIcon.path = newIcon.path;
			
			dispatchEvent(new Event(ICONS_UPDATED, true));
		}
		
		/////////////////////////////////////////////
		// Related to Virtual Images
		
		
		/**
		 * Sets the array that contains the virtual images
		 **/
		public function set virtualImages(array:ArrayCollection):void
		{
			this._virtualImages = array;
			dispatchEvent(new Event(VIRTUALIMAGES_UPDATED, true));
		}
		
		
		/**
		 * Returns the list of virtual images
		 **/
		[Bindable(event="virtualImagesUpdated_VirtualImageManager")]
		public function get virtualImages():ArrayCollection
		{
			return this._virtualImages;
		}
		
		/**
		 * Returns the list of virtual image types
		 */
		[Bindable(event="virtualImagesInformationUpdated_VirtualImageManager")]
		public function get virtualImageTypes():ArrayCollection
		{
			return this._virtualImageTypes;
		}
		
		/**
		 * Returns a list of virtual images that belongs to the same category
		 **/
		public function getVirtualImagesByCategory(category:Category):ArrayCollection
		{
			var length:int = this._virtualImages.length;
			var i:int;
			var virtualImage:VirtualImage;
			var virtualImagesByCategory:ArrayCollection = new ArrayCollection();
			
			for(i = 0; i < length; i++)
			{
				virtualImage = this._virtualImages.getItemAt(i) as VirtualImage;
				if(virtualImage.category.id == category.id)
					virtualImagesByCategory.addItem(virtualImage);
			}
			
			return virtualImagesByCategory;
		}
		
	
		/**
		 * Returns a list of virtual images that belongs to the same repository
		 **/
		public function getVirtualImagesByRepository(repository:Repository):ArrayCollection
		{
			var length:int = this._virtualImages.length;
			var i:int;
			var virtualImage:VirtualImage;
			var virtualImagesByRepository:ArrayCollection = new ArrayCollection();
			
			for(i = 0; i < length; i++)
			{
				virtualImage = this._virtualImages.getItemAt(i) as VirtualImage;
				if(virtualImage.repository.id == repository.id)
					virtualImagesByRepository.addItem(virtualImage);
			}
			
			return virtualImagesByRepository;
		}
		
		/**
		 * Adds to the virtual image list a new virtual image that has already been created in server
		 */
		public function createVirtualImage(newVirtualImage:VirtualImage):void
		{
			this._virtualImages.addItem(newVirtualImage);
			dispatchEvent(new Event(VIRTUALIMAGES_UPDATED, true));
		}
		
		/**
		 * Updates a VirtualImage with the information that has been edited, but without modifying its memory
		 * address.
		 */
		public function editVirtualImage(oldVirtualImage:VirtualImage, newVirtualImage:VirtualImage):void
		{
			//Updating the old virtual image, without modifying its memory address
			oldVirtualImage.id = newVirtualImage.id;
			oldVirtualImage.category = newVirtualImage.category;
			oldVirtualImage.cpuRequired = newVirtualImage.cpuRequired;
			oldVirtualImage.deleted = newVirtualImage.deleted;
			oldVirtualImage.description = newVirtualImage.description;
			oldVirtualImage.hdRequired = newVirtualImage.hdRequired;
			oldVirtualImage.icon = newVirtualImage.icon;
			oldVirtualImage.name = newVirtualImage.name;
			oldVirtualImage.path = newVirtualImage.path;
			oldVirtualImage.ramRequired = newVirtualImage.ramRequired;
			oldVirtualImage.repository = newVirtualImage.repository;
			oldVirtualImage.so = newVirtualImage.so;
			
			//Announcing that this virtual image has been updated
			var virtualImageEvent:VirtualImageEvent = new VirtualImageEvent(VirtualImageEvent.VIRTUAL_IMAGE_EDITED, false);
			virtualImageEvent.virtualImage = oldVirtualImage;
			dispatchEvent(virtualImageEvent);
		}
		
		/**
		 * Deletes a Virtual Image from the virtual images list, that has been already deleted
		 * in server
		 */
		public function deleteVirtualImage(virtualImage:VirtualImage):void
		{
			var index:int = this._virtualImages.getItemIndex(virtualImage);
			if(index > -1)
			{
				this._virtualImages.removeItemAt(index);
				dispatchEvent(new Event(VIRTUALIMAGES_UPDATED, true));
			}
		}
	}
	
	
}