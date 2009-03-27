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

import com.abiquo.util.*;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * @author akpor
 *         <p>
 *         A manager for property files and resource bundles that have the same baseName Structure a
 *         of resources are as follows resources properties txt - properties files as plain text
 *         files having a .properties suffix xml - properties files as xml file locale txt -
 *         resourcebundle as properties text file xml - resourcebundle as a properties xml file For
 *         example a base name of MyResource could have the following variants of property and
 *         resource bundle files. <br>
 *         Property files <br>============================================================
 *         <ol>
 *         <li>MyResource.properties</li>
 *         <li>MyResource.[SUBSECTION].properties</li>
 *         <li>MyResource.xml</li>
 *         <li>MyResource.[SUBSECTION].xml</li>
 *         </ol>
 *         <br>
 *         Resource files <br>=============================================================
 *         <li>MyResource.properties</li>
 *         <li>MyResource.[SUBSECTION].properties (rare cases)
 *         <li>MyResource_LANGUAGE.properties</li>
 *         <li>MyResource.[SUBSECTION]_LANGUAGE.properties</li> (Rare cases)
 *         <li>MyResource_LANGUAGE_COUNTRY.properties</li>
 *         <li>MyResource.[SUBSECTION]_LANGUAGE_COUNTRY.properties</li>
 *         <li>MyResource.xml</li>
 *         <li>MyResource.[SUBSECTION].xml (rare cases)
 *         <li>MyResource_LANGUAGE.xml</li>
 *         <li>MyResource.[SUBSECTION]_LANGUAGE.xml</li> (Rare cases)
 *         <li>MyResource_LANGUAGE_COUNTRY.xml</li>
 *         <li>MyResource.[SUBSECTION]_LANGUAGE_COUNTRY.xml</li>
 * 
 *         <pre>
 * Example
 * The class com.foo.Foo should could have the following properties files (as .properties and .xml)
 * and resource bundles in the resources directory as so:
 * resources/
 *      locale/
 *          txt/
 *             com/
 *              foo/
 *                 Foo.properties
 *                 Foo_es.properties
 *      properties/
 *          txt/
 *             com/
 *              foo/
 *                 Foo.properties
 *          xml/
 *             com/
 *              foo/
 *                 Foo.xml
 * To get the resource manager for this class do the following
 * &lt;code&gt;ResourceManager resourceManager = new ResourceManager(Foo.class); &lt;/code&gt;
 * 
 * The following can hence be done:
 * 1. Retrieve the properties files that 
 *   &lt;code&gt;Properties p = resourceManager.getProperties();&lt;/code&gt;
 * 2. Retrieve the properties file in xml format
 *   &lt;code&gt;Properties p = resourceManager.getPropertiesFromXML();&lt;/code&gt;
 *    
 * 3. Retrieve the resource bundle by one of the following ways
 *   &lt;code&gt;PropertyResourceBundle bundle = (PropertyResourceBundle) BasicCommand.resourceManager.getResourceBundle();&lt;/code&gt;
 *      &lt;br&gt;
 *   &lt;code&gt;XMLResourceBundle bundle = (XMLResourceBundle) BasicCommand.resourceManager.getResourceBundle();&lt;/code&gt;
 * 
 * </pre>
 */
public class ResourceManager
{

    private static final Logger logger = Logger.getLogger(ResourceManager.class);

    private ResourceBundle.Control control;

    private ClassLoader classLoader;

    private String baseName;

    public ResourceManager()
    {
        this.classLoader = this.getClass().getClassLoader();
        this.control = new ResourceBundleControl();
    }

    // The name must be relative to the resources/SUB_DIRECTORY directory properties,xml,
    /**
     * @param baseName baseName of the property file or ResourceBundle (if it serves as one).
     * 
     *            <pre>
     *                 &lt;br&gt;The file(s) must be located somewhere in the resources/SUB_DIRECTORY where 
     *                      SUB_DIRECTORY could be 
     *                       properties - location of property or Resource bundle files stored as .properties files
     *                       xml - location of property or Resource bundle files stored as in .xml files
     *</pre>
     */
    public ResourceManager(String baseName)
    {

        this();

        // Check if someone supplied a baseName with an .xml or .properties extension, if so remove
        // the extension
        if (baseName.endsWith(".properties") || baseName.endsWith(".xml"))
            baseName = baseName.substring(0, baseName.lastIndexOf("."));

        this.baseName = baseName;
    }

    /**
     * @param cl
     */
    public ResourceManager(Class cl)
    {
        this();
        this.baseName = cl.getName().replace(".", "/");
    }

    /*
     * =================================================== Propertie Methods
     * ====================================================
     */

    /**
     * Retrieves properties from a simple .properties text file
     * 
     * @param args
     * @return a reference to a Properties object
     */
    public Properties getProperties(String... args)
    {
        return this.getProperties(args, false);
    }

    /**
     * Retrieves properties from a simple XML file
     * 
     * @param args
     * @return a reference to a Properties object
     */
    public Properties getPropertiesFromXML(String... args)
    {
        return this.getProperties(args, true);
    }

    private Properties getProperties(Object... args)
    {

        Properties p = new Properties();

        String[] strs = (String[]) args[0];
        String subSection = strs.length > 0 ? strs[0] : "";
        boolean isXML = (Boolean) args[1];

        String fileName =
            (subSection.length() > 0) ? this.baseName + "." + subSection : this.baseName;

        try
        {

            if (isXML)
                p.loadFromXML(this.classLoader
                    .getResourceAsStream(ResourceConstants.RESOURCES_PROPERTIES_XML_ROOT_DIR
                        + fileName + ".xml"));
            else
                p.load(this.classLoader
                    .getResourceAsStream(ResourceConstants.RESOURCES_PROPERTIES_TXT_ROOT_DIR
                        + fileName + ".properties"));

        }
        catch (NullPointerException e)
        {
            logger.warn(e);
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        catch (Exception e)
        {
            logger.error(e);
        }

        return p;
    }

    /*
     * =================================================== Resource Bundle methods
     * ====================================================
     */
    public ResourceBundle getResourceBundle(String... args)
    {

        String subSection = (args.length > 0) ? args[0] : "";

        String bundleBaseName = this.baseName;

        if (subSection.length() > 0)
            bundleBaseName += "." + subSection;

        return ResourceBundle.getBundle(bundleBaseName, control);
    }

    /**
     * @param key
     * @param args
     * @return
     */
    public final String getMessage(String key, String... args)
    {
        // Gets the message via a PropertyResourceBundle - another method should use
        // XMLResourceBundle
        // Need to check if it is an XMLResourceBundle
        PropertyResourceBundle bundle = (PropertyResourceBundle) this.getResourceBundle();

        String pattern = bundle.handleGetObject(key).toString();

        return MessageFormat.format(pattern, (Object[]) args);

    }

    @Override
    public String toString()
    {
        return ToString.toString(this);
    }

}
