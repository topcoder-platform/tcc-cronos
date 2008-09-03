/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ProjectStatusDAO for testing.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class MockProjectStatusDAO implements ProjectStatusDAO {
    /**
     * The flag to control exception thrown.
     */
    private int flag = 0;

    /**
     * Set the flag value.
     *
     * @param flag
     *            the flag value to set.
     */
    public void setFlag(int flag) {
        this.flag = flag;
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
        long value = new Long(String.valueOf(id)).longValue();

        if (value < 0) {
            throw new IllegalArgumentException("The id should not be negative.");
        }
        if (value == 1) {
            return null;
        }
        if (value == 4) {
            throw new ClassCastException("for testing");
        }

        if (value == 2) {
            throw new EntityNotFoundException("Throw for testing");
        }
        if (value == 1001) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if (value == 1002) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        ProjectStatus status = new ProjectStatus();
        status.setName("retrieve");
        status.setDescription("des");
        status.setId(10L);

        return status;
    }

    /**
     * Get all the ProjectStatus instance.
     *
     * @return a list of ProjectStatus
     * @throws DAOException
     *             for testing
     */
    public List<ProjectStatus> retrieveAll() throws DAOException {
        if (this.flag == 1) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if (this.flag == 2) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        return new ArrayList<ProjectStatus>();
    }

    /**
     * Search by name.
     *
     * @param name
     *            the name
     * @return null
     */
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
     * @throws DAOConfigurationException
     *             for testing
     */
    public ProjectStatus save(ProjectStatus entity) throws DAOException {
        if ("DAOException".equals(entity.getName())) {

            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(entity.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }
        if ("ClassCastException".equals(entity.getName())) {
           throw new ClassCastException("For testing");
        }

        entity.setCreateUsername("status");

        return entity;
    }

    /**
     * Delete the entity.
     *
     * @param entity
     *            the entity
     * @throws EntityNotFoundException
     *             for testing
     * @thrwos DAOConfigurationException for testing
     * @throws DAOException
     *             for testing
     */
    public void delete(ProjectStatus entity) throws DAOException {
        if ("entityNotFound".equals(entity.getName())) {
            throw new EntityNotFoundException("The entity is not found.", new IllegalArgumentException("IAE"));
        }

        if ("DAOException".equals(entity.getName())) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(entity.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        entity.setCreateUsername("deleted");
    }

    /**
     * Get projects with status.
     *
     * @param status
     *            the status
     * @return a list of Project
     * @throws DAOException
     *             to test
     * @throws DAOConfigurationException
     *             for testing
     */
    public List<Project> getProjectsWithStatus(ProjectStatus status) throws DAOException {
        if ("DAOException".equals(status.getName())) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(status.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        return new ArrayList<Project>();
    }

}
