/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock CompanyDAO implementation for stress testing.
 *
 * @author mayday
 * @version 1.0
 *
 */
public class MockCompanyDAOStress implements CompanyDAO {

    /**
     * Defines the operation that performs the retrieval of an entity using the given id from the persistence.
     *
     * @param id the id
     * @return AuditableEntity
     */
    public Company retrieveById(Long id) throws DAOException {
        return createCompany();
    }

    /**
     * Defines the operation that performs the retrieval of all entities from the persistence.
     *
     * @return List
     */
    public List<Company> retrieveAll() {
        return buildCompanyList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that have the given name in the persistence.
     *
     * @param name the name for search
     * @return List;
     */
    public List searchByName(String name) throws DAOException {
        return buildCompanyList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that match the given filter in the persistence.
     *
     * @param filter the filter
     * @return List
     */
    public List search(Filter filter) throws DAOException {
        return buildCompanyList();
    }

    /**
     * Defines the operation that performs the salvation of an entity in the persistence.
     *
     * @param entity the AuditableEntity to save.
     * @return the saved entity
     */
    public Company save(Company entity) throws DAOException {
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
    public void delete(Company entity) throws DAOException {
        entity.setModifyUsername("stress");
    }

    /**
     * Defines the operation that performs the retrieval of the list with clients for the given company from
     * the persistence.
     *
     * @param company the company
     * @return a list of Client
     */
    public List<Client> getClientsForCompany(Company company) throws DAOException {
        return buildClientList();
    }

    /**
     * Defines the operation that performs the retrieval of the list with projects for the given company
     * from the persistence.
     *
     * @param c the company
     * @return a list of Client
     */
    public List<Project> getProjectsForCompany(Company c) throws DAOException {
        return buildProjectList();
    }

    /**
     * Build a company list for test.
     * @return the created company list.
     */
    private List<Company> buildCompanyList() {
        List<Company> list = new ArrayList<Company>();
        list.add(createCompany());
        return list;
    }

    /**
     * Create a company for test.
     * @return the created company.
     */
    private Company createCompany() {
        Company c = new Company();
        c.setName("company");
        c.setDeleted(false);
        c.setPasscode("passcode");
        c.setId(2L);
        return c;
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
