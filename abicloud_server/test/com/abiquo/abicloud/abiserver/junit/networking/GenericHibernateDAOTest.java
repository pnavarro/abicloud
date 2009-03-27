/**
 * 
 */
package com.abiquo.abicloud.abiserver.junit.networking;

import java.util.List;

import junit.framework.TestCase;
import com.abiquo.abiserver.business.hibernate.pojohb.networking.DatacentersIPHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.networking.GenericHibernateDAO;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class provides all the test methods for the GenericHibernateDAO class. We will use the
 * DatacenterIPHB type as template example.
 * 
 * @author abiquo
 */
@SuppressWarnings("unchecked")
public class GenericHibernateDAOTest extends TestCase
{

    /**
     * Hibernate session.
     */
    Session session;

    /**
     * GenericHibernateDAO instance
     */
    GenericHibernateDAO<DatacentersIPHB, Integer> datacentersDAO;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        session = HibernateUtil.getSession();
        session.beginTransaction();
        datacentersDAO = new GenericHibernateDAO(DatacentersIPHB.class, session);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        session.getTransaction().commit();
    }

    /**
     * Test method for {@link com.abiquo.networking.GenericHibernateDAO#GenericHibernateDAO()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGenericHibernateDAO()
    {
        assertTrue(datacentersDAO.getPersistentClass() != null);
    }

    /**
     * Test method for {@link com.abiquo.networking.GenericHibernateDAO#findAll()}.
     */
    @Test
    public void testFindAll()
    {
        try
        {
            List<DatacentersIPHB> listOfDatacenters = datacentersDAO.findAll();
            assertTrue(listOfDatacenters != null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test method for
     * {@link com.abiquo.networking.GenericHibernateDAO#makePersistent(java.lang.Object)}.
     */
    @Test
    public void testMakePersistentExampleandTransient()
    {
        List<DatacentersIPHB> listOfDatacenters = datacentersDAO.findAll();
        Integer numberOfDatacenters = listOfDatacenters.size();

        // MakePersistent test
        DatacentersIPHB datacenterPojo = new DatacentersIPHB();
        datacenterPojo.setId(999999999);
        datacenterPojo.setFirstIP("10.255.255.254");
        datacenterPojo.setLastIP("10.255.255.255");
        datacenterPojo.setNumNodes(2);
        datacentersDAO.makePersistent(datacenterPojo);
        assertTrue(datacentersDAO.findAll().size() == numberOfDatacenters + 1);

        // FindByExampleTest
        List<DatacentersIPHB> datacenterPojo2 = datacentersDAO.findByExample(datacenterPojo);
        assertTrue(datacenterPojo2.size() == 1);
        assertTrue(datacenterPojo2.iterator().next().equals(datacenterPojo));

        // FindByIdTest
        DatacentersIPHB datacenterPojo3 =
            (DatacentersIPHB) datacentersDAO.findById(datacenterPojo.getId());
        assertTrue(datacenterPojo3.equals(datacenterPojo));

        // MakeTransientTest
        datacentersDAO.makeTransient(datacenterPojo);
        assertTrue(datacentersDAO.findAll().size() == numberOfDatacenters);

    }

    /**
     * Test method for {@link com.abiquo.networking.GenericHibernateDAO#getPersistentClass()}.
     */
    @Test
    public void testGetPersistentClass()
    {
        assertTrue(datacentersDAO.getPersistentClass() != null);
    }

}
