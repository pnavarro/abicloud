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

import java.util.ArrayList;

import com.abiquo.abiserver.business.locators.resource.ResourceLocator;
import com.abiquo.abiserver.commands.UserCommand;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.user.Enterprise;
import com.abiquo.abiserver.pojo.user.EnterpriseListOptions;
import com.abiquo.abiserver.pojo.user.User;
import com.abiquo.abiserver.pojo.user.UserListOptions;

/**
 * This class defines all services related to Users management
 * 
 * @author Oliver
 */

public class UserService
{
    /**
     * Returns a list of users stored in the Data Base. Users marked as deleted will not be returned
     * 
     * @param userSession
     * @param userListOptions an UserListOptions object containing the options to retrieve the list
     *            of users
     * @return A DataResult object containing an UserListResult object
     */
    public BasicResult getUsers(UserSession userSession, UserListOptions userListOptions)
    {
        UserCommand userCommand = new UserCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = userListOptions;

        return userCommand.execute(userSession, ResourceLocator.USER_GETUSERS, args);
    }

    /**
     * Creates a new User in the Data Base
     * 
     * @param userSession
     * @param user the User to be created
     * @return A DataResult object containing the a User object with the user created in the Data
     *         Base
     */
    public BasicResult createUser(UserSession userSession, User user)
    {
        UserCommand userCommand = new UserCommand();
        Object[] args = new Object[2];

        args[0] = userSession;
        args[1] = user;

        return userCommand.execute(userSession, ResourceLocator.USER_CREATE, args);
    }

    /**
     * Marks an user in Data Base as deleted. This services DOES NOT delete the user from the Data
     * Base
     * 
     * @param userSession
     * @param user The user to be deleted
     * @return A BasicResult object, informing if the user deletion was successful
     */
    public BasicResult deleteUser(UserSession userSession, User user)
    {
        UserCommand userCommand = new UserCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = user;

        return userCommand.execute(userSession, ResourceLocator.USER_DELETE, args);
    }

    /**
     * Modifies a User that already exists in the Data Base
     * 
     * @param userSession
     * @param users A list of users to be modified
     * @return A BasicResult object, informing if the user edition was successful
     */
    public BasicResult editUser(UserSession userSession, ArrayList<User> users)
    {
        UserCommand userCommand = new UserCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        // We need to do this because users is casted as an flex.messaging.io.ArrayCollection
        args[1] = new ArrayList<User>(users);

        return userCommand.execute(userSession, ResourceLocator.USER_EDIT, args);
    }

    /**
	 * Closes any existing session for the given users
	 * 
	 * @param userSession
	 * @param users	The list of users whose session will be closed. If null, all current active sessions
	 * will be closed, except the userSession
	 * @return A BasicResult object, informing if the operation had success
	 */
	public BasicResult closeSessionUsers(UserSession userSession, ArrayList<User> users)
	{
		UserCommand userCommand = new UserCommand();
		Object[] args;
		
		if(users != null)
		{
			args = new Object[2];
			args[0] = userSession;
			args[1] = new ArrayList<User>(users);
		}
		else
		{
			args = new Object[1];
			args[0] = userSession;
		}
 		
		return userCommand.execute(userSession, ResourceLocator.USERS_CLOSE_SESSION, args);
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
    public BasicResult getEnterprises(UserSession userSession,
        EnterpriseListOptions enterpriseListOptions)
    {
        UserCommand userCommand = new UserCommand();

        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = enterpriseListOptions;

        return userCommand.execute(userSession, ResourceLocator.ENTERPRISE_GET_ENTERPRISES, args);
    }

    /**
     * Creates a new Enterprise in data base
     * 
     * @param userSession UserSession object with the info of the user that called this method
     * @param enterprise An Enterprise object with the enterprise that will be created
     * @return A DataResult object with success = true and the Enterprise that has been created (if
     *         the creation had success) or success = false otherwise
     */
    public BasicResult createEnterprise(UserSession userSession, Enterprise enterprise)
    {
        UserCommand userCommand = new UserCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = enterprise;

        return userCommand.execute(userSession, ResourceLocator.ENTERPRISE_CREATE, args);
    }

    /**
     * Updates an Enterprise in Data Base with new information
     * 
     * @param userSession UserSession object with the info of the user that called this method
     * @param enterprise The enterprise that will be updated
     * @return A BasicResult object with success = true if the edition had success
     */
    public BasicResult editEnterprise(UserSession userSession, Enterprise enterprise)
    {
        UserCommand userCommand = new UserCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = enterprise;

        return userCommand.execute(userSession, ResourceLocator.ENTERPRISE_EDIT, args);
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
    public BasicResult deleteEnterprise(UserSession userSession, Enterprise enterprise)
    {
        UserCommand userCommand = new UserCommand();
        Object[] args = new Object[2];
        args[0] = userSession;
        args[1] = enterprise;

        return userCommand.execute(userSession, ResourceLocator.ENTERPRISE_DELETE, args);
    }
}
