
package com.topcoder.mobile.filterlistscreen;

/**
 * <p>This exception is thrown when a problem occurs while this component is interacting with the filtering or sorting process.</p>
 * <p>This exception is the base exception defined for this component, other custom exceptions defined in this component are all inherited from it. This exception is defined as a runtime exception in order not to change the signature of the interfaces of this component.</p>
 * <p>Thread Safety : Class is thread safety because it is immutable.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e51]
 */
public class FilterListException extends RuntimeException {

/**
 * <p>Represents the cause of this exception.</p>
 * <p>It is set in the constructor and never changed afterward.</p>
 * <p>This can be accessed by the getter. It may be null.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7c6e]
 */
    private final Throwable cause;

/**
 * <p>Constructor accepting a message.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7c67]
 * @param message The message of the exception.
 */
    public  FilterListException(String message) {        
        cause = null;
    } 

/**
 * <p>Constructor accepting a message and cause.</p>
 * <p>Implementation Note: super(message); this.cause = cause;</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7c5e]
 * @param message The message of the exception.
 * @param cause The cause of the exception.
 */
    public  FilterListException(String message, Throwable cause) {        
       super(message);
       this.cause = cause;
    } 

/**
 * <p>Returns the cause of this exception.</p>
 * <p>May be null.</p>
 * <p>Implementation Note: return this.cause;</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7c56]
 * @return the cause of this exception.
 */
    public Throwable getCause() {        
        // your code here
        return cause;
    } 
 }
