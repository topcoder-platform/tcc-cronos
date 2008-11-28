/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.accuracytests;

import com.topcoder.jira.JiraManager;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraProjectRetrievalResult;

import java.util.List;
import java.util.ArrayList;

/**
 * Mock implementation of JiraManager.
 * 
 * @author zsudraco
 * @version 1.0
 */
public class MockJiraManager implements JiraManager {
	public MockJiraManager() {
	}

	/**
	 * Performs login of the given user with the given password to JIRA system.
	 * 
	 * @param username
	 *            name of the JIRA user. Can not be null or empty string.
	 * @param password
	 *            password of the JIRA user. Can not be null or empty string.
	 * @return an authentication token that can then be used on further SOAP calls to verify whether the user has access
	 *         to the areas being manipulated. Will not be null or empty string.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if name and password is an invalid combination.
	 */
	public String login(String username, String password) throws JiraManagerException {
		checkEqual("username", username);
		checkEqual("password", password);
		return "success";
	}

	private void checkEqual(Object obj1, Object obj2) throws JiraManagerException {
		if (!obj1.equals(obj2)) {
			throw new JiraManagerException("Invalid value.");
		}
	}

	/**
	 * Performs logout from JIRA system using the given authentication token.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @throws IllegalArgumentException
	 *             if argument is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public void logout(String token) throws JiraManagerException {
		checkEqual("token", token);
	}

	/**
	 * Creates new JIRA project using the given authentication token, project, version and type.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param project
	 *            attributes of the project to create. Can not be null.
	 * @param version
	 *            attributes of the version to add. Can not be null.
	 * @param type
	 *            type used to retrieve permission, notification and issue security schemes. Can not be null.
	 * @return non-null JIRA project creation result.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or authentication token is empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraProjectValidationException
	 *             if project is incomplete or malformed.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public JiraProjectCreationResult createProject(String token, JiraProject project, JiraVersion version,
					ComponentType type) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("project", project.getName());
		checkEqual("1.0", version.getName());
		checkEqual(ComponentType.CUSTOM_COMPONENT, type);
		JiraProjectCreationResult res = new JiraProjectCreationResult();
		res.setProject(project);
		res.setVersion(version);
		return res;
	}

	/**
	 * Adds version to the JIRA project.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param project
	 *            project where version should be added. Can not be null.
	 * @param version
	 *            attributes of the version to add. Can not be null.
	 * @return non-null JIRA project creation result.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or authentication token is empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public JiraProjectCreationResult addVersionToProject(String token, JiraProject project, JiraVersion version)
					throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("project", project.getName());
		checkEqual("1.0", version.getName());
		JiraProjectCreationResult res = new JiraProjectCreationResult();
		res.setProject(project);
		res.setVersion(version);
		return res;
	}

	/**
	 * Retrieves project with the given key.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectKey
	 *            key used to retrieve JIRA project. Can not be null or empty string.
	 * @return non-null JIRA project retrieval result.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public JiraProjectRetrievalResult getProject(String token, String projectKey) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("projectKey", projectKey);
		JiraProjectRetrievalResult res = new JiraProjectRetrievalResult();
		JiraProject project = new JiraProject();
		project.setKey(projectKey);
		res.setProject(project);
		return res;
	}

	/**
	 * Retrieves project with the given key and version name.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectKey
	 *            key used to retrieve JIRA project. Can not be null or empty string.
	 * @param versionName
	 *            version used to retrieve JIRA project. Can not be null or empty string.
	 * @return non-null JIRA project retrieval result.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public JiraProjectRetrievalResult getProject(String token, String projectKey, String versionName)
					throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("projectKey", projectKey);
		checkEqual("versionName", versionName);
		JiraProjectRetrievalResult res = new JiraProjectRetrievalResult();
		JiraProject project = new JiraProject();
		project.setKey(projectKey);
		res.setProject(project);
		JiraVersion version = new JiraVersion();
		version.setName(versionName);
		res.setVersion(version);
		return res;
	}

	/**
	 * Retrieves all versions of the given project.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectKey
	 *            key of the project to retrieve versions for. Can not be null or empty string.
	 * @return non-null list of JIRA project versions.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public List<JiraVersion> getProjectVersions(String token, String projectKey) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("projectKey", projectKey);

		List<JiraVersion> res = new ArrayList<JiraVersion>();
		JiraVersion version = new JiraVersion();
		version.setName(projectKey);
		res.add(version);
		return res;
	}

	/**
	 * Checks whether the project with the given name exists in JIRA system.
	 * <p>
	 * Check is case-insensitive.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectName
	 *            name of the project to check. Can not be null or empty string.
	 * @return true if project with the given name exists and false otherwise.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public boolean projectExists(String token, String projectName) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("projectName", projectName);
		return false;
	}

	/**
	 * Retrieves project with the given id.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectId
	 *            identifier used to retrieve JIRA project. Can not be null.
	 * @return non-null JIRA project retrieval result.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or authentication token is empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public JiraProjectRetrievalResult getProject(String token, Long projectId) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual(new Long(123), projectId);
		JiraProjectRetrievalResult res = new JiraProjectRetrievalResult();
		JiraProject project = new JiraProject();
		project.setId(projectId.toString());
		res.setProject(project);
		return res;
	}

	/**
	 * Removes project with the given key.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectKey
	 *            key of the project to remove. Can not be null or empty string.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public void deleteProject(String token, String projectKey) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("projectKey", projectKey);
	}

	/**
	 * Updates project in JIRA system.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param project
	 *            entity with properties used to update JIRA project. Can not be null.
	 * @return non-null entity with updated properties of the JIRA project.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or authentication token is empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraProjectValidationException
	 *             if project is incomplete or malformed.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public JiraProject updateProject(String token, JiraProject project) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("project", project.getName());
		return project;
	}

	/**
	 * Releases specified version of the JIRA project.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectKey
	 *            key of the project to release. Can not be null or empty string.
	 * @param version
	 *            version of the project to release. Can not be null.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or one of the string arguments is empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public void releaseVersion(String token, String projectKey, JiraVersion version) throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("projectKey", projectKey);
		checkEqual("1.0", version.getName());
	}

	/**
	 * Archives or unarchives specified version of the JIRA project.
	 * 
	 * @param token
	 *            SOAP authentication token. It's created by method <code>login</code>. Can not be null or empty
	 *            string.
	 * @param projectKey
	 *            key of the project to process. Can not be null or empty string.
	 * @param versionName
	 *            name of the version to process. Can not be null or empty string.
	 * @param archive
	 *            flag that indicates whether the version will be archived or unarchived. True means that version will
	 *            be archived and false that version will be unarchived.
	 * @throws IllegalArgumentException
	 *             if one of the arguments is null or empty.
	 * @throws JiraConnectionException
	 *             if unable to reestablish connection to JIRA system.
	 * @throws JiraManagerException
	 *             if general error occurs during this operation.
	 * @throws JiraNotAuthorizedException
	 *             if user doesn't have access to the areas being manipulated.
	 * @throws JiraSecurityTokenExpiredException
	 *             if authentication token expires or becomes stale.
	 */
	public void archiveVersion(String token, String projectKey, String versionName, boolean archive)
					throws JiraManagerException {
		checkEqual("token", token);
		checkEqual("projectKey", projectKey);
		checkEqual("versionName", versionName);
		checkEqual(true, archive);
	}
}
