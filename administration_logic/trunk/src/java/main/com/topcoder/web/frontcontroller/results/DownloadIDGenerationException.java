
package com.topcoder.web.frontcontroller.results;

/**
 * <p>
 * This exception extends the ResultExecutionException is used to indicate problems in the generation of "id"
 * for the DownloadData to be downloaded from the DownloadSource. This exception is thrown by the 
 * DownloadIdGenerator#getDownloadIDForRequest() method, if there are problems in generating the "id"
 * for the DownloadData for the current request. The implementation of DownloadIdGenerator might fail
 * in generating an "id" may be because of some missing value for a request parameter or for any other 
 * reason depending upon the implementation.
 * </p>
 * 
 * 
 */
public class DownloadIDGenerationException extends com.topcoder.web.frontcontroller.ResultExecutionException {

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p> 
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * 
 * 
 * 
 * @param message the message describing the exception
 */
    public  DownloadIDGenerationException(String message) {        
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
    public  DownloadIDGenerationException(String message, Throwable cause) {        
        super(message,cause);
    } 
 }
