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
package com.abiquo.abiserver.scheduler.test;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;
import com.abiquo.abiserver.scheduler.IScheduler;
import com.abiquo.abiserver.scheduler.SchedulerException;
import com.abiquo.abiserver.scheduler.ranking.MinFitRankingScheduler;
import com.abiquo.abiserver.scheduler.test.data.TestDataBaseUtil;
import com.abiquo.abiserver.scheduler.test.data.TestDataSet1;

/**
 * Test the scheduler behavior for a given IScheduler implementation and a TestDataSet (with a VirtualImage and PhysicalMachine test set).
 * */
public class TestScheduler extends TestCase
{
	/** The logger object */
	private final static Logger log = Logger.getLogger(TestScheduler.class);

	/** The scheduler behavior implementation to test */
	private IScheduler 					scheduler;
	
	/** 
	 * The input data set: initialize (and clean) physicalMachines (datacenter,rack and so) from DataBase 
	 * and provide the VirtualImage set to test
	 * */
	private TestDataBaseUtil		dataSet;
	
	/**
	 * Run the JUnit TestCase using:
	 * - Scheduler : MinFitRankingScheduler
	 * - DataSet :   TestDataSet1
	 * */
	public static void main(String args[])
	{		
		IScheduler		sche;	
		TestDataBaseUtil testData;
		TestScheduler 	test;
		
		BasicConfigurator.configure();
		
		sche 	=  new MinFitRankingScheduler();
		testData= new TestDataSet1();		
		test 	= new TestScheduler(sche, testData);
		
		test.setUp();
		test.testScheduler();
		test.tearDown();
	}
	
	
	public TestScheduler(IScheduler sch, TestDataBaseUtil data)
	{
		scheduler 	= sch;
		dataSet 	= data;
		
		log.info("Testing scheduler "+scheduler.getClass().getCanonicalName()+
				"\n using dataSet impl "+data.getClass().getCanonicalName());
	}
	
	
	/**
	 * Run the IScheduler.selectMachines for the given VirtualImage data set.
	 * Start the DB transaction before call the IScheduler. Commit at its end.
	 * TODO: sort the VirtualImages requirements ?
	 * */
	public void testScheduler()
	{
		List<VirtualMachine> machines;
		Session session = null;
		Transaction transaction = null;

			try 
			{
				session = HibernateUtil.getSession();
				
				transaction = session.beginTransaction();
				
				machines = scheduler.selectMachines(dataSet.getVirtaulImages(), dataSet.getDataCenter());

				assertEquals("as machines as required images",dataSet.getVirtaulImages().size(), machines.size());

				printSchedulerPlan(machines, dataSet.getVirtaulImages());
				
				transaction.commit();
			}
			catch (SchedulerException e) 
			{
				log.error(e);
				dataSet.clearDBPhysicalMachines();
				
				assertNull("any scheduler scheption",e);
			}					
	}
	
	
	/**
	 * Prints where the VirtualImages will be deployed 
	 * */
	private void printSchedulerPlan(List<VirtualMachine> machines,List<VirtualImage> images) 
	{
		assert (machines.size() == images.size());
		
		log.debug(" ========== Scheduler Plan ========== ");
		
		for(int i = 0; i < images.size(); i++)
		{
			log.debug("image "+images.get(i).getName() +"\t at machine "+machines.get(i).getName());
		}
		log.debug(" ========== ============== ========== ");
	}

	protected void setUp()
	{
		dataSet.initDBPhysicalMachines();							
	}
	
	protected void tearDown()
	{
		dataSet.clearDBPhysicalMachines();
	}
	
	
	
	
	

	
	
	
}
