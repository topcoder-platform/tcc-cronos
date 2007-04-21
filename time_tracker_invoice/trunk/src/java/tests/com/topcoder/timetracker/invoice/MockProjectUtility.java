/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.DuplicateEntityException;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;

/**
 * Mock for <code>ProjectUtility</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class MockProjectUtility implements ProjectUtility {

    /**
     * Mock method.
     *
     * @param projectId
     *            not used
     * @param entryId
     *            not used
     * @param type
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     * @throws DuplicateEntityException
     *             not thrown
     */
    public void addEntryToProject(long projectId, long entryId, EntryType type, boolean audit)
        throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param project
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     */
    public void addProject(Project project, boolean audit) throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param projects
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     */
    public void addProjects(Project[] projects, boolean audit) throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     */
    public Project[] enumerateProjects() throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param projectId
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public Project getProject(long projectId) throws DataAccessException {

        return null;
    }

    /**
     * Mock method.
     *
     * @return null
     */
    public ProjectFilterFactory getProjectFilterFactory() {
        return null;
    }

    /**
     * Mock method.
     *
     * @param projectsIds
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public Project[] getProjects(long[] projectsIds) throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param projectId
     *            not used
     * @param entryId
     *            not used
     * @param type
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public void removeEntryFromProject(long projectId, long entryId, EntryType type, boolean audit)
        throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param projectId
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public void removeProject(long projectId, boolean audit) throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param projectIds
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public void removeProjects(long[] projectIds, boolean audit) throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param clientId
     *            not used
     * @param type
     *            nut used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public long[] retrieveEntriesForClient(long clientId, EntryType type) throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param projectId
     *            not used
     * @param type
     *            not used
     *
     * @return empty array
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             if the project id is 9998
     */
    public long[] retrieveEntriesForProject(long projectId, EntryType type) throws DataAccessException {
        if (projectId == 9998) {
            throw new UnrecognizedEntityException(projectId, "Unrecognizable");
        } else {
            return new long[0];
        }
    }

    /**
     * Mock method.
     *
     * @param filter
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     */
    public Project[] searchProjects(Filter filter) throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param project
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public void updateProject(Project project, boolean audit) throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param projects
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     * @throws UnrecognizedEntityException
     *             not thrown
     */
    public void updateProjects(Project[] projects, boolean audit) throws DataAccessException {
        // nothing to do
    }

}
