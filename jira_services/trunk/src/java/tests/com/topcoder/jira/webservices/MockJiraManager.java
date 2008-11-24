/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.JiraManager;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.JiraProjectValidationException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock for JiraManager interface, used mostly in demo, on server side only.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockJiraManager implements JiraManager {
    /**
     * Performs login of the given user with the given password to JIRA system.
     *
     * @param username name of the JIRA user. Can not be null or empty string.
     * @param password password of the JIRA user. Can not be null or empty string.
     * @return an authentication token that can then be used on further SOAP calls to verify whether the user has access
     *         to the areas being manipulated. Will not be null or empty string.
     * @throws IllegalArgumentException   if one of the arguments is null or empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if name and password is an invalid combination.
     */
    public String login(String username, String password) throws JiraManagerException {
        return username + ":" + password;
    }

    /**
     * Performs logout from JIRA system using the given authentication token.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *              string.
     * @throws IllegalArgumentException if argument is null or empty.
     * @throws JiraConnectionException  if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException     if general error occurs during this operation.
     * @throws JiraSecurityTokenExpiredException
     *                                  if authentication token expires or becomes stale.
     */
    public void logout(String token) throws JiraManagerException {
        throw new IllegalArgumentException("error");
    }

    /**
     * Creates new JIRA project using the given authentication token, project, version and type.
     *
     * @param token   SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                string.
     * @param project attributes of the project to create. Can not be null.
     * @param version attributes of the version to add. Can not be null.
     * @param type    type used to retrieve permission, notification and issue security schemes. Can not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException       if one of the arguments is null or authentication token is empty.
     * @throws JiraConnectionException        if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException           if general error occurs during this operation.
     * @throws JiraNotAuthorizedException     if user doesn't have access to the areas being manipulated.
     * @throws JiraProjectValidationException if project is incomplete or malformed.
     * @throws JiraSecurityTokenExpiredException
     *                                        if authentication token expires or becomes stale.
     */
    public JiraProjectCreationResult createProject(
            String token, JiraProject project, JiraVersion version, ComponentType type) throws JiraManagerException {
        JiraProjectCreationResult result = new JiraProjectCreationResult();
        result.setProject(new JiraProject("a", "b", "c", "d", "e"));
        return result;
    }

    /**
     * Adds version to the JIRA project.
     *
     * @param token   SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                string.
     * @param project project where version should be added. Can not be null.
     * @param version attributes of the version to add. Can not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException   if one of the arguments is null or authentication token is empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public JiraProjectCreationResult addVersionToProject(String token, JiraProject project, JiraVersion version) throws
            JiraManagerException {
        return new JiraProjectCreationResult();
    }

    /**
     * Retrieves project with the given key.
     *
     * @param token      SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                   string.
     * @param projectKey key used to retrieve JIRA project. Can not be null or empty string.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException   if one of the arguments is null or empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public JiraProjectRetrievalResult getProject(String token, String projectKey) throws JiraManagerException {
        return new JiraProjectRetrievalResult();
    }

    /**
     * Retrieves project with the given key and version name.
     *
     * @param token       SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                    string.
     * @param projectKey  key used to retrieve JIRA project. Can not be null or empty string.
     * @param versionName version used to retrieve JIRA project. Can not be null or empty string.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException   if one of the arguments is null or empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public JiraProjectRetrievalResult getProject(String token, String projectKey, String versionName) throws
            JiraManagerException {
        return new JiraProjectRetrievalResult();
    }

    /**
     * Retrieves all versions of the given project.
     *
     * @param token      SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                   string.
     * @param projectKey key of the project to retrieve versions for. Can not be null or empty string.
     * @return non-null list of JIRA project versions.
     * @throws IllegalArgumentException   if one of the arguments is null or empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public List<JiraVersion> getProjectVersions(String token, String projectKey) throws JiraManagerException {
        List<JiraVersion> result = new ArrayList<JiraVersion>();
        result.add(new JiraVersion());
        result.add(new JiraVersion());
        result.add(new JiraVersion());
        return result;
    }

    /**
     * Checks whether the project with the given name exists in JIRA system. <p> Check is case-insensitive.
     *
     * @param token       SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                    string.
     * @param projectName name of the project to check. Can not be null or empty string.
     * @return true if project with the given name exists and false otherwise.
     * @throws IllegalArgumentException   if one of the arguments is null or empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public boolean projectExists(String token, String projectName) throws JiraManagerException {
        return true;
    }

    /**
     * Retrieves project with the given id.
     *
     * @param token     SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                  string.
     * @param projectId identifier used to retrieve JIRA project. Can not be null.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException   if one of the arguments is null or authentication token is empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public JiraProjectRetrievalResult getProject(String token, Long projectId) throws JiraManagerException {
        return new JiraProjectRetrievalResult();
    }

    /**
     * Removes project with the given key.
     *
     * @param token      SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                   string.
     * @param projectKey key of the project to remove. Can not be null or empty string.
     * @throws IllegalArgumentException   if one of the arguments is null or empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public void deleteProject(String token, String projectKey) throws JiraManagerException {
    }

    /**
     * Updates project in JIRA system.
     *
     * @param token   SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                string.
     * @param project entity with properties used to update JIRA project. Can not be null.
     * @return non-null entity with updated properties of the JIRA project.
     * @throws IllegalArgumentException       if one of the arguments is null or authentication token is empty.
     * @throws JiraConnectionException        if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException           if general error occurs during this operation.
     * @throws JiraNotAuthorizedException     if user doesn't have access to the areas being manipulated.
     * @throws JiraProjectValidationException if project is incomplete or malformed.
     * @throws JiraSecurityTokenExpiredException
     *                                        if authentication token expires or becomes stale.
     */
    public JiraProject updateProject(String token, JiraProject project) throws JiraManagerException {
        return new JiraProject();
    }

    /**
     * Releases specified version of the JIRA project.
     *
     * @param token      SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                   string.
     * @param projectKey key of the project to release. Can not be null or empty string.
     * @param version    version of the project to release. Can not be null.
     * @throws IllegalArgumentException   if one of the arguments is null or one of the string arguments is empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public void releaseVersion(String token, String projectKey, JiraVersion version) throws JiraManagerException {
    }

    /**
     * Archives or unarchives specified version of the JIRA project.
     *
     * @param token       SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
     *                    string.
     * @param projectKey  key of the project to process. Can not be null or empty string.
     * @param versionName name of the version to process. Can not be null or empty string.
     * @param archive     flag that indicates whether the version will be archived or unarchived. True means that
     *                    version will be archived and false that version will be unarchived.
     * @throws IllegalArgumentException   if one of the arguments is null or empty.
     * @throws JiraConnectionException    if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException       if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being manipulated.
     * @throws JiraSecurityTokenExpiredException
     *                                    if authentication token expires or becomes stale.
     */
    public void archiveVersion(String token, String projectKey, String versionName, boolean archive) throws
            JiraManagerException {
    }
}
