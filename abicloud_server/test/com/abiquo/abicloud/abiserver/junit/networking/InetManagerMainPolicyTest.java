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
package com.abiquo.abicloud.abiserver.junit.networking;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.networking.IPAddress;
import com.abiquo.networking.IPNetworkRang;
import com.abiquo.networking.InetManager;
import com.abiquo.networking.InetManagerMainPolicy;

public class InetManagerMainPolicyTest extends TestCase
{

    /**
     * Hibernate session.
     */
    Session session;

    /**
     * Inet Manager we choose to serve IP's
     */
    InetManagerMainPolicy inetManager;

    @Before
    public void setUp() throws Exception
    {
        session = HibernateUtil.getSession();
        session.beginTransaction();
        inetManager = new InetManagerMainPolicy(session, 2096);
    }

    @After
    public void tearDown() throws Exception
    {
        session.getTransaction().commit();
    }

    @Test
    public void testRequestAndDeleteDataCenterRang()
    {
        // request
        IPNetworkRang netRang1 = inetManager.requestDataCenterRang(11111111);
        assertTrue(netRang1 != null);
        IPNetworkRang netRang2 = inetManager.requestDataCenterRang(22222222);
        assertTrue(netRang2 != null);
        IPNetworkRang netRang3 = inetManager.requestDataCenterRang(33333333);
        assertTrue(netRang3 != null);
        IPNetworkRang netRang4 = inetManager.requestDataCenterRang(44444444);
        assertTrue(netRang4 != null);
        IPNetworkRang netRang5 = inetManager.requestDataCenterRang(55555555);
        assertTrue(netRang5 != null);

        // incorrect request
        assertTrue(inetManager.requestDataCenterRang(null) == null);

        // correct deletions
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(11111111));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(22222222));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(33333333));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(44444444));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(55555555));

        // incorrect deletions
        assertTrue(InetManager.InetManagerStatus.LOGIC_ERROR == inetManager
            .deleteDataCenterRang(55555555));
        assertTrue(InetManager.InetManagerStatus.LOGIC_ERROR == inetManager
            .deleteDataCenterRang(66666666));
        assertTrue(InetManager.InetManagerStatus.LOGIC_ERROR == inetManager
            .deleteDataCenterRang(null));

    }

    @Test
    public void testDatacenterPolicy()
    {
        // We first insert three rangs.
        inetManager.requestDataCenterRang(11111111);
        IPNetworkRang netRang = inetManager.requestDataCenterRang(22222222);
        IPNetworkRang netRang3 = inetManager.requestDataCenterRang(33333333);

        // We delete the second one
        inetManager.deleteDataCenterRang(22222222);

        // We insert another one
        IPNetworkRang netRang4 = inetManager.requestDataCenterRang(44444444);

        // According to the policy, datacenterId '44444444' should have the same rang than
        // the previous-deleted datacenterId '22222222', because there was an 'empty' rang
        // between datacenters '11111111' and '33333333' after removing datacenter '22222222'
        String firstAddress = netRang.getFirstAddress().toString();
        String firstAddress4 = netRang4.getFirstAddress().toString();
        String lastAddress = netRang.getLastAddress().toString();
        String lastAddress4 = netRang4.getLastAddress().toString();
        assertTrue(firstAddress.equalsIgnoreCase(firstAddress4));
        assertTrue(lastAddress.equalsIgnoreCase(lastAddress4));

        // If we insert a new rang, we should see that there is no 'empty' spaces between addresses
        // So, first address from the next rang should be after last address from datacenter
        // '33333333'
        IPNetworkRang netRang5 = inetManager.requestDataCenterRang(55555555);
        IPAddress afterDatacenter3 = netRang3.getLastAddress().nextIPAddress();
        IPAddress firstRang5 = netRang5.getFirstAddress();
        assertTrue(afterDatacenter3.toString().equalsIgnoreCase(firstRang5.toString()));

        // deleting the rest of the IP's
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(11111111));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(33333333));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(44444444));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteDataCenterRang(55555555));

    }

    // NOTE: this test needs a lot of time to execute
    // Uncomment only if you have changed something in datacenter rang assigments
    // and if the database is empty!

    @Test
    public void testNoMoreDatacenters()
    {
        Integer datacenterNum = 1;
        Integer remainingDatacenters = inetManager.remainingDatacenters();
        while (remainingDatacenters > 0)
        {
            inetManager.requestDataCenterRang(datacenterNum);
            datacenterNum++;
            remainingDatacenters--;

        }

        // One more time... And it should crash! Even the datacenter num is valid
        assertNull(inetManager.requestDataCenterRang(datacenterNum));
        datacenterNum--;

        // now we delete all the datacenters
        while (datacenterNum > 0)
        {
            assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
                .deleteDataCenterRang(datacenterNum));
            datacenterNum--;
        }

        // make sure we have deleted all the datacenters
        assertTrue(inetManager.remainingDatacenters().compareTo(inetManager.getNumberDatacenters()) == 0);

    }

    @Test
    public void testRequestAndDeleteIPAddressVM()
    {
        // request
        inetManager.requestDataCenterRang(11111111);

        // correct insertions
        IPAddress add1 = inetManager.requestIPAddressVM(11111111, "VirtualMachine1");
        assertTrue(add1 != null); // new insertion
        IPAddress add2 = inetManager.requestIPAddressVM(11111111, "VirtualMachine2");
        assertTrue(add2 != null); // new insertion
        IPAddress add3 = inetManager.requestIPAddressVM(11111111, "VirtualMachine1");
        assertTrue(add3 != null); // before insertion, return the virtual machine stored in DB
        assertTrue(add3.toString().equalsIgnoreCase(add1.toString()));

        // new rang
        inetManager.requestDataCenterRang(22222222);
        // another correct insertion
        IPAddress add4 = inetManager.requestIPAddressVM(22222222, "VirtualMachine3");
        assertTrue(add4 != null);

        // incorrect insertions
        add1 = inetManager.requestIPAddressVM(null, "VirtualMachine1");
        assertNull(add1); // null datacenter
        add1 = inetManager.requestIPAddressVM(11111111, null);
        assertNull(add1); // null virtual machine
        add1 = inetManager.requestIPAddressVM(null, null);
        assertNull(add1); // null both
        add1 = inetManager.requestIPAddressVM(33333333, "VirtualMachine1");
        assertNull(add1); // datacenter it doesn't exist in database
        add1 = inetManager.requestIPAddressVM(22222222, "VirtualMachine2");
        assertNull(add1); // virtual machine doesn't belong to the given datacenter.

        // correct deletions
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteIPAddressVM("VirtualMachine1"));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteIPAddressVM("VirtualMachine2"));
        assertTrue(InetManager.InetManagerStatus.SUCCESS == inetManager
            .deleteIPAddressVM("VirtualMachine3"));

        // incorrect deletions
        // null value
        assertTrue(InetManager.InetManagerStatus.LOGIC_ERROR == inetManager.deleteIPAddressVM(null));
        // never has exist
        assertTrue(InetManager.InetManagerStatus.LOGIC_ERROR == inetManager
            .deleteIPAddressVM("VirtualMachine4"));
        // already deleted
        assertTrue(InetManager.InetManagerStatus.LOGIC_ERROR == inetManager
            .deleteIPAddressVM("VirtualMachine1"));

        // deleting datacenters
        inetManager.deleteDataCenterRang(11111111);
        inetManager.deleteDataCenterRang(22222222);

    }

    @Test
    public void testVirtualMachinePolicy()
    {
        // new datacenter rang
        inetManager.requestDataCenterRang(11111111);

        // new virtual machines
        IPAddress addr1 = inetManager.requestIPAddressVM(11111111, "VirtualMachine1");
        IPAddress addr2 = inetManager.requestIPAddressVM(11111111, "VirtualMachine2");
        IPAddress addr3 = inetManager.requestIPAddressVM(11111111, "VirtualMachine3");
        IPAddress addr4 = inetManager.requestIPAddressVM(11111111, "VirtualMachine4");

        // we delete virtual machines 2 and 3
        inetManager.deleteIPAddressVM("VirtualMachine2");
        inetManager.deleteIPAddressVM("VirtualMachine3");

        // we create a new IPAddress which will have the IP that addr2 left
        IPAddress addr5 = inetManager.requestIPAddressVM(11111111, "VirtualMachine5");
        assertTrue(addr5.toString().equalsIgnoreCase(addr2.toString()));

        // the same with IPAddress which will have the same IP that addr3
        IPAddress addr6 = inetManager.requestIPAddressVM(11111111, "VirtualMachine6");
        assertTrue(addr6.toString().equalsIgnoreCase(addr3.toString()));

        // Due all the 'empty' spaces are full, the next IP should be the IP after addr4
        IPAddress addr7 = inetManager.requestIPAddressVM(11111111, "VirtualMachine7");
        assertTrue(addr7.toString().equalsIgnoreCase(addr4.nextIPAddress().toString()));

        // delete now the first IP address
        inetManager.deleteIPAddressVM("VirtualMachine1");

        // The new one should replace the VirtualMachine1
        IPAddress addr8 = inetManager.requestIPAddressVM(11111111, "VirtualMachine9");
        assertTrue(addr8.toString().equalsIgnoreCase(addr1.toString()));

        // delete the datacenter an all its virtual machines
        inetManager.deleteDataCenterRang(11111111);
    }

    // NOTE: this test needs a lot of time to execute
    // Uncomment only if you have changed something in virtual machine IP assigments
    // and if the database is empty!
    @Test
    public void testNoMoreVirtualMachines()
    {
        inetManager.requestDataCenterRang(1);

        int i = 0;

        for (i = 0; i < inetManager.getNumberDatacenters(); i++)
        {
            inetManager.requestIPAddressVM(1, "VM" + i);
        }

        // One more time... And it should crash! Even the virtual machine name is valid
        assertNull(inetManager.requestIPAddressVM(1, "VM" + i));

        inetManager.deleteDataCenterRang(1);
    }
}
