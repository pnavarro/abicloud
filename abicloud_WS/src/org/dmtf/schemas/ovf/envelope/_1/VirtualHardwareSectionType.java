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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Specifies virtual hardware requirements for a virtual machine
 * <p>
 * Java class for VirtualHardwareSection_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;VirtualHardwareSection_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Section_Type&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;System&quot; type=&quot;{http://schemas.dmtf.org/ovf/envelope/1}VSSD_Type&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;Item&quot; type=&quot;{http://schemas.dmtf.org/ovf/envelope/1}RASD_Type&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name=&quot;transport&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; default=&quot;&quot; /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualHardwareSection_Type", propOrder = {"system", "item"})
public class VirtualHardwareSectionType extends SectionType
{

    @XmlElement(name = "System")
    protected VSSDType system;

    @XmlElement(name = "Item")
    protected List<RASDType> item;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String transport;

    /**
     * Gets the value of the system property.
     * 
     * @return possible object is {@link VSSDType }
     */
    public VSSDType getSystem()
    {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value allowed object is {@link VSSDType }
     */
    public void setSystem(VSSDType value)
    {
        this.system = value;
    }

    /**
     * Gets the value of the item property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the item property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getItem().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link RASDType }
     */
    public List<RASDType> getItem()
    {
        if (item == null)
        {
            item = new ArrayList<RASDType>();
        }
        return this.item;
    }

    /**
     * Gets the value of the transport property.
     * 
     * @return possible object is {@link String }
     */
    public String getTransport()
    {
        if (transport == null)
        {
            return "";
        }
        else
        {
            return transport;
        }
    }

    /**
     * Sets the value of the transport property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setTransport(String value)
    {
        this.transport = value;
    }

}
