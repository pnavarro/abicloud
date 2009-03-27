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

package com.abiquo.util.resources;

import com.abiquo.util.ToString;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author aodachi
 */
public class XMLResourceBundle extends ResourceBundle
{

    private Properties properties;

    private Enumeration<String> keys;

    public XMLResourceBundle(InputStream inputStream) throws IOException
    {

        this.properties = new Properties();
        this.properties.loadFromXML(inputStream);

        this.keys = (Enumeration<String>) this.properties.propertyNames();
    }

    @Override
    public Object handleGetObject(String key)
    {
        return this.properties.getProperty(key);
    }

    @Override
    public Enumeration<String> getKeys()
    {
        return this.keys;
    }

    @Override
    public String toString()
    {
        return ToString.toString(this);
    }

}
