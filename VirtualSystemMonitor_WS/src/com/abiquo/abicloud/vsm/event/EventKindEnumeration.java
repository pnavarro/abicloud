package com.abiquo.abicloud.vsm.event;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for EventKindEnumeration.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name=&quot;EventKindEnumeration&quot;&gt;
 *   &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;&gt;
 *     &lt;enumeration value=&quot;alert&quot;/&gt;
 *     &lt;enumeration value=&quot;error&quot;/&gt;
 *     &lt;enumeration value=&quot;warning&quot;/&gt;
 *     &lt;enumeration value=&quot;info&quot;/&gt;
 *     &lt;enumeration value=&quot;user&quot;/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "EventKindEnumeration")
@XmlEnum
public enum EventKindEnumeration
{

    @XmlEnumValue("alert")
    ALERT("alert"), @XmlEnumValue("error")
    ERROR("error"), @XmlEnumValue("warning")
    WARNING("warning"), @XmlEnumValue("info")
    INFO("info"), @XmlEnumValue("user")
    USER("user");
    private final String value;

    EventKindEnumeration(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static EventKindEnumeration fromValue(String v)
    {
        for (EventKindEnumeration c : EventKindEnumeration.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
