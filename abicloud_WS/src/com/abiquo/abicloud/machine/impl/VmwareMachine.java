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
package com.abiquo.abicloud.machine.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.hypervisor.impl.VmwareHypervisor;
import com.abiquo.abicloud.model.AbiCloudModel;
import com.abiquo.abicloud.model.AbsVirtualMachine;
import com.abiquo.abicloud.model.VirtualDisk;
import com.abiquo.abicloud.model.config.Configuration;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;
import com.abiquo.abicloud.model.config.VmwareHypervisorConfiguration;
import com.vmware.apputils.version.ExtendedAppUtil;
import com.vmware.apputils.vim25.VMUtils;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.InvalidPowerState;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.ResourceAllocationInfo;
import com.vmware.vim25.SharesInfo;
import com.vmware.vim25.SharesLevel;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.ToolsUnavailable;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;

/**
 * The vmware virtual machine representation
 * 
 * @author pnavarro
 */
public class VmwareMachine extends AbsVirtualMachine
{

    /**
     * The machine name
     */
    private String machineName;

    /**
     * The machine id
     */
    private UUID machineId;

    /**
     * The vmware hypervisor
     */
    private VmwareHypervisor vmwareHyper;

    // private String dcName;

    // private String hostName;

    /**
     * Apputil from the SDK
     */
    private ExtendedAppUtil apputil;

    /**
     * The remote desktop port
     */
    private int rdPort;

    /**
     * The virtual disk
     */
    private VirtualDisk virtualDisk;

    /**
     * The virtual machine managed object reference
     */
    private ManagedObjectReference _virtualMachine;

    /**
     * The virtual machine configuration
     */
    private VirtualMachineConfiguration vmConfig;

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(VmwareMachine.class);

    /**
     * This label is for extended disk detection
     */
    private static final String extended_Disk_Label = "Hard Disk 2";

    /**
     * Vmwware hypervisor configuration
     */
    private VmwareHypervisorConfiguration vmwareConfig;

    /**
     * The standard constructor
     * 
     * @param configuration the virtual machine configuration
     * @throws VirtualMachineException
     */
    public VmwareMachine(VirtualMachineConfiguration configuration) throws VirtualMachineException
    {
        super(configuration);

        if (config.getHyper() == null)
        {
            logger.error("THE HYPERVISOR IS NULL");
        }

        if (config.isSetHypervisor() & config.getHyper() instanceof VmwareHypervisor)
        {
            vmwareHyper = (VmwareHypervisor) config.getHyper();
        }
        else
        {
            throw new VirtualMachineException("Vmware machine requires a Vmware Hypervisor "
                + "on VirtualMachineConfiguration, not a "
                + config.getHyper().getClass().getCanonicalName());
        }

        String machineNameUUID = config.getMachineName();
        // The 4 last characters of the machine name are erased because are
        // omitted by the ESXi
        int machineNameLength = machineNameUUID.length();
        this.machineName = machineNameUUID.substring(0, machineNameLength - 4);
        virtualDisk = config.getVirtualDiskBase();
        machineId = config.getMachineId();
        rdPort = config.getRdPort();
        vmConfig = configuration;
        vmwareConfig =
            AbiCloudModel.getInstance().getConfigManager().getConfiguration()
                .getVmwareHyperConfig();
        apputil = vmwareHyper.getAppUtil();
        try
        {
            // Deploy the virtual machine if it's required
            if (config.canDeployVirtualMachine())
                deployMachine();
        }
        catch (Exception e)
        {
            throw new VirtualMachineException(e);
        }

        logger.info("Created vmware machine name:" + config.getMachineName() + "\t ID:"
            + config.getMachineId().toString() + "\t " + "using hypervisor connection at "
            + config.getHyper().getAddress().toString());
    }

    @Override
    public void deleteMachine() throws Exception
    {
        reconnect();
        ArrayList vmList = getVms();
        for (int i = 0; i < vmList.size(); i++)
        {
            String vmName =
                (String) apputil.getServiceUtil3().getDynamicProperty(
                    (ManagedObjectReference) vmList.get(i), "name");
            ManagedObjectReference vmMOR = (ManagedObjectReference) vmList.get(i);
            ManagedObjectReference taskmor = null;
            logger.info("Powering off virtualmachine '" + vmName + "'");
            taskmor = apputil.getServiceConnection3().getService().destroy_Task(vmMOR);

            boolean result = getTaskInfo(taskmor);
            if (result)
            {
                logger.info("" + vmName + " powered off successfuly");
            }
        }
        vmwareHyper.logout();
    }

    @Override
    protected void deployMachine() throws Exception
    {
        reconnect();
        // if (!apputil.getServiceConnection3().isConnected())
        // Create the template vdestPathirtual machine
        createVirtualMachine();
        // Copy from the NAS to the template virtual machine
        cloneVirtualDisk();
        vmwareHyper.logout();

    }

    /**
     * Private helper to clone a virtual disk from the repository one
     * 
     * @throws Exception
     */
    private void cloneVirtualDisk() throws Exception
    {
        String dcName = vmwareConfig.getDatacenterName();
        ManagedObjectReference dcmor =
            apputil.getServiceUtil3().getDecendentMoRef(null, "Datacenter", dcName);
        ManagedObjectReference fileManager =
            apputil.getServiceConnection3().getServiceContent().getFileManager();
        String virtualDiskPath = virtualDisk.getLocation();
        String fileName = null;
        String separator = File.separator;
        int pos = virtualDiskPath.lastIndexOf(separator);
        int pos2 = virtualDiskPath.lastIndexOf(".");
        if (pos2 > -1)
            fileName = virtualDiskPath.substring(pos + 1, pos2);
        else
            fileName = virtualDiskPath.substring(pos + 1);
        String sourcePath =
            "[" + vmwareConfig.getDatastoreSanName() + "] " + fileName + "/" + fileName
                + "-flat.vmdk";
        logger.info("The source datastore path is: {}", sourcePath);
        String destPath =
            "[" + vmwareConfig.getDatastoreVmfsName() + "] " + this.machineName + "/"
                + this.machineName + "-flat.vmdk";
        // logger.info("The datastore destination name is : {}",
        // vmwareConfig.getDatastoreVmfsName());
        logger.info("The destination datastore path is: {}", destPath);
        ManagedObjectReference taskCopyMor =
            apputil.getServiceConnection3().getService().copyDatastoreFile_Task(fileManager,
                sourcePath, dcmor, destPath, dcmor, true);
        String res = apputil.getServiceUtil3().waitForTask(taskCopyMor);
        if (res.equalsIgnoreCase("sucess"))
        {
            logger.info("Virtual Machine cloned Sucessfully");
        }
        else
        {
            String message = "Virtual Machine could not be cloned. " + res;
            logger.error(message);
            throw new VirtualMachineException(message);
        }

    }

    /**
     * Private helper to create a virtual machine template from the open virtualization format
     * parameters
     * 
     * @throws Exception
     */
    private void createVirtualMachine() throws Exception
    {
        VMUtils vmUtils = new VMUtils(apputil);
        String dcName = apputil.get_option("datacentername");
        ManagedObjectReference dcmor =
            apputil.getServiceUtil3().getDecendentMoRef(null, "Datacenter", dcName);

        if (dcmor == null)
        {
            String message = "Datacenter " + dcName + " not found.";
            logger.error(message);
            throw new VirtualMachineException(message);
        }

        ManagedObjectReference hfmor = apputil.getServiceUtil3().getMoRefProp(dcmor, "hostFolder");
        ArrayList crmors = apputil.getServiceUtil3().getDecendentMoRefs(hfmor, "ComputeResource");

        String hostName = apputil.get_option("hostname");
        ManagedObjectReference hostmor;
        if (hostName != null)
        {
            hostmor = apputil.getServiceUtil3().getDecendentMoRef(hfmor, "HostSystem", hostName);
            if (hostmor == null)
            {
                String message = "Host " + hostName + " not found";
                logger.error(message);
                throw new VirtualMachineException(message);
            }
        }
        else
        {
            hostmor = apputil.getServiceUtil3().getFirstDecendentMoRef(dcmor, "HostSystem");
        }

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

        if (crmor == null)
        {
            String message = "No Compute Resource Found On Specified Host";
            logger.error(message);
            throw new VirtualMachineException(message);
        }

        ManagedObjectReference resourcePool =
            apputil.getServiceUtil3().getMoRefProp(crmor, "resourcePool");
        ManagedObjectReference vmFolderMor =
            apputil.getServiceUtil3().getMoRefProp(dcmor, "vmFolder");

        // TODO #createVMConfigSpec defines not convenient default data, change
        // this
        VirtualMachineConfigSpec vmConfigSpec =
            vmUtils.createVmConfigSpec(this.machineName, vmwareConfig.getDatastoreVmfsName(),
                this.virtualDisk.getCapacity(), crmor, hostmor);
        vmConfigSpec.setName(this.machineName);
        vmConfigSpec.setAnnotation("VirtualMachine Annotation");
        vmConfigSpec.setMemoryMB(new Long(Integer.parseInt(apputil.get_option("memorysize"))));
        vmConfigSpec.setNumCPUs(Integer.parseInt(apputil.get_option("cpucount")));
        vmConfigSpec.setGuestId(apputil.get_option("guestosid"));
        OptionValue[] values = new OptionValue[2];
        values[0] = new OptionValue(null, null, "RemoteDisplay.vnc.enabled", "true");
        values[1] = new OptionValue(null, null, "RemoteDisplay.vnc.port", rdPort);
        vmConfigSpec.setExtraConfig(values);
        logger.info("Machine name :{} Machine ID: {} ready to be created", this.machineName,
            this.machineId);
        ManagedObjectReference taskmor =
            apputil.getServiceConnection3().getService().createVM_Task(vmFolderMor, vmConfigSpec,
                resourcePool, hostmor);

        String res = apputil.getServiceUtil3().waitForTask(taskmor);
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

    @Override
    public void findSnapshot(String name)
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);

    }

    @Override
    public void pauseMachine() throws Exception
    {
        reconnect();
        ArrayList vmList = getVms();
        if (vmList == null)
        {
            throw new VirtualMachineException("Error pausing the machine: tha virtual machine was not found");
        }
        for (int i = 0; i < vmList.size(); i++)
        {
            String vmName =
                (String) apputil.getServiceUtil3().getDynamicProperty(
                    (ManagedObjectReference) vmList.get(i), "name");
            ManagedObjectReference vmMOR = (ManagedObjectReference) vmList.get(i);
            ManagedObjectReference taskmor = null;
            logger.info("Suspending virtualmachine '" + vmName + "'");
            taskmor = apputil.getServiceConnection3().getService().suspendVM_Task(vmMOR);
            boolean result = getTaskInfo(taskmor);
            if (result)
            {
                logger.info("" + vmName + " suspended successfuly");
            }
            else
            {
                throw new VirtualMachineException("Error pausing the machine");
            }
        }
        vmwareHyper.logout();

    }

    @Override
    public void populateEvent()
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);

    }

    @Override
    public void powerOffMachine() throws Exception
    {
        reconnect();
        ArrayList vmList = getVms();
        if (vmList == null)
        {
            throw new VirtualMachineException("Error powering off the machine: tha virtual machine was not found");
        }
        for (int i = 0; i < vmList.size(); i++)
        {
            String vmName =
                (String) apputil.getServiceUtil3().getDynamicProperty(
                    (ManagedObjectReference) vmList.get(i), "name");
            ManagedObjectReference vmMOR = (ManagedObjectReference) vmList.get(i);
            ManagedObjectReference taskmor = null;
            logger.info("Powering off virtualmachine '" + vmName + "'");
            taskmor = apputil.getServiceConnection3().getService().powerOffVM_Task(vmMOR);

            boolean result = getTaskInfo(taskmor);
            if (result)
            {
                logger.info("" + vmName + " powered off successfuly");
            }
            else
            {
                throw new VirtualMachineException("Error powering off the machine");
            }
        }
        vmwareHyper.logout();
    }

    @Override
    public void powerOnMachine() throws Exception
    {
        reconnect();
        ArrayList vmList = getVms();
        if (vmList == null)
        {
            throw new VirtualMachineException("Error powering on the machine: tha virtual machine was not found");
        }
        for (int i = 0; i < vmList.size(); i++)
        {
            String vmName =
                (String) apputil.getServiceUtil3().getDynamicProperty(
                    (ManagedObjectReference) vmList.get(i), "name");
            ManagedObjectReference vmMOR = (ManagedObjectReference) vmList.get(i);
            ManagedObjectReference taskmor = null;
            logger.info("Powering on virtualmachine '" + vmName + "'");
            taskmor = apputil.getServiceConnection3().getService().powerOnVM_Task(vmMOR, null);

            boolean result = getTaskInfo(taskmor);
            if (result)
            {
                logger.info("" + vmName + " powered on successfuly");
            }
            else
            {
                throw new VirtualMachineException("Error powering on the machine");
            }
        }
        vmwareHyper.logout();
    }

    /**
     * Private helper to choose the virtual machine managed object reference
     * 
     * @return a list of virtual machine managed object references
     * @throws Exception
     */
    private ArrayList getVms() throws Exception
    {
        String vmname = null;
        String operation = null;
        String host = null;
        String folder = null;
        String datacenter = null;
        String pool = null;
        String guestid = null;
        String ipaddress = null;
        String[][] filter = null;

        ExtendedAppUtil cb = vmwareHyper.getAppUtil();
        ArrayList vmList = new ArrayList();
        if (cb.option_is_set("host"))
        {
            host = cb.get_option("host");
        }
        if (cb.option_is_set("folder"))
        {
            folder = cb.get_option("folder");
        }
        if (cb.option_is_set("datacenter"))
        {
            datacenter = cb.get_option("datacenter");
        }
        if (cb.option_is_set("vmname"))
        {
            vmname = cb.get_option("vmname");
        }
        if (cb.option_is_set("pool"))
        {
            pool = cb.get_option("pool");
        }
        if (cb.option_is_set("ipaddress"))
        {
            ipaddress = cb.get_option("ipaddress");
        }
        if (cb.option_is_set("guestid"))
        {
            guestid = cb.get_option("guestid");
        }
        // filter = new String[][] { new String[] { "summary.config.guestId",
        // "winXPProGuest",},};
        vmname = this.machineName;
        filter =
            new String[][] {new String[] {"guest.ipAddress", ipaddress,},
            new String[] {"summary.config.guestId", guestid,}};
        vmList = getVMs("VirtualMachine", datacenter, folder, pool, vmname, host, filter);
        return vmList;
    }

    /**
     * Private helper to get a list of virtual machine managed object references
     * 
     * @param entity
     * @param datacenter
     * @param folder
     * @param pool
     * @param vmname
     * @param host
     * @param filter
     * @return a list of virtual machine managed object references
     * @throws Exception
     */
    public ArrayList getVMs(String entity, String datacenter, String folder, String pool,
        String vmname, String host, String[][] filter) throws Exception
    {
        ManagedObjectReference dsMOR = null;
        ManagedObjectReference hostMOR = null;
        ManagedObjectReference poolMOR = null;
        ManagedObjectReference vmMOR = null;
        ManagedObjectReference folderMOR = null;
        ManagedObjectReference tempMOR = null;
        ArrayList vmList = new ArrayList();
        String[][] filterData = null;

        if (datacenter != null)
        {
            dsMOR = apputil.getServiceUtil3().getDecendentMoRef(null, "Datacenter", datacenter);
            if (dsMOR == null)
            {
                logger.info("Datacenter Not found");
                return null;
            }
            else
            {
                tempMOR = dsMOR;
            }
        }
        if (folder != null)
        {
            folderMOR = apputil.getServiceUtil3().getDecendentMoRef(tempMOR, "Folder", folder);
            if (folderMOR == null)
            {
                logger.info("Folder Not found");
                return null;
            }
            else
            {
                tempMOR = folderMOR;
            }
        }
        if (pool != null)
        {
            poolMOR = apputil.getServiceUtil3().getDecendentMoRef(tempMOR, "ResourcePool", pool);
            if (poolMOR == null)
            {
                logger.info("Resource pool Not found");
                return null;
            }
            else
            {
                tempMOR = poolMOR;
            }
        }
        if (host != null)
        {
            hostMOR = apputil.getServiceUtil3().getDecendentMoRef(tempMOR, "HostSystem", host);
            if (hostMOR == null)
            {
                logger.info("Host Not found");
                return null;
            }
            else
            {
                tempMOR = hostMOR;
            }
        }

        if (vmname != null)
        {
            int i = 0;
            filterData = new String[filter.length + 1][2];
            for (i = 0; i < filter.length; i++)
            {
                filterData[i][0] = filter[i][0];
                filterData[i][1] = filter[i][1];
            }
            // Adding the vmname in the filter
            filterData[i][0] = "name";
            filterData[i][1] = vmname;
        }
        else if (vmname == null)
        {
            int i = 0;
            filterData = new String[filter.length + 1][2];
            for (i = 0; i < filter.length; i++)
            {
                filterData[i][0] = filter[i][0];
                filterData[i][1] = filter[i][1];
            }
        }
        vmList =
            apputil.getServiceUtil3().getDecendentMoRefs(tempMOR, "VirtualMachine", filterData);
        if ((vmList == null) || (vmList.size() == 0))
        {
            logger.error("NO Virtual Machine found");
            return null;
        }
        return vmList;

    }

    @Override
    public void resetMachine() throws Exception
    {
        reconnect();
        ArrayList vmList = getVms();
        if (vmList == null)
        {
            throw new VirtualMachineException("Error reseting the machine: tha virtual machine was not found");
        }
        for (int i = 0; i < vmList.size(); i++)
        {
            String vmName =
                (String) apputil.getServiceUtil3().getDynamicProperty(
                    (ManagedObjectReference) vmList.get(i), "name");
            ManagedObjectReference vmMOR = (ManagedObjectReference) vmList.get(i);
            ManagedObjectReference taskmor = null;
            logger.info("Reseting virtualmachine '" + vmName + "'");
            taskmor = apputil.getServiceConnection3().getService().resetVM_Task(vmMOR);

            boolean result = getTaskInfo(taskmor);
            if (result)
            {
                logger.info("Virtual Machine " + vmName + " reset successfuly");
            }
            else
            {
                throw new VirtualMachineException("Error reseting the machine");
            }
        }
        vmwareHyper.logout();
    }

    @Override
    public void resumeMachine() throws Exception
    {
        reconnect();
        ArrayList vmList = getVms();
        if (vmList == null)
        {
            throw new VirtualMachineException("Error resuming the machine: tha virtual machine was not found");
        }
        for (int i = 0; i < vmList.size(); i++)
        {
            String vmName =
                (String) apputil.getServiceUtil3().getDynamicProperty(
                    (ManagedObjectReference) vmList.get(i), "name");
            ManagedObjectReference vmMOR = (ManagedObjectReference) vmList.get(i);
            logger.info("Putting the guestOs of vm '" + vmName + "' in standby mode");
            ManagedObjectReference taskmor =
                apputil.getServiceConnection3().getService().powerOnVM_Task(vmMOR, null);
            logger.info("GuestOs of vm '" + vmName + "' put in standby mode");
            boolean result = getTaskInfo(taskmor);
            if (result)
            {
                logger.info("" + vmName + " resumed successfuly done");
            }
            else
            {
                throw new VirtualMachineException("Error resuming the machine");
            }
        }
        vmwareHyper.logout();
    }

    @Override
    public void setCurrentSnapshot(UUID id)
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);

    }

    @Override
    public void takeSnapshot(String name)
    {
        String msg = "This method is not implemented for this hypervisor plugin";
        logger.error(msg);

    }

    /**
     * Private helper to get the task information
     * 
     * @param taskmor the MOR to get the info from
     * @return
     * @throws Exception
     */
    private boolean getTaskInfo(ManagedObjectReference taskmor) throws Exception
    {
        boolean valid = false;
        DynamicProperty[] scsiArry = getDynamicProarray(taskmor, "info");
        TaskInfo tinfo = ((TaskInfo) (scsiArry[0]).getVal());
        String res = apputil.getServiceUtil3().waitForTask(taskmor);
        if (res.equalsIgnoreCase("sucess"))
        {
            valid = true;
        }
        else
        {
            valid = false;
        }
        /*
         * if (tinfo.getState().getValue().equalsIgnoreCase("error")) {
         * logger.info("Error "+tinfo.getError().getLocalizedMessage()); valid = true; } else{ valid
         * = false; }
         */
        return valid;
    }

    /**
     * Private helper to get an array dinamic property
     * 
     * @param MOR the Managed Object Referented
     * @param pName the property name
     * @return the array dinamy property
     * @throws Exception
     */
    private DynamicProperty[] getDynamicProarray(ManagedObjectReference MOR, String pName)
        throws Exception
    {
        ObjectContent[] objContent =
            apputil.getServiceUtil3().getObjectProperties(null, MOR, new String[] {pName});
        ObjectContent contentObj = objContent[0];
        DynamicProperty[] objArr = contentObj.getPropSet();
        return objArr;
    }

    @Override
    public void reconfigVM(VirtualMachineConfiguration newConfiguration)
        throws VirtualMachineException
    {

        try
        {
            reconnect();
            getVmMor(this.machineName);
            VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
            // Setting the new Ram value
            logger.info("Reconfiguring The Virtual Machine For Memory Update " + this.machineName);
            if (newConfiguration.isRam_set())
                vmConfigSpec.setMemoryAllocation(getShares(String.valueOf(newConfiguration
                    .getRamAllocationUnits())));
            logger.info("Reconfiguring The Virtual Machine For CPU Update " + this.machineName);
            // Setting the number cpu value
            if (newConfiguration.isCpu_number_set())
                vmConfigSpec.setCpuAllocation(getShares(String.valueOf(newConfiguration
                    .getCpuNumber())));
            // Setting the disk cpu value
            logger.info("Reconfiguring The Virtual Machine For disk Update " + this.machineName);
            VirtualDeviceConfigSpec vdiskSpec = getDiskDeviceConfigSpec(newConfiguration);
            if (vdiskSpec != null)
            {
                VirtualDeviceConfigSpec[] vdiskSpecArray = {vdiskSpec};
                vmConfigSpec.setDeviceChange(vdiskSpecArray);
            }
            ManagedObjectReference tmor =
                apputil.getServiceConnection3().getService().reconfigVM_Task(_virtualMachine,
                    vmConfigSpec);
            monitorTask(tmor);
            // Updating configuration
            this.vmConfig = newConfiguration;
            vmwareHyper.logout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new VirtualMachineException(e);
        }
    }

    /**
     * Private helper to monitor the task launched
     * 
     * @param tmor the task managed object reference
     * @throws Exception
     */
    private void monitorTask(ManagedObjectReference tmor) throws Exception
    {
        if (tmor != null)
        {
            String result = apputil.getServiceUtil3().waitForTask(tmor);
            if (result.equalsIgnoreCase("sucess"))
            {
                logger.info("Task Completed Sucessfully");
            }
            else
            {
                logger.error("Failure " + result);
                throw new Exception("The task could not be performed");
            }
        }
    }

    /**
     * Gets the management object reference from the virtual machine name
     * 
     * @param vmName the virtual machine name
     * @throws Exception
     */
    private void getVmMor(String vmName) throws Exception
    {
        _virtualMachine =
            apputil.getServiceUtil3().getDecendentMoRef(null, "VirtualMachine", vmName);
    }

    /**
     * Gets the device config information
     * 
     * @param newConfiguration the new virtual disk
     * @return
     * @throws Exception
     */
    private VirtualDeviceConfigSpec getDiskDeviceConfigSpec(
        VirtualMachineConfiguration newConfiguration) throws Exception
    {
        // Compares the two lists and adds or remove the disks
        List<VirtualDisk> newExtendedDiskList = newConfiguration.getExtendedVirtualDiskList();
        List<VirtualDisk> oldExtendedDiskList = vmConfig.getExtendedVirtualDiskList();

        VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
        // If there are no more extended disks, I remove the existent ones
        if ((newExtendedDiskList.size() == 0) && (oldExtendedDiskList.size() == 0))
        {
            return null;
        }
        else if ((newExtendedDiskList.size() == 0) && (oldExtendedDiskList.size() > 0))
        {
            removeVirtualDiskFromConfig(oldExtendedDiskList.get(0), diskSpec);
            return diskSpec;
        }
        if ((newExtendedDiskList.size() > 0) && (oldExtendedDiskList.size() == 0))
        {
            for (VirtualDisk newVirtualDisk : newExtendedDiskList)
            {
                String location = addVirtualDiskToConfig(newVirtualDisk, diskSpec);
                newVirtualDisk.setLocation(location);
            }
            return diskSpec;

        }
        else if ((newExtendedDiskList.get(0).getCapacity() > oldExtendedDiskList.get(0)
            .getCapacity()))
        {
            long diffSize = newExtendedDiskList.get(0).getCapacity();
            String diskLocation = oldExtendedDiskList.get(0).getLocation();
            newExtendedDiskList.get(0).setLocation(diskLocation);
            logger.info("Extending to {} bytes in the disk location: {}", diffSize, diskLocation);
            // The new extended disk capacity is larger than the old one
            extendVirtualDisk(diskLocation, diffSize);
            return null;

        }
        else if ((newExtendedDiskList.get(0).getCapacity() < oldExtendedDiskList.get(0)
            .getCapacity()))
        {
            // EXPERIMENTAL !
            long diffSize = newExtendedDiskList.get(0).getCapacity();
            String diskLocation = oldExtendedDiskList.get(0).getLocation();
            newExtendedDiskList.get(0).setLocation(diskLocation);
            logger.info("Shrinking the disk location: {}", diffSize, diskLocation);
            shrinkVirtualDisk(diskLocation, diffSize);
            // reduceVirtualDisk(newExtendedDiskList.get(0),diskSpec);
            return null;
        }
        else if ((newExtendedDiskList.get(0).getCapacity() == oldExtendedDiskList.get(0)
            .getCapacity()))
        {
            return null;
        }
        // List<VirtualDisk> disksToAdd = new ArrayList<VirtualDisk>(newExtendedDiskList);
        // List<VirtualDisk> disksToDelete = new ArrayList<VirtualDisk>(oldExtendedDiskList);
        /*
         * // Adding the new virtual disk lists if ((disksToAdd.removeAll(oldExtendedDiskList)) ||
         * (oldExtendedDiskList.size() == 0)) { for (VirtualDisk newVirtualDisk : disksToAdd) {
         * addVirtualDiskToConfig(newVirtualDisk, diskSpec); } } //Removing disks if
         * (disksToDelete.removeAll(newExtendedDiskList)) { for (VirtualDisk oldVirtualDisk :
         * disksToDelete) { removeVirtualDiskFromConfig(oldVirtualDisk, diskSpec); } }
         */

        return diskSpec;
    }

    /**
     * Adds the virtual Disk to the virtual device configuration
     * 
     * @param newVirtualDisk the new virtual disk to add
     * @param diskSpec the virtual disk device configuration
     * @throws Exception
     */
    private String reduceVirtualDisk(VirtualDisk newVirtualDisk, VirtualDeviceConfigSpec diskSpec)
        throws Exception
    {
        VirtualMachineConfigInfo vmConfigInfo =
            (VirtualMachineConfigInfo) apputil.getServiceUtil3().getDynamicProperty(
                _virtualMachine, "config");
        com.vmware.vim25.VirtualDisk disk = new com.vmware.vim25.VirtualDisk();
        VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();
        String dsName = vmwareConfig.getDatastoreVmfsName();
        int ckey = 0;
        int unitNumber = 0;

        VirtualDevice[] test = vmConfigInfo.getHardware().getDevice();
        for (int k = 0; k < test.length; k++)
        {
            if (test[k].getDeviceInfo().getLabel().equalsIgnoreCase("SCSI Controller 0"))
            {
                ckey = test[k].getKey();
            }
        }

        unitNumber = test.length + 1;
        String fileName =
            "[" + dsName + "] " + this.machineName + "/" + newVirtualDisk.getId() + ".vmdk";

        diskfileBacking.setFileName(fileName);
        // Provisioning with thin provisioning
        diskfileBacking.setThinProvisioned(true);
        diskfileBacking.setDiskMode("persistent");

        disk.setControllerKey(ckey);
        disk.setUnitNumber(unitNumber);
        disk.setBacking(diskfileBacking);
        int size = (int) (newVirtualDisk.getCapacity() / 1024);
        disk.setCapacityInKB(size);
        disk.setKey(-1);

        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.edit);
        diskSpec.setDevice(disk);

        return fileName;

    }

    private void shrinkVirtualDisk(String virtualDiskLocation, long diffSize) throws Exception
    {
        ManagedObjectReference virtualDiskManager =
            apputil.getServiceConnection3().getServiceContent().getVirtualDiskManager();
        String dcName = apputil.get_option("datacentername");
        ManagedObjectReference dcmor =
            apputil.getServiceUtil3().getDecendentMoRef(null, "Datacenter", dcName);
        if (dcmor == null)
        {
            String message = "Datacenter " + dcName + " not found.";
            logger.error(message);
            throw new VirtualMachineException(message);
        }
        // TODO Defragment the disk
        ManagedObjectReference tmor =
            apputil.getServiceConnection3().getService().shrinkVirtualDisk_Task(virtualDiskManager,
                virtualDiskLocation, dcmor, false);
        monitorTask(tmor);

    }

    private void extendVirtualDisk(String virtualDiskLocation, long diffSize) throws Exception
    {
        ManagedObjectReference virtualDiskManager =
            apputil.getServiceConnection3().getServiceContent().getVirtualDiskManager();
        String dcName = apputil.get_option("datacentername");
        ManagedObjectReference dcmor =
            apputil.getServiceUtil3().getDecendentMoRef(null, "Datacenter", dcName);
        if (dcmor == null)
        {
            String message = "Datacenter " + dcName + " not found.";
            logger.error(message);
            throw new VirtualMachineException(message);
        }
        ManagedObjectReference tmor =
            apputil.getServiceConnection3().getService().extendVirtualDisk_Task(virtualDiskManager,
                virtualDiskLocation, dcmor, diffSize / 1024);
        monitorTask(tmor);
    }

    /**
     * Removes the virtual disk configuration from the virtual disk device configuration
     * 
     * @param oldVirtualDisk the virtual disk to remove
     * @param diskSpec the virtual disk configuration specifications
     * @throws Exception
     */
    private void removeVirtualDiskFromConfig(VirtualDisk oldVirtualDisk,
        VirtualDeviceConfigSpec diskSpec) throws Exception
    {
        VirtualMachineConfigInfo vmConfigInfo =
            (VirtualMachineConfigInfo) apputil.getServiceUtil3().getDynamicProperty(
                _virtualMachine, "config");
        com.vmware.vim25.VirtualDisk disk = null;
        VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();

        VirtualDevice[] test = vmConfigInfo.getHardware().getDevice();
        for (int k = 0; k < test.length; k++)
        {
            if (test[k].getDeviceInfo().getLabel().equalsIgnoreCase(extended_Disk_Label))
            {
                disk = (com.vmware.vim25.VirtualDisk) test[k];
            }
        }
        if (disk != null)
        {
            diskSpec.setOperation(VirtualDeviceConfigSpecOperation.remove);
            diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.destroy);
            diskSpec.setDevice(disk);
        }
        else
        {
            logger.error("The disk {} was not removed since it was not found", oldVirtualDisk
                .getId());
        }

    }

    /**
     * Adds the virtual Disk to the virtual device configuration
     * 
     * @param newVirtualDisk the new virtual disk to add
     * @param diskSpec the virtual disk device configuration
     * @throws Exception
     */
    private String addVirtualDiskToConfig(VirtualDisk newVirtualDisk,
        VirtualDeviceConfigSpec diskSpec) throws Exception
    {
        VirtualMachineConfigInfo vmConfigInfo =
            (VirtualMachineConfigInfo) apputil.getServiceUtil3().getDynamicProperty(
                _virtualMachine, "config");
        com.vmware.vim25.VirtualDisk disk = new com.vmware.vim25.VirtualDisk();
        VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();
        String dsName = vmwareConfig.getDatastoreVmfsName();
        int ckey = 0;
        int unitNumber = 0;

        VirtualDevice[] test = vmConfigInfo.getHardware().getDevice();
        for (int k = 0; k < test.length; k++)
        {
            if (test[k].getDeviceInfo().getLabel().equalsIgnoreCase("SCSI Controller 0"))
            {
                ckey = test[k].getKey();
            }
        }

        unitNumber = test.length + 1;
        String fileName =
            "[" + dsName + "] " + this.machineName + "/" + newVirtualDisk.getId() + ".vmdk";

        diskfileBacking.setFileName(fileName);
        // Provisioning with thin provisioning
        diskfileBacking.setThinProvisioned(true);
        diskfileBacking.setDiskMode("persistent");

        disk.setControllerKey(ckey);
        disk.setUnitNumber(unitNumber);
        disk.setBacking(diskfileBacking);
        int size = (int) (newVirtualDisk.getCapacity() / 1024);
        disk.setCapacityInKB(size);
        disk.setKey(-1);

        diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
        diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);
        diskSpec.setDevice(disk);

        return fileName;

    }

    /**
     * Gets the resource allocation information from the resource value
     * 
     * @param value the value
     * @return the resource allocation information
     * @throws Exception
     */
    private ResourceAllocationInfo getShares(String value) throws Exception
    {
        ResourceAllocationInfo raInfo = new ResourceAllocationInfo();
        SharesInfo sharesInfo = new SharesInfo();

        if (value.equalsIgnoreCase(SharesLevel._high))
        {
            sharesInfo.setLevel(SharesLevel.high);
        }
        else if (value.equalsIgnoreCase(SharesLevel._normal))
        {
            sharesInfo.setLevel(SharesLevel.normal);
        }
        else if (value.equalsIgnoreCase(SharesLevel._low))
        {
            sharesInfo.setLevel(SharesLevel.low);
        }
        else
        {
            sharesInfo.setLevel(SharesLevel.custom);
            sharesInfo.setShares(Integer.parseInt(value));
        }
        raInfo.setShares(sharesInfo);
        return raInfo;
    }

    private void reconnect()
    {
        URL address = vmwareHyper.getAddress();
        vmwareHyper.init(address);
        vmwareHyper.connect(address);
        apputil = vmwareHyper.getAppUtil();
    }

}
