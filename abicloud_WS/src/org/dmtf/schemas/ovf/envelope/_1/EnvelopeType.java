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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * Root OVF descriptor type
 * <p>
 * Java class for EnvelopeType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;EnvelopeType&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;References&quot; type=&quot;{http://schemas.dmtf.org/ovf/envelope/1}References_Type&quot;/&gt;
 *         &lt;element ref=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Section&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Content&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;Strings&quot; type=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Strings_Type&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnvelopeType", propOrder = {"references", "section", "content", "strings"})
public class EnvelopeType
{

    @XmlElement(name = "References", required = true)
    protected ReferencesType references;

    @XmlElementRef(name = "Section", namespace = "http://schemas.dmtf.org/ovf/envelope/1", type = JAXBElement.class)
    protected List<JAXBElement< ? extends SectionType>> section;

    @XmlElementRef(name = "Content", namespace = "http://schemas.dmtf.org/ovf/envelope/1", type = JAXBElement.class)
    protected JAXBElement< ? extends EntityType> content;

    @XmlElement(name = "Strings")
    protected List<StringsType> strings;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the references property.
     * 
     * @return possible object is {@link ReferencesType }
     */
    public ReferencesType getReferences()
    {
        return references;
    }

    /**
     * Sets the value of the references property.
     * 
     * @param value allowed object is {@link ReferencesType }
     */
    public void setReferences(ReferencesType value)
    {
        this.references = value;
    }

    /**
     * Package level meta-data Gets the value of the section property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the section property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSection().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link JAXBElement }{@code <}
     * {@link VirtualHardwareSectionType }{@code >} {@link JAXBElement }{@code <}
     * {@link AnnotationSectionType }{@code >} {@link JAXBElement }{@code <}{@link DiskSectionType }
     * {@code >} {@link JAXBElement }{@code <}{@link StartupSectionType }{@code >} {@link JAXBElement }
     * {@code <}{@link ProductSectionType }{@code >} {@link JAXBElement }{@code <}
     * {@link InstallSectionType }{@code >} {@link JAXBElement }{@code <}{@link EulaSectionType }
     * {@code >} {@link JAXBElement }{@code <}{@link ResourceAllocationSectionType }{@code >}
     * {@link JAXBElement }{@code <}{@link DeploymentOptionSectionType }{@code >} {@link JAXBElement }
     * {@code <}{@link SectionType }{@code >} {@link JAXBElement }{@code <}{@link NetworkSectionType }
     * {@code >} {@link JAXBElement }{@code <}{@link OperatingSystemSectionType }{@code >}
     */
    public List<JAXBElement< ? extends SectionType>> getSection()
    {
        if (section == null)
        {
            section = new ArrayList<JAXBElement< ? extends SectionType>>();
        }
        return this.section;
    }

    /**
     * Content: A VirtualSystem or a VirtualSystemCollection
     * 
     * @return possible object is {@link JAXBElement }{@code <} {@link VirtualSystemType }{@code >}
     *         {@link JAXBElement }{@code <} {@link VirtualSystemCollectionType }{@code >}
     *         {@link JAXBElement } {@code <}{@link EntityType }{@code >}
     */
    public JAXBElement< ? extends EntityType> getContent()
    {
        return content;
    }

    /**
     * Content: A VirtualSystem or a VirtualSystemCollection
     * 
     * @param value allowed object is {@link JAXBElement }{@code <} {@link VirtualSystemType }{@code
     *            >} {@link JAXBElement }{@code <}{@link VirtualSystemCollectionType }{@code >}
     *            {@link JAXBElement }{@code <}{@link EntityType }{@code >}
     */
    public void setContent(JAXBElement< ? extends EntityType> value)
    {
        this.content = ((JAXBElement< ? extends EntityType>) value);
    }

    /**
     * Gets the value of the strings property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the strings property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStrings().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link StringsType }
     */
    public List<StringsType> getStrings()
    {
        if (strings == null)
        {
            strings = new ArrayList<StringsType>();
        }
        return this.strings;
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
