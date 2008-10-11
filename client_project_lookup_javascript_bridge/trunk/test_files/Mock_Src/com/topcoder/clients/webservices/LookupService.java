/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock class for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LookupService {
    /**
     * <p>
     * Defines the operation that performs the retrieval of an client using the given id from the
     * persistence.
     * </p>
     *
     * @param id
     *            the identifier of the client that should be retrieved. Should be positive.
     * @return the client with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public Client retrieveClient(long id) throws LookupServiceException, AuthorizationFailedException {
        Client res = new Client();
        res.setId(id);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients from the persistence. If
     * nothing is found, return an empty list.
     * </p>
     *
     * @return the list of clients found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Client> retrieveAllClients() throws LookupServiceException, AuthorizationFailedException {
        List<Client> res = new ArrayList<Client>();
        Client client1 = new Client();
        client1.setId(1);
        res.add(client1);
        Client client2 = new Client();
        client2.setId(2);
        res.add(client2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients that have the given name in
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the client that should be retrieved. Should be not empty and not null.
     * @return the list with clients with the given name retrieved from the persistence. If nothing
     *         is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Client> searchClientsByName(String name) throws LookupServiceException,
        AuthorizationFailedException {
        List<Client> res = new ArrayList<Client>();
        Client client1 = new Client();
        client1.setId(1);
        client1.setName(name);
        res.add(client1);
        Client client2 = new Client();
        client2.setId(2);
        client2.setName(name);
        res.add(client2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients that match the given filter
     * in the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched clients. Should not be null.
     * @return the list with clients that match the given filter retrieved from the persistence. If
     *         nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Client> searchClients(Filter filter) throws LookupServiceException,
        AuthorizationFailedException {
        return null;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an client status using the given id from
     * the persistence.
     * </p>
     *
     * @param id
     *            the identifier of the client status that should be retrieved. Should be positive.
     * @return the client status with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public ClientStatus getClientStatus(long id) throws LookupServiceException, AuthorizationFailedException {
        ClientStatus res = new ClientStatus();
        res.setId(id);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all client statuses from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of client statuses found in the persistence. If nothing is found, return an
     *         empty list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<ClientStatus> getAllClientStatuses() throws LookupServiceException,
        AuthorizationFailedException {
        List<ClientStatus> res = new ArrayList<ClientStatus>();
        ClientStatus clientStatus1 = new ClientStatus();
        clientStatus1.setId(1);
        res.add(clientStatus1);
        ClientStatus clientStatus2 = new ClientStatus();
        clientStatus2.setId(2);
        res.add(clientStatus2);
        return res;

    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients with the given
     * client status from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param status
     *            the given client status to retrieve it's clients. Should not be null.
     * @return the list of clients for the given client status found in the persistence. If nothing
     *         is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Client> getClientsForStatus(ClientStatus status) throws LookupServiceException,
        AuthorizationFailedException {
        List<Client> res = new ArrayList<Client>();
        Client client1 = new Client();
        client1.setId(1);
        client1.setClientStatus(status);
        res.add(client1);
        Client client2 = new Client();
        client2.setId(2);
        client2.setClientStatus(status);
        res.add(client2);
        return res;

    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an project using the given id from the
     * persistence.
     * </p>
     *
     * @param id
     *            the identifier of the project that should be retrieved. Should be positive.
     * @return the project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public Project retrieveProject(long id) throws LookupServiceException, AuthorizationFailedException {
        Project project = new Project();
        project.setId(id);
        return project;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given
     * client from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param client
     *            the given client to retrieve it's projects. Should not be null.
     * @return the list of projects for the given client found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the client is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Project> retrieveProjectsForClient(Client client) throws LookupServiceException,
        AuthorizationFailedException {
        List<Project> res = new ArrayList<Project>();
        Project project1 = new Project();
        project1.setId(1);
        project1.setClient(client);
        res.add(project1);
        Project project2 = new Project();
        project2.setId(2);
        project2.setClient(client);
        res.add(project2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from the persistence. If
     * nothing is found, return an empty list.
     * </p>
     *
     * @return the list of projects found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Project> retrieveAllProjects() throws LookupServiceException, AuthorizationFailedException {
        List<Project> res = new ArrayList<Project>();
        Project project1 = new Project();
        project1.setId(1);
        res.add(project1);
        Project project2 = new Project();
        project2.setId(2);
        res.add(project2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects that have the given name in
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the project that should be retrieved. Should be not empty and not
     *            null.
     * @return the list with projects with the given name retrieved from the persistence. If nothing
     *         is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Project> searchProjectsByName(String name) throws LookupServiceException,
        AuthorizationFailedException {
        List<Project> res = new ArrayList<Project>();
        Project project1 = new Project();
        project1.setId(1);
        project1.setName(name);
        res.add(project1);
        Project project2 = new Project();
        project2.setId(2);
        project2.setName(name);
        res.add(project2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects that match the given filter
     * in the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched projects. Should not be null.
     * @return the list with projects that match the given filter retrieved from the persistence. If
     *         nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Project> searchProjects(Filter filter) throws LookupServiceException,
        AuthorizationFailedException {
        return null;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an project status using the given id
     * from the persistence.
     * </p>
     *
     * @param id
     *            the identifier of the project status that should be retrieved. Should be positive.
     * @return the project status with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public ProjectStatus getProjectStatus(long id) throws LookupServiceException,
        AuthorizationFailedException {
        ProjectStatus status = new ProjectStatus();
        status.setId(id);
        return status;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all project statuses from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of project statuses found in the persistence. If nothing is found, return an
     *         empty list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<ProjectStatus> getAllProjectStatuses() throws LookupServiceException,
        AuthorizationFailedException {
        List<ProjectStatus> res = new ArrayList<ProjectStatus>();
        ProjectStatus status1 = new ProjectStatus();
        status1.setId(1);
        res.add(status1);
        ProjectStatus status2 = new ProjectStatus();
        status2.setId(2);
        res.add(status2);
        return res;

    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given
     * project status from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param status
     *            the given project status to retrieve it's projects. Should not be null.
     * @return the list of projects for the given project status found in the persistence. If
     *         nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the project status is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Project> getProjectsForStatus(ProjectStatus status) throws LookupServiceException,
        AuthorizationFailedException {
        List<Project> res = new ArrayList<Project>();
        Project project1 = new Project();
        project1.setId(1);
        project1.setProjectStatus(status);
        res.add(project1);
        Project project2 = new Project();
        project2.setId(2);
        project2.setProjectStatus(status);
        res.add(project2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an company using the given id from the
     * persistence.
     * </p>
     *
     * @param id
     *            the identifier of the company that should be retrieved. Should be positive.
     * @return the company with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public Company retrieveCompany(long id) throws LookupServiceException, AuthorizationFailedException {
        Company company = new Company();
        company.setId(id);
        return company;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all companies from the persistence. If
     * nothing is found, return an empty list.
     * </p>
     *
     * @return the list of companies found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Company> retrieveAllCompanies() throws LookupServiceException, AuthorizationFailedException {
        List<Company> res = new ArrayList<Company>();
        Company company1 = new Company();
        company1.setId(1);
        res.add(company1);
        Company company2 = new Company();
        company2.setId(2);
        res.add(company2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all companies that have the given name
     * in the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the company that should be retrieved. Should be not empty and not
     *            null.
     * @return the list with companies with the given name retrieved from the persistence. If
     *         nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Company> searchCompaniesByName(String name) throws LookupServiceException,
        AuthorizationFailedException {
        List<Company> res = new ArrayList<Company>();
        Company company1 = new Company();
        company1.setId(1);
        company1.setName(name);
        res.add(company1);
        Company company2 = new Company();
        company2.setId(2);
        company2.setName(name);
        res.add(company2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all companies that match the given
     * filter in the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched companies. Should not be
     *            null.
     * @return the list with companies that match the given filter retrieved from the persistence.
     *         If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Company> searchCompanies(Filter filter) throws LookupServiceException,
        AuthorizationFailedException {
        return null;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients for the given
     * company from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's clients. Should not be null.
     * @return the list of clients for the given company found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the company is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Client> getClientsForCompany(Company company) throws LookupServiceException,
        AuthorizationFailedException {
        List<Client> res = new ArrayList<Client>();
        Client client1 = new Client();
        client1.setId(1);
        client1.setCompany(company);
        res.add(client1);
        Client client2 = new Client();
        client2.setId(2);
        client2.setCompany(company);
        res.add(client2);
        return res;
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects for the given
     * company from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's projects. Should not be null.
     * @return the list of projects for the given company found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the company is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    public List<Project> getProjectsForCompany(Company company) throws LookupServiceException,
        AuthorizationFailedException {
        List<Project> res = new ArrayList<Project>();
        Project project1 = new Project();
        project1.setId(1);
        project1.setCompany(company);
        res.add(project1);
        Project project2 = new Project();
        project2.setId(2);
        project2.setCompany(company);
        res.add(project2);
        return res;
    }
}
