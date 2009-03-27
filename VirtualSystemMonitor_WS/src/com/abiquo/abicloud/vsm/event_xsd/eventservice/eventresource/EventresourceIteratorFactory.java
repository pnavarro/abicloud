package com.abiquo.abicloud.vsm.event_xsd.eventservice.eventresource;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;

import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

import com.abiquo.abicloud.vsm.event.ObjectFactory;

import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.UnsupportedFeatureFault;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.server.EnumerationItem;
import com.sun.ws.management.server.EnumerationIterator;
import com.sun.ws.management.server.EnumerationSupport;
import com.sun.ws.management.server.HandlerContext;
import com.sun.ws.management.server.IteratorFactory;
import com.sun.ws.management.xml.XmlBinding;

/* This enumeration factory is for the <i>Eventresource</i> resource.
 * It handles creation of an iterator for use to access 
 * the <i>Eventresource</i> resources.
 *
 * @see com.sun.ws.management.server.IteratorFactory
 *
 */
public final class EventresourceIteratorFactory implements IteratorFactory
{

    // Common variables shared by all Iterator instances
    public final String resourceURI;

    public final ObjectFactory factory;

    /**
     * Standard constructor.
     * 
     * @param the resourceURI for this Iterator Factory.
     */
    EventresourceIteratorFactory(final String resourceURI)
    {
        this.resourceURI = resourceURI;
        this.factory = new com.abiquo.abicloud.vsm.event.ObjectFactory();
    }

    /**
     * This creates iterators for the <i>Eventresource</i> resource.
     * 
     * @see com.sun.ws.management.server.IteratorFactory#newIterator(com.sun.ws.management.server.HandlerContext,
     *      com.sun.ws.management.enumeration.Enumeration, javax.xml.parsers.DocumentBuilder,
     *      boolean, boolean)
     */
    public final EnumerationIterator newIterator(HandlerContext context, Enumeration request,
        DocumentBuilder db, boolean includeItem, boolean includeEPR)
        throws UnsupportedFeatureFault, InternalErrorFault
    {
        return new EventresourceEnumerationIterator(context,
            resourceURI,
            request.getXmlBinding(),
            factory,
            request,
            db,
            includeItem,
            includeEPR);
    }

    /**
     * This method returns the resourceURI for this IteratorFactory
     * 
     * @returns returns the resourceURI for this IteratorFactory
     */
    public final String getResourceURI()
    {
        return this.resourceURI;
    }

    /* The Iterator implementation follows */

    /**
     * The class to be presented by a data source that would like to be enumerated. Implementations
     * of this class are specific to the data structure being enumerated.
     * 
     * @see com.sun.ws.management.server.EnumerationIterator
     */
    public final class EventresourceEnumerationIterator implements EnumerationIterator
    {
        // Log for logging messages
        private final Logger log;

        private boolean isFiltered = false;

        private final String address;

        private final String resourceURI;

        private final XmlBinding binding;

        private final ObjectFactory factory;

        private final DocumentBuilder db;

        private final boolean includeItem;

        private final boolean includeEPR;

        /**
         * Constructor used by the EventresourceIteratorFactory
         */
        protected EventresourceEnumerationIterator(final HandlerContext context,
            final String resourceURI, final XmlBinding binding, final ObjectFactory factory,
            final Enumeration request, final DocumentBuilder db, final boolean includeItem,
            final boolean includeEPR)
        {

            // TODO: Add the following if EPRs are not supported
            /*
             * if (includeEPR == true) { throw new
             * UnsupportedFeatureFault(UnsupportedFeatureFault.Detail.ENUMERATION_MODE); }
             */

            this.log = Logger.getLogger(EventresourceEnumerationIterator.class.getName());

            // TODO: Authorize user to access this resource
            // Check user info in HandlerContext

            this.address = context.getURL();
            this.resourceURI = resourceURI;
            // TODO: Add a 'binding.properties' file to the root of the classes directory
            // in the warfile with the following entry for binding to work.
            //
            // com.sun.ws.management.xml.custom.packagenames = com.abiquo.abicloud.vsm.event
            //
            // Validation may be enabled with the following property:
            //
            // com.sun.ws.management.xml.validate = true
            //
            // Include the xsd file in /xsd of the warfile if XML validation is enabled
            //
            this.binding = binding;
            this.factory = factory;
            this.db = db;
            this.includeItem = includeItem;
            this.includeEPR = includeEPR;

            // TODO: Extract any additional items needed from the 'request'

            // TODO: Initialize access to resource,
            // e.g. execute query against DB and open cursor
        }

        /**
         * Estimate the total number of elements available.
         * 
         * @return an estimate of the total number of elements available in the enumeration. Return
         *         a negative number if an estimate is not available.
         */
        public final int estimateTotalItems()
        {
            // TODO: Add code here to return an estimate count
            return -1;
        }

        /**
         * Indicates if the iterator has already been filtered. This indicates that further
         * filtering is not required by the framwork.
         * 
         * @return {@code true} if the iterator has already been filtered, {@code false} otherwise.
         */
        public final boolean isFiltered()
        {
            return isFiltered;
        }

        /**
         * Indicates if there are more elements remaining in the iteration.
         * 
         * @return {@code true} if there are more elements in the iteration, {@code false}
         *         otherwise.
         */
        public final boolean hasNext()
        {
            // TODO: Add code here to signify more resource are available
            return false;
        }

        /**
         * Supply the next element of the iteration. This is invoked to satisfy a
         * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull} request. The operation
         * must return within the
         * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull#getMaxTime timeout} specified in
         * the {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull} request, otherwise
         * {@link #release release} will be invoked and the current thread interrupted. When
         * cancelled, the implementation can return the result currently accumulated (in which case
         * no {@link com.sun.ws.management.soap.Fault Fault} is generated) or it can return {@code
         * null} in which case a {@link com.sun.ws.management.enumeration.TimedOutFault
         * TimedOutFault} is returned.
         * 
         * @return an {@link EnumerationElement Element} that is used to construct a proper response
         *         for a {@link org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse
         *         PullResponse}.
         */
        public final EnumerationItem next()
        {
            if (hasNext() == false)
            {
                throw new NoSuchElementException();
            }

            // TODO: Create the data value object
            // final <ResourceType> value = new <ResourceType>();

            // TODO: Call the setter methods to fill in the value object
            // with the values for the next element.

            // TODO: Create the JAXBElement from the value object
            final JAXBElement item = null;
            EndpointReferenceType epr = null;

            if (this.includeEPR)
            {
                // Create a map to hold the selectors
                final Map<String, String> selectorMap = new HashMap<String, String>();

                // TODO: Assign the key values to the selectorMap

                // Create the EPR
                epr =
                    EnumerationSupport.createEndpointReference(this.address, this.resourceURI,
                        selectorMap);
            }
            // Create and fill in the return object
            final EnumerationItem ee;

            // Always return item if filtering is done by EnumerationSupport
            if ((this.includeItem) || (this.isFiltered == false))
            {
                ee = new EnumerationItem(item, epr);
            }
            else
            {
                ee = new EnumerationItem(null, epr);
            }

            return ee;
        }

        /**
         * Release any resources being used by the iterator. Calls to other methods of this iterator
         * instance will exhibit undefined behaviour, after this method completes.
         */
        public final void release()
        {
            // TODO: Release any resources here, e.g. close the DB connection.
        }
    }
}
