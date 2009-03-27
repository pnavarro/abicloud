package com.abiquo.appliancemanager.exceptions;

public class DownloadException extends RepositoryException
{

    public DownloadException(String message)
    {
        super(message);
    }

    public DownloadException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
