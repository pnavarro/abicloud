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

/**
 * This interface provides the methods to implement a DAO pattern for CRUD(Create, Read, Update and
 * Delete) functionality. Each entity of the DataBase should have a class which implements its
 * methods in order to access to it.
 * 
 * @author abiquo
 */
public interface Crudable<T, ID extends Serializable>
{
    /**
     * Looks for an persistent object which a given id.
     * 
     * @param id identifier of the object.
     * @return Entity we want to get
     */
    T findById(ID id);

    /**
     * Looks for a list of persistent objects in Database.
     * 
     * @return Whole list of entities.
     */
    List<T> findAll();

    /**
     * List of persistent object which matches with a given object.
     * 
     * @param exampleInstance
     * @return List of matching entities.
     */
    List<T> findByExample(T exampleInstance);

    /**
     * Stores an entity in database.
     * 
     * @param entity Entity to store.
     * @return
     */
    T makePersistent(T entity);

    /**
     * Deletes an entity from database.
     * 
     * @param entity Entity to delete
     */
    void makeTransient(T entity);

}
