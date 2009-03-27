package com.abiquo.abicloud.vsm.event;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for EventTypeEnumeration.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name=&quot;EventTypeEnumeration&quot;&gt;
 *   &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;&gt;
 *     &lt;enumeration value=&quot;PoweredOn&quot;/&gt;
 *     &lt;enumeration value=&quot;PoweredOff&quot;/&gt;
 *     &lt;enumeration value=&quot;Suspended&quot;/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "EventTypeEnumeration")
@XmlEnum
public enum EventTypeEnumeration
{

    @XmlEnumValue("PoweredOn")
    POWERED_ON("PoweredOn"), @XmlEnumValue("PoweredOff")
    POWERED_OFF("PoweredOff"), @XmlEnumValue("Suspended")
    SUSPENDED("Suspended");
    private final String value;

    EventTypeEnumeration(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static EventTypeEnumeration fromValue(String v)
    {
        for (EventTypeEnumeration c : EventTypeEnumeration.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
