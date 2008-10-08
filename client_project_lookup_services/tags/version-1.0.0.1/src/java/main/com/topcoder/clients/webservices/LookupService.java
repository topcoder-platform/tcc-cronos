/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the <code>LookupService</code> web service endpoint interface. This interface
 * defines the methods available for the <code>LookupService</code> web service: retrieve client, project,
 * company, client status and project status by id, retrieve all clients, projects, companies, client statuses
 * and project statuses, search all clients, projects, companies by name, search all clients, projects,
 * companies by filter, retrieve clients for status, retrieve projects for client, retrieve projects for
 * status, retrieve projects for company, retrieve clients for company.
 * </p>
 * <p>
 * Thread safety: Implementations of this interface should be thread safe. Thread safety should be provided
 * technically or by EJB container.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@WebService(name = "LookupService")
public interface LookupService {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the LookupService web service
     * endpoint interface. Represents the EJB session bean name. It is initialized to a default value:
     * "LookupServiceBean" String during runtime. Accessed directly, it is public. Can not be changed. It is
     * constant.
     * </p>
     */
    public static final String BEAN_NAME = "LookupServiceBean";

    /**
     * <p>
     * Defines the operation that performs the retrieval of an client using the given id from the persistence.
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
    @WebMethod
    public Client retrieveClient(long id) throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients from the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @return the list of clients found in the persistence. If nothing is found, return an empty list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Client > retrieveAllClients() throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients that have the given name in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the client that should be retrieved. Should be not empty and not null.
     * @return the list with clients with the given name retrieved from the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Client > searchClientsByName(String name) throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients that match the given filter in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched clients. Should not be null.
     * @return the list with clients that match the given filter retrieved from the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    @RequestWrapper(localName = "searchRequest", className = "com.topcoder.clients.webservices.SearchRequest")
    public List < Client > searchClients(@WebParam(name = "filter") Filter filter)
            throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of an client status using the given id from the
     * persistence.
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
    @WebMethod
    public ClientStatus getClientStatus(long id) throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all client statuses from the persistence. If
     * nothing is found, return an empty list.
     * </p>
     *
     * @return the list of client statuses found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < ClientStatus > getAllClientStatuses() throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients with the given client status
     * from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param status
     *            the given client status to retrieve it's clients. Should not be null.
     * @return the list of clients for the given client status found in the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Client > getClientsForStatus(ClientStatus status) throws LookupServiceException,
            AuthorizationFailedException;

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
    @WebMethod
    public Project retrieveProject(long id) throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given client from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param client
     *            the given client to retrieve it's projects. Should not be null.
     * @return the list of projects for the given client found in the persistence. If nothing is found, return
     *         an empty list.
     * @throws IllegalArgumentException
     *             if the client is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Project > retrieveProjectsForClient(Client client) throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @return the list of projects found in the persistence. If nothing is found, return an empty list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Project > retrieveAllProjects() throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects that have the given name in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the project that should be retrieved. Should be not empty and not null.
     * @return the list with projects with the given name retrieved from the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Project > searchProjectsByName(String name) throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects that match the given filter in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched projects. Should not be null.
     * @return the list with projects that match the given filter retrieved from the persistence. If nothing
     *         is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    @RequestWrapper(localName = "searchRequest", className = "com.topcoder.clients.webservices.SearchRequest")
    public List < Project > searchProjects(@WebParam(name = "filter") Filter filter)
            throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of an project status using the given id from the
     * persistence.
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
    @WebMethod
    public ProjectStatus getProjectStatus(long id) throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all project statuses from the persistence. If
     * nothing is found, return an empty list.
     * </p>
     *
     * @return the list of project statuses found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < ProjectStatus > getAllProjectStatuses() throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given project
     * status from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param status
     *            the given project status to retrieve it's projects. Should not be null.
     * @return the list of projects for the given project status found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the project status is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Project > getProjectsForStatus(ProjectStatus status) throws LookupServiceException,
            AuthorizationFailedException;

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
    @WebMethod
    public Company retrieveCompany(long id) throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all companies from the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @return the list of companies found in the persistence. If nothing is found, return an empty list.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Company > retrieveAllCompanies() throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all companies that have the given name in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the company that should be retrieved. Should be not empty and not null.
     * @return the list with companies with the given name retrieved from the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Company > searchCompaniesByName(String name) throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all companies that match the given filter in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched companies. Should not be null.
     * @return the list with companies that match the given filter retrieved from the persistence. If nothing
     *         is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    @RequestWrapper(localName = "searchRequest", className = "com.topcoder.clients.webservices.SearchRequest")
    public List < Company > searchCompanies(@WebParam(name = "filter") Filter filter)
            throws LookupServiceException, AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients for the given company from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's clients. Should not be null.
     * @return the list of clients for the given company found in the persistence. If nothing is found, return
     *         an empty list.
     * @throws IllegalArgumentException
     *             if the company is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Client > getClientsForCompany(Company company) throws LookupServiceException,
            AuthorizationFailedException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects for the given company from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's projects. Should not be null.
     * @return the list of projects for the given company found in the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the company is null.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     */
    @WebMethod
    public List < Project > getProjectsForCompany(Company company) throws LookupServiceException,
            AuthorizationFailedException;
}
