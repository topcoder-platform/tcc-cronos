/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock ProjectDAO implementation for stress testing.
 *
 * @author mayday
 * @version 1.0
 *
 */
public class MockProjectDAOStress implements ProjectDAO {

    /**
     * Defines the operation that performs the retrieval of an entity using the given id from the persistence.
     *
     * @param id the id
     * @return AuditableEntity
     */
    public Project retrieveById(Long id) throws DAOException {
        return createProject();
    }

    /**
     * Defines the operation that performs the retrieval of all projects from the persistence.
     *
     * @param includeChildren include children or not
     * @return List
     */
    public List<Project> retrieveAll(boolean includeChildren) throws DAOException {
        return buildProjectList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that have the given name in the persistence.
     *
     * @param name the name for search
     * @return List;
     */
    public List<Project> searchByName(String name) {
        return buildProjectList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that match the given filter in the persistence.
     *
     * @param filter the filter
     * @return List
     */
    public List<Project> search(Filter filter) {
        return buildProjectList();
    }

    /**
     * Defines the operation that performs the salvation of an entity in the persistence.
     *
     * @param entity the Project to save.
     * @return the saved entity
     */
    public Project save(Project entity) {
        entity.setCreateDate(new Date());
        entity.setCreateUsername("test");
        entity.setModifyUsername("stress");
        return entity;
    }

    /**
     * Defines the operation that performs the deletion of an entity from the persistence.
     *
     * @param entity the entity to delete
     */
    public void delete(Project entity) throws DAOException {
        entity.setModifyUsername("stress");
    }

    /**
     * Defines the operation that performs the retrieval of a project using the given id  from the persistence.
     *
     * @param id the id for project
     * @param include flag for child Project
     * @return project instance
     */
    public Project retrieveById(Long id, boolean include) throws DAOException {
        return createProject();
    }

    /**
     * Defines the operation that performs the retrieval of all projects from the persistence.
     *
     * @return List
     */
    public List<Project> retrieveAll() throws DAOException {
        return buildProjectList();
    }

    /**
     * Build a project list for test.
     * @return the created project list.
     */
    private List<Project> buildProjectList() {
        List<Project> list = new ArrayList<Project>();
        list.add(createProject());
        return list;
    }

    /**
     * Create a project for test.
     * @return the created project.
     */
    private Project createProject() {
        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("project");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);
        return project;
    }

}
