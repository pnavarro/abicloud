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

import com.abiquo.util.resources.ResourceManager;
import java.util.ArrayList;

import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorTypeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.SoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.RoleHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisorType;
import com.abiquo.abiserver.pojo.infrastructure.SO;
import com.abiquo.abiserver.pojo.main.MainResult;
import com.abiquo.abiserver.pojo.result.DataResult;
import com.abiquo.abiserver.pojo.user.Role;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * This command collects all actions related to Main Application actions
 * 
 * @author Oliver
 */

public class MainCommand extends BasicCommand
{

    private static final ResourceManager resourceManager = new ResourceManager(MainCommand.class);

    /**
     * Returns the the common information that the application needs when has loaded
     * 
     * @param userSession A UserSession object with the user that called this method
     * @return a DataResult Object, containing a MainResult Object with all the necessary
     *         information
     */
    @SuppressWarnings("unchecked")
    protected DataResult<MainResult> getCommonInformation(UserSession userSession)
    {
        DataResult<MainResult> dataResult = new DataResult<MainResult>();
        ArrayList<SO> osList = new ArrayList<SO>();
        ArrayList<Role> rolesList = new ArrayList<Role>();
        ArrayList<HyperVisorType> hypervisorTypesList = new ArrayList<HyperVisorType>();

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

            // 1 - Retrieving the list of OS
            ArrayList<SoHB> ossHB =
                (ArrayList<SoHB>) session.createCriteria(SoHB.class).addOrder(
                    Order.asc("description")).list();

            SO so;
            for (SoHB soHB : ossHB)
            {
                so = (SO) soHB.toPojo();
                osList.add(so);
            }

            // 2 - Retrieving the list of Roles
            // Only the Roles with a security level equal or less than the user who called this
            // method will be returned
            ArrayList<RoleHB> rolesHB =
                (ArrayList<RoleHB>) session.createCriteria(RoleHB.class).addOrder(
                    Order.asc("shortDescription")).list();

            for (RoleHB roleHB : rolesHB)
            {
                // Tip: in securityLevel scale, 1 is the greater level of security, and 99 the
                // lowest
                if (roleHB.getSecurityLevel().compareTo(userHB.getRoleHB().getSecurityLevel()) > -1)
                {
                    // This user can view this role
                    rolesList.add((Role) roleHB.toPojo());
                }
            }

            // 3 - Retrieving the list of HyperVisorType
            ArrayList<HypervisorTypeHB> hypervisorsTypeHB =
                (ArrayList<HypervisorTypeHB>) session.createCriteria(HypervisorTypeHB.class)
                    .addOrder(Order.asc("name")).list();

            for (HypervisorTypeHB hypervisorTypeHB : hypervisorsTypeHB)
            {
                hypervisorTypesList.add((HyperVisorType) hypervisorTypeHB.toPojo());
            }

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            dataResult.setSuccess(false);
            dataResult.setMessage(e.getMessage());

            this.errorManager.reportError(MainCommand.resourceManager, dataResult,
                "getCommonInformation.Exception", e);

            return dataResult;
        }

        MainResult mainResult = new MainResult();
        mainResult.setOperatingSystems(osList);
        mainResult.setRoles(rolesList);
        mainResult.setHypervisorTypes(hypervisorTypesList);

        dataResult.setData(mainResult);
        dataResult.setSuccess(true);
        dataResult.setMessage(MainCommand.resourceManager
            .getMessage("getCommonInformation.success"));

        return dataResult;
    }
}
