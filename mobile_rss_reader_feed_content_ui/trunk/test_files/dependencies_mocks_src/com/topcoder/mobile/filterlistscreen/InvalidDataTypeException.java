
package com.topcoder.mobile.filterlistscreen;

/**
 * <p>This exception is thrown when a problem occurs while type conversion fails for a column.</p>
 * <p>For example, if the column value cannot be converted to integer value, then this exception will be thrown.</p>
 * <p>Thread Safety : This class doesn't contains attributes and its super class is thread safety so it is thread safety.</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7e4a]
 */
public class InvalidDataTypeException extends FilterListException {

/**
 * <p>Constructor accepting a message.</p>
 * <p>Implementation Note: super(message);</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b9b]
 * @param message The message of the exception.
 */
    public  InvalidDataTypeException(String message) {        
        super(message);
    } 

/**
 * <p>Constructor accepting a message and cause.</p>
 * <p>Implementation Note: super(message, cause);</p>
 * 
 * @poseidon-object-id [I6d0f7eam11394169b90mm7b92]
 * @param message The message of the exception.
 * @param cause The cause of the exception.
 */
    public  InvalidDataTypeException(String message, Throwable cause) {        
        super(message, cause);
    } 
 }
