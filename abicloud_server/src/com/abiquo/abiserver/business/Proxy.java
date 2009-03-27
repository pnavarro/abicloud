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
package com.abiquo.abiserver.business;

import com.abiquo.abiserver.business.authentication.AuthenticationManagerDB;
import com.abiquo.abiserver.business.authentication.IAuthenticationManager;
import com.abiquo.abiserver.business.authorization.IAuthorizationManager;
import com.abiquo.abiserver.pojo.authentication.Login;
import com.abiquo.abiserver.pojo.authentication.LoginResult;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;

/**
 * This class provides authentication and authorization features, making a bridge between a class
 * that needs these features, and the classes that provides them.
 * 
 * @author Oliver
 */

public class Proxy
{

    // Singleton class
    private static Proxy instance;

    // Object that provides the authentication features
    private IAuthenticationManager authenticationManager;

    // Object that provides the authorization features
    private IAuthorizationManager authorizationManager;

    private Proxy()
    {
        // TODO Instantiate convenient authentication and authorization managers
        authenticationManager = new AuthenticationManagerDB();
    }

    public static Proxy getInstance()
    {
        if (instance == null)
            instance = new Proxy();

        return instance;
    }

    /**
     * Calls the Authentication Manager to perform a controlled login.
     * 
     * @param login
     * @return A Session object with session information if the login process had success Null if
     *         login action was unsuccessful
     */
    public DataResult<LoginResult> doLogin(Login login)
    {
        return authenticationManager.doLogin(login);
    }

    /**
     * Calls the Authentication Manager to perform a controlled logout
     * 
     * @param session The session that wants to logout from the server
     * @return
     */
    public BasicResult doLogout(UserSession session)
    {
        return authenticationManager.doLogout(session);
    }

    /**
     * Calls the Authentication Manager to check if a session is valid
     * 
     * @param session The session to check
     * @return true if a session is still valid
     */
    public BasicResult checkSession(UserSession session)
    {
        return authenticationManager.checkSession(session);
    }

    /**
     * Calls the Authorization Manager to check if this method can be used without a session
     * 
     * @param methodName
     * @return true if this method can be used without start a session
     */
    public boolean doAuthorization(String methodName)
    {
        return authorizationManager.checkAuthorization(null, methodName);
    }

    /**
     * Calls the Authorization Manager to check if this session is authorized to use this method
     * 
     * @param session
     * @param methodName
     * @return true if this session is authorized to use this method
     */
    public boolean doAuthorization(UserSession session, String methodName)
    {
        // TODO return authorizationManager.checkAuthorization(session, methodName);
        return true;
    }

}
