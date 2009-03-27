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

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.abiquo.abiserver.business.Proxy;
import com.abiquo.abiserver.business.hibernate.pojohb.authorization.AuthClientResourceHB;
import com.abiquo.abiserver.business.hibernate.pojohb.authorization.AuthClientresourceExceptionHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.authentication.Login;
import com.abiquo.abiserver.pojo.authentication.LoginResult;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.authorization.Resource;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;

/**
 * This command collects all actions related to Login actions
 * 
 * @author Oliver
 */

public class LoginCommand extends BasicCommand
{

    final static private ResourceManager resourceManager = new ResourceManager(LoginCommand.class);

    /**
     * Performs a Login action. Uses Proxy to be able to access to Authentication Manager
     * 
     * @param loginData necessary data to perform a login action
     * @return a DataResult object, containing a LoginResult with the user's session, information
     *         and client resources
     */
    @SuppressWarnings("unchecked")
    protected DataResult<LoginResult> login(Login loginData)
    {
        DataResult<LoginResult> resultResponse = Proxy.getInstance().doLogin(loginData);

        if (resultResponse.getSuccess())
        {
            // Generating the list of client resources for the user that has logged in
            Session session = null;
            Transaction transaction = null;

            ArrayList<Resource> userResources = new ArrayList<Resource>();

            try
            {
                session = HibernateUtil.getSession();
                transaction = session.beginTransaction();

                // Getting the user that is being loggin in
                UserHB userHBLogged =
                    (UserHB) session.get(UserHB.class, resultResponse.getData().getUser().getId());

                // Getting the list of all client resources
                ArrayList<AuthClientResourceHB> allClientResourcesHB =
                    (ArrayList<AuthClientResourceHB>) session.createCriteria(
                        AuthClientResourceHB.class).list();

                AuthClientresourceExceptionHB authClientresourceExceptionHB;
                for (AuthClientResourceHB authClientResourceHB : allClientResourcesHB)
                {
                    // Checking if there is any exception for this client resource and this user
                    authClientresourceExceptionHB = null;
                    authClientresourceExceptionHB =
                        (AuthClientresourceExceptionHB) session.createCriteria(
                            AuthClientresourceExceptionHB.class).add(
                            Restrictions.eq("userHB", userHBLogged)).add(
                            Restrictions.eq("authResourceHB", authClientResourceHB)).uniqueResult();

                    int priorAuth =
                        authClientResourceHB.getRoleHB().getSecurityLevel().compareTo(
                            userHBLogged.getRoleHB().getSecurityLevel());
                    if (priorAuth >= 0)
                    {
                        // User has authorization for this client resource. Checking if there is any
                        // exception for that
                        if (authClientresourceExceptionHB == null)
                            // No exceptions. Adding the client resource for this user
                            userResources.add((Resource) authClientResourceHB.toPojo());
                        else
                        {
                            // There is an exception, so this user is not authorized to use this
                            // client resource
                            // We do not add this client resource
                        }
                    }
                    else
                    {
                        // User is not authorized for this client resource. Checking if there is any
                        // exception for that
                        if (authClientresourceExceptionHB != null)
                            // An exception exists, so this user is authorized. Adding the client
                            // resource
                            userResources.add((Resource) authClientResourceHB.toPojo());
                        else
                        {
                            // No exception exists, so we do not add this client resource
                        }
                    }
                }

                transaction.commit();

            }
            catch (Exception e)
            {
                if (transaction != null && transaction.isActive())
                    transaction.rollback();

                this.errorManager.reportError(LoginCommand.resourceManager, resultResponse,
                    "login.resourceCreation", e);

                return resultResponse;
            }

            // Returning result
            resultResponse.getData().setClientResources(userResources);
        }

        return resultResponse;
    }

    /**
     * Performs a Logout action. Uses the Proxy to be able to access to Authentication Manager
     * 
     * @param session The session that we want to logout from the server
     * @return
     */
    protected BasicResult logout(UserSession session)
    {
        return Proxy.getInstance().doLogout(session);
    }
}
