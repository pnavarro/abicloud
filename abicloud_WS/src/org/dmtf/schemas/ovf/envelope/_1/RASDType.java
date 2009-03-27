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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.dmtf.schemas.wbem.wscim._1.cim_schema._2.cim_resourceallocationsettingdata.CIMResourceAllocationSettingDataType;

/**
 * Wrapper for CIM_ResourceAllocationSettingData_Type
 * <p>
 * Java class for RASD_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;RASD_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ResourceAllocationSettingData}CIM_ResourceAllocationSettingData_Type&quot;&gt;
 *       &lt;attribute name=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}boolean&quot; default=&quot;true&quot; /&gt;
 *       &lt;attribute name=&quot;configuration&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *       &lt;attribute name=&quot;bound&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RASD_Type")
public class RASDType extends CIMResourceAllocationSettingDataType
{

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected Boolean required;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String configuration;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected String bound;

    /**
     * Gets the value of the required property.
     * 
     * @return possible object is {@link Boolean }
     */
    public boolean isRequired()
    {
        if (required == null)
        {
            return true;
        }
        else
        {
            return required;
        }
    }

    /**
     * Sets the value of the required property.
     * 
     * @param value allowed object is {@link Boolean }
     */
    public void setRequired(Boolean value)
    {
        this.required = value;
    }

    /**
     * Gets the value of the configuration property.
     * 
     * @return possible object is {@link String }
     */
    public String getConfiguration()
    {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setConfiguration(String value)
    {
        this.configuration = value;
    }

    /**
     * Gets the value of the bound property.
     * 
     * @return possible object is {@link String }
     */
    public String getBound()
    {
        return bound;
    }

    /**
     * Sets the value of the bound property.
     * 
     * @param value allowed object is {@link String }
     */
    public void setBound(String value)
    {
        this.bound = value;
    }

}
