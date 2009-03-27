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
package com.abiquo.abiserver.helpers;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class VelocityHelper
{

    static Logger log = Logger.getLogger(VelocityHelper.class);

    static public void initialize(String path)
    {
        // Inicializo el Velocity
        Properties props = new Properties();
        props.setProperty("file.resource.loader.path", path);
        try
        {
            Velocity.init(props);
        }
        catch (Exception e)
        {
            log.error("", e);
        }
    }

    static public Template getTemplate(String templateFile)
    {
        Template template = null;

        try
        {
            template = Velocity.getTemplate(templateFile);
        }
        catch (ResourceNotFoundException e)
        {
            log.error("", e);
        }
        catch (ParseErrorException e)
        {
            log.error("", e);
        }
        catch (Exception e)
        {
            log.error("", e);
        }

        return template;
    }
}
