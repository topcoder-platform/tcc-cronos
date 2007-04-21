/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.DuplicateEntityException;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;

/**
 * A mocked implmementation for interface ProjectUtility.
 *
 * @author Chenhong
 * @version 1.0
 */
public class MyProjectUtility implements ProjectUtility {

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#addProject(com.topcoder.timetracker.project.Project,
     *      boolean)
     */
    public void addProject(Project arg0, boolean arg1) throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#updateProject(com.topcoder.timetracker.project.Project,
     *      boolean)
     */
    public void updateProject(Project arg0, boolean arg1) throws DataAccessException, UnrecognizedEntityException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#removeProject(long, boolean)
     */
    public void removeProject(long arg0, boolean arg1) throws DataAccessException, UnrecognizedEntityException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#getProject(long)
     */
    public Project getProject(long arg0) throws DataAccessException, UnrecognizedEntityException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#searchProjects(com.topcoder.search.builder.filter.Filter)
     */
    public Project[] searchProjects(Filter arg0) throws DataAccessException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#addProjects(com.topcoder.timetracker.project.Project[],
     *      boolean)
     */
    public void addProjects(Project[] arg0, boolean arg1) throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#updateProjects(com.topcoder.timetracker.project.Project[],
     *      boolean)
     */
    public void updateProjects(Project[] arg0, boolean arg1) throws DataAccessException, UnrecognizedEntityException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#removeProjects(long[], boolean)
     */
    public void removeProjects(long[] arg0, boolean arg1) throws DataAccessException, UnrecognizedEntityException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#getProjects(long[])
     */
    public Project[] getProjects(long[] arg0) throws DataAccessException, UnrecognizedEntityException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#addEntryToProject(long, long,
     *      com.topcoder.timetracker.project.EntryType, boolean)
     */
    public void addEntryToProject(long arg0, long arg1, EntryType arg2, boolean arg3) throws DataAccessException,
            UnrecognizedEntityException, DuplicateEntityException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#removeEntryFromProject(long, long,
     *      com.topcoder.timetracker.project.EntryType, boolean)
     */
    public void removeEntryFromProject(long arg0, long arg1, EntryType arg2, boolean arg3)
            throws DataAccessException, UnrecognizedEntityException {
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#retrieveEntriesForProject(long,
     *      com.topcoder.timetracker.project.EntryType)
     */
    public long[] retrieveEntriesForProject(long arg0, EntryType arg1) throws DataAccessException,
            UnrecognizedEntityException {
        return new long[] { 1 };
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#retrieveEntriesForClient(long,
     *      com.topcoder.timetracker.project.EntryType)
     */
    public long[] retrieveEntriesForClient(long arg0, EntryType arg1) throws DataAccessException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#getProjectFilterFactory()
     */
    public ProjectFilterFactory getProjectFilterFactory() {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.project.ProjectUtility#enumerateProjects()
     */
    public Project[] enumerateProjects() throws DataAccessException {
        return null;
    }
}