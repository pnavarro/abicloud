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
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * Type for an external reference to a resource
 * <p>
 * Java class for File_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;File_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;attribute name=&quot;id&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;href&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anyURI&quot; /&gt;
 *       &lt;attribute name=&quot;size&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}integer&quot; /&gt;
 *       &lt;attribute name=&quot;compression&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; default=&quot;&quot; /&gt;
 *       &lt;attribute name=&quot;chunkSize&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "File_Type")
public class FileType
{

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
    protected String id;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String href;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected BigInteger size;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String compression;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected Long chunkSize;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link String }
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setId(String value)
    {
        this.id = value;
    }

    /**
     * Gets the value of the href property.
     * 
     * @return possible object is {@link String }
     */
    public String getHref()
    {
        return href;
    }

    /**
     * Sets the value of the href property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setHref(String value)
    {
        this.href = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return possible object is {@link BigInteger }
     */
    public BigInteger getSize()
    {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value allowed object is {@link BigInteger }
     */
    public void setSize(BigInteger value)
    {
        this.size = value;
    }

    /**
     * Gets the value of the compression property.
     * 
     * @return possible object is {@link String }
     */
    public String getCompression()
    {
        if (compression == null)
        {
            return "";
        }
        else
        {
            return compression;
        }
    }

    /**
     * Sets the value of the compression property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setCompression(String value)
    {
        this.compression = value;
    }

    /**
     * Gets the value of the chunkSize property.
     * 
     * @return possible object is {@link Long }
     */
    public Long getChunkSize()
    {
        return chunkSize;
    }

    /**
     * Sets the value of the chunkSize property.
     * 
     * @param value allowed object is {@link Long }
     */
    public void setChunkSize(Long value)
    {
        this.chunkSize = value;
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
