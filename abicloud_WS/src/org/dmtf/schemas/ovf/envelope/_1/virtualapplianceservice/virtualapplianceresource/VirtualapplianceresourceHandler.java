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
package org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice.virtualapplianceresource;

import org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice.virtualapplianceresource.impl.VirtualapplianceresourceHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.Management;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.framework.handlers.ResourceHandler;
import com.sun.ws.management.server.EnumerationSupport;
import com.sun.ws.management.server.HandlerContext;

/**
 * VirtualapplianceresourceHandler delegate is responsible for processing enumeration actions.
 * 
 * @GENERATED
 */
public class VirtualapplianceresourceHandler extends ResourceHandler
{

    public static final String resourceURI =
        "http://schemas.dmtf.org/ovf/envelope/1/virtualApplianceService/virtualApplianceResource";

    // Log for logging messages
    @SuppressWarnings("unused")
    private final static Logger logger =
        LoggerFactory.getLogger(VirtualapplianceresourceHandler.class);

    public VirtualapplianceresourceHandler()
    {
        super();

        try
        {
            // Register the IteratorFactory with EnumerationSupport
            EnumerationSupport
                .registerIteratorFactory(
                    "http://schemas.dmtf.org/ovf/envelope/1/virtualApplianceService/virtualApplianceResource",
                    new VirtualapplianceresourceIteratorFactory("http://schemas.dmtf.org/ovf/envelope/1/virtualApplianceService/virtualApplianceResource"));
        }
        catch (Exception e)
        {
            throw new InternalErrorFault(e);
        }
    }

    public void Get(HandlerContext context, Management request, Management response)
    {
        VirtualapplianceresourceHandlerImpl.get(request, response);
    }

    public void RenewSubscriptionOp(HandlerContext context, Eventing request, Eventing response)
    {
        // TODO: For subscribe:
        // Call EventSupport.subscribe() & save the UUID returned (use
        // ContextListener to detect subscribe/unsubscribes)
        // Start sending events to EventSupport.sendEvent(uuid, event)

        // TODO: For unsubscribe:
        // Call EventSupport.unsubscribe()
        // Stop sending events for this UUID
        super.renewSubscription(context, request, response);
    }

    public void ReleaseOp(HandlerContext context, Enumeration request, Enumeration response)
    {
        super.release(context, request, response);
    }

    public void Delete(HandlerContext context, Management request, Management response)
    {
        VirtualapplianceresourceHandlerImpl.delete(request, response);
    }

    public void SubscribeOp(HandlerContext context, Eventing request, Eventing response)
    {
        // TODO: For subscribe:
        // Call EventSupport.subscribe() & save the UUID returned (use
        // ContextListener to detect subscribe/unsubscribes)
        // Start sending events to EventSupport.sendEvent(uuid, event)

        // TODO: For unsubscribe:
        // Call EventSupport.unsubscribe()
        // Stop sending events for this UUID
        super.subscribe(context, request, response);
    }

    public void EnumerateOp(HandlerContext context, Enumeration request, Enumeration response)
    {
        super.enumerate(context, request, response);
    }

    public void Create(HandlerContext context, Management request, Management response)
    {
        // super.create(context, request, response);
        VirtualapplianceresourceHandlerImpl.create(request, response);
    }

    public void UnsubscribeOp(HandlerContext context, Eventing request, Eventing response)
    {
        // TODO: For subscribe:
        // Call EventSupport.subscribe() & save the UUID returned (use
        // ContextListener to detect subscribe/unsubscribes)
        // Start sending events to EventSupport.sendEvent(uuid, event)

        // TODO: For unsubscribe:
        // Call EventSupport.unsubscribe()
        // Stop sending events for this UUID
        super.unsubscribe(context, request, response);
    }

    public void Put(HandlerContext context, Management request, Management response)
    {
        VirtualapplianceresourceHandlerImpl.put(request, response);
        // super.put(context, request, response);
    }

    public void PullOp(HandlerContext context, Enumeration request, Enumeration response)
    {
        super.pull(context, request, response);
    }

    public void GetStatusOp(HandlerContext context, Enumeration request, Enumeration response)
    {
        super.getStatus(context, request, response);
    }

    public void RenewOp(HandlerContext context, Enumeration request, Enumeration response)
    {
        super.renew(context, request, response);
    }

    public void GetSubscriptionStatusOp(HandlerContext context, Eventing request, Eventing response)
    {
        // TODO: For subscribe:
        // Call EventSupport.subscribe() & save the UUID returned (use
        // ContextListener to detect subscribe/unsubscribes)
        // Start sending events to EventSupport.sendEvent(uuid, event)

        // TODO: For unsubscribe:
        // Call EventSupport.unsubscribe()
        // Stop sending events for this UUID
        super.getSubscriptionStatus(context, request, response);
    }
}
