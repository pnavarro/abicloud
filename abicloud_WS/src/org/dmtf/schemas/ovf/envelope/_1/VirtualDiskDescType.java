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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Type for virtual disk descriptor
 * <p>
 * Java class for VirtualDiskDesc_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;VirtualDiskDesc_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;attribute name=&quot;diskId&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;fileRef&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;capacity&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;capacityAllocationUnits&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;format&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;populatedSize&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot; /&gt;
 *       &lt;attribute name=&quot;parentRef&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualDiskDesc_Type")
public class VirtualDiskDescType
{

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
    protected String diskId;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String fileRef;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
    protected String capacity;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String capacityAllocationUnits;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
    protected String format;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected Long populatedSize;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String parentRef;

    /**
     * Gets the value of the diskId property.
     * 
     * @return possible object is {@link String }
     */
    public String getDiskId()
    {
        return diskId;
    }

    /**
     * Sets the value of the diskId property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setDiskId(String value)
    {
        this.diskId = value;
    }

    /**
     * Gets the value of the fileRef property.
     * 
     * @return possible object is {@link String }
     */
    public String getFileRef()
    {
        return fileRef;
    }

    /**
     * Sets the value of the fileRef property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setFileRef(String value)
    {
        this.fileRef = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     * @return possible object is {@link String }
     */
    public String getCapacity()
    {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setCapacity(String value)
    {
        this.capacity = value;
    }

    /**
     * Gets the value of the capacityAllocationUnits property.
     * 
     * @return possible object is {@link String }
     */
    public String getCapacityAllocationUnits()
    {
        return capacityAllocationUnits;
    }

    /**
     * Sets the value of the capacityAllocationUnits property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setCapacityAllocationUnits(String value)
    {
        this.capacityAllocationUnits = value;
    }

    /**
     * Gets the value of the format property.
     * 
     * @return possible object is {@link String }
     */
    public String getFormat()
    {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setFormat(String value)
    {
        this.format = value;
    }

    /**
     * Gets the value of the populatedSize property.
     * 
     * @return possible object is {@link Long }
     */
    public Long getPopulatedSize()
    {
        return populatedSize;
    }

    /**
     * Sets the value of the populatedSize property.
     * 
     * @param value allowed object is {@link Long }
     */
    public void setPopulatedSize(Long value)
    {
        this.populatedSize = value;
    }

    /**
     * Gets the value of the parentRef property.
     * 
     * @return possible object is {@link String }
     */
    public String getParentRef()
    {
        return parentRef;
    }

    /**
     * Sets the value of the parentRef property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setParentRef(String value)
    {
        this.parentRef = value;
    }

}
