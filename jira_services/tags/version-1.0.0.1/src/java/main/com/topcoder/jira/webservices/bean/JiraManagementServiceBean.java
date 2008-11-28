/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.bean;

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
import com.topcoder.jira.webservices.JiraManagementServiceLocal;
import com.topcoder.jira.webservices.JiraManagementServiceRemote;
import com.topcoder.jira.webservices.JiraServiceConfigurationException;
import com.topcoder.jira.webservices.JiraServiceConnectionException;
import com.topcoder.jira.webservices.JiraServiceException;
import com.topcoder.jira.webservices.JiraServiceNotAuthorizedException;
import com.topcoder.jira.webservices.JiraServiceProjectValidationException;
import com.topcoder.jira.webservices.JiraServiceSecurityTokenExpiredException;
import com.topcoder.jira.webservices.Util;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;
import java.util.List;

/**
 * The stateless session bean that implements all operations in the JiraManagementService to expose the JiraManager
 * methods as web services. All its methods are exposed, and all methods are just delegated to.
 *
 * Any exceptions in the manager are mapped to the service exceptions as per CS 1.3.2.
 *
 * It uses the Configuration API and Persistence components to obtain configuration information. It uses the Object
 * Factory and its Configuration API-backed specification factory to instantiate the JiraManager, and the Log Manager to
 * perform logging of activity and errors.
 *
 * Thread Safety: This bean is mutable and not thread-safe as it deals with non-thread-safe entities. However, in the
 * context of being used in a container, it is thread-safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@WebService(endpointInterface = "com.topcoder.jira.webservices.JiraManagementService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JiraManagementServiceBean implements JiraManagementServiceLocal, JiraManagementServiceRemote {
    /**
     * Represents the default configuration path to create the ConfigurationFileManager. It would be used in the
     * initialize method if the corresponding jiraManagerFile variable is not injected.
     */
    public static final String DEFAULT_CONFIG_PATH =
            "com/topcoder/jira/webservices/bean/JiraManagementServiceBean.properties";

    /**
     * Represents the default namespace to obtain the configuration object for the object factory. It would be used in
     * the initialize method if the corresponding jiraManagerNamespace variable is not injected.
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.jira.webservices.bean.JiraManagementServiceBean";

    /**
     * It will be used to log all activity. It is set in the initialize method It is used in all business methods. It
     * may be null after initialization to indicate no logging to take place. Once set, it will not change.
     */
    private Log log;

    /**
     * The instance of the JiraManager used to perform all actual JIRA business operations. It is set in the initialize
     * method It is used in all business methods. It will not be null after initialization. Once set, it will not
     * change.
     */
    private JiraManager jiraManager;

    /**
     * Represents the configuration path to create the ConfigurationFileManager that will be used for the configuration
     * of this bean and the JIRA manager it will use.
     *
     * It is injected into the bean by the container. It is used in the initialize method. Can be any value. Once set,
     * it will not change.
     */
    @Resource(name = "jiraManagerFile")
    private String jiraManagerFile;

    /**
     * Represents the namespace to create the ConfigurationFileManager that will be used for the configuration of this
     * bean and the JIRA manager it will use.
     *
     * It is injected into the bean by the container. It is used in the initialize method. Values Can be any value. Once
     * set, it will not change.
     */
    @Resource(name = "jiraManagerNamespace")
    private String jiraManagerNamespace;

    /**
     * Default constructor, does nothing.
     */
    public JiraManagementServiceBean() {
    }

    /**
     * getProjectVersions Performs the login of the given user with the given password to JIRA.
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String login(String username, String password) throws JiraServiceException {
        Util.logEnter(log, "login", username, password);
        Util.checkString(log, "username", username);
        Util.checkString(log, "password", password);

        try {
            String result = jiraManager.login(username, password);
            Util.logExit(log, "login", result);
            return result;
        } catch (JiraManagerException e) {
            throw processError(e);
        }
    }

    /**
     * Performs the log out from the JIRA using the given token.
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void logout(String token) throws JiraServiceException {
        Util.logEnter(log, "logout", token);
        Util.checkString(log, "token", token);

        try {
            jiraManager.logout(token);
            Util.logExit(log, "logout");
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public JiraProjectCreationResult createProject(
            String token, JiraProject project, JiraVersion version, ComponentType type) throws JiraServiceException {
        Util.logEnter(log, "createProject", token, project, version, type);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "project", project);
        Util.checkNull(log, "version", version);
        Util.checkNull(log, "type", type);

        try {
            JiraProjectCreationResult result = jiraManager.createProject(token, project, version, type);
            Util.logExit(log, "createProject", result);
            return result;
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public JiraProjectCreationResult addVersionToProject(String token, JiraProject project, JiraVersion version) throws
            JiraServiceException {
        Util.logEnter(log, "addVersionToProject", token, project, version);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "project", project);
        Util.checkNull(log, "version", version);

        try {
            JiraProjectCreationResult result = jiraManager.addVersionToProject(token, project, version);
            Util.logExit(log, "addVersionToProject", result);
            return result;
        } catch (JiraManagerException e) {
            throw processError(e);
        }
    }

    /**
     * USAGE: performs the retrieval of a JIRA project using the given token and with the projectKey.
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public JiraProjectRetrievalResult getProject(String token, String projectKey) throws JiraServiceException {
        Util.logEnter(log, "getProject", token, projectKey);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);

        try {
            JiraProjectRetrievalResult result = jiraManager.getProject(token, projectKey);
            Util.logExit(log, "getProject", result);
            return result;
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public JiraProjectRetrievalResult getProject(String token, String projectKey, String version) throws
            JiraServiceException {
        Util.logEnter(log, "getProject", token, projectKey, version);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);
        Util.checkString(log, "version", version);

        try {
            JiraProjectRetrievalResult result = jiraManager.getProject(token, projectKey, version);
            Util.logExit(log, "getProject", result);
            return result;
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<JiraVersion> getProjectVersions(String token, String projectKey) throws JiraServiceException {
        Util.logEnter(log, "getProjectVersions", token, projectKey);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);

        try {
            List<JiraVersion> result = jiraManager.getProjectVersions(token, projectKey);
            Util.logExit(log, "getProjectVersions", result);
            return result;
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean projectExists(String token, String projectName) throws JiraServiceException {
        Util.logEnter(log, "projectExists", token, projectName);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectName", projectName);

        try {
            boolean result = jiraManager.projectExists(token, projectName);
            Util.logExit(log, "projectExists", result);
            return result;
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public JiraProjectRetrievalResult getProject(String token, Long projectId) throws JiraServiceException {
        Util.logEnter(log, "getProject", token, projectId);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "projectId", projectId);

        try {
            JiraProjectRetrievalResult result = jiraManager.getProject(token, projectId);
            Util.logExit(log, "getProject", result);
            return result;
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteProject(String token, String projectKey) throws JiraServiceException {
        Util.logEnter(log, "deleteProject", token, projectKey);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);

        try {
            jiraManager.deleteProject(token, projectKey);
            Util.logExit(log, "deleteProject");
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public JiraProject updateProject(String token, JiraProject project) throws JiraServiceException {
        Util.logEnter(log, "updateProject", token, project);
        Util.checkString(log, "token", token);
        Util.checkNull(log, "project", project);

        try {
            JiraProject result = jiraManager.updateProject(token, project);
            Util.logExit(log, "updateProject", result);
            return result;
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void releaseVersion(String token, String projectKey, JiraVersion version) throws JiraServiceException {
        Util.logEnter(log, "releaseVersion", token, projectKey, version);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);
        Util.checkNull(log, "version", version);

        try {
            jiraManager.releaseVersion(token, projectKey, version);
            Util.logExit(log, "releaseVersion");
        } catch (JiraManagerException e) {
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void archiveVersion(String token, String projectKey, String versionName, boolean archive) throws
            JiraServiceException {
        Util.logEnter(log, "archiveVersion", token, projectKey, versionName, archive);
        Util.checkString(log, "token", token);
        Util.checkString(log, "projectKey", projectKey);
        Util.checkString(log, "versionName", versionName);

        try {
            jiraManager.archiveVersion(token, projectKey, versionName, archive);
            Util.logExit(log, "archiveVersion");
        } catch (JiraManagerException e) {
            throw processError(e);
        }
    }

    /**
     * Initializes this bean for use.
     *
     * @throws JiraServiceConfigurationException
     *          if cant create the file manager or extract the configuration from it, or if the configuration does not
     *          have required properties, or if there is an error while accessing these properties.
     */
    @PostConstruct
    protected void initialize() {
        try {
            ConfigurationObject config = Util.readConfiguration(
                    jiraManagerFile != null ? jiraManagerFile : DEFAULT_CONFIG_PATH,
                    jiraManagerNamespace != null ? jiraManagerNamespace : DEFAULT_NAMESPACE);

            // create manager
            ObjectFactory factory = Util.createFactory(config);
            config = Util.getChild(config, "default");
            String clientKey = Util.getProperty(config, "jira_manager_key", true);
            jiraManager = Util.createObject(factory, clientKey, JiraManager.class);

            // set logger
            String logName = Util.getProperty(config, "log_name", false);
            if (logName != null) {
                log = LogManager.getLog(logName);
            }
        } catch (JiraManagerConfigurationException e) {
            throw new JiraServiceConfigurationException(e.getMessage(), e);
        }
    }

    /**
     * Simple routine to process caught exceptions from manager, log them and convert to corresponding service errors.
     *
     * @param error error to process
     * @return service error to throw
     */
    private JiraServiceException processError(JiraManagerException error) {
        // log exception, if necessary
        Util.logError(log, error);

        // convert it
        if (error instanceof JiraConnectionException) {
            return new JiraServiceConnectionException(error.getMessage());
        } else if (error instanceof JiraNotAuthorizedException) {
            return new JiraServiceNotAuthorizedException(error.getMessage());
        } else if (error instanceof JiraSecurityTokenExpiredException) {
            return new JiraServiceSecurityTokenExpiredException(error.getMessage());
        } else if (error instanceof JiraProjectValidationException) {
            return new JiraServiceProjectValidationException(error.getMessage());
        } else {
            return new JiraServiceException(error.getMessage());
        }
    }
}