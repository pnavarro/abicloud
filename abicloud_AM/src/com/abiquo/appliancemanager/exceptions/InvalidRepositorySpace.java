package com.abiquo.appliancemanager.exceptions;

/**
 * It he URI can not be reach or the ovfindex.xml is invalid.
 */
public class InvalidRepositorySpace extends Exception
{
    public InvalidRepositorySpace(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidRepositorySpace(String message)
    {
        super(message);
    }

    public InvalidRepositorySpace(Throwable cause)
    {
        super(cause);
    }

}
