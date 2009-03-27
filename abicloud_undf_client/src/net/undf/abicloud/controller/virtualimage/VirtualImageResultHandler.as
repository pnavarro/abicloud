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

package net.undf.abicloud.controller.virtualimage
{
	import mx.collections.ArrayCollection;
	import mx.resources.ResourceBundle;
	
	import net.undf.abicloud.controller.ResultHandler;
	import net.undf.abicloud.model.AbiCloudModel;
	import net.undf.abicloud.vo.result.BasicResult;
	import net.undf.abicloud.vo.result.DataResult;
	import net.undf.abicloud.vo.virtualimage.Category;
	import net.undf.abicloud.vo.virtualimage.Icon;
	import net.undf.abicloud.vo.virtualimage.Repository;
	import net.undf.abicloud.vo.virtualimage.VirtualImage;
	import net.undf.abicloud.vo.virtualimage.VirtualImageResult;
	
	/**
	 * Class to handle server responses when calling Virtual Images remote services defined in VirtualImageEventMap
	 **/
	public class VirtualImageResultHandler extends ResultHandler
	{
		/* ------------- Constructor --------------- */
		public function VirtualImageResultHandler()
		{
			super();
		}
		
		[ResourceBundle("Common")]
		private var rb:ResourceBundle;
		
		public function handleEditRepository(result:BasicResult, oldRepository:Repository, newRepository:Repository):void
		{
			if(result.success)
			{
				//Editing the repository in the model
				AbiCloudModel.getInstance().virtualImageManager.editRepository(oldRepository, newRepository);
			}
			else
			{
				//There was a problem editing the Repository
				super.handleResult(result);
			}
		}
		
		public function handleGetVirtualImagesInformation(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding to the model all the virtual image information
				var virtualImageResult:VirtualImageResult = DataResult(result).data as VirtualImageResult;
				AbiCloudModel.getInstance().virtualImageManager.setVirtualImagesInformation(virtualImageResult.repositories, virtualImageResult.categories, virtualImageResult.icons, virtualImageResult.virtualImageTypes);
			}
			else
			{
				//There was a problem retrieving the Virtual Images
				super.handleResult(result);
			}
		}
		
		
		
		/////////////////////////////////
		//Virtual Images
		
		public function handleGetVirtualImagesByUser(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding to the model the list of user's virtual images
				AbiCloudModel.getInstance().virtualImageManager.virtualImages = DataResult(result).data as ArrayCollection;
			}
			else
			{
				//There was a problem retrieving the Virtual Images
				super.handleResult(result);
			}
		}
		
		public function handleCreateVirtualImage(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new Virtual Image to the model
				var createdVirtualImage:VirtualImage = DataResult(result).data as VirtualImage;
				AbiCloudModel.getInstance().virtualImageManager.createVirtualImage(createdVirtualImage);
			}
			else
			{
				//There was a problem creating the Virtual Image
				super.handleResult(result);
			}
		}
		
		public function handleEditVirtualImage(result:BasicResult, oldVirtualImage:VirtualImage, newVirtualImage:VirtualImage):void
		{
			if(result.success)
			{
				//Editing the Virtual Image in the model
				AbiCloudModel.getInstance().virtualImageManager.editVirtualImage(oldVirtualImage, newVirtualImage);
			}
			else
			{
				//There was a problem editing the Virtual Image
				super.handleResult(result);
			}
		}
		
		public function handleDeleteVirtualImage(result:BasicResult, virtualImage:VirtualImage):void
		{
			if(result.success)
			{
				//Deleting the virtual image in model
				AbiCloudModel.getInstance().virtualImageManager.deleteVirtualImage(virtualImage);
			}
			else
			{
				//There was a problem with the virtual image deletion
				super.handleResult(result);
			}
		}
		
		/////////////////////////////////
		//CATEGORIES
		
		public function handleCreateCategory(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new category to the model
				var categoryCreated:Category = DataResult(result).data as Category;
				AbiCloudModel.getInstance().virtualImageManager.createCategory(categoryCreated);
			}
			else
			{
				//There was a problem creating the new category
				super.handleResult(result);
			}
		}
		
		public function handleDeleteCategory(result:BasicResult, deletedCategory:Category):void
		{
			if(result.success)
			{
				//Deleting the category from the model
				AbiCloudModel.getInstance().virtualImageManager.deleteCategory(deletedCategory);
			}
			else
			{
				//There was a problem deleting the category
				super.handleResult(result);
			}
		}
		
		/////////////////////////////////
		//ICONS
		
		public function handleCreateIcon(result:BasicResult):void
		{
			if(result.success)
			{
				//Adding the new icon to the model
				var iconCreated:Icon = DataResult(result).data as Icon;
				AbiCloudModel.getInstance().virtualImageManager.createIcon(iconCreated);
			}
			else
			{
				//There was a problem creating the new category
				super.handleResult(result);
			}
		}
		
		public function handleDeleteIcon(result:BasicResult, iconDeleted:Icon):void
		{
			if(result.success)
			{
				//Deleting the icon from the model
				AbiCloudModel.getInstance().virtualImageManager.deleteIcon(iconDeleted);
			}
			else
			{
				//There was a problem deleting the icon
				super.handleResult(result);
			}
		}
		
		public function handleEditIcon(result:BasicResult, oldIcon:Icon, newIcon:Icon):void
		{
			if(result.success)
			{
				//Updating the icon in the model
				AbiCloudModel.getInstance().virtualImageManager.editIcon(oldIcon, newIcon);
			}
			else
			{
				//There was a problem editing the icon
				super.handleResult(result);
			}
		}
	}
}