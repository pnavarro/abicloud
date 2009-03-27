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
package org.dmtf.schemas.wbem.wscim._1.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the org.dmtf.schemas.wbem.wscim._1.common package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory
{

    private final static QName _CimDateTimeCIMDateTime_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/common", "CIM_DateTime");

    private final static QName __0020DefaultValue_QNAME =
        new QName("http://schemas.dmtf.org/wbem/wscim/1/common", " DefaultValue");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: org.dmtf.schemas.wbem.wscim._1.common
     */
    public ObjectFactory()
    {
    }

    /**
     * Create an instance of {@link QualifierUInt32 }
     */
    public QualifierUInt32 createQualifierUInt32()
    {
        return new QualifierUInt32();
    }

    /**
     * Create an instance of {@link CimBoolean }
     */
    public CimBoolean createCimBoolean()
    {
        return new CimBoolean();
    }

    /**
     * Create an instance of {@link CimBase64Binary }
     */
    public CimBase64Binary createCimBase64Binary()
    {
        return new CimBase64Binary();
    }

    /**
     * Create an instance of {@link CimDouble }
     */
    public CimDouble createCimDouble()
    {
        return new CimDouble();
    }

    /**
     * Create an instance of {@link QualifierBoolean }
     */
    public QualifierBoolean createQualifierBoolean()
    {
        return new QualifierBoolean();
    }

    /**
     * Create an instance of {@link CimUnsignedInt }
     */
    public CimUnsignedInt createCimUnsignedInt()
    {
        return new CimUnsignedInt();
    }

    /**
     * Create an instance of {@link CimUnsignedByte }
     */
    public CimUnsignedByte createCimUnsignedByte()
    {
        return new CimUnsignedByte();
    }

    /**
     * Create an instance of {@link CimHexBinary }
     */
    public CimHexBinary createCimHexBinary()
    {
        return new CimHexBinary();
    }

    /**
     * Create an instance of {@link CimInt }
     */
    public CimInt createCimInt()
    {
        return new CimInt();
    }

    /**
     * Create an instance of {@link QualifierString }
     */
    public QualifierString createQualifierString()
    {
        return new QualifierString();
    }

    /**
     * Create an instance of {@link CimFloat }
     */
    public CimFloat createCimFloat()
    {
        return new CimFloat();
    }

    /**
     * Create an instance of {@link CimUnsignedLong }
     */
    public CimUnsignedLong createCimUnsignedLong()
    {
        return new CimUnsignedLong();
    }

    /**
     * Create an instance of {@link CimDateTime }
     */
    public CimDateTime createCimDateTime()
    {
        return new CimDateTime();
    }

    /**
     * Create an instance of {@link QualifierSInt64 }
     */
    public QualifierSInt64 createQualifierSInt64()
    {
        return new QualifierSInt64();
    }

    /**
     * Create an instance of {@link CimString }
     */
    public CimString createCimString()
    {
        return new CimString();
    }

    /**
     * Create an instance of {@link CimChar16 }
     */
    public CimChar16 createCimChar16()
    {
        return new CimChar16();
    }

    /**
     * Create an instance of {@link CimReference }
     */
    public CimReference createCimReference()
    {
        return new CimReference();
    }

    /**
     * Create an instance of {@link CimShort }
     */
    public CimShort createCimShort()
    {
        return new CimShort();
    }

    /**
     * Create an instance of {@link QualifierSArray }
     */
    public QualifierSArray createQualifierSArray()
    {
        return new QualifierSArray();
    }

    /**
     * Create an instance of {@link CimByte }
     */
    public CimByte createCimByte()
    {
        return new CimByte();
    }

    /**
     * Create an instance of {@link CimLong }
     */
    public CimLong createCimLong()
    {
        return new CimLong();
    }

    /**
     * Create an instance of {@link CimUnsignedShort }
     */
    public CimUnsignedShort createCimUnsignedShort()
    {
        return new CimUnsignedShort();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/common", name = "CIM_DateTime", scope = CimDateTime.class)
    public JAXBElement<String> createCimDateTimeCIMDateTime(String value)
    {
        return new JAXBElement<String>(_CimDateTimeCIMDateTime_QNAME,
            String.class,
            CimDateTime.class,
            value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.dmtf.org/wbem/wscim/1/common", name = " DefaultValue")
    public JAXBElement<Object> create_0020DefaultValue(Object value)
    {
        return new JAXBElement<Object>(__0020DefaultValue_QNAME, Object.class, null, value);
    }

}
