//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.25 at 04:38:05 PM CET 
//

package org.dmtf.schemas.wbem.wscim._1.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for qualifierString complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;qualifierString&quot;&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base=&quot;&lt;http://schemas.dmtf.org/wbem/wscim/1/common&gt;cimString&quot;&gt;
 *       &lt;attribute ref=&quot;{http://schemas.dmtf.org/wbem/wscim/1/common}qualifier use=&quot;required&quot;&quot;/&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "qualifierString")
@XmlSeeAlso( {QualifierSArray.class})
public class QualifierString extends CimString
{

    @XmlAttribute(namespace = "http://schemas.dmtf.org/wbem/wscim/1/common", required = true)
    protected boolean qualifier;

    /**
     * Gets the value of the qualifier property.
     */
    public boolean isQualifier()
    {
        return qualifier;
    }

    /**
     * Sets the value of the qualifier property.
     */
    public void setQualifier(boolean value)
    {
        this.qualifier = value;
    }

}
