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

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abiquo.abiserver.business.hibernate.pojohb.networking.DatacentersIPHB;
import com.abiquo.abiserver.business.hibernate.pojohb.networking.VirtualMachineIPHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;

/**
 * This class provides all the test methods to Database for the Hibernate Pojo 'VirtualMachineIPHB'
 * 
 * @author abiquo
 */
public class VirtualMachineIPHBTest extends TestCase
{

    /**
     * Session connection we will need in all the tests.
     */
    Session session;

    /**
     * Pojo of the table 'datacenters_ip'
     */
    DatacentersIPHB newDatacenterPojo;

    /**
     * Pojo of the table 'vm_ip'
     */
    VirtualMachineIPHB newVMPojo;

    /**
     * Initialize the connection
     */
    @Before
    protected void setUp() throws Exception
    {
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }

    /**
     * Close the session.
     * 
     * @throws java.lang.Exception
     */
    @After
    protected void tearDown() throws Exception
    {
        if (session.isConnected())
        {
            HibernateUtil.getSession().getTransaction().commit();
        }
    }

    /**
     * This test only checks connection.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testConnectionAndPojo()
    {
        if (!session.isConnected())
        {
            fail();
        }

    }

    /**
     * This test checks the list method, inserts a new register, gets this register and after all
     * deletes it in order to leave the table like before the test.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInsertAndDeleteRegister()
    {
        // First, we check the connection. HibernateException
        if (!session.isConnected())
        {
            fail();
        }
        try
        {
            List<VirtualMachineIPHB> listVMs = session.createCriteria("VirtualMachineIPHB").list();
            int currentRegisters = listVMs.size();

            // we set an improbable data center in order to don't violate constrain
            newDatacenterPojo = new DatacentersIPHB();
            newDatacenterPojo.setId(99999999);
            newDatacenterPojo.setFirstIP("192.168.1.1");
            newDatacenterPojo.setLastIP("192.168.1.255");
            newDatacenterPojo.setNumNodes(256);
            // checking writing permissions in DB
            session.save("DatacentersIPHB", newDatacenterPojo);

            // set a new virtual machine
            VirtualMachineIPHB newVMPojo = new VirtualMachineIPHB();
            newVMPojo.setId("virtualMachine1");
            newVMPojo.setDatacenterId(99999999);
            newVMPojo.setVirtualIP("192.168.1.1");
            // checking writing permissions in DB
            session.save("VirtualMachineIPHB", newVMPojo);
            listVMs = session.createCriteria("VirtualMachineIPHB").list();
            assertTrue(currentRegisters == (listVMs.size() - 1));

            // checking read
            newVMPojo = (VirtualMachineIPHB) session.get("VirtualMachineIPHB", newVMPojo.getId());
            assertTrue(newVMPojo.getId().equals("virtualMachine1"));
            assertTrue(newVMPojo.getDatacenterId() == 99999999);
            assertTrue(newVMPojo.getVirtualIP().equals("192.168.1.1"));

            // checking delete
            session.delete("VirtualMachineIPHB", newVMPojo);
            listVMs = session.createCriteria("VirtualMachineIPHB").list();
            assertTrue(currentRegisters == listVMs.size());

            newVMPojo = (VirtualMachineIPHB) session.get("VirtualMachineIPHB", newVMPojo.getId());
            assertNull(newVMPojo);

            // deleting temporal data center
            session.delete("DatacentersIPHB", newDatacenterPojo);

            session.getTransaction().commit();
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * This sessions checks if the constraint we set for datacenter_ip is working properly. We will
     * insert a new register in 'vm_ip' table without its datacenter_fid in the 'datacenters_ip'
     * table. It should throw an HibernateException
     */
    @Test
    public void testInsertViolateConstraint()
    {
        // set a new virtual machine
        newVMPojo = new VirtualMachineIPHB();
        newVMPojo.setId("virtualMachine1");
        newVMPojo.setDatacenterId(666666666);
        newVMPojo.setVirtualIP("192.168.1.1");

        // First, we check the connection. HibernateException
        // it only should throw in 'save' method.
        if (!session.isConnected())
        {
            fail();
        }

        try
        {
            // It should throw an HibernateException just in 'save'.
            // Otherwise, it will fail.
            session.save("VirtualMachineIPHB", newVMPojo);
            HibernateUtil.getSession().getTransaction().commit();
            fail();
        }
        catch (HibernateException e)
        {
            // Success!
            assertTrue(true);
            HibernateUtil.getSession().beginTransaction();
        }
        catch (Exception e)
        {
            fail();
            e.printStackTrace();
        }
    }

    /**
     * 'VirtualMachineIPHB' foreign key is defined as 'CASCADE'. So, if we delete a row in
     * 'DatacentersIPHB' previous-created-register, it will also delete the 'VirtualMachineIPHB'
     * pojo.
     */
    public void testDeleteCascadeConstraint()
    {
        // First, we check the connection. HibernateException
        if (!session.isConnected())
        {
            fail();
        }

        // we set an improbable data center in order to don't violate constrain
        newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(99999999);
        newDatacenterPojo.setFirstIP("192.168.1.1");
        newDatacenterPojo.setLastIP("192.168.1.255");
        newDatacenterPojo.setNumNodes(256);
        // checking writing permissions in DB
        session.save("DatacentersIPHB", newDatacenterPojo);

        // set a new virtual machine
        newVMPojo = new VirtualMachineIPHB();
        newVMPojo.setId("virtualMachine1");
        newVMPojo.setDatacenterId(99999999);
        newVMPojo.setVirtualIP("192.168.1.1");
        // checking writing permissions in DB
        session.save("VirtualMachineIPHB", newVMPojo);

        // newVMPojo is in database..
        newVMPojo = (VirtualMachineIPHB) session.get("VirtualMachineIPHB", newVMPojo.getId());
        assertTrue(newVMPojo != null);

        // deleting temporal data center
        session.delete("DatacentersIPHB", newDatacenterPojo);

        // a 'commit' is required to execute the CASCADE constraint..
        session.getTransaction().commit();

        // we recover another session
        session = HibernateUtil.getSession();
        session.beginTransaction();

        // getting the 'VirtualMachineIPHB' pojo..
        newVMPojo = (VirtualMachineIPHB) session.get("VirtualMachineIPHB", newVMPojo.getId());
        assertNull(newVMPojo);

    }

    /**
     * Pojo variable 'vmUUID' is marked as NOT NULL in DB. This test checks its property inserting a
     * null value in vmUUID method variable and it should throw an Exception
     */
    @Test
    public void testInsertNULLvmUUID()
    {
        // First, we check the connection. HibernateException
        if (!session.isConnected())
        {
            fail();
        }

        // we set an improbable data center in order to don't violate constrain
        newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(99999999);
        newDatacenterPojo.setFirstIP("192.168.1.1");
        newDatacenterPojo.setLastIP("192.168.1.255");
        newDatacenterPojo.setNumNodes(256);
        // checking writing permissions in DB
        session.save("DatacentersIPHB", newDatacenterPojo);

        // set a new virtual machine
        HibernateUtil.getSession().beginTransaction();
        newVMPojo = new VirtualMachineIPHB();
        newVMPojo.setId(null);
        newVMPojo.setDatacenterId(99999999);
        newVMPojo.setVirtualIP("192.168.1.1");

        // if 'save' method doesn't throw an HibernateException,
        // test will fail
        try
        {
            session.save("VirtualMachineIPHB", newVMPojo);
            fail();
        }
        catch (HibernateException e)
        {
            // SUCCESS!
            assertTrue(true);
            HibernateUtil.getSession().beginTransaction();
        }
        finally
        {
            // deleting temporal data center
            session.clear();
            session.delete("DatacentersIPHB", newDatacenterPojo);
        }
    }

    /**
     * Pojo variable 'datacenterId' is marked as NOT NULL in DB. This test checks its property
     * inserting a null value in 'datacenterId' method variable and it should throw an Exception
     */
    @Test
    public void testInsertNULLdatacenterId()
    {
        // First, we check the connection. HibernateException
        if (!session.isConnected())
        {
            fail();
        }

        // we set an improbable data center in order to don't violate constrain
        newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(22222222);
        newDatacenterPojo.setFirstIP("192.168.1.1");
        newDatacenterPojo.setLastIP("192.168.1.255");
        newDatacenterPojo.setNumNodes(256);
        // checking writing permissions in DB
        session.save("DatacentersIPHB", newDatacenterPojo);

        // set a new virtual machine
        newVMPojo = new VirtualMachineIPHB();
        newVMPojo.setId("virtualMachine1");
        newVMPojo.setDatacenterId(null);
        newVMPojo.setVirtualIP("192.168.1.1");

        // if 'save' method doesn't throw an HibernateException,
        // test will fail
        try
        {
            session.save("VirtualMachineIPHB", newVMPojo);
            fail();
        }
        catch (HibernateException e)
        {
            // SUCCESS!
            assertTrue(true);
        }
        finally
        {
            session.clear();
            session.delete("DatacentersIPHB", newDatacenterPojo);
        }
    }

    /**
     * Pojo variable 'virtualIP' is marked as NOT NULL in DB. This test checks its property
     * inserting a null value in 'virtualIP' method variable and it should throw an Exception
     */
    @Test
    public void testInsertNULLvirtualIP()
    {
        // First, we check the connection. HibernateException
        if (!session.isConnected())
        {
            fail();
        }

        // we set an improbable data center in order to don't violate constrain
        newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(11111111);
        newDatacenterPojo.setFirstIP("192.168.1.1");
        newDatacenterPojo.setLastIP("192.168.1.255");
        newDatacenterPojo.setNumNodes(256);
        // checking writing permissions in DB
        session.save("DatacentersIPHB", newDatacenterPojo);

        // set a new virtual machine
        newVMPojo = new VirtualMachineIPHB();
        newVMPojo.setId("virtualMachine1");
        newVMPojo.setDatacenterId(11111111);
        newVMPojo.setVirtualIP(null);

        // if 'save' method doesn't throw an HibernateException,
        // test will fail
        try
        {
            session.save("VirtualMachineIPHB", newVMPojo);
            fail();
        }
        catch (HibernateException e)
        {
            // Succes!
            assertTrue(true);
        }
        finally
        {
            session.clear();
            session.delete("DatacentersIPHB", newDatacenterPojo);
        }
    }
}
