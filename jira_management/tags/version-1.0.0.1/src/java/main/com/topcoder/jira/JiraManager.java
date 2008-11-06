/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import java.util.List;

import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

/**
 * This interface defines functionality for logging a user in and out of JIRA system, as well as
 * creating new project and versions, as well as retrieving information about created projects and
 * versions.
 * <p>
 * Thread-safety. Implementations of this interface are expected to be thread-safe.
 *
 * @author Mafy, agh
 * @version 1.0
 */
public interface JiraManager {

    /**
     * Performs login of the given user with the given password to JIRA system.
     *
     * @param username name of the JIRA user. Can not be null or empty string.
     * @param password password of the JIRA user. Can not be null or empty string.
     * @return an authentication token that can then be used on further SOAP calls to verify whether
     *         the user has access to the areas being manipulated. Will not be null or empty string.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if name and password is an invalid combination.
     */
    String login(String username, String password) throws JiraManagerException;

    /**
     * Performs logout from JIRA system using the given authentication token.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @throws IllegalArgumentException if argument is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    void logout(String token) throws JiraManagerException;

    /**
     * Creates new JIRA project using the given authentication token, project, version and type.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param project attributes of the project to create. Can not be null.
     * @param version attributes of the version to add. Can not be null.
     * @param type type used to retrieve permission, notification and issue security schemes. Can
     *        not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException if one of the arguments is null or authentication token is
     *         empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraProjectValidationException if project is incomplete or malformed.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    JiraProjectCreationResult createProject(String token, JiraProject project, JiraVersion version,
        ComponentType type) throws JiraManagerException;

    /**
     * Adds version to the JIRA project.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param project project where version should be added. Can not be null.
     * @param version attributes of the version to add. Can not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException if one of the arguments is null or authentication token is
     *         empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    JiraProjectCreationResult addVersionToProject(String token, JiraProject project, JiraVersion version)
        throws JiraManagerException;

    /**
     * Retrieves project with the given key.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectKey key used to retrieve JIRA project. Can not be null or empty string.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    JiraProjectRetrievalResult getProject(String token, String projectKey) throws JiraManagerException;

    /**
     * Retrieves project with the given key and version name.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectKey key used to retrieve JIRA project. Can not be null or empty string.
     * @param versionName version used to retrieve JIRA project. Can not be null or empty string.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    JiraProjectRetrievalResult getProject(String token, String projectKey, String versionName)
        throws JiraManagerException;

    /**
     * Retrieves all versions of the given project.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectKey key of the project to retrieve versions for. Can not be null or empty
     *        string.
     * @return non-null list of JIRA project versions.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    List<JiraVersion> getProjectVersions(String token, String projectKey) throws JiraManagerException;

    /**
     * Checks whether the project with the given name exists in JIRA system.
     * <p>
     * Check is case-insensitive.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectName name of the project to check. Can not be null or empty string.
     * @return true if project with the given name exists and false otherwise.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    boolean projectExists(String token, String projectName) throws JiraManagerException;

    /**
     * Retrieves project with the given id.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectId identifier used to retrieve JIRA project. Can not be null.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException if one of the arguments is null or authentication token is
     *         empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    JiraProjectRetrievalResult getProject(String token, Long projectId) throws JiraManagerException;

    /**
     * Removes project with the given key.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectKey key of the project to remove. Can not be null or empty string.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    void deleteProject(String token, String projectKey) throws JiraManagerException;

    /**
     * Updates project in JIRA system.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param project entity with properties used to update JIRA project. Can not be null.
     * @return non-null entity with updated properties of the JIRA project.
     * @throws IllegalArgumentException if one of the arguments is null or authentication token is
     *         empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraProjectValidationException if project is incomplete or malformed.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    JiraProject updateProject(String token, JiraProject project) throws JiraManagerException;

    /**
     * Releases specified version of the JIRA project.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectKey key of the project to release. Can not be null or empty string.
     * @param version version of the project to release. Can not be null.
     * @throws IllegalArgumentException if one of the arguments is null or one of the string
     *         arguments is empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    void releaseVersion(String token, String projectKey, JiraVersion version) throws JiraManagerException;

    /**
     * Archives or unarchives specified version of the JIRA project.
     *
     * @param token SOAP authentication token. It's created by method <code>login</code>. Can not
     *        be null or empty string.
     * @param projectKey key of the project to process. Can not be null or empty string.
     * @param versionName name of the version to process. Can not be null or empty string.
     * @param archive flag that indicates whether the version will be archived or unarchived. True
     *        means that version will be archived and false that version will be unarchived.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraConnectionException if unable to reestablish connection to JIRA system.
     * @throws JiraManagerException if general error occurs during this operation.
     * @throws JiraNotAuthorizedException if user doesn't have access to the areas being
     *         manipulated.
     * @throws JiraSecurityTokenExpiredException if authentication token expires or becomes stale.
     */
    void archiveVersion(String token, String projectKey, String versionName, boolean archive)
        throws JiraManagerException;
}
