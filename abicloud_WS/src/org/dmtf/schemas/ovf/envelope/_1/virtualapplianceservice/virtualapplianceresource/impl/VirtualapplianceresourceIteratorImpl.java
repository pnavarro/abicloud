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
package org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice.virtualapplianceresource.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.dmtf.schemas.ovf.envelope._1.EnvelopeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

import com.abiquo.abicloud.model.VirtualAppliance;
import com.abiquo.abicloud.model.VirtualApplianceModel;
import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.framework.transfer.TransferSupport;
import com.sun.ws.management.server.EnumerationItem;

/**
 * Specific implementations of VirtualApplianceResourceIterator The class to be presented by a data
 * source that would like to be enumerated.
 * 
 * @see com.sun.ws.management.server.EnumerationIterator
 */
public class VirtualapplianceresourceIteratorImpl
{
    private static final String WSMAN_VIRTUALAPPLIANCE_RESOURCE =
        "http://schemas.dmtf.org/ovf/envelope/1/virtualApplianceService/virtualApplianceResource";

    private static final Logger loggger =
        LoggerFactory.getLogger(VirtualapplianceresourceIteratorImpl.class);

    private int length;

    private final String address;

    private final boolean includeEPR;

    private Iterator<VirtualAppliance> virtualAppliances;

    /**
     * Standard constructor
     * 
     * @param address the addres
     * @param includeEPR a flag to include EPR
     */
    public VirtualapplianceresourceIteratorImpl(String address, boolean includeEPR)
    {
        this.address = address;
        this.includeEPR = includeEPR;

        loggger.info("Created VirtualapplianceresourceIteratorImpl to address " + address);

        release();
    }

    /**
     * Supplys the next element of the iteration. This is invoked to satisfy a
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull} request. The operation must
     * return within the {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull#getMaxTime
     * timeout} specified in the {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull}
     * request, otherwise {@link #release release} will be invoked and the current thread
     * interrupted. When cancelled, the implementation can return the result currently accumulated
     * (in which case no {@link com.sun.ws.management.soap.Fault Fault} is generated) or it can
     * return {@code null} in which case a {@link com.sun.ws.management.enumeration.TimedOutFault
     * TimedOutFault} is returned.
     * 
     * @return an {@link EnumerationElement Element} that is used to construct a proper response for
     *         a {@link org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse PullResponse}.
     */
    public EnumerationItem next()
    {
        // Get the next VirtualAppliance
        VirtualAppliance virtualAppliance = virtualAppliances.next();
        Map<String, String> selectors = new HashMap<String, String>();

        selectors.put("id", virtualAppliance.getVirtualApplianceId());

        try
        {
            final EndpointReferenceType epr;

            if (includeEPR)
            {
                epr =
                    TransferSupport.createEpr(address, WSMAN_VIRTUALAPPLIANCE_RESOURCE, selectors);
            }
            else
            {
                epr = null;
            }

            // Create the EnumerationItem and return it
            JAXBElement<EnvelopeType> vSystemElement =
                VirtualapplianceresourceHandlerImpl.createEnvelope(virtualAppliance);
            // TODO think about filtering and including items
            /*
             * // Always return item if filtering is done by EnumerationSupport if
             * ((this.includeItem) || (this.isFiltered == false)) { ee = new EnumerationItem(item,
             * epr); } else { ee = new EnumerationItem(null, epr); }
             */
            return new EnumerationItem(vSystemElement, epr);
        }
        catch (JAXBException e)
        {
            throw new InternalErrorFault(e.getMessage());
        }
    }

    /**
     * Estimates the total number of elements available.
     * 
     * @return an estimate of the total number of elements available in the enumeration. Return a
     *         negative number if an estimate is not available.
     */
    public int estimateTotalItems()
    {
        return length;
    }

    /**
     * Indicates if there are more elements remaining in the iteration.
     * 
     * @return {@code true} if there are more elements in the iteration, {@code false} otherwise.
     */
    public boolean hasNext()
    {
        return virtualAppliances.hasNext();
    }

    /**
     * Releases any resources being used by the iterator. Calls to other methods of this iterator
     * instance will exhibit undefined behaviour, after this method completes.
     */
    public void release()
    {
        this.length = VirtualApplianceModel.getModel().getVirtualAppliances().size();
        this.virtualAppliances = VirtualApplianceModel.getModel().getVirtualAppliances().iterator();
    }
}
