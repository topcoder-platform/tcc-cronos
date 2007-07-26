/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserRetrieval;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.team.InvalidPositionException;
import com.topcoder.management.team.InvalidTeamException;
import com.topcoder.management.team.PositionFilterFactory;
import com.topcoder.management.team.PositionRemovalException;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamFilterFactory;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamManagerException;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.UtilityFilterFactory;
import com.topcoder.management.team.offer.InvalidOfferDataException;
import com.topcoder.management.team.offer.Offer;
import com.topcoder.management.team.offer.OfferFilterFactory;
import com.topcoder.management.team.offer.OfferManager;
import com.topcoder.management.team.offer.OfferManagerException;
import com.topcoder.management.team.offer.OfferStatusType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.registration.contactmember.service.ContactMemberService;
import com.topcoder.registration.contactmember.service.ContactMemberServiceException;
import com.topcoder.registration.contactmember.service.Message;
import com.topcoder.registration.contactmember.service.MessagePersistenceException;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.ResourcePosition;
import com.topcoder.registration.team.service.TeamServiceConfigurationException;
import com.topcoder.registration.team.service.TeamServices;
import com.topcoder.registration.team.service.TeamServicesException;
import com.topcoder.registration.team.service.UnknownEntityException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * Full implementation of the <code>TeamServices</code> interface. This implementation makes use
 * of a large array of components to accomplish its task of managing teams and offers: <b>User Data
 * Store Persistence</b> component to retrieve full user information. It uses the <b>Phase</b> and
 * <b>Resource Management</b>, <b>Project Phases</b>, <b>Project Services</b>, <b>Team Management</b>,
 * <b>OfferManagement</b>, and <b>Contact Member Service</b>.
 * </p>
 * <p>
 * To provide a good view as the steps are progressing in each method, the <b>Logging Wrapper</b>
 * component is used in each method. To configure this component, the <b>ConfigManager</b> and
 * <b>ObjectFactory</b> components are used.
 * </p>
 * <p>
 * Here is the sample configuration for this class:
 *
 * <pre>
 *    &lt;Config name=&quot;com.topcoder.registration.team.service.impl.TeamServicesImpl&quot;&gt;
 *      &lt;Property name=&quot;specNamespace&quot;&gt;
 *          &lt;Value&gt;com.topcoder.util.objectfactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;userRetrievalKey&quot;&gt;
 *          &lt;Value&gt;userRetrievalKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;resourceManagerKey&quot;&gt;
 *          &lt;Value&gt;resourceManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;projectServicesKey&quot;&gt;
 *          &lt;Value&gt;projectServicesKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;phaseManagerKey&quot;&gt;
 *          &lt;Value&gt;phaseManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;projectManagerKey&quot;&gt;
 *          &lt;Value&gt;projectManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *          &lt;Value&gt;teamManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;offerManagerKey&quot;&gt;
 *          &lt;Value&gt;offerManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;contactMemberServiceKey&quot;&gt;
 *          &lt;Value&gt;contactMemberServiceKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;loggerName&quot;&gt;
 *          &lt;Value&gt;defaultLogger&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;registrationPhaseId&quot;&gt;
 *          &lt;Value&gt;1&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;freeAgentRoleId&quot;&gt;
 *          &lt;Value&gt;1&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;teamCaptainRoleId&quot;&gt;
 *          &lt;Value&gt;2&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;submitterRoleId&quot;&gt;
 *          &lt;Value&gt;3&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;removeTeamMessageTemplateName&quot;&gt;
 *          &lt;Value&gt;test_files/doc_gen_files/remove_team_template&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;sendOfferTemplateName&quot;&gt;
 *          &lt;Value&gt;test_files/doc_gen_files/offer_template&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;acceptOfferTemplateName&quot;&gt;
 *          &lt;Value&gt;test_files/doc_gen_files/offer_template&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;rejectOfferTemplateName&quot;&gt;
 *          &lt;Value&gt;test_files/doc_gen_files/offer_template&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;removeMemberMessageTemplateName&quot;&gt;
 *          &lt;Value&gt;test_files/doc_gen_files/remove_member_template&lt;/Value&gt;
 *      &lt;/Property&gt;
 *    &lt;/Config&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is immutable but operates on non thread safe objects, thus making it
 * potentially non thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamServicesImpl implements TeamServices {

    /**
     * <p>
     * Represents the default namespace used by the default constructor to access configuration info
     * in the construction.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.registration.team.service.impl.TeamServicesImpl";

    /**
     * <p>
     * Represents the specNamespace property key.
     * </p>
     */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents the userRetrievalKey property key.
     * </p>
     */
    private static final String USER_RETRIEVAL_KEY = "userRetrievalKey";

    /**
     * <p>
     * Represents the resourceManagerKey property key.
     * </p>
     */
    private static final String RESOURCE_MANAGER_KEY = "resourceManagerKey";

    /**
     * <p>
     * Represents the projectServicesKey property key.
     * </p>
     */
    private static final String PROJECT_SERVICES_KEY = "projectServicesKey";

    /**
     * <p>
     * Represents the phaseManagerKey property key.
     * </p>
     */
    private static final String PHASE_MANAGER_KEY = "phaseManagerKey";

    /**
     * <p>
     * Represents the projectManagerKey property key.
     * </p>
     */
    private static final String PROJECT_MANAGER_KEY = "projectManagerKey";

    /**
     * <p>
     * Represents the teamManagerKey property key.
     * </p>
     */
    private static final String TEAM_MANAGER_KEY = "teamManagerKey";

    /**
     * <p>
     * Represents the offerManagerKey property key.
     * </p>
     */
    private static final String OFFER_MANAGER_KEY = "offerManagerKey";

    /**
     * <p>
     * Represents the contactMemberServiceKey property key.
     * </p>
     */
    private static final String CONTACT_MEMBER_SERVICE_KEY = "contactMemberServiceKey";

    /**
     * <p>
     * Represents the loggerName property key.
     * </p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the registrationPhaseId property key.
     * </p>
     */
    private static final String REGISTRATION_PHASE_ID = "registrationPhaseId";

    /**
     * <p>
     * Represents the freeAgentRoleId property key.
     * </p>
     */
    private static final String FREE_AGENT_ROLE_ID = "freeAgentRoleId";

    /**
     * <p>
     * Represents the teamCaptainRoleId property key.
     * </p>
     */
    private static final String TEAM_CAPTAIN_ROLE_ID = "teamCaptainRoleId";

    /**
     * <p>
     * Represents the submitterRoleId property key.
     * </p>
     */
    private static final String SUBMITTER_ROLE_ID = "submitterRoleId";

    /**
     * <p>
     * Represents the removeTeamMessageTemplateName property key.
     * </p>
     */
    private static final String REMOVE_TEAM_MSG_TEMPLATE_NAME = "removeTeamMessageTemplateName";

    /**
     * <p>
     * Represents the sendOfferTemplateName property key.
     * </p>
     */
    private static final String SEND_OFFER_TEMPLATE_NAME = "sendOfferTemplateName";

    /**
     * <p>
     * Represents the acceptOfferTemplateName property key.
     * </p>
     */
    private static final String ACCEPT_OFFER_TEMPLATE_NAME = "acceptOfferTemplateName";

    /**
     * <p>
     * Represents the rejectOfferTemplateName property key.
     * </p>
     */
    private static final String REJECT_OFFER_TEMPLATE_NAME = "rejectOfferTemplateName";

    /**
     * <p>
     * Represents the removeMemberMessageTemplateName property key.
     * </p>
     */
    private static final String REMOVE_MEMBER_MSG_TEMPLATE_NAME = "removeMemberMessageTemplateName";

    /**
     * <p>
     * Represents the key name of handle in Resource.
     * </p>
     */
    private static final String HANDLE = "Handle";

    /**
     * <p>
     * Represents the external reference id property of Resource.
     * </p>
     */
    private static final String EXTERNAL_REFERENCE_ID = "External Reference ID";

    /**
     * <p>
     * Represents the name property of Project.
     * </p>
     */
    private static final String PROJECT_NAME = "Project Name";

    /**
     * <p>
     * Represents the <code>UserRetrieval</code> instance that is used to retrieve the detailed
     * user information. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final UserRetrieval userRetrieval;

    /**
     * <p>
     * Represents the <code>ResourceManager</code> instance that is used to manage a resource, and
     * obtain information about roles. It is set in the constructor to a non-null value, and will
     * never change.
     * </p>
     */
    private final ResourceManager resourceManager;

    /**
     * <p>
     * Represents the <code>ProjectServices</code> instance that is used to obtain information
     * about current projects and their resources. Often, the <code>ProjectServices</code> and
     * <code>ResourceManager</code> are interchangeable, but often <code>ProjectServices</code>
     * provides more convenient API for accessing resources. It is set in the constructor to a
     * non-null value, and will never change.
     * </p>
     */
    private final ProjectServices projectServices;

    /**
     * <p>
     * Represents the <code>PhaseManager</code> instance that is used to obtain phase information
     * about a project. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final PhaseManager phaseManager;

    /**
     * <p>
     * Represents the <code>TeamManager</code> instance that is used to manage teams and
     * positions. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final TeamManager teamManager;

    /**
     * <p>
     * Represents the <code>OfferManager</code> instance that is used to manage offers. It is set
     * in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final OfferManager offerManager;

    /**
     * <p>
     * Represents the <code>ContactMemberService</code> instance that is used to send messages to
     * resources. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ContactMemberService contactMemberService;

    /**
     * <p>
     * Represents the <code>ProjectManager</code> instance that is used to manage projects. It is
     * set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ProjectManager projectManager;

    /**
     * <p>
     * Used extensively in this class to log information. This will include logging method entry and
     * exit, errors, debug information for calls to other components, etc.
     * </p>
     * <p>
     * Note that logging is optional and can be null, in which case, no logging will take place. It
     * will be set in the constructor and will not change.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Represents the id of the phase that corresponds to the registration phase. It is set in the
     * constructor to a non-negative value, and will never change.
     * </p>
     */
    private final long registrationPhaseId;

    /**
     * <p>
     * Represents the id of a free agent resource role. It is set in the constructor to a
     * non-negative value, and will never change.
     * </p>
     */
    private final long freeAgentRoleId;

    /**
     * <p>
     * Represents the id of a team captain resource role. It is set in the constructor to a
     * non-negative value, and will never change.
     * </p>
     */
    private final long teamCaptainRoleId;

    /**
     * <p>
     * Represents the id of a submitter resource role. It is set in the constructor to a
     * non-negative value, and will never change.
     * </p>
     */
    private final long submitterRoleId;

    /**
     * <p>
     * Represents the name of the template that will be used to generate a massage for the team
     * members when a team is removed. It is set in the constructor to a non-null/empty value, and
     * will never change.
     * </p>
     */
    private final String removeTeamMessageTemplateName;

    /**
     * <p>
     * Represents the name of the template that will be used to generate a massage about the offer
     * to the recipient. It is set in the constructor to a non-null/empty value, and will never
     * change.
     * </p>
     */
    private final String sendOfferTemplateName;

    /**
     * <p>
     * Represents the name of the template that will be used to generate a massage about the
     * acceptance of the offer to the offerer. It is set in the constructor to a non-null/empty
     * value, and will never change.
     * </p>
     */
    private final String acceptOfferTemplateName;

    /**
     * <p>
     * Represents the name of the template that will be used to generate a massage about the
     * rejection of the offer to the offerer. It is set in the constructor to a non-null/empty
     * value, and will never change.
     * </p>
     */
    private final String rejectOfferTemplateName;

    /**
     * <p>
     * Represents the name of the template that will be used to generate a massage to send to the
     * team captain that a member has been removed from the team. It is set in the constructor to a
     * non-null/empty value, and will never change.
     * </p>
     */
    private final String removeMemberMessageTemplateName;

    /**
     * <p>
     * Default constructor.
     * </p>
     * @throws TeamServiceConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services.
     */
    public TeamServicesImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Creates an instance of this class from configuration.
     * </p>
     * <p>
     * Initializes this instance from configuration info from the Config Manager. It will use the
     * Object Factory to create instances of required objects.
     * </p>
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws TeamServiceConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services.
     */
    public TeamServicesImpl(String namespace) {
        Util.checkStrNotNullOrEmpty(namespace, "namespace");

        ConfigManager cm = ConfigManager.getInstance();
        try {
            // gets namespace for ConfigManagerSpecificationFactory
            String specNamespace = cm.getString(namespace, SPEC_NAMESPACE);
            // creates an instance of ObjectFactory
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                specNamespace));

            // gets key for UserRetrieval and creates an instance by ObjectFactory
            this.userRetrieval = (UserRetrieval) createObject(cm, objectFactory, namespace,
                USER_RETRIEVAL_KEY);
            // gets key for ResourceManager and creates an instance by ObjectFactory
            this.resourceManager = (ResourceManager) createObject(cm, objectFactory, namespace,
                RESOURCE_MANAGER_KEY);
            // gets key for ProjectServices and creates an instance by ObjectFactory
            this.projectServices = (ProjectServices) createObject(cm, objectFactory, namespace,
                PROJECT_SERVICES_KEY);
            // gets key for PhaseManager and creates an instance by ObjectFactory
            this.phaseManager = (PhaseManager) createObject(cm, objectFactory, namespace,
                PHASE_MANAGER_KEY);
            // gets key for ProjectManager and creates an instance by ObjectFactory
            this.projectManager = (ProjectManager) createObject(cm, objectFactory, namespace,
                PROJECT_MANAGER_KEY);
            // gets key for TeamManager and creates an instance by ObjectFactory
            this.teamManager = (TeamManager) createObject(cm, objectFactory, namespace,
                TEAM_MANAGER_KEY);
            // gets key for OfferManager and creates an instance by ObjectFactory
            this.offerManager = (OfferManager) createObject(cm, objectFactory, namespace,
                OFFER_MANAGER_KEY);
            // gets key for ContactMemberService and creates an instance by ObjectFactory
            this.contactMemberService = (ContactMemberService) createObject(cm, objectFactory,
                namespace, CONTACT_MEMBER_SERVICE_KEY);

            // gets name of the log and gets the logger instance from LogManager if necessary
            String logName = cm.getString(namespace, LOGGER_NAME);
            this.logger = (logName == null) ? null : LogManager.getLog(logName);

            // gets the value of registrationPhaseId
            this.registrationPhaseId = getLongValue(cm, namespace, REGISTRATION_PHASE_ID);
            // gets the value of freeAgentRoleId
            this.freeAgentRoleId = getLongValue(cm, namespace, FREE_AGENT_ROLE_ID);
            // gets the value of teamCaptainRoleId
            this.teamCaptainRoleId = getLongValue(cm, namespace, TEAM_CAPTAIN_ROLE_ID);
            // gets the value of submitterRoleId
            this.submitterRoleId = getLongValue(cm, namespace, SUBMITTER_ROLE_ID);

            // gets the value of removeTeamMessagTemplateName
            this.removeTeamMessageTemplateName = getTemplateName(cm, namespace,
                REMOVE_TEAM_MSG_TEMPLATE_NAME);
            // gets the value of sendOfferTemplateName
            this.sendOfferTemplateName = getTemplateName(cm, namespace, SEND_OFFER_TEMPLATE_NAME);
            // gets the value of acceptOfferTemplateName
            this.acceptOfferTemplateName = getTemplateName(cm, namespace,
                ACCEPT_OFFER_TEMPLATE_NAME);
            // gets the value of rejectOfferTemplateName
            this.rejectOfferTemplateName = getTemplateName(cm, namespace,
                REJECT_OFFER_TEMPLATE_NAME);
            // gets the value of removeMemberMessageTemplateName
            this.removeMemberMessageTemplateName = getTemplateName(cm, namespace,
                REMOVE_MEMBER_MSG_TEMPLATE_NAME);
        } catch (UnknownNamespaceException ex) {
            throw new TeamServiceConfigurationException(
                "Given namespace can't be recognized by ConfigManager.", ex);
        } catch (IllegalReferenceException ex) {
            throw new TeamServiceConfigurationException(
                "IllegalReferenceException occurred when initializing ObjectFactory.", ex);
        } catch (SpecificationConfigurationException ex) {
            throw new TeamServiceConfigurationException(
                "SpecificationConfigurationException occurred when initializing ObjectFactory.", ex);
        } catch (IllegalArgumentException ex) {
            throw new TeamServiceConfigurationException(
                "Some configuration value for ObjectFactory is null or empty.", ex);
        } catch (ClassCastException ex) {
            throw new TeamServiceConfigurationException(
                "Object created by ObjectFactory can not be casted to specific type.", ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new TeamServiceConfigurationException(
                "The configuration for ObjectFactory is invalid.", ex);
        }
    }

    /**
     * <p>
     * Creates new object by ObjectFactory.
     * </p>
     * @param cm
     *            ConfigManager instance
     * @param objFactory
     *            ObjectFactory instance
     * @param namespace
     *            the namespace of configuration
     * @param propertyName
     *            the property name in configuration
     * @return the new created object
     * @throws IllegalArgumentException
     *             if any property value in configuration is null or empty
     * @throws InvalidClassSpecificationException
     *             if configuration for ObjectFactory is invalid
     * @throws UnknownNamespaceException
     *             if given namespace is unknown by ConfigManager
     */
    private Object createObject(ConfigManager cm, ObjectFactory objFactory, String namespace,
        String propertyName) throws InvalidClassSpecificationException, UnknownNamespaceException {
        // gets the property value by ConfigManager
        String propertyValue = cm.getString(namespace, propertyName);
        // creates a new object using ObjectFactory
        return objFactory.createObject(propertyValue);
    }

    /**
     * <p>
     * Gets the long property value in configuration.
     * </p>
     * @param cm
     *            ConfigManager instance
     * @param namespace
     *            the namespace of configuration
     * @param propertyName
     *            the property name in configuration
     * @return long value of given property
     * @throws UnknownNamespaceException
     *             if given namespace is unknown by ConfigManager
     * @throws TeamServiceConfigurationException
     *             if error occurred when converting string to 'long' value, or retrieved id is
     *             negative
     */
    private long getLongValue(ConfigManager cm, String namespace, String propertyName)
        throws UnknownNamespaceException {
        String val = cm.getString(namespace, propertyName);
        long id = -1;
        try {
            id = Long.parseLong(val);
            if (id < 0) {
                throw new TeamServiceConfigurationException("Value of [" + propertyName
                    + "] should not be negative.");
            }
        } catch (NumberFormatException ex) {
            throw new TeamServiceConfigurationException("The key value for [" + propertyName
                + "] is not a valid long value.");
        }
        return id;
    }

    /**
     * <p>
     * Gets the name of template for <code>DocumentGenerator</code>.
     * </p>
     * @param cm
     *            the ConfigManager instance
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the property name
     * @return template name for DocumentGenerator, can't be null or empty
     * @throws UnknownNamespaceException
     *             if given namespace is unknown by ConfigManager
     * @throws TeamServiceConfigurationException
     *             if retrieved value is null or empty
     */
    private String getTemplateName(ConfigManager cm, String namespace, String propertyName)
        throws UnknownNamespaceException {
        String templateName = cm.getString(namespace, propertyName);
        if (templateName == null || templateName.trim().length() == 0) {
            throw new TeamServiceConfigurationException("Value of [" + propertyName
                + "] should not be null or empty.");
        }
        return templateName;
    }

    /**
     * <p>
     * Logs messages if necessary.
     * </p>
     * @param level
     *            the log level
     * @param msg
     *            the log message
     */
    private void log(Level level, String msg) {
        if (logger != null) {
            logger.log(level, msg);
        }
    }

    /**
     * <p>
     * Logs DEBUG level message when calling external Topcoder classes.
     * </p>
     * @param msg
     *            the logging message
     */
    private void logDebug(String msg) {
        log(Level.DEBUG, msg);
    }

    /**
     * <p>
     * Creates or updates the team. A team with no team Id is created and none with a valid existing
     * teamId is updated. The operation will not go forward if the team being updated is already
     * finalized. Nor can the team name be already used by an existing team in any active project,
     * or be the same as any member resource's handle, but captain's handle is ok.
     * </p>
     * @return OperationResult with the results of the operation
     * @param team
     *            The team header to either create or update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null, or userId is negative
     * @throws UnknownEntityException
     *             If teamId in team is non-negative but not known
     * @throws ProjectServicesException
     *             if error occurred when retrieving FullProjectData objects
     * @throws TeamPersistenceException
     *             if error occurred when retrieving or updating team by TeamManager
     */
    public OperationResult createOrUpdateTeam(TeamHeader team, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#createOrUpdateTeam method.");
        try {
            Util.checkObjNotNull(team, "team");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#createOrUpdateTeam method.");
            throw ex;
        }

        OperationResultImpl operationResult = new OperationResultImpl();
        try {
            // gets all active projects
            logDebug("Starts calling ProjectServices#findActiveProjects method.");
            FullProjectData[] fullProjectDatas = projectServices.findActiveProjects();
            logDebug("Finished calling ProjectServices#findActiveProjects method.");

            // if team id is negative, creates a new team
            if (team.getTeamId() < 0) {
                // checks whether team name is legal
                if (!isTeamLegal(team, fullProjectDatas, true, operationResult)) {
                    // team is illegal, just return a unsuccessful result
                    log(Level.INFO, "Exits TeamServicesImpl=createOrUpdateTeam method.");
                    return operationResult;
                }

                // creates the teams with TeamManager
                logDebug("Starts calling TeamManager#createTeam method.");
                teamManager.createTeam(team, userId);
                logDebug("Finished calling TeamManager#createTeam method.");
            } else {
                // updates the team
                // checks whether this team exists in persistence
                checkTeamExists(team.getTeamId());

                // checks whether team name is legal
                if (!isTeamLegal(team, fullProjectDatas, false, operationResult)) {
                    // team is illegal, just return a unsuccessful result
                    log(Level.INFO, "Exits TeamServicesImpl=createOrUpdateTeam method.");
                    return operationResult;
                }

                // updates the team with TeamManager
                logDebug("Starts calling TeamManager#createTeam method.");
                teamManager.updateTeam(team, userId);
                logDebug("Finished calling TeamManager#createTeam method.");
            }
        } catch (InvalidTeamException ex) {
            log(Level.ERROR, "InvalidTeamException occurred.");
            // error occurred, set the result to be unsuccessful
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
        } catch (com.topcoder.management.team.UnknownEntityException ex) {
            log(Level.ERROR, "UnknownEntityException occurred.");
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
        } catch (TeamPersistenceException ex) {
            log(Level.ERROR, "TeamPersistenceException occurred.");
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
        }

        log(Level.INFO, "Exits TeamServicesImpl#createOrUpdateTeam method.");
        return operationResult;
    }

    /**
     * <p>
     * Checks whether given team exists in persistence.
     * </p>
     * @param teamId
     *            id of the team
     * @return the full team instance
     * @throws TeamPersistenceException
     *             if error occurred when retrieving team by TeamManager
     * @throws UnknownEntityException
     *             if given team does not exist in persistence
     */
    private Team checkTeamExists(long teamId) {
        // checks whether this team exists in persistence
        logDebug("Starts calling TeamManager#getTeam method.");
        Team theTeam = teamManager.getTeam(teamId);
        logDebug("Finished calling TeamManager#getTeam method.");

        // if returned team is null, throw an exception
        if (theTeam == null) {
            log(Level.ERROR, "UnknownEntityException occurred.");
            throw new UnknownEntityException("Given team [ID=" + teamId
                + "] does not exist in the system.");
        }

        return theTeam;
    }

    /**
     * <p>
     * Checks whether given team is consistent with the system.
     * </p>
     * @param team
     *            the team to be checked
     * @param fullProjectDatas
     *            the FullProjectData array
     * @param create
     *            flag indicating whether creating or updating the team
     * @param operationResult
     *            the operation result
     * @return true if given team is consistent with the system, false otherwise or any unexpected
     *         non-runtime exception occurred
     * @throws TeamPersistenceException
     *             if error occurred when retrieving team by TeamManager
     */
    private boolean isTeamLegal(TeamHeader team, FullProjectData[] fullProjectDatas,
        boolean create, OperationResultImpl operationResult) {
        // checks whether team name is legal
        for (int i = 0; i < fullProjectDatas.length; i++) {
            if (fullProjectDatas[i].getProjectHeader().getId() != team.getProjectId()) {
                continue;
            }

            // gets all teams in this project
            TeamHeader[] teams = fullProjectDatas[i].getTeams();
            
            for (int j = 0; j < teams.length; j++) {
                TeamHeader teamHeader = teams[j];
                // if this is updating and this team's id equals to given team's, skip it
                if (!create && teamHeader.getTeamId() == team.getTeamId()) {
                    // do nothing
                } else {
                    // check team.name does not equal to teamHeader.name
                    if (teamHeader.getName().equals(team.getName())) {
                        operationResult.setSuccessful(false);
                        operationResult.setErrors(new String[] {"Name of given team "
                            + "should not be equal to that of any existing team."});
                        return false;
                    }
                }


                try {
                    // gets complete team information of this teamHeader
                    logDebug("Starts calling TeamManager#getTeam method.");
                    Team fullTeam = teamManager.getTeam(teamHeader.getTeamId());
                    logDebug("Finished calling TeamManager#getTeam method.");

                    // gets all registered Positions in this team
                    TeamPosition[] positions = fullTeam.getPositions();
                    for (int k = 0; k < positions.length; k++) {
                        // gets the resource associated with this position
                        logDebug("Starts calling ResourceManager#getResource method.");
                        // no sense processing unfilled positions
                        if (!positions[k].getFilled()) {
                            continue;
                        }
                        Resource resource = resourceManager.getResource(positions[k]
                            .getMemberResourceId());
                        logDebug("Finished calling ResourceManager#getResource method.");

                        // checks name of given team does not match member handle
                        if (resource.getProperty(HANDLE).equals(team.getName())) {
                            operationResult.setSuccessful(false);
                            operationResult.setErrors(new String[] {"Name of given team should "
                                + "not match any handles of existing members."});
                            return false;
                        }
                    }
                } catch (ResourcePersistenceException ex) {
                    // exception occurred, sets successful to false, sets the error message and
                    // return
                    operationResult.setSuccessful(false);
                    operationResult.setErrors(new String[] {ex.getMessage()});
                    return false;
                }
            }
        }
        // every thing is consistent, return true
        return true;
    }

    /**
     * <p>
     * Gets all teams that are registered for the project with the given ID. Returns empty array if
     * the there are no teams in this project.
     * </p>
     * @return TeamHeader array of teams in the given project
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws UnknownEntityException
     *             If projectId is not known
     * @throws ProjectServicesException
     *             if error occurred when retrieving FullProjectData objects
     * @throws TeamPersistenceException
     *             if error occurred when retrieving or updating team by TeamManager
     */
    public TeamHeader[] getTeams(long projectId) {
        log(Level.INFO, "Enters TeamServicesImpl#getTeams method.");
        try {
            Util.checkIDNotNegative(projectId, "projectId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#getTeams method.");
            throw ex;
        }

        // checks whether given project exist in the system, if not, throw an exception
        checkProjectExist(projectId);

        logDebug("Starts calling TeamManager#findTeams method.");
        TeamHeader[] teams = teamManager.findTeams(projectId);
        logDebug("Finished calling TeamManager#findTeams method.");

        log(Level.INFO, "Exits TeamServicesImpl#getTeams method.");
        return teams;
    }

    /**
     * <p>
     * Checks whether given project exist in system.
     * </p>
     * @param projectId
     *            id of the project to be checked
     * @return the full project data
     * @throws ProjectServicesException
     *             if error occurred when retrieving FullProjectData objects
     * @throws UnknownEntityException
     *             if given project can't not be found
     */
    private FullProjectData checkProjectExist(long projectId) {
        // gets all active projects
        logDebug("Starts calling ProjectServices#getFullProjectData method.");
        FullProjectData fullProjectData = projectServices.getFullProjectData(projectId);
        logDebug("Finished calling ProjectServices#getFullProjectData method.");

        if (fullProjectData == null) {
            log(Level.ERROR, "UnknownEntityException occurred.");
            throw new UnknownEntityException("Given project [ID=" + projectId
                + "] does not exist in persistence.");
        }
        return fullProjectData;
    }

    /**
     * <p>
     * Gets the team members, including the captain, in this team. It will return at least one
     * member in the array since a team always has a captain.
     * </p>
     * @return Resource array of the participants in the team
     * @param teamId
     *            The ID of the team whose participants are to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     * @throws TeamServicesException
     *             if error occurred when retrieving Resource by ResouceManager
     */
    public Resource[] getTeamMembers(long teamId) {
        log(Level.INFO, "Enters TeamServicesImpl#getTeamMembers method.");
        try {
            Util.checkIDNotNegative(teamId, "teamId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#getTeamMembers method.");
            throw ex;
        }

        // gets the complete team info for given teamID, if not exist, throw an
        // UnknownEntityException
        Team team = checkTeamExists(teamId);

        // represents the Resource ids list
        List ids = new ArrayList();
        // adds the Resource id of Team Captain
        ids.add(new Long(team.getTeamHeader().getCaptainResourceId()));
        // adds ids of team members
        TeamPosition[] members = team.getPositions();
        for (int i = 0; i < members.length; i++) {
            if (members[i].getFilled()) {
                ids.add(new Long(members[i].getMemberResourceId()));
            }
        }

        // represents the Resource array
        Resource[] resources = new Resource[ids.size()];
        for (int i = 0; i < resources.length; i++) {
            try {
                logDebug("Starts calling ResourceManager#getResource method.");
                Resource resource = resourceManager.getResource(((Long) ids.get(i)).longValue());
                logDebug("Finished calling ResourceManager#getResource method.");

                resources[i] = resource;
            } catch (ResourcePersistenceException ex) {
                log(Level.ERROR,
                    "TeamServicesException occurred in TeamServicesImpl#getTeamMembers method.");
                throw new TeamServicesException(
                    "Error occurred when retrieving Resource instance by ResourceManager.", ex);
            }
        }

        log(Level.INFO, "Exits TeamServicesImpl#getTeamMembers method.");
        return resources;
    }

    /**
     * <p>
     * Removes a team and all its positions. It will send a notification message to all team members
     * and captain informing of the team removal, All members' role is changed to a Free Agent. Any
     * offer from the team captain to free agents are removed.
     * </p>
     * <p>
     * Here is the sample XML template data format:
     * 
     * <pre>
     *     &lt;DATA&gt;
     *       &lt;TEAM_NAME&gt;name&lt;/TEAM_NAME&gt;
     *       &lt;TEAM_DESCRIPTION&gt;description&lt;/TEAM_DESCRIPTION&gt;
     *       &lt;PROJECT_NAME&gt;project name&lt;/PROJECT_NAME&gt;
     *       &lt;CAPTAIN_HANDLE&gt;handle&lt;/CAPTAIN_HANDLE&gt;
     *     &lt;/DATA&gt;
     * </pre>
     * 
     * </p>
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     * @throws TeamPersistenceException
     *             if error occurred when retrieving team by TeamManager
     * @throws OfferManagerException
     *             if error occurred when retrieving or expiring offers
     * @throws MessagePersistenceException
     *             If there is an error in persistence
     * @throws ContactMemberServiceException
     *             If there is a general system error while performing this operation, such as an
     *             inability to obtain an ID
     */
    public OperationResult removeTeam(long teamId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#removeTeam method.");
        try {
            Util.checkIDNotNegative(teamId, "teamId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#removeTeam method.");
            throw ex;
        }

        // creates a new OperationResult instance
        OperationResultImpl operationResult = new OperationResultImpl();

        // gets the ExternalUser instance of operator
        ExternalUser operator = getOperator(userId, operationResult);
        if (operator == null) {
            log(Level.INFO, "Exits TeamServicesImpl#removeTeam method.");
            return operationResult;
        }

        // gets the complete team information using TeamManager, if null returned, throw an
        // UnknownEntityException
        Team team = checkTeamExists(teamId);

        // updates each member's role of this team and the Captain's resource to Free Agent
        // gets the team members
        TeamPosition[] members = team.getPositions();
        // represents the resources' ids array, including Captain's and members', the first one will
        // be Captain's
        long[] ids = new long[members.length + 1];
        // sets the ids array
        ids[0] = team.getTeamHeader().getCaptainResourceId();
        for (int i = 0; i < members.length; i++) {
            ids[i + 1] = members[i].getMemberResourceId();
        }

        // represents the userIds of captain and each member Resource, the first one will be
        // Captain's
        long[] userIds = new long[members.length + 1];
        // represents the handles of captain and each member Resource, the first one will be
        // Captain's
        String[] handles = new String[members.length + 1];

        try {
            for (int i = 0; i < ids.length; i++) {
                // ignore unfilled positions
                if (ids[i] == -1) {
                    continue;
                }
                
                // gets the Resource instance of this member
                logDebug("Starts calling ResourceManager#getResource method.");
                Resource resource = resourceManager.getResource(ids[i]);
                logDebug("Finished calling ResourceManager#getResource method.");

                // gets the External reference ID property of this Resource
                userIds[i] = Long.parseLong(resource.getProperty(EXTERNAL_REFERENCE_ID).toString());
                // gets the Handle property of this Resource
                handles[i] = (String) resource.getProperty(HANDLE);

                // sets ResourceRole of this Resource to Free Agent
                resource.setResourceRole(new ResourceRole(freeAgentRoleId));

                // updates the Resource instance
                logDebug("Starts calling ResourceManager#updateResource method.");
                resourceManager.updateResource(resource, operator.getHandle());
                logDebug("Starts calling ResourceManager#updateResource method.");
                
                teamManager.removeTeam(teamId, userId);
            }
        } catch (Exception ex) {
            log(Level.ERROR, "Exception occurred in TeamServicesImpl#removeTeam method.");
            // error occurred, set unsuccessful flag to OperationResult and return
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            log(Level.INFO, "Exits TeamServicesImpl#removeTeam method.");
            return operationResult;
        }

        // expires all pending offers sent by the Team Captain
        expireOffers(userIds[0], team.getTeamHeader());

        if (members.length > 0) {
            
           // sends messages to team members
            Message message = new Message();
    
            try {
                // gets name of this project
                logDebug("Starts calling ProjectManager#getProject method.");
                String projectName = (String) projectManager.getProject(
                    team.getTeamHeader().getProjectId()).getProperty(PROJECT_NAME);
                logDebug("Finished calling ProjectManager#getProject method.");
    
                // gets instance of DocumentGenerator
                DocumentGenerator docGen = DocumentGenerator.getInstance();
                // gets the Template for removing team
                Template template = docGen.getTemplate(removeTeamMessageTemplateName);
                // builds the XML template data
                String templateData = buildsTemplateDataForRemovingTeam(team.getTeamHeader().getName(),
                    team.getTeamHeader().getDescription(), projectName, handles[0]);
                // generates message text
                String msgText = docGen.applyTemplate(template, templateData);
    
                // gets the destination handles
                String[] toHandles = new String[members.length];
                List toHandlesList = new ArrayList();
                if(handles != null) {
                	for (int i = 0; i < handles.length; i++) {
						if(handles[i] != null) {
							toHandlesList.add(handles[i]);
						}
					}
                }
                toHandles = (String[]) toHandlesList.toArray(new String[0]);
                
                //System.arraycopy(handles, 1, toHandles, 0, members.length);
                // sets up the Message
                setUpMessage(message, msgText, toHandles, handles[0], team.getTeamHeader()
                    .getProjectId(), projectName);
            } catch (Exception ex) {
                // error occurred, set operationResult to unsuccessful, and return
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {ex.getMessage()});
                log(Level.INFO, "Exits TeamServicesImpl#removeTeam method.");
                return operationResult;
            }
            // sends the message
            
            logDebug("Starts calling ContactMemberServices#sendMessage method.");
            
            contactMemberService.sendMessage(message);
            logDebug("Finished calling ContactMemberServices#sendMessage method.");
        }
 
        log(Level.INFO, "Exits TeamServicesImpl#removeTeam method.");
        
        return operationResult;
    }

    /**
     * <p>
     * Gets the external user of operator.
     * </p>
     * @param userId
     *            the operator user id
     * @param operationResult
     *            the operationResult
     * @return the external user of operator, null will be returned if error occurred or no such
     *         user exists
     */
    private ExternalUser getOperator(long userId, OperationResultImpl operationResult) {
        ExternalUser operator = null;
        try {
            operator = userRetrieval.retrieveUser(userId);
        } catch (RetrievalException ex) {
            // error occurred, set unsuccessful flag to OperationResult and return
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return null;
        }
        if (operator == null) {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {"No such user exists."});
        }
        return operator;
    }

    /**
     * <p>
     * Sets up the Message with given parameters.
     * </p>
     * @param message
     *            the Message instance to be set
     * @param text
     *            the message text
     * @param toHandles
     *            the receivers
     * @param fromHandle
     *            the sender
     * @param projectId
     *            the project id
     * @param projectName
     *            the project name
     */
    private void setUpMessage(Message message, String text, String[] toHandles, String fromHandle,
        long projectId, String projectName) {
        message.setFromHandle(fromHandle);
        message.setProjectId(projectId);
        message.setProjectName(projectName);
        message.setText(text);
        message.setTimeStamp(new Date());
        message.setToHandles(toHandles);
    }

    /**
     * <p>
     * Builds XML template data for removing team.
     * </p>
     * @param teamName
     *            name of the team to be removed
     * @param description
     *            the description of the team
     * @param projectName
     *            name of the project team has registered
     * @param handle
     *            handle of Captain of the team
     * @return the template data for removing team
     */
    private String buildsTemplateDataForRemovingTeam(String teamName, String description,
        String projectName, String handle) {
        StringBuffer tmpData = new StringBuffer();
        tmpData.append("<DATA><TEAM_NAME>");
        tmpData.append(teamName);
        tmpData.append("</TEAM_NAME><TEAM_DESCRIPTION>");
        tmpData.append(description);
        tmpData.append("</TEAM_DESCRIPTION><PROJECT_NAME>");
        tmpData.append(projectName);
        tmpData.append("</PROJECT_NAME><CAPTAIN_HANDLE>");
        tmpData.append(handle);
        tmpData.append("</CAPTAIN_HANDLE></DATA>");

        return tmpData.toString();
    }

    /**
     * <p>
     * Expires all pending offers.
     * </p>
     * @param captainId
     *            the user id of Team Captain
     * @param teamHeader
     *            the team header
     * @throws OfferManagerException
     *             if error occurred when retrieving or expiring offers
     */
    private void expireOffers(long captainId, TeamHeader teamHeader) {
        // constructs a filter to retrieve all pending offers
        Filter filter = OfferFilterFactory.createAndFilter(OfferFilterFactory.createSenderIdFilter(captainId),
        		OfferFilterFactory.createOfferStatusTypeFilter(OfferStatusType.OFFERED));
        // find offers using above filter
        logDebug("Starts calling OfferManager#findOffers method.");
        Offer[] offers = offerManager.findOffers(filter);
        logDebug("Finished calling OfferManager#findOffers method.");

        // gets all free agents in this project
        Resource[] freeAgents = findFreeAgents(teamHeader.getProjectId());

        // handles each offer, if it has a corresponding Free Agent, then expire it
        for (int i = 0; i < offers.length; i++) {
            for (int j = 0; j < freeAgents.length; j++) {
                if (offers[i].getToUserId() == Long.parseLong(freeAgents[j].getProperty(EXTERNAL_REFERENCE_ID).toString())) {
                    // Free Agent found, expire this offer
                    logDebug("Starts calling OfferManager#expireOffer method.");
                    offerManager.expireOffer(offers[i].getOfferId());
                    logDebug("Finished calling OfferManager#expireOffer method.");
                    break;
                }
            }
        }
    }

    /**
     * <p>
     * Gets the details, in terms of the position and resource, of each position. If there are no
     * positions yet configured for a team, it returns an empty array.
     * </p>
     * @return ResourcePosition array, The details of all positions in the team
     * @param teamId
     *            The ID of the team whose position details are to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     * @throws TeamServicesException
     *             if error occurred when retrieving Resource using ResourceManager
     */
    public ResourcePosition[] getTeamPositionsDetails(long teamId) {
        log(Level.INFO, "Enters TeamServicesImpl#getTeamPositionsDetails method.");
        try {
            Util.checkIDNotNegative(teamId, "teamId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#getTeamPositionsDetails method.");
            throw ex;
        }

        // gets the complete team, if no such team exists, throw an UnknownEntityException
        Team team = checkTeamExists(teamId);

        // gets the positions of this team
        TeamPosition[] teamPos = team.getPositions();

        // represents the ResourcePosition array to be returned
        // FIX BUG TCRT-8531
		ResourcePosition[] rp = new ResourcePosition[teamPos.length];
        for (int i = 0; i < teamPos.length; i++) {
            try {
                // gets the Resource associated with this position
                if (teamPos[i].getFilled()) {
	                logDebug("Starts calling ResourceManager#getResource method.");
                    Resource resource = resourceManager.getResource(teamPos[i]
                        .getMemberResourceId());
                    rp[i] = new ResourcePositionImpl(resource, teamPos[i]);
                } else {
                    rp[i] = new ResourcePositionImpl();
                    rp[i].setPosition(teamPos[i]);
                }
                logDebug("Finished calling ResourceManager#getResource method.");

            } catch (ResourcePersistenceException ex) {
                log(Level.ERROR,
                    "TeamServicesException occurred in TeamServicesImpl#getTeamPositionsDetails method.");
                throw new TeamServicesException(
                    "Error occurred when retrieving Resource using ResourceManager.", ex);
            }
        }

        log(Level.INFO, "Exits TeamServicesImpl#getTeamPositionsDetails method.");
        return rp;
    }

    /**
     * <p>
     * Gets all free agents available to this project. Returns an empty array if there are no
     * available free agents.
     * </p>
     * @return Resource array of resources whose roles are defined as being free agents
     * @param projectId
     *            The ID of the project whose free agents are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws UnknownEntityException
     *             If projectId is not known
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs when finding positions
     */
    public Resource[] findFreeAgents(long projectId) {
        log(Level.INFO, "Enters TeamServicesImpl#findFreeAgents method.");
        try {
            Util.checkIDNotNegative(projectId, "projectId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#findFreeAgents method.");
            throw ex;
        }

        // gets the FullProjectData with given projectId, if not found, throw an
        // UnknownEntityException
        FullProjectData fullProjectData = checkProjectExist(projectId);
        // gets all resources
        Resource[] resources = fullProjectData.getResources();
        // represents the Resource list containing Free Agents
        List freeAgents = new ArrayList();

        // filters out Free Agents
        for (int i = 0; i < resources.length; i++) {
            // creates a filter to filters positions for given resource
            Filter filter = PositionFilterFactory.createResourceIdFilter(resources[i].getId());

            // searches the positions
            logDebug("Starts calling TeamManager#findPositions method.");
            TeamPosition[] positions = teamManager.findPositions(filter);
            logDebug("Finished calling TeamManager#findPositions method.");

            // if no positions found, add this resource to Free Agents list
            if (positions.length == 0) {
                freeAgents.add(resources[i]);
            }
        }
        
        // FIX BUG TCRT-8524
        // remove team captains
        TeamHeader[] teams = fullProjectData.getTeams();
        for (int i = 0; i < teams.length; i++) {
            TeamHeader team = teams[i];
            for (Iterator iter = freeAgents.iterator(); iter.hasNext();) {
                Resource resource = (Resource) iter.next();
                if (resource.getId() == team.getCaptainResourceId()) {
                    iter.remove();
                }
            }
        }

        log(Level.INFO, "Exits TeamServicesImpl#findFreeAgents method.");
        return (Resource[]) freeAgents.toArray(new Resource[freeAgents.size()]);
    }

    /**
     * <p>
     * Creates or updates the position. A position with no position Id is added and none with a
     * valid existing posotionId is updated. A position cannot be added after a team is finalized,
     * the name of the position cannot correspond to any member handle or to any other position name
     * in the team, and if the position is filled, the member must be a free agent and not be in any
     * other team in the project.
     * </p>
     * @return OperationResult with the results of the operation
     * @param position
     *            TeamPosition to create or update
     * @param teamId
     *            The ID of the team this position is part of
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, or userId or teamId is negative
     * @throws UnknownEntityException
     *             If positionId in position is non-negative but not known
     * @throws TeamPersistenceException
     *             if any error occurred when retrieving the full team
     * @throws ProjectServicesException
     *             if error occurred when retrieving FullProjectData objects
     */
    public OperationResult createOrUpdatePosition(TeamPosition position, long teamId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#createOrUpdatePosition method.");
        try {
            Util.checkObjNotNull(position, "position");
            Util.checkIDNotNegative(teamId, "teamId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#createOrUpdatePosition method.");
            throw ex;
        }

        // creates a new instance of OperationResult
        OperationResultImpl operationResult = new OperationResultImpl();

        // checks the position name does not match any user name
        try {
            logDebug("Starts calling UserRetrieval#retrieveUser method.");
            ExternalUser theUser = userRetrieval.retrieveUser(position.getName());
            logDebug("Finished calling UserRetrieval#retrieveUser method.");
            // if user exists, set operationResult to unsuccessful and return
            if (theUser != null) {
                operationResult.setSuccessful(false);
                operationResult
                    .setErrors(new String[] {"Name of given position should not match any user name."});
                log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
                return operationResult;
            }
        } catch (RetrievalException ex) {
            log(Level.ERROR,
                "RetrievalException occurred in TeamServicesImpl#createOrUpdatePosition method.");
            // if error occurred, set operationResult to unsuccessful and return it
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
            return operationResult;
        }

        // gets the complete team information using TeamManager
        logDebug("Starts calling TeamManager#getTeam method.");
        Team team = teamManager.getTeam(teamId);
        logDebug("Finished calling TeamManager#getTeam method.");

        try {
            // if id of the position is negative, creates the position
            if (position.getPositionId() < 0) {
                // checks the position's name is not used already
                if (!checkPositionNameNotUsed(position, team, operationResult, true)) {
                    log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
                    return operationResult;
                }
                // position's resource should not have been registered for this project before
                // sum of payment percentage should not exceed 100
                if (!checkPaymentSumValid(team, position, operationResult)) {
                    log(Level.INFO, "Enters TeamServicesImpl#acceptOffer method.");
                    return operationResult;
                }

                // position is filled already, continue checking
                if (position.getFilled()) {
                    // position's resource should be a Free Agent
                    if (!checkResourceFreeAgent(position, team, operationResult)) {
                        log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
                        return operationResult;
                    }
                    // position's resource should not have been registered for this project before
                    if (!checkResourceNotRegisteredBefore(position, team, operationResult)) {
                        log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
                        return operationResult;
                    }

                }

                logDebug("Starts calling TeamManager#addPosition method.");
                teamManager.addPosition(teamId, position, userId);
                logDebug("Finished calling TeamManager#addPosition method.");
            } else {
                // position.id is non-negative, updates the position
                // checks the position's name is not used by other positions in the team
                if (!checkPositionNameNotUsed(position, team, operationResult, false)) {
                    log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
                    return operationResult;
                }
                // position is filled already, continue checking
                if (position.getFilled()) {
                    // if position has a new memberResourceId, then this id should not been already
                    // registered for this project
                    if (hasNewId(position, team)) {
                        // position's resource should be a Free Agent
                        if (!checkResourceFreeAgent(position, team, operationResult)) {
                            log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
                            return operationResult;
                        }

                        // position's resource should not have been registered for this project
                        // before
                        if (!checkResourceNotRegisteredBefore(position, team, operationResult)) {
                            log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
                            return operationResult;
                        }
                    }
                }
                // updates the position
                logDebug("Starts calling TeamManager#updatePosition method.");
                teamManager.updatePosition(position, userId);
                logDebug("Finished calling TeamManager#updatePosition method.");
            }
        } catch (InvalidPositionException ex) {
            log(Level.ERROR,
                "InvalidPositionException occurred in TeamServicesImpl#createOrUpdatePosition method.");
            // if error occurred, set operationResult to unsuccessful and return it
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
            return operationResult;
        } catch (TeamPersistenceException ex) {
            log(Level.ERROR,
            "TeamPersistenceException occurred in TeamServicesImpl#createOrUpdatePosition method.");
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
            return operationResult;
        } catch (com.topcoder.management.team.UnknownEntityException ex) {
            log(Level.ERROR,
            "UnknownEntityException occurred in TeamServicesImpl#createOrUpdatePosition method.");
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
        }

        log(Level.INFO, "Exits TeamServicesImpl#createOrUpdatePosition method.");
        return operationResult;
    }

    /**
     * <p>
     * Checks whether position has a new memberResourceId.
     * </p>
     * @param position
     *            the position
     * @param team
     *            the team
     * @return true if given position has a new memberResouceId, false otherwise
     */
    private boolean hasNewId(TeamPosition position, Team team) {
        TeamPosition[] positions = team.getPositions();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].getPositionId() == position.getPositionId()
                && positions[i].getMemberResourceId() != position.getMemberResourceId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks position name is not used by other positions.
     * </p>
     * @param position
     *            the position instance
     * @param team
     *            the team for which the position will be register
     * @param operationResult
     *            the operationResult instance
     * @param create
     *            flag indicating whether creating or updating the position
     * @return true if no duplicate name exist, false otherwise
     * @throws UnknownEntityException
     *             if create is false, and no position in given team matches given position
     */
    private boolean checkPositionNameNotUsed(TeamPosition position, Team team,
        OperationResultImpl operationResult, boolean create) {
        TeamPosition[] positions = team.getPositions();

        boolean match = false;
        boolean nameNotDup = true;
        for (int i = 0; i < positions.length; i++) {
            // if updating the position, then this checking will skip itself
            if (!create && positions[i].getPositionId() == position.getPositionId()) {
                match = true;
                continue;
            }

            if (position.getName().equals(positions[i].getName())) {
                operationResult.setSuccessful(false);
                operationResult
                    .setErrors(new String[] {"Name of position should not equals to existing position's name."});
                nameNotDup = false;
            }
        }
        if (!create && !match) {
            log(Level.ERROR, "UnknownEntityException occurred.");
            throw new UnknownEntityException("Given position can't be recognized by given team.");
        }
        return nameNotDup;
    }

    /**
     * <p>
     * Checks resource of position is a Free Agent.
     * </p>
     * @param position
     *            the position instance
     * @param team
     *            the team for which the position will be register
     * @param operationResult
     *            the operationResult instance
     * @return true if given position's resource is a Free Agent, false otherwise
     */
    private boolean checkResourceFreeAgent(TeamPosition position, Team team,
        OperationResultImpl operationResult) {
        // position's resource should be a Free Agent
        Resource[] freeAgents = findFreeAgents(team.getTeamHeader().getProjectId());
        // flag indicating whether position's resource is a Free Agent, initially false
        boolean free = false;
        for (int i = 0; i < freeAgents.length; i++) {
            if (freeAgents[i].getId() == position.getMemberResourceId()) {
                free = true;
                break;
            }
        }
        // if not free, set operationResult and return
        if (!free) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"Resource of given position should be a Free Agent."});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks resource of position has not been registered for specific project before.
     * </p>
     * @param position
     *            the position instance
     * @param team
     *            the team for which the position will be register
     * @param operationResult
     *            the operationResult instance
     * @return true if not registered before, false otherwise
     * @throws ProjectServicesException
     *             if error occurred when retrieving FullProjectData objects
     */
    private boolean checkResourceNotRegisteredBefore(TeamPosition position, Team team,
        OperationResultImpl operationResult) {
        // position's resource should not have been registered for this project before
        // constructs filter to find positions
        Filter projectIdFilter = PositionFilterFactory.createProjectIdFilter(team.getTeamHeader()
            .getProjectId());
        Filter resourceIdFilter = PositionFilterFactory.createResourceIdFilter(position
            .getMemberResourceId());
        Filter filter = UtilityFilterFactory.createAndFilter(projectIdFilter, resourceIdFilter);
        // searches the positions
        logDebug("Starts calling TeamManager#findPositions method.");
        TeamPosition[] positions = teamManager.findPositions(filter);
        logDebug("Finished calling TeamManager#findPositions method.");
        if (positions.length == 0) {
            return true;
        }
        operationResult.setSuccessful(false);
        operationResult
            .setErrors(new String[] {"Given Resouce has registered for this project already."});
        return false;
    }

    /**
     * <p>
     * Gets the position with given position id.
     * </p>
     * @return TeamPosition that matches the ID
     * @param positionId
     *            The ID of the position to retrieve
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws UnknownEntityException
     *             If positionId is not known
     * @throws TeamPersistenceException
     *             if any error occurred when retrieving the team by position
     */
    public TeamPosition getPosition(long positionId) {
        log(Level.INFO, "Enters TeamServicesImpl#getPosition method.");
        try {
            Util.checkIDNotNegative(positionId, "positionId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#getPosition method.");
            throw ex;
        }

        // gets the team info
        logDebug("Starts calling TeamManager#getTeamFromPosition method.");
        Team team = teamManager.getTeamFromPosition(positionId);
        logDebug("Finished calling TeamManager#getTeamFromPosition method.");

        // if team is null, thrown an exception
        if (team == null) {
            log(Level.ERROR,
                "UnkownEntityException occurred in TeamServicesImpl#getPosition method.");
            throw new UnknownEntityException("Given positionId is not known.");
        }

        // gets the TeamPosition instance
        TeamPosition position = null;
        TeamPosition[] positions = team.getPositions();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].getPositionId() == positionId) {
                position = positions[i];
                break;
            }
        }

        log(Level.INFO, "Exits TeamServicesImpl#getPosition method.");
        return position;
    }

    /**
     * <p>
     * Removes a position. The team of this position must not be finalized, and this position must
     * be unpublished and unfilled
     * </p>
     * @return OperationResult with the results of the operation
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws UnknownEntityException
     *             If positionId is not known
     * @throws TeamPersistenceException
     *             if error occurred when retrieving Position using TeamManager
     */
    public OperationResult removePosition(long positionId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#removePosition method.");
        try {
            Util.checkIDNotNegative(positionId, "positionId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#removePosition method.");
            throw ex;
        }
        Offer[] offers = getOffers(userId);
        for (int i = 0; i < offers.length; i++) {
            Offer offer = offers[i];
            if (offer.getPositionId() == positionId && offer.getStatus().getStatusType() == OfferStatusType.OFFERED) {
                offerManager.expireOffer(offer.getOfferId());
            }
        }

        // check whether position exists
        logDebug("Starts calling TeamManager#getPosition method.");
        TeamPosition position = teamManager.getPosition(positionId);
        logDebug("Finished calling TeamManager#getPosition method.");
        if (position == null) {
            log(Level.ERROR,
                "UnkownEntityException occurred in TeamServicesImpl#removePosition method.");
            throw new UnknownEntityException(
                "Given positionId has no corresponding Position instance in system.");
        }

        OperationResultImpl operationResult = new OperationResultImpl();
        try {
            logDebug("Starts calling TeamManager#removePosition method.");
            teamManager.removePosition(positionId, userId);
            logDebug("Finished calling TeamManager#removePosition method.");
        } catch (PositionRemovalException ex) {
            log(Level.ERROR, "PositionRemovalException occurred.");
            // if any error occurred, set operationResult to unsuccessful
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            log(Level.INFO, "Exits TeamServicesImpl#removePosition method.");
            return operationResult;
        } catch (com.topcoder.management.team.UnknownEntityException ex) {
            log(Level.ERROR, "com.topcoder.management.team.UnknownEntityException occurred.");
            // if any error occurred, set operationResult to unsuccessful
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            log(Level.INFO, "Exits TeamServicesImpl#removePosition method.");
            return operationResult;
        }

        log(Level.INFO, "Exits TeamServicesImpl#removePosition method.");
        return operationResult;
    }

    /**
     * <p>
     * Validates the team for finalization. The team must be associated with an active project. The
     * calling user must be the team&rsquo;s captain. The project must be currently in the
     * registration phase. All team members must be registered to the project as Free Agents. Team
     * captain must be registered as a resource to the project with the Team Captain ResourceRole.
     * All positions must be already filled. Team must not be already finalized. Sum of payment percentages
     * of team members and captain should be equal to 1.
     * </p>
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to validate
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     * @throws ProjectServicesException
     *             if error occurred when finding active projects
     */
    public OperationResult validateFinalization(long teamId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#validateFinalization method.");
        try {
            Util.checkIDNotNegative(teamId, "teamId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#validateFinalization method.");
            throw ex;
        }

        // checks whether given team is known
        Team team = checkTeamExists(teamId);

        // creates a new instance of OperationResultImpl
        OperationResultImpl operationResult = new OperationResultImpl();

        // validates the team info
        // team's project should be active
        if (!checkProjectOfTeamActive(team, operationResult)) {
            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
            return operationResult;
        }
        // team's project should be in registration phase
        if (!checkProjectOfTeamInRegistration(team, operationResult)) {
            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
            return operationResult;
        }
//        // given userId should match team.captainResourceId
//        if (userId != team.getTeamHeader().getCaptainResourceId()) {
//            operationResult.setSuccessful(false);
//            operationResult
//                .setErrors(new String[] {"Given userId should match team.captainResourceId"});
//            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
//            return operationResult;
//        }
        // all positions' memberResourceId should match Free Agents
//        if (!checkMembersFree(team, operationResult)) {
//            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
//            return operationResult;
//        }
        // team's captain should be a project resource with a role "Team Captain"
        if (!checkTeamCaptainValid(team, operationResult)) {
            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
            return operationResult;
        }
        // each position of team should be filled
        if (!checkPositionsFilled(team, operationResult)) {
            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
            return operationResult;
        }
        
        // Sum of payment percentages of team members and captain should be equal to 1.
        if (!checkPaymentPercentages(team, operationResult)) {
            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
            return operationResult;
        }

        // team should not be finalized before
        if (team.getTeamHeader().isFinalized()) {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {"Team should not be finalized before."});
            log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
            return operationResult;
        }

        log(Level.INFO, "Exits TeamServicesImpl#validateFinalization method.");
        return operationResult;
    }

    /**
     * <p>
     * Checks team's project active.
     * </p>
     * @param team
     *            the full team info
     * @param operationResult
     *            the operation result
     * @return true if team's project is active, false otherwise
     * @throws ProjectServicesException
     *             if error occurred when finding active projects
     */
    private boolean checkProjectOfTeamActive(Team team, OperationResultImpl operationResult) {
        // gets all active active projects
        logDebug("Starts calling ProjectServices#findActiveProjectsHeaders method.");
        Project[] projects = projectServices.findActiveProjectsHeaders();
        logDebug("Finished calling ProjectServices#findActiveProjectsHeaders method.");

        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() == team.getTeamHeader().getProjectId()) {
                return true;
            }
        }

        // team's project is not active, set operationResult to unsuccessful and return false
        operationResult.setSuccessful(false);
        operationResult.setErrors(new String[] {"Team's project is not active now."});
        return false;
    }

    /**
     * <p>
     * Checks team's project is in registration phase.
     * </p>
     * @param team
     *            the team
     * @param operationResult
     *            the operation result
     * @return true if team's project is in registration phase
     */
    private boolean checkProjectOfTeamInRegistration(Team team, OperationResultImpl operationResult) {
        try {
            // gets all phases of team's project
            logDebug("Starts calling PhaseManager#getPhases method.");
            com.topcoder.project.phases.Project phaseProject = phaseManager.getPhases(team
                .getTeamHeader().getProjectId());
            logDebug("Finished calling PhaseManager#getPhases method.");

            Phase[] phases = phaseProject.getAllPhases();
            for (int i = 0; i < phases.length; i++) {
                if (phases[i].getPhaseType().getId() == this.registrationPhaseId
                    && phases[i].getPhaseStatus().getId() == PhaseStatus.OPEN.getId()) {
                    return true;
                }
            }
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "PhaseManagementException occurred.");
            // error occurred, return false, set operationResult to unsuccessful
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return false;
        }

        // project not in registration, set operationResult to unsuccessful
        operationResult.setSuccessful(false);
        operationResult.setErrors(new String[] {"Team's project is not in registration now."});
        return false;
    }

    /**
     * <p>
     * Checks all positions' member resources are free agents.
     * </p>
     * @param team
     *            the full team info
     * @param operationResult
     *            the operation result
     * @return true if all resources are free agents, false otherwise
     */
    private boolean checkMembersFree(Team team, OperationResultImpl operationResult) {
        // gets all free agents
        Resource[] freeAgents = findFreeAgents(team.getTeamHeader().getProjectId());
        // gets all TeamPositions
        TeamPosition[] positions = team.getPositions();

        for (int i = 0; i < positions.length; i++) {
            boolean free = false;
            for (int j = 0; j < freeAgents.length; j++) {
                if (positions[i].getMemberResourceId() == freeAgents[j].getId()) {
                    free = true;
                    break;
                }
            }
            // if not free, set operationResult to unsuccessful and return false
            if (!free) {
                operationResult.setSuccessful(false);
                operationResult
                    .setErrors(new String[] {"Some member resources of given team are not free agents."});
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Check sum of payment percentage of members and captain is equal to 100%.
     * </p>
     *
     * @param team
     *              the full team info
     * @param operationResult
     *              the operation result
     * @return true if sum is equal to 100%, otherwise false.
     */
    private boolean checkPaymentPercentages(Team team, OperationResultImpl operationResult) {
        // gets all TeamPositions
        TeamPosition[] positions = team.getPositions();
        int sum = 0;
        for (int i = 0; i < positions.length; ++i) {
            sum += positions[i].getPaymentPercentage();
        }
        sum += team.getTeamHeader().getCaptainPaymentPercentage();
        if (sum < 100) {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {"Sum of payment percentage of members and " +
                    "captain is not equal to 100%."});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks team captain is a project resource with a role "Team Captain".
     * </p>
     * @param team
     *            the full team info
     * @param operationResult
     *            the operation result
     * @return true if captain of this team is a project resource with a role "Team Captain", false
     *         otherwise
     * @throws ProjectServicesException
     *             if error occurred when finding active projects
     */
    private boolean checkTeamCaptainValid(Team team, OperationResultImpl operationResult) {
        // checks that team captain is in project resources list and its resource role id match the
        // teamCaptainRole id
        logDebug("Starts calling ProjectServices#getFullProjectData method.");
        Resource[] resources = projectServices.getFullProjectData(
            team.getTeamHeader().getProjectId()).getResources();
        logDebug("Finished calling ProjectServices#getFullProjectData method.");

        boolean match = false;
        for (int i = 0; i < resources.length; i++) {
            if (resources[i].getId() == team.getTeamHeader().getCaptainResourceId()
                && resources[i].getResourceRole().getId() == teamCaptainRoleId) {
                match = true;
                break;
            }
        }
        // if not match, return false
        if (!match) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"Team captain should be in project's resources list and"
                    + " its role id should match the teamCaptainRoleId."});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks whether each position in given team has been filled.
     * </p>
     * @param team
     *            the team
     * @param operationResult
     *            the operation result
     * @return true if all positions are filled, false otherwise
     */
    private boolean checkPositionsFilled(Team team, OperationResultImpl operationResult) {
        // gets all TeamPositions
        TeamPosition[] positions = team.getPositions();
        for (int i = 0; i < positions.length; i++) {
            if (!positions[i].getFilled()) {
                // some position not filled, return false
                operationResult.setSuccessful(false);
                operationResult
                    .setErrors(new String[] {"Team still has some unfilled positions, can't be finalized."});
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Finalizes the team. Performs validation to test if team is finalizable, and sets the team
     * captain's role as the submitter.
     * </p>
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to finalize
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     */
    public OperationResult finalizeTeam(long teamId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#finalizeTeam method.");
        try {
            Util.checkIDNotNegative(teamId, "teamId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#finalizeTeam method.");
            throw ex;
        }

        // checks whether this team can be finalized
        OperationResultImpl operationResult = (OperationResultImpl) validateFinalization(teamId,
            userId);
        if (!operationResult.isSuccessful()) {
            log(Level.INFO, "Exits TeamServicesImpl#finalizeTeam method.");
            return operationResult;
        }
        // gets complete team info
        Team team = checkTeamExists(teamId);

        // gets the operator
        ExternalUser operator = getOperator(userId, operationResult);
        if (operator == null) {
            log(Level.INFO, "Exits TeamServicesImpl#finalizeTeam method.");
            return operationResult;
        }

        // sets team's 'finalized' flag to true
        team.getTeamHeader().setFinalized(true);

        // updates role of captain to submitter
        try {
            teamManager.updateTeam(team.getTeamHeader(), userId);

            // gets resource of captain
            logDebug("Starts calling ResourceManager#getResource method.");
            Resource captain = resourceManager.getResource(team.getTeamHeader()
                .getCaptainResourceId());
            logDebug("Finished calling ResourceManager#getResoruce method.");

            // sets its resource role to submitter
            captain.setResourceRole(new ResourceRole(submitterRoleId));

            // updates the captain resource
            logDebug("Starts calling ResourceManager#updateResource method.");
            resourceManager.updateResource(captain, operator.getHandle());
            logDebug("Finished calling ResourceManager#updateResource method.");
            

        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ResourcePersistenceException occurred.");
            // error occurred, return a unsuccessful OperationResult
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
        } catch (TeamManagerException ex) {
            log(Level.ERROR, "TeamManagerException occurred.");
            // error occurred, return a unsuccessful OperationResult
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
        }

        log(Level.INFO, "Exits TeamServicesImpl#finalizeTeam method.");
        return operationResult;
    }

    /**
     * <p>
     * Sends the offer and contact message to the destination user in the offer. The position must
     * be published and unfilled. The project associated with the team must be in the registration
     * phase. The project must be currently in the registration phase. If the sender is a team
     * captain, the receivers must be free agents registered for the project. If the sender is a
     * free agent, the receiver must be the team&rsquo;s captain.
     * </p>
     * <p>
     * Here is the sample XML template data format:
     * 
     * <pre>
     *    &lt;DATA&gt;
     *       &lt;OFFER&gt;
     *           &lt;FROM&gt;joe&lt;/FROM &gt;
     *           &lt;TO&gt;jim&lt;/TO&gt;
     *           &lt;POSITION&gt;futile&lt;/POSITION&gt;
     *           &lt;STATUS&gt;offered&lt;/STATUS&gt;
     *           &lt;PERCENTAGE&gt;25&lt;/PERCENTAGE&gt;
     *           &lt;MESSAGE&gt;Please accept&lt;/MESSAGE&gt;
     *           &lt;REJECTION&gt;Because I can&lt;/REJECTION&gt;
     *       &lt;/OFFER&gt;
     *    &lt;/DATA&gt;
     * </pre>
     * 
     * </p>
     * @return OperationResult with the results of the operation
     * @param offer
     *            Offer to send
     * @throws IllegalArgumentException
     *             If offer is null, or offer.offerId is negative
     * @throws TeamPersistenceException
     *             if error occurred when retrieving team by TeamManager
     * @throws InvalidOfferDataException
     *             If offer violates any validation rules
     * @throws OfferManagerException
     *             if error occurred when sending offer
     * @throws ContactMemberServiceException
     *             If there is a general system error while sending messages
     */
    public OperationResult sendOffer(Offer offer) {
        log(Level.INFO, "Enters TeamServicesImpl#sendOffer method.");
        try {
            Util.checkObjNotNull(offer, "offer");
            if (offer.getOfferId() < 0) {
                throw new IllegalArgumentException("ID of given offer should not be negative.");
            }
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#sendOffer method.");
            throw ex;
        }

        // creates a new OperationResultImpl instance
        OperationResultImpl operationResult = new OperationResultImpl();

        // gets the complete team info associated with given offer
        logDebug("Starts calling TeamManager#getTeamFromPosition method.");
        Team team = teamManager.getTeamFromPosition(offer.getPositionId());
        logDebug("Finished calling TeamManager#getTeamFromPosition method.");
        // if team does not exist, return unsuccessful OperationResult
        if (team == null) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"The team associated with given offer does not exist."});
            log(Level.INFO, "Exits TeamServicesImpl#sendOffer method.");
            return operationResult;
        }
        
        // gets the capt user id
        long captId = -1;
        try {
        captId = Long.parseLong((String)resourceManager.getResource(
                team.getTeamHeader().getCaptainResourceId()).getProperty(EXTERNAL_REFERENCE_ID));
        } catch(Exception e) {
        	// handle quitely
        	logDebug("exception occured while getting Captain's ID");
        }
        
        // gets the position instance
        logDebug("Starts calling TeamManager#getPosition method.");
        TeamPosition position = teamManager.getPosition(offer.getPositionId());
        logDebug("Finished calling TeamManager#getPosition method.");

        // not from TC and position.filled should be false, position.published should be true
        // FIX BUG TCRT-8530
        if ((offer.getFromUserId() != captId) && (position.getFilled() || !position.getPublished())) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"The position associated with given offer should"
                    + " be published and not filled."});
            log(Level.INFO, "Exits TeamServicesImpl#sendOffer method.");
            return operationResult;
        }
        // the project should be in registration phase
        if (!checkProjectOfTeamInRegistration(team, operationResult)) {
            log(Level.INFO, "Exits TeamServicesImpl#sendOffer method.");
            return operationResult;
        }
        // If the sender is a team captain, the receivers must be free agents registered for the
        // project. If the sender is a free agent, the receiver must be the team&rsquo;s captain
        if (!checkReceiverValid(offer, team, operationResult)) {
            log(Level.INFO, "Exits TeamServicesImpl#sendOffer method.");
            return operationResult;
        }

        // sends offer by OfferManager
        logDebug("Starts calling OfferManager#sendOffer method.");
        offerManager.sendOffer(offer);
        logDebug("Finished calling OfferManager#sendOffer method.");

        // prepare message
        Message message = new Message();
        // prepare the message for sending
        if (!prepareMessage(offer, team, sendOfferTemplateName, position, message, operationResult,
            "Sending Offer")) {
            log(Level.INFO, "Exits TeamServicesImpl#sendOffer method.");
            return operationResult;
        }

        // sends the message
        logDebug("Starts calling ContactMemberService#sendMessage method.");
        contactMemberService.sendMessage(message);
        logDebug("Finished calling ContactMemberService#sendMessage method.");

        log(Level.INFO, "Exits TeamServicesImpl#sendOffer method.");
        return operationResult;
    }

    /**
     * <p>
     * Prepares the message to be sent out for offer.
     * </p>
     * @param offer
     *            the offer
     * @param team
     *            the team
     * @param templateName
     *            the template name
     * @param position
     *            the position
     * @param message
     *            the Message instance
     * @param operationResult
     *            the operation result
     * @param subject
     *            subject of the message
     * @return true if preparing message successfully, false otherwise
     */
    private boolean prepareMessage(Offer offer, Team team, String templateName,
        TeamPosition position, Message message, OperationResultImpl operationResult, String subject) {
        try {
            // retrieves sender's handle
            logDebug("Starts calling UserRetrieval#retrieveUser method.");
            String senderHandle = userRetrieval.retrieveUser(offer.getFromUserId()).getHandle();
            logDebug("Finished calling UserRetrieval#retrieveUser method.");
            // retrieves receiver's handle
            logDebug("Starts calling UserRetrieval#retrieveUser method.");
            String receiverHandle = userRetrieval.retrieveUser(offer.getToUserId()).getHandle();
            logDebug("Finished calling UserRetrieval#retrieveUser method.");

            // gets name of this project
            logDebug("Starts calling ProjectManager#getProject method.");
            String projectName = (String) projectManager.getProject(
                team.getTeamHeader().getProjectId()).getProperty(PROJECT_NAME);
            logDebug("Finished calling ProjectManager#getProject method.");

            // gets instance of DocumentGenerator
            DocumentGenerator docGen = DocumentGenerator.getInstance();
            // gets the Template for removing team
            Template template = docGen.getTemplate(templateName);
            // builds template data
            String templateData = buildTemplateDataForOffer(offer, position, senderHandle,
                receiverHandle);
            // generates message text
            String msgText = docGen.applyTemplate(template, templateData);

            // sets up the message
            setUpMessage(message, senderHandle, receiverHandle, msgText, team.getTeamHeader()
                .getProjectId(), projectName, subject);

        } catch (Exception ex) {
            // error occurred, return false
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Sets up message.
     * </p>
     * @param message
     *            the message
     * @param senderHandle
     *            the sender handle
     * @param receiverHandle
     *            the receiver handle
     * @param msgText
     *            the message text
     * @param projectId
     *            the projectId
     * @param projectName
     *            the project name
     * @param subject
     *            the message subject
     */
    private void setUpMessage(Message message, String senderHandle, String receiverHandle,
        String msgText, long projectId, String projectName, String subject) {
        message.setSubject(subject);
        message.setFromHandle(senderHandle);
        message.setToHandles(new String[] {receiverHandle});
        message.setProjectId(projectId);
        message.setProjectName(projectName);
        message.setText(msgText);
        message.setTimeStamp(new Date());
    }

    /**
     * <p>
     * Builds XML template data for sending offer.
     * </p>
     * @param offer
     *            the offer
     * @param position
     *            the position
     * @param senderHandle
     *            the sender handle
     * @param receiverHandle
     *            the receiver handle
     * @return the template data
     */
    private String buildTemplateDataForOffer(Offer offer, TeamPosition position,
        String senderHandle, String receiverHandle) {
        StringBuffer data = new StringBuffer();

        data.append("<DATA><OFFER><FROM>");
        data.append(senderHandle);
        data.append("</FROM ><TO>");
        data.append(receiverHandle);
        data.append("</TO><POSITION>");
        data.append(position.getName());
        data.append("</POSITION><STATUS>");
        data.append(offer.getStatus().getStatusType().getOfferStatusTypeName());
        data.append("</STATUS><PERCENTAGE>");
        data.append(offer.getPercentageOffered());
        data.append("</PERCENTAGE><MESSAGE>");
        data.append(offer.getMessage());
        data.append("</MESSAGE><REJECTION>");
        data.append(offer.getRejectionCause());
        data.append("</REJECTION></OFFER></DATA>");

        return data.toString();
    }

    /**
     * <p>
     * Checks whether receivers are free agents when sender is team captain.
     * </p>
     * <p>
     * If the sender is a team captain, the receivers must be free agents registered for the
     * project. If the sender is a free agent, the receiver must be the team&rsquo;s captain.
     * </p>
     * @param offer
     *            the offer
     * @param team
     *            the team associated with given offer
     * @param the
     *            operation result
     * @return true if receivers are valid, otherwise false
     */
    private boolean checkReceiverValid(Offer offer, Team team, OperationResultImpl operationResult) {
        try {
            // gets the captains user id
            logDebug("Starts calling ResourceManager#getResource method.");
            Long captainId = Long.valueOf(resourceManager.getResource(
                team.getTeamHeader().getCaptainResourceId()).getProperty(EXTERNAL_REFERENCE_ID).toString());
            logDebug("Finished calling ResourceManager#getResource method.");

            // gets all free agents
            Resource[] freeAgents = findFreeAgents(team.getTeamHeader().getProjectId());

            // if sender is the team's captain, then receiver should be a free agent
            if (offer.getFromUserId() == captainId.longValue()) {
                for (int i = 0; i < freeAgents.length; i++) {
                    if (Long.parseLong(freeAgents[i].getProperty(EXTERNAL_REFERENCE_ID).toString()) == offer
                        .getToUserId()) {
                        return true;
                    }
                }
                // receiver is not a free agent, return false;
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {"Receiver of offer should be a free agent"
                    + " when sender is the team captain."});
                return false;

            } else {
                // sender should be some free agent
                boolean free = false;
                for (int i = 0; i < freeAgents.length; i++) {
                    if (Long.parseLong(freeAgents[i].getProperty(EXTERNAL_REFERENCE_ID).toString()) == offer
                        .getFromUserId()) {
                        free = true;
                        break;
                    }
                }
                // if sender is not a free agent, return false
                if (!free) {
                    operationResult.setSuccessful(false);
                    operationResult
                        .setErrors(new String[] {"Send should be a free agent when it isn't the team captain."});
                    return false;
                }
                // receiver should be the team captain
                if (offer.getToUserId() != captainId.longValue()) {
                    operationResult.setSuccessful(false);
                    operationResult
                        .setErrors(new String[] {"Receiver of offer should be team captain "
                            + "when sender is a free agent."});
                    return false;
                }
            }
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR, "ResourcePersistenceException occurred.");
            // error occurred, return false
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Gets all offers for the given user that are unanswered or not expired by that user.
     * </p>
     * @return Offer array of offers that match the search criteria
     * @param userId
     *            The ID of the user whose offer receivables we want
     * @throws IllegalArgumentException
     *             If userId is negative
     * @throws OfferManagerException
     *             If any offer-related issue comes up, such as a failure to retrieve the offers
     */
    public Offer[] getOffers(long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#getOffers method.");
        try {
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#getOffers method.");
            throw ex;
        }

        // constructs a filter to retrieve all unanswered an non-expired offers for toUserId=userId
        Filter filter = OfferFilterFactory.createReceiverIdFilter(userId);
        // finds all matching offers
        logDebug("Starts calling OfferManager#findOffers method.");
        Offer[] offers = offerManager.findOffers(filter);
        logDebug("Finished calling OfferManager#findOffers method.");

        log(Level.INFO, "Exits TeamServicesImpl#getOffers method.");
        return offers;
    }

    /**
     * <p>
     * Accepts the offer and sets the position resource ID, payment percentage and making the
     * position filled and sends a message to the user informing of the acceptance. The position
     * must be unfilled. The project associated with the team must be in the registration phase. The
     * new payment percentage must not make the team total to exceed 100%.
     * </p>
     * <p>
     * Here is the sample XML template data format:
     * 
     * <pre>
     *    &lt;DATA&gt;
     *        &lt;OFFER&gt;
     *            &lt;FROM&gt;joe&lt;/FROM &gt;
     *            &lt;TO&gt;jim&lt;/TO&gt;
     *            &lt;POSITION&gt;futile&lt;/POSITION&gt;
     *            &lt;STATUS&gt;offered&lt;/STATUS&gt;
     *            &lt;PERCENTAGE&gt;25&lt;/PERCENTAGE&gt;
     *            &lt;MESSAGE&gt;Please accept&lt;/MESSAGE&gt;
     *            &lt;REJECTION&gt;Because I can&lt;/REJECTION&gt;
     *        &lt;/OFFER&gt;
     *    &lt;/DATA&gt;
     * </pre>
     * 
     * </p>
     * @return OperationResult with the results of the operation
     * @param offerId
     *            The ID of the offer to accept
     * @param userId
     *            The ID of the user who is accepting the offer
     * @throws IllegalArgumentException
     *             If offerId or userId is negative
     * @throws TeamPersistenceException
     *             if error occurred when retrieving team by TeamManager
     * @throws OfferManagerException
     *             If any offer-related issue comes up, such as a failure to retrieve the offers
     * @throws ContactMemberServiceException
     *             If there is a general system error while sending messages
     */
    public OperationResult acceptOffer(long offerId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#acceptOffer method.");
        try {
            Util.checkIDNotNegative(offerId, "offerId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#acceptOffer method.");
            throw ex;
        }

        // creates a new instance of OperationResult
        OperationResultImpl operationResult = new OperationResultImpl();

        // retrieves the Offer instance by given offerId
        Offer offer = retrieveOffer(offerId, operationResult);
        if (offer == null) {
            log(Level.INFO, "Enters TeamServicesImpl#acceptOffer method.");
            return operationResult;
        }

        // gets complete team info associated with given offer, if no exists, return unsuccessful
        // operationResult
        logDebug("Starts calling TeamManager#getTeamFromPosition method.");
        Team team = teamManager.getTeamFromPosition(offer.getPositionId());
        logDebug("Finished calling TeamManager#getTeamFromPosition method.");
        if (team == null) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"Team associated with given offer should exist."});
            return operationResult;
        }
        // gets the TeamPosition associated with given offer
        logDebug("Starts calling TeamManager#getPosition method.");
        TeamPosition position = teamManager.getPosition(offer.getPositionId());
        logDebug("Finished calling TeamManager#getPosition method.");

        // position should be not filled
        if (position.getFilled()) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"The position associated with offer should not be filled."});
            return operationResult;
        }
        // the project should be in registration phase
        if (!checkProjectOfTeamInRegistration(team, operationResult)) {
            log(Level.INFO, "Enters TeamServicesImpl#acceptOffer method.");
            return operationResult;
        }
        // sum of payment percentage should not exceed 100
        if (!checkPaymentSumValid(team, offer, operationResult)) {
            log(Level.INFO, "Enters TeamServicesImpl#acceptOffer method.");
            return operationResult;
        }
        // the user should not already accepted an offer for this project
        if (!checkUserNotAcceptedOfferBefore(team, offer, operationResult)) {
            log(Level.INFO, "Enters TeamServicesImpl#acceptOffer method.");
            return operationResult;
        }

        // accepts this offer
        logDebug("Starts calling OfferManager#acceptOffer method.");
        offerManager.acceptOffer(offerId);
        logDebug("Finished calling OfferManager#accepteOffer method.");

        // sets the position with offer
        FullProjectData fullProjectData = projectServices.getFullProjectData(team.getTeamHeader().getProjectId());
        Resource[] projectResources = fullProjectData.getResources();
        long toResource = getResourceIdForUser(projectResources, offer.getToUserId());
        long fromResource = getResourceIdForUser(projectResources, offer.getFromUserId());
        
        if (team.getTeamHeader().getCaptainResourceId() == toResource) {
            position.setMemberResourceId(fromResource);
        } else {
            position.setMemberResourceId(toResource);
        }

        position.setPaymentPercentage(offer.getPercentageOffered());
        position.setFilled(true);
        try {
            // updates the position
            logDebug("Starts calling TeamManager#updatePosition method.");
            teamManager.updatePosition(position, userId);
            logDebug("Finished calling TeamManager#updatePosition method.");
        } catch (InvalidPositionException ex) {
            log(Level.ERROR, "InvalidPositionException occurred.");
            // error occurred when updating position, return unsuccessful operationResult
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return operationResult;
        } catch (com.topcoder.management.team.UnknownEntityException ex) {
            log(Level.ERROR, "UnknownEntityException occurred.");
            // error occurred when updating position, return unsuccessful operationResult
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return operationResult;
        } catch (TeamPersistenceException ex) {
            log(Level.ERROR, "TeamPersistenceException occurred.");
            // error occurred when updating position, return unsuccessful operationResult
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return operationResult;
        }
        // represents the Message to be sent
        Message message = new Message();
        // prepare the message for sending
        if (!prepareMessage(offer, team, acceptOfferTemplateName, position, message,
            operationResult, "Accepting Offer")) {
            log(Level.INFO, "Exits TeamServicesImpl#sendOffer method.");
            return operationResult;
        }
        // sends the message
        logDebug("Starts calling ContactMemberService#sendMessage method.");
        contactMemberService.sendMessage(message);
        logDebug("Finished calling ContactMemberService#sendMessage method.");

        log(Level.INFO, "Enters TeamServicesImpl#acceptOffer method.");
        return operationResult;
    }

    private long getResourceIdForUser(Resource[] projectResources, long resourceUserId) {
        for (int i = 0; i < projectResources.length; i++) {
            Resource resource = projectResources[i];
            if (resourceUserId == Long.parseLong(resource.getProperty(EXTERNAL_REFERENCE_ID).toString())) {
                return resource.getId();
            }
        }
        return -1;
    }

    /**
     * <p>
     * Retrieves the Offer instance with given offer id.
     * </p>
     * @param offerId
     *            the offer id
     * @param operationResult
     *            the operation result instance
     * @return the retrieved offer, null if not found
     * @throws OfferManagerException
     *             If any offer-related issue comes up, such as a failure to retrieve the offers
     */
    private Offer retrieveOffer(long offerId, OperationResultImpl operationResult) {
        // retrieves the Offer instance by given offerId
        logDebug("Starts calling OfferManager#findOffers method.");
        Offer[] offer = offerManager.findOffers(OfferFilterFactory.createOfferIdFilter(offerId));
        logDebug("Finished calling OfferManager#findOffers method.");
        // if not one offer retrieved, return unsuccessful operationResult
        if (offer.length != 1) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"There should be one and only one offer exists with given offer id."});
            return null;
        }
        return offer[0];
    }

    /**
     * <p>
     * Checks the sum of payment percentage not exceeds 100.
     * </p>
     * @param team
     *            the team associated with offer
     * @param offer
     *            the offer
     * @param operationResult
     *            the operationResult
     * @return true if sum does not exceed 100, false otherwise
     */
    private boolean checkPaymentSumValid(Team team, Offer offer, OperationResultImpl operationResult) {
        int sum = 0;
        sum += team.getTeamHeader().getCaptainPaymentPercentage();
        sum += offer.getPercentageOffered();
        TeamPosition[] positions = team.getPositions();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].getPositionId() == offer.getPositionId()) {
                continue;
            }
            sum += positions[i].getPaymentPercentage();
        }
        if (sum > 100) {
            operationResult.setSuccessful(false);
            operationResult
            .setErrors(new String[] {"Sum of payments percentage should not exceed 100."});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks the sum of payment percentage not exceeds 100.
     * </p>
     * @param team
     *            the team associated with offer
     * @param offer
     *            the offer
     * @param operationResult
     *            the operationResult
     * @return true if sum does not exceed 100, false otherwise
     */
    private boolean checkPaymentSumValid(Team team, TeamPosition position, OperationResultImpl operationResult) {
        int sum = 0;
        sum += team.getTeamHeader().getCaptainPaymentPercentage();
        sum += position.getPaymentPercentage();
        TeamPosition[] positions = team.getPositions();
        for (int i = 0; i < positions.length; i++) {
            sum += positions[i].getPaymentPercentage();
        }
        
        if (sum > 100) {
            operationResult.setSuccessful(false);
            operationResult
            .setErrors(new String[] {"Sum of payments percentage should not exceed 100."});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks user has not already accepted an offer for this project.
     * </p>
     * @param team
     *            the team
     * @param offer
     *            the offer
     * @param operationResult
     *            the operationResult
     * @return true if user has not already accepted an offer for this project, otherwise false
     * @throws OfferManagerException
     *             If any offer-related issue comes up, such as a failure to retrieve the offers
     * @throws TeamPersistenceException
     *             if any error occurred when retrieving complete team
     */
    private boolean checkUserNotAcceptedOfferBefore(Team team, Offer offer,
        OperationResultImpl operationResult) {
        // creates the filter to retrieve offers
        Filter statusFilter = OfferFilterFactory
            .createOfferStatusTypeFilter(OfferStatusType.ACCEPTED);
        Filter receiverFilter = OfferFilterFactory.createReceiverIdFilter(offer.getToUserId());
        Filter filter = OfferFilterFactory.createAndFilter(statusFilter, receiverFilter);

        // finds offers using OfferManager
        logDebug("Starts calling OfferManager#findOffers method.");
        Offer[] offers = offerManager.findOffers(filter);
        logDebug("Finished calling OfferManager#findOffers method.");

        // check there is no offer returned associated with specific project
        boolean in = false;
        for (int i = 0; i < offers.length; i++) {
            // gets the complete team with offers[i].positionId
            logDebug("Starts calling TeamManager#getTeamFromPosition method.");
            Team theTeam = teamManager.getTeamFromPosition(offers[i].getPositionId());
            logDebug("Finished calling TeamManager#getTeamFromPosition method.");
            // theTeam.projectId should not equal to given team's projectId
            if (theTeam.getTeamHeader().getProjectId() == team.getTeamHeader().getProjectId()) {
                in = true;
                break;
            }
        }
        // if some offers associated with specific project found, return false
        if (in) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"The user should not already accepted an offer for this project."});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Rejects the offer. It sends a notification message to offerer stating this.
     * </p>
     * <p>
     * Here is the sample XML template data format:
     * 
     * <pre>
     *    &lt;DATA&gt;
     *        &lt;OFFER&gt;
     *            &lt;FROM&gt;joe&lt;/FROM &gt;
     *            &lt;TO&gt;jim&lt;/TO&gt;
     *            &lt;POSITION&gt;futile&lt;/POSITION&gt;
     *            &lt;STATUS&gt;offered&lt;/STATUS&gt;
     *            &lt;PERCENTAGE&gt;25&lt;/PERCENTAGE&gt;
     *            &lt;MESSAGE&gt;Please accept&lt;/MESSAGE&gt;
     *            &lt;REJECTION&gt;Because I can&lt;/REJECTION&gt;
     *        &lt;/OFFER&gt;
     *    &lt;/DATA&gt;
     * </pre>
     * 
     * </p>
     * @return OperationResult with the results of the operation
     * @param offerId
     *            The ID of the offer to reject
     * @param cause
     *            The reason for the rejection
     * @param userId
     *            The ID of the user who is rejecting the offer
     * @throws IllegalArgumentException
     *             If offerId or userId is negative
     * @throws TeamPersistenceException
     *             if error occurred when retrieving team by TeamManager
     * @throws OfferManagerException
     *             If any offer-related issue comes up, such as a failure to retrieve the offers
     * @throws ContactMemberServiceException
     *             If there is a general system error while sending messages
     */
    public OperationResult rejectOffer(long offerId, String cause, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#rejectOffer method.");
        try {
            Util.checkIDNotNegative(offerId, "offerId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#rejectOffer method.");
            throw ex;
        }

        // creates a new instance of OperationResult
        OperationResultImpl operationResult = new OperationResultImpl();

        // retrieves the Offer instance by given offerId
        Offer offer = retrieveOffer(offerId, operationResult);
        if (offer == null) {
            log(Level.INFO, "Exits TeamServicesImpl#rejectOffer method.");
            return operationResult;
        }
        // rejects the offer using OfferManager
        logDebug("Starts calling OfferManager#rejectOffer method.");
        offerManager.rejectOffer(offerId, cause);
        logDebug("Finished calling OfferManager#rejectOffer method.");

        // gets complete team info associated with given offer
        logDebug("Starts calling TeamManager#getTeamFromPosition method.");
        Team team = teamManager.getTeamFromPosition(offer.getPositionId());
        logDebug("Finished calling TeamManager#getTeamFromPosition method.");
        if (team == null) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"Team associated with given offer should exist."});
            return operationResult;
        }
        // gets the TeamPosition associated with given offer
        logDebug("Starts calling TeamManager#getPosition method.");
        TeamPosition position = teamManager.getPosition(offer.getPositionId());
        logDebug("Finished calling TeamManager#getPosition method.");

        // represents the Message to be sent
        Message message = new Message();
        // prepare the message for sending
        if (!prepareMessage(offer, team, rejectOfferTemplateName, position, message,
            operationResult, "Rejecting Offer")) {
            log(Level.INFO, "Exits TeamServicesImpl#rejectOffer method.");
            return operationResult;
        }
        // sends the message
        logDebug("Starts calling ContactMemberService#sendMessage method.");
        contactMemberService.sendMessage(message);
        logDebug("Finished calling ContactMemberService#sendMessage method.");

        log(Level.INFO, "Exits TeamServicesImpl#rejectOffer method.");
        return operationResult;
    }

    /**
     * <p>
     * Removes a team member from a team and sends a notification message to the team captain. It
     * only removes team members. Not team captains. It must send a notification of the removal to
     * the team captain. If the team is finalized, the payment percentage of the removed member is
     * distributed evenly along all members including the team captain.
     * </p>
     * <p>
     * Here is the sample XML template data format:
     * 
     * <pre>
     *    &lt;DATA&gt;
     *        &lt;HANDLE&gt;removed&lt;/HANDLE&gt;
     *        &lt;POSITION_NAME&gt;position name&lt;/POSITION_NAME&gt;
     *        &lt;TEAM_NAME&gt;name&lt;/TEAM_NAME&gt;
     *        &lt;PROJECT_NAME&gt;project name&lt;/PROJECT_NAME&gt;
     *        &lt;CAPTAIN_HANDLE&gt;handle&lt;/CAPTAIN_HANDLE&gt;
     *    &lt;/DATA&gt;
     * </pre>
     * 
     * </p>
     * @return OperationResult with the results of the operation
     * @param resourceId
     *            The resource to remove from the team
     * @param userId
     *            userId The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If resourceId or userId is negative
     * @throws UnknownEntityException
     *             If resourceId is not known
     * @throws TeamPersistenceException
     *             if error occurred when operating TeamManager
     */
    public OperationResult removeMember(long resourceId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#removeMember method.");
        try {
            Util.checkIDNotNegative(resourceId, "resourceId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#removeMember method.");
            throw ex;
        }

        // creates a new instance of OperationResult
        OperationResultImpl operationResult = new OperationResultImpl();

        // checks given resource exists, if not, throw a UnknownEntityException
        Resource resource = checkResourceExists(resourceId, operationResult);
        if (resource == null) {
            log(Level.INFO, "Exits TeamServicesImpl#removeMember method.");
            return operationResult;
        }
        // retrieve TeamPosition with resourceId
        logDebug("Starts calling TeamManager#findPositions method.");
        TeamPosition[] positions = teamManager.findPositions(PositionFilterFactory
            .createResourceIdFilter(resourceId));
        logDebug("Finished calling TeamManager#findPosition method.");
        // there should be one and only one position exists
        if (positions.length != 1) {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {"There should be one and only "
                + "one position associated with given resourceId."});
            return operationResult;
        }
        // gets complete team info
        logDebug("Starts calling TeamManager#getTeamFromPosition method.");
        Team team = teamManager.getTeamFromPosition(positions[0].getPositionId());
        logDebug("Finished calling TeamManager#getTeamFromPosition method.");

        if (team.getTeamHeader().isFinalized()) {
            if (!updatePayments(team, positions[0], operationResult, userId)) {
                log(Level.INFO, "Exits TeamServicesImpl#removeMember method.");
                return operationResult;
            }
        } else {
            
            TeamPosition pos = new TeamPosition();
            pos.setDescription(positions[0].getDescription());
            pos.setName(positions[0].getName());
            pos.setFilled(false);
            pos.setPaymentPercentage(positions[0].getPaymentPercentage());
            pos.setPositionId(positions[0].getPositionId());
            pos.setPublished(positions[0].getPublished());
            
            try {
                // updates this position
                logDebug("Starts calling TeamManager#updatePosition method.");
                teamManager.updatePosition(pos, userId);
                logDebug("Finished calling TeamManager#updatePosition method.");
            } catch (InvalidPositionException ex) {
                // error occurred, return false
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {ex.getMessage()});
                return operationResult;
            } catch (com.topcoder.management.team.UnknownEntityException ex) {
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {ex.getMessage()});
                return operationResult;
            } catch (TeamPersistenceException ex) {
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {ex.getMessage()});
                return operationResult;
            }
        }
        Message message = new Message();
        try {
            // retrieves the operator handle
            logDebug("Starts calling UserRetrieval#retrieveUser method.");
            ExternalUser operator = userRetrieval.retrieveUser(userId);
            logDebug("Finished calling UserRetrieval#retrieveUser method.");
            String operatorHandle = operator.getHandle();

            // retrieve the captain handle
            logDebug("Starts calling ResourceManager#getResource method.");
            Resource captain = resourceManager.getResource(team.getTeamHeader()
                .getCaptainResourceId());
            logDebug("Finished calling ResourceManager#getResource method.");
            String captainHandle = (String) captain.getProperty(HANDLE);

            // gets name of this project
            logDebug("Starts calling ProjectManager#getProject method.");
            String projectName = (String) projectManager.getProject(
                team.getTeamHeader().getProjectId()).getProperty(PROJECT_NAME);
            logDebug("Finished calling ProjectManager#getProject method.");

            // gets instance of DocumentGenerator
            DocumentGenerator docGen = DocumentGenerator.getInstance();
            // gets the Template for removing team
            Template template = docGen.getTemplate(removeMemberMessageTemplateName);
            // builds template data
            String data = buildsTemplateDataForRemovingMember(
                (String) resource.getProperty(HANDLE), positions[0].getName(), team.getTeamHeader()
                    .getName(), projectName, captainHandle);
            // generate the message text
            String msgText = docGen.applyTemplate(template, data);

            message.setProjectId(team.getTeamHeader().getProjectId());
            message.setText(msgText);
            message.setToHandles(new String[] {captainHandle});
            message.setFromHandle(operatorHandle);
            message.setProjectName(projectName);
            message.setTimeStamp(new Date());
        } catch (Exception ex) {
            // error occurred when updating position, return unsuccessful operationResult
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return operationResult;
        }
        // sends the message
        logDebug("Starts calling ContactMemberService#sendMessage method.");
        contactMemberService.sendMessage(message);
        logDebug("Finished calling ContactMemberService#sendMessage method.");

        log(Level.INFO, "Exits TeamServicesImpl#removeMember method.");
        return operationResult;
    }

    /**
     * <p>
     * Builds template data for removing member.
     * </p>
     * @param handle
     *            the handle of user to be removed
     * @param positionName
     *            name of this position
     * @param teamName
     *            name of the team
     * @param projectName
     *            name of the project
     * @param captainHandle
     *            handle of the captain
     * @return the template data for removing member
     */
    private String buildsTemplateDataForRemovingMember(String handle, String positionName,
        String teamName, String projectName, String captainHandle) {
        StringBuffer data = new StringBuffer();
        data.append("<DATA><HANDLE>");
        data.append(handle);
        data.append("</HANDLE><POSITION_NAME>");
        data.append(positionName);
        data.append("</POSITION_NAME><TEAM_NAME>");
        data.append(teamName);
        data.append("</TEAM_NAME><PROJECT_NAME>");
        data.append(projectName);
        data.append("</PROJECT_NAME><CAPTAIN_HANDLE>");
        data.append(captainHandle);
        data.append("</CAPTAIN_HANDLE></DATA>");

        return data.toString();
    }

    /**
     * <p>
     * Updates payments before removing team member.
     * </p>
     * @param team
     *            the team
     * @param position
     *            the position
     * @param operationResult
     *            the operation result
     * @param userId
     *            the operator id
     * @return true if updating payments successfully, false otherwise
     * @throws TeamPersistenceException
     *             if error occurred when operating TeamManager
     */
    private boolean updatePayments(Team team, TeamPosition position,
        OperationResultImpl operationResult, long userId) {
        // gets all positions in this team
        TeamPosition[] positions = team.getPositions();
        // calculates the payment increase
        int inc = position.getPaymentPercentage() / positions.length;
        // increase each member's payment by inc except the given one and updates it
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].getPositionId() == position.getPositionId()) {
                continue;
            }
            positions[i].setPaymentPercentage(positions[i].getPaymentPercentage() + inc);
            try {
                // updates this position
                logDebug("Starts calling TeamManager#updatePosition method.");
                teamManager.updatePosition(positions[i], userId);
                logDebug("Finished calling TeamManager#updatePosition method.");
            } catch (InvalidPositionException ex) {
                // error occurred, return false
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {ex.getMessage()});
                return false;
            } catch (com.topcoder.management.team.UnknownEntityException ex) {
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {ex.getMessage()});
                return false;
            } catch (TeamPersistenceException ex) {
                operationResult.setSuccessful(false);
                operationResult.setErrors(new String[] {ex.getMessage()});
                return false;
            }
        }
        // increase the captain's payment by inc
        team.getTeamHeader().setCaptainPaymentPercentage(
            team.getTeamHeader().getCaptainPaymentPercentage() + inc);
        try {
            // removes given position
            teamManager.removePosition(position.getPositionId(), userId);
        } catch (PositionRemovalException ex) {
            // error occurred, return false
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return false;
        } catch (com.topcoder.management.team.UnknownEntityException ex) {
            // error occurred, return false
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks whether given resource exists.
     * </p>
     * @param resourceId
     *            the resource id
     * @param operationResult
     *            the operation result
     * @return the Resource instance
     * @throws UnknownEntityException
     *             If resourceId is not known
     */
    private Resource checkResourceExists(long resourceId, OperationResultImpl operationResult) {
        // checks given resource exists, if not, throw a UnknownEntityException
        Resource resource = null;
        try {
            logDebug("Starts calling ResourceManager#getResource method.");
            resource = resourceManager.getResource(resourceId);
            logDebug("Finished calling ResourceManager#getResource method.");
            if (resource == null) {
                log(Level.ERROR, "UnknownEntityException occurred.");
                throw new UnknownEntityException("The Resource instance with [ID=" + resourceId
                    + "] does not exist.");
            }
        } catch (ResourcePersistenceException ex) {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {ex.getMessage()});
            return null;
        }
        return resource;
    }

    /**
     * <p>
     * Forces the finalization of all not finalized teams. All such teams must still have captains
     * that are registered resources in the project and all members must be free agents in that
     * project. All unfilled positions are removed and their percentage payments redistributed among
     * the remaining members.
     * </p>
     * @return OperationResult with the results of the operation
     * @param projectId
     *            The ID of the project whose unfinalized teams are forced to be finalized
     * @param userId
     *            userId The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If projectId or userId is negative
     * @throws UnknownEntityException
     *             If projectId is not known
     * @throws ProjectServicesException
     *             if error occurred when retrieving full project data
     * @throws TeamPersistenceException
     *             if any error occurred when retrieving the full team
     */
    public OperationResult forceFinalization(long projectId, long userId) {
        log(Level.INFO, "Enters TeamServicesImpl#forceFinalization method.");
        try {
            Util.checkIDNotNegative(projectId, "projectId");
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in TeamServicesImpl#forceFinalization method.");
            throw ex;
        }
        // checks given project exists
        checkProjectExist(projectId);

        // creates a new OperationResult instance
        OperationResultImpl operationResult = new OperationResultImpl();

        // gets all free agents
        Resource[] freeAgents = findFreeAgents(projectId);

        // gets all teams that are not finalized in the project
        logDebug("Starts calling TeamManager#findTeams method.");
        TeamHeader[] teams = teamManager.findTeams(TeamFilterFactory.createFinalizedFilter(false));
        logDebug("Finished calling TeamManager#findTeams method.");

        for (int i = 0; i < teams.length; i++) {
            // gets complete team info
            Team team = teamManager.getTeam(teams[i].getTeamId());
            // check all filled positions have memberResourceId in freeAgents
            if (!checkFilledPositionsResourceFree(team, freeAgents, operationResult)) {
                log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
                return operationResult;
            }
            // checks team captain valid
            if (!checkTeamCaptainFree(team, operationResult)) {
                log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
                return operationResult;
            }
            // removes all unfilled positions, gets the sum of their payments
            int paymentsInc = 0;
            TeamPosition[] positions = team.getPositions();
            int members = positions.length;
            for (int j = 0; j < positions.length; j++) {
                if (!positions[j].getFilled()) {
                    members--;
                    paymentsInc += positions[j].getPaymentPercentage();
                    try {
                        logDebug("Starts calling TeamManager#reomvePosition method.");
                        teamManager.removePosition(positions[j].getPositionId(), userId);
                        logDebug("Finished calling TeamManager#removePosition method.");
                    } catch (com.topcoder.management.team.UnknownEntityException ex) {
                        operationResult.setSuccessful(false);
                        operationResult.setErrors(new String[] {ex.getMessage()});
                        log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
                        return operationResult;
                    } catch (PositionRemovalException ex) {
                        operationResult.setSuccessful(false);
                        operationResult.setErrors(new String[] {ex.getMessage()});
                        log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
                        return operationResult;
                    }
                }
            }
            // distributes the payments to existing members and captain
            int inc = paymentsInc / (1 + members);
            positions = team.getPositions();
            for (int j = 0; j < positions.length; j++) {
                positions[j].setPaymentPercentage(positions[j].getPaymentPercentage() + inc);
                try {
                    logDebug("Starts calling TeamManager#updatePosition method.");
                    teamManager.updatePosition(positions[j], userId);
                    logDebug("Finished calling TeamManager#updatePosition method.");
                } catch (InvalidPositionException ex) {
                    operationResult.setSuccessful(false);
                    operationResult.setErrors(new String[] {ex.getMessage()});
                    log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
                    return operationResult;
                } catch (com.topcoder.management.team.UnknownEntityException ex) {
                    operationResult.setSuccessful(false);
                    operationResult.setErrors(new String[] {ex.getMessage()});
                    log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
                    return operationResult;
                } catch (TeamPersistenceException ex) {
                    operationResult.setSuccessful(false);
                    operationResult.setErrors(new String[] {ex.getMessage()});
                    log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
                    return operationResult;
                }
            }
            // add the payment inc to captain
            team.getTeamHeader().setCaptainPaymentPercentage(
                team.getTeamHeader().getCaptainPaymentPercentage() + inc);
            // sets team to be finalized
            team.getTeamHeader().setFinalized(true);
        }

        log(Level.INFO, "Exits TeamServicesImpl#forceFinalization method.");
        return operationResult;
    }

    /**
     * <p>
     * Checks the team captain is a project resource with role Team Captain.
     * </p>
     * @param team
     *            the team
     * @param operationResult
     *            the operation result
     * @return true if the team captain is a project resource with role Team Captain, false
     *         otherwise
     * @throws ProjectServicesException
     *             if error occurred when retrieving full project data
     */
    private boolean checkTeamCaptainFree(Team team, OperationResultImpl operationResult) {
        // gets all resources of this project
        logDebug("Starts calling ProjectServices#getFullProjectData method.");
        Resource[] resources = projectServices.getFullProjectData(
            team.getTeamHeader().getProjectId()).getResources();
        logDebug("Finished calling ProjectServices#getFullProjectData method.");
        // checks captain is a resource of this project
        Resource captain = null;
        for (int i = 0; i < resources.length; i++) {
            if (team.getTeamHeader().getCaptainResourceId() == resources[i].getId()) {
                captain = resources[i];
                break;
            }
        }
        if (captain == null) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"Team captain is not a Resource in current project."});
            return false;
        }
        // checks captain has a role 'Team Captain'
        if (captain.getResourceRole().getId() != teamCaptainRoleId) {
            operationResult.setSuccessful(false);
            operationResult
                .setErrors(new String[] {"Team captain's role is not a 'Team Captain' role."});
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks whether all filled positions of given team have memberResourceIds in given free agents
     * list.
     * </p>
     * @param team
     *            the team
     * @param freeAgents
     *            the free agents array
     * @param operationResult
     *            the operation result
     * @return true if all filled positions of given team have memberResourceIds in given free
     *         agents list, false otherwise
     */
    private boolean checkFilledPositionsResourceFree(Team team, Resource[] freeAgents,
        OperationResultImpl operationResult) {
        // gets all positions
        TeamPosition[] positions = team.getPositions();
        for (int i = 0; i < positions.length; i++) {
            if (!positions[i].getFilled()) {
                continue;
            }
            // gets the memberResourceId of this position
            long id = positions[i].getMemberResourceId();
            boolean in = false;
            for (int j = 0; j < freeAgents.length; j++) {
                if (id == freeAgents[j].getId()) {
                    in = true;
                    break;
                }
            }
            if (!in) {
                operationResult.setSuccessful(false);
                operationResult
                    .setErrors(new String[] {"Some member resources of positions are not free agents."});
                return false;
            }
        }
        return true;
    }
}
