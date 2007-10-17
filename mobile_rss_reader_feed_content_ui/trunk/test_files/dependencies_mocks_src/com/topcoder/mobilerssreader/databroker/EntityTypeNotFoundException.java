
package com.topcoder.mobilerssreader.databroker;

/**
 * This exception extends Exception class that represents that user specified type id can not be identified by this component.
 * <p>It is triggered in <span style='font-size:12.0pt;font-family: "Times New Roman";'>MobileDataBroker</span> <span style="font-size:12.0pt;font-family:Arial;">methods</span> <span style='font-size:12.0pt;font-family:"Times New Roman";'>that relates entity type id checking</span></p>
 * 
 * @poseidon-object-id [Im33a3f316m1137a8b2091mm7c52]
 */
public class EntityTypeNotFoundException extends Exception {

/**
 * <p>Constructor. Simply calls the super class constructor without parameter.</p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7bbf]
 */
    public  EntityTypeNotFoundException() {        
        // your code here
    } 

/**
 * <p>Constructor. Simply calls the super class constructor with the required parameter.</p>
 * <p>The message needs to be meaningful, so that the user will benefit from meaningful messages.</p>
 * <p></p>
 * 
 * @poseidon-object-id [I3d61be86m1137abb007dmm7b9d]
 * @param message the message describing the exception
 */
    public  EntityTypeNotFoundException(String message) {        
        // your code here
    } 
 }
