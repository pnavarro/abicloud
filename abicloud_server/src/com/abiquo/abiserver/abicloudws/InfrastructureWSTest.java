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
package com.abiquo.abiserver.abicloudws;

import java.util.UUID;

import junit.framework.TestCase;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.commands.InfrastructureCommand;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisor;
import com.abiquo.abiserver.pojo.infrastructure.State;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class InfrastructureWSTest extends TestCase
{

    private InfrastructureWS infraWs;

    private InfrastructureCommand infraCommand;

    private VirtualMachine virtualMachine;

    protected void setUp() throws Exception
    {
        super.setUp();
        virtualMachine = new VirtualMachine();
        // Creating a Virtual Box hypervisor
        HyperVisor hyperVisor = new HyperVisor();
        hyperVisor.setName("vBox");
        hyperVisor.setIp("192.168.102.61");
        hyperVisor.setPort(18083);
        virtualMachine.setAssignedTo(hyperVisor);
        virtualMachine.setName("XUbuntu");
        virtualMachine.setUUID(UUID.randomUUID().toString());
        // Creating a virtualImage
        VirtualImage virtualImage = new VirtualImage();
        virtualImage.setPath("/opt/vm_repository/xubuntu-8.04-x86.vdi");
        virtualImage.setName("XubuntuImage");
        virtualMachine.setVirtualImage(virtualImage);
        infraWs = new InfrastructureWS();
        infraCommand = new InfrastructureCommand();
        infraWs = new InfrastructureWS();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /*
     * public void testCreateVirtualMachineDB(){ infraCommand.createVirtualMachine(virtualMachine);
     * }
     */

    /*
     * public void testCreateVirtualMachine() throws InterruptedException { VirtualMachine
     * virtualMachineDeployed = getVirtualImageFromDb(1); BasicResult result =
     * infraWs.createVirtualMachine(virtualMachineDeployed); assertTrue("An error was occured: " +
     * result.getMessage(), result.getSuccess()); Thread.sleep(5000); }
     */

    public void testDeployVirtualMachine() throws InterruptedException
    {

        BasicResult result = infraWs.createVirtualMachine(virtualMachine);
        assertTrue("An error was occured: " + result.getMessage(), result.getSuccess());
        Thread.sleep(5000);
    }

    public void testPowerUpMachine() throws InterruptedException
    {
        State powerUpState = new State();
        powerUpState.setId(State.POWERED_OFF);
        virtualMachine.setState(powerUpState);
        BasicResult result = infraWs.setVirtualMachineState(virtualMachine, "PowerUp");
        Thread.sleep(10000);
        assertTrue("An error was occured: " + result.getMessage(), result.getSuccess());

    }

    public void testPowerDownMachine() throws InterruptedException
    {
        State powerUpState = new State();
        powerUpState.setId(State.RUNNING);
        virtualMachine.setState(powerUpState);
        BasicResult result = infraWs.setVirtualMachineState(virtualMachine, "PowerOff");
        Thread.sleep(10000);
        assertTrue("An error was occured: " + result.getMessage(), result.getSuccess());

    }

    public void testDeleteVirtualMachine()
    {
        BasicResult result = infraWs.deleteVirtualMachine(virtualMachine);
        assertTrue("An error was occured: " + result.getMessage(), result.getSuccess());
    }

    private VirtualMachine getVirtualImageFromDb(int virtualMachineId)
    {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        VirtualmachineHB virtualMachinePojo =
            (VirtualmachineHB) session.get(VirtualmachineHB.class, virtualMachineId);
        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.setCpu(virtualMachinePojo.getCpu());
        virtualMachine.setDescription(virtualMachinePojo.getDescription());
        virtualMachine.setHd(virtualMachine.getHd());
        virtualMachine.setHighDisponibility(virtualMachinePojo.getHighDisponibility() != 0);
        virtualMachine.setId(virtualMachinePojo.getIdVm());
        virtualMachine.setName(virtualMachinePojo.getName());
        virtualMachine.setRam(virtualMachinePojo.getRam());
        State state = new State();
        state.setId(virtualMachinePojo.getState().getIdState());
        state.setDescription(virtualMachinePojo.getState().getDescription());
        virtualMachine.setState(state);
        virtualMachine.setUUID(virtualMachinePojo.getUuid());
        virtualMachine.setVdrpIP(virtualMachinePojo.getVdrpIp());
        virtualMachine.setVdrpPort(virtualMachinePojo.getVdrpPort());
        VirtualimageHB virtualimagePojo = virtualMachinePojo.getImage();
        VirtualImage virtualImage = new VirtualImage();
        virtualImage.setName(virtualimagePojo.getName());
        virtualImage.setPath(virtualimagePojo.getPathName());
        virtualMachine.setVirtualImage(virtualImage);
        // Setting the hypervisor
        HypervisorHB hypervisorPojo = virtualMachinePojo.getHypervisor();
        HyperVisor hypervisor = new HyperVisor();
        hypervisor.setIp(hypervisorPojo.getIp());
        hypervisor.setPort(hypervisorPojo.getPort());
        hypervisor.setName(hypervisorPojo.getType().getName());
        virtualMachine.setAssignedTo(hypervisor);
        transaction.commit();

        return virtualMachine;

    }
}
