
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.Project;
/**
 * <p>
 * This is the interface definition for the DAO that is responsible handling the association and 
 * dissassociation of an Entry identifier with a project.  It is also capable of retrieving the entries
 * that have been associated with a particular project.  The DAO is also responsible for generating
 * any identifiers (if necessary) for the associations.
 * </p>
 * <p>
 * The type of Entry (Time / Expense / Fixed Billing ) that is being supported will depend 
 * on the type of implementation that is being used.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm69f7]
 */
public interface ProjectEntryDAO {
/**
 * <p>
 * Associates the specified entry with the project.  There is also the
 * option to perform an audit.  If an audit is specified, then the operation must be rolled back in 
 * the case that the audit fails.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm69bf]
 * @param projectId The id of the project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if any id has a value <= 0.
 * @throws InvalidCompanyException if the entry and project company ids do not match.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 * @throws DuplicateEntityException if the entry has already been associated with the project.
 */
    public void addEntryToProject(long projectId, long entryId, boolean audit);
/**
 * <p>
 * This disassociates an Entry with the project.  There is also the
 * option to perform an audit.  If an audit is specified, then the operation must be rolled back in 
 * the case that the audit fails.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm69a7]
 * @param projectId The id of the project for which the operation should be performed.
 * @param entryId The id of the entry for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if any id has a value <= 0.
 * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void removeEntryFromProject(long projectId, long entryId, boolean audit);
/**
 * <p>
 * Retrieves all the Entries of the specified type that are associated with the specified projects. 
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm698f]
 * @param projectIds An array of ids of the projects for which the operation should be performed.
 * @return An array of long identifiers of the Entries corresponding to the given project ids.
 * @throws IllegalArgumentException if projectIds is null or contains elements <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public long[] retrieveEntriesForProjects(long[] projectIds);
}


