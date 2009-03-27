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
 * Specifies the order in which entities in a VirtualSystemCollection are powered on and shut down
 * <p>
 * Java class for StartupSection_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;StartupSection_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Section_Type&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;Item&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                 &lt;attribute name=&quot;id&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *                 &lt;attribute name=&quot;order&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; /&gt;
 *                 &lt;attribute name=&quot;startDelay&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; default=&quot;0&quot; /&gt;
 *                 &lt;attribute name=&quot;waitingForGuest&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}boolean&quot; default=&quot;false&quot; /&gt;
 *                 &lt;attribute name=&quot;stopDelay&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; default=&quot;0&quot; /&gt;
 *                 &lt;attribute name=&quot;startAction&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; default=&quot;powerOn&quot; /&gt;
 *                 &lt;attribute name=&quot;stopAction&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; default=&quot;powerOff&quot; /&gt;
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
@XmlType(name = "StartupSection_Type", propOrder = {"item"})
public class StartupSectionType extends SectionType
{

    @XmlElement(name = "Item")
    protected List<StartupSectionType.Item> item;

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
     * Objects of the following type(s) are allowed in the list {@link StartupSectionType.Item }
     */
    public List<StartupSectionType.Item> getItem()
    {
        if (item == null)
        {
            item = new ArrayList<StartupSectionType.Item>();
        }
        return this.item;
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
     *       &lt;attribute name=&quot;id&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
     *       &lt;attribute name=&quot;order&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; /&gt;
     *       &lt;attribute name=&quot;startDelay&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; default=&quot;0&quot; /&gt;
     *       &lt;attribute name=&quot;waitingForGuest&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}boolean&quot; default=&quot;false&quot; /&gt;
     *       &lt;attribute name=&quot;stopDelay&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; default=&quot;0&quot; /&gt;
     *       &lt;attribute name=&quot;startAction&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; default=&quot;powerOn&quot; /&gt;
     *       &lt;attribute name=&quot;stopAction&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; default=&quot;powerOff&quot; /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Item
    {

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
        protected String id;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
        protected int order;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
        protected Integer startDelay;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
        protected Boolean waitingForGuest;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
        protected Integer stopDelay;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
        protected String startAction;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
        protected String stopAction;

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
         * Gets the value of the order property.
         */
        public int getOrder()
        {
            return order;
        }

        /**
         * Sets the value of the order property.
         */
        public void setOrder(int value)
        {
            this.order = value;
        }

        /**
         * Gets the value of the startDelay property.
         * 
         * @return possible object is {@link Integer }
         */
        public int getStartDelay()
        {
            if (startDelay == null)
            {
                return 0;
            }
            else
            {
                return startDelay;
            }
        }

        /**
         * Sets the value of the startDelay property.
         * 
         * @param value allowed object is {@link Integer }
         */
        public void setStartDelay(Integer value)
        {
            this.startDelay = value;
        }

        /**
         * Gets the value of the waitingForGuest property.
         * 
         * @return possible object is {@link Boolean }
         */
        public boolean isWaitingForGuest()
        {
            if (waitingForGuest == null)
            {
                return false;
            }
            else
            {
                return waitingForGuest;
            }
        }

        /**
         * Sets the value of the waitingForGuest property.
         * 
         * @param value allowed object is {@link Boolean }
         */
        public void setWaitingForGuest(Boolean value)
        {
            this.waitingForGuest = value;
        }

        /**
         * Gets the value of the stopDelay property.
         * 
         * @return possible object is {@link Integer }
         */
        public int getStopDelay()
        {
            if (stopDelay == null)
            {
                return 0;
            }
            else
            {
                return stopDelay;
            }
        }

        /**
         * Sets the value of the stopDelay property.
         * 
         * @param value allowed object is {@link Integer }
         */
        public void setStopDelay(Integer value)
        {
            this.stopDelay = value;
        }

        /**
         * Gets the value of the startAction property.
         * 
         * @return possible object is {@link String }
         */
        public String getStartAction()
        {
            if (startAction == null)
            {
                return "powerOn";
            }
            else
            {
                return startAction;
            }
        }

        /**
         * Sets the value of the startAction property.
         * 
         * @param value allowed object is {@link String }
         */
        public void setStartAction(String value)
        {
            this.startAction = value;
        }

        /**
         * Gets the value of the stopAction property.
         * 
         * @return possible object is {@link String }
         */
        public String getStopAction()
        {
            if (stopAction == null)
            {
                return "powerOff";
            }
            else
            {
                return stopAction;
            }
        }

        /**
         * Sets the value of the stopAction property.
         * 
         * @param value allowed object is {@link String }
         */
        public void setStopAction(String value)
        {
            this.stopAction = value;
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
