/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ProjectDAO implementation for testing the performence of DAOProjectManager.
 *
 * @author  CaDenza
 * @version 1.0
 *
 */
public class MockProjectDAO implements ProjectDAO {

    /**
     * Represents counter for project stored.
     */
    private static int projectCOUNTER = 1;

    /**
     * Represents pool for project stored.
     */
    private Map<String, Project> mapping = new HashMap<String, Project>();

    /**
     * The flag to control exception thrown.
     */
    private boolean flag = false;

    /**
     * Default constructor.
     */
    public MockProjectDAO() {
        // do nothing.
    }

    /**
     * Set the flag value.
     *
     * @param flag
     *            the flag value to set.
     */
    public void setFlag(boolean flag) {
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
        checkFlag();
        return Arrays.asList(mapping.values().toArray(new Project[mapping.size()]));
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
    public Project retrieveById(Long id) throws DAOException {
        checkFlag();
        return mapping.get(String.valueOf(id));
    }

    /**
     * Get all the entity.
     *
     * @return a list of Project
     * @throws DAOException
     *             to junit
     */
    public List<Project> retrieveAll() throws DAOException {
        checkFlag();
        return Arrays.asList(mapping.values().toArray(new Project[mapping.size()]));
    }

    /**
     * Search Project with name.
     *
     * @param name
     *            the name to search
     * @return a list of Project
     * @throws DAOException for testing
     */
    public List<Project> searchByName(String name) throws DAOException {
        checkFlag();
        List<Project> result = new ArrayList<Project>();
        for (Map.Entry<String, Project> entry : mapping.entrySet()) {
            if (entry.getValue().equals(name)) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * search the projects.
     *
     * @param filter
     *            the filter
     * @return a list of Project
     * @throws DAOException for testing
     */
    public List<Project> search(Filter filter) throws DAOException {
        checkFlag();
        return new ArrayList<Project>();
    }

    /**
     * Save the entry.
     *
     * @param entry
     *            the AuditableEntity to save.
     * @return entity
     * @throws DAOException for testing
     */
    public Project save(Project entry) throws DAOException {
        checkFlag();
        if (entry.getId() <= 0) {
            entry.setId(projectCOUNTER);
            projectCOUNTER++;
        }
        mapping.put(String.valueOf(entry.getId()), entry);
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
    public void delete(Project entity) throws DAOException {
        checkFlag();
        mapping.remove(String.valueOf(entity.getId()));
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
     */
    public Project retrieveById(Long id, boolean include) throws DAOException {
        checkFlag();
        return mapping.get(String.valueOf(id));
    }

    /**
     * Check flag status whether exception should be thrown or not.
     *
     * @throws DAOException if flag is true.
     */
    private void checkFlag() throws DAOException {
        if (flag) {
            throw new DAOException("Failure from persistence.");
        }
    }
}