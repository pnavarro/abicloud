package com.abiquo.appliancemanager.exceptions;

/**
 * When try to add an XML id already present on the document section. (for fileId, diskId ...)
 */
public class IdAlreadyExists extends Exception
{
    public IdAlreadyExists(String message)
    {
        super(message);
    }

    public IdAlreadyExists(String message, Throwable cause)
    {
        super(message, cause);
    }

}
