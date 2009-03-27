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
package com.abiquo.abiserver.business.hibernate.util;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import com.abiquo.abiserver.AbiConfiguration;

/**
 * 
 * 
 *
 */
public class HibernateUtil
{

    private static final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(HibernateUtil.class);

    static
    {

        try
        {

            logger.info("Creating SessionFactory .... ");

            AbiConfiguration abiConfig = AbiConfiguration.getAbiConfiguration();

            // Create the SessionFactory from hibernate.cfg.xml

            Configuration conf = new Configuration().configure("conf/hibernate.cfg.xml");
            conf.setProperty("hibernate.connection.url", abiConfig.getDbUrl());
            conf.setProperty("hibernate.connection.username", abiConfig.getDbUserName());
            conf.setProperty("hibernate.connection.password", abiConfig.getDbPassword());

            sessionFactory = conf.buildSessionFactory();

            logger.info("SessionFactory created!");

        }
        catch (Throwable ex)
        {
            // Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public static void beginTransaction()
    {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    public static void commitTransaction()
    {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    public static void rollbackTransaction()
    {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
    }

    public static Session getSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public static Criteria createCriteria(Class clase)
    {
        return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(clase);
    }

}
