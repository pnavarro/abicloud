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
package com.abiquo.abicloud.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.abicloud.exception.VirtualMachineException;
import com.abiquo.abicloud.model.config.VirtualMachineConfiguration;

/**
 * The Class AbsVirtualMachine.
 */
public abstract class AbsVirtualMachine
{

    protected final static Logger logger = LoggerFactory.getLogger(AbsVirtualMachine.class);

    protected MachineState state;

    protected VirtualMachineConfiguration config;

    /**
     * Standard constructor
     * 
     * @param configuration the configuration file to properly instantiate a virtual machine
     */
    public AbsVirtualMachine(VirtualMachineConfiguration configuration)

    {
        config = configuration;
        state = MachineState.POWER_OFF;
    }

    /**
     * Gets the virtual machine configuration object
     * 
     * @return the virtual machine configuration
     */
    public VirtualMachineConfiguration getConfiguration()
    {
        return config;
    }

    /**
     * Gets the state.
     * 
     * @return the state
     */
    public MachineState getState()
    {
        return state;
    }

    /**
     * Sets the state
     * 
     * @param state the new state
     */
    public void setState(MachineState state)
    {
        this.state = state;
    }

    /**
     * Performs the action related to the state indicated by the argument.
     * 
     * @param state the new state
     * @throws Exception
     */
    public void applyState(MachineState newstate) throws Exception
    {
        // try
        // {
        logger.info("The present state is :" + state.value());

        if (state.equals(MachineState.POWER_OFF))
        {
            if (newstate.equals(MachineState.POWER_UP))
            {
                powerOnMachine();
                this.state = MachineState.POWER_UP;
            }
        }
        else if (state.equals(MachineState.POWER_UP))
        {
            if (newstate.equals(MachineState.POWER_OFF))
            {
                powerOffMachine();
                this.state = MachineState.POWER_OFF;
            }
            else if (newstate.equals(MachineState.PAUSE))
            {
                pauseMachine();
                this.state = MachineState.PAUSE;
            }
        }
        else if (state.equals(MachineState.PAUSE))
        {
            if (newstate.equals(MachineState.POWER_OFF))
            {
                powerOffMachine();
                this.state = MachineState.POWER_OFF;
            }
            else if (newstate.equals(MachineState.RESUME))
            {
                resumeMachine();
                this.state = MachineState.POWER_UP;
            }
        }
        else
        {
            throw new IllegalArgumentException("MachineState value " + newstate.toString());
        }
        // }
        /*
         * catch (Exception e) { throw newVirtualMachineException(
         * "An error was occurred when changing the state of the virtual machine" , e); }
         */

        logger.info("The new state is :" + state.value());

        // oSession.close();
    }

    /**
     * Deploys a virtual machine.
     * 
     * @throws Exception
     */
    protected abstract void deployMachine() throws Exception;

    /**
     * Starts the virtual machine execution
     * 
     * @throws Exception
     */
    public abstract void powerOnMachine() throws Exception;

    /**
     * Stops the virtual machine execution
     */
    public abstract void powerOffMachine() throws Exception;

    /**
     * Pauses the virtual machine execution.
     */
    public abstract void pauseMachine() throws Exception;

    /**
     * Resumes the virtual machine execution.
     */
    public abstract void resumeMachine() throws Exception;

    /**
     * Resets the virtual machine
     */
    public abstract void resetMachine() throws Exception;

    // TODO AbsVirtualMachineSnapshotable :P

    /**
     * Returns a snapshot of the machine with the given name
     */
    public abstract void takeSnapshot(String name);

    /**
     * Returns a snapshot of the machine with the given name.
     * 
     * @param name
     */
    public abstract void findSnapshot(String name);

    /**
     * Sets the current snapshot of this machine.
     */
    public abstract void setCurrentSnapshot(UUID id);

    // Events handling
    public abstract void populateEvent();

    /**
     * Deletes the machine and the attached virtual disks
     */
    public abstract void deleteMachine() throws Exception;

    /**
     * Reconfigures the virtual machine parameters
     * 
     * @param newConfiguration the new configuration parameters
     * @throws Exception
     */
    public abstract void reconfigVM(VirtualMachineConfiguration newConfiguration)
        throws VirtualMachineException;

}
