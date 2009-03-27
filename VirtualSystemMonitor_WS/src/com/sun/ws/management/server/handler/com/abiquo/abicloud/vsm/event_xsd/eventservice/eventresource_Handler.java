package com.sun.ws.management.server.handler.com.abiquo.abicloud.vsm.event_xsd.eventservice;

import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.framework.handlers.DelegatingHandler;
import com.sun.ws.management.server.HandlerContext;
import com.sun.ws.management.Management;

import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import com.abiquo.abicloud.vsm.event_xsd.eventservice.eventresource.EventresourceHandler;

/**
 * This Handler delegates to the
 * com.abiquo.abicloud.vsm.event_xsd.eventservice.eventresource.EventresourceHandler class. There is
 * typically nothing to implement in this class.
 * 
 * @GENERATED
 */
public class eventresource_Handler extends DelegatingHandler
{
    // Log for logging messages
    @SuppressWarnings("unused")
    private final Logger log;

    private static final EventresourceHandler delegate = new EventresourceHandler();

    /**
     * Handler constructor.
     */
    public eventresource_Handler()
    {
        super(delegate);
        log = Logger.getLogger(eventresource_Handler.class.getName());
    }

    /**
     * Overridden handle operation to support the custom operation name mapping to wsa:Action uri
     * for SPEC Action URIs
     */
    @Override
    public void handle(String action, String resourceURI, HandlerContext context,
        Management request, Management response) throws Exception
    {
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
        if ("http://schemas.xmlsoap.org/ws/2004/08/eventing/Unsubscribe".equals(action))
        {
            response
                .setAction("http://schemas.xmlsoap.org/ws/2004/08/eventing/UnsubscribeResponse");
            delegate.UnsubscribeOp(context, new Eventing(request), new Eventing(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/Renew".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/RenewResponse");
            delegate.RenewOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/Pull".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/PullResponse");
            delegate.PullOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/08/eventing/GetStatus".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/08/eventing/GetStatusResponse");
            delegate
                .GetSubscriptionStatusOp(context, new Eventing(request), new Eventing(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/GetStatus".equals(action))
        {
            response
                .setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/GetStatusResponse");
            delegate.GetStatusOp(context, new Enumeration(request), new Enumeration(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/enumeration/Release".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/enumeration/ReleaseResponse");
            delegate.ReleaseOp(context, new Enumeration(request), new Enumeration(response));
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
        if ("http://schemas.xmlsoap.org/ws/2004/08/eventing/Renew".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/08/eventing/RenewResponse");
            delegate.RenewSubscriptionOp(context, new Eventing(request), new Eventing(response));
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/transfer/Get".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/transfer/GetResponse");
            delegate.Get(context, request, response);
            return;
        }
        if ("http://schemas.xmlsoap.org/ws/2004/09/transfer/Put".equals(action))
        {
            response.setAction("http://schemas.xmlsoap.org/ws/2004/09/transfer/PutResponse");
            delegate.Put(context, request, response);
            return;
        }
        // be sure to call to super to ensure all operations are handled.
        super.handle(action, resourceURI, context, request, response);
    }
}
