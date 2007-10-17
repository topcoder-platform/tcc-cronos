
package com.topcoder.mobilerssreader.databroker;

/**
 * This exception extends RecordStoreException class to represent failure happens during the process of storing entity or relationship into RecordStore.
 * <p>It is triggered in the store** methods of MobileDataBroker class, when RecordStore related exception happens.</p>
 * 
 * @poseidon-object-id [Im33a3f316m1137a8b2091mm7c39]
 */
public class StoreFailedException extends javax.microedition.rms.RecordStoreException {

/**
 * <p>Constructor. Simply calls the super class constructor without parameter.</p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7c2e]
 */
    public  StoreFailedException() {        
        // your code here
    } 

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p>
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7c0b]
 * @param message the message describing the exception
 */
    public  StoreFailedException(String message) {        
        // your code here
    } 
 }
