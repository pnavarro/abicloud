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
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Type for string resource bundle
 * <p>
 * Java class for Strings_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;Strings_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;Msg&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base=&quot;&lt;http://www.w3.org/2001/XMLSchema&gt;string&quot;&gt;
 *                 &lt;attribute name=&quot;msgid&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref=&quot;{http://www.w3.org/XML/1998/namespace}lang&quot;/&gt;
 *       &lt;attribute name=&quot;fileRef&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Strings_Type", propOrder = {"msg"})
public class StringsType
{

    @XmlElement(name = "Msg")
    protected List<StringsType.Msg> msg;

    @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String fileRef;

    /**
     * Gets the value of the msg property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the msg property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMsg().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link StringsType.Msg }
     */
    public List<StringsType.Msg> getMsg()
    {
        if (msg == null)
        {
            msg = new ArrayList<StringsType.Msg>();
        }
        return this.msg;
    }

    /**
     * Locale for this string resource bundle
     * 
     * @return possible object is {@link String }
     */
    public String getLang()
    {
        return lang;
    }

    /**
     * Locale for this string resource bundle
     * 
     * @param value allowed object is {@link String }
     */
    public void setLang(String value)
    {
        this.lang = value;
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
     * String element value
     * <p>
     * Java class for anonymous complex type.
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base=&quot;&lt;http://www.w3.org/2001/XMLSchema&gt;string&quot;&gt;
     *       &lt;attribute name=&quot;msgid&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"value"})
    public static class Msg
    {

        @XmlValue
        protected String value;

        @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1", required = true)
        protected String msgid;

        /**
         * Gets the value of the value property.
         * 
         * @return possible object is {@link String }
         */
        public String getValue()
        {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value allowed object is {@link String }
         */
        public void setValue(String value)
        {
            this.value = value;
        }

        /**
         * Gets the value of the msgid property.
         * 
         * @return possible object is {@link String }
         */
        public String getMsgid()
        {
            return msgid;
        }

        /**
         * Sets the value of the msgid property.
         * 
         * @param value allowed object is {@link String }
         */
        public void setMsgid(String value)
        {
            this.msgid = value;
        }

    }

}
