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
package com.abiquo.abiserver.scheduler.test.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.undf.abiserver.business.authentication.SessionUtil;
import net.undf.abiserver.business.hibernate.pojohb.infrastructure.DatacenterHB;
import net.undf.abiserver.business.hibernate.pojohb.infrastructure.HypervisorHB;
import net.undf.abiserver.business.hibernate.pojohb.infrastructure.HypervisorTypeHB;
import net.undf.abiserver.business.hibernate.pojohb.infrastructure.PhysicalmachineHB;
import net.undf.abiserver.business.hibernate.pojohb.infrastructure.RackHB;
import net.undf.abiserver.business.hibernate.pojohb.infrastructure.SoHB;
import net.undf.abiserver.business.hibernate.util.HibernateUtil;
import net.undf.abiserver.pojo.infrastructure.DataCenter;
import net.undf.abiserver.pojo.infrastructure.HyperVisor;
import net.undf.abiserver.pojo.infrastructure.PhysicalMachine;
import net.undf.abiserver.pojo.infrastructure.Rack;
import net.undf.abiserver.pojo.infrastructure.SO;
import net.undf.abiserver.pojo.virtualimage.VirtualImage;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


public abstract class TestDataBaseUtil
{
	/** The logger object*/
	private static 		Logger 				log = Logger.getLogger(TestDataBaseUtil.class);
	
	
	/** Provide the test set: witch  PhysicalMachines. @see ConcretDataSet.*/
	protected abstract 	List<PhysicalMachine> initPhysical();
	
	/** Provide the test set: witch  VirtualImage. @see ConcretDataSet.*/
	protected abstract 	List<VirtualImage>	initVirtual();
	
	
	/** Maintains the VirtualImage test set */
	protected 			List<VirtualImage> 	virtualsImages = initVirtual();
	
	
	/** Test Datacenter name for all PhysicalMachines instances (to be clean at tearDown) */
	protected static 	String 				dataCenter  = "dcSchedulerTest";
	
	/** Test So name for all PhysicalMachines instances (to be clean at tearDown) */
	protected static 	String 				so			= "soSchedulerTest";
	
	/** Test Hypervisor name for all PhysicalMachines instances (to be clean at tearDown) */
	protected static 	String 				hyper		= "hyperSchedulerTest";
	
	
	/** All added PhysicalMachine's rack*/
	private static Rack rackStatic 	= createRack(dataCenter);
	
	/** All added PhysicalMachine's so*/
	private static SO   soStatic 	= createSO(so);
	
	/** All added PhysicalMachine's Hypervisor */
	private static HyperVisor hyperStatic = createHypervisor(hyper);
	
	protected static HypervisorTypeHB	hyperType   = createHypervisorType(hyper);
	
	
	
	
	
	/** @return the VirtualImageSet from initVirtual() implementation */
	public List<VirtualImage> getVirtaulImages()
	{
		return virtualsImages;
	}
	
	

	/**
	 *  @return the dataCenter for all PhysicalMachines on the test set 
	 * (used to call IScheduler.select(xxx,datacenter))
	 * */
	public String getDataCenter()
	{
		return dataCenter;
	}
	
	
	/**
	 * Adds to DB the PhysicalMachines from initPhysical implementation.
	 * */
	public void initDBPhysicalMachines()
	{
		clearDBPhysicalMachines();
		
		
		for(PhysicalMachine pm : initPhysical())
		{
			log.debug("Adding to DB PhysicalMachines "+pm.getName());
			addDBPhysicalMachine(pm);
		}
	}

	
	/**
	 * Persist on database the input PhysicalMachine (also its DataCenter, Rack and So).
	 * Use UserHBByIdUserCreation = "root" for added DataCenter, Rack and PhysicalMachines
	 * */
	protected void addDBPhysicalMachine(PhysicalMachine machine)
	{
		DatacenterHB		dataHb;
		RackHB				rackHb;
		PhysicalmachineHB 	machineHb;
		SoHB				soHb;
		HypervisorHB		hyperHb;
		
		Session 	session; 
		Transaction transaction;
		
		
		dataHb = (DatacenterHB) (((Rack) machine.getAssignedTo()).getDataCenter()).toPojoHB();
		rackHb = (RackHB) ((Rack) machine.getAssignedTo()).toPojoHB();
		machineHb = (PhysicalmachineHB) machine.toPojoHB();
		soHb 	= (SoHB) machine.getHostSO().toPojoHB();
		
		hyperHb = (HypervisorHB) hyperStatic.toPojoHB();
		
		hyperHb.setIdHyper(666);
		//hyperHb.setType(hyperType);
		
		
		
		dataHb.setUserHBByIdUserCreation(SessionUtil.findUserHBByName("root"));
		rackHb.setUserHBByIdUserCreataion(SessionUtil.findUserHBByName("root"));		
		machineHb.setUserHBByIdUserCreation(SessionUtil.findUserHBByName("root"));
		
		dataHb.setCreationDate(new Date());
		rackHb.setCreatioNdate(new Date());
		machineHb.setCreationDate(new Date());
		
		
		
		session 	= HibernateUtil.getSession();
		transaction = session.beginTransaction();
		//transaction.begin();
		
		//session.saveOrUpdate(dataHb);
		session.save(dataHb);
		rackHb.setDatacenter(dataHb);
		
		//session.saveOrUpdate(rackHb);
		session.save(rackHb);
		machineHb.setRack(rackHb);
		
		//session.saveOrUpdate(soHb);
		session.save(soHb);
		machineHb.setSo(soHb);
		
		session.save(machineHb);
		hyperHb.setPhysicalMachine(machineHb);
				
		session.save(hyperType);		
		hyperHb.setType(hyperType);
		
		session.save(hyperHb);
		
		
		transaction.commit();
		
		log.debug("Added machine "+machineHb.getName() +" ("+machineHb.getDescription()+")");
	}


	/**
	 * Remove from DB the PhyisicalMachines from initPhysical implementation 
	 * (actually remove DataCenter  @see dataCenter, for cascading delete physical machines disapear). 
	 * Also remove SO's PhysicalMachine (because it is NO cascading delete).
	 * */
	@SuppressWarnings("unchecked") // DatacenterHB, SoHB 
	public void clearDBPhysicalMachines() 
	{		
		log.debug("Cleaning form DB PhysicalMachines on dataCenter "+ dataCenter);
		log.debug("Cleaning from DB SO "+so);
		
		List<DatacenterHB> datasHb;
		List<SoHB>  sosHb;
		List<HypervisorHB> hypsHb;
		List<HypervisorTypeHB> hypTypsHb;
		
		Session 	session; 
		Transaction transaction;
		
		session 	= HibernateUtil.getSession();
		transaction = session.beginTransaction();
		
		
		datasHb = session.createCriteria(DatacenterHB.class).add(Restrictions.eq("name", dataCenter)).list();		
		sosHb   = session.createCriteria(SoHB.class).add(Restrictions.eq("description", so)).list();
		hypsHb  = session.createCriteria(HypervisorHB.class).add(Restrictions.eq("type", hyperType)).list();		
		//hypsHb  = session.createCriteria(HypervisorHB.class).add(Restrictions.eq("ip", "hyperIp")).list();		
		hypTypsHb=session.createCriteria(HypervisorTypeHB.class).add(Restrictions.eq("name", hyper)).list();

		for(DatacenterHB cdHb : datasHb)
		{
			session.delete(cdHb);
		}
		
		for(SoHB sohb : sosHb)
		{
			session.delete(sohb);
		}
		

		/* CASCADING at delete HypervisorTypes
		for(HypervisorHB hyphb: hypsHb)
		{
			session.delete(hyphb);
		}
		*/
		

		for(HypervisorTypeHB hyptyhb: hypTypsHb)
		{
			session.delete(hyptyhb);
		}
		
		transaction.commit();
	}
	
	
	
	
	/** VirtualImage Factory construction */
	protected static VirtualImage createImage(String name, String desc, BigDecimal cpu, int hd, int ram)
	{
		VirtualImage image = new VirtualImage();
		
		//image.setId(id);
		image.setName(name);
		image.setDescription(desc);
		
		image.setCpuRequired(cpu);
		image.setHdRequired(hd);
		image.setRamRequired(ram);
		
		return image;
	}
	
	
	/** PhysicalMachine Factory construction */
	protected static PhysicalMachine createPhysical(String name, String desc, BigDecimal cpu, int hd, int ram)
	{
		PhysicalMachine mach = new PhysicalMachine();		
		
		mach.setName(name);
		mach.setDescription(desc);
		
		mach.setCpu(cpu);
		mach.setHd(hd);
		mach.setRam(ram);
		
		mach.setCpuUsed(new BigDecimal(0));
		mach.setHdUsed(0);
		mach.setRamUsed(0);
		
		mach.setAssignedTo(rackStatic);		
		mach.setHostSO(soStatic);
		
		hyperStatic.setAssignedTo(mach);
		
		
		return mach;
	}
	
	
	/** Rack Factory construction */
	protected static Rack createRack(String dataCenter)
	{
		DataCenter 	data = new DataCenter();
		Rack 		rack = new Rack();
		
		//data.setId(66600);
		data.setName(dataCenter);
		data.setSituation("defaultLocation");
		
		
		//rack.setId(66601);
		rack.setName("defaultRack");
		rack.setShortDescription("defaultDescription");
		rack.setLargeDescription("defaultDescription");
		rack.setDataCenter(data);
		
		return rack;		
	}
	
	/** So Factory construction */
	protected static SO createSO(String name)
	{		
		SO hostSo = new SO();
		
		//hostSo.setId(66602);		
		hostSo.setDescription(name);
		
		return hostSo;
	}
	
	/** So Factory construction */
	protected static HyperVisor createHypervisor(String name)
	{		
		HyperVisor hyp = new HyperVisor();
		
		hyp.setName(name);
		hyp.setIp("hyperIp");
		hyp.setPort(0);
		hyp.setShortDescription("hyperShortDesc");
		hyp.setLargeDescription("hyperLargeDesc");
		
		return hyp;
	}
	
	private static HypervisorTypeHB createHypervisorType(String hyper2) 
	{
		HypervisorTypeHB	hyperType   = new HypervisorTypeHB();
	
		hyperType.setName(hyper2);
		hyperType.setId(69);
		
		return hyperType;
	}
	

	/** Log all PhysicalMachines on the DataBase */
	@SuppressWarnings("unchecked") // PhysicalmachineHB
	public static void listPhysicalMachines() 
	{
		List<PhysicalmachineHB> machinesHibernate;
		
		
		Session 	session; 
		Transaction transaction;
		
		session 	= HibernateUtil.getSession();
		transaction = session.beginTransaction();

		machinesHibernate = session.createCriteria(PhysicalmachineHB.class).list();
		
		log.debug("########## All PhysicalMachiene ##########");
		for(PhysicalmachineHB pm : machinesHibernate)
		{		
			log.debug("PhysicalMachine name:"+pm.getName() +" dc:"+pm.getRack().getDatacenter().getName()
					+"\t cpu:"+pm.getCpu()+"("+pm.getCpuUsed()+")"
					+"\t ram:"+pm.getRam()+"("+pm.getRamUsed()+")"
					+"\t hd:"+pm.getHd()+"("+pm.getHdUsed()+")");				
		}
		
		transaction.commit();	
		
		
	}
	
	
}
