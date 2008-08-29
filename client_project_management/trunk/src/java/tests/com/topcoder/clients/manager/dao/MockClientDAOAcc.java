/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ClientDAO implementation for testing.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class MockClientDAOAcc implements ClientDAO<Client, Long> {

    /**
     * get entity with id.
     *
     * @param id
     *            the id
     * @return null
     */
    public AuditableEntity retrieveById(Serializable id) {

        return null;
    }

    /**
     * Get all.
     *
     * @return null
     */
    public List retrieveAll() {

        return null;
    }

    /**
     * Search by name.
     *
     * @param name
     *            the name for search
     * @return null;
     */
    public List searchByName(String name) {

        return null;
    }

    /**
     * Search with filter.
     *
     * @param filter
     *            the filter
     * @return null
     */
    public List search(Filter filter) {

        return null;
    }

    /**
     * Save the entry.
     *
     * @param entity
     *            the AuditableEntity to save.
     * @return the saved entity
     */
    public AuditableEntity save(AuditableEntity entity) {
        entity.setCreateDate(new Date());
        entity.setCreateUsername("tester");
        entity.setModifyUsername("unittester");
        return entity;
    }

    /**
     * Delete the Client entity.
     *
     * @param entity
     *            the entity to delete
     */
    public void delete(AuditableEntity entity) {
        // empty
    }

    /**
     * Get a list of project for client.
     *
     * @param client
     *            the client
     * @return a list of project
     */
    public List<Project> getProjectsForClient(Client client) {
        if (client.getId() == 1L) {
            return new ArrayList<Project>();
        }

        if (client.getId() == 2L) {

            List<Project> projects = new ArrayList<Project>();

            Project project1 = new Project();
            project1.setChildProjects(new ArrayList<Project>());
            project1.setId(10L);
            project1.setName("name1");

            Project project2 = new Project();
            project2.setChildProjects(null);
            project2.setId(20);
            project2.setName("name2");

            projects.add(project1);
            projects.add(project2);

            return projects;

        }

        return new ArrayList<Project>();
    }

}
