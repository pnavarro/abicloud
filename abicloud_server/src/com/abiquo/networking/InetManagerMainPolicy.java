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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.abiquo.abiserver.business.hibernate.pojohb.networking.DatacentersIPHB;
import com.abiquo.abiserver.business.hibernate.pojohb.networking.VirtualMachineIPHB;

/**
 * This class implements the first policy to assign IP to virtualMachines and IP-Rang to
 * datacenters. Inside the 10.0.0.0 - 10.255.255.255 class-A network, we will provide the next
 * N-nodes available when a datacenter rang is requested. When a new virtual machine IP is
 * requested, first we check if its datacenter is
 * 
 * @author abiquo
 */
public class InetManagerMainPolicy extends InetManager
{

    /**
     * This variable DAO contains the database access to table 'datacenters_ip'
     */
    private GenericHibernateDAO<DatacentersIPHB, Integer> datacentersDAO;

    /**
     * This variable DAO contains all the database access to table 'vm_ip'
     */
    private GenericHibernateDAO<VirtualMachineIPHB, String> virtualMachineDAO;

    /**
     * Number of nodes will have every virtual datacenter in Database.
     */
    private Integer datacenterNodes;

    /**
     * Number of datacenters we can allocate depending on datacenterNodes
     */
    private Integer numberOfDatacenters;

    /**
     * Class constructor
     * 
     * @param session We need an HibernateSession in order to don't throw exceptions in Hibernate
     *            queries
     */
    @SuppressWarnings("unchecked")
    public InetManagerMainPolicy(Session session, Integer datacenterNodes)
    {
        // TODO: leer del fichero de configuracion
        this.datacenterNodes = datacenterNodes;
        numberOfDatacenters = 16777216 / datacenterNodes;
        datacentersDAO = new GenericHibernateDAO(DatacentersIPHB.class, session);
        virtualMachineDAO = new GenericHibernateDAO(VirtualMachineIPHB.class, session);
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.InetManager#deleteDataCenterRang(java.lang.String)
     */
    @Override
    public InetManagerStatus deleteDataCenterRang(Integer dataCenterId)
    {
        // check for errors
        if (dataCenterId == null)
        {
            System.err.print("You are trying to delete the 'null' datacenter!");
            System.err.println("Check your code");
            return InetManagerStatus.LOGIC_ERROR;
        }

        DatacentersIPHB reqDatacenter = (DatacentersIPHB) datacentersDAO.findById(dataCenterId);

        if (reqDatacenter == null)
        {
            System.err.println("Datacenter Id " + dataCenterId + " not found in DataBase.");
            return InetManagerStatus.LOGIC_ERROR;
        }
        else
        {
            try
            {
                datacentersDAO.makeTransient(reqDatacenter);
                return InetManagerStatus.SUCCESS;
            }
            catch (HibernateException e)
            {
                System.err.println("Unexpected error trying to access to DB");
                return InetManagerStatus.DATABASE_ERROR;
            }
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.InetManager#deleteIPAddressVM(java.lang.String, java.lang.String)
     */
    @Override
    public InetManagerStatus deleteIPAddressVM(String virtualMachineId)
    {
        // check for errors
        if (virtualMachineId == null)
        {
            System.err.print("You are trying to delete the 'null' virtual machine!");
            System.err.println("Check your code!");
            return InetManagerStatus.LOGIC_ERROR;
        }

        VirtualMachineIPHB reqVM =
            (VirtualMachineIPHB) virtualMachineDAO.findById(virtualMachineId);

        if (reqVM == null)
        {
            System.err
                .println("Virtual Machine Id " + virtualMachineId + " not found in DataBase.");
            return InetManagerStatus.LOGIC_ERROR;
        }
        else
        {
            try
            {
                virtualMachineDAO.makeTransient(reqVM);
                return InetManagerStatus.SUCCESS;
            }
            catch (HibernateException e)
            {
                System.err.println("Unexpected error trying to access to DB");
                return InetManagerStatus.DATABASE_ERROR;
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.InetManager#requestDataCenterRang(java.lang.String,
     * java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public IPNetworkRang requestDataCenterRang(Integer dataCenterId)
    {
        // Network rang we will return.
        IPNetworkRang reqNetRang = null;
        DatacentersIPHB reqDatacenter = null;

        // checking possible fails...
        if (this.remainingDatacenters() == 0)
        {
            System.err.println("No more datacenters available to store in DataBase");
            return null;
        }
        if (dataCenterId == null)
        {
            System.err.print("You can not pass 'null' as dataCenterId!!! ");
            System.err.println("Check your code!!!");
            return null;
        }

        // check if dataCenterId is already in database:
        reqDatacenter = (DatacentersIPHB) datacentersDAO.findById(dataCenterId);

        if (reqDatacenter != null)
        {
            // Datacenter already exists
            reqNetRang = this.setNetworkRang(reqDatacenter);
        }
        else
        {
            List<DatacentersIPHB> listDC = (ArrayList<DatacentersIPHB>) datacentersDAO.findAll();

            // pass list of <DatacentersIPHB> to list of strings with first IP
            List<String> listFirstIP = new ArrayList<String>();
            for (int i = 0; i < listDC.size(); i++)
            {
                listFirstIP.add(listDC.get(i).getFirstIP());
            }

            boolean registeredRang = true;
            Integer remainingDatacenters = numberOfDatacenters;

            // we can be sure of one point: All the registers in the same cloud should have the
            // same rang.
            // So, we can calculate the empty registers between rangs.
            IPAddress nextIPAddress = IPAddress.newIPAddress("10.0.0.0");
            while (remainingDatacenters > 0 && registeredRang)
            {
                if (!listFirstIP.contains(nextIPAddress.toString()))
                {
                    // we set the variable to go out the loop
                    registeredRang = false;

                    reqDatacenter = new DatacentersIPHB();
                    reqDatacenter.setId(dataCenterId);
                    reqDatacenter.setFirstIP(nextIPAddress.toString());
                    reqDatacenter.setLastIP(nextIPAddress
                        .lastIPAddressWithNumNodes(datacenterNodes).toString());
                    reqDatacenter.setNumNodes(datacenterNodes);

                    // we store in database
                    datacentersDAO.makePersistent(reqDatacenter);

                    // we set the variable to return
                    reqNetRang = this.setNetworkRang(reqDatacenter);
                }
                else
                {
                    nextIPAddress = nextIPAddress.lastIPAddressWithNumNodes(datacenterNodes + 1);
                    remainingDatacenters--;
                }
            }
        }
        return reqNetRang;
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.InetManager#requestIPAddressVM(java.lang.String, java.lang.String)
     */
    @Override
    public IPAddress requestIPAddressVM(Integer dataCenterId, String virtualMachineId)
    {
        IPAddress reqAddress = null;
        VirtualMachineIPHB reqVM = null;

        // check the correct parameters
        if (dataCenterId == null || virtualMachineId == null)
        {
            return null;
        }

        // Check if datacenter exists
        DatacentersIPHB reqDatacenter = (DatacentersIPHB) datacentersDAO.findById(dataCenterId);
        if (reqDatacenter == null)
        {
            return null;
        }

        // Check if virtual machine exists
        reqVM = (VirtualMachineIPHB) virtualMachineDAO.findById(virtualMachineId);
        if (reqVM != null)
        {
            // Check if virtual machine belongs to the given datacenter
            if (reqVM.getDatacenterId().compareTo(reqDatacenter.getId()) == 0)
            {
                reqAddress = IPAddress.newIPAddress(reqVM.getVirtualIP());
            }
            // else will return null
        }
        else
        {
            // getting all the virtual machines filtering by datacenter Id
            Criterion filter = Restrictions.like("datacenterId", dataCenterId);
            List<VirtualMachineIPHB> listVM = virtualMachineDAO.findByCriteria(filter);

            // check if all the nodes of the datacenter are busy
            if (listVM.size() == reqDatacenter.getNumNodes())
            {
                System.out.println("No more virtual machines available in this Datacenter!");
                return null;
            }

            // first, we set the list of IP's
            List<String> listIP = new ArrayList<String>();
            for (int i = 0; i < listVM.size(); i++)
            {
                listIP.add(listVM.get(i).getVirtualIP());
            }

            // initialize the variables to assign new IP
            boolean registeredIP = true;
            Integer numberIPs = reqDatacenter.getNumNodes();
            IPAddress nextIP = IPAddress.newIPAddress(reqDatacenter.getFirstIP()).nextIPAddress();

            while (numberIPs > 0 && registeredIP)
            {
                if (!listIP.contains(nextIP.toString()))
                {
                    reqAddress = nextIP;

                    // set the entity to store
                    reqVM = new VirtualMachineIPHB();
                    reqVM.setId(virtualMachineId);
                    reqVM.setDatacenterId(dataCenterId);
                    reqVM.setVirtualIP(reqAddress.toString());

                    // store it
                    virtualMachineDAO.makePersistent(reqVM);

                    // set the variable to exit the bucle
                    registeredIP = false;
                }
                else
                {
                    nextIP = nextIP.nextIPAddress();
                    numberIPs--;
                }

            }

        }
        return reqAddress;
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.InetManager#getNumberDatacenters()
     */
    @Override
    public Integer getNumberDatacenters()
    {
        return numberOfDatacenters;
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.InetManager#remainingDatacenters()
     */
    @Override
    public Integer remainingDatacenters()
    {
        return numberOfDatacenters - datacentersDAO.findAll().size();
    }

    /**
     * Return a new IPNetworkRang for a given DatacenterIPHB Pojo
     */
    protected IPNetworkRang setNetworkRang(DatacentersIPHB rang)
    {
        return new IPNetworkRang(IPAddress.newIPAddress(rang.getFirstIP()), IPAddress
            .newIPAddress(rang.getLastIP()), rang.getNumNodes());
    }

}
