//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.25 at 04:38:05 PM CET 
//

package org.dmtf.schemas.ovf.envelope._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * If present indicates that the virtual machine needs to be initially booted to install and
 * configure the software
 * <p>
 * Java class for InstallSection_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;InstallSection_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Section_Type&quot;&gt;
 *       &lt;attribute name=&quot;initialBoot&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}boolean&quot; default=&quot;true&quot; /&gt;
 *       &lt;attribute name=&quot;initialBootStopDelay&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; default=&quot;0&quot; /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstallSection_Type")
public class InstallSectionType extends SectionType
{

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected Boolean initialBoot;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected Integer initialBootStopDelay;

    /**
     * Gets the value of the initialBoot property.
     * 
     * @return possible object is {@link Boolean }
     */
    public boolean isInitialBoot()
    {
        if (initialBoot == null)
        {
            return true;
        }
        else
        {
            return initialBoot;
        }
    }

    /**
     * Sets the value of the initialBoot property.
     * 
     * @param value allowed object is {@link Boolean }
     */
    public void setInitialBoot(Boolean value)
    {
        this.initialBoot = value;
    }

    /**
     * Gets the value of the initialBootStopDelay property.
     * 
     * @return possible object is {@link Integer }
     */
    public int getInitialBootStopDelay()
    {
        if (initialBootStopDelay == null)
        {
            return 0;
        }
        else
        {
            return initialBootStopDelay;
        }
    }

    /**
     * Sets the value of the initialBootStopDelay property.
     * 
     * @param value allowed object is {@link Integer }
     */
    public void setInitialBootStopDelay(Integer value)
    {
        this.initialBootStopDelay = value;
    }

}
