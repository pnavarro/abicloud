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
 * Consell de Cent 296 principal 2º, 08007 Barcelona, Spain.
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
package com.abiquo.abiserver.business.hibernate.pojohb.networking;

import java.io.Serializable;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.pojo.IPojo;

/**
 * Entity which maps with MySql table DATACENTERS_IP
 * 
 * @author abiquo
 */
public final class DatacentersIPHB implements Serializable, IPojoHB
{

    private static final long serialVersionUID = -3289413339406934860L;

    /**
     * variable which corresponds with column 'id'
     */
    private Integer id;
    
    /**
     * variable which corresponds with column 'first_ip'
     */
    private String firstIP;

    /**
     * variable which corresponds with column 'last_ip'
     */
    private String lastIP;

    /**
     * variable which corresponds with column 'num_nodes'
     */
    private Integer numNodes;

    /**
     * Default constructor for access to this class class through reflection.
     */
    public DatacentersIPHB()
    {
        
    }
    
    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the firstIP
     */
    public String getFirstIP()
    {
        return firstIP;
    }

    /**
     * @param fisrtIP the firstIP to set
     */
    public void setFirstIP(String firstIP)
    {
        this.firstIP = firstIP;
    }

    /**
     * @return the lastIP
     */
    public String getLastIP()
    {
        return lastIP;
    }

    /**
     * @param lastIP the lastIP to set
     */
    public void setLastIP(String lastIP)
    {
        this.lastIP = lastIP;
    }

    /**
     * @return the numNodes
     */
    public Integer getNumNodes()
    {
        return numNodes;
    }

    /**
     * @param numNodes the numNodes to set
     */
    public void setNumNodes(Integer numNodes)
    {
        this.numNodes = numNodes;
    }

    @Override
    public IPojo toPojo()
    {
        // TODO Auto-generated method stub
        return null;
    }



}
