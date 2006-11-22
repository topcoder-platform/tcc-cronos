
package com.topcoder.util.rssgenerator;

/**
 * Purpose: This exception is thrown when an error occurs during the persistence of RSS feeds or items by the data store.
 * <p>Implementation: It extends the RSSException to allow for an eror message and inner exception wrapping. It defines constructors with signatures similar to those of its super class.</p>
 * <p>Thread Safety: This class is thread safe as it has no state and its super class is also thread safe.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm5006]
 */
public class RSSPersistenceException extends com.topcoder.util.rssgenerator.RSSException {

/**
 * <p>Purpose: Constructs this exception with no message or cause.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super()</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm4fc8]
 */
    public  RSSPersistenceException() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message.</p>
 * <p>Args: message - The error message. Possibly null or empty.</p>
 * <p>Implementation: Simply call super(message)</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm4f9c]
 * @param message 
 */
    public  RSSPersistenceException(String message) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message and cause.</p>
 * <p>Args: message - The error message. Possibly null or empty.<br/>
 *  cause - The cause of this exception. Possibly null.</p>
 * <p>Implementation: Simply call super(message, cause)</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im24d61a5dm10e29ade829mm4f70]
 * @param message 
 * @param cause 
 */
    public  RSSPersistenceException(String message, Throwable cause) {        
        // your code here
    } 
 }
