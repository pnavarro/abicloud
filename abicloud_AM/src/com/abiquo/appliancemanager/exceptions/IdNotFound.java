package com.abiquo.appliancemanager.exceptions;

/**
 * When try to access an XML id not present on the document section. (for fileId, diskId ...)
 */
public class IdNotFound extends Exception
{

    public IdNotFound(String message)
    {
        super(message);
    }

    public IdNotFound(String message, Throwable cause)
    {
        super(message, cause);
    }

}
