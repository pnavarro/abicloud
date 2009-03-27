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
 * Consell de Cent 296, Principal 2º, 08007 Barcelona, Spain.
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

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abiquo.abiserver.business.hibernate.pojohb.networking.DatacentersIPHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;

import junit.framework.TestCase;

/**
 * This class provides all the test methods to Database for the Hibernate Pojo 'DatacentersIPHB'.
 * 
 * @author abiquo
 */
public class DatacentersIPHBConnectionTest extends TestCase
{

    /**
     * Session connection we will need in all the tests.
     */
    Session session;

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
     * After all the tests, we have to close the connection.
     */
    @After
    public void tearDown()
    {
        HibernateUtil.getSession().getTransaction().commit();
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
        if (!session.isConnected())
        {
            fail();
        }

        try
        {
            List<DatacentersIPHB> listDatacentersIP =
                session.createCriteria("DatacentersIPHB").list();
            int currentRegisters = listDatacentersIP.size();

            // we set an improbable datacenter
            DatacentersIPHB newDatacenterPojo = new DatacentersIPHB();
            newDatacenterPojo.setId(99999999);
            newDatacenterPojo.setFirstIP("255.255.255.255");
            newDatacenterPojo.setLastIP("255.255.255.255");
            newDatacenterPojo.setNumNodes(1);

            // checking writing permissions in DB
            session.save("DatacentersIPHB", newDatacenterPojo);
            listDatacentersIP = session.createCriteria("DatacentersIPHB").list();
            assertTrue(currentRegisters == (listDatacentersIP.size() - 1));

            // checking read
            newDatacenterPojo =
                (DatacentersIPHB) session.get("DatacentersIPHB", newDatacenterPojo.getId());
            assertTrue(newDatacenterPojo.getId() == 99999999);
            assertTrue(newDatacenterPojo.getFirstIP().equals("255.255.255.255"));
            assertTrue(newDatacenterPojo.getLastIP().equals("255.255.255.255"));
            assertTrue(newDatacenterPojo.getNumNodes() == 1);

            // checking delete
            session.delete("DatacentersIPHB", newDatacenterPojo);
            listDatacentersIP = session.createCriteria("DatacentersIPHB").list();
            assertTrue(currentRegisters == listDatacentersIP.size());

            newDatacenterPojo =
                (DatacentersIPHB) session.get("DatacentersIPHB", newDatacenterPojo.getId());
            assertNull(newDatacenterPojo);

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Pojo variable 'datacenterId' is marked as NOT NULL in DB. This test checks its property
     * inserting a null value in datacenterId method variable and it should throw an
     * HibernateException
     */
    @Test
    public void testInsertNULLdatacenterId()
    {
        if (!session.isConnected())
        {
            fail();
        }

        // we set an improbable data center in order to don't violate constrain
        DatacentersIPHB newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(null);
        newDatacenterPojo.setFirstIP("192.168.1.1");
        newDatacenterPojo.setLastIP("192.168.1.255");
        newDatacenterPojo.setNumNodes(256);

        try
        {
            // if 'save' method doesn't throw an HibernateException we won't be happy
            session.save("DatacentersIPHB", newDatacenterPojo);
            fail();
        }
        catch (HibernateException e)
        {
            // Success!!
            assertTrue(true);
        }
        catch (Exception e)
        {
            // another kind of exception is not expected
            fail();
            e.printStackTrace();
        }
        finally
        {
            session.clear();
        }
    }

    /**
     * Pojo variable 'firstIP' is marked as NOT NULL in DB. This test checks its property inserting
     * a null value in firstIP method variable and it should throw an HibernateException
     */
    @Test
    public void testInsertNULLfirstIP()
    {
        if (!session.isConnected())
        {
            fail();
        }

        // we set an improbable data center in order to don't violate constrain
        DatacentersIPHB newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(99999999);
        newDatacenterPojo.setFirstIP(null);
        newDatacenterPojo.setLastIP("192.168.1.255");
        newDatacenterPojo.setNumNodes(256);

        try
        {
            // if 'save' method doesn't throw an HibernateException we won't be happy
            session.save("DatacentersIPHB", newDatacenterPojo);
            fail();
        }
        catch (HibernateException e)
        {
            // Success!!
            assertTrue(true);
        }
        catch (Exception e)
        {
            // another kind of exception is not expected
            fail();
            e.printStackTrace();
        }
        finally
        {
            session.clear();
        }
    }

    /**
     * Pojo variable 'lastIP' is marked as NOT NULL in DB. This test checks its property inserting a
     * null value in lastIP method variable and it should throw an HibernateException
     */
    @Test
    public void testInsertNULLlastIP()
    {
        if (!session.isConnected())
        {
            fail();
        }
        // we set an improbable data center in order to don't violate constrain
        DatacentersIPHB newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(99999999);
        newDatacenterPojo.setFirstIP("192.168.1.1");
        newDatacenterPojo.setLastIP(null);
        newDatacenterPojo.setNumNodes(256);

        try
        {
            // if 'save' method doesn't throw an HibernateException we won't be happy
            session.save("DatacentersIPHB", newDatacenterPojo);
            fail();
        }
        catch (HibernateException e)
        {
            // Success!!
            assertTrue(true);
        }
        catch (Exception e)
        {
            // another kind of exception is not expected
            fail();
            e.printStackTrace();
        }
        finally
        {
            session.clear();
        }
    }

    /**
     * Pojo variable 'numNodes' is marked as NOT NULL in DB. This test checks its property inserting
     * a null value in numNodes method variable and it should throw an HibernateException
     */
    @Test
    public void testInsertNULLnumNodes()
    {
        if (!session.isConnected())
        {
            fail();
        }
        // we set an improbable data center in order to don't violate constrain
        DatacentersIPHB newDatacenterPojo = new DatacentersIPHB();
        newDatacenterPojo.setId(99999999);
        newDatacenterPojo.setFirstIP("192.168.1.1");
        newDatacenterPojo.setLastIP("192.168.1.265");
        newDatacenterPojo.setNumNodes(null);

        try
        {
            // if 'save' method doesn't throw an HibernateException we won't be happy
            session.save("DatacentersIPHB", newDatacenterPojo);
            fail();
        }
        catch (HibernateException e)
        {
            // Success!!
            assertTrue(true);
        }
        catch (Exception e)
        {
            // another kind of exception is not expected
            fail();
            e.printStackTrace();
        }
        finally
        {
            session.clear();
        }
    }

}
