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
 * Consell de Cent 296, Principal 2�, 08007 Barcelona, Spain.
 * 
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License", 
 * available at http://cpal.abiquo.com/), in which case the 
 * provisions of CPAL License are applicable instead of those above. In relation 
 * of this portions of the Code, a Legal Notice according to Exhibits A and B of 
 * CPAL Licence should be provided in any distribution of the corresponding Code 
 * to Graphical User Interface.
 */
package com.abiquo.abiserver.abicloudws;

import javax.xml.namespace.QName;

public class AbiCloudConstants
{

    // The resource URI of the VirtualAppliance Resource
    public static final String RESOURCE_URI =
        "http://schemas.dmtf.org/ovf/envelope/1/virtualApplianceService/virtualApplianceResource";

    // machine State Qname
    public final static QName machineStateQname = new QName("machineStateAction");

    // Remote Desktop Port
    public final static QName remoteDesktopQname = new QName("remoteDesktopPort");

    // PowerUp action
    public final static String POWERUP_ACTION = "PowerUp";

    // Powerdown action
    public final static String POWERDOWN_ACTION = "PowerOff";

    // Pause action
    public final static String PAUSE_ACTION = "Pause";

    // Resume action
    public final static String RESUME_ACTION = "Resume";

    // Undeploy action
    public final static String UNDEPLOY_ACTION = "Undeploy";

    public final static String ERROR_PREFIX = "ABI-S";
}
