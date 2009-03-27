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
package com.abiquo.abiserver.scheduler;

import java.util.List;

import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

public interface IScheduler
{

    /**
     * Return an VirtualMachine (on a PhysicalMachine on the given dataCenter) object ready to
     * deploy the given VirtualImage. (Optimistic: update PyhisicalMachine's VirtualMachine resource
     * usages, if image deploy do not success call ''rollback'')
     * 
     * @precondition An active databse transaction (HibernateUtils.getSession().beginTranstion())
     *               MUST to be opened before running this method.
     * @param targetImage, target image to deploy on returned VirtualMachine. Used to filer
     *            PhysicalMachines without enough cpu, ram or hd.
     * @param dataCenter, required data center for the selected PyhisicalMachine's VirtualMachine.
     * @return a new Instance of VirtualMachine on a PhysicalMachine (on dataCenter) with enough
     *         cpu,ram,hd for the desired VirtualImage.
     * @throws SchedulerException if there is not any PhysicalMachine with enough resources.
     */
    public VirtualMachine selectMachine(VirtualImage targetImage, String dataCenter)
        throws SchedulerException;

    /**
     * Return an list VirtualMachine (on a PhysicalMachine on the given dataCenter) ready to deploy
     * the given VirtualImages. (Optimistic: update PyhisicalMachine's VirtualMachine resource
     * usages, if image deploy do not success call ''rollback'')
     * 
     * @precondition An active databse transaction (HibernateUtils.getSession().beginTranstion())
     *               MUST to be opened before running this method.
     * @param targetImage, target image to deploy on returned VirtualMachine. Used to filer
     *            PhysicalMachines without enough cpu, ram or hd.
     * @param dataCenter, required data center for the selected PyhisicalMachine's VirtualMachine.
     * @return a new Instance of VirtualMachine on a PhysicalMachine (on dataCenter) with enough
     *         cpu,ram,hd for the desired VirtualImage.
     * @throws SchedulerException if there is not any PhysicalMachine with enough resources.
     */
    public List<VirtualMachine> selectMachines(List<VirtualImage> targetImages, String dataCenter)
        throws SchedulerException;

    /**
     * Called if some error during VirtualMachine deploy for the VirtualImage. It updates the
     * optimistic PyhisicalMachine's VirtualMachine resource usages form ''selectMachine''.
     * 
     * @precondition An active databse transaction (HibernateUtils.getSession().beginTranstion())
     *               MUST to be opened before running this method.
     */
    public void rollback(VirtualMachine virtual, PhysicalMachine physicalMachine);

}
