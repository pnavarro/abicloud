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
package org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_virtualsystemsettingdata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.dmtf.schemas.wbem.wscim._1.common.CimString;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_virtualsystemsettingdata
 * package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory
{

    private final static QName _Description_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData",
            "Description");

    private final static QName _VirtualSystemIdentifier_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData",
            "VirtualSystemIdentifier");

    private final static QName _CIMVirtualSystemSettingData_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData",
            "CIM_VirtualSystemSettingData");

    private final static QName _InstanceId_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData",
            "InstanceId");

    private final static QName _Caption_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData",
            "Caption");

    private final static QName _VirtualSystemType_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData",
            "VirtualSystemType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_virtualsystemsettingdata
     */
    public ObjectFactory()
    {
    }

    /**
     * Create an instance of {@link CIMVirtualSystemSettingDataType }
     */
    public CIMVirtualSystemSettingDataType createCIMVirtualSystemSettingDataType()
    {
        return new CIMVirtualSystemSettingDataType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CimString } {@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData", name = "Description")
    public JAXBElement<CimString> createDescription(CimString value)
    {
        return new JAXBElement<CimString>(_Description_QNAME, CimString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CimString } {@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData", name = "VirtualSystemIdentifier")
    public JAXBElement<CimString> createVirtualSystemIdentifier(CimString value)
    {
        return new JAXBElement<CimString>(_VirtualSystemIdentifier_QNAME,
            CimString.class,
            null,
            value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <} {@link CIMVirtualSystemSettingDataType }
     * {@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData", name = "CIM_VirtualSystemSettingData")
    public JAXBElement<CIMVirtualSystemSettingDataType> createCIMVirtualSystemSettingData(
        CIMVirtualSystemSettingDataType value)
    {
        return new JAXBElement<CIMVirtualSystemSettingDataType>(_CIMVirtualSystemSettingData_QNAME,
            CIMVirtualSystemSettingDataType.class,
            null,
            value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CimString } {@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData", name = "InstanceId")
    public JAXBElement<CimString> createInstanceId(CimString value)
    {
        return new JAXBElement<CimString>(_InstanceId_QNAME, CimString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CimString } {@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData", name = "Caption")
    public JAXBElement<CimString> createCaption(CimString value)
    {
        return new JAXBElement<CimString>(_Caption_QNAME, CimString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CimString } {@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData", name = "VirtualSystemType")
    public JAXBElement<CimString> createVirtualSystemType(CimString value)
    {
        return new JAXBElement<CimString>(_VirtualSystemType_QNAME, CimString.class, null, value);
    }

}
