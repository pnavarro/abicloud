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
package com.abiquo.abicloudWS.test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;

import org.dmtf.schemas.ovf.envelope._1.AnnotationSectionType;
import org.dmtf.schemas.ovf.envelope._1.DiskSectionType;
import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.dmtf.schemas.ovf.envelope._1.FileType;
import org.dmtf.schemas.ovf.envelope._1.RASDType;
import org.dmtf.schemas.ovf.envelope._1.ReferencesType;
import org.dmtf.schemas.ovf.envelope._1.VSSDType;
import org.dmtf.schemas.ovf.envelope._1.VirtualDiskDescType;
import org.dmtf.schemas.ovf.envelope._1.VirtualHardwareSectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemCollectionType;
import org.dmtf.schemas.ovf.envelope._1.VirtualSystemType;
import org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_resourceallocationsettingdata.ResourceType;
import org.dmtf.schemas.wbem.wscim._1.common.CimString;
import org.dmtf.schemas.wbem.wscim._1.common.CimUnsignedLong;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3c.dom.Document;

import util.WsManBaseTestSupport;

import com.abiquo.abicloud.model.MachineState;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;
import com.sun.ws.management.Management;
import com.sun.ws.management.client.EnumerableResource;
import com.sun.ws.management.client.EnumerationCtx;
import com.sun.ws.management.client.Resource;
import com.sun.ws.management.client.ResourceFactory;
import com.sun.ws.management.client.exceptions.FaultException;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.xml.XmlBinding;

/**
 * This class tests a virtual appliance deployment and the main operations
 * 
 * @author pnavarro
 */
public class VirtualApplianceTestCase extends WsManBaseTestSupport
{

    /**
     * General parameters
     */
    // Memory ram in MB
    private final static String memoryRam = "128";

    // virtual cpu number
    private final static String numberCpu = "1";

    // remote desktop port
    private final static String remoteDesktopPort = "3389";

    // Disk capacity in bytes
    // private final static String diskCapacity = "8589934592";
    private final static String diskCapacity = "107374080";

    /**
     * Parameters for the virtual machine test 1
     */
    private final static String machineID = "d7a8a3ef-32ee-4bf9-b7e4-143a1eabbc5b"; // UUID.randomUUID().toString();

    private final static String machineName = "d7a8a3ef-32ee-4bf9-b7e4-143a1eabbc5b";

    private final static String machineType = "vBox";

    private final static String virtualDiskfileId = "ubuntuFile";

    private final static String virtualDiskLocation = "c:\\myRepository\\AbiquoMW.vdi";

    /**
     * Parameters for the virtual machine test 2
     */
    private final static String machineID2 = UUID.randomUUID().toString();

    private final static String machineName2 = "Tomcat";

    private final static String machineType2 = "vBox";

    private final static String virtualDiskfileId2 = "tomcaFile";

    private final static String virtualDiskLocation2 = "c:\\myRepository\\AbiquoMW.vdi";

    /**
     * virtual Appliance test name
     */
    private final static String virtualApplianceID = "xubuntuTomcat";

    // private static final String RESOURCE_URI =
    // "urn:resources.abicloud.com/virtualmachine";
    private static final String RESOURCE_URI =
        "http://schemas.dmtf.org/ovf/envelope/1/virtualApplianceService/virtualApplianceResource";

    private static final String TRANSFER_NS = "http://schemas.xmlsoap.org/ws/2004/09/transfer";

    private static final String WSMAN_NS = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd";

    private static final String WSADD_NS = "http://schemas.xmlsoap.org/ws/2004/08/addressing";

    /**
     * virtual Appliance container destination
     */
    private static final String DESTINATION = "http://localhost:8080/abicloud_WS/";

    private static final long TIME_OUT = 6000000;

    // private static final String DESTINATION =
    // "http://192.168.102.63:8080/virtualAppliance/";
    // private static final String DESTINATION =
    // "http://localhost:8080/virtualAppliance/";

    // private static final String HYPERVISOR_ADDRESS =
    // "http://192.168.102.63:18083/";
    /**
     * machine 1 hypervisor WS address
     */
    private static final String HYPERVISOR_ADDRESS = "http://localhost:18083/";

    // private static final String HYPERVISOR_ADDRESS = "http://192.168.1.34:443/";
    /**
     * machine 2 hypervisor WS address
     */
    private static final String HYPERVISOR_ADDRESS_cambridge = "http://192.168.102.61:18083/";

    public static final org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory managementFactory =
        new org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory();

    private org.dmtf.schemas.ovf.envelope._1.ObjectFactory virtualApplianceFactory =
        new org.dmtf.schemas.ovf.envelope._1.ObjectFactory();

    private XmlBinding binding;

    private Logger log = Logger.getLogger(VirtualApplianceTestCase.class.getName());

    private Resource[] vApplianceObjs;

    /**
     * Main test to deploy/pause/resume/poweroff/undeploy the virtualAppliance
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception
    {
        VirtualApplianceTestCase test = new VirtualApplianceTestCase();
        test.deployVirtualApplication();
        test.enumerateResources();
        test.putMachineState(machineType, MachineState.POWER_UP.value(), machineName, machineID);
        Thread.sleep(3000);
        // test.reconfigVirtualMachine();
        /*
         * test.putMachineState("vBox", MachineState.PAUSE.value(), machineName, machineID);
         * test.putMachineState("vBox", MachineState.RESUME.value(), machineName, machineID);
         */
        Thread.sleep(3000);
        test.putMachineState(machineType, MachineState.POWER_OFF.value(), machineName, machineID);
        Thread.sleep(3000);
        test.deleteVirtualSystem(virtualApplianceID);

    }

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public VirtualApplianceTestCase()
    {
        super();
        try
        {
            binding = new XmlBinding(null, "org.dmtf.schemas.ovf.envelope._1");
        }
        catch (JAXBException e)
        {
            fail(e.getMessage());
        }

        // Enable basic authenticaton for tests
        System.getProperties().put("wsman.user", "wsman");
        System.getProperties().put("wsman.password", "secret");
        HttpClient.setAuthenticator(new transport.BasicAuthenticator());
    }

    public void testCreateVirtualSystem() throws JAXBException, SOAPException,
        DatatypeConfigurationException, IOException, XPathExpressionException
    {

        // Submit this document
        Document doc = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement =
            createEnvelopeType(machineType, machineID, machineName, virtualDiskLocation,
                virtualDiskfileId, HYPERVISOR_ADDRESS);
        binding.marshal(envelopeElement, doc);
        Management ret = sendCreateRequest(DESTINATION, RESOURCE_URI, doc);

        // Is there a body?
        assertNotNull(ret.getBody());

        // Is there a valid resource URI?
        String xpathBase =
            "*[namespace-uri()=\"" + TRANSFER_NS + "\" and local-name()=\"ResourceCreated\"]/"
                + "*[namespace-uri()=\"" + WSADD_NS
                + "\" and local-name()=\"ReferenceParameters\"]";

        assertTrue(getXPathValues(xpathBase, ret.getBody()).getLength() > 0);

        String xPathResourceUri =
            xpathBase + "/*[namespace-uri()=\"" + WSMAN_NS + "\" and local-name()=\"ResourceURI\"]";
        assertEquals(
            "http://schemas.dmtf.org/ovf/envelope/1/virtualApplianceService/virtualApplianceResource",
            getXPathText(xPathResourceUri, ret.getBody()));

        String xPathSelector =
            xpathBase + "/*[namespace-uri()=\"" + WSMAN_NS
                + "\" and local-name()=\"SelectorSet\"]/*" + "[namespace-uri()=\"" + WSMAN_NS
                + "\" and local-name()=\"Selector\" and @Name='id']";
        assertEquals(machineName, getXPathText(xPathSelector, ret.getBody()));

    }

    public void testEnumerate() throws SOAPException, JAXBException, IOException, FaultException,
        DatatypeConfigurationException
    {

        HashMap<String, String> selectors = null;

        Resource[] resources = ResourceFactory.find(DESTINATION, RESOURCE_URI, 30000, selectors);
        if (resources == null || resources.length == 0)
        {
            log.info("No resources");
        }
        EnumerableResource enumResource = (EnumerableResource) resources[0];
        final String filter = null;
        final Map<String, String> namespaces = null;
        final boolean getEpr = true;
        EnumerationCtx context =
            enumResource.enumerate(filter, namespaces, Resource.XPATH_DIALECT, getEpr, false);
        int timeout = 30000;
        int numberOfRecords = 5;
        // Work in batches of 5
        while (enumResource.isEndOfSequence() == false)
        {
            Resource[] vSystemsObs = null;
            try
            {
                vSystemsObs =
                    enumResource.pullResources(context, timeout, numberOfRecords,
                        Resource.IGNORE_MAX_CHARS, DESTINATION);
            }
            catch (Exception e)
            {
                break;
            }
            if (vSystemsObs == null || vSystemsObs.length == 0)
            {
                break;
            }

        }
    }

    private void deployVirtualApplication() throws JAXBException, SOAPException,
        DatatypeConfigurationException, IOException, FaultException
    {
        Resource resource;
        // Submit this document
        Document doc = Management.newDocument();
        // Create an OVF envelope
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();
        VirtualSystemCollectionType virtualSystemCollectionType =
            virtualApplianceFactory.createVirtualSystemCollectionType();
        virtualSystemCollectionType.setId(virtualApplianceID);
        JAXBElement<VirtualSystemType> xubuntuVirtualSystem =
            createVirtualSystem(machineType, machineID, machineName, virtualDiskLocation,
                virtualDiskfileId, HYPERVISOR_ADDRESS, memoryRam, numberCpu, remoteDesktopPort,
                diskCapacity);
        /*
         * JAXBElement<VirtualSystemType> tomcatVirtualSystem = createVirtualSystem(machineType2,
         * machineID2, machineName2, virtualDiskLocation2, virtualDiskfileId2,
         * HYPERVISOR_ADDRESS_cambridge);
         */
        virtualSystemCollectionType.getContent().add(xubuntuVirtualSystem);
        // virtualSystemCollectionType.getContent().add(tomcatVirtualSystem);
        JAXBElement<VirtualSystemCollectionType> virtualSystemCollection =
            virtualApplianceFactory.createVirtualSystemCollection(virtualSystemCollectionType);
        envelope.setContent(virtualSystemCollection);
        // Adding the virtual disks to references
        // Setting the virtualDisk File references
        // Adding xubuntu virtual disk
        ReferencesType references = virtualApplianceFactory.createReferencesType();
        FileType xubuntuVirtualDiskFile = virtualApplianceFactory.createFileType();
        xubuntuVirtualDiskFile.setId(virtualDiskfileId);
        xubuntuVirtualDiskFile.setHref(virtualDiskLocation);
        // Adding tomcat virtual disk
        /*
         * FileType tomcatVirtualDiskFile = virtualApplianceFactory.createFileType();
         * tomcatVirtualDiskFile.setId(virtualDiskfileId2);
         * tomcatVirtualDiskFile.setHref(virtualDiskLocation2);
         */
        references.getFile().add(xubuntuVirtualDiskFile);
        // references.getFile().add(tomcatVirtualDiskFile);
        envelope.setReferences(references);

        // Setting the virtual Disk package level element
        DiskSectionType diskSectionTypePackage = virtualApplianceFactory.createDiskSectionType();
        VirtualDiskDescType virtualDescTypePackageXubuntu =
            virtualApplianceFactory.createVirtualDiskDescType();
        // TODO This id are not used
        virtualDescTypePackageXubuntu.setDiskId("virtualDiskId");
        virtualDescTypePackageXubuntu.setFileRef(virtualDiskfileId);
        virtualDescTypePackageXubuntu.setCapacity(diskCapacity);
        diskSectionTypePackage.getDisk().add(virtualDescTypePackageXubuntu);
        /*
         * VirtualDiskDescType virtualDescTypePackageTomcat =
         * virtualApplianceFactory.createVirtualDiskDescType(); // TODO This id are not used
         * virtualDescTypePackageTomcat.setDiskId("virtualDiskId");
         * virtualDescTypePackageTomcat.setFileRef(virtualDiskfileId2);
         * diskSectionTypePackage.getDisk().add(virtualDescTypePackageTomcat);
         */
        JAXBElement<DiskSectionType> diskSectionPackage =
            virtualApplianceFactory.createDiskSection(diskSectionTypePackage);
        envelope.getSection().add(diskSectionPackage);

        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        binding.marshal(envelopeElement, doc);
        // Management ret = sendCreateRequest(DESTINATION, RESOURCE_URI, doc);
        resource =
            ResourceFactory
                .create(DESTINATION, RESOURCE_URI, TIME_OUT, doc, ResourceFactory.LATEST);
    }

    public void undeployVirtualAppliance() throws SOAPException, JAXBException,
        DatatypeConfigurationException, IOException
    {
        // Now request the state of id
        SelectorType nameSelectorType = managementFactory.createSelectorType();
        nameSelectorType.setName("id");
        nameSelectorType.getContent().add(virtualApplianceID);
        HashSet<SelectorType> selectorsHash = new HashSet<SelectorType>();
        selectorsHash.add(nameSelectorType);
        Management response = sendDeleteRequest(DESTINATION, RESOURCE_URI, selectorsHash);
    }

    private JAXBElement<VirtualSystemType> createVirtualSystem(String vsystemType,
        String instanceIdString, String machineName, String virtualDiskLocationRef,
        String virtualDiskfileId, String hypervisorAddress, String memoryRam, String cpuNumber,
        String remoteDesktopPort, String diskCapacity)
    {

        // The Id of the virtualSystem is used for machine name
        VirtualSystemType virtualSystemType = virtualApplianceFactory.createVirtualSystemType();
        virtualSystemType.setId(machineName);

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
        ramUnits.setValue(new BigInteger(memoryRam));
        ramRasd.setVirtualQuantity(ramUnits);
        items.add(ramRasd);
        RASDType cpuNumberRasd = virtualApplianceFactory.createRASDType();
        CimString cpuNumberInstanceId = new CimString();
        cpuNumberInstanceId.setValue("1");
        cpuNumberRasd.setInstanceId(cpuNumberInstanceId);
        ResourceType cpuRasd = new ResourceType();
        cpuRasd.setValue("3");
        CimUnsignedLong cpuUnits = new CimUnsignedLong();
        cpuUnits.setValue(new BigInteger(cpuNumber));
        cpuNumberRasd.setVirtualQuantity(cpuUnits);

        // Creating the Annotation Type
        AnnotationSectionType annotationSectionType =
            virtualApplianceFactory.createAnnotationSectionType();
        Map<QName, String> otherAttributes = annotationSectionType.getOtherAttributes();
        otherAttributes.put(VirtualMachineConfiguration.remoteDesktopQname, remoteDesktopPort);
        JAXBElement<AnnotationSectionType> annotationElement =
            virtualApplianceFactory.createAnnotationSection(annotationSectionType);

        // Setting the Virtual Disk
        DiskSectionType diskSectionType = virtualApplianceFactory.createDiskSectionType();
        VirtualDiskDescType virtualDescType = virtualApplianceFactory.createVirtualDiskDescType();
        // TODO This id is not used
        virtualDescType.setDiskId("virtualDiskId");
        virtualDescType.setFileRef(virtualDiskfileId);
        diskSectionType.getDisk().add(virtualDescType);
        JAXBElement<DiskSectionType> diskSection =
            virtualApplianceFactory.createDiskSection(diskSectionType);
        virtualSystemType.getSection().add(diskSection);

        JAXBElement<VirtualHardwareSectionType> sectionElement =
            virtualApplianceFactory.createVirtualHardwareSection(virtualHardwareType);

        virtualSystemType.getSection().add(sectionElement);
        virtualSystemType.getSection().add(annotationElement);

        JAXBElement<VirtualSystemType> virtualSystemTypeElement =
            virtualApplianceFactory.createVirtualSystem(virtualSystemType);

        return virtualSystemTypeElement;

    }

    private JAXBElement<EnvelopeType> createEnvelopeType(String vsystemType,
        String instanceIdString, String machineName, String virtualDiskLocationRef,
        String virtualDiskfileId, String hypervisorAddress)
    {
        // Create an OVF envelope
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();

        // The Id of the virtualSystem is used for machine name
        VirtualSystemType virtualSystemType = virtualApplianceFactory.createVirtualSystemType();
        virtualSystemType.setId(machineName);

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
        VirtualDiskDescType virtualDescType = virtualApplianceFactory.createVirtualDiskDescType();
        // TODO This id are not used
        virtualDescType.setDiskId("virtualDiskId");
        virtualDescType.setFileRef(virtualDiskfileId);
        diskSectionType.getDisk().add(virtualDescType);
        JAXBElement<DiskSectionType> diskSection =
            virtualApplianceFactory.createDiskSection(diskSectionType);
        virtualSystemType.getSection().add(diskSection);

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
        // TODO This id are not used
        virtualDescTypePackage.setDiskId("virtualDiskId");
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

        // Preparing for submision
        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        return envelopeElement;
    }

    private void deleteVirtualSystem(String machineName) throws SOAPException, JAXBException,
        DatatypeConfigurationException, IOException
    {
        // Now request the state of id
        SelectorType nameSelectorType = managementFactory.createSelectorType();
        nameSelectorType.setName("id");
        nameSelectorType.getContent().add(machineName);
        HashSet<SelectorType> selectorsHash = new HashSet<SelectorType>();
        selectorsHash.add(nameSelectorType);
        Management response = sendDeleteRequest(DESTINATION, RESOURCE_URI, selectorsHash);
    }

    private Document changeMachineState(String hypervisorType, String machineState,
        String machineName, String machineId) throws JAXBException, SOAPException,
        DatatypeConfigurationException, IOException
    {
        // Create a new, empty JAXB Envelope Type
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();

        // Create a new VirtualSystem
        VirtualSystemType vSystemType = virtualApplianceFactory.createVirtualSystemType();
        vSystemType.setId(machineId);

        // Create new virtualSystemCollection

        VirtualSystemCollectionType vSystemCollectionType =
            virtualApplianceFactory.createVirtualSystemCollectionType();

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
        annotationSectionType.getOtherAttributes()
            .put(MachineState.machineStateQname, machineState);
        JAXBElement<AnnotationSectionType> annotationElement =
            virtualApplianceFactory.createAnnotationSection(annotationSectionType);

        // Adding section elements
        vSystemType.getSection().add(virtualHardwareSectionElement);
        vSystemType.getSection().add(annotationElement);

        JAXBElement<VirtualSystemType> virtualSystemElement =
            virtualApplianceFactory.createVirtualSystem(vSystemType);
        vSystemCollectionType.getContent().add(virtualSystemElement);
        vSystemCollectionType.setId("Virtual Application Test");
        JAXBElement<VirtualSystemCollectionType> virtualSystemCollectionElement =
            virtualApplianceFactory.createVirtualSystemCollection(vSystemCollectionType);
        envelope.setContent(virtualSystemCollectionElement);

        // Now request the state of id
        SelectorType nameSelectorType = managementFactory.createSelectorType();
        nameSelectorType.setName("id");
        nameSelectorType.getContent().add(machineId);
        HashSet<SelectorType> selectorsHash = new HashSet<SelectorType>();
        selectorsHash.add(nameSelectorType);

        // Submit this document
        Document doc = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        binding.marshal(envelopeElement, doc);
        return doc;
    }

    private void enumerateResources() throws SOAPException, JAXBException, IOException,
        FaultException, DatatypeConfigurationException
    {
        HashMap<String, String> selectors = null;

        Resource[] resources = ResourceFactory.find(DESTINATION, RESOURCE_URI, 30000, selectors);
        if (resources == null || resources.length == 0)
        {
            log.info("No resources");
        }
        EnumerableResource enumResource = (EnumerableResource) resources[0];
        final String filter = null;
        final Map<String, String> namespaces = null;
        final boolean getEpr = true;
        EnumerationCtx context =
            enumResource.enumerate(filter, namespaces, Resource.XPATH_DIALECT, getEpr, false);
        int timeout = 30000;
        int numberOfRecords = 5;
        // Work in batches of 5
        while (enumResource.isEndOfSequence() == false)
        {
            vApplianceObjs = null;
            try
            {
                vApplianceObjs =
                    enumResource.pullResources(context, timeout, numberOfRecords,
                        Resource.IGNORE_MAX_CHARS, DESTINATION);
            }
            catch (Exception e)
            {
                break;
            }
            if (vApplianceObjs == null || vApplianceObjs.length == 0)
            {
                break;
            }

        }
    }

    private void putMachineState(String hypervisorType, String machineState, String machineName,
        String machineId) throws SOAPException, JAXBException, IOException, FaultException,
        DatatypeConfigurationException
    {
        Document doc = changeMachineState(hypervisorType, machineState, machineName, machineId);
        Resource resource = vApplianceObjs[0];
        resource.put(doc);
    }

    private void reconfigVirtualMachine() throws JAXBException, SOAPException, IOException,
        FaultException, DatatypeConfigurationException
    {
        long newHd = 214748160;
        // long newHd = 107374080;
        int newRam = 256;
        int newCpu = 2;
        String hypervisorAddress = HYPERVISOR_ADDRESS;
        String vsystemType = machineType;
        String instanceIdString = machineID;
        String virtualDiskLocationRef = virtualDiskLocation;
        String virtualDiskId = UUID.randomUUID().toString();

        // Create a new, empty JAXB Envelope Type
        EnvelopeType envelope = virtualApplianceFactory.createEnvelopeType();

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
        // Extended disk
        VirtualDiskDescType extendedVirtualDescType =
            virtualApplianceFactory.createVirtualDiskDescType();
        extendedVirtualDescType.setDiskId(UUID.randomUUID().toString());
        extendedVirtualDescType.setCapacity(String.valueOf(newHd));
        diskSectionType.getDisk().add(extendedVirtualDescType);
        JAXBElement<DiskSectionType> diskSection =
            virtualApplianceFactory.createDiskSection(diskSectionType);
        virtualSystemType.getSection().add(diskSection);

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
        ramUnits.setValue(new BigInteger(String.valueOf(newRam)));
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
        cpuUnits.setValue(BigInteger.valueOf(newCpu));
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
        // Submit this document
        Document doc = Management.newDocument();
        JAXBElement<EnvelopeType> envelopeElement =
            virtualApplianceFactory.createEnvelope(envelope);
        binding.marshal(envelopeElement, doc);
        // Now request the state of id
        SelectorType nameSelectorType = managementFactory.createSelectorType();
        nameSelectorType.setName("id");
        nameSelectorType.getContent().add(machineName);
        HashSet<SelectorType> selectorsHash = new HashSet<SelectorType>();
        selectorsHash.add(nameSelectorType);
        Management response = sendPutRequest(DESTINATION, RESOURCE_URI, selectorsHash, doc);
    }

}
