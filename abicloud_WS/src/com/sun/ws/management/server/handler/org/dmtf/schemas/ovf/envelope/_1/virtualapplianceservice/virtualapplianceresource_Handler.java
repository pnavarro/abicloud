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
package com.sun.ws.management.server.handler.org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice;

import org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice.virtualapplianceresource.VirtualapplianceresourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.ws.management.Management;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.framework.handlers.DelegatingHandler;
import com.sun.ws.management.server.HandlerContext;

/**
 * This Handler delegates to the org.dmtf.schemas.ovf.envelope._1.virtualapplianceservice
 * .virtualapplianceresource.VirtualapplianceresourceHandler class. There is typically nothing to
 * implement in this class.
 * 
 * @GENERATED
 */
public class virtualapplianceresource_Handler extends DelegatingHandler
{
    // Log for logging messages
    @SuppressWarnings("unused")
    private final static Logger loggger =
        LoggerFactory.getLogger(virtualapplianceresource_Handler.class);

    private static final VirtualapplianceresourceHandler delegate =
        new VirtualapplianceresourceHandler();

    /**
     * Handler constructor.
     */
    public virtualapplianceresource_Handler()
    {
        super(delegate);
    }

    /**
     * Overridden handle operation to support the custom operation name mapping to wsa:Action uri
     * for SPEC Action URIs
     */
    @Override
    public void handle(String action, String resourceURI, HandlerContext context,
        Management request, Management response) throws Exception
    {
        if ("http://schemas.xmlsoap.org/ws/2004/09/transfer/Get".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/transfer/GetResponse");
            delegate.Get(context, request, response);
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/08/eventing/Renew".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/08/eventing/RenewResponse");
            delegate.RenewSubscriptionOp(context, new Eventing(request), new Eventing(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/Release".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/ReleaseResponse");
            delegate.ReleaseOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/transfer/Delete".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/transfer/DeleteResponse");
            delegate.Delete(context, request, response);
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/08/eventing/Subscribe".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/08/eventing/SubscribeResponse");
            delegate.SubscribeOp(context, new Eventing(request), new Eventing(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/Enumerate".equals(action))
        {
            response
                .setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/EnumerateResponse");
            delegate.EnumerateOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/transfer/Create".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/transfer/CreateResponse");
            delegate.Create(context, request, response);
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/08/eventing/Unsubscribe".equals(action))
        {
            response
                .setAction("http://schemas.xmlsoap.org/ws/2004/08/eventing/UnsubscribeResponse");
            delegate.UnsubscribeOp(context, new Eventing(request), new Eventing(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/transfer/Put".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/transfer/PutResponse");
            delegate.Put(context, request, response);
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/Pull".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/PullResponse");
            delegate.PullOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/GetStatus".equals(action))
        {
            response
                .setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/GetStatusResponse");
            delegate.GetStatusOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/Renew".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/RenewResponse");
            delegate.RenewOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/08/eventing/GetStatus".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/08/eventing/GetStatusResponse");
            delegate
                .GetSubscriptionStatusOp(context, new Eventing(request), new Eventing(response));
            return;
        }
        // be sure to call to super to ensure all operations are handled.
        super.handle(action, resourceURI, context, request, response);
    }
}
