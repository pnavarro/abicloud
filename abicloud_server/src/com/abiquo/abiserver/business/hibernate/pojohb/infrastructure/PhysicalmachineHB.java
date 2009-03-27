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
package com.abiquo.abiserver.business.hibernate.pojohb.infrastructure;

// Generated 16-oct-2008 16:52:14 by Hibernate Tools 3.2.1.GA

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.abiquo.abiserver.business.hibernate.pojohb.IPojoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.pojo.IPojo;
import com.abiquo.abiserver.pojo.infrastructure.DataCenter;
import com.abiquo.abiserver.pojo.infrastructure.NetworkModule;
import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;
import com.abiquo.abiserver.pojo.infrastructure.Rack;
import com.abiquo.abiserver.pojo.infrastructure.SO;

/**
 * Physicalmachine generated by hbm2java
 */
public class PhysicalmachineHB implements java.io.Serializable, IPojoHB
{

	private static final long serialVersionUID = 9075806281973956772L;
	
	private Integer idPhysicalMachine;
	private UserHB userHBByIdUserLastModification;
	private SoHB so;
	private UserHB userHBByIdUserCreation;
	private RackHB rack;
	private DatacenterHB dataCenter;
	private String name;
	private String description;
	
	private int ram;
	private int cpu;
	private long hd;

	private int ramUsed;
	private int cpuUsed;
	private long hdUsed;
	
	
	private Date creationDate;
	private Date lastModificationDate;

	private Set<HypervisorHB> hypervisors = new HashSet<HypervisorHB>(0);
	private Set<NetworkmoduleHB> networkmodules = new HashSet<NetworkmoduleHB>(0);


	public Integer getIdPhysicalMachine()
	{
		return this.idPhysicalMachine;
	}

	public void setIdPhysicalMachine(Integer idPhysicalMachine)
	{
		this.idPhysicalMachine = idPhysicalMachine;
	}

	public UserHB getUserHBByIdUserLastModification()
	{
		return this.userHBByIdUserLastModification;
	}

	public void setUserHBByIdUserLastModification(UserHB userHBByIdUserLastModification)
	{
		this.userHBByIdUserLastModification = userHBByIdUserLastModification;
	}

	public SoHB getSo()
	{
		return this.so;
	}

	public void setSo(SoHB so)
	{
		this.so = so;
	}

	public UserHB getUserHBByIdUserCreation()
	{
		return this.userHBByIdUserCreation;
	}

	public void setUserHBByIdUserCreation(UserHB userHBByIdUserCreation)
	{
		this.userHBByIdUserCreation = userHBByIdUserCreation;
	}

	public RackHB getRack()
	{
		return this.rack;
	}

	public void setRack(RackHB rack)
	{
		this.rack = rack;
	}
	
	public DatacenterHB getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(DatacenterHB dataCenter) {
		this.dataCenter = dataCenter;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getRam()
	{
		return this.ram;
	}

	public void setRam(int ram)
	{
		this.ram = ram;
	}

	public int getCpu()
	{
		return this.cpu;
	}

	public void setCpu(int i)
	{
		this.cpu = i;
	}

	public long getHd()
	{
		return this.hd;
	}

	public void setHd(long hd)
	{
		this.hd = hd;
	}
	

	// used
		public int getRamUsed() {
			return ramUsed;
		}

		public void setRamUsed(int ram) {
			this.ramUsed = ram;
		}

		public int getCpuUsed() {
			return cpuUsed;
		}

		public void setCpuUsed(int cpu) {
			this.cpuUsed = cpu;
		}

		public long getHdUsed() {
			return hdUsed;
		}

		public void setHdUsed(long hd) {
			this.hdUsed = hd;
		}
	//

	public Date getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public Date getLastModificationDate()
	{
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate)
	{
		this.lastModificationDate = lastModificationDate;
	}

	public Set<NetworkmoduleHB> getNetworkmodules()
	{
		return this.networkmodules;
	}

	public void setNetworkmodules(Set<NetworkmoduleHB> networkmodules)
	{
		this.networkmodules = networkmodules;
	}

	public Set<HypervisorHB> getHypervisors()
	{
		return hypervisors;
	}

	public void setHypervisors(Set<HypervisorHB> hypervisors)
	{
		this.hypervisors = hypervisors;
	}

	public IPojo toPojo()
	{
		PhysicalMachine physicalMachine = new PhysicalMachine();
		
		physicalMachine.setDataCenter((DataCenter)this.getDataCenter().toPojo());
		physicalMachine.setCpu(this.cpu);
		physicalMachine.setCpuUsed(this.cpuUsed);
		physicalMachine.setDescription(this.description);
		physicalMachine.setHd(this.hd);
		physicalMachine.setHdUsed(this.hdUsed);
		physicalMachine.setHostSO((SO)this.so.toPojo());
		physicalMachine.setId(this.idPhysicalMachine);
		physicalMachine.setName(this.name);
		ArrayList<NetworkModule> networkModuleList = new ArrayList<NetworkModule>();
		for (NetworkmoduleHB networkModule : this.networkmodules)
		{
			networkModuleList.add((NetworkModule)networkModule.toPojo());
		}
		physicalMachine.setAssignedTo((this.rack == null) ? null : (Rack) this.rack.toPojo());
		physicalMachine.setNetworkModuleList(networkModuleList);
		physicalMachine.setRam(this.ram);
		physicalMachine.setRamUsed(this.ramUsed);
		return physicalMachine;
	}
}