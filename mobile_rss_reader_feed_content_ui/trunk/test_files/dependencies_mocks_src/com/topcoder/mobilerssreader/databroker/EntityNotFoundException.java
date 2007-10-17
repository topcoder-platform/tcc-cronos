
package com.topcoder.mobilerssreader.databroker;

/**
 * This exception extends Exception that represents no entity found according to some search parameters.
 * <p>It is triggered in MobileDataBroker methods that relate associations, when association meets with entity that doesn't exit.</p>
 * 
 * @poseidon-object-id [Im24c05902m1138bc1c825mm673f]
 */
public class EntityNotFoundException extends Exception {

/**
 * <p>Constructor. Simply calls the super class constructor without parameter.</p>
 * 
 * @poseidon-object-id [Im24c05902m1138bc1c825mm671d]
 */
    public  EntityNotFoundException() {        
        // your code here
    } 

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p>
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im24c05902m1138bc1c825mm66fb]
 * @param namespace the message describing the exception
 */
    public  EntityNotFoundException(String namespace) {        
        // your code here
    } 
 }
