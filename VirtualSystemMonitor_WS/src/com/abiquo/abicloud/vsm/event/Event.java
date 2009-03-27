package com.abiquo.abicloud.vsm.event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for Event complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;Event&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;attribute name=&quot;virtualSystemID&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anyURI&quot; /&gt;
 *       &lt;attribute name=&quot;createdTime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}dateTime&quot; /&gt;
 *       &lt;attribute name=&quot;user&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;eventKind&quot; type=&quot;{http://abiquo.com/abicloud/vsm/event.xsd}EventKindEnumeration&quot; /&gt;
 *       &lt;attribute name=&quot;eventType&quot; type=&quot;{http://abiquo.com/abicloud/vsm/event.xsd}EventTypeEnumeration&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Event")
public class Event
{

    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String virtualSystemID;

    @XmlAttribute
    protected XMLGregorianCalendar createdTime;

    @XmlAttribute
    protected String user;

    @XmlAttribute
    protected EventKindEnumeration eventKind;

    @XmlAttribute
    protected EventTypeEnumeration eventType;

    /**
     * Gets the value of the virtualSystemID property.
     * 
     * @return possible object is {@link String }
     */
    public String getVirtualSystemID()
    {
        return virtualSystemID;
    }

    /**
     * Sets the value of the virtualSystemID property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setVirtualSystemID(String value)
    {
        this.virtualSystemID = value;
    }

    /**
     * Gets the value of the createdTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getCreatedTime()
    {
        return createdTime;
    }

    /**
     * Sets the value of the createdTime property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     */
    public void setCreatedTime(XMLGregorianCalendar value)
    {
        this.createdTime = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return possible object is {@link String }
     */
    public String getUser()
    {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setUser(String value)
    {
        this.user = value;
    }

    /**
     * Gets the value of the eventKind property.
     * 
     * @return possible object is {@link EventKindEnumeration }
     */
    public EventKindEnumeration getEventKind()
    {
        return eventKind;
    }

    /**
     * Sets the value of the eventKind property.
     * 
     * @param value allowed object is {@link EventKindEnumeration }
     */
    public void setEventKind(EventKindEnumeration value)
    {
        this.eventKind = value;
    }

    /**
     * Gets the value of the eventType property.
     * 
     * @return possible object is {@link EventTypeEnumeration }
     */
    public EventTypeEnumeration getEventType()
    {
        return eventType;
    }

    /**
     * Sets the value of the eventType property.
     * 
     * @param value allowed object is {@link EventTypeEnumeration }
     */
    public void setEventType(EventTypeEnumeration value)
    {
        this.eventType = value;
    }

}
