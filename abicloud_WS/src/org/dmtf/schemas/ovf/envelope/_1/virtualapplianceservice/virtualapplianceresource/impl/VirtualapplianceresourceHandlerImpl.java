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
 * Consell de Cent 296 principal 2�, 08007 Barcelona, Spain.
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
package org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice.virtualapplianceresource.impl;

import com.abiquo.abicloud.db.DB;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;

import org.dmtf.schemas.ovf.envelope._1.AnnotationSectionType;
import org.dmtf.schemas.ovf.envelope._1.DiskSectionType;
import org.dmtf.schemas.ovf.envelope._1.EntityType;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;
import org.dmtf.schemas.ovf.envelope._1.ObjectFactory;
import org.dmtf.schemas.ovf.envelope._1.RASDType;
import org.dmtf.schemas.ovf.envelope._1.ReferencesType;
import org.dmtf.schemas.ovf.envelope._1.SectionType;
import org.dmtf.schemas.ovf.envelope._1.VSSDType;
import org.dmtf.schemas.ovf.envelope._1.VirtualDiskDescType;
import org.dmtf.schemas.ovf.envelope._1.VirtualHardwareSectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemCollectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemType;
import org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_resourceallocationsettingdata.ResourceType;
import org.dmtf.schemas.wbem.wscim._1.common.CimString;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.model.AbsVirtualMachine;
import com.abiquo.abicloud.model.MachineState;
import com.abiquo.abicloud.model.VirtualAppliance;
import com.abiquo.abicloud.model.VirtualApplianceModel;
import com.abiquo.abicloud.model.VirtualDisk;
import com.abiquo.abicloud.model.VirtualSystemModel;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;
import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.InvalidSelectorsFault;
import com.sun.ws.management.Management;
import com.sun.ws.management.UnsupportedFeatureFault;
import com.sun.ws.management.framework.Utilities;
import com.sun.ws.management.transfer.InvalidRepresentationFault;
import com.sun.ws.management.transfer.TransferExtensions;
import com.sun.ws.management.xml.XmlBinding;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringReader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Specific implementations of VirtualApplianceResourceHandler. This class handles the basic
 * operations
 * 
 * @author abiquo
 */
public class VirtualapplianceresourceHandlerImpl
{

    private static final ObjectFactory virtualApplianceFactory = new ObjectFactory();

    private final static Logger logger =
        LoggerFactory.getLogger(VirtualapplianceresourceHandlerImpl.class);

    private static final QName QNAME =
        new QName("http://schemas.dmtf.org/ovf/envelope/1", "Envelope");

    private static XmlBinding xmlBinding = null;

    private static final DB db = new DB();

    // Create the XmlBinding object
    static
    {
        try
        {
            xmlBinding = new XmlBinding(null, "org.dmtf.schemas.ovf.envelope._1");
        }
        catch (JAXBException e)
        {
            logger.error("Unable to create XMLBinding object", e);
        }
    }

    /**
     * Get operations
     * 
     * @param request the request
     * @param response the generated response
     */
    public static void get(Management request, Management response)
    {

        String id = getIdSelector(request);

        try
        {
            VirtualAppliance virtualAppliance =
                VirtualApplianceModel.getModel().getVirtualAppliance(id);

            if (virtualAppliance == null)
            {
                AbsVirtualMachine virtualMachine =
                    VirtualSystemModel.getModel().getMachine(UUID.fromString(id));
                if (virtualMachine == null)
                {
                    logger.info("An attempt was made to get a non-existent resource called " + id);
                    throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
                }
            }

            TransferExtensions xferRequest = new TransferExtensions(request);
            TransferExtensions xferResponse = new TransferExtensions(response);

            xferResponse.setFragmentGetResponse(xferRequest.getFragmentHeader(),
                createEnvelope(virtualAppliance));

            logger.info("GET: " + id);
        }
        catch (SOAPException e)
        {
            throw new InternalErrorFault(e);
        }
        catch (JAXBException e)
        {
            throw new InternalErrorFault(e);
        }
        catch (VirtualMachineException e)
        {
            logger.info("An attempt was made to get a resource that did not exist called " + id);
            throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
        }
    }

    /**
     * Put operation
     * 
     * @param request the request
     * @param response the generated response
     */
    public static void put(Management request, Management response)
    {
        // Use name selector to find the right virtualSystem
        String id = getIdSelector(request);

        logger.info("PUT:::::: ");

        try
        {
            VirtualAppliance virtualAppliance =
                VirtualApplianceModel.getModel().getVirtualAppliance(id);
            AbsVirtualMachine virtualMachine = null;
            List<AbsVirtualMachine> vappMachines = new ArrayList<AbsVirtualMachine>();
            if (virtualAppliance == null)
            {
                try
                {
                    virtualMachine = VirtualSystemModel.getModel().getMachine(UUID.fromString(id));
                    if (virtualMachine == null)
                    {
                        logger
                            .info("An attempt was made to get a resource that did not exist called "
                                + id);
                        throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
                    }
                }
                catch (Exception e)
                {
                    logger.info("An attempt was made to get a resource that did not exist called "
                        + id);
                    throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
                }
            }
            // Get the resource passed in the body
            Object obj = getResource(request);

            if (obj instanceof JAXBElement)
            {
                JAXBElement elem = (JAXBElement) obj;
                JAXBElement<EnvelopeType> enElement = (JAXBElement<EnvelopeType>) elem;

                // Store the configuration in the database;
                if (virtualAppliance != null)
                {
                    storeVirtualAppConfigurationInDB(virtualAppliance.getVirtualApplianceId(),
                        enElement);
                }

                EnvelopeType envelope = enElement.getValue();
                JAXBElement<EntityType> content = (JAXBElement<EntityType>) envelope.getContent();
                EntityType entityInstance = content.getValue();
                List<VirtualDisk> extendedDisk = new ArrayList<VirtualDisk>();
                long memoryRam = 256 * 1024 * 1024;
                int cpuNumber = 1;
                if (entityInstance instanceof VirtualSystemType)
                {
                    VirtualMachineConfiguration vmConfig = virtualMachine.getConfiguration();
                    VirtualMachineConfiguration newConfig =
                        new VirtualMachineConfiguration(vmConfig.getMachineId(),
                            vmConfig.getMachineName(),
                            vmConfig.getVirtualDiskBase(),
                            vmConfig.getRdPort(),
                            vmConfig.getRamAllocationUnits(),
                            vmConfig.getCpuNumber());
                    boolean reconfig = false;
                    // Getting state property
                    Iterator<JAXBElement< ? extends SectionType>> it =
                        (Iterator<JAXBElement< ? extends SectionType>>) entityInstance.getSection()
                            .iterator();
                    while (it.hasNext())
                    {
                        JAXBElement< ? extends SectionType> section =
                            (JAXBElement< ? extends SectionType>) it.next();
                        SectionType sectionType = section.getValue();
                        if (sectionType instanceof AnnotationSectionType)
                        {
                            Map<QName, String> attributes = sectionType.getOtherAttributes();
                            String machineStateValue =
                                attributes.get(MachineState.machineStateQname);
                            logger.info("Detected the new machine state: " + machineStateValue);
                            virtualMachine.applyState(MachineState.fromValue(machineStateValue));

                            // Update the state in the database
                            changeVirtualAppMachineStateInDB(newConfig.getMachineId().toString(),
                                machineStateValue);
                        }
                        else if (sectionType instanceof DiskSectionType)
                        {
                            logger.info("Checking Disk Section");
                            DiskSectionType diskType = (DiskSectionType) sectionType;
                            List<VirtualDiskDescType> diskList = diskType.getDisk();
                            reconfig = true;
                            for (VirtualDiskDescType d : diskList)
                            {
                                VirtualDisk extendedVirtualDisk = new VirtualDisk();
                                extendedVirtualDisk.setId(d.getDiskId());
                                extendedVirtualDisk.setCapacity(Long.parseLong(d.getCapacity()));
                                extendedDisk.add(extendedVirtualDisk);
                            }
                            newConfig.getExtendedVirtualDiskList().addAll(extendedDisk);
                        }
                        else if (sectionType instanceof VirtualHardwareSectionType)
                        {
                            logger.info("Checking VirtualHardwareSectionType object");
                            // Control more than one VirtualHardwareSectionType
                            // instances
                            VirtualHardwareSectionType virtualHardware =
                                (VirtualHardwareSectionType) sectionType;
                            List<RASDType> items = virtualHardware.getItem();

                            for (RASDType item : items)
                            {
                                int resourceType =
                                    Integer.parseInt(item.getResourceType().getValue());
                                switch (resourceType)
                                {
                                    case ResourceType.ResourceType_Memory:
                                        memoryRam =
                                            item.getVirtualQuantity().getValue().longValue() * 1024 * 1024;
                                        newConfig.setRamAllocationUnits(memoryRam);
                                        reconfig = true;
                                        break;
                                    case ResourceType.ResourceType_Processor:
                                        cpuNumber = item.getVirtualQuantity().getValue().intValue();
                                        newConfig.setCpuNumber(cpuNumber);
                                        reconfig = true;
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    if (reconfig)
                    {
                        virtualMachine.reconfigVM(newConfig);
                    }

                }
                else if (entityInstance instanceof VirtualSystemCollectionType)
                {
                    VirtualSystemCollectionType virtualSystemCollectionType =
                        (VirtualSystemCollectionType) entityInstance;
                    Iterator<JAXBElement< ? extends EntityType>> it =
                        virtualSystemCollectionType.getContent().iterator();
                    // Changing the state of the VirtualSystems contained in a
                    // VirtualSystemCollection
                    while (it.hasNext())
                    {
                        // Getting state property
                        EntityType subVirtualSystem = it.next().getValue();
                        String virtualSystemId = subVirtualSystem.getId();
                        logger.info("Checking the virtual Machine: " + virtualSystemId);
                        // Check if the machine is created
                        AbsVirtualMachine virtualMachineNew =
                            VirtualSystemModel.getModel().getMachine(
                                UUID.fromString(virtualSystemId));
                        // If the machine does not exist, includes all the new machines
                        if (virtualMachineNew == null)
                        {
                            logger.info("Detected a new virtual machine: {} to virtual appliance ",
                                virtualSystemId);
                            updateVirtualAppliance(virtualAppliance, request);
                            // Get the updated machine
                            virtualMachineNew =
                                VirtualSystemModel.getModel().getMachine(
                                    UUID.fromString(virtualSystemId));
                        }
                        vappMachines.add(virtualMachineNew);
                        Iterator<JAXBElement< ? extends SectionType>> itSection =
                            (Iterator<JAXBElement< ? extends SectionType>>) subVirtualSystem
                                .getSection().iterator();
                        VirtualMachineConfiguration vmConfig = virtualMachineNew.getConfiguration();
                        VirtualMachineConfiguration newConfig =
                            new VirtualMachineConfiguration(vmConfig.getMachineId(), vmConfig
                                .getMachineName(), vmConfig.getVirtualDiskBase(), vmConfig
                                .getRdPort(), vmConfig.getRamAllocationUnits(), vmConfig
                                .getCpuNumber());
                        boolean reconfig = false;
                        while (itSection.hasNext())
                        {
                            JAXBElement< ? extends SectionType> section =
                                (JAXBElement< ? extends SectionType>) itSection.next();
                            SectionType sectionType = section.getValue();
                            if (sectionType instanceof AnnotationSectionType)
                            {
                                Map<QName, String> attributes = sectionType.getOtherAttributes();
                                String machineStateValue =
                                    attributes.get(MachineState.machineStateQname);
                                logger.info("Detected the new machine state: " + machineStateValue);
                                VirtualSystemModel.getModel().getMachine(
                                    UUID.fromString(virtualSystemId)).applyState(
                                    MachineState.fromValue(machineStateValue));

                                // Update the state in the database
                                changeVirtualAppMachineStateInDB(virtualSystemId, machineStateValue);
                            }
                            // TODO Reconfigure a virtual application when the interface allows it
                            /*
                             * else if (sectionType instanceof DiskSectionType) { reconfig = true;
                             * logger.info("Checking Disk Section"); DiskSectionType diskType =
                             * (DiskSectionType) sectionType; List<VirtualDiskDescType> diskList =
                             * diskType.getDisk(); for (VirtualDiskDescType d : diskList) {
                             * VirtualDisk extendedVirtualDisk = new VirtualDisk();
                             * extendedVirtualDisk.setId(d.getDiskId());
                             * extendedVirtualDisk.setCapacity(Long.parseLong(d.getCapacity()));
                             * extendedDisk.add(extendedVirtualDisk); }
                             * newConfig.getExtendedVirtualDiskList().addAll(extendedDisk); } else
                             * if (sectionType instanceof VirtualHardwareSectionType) {
                             * logger.info("Checking VirtualHardwareSectionType object"); // Control
                             * more than one VirtualHardwareSectionType // instances
                             * VirtualHardwareSectionType virtualHardware =
                             * (VirtualHardwareSectionType) sectionType; List<RASDType> items =
                             * virtualHardware.getItem(); for (RASDType item : items) { int
                             * resourceType = Integer.parseInt(item.getResourceType().getValue());
                             * switch (resourceType) { case ResourceType.ResourceType_Memory:
                             * memoryRam = item.getVirtualQuantity().getValue().longValue() * 1024 *
                             * 1024; newConfig.setRamAllocationUnits(memoryRam); reconfig = true;
                             * break; case ResourceType.ResourceType_Processor: cpuNumber =
                             * item.getVirtualQuantity().getValue().intValue();
                             * newConfig.setCpuNumber(cpuNumber); reconfig = true; break; default:
                             * break; } } }
                             */
                        }
                        /*
                         * if (reconfig) { logger.info("Reconfiguring Virtual machine parameters");
                         * virtualMachineNew.reconfigVM(newConfig); }
                         */
                    }
                    // Updating the machines list instanced in the virtual appliance
                    logger.info("Updating the machines list");
                    virtualAppliance.updateMachines(vappMachines);

                    // Now update the state of the virtual appliance
                    changeVirtualAppStateInDB(virtualAppliance.getVirtualApplianceId(), "T");
                }

            }
            else
            {
                throw new InternalErrorFault("Wrong resource type (instead of JAXBElement) :"
                    + obj.getClass().getCanonicalName() + "\n");
            }

        }
        catch (VirtualMachineException e)
        {
            logger.info("An error was occurred in a virtual machine operation " + e.getMessage());
            throw new InternalErrorFault(e);
        }
        catch (SOAPException e)
        {
            logger.info("A SOAP Exception is thrown" + e.getMessage());
            throw new InternalErrorFault(e);
        }
        catch (JAXBException e)
        {
            logger.info("A JAXB Exception is thrown" + e.getMessage());
            throw new InternalErrorFault(e);
        }
        catch (MalformedURLException e)
        {
            logger.info("A MalformedURLException is thrown \n Caused by:" + e.getMessage());
            throw new InternalErrorFault(e);

        }
        catch (Exception e)
        {
            logger.info("An error was occurred: {}", e.getMessage());
            throw new InternalErrorFault(e);
        }

    }

    /**
     * Private helper to update a virtual appliance with the new virtual machines from the request
     * 
     * @param virtualAppliance the virtual appliance to update
     * @param request the request
     * @throws SOAPException
     * @throws JAXBException
     * @throws MalformedURLException
     * @throws VirtualMachineException
     */
    private static void updateVirtualAppliance(VirtualAppliance virtualAppliance, Management request)
        throws SOAPException, JAXBException, MalformedURLException, VirtualMachineException
    {
        TransferExtensions xferRequest = new TransferExtensions(request);
        Object element = xferRequest.getResource(QNAME);

        // Map with all the reference Files
        Map<String, String> referenceFiles = new HashMap<String, String>();
        // Map of file references and locationDisk
        Map<String, VirtualDisk> virtualDiskMap = new HashMap<String, VirtualDisk>();
        if (element != null)
        {
            logger.info("Checking Envelope object");
            JAXBElement<EnvelopeType> vhElement = getResource(request);
            EnvelopeType envelope = vhElement.getValue();
            // Indexing reference files
            ReferencesType references = envelope.getReferences();
            List<FileType> files = references.getFile();
            for (FileType f : files)
            {
                logger.info("Registering the file id: " + f.getId() + " href:" + f.getHref());
                referenceFiles.put(f.getId(), f.getHref());
            }
            // Indexing virtual Disks
            List<JAXBElement< ? extends SectionType>> sectionList = envelope.getSection();
            for (JAXBElement< ? extends SectionType> sType : sectionList)
            {
                SectionType sectionType = sType.getValue();
                if (sectionType instanceof DiskSectionType)
                {
                    logger.info("Checking Disk Section");
                    DiskSectionType diskType = (DiskSectionType) sectionType;
                    List<VirtualDiskDescType> diskList = diskType.getDisk();
                    for (VirtualDiskDescType d : diskList)
                    {
                        if (referenceFiles.containsKey(d.getFileRef()))
                        {
                            String path = referenceFiles.get(d.getFileRef());
                            logger.info("Registering the virtual disk location:" + path);
                            VirtualDisk virtualDisk = new VirtualDisk();
                            virtualDisk.setId(d.getDiskId());
                            virtualDisk.setLocation(path);
                            virtualDisk.setCapacity(Long.parseLong(d.getCapacity()));
                            virtualDiskMap.put(d.getDiskId(), virtualDisk);
                        }
                        else
                        {
                            // TODO Propagate error
                            logger
                                .info("The Disk fileRef should be included in the References file");
                        }
                    }
                }
            }
            // Checking content element
            JAXBElement<EntityType> content = (JAXBElement<EntityType>) envelope.getContent();
            EntityType entityInstance = content.getValue();
            if (entityInstance instanceof VirtualSystemType)
            {
                logger.info("Checking a Virtual System");
                VirtualSystemType virtualSystemInstance = (VirtualSystemType) entityInstance;
                String virtualApplianceId = virtualSystemInstance.getId();
                Iterator<JAXBElement< ? extends SectionType>> it =
                    (Iterator<JAXBElement< ? extends SectionType>>) entityInstance.getSection()
                        .iterator();
                String rdPort = null;
                while (it.hasNext())
                {
                    logger.info("Analizying the annotation section");
                    JAXBElement< ? extends SectionType> section =
                        (JAXBElement< ? extends SectionType>) it.next();
                    SectionType sectionType = section.getValue();
                    if (sectionType instanceof AnnotationSectionType)
                    {
                        Map<QName, String> attributes = sectionType.getOtherAttributes();
                        rdPort = attributes.get(VirtualMachineConfiguration.remoteDesktopQname);
                    }
                }
                addMachinesToVirtualAppliance(virtualAppliance, virtualSystemInstance,
                    virtualDiskMap, rdPort);
            }
            else if (entityInstance instanceof VirtualSystemCollectionType)
            {
                logger.info("Checking a Virtual System collection");
                VirtualSystemCollectionType virtualSystemCollection =
                    (VirtualSystemCollectionType) entityInstance;

                List<JAXBElement< ? extends EntityType>> contentCollectionList =
                    virtualSystemCollection.getContent();
                for (JAXBElement< ? extends EntityType> contentCollection : contentCollectionList)
                {
                    // TODO The OVF standard points out that a
                    // VirtualSystemCollection may include a
                    // VirtualSystemCollection but we consider it just
                    // includes a list of VirtualSystems
                    EntityType contentValue = contentCollection.getValue();
                    if (contentValue instanceof VirtualSystemType)
                    {
                        VirtualSystemType virtualSystemInstance = (VirtualSystemType) contentValue;
                        Iterator<JAXBElement< ? extends SectionType>> it =
                            (Iterator<JAXBElement< ? extends SectionType>>) contentValue
                                .getSection().iterator();
                        String rdPort = null;
                        while (it.hasNext())
                        {
                            logger.info("Analizying the annotation section");
                            JAXBElement< ? extends SectionType> section =
                                (JAXBElement< ? extends SectionType>) it.next();
                            SectionType sectionType = section.getValue();
                            if (sectionType instanceof AnnotationSectionType)
                            {
                                Map<QName, String> attributes = sectionType.getOtherAttributes();
                                rdPort =
                                    attributes.get(VirtualMachineConfiguration.remoteDesktopQname);
                            }
                        }
                        addMachinesToVirtualAppliance(virtualAppliance, virtualSystemInstance,
                            virtualDiskMap, rdPort);
                    }
                }

            }

        }
        else
        {
            // Create and store a reference to this object in our mini-model
            // This case is not possible
            logger
                .warn("Request resource element is null (The body of your request is empty but it is optional)");

        }

    }

    /**
     * The delete operation
     * 
     * @param request the request
     * @param response the response
     */
    public static void delete(Management request, Management response)
    {
        // Use name selector to find the right virtualSystem
        String id = getIdSelector(request);

        try
        {
            VirtualAppliance virtualAppliance =
                VirtualApplianceModel.getModel().getVirtualAppliance(id);

            if (virtualAppliance == null)
            {
                logger
                    .info("An attempt was made to get a resource that did not exist called " + id);
                throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
            }
            logger.info("DELETE :" + id);

            try
            {
                String virtualApplianceID = virtualAppliance.getVirtualApplianceId();
                VirtualApplianceModel.getModel().deleteVirtualAppliance(id);
                TransferExtensions xferResponse = new TransferExtensions(response);

                // Remove information for this virtual id in the database
                deleteVirtualAppInDB(virtualApplianceID);

                // Remove entries for the states of the machines that belong to this virtual
                // appliance
                deleteVirtualAppMachineStatesInDB(virtualAppliance);

                xferResponse.setDeleteResponse();
            }
            catch (SOAPException e)
            {
                throw new InternalErrorFault(e);
            }
        }
        catch (InterruptedException e)
        {
            logger
                .info("A problem occurred while the system was waiting to power down the machine "
                    + id);
            throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
        }
        catch (VirtualMachineException e1)
        {
            logger.info("An attempt was made to get a resource that did not exist called: " + id);
            throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
        }
        catch (Exception e)
        {
            logger.info("An error occurred: {}", e.getMessage());
            throw new InternalErrorFault(e);
        }
    }

    /**
     * The create operation
     * 
     * @param request the request
     * @param response the response
     */
    public static void create(Management request, Management response)
    {
        try
        {
            TransferExtensions xferResponse = new TransferExtensions(response);

            // Selectors
            HashMap<String, String> selectors = new HashMap<String, String>();
            VirtualAppliance virtualAppliance = null;

            // Creating the virtual appliance
            virtualAppliance = createVirtualAppliance(request);

            logger.info("Building the selector ID: {}", virtualAppliance.getVirtualApplianceId());
            selectors.put("id", virtualAppliance.getVirtualApplianceId());
            // selectors.put("type",
            // virtualSystem.getConfiguration().getHyper().getHypervisorType());
            logger.info("Building the end point reference");
            EndpointReferenceType epr =
                xferResponse.createEndpointReference(request.getTo(), request.getResourceURI(),
                    selectors);
            logger.info("Building the response");
            xferResponse.setCreateResponse(epr);
        }
        catch (SOAPException e)
        {
            logger.info("A SOAP Exception is thrown" + e.getMessage());
            throw new InternalErrorFault(e);
        }
        catch (JAXBException e)
        {
            logger.info("A JAXB Exception is thrown" + e.getMessage());
            throw new InternalErrorFault(e);
        }
        catch (MalformedURLException e)
        {
            logger.info("A MalformedURLException is thrown \n Caused by:" + e.getMessage());
            throw new InternalErrorFault(e);

        }
        catch (VirtualMachineException e)
        {
            logger.info("A VirtualMachineException was thrown \n Caused by:" + e.getMessage(), e);
            throw new InternalErrorFault(e);
        }
    }

    /**
     * Private helper to create a virtual appliance from a request
     * 
     * @param request the request
     * @return the virtual appliance
     * @throws SOAPException
     * @throws JAXBException
     * @throws MalformedURLException
     * @throws VirtualMachineException
     */
    private static VirtualAppliance createVirtualAppliance(Management request)
        throws SOAPException, JAXBException, MalformedURLException, VirtualMachineException
    {
        TransferExtensions xferRequest = new TransferExtensions(request);
        Object element = xferRequest.getResource(QNAME);

        VirtualAppliance virtualAppliance = null;
        if (element != null)
        {
            JAXBElement<EnvelopeType> vhElement = getResource(request);
            virtualAppliance = createVirtualAppliance(vhElement);
        }
        else
        {
            // Create and store a reference to this object in our mini-model
            // This case is not possible
            logger
                .warn("Request resource element is null (The body of your request is empty but it is optional)");
        }

        return virtualAppliance;
    }

    /**
     * NOTE: In the future certain measures would have to be taken if the Hypervisor crashes and all
     * information is lost, in which case the Virtual Machines will have to be created all over
     * again. For the moment, the boolean writeToDB is used as analogous to deploying the virtual
     * machine i.e when writeToDB is false (when a retrieval from the BBDD is being done), a virtual
     * machine doesn't need to be deployed either
     * 
     * @param vhElement
     * @param args (optional) a boolean indicating whether or not the structure for this
     *            VirtualAppliance to be created should be stored in the database or not.
     * @return a reference to a VirtualAppliance object representing the newly created
     *         virtualAppliance
     * @throws java.net.MalformedURLException
     * @throws com.abiquo.abicloud.exception.VirtualMachineException
     */
    private static VirtualAppliance createVirtualAppliance(JAXBElement<EnvelopeType> vhElement,
        boolean... args) throws MalformedURLException, VirtualMachineException
    {

        VirtualAppliance virtualAppliance = null;

        boolean writeToDB = (args.length > 0) ? args[0] : true;

        // Write the XML file to the database if the writeToDB is true, this indicates that
        // the virtual machine is being created for the first time and hence its configuration
        // should be stored in the
        // database in case of a failure during its creation
        if (writeToDB)
        {

            // Get the virtual application id
            EntityType entityType = vhElement.getValue().getContent().getValue();
            String virtualAppID = ((VirtualSystemCollectionType) entityType).getId();
            storeVirtualAppConfigurationInDB(virtualAppID, vhElement);
        }

        // Map with all the reference Files
        Map<String, String> referenceFiles = new HashMap<String, String>();
        // Map of file references and locationDisk
        Map<String, VirtualDisk> virtualDiskMap = new HashMap<String, VirtualDisk>();

        logger.info("Checking Envelope object");
        EnvelopeType envelope = vhElement.getValue();
        // Indexing reference files
        ReferencesType references = envelope.getReferences();
        List<FileType> files = references.getFile();
        for (FileType f : files)
        {
            logger.info("Registering the file id: " + f.getId() + " href:" + f.getHref());
            referenceFiles.put(f.getId(), f.getHref());
        }
        // Indexing virtual Disks
        List<JAXBElement< ? extends SectionType>> sectionList = envelope.getSection();
        for (JAXBElement< ? extends SectionType> sType : sectionList)
        {
            SectionType sectionType = sType.getValue();
            if (sectionType instanceof DiskSectionType)
            {
                logger.info("Checking Disk Section");
                DiskSectionType diskType = (DiskSectionType) sectionType;
                List<VirtualDiskDescType> diskList = diskType.getDisk();
                for (VirtualDiskDescType d : diskList)
                {
                    if (referenceFiles.containsKey(d.getFileRef()))
                    {
                        String path = referenceFiles.get(d.getFileRef());
                        VirtualDisk virtualDisk = new VirtualDisk();
                        virtualDisk.setId(d.getDiskId());
                        virtualDisk.setLocation(path);
                        virtualDisk.setCapacity(Long.parseLong(d.getCapacity()));
                        logger.info("Registering the virtual disk: {}, location: {}",
                            d.getDiskId(), path);
                        virtualDiskMap.put(d.getDiskId(), virtualDisk);
                    }
                    else
                    {
                        // TODO Propagate error
                        logger.error("The Disk fileRef should be included in the References file");
                    }
                }
            }
        }

        // Checking content element
        JAXBElement<EntityType> content = (JAXBElement<EntityType>) envelope.getContent();
        EntityType entityInstance = content.getValue();
        if (entityInstance instanceof VirtualSystemType)
        {
            logger.info("Checking a Virtual System");
            VirtualSystemType virtualSystemInstance = (VirtualSystemType) entityInstance;
            String virtualApplianceId = virtualSystemInstance.getId();
            Iterator<JAXBElement< ? extends SectionType>> it =
                (Iterator<JAXBElement< ? extends SectionType>>) entityInstance.getSection()
                    .iterator();
            String rdPort = null;
            while (it.hasNext())
            {
                logger.info("Analizying the annotation section");
                JAXBElement< ? extends SectionType> section =
                    (JAXBElement< ? extends SectionType>) it.next();
                SectionType sectionType = section.getValue();
                if (sectionType instanceof AnnotationSectionType)
                {
                    Map<QName, String> attributes = sectionType.getOtherAttributes();
                    rdPort = attributes.get(VirtualMachineConfiguration.remoteDesktopQname);
                }
            }
            virtualAppliance =
                VirtualApplianceModel.getModel().createVirtualAppliance(virtualApplianceId);
            addMachinesToVirtualAppliance(virtualAppliance, virtualSystemInstance, virtualDiskMap,
                rdPort, writeToDB);
        }
        else if (entityInstance instanceof VirtualSystemCollectionType)
        {
            logger.info("Checking a Virtual System collection");
            VirtualSystemCollectionType virtualSystemCollection =
                (VirtualSystemCollectionType) entityInstance;
            String virtualApplianceId = virtualSystemCollection.getId();
            virtualAppliance =
                VirtualApplianceModel.getModel().createVirtualAppliance(virtualApplianceId);
            List<JAXBElement< ? extends EntityType>> contentCollectionList =
                virtualSystemCollection.getContent();
            for (JAXBElement< ? extends EntityType> contentCollection : contentCollectionList)
            {
                // TODO The OVF standard points out that a
                // VirtualSystemCollection may include a
                // VirtualSystemCollection but we consider it just
                // includes a list of VirtualSystems
                EntityType contentValue = contentCollection.getValue();
                if (contentValue instanceof VirtualSystemType)
                {
                    VirtualSystemType virtualSystemInstance = (VirtualSystemType) contentValue;
                    Iterator<JAXBElement< ? extends SectionType>> it =
                        (Iterator<JAXBElement< ? extends SectionType>>) contentValue.getSection()
                            .iterator();
                    String rdPort = null;
                    while (it.hasNext())
                    {
                        logger.info("Analizying the annotation section");
                        JAXBElement< ? extends SectionType> section =
                            (JAXBElement< ? extends SectionType>) it.next();
                        SectionType sectionType = section.getValue();
                        if (sectionType instanceof AnnotationSectionType)
                        {
                            Map<QName, String> attributes = sectionType.getOtherAttributes();
                            rdPort = attributes.get(VirtualMachineConfiguration.remoteDesktopQname);
                        }
                    }

                    addMachinesToVirtualAppliance(virtualAppliance, virtualSystemInstance,
                        virtualDiskMap, rdPort, writeToDB);
                }
            }

        }

        // Update the state of the database if writeDB is true (which indicates a newly created
        // virtual appliance)
        if (writeToDB)
            changeVirtualAppStateInDB(virtualAppliance.getVirtualApplianceId(), "T");

        return virtualAppliance;
    }

    /**
     * Private helper to add machines to the virtual appliance
     * 
     * @param virtualAppliance the virtual appliance where the machines will be added
     * @param entityInstance the virtualSystem instance
     * @param virtualDiskMap the map with the virtual disks contained in the OVF packaged
     * @param rdPort the remote desktop port
     * @throws MalformedURLException
     * @throws VirtualMachineException
     */
    private static void addMachinesToVirtualAppliance(VirtualAppliance virtualAppliance,
        VirtualSystemType entityInstance, Map<String, VirtualDisk> virtualDiskMap, String rdPort,
        boolean... args) throws MalformedURLException, VirtualMachineException
    {
        logger.info("Creating a virtual machine from a Virtual System");
        VirtualSystemType virtualSystemInstance = (VirtualSystemType) entityInstance;
        String virtualSystemId = null;
        String virtualSystemTypeString = null;
        String virtualSystemAddress = null;
        VirtualDisk virtualDisk = null;
        // TODO the default value should be 0, but to avoid errors 256MB is assigned
        long memoryRam = 256 * 1024 * 1024;
        int cpuNumber = 1;
        Iterator<JAXBElement< ? extends SectionType>> it =
            (Iterator<JAXBElement< ? extends SectionType>>) virtualSystemInstance.getSection()
                .iterator();
        String virtualApplianceId = virtualSystemInstance.getId();
        List<VirtualDisk> extendedDisk = new ArrayList<VirtualDisk>();
        while (it.hasNext())
        {
            JAXBElement< ? extends SectionType> section =
                (JAXBElement< ? extends SectionType>) it.next();
            SectionType sectionType = section.getValue();
            // TODO The DiskSectionType in the VirtualSystem is not the standarized way to get the
            // hard disk information. Use the resource
            // allocation instead
            if (sectionType instanceof DiskSectionType)
            {
                logger.info("Checking DiskSectionType object");

                DiskSectionType disk = (DiskSectionType) sectionType;
                List<VirtualDiskDescType> diskDesc = disk.getDisk();
                for (VirtualDiskDescType diskUnit : diskDesc)
                {
                    if (virtualDiskMap.containsKey(diskUnit.getDiskId()))
                    {
                        virtualDisk = virtualDiskMap.get(diskUnit.getDiskId());
                        logger.info("Found the disk: " + virtualDisk.getLocation());
                        break;
                    }
                }
            }
            else if (sectionType instanceof VirtualHardwareSectionType)
            {
                logger.info("Checking VirtualHardwareSectionType object");
                // Control more than one VirtualHardwareSectionType
                // instances
                VirtualHardwareSectionType virtualHardware =
                    (VirtualHardwareSectionType) sectionType;
                // Getting the Virtual System type
                VSSDType virtualSystemDataType = virtualHardware.getSystem();
                CimString virtualSystemTypeCimString = virtualSystemDataType.getVirtualSystemType();
                virtualSystemTypeString = virtualSystemTypeCimString.getValue();
                logger.info("The VirtualSystemTypeString is :" + virtualSystemTypeString);
                // Getting the virtual system address
                CimString virtualSystemIdCimString =
                    virtualSystemDataType.getVirtualSystemIdentifier();
                virtualSystemAddress = virtualSystemIdCimString.getValue();
                logger.info("The VirtualSystem Addres is :" + virtualSystemAddress);
                // Getting the virtual system instance ID
                CimString instanceIdCimString = virtualSystemDataType.getInstanceId();
                virtualSystemId = instanceIdCimString.getValue();
                logger.info("The VirtualSystemId is :" + virtualSystemId);
                List<RASDType> items = virtualHardware.getItem();

                for (RASDType item : items)
                {
                    int resourceType = Integer.parseInt(item.getResourceType().getValue());
                    switch (resourceType)
                    {
                        case ResourceType.ResourceType_Memory:
                            memoryRam =
                                item.getVirtualQuantity().getValue().longValue() * 1024 * 1024;
                            break;
                        case ResourceType.ResourceType_Processor:
                            cpuNumber = item.getVirtualQuantity().getValue().intValue();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        logger.info("CREATE : " + virtualSystemId);

        logger.info("The remote desktop port is : " + rdPort);

        boolean deployVirtualMachine = (args.length > 0) ? args[0] : true;
        VirtualMachineConfiguration virtualConfig =
            new VirtualMachineConfiguration(UUID.fromString(virtualSystemId),
                virtualApplianceId,
                virtualDisk,
                Integer.parseInt(rdPort),
                memoryRam,
                cpuNumber,
                deployVirtualMachine);
        virtualConfig.getExtendedVirtualDiskList().addAll(extendedDisk);

        // Adding the virtualMachine if it doesn't exist
        AbsVirtualMachine virtualmachine =
            VirtualSystemModel.getModel().getMachine(UUID.fromString(virtualSystemId));

        if (virtualmachine == null)
        {
            virtualAppliance.addMachine(virtualSystemTypeString, new URL(virtualSystemAddress),
                virtualConfig);
        }

        // if deployVirtualMachine is true then it is a new entry and hence has to be entered in the
        // virtualAppMachineState
        if (deployVirtualMachine)
            createVirtualAppMachineStateInDB(virtualConfig.getMachineId().toString());

        logger.info("FINISHED ADDING VIRTUAL MACHINES ");
    }

    /**
     * Private helper to the get the selector from the request
     * 
     * @param request the request
     * @return returns the id
     * @throws InternalErrorFault
     */
    private static String getIdSelector(Management request) throws InternalErrorFault
    {
        Set<SelectorType> selectors;
        try
        {
            selectors = request.getSelectors();
        }
        catch (JAXBException e)
        {
            throw new InternalErrorFault(e);
        }
        catch (SOAPException e)
        {
            throw new InternalErrorFault(e);
        }
        if (Utilities.getSelectorByName("id", selectors) == null)
        {
            throw new InvalidSelectorsFault(InvalidSelectorsFault.Detail.INSUFFICIENT_SELECTORS);
        }
        return (String) Utilities.getSelectorByName("id", selectors).getContent().get(0);
    }

    /**
     * Private helper to get the Resource from the request
     * 
     * @param request the request to get the resource from
     * @return the resource
     */
    private static JAXBElement<EnvelopeType> getResource(Management request)
    {
        JAXBElement<EnvelopeType> envelopeElement = null;

        try
        {
            // Get JAXB Representation of Soap Body property document
            TransferExtensions transfer = new TransferExtensions(request);
            Object element = transfer.getResource(QNAME);
            if (element == null)
            {
                throw new InvalidRepresentationFault(InvalidRepresentationFault.Detail.MISSING_VALUES);
            }
            if (element instanceof JAXBElement)
            {
                if (((JAXBElement) element).getDeclaredType().equals(EnvelopeType.class))
                {
                    envelopeElement = (JAXBElement<EnvelopeType>) element;
                }
                else
                {
                    // XmlFragment only supported on Get
                    throw new UnsupportedFeatureFault(UnsupportedFeatureFault.Detail.FRAGMENT_LEVEL_ACCESS);
                }
            }
            else
            {
                throw new InvalidRepresentationFault(InvalidRepresentationFault.Detail.INVALID_VALUES);
            }
        }
        catch (SOAPException e)
        {
            throw new InternalErrorFault(e);
        }
        catch (JAXBException e)
        {
            throw new InternalErrorFault(e);
        }
        return envelopeElement;
    }

    /**
     * Private helper to create an envelope OVF from the virtual appliance
     * 
     * @param virtualAppliance the virtual Appliance
     * @return the envelope
     */
    private static EnvelopeType createEnvelopeType(VirtualAppliance virtualAppliance)
    {
        // Create a new, empty JAXB Envelope Type
        EnvelopeType envType = virtualApplianceFactory.createEnvelopeType();

        // If there are more than one machine the OVF envelope contains a
        // VirtualSystemCollection
        if (virtualAppliance.getMachines().size() > 1)
        {
            VirtualSystemCollectionType virtualSystemCollectionType =
                virtualApplianceFactory.createVirtualSystemCollectionType();
            for (AbsVirtualMachine machine : virtualAppliance.getMachines())
            {
                virtualSystemCollectionType.getContent().add(createVirtualSystem(machine));
            }
            JAXBElement<VirtualSystemCollectionType> virtualSystemCollectionTypeElement =
                virtualApplianceFactory.createVirtualSystemCollection(virtualSystemCollectionType);
            envType.setContent(virtualSystemCollectionTypeElement);

        }
        else
        {
            AbsVirtualMachine machine = virtualAppliance.getMachines().iterator().next();
            envType.setContent(createVirtualSystem(machine));
        }

        return envType;

    }

    /**
     * Helper to create a VirtualSystem from a machine description
     * 
     * @param machine the machine
     * @return the virtual system element
     */
    public static JAXBElement<VirtualSystemType> createVirtualSystem(AbsVirtualMachine machine)
    {
        // Create a new VirtualSystem
        VirtualSystemType vSystemType = virtualApplianceFactory.createVirtualSystemType();
        vSystemType.setId(machine.getConfiguration().getMachineId().toString());// .getId());

        // Creating the VirtualHardwareType
        VirtualHardwareSectionType virtualHardwareType =
            virtualApplianceFactory.createVirtualHardwareSectionType();
        VSSDType type = new VSSDType();
        CimString string = new CimString();
        string.setValue(machine.getConfiguration().getHyper().getHypervisorType());// getVirtualSystemTypeString());
        type.setVirtualSystemType(string);
        virtualHardwareType.setSystem(type);
        JAXBElement<VirtualHardwareSectionType> sectionElement =
            virtualApplianceFactory.createVirtualHardwareSection(virtualHardwareType);

        // Creating VirtualDiskType

        DiskSectionType disk = virtualApplianceFactory.createDiskSectionType();
        List<VirtualDiskDescType> diskDesc = disk.getDisk();
        for (VirtualDiskDescType diskUnit : diskDesc)
        {
            VirtualDisk virtualdisk = machine.getConfiguration().getVirtualDiskBase();
            diskUnit.setFileRef(virtualdisk.getLocation());
            diskUnit.setDiskId(virtualdisk.getId());
        }

        // Creating the Annotation Type
        AnnotationSectionType annotationSectionType =
            virtualApplianceFactory.createAnnotationSectionType();
        annotationSectionType.getOtherAttributes().put(MachineState.machineStateQname,
            machine.getState().value());
        JAXBElement<AnnotationSectionType> annotationElement =
            virtualApplianceFactory.createAnnotationSection(annotationSectionType);

        // Adding section elements
        vSystemType.getSection().add(sectionElement);
        vSystemType.getSection().add(annotationElement);

        JAXBElement<VirtualSystemType> virtualSystemElement =
            virtualApplianceFactory.createVirtualSystem(vSystemType);

        return virtualSystemElement;
    }

    /**
     * Helper to create an envelope from a virtual appliance
     * 
     * @param virtualAppliance the virtual appliance
     * @return the OVF envelope
     */
    public static JAXBElement<EnvelopeType> createEnvelope(VirtualAppliance virtualAppliance)
    {
        EnvelopeType envType = createEnvelopeType(virtualAppliance);
        return virtualApplianceFactory.createEnvelope(envType);
    }

    // ============== Database related methods =====================

    /**
     * Helper method used in the creation and modification of virtual appliances
     * 
     * @param virtualApplianceId
     * @param xmlContent
     */
    private static void storeVirtualAppConfigurationInDB(String virtualApplianceId,
        JAXBElement<EnvelopeType> xmlContent)
    {

        try
        {

            // First delete an entry for this virtualApplianceId in the database if it exists
            // This is done taking into account that the configuration to be stored could be one
            // of a already existing virtual appliance
            deleteVirtualAppInDB(virtualApplianceId);

            // Now serialize the Java Content tree back to XML data
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            xmlBinding.marshal(xmlContent, doc);

            // Now write the XML content to the database
            String xmlContentStr = DB.convertXMLToString(doc);
            String sql = DB.createSQL(DB.CREATE_NEW_APP, virtualApplianceId, xmlContentStr);

            if (db.updateDB(sql))
                logger.info("XML structure for Virtual Appliance has been written to the database");
            else
                logger
                    .error("Problems occurred while trying to write the xml structe of the Virtual Appliance to the database");

            // xmlBinding.mar
        }
        catch (Exception e)
        {
            logger.error("Exception obtained while writing XML to the database", e);
        }
    }

    /**
     * Changes the state of a virtual appliance in the database
     * 
     * @param virtualApplianceID
     * @param newState the value to which the state should be set to <code>"T"</code>(deployed) or <code>"F</code>
     *            (not deployed)
     */
    private static void changeVirtualAppStateInDB(String virtualApplianceID, String newState)
    {

        String sql = DB.createSQL(DB.UPDATE_APP_STATE, newState, virtualApplianceID);

        if (db.updateDB(sql))
            logger.info("Virtual appliance [id='" + virtualApplianceID + "'] state set to '"
                + newState);
        else
            logger.error("Unable to set state to '" + newState + "' for virtual appliance [id='"
                + virtualApplianceID + "']");

    }

    private static void deleteVirtualAppInDB(String virtualApplianceID)
    {

        String sql = DB.createSQL(DB.DEL_APP, virtualApplianceID);

        if (db.updateDB(sql))
            logger.info("Virtual Appliance [id='" + virtualApplianceID
                + "'] deleted successfully '");
        else
            logger
                .error("Unable to delete the Virtual Appliance [id='" + virtualApplianceID + "']");

    }

    private static void createVirtualAppMachineStateInDB(String idVirtualAppMachine)
    {

        // First delete the entry
        deleteVirtualAppMachineStatesInDB("'" + idVirtualAppMachine + "'");

        String sql = DB.createSQL(DB.CREATE_NEW_APP_MACHINE_STATE, idVirtualAppMachine);

        if (db.updateDB(sql))
            logger.info("A new entry for the Virtual machine [id='" + idVirtualAppMachine
                + "'] has been added to the database '");
        else
            logger.error("Unable to add a new entry for the Virtual machine [id='"
                + idVirtualAppMachine + "'] to the database '");

    }

    /*
     * private static void updateVirtualAppXMLInDB(String virtualApplianceID,String xmlDoc){ String
     * sql = DB.createSQL(DB.UPDATE_APP_XML,xmlDoc,virtualApplianceID); if( db.updateDB(sql,true) )
     * logger.info("Virtual Appliance [id='" + virtualApplianceID +
     * "'] xml description updated successfully '"); else
     * logger.error("Unable to update the Virtual Appliance [id='" + virtualApplianceID +
     * "'] xml description"); }
     */

    private static void changeVirtualAppMachineStateInDB(String idVirtualAppMachine,
        String machineState)
    {

        String sql = DB.createSQL(DB.UPDATE_APP_MACHINE_STATE, machineState, idVirtualAppMachine);

        if (db.updateDB(sql))
            logger.info("Virtual machine [id='" + idVirtualAppMachine + "'] state set to '"
                + machineState);
        else
            logger.error("Unable to set state to '" + machineState + "' for virtual machine [id='"
                + idVirtualAppMachine + "']");

    }

    /**
     * Deletes the entries of the states of all the virtual machine that belong to a particular
     * virtual appliance
     * 
     * @param virtualAppliance A reference to the VirtualAppliance object whose entries for its
     *            Virtual Machines' states should be deleted from the database
     */
    private static void deleteVirtualAppMachineStatesInDB(VirtualAppliance virtualAppliance)
    {

        // Now go through all its machines
        Iterator<AbsVirtualMachine> iter = virtualAppliance.getMachines().iterator();
        String machineIDList = "";

        while (iter.hasNext())
            machineIDList += "'" + iter.next().getConfiguration().getMachineName() + "',";

        // Remove the last "," from the string
        machineIDList = machineIDList.replaceFirst(",$", "");

        String sql = DB.createSQL(DB.DEL_APP_MACHINE_STATES, machineIDList);

        deleteVirtualAppMachineStatesInDB(machineIDList);

    }

    // Helper method to other overloaded method
    private static void deleteVirtualAppMachineStatesInDB(String virtualMachineIDList)
    {

        String sql = DB.createSQL(DB.DEL_APP_MACHINE_STATES, virtualMachineIDList);

        if (db.updateDB(sql))
            logger.info("The entries for the following virtual machine ids have been deleted ["
                + virtualMachineIDList + "]");
        else
            logger.error("Unable to delete the entries for the following virtual machine ids ["
                + virtualMachineIDList + "]");

    }

    /**
     * Retrieves the all the virtual appliance configurations and current states stored in the
     * database (if any) before shutdown of the application and creates virtual appliances from
     * them.
     */
    public static void restoreVirtualAppsFromDB()
    {

        logger.info("Checking for Virtual Application definitions in the database ... ");

        ArrayList<HashMap<String, Object>> rows = db.queryDB(DB.createSQL(DB.GET_APPS));

        if (!rows.isEmpty())
        {

            logger
                .info(rows.size()
                    + " Virtual Application definitions found in the database, creating Virtual applications ...");

            // Create a hashmap that will containing a mapping of the machine id to its state
            HashMap<String, String> map = new HashMap<String, String>();
            ArrayList<HashMap<String, Object>> rows2 =
                db.queryDB(DB.createSQL(DB.GET_APP_MACHINE_STATES));

            for (HashMap<String, Object> row : rows2)
                map.put(row.get("idVirtualAppMachine").toString(), row.get("machineState")
                    .toString());

            com.abiquo.abicloud.utils.ToString.log(rows);
            com.abiquo.abicloud.utils.ToString.log(map);

            String virtualApplianceID, xmlDoc, virtualMachineID, virtualMachineStateInDB;

            try
            {

                XmlBinding xmlBinding = new XmlBinding(null, "org.dmtf.schemas.ovf.envelope._1");

                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                docBuilderFactory.setNamespaceAware(true);
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc;
                JAXBElement<EnvelopeType> envelopeElement;

                for (HashMap<String, Object> row : rows)
                {

                    virtualApplianceID = row.get("idVirtualApp").toString();
                    xmlDoc = row.get("xmlDoc").toString();

                    logger.info("Creating Virtual appliance with id: " + virtualApplianceID
                        + " ...");

                    doc = docBuilder.parse(new InputSource(new StringReader(xmlDoc)));

                    envelopeElement = (JAXBElement<EnvelopeType>) xmlBinding.unmarshal(doc);

                    // Create the virtual appliance but don't write it to the database
                    VirtualapplianceresourceHandlerImpl.createVirtualAppliance(envelopeElement,
                        false);

                    VirtualAppliance virtualAppliance =
                        VirtualApplianceModel.getModel().getVirtualAppliance(virtualApplianceID);

                    // Now go through all its machines an update their states
                    Iterator<AbsVirtualMachine> iter = virtualAppliance.getMachines().iterator();
                    AbsVirtualMachine virtualMachine;
                    logger.info("Setting virtual machine states ...");
                    while (iter.hasNext())
                    {

                        virtualMachine = iter.next();
                        virtualMachineID =
                            virtualMachine.getConfiguration().getMachineId().toString();

                        virtualMachineStateInDB = map.get(virtualMachineID);

                        // Update the virtual machine state if the state is not null
                        if (virtualMachineStateInDB != null)
                        {
                            logger.info("Setting state of virtual machine [id=" + virtualMachineID
                                + "] to " + virtualMachineStateInDB);

                            virtualMachine
                                .setState(MachineState.fromValue(virtualMachineStateInDB));

                            // TODO: the state of each machine should be test
                            // If the machine is switched on - then we should send a message to the
                            // machine to see if it is still on
                            //

                            logger.info("State of virtual virtual machine [id=" + virtualMachineID
                                + "] set to: " + virtualMachineStateInDB);
                        }

                    }
                    logger.info("Virtual machine states set");

                    logger.info("Virtual appliance [id: " + virtualApplianceID + "] created.");
                }

                logger.info("Virtual applications successfully created!");

            }
            catch (Exception e)
            {
                logger.error(
                    "Error occurred during the retrieval  of virtual appliances from the database",
                    e);
            }

        }
        else
        {
            logger.info("No Virtual Application definitions in the database");
        }

    }

    // ============== End of database related functions ============

}
