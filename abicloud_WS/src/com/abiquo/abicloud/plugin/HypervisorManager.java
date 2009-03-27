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
package com.abiquo.abicloud.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.exception.HypervisorException;
import com.abiquo.abicloud.exception.PluginException;
import com.abiquo.abicloud.hypervisor.impl.VirtualBoxHypervisor;
import com.abiquo.abicloud.hypervisor.impl.VmwareHypervisor;
import com.abiquo.abicloud.model.IHypervisor;

/**
 * Maintain Hypervisor's plugins. It loads from package "com.abiquo.abicloud.plugin.impl" (by
 * default, can add more paths where look up) all the classes implementing IHypervisor interface,
 * only one implementation class allowed for each HypervisorType. Requires on the implementation
 * class a default empty constructor (reflection instantiation). TODO: allow URI paths / java
 * sources (resolving dependences �bcel/maven?)
 */
public class HypervisorManager
{

    /** The logger object */
    private final static Logger logger = LoggerFactory.getLogger(HypervisorManager.class);

    /** Enumerate all paths where look up for plugin classes */
    private List<String> pluginClassPaths;

    /**
     * All IHypervisor plugin implementation classes indexed by its hypervisor type
     */
    private Map<String, Class< ? extends IHypervisor>> htHypervisorPlugins;

    /**
     * All singleton IHypervisors objects indexed by its hypervisor type / address
     */
    private Map<String, Map<URL, IHypervisor>> htHypervisors;

    private final static String hypervisorInterface = "com.abiquo.abicloud.model.IHypervisor";

    private final static String hypervisorPackage = "com.abiquo.abicloud.hypervisor.impl";

    private final static String hypervisorClassPahtProperty = "java.library.path"; // "java.ext.dirs"

    private final static boolean isRefreshOnCreation = false;

    /**
     * Creates a new Plugin Manager and load all IHypervisor implementations located at package
     * "com.abiquo.abicloud.plugin.impl"
     */
    public HypervisorManager()
    {
        htHypervisorPlugins = new Hashtable<String, Class< ? extends IHypervisor>>();
        htHypervisors = new Hashtable<String, Map<URL, IHypervisor>>();
        pluginClassPaths = new ArrayList<String>();

        URL baseClasspath =
            HypervisorManager.class.getClassLoader().getResource(
                hypervisorPackage.replace('.', File.separatorChar));// classes
        pluginClassPaths.add(baseClasspath.getPath());

        // TODO java sources
        // addPluginClassPath("/pro/abicloud/abiCloud_server/src/com/abiquo/abicloud/plugin/impl");
        if (isRefreshOnCreation)
        {
            logger.info("refresh plugins");
            refreshHypervisorPlugins();
            logger.info("refresh plugins done");
        }
        else
        {
            logger.info("ONLY: adds Virtual Box Hypervisor plugin");

            try
            {
                addHypervisorClass(VirtualBoxHypervisor.class.asSubclass(IHypervisor.class), false); // do
                                                                                                     // not
                                                                                                     // update
                addHypervisorClass(VmwareHypervisor.class.asSubclass(IHypervisor.class), false); // do
                                                                                                 // not
                                                                                                 // update
            }
            catch (PluginException e)
            {
                logger.error("VirtualBox plugin can not be loaded :" + e.getLocalizedMessage());
            }

        }
    }

    /**
     * Enumerates all the available hypervisors types. No multiple implementations of the same
     * hypervisor type allowed.
     * 
     * @return a list containing all IHypervisor.getHypervisorTyper() for each IHypervisor
     *         implementation. getHypervisorType is the IHypervisor identifier, so MUST be different
     *         for each class.
     * @see IHypervisor.getHypervisorType()
     */
    public String[] getHypervisorTypes()
    {
        return htHypervisorPlugins.keySet()
            .toArray(new String[htHypervisorPlugins.keySet().size()]);
    }

    /**
     * Returns an Hypervisor singleton instance by its type.
     * 
     * @param type the desired hypervisor type
     * @return a singleton instance for the IHypervisor implementation for given hypervisor type.
     * @throws PluginException if there is not an implementation class for this hypervisor type
     */
    public List<IHypervisor> getAllHypervisors(String type) throws HypervisorException
    {
        List<IHypervisor> hyper;

        if (htHypervisors.containsKey(type))
        {
            hyper = new ArrayList<IHypervisor>(htHypervisors.get(type).values());
        }
        else
        {
            throw new HypervisorException("There is any Hypervisor for type " + type);
        }

        return hyper;
    }

    /**
     * Gets the singleton instance . Or creates a new instance.
     */
    public IHypervisor getHypervisor(String type, URL address) throws PluginException
    {
        IHypervisor hyper;
        Map<URL, IHypervisor> hypersByAdd;

        if (htHypervisors.containsKey(type))
        {
            hypersByAdd = htHypervisors.get(type);

            if (hypersByAdd.containsKey(address))
            {
                return htHypervisors.get(type).get(address);
            }
        }
        else
        {
            hypersByAdd = new Hashtable<URL, IHypervisor>();
        }

        hyper = instantiateHypervisor(type);

        hyper.init(address);

        hyper.connect(address);

        hypersByAdd.put(address, hyper);
        htHypervisors.put(type, hypersByAdd);

        return hyper;
    }

    /**
     * Creates a new Hypervisor plugin.
     * 
     * @param type the desired hypervisor type
     * @return new plugin instance for the given hypervisor type.
     * @throws PluginException if there is not any class implementing the desired hypervisor type or
     *             exist but can not no be instantiated (not default empty constructor ?)
     * @see IHypervisor.getHypervisorType()
     */
    protected IHypervisor instantiateHypervisor(String type) throws PluginException
    {
        Class< ? extends IHypervisor> classHyper;
        IHypervisor hyper;

        if (htHypervisorPlugins.containsKey(type))
        {
            classHyper = htHypervisorPlugins.get(type);

            try
            {
                // TODO: required default constructor
                hyper = classHyper.newInstance();
            }
            catch (Exception e1) // InstantiationException or IllegalAccessException
            {
                // TODO: try to find the right constructor
                throw new PluginException("Failed to instantiate Hypervisor plugin " + "for "
                    + type + " using class " + classHyper.getCanonicalName(), e1);
            }
        }
        else
        {
            throw new PluginException("Plugin for hypervisor type " + type + " not loaded ");
            // TODO try to reload ??
        }

        return hyper;
    }

    /**
     * Cleans up the existing IHypervisors plugin implementation classes and start search on all
     * given paths (pluginClassPaths) look up for new IHypervisor implementations.
     */
    public void refreshHypervisorPlugins()
    {
        htHypervisorPlugins.clear();

        logger.info("Loading hypervisors plugins from ");

        for (String path : pluginClassPaths)
        {
            logger.info(" path :" + path);

            File fPath = new File(path);

            if (fPath.isDirectory())
            {
                // TODO: if there are subdirectories
                for (File f : fPath.listFiles())
                {
                    try
                    {
                        loadHypervisors(f.getAbsolutePath());
                    }
                    catch (PluginException e)
                    {
                        logger.error("Failed to load plugin at " + f.getAbsolutePath()
                            + "\n Caused by:" + e.getLocalizedMessage() + "\n"
                            + e.getCause().getLocalizedMessage());
                    }
                }
            }
            else
            {
                try
                {
                    loadHypervisors(path);
                }
                catch (PluginException e)
                {
                    logger.error("Failed to load plugin at " + path + "\n Caused by:"
                        + e.getLocalizedMessage() + "\n" + e.getCause().getLocalizedMessage());
                }
            }

        }// for paths
    }

    /**
     * Checks if javaFilePath contain class o source of an IHypervisor implementation, if so, adds
     * to existing plugin repository indexed by its getHypervisorType.
     * 
     * @param javaFilePath candidate java file to implement IHypervisor
     * @throws PluginException if the given class can not no be loaded or is not a java class
     *             (ClassFormatException)
     */
    private void loadHypervisors(String javaFilePath) throws PluginException
    {
        JavaClass java_class;

        if (javaFilePath.endsWith(".class"))
        {
            try
            {
                java_class = new ClassParser(javaFilePath).parse();
            }
            catch (IOException e1)
            {
                final String ex_msg =
                    "IOException while loading class file " + javaFilePath + "\n Caused by "
                        + e1.getCause().getLocalizedMessage();
                // log.severe(ex_msg);

                throw new PluginException(ex_msg, e1);
            }
            catch (ClassFormatException e2)
            {
                final String ex_msg =
                    "ClassFormatException while loading class file " + javaFilePath
                        + "\n Caused by " + e2.getCause().getLocalizedMessage();
                // log.severe(ex_msg);

                throw new PluginException(ex_msg, e2);
            }
        } // dot class
        else
        // java or jar
        {
            try
            {

                java_class = Repository.lookupClass(javaFilePath);
                // TODO Repository.getRepository().findClass(javaFilePath);
            }
            catch (ClassNotFoundException e)
            {
                final String ex_msg =
                    "ClassNotFoundException while loading java file " + javaFilePath
                        + "\n Caused by " + e.getCause().getLocalizedMessage();

                throw new PluginException(ex_msg, e);
            }
        } // dot java

        // log.fine("Try to load " + java_class.getClassName());

        try
        {
            if (Repository.implementationOf(java_class, hypervisorInterface))
            {

                Class< ? extends IHypervisor> classHyper =
                    Class.forName(java_class.getClassName()).asSubclass(IHypervisor.class);

                if (!addHypervisorClass(classHyper, false)) // do not update
                {
                    throw new PluginException("Hypervisor Type already defined, descarted plugin implementation at class"
                        + classHyper.getCanonicalName(),
                        new Throwable());
                }
            }
            // else do nothing
        }
        catch (ClassNotFoundException e) // must fail before
        {
            final String ex_msg =
                "ClassNotFoundException while loading java file " + javaFilePath + "\n Caused by "
                    + e.getCause().getLocalizedMessage();

            throw new PluginException(ex_msg, e);
        }
    }

    /**
     * @return a list containing all the class paths where plugins are look up.
     */
    public List<String> getPluginClassPaths()
    {
        return pluginClassPaths;
    }

    /***
     * Adds the given path at the end of pluginClassPaths. Remember only one instance allowed for
     * each hypervisor type, so if some class already implements a plugin before (find on previous
     * class paths) it will throw a PluginException when try to load from the new path .
     * 
     * @param path the new path where to look up for IHypervisor implementations.
     * @param isHighPriority if true, will find first on this class path, otherwise will look up on
     *            it before all others.
     * @return true if the given path are not already on the list, exist and it could be accessible,
     *         false otherwise.
     */
    public boolean addPluginClassPath(String path, boolean isHighPriority)
    {
        if (pluginClassPaths.contains(path))
        {
            return false;
        }
        else
        {
            File fPath = new File(path);
            if (!fPath.exists())
            {
                logger.error("Plugin path " + path + " do not exist");
                return false;
            }
            else if (!fPath.canRead())
            {
                logger.error("Plugin path " + path + " can not be read");

                return false;
            }
            else
            {
                logger.info("Added plugin path " + path);

                if (isHighPriority)
                {
                    pluginClassPaths.add(0, path);

                    // TODO Repository.getRepository().getClassPath().getClassPath()
                    System.setProperty("java.ext.dirs", path + File.pathSeparatorChar
                        + System.getProperty(hypervisorClassPahtProperty));
                }
                else
                {
                    pluginClassPaths.add(path);

                    // TODO Repository.getRepository().getClassPath().getClassPath()
                    System.setProperty("java.ext.dirs", System
                        .getProperty(hypervisorClassPahtProperty)
                        + File.pathSeparatorChar + path);
                }

                return true;
            }
        }// not contain
    }

    /**
     * Deletes the given IHypervisor class path
     * 
     * @param path the IHypervisor class path to want to remove.
     * @return true if success remove an existing IHypervisor class path,false if already do not
     *         exist.
     */
    public boolean removePluginClassPath(String path)
    {
        return pluginClassPaths.remove(path);
    }

    /**
     * Gets the current plugin index.
     * 
     * @return all the Hypervisors plugin class implementations indexed by its hypervisor type.
     */
    public Map<String, Class< ? extends IHypervisor>> getHypervisorMap()
    {
        return htHypervisorPlugins;
    }

    /**
     * Adds a new IHypervisor class implementation indexed by its getHypervisorType.
     * 
     * @param hypervisorClass the IHypervisor implementation class want to add.
     * @param update require to overwrite already defined implementation class.
     * @return true if success add the new IHypervisor class, false if update is not required and
     *         some IHypervisor class already defined for the same hypervisor type.
     * @throws PluginException if the given class can not no be instantiated (not default empty
     *             constructor ?)
     */
    public boolean addHypervisorClass(Class< ? extends IHypervisor> hyperClass, boolean update)
        throws PluginException
    {
        try
        {
            String hyperType;

            hyperType = hyperClass.newInstance().getHypervisorType();

            if (htHypervisorPlugins.containsKey(hyperType) && !update)
            {
                return false;
            }
            else
            {
                htHypervisorPlugins.put(hyperType, hyperClass);

                logger.info("Added an IHypervisor implementation at "
                    + hyperClass.getCanonicalName() + "for hypervisor type " + hyperType);

                return true;
            }
        }
        catch (InstantiationException e)
        {
            final String ex_msg =
                "ClassNotFoundException while loading java class file "
                    + hyperClass.getCanonicalName();
            throw new PluginException(ex_msg, e);
        }
        catch (IllegalAccessException e)
        {
            final String ex_msg =
                "ClassNotFoundException while loading java clas file "
                    + hyperClass.getCanonicalName();
            throw new PluginException(ex_msg, e);
        }
    }

    /**
     * Deletes the IHypervisor class implementation for the given type.
     * 
     * @param hypervisorType the IHypervisor identifier want to remove.
     * @return true if success remove an existing IHypervisor class, false if do not exist any
     *         IHypervisor class for the given type.
     */
    public boolean removeHypervisorClassFor(String hypervisorType)
    {
        if (htHypervisorPlugins.containsKey(hypervisorType))
        {
            htHypervisorPlugins.remove(hypervisorType);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Tests load hypervisor plugin, reload each 10 seconds.
     */
    public static void main(String args[]) throws Exception
    {
        final HypervisorManager pm = new HypervisorManager();

        Thread thReload = new Thread()
        {
            public void run()
            {
                pm.refreshHypervisorPlugins();

                for (String sh : pm.getHypervisorTypes())
                {
                    System.out.println("Hypervisor " + sh);
                }

                try
                {
                    Thread.sleep(10000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                run();
            }

        };

        thReload.start();
    }

    // ///// TODO DEPENDENCES RESOLVER :: triged if PluginException at class Instantiation
    /*
     * public static String[] getClassDependencies(JavaClass.getConstantPool() pool) { String[]
     * tempArray = new String[pool.getLength()]; int size = 0; StringBuffer buf = new
     * StringBuffer(); for(int idx = 0; idx < pool.getLength(); idx++) { Constant c =
     * pool.getConstant(idx); if(c != null && c.getTag() == Constants.CONSTANT_Class) { ConstantUtf8
     * c1 = (ConstantUtf8) pool.getConstant(((ConstantClass)c).getNameIndex()); buf.setLength(0);
     * buf.append(c1.getBytes()); for(int n = 0; n < buf.length(); n++) { if(buf.charAt(n) == '/') {
     * buf.setCharAt(n, '.'); } } tempArray[size++] = buf.toString(); } } String[] dependencies =
     * new String[size]; System.arraycopy(tempArray, 0, dependencies, 0, size); return dependencies;
     * }
     */
}
