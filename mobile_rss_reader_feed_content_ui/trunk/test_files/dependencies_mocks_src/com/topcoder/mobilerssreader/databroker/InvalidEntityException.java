
package com.topcoder.mobilerssreader.databroker;

/**
 * This exception extends Exception class to represent that entity contains unexpected data or misses data.
 * <p>It is triggered in the storeEntity method of MobileDataBroker class, when entity contains unexpected data or misses data</p>
 * 
 * @poseidon-object-id [Im33a3f316m1137a8b2091mm7c6a]
 */
public class InvalidEntityException extends Exception {

/**
 * <p>Constructor. Simply calls the super class constructor without parameter.</p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7b56]
 */
    public  InvalidEntityException() {        
        // your code here
    } 

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p>
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * <p></p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7b33]
 * @param message the message describing the exception
 */
    public  InvalidEntityException(String message) {        
        // your code here
    } 
 }
