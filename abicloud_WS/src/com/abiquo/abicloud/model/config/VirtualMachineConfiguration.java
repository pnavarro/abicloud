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
package com.abiquo.abicloud.model.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.namespace.QName;

import com.abiquo.abicloud.model.IHypervisor;
import com.abiquo.abicloud.model.VirtualDisk;

/**
 * The Class VirtualMachineConfiguration.
 */
public class VirtualMachineConfiguration
{

    /** The id. */
    protected final UUID id;

    /** The remote desktop port */
    protected int rdPort;

    /** The ram in bytes */
    private long ramAllocationUnits;

    /** Determines whether or not the Virtual Machine created will be deployed */
    private boolean deployVirtualMachine;

    /**
     * True if the ram is set
     */
    private boolean ram_set;

    public boolean isRam_set()
    {
        return ram_set;
    }

    public boolean isCpu_number_set()
    {
        return cpu_number_set;
    }

    /** The CPU number */
    private int cpuNumber;

    /**
     * True if the CPU number is set
     */
    private boolean cpu_number_set;

    /** The hyper. */
    protected IHypervisor hyper;

    /** The name. */
    protected String name;

    /** The virtual disk base information */
    protected VirtualDisk virtualDiskBase;

    /**
     * True if a virtual disk is added
     */

    private boolean virtual_disk_is_added;

    /**
     * The virtual disk extended list
     */
    private List<VirtualDisk> extendedVirtualDisk;

    // Remote Desktop Port
    public final static QName remoteDesktopQname = new QName("remoteDesktopPort");

    /**
     * Instantiates a new virtual machine configuration. This is the same as <code>
     *  VirtualMachineConfiguration(id,name,virtualDisk,rdPort,ramAllocationUnits,cpuNumber,true);
     * </code>
     * 
     * @param id the id
     * @param name the name
     * @param rdPort the remote desktop port
     * @param ramAllocationUnits TODO
     * @param cpuNumber TODO
     * @param hardDiskLocation the hard disk location
     */
    public VirtualMachineConfiguration(UUID id, String name, VirtualDisk virtualDisk, int rdPort,
        long ramAllocationUnits, int cpuNumber) // IHypervisor hyper,
    {
        this(id, name, virtualDisk, rdPort, ramAllocationUnits, cpuNumber, true);

    }

    /**
     * Overloaded constructor which
     * 
     * @param id
     * @param name
     * @param virtualDisk
     * @param rdPort
     * @param ramAllocationUnits
     * @param cpuNumber
     * @param deployVirtualMachine indicates if the created virtual machine should be deployed or
     *            not
     */
    public VirtualMachineConfiguration(UUID id, String name, VirtualDisk virtualDisk, int rdPort,
        long ramAllocationUnits, int cpuNumber, boolean deployVirtualMachine) // IHypervisor hyper,
    {
        this.id = id;
        this.name = name;
        this.virtualDiskBase = virtualDisk;
        this.rdPort = rdPort;
        this.ramAllocationUnits = ramAllocationUnits;
        this.cpuNumber = cpuNumber;
        this.extendedVirtualDisk = new ArrayList<VirtualDisk>();
        this.deployVirtualMachine = deployVirtualMachine;

    }

    /**
     * Gets the machine id.
     * 
     * @return the machine id
     */
    public UUID getMachineId()
    {
        return id;
    }

    /**
     * Gets the machine name.
     * 
     * @return the machine name
     */
    public String getMachineName()
    {
        return name;
    }

    /**
     * Gets the hyper.
     * 
     * @return the hyper
     */
    public IHypervisor getHyper()
    {
        return hyper;
    }

    // IS

    /**
     * Checks if is sets the hypervisor.
     * 
     * @return true, if is sets the hypervisor
     */
    public boolean isSetHypervisor()
    {
        return !(hyper == null);
    }

    // SETTERS

    /**
     * Sets the hypervisor.
     * 
     * @param hyper the new hypervisor
     */
    public void setHypervisor(IHypervisor hyper)
    {
        this.hyper = hyper;
    }

    /**
     * Sets the machine name.
     * 
     * @param name the new machine name
     */
    public void setMachineName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the virtual Disk base
     * 
     * @return the virtual disk base
     */
    public VirtualDisk getVirtualDiskBase()
    {
        return virtualDiskBase;
    }

    /**
     * Sets the virtual Disk base
     * 
     * @param virtualDiskBase the virtual Disk base
     */
    public void setVirtualDiskBase(VirtualDisk virtualDiskBase)
    {
        this.virtualDiskBase = virtualDiskBase;
    }

    /**
     * Gets the remote desktop port
     * 
     * @return the rdPort
     */
    public int getRdPort()
    {
        return rdPort;
    }

    /**
     * Sets the remote desktop port
     * 
     * @param rdPort the rdPort to set
     */
    public void setRdPort(int rdPort)
    {
        this.rdPort = rdPort;
    }

    /**
     * Sets the memory RAM allocation units
     * 
     * @param ramAllocationUnits the ramAllocationUnits to set
     */
    public void setRamAllocationUnits(long ramAllocationUnits)
    {
        this.ramAllocationUnits = ramAllocationUnits;
        this.ram_set = true;
    }

    /**
     * Gets the memory RAM allocation units
     * 
     * @return the ramAllocationUnits
     */
    public long getRamAllocationUnits()
    {
        return ramAllocationUnits;
    }

    /**
     * Sets the CPU number
     * 
     * @param cpuNumber the cpuNumber to set
     */
    public void setCpuNumber(int cpuNumber)
    {
        this.cpuNumber = cpuNumber;
        this.cpu_number_set = true;
    }

    /**
     * Gets the CPU number
     * 
     * @return the cpuNumber
     */
    public int getCpuNumber()
    {
        return cpuNumber;
    }

    /**
     * Gets the Virtual Disk extended List. To add a virtual disk, just call this method and add the
     * disk with the {@link List#add(Object)} method
     * 
     * @return
     */
    public List<VirtualDisk> getExtendedVirtualDiskList()
    {
        return extendedVirtualDisk;
    }

    /**
     * @return the deployVirtualMachine
     */
    public boolean canDeployVirtualMachine()
    {
        return deployVirtualMachine;
    }

    /**
     * @param deployVirtualMachine the deployVirtualMachine to set
     */
    public void setDeployVirtualMachine(boolean deployVirtualMachine)
    {
        this.deployVirtualMachine = deployVirtualMachine;
    }

}
