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
package org.dmtf.schemas.wbem.wscim._1.common;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for cimDateTime complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;cimDateTime&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;choice&gt;
 *         &lt;element name=&quot;CIM_DateTime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;/&gt;
 *         &lt;element name=&quot;Interval&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}duration&quot;/&gt;
 *         &lt;element name=&quot;Date&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}date&quot;/&gt;
 *         &lt;element name=&quot;Time&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}time&quot;/&gt;
 *         &lt;element name=&quot;Datetime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}dateTime&quot;/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cimDateTime", propOrder = {"cimDateTime", "interval", "date", "time", "datetime"})
public class CimDateTime
{

    @XmlElementRef(name = "CIM_DateTime", namespace = "http://schemas.dmtf.org/wbem/wscim/1/common", type = JAXBElement.class)
    protected JAXBElement<String> cimDateTime;

    @XmlElement(name = "Interval")
    protected Duration interval;

    @XmlElement(name = "Date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;

    @XmlElement(name = "Time")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar time;

    @XmlElement(name = "Datetime")
    protected XMLGregorianCalendar datetime;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the cimDateTime property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String } {@code >}
     */
    public JAXBElement<String> getCIMDateTime()
    {
        return cimDateTime;
    }

    /**
     * Sets the value of the cimDateTime property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String } {@code >}
     */
    public void setCIMDateTime(JAXBElement<String> value)
    {
        this.cimDateTime = ((JAXBElement<String>) value);
    }

    /**
     * Gets the value of the interval property.
     * 
     * @return possible object is {@link Duration }
     */
    public Duration getInterval()
    {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     * 
     * @param value allowed object is {@link Duration }
     */
    public void setInterval(Duration value)
    {
        this.interval = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDate()
    {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     */
    public void setDate(XMLGregorianCalendar value)
    {
        this.date = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getTime()
    {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     */
    public void setTime(XMLGregorianCalendar value)
    {
        this.time = value;
    }

    /**
     * Gets the value of the datetime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDatetime()
    {
        return datetime;
    }

    /**
     * Sets the value of the datetime property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     */
    public void setDatetime(XMLGregorianCalendar value)
    {
        this.datetime = value;
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
