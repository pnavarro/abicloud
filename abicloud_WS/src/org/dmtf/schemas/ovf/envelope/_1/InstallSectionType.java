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

/**
 * If present indicates that the virtual machine needs to be initially booted to install and
 * configure the software
 * <p>
 * Java class for InstallSection_Type complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;InstallSection_Type&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://schemas.dmtf.org/ovf/envelope/1}Section_Type&quot;&gt;
 *       &lt;attribute name=&quot;initialBoot&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}boolean&quot; default=&quot;true&quot; /&gt;
 *       &lt;attribute name=&quot;initialBootStopDelay&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot; default=&quot;0&quot; /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstallSection_Type")
public class InstallSectionType extends SectionType
{

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected Boolean initialBoot;

    @XmlAttribute(namespace = "http://schemas.dmtf.org/ovf/envelope/1")
    protected Integer initialBootStopDelay;

    /**
     * Gets the value of the initialBoot property.
     * 
     * @return possible object is {@link Boolean }
     */
    public boolean isInitialBoot()
    {
        if (initialBoot == null)
        {
            return true;
        }
        else
        {
            return initialBoot;
        }
    }

    /**
     * Sets the value of the initialBoot property.
     * 
     * @param value allowed object is {@link Boolean }
     */
    public void setInitialBoot(Boolean value)
    {
        this.initialBoot = value;
    }

    /**
     * Gets the value of the initialBootStopDelay property.
     * 
     * @return possible object is {@link Integer }
     */
    public int getInitialBootStopDelay()
    {
        if (initialBootStopDelay == null)
        {
            return 0;
        }
        else
        {
            return initialBootStopDelay;
        }
    }

    /**
     * Sets the value of the initialBootStopDelay property.
     * 
     * @param value allowed object is {@link Integer }
     */
    public void setInitialBootStopDelay(Integer value)
    {
        this.initialBootStopDelay = value;
    }

}
