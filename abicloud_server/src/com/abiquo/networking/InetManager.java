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
package com.abiquo.networking;

/**
 * Abstract class to determine the methods in order to be an abiCloud IPAdress Manager
 * 
 * @author abiquo
 */
public abstract class InetManager
{
    /**
     * Status we can find deleting dataCenters or virtual machines.
     */
    public enum InetManagerStatus
    {
        SUCCESS, LOGIC_ERROR, DATABASE_ERROR
    };

    /**
     * Provides a rang of IP's for a given DataCenter.
     * 
     * @param dataCenterId id of the Data Center we request a Rang. rang
     * @return new IPNetworkRang if dataCenterId didn't request before; previous IPNetworkRang if
     *         dataCenter already request a rang; null if dataCenterId didn't request before and no
     *         more dataCenter available
     */
    public abstract IPNetworkRang requestDataCenterRang(Integer dataCenterId);

    /**
     * Provides an IP Address for a given dataCenter and virtual Machine.
     * 
     * @param dataCenterId id of the Data Center we request a IP Address virtualMachineId number of
     *            the virtual machine which has the IP Address
     * @return a new IPAddress if virtualMachineId didn't request before an IP Address; stored
     *         IPAddres if virtualMachineId had already an IP Address; null if virtualMachineId
     *         didn't request before an IP Address and no more IPAddress available in the dataCenter
     *         rang. If virtual machine exists but doesn't belong to the given datacenter, also
     *         return null.
     */
    public abstract IPAddress requestIPAddressVM(Integer dataCenterId, String virtualMachineId);

    /**
     * Deletes a DataCenter and its virtualMachines IP's in data base.
     * 
     * @param dataCenterId we want to delete
     * @return InetManagerStatus
     */
    public abstract InetManagerStatus deleteDataCenterRang(Integer dataCenterId);

    /**
     * Delete a virtualMachine IP in database
     * 
     * @param virtualMachineId id of the virtual machine we want to delete
     * @return InetManagerStatus
     */
    public abstract InetManagerStatus deleteIPAddressVM(String virtualMachineId);

    /**
     * A method to query how many datacenters we can register
     */
    public abstract Integer getNumberDatacenters();

    /**
     * A method to query how many datacenters registers are available in Database.
     * 
     * @return Number of datacenters available.
     */
    public abstract Integer remainingDatacenters();

};
