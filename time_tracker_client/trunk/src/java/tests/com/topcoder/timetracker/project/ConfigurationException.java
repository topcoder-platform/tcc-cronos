
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This exception is thrown when a problem occurs while configuring
 * one of the xxxDelegate classes.
 * </p>
 * 
 * @poseidon-object-id [Im7643cd17m1115bfb081emme41]
 */
public class ConfigurationException extends com.topcoder.timetracker.project.DataAccessException {

/**
 * <p>
 * Constructor accepting a message.
 * </p>
 * 
 * @poseidon-object-id [Im7643cd17m1115bfb081emme43]
 * @param message The message of the exception.
 */
    public  ConfigurationException(String message) {        
        super(message);
    } 

/**
 * <p>
 * Constructor accepting a message and cause.
 * </p>
 * 
 * @poseidon-object-id [Im7643cd17m1115bfb081emme49]
 * @param message The message of the exception.
 * @param cause The cause of the exception.
 */
    public  ConfigurationException(String message, Throwable cause) {        
        super(message, cause);
    } 
 }
