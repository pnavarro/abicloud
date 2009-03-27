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
 * Consell de Cent 296 principal 2�, 08007 Barcelona, Spain.
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License",
 * available at http://cpal.abiquo.com), in which case the provisions of CPAL
 * License are applicable instead of those above. In relation of this portions 
 * of the Code, a Legal Notice according to Exhibits A and B of CPAL Licence 
 * should be provided in any distribution of the corresponding Code to Graphical
 * User Interface.
 */
package com.abiquo.abicloud.model;

import java.net.URL;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;

/**
 * The Interface IHypervisor.
 */
public interface IHypervisor
{

    // TODO: required default constructor !!!

    /**
     * Connects to the hypervisor.
     * 
     * @param url the url
     */
    public void connect(URL url);

    /**
     * Gets the address.
     * 
     * @return the address
     */
    public URL getAddress();

    /**
     * Logins in with the user & password.
     * 
     * @param user the user
     * @param password the password
     */
    public void login(String user, String password);

    /**
     * Logs out the hypervisor user
     */
    public void logout();

    /**
     * Creates a new virtual machine.
     * 
     * @param config the config
     * @return the abs virtual machine
     * @throws VirtualMachineException the virtual machine exception
     */
    public AbsVirtualMachine createMachine(VirtualMachineConfiguration config)
        throws VirtualMachineException;

    /**
     * Returns the Hypervisor this class is wrapping.
     * 
     * @return the hypervisor type
     */
    public String getHypervisorType();

    /**
     * Initializes the hypervisor
     * 
     * @param url TODO
     */
    public void init(URL url);

}
