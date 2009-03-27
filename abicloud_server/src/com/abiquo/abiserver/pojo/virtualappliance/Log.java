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
package com.abiquo.abiserver.pojo.virtualappliance;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.LogHB;
import com.abiquo.abiserver.pojo.IPojo;
import java.util.Date;

/**
 * This class represents a Log entry for Virtual Appliances
 * 
 * @author Oliver
 */

public class Log implements IPojo
{

    /* ------------- Public attributes ------------- */

    /**
     * The log identification
     */
    private int idLog;

    /**
     * The ID of the Virtual Appliance to which this log belongs We do not use the entire Virtual
     * Appliance object here, since a VirtualAppliance object already contains a list of logs
     */
    private int idVirtualAppliance;

    /**
     * Log description
     */
    private String description;

    /**
     * Log date
     */
    private Date logDate;

    /**
     * /** Basic constructor.
     */
    public Log()
    {
    }

    public int getIdLog()
    {
        return idLog;
    }

    public void setIdLog(int idLog)
    {
        this.idLog = idLog;
    }

    public int getIdVirtualAppliance()
    {
        return idVirtualAppliance;
    }

    public void setIdVirtualAppliance(int idVirtualAppliance)
    {
        this.idVirtualAppliance = idVirtualAppliance;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getLogDate()
    {
        return logDate;
    }

    public void setLogDate(Date logDate)
    {
        this.logDate = logDate;
    }

    /**
     * This method transform to Hibernate pojo object.
     */
    public IPojoHB toPojoHB()
    {
        LogHB log = new LogHB();
        log.setIdLog(this.idLog);
        log.setDescription(this.description);
        log.setIdVirtualAppliance(this.idVirtualAppliance);
        log.setLogDate(this.logDate);

        return log;
    }

}
