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
package com.abiquo.abiserver.commands;

import java.util.ArrayList;
import java.util.Date;

import com.abiquo.abiserver.business.authentication.SessionUtil;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.SoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.CategoryHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.IconHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.RepositoryHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageTypeHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;
import com.abiquo.abiserver.pojo.virtualimage.Category;
import com.abiquo.abiserver.pojo.virtualimage.Icon;
import com.abiquo.abiserver.pojo.virtualimage.Repository;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImageResult;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImageType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.abiquo.util.resources.ResourceManager;

/**
 * This command collects all actions related to Virtual Images
 * 
 * @author Oliver
 */

public class VirtualImageCommand extends BasicCommand
{

    private static final ResourceManager resourceManager =
        new ResourceManager(VirtualImageCommand.class);

    protected BasicResult editRepository(UserSession userSession, Repository repository)
    {
        BasicResult basicResult = new BasicResult();
        Session session = null;
        Transaction transaction = null;

        try
        {
            // Retrieving the user that has called this method
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Retrieving the Repository that will be edited
            RepositoryHB repositoryHB =
                (RepositoryHB) session.get(RepositoryHB.class, repository.getId());

            // Updating changes
            repositoryHB.setName(repository.getName());
            repositoryHB.setUrl(repository.getURL());
            repositoryHB.setLastModificationDate(new Date());
            repositoryHB.setUserHBByIdUserLastModification(userHB);

            session.update(repositoryHB);
            transaction.commit();

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, basicResult,
                "editRepository", e);
            return basicResult;
        }

        // Everything went fine
        basicResult.setSuccess(true);
        basicResult.setMessage(VirtualImageCommand.resourceManager
            .getMessage("editRepository.success"));
        return basicResult;
    }

    /**
     * Returns Repositories, Categories and icons stored in the Data Base TODO: Retrieve only
     * information that belongs to a user
     * 
     * @return a DataResult object containing a VirtualImageResult with repositories and categories
     */
    @SuppressWarnings("unchecked")
    protected DataResult<VirtualImageResult> getVirtualImagesInformation()
    {
        DataResult<VirtualImageResult> dataResult = new DataResult<VirtualImageResult>();
        ArrayList<Repository> repositories = new ArrayList<Repository>();
        ArrayList<Category> categories = new ArrayList<Category>();
        ArrayList<Icon> icons = new ArrayList<Icon>();
        ArrayList<VirtualImageType> virtualImageTypes = new ArrayList<VirtualImageType>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            ArrayList<RepositoryHB> repositoriesHB =
                (ArrayList<RepositoryHB>) session.createCriteria(RepositoryHB.class).list();
            ArrayList<CategoryHB> categoriesHB =
                (ArrayList<CategoryHB>) session.createCriteria(CategoryHB.class).list();
            ArrayList<IconHB> iconsHB =
                (ArrayList<IconHB>) session.createCriteria(IconHB.class).list();
            ArrayList<VirtualimageTypeHB> virtualImageTypesHB =
                (ArrayList<VirtualimageTypeHB>) session.createCriteria(VirtualimageTypeHB.class)
                    .list();

            Repository repository;
            for (RepositoryHB respositoryHB : repositoriesHB)
            {
                repository = (Repository) respositoryHB.toPojo();
                repositories.add(repository);
            }

            Category category;
            for (CategoryHB categoryHB : categoriesHB)
            {
                category = (Category) categoryHB.toPojo();
                categories.add(category);
            }

            Icon icon;
            for (IconHB iconHB : iconsHB)
            {
                icon = (Icon) iconHB.toPojo();
                icons.add(icon);
            }

            VirtualImageType virtualImageType;
            for (VirtualimageTypeHB virtualImageTypeHB : virtualImageTypesHB)
            {
                virtualImageType = (VirtualImageType) virtualImageTypeHB.toPojo();
                virtualImageTypes.add(virtualImageType);
            }

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, dataResult,
                "getVirtualImagesInformation", e);

            return dataResult;
        }

        VirtualImageResult virtualImageResult = new VirtualImageResult();
        virtualImageResult.setCategories(categories);
        virtualImageResult.setRepositories(repositories);
        virtualImageResult.setIcons(icons);
        virtualImageResult.setVirtualImageTypes(virtualImageTypes);

        dataResult.setSuccess(true);
        dataResult.setMessage(VirtualImageCommand.resourceManager
            .getMessage("getVirtualImagesInformation.success"));
        dataResult.setData(virtualImageResult);

        return dataResult;
    }

    /**
     * Returns Virtual Images stored in the Data Base TODO: Get only Virtual Images from public
     * repositories or from repositories that belongs to the user who called this method
     * (session.user)
     * 
     * @param session
     * @return a DataResult object containing an ArrayList of VirtualImage
     */
    @SuppressWarnings("unchecked")
    protected DataResult<ArrayList<VirtualImage>> getVirtualImagesByUser(UserSession userSession)
    {
        DataResult<ArrayList<VirtualImage>> dataResult = new DataResult<ArrayList<VirtualImage>>();
        ArrayList<VirtualImage> virtualImages = new ArrayList<VirtualImage>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who is asking for the Virtual Images
            // UserHB userHB =
            // (UserHB)session.createCriteria(UserHB.class).add(Restrictions.eq("user",
            // userSession.getUser())).uniqueResult();

            // Getting the WHOLE list of virtual images
            ArrayList<VirtualimageHB> virtualImagesHB =
                (ArrayList<VirtualimageHB>) session.createCriteria(VirtualimageHB.class).list();

            VirtualImage virtualImage;
            for (VirtualimageHB virtualImageHB : virtualImagesHB)
            {
                virtualImage = (VirtualImage) virtualImageHB.toPojo();
                virtualImages.add(virtualImage);
            }

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, dataResult,
                "getVirtualImagesByUser", e);

            return dataResult;
        }

        // Virtual Images retrieved succesfull
        dataResult.setSuccess(true);
        dataResult.setMessage(VirtualImageCommand.resourceManager
            .getMessage("getVirtualImagesByUser.success"));
        dataResult.setData(virtualImages);

        return dataResult;
    }

    /**
     * Creates a new Virtual Image
     * 
     * @param userSession The user's session that has called this service
     * @param virtualImage The new Virtual Image that will be created
     * @return A DataResult object containing a VirtualImage object with the virtual image created,
     *         if the creation process was successful. Otherwise, a BasicResult object with the
     *         reason why the virtual image could not be created
     */
    protected DataResult<VirtualImage> createVirtualImage(UserSession userSession,
        VirtualImage virtualImage)
    {
        DataResult<VirtualImage> dataResult = new DataResult<VirtualImage>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user that called this method
            UserHB userHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Creating the object that will be saved in Data Base
            VirtualimageHB virtualimageHB = (VirtualimageHB) virtualImage.toPojoHB();
            // Append the path
            // RepositoryHB repository = virtualimageHB.getRepository();
            virtualimageHB.setPathName(virtualImage.getPath());
            virtualimageHB.setUserHBByIdUserCreation(userHB);
            virtualimageHB.setCreationDate(new Date());

            // Saving the new virtual image
            session.save(virtualimageHB);

            transaction.commit();

            // Creating the result that will be returned
            dataResult.setSuccess(true);
            dataResult.setData((VirtualImage) virtualimageHB.toPojo());
            dataResult.setMessage(VirtualImageCommand.resourceManager
                .getMessage("createVirtualImage.success"));
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            // Generating the result with the error
            this.errorManager.reportError(VirtualImageCommand.resourceManager, dataResult,
                "createVirtualImage", e);
            ;
        }

        return dataResult;
    }

    /**
     * Edits virtualImage information in the DataBase
     * 
     * @param virtualImage
     * @return a BasicResult object, announcing if the edition had success
     */
    protected BasicResult editVirtualImage(UserSession userSession, VirtualImage virtualImage)
    {
        Session session = null;
        Transaction transaction = null;
        BasicResult basicResult = new BasicResult();

        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Retrieving the Virtual Image that user wants to edit
            VirtualimageHB virtualImageHB =
                (VirtualimageHB) session.get(VirtualimageHB.class, virtualImage.getId());

            // Updating attributes for the VirtualImage
            RepositoryHB newRepositoryHB =
                (RepositoryHB) session
                    .get(RepositoryHB.class, virtualImage.getRepository().getId());
            CategoryHB newCategoryHB =
                (CategoryHB) session.get(CategoryHB.class, virtualImage.getCategory().getId());
            SoHB newSOHB = (SoHB) session.get(SoHB.class, virtualImage.getSo().getId());
            IconHB newIconHB = null;
            if (virtualImage.getIcon() != null)
                newIconHB = (IconHB) session.get(IconHB.class, virtualImage.getIcon().getId());
            VirtualimageTypeHB newVirtualImageTypeHB =
                (VirtualimageTypeHB) session.get(VirtualimageTypeHB.class, virtualImage
                    .getVirtualImageType().getId());

            virtualImageHB.setName(virtualImage.getName());
            virtualImageHB.setDescription(virtualImage.getDescription());
            virtualImageHB.setVirtualimageTypeHB(newVirtualImageTypeHB);
            // Append the URL + relative path
            virtualImageHB.setPathName(virtualImage.getPath());
            virtualImageHB.setCategory(newCategoryHB);
            virtualImageHB.setRepository(newRepositoryHB);
            virtualImageHB.setSo(newSOHB);
            virtualImageHB.setRamRequired(virtualImage.getRamRequired());
            virtualImageHB.setCpuRequired(virtualImage.getCpuRequired());
            virtualImageHB.setHdRequired(virtualImage.getHdRequired());
            virtualImageHB.setIcon(newIconHB);

            // User and Date modification
            virtualImageHB.setLastModificationDate(new Date());
            virtualImageHB.setUserHBByIdUserLastModification(userHB);

            session.update(virtualImageHB);
            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, basicResult,
                "editVirtualImage", e);

            return basicResult;
        }

        basicResult.setSuccess(true);
        basicResult.setMessage(VirtualImageCommand.resourceManager
            .getMessage("editVirtualImage.success"));

        return basicResult;
    }

    /**
     * Deletes a virtual image from the Data base
     * 
     * @param virtualImage The Virtual Image that will be deleted. This virtual image can not be
     *            being used by any Virtual Machine or Virtual Appliance's Node. If so, the deletion
     *            will not be allowed and an error will be returned with code
     *            BasicResult.VIRTUAL_IMAGE_IN_USE
     * @return A BasicResult object containing the result of the virtual image deletion
     */
    @SuppressWarnings("unchecked")
    protected BasicResult deleteVirtualImage(VirtualImage virtualImage)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the Virtual Image that we want to delete
            VirtualimageHB virtualImageHB =
                (VirtualimageHB) session.get(VirtualimageHB.class, virtualImage.getId());

            // Checking if the virtual image is being used by any virtual machine or node
            ArrayList<VirtualmachineHB> virtualMachinesInUse =
                (ArrayList<VirtualmachineHB>) session.createCriteria(VirtualmachineHB.class).add(
                    Restrictions.eq("image", virtualImageHB)).list();
            ArrayList<NodeHB> nodesInUse =
                (ArrayList<NodeHB>) session.createCriteria(NodeHB.class).add(
                    Restrictions.eq("virtualimage", virtualImageHB)).list();
            if (virtualMachinesInUse.size() == 0 && nodesInUse.size() == 0)
            {
                // The virtual image is not being used. We can safely delete it
                session.delete(virtualImageHB);

                basicResult.setSuccess(true);
                basicResult.setMessage(VirtualImageCommand.resourceManager
                    .getMessage("deleteVirtualImage.success"));
            }
            else
            {
                // The Virtual Image is being used. We can not delete it
                basicResult.setResultCode(BasicResult.VIRTUAL_IMAGE_IN_USE);
                basicResult.setSuccess(false);
            }

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, basicResult,
                "deleteVirtualImage", e);
        }

        return basicResult;
    }

    /**
     * Creates a new Category
     * 
     * @param userSession The user's session that has called this service
     * @param category The new category that will be created
     * @return A DataResult object containing a Category object with the category created, if the
     *         creation process was successful. Otherwise, a BasicResult object with the reason why
     *         the category could not be created
     */
    protected DataResult<Category> createCategory(UserSession userSession, Category category)
    {
        DataResult<Category> dataResult = new DataResult<Category>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user that called this method
            UserHB userHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Creating the object that will be saved in Data Base
            CategoryHB categoryHB = (CategoryHB) category.toPojoHB();
            categoryHB.setUserHBByIdUserCreation(userHB);
            categoryHB.setCreationDate(new Date());

            // Saving the new category
            session.save(categoryHB);

            transaction.commit();

            // Creating the result that will be returned
            dataResult.setSuccess(true);
            dataResult.setData((Category) categoryHB.toPojo());
            dataResult.setMessage(VirtualImageCommand.resourceManager
                .getMessage("createCategory.success"));
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            // Generating the result with the error
            this.errorManager.reportError(VirtualImageCommand.resourceManager, dataResult,
                "createCategory", e);

        }

        return dataResult;
    }

    /**
     * Deletes a category in the data base
     * 
     * @param userSession The UserSession that called this method
     * @param category The category that will be deleted
     * @return A BasicResult object containing the result of the category deletion
     */
    @SuppressWarnings("unchecked")
    protected BasicResult deleteCategory(UserSession userSession, Category category)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user that called this method
            UserHB userHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Getting the category that will be deleted
            CategoryHB categoryHBToDelete =
                (CategoryHB) session.get(CategoryHB.class, category.getId());

            // First, we have to check if there are any virtual image assigned to this category
            ArrayList<VirtualimageHB> virtualImageHBAssigned =
                (ArrayList<VirtualimageHB>) session.createCriteria(VirtualimageHB.class).add(
                    Restrictions.eq("category", categoryHBToDelete)).list();
            if (virtualImageHBAssigned != null && virtualImageHBAssigned.size() > 0)
            {
                // Setting all the virtual images that belong to this category to the default
                // category
                CategoryHB defaultCategoryHB =
                    (CategoryHB) session.createCriteria(CategoryHB.class).add(
                        Restrictions.eq("isDefault", 1)).uniqueResult();
                for (VirtualimageHB virtualImageHB : virtualImageHBAssigned)
                {
                    virtualImageHB.setCategory(defaultCategoryHB);
                    virtualImageHB.setUserHBByIdUserLastModification(userHB);
                    virtualImageHB.setLastModificationDate(new Date());
                    session.update(virtualImageHB);
                }
            }

            // Now, we can delete the selected category
            session.delete(categoryHBToDelete);

            basicResult.setSuccess(true);
            basicResult.setMessage(VirtualImageCommand.resourceManager
                .getMessage("deleteCategory.success"));

            transaction.commit();

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, basicResult,
                "deleteCategory", e);
        }

        return basicResult;
    }

    /**
     * Creates a new Icon
     * 
     * @param userSession The UserSession that called this method
     * @param icon The Icon that will be created
     * @return A DataResult object containing the Icon created in the Data Base, if the creation had
     *         success.
     */
    protected DataResult<Icon> createIcon(UserSession userSession, Icon icon)
    {
        DataResult<Icon> dataResult = new DataResult<Icon>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user that called this method
            UserHB userHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Creating the object that will be saved
            IconHB iconHB = (IconHB) icon.toPojoHB();
            iconHB.setUserHBByIdUserCreation(userHB);
            iconHB.setCreationDate(new Date());

            session.save(iconHB);

            transaction.commit();

            dataResult.setSuccess(true);
            dataResult.setData((Icon) iconHB.toPojo());
            dataResult.setMessage(VirtualImageCommand.resourceManager
                .getMessage("createIcon.success"));
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, dataResult,
                "createIcon", e);
        }

        return dataResult;
    }

    /**
     * Deletes an icon from the Data Base
     * 
     * @param session The UserSession that called this method
     * @param icon The Icon that will be deleted from the Data Base
     * @return A BasicResult object containing the result from the deletion
     */
    @SuppressWarnings("unchecked")
    protected BasicResult deleteIcon(UserSession userSession, Icon icon)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the icon to delete
            IconHB iconHBToDelete = (IconHB) session.get(IconHB.class, icon.getId());

            // Checking if this icon is being used by a VirtualImage
            ArrayList<VirtualimageHB> virtualimageHBList =
                (ArrayList<VirtualimageHB>) session.createCriteria(VirtualimageHB.class).add(
                    Restrictions.eq("icon", iconHBToDelete)).list();

            if (virtualimageHBList.size() > 0)
            {
                // The icon is being used. We cannot delete it
                basicResult.setSuccess(false);
                basicResult.setMessage(VirtualImageCommand.resourceManager
                    .getMessage("deleteIcon.iconBeingUsedError"));
            }
            else
            {
                // We can delete the icon
                session.delete(iconHBToDelete);
                basicResult.setSuccess(true);
                basicResult.setMessage(VirtualImageCommand.resourceManager
                    .getMessage("deleteIcon.success"));
            }

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, basicResult,
                "deleteIcon", e);
        }

        return basicResult;
    }

    /**
     * Updated an icon in Data Base with new values
     * 
     * @param session The UserSession that called this method
     * @param icon The icon that will be updated in Data Base
     * @return A BasicResult object containing the result of the edition (success = true if the
     *         edition was successful, or false otherwise)
     */
    protected BasicResult editIcon(UserSession userSession, Icon icon)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user that called this method
            UserHB userHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Getting the icon to edit
            IconHB iconHBToEdit = (IconHB) session.get(IconHB.class, icon.getId());

            // Updating fields
            iconHBToEdit.setName(icon.getName());
            iconHBToEdit.setPath(icon.getPath());
            iconHBToEdit.setLastModificationDate(new Date());
            iconHBToEdit.setUserHBByIdUserLastModification(userHB);

            session.update(iconHBToEdit);

            basicResult.setSuccess(true);
            basicResult.setMessage(VirtualImageCommand.resourceManager
                .getMessage("editIcon.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(VirtualImageCommand.resourceManager, basicResult,
                "editIcon", e);
        }

        return basicResult;
    }
}
