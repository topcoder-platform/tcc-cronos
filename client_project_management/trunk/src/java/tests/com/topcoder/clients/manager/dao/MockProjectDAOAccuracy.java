/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ProjectDAO implementation for testing the accuracy performence of DAOProjectManager.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class MockProjectDAOAccuracy implements ProjectDAO {

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
        if (flag) {
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
        long value = new Long(String.valueOf(id)).longValue();

        if (value == 10) {
            Project project = new Project();
            project.setSalesTax(10.0);
            project.setName("name");
            project.setDescription("description");
            project.setDeleted(false);
            project.setId(10L);

            return project;
        }

        if (value == 20) {
            Project project = new Project();
            project.setSalesTax(10.0);
            project.setName("name2");
            project.setDescription("description2");
            project.setDeleted(false);
            project.setId(20L);

            return project;
        }

        return null;
    }

    /**
     * Search Project with name.
     *
     * @param name
     *            the name to search
     * @return a list of Project
     */
    public List<Project> searchByName(String name) {
        if ("empty".equals(name)) {
            return new ArrayList<Project>();
        }

        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);

        List<Project> ret = new ArrayList<Project>();
        ret.add(project);

        return ret;

    }

    /**
     * search the projects.
     *
     * @param filter
     *            the filter
     * @return a list of Project
     */
    public List<Project> search(Filter filter) {
        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);

        List<Project> ret = new ArrayList<Project>();
        ret.add(project);

        return ret;
    }

    /**
     * Save the entry.
     *
     * @param entity
     *            the Project to save.
     * @return entity
     */
    public Project save(Project entity) {
        entity.setCreateDate(new Date());
        entity.setCreateUsername("tester");
        entity.setModifyUsername("projectDAO");
        return entity;
    }

    /**
     * Delete the Project.
     *
     * @param entity
     *            the Project to delete
     * @throws DAOException
     *             if anything goes wrong
     */
    public void delete(Project entity) throws DAOException {
        entity.setModifyUsername("deleted");
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
        if (id.longValue() == 10) {
            Project project = new Project();
            project.setSalesTax(10.0);
            project.setName("name");
            project.setDescription("description");
            project.setDeleted(false);
            project.setId(10L);

            return project;
        }

        if (id == 20) {
            Project project = new Project();
            project.setSalesTax(10.0);
            project.setName("name2");
            project.setDescription("description2");
            project.setDeleted(false);
            project.setId(20L);

            return project;
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
    public List<Project> retrieveAll() throws DAOException {
        return new ArrayList<Project>();
    }

}
