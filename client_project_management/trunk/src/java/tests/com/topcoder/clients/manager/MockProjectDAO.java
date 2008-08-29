/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ProjectDAO implementation for testing the performence of DAOProjectManager.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class MockProjectDAO implements ProjectDAO<Project, Long> {
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
     * Get all the Project.
     *
     * @param flag
     *            the flag
     * @return a list of Project
     * @throws DAOException
     *             to junit
     */
    public List<Project> retrieveAll(boolean flag) throws DAOException {
        if (this.flag == 1) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if (this.flag == 2) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }
        return new ArrayList<Project>();
    }

    /**
     * Get the entity with id.
     *
     * @param id
     *            the id
     * @return the entity to return
     * @throws DAOException
     *             to junit
     */
    public AuditableEntity retrieveById(Serializable id) throws DAOException {
        long value = new Long(String.valueOf(id)).longValue();

        if (value < 0) {
            throw new IllegalArgumentException("Throw for testing");
        }

        if (value == 1) {
            return null;
        }

        if (value == 2) {
            throw new DAOException("Throw for testing");
        }

        if (value == 3) {
            throw new DAOConfigurationException("Throw DAOConfigurationException for testing");
        }

        if (value == 4) {
            throw new EntityNotFoundException("not found.", 4L);
        }

        return null;
    }

    /**
     * Get all the entity.
     *
     * @return a list of Project
     * @throws DAOException
     *             to junit
     */
    public List retrieveAll() throws DAOException {
        return null;
    }

    /**
     * Search Project with name.
     *
     * @param name
     *            the name to search
     * @return a list of Project
     * @throws DAOException for testing
     */
    public List searchByName(String name) throws DAOException {
        if ("DAOException".equals(name)) {

            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(name)) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        return new ArrayList<Client>();
    }

    /**
     * search the projects.
     *
     * @param filter
     *            the filter
     * @return a list of Project
     * @throws DAOException for testing
     */
    public List search(Filter filter) throws DAOException {

        if (filter instanceof EqualToFilter) {
            EqualToFilter f = (EqualToFilter) filter;

            if ("DAOException".equals(f.getName())) {

                throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
            }

            if ("DAOConfigurationException".equals(f.getName())) {
                throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
            }

        }
        return new ArrayList<Client>();
    }

    /**
     * Save the entry.
     *
     * @param entry
     *            the AuditableEntity to save.
     * @return entity
     * @throws DAOException for testing
     */
    public AuditableEntity save(AuditableEntity entry) throws DAOException {
        if ("DAOException".equals(entry.getName())) {

            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(entry.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }
        if ("ClassCastException".equals(entry.getName())) {
            return new Client();
        }

        entry.setModifyUsername("saved");
        return entry;
    }

    /**
     * Delete the AuditableEntity.
     *
     * @param entity
     *            the AuditableEntity to delete
     * @throws DAOException
     *             if anything goes wrong
     */
    public void delete(AuditableEntity entity) throws DAOException {
        if ("entityNotFound".equals(entity.getName())) {
            throw new EntityNotFoundException("The entity is not found.", new IllegalArgumentException("IAE"), 1L);
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
     * Retrieve Project with given id and include.
     *
     * @param id
     *            the id for project
     * @param include
     *            flag for child Project
     * @return project instance
     * @throws DAOException
     *             if failed in the persistence
     * @throws DAOConfigurationException for testing
     */
    public Project retrieveById(Long id, boolean include) throws DAOException {
        if (id == 101) {
            throw new DAOException("Throw for testing");
        }

        if (id == 102) {
            throw new DAOConfigurationException("For testing");
        }

        return null;
    }
}
