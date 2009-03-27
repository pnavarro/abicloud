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
package com.abiquo.abiserver.pojo.infrastructure;

import java.util.HashSet;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorTypeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.PhysicalmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.pojo.IPojo;

/**
 * An Hypervisor controls the virtualization software in a Physical Machine We see it as another
 * infrastructure element, where Virtual Machines are attached An HyperVisor is attached to a
 * Physical Machine
 * 
 * @author Oliver
 */
public class HyperVisor extends InfrastructureElement implements IPojo
{
    private String shortDescription;

    private String ip;

    private int port;

    private HyperVisorType type;

    public HyperVisor()
    {
        super();
        shortDescription = "";
        ip = "";
        port = 0;
        type = new HyperVisorType();
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public HyperVisorType getType()
    {
        return type;
    }

    public void setType(HyperVisorType hyperVisorType)
    {
        this.type = hyperVisorType;
    }

    public IPojoHB toPojoHB()
    {
        HypervisorHB hyperVisorHB = new HypervisorHB();

        hyperVisorHB.setIdHyper(super.getId());
        hyperVisorHB.setShortDescription(this.shortDescription);
        hyperVisorHB.setVirtualmachines(new HashSet<VirtualmachineHB>(0));
        hyperVisorHB.setIp(this.ip);
        hyperVisorHB.setPort(this.port);

        PhysicalMachine physicalMachine = (PhysicalMachine) super.getAssignedTo();
        hyperVisorHB.setPhysicalMachine((PhysicalmachineHB) physicalMachine.toPojoHB());

        hyperVisorHB.setType((HypervisorTypeHB) this.type.toPojoHB());

        return hyperVisorHB;
    }

}
