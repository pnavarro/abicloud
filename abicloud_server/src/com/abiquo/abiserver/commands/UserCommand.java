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
import com.abiquo.abiserver.business.hibernate.pojohb.authorization.AuthResourceHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.EnterpriseHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.RoleHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;
import com.abiquo.abiserver.pojo.user.Enterprise;
import com.abiquo.abiserver.pojo.user.EnterpriseListOptions;
import com.abiquo.abiserver.pojo.user.EnterpriseListResult;
import com.abiquo.abiserver.pojo.user.User;
import com.abiquo.abiserver.pojo.user.UserListOptions;
import com.abiquo.abiserver.pojo.user.UserListResult;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.abiquo.util.resources.ResourceManager;

/**
 * This command collects all actions related to users management
 * 
 * @author Oliver
 */
public class UserCommand extends BasicCommand
{
    private static final ResourceManager resourceManager = new ResourceManager(UserCommand.class);

    /**
     * Returns a list of users stored in the Data Base
     * 
     * @param userSession
     * @param userListOptions an UserListOptions object containing the options to retrieve the list
     *            of users
     * @return A DataResult object containing an UserListResult object with an ArrayList of User and
     *         the number of total users
     */
    @SuppressWarnings("unchecked")
    protected DataResult<UserListResult> getUsers(UserSession userSession,
        UserListOptions userListOptions)
    {
        DataResult<UserListResult> dataResult = new DataResult<UserListResult>();
        UserListResult userListResult = new UserListResult();

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

            // getUsers has two different authorization resources
            // If the user who called this method, does not match the security level for the
            // authorization resource
            // USER_GET_ALL_USERS, this method will only return those users who belongs to the same
            // enterprise than
            // the user that called this method

            // Getting the authorization resource USER_GET_ALL_USERS
            AuthResourceHB authResourceHB =
                (AuthResourceHB) session.createCriteria(AuthResourceHB.class).add(
                    Restrictions.eq("name", "USER_GET_ALL_USERS")).uniqueResult();

            // Checking if the user has not the necessary security level to see the whole list of
            // Users. If so, we force
            // the userListOptions object to filter by the enterprise assigned to the user
            if (authResourceHB.getRoleHB().getSecurityLevel().compareTo(
                userHB.getRoleHB().getSecurityLevel()) == -1)
            {
                // NOT GRANTED!
                userListOptions.setByEnterprise((Enterprise) userHB.getEnterpriseHB().toPojo());
            }

            // Creating users criteria
            Criteria usersListCriteria = session.createCriteria(UserHB.class);
            Criteria usersCountCriteria = session.createCriteria(UserHB.class);

            // Removing the users that are deleted
            usersListCriteria.add(Restrictions.eq("deleted", 0));
            usersCountCriteria.add(Restrictions.eq("deleted", 0));

            // Adding filter by name, surname or email
            Disjunction filterDisjunction = Restrictions.disjunction();
            if (userListOptions.getFilter().length() > 0)
            {
                filterDisjunction.add(Restrictions.like("name",
                    '%' + userListOptions.getFilter() + '%'));
                filterDisjunction.add(Restrictions.like("surname", '%' + userListOptions
                    .getFilter() + '%'));
                filterDisjunction.add(Restrictions.like("email",
                    '%' + userListOptions.getFilter() + '%'));
            }
            usersListCriteria.add(filterDisjunction);
            usersCountCriteria.add(filterDisjunction);

            // Adding filter by Enterprise
            if (userListOptions.getByEnterprise() != null)
            {
                usersListCriteria.add(Restrictions.eq("enterpriseHB",
                    (EnterpriseHB) userListOptions.getByEnterprise().toPojoHB()));
                usersCountCriteria.add(Restrictions.eq("enterpriseHB",
                    (EnterpriseHB) userListOptions.getByEnterprise().toPojoHB()));
            }

            // Adding order
            // Little fix to match the object used in Hibernate
            if (userListOptions.getOrderBy().compareTo("role") == 0)
                userListOptions.setOrderBy("roleHB");

            Order order;
            if (userListOptions.getAsc())
                order = Order.asc(userListOptions.getOrderBy());
            else
                order = Order.desc(userListOptions.getOrderBy());

            usersListCriteria.addOrder(order);
            usersCountCriteria.addOrder(order);

            // Adding filter to get only the users that currently have an active session
            if (userListOptions.getLoggedOnly())
            {
                ArrayList<String> currentLoggedUsers =
                    (ArrayList<String>) session.createSQLQuery(
                        "SELECT user FROM session WHERE expireDate > CURRENT_TIMESTAMP()").list();
                usersListCriteria.add(Restrictions.in("user", currentLoggedUsers));
                usersCountCriteria.add(Restrictions.in("user", currentLoggedUsers));
            }

            // Once we have the criteria...
            // 1. Getting the total number of users that match userListOptions
            Integer totalUsers =
                (Integer) usersCountCriteria.setProjection(Projections.rowCount()).uniqueResult();

            // 2. Getting the list of users, applying an offset and a max of results
            ArrayList<UserHB> usersHB =
                (ArrayList<UserHB>) usersListCriteria.setFirstResult(userListOptions.getOffset())
                    .setMaxResults(userListOptions.getLength()).list();

            // Building result
            ArrayList<User> usersList = new ArrayList<User>();
            for (UserHB userToReturnHB : usersHB)
            {
                usersList.add((User) userToReturnHB.toPojo());
            }

            userListResult.setTotalUsers(totalUsers);
            userListResult.setUsersList(usersList);

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "getUsers", e);

            return dataResult;
        }

        dataResult.setData(userListResult);
        dataResult.setSuccess(true);
        dataResult.setMessage(UserCommand.resourceManager.getMessage("getUsers.success"));

        return dataResult;
    }

    /**
     * Creates a new User in the Data Base
     * 
     * @param userSession
     * @param user the User to be created
     * @return A DataResult object containing the a User object with the user created in the Data
     *         Base
     */
    protected DataResult<User> createUser(UserSession userSession, User user)
    {
        DataResult<User> dataResult = new DataResult<User>();
        Session session = null;
        Transaction transaction = null;

        UserHB newUserHB;
        try
        {
            // Getting the user who has called this method
            UserHB userInfoHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            newUserHB = (UserHB) user.toPojoHB();
            newUserHB.setUserHBByIdUserCreation(userInfoHB.getIdUser());
            newUserHB.setCreationDate(new Date());
            session.save(newUserHB);

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            // Check if the error was produces because an invalid user name
            if (e instanceof ConstraintViolationException)
            {
                dataResult.setSuccess(false);
                dataResult.setMessage(UserCommand.resourceManager
                    .getMessage("createUser.userNotAvailable"));
            }
            else
                this.errorManager.reportError(resourceManager, dataResult, "createUser", e);

            return dataResult;
        }

        dataResult.setSuccess(true);
        dataResult.setData((User) newUserHB.toPojo());
        dataResult.setMessage(UserCommand.resourceManager.getMessage("createUser.success"));

        return dataResult;
    }

    /**
     * Modifies a User that already exists in the Data Base
     * 
     * @param userSession
     * @param users A list of users to be modified
     * @return A BasicResult object, informing if the user edition was successful
     */
    protected BasicResult editUser(UserSession userSession, ArrayList<User> users)
    {
        BasicResult basicResult = new BasicResult();
        Session session = null;
        Transaction transaction = null;

        try
        {
            // Getting the user who has called this method
            UserHB userInfoHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            EnterpriseHB userEnterpriseHB;

            for (User user : users)
            {
                // Getting from the Data Base the user that is being edited
                UserHB userHBToEdit = (UserHB) session.get(UserHB.class, user.getId());

                // Editing the user
                userHBToEdit.setRoleHB((RoleHB) user.getRole().toPojoHB());
                userHBToEdit.setUser(user.getUser());
                userHBToEdit.setName(user.getName());
                userHBToEdit.setSurname(user.getSurname());
                userHBToEdit.setDescription(user.getDescription());
                userHBToEdit.setEmail(user.getEmail());
                userHBToEdit.setLocale(user.getLocale());
                userHBToEdit.setPassword(user.getPass());
                userHBToEdit.setActive(user.getActive() ? 1 : 0);
                userHBToEdit.setDeleted(user.getDeleted() ? 1 : 0);

                // Enterprise update
                if (user.getEnterprise() != null)
                {
                    userEnterpriseHB =
                        (EnterpriseHB) session
                            .get(EnterpriseHB.class, user.getEnterprise().getId());
                    userHBToEdit.setEnterpriseHB(userEnterpriseHB);
                }
                else
                    userHBToEdit.setEnterpriseHB(null);

                // Adding the last edit info
                userHBToEdit.setUserHBByIdUserLastModification(userInfoHB.getIdUser());
                userHBToEdit.setLastModificationDate(new Date());

                session.update(userHBToEdit);
            }
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            // Check if the error was produces because an invalid user name
            if (e instanceof ConstraintViolationException)
                basicResult.setResultCode(BasicResult.USER_INVALID);

            this.errorManager.reportError(resourceManager, basicResult, "editUser", e);

            return basicResult;
        }

        basicResult.setSuccess(true);
        basicResult.setMessage(UserCommand.resourceManager.getMessage("editUser.success"));

        return basicResult;
    }

    /**
     * Marks an user in Data Base as deleted. This services DOES NOT delete the user from the Data
     * Base
     * 
     * @param userSession
     * @param user The user to be deleted
     * @return A BasicResult object, informing if the user deletion was successful
     */
    protected BasicResult deleteUser(UserSession userSession, User user)
    {

        BasicResult basicResult = new BasicResult();
        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who has called this method
            UserHB userInfoHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Getting from the Data Base the user that is being deleted
            UserHB userHBToDelete = (UserHB) session.get(UserHB.class, user.getId());

            // Marking the user as deleted.
            userHBToDelete.setDeleted(1);

            // Updating other fields
            userHBToDelete.setLastModificationDate(new Date());
            userHBToDelete.setUserHBByIdUserLastModification(userInfoHB.getIdUser());

            // Updating user in Data Base
            session.update(userHBToDelete);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "deleteUser", e);

            return basicResult;
        }

        basicResult.setSuccess(true);
        basicResult.setMessage(UserCommand.resourceManager.getMessage("deleteUser.success"));

        return basicResult;
    }

    /**
     * Closes any existing session for the given users
     * 
     * @param userSession
     * @param users The list of users whose session will be closed
     * @return A BasicResult object, informing if the operation had success
     */
    protected BasicResult closeSessionUsers(UserSession userSession, ArrayList<User> users)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Generating the list of users for the query
            // String userNames = "(";

            if (users.size() > 0)
            {
                String userNames = "(";
                User user;
                for (int i = 0; i < users.size(); i++)
                {
                    user = users.get(i);
                    if (i > 0)
                        userNames = userNames + "," + "'" + user.getUser() + "'";
                    else
                        userNames = userNames + "'" + user.getUser() + "'";
                }
                userNames = userNames + ")";

                session.createSQLQuery("DELETE FROM session WHERE user IN " + userNames)
                    .executeUpdate();
            }

            transaction.commit();

            // Generating result
            basicResult.setSuccess(true);
            basicResult.setMessage(UserCommand.resourceManager
                .getMessage("closeSessionUsers.success"));
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            // Generating error
            this.errorManager.reportError(resourceManager, basicResult, "closeSessionUsers", e);
        }

        return basicResult;
    }

    // ///////////////////////////////////////
    // ENTERPRISES

    /**
     * Gets the List of enterprises from the Data Base. Enterprises marked as deleted will not be
     * returned
     * 
     * @param userSession A UserSession object containing the information of the user that called
     *            this method
     * @param enterpriseListOptions an UserListOptions object containing the options to retrieve the
     *            list of users
     * @return A DataResult object containing an EnterpriseListResult object
     */
    @SuppressWarnings("unchecked")
    protected DataResult<EnterpriseListResult> getEnterprises(UserSession userSession,
        EnterpriseListOptions enterpriseListOptions)
    {
        DataResult<EnterpriseListResult> dataResult = new DataResult<EnterpriseListResult>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            ArrayList<Enterprise> enterpriseList = new ArrayList<Enterprise>();
            Integer totalEnterprises = 0;

            // Getting the user that called this method
            UserHB userHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // getEnterprises has two different authorization resources
            // If the user who called this method, does not match the security level for the
            // authorization resource
            // ENTERPRISE_GET_ALL_ENTERPRISES, this method will only return the enterprise to which
            // the user belongs

            // Getting the authorization resources ENTERPRISE_GET_ALL_ENTERPRISES
            AuthResourceHB authResourceHB =
                (AuthResourceHB) session.createCriteria(AuthResourceHB.class).add(
                    Restrictions.eq("name", "ENTERPRISE_GET_ALL_ENTERPRISES")).uniqueResult();

            // Checking if the user has a security level equal or higher than the necessary for
            // retrieve the whole list of
            // enterprises. Tip: in securityLevel scale, 1 is the greater level of security, and 99
            // the lowest
            if (authResourceHB.getRoleHB().getSecurityLevel().compareTo(
                userHB.getRoleHB().getSecurityLevel()) > -1)
            {
                // GRANTED! This User can view other Enterprises than the one to he belongs

                // Creating enterprises criteria
                Criteria enterprisesListCriteria = session.createCriteria(EnterpriseHB.class);
                Criteria enterprisesCountCriteria = session.createCriteria(EnterpriseHB.class);

                // Removing the enterprises that are deleted
                enterprisesListCriteria.add(Restrictions.eq("deleted", 0));
                enterprisesCountCriteria.add(Restrictions.eq("deleted", 0));

                // Adding filter text
                enterprisesListCriteria.add(Restrictions.like("name", '%' + enterpriseListOptions
                    .getFilter() + '%'));
                enterprisesCountCriteria.add(Restrictions.like("name", '%' + enterpriseListOptions
                    .getFilter() + '%'));

                // Adding order
                enterprisesListCriteria.addOrder(Order.asc("name"));

                // Once we have the criteria...
                // 1. Getting the total number of enterprises that match enterpriseListOptions
                totalEnterprises =
                    (Integer) enterprisesCountCriteria.setProjection(Projections.rowCount())
                        .uniqueResult();

                // 2. Getting the list of enterprises, applying an offset and a max of results
                ArrayList<EnterpriseHB> enterpriseHBList =
                    (ArrayList<EnterpriseHB>) enterprisesListCriteria.setFirstResult(
                        enterpriseListOptions.getOffset()).setMaxResults(
                        enterpriseListOptions.getLength()).list();

                // Building result
                for (EnterpriseHB enterpriseHB : enterpriseHBList)
                {
                    enterpriseList.add((Enterprise) enterpriseHB.toPojo());
                }
            }
            else
            {
                // NOT GRANTED! This User can only view the Enterprise to which he belongs
                enterpriseList.add((Enterprise) userHB.getEnterpriseHB().toPojo());
                totalEnterprises = 1;
            }

            transaction.commit();

            EnterpriseListResult enterpriseListResult = new EnterpriseListResult();
            enterpriseListResult.setEnterprisesList(enterpriseList);
            enterpriseListResult.setTotalEnterprises(totalEnterprises);

            dataResult.setSuccess(true);
            dataResult.setData(enterpriseListResult);
            dataResult.setMessage(UserCommand.resourceManager.getMessage("getEnterprises.success"));
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "getEnterprises", e);
        }

        return dataResult;
    }

    /**
     * Creates a new Enterprise in data base
     * 
     * @param userSession UserSession object with the info of the user that called this method
     * @param enterprise An Enterprise object with the enterprise that will be created
     * @return A DataResult object with success = true and the Enterprise that has been created (if
     *         the creation had success) or success = false otherwise
     */
    protected DataResult<Enterprise> createEnterprise(UserSession userSession, Enterprise enterprise)
    {
        DataResult<Enterprise> dataResult = new DataResult<Enterprise>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who has called this method
            // Getting the user who has called this method
            UserHB userInfoHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            EnterpriseHB enterpriseHB = (EnterpriseHB) enterprise.toPojoHB();
            enterpriseHB.setUserHBByIdUserCreation(userInfoHB);
            enterpriseHB.setCreationDate(new Date());

            session.save(enterpriseHB);

            // Building result
            dataResult.setSuccess(true);
            dataResult.setData((Enterprise) enterpriseHB.toPojo());
            dataResult.setMessage(UserCommand.resourceManager
                .getMessage("createEnterprise.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "createEnterprise", e);
        }

        return dataResult;
    }

    /**
     * Updates an Enterprise in Data Base with new information
     * 
     * @param userSession UserSession object with the info of the user that called this method
     * @param enterprise The enterprise that will be updated
     * @return A BasicResult object with success = true if the edition had success
     */
    protected BasicResult editEnterprise(UserSession userSession, Enterprise enterprise)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who has called this method
            UserHB userInfoHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Getting the enterprise that will be edited
            EnterpriseHB enterpriseHB =
                (EnterpriseHB) session.get(EnterpriseHB.class, enterprise.getId());

            // Updating fields
            enterpriseHB.setName(enterprise.getName());
            enterpriseHB.setDeleted(enterprise.getDeleted() ? 1 : 0);
            enterpriseHB.setUserHBByIdUserLastModification(userInfoHB);
            enterpriseHB.setLastModificationDate(new Date());

            session.update(enterpriseHB);

            // Building result
            basicResult.setSuccess(true);
            basicResult
                .setMessage(UserCommand.resourceManager.getMessage("editEnterprise.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "createEnterprise", e);
        }

        return basicResult;
    }

    /**
     * Marks an Enterprise as deleted. This service DOES NOT deletes the enterprise from the Data
     * Base
     * 
     * @param userSession UserSession object with the info of the user that called this method
     * @param enterprise The enterprise that will be marked as deleted
     * @return A BasicResult object with success = true if the operation had success. success =
     *         false otherwise
     */
    protected BasicResult deleteEnterprise(UserSession userSession, Enterprise enterprise)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the user who has called this method
            UserHB userInfoHB =
                (UserHB) session.createCriteria(UserHB.class).add(
                    Restrictions.eq("user", userSession.getUser())).uniqueResult();

            // Getting the enterprise that will be marked as deleted
            EnterpriseHB enterpriseHB =
                (EnterpriseHB) session.get(EnterpriseHB.class, enterprise.getId());

            // Updating fields
            enterpriseHB.setDeleted(1);
            enterpriseHB.setUserHBByIdUserLastModification(userInfoHB);
            enterpriseHB.setLastModificationDate(new Date());

            session.update(enterpriseHB);

            // Building result
            basicResult.setSuccess(true);
            basicResult.setMessage(UserCommand.resourceManager
                .getMessage("deleteEnterprise.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "deleteEnterprise", e);
        }

        return basicResult;
    }
}
