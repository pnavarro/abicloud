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
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.StateHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachinenetworkmoduleHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

public class VirtualMachine extends InfrastructureElement implements IPojo
{

    /* ------------- Public atributes ------------- */

    private VirtualImage virtualImage;

    private String UUID;

    private String description;

    private int ram;

    private int cpu;

    private long hd;

    private int vdrpPort;

    private String vdrpIP;

    private State state;

    private boolean highDisponibility;

    /* ------------- Constructor ------------- */
    public VirtualMachine()
    {
        super();
        virtualImage = new VirtualImage();
        UUID = "";
        description = "";
        ram = 0;
        cpu = 0;
        hd = 0;
        vdrpPort = 0;
        vdrpIP = "";
        state = new State();
        highDisponibility = false;
    }

    public VirtualImage getVirtualImage()
    {
        return virtualImage;
    }

    public void setVirtualImage(VirtualImage virtualImage)
    {
        this.virtualImage = virtualImage;
    }

    public String getUUID()
    {
        return UUID;
    }

    public void setUUID(String uuid)
    {
        UUID = uuid;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getRam()
    {
        return ram;
    }

    public void setRam(int ram)
    {
        this.ram = ram;
    }

    public int getCpu()
    {
        return cpu;
    }

    public void setCpu(int cpu)
    {
        this.cpu = cpu;
    }

    public long getHd()
    {
        return hd;
    }

    public void setHd(long hd)
    {
        this.hd = hd;
    }

    public int getVdrpPort()
    {
        return vdrpPort;
    }

    public void setVdrpPort(int vdrpPort)
    {
        this.vdrpPort = vdrpPort;
    }

    public String getVdrpIP()
    {
        return vdrpIP;
    }

    public void setVdrpIP(String vdrpIP)
    {
        this.vdrpIP = vdrpIP;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public boolean isHighDisponibility()
    {
        return highDisponibility;
    }

    public boolean getHighDisponibility()
    {
        return this.highDisponibility;
    }

    public void setHighDisponibility(boolean highDisponibility)
    {
        this.highDisponibility = highDisponibility;
    }

    public IPojoHB toPojoHB()
    {
        VirtualmachineHB virtualMachineHB = new VirtualmachineHB();

        virtualMachineHB.setIdVm(this.getId());
        HyperVisor hypervisor = (HyperVisor) this.getAssignedTo();
        virtualMachineHB.setHypervisor((HypervisorHB) hypervisor.toPojoHB());

        virtualMachineHB.setState((StateHB) this.state.toPojoHB());
        virtualMachineHB.setImage((VirtualimageHB) this.virtualImage.toPojoHB());

        virtualMachineHB.setUuid(this.UUID);
        virtualMachineHB.setName(this.getName());
        virtualMachineHB.setDescription(this.description);
        virtualMachineHB.setRam(this.ram);
        virtualMachineHB.setCpu(this.cpu);
        virtualMachineHB.setHd(this.hd);
        virtualMachineHB.setVdrpIp(this.vdrpIP);
        virtualMachineHB.setVdrpPort(this.vdrpPort);
        virtualMachineHB.setHighDisponibility(this.highDisponibility ? 1 : 0);
        virtualMachineHB
            .setVirtualmachinenetworkmodules(new HashSet<VirtualmachinenetworkmoduleHB>(0));

        return virtualMachineHB;
    }

}
