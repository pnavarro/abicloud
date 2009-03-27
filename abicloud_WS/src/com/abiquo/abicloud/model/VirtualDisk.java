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

/**
 * This class represents a virtual disk
 * 
 * @author pnavarro
 */
public class VirtualDisk
{
    /**
     * Absolute path location
     */
    private String location;

    /**
     * Virtual disk Id
     */
    private String id;

    /**
     * Virtual Disk capacity in bytes
     */
    private long capacity;

    /**
     * Gets the virtual disk location
     * 
     * @return
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Sets the virtual disk location
     * 
     * @param location a virtual disk file path
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * Gets the id
     * 
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the Id
     * 
     * @param id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Ses the capacity of the virtual disk in bytes
     * 
     * @param capacity the capacity to set
     */
    public void setCapacity(long capacity)
    {
        this.capacity = capacity;
    }

    /**
     * Gets the capacity of the virtual disk in bytes
     * 
     * @return the capacity
     */
    public long getCapacity()
    {
        return capacity;
    }
}
