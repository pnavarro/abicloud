package com.abiquo.appliancemanager.exceptions;

public class RepositoryException extends Exception
{

    public RepositoryException(String message)
    {
        super(message);
    }

    public RepositoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
