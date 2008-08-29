/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
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
public class MockProjectDAO implements ProjectDAO<Project, Long> {
    /**
     * Mock Method.
     *
     * @param includeChildren mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public List<Project> retrieveAll(boolean includeChildren)
        throws DAOException {
        List<Project> list = new ArrayList<Project>();
        list.add(getSampleProject(1));
        list.add(getSampleProject(2));
        list.add(getSampleProject(3));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param id mock parameter
     * @param includeChildren mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     * @throws com.topcoder.clients.dao.EntityNotFoundException if such error
     */
    public Project retrieveById(Long id, boolean includeChildren)
        throws DAOException {
        return getSampleProject((Long) id);
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
        return getSampleProject((Long) id);
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
    @SuppressWarnings("unchecked")
    public List search(Filter filter) throws DAOException {
        List<Project> list = new ArrayList<Project>();
        list.add(getSampleProject(1));
        list.add(getSampleProject(3));

        return list;
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
        List<Project> list = new ArrayList<Project>();
        list.add(getSampleProject(3));

        return list;
    }

    /**
     * Get sample project.
     * @param id the id
     * @return mock Project.
     */
    static Project getSampleProject(long id) {
        Project project = new Project();
        project.setSalesTax(0.23d);
        project.setDescription("desc");
        project.setName("test");
        project.setDeleted(false);
        project.setId(id);

        ProjectStatus status = new ProjectStatus();
        status.setName("test");
        status.setDescription("desc");
        status.setDeleted(false);
        status.setId(id);
        project.setProjectStatus(status);

        return project;
    }
}
