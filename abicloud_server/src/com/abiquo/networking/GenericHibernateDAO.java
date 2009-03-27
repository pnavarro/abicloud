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

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

/**
 * This class implements the CRUD functionality for the Hibernate framework. Provides a session
 * management.
 * 
 * @author abiquo
 */
@SuppressWarnings("unchecked")
public class GenericHibernateDAO<T, ID extends Serializable> implements Crudable<T, ID>
{

    private Class<T> persistentClass;

    private Session session;

    public GenericHibernateDAO(Class<T> persistentClass, Session session)
    {
        this.persistentClass = persistentClass;
        this.session = session;

    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.Crudable#findAll()
     */
    @Override
    public List findAll()
    {
        return findByCriteria();
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.Crudable#findByExample(java.lang.Object)
     */
    @Override
    public List findByExample(Object exampleInstance)
    {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        crit.add(example);
        return crit.list();
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.Crudable#findById(java.io.Serializable)
     */
    @Override
    public Object findById(Serializable id)
    {
        try
        {
            return (T) getSession().get(getPersistentClass().getSimpleName(), id);
        }
        catch (ObjectNotFoundException e)
        {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.Crudable#makePersistent(java.lang.Object)
     */
    @Override
    public Object makePersistent(Object entity)
    {
        getSession().saveOrUpdate(entity.getClass().getSimpleName(), entity);
        return entity;
    }

    /*
     * (non-Javadoc)
     * @see com.abiquo.networking.Crudable#makeTransient(java.lang.Object)
     */
    @Override
    public void makeTransient(Object entity)
    {
        getSession().delete(entity.getClass().getSimpleName(), entity);
    }

    public List<T> findByCriteria(Criterion... criterion)
    {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion)
        {
            crit.add(c);
        }
        return crit.list();
    }

    public Session getSession()
    {
        if (session == null)
        {
            throw new IllegalStateException("Session has not been set on DAO before usage");
        }
        return session;
    }

    public Class<T> getPersistentClass()
    {
        return persistentClass;
    }

}
