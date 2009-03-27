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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.dmtf.schemas.ovf.envelope._1.VSSDType;
import org.dmtf.schemas.wbem.wscim._1.common.CimString;
import org.w3c.dom.Element;

/**
 * <p>
 * Java class for CIM_VirtualSystemSettingData_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;CIM_VirtualSystemSettingData_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref=&quot;{http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData}Caption&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData}Description&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData}InstanceId&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData}VirtualSystemIdentifier&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData}VirtualSystemType&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;any/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CIM_VirtualSystemSettingData_Type", propOrder = {"caption", "description",
"instanceId", "virtualSystemIdentifier", "virtualSystemType", "any"})
@XmlSeeAlso( {VSSDType.class})
public class CIMVirtualSystemSettingDataType
{

    @XmlElement(name = "Caption", nillable = true)
    protected List<CimString> caption;

    @XmlElement(name = "Description", nillable = true)
    protected List<CimString> description;

    @XmlElement(name = "InstanceId", nillable = true)
    protected CimString instanceId;

    @XmlElement(name = "VirtualSystemIdentifier", nillable = true)
    protected CimString virtualSystemIdentifier;

    @XmlElement(name = "VirtualSystemType", nillable = true)
    protected CimString virtualSystemType;

    @XmlAnyElement(lax = true)
    protected List<Object> any;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the caption property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the caption property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getCaption().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link CimString }
     */
    public List<CimString> getCaption()
    {
        if (caption == null)
        {
            caption = new ArrayList<CimString>();
        }
        return this.caption;
    }

    /**
     * Gets the value of the description property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the description property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDescription().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link CimString }
     */
    public List<CimString> getDescription()
    {
        if (description == null)
        {
            description = new ArrayList<CimString>();
        }
        return this.description;
    }

    /**
     * Gets the value of the instanceId property.
     * 
     * @return possible object is {@link CimString }
     */
    public CimString getInstanceId()
    {
        return instanceId;
    }

    /**
     * Sets the value of the instanceId property.
     * 
     * @param value allowed object is {@link CimString }
     */
    public void setInstanceId(CimString value)
    {
        this.instanceId = value;
    }

    /**
     * Gets the value of the virtualSystemIdentifier property.
     * 
     * @return possible object is {@link CimString }
     */
    public CimString getVirtualSystemIdentifier()
    {
        return virtualSystemIdentifier;
    }

    /**
     * Sets the value of the virtualSystemIdentifier property.
     * 
     * @param value allowed object is {@link CimString }
     */
    public void setVirtualSystemIdentifier(CimString value)
    {
        this.virtualSystemIdentifier = value;
    }

    /**
     * Gets the value of the virtualSystemType property.
     * 
     * @return possible object is {@link CimString }
     */
    public CimString getVirtualSystemType()
    {
        return virtualSystemType;
    }

    /**
     * Sets the value of the virtualSystemType property.
     * 
     * @param value allowed object is {@link CimString }
     */
    public void setVirtualSystemType(CimString value)
    {
        this.virtualSystemType = value;
    }

    /**
     * Gets the value of the any property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the any property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAny().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Element } {@link Object }
     */
    public List<Object> getAny()
    {
        if (any == null)
        {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * <p>
     * the map is keyed by the name of the attribute and the value is the string value of the
     * attribute. the map returned by this method is live, and you can add new attribute by updating
     * the map directly. Because of this design, there's no setter.
     * 
     * @return always non-null
     */
    public Map<QName, String> getOtherAttributes()
    {
        return otherAttributes;
    }

}
