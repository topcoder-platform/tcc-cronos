/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.search.builder.filter.Filter;



/**
 * Mock a ProjectStatusDAO for testing.
 *
 * @author  CaDenza
 * @version 1.0
 *
 */
public class MockProjectStatusDAO implements ProjectStatusDAO {

    /**
     * Mapping for stored project status.
     */
    private Map<String, ProjectStatus> mapping = new HashMap<String, ProjectStatus>();

    /**
     * Default constructor.
     */
    public MockProjectStatusDAO() {
        // do nothing.
    }

    /**
     * Get the ProjectStatus with id.
     *
     * @param id
     *            the id
     * @return the ProjectStatus instance
     * @throws DAOException
     *             to junit
     */
    public ProjectStatus retrieveById(Long id) throws DAOException {
        return mapping.get(String.valueOf(id));
    }

    /**
     * Get all the ProjectStatus instance.
     *
     * @return a list of ProjectStatus
     * @throws DAOException
     *             for testing
     */
    public List<ProjectStatus> retrieveAll() throws DAOException {
        return new ArrayList<ProjectStatus>();
    }

    /**
     * Search by name.
     *
     * @param name
     *            the name
     * @return null
     */
    @SuppressWarnings("unchecked")
    public List searchByName(String name) {
        return null;
    }

    /**
     * search with filter.
     *
     * @param filter
     *            the filter
     * @return a list
     */
    @SuppressWarnings("unchecked")
    public List search(Filter filter) {
        return null;
    }

    /**
     * Save the entity.
     *
     * @param entity
     *            the entity to save
     * @return the entity
     * @throws DAOException
     *             for testing
     */

    public ProjectStatus save(ProjectStatus entity) throws DAOException {
        mapping.put(String.valueOf(entity.getId()), entity);
        return entity;
    }

    /**
     * Delete the entity.
     *
     * @param entity
     *            the entity
     * @throws DAOException
     *             for testing
     */
    public void delete(ProjectStatus entity) throws DAOException {
        mapping.remove(String.valueOf(entity.getId()));
    }

    /**
     * Get projects with status.
     *
     * @param status
     *            the status
     * @return a list of Project
     * @throws DAOException
     *             to test
     */
    public List<Project> getProjectsWithStatus(ProjectStatus status) throws DAOException {
        return new ArrayList<Project>();
    }
}