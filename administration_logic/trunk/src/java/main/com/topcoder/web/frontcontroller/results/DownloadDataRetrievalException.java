
package com.topcoder.web.frontcontroller.results;

/**
 * <p>
 * This exception extends the ResultExecutionException is used to indicate problems fetching of the DownloadData
 * instance corresponding to the "id" specified by the DownloadSource implementation. The fetching of DownloadData
 * corresponding to the "id" specified from the DownloadSource might fail for a variety of reason, based on the 
 * implementation of DownloadSource. In short, this exception indicates the retrieval of DownloadData for the
 * requested "id" from the DownloadSource failed and might act as a wrapper exception over the actual exception
 * that might have caused this failure in the retrieval.
 * </p>
 * 
 * 
 */
public class DownloadDataRetrievalException extends com.topcoder.web.frontcontroller.ResultExecutionException {

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p> 
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * 
 * 
 * 
 * @param message the message describing the exception
 */
    public  DownloadDataRetrievalException(String message) {        
        super(message);
    } 

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p> 
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p> 
 * <p>The cause is the inner exception </p>
 * 
 * 
 * @param message the message describing the exception
 * @param cause the cause of the exception
 */
    public  DownloadDataRetrievalException(String message, Throwable cause) {        
        super(message, cause);
    } 
 }
