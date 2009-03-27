package com.abiquo.abicloud.vsm.exception;

/**
 * Plugin Manager specific exception.
 */
public class PluginException extends Exception
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3785279080652273241L;

    /**
     * Instantiates a new plugin exception.
     */
    public PluginException()
    {
        super();
    }

    /**
     * Instantiates a new plugin exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public PluginException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Instantiates a new plugin exception.
     * 
     * @param message the message
     */
    public PluginException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new plugin exception.
     * 
     * @param cause the cause
     */
    public PluginException(Throwable cause)
    {
        super(cause);
    }
}
