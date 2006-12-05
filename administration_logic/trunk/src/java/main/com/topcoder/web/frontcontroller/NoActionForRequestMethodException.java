package com.topcoder.web.frontcontroller;

/**
 * <p>
 * This exception extends the ActionSelectionException and is thrown by the implementations of the 
 * ActionSelector implementation when it determines that there is an Action whose request URL pattern
 * matches the Request URL for the request being processed, but the Action is not allowed for the particular
 * type of Request Method. This exception is thrown to indicate there might be actions whose Request URL pattern
 * matches the current requests URL, but the Action is not valid for the current requests method of request.
 * Note that this exception will not be thrown, if no actions request URL pattern matches the request URL of the 
 * current request.
 * </p>
 * 
 * 
 */
public class NoActionForRequestMethodException extends
        com.topcoder.web.frontcontroller.ActionSelectionException {

    /**
     * <p>Constructor. Simply calls the super class constructor with the required parameter.</p> 
     * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
     * 
     * 
     * 
     * @param message the message describing the exception
     */
    public NoActionForRequestMethodException(String message) {
  super(message);
    }

    /**
     * <p>Constructor. Simply calls the super class constructor with the required parameter.</p> 
     * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p> 
     * <p>The cause is the inner exception </p>
     * 
     * 
     * 
     * @param message the message describing the exception
     * @param cause the cause of the exception
     */
    public NoActionForRequestMethodException(String message, Throwable cause) {
        super(message,cause);
    }
}
