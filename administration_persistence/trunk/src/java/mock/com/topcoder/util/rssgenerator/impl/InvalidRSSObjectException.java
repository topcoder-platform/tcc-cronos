
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This exceptions is thrown by various methods of classes of this package and its sub-packages when decoration fails due to the underlying RSSObject containing invalid properties. The conditions for it being thrown are discussed in the CS. <p>Implementation: It extends BaseRuntimeException, as it is a runtime exception. It adds constructors for easy construction of the exception on the lines of its super class.</p> <p>Thread Safety: This class is thread safe as it has no state and its super class is thread safe.</p>
 * 
 */
public class InvalidRSSObjectException extends com.topcoder.util.errorhandling.BaseRuntimeException {

/**
 * <p>Purpose: Constructs this exception with no message or cause.</p> <p>Args: None.</p> <p>Implementation: Simply call super()</p> <p>Exceptions: None.</p>
 * 
 */
    public  InvalidRSSObjectException() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message.</p> <p>Args: message - The error message. Possibly null or empty.</p> <p>Implementation: Simply call super(message)</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param message 
 */
    public  InvalidRSSObjectException(String message) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message and cause.</p> <p>Args: message - The error message. Possibly null or empty.<br/> cause - The cause of this exception. Possibly null.</p> <p>Implementation: Simply call super(message, cause)</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param message 
 * @param cause 
 */
    public  InvalidRSSObjectException(String message, Throwable cause) {        
        // your code here
    } 
 }
