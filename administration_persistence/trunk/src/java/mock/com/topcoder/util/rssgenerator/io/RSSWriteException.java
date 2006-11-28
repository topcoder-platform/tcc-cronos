
package com.topcoder.util.rssgenerator.io;

/**
 * Purpose: This exceptions is thrown by all methods of the RSSWriter contract if any error occurs during the writing. <p>Implementation: It extends RSSException, which is the geeneric exception of the component. It adds constructors for easy construction of the exception on the lines of its super class.</p> <p>Thread Safety: This class is thread safe as it has no state and its super class is thread safe.</p>
 * 
 */
public class RSSWriteException extends com.topcoder.util.rssgenerator.RSSException {

/**
 * <p>Purpose: Constructs this exception with no message or cause.</p> <p>Args: None.</p> <p>Implementation: Simply call super()</p> <p>Exceptions: None.</p>
 * 
 */
    public  RSSWriteException() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message.</p> <p>Args: message - The error message. Possibly null or empty.</p> <p>Implementation: Simply call super(message)</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param message 
 */
    public  RSSWriteException(String message) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message and cause.</p> <p>Args: message - The error message. Possibly null or empty.<br/> cause - The cause of this exception. Possibly null.</p> <p>Implementation: Simply call super(message, cause)</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param message 
 * @param cause 
 */
    public  RSSWriteException(String message, Throwable cause) {        
        // your code here
    } 
 }
