
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This exception is thrown when there is an attempt to associate an entity
 * with a Project, and the Company IDs of the Project and the Entity
 * do not match.
 * </p>
 * <p>
 * An example would be adding an Entry (Time /Expense/
 * Fixed Billing) to a Project, where the Company of the Entry and the
 * Company of the Project do not match.
 * </p>
 * <p>
 * Usually, the expectedCompanyId would be the Project's company id,
 * and the foundCompany id would be the id of the entity being associated
 * with the Project.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm394f]
 */
public class InvalidCompanyException extends DataAccessException {

/**
 * <p>
 * The company id of the entity being associated with the project.
 * </p>
 * 
 */
    private final long foundCompanyId;

/**
 * <p>
 * The company id of the project.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm3926]
 */
    private final long expectedCompanyId;

/**
 * <p>
 * Creates an InvalidCompanyException with the specified found and
 * expectedIds.  A default message will be provided.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm38ff]
 * @param foundId The foundId of the exception.
 * @param expectedId The expectedId of the exception.
 */
    public  InvalidCompanyException(long foundId, long expectedId) {   
        super("Error");
        this.expectedCompanyId = expectedId;
        this.foundCompanyId = foundId;
    } 

/**
 * <p>
 * Creates an InvalidCompanyException with the specified found and
 * expectedIds. 
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm38e7]
 * @param foundId The foundId of the exception.
 * @param expectedId The expectedId of the exception.
 * @param msg The message of the exception.
 */
    public  InvalidCompanyException(long foundId, long expectedId, String msg) {        
        super(msg);
        this.expectedCompanyId = expectedId;
        this.foundCompanyId = foundId;
    } 

/**
 * <p>
 * Retrieves the company id of the entity being associated with the project.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm36ea]
 * @return The company id of the entity being associated with the project.
 */
    public long getFoundCompanyId() {        
        return foundCompanyId;
    } 

/**
 * <p>
 * Retrieves he company id of the project.
 * </p>
 * 
 * @poseidon-object-id [Im78dfb4fbm110c52b5486mm36d0]
 * @return The company id of the project.
 */
    public long getExpectedCompanyId() {        
        return expectedCompanyId;
    } 
 }
