package com.abiquo.appliancemanager.exceptions;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

/**
 * Any XML error, caused by JAXBException, XMLStreamException or document do not validate.
 */
public class XMLException extends Exception
{
    public XMLException(String message, XMLStreamException cause)
    {
        super(message, cause);
    }

    public XMLException(String message, JAXBException cause)
    {
        super(message, cause);
    }

    public XMLException(XMLStreamException cause)
    {
        super(cause);
    }

    public XMLException(JAXBException cause)
    {
        super(cause);
    }

}
