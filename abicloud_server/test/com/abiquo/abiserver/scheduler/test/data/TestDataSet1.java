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
import java.util.ArrayList;
import java.util.List;


import net.undf.abiserver.pojo.infrastructure.PhysicalMachine;
import net.undf.abiserver.pojo.virtualimage.VirtualImage;


/**
 * A specific DataSet (PhysicalMachines and VirtualImages) to run TestScheduler.
 * */
public class TestDataSet1 extends TestDataBaseUtil
{
	
	/**
	 * @see TestDataBaseUtil.initPhysical()
	 * */
	protected List<PhysicalMachine> initPhysical()
	{
		List<PhysicalMachine> machines = new ArrayList<PhysicalMachine>();
		
		
		machines.add(createPhysical("pm1", "pm1", new BigDecimal(3),	100, 2048));
		machines.add(createPhysical("pm2", "pm2", new BigDecimal(2), 	100, 2048));
		machines.add(createPhysical("pm3", "pm3", new BigDecimal(3),   	100, 1024));
		machines.add(createPhysical("pm4", "pm4", new BigDecimal(0.5), 	100, 128));
		machines.add(createPhysical("pm4", "pm5", new BigDecimal(1),   	1, 	 256));
		
		return machines;
	}
	
	
	/**
	 * @see TestDataBaseUtil.initVirtual()
	 * */	
	 protected List<VirtualImage> initVirtual()
	{
		List<VirtualImage> imgs = new ArrayList<VirtualImage>();
		
		imgs.add(createImage("vi1", "vi1", new BigDecimal(1), 2, 256));
		imgs.add(createImage("vi2", "vi2", new BigDecimal(1), 2, 256));
		imgs.add(createImage("vi3", "vi3", new BigDecimal(1), 2, 256));
		imgs.add(createImage("vi4", "vi4", new BigDecimal(1), 2, 256));
		imgs.add(createImage("vi5", "vi5", new BigDecimal(1), 2, 256));
		
		return imgs;
	}
	
	
}
