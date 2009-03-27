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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * Descriptions of logical networks used within the package
 * <p>
 * Java class for NetworkSection_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;NetworkSection_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Section_Type&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;Network&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name=&quot;Description&quot; type=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Msg_Type&quot; minOccurs=&quot;0&quot;/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name=&quot;name&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetworkSection_Type", propOrder = {"network"})
public class NetworkSectionType extends SectionType
{

    @XmlElement(name = "Network")
    protected List<NetworkSectionType.Network> network;

    /**
     * Gets the value of the network property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the network property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getNetwork().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link NetworkSectionType.Network }
     */
    public List<NetworkSectionType.Network> getNetwork()
    {
        if (network == null)
        {
            network = new ArrayList<NetworkSectionType.Network>();
        }
        return this.network;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
     *       &lt;sequence&gt;
     *         &lt;element name=&quot;Description&quot; type=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Msg_Type&quot; minOccurs=&quot;0&quot;/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name=&quot;name&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"description"})
    public static class Network
    {

        @XmlElement(name = "Description")
        protected MsgType description;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
        protected String name;

        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

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
         * Gets the value of the name property.
         * 
         * @return possible object is {@link String }
         */
        public String getName()
        {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value allowed object is {@link String }
         */
        public void setName(String value)
        {
            this.name = value;
        }

        /**
         * Gets a map that contains attributes that aren't bound to any typed property on this
         * class.
         * <p>
         * the map is keyed by the name of the attribute and the value is the string value of the
         * attribute. the map returned by this method is live, and you can add new attribute by
         * updating the map directly. Because of this design, there's no setter.
         * 
         * @return always non-null
         */
        public Map<QName, String> getOtherAttributes()
        {
            return otherAttributes;
        }

    }

}
