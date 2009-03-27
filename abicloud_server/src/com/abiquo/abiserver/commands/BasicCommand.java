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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.abiquo.abiserver.business.Proxy;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;

import com.abiquo.abiserver.AbiConfiguration;
import com.abiquo.abiserver.abicloudws.AbiCloudConstants;
import com.abiquo.util.ErrorManager;
import com.abiquo.util.resources.ResourceManager;

/**
 * This class is the base class for all command classes in AbiServer. A command class in AbiServer
 * always extends BasicCommand, and contains methods where server's tasks are performed. There
 * methods implement business logic, Data Base access, and so on. All commands MUST declare all its
 * methods "protected", to ensure that all of them are accessed through the public method "execute".
 * Using the method "execute", we can check that commands are only used by users authorized to do
 * so. If a command declares a public method, that method could be used by an unauthenticated user,
 * and means a public access point to AbiServer
 * 
 * @author Oliver
 */

abstract class BasicCommand
{

    protected final ErrorManager errorManager =
        ErrorManager.getInstance(AbiCloudConstants.ERROR_PREFIX);

    final static private ResourceManager resourceManager = new ResourceManager(BasicCommand.class);

    protected final AbiConfiguration abiConfig = AbiConfiguration.getAbiConfiguration();

    /**
     * Standard access way to a Command class, without asking for user's session. Proxy will
     * determine if the asked method can be used without a session.
     * 
     * @param resource A String array containing in the first position the resource name, for check
     *            authorization and in the second position the command's method name, to call it
     * @param args array with the arguments for the method
     * @return
     */
    public BasicResult execute(String[] resource, Object[] args)
    {

        boolean isAuthorized = true; // proxy.doAuthorization(resource[0]); <-- AUTHORIZATION
                                     // TEMPORALY DISABLED!

        if (isAuthorized)
        {
            return onExecute(resource[1], args);
        }
        else
        {
            return onFaultAuthorization(resource[1]);
        }
    }

    /**
     * Standard access way to a Command class. After checking against Proxy that session is valid,
     * it will call the asked method, and will return its result.
     * 
     * @param session
     * @param resource A String array containing in the first position the resource name, for check
     *            authorization and in the second position the command's method name, to call it
     * @param args
     * @return
     */
    public BasicResult execute(UserSession session, String[] resource, Object[] args)
    {
        // First, we check that the session is still valid
        BasicResult checkSessionResult = Proxy.getInstance().checkSession(session);

        // If the session is valid, we check if this session is authorized to use the asked method
        boolean isAuthorized = false;
        if (checkSessionResult.getSuccess())
        {
            // The session is valid. Checking authorization
            isAuthorized = true; // TODO Proxy.getInstance().doAuthorization(session, resource[0]);
                                 // AUTHORIZATION ONLY ENABLED TO CHECK SESSION

            if (isAuthorized)
            {
                // Everything is OK. We call the method
                return onExecute(resource[1], args);
            }
            else
            {
                // This session is not authorized to call the asked method
                return onFaultAuthorization(session, resource[1]);
            }
        }
        else
        {
            // The session is invalid or has timeout
            return checkSessionResult;
        }

    }

    /**
     * Standard fault response, when someone calls a Command without a session
     * 
     * @param methodName
     * @return
     */
    private BasicResult onFaultAuthorization(String methodName)
    {
        BasicResult faultResult = new BasicResult();

        faultResult.setSuccess(false);

        this.errorManager.reportError(BasicCommand.resourceManager, faultResult,
            "onFaultAuthorization.needsAuthorization", methodName);

        return faultResult;
    }

    /**
     * Fault response when authorization fails when calling a method
     * 
     * @param session
     * @param methodName
     * @return
     */
    private BasicResult onFaultAuthorization(UserSession session, String methodName)
    {
        BasicResult faultResult = new BasicResult();

        faultResult.setSuccess(false);

        this.errorManager.reportError(BasicCommand.resourceManager, faultResult,
            "onFaultAuthorization.noPermission", methodName);

        return faultResult;
    }

    /**
     * When this Command has checked that user's session is valid, the method asked by the user,
     * with the corresponding arguments, will be called here
     * 
     * @param methodName
     * @param args array with the parameters for the method asked by user. null if the method has no
     *            parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    private BasicResult onExecute(String methodName, Object[] args)
    {
        BasicResult result = new BasicResult();

        try
        {
            Class[] argsType;
            if (args != null)
            {
                argsType = new Class[args.length];

                for (int i = 0; i < args.length; i++)
                {
                    argsType[i] = args[i].getClass();
                }
            }
            else
            {
                argsType = new Class[0];
            }

            Method method = this.getClass().getDeclaredMethod(methodName, argsType);
            return (BasicResult) method.invoke(this, args);
        }
        catch (IllegalAccessException e)
        {
            this.errorManager.reportError(BasicCommand.resourceManager, result,
                "onExecute.IllegalAccessException", e, methodName);
        }
        catch (IllegalArgumentException e)
        {
            this.errorManager.reportError(BasicCommand.resourceManager, result,
                "onExecute.IllegalArgumentException", e, methodName);
        }
        catch (InvocationTargetException e)
        {
            this.errorManager.reportError(BasicCommand.resourceManager, result,
                "onExecute.InvocationTargetException", e, methodName);
        }
        catch (NoSuchMethodException e)
        {
            this.errorManager.reportError(BasicCommand.resourceManager, result,
                "onExecute.NoSuchMethodException", e, methodName);
        }
        return result;
    }

}
