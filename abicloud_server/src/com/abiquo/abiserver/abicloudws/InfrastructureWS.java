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
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;

import com.abiquo.abiserver.pojo.infrastructure.HyperVisor;
import com.abiquo.abiserver.pojo.infrastructure.VirtualMachine;
import com.abiquo.abiserver.pojo.result.BasicResult;
import com.abiquo.abiserver.pojo.virtualimage.VirtualImage;

import org.dmtf.schemas.ovf.envelope._1.AnnotationSectionType;
import org.dmtf.schemas.ovf.envelope._1.DiskSectionType;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;
import org.dmtf.schemas.ovf.envelope._1.RASDType;
import org.dmtf.schemas.ovf.envelope._1.ReferencesType;
import org.dmtf.schemas.ovf.envelope._1.VSSDType;
import org.dmtf.schemas.ovf.envelope._1.VirtualDiskDescType;
import org.dmtf.schemas.ovf.envelope._1.VirtualHardwareSectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemType;
import org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_resourceallocationsettingdata.ResourceType;
import org.dmtf.schemas.wbem.wscim._1.common.CimString;
import org.dmtf.schemas.wbem.wscim._1.common.CimUnsignedLong;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3c.dom.Document;

import com.sun.ws.management.Management;
import com.sun.ws.management.client.Resource;
import com.sun.ws.management.client.ResourceFactory;
import com.sun.ws.management.client.exceptions.FaultException;
import com.sun.ws.management.xml.XmlBinding;

import com.abiquo.abiserver.AbiConfiguration;
import com.abiquo.util.ErrorManager;
import com.abiquo.util.resources.ResourceManager;

/**
 * This class connects Infrastructure Command with AbiCloud Web Services
 * 
 * @author Oliver
 */
public class InfrastructureWS
{

    private org.dmtf.schemas.ovf.envelope._1.ObjectFactory virtualApplianceFactory =
        new org.dmtf.schemas.ovf.envelope._1.ObjectFactory();

    private final org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory managementFactory =
        new org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory();

    private XmlBinding binding;

    private final AbiConfiguration abiConfig = AbiConfiguration.getAbiConfiguration();

    private static final ResourceManager resourceManager =
        new ResourceManager(InfrastructureWS.class);

    private final ErrorManager errorManager =
        ErrorManager.getInstance(AbiCloudConstants.ERROR_PREFIX);

    public InfrastructureWS() throws JAXBException
    {
        binding = new XmlBinding(null, "org.dmtf.schemas.ovf.envelope._1");
    }

    /**
     * Performs the action in Abicloud associated with the attribute "state" in the virtual machine
     * 
     * @param virtualMachine the virtual machine to perform the state change
     * @param actionState the action state to perform
     * @return a basic result
     */
    public BasicResult setVirtualMachineState(VirtualMachine virtualMachine, String actionState)
    {
        BasicResult result = null;
        try
        {
            result = new BasicResult();
            HyperVisor hypervisor = (HyperVisor) virtualMachine.getAssignedTo();
            Document doc =
                changeMachineState(hypervisor.getName(), actionState, virtualMachine.getName());
            Resource resource = findResource(virtualMachine);
            if (resource != null)
            {
                result.setSuccess(true);
            }
            else
            {
                this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                    "resourceNotFound", virtualMachine.getName());
            }
            resource.put(doc);
        }
        catch (Exception e)
        {
            this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                "operationFailed", e);

        }
        return result;
    }

    /**
     * Creates a virtual machine in the target hypervisor
     * 
     * @param virtualMachine the virtual machine to create
     * @return a basic result
     */
    public BasicResult createVirtualMachine(VirtualMachine virtualMachine)
    {
        BasicResult result = null;
        try
        {
            result = new BasicResult();
            HyperVisor hypervisor = (HyperVisor) virtualMachine.getAssignedTo();
            Document envelope = createEnvelopeDocument(virtualMachine);
            Resource resource =
                ResourceFactory.create(this.abiConfig.getDestination(),
                    AbiCloudConstants.RESOURCE_URI, this.abiConfig.getTimeout(), envelope,
                    ResourceFactory.LATEST);
            if (resource != null)
            {
                result.setSuccess(true);
            }
            else
            {
                this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                    "resourceNotFound", virtualMachine.getName());
            }
        }
        catch (Exception e)
        {
            this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                "operationFailed", e);
        }
        return result;

    }

    /**
     * Deletes the virtual machine
     * 
     * @param virtualMachine the virtual machine to delete
     * @return a basic result
     */
    public BasicResult deleteVirtualMachine(VirtualMachine virtualMachine)
    {
        BasicResult result = null;
        try
        {
            result = new BasicResult();
            Resource resource = findResource(virtualMachine);
            if (resource != null)
            {
                result.setSuccess(true);
            }
            else
            {
                this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                    "resourceNotFound", virtualMachine.getName());
            }
            resource.delete();
        }
        catch (Exception e)
        {
            this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                "operationFailed", e);
        }
        return result;
    }

    /**
     * Edits the virtualMachine with a new configuration
     * 
     * @param virtualMachine the virtual machine to edit
     * @return
     */
    public BasicResult editVirtualMachine(VirtualMachine virtualMachine)
    {
        BasicResult result = null;
        try
        {
            result = new BasicResult();
            HyperVisor hypervisor = (HyperVisor) virtualMachine.getAssignedTo();
            /*
             * Document doc = changeMachineState(hypervisor.getName(), actionState,
             * virtualMachine.getName());
             */
            Document doc = updateVirtualMachine(virtualMachine);
            Resource resource = findResource(virtualMachine);
            if (resource != null)
            {
                result.setSuccess(true);
            }
            else
            {
                this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                    "resourceNotFound", virtualMachine.getName());
            }
            resource.put(doc);
        }
        catch (Exception e)
        {
            this.errorManager.reportError(InfrastructureWS.resourceManager, result,
                "operationFailed", e);

        }
        return result;
    }

    /**
     * Updates the virtual machine
     * 
     * @param virtualMachine the virtual machine to get the parameters from
     * @return the Document with the new machine values
     * @throws JAXBException
     */
    private Document updateVirtualMachine(VirtualMachine virtualMachine) throws JAXBException
    {
        // Creates an OVF envelope
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();
        // Fills the envelope from the virtual machine parameters
        constructEnvelopeType(envelope, virtualMachine);
        // Updates the changed parameters
        // Preparing for submision
        Document doc = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        binding.marshal(envelopeElement, doc);
        return doc;
    }

    /**
     * Private helper to create a document with the OVF envelope. This envelope contains the
     * information with the virtualMachine creation
     * 
     * @param virtualMachine the parameters to construct an OVF
     * @return a OVF Document
     * @throws JAXBException
     */
    private Document createEnvelopeDocument(VirtualMachine virtualMachine) throws JAXBException
    {
        // Creates an OVF envelope
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();
        // Fills the envelope from the virtual machine parameters
        constructEnvelopeType(envelope, virtualMachine);
        // Preparing for submision
        Document doc = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        binding.marshal(envelopeElement, doc);
        return doc;
    }

    /**
     * Private helper to construct the basic parameters from the virtual machine
     * 
     * @param envelope
     * @param virtualMachine
     */
    private void constructEnvelopeType(EnvelopeType envelope, VirtualMachine virtualMachine)
    {
        HyperVisor hypervisor = (HyperVisor) virtualMachine.getAssignedTo();
        VirtualImage image = virtualMachine.getVirtualImage();
        String hypervisorAddress =
            "http://" + hypervisor.getIp() + ":" + hypervisor.getPort() + "/";
        String vsystemType = hypervisor.getName();
        String instanceIdString = virtualMachine.getUUID();
        String machineName = virtualMachine.getName();
        String virtualDiskLocationRef = image.getPath();
        String virtualDiskfileId = image.getName();
        String virtualDiskId = String.valueOf(image.getId());

        // The Id of the virtualSystem is used for machine name
        VirtualSystemType virtualSystemType = virtualApplianceFactory.createVirtualSystemType();
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

        // Setting the Virtual Disk
        DiskSectionType diskSectionType = virtualApplianceFactory.createDiskSectionType();
        // Not setting the base virtual disk, since we don't create virtual machines in the
        // InfrastructureWS calls
        // Extended disk
        if ((virtualMachine.getHd()) - image.getHdRequired() != 0)
        {
            VirtualDiskDescType extendedVirtualDescType =
                virtualApplianceFactory.createVirtualDiskDescType();
            extendedVirtualDescType.setDiskId(UUID.randomUUID().toString());
            extendedVirtualDescType.setCapacity(String.valueOf(virtualMachine.getHd()
                - image.getHdRequired()));
            diskSectionType.getDisk().add(extendedVirtualDescType);
            JAXBElement<DiskSectionType> diskSection =
                virtualApplianceFactory.createDiskSection(diskSectionType);
            virtualSystemType.getSection().add(diskSection);
        }

        // Setting the RAM and CPU from machine
        List<RASDType> items = virtualHardwareType.getItem();
        RASDType ramRasd = virtualApplianceFactory.createRASDType();
        CimString ramInstanceId = new CimString();
        ramInstanceId.setValue("2");
        ramRasd.setInstanceId(ramInstanceId);
        ResourceType ramResourceType = new ResourceType();
        ramResourceType.setValue("4");
        ramRasd.setResourceType(ramResourceType);
        CimUnsignedLong ramUnits = new CimUnsignedLong();
        ramUnits.setValue(new BigInteger(String.valueOf(virtualMachine.getRam())));
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
        cpuUnits.setValue(BigInteger.valueOf(virtualMachine.getCpu()));
        cpuNumberRasd.setVirtualQuantity(cpuUnits);
        items.add(cpuNumberRasd);

        JAXBElement<VirtualHardwareSectionType> sectionElement =
            virtualApplianceFactory.createVirtualHardwareSection(virtualHardwareType);

        virtualSystemType.getSection().add(sectionElement);

        JAXBElement<VirtualSystemType> virtualSystemTypeElement =
            virtualApplianceFactory.createVirtualSystem(virtualSystemType);

        envelope.setContent(virtualSystemTypeElement);

        // Setting the virtual Disk package level element
        DiskSectionType diskSectionTypePackage = virtualApplianceFactory.createDiskSectionType();
        VirtualDiskDescType virtualDescTypePackage =
            virtualApplianceFactory.createVirtualDiskDescType();
        virtualDescTypePackage.setDiskId(virtualDiskId);
        virtualDescTypePackage.setFileRef(virtualDiskfileId);
        diskSectionTypePackage.getDisk().add(virtualDescTypePackage);
        JAXBElement<DiskSectionType> diskSectionPackage =
            virtualApplianceFactory.createDiskSection(diskSectionTypePackage);
        envelope.getSection().add(diskSectionPackage);

        // Setting the virtualDisk File reference
        ReferencesType references = virtualApplianceFactory.createReferencesType();
        FileType virtualDiskFile = virtualApplianceFactory.createFileType();
        virtualDiskFile.setId(virtualDiskfileId);
        virtualDiskFile.setHref(virtualDiskLocationRef);
        references.getFile().add(virtualDiskFile);
        envelope.setReferences(references);

    }

    /**
     * Private helper to create a document with the information to perform the machine state change.
     * 
     * @param hypervisorType the hypervisor type
     * @param machineState the machine State
     * @param machineName the machine name
     * @return the document to submit
     * @throws JAXBException
     * @throws SOAPException
     * @throws DatatypeConfigurationException
     * @throws IOException
     */
    private Document changeMachineState(String hypervisorType, String machineState,
        String machineName) throws JAXBException, SOAPException, DatatypeConfigurationException,
        IOException
    {
        // Create a new, empty JAXB Envelope Type
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();

        // Create a new VirtualSystem
        VirtualSystemType vSystemType = virtualApplianceFactory.createVirtualSystemType();

        // Creating the VirtualHardwareType
        VirtualHardwareSectionType virtualHardwareType =
            virtualApplianceFactory.createVirtualHardwareSectionType();
        VSSDType type = new VSSDType();
        CimString string = new CimString();
        string.setValue(hypervisorType);
        type.setVirtualSystemType(string);
        virtualHardwareType.setSystem(type);
        JAXBElement<VirtualHardwareSectionType> virtualHardwareSectionElement =
            virtualApplianceFactory.createVirtualHardwareSection(virtualHardwareType);

        // Creating the Annotation Type
        AnnotationSectionType annotationSectionType =
            virtualApplianceFactory.createAnnotationSectionType();
        annotationSectionType.getOtherAttributes().put(AbiCloudConstants.machineStateQname,
            machineState);
        JAXBElement<AnnotationSectionType> annotationElement =
            virtualApplianceFactory.createAnnotationSection(annotationSectionType);

        // Adding section elements
        vSystemType.getSection().add(virtualHardwareSectionElement);
        vSystemType.getSection().add(annotationElement);

        JAXBElement<VirtualSystemType> virtualSystemElement =
            virtualApplianceFactory.createVirtualSystem(vSystemType);
        envelope.setContent(virtualSystemElement);

        // Submit this document
        Document doc = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        binding.marshal(envelopeElement, doc);
        return doc;
    }

    /**
     * Private helper to create a selector id with the virtual machine name
     * 
     * @param machineName
     * @return
     */
    private SelectorSetType createSelectorId(String machineName)
    {
        // Creating a selector passing as the id the machine name
        SelectorType nameSelectorType = managementFactory.createSelectorType();
        nameSelectorType.setName("id");
        nameSelectorType.getContent().add(machineName);
        SelectorSetType selector = new SelectorSetType();
        selector.getSelector().add(nameSelectorType);
        return selector;
    }

    /**
     * Private helper to find a resource through the virtualMachine name
     * 
     * @param virtualMachine the virtualMachine to find the resource from
     * @return the resource found
     * @throws SOAPException
     * @throws JAXBException
     * @throws IOException
     * @throws FaultException
     * @throws DatatypeConfigurationException
     */
    private Resource findResource(VirtualMachine virtualMachine) throws SOAPException,
        JAXBException, IOException, FaultException, DatatypeConfigurationException
    {
        // Creating a selector passing as the id the machine name
        SelectorSetType selector = createSelectorId(virtualMachine.getUUID());
        Resource[] resources =
            ResourceFactory.find(this.abiConfig.getDestination(), AbiCloudConstants.RESOURCE_URI,
                this.abiConfig.getTimeout(), selector);
        Resource resource = resources[0];
        return resource;

    }
}
