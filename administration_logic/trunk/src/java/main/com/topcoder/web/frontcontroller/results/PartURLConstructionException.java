
package com.topcoder.web.frontcontroller.results;

/**
 * <p>
 * This exception extends the ResultExecutionException is used to indicate problems in the generation of the 
 * part of the url to be suffixed to the static part of the url during the generation of the URL for the ForwardResult
 * or the RedirectResult. This exception will be thrown by the PartURLConstructor#getURLSuffix() method if there
 * are problems in the creation of part of the URL for the current request. The problem in creation of the part of the 
 * URL can be for any reason depending upon the implementation can be anything from missing of required parameter values
 * in the request or for any other reason (based on the implementation).
 * </p>
 * 
 * 
 */
public class PartURLConstructionException extends com.topcoder.web.frontcontroller.ResultExecutionException {

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p> 
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * 
 * 
 * 
 * @param message the message describing the exception
 */
    public  PartURLConstructionException(String message) {        
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
    public  PartURLConstructionException(String message, Throwable cause) {        
        super(message,cause);
    } 
 }
