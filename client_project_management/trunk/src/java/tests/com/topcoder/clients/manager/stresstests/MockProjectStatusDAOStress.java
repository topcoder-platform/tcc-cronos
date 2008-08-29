/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock ProjectStatusDAO implementation for stress testing.
 *
 * @author mayday
 * @version 1.0
 *
 */
public class MockProjectStatusDAOStress implements ProjectStatusDAO<ProjectStatus, Long> {

    /**
     * Defines the operation that performs the retrieval of an entity using the given id from the persistence.
     *
     * @param id the id
     * @return AuditableEntity
     */
    public ProjectStatus retrieveById(Serializable id) throws DAOException {
        return createProjectStatus();
    }

    /**
     * Defines the operation that performs the retrieval of all entities from the persistence.
     *
     * @return List
     */
    public List<ProjectStatus> retrieveAll() {
        return buildProjectStatusList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that have the given name in the persistence.
     *
     * @param name the name for search
     * @return List;
     */
    public List searchByName(String name) {
        return buildProjectStatusList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that match the given filter in the persistence.
     *
     * @param filter the filter
     * @return List
     */
    public List search(Filter filter) {
        return buildProjectStatusList();
    }

    /**
     * Defines the operation that performs the salvation of an entity in the persistence.
     *
     * @param entity the AuditableEntity to save.
     * @return the saved entity
     */
    public AuditableEntity save(AuditableEntity entity) {
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
    public void delete(AuditableEntity entity) {
        entity.setModifyUsername("stress");
    }

    /**
     * Defines the operation that performs the retrieval of the list with projects with the given status
     * from the persistence.
     *
     * @param status the status
     * @return a list of Project
     */
    public List<Project> getProjectsWithStatus(ProjectStatus status) {
        return buildProjectList();
    }

    /**
     * Build a ProjectStatus list for test.
     * @return the created ProjectStatus list.
     */
    private List<ProjectStatus> buildProjectStatusList() {
        List<ProjectStatus> list = new ArrayList<ProjectStatus>();
        list.add(createProjectStatus());
        return list;
    }

    /**
     * Create a ProjectStatus for test.
     * @return the created ProjectStatus.
     */
    private ProjectStatus createProjectStatus() {
        ProjectStatus status = new ProjectStatus();
        status.setName("project status");
        status.setDescription("des");
        status.setId(10L);
        return status;
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
