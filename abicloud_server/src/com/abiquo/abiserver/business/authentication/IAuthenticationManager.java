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

import com.abiquo.abiserver.pojo.authentication.Login;
import com.abiquo.abiserver.pojo.authentication.LoginResult;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;

/**
 * These interface defines the methods that a class that wants to provide Authentication features
 * must implement
 * 
 * @author Oliver
 */

public interface IAuthenticationManager
{

    /**
     * Performs a Login action
     * 
     * @param login
     * @return A DataResult object with a LoginResult object containing the user who has logged in
     *         and his session. If there was a problem with the login process, the DataResult will
     *         containing the information with the problem
     */
    public DataResult<LoginResult> doLogin(Login login);

    /**
     * Performs a standard Logout action, destroying the session
     * 
     * @param session to destroy
     * @return A BasicResult object with success = true if the logout action had success
     */
    public BasicResult doLogout(UserSession session);

    /**
     * Checks if a session object is still valid
     * 
     * @param session to check
     * @return A BasicResult containing the result of the check session
     */
    public BasicResult checkSession(UserSession session);
}
