/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

/**
 * <p>
 * This is the interface definition for the DAO that is responsible handling the association and
 * de-association of an Entry identifier with a project.
 * </p>
 *
 * <p>
 * It is also capable of retrieving the entries that have been associated with a particular project.
 * </p>
 *
 * <p>
 * The DAO is also responsible for generating any identifiers (if necessary) for the associations.
 * </p>
 *
 * <p>
 * The type of Entry (<tt>Time</tt>/<tt>Expense</tt>/<tt>Fixed Billing</tt>) that is being supported
 * will depend on the type of implementation that is being used.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectEntryDAO {
    /**
     * <p>
     * Associates the specified entry with the project.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The id of the project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id has a value &lt;= 0.
     * @throws DuplicateEntityException if the entry has already been associated with the project.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addEntryToProject(Project projectId, long entryId, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This disassociates an Entry with the project.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param project The project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id has a value &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeEntryFromProject(Project project, long entryId, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Retrieves all the entries of the specified type that are associated with the specified projects.
     * </p>
     *
     * <p>
     * The return values will be stored such that the return value at position <b>i</b> will
     * correspond to the types input as position <b>i</b>.  If no entities are found at a position,
     * then the corresponding position of the return values should be an empty array.
     * </p>
     *
     * @param projectIds An array of ids of the projects for which the operation should be performed.
     * @return A 2D array representing the entities ids for each project.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains elements &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[][] retrieveEntriesForProjects(long[] projectIds) throws DataAccessException;
}