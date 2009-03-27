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
package com.abiquo.abiserver.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.abiquo.abiserver.abicloudws.AbiCloudConstants;
import com.abiquo.abiserver.abicloudws.InfrastructureWS;
import com.abiquo.abiserver.abicloudws.VirtualApplianceWS;
import com.abiquo.abiserver.business.authentication.SessionUtil;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.DatacenterHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.DnsHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.HypervisorTypeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.NetworkmoduleHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.PhysicalmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.RackHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.SoHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.StateHB;
import com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.VirtualmachineHB;
import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualappliance.NodeHB;
import com.abiquo.abiserver.business.hibernate.pojohb.virtualimage.VirtualimageHB;
import com.abiquo.abiserver.business.hibernate.util.HibernateUtil;
import com.abiquo.abiserver.pojo.authentication.UserSession;
import com.abiquo.abiserver.pojo.infrastructure.DNS;
import com.abiquo.abiserver.pojo.infrastructure.DataCenter;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisor;
import com.abiquo.abiserver.pojo.infrastructure.InfrastructureElement;
import com.abiquo.abiserver.pojo.infrastructure.NetworkModule;
import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachine;
import com.abiquo.abiserver.pojo.infrastructure.PhysicalMachineCreation;
import com.abiquo.abiserver.pojo.infrastructure.Rack;
import com.abiquo.abiserver.pojo.infrastructure.State;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.result.DataResult;
import com.abiquo.util.resources.ResourceManager;
import com.sun.ws.management.client.exceptions.FaultException;

/**
 * This command collects all actions related to Infrastructure
 * 
 * @author Oliver
 */

public class InfrastructureCommand extends BasicCommand
{

    /** The logger object */
    private final static Logger logger = Logger.getLogger(InfrastructureCommand.class);

    // private DataResult<ArrayList<InfrastructureElement>> infrastructureByDataCenter;

    private static final ResourceManager resourceManager =
        new ResourceManager(InfrastructureCommand.class);

    /*
     * ______________________________ DATA CENTER _______________________________
     */

    /**
     * Returns the whole infrastructure stored in a data center
     * 
     * @param dataCenter
     * @return
     */
    @SuppressWarnings("unchecked")
    protected DataResult<ArrayList<InfrastructureElement>> getInfrastructureByDataCenter(
        DataCenter dataCenter)
    {

        DataResult<ArrayList<InfrastructureElement>> dataResult =
            new DataResult<ArrayList<InfrastructureElement>>();
        ArrayList<InfrastructureElement> infrastructures = null;
        DatacenterHB datacenterPojo = null;

        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try
        {
            transaction = session.beginTransaction();
            infrastructures = new ArrayList<InfrastructureElement>();
            datacenterPojo = (DatacenterHB) session.get(DatacenterHB.class, dataCenter.getId());

            // Adding the racks
            Set<RackHB> racks = datacenterPojo.getRacks();
            for (RackHB rackPojo : racks)
            {
                Rack rack = (Rack) rackPojo.toPojo();
                rack.setDataCenter(dataCenter);
                // Adding to the infrastructure list
                infrastructures.add(rack);

                // Adding the physicalMachines
                Set<PhysicalmachineHB> phyMachines = rackPojo.getPhysicalmachines();
                for (PhysicalmachineHB phyMachinePojo : phyMachines)
                {
                    PhysicalMachine phyMachine = (PhysicalMachine) phyMachinePojo.toPojo();
                    phyMachine.setAssignedTo(rack);
                    infrastructures.add(phyMachine);

                    // Adding the HyperVisors
                    Set<HypervisorHB> hypervisorPhysicalList = phyMachinePojo.getHypervisors();
                    for (HypervisorHB hypervisorPojo : hypervisorPhysicalList)
                    {
                        HyperVisor hypervisor = (HyperVisor) hypervisorPojo.toPojo();
                        hypervisor.setAssignedTo(phyMachine);
                        infrastructures.add(hypervisor);

                        // Adding the VirtualMachines
                        Set<VirtualmachineHB> virtualMachines = hypervisorPojo.getVirtualmachines();
                        for (VirtualmachineHB virtualMachinePojo : virtualMachines)
                        {
                            VirtualMachine virtualMachine =
                                (VirtualMachine) virtualMachinePojo.toPojo();
                            virtualMachine.setAssignedTo(hypervisor);
                            infrastructures.add(virtualMachine);
                        }
                    }
                }
            }

            // Adding the physical machines in this Data Center, without a rack
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.isNull("rack"));
            conjunction.add(Restrictions.eq("dataCenter", datacenterPojo));
            ArrayList<PhysicalmachineHB> physicalMachinesWORack =
                (ArrayList<PhysicalmachineHB>) session.createCriteria(PhysicalmachineHB.class).add(
                    conjunction).list();
            for (PhysicalmachineHB physicalMachineHB : physicalMachinesWORack)
            {
                infrastructures.add((PhysicalMachine) physicalMachineHB.toPojo());
            }

            // We are done!
            transaction.commit();

            dataResult.setSuccess(true);
            dataResult.setData(infrastructures);
        }
        catch (HibernateException e)
        {
            if (transaction != null)
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, dataResult,
                "getInfrastructureByDataCenter", e);
        }

        return dataResult;
    }

    /**
     * Returns all data centers contained in the data base
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected DataResult<ArrayList<DataCenter>> getDataCenters()
    {

        DataResult<ArrayList<DataCenter>> dataResult = new DataResult<ArrayList<DataCenter>>();

        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try
        {
            transaction = session.beginTransaction();

            ArrayList<DataCenter> dataCentersPojo = new ArrayList<DataCenter>();
            ArrayList<DatacenterHB> dataCenters =
                (ArrayList<DatacenterHB>) HibernateUtil.getSession().createCriteria(
                    DatacenterHB.class).addOrder(Order.asc("name")).list();
            for (DatacenterHB datacenterHB : dataCenters)
            {
                DataCenter dataCenter = (DataCenter) datacenterHB.toPojo();
                dataCentersPojo.add(dataCenter);
            }

            dataResult.setData(dataCentersPojo);
            dataResult.setSuccess(true);
            dataResult.setMessage(InfrastructureCommand.resourceManager
                .getMessage("getDataCenters.success"));

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, dataResult,
                "getDataCenters", e);
        }

        return dataResult;
    }

    /**
     * Creates a new data center in the data base
     * 
     * @param dataCenter
     * @return the Data Center created in DDBB
     */
    protected DataResult<DataCenter> createDataCenter(UserSession userSession, DataCenter dataCenter)
    {
        DataResult<DataCenter> dataResult;
        dataResult = new DataResult<DataCenter>();
        dataResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            DatacenterHB datacenterPojo = (DatacenterHB) dataCenter.toPojoHB();
            datacenterPojo.setUserHBByIdUserCreation(userHB);
            datacenterPojo.setCreationDate(new Date());
            session.save(datacenterPojo);

            transaction.commit();

            dataCenter.setId(datacenterPojo.getIdDataCenter());
            dataResult.setData(dataCenter);
        }
        catch (HibernateException e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, dataResult,
                "createDataCenter", e);
        }
        return dataResult;
    }

    /**
     * Edits dataCenter's information in the data base
     * 
     * @param dataCenter
     * @return
     */
    protected BasicResult editDataCenter(UserSession userSession, DataCenter dataCenter)
    {
        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            DatacenterHB datacenterPojo =
                (DatacenterHB) session.get(DatacenterHB.class, dataCenter.getId());
            datacenterPojo.setName(dataCenter.getName());
            datacenterPojo.setSituation(dataCenter.getSituation());
            datacenterPojo.setLastModificationDate(new Date());

            datacenterPojo.setUserHBByIdUserLastModification(userHB);

            session.update(datacenterPojo);

            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, basicResult,
                "editDataCenter", e);
        }
        return basicResult;
    }

    /**
     * Deletes the selected data center from the data base
     * 
     * @param dataCenter
     * @return
     */
    protected BasicResult deleteDataCenter(DataCenter dataCenter)
    {

        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            DatacenterHB datacenterPojo =
                (DatacenterHB) session.get(DatacenterHB.class, dataCenter.getId());
            session.delete(datacenterPojo);
            basicResult = deleteVirtualInfrastructureFromDatacenter(datacenterPojo);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, basicResult,
                "deleteDataCenter", e);
        }

        return basicResult;
    }

    /* ______________________________ RACKS _______________________________ */

    /**
     * Creates a new rack in the data base
     */
    protected DataResult<Rack> createRack(UserSession userSession, Rack rack)
    {
        DataResult<Rack> dataResult;
        dataResult = new DataResult<Rack>();
        dataResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            RackHB rackHB = (RackHB) rack.toPojoHB();
            rackHB.setUserHBByIdUserCreataion(userHB);
            rackHB.setCreatioNdate(new Date());
            session.save(rackHB);

            rack.setId(rackHB.getIdRack());
            dataResult.setData(rack);

            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, dataResult,
                "createRack", e);
        }
        return dataResult;
    }

    /**
     * Deletes the rack from the data base
     * 
     * @param rack
     * @return
     */
    protected BasicResult deleteRack(Rack rack)
    {
        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            RackHB rackHB = (RackHB) session.get(RackHB.class, rack.getId());
            session.delete(rackHB);
            basicResult = deleteVirtualInfrastructureFromRack(rackHB);
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, basicResult,
                "deleteRack", e);
        }
        return basicResult;
    }

    /**
     * Edits rack's information in the data base
     * 
     * @param rack
     * @return
     */
    protected BasicResult editRack(UserSession userSession, Rack rack)
    {

        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;

        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            RackHB rackPojo =
                (RackHB) session.get(
                    com.abiquo.abiserver.business.hibernate.pojohb.infrastructure.RackHB.class,
                    rack.getId());
            rackPojo.setName(rack.getName());
            rackPojo.setShortDescription(rack.getShortDescription());
            rackPojo.setLargeDescription(rack.getLargeDescription());
            rackPojo.setLastModificationDate(new Date());

            rackPojo.setUserHBByIdUserLastModification(userHB);

            session.update(rackPojo);

            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, basicResult,
                "editRack", e);

        }
        return basicResult;
    }

    /*
     * ______________________________ PHYSICAL MACHINES _______________________________
     */
    /**
     * Creates a new physical machine in the data base
     * 
     * @param userSession the user in the session
     * @param physicalMachine the infrastructure pojo object
     * @return the DataResult
     */
    protected DataResult<PhysicalMachineCreation> createPhysicalMachine(UserSession userSession,
        PhysicalMachineCreation physicalMachineCreation)
    {
        DataResult<PhysicalMachineCreation> dataResult;
        dataResult = new DataResult<PhysicalMachineCreation>();

        Session session = null;
        Transaction transaction = null;
        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            PhysicalMachine physicalMachine = physicalMachineCreation.getPhysicalMachine();

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Creating the PhysicalMachineHB object to save the PhysicalMachine in the Data Base
            PhysicalmachineHB physicalMachineHB = (PhysicalmachineHB) physicalMachine.toPojoHB();
            physicalMachineHB.setUserHBByIdUserCreation(userHB);
            physicalMachineHB.setCreationDate(new Date());
            session.save(physicalMachineHB);

            // Saving the NetworkModules and DNS of this PhysicalMachine
            NetworkmoduleHB networkmoduleHB;
            int idNetworkModule = 1; // We need to generate one of the fields of the NetworkModule
                                     // composite Primary Key
            for (NetworkModule networkModule : physicalMachine.getNetworkModuleList())
            {
                networkmoduleHB = (NetworkmoduleHB) networkModule.toPojoHB();

                networkmoduleHB.getId().setIdNetworkModule(idNetworkModule);
                networkmoduleHB.getId().setIdPhysicalMachine(
                    physicalMachineHB.getIdPhysicalMachine());
                networkmoduleHB.setUserHBByIdUserCreation(userHB);
                networkmoduleHB.setCreationDate(new Date());
                session.save(networkmoduleHB);

                // Saving the DNS of this NetworkModule
                DnsHB dnsHB;
                int idDNS = 1; // We need to generate one of the fields of the DNS composite Primary
                               // Key
                for (DNS dns : networkModule.getDns())
                {
                    dnsHB = (DnsHB) dns.toPojoHB();

                    dnsHB.getId().setIdDns(idDNS);
                    dnsHB.getId().setIdNetworkModule(networkmoduleHB.getId().getIdNetworkModule());
                    dnsHB.getId().setIdPhysicalMachine(physicalMachineHB.getIdPhysicalMachine());
                    dnsHB.setUserHBByIdUserCreation(userHB);
                    dnsHB.setCreationDate(new Date());
                    session.save(dnsHB);
                    idDNS++;
                }

                idNetworkModule++;
            }

            // Creating the hypervisors, if there is any
            ArrayList<HyperVisor> hypervisorList = physicalMachineCreation.getHypervisors();
            ArrayList<HyperVisor> createdHypervisorList = new ArrayList<HyperVisor>();
            HypervisorHB hypervisorHBToCreate;
            for (HyperVisor hypervisorToCreate : hypervisorList)
            {
                hypervisorHBToCreate = (HypervisorHB) hypervisorToCreate.toPojoHB();
                hypervisorHBToCreate.setPhysicalMachine(physicalMachineHB);
                hypervisorHBToCreate.setUserHBByIdUserCreation(userHB);
                hypervisorHBToCreate.setCreationDate(new Date());

                session.save(hypervisorHBToCreate);

                createdHypervisorList.add((HyperVisor) hypervisorHBToCreate.toPojo());

            }

            // Returning the PhysicalMachine and the Hypervisors created to the client
            PhysicalmachineHB physicalMachineHBCreated =
                (PhysicalmachineHB) session.get(PhysicalmachineHB.class, physicalMachineHB
                    .getIdPhysicalMachine());
            PhysicalMachine physicalMachineCreated =
                (PhysicalMachine) physicalMachineHBCreated.toPojo();

            transaction.commit();

            physicalMachineCreation.setPhysicalMachine(physicalMachineCreated);
            physicalMachineCreation.setHypervisors(createdHypervisorList);
            dataResult.setData(physicalMachineCreation);
            dataResult.setSuccess(true);
            dataResult.setMessage(InfrastructureCommand.resourceManager
                .getMessage("createPhysicalMachine.success"));
        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, dataResult,
                "createPhysicalMachine", e);

        }
        return dataResult;
    }

    /**
     * Deletes the physical machine from the data base
     * 
     * @param physicalMachine
     * @return
     */
    protected BasicResult deletePhysicalMachine(PhysicalMachine physicalMachine)
    {
        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            PhysicalmachineHB physicalMachineHb =
                (PhysicalmachineHB) session.get(PhysicalmachineHB.class, physicalMachine.getId());
            session.delete(physicalMachineHb);
            basicResult = deleteVirtualInfrastructureFromPhysicalMachine(physicalMachineHb);
            transaction.commit();

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(InfrastructureCommand.resourceManager, basicResult,
                "deletePhysicalMachine", e);

        }
        return basicResult;
    }

    /**
     * Edits physical machine's information in the data base TODO: Possibly we need to connect
     * AbiCloud WS too, for example, when we change information related to Network Module
     * 
     * @param physicalMachine
     * @return
     */
    protected BasicResult editPhysicalMachine(UserSession userSession,
        PhysicalMachine physicalMachine)
    {

        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // The new rack for the Physical Machine
            PhysicalmachineHB physicalMachineHb =
                (PhysicalmachineHB) session.get(PhysicalmachineHB.class, physicalMachine.getId());
            RackHB rackHB =
                (RackHB) session
                    .get(RackHB.class, ((Rack) physicalMachine.getAssignedTo()).getId());
            physicalMachineHb.setRack(rackHB);

            // The new SO for the Physical Machine
            SoHB so = new SoHB();
            so.setDescription(physicalMachine.getHostSO().getDescription());
            so.setIdSo(physicalMachine.getHostSO().getId());
            physicalMachineHb.setSo(so);

            // Updating the other attributes
            physicalMachineHb.setName(physicalMachine.getName());
            physicalMachineHb.setDescription(physicalMachine.getDescription());
            physicalMachineHb.setCpu(physicalMachine.getCpu());
            physicalMachineHb.setRam(physicalMachine.getRam());
            physicalMachineHb.setHd(physicalMachine.getHd());

            // User and Date modification
            physicalMachineHb.setUserHBByIdUserLastModification(userHB);
            physicalMachineHb.setLastModificationDate(new Date());

            session.update(physicalMachineHb);

            // Updating the Network Modules
            // First, we have to delete the old network modules
            for (NetworkmoduleHB networkmoduleHB : physicalMachineHb.getNetworkmodules())
            {
                session.delete(networkmoduleHB);
            }

            // Now, we add the new network modules and its DNS
            NetworkmoduleHB networkmoduleHB;
            int idNetworkModule = 1; // We need to generate one of the fields of the NetworkModule
                                     // composite Primary Key
            for (NetworkModule networkModule : physicalMachine.getNetworkModuleList())
            {
                networkmoduleHB = (NetworkmoduleHB) networkModule.toPojoHB();

                networkmoduleHB.getId().setIdNetworkModule(idNetworkModule);
                networkmoduleHB.getId().setIdPhysicalMachine(
                    physicalMachineHb.getIdPhysicalMachine());
                networkmoduleHB.setUserHBByIdUserCreation(userHB);
                networkmoduleHB.setCreationDate(new Date());
                session.save(networkmoduleHB);

                // Saving the DNS of this NetworkModule
                DnsHB dnsHB;
                int idDNS = 1; // We need to generate one of the fields of the DNS composite Primary
                               // Key
                for (DNS dns : networkModule.getDns())
                {
                    dnsHB = (DnsHB) dns.toPojoHB();

                    dnsHB.getId().setIdDns(idDNS);
                    dnsHB.getId().setIdNetworkModule(networkmoduleHB.getId().getIdNetworkModule());
                    dnsHB.getId().setIdPhysicalMachine(physicalMachineHb.getIdPhysicalMachine());
                    dnsHB.setUserHBByIdUserCreation(userHB);
                    dnsHB.setCreationDate(new Date());
                    session.save(dnsHB);
                    idDNS++;
                }

                idNetworkModule++;
            }

            transaction.commit();

        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "editPhysicalMachine", e);

        }

        return basicResult;
    }

    /**
     * Moves a physical machine from one rack to another. This means: 1. Edit the necessary
     * information in the data base to save the information (the physicalMachine will contain the ID
     * of the new Rack) 2. Do we need to connect with AbiCloud WS?
     * 
     * @param physicalMachine
     * @return
     */
    protected BasicResult movePhysicalMachine(UserSession userSession,
        PhysicalMachine physicalMachine)
    {

        BasicResult basicResult;
        basicResult = editPhysicalMachine(userSession, physicalMachine);

        return basicResult;

    }

    /*
     * ______________________________ HYPERVISORS _______________________________
     */

    /**
     * Creates a new Hypervisor in Data Base
     * 
     * @param userSession the UserSession that called this method
     * @param hypervisor The Hypervisor that will be created in Data Base
     * @return a DataResult object containing the HyperVisor that has been created in DataBase, if
     *         the query had success
     */
    protected DataResult<HyperVisor> createHypervisor(UserSession userSession, HyperVisor hypervisor)
    {
        DataResult<HyperVisor> dataResult = new DataResult<HyperVisor>();
        dataResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            HypervisorHB hypervisorHB = (HypervisorHB) hypervisor.toPojoHB();
            hypervisorHB.setUserHBByIdUserCreation(userHB);
            hypervisorHB.setCreationDate(new Date());

            session.save(hypervisorHB);

            transaction.commit();

            dataResult.setData((HyperVisor) hypervisorHB.toPojo());
        }
        catch (HibernateException e)
        {
            if (transaction != null & transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "createHypervisor", e);
        }
        return dataResult;
    }

    /**
     * Edits an existing in Hypervisor in DataBase, with the information contained in parameter
     * hypervisor
     * 
     * @param userSession UserSession object with the user's session who called this method
     * @param hypervisor The hypervisor that will be edited, with the new information
     * @return A BasicResult object with the result of the edition
     */
    protected BasicResult editHypervisor(UserSession userSession, HyperVisor hypervisor)
    {

        BasicResult basicResult;
        basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;

        try
        {
            UserHB userHB = SessionUtil.findUserHBByName(userSession.getUser());

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            HypervisorHB hypervisorHB =
                (HypervisorHB) session.get(HypervisorHB.class, hypervisor.getId());
            PhysicalmachineHB physicalMachineHB =
                (PhysicalmachineHB) session.get(PhysicalmachineHB.class, hypervisor.getAssignedTo()
                    .getId());

            // Updating the Hypervisor
            hypervisorHB.setShortDescription(hypervisor.getShortDescription());
            hypervisorHB.setType((HypervisorTypeHB) hypervisor.getType().toPojoHB());
            hypervisorHB.setIp(hypervisor.getIp());
            hypervisorHB.setPort(hypervisor.getPort());
            hypervisorHB.setPhysicalMachine(physicalMachineHB);
            hypervisorHB.setUserHBByIdUserLastModification(userHB);
            hypervisorHB.setLastModificationDate(new Date());

            session.update(hypervisorHB);

            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "editHypervisor", e);
        }

        basicResult.setMessage(InfrastructureCommand.resourceManager
            .getMessage("editHypervisor.success"));
        basicResult.setSuccess(true);

        return basicResult;
    }

    protected BasicResult deleteHypervisor(HyperVisor hypervisor)
    {
        BasicResult basicResult;
        basicResult = new BasicResult();

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            HypervisorHB hypervisorHB =
                (HypervisorHB) session.get(HypervisorHB.class, hypervisor.getId());
            session.delete(hypervisorHB);

            // TODO Do we have to delete Virtual Infrastructure when an Hypervisor has been deleted?
            transaction.commit();

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "deleteHypervisor", e);
        }

        basicResult.setSuccess(true);
        return basicResult;
    }

    /*
     * ______________________________ VIRTUAL MACHINES _______________________________
     */

    /**
     * Creates a new Virtual Machine in the Data Base
     * 
     * @param virtualMachine A VirtualMachine object containing the necessary information to create
     *            a new Virtual Machine. UUID and State fields will be ignored, since they will be
     *            generated.
     * @return a DataResult object containing a VirtualMachine object with the Virtual Machine
     *         created
     */
    protected DataResult<VirtualMachine> createVirtualMachine(VirtualMachine virtualMachine)
    {
        DataResult<VirtualMachine> dataResult = new DataResult<VirtualMachine>();
        BasicResult wsResult = new BasicResult();
        VirtualMachine createdVirtualMachine;

        Session session = null;
        Transaction transaction = null;

        try
        {
            // Starting the hibernate session
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting State "NOT_DEPLOYED"
            StateHB stateHB = (StateHB) session.get(StateHB.class, State.NOT_DEPLOYED);

            // Generate the Virtual Machine that will be created
            VirtualmachineHB virtualMachineHB = (VirtualmachineHB) virtualMachine.toPojoHB();
            virtualMachineHB.setState(stateHB);
            virtualMachineHB.setUuid(UUID.randomUUID().toString());

            session.save(virtualMachineHB);

            // Recovering the Virtual Machine created, that will be returned to the user
            createdVirtualMachine = (VirtualMachine) virtualMachineHB.toPojo();

            // TODO Call the WebService to create the VirtualMachine
            /*
             * InfrastructureWS infrWS = new InfrastructureWS(); wsResult =
             * infrWS.createVirtualMachine(virtualMachine); if(! wsResult.getSuccess()) { Exception
             * e = new Exception(wsResult.getMessage()); throw e; }
             */

            // If everything went fine, we can save the hibernate session and return the result
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "createVirtualMachine", e);

            return dataResult;
        }

        dataResult.setData(createdVirtualMachine);
        dataResult.setMessage(wsResult.getMessage());
        dataResult.setSuccess(true);

        return dataResult;

    }

    /**
     * Deletes the virtual machine. 1. From the data base 2. Connect with AbiCloud WS to delete it
     * from the Physical Machine
     * 
     * @param virtualMachine
     * @return
     */
    protected BasicResult deleteVirtualMachine(VirtualMachine virtualMachine)
    {
        // TODO Connect with database
        BasicResult basicResult = null;
        InfrastructureWS infrWS = null;
        VirtualmachineHB virtualMachinePojo = null;
        Transaction transaction = null;
        try
        {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            infrWS = new InfrastructureWS();
            virtualMachinePojo =
                (VirtualmachineHB) session.get(VirtualmachineHB.class, virtualMachine.getId());
            session.delete(virtualMachinePojo);
            basicResult = infrWS.deleteVirtualMachine(virtualMachine);
        }
        catch (Exception e)
        {
            this.errorManager.reportError(resourceManager, basicResult, "deleteVirtualMachine", e);
        }
        if (basicResult.getSuccess())
        {
            transaction.commit();
        }
        return basicResult;
    }

    /**
     * Edits virtual machine's information in the data base
     * 
     * @param virtualMachine
     * @return
     */
    protected BasicResult editVirtualMachine(UserSession userSession, VirtualMachine virtualMachine)
    {
        BasicResult basicResult = new BasicResult();
        InfrastructureWS infrWS = null;
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            VirtualmachineHB virtualMachineHB =
                (VirtualmachineHB) session.get(VirtualmachineHB.class, virtualMachine.getId());

            // The new Hypervisor for this VirtualMachine
            HypervisorHB newHyperVisorHB =
                (HypervisorHB) session.get(HypervisorHB.class, virtualMachine.getAssignedTo()
                    .getId());
            virtualMachineHB.setHypervisor(newHyperVisorHB);

            // TODO Anunciar al WS del cambio de Hypervisor de la Maquina Virtual
            // TODO Tener en cuenta cuando la Maquina Virtual esta encendida, etc.
            infrWS = new InfrastructureWS();
            basicResult = infrWS.editVirtualMachine(virtualMachine);

            if (basicResult.getSuccess())
            {
                // Updating the other attributes
                virtualMachineHB.setName(virtualMachine.getName());
                virtualMachineHB.setDescription(virtualMachine.getDescription());
                virtualMachineHB.setCpu(virtualMachine.getCpu());
                virtualMachineHB.setRam(virtualMachine.getRam());
                virtualMachineHB.setHd(virtualMachine.getHd());
                virtualMachineHB
                    .setHighDisponibility(virtualMachine.getHighDisponibility() ? 1 : 0);
                session.update(virtualMachineHB);
                transaction.commit();
            }

        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "editVirtualMachine", e);
        }

        basicResult.setSuccess(true);
        basicResult.setMessage(InfrastructureCommand.resourceManager
            .getMessage("editVirtualMachine.success"));

        return basicResult;
    }

    /**
     * Performs the action in Abicloud associated with the attribute "state" in the virtual machine
     * Connects with AbiCloud WS to save the new virtual machine's state in Data Base
     * 
     * @param virtualMachine
     * @param actionState the action state to perform
     * @return
     */
    private BasicResult setVirtualMachineState(VirtualMachine virtualMachine, String actionState)
    {
        InfrastructureWS infrWS = null;
        BasicResult basicResult = null;
        try
        {
            infrWS = new InfrastructureWS();
            basicResult = infrWS.setVirtualMachineState(virtualMachine, actionState);
        }
        catch (Exception e)
        {
            this.errorManager.reportError(resourceManager, basicResult, "setVirtualMachineState",
                e, actionState);
        }

        return basicResult;
    }

    /**
     * Performs a "Start" action in the Virtual Machine
     * 
     * @param virtualMachine
     * @return a DataResult object, with a State object that represents the state "Running"
     */
    protected DataResult<State> startVirtualMachine(VirtualMachine virtualMachine)
    {
        DataResult<State> dataResult = new DataResult<State>();
        BasicResult basicResult = null;

        // Saving the state of the virtual machine sent by the user
        State oldState = virtualMachine.getState();

        // Checking the current state of the virtual machine
        DataResult<State> currentStateAndAllow;
        try
        {
            currentStateAndAllow = checkVirtualMachineState(virtualMachine);
        }
        catch (Exception e)
        {
            // There was a problem checking the state of the virtual machine. We can not
            // manipulate it
            this.errorManager.reportError(resourceManager, dataResult, "startVirtualMachine", e);
            dataResult.setData(oldState);
            return dataResult;
        }

        if (currentStateAndAllow.getSuccess())
        {
            // The Virtual Machine is now blocked to other users, and we can manipulate it
            if (virtualMachine.getState().getId() == State.PAUSED)
            {
                basicResult =
                    setVirtualMachineState(virtualMachine, AbiCloudConstants.RESUME_ACTION);
            }
            else if (virtualMachine.getState().getId() == State.POWERED_OFF)
            {
                basicResult =
                    setVirtualMachineState(virtualMachine, AbiCloudConstants.POWERUP_ACTION);
            }

            dataResult.setMessage(basicResult.getMessage());
            dataResult.setSuccess(basicResult.getSuccess());
            if (basicResult.getSuccess())
            {
                // Everything went fine. Updating the virtual machine with the new state
                // and returning the new state
                State newState = new State();
                newState.setId(State.RUNNING);

                basicResult = updateStateInDB(virtualMachine, newState);
                dataResult.setData(newState);
            }
            else
            {
                // There was an error changing the state
                // Leaving the virtual machine with its old state
                updateStateInDB(virtualMachine, oldState);
                dataResult.setData(oldState);
            }
        }
        else
        {
            // The Virtual Machine is being used by other user, or it is not up to date.
            // We inform the new state
            dataResult.setSuccess(true);
            dataResult.setMessage(InfrastructureCommand.resourceManager
                .getMessage("startVirtualMachine.success"));
            dataResult.setData(currentStateAndAllow.getData());

        }

        return dataResult;

    }

    /**
     * Performs a "Pause" action in the Virtual Machine
     * 
     * @param virtualMachine
     * @return a DataResult object, with a State object that represents the state "Paused"
     */
    protected DataResult<State> pauseVirtualMachine(VirtualMachine virtualMachine)
    {
        DataResult<State> dataResult = new DataResult<State>();

        // Saving the state of the virtual machine sent by the user
        State oldState = virtualMachine.getState();

        // Checking the current state of the virtual machine
        DataResult<State> currentStateAndAllow;
        try
        {
            currentStateAndAllow = checkVirtualMachineState(virtualMachine);
        }
        catch (Exception e)
        {
            // There was a problem checking the state of the virtual machine. We can not
            // manipulate it
            this.errorManager.reportError(resourceManager, dataResult, "pauseVirtualMachine", e);
            dataResult.setData(oldState);
            return dataResult;
        }

        if (currentStateAndAllow.getSuccess())
        {
            // The Virtual Machine is now blocked to other users, and we can manipulate it
            BasicResult basicResult =
                setVirtualMachineState(virtualMachine, AbiCloudConstants.PAUSE_ACTION);

            dataResult.setMessage(basicResult.getMessage());
            dataResult.setSuccess(basicResult.getSuccess());
            if (basicResult.getSuccess())
            {
                // Everything went fine. Updating the virtual machine with the new state
                // and returning the new state
                State newState = new State();
                newState.setId(State.PAUSED);

                basicResult = updateStateInDB(virtualMachine, newState);
                dataResult.setData(newState);
            }
            else
            {
                // There was an error changing the state
                // Leaving the virtual machine with its old state
                updateStateInDB(virtualMachine, oldState);
                dataResult.setData(oldState);
            }
        }
        else
        {
            // The Virtual Machine is being used by other user, or it is not up to date.
            // We inform the new state
            dataResult.setSuccess(true);
            dataResult.setMessage(InfrastructureCommand.resourceManager
                .getMessage("pauseVirtualMachine.success"));
            dataResult.setData(currentStateAndAllow.getData());

        }

        return dataResult;
    }

    /**
     * Performs a "Reboot" action in the Virtual Machine
     * 
     * @param virtualMachine
     * @return a DataResult object, with a State object that represents the state "Running"
     */
    protected DataResult<State> rebootVirtualMachine(VirtualMachine virtualMachine)
    {
        // Rebooting the machine implies powering off and powering up
        DataResult<State> dataResult = new DataResult<State>();

        // Saving the state of the virtual machine sent by the user
        State oldState = virtualMachine.getState();

        // Checking the current state of the virtual machine
        DataResult<State> currentStateAndAllow;
        try
        {
            currentStateAndAllow = checkVirtualMachineState(virtualMachine);
        }
        catch (Exception e)
        {
            // There was a problem checking the state of the virtual machine. We can not
            // manipulate it
            this.errorManager.reportError(resourceManager, dataResult, "rebootVirtualMachine", e);
            dataResult.setData(oldState);
            return dataResult;
        }

        if (currentStateAndAllow.getSuccess())
        {
            // The Virtual Machine is now blocked to other users, and we can manipulate it

            // First we have to shut down the virtual machine
            BasicResult basicResult =
                setVirtualMachineState(virtualMachine, AbiCloudConstants.POWERDOWN_ACTION);
            if (!basicResult.getSuccess())
            {
                // There was a problem shuting down the virtual machine
                // Leaving the virtual machine with its old state
                updateStateInDB(virtualMachine, oldState);

                // Generating the result
                dataResult.setMessage(basicResult.getMessage());
                dataResult.setSuccess(basicResult.getSuccess());
                dataResult.setData(oldState);
            }

            else
            {
                // The shutting down had success. Powering on the virtual machine again
                BasicResult basicResultPowerUP =
                    setVirtualMachineState(virtualMachine, AbiCloudConstants.POWERUP_ACTION);

                dataResult.setMessage(basicResultPowerUP.getMessage());
                dataResult.setSuccess(basicResultPowerUP.getSuccess());
                if (basicResult.getSuccess())
                {
                    // Everything went fine. Updating the virtual machine with the new state
                    // and returning the new state
                    State newState = new State();
                    newState.setId(State.RUNNING);

                    basicResult = updateStateInDB(virtualMachine, newState);
                    dataResult.setData(newState);
                }
                else
                {
                    // There was an error powering on the virtual machine
                    // Leaving the virtual machine as power off
                    State powerOffState = new State();
                    powerOffState.setId(State.POWERED_OFF);
                    updateStateInDB(virtualMachine, powerOffState);

                    dataResult.setData(oldState);
                }
            }
        }
        else
        {
            // The Virtual Machine is being used by other user, or it is not up to date.
            // We inform the new state
            dataResult.setSuccess(true);
            dataResult.setMessage(InfrastructureCommand.resourceManager
                .getMessage("rebootVirtualMachine.success"));
            dataResult.setData(currentStateAndAllow.getData());

        }

        return dataResult;
    }

    /**
     * Performs a "Shutdown" action in the Virtual Machine
     * 
     * @param virtualMachine
     * @return a DataResult object, with a State object that represents the state "Powered Off"
     */
    protected DataResult<State> shutdownVirtualMachine(VirtualMachine virtualMachine)
    {
        DataResult<State> dataResult = new DataResult<State>();

        // Saving the state of the virtual machine sent by the user
        State oldState = virtualMachine.getState();

        // Checking the current state of the virtual machine
        DataResult<State> currentStateAndAllow;
        try
        {
            currentStateAndAllow = checkVirtualMachineState(virtualMachine);
        }
        catch (Exception e)
        {
            // There was a problem checking the state of the virtual machine. We can not
            // manipulate it
            this.errorManager.reportError(resourceManager, dataResult, "shutdownVirtualMachine", e);
            dataResult.setData(oldState);
            return dataResult;
        }

        if (currentStateAndAllow.getSuccess())
        {
            // The Virtual Machine is now blocked to other users, and we can manipulate it
            BasicResult basicResult =
                setVirtualMachineState(virtualMachine, AbiCloudConstants.POWERDOWN_ACTION);

            dataResult.setMessage(basicResult.getMessage());
            dataResult.setSuccess(basicResult.getSuccess());
            if (basicResult.getSuccess())
            {
                // Everything went fine. Updating the virtual machine with the new state
                // and returning the new state
                State newState = new State();
                newState.setId(State.POWERED_OFF);

                basicResult = updateStateInDB(virtualMachine, newState);
                dataResult.setData(newState);
            }
            else
            {
                // There was an error changing the state
                // Leaving the virtual machine with its old state
                updateStateInDB(virtualMachine, oldState);
                dataResult.setData(oldState);
            }
        }
        else
        {
            // The Virtual Machine is being used by another user, or it is not up to date.
            // We inform of the new state
            dataResult.setSuccess(true);
            dataResult.setMessage(InfrastructureCommand.resourceManager
                .getMessage("shutdownVirtualMachine.success"));
            dataResult.setData(currentStateAndAllow.getData());

        }

        return dataResult;
    }

    /**
     * Moves a virtual machine from a Physical Machine to another virtualMachine's "assignedTo"
     * attribute will contain the new HyperVisor, to which the virtual machine will be assigned.
     * 
     * @param virtualMachine
     * @return
     */
    protected BasicResult moveVirtualMachine(UserSession userSession, VirtualMachine virtualMachine)
    {

        return editVirtualMachine(userSession, virtualMachine);
    }

    /**
     * Private helper to update the state in the Database
     * 
     * @param virtualMachine
     * @param newState the new state to update
     * @return a basic Result with the operation result
     */
    private BasicResult updateStateInDB(VirtualMachine virtualMachine, State newState)
    {
        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);

        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            VirtualmachineHB virtualMachinePojo =
                (VirtualmachineHB) session.get(VirtualmachineHB.class, virtualMachine.getId());
            StateHB newStatePojo = new StateHB();
            newStatePojo.setIdState(newState.getId());
            virtualMachinePojo.setState(newStatePojo);
            session.update(virtualMachinePojo);

            transaction.commit();
        }
        catch (HibernateException e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, basicResult, "updateStateInDB", e);
        }
        return basicResult;
    }

    /**
     * Private helper to delete the virtual infrastructure related to this dataCenter
     * 
     * @param dataCenter the dataCenter to delete
     * @throws JAXBException
     * @throws FaultException
     * @throws IOException
     * @throws DatatypeConfigurationException
     * @throws SOAPException
     */
    private BasicResult deleteVirtualInfrastructureFromDatacenter(DatacenterHB datacenterPojo)
        throws JAXBException, SOAPException, DatatypeConfigurationException, IOException,
        FaultException
    {
        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);
        Set<RackHB> racks = datacenterPojo.getRacks();
        for (RackHB rackPojo : racks)
        {
            basicResult = deleteVirtualInfrastructureFromRack(rackPojo);
            if (!basicResult.getSuccess())
                return basicResult;
        }
        return basicResult;
    }

    /**
     * Private helper to delete the virtual infrastructure contained in the rack
     * 
     * @param rackPojo the rack to delete
     * @throws JAXBException
     * @throws FaultException
     * @throws IOException
     * @throws DatatypeConfigurationException
     * @throws SOAPException
     */
    private BasicResult deleteVirtualInfrastructureFromRack(RackHB rackPojo) throws JAXBException,
        SOAPException, DatatypeConfigurationException, IOException, FaultException
    {
        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);
        // Adding the physicalMachines
        Set<PhysicalmachineHB> phyMachines = rackPojo.getPhysicalmachines();
        for (PhysicalmachineHB phyMachinePojo : phyMachines)
        {
            basicResult = deleteVirtualInfrastructureFromPhysicalMachine(phyMachinePojo);
            if (!basicResult.getSuccess())
                return basicResult;
        }
        return basicResult;

    }

    /**
     * Private helper to delete all the virtual infrastructure contained in the physical machine
     * 
     * @param phyMachinePojo the physical machine to delete
     * @return TODO
     * @throws JAXBException
     * @throws FaultException
     * @throws IOException
     * @throws DatatypeConfigurationException
     * @throws SOAPException
     */
    private BasicResult deleteVirtualInfrastructureFromPhysicalMachine(
        PhysicalmachineHB phyMachinePojo) throws JAXBException, SOAPException,
        DatatypeConfigurationException, IOException, FaultException
    {
        BasicResult basicResult;
        basicResult = new BasicResult();
        basicResult.setSuccess(true);
        VirtualApplianceWS virtualApplianceWs;
        // Adding the HyperVisors
        Set<HypervisorHB> hypervisorPhysicalList = phyMachinePojo.getHypervisors();
        for (HypervisorHB hypervisorPojo : hypervisorPhysicalList)
        {
            // Adding the VirtualMachines
            Set<VirtualmachineHB> virtualMachines = hypervisorPojo.getVirtualmachines();
            for (VirtualmachineHB virtualMachinePojo : virtualMachines)
            {
                logger
                    .info("Getting the virtual applications to delete contained in this physical machine: "
                        + phyMachinePojo.getName());
                VirtualimageHB image = virtualMachinePojo.getImage();
                NodeHB nodeHB =
                    (NodeHB) HibernateUtil.getSession().createCriteria(NodeHB.class).add(
                        Restrictions.eq("virtualmachine", virtualMachinePojo)).uniqueResult();

                if (nodeHB != null)
                {
                    // TODO REFACTOR THIS!
                    /*
                     * VirtualAppliance virtualAppliance = (VirtualAppliance)
                     * nodeHB.getVirtualapp().toPojo();
                     * logger.info("Deleting the virtual appliance: " +virtualAppliance.getName());
                     * virtualApplianceWs = new VirtualApplianceWS(); basicResult =
                     * virtualApplianceWs.shutdownVirtualAppliance(virtualAppliance); if
                     * (basicResult.getSuccess()) basicResult =
                     * virtualApplianceWs.deleteVirtualAppliance(virtualAppliance); //TODO check the
                     * result logger.info("Virtual appliance deleted");
                     */
                }
            }
        }
        return basicResult;

    }

    /**
     * Checks if the state of a given virtual machine, is actually the last valid state in the Data
     * Base If it is the same, the state of the virtual machine will be updated to
     * State.IN_PROGRESS, and a boolean will be returned to true, to indicate that the virtual
     * machine can be manipulated Otherwise, the current state will be returned, and the boolean
     * will be set to false, indicating that the virtual machine can not be manipulated
     * 
     * @param virtualMachine The virtual machine that will be checked
     * @return A DataResult object, containing a boolean that indicates if the virtual machine can
     *         be manipulated and, in any case, it will contain the last valid state of the virtual
     *         machine
     * @throws Exception An Exception is thrown if there was a problem connecting to the Data base
     */
    private DataResult<State> checkVirtualMachineState(VirtualMachine virtualMachine)
        throws Exception
    {
        Session session = null;
        Transaction transaction = null;

        DataResult<State> currentStateAndAllow = new DataResult<State>();

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Getting the last state of the virtual machine
            VirtualmachineHB virtualMachineHB =
                (VirtualmachineHB) session.get(VirtualmachineHB.class, virtualMachine.getId());

            if (virtualMachine.getState().getId() == virtualMachineHB.getState().getIdState()
                && virtualMachineHB.getState().getIdState() != State.IN_PROGRESS)
            {
                // The given virtual machine is up to date, and is not in progress.
                // We set it now to IN_PROGRESS, and return that it is allowed to manipulate it
                StateHB newStateHB = new StateHB();
                newStateHB.setIdState(State.IN_PROGRESS);
                virtualMachineHB.setState(newStateHB);

                session.update(virtualMachineHB);

                // Generating the result
                currentStateAndAllow.setSuccess(true);
                currentStateAndAllow.setData((State) newStateHB.toPojo());
            }
            else
            {
                // The given virtual machine is not up to date, or the virtual machine
                // is already in the state State.IN_PROGRESS. Manipulating it is not allowed

                // Generating the result
                currentStateAndAllow.setSuccess(false);
                currentStateAndAllow.setData((State) virtualMachineHB.getState().toPojo());
            }

            transaction.commit();
        }

        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            throw e;
        }

        return currentStateAndAllow;
    }

    /**
     * Checks the current state of a list of virtual machines
     * 
     * @param virtualMachinesToCheck ArrayList with the list of virtual machines to check
     * @return A DataResult object containing a list of the same virtual machines with their state
     *         updated
     */
    @SuppressWarnings("unchecked")
    protected DataResult<ArrayList<VirtualMachine>> checkVirtualMachinesState(
        ArrayList<VirtualMachine> virtualMachinesToCheck)
    {
        DataResult<ArrayList<VirtualMachine>> dataResult =
            new DataResult<ArrayList<VirtualMachine>>();
        ArrayList<VirtualMachine> virtualMachinesChecked = new ArrayList<VirtualMachine>();

        Session session = null;
        Transaction transaction = null;

        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Generating the list of id's of the virtual machines requested
            ArrayList<Integer> virtualMachinesToCheckIds = new ArrayList<Integer>();
            for (VirtualMachine virtualMachine : virtualMachinesToCheck)
            {
                virtualMachinesToCheckIds.add(virtualMachine.getId());
            }

            // Getting the virtual machines updated from the data base
            ArrayList<VirtualmachineHB> virtualMachinesHBChecked =
                (ArrayList<VirtualmachineHB>) session.createCriteria(VirtualmachineHB.class).add(
                    Restrictions.in("idVm", virtualMachinesToCheckIds)).list();

            // Returning the result
            for (VirtualmachineHB virtualMachineHB : virtualMachinesHBChecked)
            {
                virtualMachinesChecked.add((VirtualMachine) virtualMachineHB.toPojo());
            }

            transaction.commit();

            dataResult.setSuccess(true);
            dataResult.setMessage(resourceManager.getMessage("checkVirtualMachinesState.success"));
            dataResult.setData(virtualMachinesChecked);
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();

            this.errorManager.reportError(resourceManager, dataResult, "checkVirtualMachinesState",
                e);
        }

        return dataResult;
    }

}
