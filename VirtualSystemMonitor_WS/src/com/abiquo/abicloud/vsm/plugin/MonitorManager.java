package com.abiquo.abicloud.vsm.plugin;

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

import com.abiquo.abicloud.vsm.exception.MonitorException;
import com.abiquo.abicloud.vsm.exception.PluginException;
import com.abiquo.abicloud.vsm.model.IMonitor;

/**
 * Maintain Monitors's plugins. It loads from package "com.abiquo.abicloud.vsm.plugin.impl" (by
 * default, can add more paths where look up) all the classes implementing IMonitor interface, only
 * one implementation class allowed for each MonitorType. Requires on the implementation class a
 * default empty constructor (reflection instantiation). TODO: allow URI paths / java sources
 * (resolving dependences �bcel/maven?)
 */
public class MonitorManager
{

    /** The logger object */
    private final static Logger logger = LoggerFactory.getLogger(MonitorManager.class);

    /** Enumerate all paths where look up for plugin classes */
    private List<String> pluginClassPaths;

    /**
     * All IMonitor plugin implementation classes indexed by its Monitor type
     */
    private Map<String, Class< ? extends IMonitor>> htMonitorPlugins;

    /**
     * All singleton IMonitors objects indexed by its Monitor type / address
     */
    private Map<String, Map<URL, IMonitor>> htMonitors;

    private final static String MonitorInterface = "com.abiquo.abicloud.vsm.model.IMonitor";

    private final static String MonitorPackage = "com.abiquo.abicloud.vsm.plugin.impl";

    private final static String MonitorClassPahtProperty = "java.library.path"; // "java.ext.dirs"

    private final static boolean isRefreshOnCreation = false;

    /**
     * Creates a new Plugin Manager and load all IMonitor implementations located at package
     * "com.abiquo.abicloud.vsm.plugin.impl"
     */
    public MonitorManager()
    {
        htMonitorPlugins = new Hashtable<String, Class< ? extends IMonitor>>();
        htMonitors = new Hashtable<String, Map<URL, IMonitor>>();
        pluginClassPaths = new ArrayList<String>();

        URL baseClasspath =
            MonitorManager.class.getClassLoader().getResource(
                MonitorPackage.replace('.', File.separatorChar));// classes
        pluginClassPaths.add(baseClasspath.getPath());

        if (isRefreshOnCreation)
        {
            logger.info("refresh plugins");
            refreshMonitorPlugins();
            logger.info("refresh plugins done");
        }
        else
        {
            logger.info("ONLY: adds Virtual Box Monitor plugin");

            // TODO Load the plugins monitor
            /*
             * try { //addMonitorClass(VirtualBoxMonitor.class.asSubclass(IMonitor.class), false);
             * // do not update } catch (PluginException e) {
             * logger.error("VirtualBox plugin can not be loaded :" + e.getLocalizedMessage()); }
             */

        }
    }

    /**
     * Enumerates all the available Monitors types. No multiple implementations of the same Monitor
     * type allowed.
     * 
     * @return a list containing all IMonitor.getMonitorTyper() for each IMonitor implementation.
     *         getMonitorType is the IMonitor identifier, so MUST be different for each class.
     * @see IMonitor.getMonitorType()
     */
    public String[] getMonitorTypes()
    {
        return htMonitorPlugins.keySet().toArray(new String[htMonitorPlugins.keySet().size()]);
    }

    /**
     * Returns an Monitor singleton instance by its type.
     * 
     * @param type the desired Monitor type
     * @return a singleton instance for the IMonitor implementation for given Monitor type.
     * @throws PluginException if there is not an implementation class for this Monitor type
     */
    public List<IMonitor> getAllMonitors(String type) throws MonitorException
    {
        List<IMonitor> monitor;

        if (htMonitors.containsKey(type))
        {
            monitor = new ArrayList<IMonitor>(htMonitors.get(type).values());
        }
        else
        {
            throw new MonitorException("There is any Monitor for type " + type);
        }

        return monitor;
    }

    /**
     * Gets the singleton instance . Or creates a new instance.
     */
    public IMonitor getMonitor(String type, URL address) throws PluginException
    {
        IMonitor monitor;
        Map<URL, IMonitor> monitorsByAdd;

        if (htMonitors.containsKey(type))
        {
            monitorsByAdd = htMonitors.get(type);

            if (monitorsByAdd.containsKey(address))
            {
                return htMonitors.get(type).get(address);
            }
        }
        else
        {
            monitorsByAdd = new Hashtable<URL, IMonitor>();
        }

        monitor = instantiateMonitor(type);

        // TODO to think if the monitor needs some kind of initialization

        // monitor.init(address);

        // monitor.connect(address);

        monitorsByAdd.put(address, monitor);
        htMonitors.put(type, monitorsByAdd);

        return monitor;
    }

    /**
     * Creates a new Monitor plugin.
     * 
     * @param type the desired Monitor type
     * @return new plugin instance for the given Monitor type.
     * @throws PluginException if there is not any class implementing the desired Monitor type or
     *             exist but can not no be instantiated (not default empty constructor ?)
     * @see IMonitor.getMonitorType()
     */
    protected IMonitor instantiateMonitor(String type) throws PluginException
    {
        Class< ? extends IMonitor> classmonitor;
        IMonitor monitor;

        if (htMonitorPlugins.containsKey(type))
        {
            classmonitor = htMonitorPlugins.get(type);

            try
            {
                // TODO: required default constructor
                monitor = classmonitor.newInstance();
            }
            catch (Exception e1) // InstantiationException or IllegalAccessException
            {
                // TODO: try to find the right constructor
                throw new PluginException("Failed to instantiate Monitor plugin " + "for " + type
                    + " using class " + classmonitor.getCanonicalName(), e1);
            }
        }
        else
        {
            throw new PluginException("Plugin for Monitor type " + type + " not loaded ");
            // TODO try to reload ??
        }

        return monitor;
    }

    /**
     * Cleans up the existing IMonitors plugin implementation classes and start search on all given
     * paths (pluginClassPaths) look up for new IMonitor implementations.
     */
    public void refreshMonitorPlugins()
    {
        htMonitorPlugins.clear();

        logger.info("Loading Monitors plugins from ");

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
                        loadMonitors(f.getAbsolutePath());
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
                    loadMonitors(path);
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
     * Checks if javaFilePath contain class o source of an IMonitor implementation, if so, adds to
     * existing plugin repository indexed by its getMonitorType.
     * 
     * @param javaFilePath candidate java file to implement IMonitor
     * @throws PluginException if the given class can not no be loaded or is not a java class
     *             (ClassFormatException)
     */
    private void loadMonitors(String javaFilePath) throws PluginException
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
            if (Repository.implementationOf(java_class, MonitorInterface))
            {

                Class< ? extends IMonitor> classmonitor =
                    Class.forName(java_class.getClassName()).asSubclass(IMonitor.class);

                if (!addMonitorClass(classmonitor, false)) // do not update
                {
                    throw new PluginException("Monitor Type already defined, descarted plugin implementation at class"
                        + classmonitor.getCanonicalName(),
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
     * each Monitor type, so if some class already implements a plugin before (find on previous
     * class paths) it will throw a PluginException when try to load from the new path .
     * 
     * @param path the new path where to look up for IMonitor implementations.
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
                        + System.getProperty(MonitorClassPahtProperty));
                }
                else
                {
                    pluginClassPaths.add(path);

                    // TODO Repository.getRepository().getClassPath().getClassPath()
                    System.setProperty("java.ext.dirs", System
                        .getProperty(MonitorClassPahtProperty)
                        + File.pathSeparatorChar + path);
                }

                return true;
            }
        }// not contain
    }

    /**
     * Deletes the given IMonitor class path
     * 
     * @param path the IMonitor class path to want to remove.
     * @return true if success remove an existing IMonitor class path,false if already do not exist.
     */
    public boolean removePluginClassPath(String path)
    {
        return pluginClassPaths.remove(path);
    }

    /**
     * Gets the current plugin index.
     * 
     * @return all the Monitors plugin class implementations indexed by its Monitor type.
     */
    public Map<String, Class< ? extends IMonitor>> getMonitorMap()
    {
        return htMonitorPlugins;
    }

    /**
     * Adds a new IMonitor class implementation indexed by its getMonitorType.
     * 
     * @param MonitorClass the IMonitor implementation class want to add.
     * @param update require to overwrite already defined implementation class.
     * @return true if success add the new IMonitor class, false if update is not required and some
     *         IMonitor class already defined for the same Monitor type.
     * @throws PluginException if the given class can not no be instantiated (not default empty
     *             constructor ?)
     */
    public boolean addMonitorClass(Class< ? extends IMonitor> monitorClass, boolean update)
        throws PluginException
    {
        try
        {
            String monitorType;

            monitorType = monitorClass.newInstance().getMonitorType();

            if (htMonitorPlugins.containsKey(monitorType) && !update)
            {
                return false;
            }
            else
            {
                htMonitorPlugins.put(monitorType, monitorClass);

                logger.info("Added an IMonitor implementation at "
                    + monitorClass.getCanonicalName() + "for Monitor type " + monitorType);

                return true;
            }
        }
        catch (InstantiationException e)
        {
            final String ex_msg =
                "ClassNotFoundException while loading java class file "
                    + monitorClass.getCanonicalName();
            throw new PluginException(ex_msg, e);
        }
        catch (IllegalAccessException e)
        {
            final String ex_msg =
                "ClassNotFoundException while loading java clas file "
                    + monitorClass.getCanonicalName();
            throw new PluginException(ex_msg, e);
        }
    }

    /**
     * Deletes the IMonitor class implementation for the given type.
     * 
     * @param MonitorType the IMonitor identifier want to remove.
     * @return true if success remove an existing IMonitor class, false if do not exist any IMonitor
     *         class for the given type.
     */
    public boolean removeMonitorClassFor(String MonitorType)
    {
        if (htMonitorPlugins.containsKey(MonitorType))
        {
            htMonitorPlugins.remove(MonitorType);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Tests load Monitor plugin, reload each 10 seconds.
     */
    public static void main(String args[]) throws Exception
    {
        final MonitorManager pm = new MonitorManager();

        Thread thReload = new Thread()
        {
            public void run()
            {
                pm.refreshMonitorPlugins();

                for (String sh : pm.getMonitorTypes())
                {
                    System.out.println("Monitor " + sh);
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
