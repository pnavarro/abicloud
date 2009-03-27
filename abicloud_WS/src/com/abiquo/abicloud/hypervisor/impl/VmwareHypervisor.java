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
package com.abiquo.abicloud.hypervisor.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.exception.HypervisorException;
import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.machine.impl.VmwareMachine;
import com.abiquo.abicloud.model.AbiCloudModel;
import com.abiquo.abicloud.model.AbsVirtualMachine;
import com.abiquo.abicloud.model.IHypervisor;
import com.abiquo.abicloud.model.config.Configuration;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;
import com.abiquo.abicloud.model.config.VmwareHypervisorConfiguration;
import com.vmware.apputils.OptionSpec;
import com.vmware.apputils.version.ExtendedAppUtil;
import com.vmware.vim25.HostMountMode;
import com.vmware.vim25.HostNasVolumeSpec;
import com.vmware.vim25.ManagedObjectReference;

public class VmwareHypervisor implements IHypervisor
{

    /** The Constant logger. */
    private final static Logger logger = LoggerFactory.getLogger(VmwareHypervisor.class.getName());

    private HashMap<String, String> optsEntered = new HashMap<String, String>();

    private ExtendedAppUtil apputil;

    private URL url;

    /**
     * It adds parameters from the configuration file
     * 
     * @param config
     */
    private void builtinOptionsEntered(VmwareHypervisorConfiguration config)
    {
        // optsEntered.put("url", "https://192.168.1.34/sdk");
        // optsEntered.put("url", "https://abiquo.homelinux.net/sdk");
        // optsEntered.put("username", config.getUser());
        // optsEntered.put("password", config.getPassword());
        // TODO put the paramerts from the config file
        optsEntered.put("username", config.getUser());
        optsEntered.put("password", config.getPassword());
        optsEntered.put("ignorecert", config.getIgnorecert().toString());
        optsEntered.put("datastorename", config.getDatastoreSanName());
        optsEntered.put("datacentername", config.getDatacenterName());
        // if (config.getIgnorecert()) optsEntered.put("ignorecert", "true");

    }

    /**
     * It constructs the basic options needed to work
     * 
     * @return
     */
    private static OptionSpec[] constructOptions()
    {
        OptionSpec[] useroptions = new OptionSpec[8];
        useroptions[0] = new OptionSpec("vmname", "String", 1, "Name of the virtual machine", null);
        useroptions[1] =
            new OptionSpec("datacentername", "String", 1, "Name of the datacenter", null);
        useroptions[2] = new OptionSpec("hostname", "String", 0, "Name of the host", null);
        useroptions[3] =
            new OptionSpec("guestosid", "String", 0, "Type of Guest OS", "winXPProGuest");
        useroptions[4] = new OptionSpec("cpucount", "Integer", 0, "Total CPU Count", "1");
        useroptions[5] = new OptionSpec("disksize", "Integer", 0, "Size of the Disk", "64");
        useroptions[6] =
            new OptionSpec("memorysize",
                "Integer",
                0,
                "Size of the Memory in the blocks of 1024 MB",
                "1024");
        useroptions[7] =
            new OptionSpec("datastorename", "String", 0, "Name of the datastore", null);
        return useroptions;
    }

    /**
     * Gets the utility to connect the vmware VI
     * 
     * @return
     */
    public ExtendedAppUtil getAppUtil()
    {
        return apputil;
    }

    public void connect(URL url)
    {
        if (url == null)
        {
            logger.error("The url can not be null");
            throw new RuntimeException("The url to connect to the hypervisor can not be null");
        }

        try
        {
            apputil.connect();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }

    }

    public AbsVirtualMachine createMachine(VirtualMachineConfiguration config)
        throws VirtualMachineException
    {
        return new VmwareMachine(config);
    }

    public URL getAddress()
    {
        return url;
    }

    public String getHypervisorType()
    {

        return "vmx-04";
    }

    public void login(String user, String password)
    {
        optsEntered.put("username", user);
        optsEntered.put("password", password);

    }

    public void logout()
    {
        try
        {
            this.getAppUtil().disConnect();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }

    }

    public static void main(String[] args) throws MalformedURLException
    {
        VmwareHypervisor vmwareHype = new VmwareHypervisor();
        logger.info("Starting the test");
        vmwareHype.init(new URL("https://192.168.1.34/sdk"));
        // vmwareHype.init(new URL("https://abiquo.homelinux.net/sdk"));
    }

    public void init(URL url)
    {
        AbiCloudModel model = AbiCloudModel.getInstance();
        Configuration mainConfig = model.getConfigManager().getConfiguration();
        VmwareHypervisorConfiguration config = mainConfig.getVmwareHyperConfig();
        // Converting to https
        String ip = url.getHost();
        URL sdkUrl = null;
        try
        {
            sdkUrl = new URL("https://" + ip + "/sdk");
        }
        catch (MalformedURLException e1)
        {
            logger.error(e1.getMessage());
        }
        this.optsEntered.put("url", sdkUrl.toString());
        this.url = sdkUrl;
        builtinOptionsEntered(config);
        // 1. Check the vmware hypervisor infrastructure
        try
        {
            // 3. Configure the default parameters from the config file
            apputil = ExtendedAppUtil.init("VmwareHypervisor", constructOptions(), optsEntered);
            // 2. Create the NAS in a hostname
            // createNAS(null);
            // connect(this.url);
            // copyDataStorefile();
            // logout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

    /**
     * It prepares the vmware ESXi to work with abicloud infrastructure. It creates a NFS datastore
     * from the configuration file
     * 
     * @param hostName the hostname where the datastore will be created.
     * @throws Exception
     */
    private void createNAS(String hostName) throws Exception
    {
        Configuration mainConfig =
            AbiCloudModel.getInstance().getConfigManager().getConfiguration();
        VmwareHypervisorConfiguration config = mainConfig.getVmwareHyperConfig();
        connect(this.url);
        // String dcName = apputil.get_option("datacentername");
        String dcName = config.getDatacenterName();
        ManagedObjectReference dcmor =
            apputil.getServiceUtil3().getDecendentMoRef(null, "Datacenter", dcName);

        if (dcmor == null)
        {
            String message = "Datacenter " + dcName + " not found.";
            logger.error(message);
            throw new HypervisorException(message);
        }

        ManagedObjectReference hfmor = apputil.getServiceUtil3().getMoRefProp(dcmor, "hostFolder");
        ArrayList crmors = apputil.getServiceUtil3().getDecendentMoRefs(hfmor, "ComputeResource");

        ManagedObjectReference hostmor;
        if (hostName != null)
        {
            hostmor = apputil.getServiceUtil3().getDecendentMoRef(hfmor, "HostSystem", hostName);
            if (hostmor == null)
            {
                String message = "Host " + hostName + " not found";
                logger.error(message);
                throw new HypervisorException(message);
            }
        }
        else
        {
            hostmor = apputil.getServiceUtil3().getFirstDecendentMoRef(dcmor, "HostSystem");
        }

        // Attaching the NAS
        ManagedObjectReference hostDatastoreSystemmor;
        hostDatastoreSystemmor =
            (ManagedObjectReference) apputil.getServiceUtil3().getDynamicProperty(hostmor,
                "configManager.datastoreSystem");
        HostNasVolumeSpec spec = new HostNasVolumeSpec();
        spec.setRemoteHost(mainConfig.getRemoteHost());
        spec.setAccessMode(HostMountMode._readWrite);
        spec.setRemotePath(mainConfig.getRemotePath());
        spec.setLocalPath(config.getDatastoreSanName());
        apputil.getServiceConnection3().getService().createNasDatastore(hostDatastoreSystemmor,
            spec);
        // TODO Creating the VMFS default datacenter

        // Test Register VM machine
        ManagedObjectReference crmor = null;
        hostName = (String) apputil.getServiceUtil3().getDynamicProperty(hostmor, "name");
        for (int i = 0; i < crmors.size(); i++)
        {
            ManagedObjectReference[] hrmors =
                (ManagedObjectReference[]) apputil.getServiceUtil3().getDynamicProperty(
                    (ManagedObjectReference) crmors.get(i), "host");
            if (hrmors != null && hrmors.length > 0)
            {
                for (int j = 0; j < hrmors.length; j++)
                {
                    String hname =
                        (String) apputil.getServiceUtil3().getDynamicProperty(hrmors[j], "name");
                    if (hname.equalsIgnoreCase(hostName))
                    {
                        crmor = (ManagedObjectReference) crmors.get(i);
                        i = crmors.size() + 1;
                        j = hrmors.length + 1;
                    }
                }
            }
        }
        ManagedObjectReference resourcePool =
            apputil.getServiceUtil3().getMoRefProp(crmor, "resourcePool");
        ManagedObjectReference vmFolderMor =
            apputil.getServiceUtil3().getMoRefProp(dcmor, "vmFolder");

        /*
         * ManagedObjectReference taskmor = apputil.getServiceConnection3()
         * .getService().registerVM_Task(vmFolderMor, "[nfsrepository] ubuntu/ubuntu.vmx",
         * "testubuntu", false, resourcePool, hostmor); String res =
         * apputil.getServiceUtil3().waitForTask(taskmor); if (res.equalsIgnoreCase("sucess")) {
         * logger.info("Virtual Machine Created Sucessfully"); } else { String message =
         * "Virtual Machine could not be created. " + res; logger.error(message); throw new
         * VirtualMachineException(message); }
         */
        this.logout();
    }

    private void copyDataStorefile() throws Exception
    {
        Configuration mainConfig =
            AbiCloudModel.getInstance().getConfigManager().getConfiguration();
        VmwareHypervisorConfiguration config = mainConfig.getVmwareHyperConfig();
        // String dcName = apputil.get_option("datacentername");
        String dcName = config.getDatacenterName();
        ManagedObjectReference dcmor =
            apputil.getServiceUtil3().getDecendentMoRef(null, "Datacenter", dcName);
        ManagedObjectReference fileManager =
            apputil.getServiceConnection3().getServiceContent().getFileManager();
        ManagedObjectReference taskCopyMor =
            apputil.getServiceConnection3().getService().copyDatastoreFile_Task(fileManager,
                "[nfsrepository] ubuntu810desktop/ubuntu810desktop-flat.vmdk", dcmor,
                "[datastore1] test/test-flat.vmdk", dcmor, true);
        /*
         * ManagedObjectReference taskCopyMor =
         * apputil.getServiceConnection3().getService().copyDatastoreFile_Task(fileManager,
         * "[datastore1] testubuntu/testubuntu.vmdk", dcmor,
         * "[datastore1] 11b0b35e-4810-4aed-95c5-12b4dc06e80a/11b0b35e-4810-4aed-95c5-12b4dc06e80a.vmdk"
         * , dcmor, true);
         */
        // ManagedObjectReference taskCopyMor =
        // apputil.getServiceConnection3().getService().copyVirtualDisk_Task(virtualDiskManager,
        // "[nfsrepository] ubuntu/Ubuntu.8.10.Server.vmdk", dcmor,
        // "[datastore1] test/Nostalgia.vmdk", dcmor, null, true);
        String res = apputil.getServiceUtil3().waitForTask(taskCopyMor);
        if (res.equalsIgnoreCase("sucess"))
        {
            logger.info("Virtual Machine Created Sucessfully");
        }
        else
        {
            String message = "Virtual Machine could not be created. " + res;
            logger.error(message);
            throw new VirtualMachineException(message);
        }
    }
}
