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
 * Entity which maps with MySql table VM_ID
 * 
 * @author abiquo
 *
 */
public class VirtualMachineIPHB implements Serializable, IPojoHB
{
    private static final long serialVersionUID = 2979918374139996408L;

    /**
     * variable which corresponds with column 'id'
     */
    private String id;
    
    /**
     * variable which corresponds with column 'datacenter_fid'
     */
    private Integer datacenterId;
    
    /**
     * variable which corresponds with column 'vm_ip'
     */
    private String virtualIP;
    
    /**
     * Default constructor for access to this class class through reflection.
     */
    public VirtualMachineIPHB()
    {
        
    }
    
    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * @return the datacenterId
     */
    public Integer getDatacenterId()
    {
        return datacenterId;
    }

    /**
     * @param datacenterId the datacenterId to set
     */
    public void setDatacenterId(Integer datacenterId)
    {
        this.datacenterId = datacenterId;
    }

    /**
     * @return the virtualIP
     */
    public String getVirtualIP()
    {
        return virtualIP;
    }

    /**
     * @param virtualIP the virtualIP to set
     */
    public void setVirtualIP(String virtualIP)
    {
        this.virtualIP = virtualIP;
    }

    /* (non-Javadoc)
     * @see com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB#toPojo()
     */
    @Override
    public IPojo toPojo()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
