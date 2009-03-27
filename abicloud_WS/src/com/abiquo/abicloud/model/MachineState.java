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
package com.abiquo.abicloud.model;

import javax.xml.namespace.QName;

/**
 * The Enum MachineState.
 */
public enum MachineState
{

    /** The POWER OFF state. */
    POWER_OFF("PowerOff"),
    /** The POWER UP state. */
    POWER_UP("PowerUp"),
    /** The PAUSE state. */
    PAUSE("Pause"),
    /** The RESUME state. */
    RESUME("Resume"),
    /** The UNDEPLOY state */
    UNDEPLOY("Undeploy");

    /** The value. */
    private final String value;

    /** The Constant machineStateQname. */
    public final static QName machineStateQname = new QName("machineStateAction");

    /**
     * Instantiates a new machine state.
     * 
     * @param value the value
     */
    MachineState(String value)
    {
        this.value = value;
    }

    /**
     * Gets the value.
     * 
     * @return the string
     */
    public String value()
    {
        return value;
    }

    /**
     * Gets the MachineState representation from a string
     * 
     * @param value the value
     * @return the machine state
     */
    public static MachineState fromValue(String value)
    {
        for (MachineState c : MachineState.values())
        {
            if (c.value.equals(value))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
