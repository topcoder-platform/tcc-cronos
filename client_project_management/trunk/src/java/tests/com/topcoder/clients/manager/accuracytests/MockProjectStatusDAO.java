/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

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
 * Mock Implementation of ClientStatusDAO.
 *
 * @author onsky
 * @version 1.0
 */
public class MockProjectStatusDAO implements ProjectStatusDAO<ProjectStatus, Long> {
    /**
     * Mock Method.
     *
     * @param status mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     * @throws com.topcoder.clients.dao.EntityNotFoundException if such error
     */
    public List<Project> getProjectsWithStatus(ProjectStatus status)
        throws DAOException {
        List<Project> list = new ArrayList<Project>();
        list.add(MockProjectDAO.getSampleProject(1));
        list.add(MockProjectDAO.getSampleProject(3));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param entity mock parameter
     *
     * @throws DAOException if such error
     */
    public void delete(AuditableEntity entity) throws DAOException {
    }

    /**
     * Mock Method.
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List retrieveAll() throws DAOException {
        return null;
    }

    /**
     * Mock Method.
     *
     * @param id mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public AuditableEntity retrieveById(Serializable id)
        throws DAOException {
        return getSampleProjectStatus((Long) id);
    }

    /**
     * Mock Method.
     *
     * @param entity mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public AuditableEntity save(AuditableEntity entity)
        throws DAOException {
        entity.setId(10L);

        return entity;
    }

    /**
     * Mock Method.
     *
     * @param filter mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public List search(Filter filter) throws DAOException {
        return null;
    }

    /**
     * Mock Method.
     *
     * @param name mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List searchByName(String name) throws DAOException {
        return null;
    }

    ProjectStatus getSampleProjectStatus(long id) {
        ProjectStatus status = new ProjectStatus();
        status.setName("test");
        status.setDescription("desc");
        status.setDeleted(false);
        status.setId(id);

        return status;
    }
}
