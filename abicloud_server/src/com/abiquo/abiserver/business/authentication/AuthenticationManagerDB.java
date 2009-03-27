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
package com.abiquo.abiserver.business.authentication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.abiquo.abiserver.AbiConfiguration;

import com.abiquo.abiserver.abicloudws.AbiCloudConstants;
import com.abiquo.util.ErrorManager;
import com.abiquo.util.resources.ResourceManager;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.authentication.Login;
import com.abiquo.abiserver.pojo.authentication.LoginResult;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;
import com.abiquo.abiserver.pojo.user.User;

/**
 * This Authentication Manager provides authentication services, with a Data Base backend
 * 
 * @author Oliver
 */
public class AuthenticationManagerDB implements IAuthenticationManager
{

    private final AbiConfiguration abiConfig = AbiConfiguration.getAbiConfiguration();

    private static final ResourceManager resourceManger =
        new ResourceManager(AuthenticationManagerDB.class);

    private final ErrorManager errorManager =
        ErrorManager.getInstance(AbiCloudConstants.ERROR_PREFIX);

    @SuppressWarnings("unchecked")
    public DataResult<LoginResult> doLogin(Login login)
    {
        DataResult<LoginResult> dataResult = new DataResult<LoginResult>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();

            transaction = session.beginTransaction();

            // Checking if a user exists for the given credentials (remeber to check that the user
            // is not deleted!)
            String customQuery =
                "SELECT * FROM user WHERE user='" + login.getUser() + "' AND MD5(password)='"
                    + login.getPassword() + "' AND deleted = 0";
            UserHB userHB =
                (UserHB) session.createSQLQuery(customQuery).addEntity(UserHB.class).uniqueResult();

            if (userHB != null)
            {
                // User exists. Check if it is active
                if (userHB.getActive() == 1)
                {
                    // User exists in database and is active.

                    // Looking for all existing active sessions of this user, ordered by when were
                    // created
                    ArrayList<UserSession> oldUserSessions =
                        (ArrayList<UserSession>) session.createCriteria(UserSession.class).add(
                            Restrictions.eq("user", login.getUser())).addOrder(Order.desc("key"))
                            .list();
                    Date currentTime = new Date();

                    // Get the maximum number of sessions - a value of 0 indicates no limit
                    int maxNumSessions = this.abiConfig.getMaxNumSessions();

                    // We erase old expired sessions, or those that exceed the maximum number of
                    // simultaneous active sessions
                    // trying to keep the newer ones
                    int currentActiveSessions = 0;
                    for (UserSession existingSession : oldUserSessions)
                    {
                        if (currentTime.after(existingSession.getExpireDate()))
                            session.delete(existingSession);
                        else
                        {
                            currentActiveSessions++;
                            if (maxNumSessions > 0 && currentActiveSessions >= maxNumSessions)
                                session.delete(existingSession);
                        }
                    }

                    // Creating the user session
                    UserSession userSession = new UserSession();
                    userSession.setUser(userHB.getUser());
                    userSession.setKey(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    userSession.setLocale(login.getLocale());

                    int sessionTimeout = this.abiConfig.getSessionTimeout();
                    long expireMilis = (new Date()).getTime() + sessionTimeout * 60 * 1000;
                    Date expireDate = new Date(expireMilis);
                    userSession.setExpireDate(expireDate);

                    // Saving in Data Base the created User Session
                    session.save(userSession);

                    // Generating the login result, with the user who has logged in and his session
                    LoginResult loginResult = new LoginResult();
                    loginResult.setSession(userSession);
                    loginResult.setUser((User) userHB.toPojo());

                    // Generating the DataResult
                    dataResult.setSuccess(true);
                    dataResult.setMessage(AuthenticationManagerDB.resourceManger
                        .getMessage("doLogin.success"));
                    dataResult.setData(loginResult);

                    /*
                     * if(currentActiveSessions < maxNumSessions || maxNumSessions == 0) { } else {
                     * //The maximum number of simultaneous sessions has been reached
                     * this.errorManager.reportError(resourceManger,
                     * dataResult,"doLogin.sessionExpired");
                     * dataResult.setResultCode(BasicResult.SESSION_MAX_NUM_REACHED); }
                     */

                }
                else
                {
                    // User is not active. Generating the DataResult
                    this.errorManager.reportError(resourceManger, dataResult,
                        "doLogin.userInActive");
                }
            }
            else
            {
                // User not exists in database or bad credentials. Generating the DataResult
                this.errorManager.reportError(resourceManger, dataResult,
                    "doLogin.passwordUserIncorrect");

                dataResult.setResultCode(BasicResult.USER_INVALID);
            }

            transaction.commit();

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManger, dataResult, "doLogin.exception", e);
        }

        return dataResult;
    }

    public BasicResult doLogout(UserSession userSession)
    {
        BasicResult basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Deleting the user session
            UserSession previousSession =
                (UserSession) session.createCriteria(UserSession.class).add(
                    Restrictions.eq("user", userSession.getUser())).add(
                    Restrictions.eq("key", userSession.getKey())).uniqueResult();

            if (previousSession != null)
                session.delete(previousSession);

            basicResult.setSuccess(true);
            basicResult.setMessage(AuthenticationManagerDB.resourceManger
                .getMessage("doLogout.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManger, basicResult, "doLogout", e);
        }

        return basicResult;
    }

    public BasicResult checkSession(UserSession userSession)
    {
        BasicResult checkSessionResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        UserSession sessionToCheck = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            sessionToCheck =
                (UserSession) HibernateUtil.getSession().createCriteria(UserSession.class).add(
                    Restrictions.eq("user", userSession.getUser())).add(
                    Restrictions.eq("key", userSession.getKey())).uniqueResult();

            if (sessionToCheck == null)
            {
                // The session does not exist, so is not valid
                checkSessionResult.setResultCode(BasicResult.SESSION_INVALID);
                this.errorManager.reportError(resourceManger, checkSessionResult,
                    "checkSession.invalid");
            }
            else
            {
                // Checking if the session has expired
                Date currentDate = new Date();
                if (currentDate.before(sessionToCheck.getExpireDate()))
                {
                    // The session is valid updating the expire Date
                    int sessionTimeout = this.abiConfig.getSessionTimeout();
                    long expireMilis = (new Date()).getTime() + sessionTimeout * 60 * 1000;
                    Date expireDate = new Date(expireMilis);
                    sessionToCheck.setExpireDate(expireDate);

                    session.update(sessionToCheck);

                    checkSessionResult.setSuccess(true);
                    checkSessionResult.setMessage(AuthenticationManagerDB.resourceManger
                        .getMessage("checkSession.success"));
                }
                else
                {
                    // The session has time out. Deleting the session from Data Base
                    session.delete(sessionToCheck);

                    checkSessionResult.setResultCode(BasicResult.SESSION_TIMEOUT);
                    this.errorManager.reportError(resourceManger, checkSessionResult,
                        "checkSession.expired");
                }

            }

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManger, checkSessionResult,
                "checkSession.exception", e);
        }

        return checkSessionResult;
    }

    public static void main(String[] args)
    {

        String username = "admin";
        String password = com.abiquo.util.ToString.MD5("xabiquo");

        Login login = new Login();
        login.setUser(username);
        login.setPassword(password);
        AuthenticationManagerDB m = new AuthenticationManagerDB();
        m.doLogin(login);

    }

}
