
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This exception is thrown when a problem occurs while this component
 * is interacting with the persistent store.  It is thrown by all the DAO and
 * Utility interfaces (and their respective implementations).
 * </p>
 * <ID="UML note. note-id=[Im78dfb4fbm110c52b5486mm363e] ">Thrown by All DAO and
 * Utility interfaces in this
 * package.
 * <BR></ID>
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3b69]
 */
public class DataAccessException extends com.topcoder.util.errorhandling.BaseException {

/**
 * <p>
 * Constructor accepting a message.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3b46]
 * @param message The message of the exception.
 */
    public  DataAccessException(String message) {        
        // your code here
    } 

/**
 * <p>
 * Constructor accepting a message and cause.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3b2e]
 * @param message The message of the exception.
 * @param cause The cause of the exception.
 */
    public  DataAccessException(String message, Throwable cause) {        
        // your code here
    } 
 }
