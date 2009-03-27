package com.abiquo.abicloud.vsm.exception;

/**
 * Plugin Manager specific exception.
 */
public class MonitorException extends Exception
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3785279080652273241L;

    /**
     * Instantiates a new monitor exception.
     */
    public MonitorException()
    {
        super();
    }

    /**
     * Instantiates a new monitor exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public MonitorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Instantiates a new monitor exception.
     * 
     * @param message the message
     */
    public MonitorException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new monitor exception.
     * 
     * @param cause the cause
     */
    public MonitorException(Throwable cause)
    {
        super(cause);
    }
}
