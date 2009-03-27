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

import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;

import com.abiquo.abiserver.commands.VirtualApplianceCommand;
import com.abiquo.abiserver.pojo.infrastructure.HyperVisor;
import com.abiquo.abiserver.pojo.infrastructure.State;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.virtualappliance.Node;
import com.abiquo.abiserver.pojo.virtualappliance.NodeVirtualImage;
import com.abiquo.abiserver.pojo.virtualappliance.VirtualAppliance;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

import org.apache.log4j.Logger;
import org.dmtf.schemas.ovf.envelope._1.AnnotationSectionType;
import org.dmtf.schemas.ovf.envelope._1.DiskSectionType;
import org.dmtf.schemas.ovf.envelope._1.EntityType;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;
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
import org.dmtf.schemas.wbem.wscim._1.common.CimUnsignedLong;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3c.dom.Document;

import com.abiquo.abiserver.AbiConfiguration;
import com.sun.ws.management.Management;
import com.sun.ws.management.client.Resource;
import com.sun.ws.management.client.ResourceFactory;
import com.sun.ws.management.client.exceptions.FaultException;
import com.sun.ws.management.xml.XmlBinding;

import com.abiquo.abiserver.AbiConfiguration;
import com.abiquo.util.ErrorManager;
import com.abiquo.util.resources.ResourceManager;

/**
 * This class connects Virtual Appliance Command with AbiCloud Web Services
 * 
 * @author pnavarro
 */
public class VirtualApplianceWS
{
    private XmlBinding binding;

    private org.dmtf.schemas.ovf.envelope._1.ObjectFactory virtualApplianceFactory =
        new org.dmtf.schemas.ovf.envelope._1.ObjectFactory();

    private final org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory managementFactory =
        new org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory();

    private final AbiConfiguration abiConfig = AbiConfiguration.getAbiConfiguration();

    private static final ResourceManager resourceManager =
        new ResourceManager(VirtualApplianceWS.class);

    private final ErrorManager errorManager =
        ErrorManager.getInstance(AbiCloudConstants.ERROR_PREFIX);

    /** The logger object */
    private final static Logger logger = Logger.getLogger(VirtualApplianceWS.class);

    public VirtualApplianceWS() throws JAXBException
    {
        binding = new XmlBinding(null, "org.dmtf.schemas.ovf.envelope._1");
    }

    /**
     * Performs a "Start" action in the Virtual Machine
     * 
     * @param virtualAppliance
     * @return a BasicResult
     * @throws IOException
     * @throws DatatypeConfigurationException
     * @throws SOAPException
     * @throws JAXBException
     * @throws FaultException
     */
    public BasicResult startVirtualAppliance(VirtualAppliance virtualAppliance)
        throws JAXBException, SOAPException, DatatypeConfigurationException, IOException,
        FaultException
    {
        BasicResult result = new BasicResult();
        Resource resource;
        Document envelope = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement = createVirtualApplication(virtualAppliance);
        binding.marshal(envelopeElement, envelope);
        if (virtualAppliance.getState().getId() == State.NOT_DEPLOYED)
        {
            resource =
                ResourceFactory.create(this.abiConfig.getDestination(),
                    AbiCloudConstants.RESOURCE_URI, this.abiConfig.getTimeout(), envelope,
                    ResourceFactory.LATEST);
        }
        else
        {
            resource = findResource(virtualAppliance);
        }
        if (resource != null)
        {
            // Starting the virtual Appliance
            // Changing the virtualSystems to running
            Document envelopeRunning = Management.newDocument();
            JAXBElement<EnvelopeType> envelopeElementRunning =
                changeStateVirtualMachine(envelopeElement, AbiCloudConstants.POWERUP_ACTION);
            binding.marshal(envelopeElementRunning, envelopeRunning);
            resource.put(envelopeRunning);
            result.setSuccess(true);
        }
        else
        {
            this.errorManager.reportError(VirtualApplianceWS.resourceManager, result,
                "resourceNotFound", virtualAppliance.getName());
        }

        return result;
    }

    /**
     * Performs a "Shutdown" action in the Virtual Machine
     * 
     * @param virtualAppliance
     * @return a BasicResult
     * @throws IOException
     * @throws DatatypeConfigurationException
     * @throws SOAPException
     * @throws JAXBException
     * @throws FaultException
     */
    public BasicResult shutdownVirtualAppliance(VirtualAppliance virtualAppliance)
        throws JAXBException, SOAPException, DatatypeConfigurationException, IOException,
        FaultException
    {
        BasicResult result = null;
        result = new BasicResult();
        Document envelopeDoc = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement = createVirtualApplication(virtualAppliance);
        Resource resource = findResource(virtualAppliance);
        if (resource != null)
        {
            JAXBElement<EnvelopeType> envelopeElementRunning =
                changeStateVirtualMachine(envelopeElement, AbiCloudConstants.POWERDOWN_ACTION);
            binding.marshal(envelopeElementRunning, envelopeDoc);
            resource.put(envelopeDoc);
            result.setSuccess(true);
        }
        else
        {
            this.errorManager.reportError(VirtualApplianceWS.resourceManager, result,
                "resourceNotFound", virtualAppliance.getName());
        }

        return result;
    }

    /**
     * Deletes a VirtualAppliance that exists in the Data Base
     * 
     * @param virtualAppliance
     * @return a BasicResult object, containing success = true if the deletion was successful
     * @throws DatatypeConfigurationException
     * @throws FaultException
     * @throws IOException
     * @throws JAXBException
     * @throws SOAPException
     */
    public BasicResult deleteVirtualAppliance(VirtualAppliance virtualAppliance)
        throws SOAPException, JAXBException, IOException, FaultException,
        DatatypeConfigurationException
    {

        BasicResult result = null;
        result = new BasicResult();
        Resource resource = findResource(virtualAppliance);
        if (resource != null)
        {
            // First shutdown the virtualAppliance
            // result = shutdownVirtualAppliance(virtualAppliance);
            result.setSuccess(true);
        }
        else
        {
            this.errorManager.reportError(VirtualApplianceWS.resourceManager, result,
                "resourceNotFound", virtualAppliance.getName());

            return result;
        }
        resource.delete();
        return result;
    }

    /**
     * Private helper to create a virtual appliance
     * 
     * @param virtualAppliance
     * @return
     * @throws JAXBException
     * @throws SOAPException
     * @throws DatatypeConfigurationException
     * @throws IOException
     */
    private JAXBElement<EnvelopeType> createVirtualApplication(VirtualAppliance virtualAppliance)
        throws JAXBException, SOAPException, DatatypeConfigurationException, IOException
    {
        // Create an OVF envelope
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();
        VirtualSystemCollectionType virtualSystemCollectionType =
            virtualApplianceFactory.createVirtualSystemCollectionType();
        // Using the name as the virtual System Id
        virtualSystemCollectionType.setId(String.valueOf(virtualAppliance.getId()));
        // Creating the references element
        ReferencesType references = virtualApplianceFactory.createReferencesType();
        // Creating the virtual Disk package level element
        DiskSectionType diskSectionTypePackage = virtualApplianceFactory.createDiskSectionType();
        // Getting the virtual Machines
        List<Node> nodesList = virtualAppliance.getNodes();
        for (Node node : nodesList)
        {
            if (node instanceof NodeVirtualImage)
            {
                NodeVirtualImage nodeVirtualImage = (NodeVirtualImage) node;
                VirtualMachine virtualMachine = nodeVirtualImage.getVirtualMachine();
                VirtualImage virtualImage = nodeVirtualImage.getVirtualImage();

                JAXBElement<VirtualSystemType> virtualSystem =
                    createVirtualSystem(virtualMachine, virtualImage);
                virtualSystemCollectionType.getContent().add(virtualSystem);
                // Adding the virtual disks to references
                FileType virtualDiskImageFile = virtualApplianceFactory.createFileType();
                virtualDiskImageFile.setId(virtualImage.getName());
                // Combining the repository path + the virtual machine relative path
                String absolutePath =
                    virtualImage.getRepository().getURL() + virtualImage.getPath();
                virtualDiskImageFile.setHref(absolutePath);
                references.getFile().add(virtualDiskImageFile);
                // Adding the virtual Disks to the package level element
                VirtualDiskDescType virtualDescTypePackage =
                    virtualApplianceFactory.createVirtualDiskDescType();
                virtualDescTypePackage.setDiskId(String.valueOf(virtualImage.getId()));
                virtualDescTypePackage.setFileRef(virtualImage.getName());
                virtualDescTypePackage.setCapacity(String.valueOf(virtualImage.getHdRequired()));
                diskSectionTypePackage.getDisk().add(virtualDescTypePackage);
            }

        }

        // Adding the virtual System collection to the envelope
        JAXBElement<VirtualSystemCollectionType> virtualSystemCollection =
            virtualApplianceFactory.createVirtualSystemCollection(virtualSystemCollectionType);
        envelope.setContent(virtualSystemCollection);
        // Adding the references to the envelope
        envelope.setReferences(references);

        // Setting the virtual Disk package level element to the envelope
        JAXBElement<DiskSectionType> diskSectionPackage =
            virtualApplianceFactory.createDiskSection(diskSectionTypePackage);
        envelope.getSection().add(diskSectionPackage);

        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        return envelopeElement;
    }

    /**
     * Private helper to create a Virtual System
     * 
     * @param virtualMachine the virtual machine to create the virtual system from
     * @param virtualImage ther virtual image to create the virtual system from
     * @return
     */
    private JAXBElement<VirtualSystemType> createVirtualSystem(VirtualMachine virtualMachine,
        VirtualImage virtualImage)
    {
        HyperVisor hypervisor = (HyperVisor) virtualMachine.getAssignedTo();
        String hypervisorAddres = "http://" + hypervisor.getIp() + ":" + hypervisor.getPort() + "/";
        String vsystemType = hypervisor.getName();
        String instanceIdString = virtualMachine.getUUID();
        String virtualDiskfileId = virtualImage.getName();
        String virtualDiskId = String.valueOf(virtualImage.getId());
        String hypervisorAddress = hypervisorAddres;
        int rdPort = virtualMachine.getVdrpPort();
        // The Id of the virtualSystem is used for machine name
        VirtualSystemType virtualSystemType = virtualApplianceFactory.createVirtualSystemType();
        // virtualSystemType.setId(machineName);
        // TODO Using the machine instance UUID as ID
        virtualSystemType.setId(instanceIdString);

        // Creating the VirtualHardware element
        VirtualHardwareSectionType virtualHardwareType =
            virtualApplianceFactory.createVirtualHardwareSectionType();
        VSSDType type = new VSSDType();
        // Setting the hypervisor type
        CimString stringType = new CimString();
        stringType.setValue(vsystemType);
        type.setVirtualSystemType(stringType);
        virtualHardwareType.setSystem(type);
        // Setting the hypervisor address as VirtualSystemIdentifier element
        CimString hyperAddress = new CimString();
        hyperAddress.setValue(hypervisorAddress);
        type.setVirtualSystemIdentifier(hyperAddress);
        // Setting the virtual machine ID
        CimString instanceId = new CimString();
        instanceId.setValue(instanceIdString);
        type.setInstanceId(instanceId);
        List<RASDType> items = virtualHardwareType.getItem();
        RASDType ramRasd = virtualApplianceFactory.createRASDType();
        CimString ramInstanceId = new CimString();
        ramInstanceId.setValue("2");
        ramRasd.setInstanceId(ramInstanceId);
        ResourceType ramResourceType = new ResourceType();
        ramResourceType.setValue("4");
        ramRasd.setResourceType(ramResourceType);
        CimUnsignedLong ramUnits = new CimUnsignedLong();
        ramUnits.setValue(new BigInteger(String.valueOf(virtualImage.getRamRequired())));
        ramRasd.setVirtualQuantity(ramUnits);
        items.add(ramRasd);
        RASDType cpuNumberRasd = virtualApplianceFactory.createRASDType();
        CimString cpuNumberInstanceId = new CimString();
        cpuNumberInstanceId.setValue("1");
        cpuNumberRasd.setInstanceId(cpuNumberInstanceId);
        ResourceType cpuRasd = new ResourceType();
        cpuRasd.setValue("3");
        cpuNumberRasd.setResourceType(cpuRasd);
        CimUnsignedLong cpuUnits = new CimUnsignedLong();
        cpuUnits.setValue(BigInteger.valueOf(virtualImage.getCpuRequired()));
        cpuNumberRasd.setVirtualQuantity(cpuUnits);
        items.add(cpuNumberRasd);

        // Creating the Annotation Type
        AnnotationSectionType annotationSectionType =
            virtualApplianceFactory.createAnnotationSectionType();
        Map<QName, String> otherAttributes = annotationSectionType.getOtherAttributes();
        otherAttributes.put(AbiCloudConstants.remoteDesktopQname, String.valueOf(rdPort));
        logger.debug("The remote desktop port included is: " + String.valueOf(rdPort));
        JAXBElement<AnnotationSectionType> annotationElement =
            virtualApplianceFactory.createAnnotationSection(annotationSectionType);

        // Setting the Virtual Disk
        DiskSectionType diskSectionType = virtualApplianceFactory.createDiskSectionType();
        VirtualDiskDescType virtualDescType = virtualApplianceFactory.createVirtualDiskDescType();
        virtualDescType.setDiskId(virtualDiskId);
        virtualDescType.setFileRef(virtualDiskfileId);
        diskSectionType.getDisk().add(virtualDescType);
        JAXBElement<DiskSectionType> diskSection =
            virtualApplianceFactory.createDiskSection(diskSectionType);
        virtualSystemType.getSection().add(diskSection);

        JAXBElement<VirtualHardwareSectionType> virtualHardwareElement =
            virtualApplianceFactory.createVirtualHardwareSection(virtualHardwareType);

        virtualSystemType.getSection().add(virtualHardwareElement);
        virtualSystemType.getSection().add(annotationElement);

        JAXBElement<VirtualSystemType> virtualSystemTypeElement =
            virtualApplianceFactory.createVirtualSystem(virtualSystemType);

        return virtualSystemTypeElement;

    }

    /**
     * Private helper to change the state
     * 
     * @param enveloe
     * @return
     */
    private JAXBElement<EnvelopeType> changeStateVirtualMachine(
        JAXBElement<EnvelopeType> envelopeElement, String newState)
    {
        EnvelopeType envelope = envelopeElement.getValue();
        JAXBElement<EntityType> content = (JAXBElement<EntityType>) envelope.getContent();
        EntityType entityInstance = content.getValue();
        if (entityInstance instanceof VirtualSystemType)
        {
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
                    attributes.put(AbiCloudConstants.machineStateQname, newState);
                }
            }
        }
        else if (entityInstance instanceof VirtualSystemCollectionType)
        {
            VirtualSystemCollectionType virtualSystemCollectionType =
                (VirtualSystemCollectionType) entityInstance;
            Iterator<JAXBElement< ? extends EntityType>> it =
                virtualSystemCollectionType.getContent().iterator();
            // Changing the state of the VirtualSystems contained in a VirtualSystemCollection
            while (it.hasNext())
            {
                // Getting state property
                EntityType subVirtualSystem = it.next().getValue();
                String virtualSystemId = subVirtualSystem.getId();
                Iterator<JAXBElement< ? extends SectionType>> itSection =
                    (Iterator<JAXBElement< ? extends SectionType>>) subVirtualSystem.getSection()
                        .iterator();
                while (itSection.hasNext())
                {
                    JAXBElement< ? extends SectionType> section =
                        (JAXBElement< ? extends SectionType>) itSection.next();
                    SectionType sectionType = section.getValue();
                    if (sectionType instanceof AnnotationSectionType)
                    {
                        Map<QName, String> attributes = sectionType.getOtherAttributes();
                        attributes.put(AbiCloudConstants.machineStateQname, newState);
                    }
                }
            }
        }
        return envelopeElement;
    }

    /**
     * Private helper to find a resource through the virtualAppliance name
     * 
     * @param virtualAppliance the virtualAppliance to find the resource from
     * @return the resource found
     * @throws SOAPException
     * @throws JAXBException
     * @throws IOException
     * @throws FaultException
     * @throws DatatypeConfigurationException
     */
    private Resource findResource(VirtualAppliance virtualAppliance) throws SOAPException,
        JAXBException, IOException, FaultException, DatatypeConfigurationException
    {
        // Creating a selector passing as the id the machine name
        SelectorSetType selector = createSelectorId(String.valueOf(virtualAppliance.getId()));
        Resource[] resources =
            ResourceFactory.find(this.abiConfig.getDestination(), AbiCloudConstants.RESOURCE_URI,
                this.abiConfig.getTimeout(), selector);
        Resource resource = resources[0];
        return resource;

    }

    /**
     * Private helper to create a selector id with the virtual application name
     * 
     * @param virtualApplianceName
     * @return
     */
    private SelectorSetType createSelectorId(String virtualApplianceName)
    {
        // Creating a selector passing as the id the machine name
        SelectorType nameSelectorType = managementFactory.createSelectorType();
        nameSelectorType.setName("id");
        nameSelectorType.getContent().add(virtualApplianceName);
        SelectorSetType selector = new SelectorSetType();
        selector.getSelector().add(nameSelectorType);
        return selector;
    }

    /**
     * Modifies the information of a VirtualAppliance that already exists in the Data Base
     * 
     * @param virtualAppliance
     * @return A DataResult object, containing a list of nodes modified
     */
    public BasicResult editVirtualAppliance(VirtualAppliance virtualAppliance)
        throws SOAPException, JAXBException, IOException, FaultException,
        DatatypeConfigurationException
    {
        BasicResult result = null;
        result = new BasicResult();
        if (virtualAppliance.getState().getId() == State.RUNNING)
        {
            Document envelope = Management.newDocument();
            JAXBElement<EnvelopeType> envelopeElement = createVirtualApplication(virtualAppliance);
            binding.marshal(envelopeElement, envelope);
            Resource resource = findResource(virtualAppliance);
            if (resource != null)
            {
                // Starting the virtual Appliance
                // Changing the virtualSystems to running
                Document envelopeRunning = Management.newDocument();
                JAXBElement<EnvelopeType> envelopeElementRunning =
                    changeStateVirtualMachine(envelopeElement, AbiCloudConstants.POWERUP_ACTION);
                binding.marshal(envelopeElementRunning, envelopeRunning);
                resource.put(envelopeRunning);
                result.setSuccess(true);
            }
            else
            {
                this.errorManager.reportError(VirtualApplianceWS.resourceManager, result,
                    "resourceNotFound", virtualAppliance.getName());
            }
        }
        else
        {
            result.setSuccess(true);
        }
        return result;
    }

}
