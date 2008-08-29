/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.AuditableEntity;
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
public class MockProjectStatusDAOAcc implements ProjectStatusDAO<ProjectStatus, Long> {

    /**
     * Get the ProjectStatus with id.
     *
     * @param id
     *            the id
     * @return the ProjectStatus instance
     * @throws DAOException
     *             to junit
     */
    public ProjectStatus retrieveById(Serializable id) throws DAOException {
        long value = new Long(String.valueOf(id)).longValue();

        if (value == 1) {
            return null;
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
     */
    public List<ProjectStatus> retrieveAll() {
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
     */
    public AuditableEntity save(AuditableEntity entity) {
        entity.setCreateUsername("status");

        return entity;
    }

    /**
     * Delete the entity.
     *
     * @param entity
     *            the entity
     */
    public void delete(AuditableEntity entity) {
    }

    /**
     * Get projects with status.
     *
     * @param status
     *            the status
     * @return a list of Project
     */
    public List<Project> getProjectsWithStatus(ProjectStatus status) {
        return new ArrayList<Project>();
    }

}
