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
package org.dmtf.schemas.ovf.envelope._1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Specification of the operating system installed in the guest
 * <p>
 * Java class for OperatingSystemSection_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;OperatingSystemSection_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Section_Type&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;Description&quot; type=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Msg_Type&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name=&quot;id&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}integer&quot; /&gt;
 *       &lt;attribute name=&quot;version&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperatingSystemSection_Type", propOrder = {"description"})
public class OperatingSystemSectionType extends SectionType
{

    @XmlElement(name = "Description")
    protected MsgType description;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
    protected BigInteger id;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String version;

    /**
     * Gets the value of the description property.
     * 
     * @return possible object is {@link MsgType }
     */
    public MsgType getDescription()
    {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value allowed object is {@link MsgType }
     */
    public void setDescription(MsgType value)
    {
        this.description = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getId()
    {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value allowed object is {@link BigInteger }
     */
    public void setId(BigInteger value)
    {
        this.id = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return possible object is {@link String }
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setVersion(String value)
    {
        this.version = value;
    }

}
