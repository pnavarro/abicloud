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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.PhysicalmachineHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisor;
import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;
import com.abiquo.abiserver.pojo.infrastructure.State;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Abstract IScheduler implementation, contains database transactions (Hibernate ->DB layer).
 * Selects form database only the PhysicalMachines with enough cpu, ram and hd on the selectMachine
 * function. Convert ''selectMachines'' to ''select'' with a PhysicalMachines candidate (all capable
 * to deploy the image) list.
 */
public abstract class SchedulerRestrictions implements IScheduler
{

    /** The logger object */
    private final static Logger log = Logger.getLogger(SchedulerRestrictions.class);

    /** The remote desktop min port **/
    private final static int minRemoteDesktopPort = 3389;

    /**
     * Select one PhysicalMachine form the candidate list. Strategy pattern implementation.
     * 
     * @param candidateMachines a list of PhysicalMachines with enough resources for the target
     *            VirtualImage.
     * @return a PhysicalMachine contained on candidateMachines.
     */
    public abstract PhysicalMachine select(List<PhysicalMachine> candidateMachines);

    /**
     * @see com.abiquo.abiserver.scheduler.IScheduler#selectMachine
     */
    public VirtualMachine selectMachine(VirtualImage targetImage, String dataCenter)
        throws SchedulerException
    {
        ImageRequired required;
        List<PhysicalMachine> physicalCandidates;
        PhysicalMachine physicalTarget;
        VirtualMachine virtualTarget;

        required =
            new ImageRequired(targetImage.getCpuRequired(),
                targetImage.getHdRequired(),
                targetImage.getRamRequired(),
                dataCenter);

        log.debug("TargetImage name:" + targetImage.getName() + "at dataCenter:" + dataCenter
            + " REQUIRE hd:" + required.getHd() + " ram:" + required.getRam());

        physicalCandidates = getFilteredMachines(required);

        log.debug("There are " + physicalCandidates.size() + "physical machine candidates ");

        physicalTarget = select(physicalCandidates);

        log.info("Selected PhysicalMachine !!! " + physicalTarget.getName());

        updateUsageDeployedPhysicalMachine(physicalTarget, required, true);

        log.debug("Updated physical resources usage");

        virtualTarget = instantiate(physicalTarget, targetImage);

        log.debug("Instantiated VirtualMachine " + virtualTarget.getName());

        return virtualTarget;
    }

    /**
     * @see com.abiquo.abiserver.scheduler.IScheduler#selectMachine Iterative: requirements are take
     *      one by one. (TODO: requirement sort)
     */
    public List<VirtualMachine> selectMachines(List<VirtualImage> targetImages, String dataCenter)
        throws SchedulerException
    {
        List<VirtualMachine> virtuals;

        virtuals = new ArrayList<VirtualMachine>();

        log.warn("Iterative allocate multiple VirtualMachines");

        for (VirtualImage image : targetImages)
        {
            virtuals.add(selectMachine(image, dataCenter));
        }

        return virtuals;
    }

    /**
     * Get all the available PhysicalMachines with enough resources for the VirtualImage. It access
     * DB throw Hibernate.
     * 
     * @param required, the resources capacity used on the target VirtualImage (ram, hd,
     *            dataCenter).
     * @return all PhysicalMachines available to instantiate a VirtualMachine for the target
     *         VirtualImage.
     * @throws SchedulerException, if there is not any PhysicalMachine with enough resources.
     */
    @SuppressWarnings("unchecked")
    // PhysicalmachineHB
    private List<PhysicalMachine> getFilteredMachines(ImageRequired required)
        throws SchedulerException
    {
        List<PhysicalMachine> machines;
        List<PhysicalmachineHB> machinesHibernate;

        Query physicalFiler;
        Session session;

        session = HibernateUtil.getSession();

        physicalFiler =
            session
                .createQuery("select pm "
                    + "from com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.PhysicalmachineHB pm "
                    + "join pm.rack rk " + "join rk.datacenter dc " + "where " + "dc.name in  ('"
                    + required.getDataCenter() + "') and " + "(pm.ram - pm.ramUsed) >= "
                    + required.getRam() + " " + "and (pm.hd  - pm.hdUsed)  >= " + required.getHd());

        log.debug("HQL : " + physicalFiler.getQueryString());

        machinesHibernate = physicalFiler.list();

        if (machinesHibernate.size() == 0)
        {

            throw new SchedulerException("Not enough physical machine capacity to instantiate the required virtual appliance",
                required);
        }

        machines = new ArrayList<PhysicalMachine>();

        for (PhysicalmachineHB machineHib : machinesHibernate)
        {
            log.debug("PhysicalMachine candidate : " + machineHib.getName());
            machines.add((PhysicalMachine) machineHib.toPojo());
        }

        return machines;
    }

    /**
     * Set the resource usage on PhysicalMachine after instantiating the new VirtualMachine. It
     * access DB throw Hibernate.
     * 
     * @param machine, the machine to reduce/increase its resource capacity.
     * @param used, the VirtualImage requirements to substract/add.
     * @param isAdd, true if reducing the amount of resources on the PhysicalMachine. Else it adds
     *            capacity (as a rollback on VirtualImage deploy Exception).
     */
    private void updateUsageDeployedPhysicalMachine(PhysicalMachine machine, ImageRequired used,
        boolean isReducing)
    {
        PhysicalmachineHB machineHib;
        Session session;

        session = HibernateUtil.getSession();

        log.debug("ram used: " + used.getRam() + "hd used: " + used.getHd());
        machineHib = (PhysicalmachineHB) session.get(PhysicalmachineHB.class, machine.getId());

        if (isReducing)
        {
            // machineHib.setCpuUsed(machineHib.getCpuUsed().add(used.getCpu()));
            machineHib.setRamUsed(machineHib.getRamUsed() + used.getRam());
            machineHib.setHdUsed(machineHib.getHdUsed() + used.getHd());
        }
        else
        {
            // machineHib.setCpuUsed(machineHib.getCpuUsed().subtract(used.getCpu()));
            machineHib.setRamUsed(machineHib.getRamUsed() - used.getRam());
            machineHib.setHdUsed(machineHib.getHdUsed() - used.getHd());
        }

        session.update(machineHib);
    }

    /**
     * Create a Virtual Machine on the given PhysicalMachine to deploy the given VirtualMachine.
     * 
     * @param physical, the target PhysicalMachine where create a new VirtualMachine (using
     *            PhysicalMachine's Hypervisor)
     * @param image, the target VirtaulImage to be deployed on the returned VirtualMachine (used to
     *            allocate resource usage)
     * @return a new VirtualMachine instance inside physical to load image. TODO: creating default
     *         Hypervisor instance TODO: VdrpIP, VdrpPort
     */
    private VirtualMachine instantiate(PhysicalMachine physical, VirtualImage image)
    {
        HyperVisor hypervisor;
        VirtualMachine virtual;

        // The hypervisors shall be discovered when the physical machine are loaded so we recover
        // the hypervisors from the DB
        Session session;
        PhysicalmachineHB physicalMachineHB;
        Set<HypervisorHB> hypervisorsHB;

        session = HibernateUtil.getSession();

        physicalMachineHB =
            (PhysicalmachineHB) session.get(PhysicalmachineHB.class, physical.getId());
        hypervisorsHB = physicalMachineHB.getHypervisors();

        // Getting one hypervisor from the list. TODO Change this decision when multihypervisors are
        // used
        HypervisorHB hypervisorHB = hypervisorsHB.iterator().next();
        log.info("The hypervisor: " + hypervisorHB.getType().getName() + " will be used");
        hypervisor = (HyperVisor) hypervisorHB.toPojo();

        State state = new State();
        // state.setDescription(statePojo.getDescription());
        state.setId(State.NOT_DEPLOYED);

        // TODO default also high disponibility flag)
        virtual = new VirtualMachine();
        virtual.setVirtualImage(image);
        virtual.setName(UUID.randomUUID().toString()); // TODO
        virtual.setDescription(image.getDescription());
        virtual.setCpu(image.getCpuRequired());
        virtual.setHd(image.getHdRequired());
        virtual.setRam(image.getRamRequired());
        virtual.setState(state);
        // Setting the default VRDP acces
        virtual.setVdrpIP(hypervisor.getIp());
        // Selecting the max port
        Integer rdpPort = null;
        Integer maxRdpPort =
            (Integer) session
                .createQuery(
                    "select MAX(vdrpPort)"
                        + "from com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB "
                        + "where idHypervisor = " + hypervisor.getId()).uniqueResult();

        if (maxRdpPort == null)
        {
            rdpPort = minRemoteDesktopPort;
        }
        else
        {
            rdpPort = maxRdpPort + 1;
            log.debug("The assigned port is: " + rdpPort);
        }
        virtual.setVdrpPort(rdpPort);
        virtual.setUUID(UUID.randomUUID().toString());
        virtual.setAssignedTo(hypervisor);

        /**
         * TODO virtualMachine.setHighDisponibility(virtualMachinePojo.get)
         * virtual.setVdrpIP(virtualMachinePojo.getVdrpIp());
         * virtual.setVdrpPort(virtualMachinePojo.getVdrpPort());
         **/

        return virtual;
    }

    /**
     * @param physicalMachine
     * @see com.abiquo.abiserver.scheduler.IScheduler#rollback
     */
    public void rollback(VirtualMachine virtual, PhysicalMachine physicalMachine)
    {
        ImageRequired required;

        required =
            new ImageRequired(virtual.getCpu(), virtual.getHd(), virtual.getRam(), "nodatacenter");

        updateUsageDeployedPhysicalMachine(physicalMachine, required, false);
    }

}

/**
 * Pojo class maintaining the resource requirements for a VirtualImage
 */
class ImageRequired
{
    int cpu;

    long hd;

    int ram;

    String dataCenter;

    public ImageRequired(int i, long l, int ram, String dataCenter)
    {
        this.cpu = i;
        this.hd = l;
        this.ram = ram;
        this.dataCenter = dataCenter;
    }

    public String getDataCenter()
    {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter)
    {
        this.dataCenter = dataCenter;
    }

    public int getCpu()
    {
        return cpu;
    }

    public long getHd()
    {
        return hd;
    }

    public int getRam()
    {
        return ram;
    }
}
