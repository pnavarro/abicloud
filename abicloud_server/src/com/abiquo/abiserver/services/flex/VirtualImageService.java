/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in 
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is available at http://www.abiquo.com/.....
 * 
 * The Initial Developer of the Original Code is Soluciones Grid, S.L. (www.abiquo.com),
 * Consell de Cent 296, Principal 2�, 08007 Barcelona, Spain.
 * 
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License", 
 * available at http://cpal.abiquo.com/), in which case the 
 * provisions of CPAL License are applicable instead of those above. In relation 
 * of this portions of the Code, a Legal Notice according to Exhibits A and B of 
 * CPAL Licence should be provided in any distribution of the corresponding Code 
 * to Graphical User Interface.
 */
package com.abiquo.abiserver.services.flex;

import com.abiquo.abiserver.business.locators.resource.ResourceLocator;
import com.abiquo.abiserver.commands.VirtualImageCommand;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.virtualimage.Category;
import com.abiquo.abiserver.pojo.virtualimage.Icon;
import com.abiquo.abiserver.pojo.virtualimage.Repository;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

/**
 * This class defines all services related to Virtual Images management
 * 
 * @author Oliver
 */

public class VirtualImageService
{
    /**
     * Edits a repository information in the DataBase
     * 
     * @param session The user's session that has called this method
     * @param repository The repository with the new information that will be edited
     * @return
     */
    public BasicResult editRepository(UserSession session, Repository repository)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];
        args[0] = session;
        args[1] = repository;

        return virtualImageCommand.execute(session, ResourceLocator.REPOSITORY_EDIT, args);
    }

    /**
     * Returns Virtual Images stored in the Data Base TODO: Get only Virtual Images from public
     * repositories or from repositories that belongs to the user who called this method
     * (session.user)
     * 
     * @return a DataResult object containing an ArrayList of VirtualImage
     */
    public BasicResult getVirtualImagesByUser(UserSession session)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[1];
        args[0] = session;

        return virtualImageCommand.execute(session, ResourceLocator.VIRTUALIMAGE_GETBYUSER, args);
    }

    /**
     * Returns Repositories stored in the Data Base
     * 
     * @param session
     * @return a DataResult object containing a VirtualImageResult object with categories and
     *         repositories
     */
    public BasicResult getVirtualImagesInformation(UserSession session)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();

        return virtualImageCommand.execute(session, ResourceLocator.VIRTUALIMAGEINFORMATION_GET,
            null);
    }

    /**
     * Creates a new Virtual Image
     * 
     * @param session The user's session that has called this service
     * @param virtualImage The new Virtual Image that will be created
     * @return A DataResult object containing a VirtualImage object with the virtual image created,
     *         if the creation process was successful. Otherwise, a BasicResult object with the
     *         reason why the virtual image could not be created
     */
    public BasicResult createVirtualImage(UserSession session, VirtualImage virtualImage)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = virtualImage;

        return virtualImageCommand.execute(session, ResourceLocator.VIRTUALIMAGE_CREATE, args);
    }

    /**
     * Edits virtualImage information in the DataBase
     * 
     * @param virtualImage
     * @return a BasicResult object, announcing if the edition had success
     */
    public BasicResult editVirtualImage(UserSession session, VirtualImage virtualImage)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = virtualImage;

        return virtualImageCommand.execute(session, ResourceLocator.VIRTUALIMAGE_EDIT, args);
    }

    /**
     * Deletes a virtual image from the Data base
     * 
     * @param session The user's session that called this method
     * @param virtualImage The Virtual Image that will be deleted. This virtual image can not be
     *            being used by any Virtual Machine or Virtual Appliance's Node. If so, the deletion
     *            will not be allowed and an error will be returned with code
     *            BasicResult.VIRTUAL_IMAGE_IN_USE
     * @return A BasicResult object containing the result of the virtual image deletion
     */
    public BasicResult deleteVirtualImage(UserSession session, VirtualImage virtualImage)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[1];

        args[0] = virtualImage;

        return virtualImageCommand.execute(session, ResourceLocator.VIRTUALIMAGE_DELETE, args);
    }

    /**
     * Creates a new Category
     * 
     * @param session The user's session that called this service
     * @param category The new Category that will be created
     * @return A DataResult object containing the category that has been created, if the creation
     *         process was successful. Otherwise, a BasicResult object with the reason why the
     *         category could not be created
     */
    public BasicResult createCategory(UserSession session, Category category)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = category;

        return virtualImageCommand.execute(session, ResourceLocator.CATEGORY_CREATE, args);
    }

    /**
     * Deletes a category in the data base
     * 
     * @param session The UserSession that called this method
     * @param category The category that will be deleted
     * @return A BasicResult object containing the result of the category deletion
     */
    public BasicResult deleteCategory(UserSession session, Category category)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = category;

        return virtualImageCommand.execute(session, ResourceLocator.CATEGORY_DELETE, args);
    }

    /**
     * Creates a new Icon
     * 
     * @param session The UserSession that called this method
     * @param icon The Icon that will be created
     * @return A DataResult object containing the Icon created in the Data Base, if the creation had
     *         success.
     */
    public BasicResult createIcon(UserSession session, Icon icon)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = icon;

        return virtualImageCommand.execute(session, ResourceLocator.ICON_CREATE, args);
    }

    /**
     * Deletes an icon from the Data Base
     * 
     * @param session The UserSession that called this method
     * @param icon The Icon that will be deleted from the Data Base
     * @return A BasicResult object containing the result from the deletion
     */
    public BasicResult deleteIcon(UserSession session, Icon icon)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = icon;

        return virtualImageCommand.execute(session, ResourceLocator.ICON_DELETE, args);
    }

    /**
     * Updated an icon in Data Base with new values
     * 
     * @param session The UserSession that called this method
     * @param icon The icon that will be updated in Data Base
     * @return A BasicResult object containing the result of the edition (success = true if the
     *         edition was successful, or false otherwise)
     */
    public BasicResult editIcon(UserSession session, Icon icon)
    {
        VirtualImageCommand virtualImageCommand = new VirtualImageCommand();
        Object[] args = new Object[2];

        args[0] = session;
        args[1] = icon;

        return virtualImageCommand.execute(session, ResourceLocator.ICON_EDIT, args);
    }
}
