
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This exception is thrown when adding an entry to a Project, and the entry has already
 * been found to be associated with the project.
 * </p>
 * 
 * 
 * @poseidon-object-id [I715b59d7m1110a37de3fmm3571]
 */
public class DuplicateEntityException extends com.topcoder.timetracker.project.DataAccessException {

/**
 * <p>
 * This is the id of the entry that has been found to have a duplicate.
 * </p>
 * 
 * @poseidon-object-id [I715b59d7m1110a37de3fmm3559]
 */
    private long id;

/**
 * <p>
 * Constructor with an entry id.  A default message will be provided.
 * </p>
 * 
 * @poseidon-object-id [I715b59d7m1110a37de3fmm3548]
 * @param id the id of the entry that has been found to have a duplicate.
 */
    public  DuplicateEntityException(long id) {    
        super("Error");
        this.id = id;
    } 

/**
 * <p>
 * Constructor with the entry id and custom message.
 * </p>
 * 
 * @poseidon-object-id [I715b59d7m1110a37de3fmm3530]
 * @param id the id of the entry that has been found to have a duplicate.
 * @param msg A custom message to use.
 */
    public  DuplicateEntityException(long id, String msg) {
        super(msg);
        this.id = id;
    } 

/**
 * <p>
 * Retrieves the id of the entry that has been found to have a duplicate.
 * </p>
 * 
 * 
 * @poseidon-object-id [I715b59d7m1110a37de3fmm3518]
 * @return the id of the entry that has been found to have a duplicate.
 */
    public long getId() {        
        // your code here
        return id;
    } 
 }
