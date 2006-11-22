
package com.topcoder.util.rssgenerator.io;

/**
 * Purpose: This exceptions is thrown by all methods of the RSSParser contract if any error occurs during the parsing.
 * <p>Implementation: It extends RSSException, which is the geeneric exception of the component. It adds constructors for easy construction of the exception on the lines of its super class.</p>
 * <p>Thread Safety: This class is thread safe as it has no state and its super class is thread safe.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm5769]
 */
public class RSSParseException extends com.topcoder.util.rssgenerator.RSSException {

/**
 * <p>Purpose: Constructs this exception with no message or cause.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super()</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm5737]
 */
    public  RSSParseException() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message.</p>
 * <p>Args: message - The error message. Possibly null or empty.</p>
 * <p>Implementation: Simply call super(message)</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm5715]
 * @param message 
 */
    public  RSSParseException(String message) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with the given message and cause.</p>
 * <p>Args: message - The error message. Possibly null or empty.<br/>
 *  cause - The cause of this exception. Possibly null.</p>
 * <p>Implementation: Simply call super(message, cause)</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im228eddbdm10e24912d40mm56d2]
 * @param message 
 * @param cause 
 */
    public  RSSParseException(String message, Throwable cause) {        
        // your code here
    } 
 }
