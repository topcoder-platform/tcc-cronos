/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

/**
 * The business interface that defines all methods for accessing the JIRA Management component. It basically has the
 * same API as the JiraManager interface, and is meant to be used as a pass-through.
 *
 * Thread Safety: implementation supposed to be thread-safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@WebService
public interface JiraManagementService {
    /**
     * Defines the operation that performs the login of the given user with the given password to JIRA.
     *
     * @param username the user name that should be logged in JIRA. Should not be null or empty String.
     * @param password the password for the given user name that should be logged in JIRA. Should not be null or empty
     *                 String.
     * @return an authentication token that can then be used on further SOAP calls to verify whether the user has access
     *         to the areas being manipulated. Will not be null or empty string.
     * @throws IllegalArgumentException       if the userName or password are null or empty Strings.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if the user or password are invalid.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public String login(String username, String password) throws JiraServiceException;

    /**
     * Defines the operation that performs the log out from the JIRA using the given token.
     *
     * @param token the token to be used to log out from the JIRA. This token is created by
     *              login(userName,password):String method and is used to verify if the user has access to the specific
     *              areas being manipulated. Should not be null or empty String.
     * @throws IllegalArgumentException       if the token is null or empty String.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public void logout(String token) throws JiraServiceException;

    /**
     * Defines the operation that performs the creation of a JIRA project using the given token, project, version and
     * type.
     *
     * @param token   the token to be used to create a JIRA project. This token is created by
     *                login(userName,password):String method and is used to verify if the user has access to the
     *                specific areas being manipulated. Should not be null or empty String.
     * @param project the project to be used to create a JIRA project. Should not be null.
     * @param version the version to be used to create a JIRA project. Should not be null.
     * @param type    the type to be used to create a JIRA project. Should not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public JiraProjectCreationResult createProject(
            String token, JiraProject project, JiraVersion version, ComponentType type) throws JiraServiceException;

    /**
     * Defines the operation that performs the addition of a version to a JIRA project using the given token, project
     * and version.
     *
     * @param token   the token to be used to add a version to a JIRA project. This token is created by
     *                login(userName,password):String method and is used to verify if the user has access to the
     *                specific areas being manipulated. Should not be null or empty String.
     * @param project the project to be used to add a version for a JIRA project. Should not be null.
     * @param version the version to be used to add a version for a JIRA project. Should not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceProjectValidationException
     *                                        if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public JiraProjectCreationResult addVersionToProject(String token, JiraProject project, JiraVersion version) throws
            JiraServiceException;

    /**
     * Defines the operation that performs the retrieval of a JIRA project using the given token and with the
     * projectKey.
     *
     * @param token      the token to be used to retrieve a JIRA project. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to retrieve a JIRA project. Should not be null or empty String.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    @WebMethod(operationName = "getProject")
    public JiraProjectRetrievalResult getProject(String token, String projectKey) throws JiraServiceException;

    /**
     * Defines the operation that performs the retrieval of a JIRA project using the given token and with the projectKey
     * and versionName.
     *
     * @param token      the token to be used to retrieve a JIRA project. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to retrieve a JIRA project. Should not be null or empty String.
     * @param version    the version name to be used to retrieve a JIRA project. Should not be null or empty String.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    @WebMethod(operationName = "getProjectByVersion")
    @RequestWrapper(className = "com.topcoder.jira.webservices.jaxws.GetProjectByVersion")
    @ResponseWrapper(className = "com.topcoder.jira.webservices.jaxws.GetProjectByVersionResponse")
    public JiraProjectRetrievalResult getProject(String token, String projectKey, String version) throws
            JiraServiceException;

    /**
     * Defines the operation that performs the retrieval of JIRA project versions using the given token and with the
     * projectKey.
     *
     * @param token      the token to be used to retrieve JIRA project versions. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to retrieve JIRA project versions. Should not be null or empty
     *                   String.
     * @return non-null list of JIRA project versions.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceProjectValidationException
     *                                        if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public List<JiraVersion> getProjectVersions(String token, String projectKey) throws JiraServiceException;

    /**
     * Defines the operation that performs the checking for existence of a JIRA project using the given token and
     * projectName.
     *
     * @param token       the token to be used to check for existence of a JIRA project. This token is created by
     *                    login(userName,password):String method and is used to verify if the user has access to the
     *                    specific areas being manipulated. Should not be null or empty String.
     * @param projectName the project name to be used to check for existence of a JIRA project. Should not be null or
     *                    empty String.
     * @return true if project with the given name exists and false otherwise.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public boolean projectExists(String token, String projectName) throws JiraServiceException;

    /**
     * Defines the operation that performs the retrieval of a JIRA project using the given token and with the
     * projectId.
     *
     * @param token     the token to be used to retrieve a JIRA project. This token is created by
     *                  login(userName,password):String method and is used to verify if the user has access to the
     *                  specific areas being manipulated. Should not be null or empty String.
     * @param projectId the project id to be used to retrieve a JIRA project. Should not be null.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    @WebMethod(operationName = "getProjectById")
    @RequestWrapper(className = "com.topcoder.jira.webservices.jaxws.GetProjectById")
    @ResponseWrapper(className = "com.topcoder.jira.webservices.jaxws.GetProjectByIdResponse")
    public JiraProjectRetrievalResult getProject(String token, Long projectId) throws JiraServiceException;

    /**
     * Defines the operation that performs the deletion of a JIRA project using the given token and with the
     * projectKey.
     *
     * @param token      the token to be used to delete a JIRA project. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to delete a JIRA project. Should not be null or empty String.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceProjectValidationException
     *                                        if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public void deleteProject(String token, String projectKey) throws JiraServiceException;

    /**
     * Defines the operation that performs the update of a JIRA project using the given token and project.
     *
     * @param token   the token to be used to update a JIRA project. This token is created by
     *                login(userName,password):String method and is used to verify if the user has access to the
     *                specific areas being manipulated. Should not be null or empty String.
     * @param project the project to be used to update a JIRA project. Should not be null.
     * @return non-null entity with updated properties of the JIRA project.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceProjectValidationException
     *                                        if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation.
     */
    public JiraProject updateProject(String token, JiraProject project) throws JiraServiceException;

    /**
     * Defines the operation that performs the release of a JIRA version using the given token and with the projectKey
     * and version.
     *
     * @param token      the token to be used to release a JIRA version. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to release a JIRA version. Should not be null or empty String.
     * @param version    the version to be used to release a JIRA version. Should not be null.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceProjectValidationException
     *                                        if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation. Also it is thrown if
     *                                        the version does not exists.
     */
    public void releaseVersion(String token, String projectKey, JiraVersion version) throws JiraServiceException;

    /**
     * Defines the operation that performs the archive of a JIRA version using the given token and with the projectKey,
     * versionName and archive flag.
     *
     * @param token       the token to be used to archive a JIRA version. This token is created by
     *                    login(userName,password):String method and is used to verify if the user has access to the
     *                    specific areas being manipulated. Should not be null or empty String.
     * @param projectKey  the project key to be used to archive a JIRA version. Should not be null or empty String.
     * @param versionName the version name to be used to archive a JIRA version. Should not be null.
     * @param archive     the archive flag to be used to archive a JIRA version. If true archive; otherwise don't
     *                    archive.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraServiceConnectionException if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraServiceNotAuthorizedException
     *                                        if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraServiceProjectValidationException
     *                                        if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraServiceSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraServiceException           if any error occurs while performing this operation. Also it is thrown if
     *                                        the version does not exists.
     */
    public void archiveVersion(String token, String projectKey, String versionName, boolean archive) throws
            JiraServiceException;
}
