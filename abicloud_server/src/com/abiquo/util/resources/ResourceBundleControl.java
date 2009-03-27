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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * @author akpor
 */
public class ResourceBundleControl extends ResourceBundle.Control
{

    private static final Logger logger = Logger.getLogger(ResourceBundleControl.class);

    public ResourceBundleControl()
    {
        super();
    }

    @Override
    public List<String> getFormats(String baseName)
    {

        List<String> list = super.getFormats(baseName);

        ArrayList<String> arr = new ArrayList<String>();

        for (String str : list)
            arr.add(str);

        arr.add("xml");

        return arr;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format,
        ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException,
        IOException
    {

        // Get the bundleName, if locale.toString.length () > 0 bundleName = baseName_locale (e.g
        // baseName_es_Es) if not bundleName = baseName
        String bundleName = this.toBundleName(baseName, locale);

        logger.debug("BaseName:" + baseName + " locale:" + locale + " format:" + format
            + " bundleName: " + bundleName);

        if (format.equalsIgnoreCase("java.class"))
        {
            try
            {
                return (ResourceBundle) ResourceConstants.CLASS_LOADER.loadClass(bundleName)
                    .newInstance();
            }
            catch (Exception e)
            {
                return null;
            }

        }
        else if (format.equalsIgnoreCase("java.properties"))
        {

            String fileName =
                ResourceConstants.RESOURCES_LOCALE_TXT_ROOT_DIR + bundleName + ".properties";

            InputStream inStream = loader.getResourceAsStream(fileName);

            if (inStream != null)
                return new PropertyResourceBundle(inStream);
            else
                return null;

        }
        else if (format.equalsIgnoreCase("xml"))
        {

            String fileName = ResourceConstants.RESOURCES_LOCALE_XML_ROOT_DIR + bundleName + ".xml";

            return new XMLResourceBundle(loader.getResourceAsStream(fileName));

        }
        else
        {
            return null;
        }

    }

}
