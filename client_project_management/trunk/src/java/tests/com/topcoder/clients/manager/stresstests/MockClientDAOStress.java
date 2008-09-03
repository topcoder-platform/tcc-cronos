/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock ClientDAO implementation for stress testing.
 *
 * @author mayday
 * @version 1.0
 *
 */
public class MockClientDAOStress implements ClientDAO {

    /**
     * Defines the operation that performs the retrieval of an entity using the given id from the persistence.
     *
     * @param id the id
     * @return AuditableEntity
     */
    public Client retrieveById(Long id) {
        return createClient();
    }

    /**
     * Defines the operation that performs the retrieval of all entities from the persistence.
     *
     * @return List
     */
    public List retrieveAll() {
        return buildClientList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that have the given name in the persistence.
     *
     * @param name the name for search
     * @return List;
     */
    public List searchByName(String name) {
        return buildClientList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that match the given filter in the persistence.
     *
     * @param filter the filter
     * @return List
     */
    public List search(Filter filter) {
        return buildClientList();
    }

    /**
     * Defines the operation that performs the salvation of an entity in the persistence.
     *
     * @param entity the AuditableEntity to save.
     * @return the saved entity
     */
    public Client save(Client entity) {
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
    public void delete(Client entity) {
        entity.setModifyUsername("stress");
    }

    /**
     * Defines the operation that performs the retrieval of the list with projects with the given client from
     * the persistence.
     *
     * @param client the client
     * @return a list of project
     */
    public List<Project> getProjectsForClient(Client client) {
        List<Project> projects = new ArrayList<Project>();

        Project project1 = new Project();
        project1.setChildProjects(new ArrayList<Project>());
        project1.setId(10L);
        project1.setName("project1");

        Project project2 = new Project();
        project2.setChildProjects(null);
        project2.setId(20);
        project2.setName("project2");

        projects.add(project1);
        projects.add(project2);

        return projects;
    }

    /**
     * Build a client list for test.
     * @return the created client list.
     */
    private List<Client> buildClientList() {
        List<Client> list = new ArrayList<Client>();
        list.add(createClient());
        return list;
    }

    /**
     * Create a client for test.
     * @return the created client.
     */
    private Client createClient() {
        Client client = new Client();
        client.setStartDate(StressTestHelper.STARTDATE);
        client.setEndDate(StressTestHelper.ENDDATE);
        client.setName("client");
        client.setDeleted(false);
        client.setId(1L);
        return client;
    }
}
