
package com.topcoder.web.frontcontroller.results;

/**
 * <p>
 * This exception extends the ResultExecutionException is used to indicate problems during the retrieval
 * of contents for the DownloadData. Basically this exception indicates that though valid metadata for the
 * DownloadData is present, there are problems during the actual retrieval of contents. The problem could be
 * anything from an IOException when retrieving the contents or the resource whose contents is requested to be
 * fetched is not found. Basically it will act as a wrapper over any exception (IOException or any other exception)
 * that could be thrown during the retrieval of contents for the DownloadData. This exception will be thrown by
 * the DownloadData#getContent() method and SimpleDownloadData#getContent() method.
 * </p>
 * 
 * 
 */
public class ContentRetrievalException extends com.topcoder.web.frontcontroller.ResultExecutionException {

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p> 
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * 
 * 
 * 
 * @param message the message describing the exception
 */
    public  ContentRetrievalException(String message) {        
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
    public  ContentRetrievalException(String message, Throwable cause) {        
        super(message, cause);
    } 
 }
