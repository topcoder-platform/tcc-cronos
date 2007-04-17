
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This exception is thrown when interacting with the data store and an
 * entity cannot be recognized.  It may be thrown when an entity with
 * a specified identifier cannot be found.  It is thrown by all the Utility
 * and DAO interfaces (and their implementations).
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3a56]
 */
public class UnrecognizedEntityException extends com.topcoder.timetracker.project.DataAccessException {

/**
 * <p>
 * This is the id of the entity that could not be located in the persistent store.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3a3e]
 */
    private final long id;

/**
 * <p>
 * Constructor that allows an exception to be defined with the id of
 * the unrecognized entity.  A default message is provided.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3a2d]
 * @param id The id of the unrecognized entity.
 */
    public  UnrecognizedEntityException(long id) { 
        super("error");
        this.id = id;
    } 

/**
 * <p>
 * Constructor that allows an exception to be defined with the id of
 * the unrecognized entity. 
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3a15]
 * @param id The id of the unrecognized entity.
 * @param msg The message of the exception.
 */
    public  UnrecognizedEntityException(long id, String msg) {
        super(msg);
        this.id = id;
    } 

/**
 * <p>
 * Retrieves the id of the entity that could not be located in the persistent store.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm39fd]
 * @return the id of the entity that could not be located in the persistent store.
 */
    public long getId() {        
        // your code here
        return 0;
    } 
 }
