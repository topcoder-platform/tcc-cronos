/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.delegate;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.JiraManager;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.JiraProjectValidationException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.managers.JiraManagerConfigurationException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.webservices.JiraManagementService;
import com.topcoder.jira.webservices.JiraServiceConnectionException;
import com.topcoder.jira.webservices.JiraServiceException;
import com.topcoder.jira.webservices.JiraServiceNotAuthorizedException;
import com.topcoder.jira.webservices.JiraServiceProjectValidationException;
import com.topcoder.jira.webservices.JiraServiceSecurityTokenExpiredException;
import com.topcoder.jira.webservices.Util;
import com.topcoder.jira.webservices.client.JiraManagementServiceClient;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;

import javax.xml.ws.WebServiceException;
import java.util.List;

/**
 * A simple business delegate implementation of the JiraManager that uses the JiraManagementServiceClient as the lookup
 * service for the JiraManagementService proxies to be used to communicate with the Web Services. All methods are
 * implemented and all methods just delegate to the proxy.
 *
 * Any exceptions in the proxy are mapped to the Jira Management exceptions as per CS 1.3.2.
 *
 * It uses the Configuration API and Persistence components to obtain configuration information. It uses the Object
 * Factory and its Configuration API-backed specification factory to instantiate the service client, and the Log Manager
 * to perform logging of activity and errors.
 *
 * Thread Safety This class is immutable and effectively thread-safe, since although it deals with non-thread-safe
 * entities, these are not expected to be used by multiple threads.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class JiraManagerWebServiceDelegate implements JiraManager {
    /**
     * Represents the default configuration path to create the ConfigurationFileManager. It will be used in the
     * parameterless constructor.
     */
    public static final String DEFAULT_CONFIG_PATH =
            "com/topcoder/jira/webservices/delegate/JiraManagerWebServiceDelegate.properties";

    /**
     * Represents the default namespace to obtain the configuration object for the object factory. It will be used in
     * the parameterless constructor.
     */
    public static final String DEFAULT_NAMESPACE =
            "com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegate";

    /**
     * It will be used to log all activity. It is set in the constructor It is used in all methods. It may be null to
     * indicate no logging to take place. Once set, it will not change.
     */
    private final Log log;

    /**
     * The client used to obtain the service instance to communicate with the Web Services. It is set in the constructor
     * It is used in all methods. It will not be null. .Once set, it will not change.
     */
    private final JiraManagementServiceClient serviceClient;

    /**
     * Configures this instance from default configuration.
     *
     * @throws JiraManagerConfigurationException
     *          if cant create the file manager or extract the configuration from it, or if the configuration does not
     *          have required properties, or if there is an error while accessing these properties.
     */
    public JiraManagerWebServiceDelegate() {
        this(DEFAULT_CONFIG_PATH, DEFAULT_NAMESPACE);
    }

    /**
     * Configures this instance from configuration.
     *
     * @param filename  The path to the configuration file
     * @param namespace The namespace of the configuration to use
     * @throws IllegalArgumentException if either param is null/empty
     * @throws JiraManagerConfigurationException
     *                                  if cant create the file manager or extract the configuration from it, or if the
     *                                  configuration does not have required properties, or if there is an error while
     *                                  accessing these properties.
     */
    public JiraManagerWebServiceDelegate(String filename, String namespace) {
        this(Util.readConfiguration(filename, namespace), true);
    }

    /**
     * Configures this instance from configuration.
     *
     * @param configurationObject configuration to initialize instance
     * @throws IllegalArgumentException if parameter is null
     * @throws JiraManagerConfigurationException
     *                                  if instance can't be created for some reason.
     */
    public JiraManagerWebServiceDelegate(ConfigurationObject configurationObject) {
        this(configurationObject, false);
    }

    /**
     * Private constructor invoked from several public ones.
     *
     * @param configurationObject configuration to initialize instance
     * @param skipDefault         true if configuration was read from file and thus 'default' child need to be skipped
     *                            to read properties, false otherwise
     * @throws JiraManagerConfigurationException
     *          if instance can't be created for some reason.
     */
    private JiraManagerWebServiceDelegate(ConfigurationObject configurationObject, boolean skipDefault) {
        Util.checkNull("configurationObject", configurationObject);

        // create service client
        ObjectFactory factory = Util.createFactory(configurationObject);
        if (skipDefault) {
            configurationObject = Util.getChild(configurationObject, "default");
        }
        String clientKey = Util.getProperty(configurationObject, "service_client_key", true);
        serviceClient = Util.createObject(factory, clientKey, JiraManagementServiceClient.class);

        // set logger
        String logName = Util.getProperty(configurationObject, "log_name", false);
        if (logName != null) {
            log = LogManager.getLog(logName);
        } else {
            log = null;
        }
    }

    /**
     * Simply sets the fields with the passed namesake parameters.
     *
     * @param serviceClient the JiraManagementServiceClient instance to use
     * @param log           the Log to use to log activity
     * @throws IllegalArgumentException if serviceClient is null
     */
    public JiraManagerWebServiceDelegate(JiraManagementServiceClient serviceClient, Log log) {
        Util.checkNull("serviceClient", serviceClient);

        this.serviceClient = serviceClient;
        this.log = log;
    }

    /**
     * Performs the login of the given user with the given password to JIRA.
     *
     * @param username the user name that should be logged in JIRA. Should not be null or empty String.
     * @param password the password for the given user name that should be logged in JIRA. Should not be null or empty
     *                 String.
     * @return an authentication token that can then be used on further SOAP calls to verify whether the user has access
     *         to the areas being manipulated. Will not be null or empty string.
     * @throws IllegalArgumentException   if the userName or password are null or empty Strings.
     * @throws JiraConnectionException    if an there is an issue while attempting to reestablish the connection to
     *                                    JIRA.
     * @throws JiraNotAuthorizedException if the user or password are invalid.
     * @throws JiraManagerException       if any error occurs while performing this operation.
     */
    public String login(String username, String password) throws JiraManagerException {
        Util.logEnter(log, "login", username, password);
        Util.checkString(log, "username", username);
        Util.checkString(log, "password", password);

        try {
            String result = getJiraManagementServicePort().login(username, password);
            Util.logExit(log, "login", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the log out from the JIRA using the given token.
     *
     * @param token the token to be used to log out from the JIRA. This token is created by
     *              login(userName,password):String method and is used to verify if the user has access to the specific
     *              areas being manipulated. Should not be null or empty String.
     * @throws IllegalArgumentException   if the token is null or empty String.
     * @throws JiraConnectionException    if an there is an issue while attempting to reestablish the connection to
     *                                    JIRA.
     * @throws JiraNotAuthorizedException if a user attempts an unauthorized call to JIRA (the token used to
     *                                    authenticate is invalid or the user does not have access to the specific areas
     *                                    being manipulated).
     * @throws JiraSecurityTokenExpiredException
     *                                    if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException       if any error occurs while performing this operation.
     */
    public void logout(String token) throws JiraManagerException {
        Util.logEnter(log, "logout", token);
        Util.checkString(log, "token", token);

        try {
            getJiraManagementServicePort().logout(token);
            Util.logExit(log, "logout");
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the creation of a JIRA project using the given token, project, version and type.
     *
     * @param token   the token to be used to create a JIRA project. This token is created by
     *                login(userName,password):String method and is used to verify if the user has access to the
     *                specific areas being manipulated. Should not be null or empty String.
     * @param project the project to be used to create a JIRA project. Should not be null.
     * @param version the version to be used to create a JIRA project. Should not be null.
     * @param type    the type to be used to create a JIRA project. Should not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException   if any argument is null or any String argument is empty.
     * @throws JiraConnectionException    if an there is an issue while attempting to reestablish the connection to
     *                                    JIRA.
     * @throws JiraNotAuthorizedException if a user attempts an unauthorized call to JIRA (the token used to
     *                                    authenticate is invalid or the user does not have access to the specific areas
     *                                    being manipulated).
     * @throws JiraSecurityTokenExpiredException
     *                                    if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException       if any error occurs while performing this operation.
     */
    public JiraProjectCreationResult createProject(
            String token, JiraProject project, JiraVersion version, ComponentType type) throws JiraManagerException {
        Util.logEnter(log, "createProject", token, project, version, type);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "project", project);
        Util.checkNull(log, "version", version);
        Util.checkNull(log, "type", type);

        try {
            JiraProjectCreationResult result =
                    getJiraManagementServicePort().createProject(token, project, version, type);
            Util.logExit(log, "createProject", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the addition of a version to a JIRA project using the given token, project and version.
     *
     * @param token   the token to be used to add a version to a JIRA project. This token is created by
     *                login(userName,password):String method and is used to verify if the user has access to the
     *                specific areas being manipulated. Should not be null or empty String.
     * @param project the project to be used to add a version for a JIRA project. Should not be null.
     * @param version the version to be used to add a version for a JIRA project. Should not be null.
     * @return non-null JIRA project creation result.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraConnectionException        if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraNotAuthorizedException     if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraProjectValidationException if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException           if any error occurs while performing this operation.
     */
    public JiraProjectCreationResult addVersionToProject(String token, JiraProject project, JiraVersion version) throws
            JiraManagerException {
        Util.logEnter(log, "addVersionToProject", token, project, version);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "project", project);
        Util.checkNull(log, "version", version);

        try {
            JiraProjectCreationResult result =
                    getJiraManagementServicePort().addVersionToProject(token, project, version);
            Util.logExit(log, "addVersionToProject", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the retrieval of a JIRA project using the given token and with the projectKey.
     *
     * @param token      the token to be used to retrieve a JIRA project. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to retrieve a JIRA project. Should not be null or empty String.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException   if any argument is null or any String argument is empty.
     * @throws JiraConnectionException    if an there is an issue while attempting to reestablish the connection to
     *                                    JIRA.
     * @throws JiraNotAuthorizedException if a user attempts an unauthorized call to JIRA (the token used to
     *                                    authenticate is invalid or the user does not have access to the specific areas
     *                                    being manipulated).
     * @throws JiraSecurityTokenExpiredException
     *                                    if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException       if any error occurs while performing this operation.
     */
    public JiraProjectRetrievalResult getProject(String token, String projectKey) throws JiraManagerException {
        Util.logEnter(log, "getProject", token, projectKey);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);

        try {
            JiraProjectRetrievalResult result = getJiraManagementServicePort().getProject(token, projectKey);
            Util.logExit(log, "getProject", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the retrieval of a JIRA project using the given token and with the projectKey and versionName.
     *
     * @param token      the token to be used to retrieve a JIRA project. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to retrieve a JIRA project. Should not be null or empty String.
     * @param version    the version name to be used to retrieve a JIRA project. Should not be null or empty String.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException   if any argument is null or any String argument is empty.
     * @throws JiraConnectionException    if an there is an issue while attempting to reestablish the connection to
     *                                    JIRA.
     * @throws JiraNotAuthorizedException if a user attempts an unauthorized call to JIRA (the token used to
     *                                    authenticate is invalid or the user does not have access to the specific areas
     *                                    being manipulated).
     * @throws JiraSecurityTokenExpiredException
     *                                    if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException       if any error occurs while performing this operation.
     */
    public JiraProjectRetrievalResult getProject(String token, String projectKey, String version) throws
            JiraManagerException {
        Util.logEnter(log, "getProject", token, projectKey, version);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);
        Util.checkString(log, "version", version);

        try {
            JiraProjectRetrievalResult result = getJiraManagementServicePort().getProject(token, projectKey, version);
            Util.logExit(log, "getProject", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the retrieval of JIRA project versions using the given token and with the projectKey.
     *
     * @param token      the token to be used to retrieve JIRA project versions. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to retrieve JIRA project versions. Should not be null or empty
     *                   String.
     * @return non-null list of JIRA project versions.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraConnectionException        if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraNotAuthorizedException     if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraProjectValidationException if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException           if any error occurs while performing this operation.
     */
    public List<JiraVersion> getProjectVersions(String token, String projectKey) throws JiraManagerException {
        Util.logEnter(log, "getProjectVersions", token, projectKey);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);

        try {
            List<JiraVersion> result = getJiraManagementServicePort().getProjectVersions(token, projectKey);
            Util.logExit(log, "getProjectVersions", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the checking for existence of a JIRA project using the given token and projectName.
     *
     * @param token       the token to be used to check for existence of a JIRA project. This token is created by
     *                    login(userName,password):String method and is used to verify if the user has access to the
     *                    specific areas being manipulated. Should not be null or empty String.
     * @param projectName the project name to be used to check for existence of a JIRA project. Should not be null or
     *                    empty String.
     * @return true if project with the given name exists and false otherwise.
     * @throws IllegalArgumentException   if any argument is null or any String argument is empty.
     * @throws JiraConnectionException    if an there is an issue while attempting to reestablish the connection to
     *                                    JIRA.
     * @throws JiraNotAuthorizedException if a user attempts an unauthorized call to JIRA (the token used to
     *                                    authenticate is invalid or the user does not have access to the specific areas
     *                                    being manipulated).
     * @throws JiraSecurityTokenExpiredException
     *                                    if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException       if any error occurs while performing this operation.
     */
    public boolean projectExists(String token, String projectName) throws JiraManagerException {
        Util.logEnter(log, "projectExists", token, projectName);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectName", projectName);

        try {
            boolean result = getJiraManagementServicePort().projectExists(token, projectName);
            Util.logExit(log, "projectExists", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the retrieval of a JIRA project using the given token and with the projectId.
     *
     * @param token     the token to be used to retrieve a JIRA project. This token is created by
     *                  login(userName,password):String method and is used to verify if the user has access to the
     *                  specific areas being manipulated. Should not be null or empty String.
     * @param projectId the project id to be used to retrieve a JIRA project. Should not be null.
     * @return non-null JIRA project retrieval result.
     * @throws IllegalArgumentException   if any argument is null or any String argument is empty.
     * @throws JiraConnectionException    if an there is an issue while attempting to reestablish the connection to
     *                                    JIRA.
     * @throws JiraNotAuthorizedException if a user attempts an unauthorized call to JIRA (the token used to
     *                                    authenticate is invalid or the user does not have access to the specific areas
     *                                    being manipulated).
     * @throws JiraSecurityTokenExpiredException
     *                                    if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException       if any error occurs while performing this operation.
     */
    public JiraProjectRetrievalResult getProject(String token, Long projectId) throws JiraManagerException {
        Util.logEnter(log, "getProject", token, projectId);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "projectId", projectId);

        try {
            JiraProjectRetrievalResult result = getJiraManagementServicePort().getProject(token, projectId);
            Util.logExit(log, "getProject", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the deletion of a JIRA project using the given token and with the projectKey.
     *
     * @param token      the token to be used to delete a JIRA project. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to delete a JIRA project. Should not be null or empty String.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraConnectionException        if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraNotAuthorizedException     if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraProjectValidationException if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException           if any error occurs while performing this operation.
     */
    public void deleteProject(String token, String projectKey) throws JiraManagerException {
        Util.logEnter(log, "deleteProject", token, projectKey);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);

        try {
            getJiraManagementServicePort().deleteProject(token, projectKey);
            Util.logExit(log, "deleteProject");
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the update of a JIRA project using the given token and project.
     *
     * @param token   the token to be used to update a JIRA project. This token is created by
     *                login(userName,password):String method and is used to verify if the user has access to the
     *                specific areas being manipulated. Should not be null or empty String.
     * @param project the project to be used to update a JIRA project. Should not be null.
     * @return non-null entity with updated properties of the JIRA project.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraConnectionException        if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraNotAuthorizedException     if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraProjectValidationException if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException           if any error occurs while performing this operation.
     */
    public JiraProject updateProject(String token, JiraProject project) throws JiraManagerException {
        Util.logEnter(log, "updateProject", token, project);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "project", project);

        try {
            JiraProject result = getJiraManagementServicePort().updateProject(token, project);
            Util.logExit(log, "updateProject", result);
            return result;
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the release of a JIRA version using the given token and with the projectKey and version.
     *
     * @param token      the token to be used to release a JIRA version. This token is created by
     *                   login(userName,password):String method and is used to verify if the user has access to the
     *                   specific areas being manipulated. Should not be null or empty String.
     * @param projectKey the project key to be used to release a JIRA version. Should not be null or empty String.
     * @param version    the version to be used to release a JIRA version. Should not be null.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraConnectionException        if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraNotAuthorizedException     if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraProjectValidationException if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException           if any error occurs while performing this operation. Also it is thrown if
     *                                        the version does not exists.
     */
    public void releaseVersion(String token, String projectKey, JiraVersion version) throws JiraManagerException {
        Util.logEnter(log, "releaseVersion", token, projectKey, version);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);
        Util.checkNull(log, "version", version);

        try {
            getJiraManagementServicePort().releaseVersion(token, projectKey, version);
            Util.logExit(log, "releaseVersion");
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the archive of a JIRA version using the given token and with the projectKey, versionName and archive
     * flag.
     *
     * @param token       the token to be used to archive a JIRA version. This token is created by
     *                    login(userName,password):String method and is used to verify if the user has access to the
     *                    specific areas being manipulated. Should not be null or empty String.
     * @param projectKey  the project key to be used to archive a JIRA version. Should not be null or empty String.
     * @param versionName the version name to be used to archive a JIRA version. Should not be null.
     * @param archive     the archive flag to be used to archive a JIRA version. If true archive; otherwise don't
     *                    archive.
     * @throws IllegalArgumentException       if any argument is null or any String argument is empty.
     * @throws JiraConnectionException        if an there is an issue while attempting to reestablish the connection to
     *                                        JIRA.
     * @throws JiraNotAuthorizedException     if a user attempts an unauthorized call to JIRA (the token used to
     *                                        authenticate is invalid or the user does not have access to the specific
     *                                        areas being manipulated).
     * @throws JiraProjectValidationException if the needed JIRA project is invalid (it does not exists in JIRA).
     * @throws JiraSecurityTokenExpiredException
     *                                        if the token obtained for authorization expires or becomes stale.
     * @throws JiraManagerException           if any error occurs while performing this operation. Also it is thrown if
     *                                        the version does not exists.
     */
    public void archiveVersion(String token, String projectKey, String versionName, boolean archive) throws
            JiraManagerException {
        Util.logEnter(log, "archiveVersion", token, projectKey, versionName, archive);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);
        Util.checkString(log, "versionName", versionName);

        try {
            getJiraManagementServicePort().archiveVersion(token, projectKey, versionName, archive);
            Util.logExit(log, "archiveVersion");
        } catch (JiraServiceException e) {
            throw processError(e);
        }
    }

    /**
     * Simple routine to process caught service exceptions, log them and convert to corresponding manager errors.
     *
     * @param error error to process
     * @return manager error to throw
     */
    private JiraManagerException processError(JiraServiceException error) {
        // log exception, if necessary
        Util.logError(log, error);

        // convert it
        if (error instanceof JiraServiceConnectionException) {
            return new JiraConnectionException(error.getMessage());
        } else if (error instanceof JiraServiceNotAuthorizedException) {
            return new JiraNotAuthorizedException(error.getMessage());
        } else if (error instanceof JiraServiceSecurityTokenExpiredException) {
            return new JiraSecurityTokenExpiredException(error.getMessage());
        } else if (error instanceof JiraServiceProjectValidationException) {
            return new JiraProjectValidationException(error.getMessage());
        } else {
            return new JiraManagerException(error.getMessage());
        }
    }

    /**
     * Helper to retrieve JiraManagementService from service client and handle possible errors.
     *
     * @return JiraManagementService implementation
     * @throws JiraManagerException if implementation can't be retrieved for some reason
     */
    private JiraManagementService getJiraManagementServicePort() throws JiraManagerException {
        try {
            return serviceClient.getJiraManagementServicePort();
        } catch (WebServiceException e) {
            Util.logError(log, e);
            throw new JiraManagerException("Unable to create JiraManagementService proxy.", e);
        }
    }
}
