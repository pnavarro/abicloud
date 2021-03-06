//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.25 at 04:38:05 PM CET 
//

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
