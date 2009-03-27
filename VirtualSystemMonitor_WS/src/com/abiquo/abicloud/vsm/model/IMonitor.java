package com.abiquo.abicloud.vsm.model;

import java.net.URL;
import java.util.UUID;

import org.dmtf.schemas.wbem.wsman._1.wsman.EventType;

import com.abiquo.abicloud.vsm.event.EventTypeEnumeration;

/**
 * This interface defines the event monitor methods
 * 
 * @author abiquo
 */
public interface IMonitor extends Runnable
{
    /**
     * Subscribes the event to the event monitor
     * 
     * @param hypervisorAddress the hypervisor address to monitor
     * @param virtualSystemID the virtual system identifier
     * @param eventType the event type
     * @param notifyTo the event sink to notify to
     */
    public void suscribe(URL hypervisorAddress, UUID virtualSystemID,
        EventTypeEnumeration eventType, URL notifyTo);

    /**
     * Unsubscribes the event
     * 
     * @param hypervisorAddress the hypervisor address
     * @param virtualSystemID the virtual system identifier
     * @param eventType the event type
     */
    public void unSuscribe(URL hypervisorAddress, UUID virtualSystemID,
        EventTypeEnumeration eventType);

    /**
     * Raises the event
     */
    public void raiseEvent();

    /**
     * Its used to load the hypervisor type in the plugin manager
     */
    public String getMonitorType();
}
