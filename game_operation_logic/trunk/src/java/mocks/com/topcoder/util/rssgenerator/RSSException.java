
package com.topcoder.util.rssgenerator;

/**
 * Purpose: This class acts as the generic exception for this component and all exceptions (ignoring run-time exceptions) of this component must extend this exception. Applications need only catch this exception in case they aren't interested in dealing with specific exceptions (ignoring run-time exceptions).
 * <p>Implementation: We extend BaseException so that we are provided with the cause and message properties. We add three constructors that allow construction with no args, with a message, and with a message as well as a cause.</p>
 * <p>Thread Safety: This class is thread safe as it is has no state and its super class is also thread safe.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm4abb]
 */
public class RSSException extends com.topcoder.util.errorhandling.BaseException {

/**
 * <p>Purpose: Constructs this exception with no message or cause.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super()</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm4a5c]
 */
    public  RSSException() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message.</p>
 * <p>Args: message - The error message. Possibly null or empty.</p>
 * <p>Implementation: Simply call super(message)</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm4a1c]
 * @param message 
 */
    public  RSSException(String message) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message and cause.</p>
 * <p>Args: message - The error message. Possibly null or empty.<br/>
 *  cause - The cause of this exception. Possibly null.</p>
 * <p>Implementation: Simply call super(message, cause)</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm49dc]
 * @param message 
 * @param cause 
 */
    public  RSSException(String message, Throwable cause) {        
        // your code here
    } 
 }
