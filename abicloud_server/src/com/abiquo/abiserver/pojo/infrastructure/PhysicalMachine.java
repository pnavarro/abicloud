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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.DatacenterHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.NetworkmoduleHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.PhysicalmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.RackHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.SoHB;
import com.abiquo.abiserver.pojo.IPojo;

public class PhysicalMachine extends InfrastructureElement implements IPojo
{

    /* ------------- Public atributes ------------- */
    // public var hyperVisor:HyperVisor;
    private DataCenter dataCenter;

    private String description;

    private int ram;

    private int cpu;

    private long hd;

    private SO hostSO;

    private ArrayList<NetworkModule> networkModuleList;

    private int ramUsed;

    private int cpuUsed;

    private long hdUsed;

    public PhysicalMachine()
    {
        super();

        dataCenter = new DataCenter();

        description = "";
        ram = 0;
        cpu = 0;
        hd = 0;

        ramUsed = 0;
        cpuUsed = 0;
        hdUsed = 0;

        hostSO = new SO();
        networkModuleList = new ArrayList<NetworkModule>();
    }

    public DataCenter getDataCenter()
    {
        return dataCenter;
    }

    public void setDataCenter(DataCenter dataCenter)
    {
        this.dataCenter = dataCenter;
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

    // used
    public int getRamUsed()
    {
        return ramUsed;
    }

    public void setRamUsed(int ram)
    {
        this.ramUsed = ram;
    }

    public int getCpuUsed()
    {
        return cpuUsed;
    }

    public void setCpuUsed(int cpu)
    {
        this.cpuUsed = cpu;
    }

    public long getHdUsed()
    {
        return hdUsed;
    }

    public void setHdUsed(long hd)
    {
        this.hdUsed = hd;
    }

    //

    public SO getHostSO()
    {
        return hostSO;
    }

    public void setHostSO(SO hostSO)
    {
        this.hostSO = hostSO;
    }

    public ArrayList<NetworkModule> getNetworkModuleList()
    {
        return networkModuleList;
    }

    public void setNetworkModuleList(ArrayList<NetworkModule> networkModuleList)
    {
        this.networkModuleList = networkModuleList;
    }

    public IPojoHB toPojoHB()
    {
        PhysicalmachineHB physicalMachineHB = new PhysicalmachineHB();

        physicalMachineHB.setDataCenter((DatacenterHB) this.getDataCenter().toPojoHB());
        physicalMachineHB.setIdPhysicalMachine(this.getId());
        physicalMachineHB.setName(this.getName());
        physicalMachineHB.setDescription(this.getDescription());
        physicalMachineHB.setCpu(this.getCpu());
        physicalMachineHB.setCpuUsed(this.getCpuUsed());
        physicalMachineHB.setRam(this.getRam());
        physicalMachineHB.setRamUsed(this.getRamUsed());
        physicalMachineHB.setHd(this.getHd());
        physicalMachineHB.setHdUsed(this.getHdUsed());
        physicalMachineHB.setSo((SoHB) this.getHostSO().toPojoHB());
        Rack rack = (Rack) this.getAssignedTo();
        physicalMachineHB.setRack((RackHB) rack.toPojoHB());

        // Setting the network module list
        Set<NetworkmoduleHB> networkModuleHBList = new HashSet<NetworkmoduleHB>();
        for (NetworkModule networkModule : networkModuleList)
        {
            networkModuleHBList.add((NetworkmoduleHB) networkModule.toPojoHB());
        }
        physicalMachineHB.setNetworkmodules(networkModuleHBList);
        return physicalMachineHB;
    }

}
