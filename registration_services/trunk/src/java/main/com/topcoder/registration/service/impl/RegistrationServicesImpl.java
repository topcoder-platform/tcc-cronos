/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserRetrieval;
import com.topcoder.management.ban.BanManager;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.team.PositionFilterFactory;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.registration.contactmember.service.ContactMemberService;
import com.topcoder.registration.contactmember.service.Message;
import com.topcoder.registration.service.CustomResourceProperties;
import com.topcoder.registration.service.InvalidRoleException;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationPosition;
import com.topcoder.registration.service.RegistrationResult;
import com.topcoder.registration.service.RegistrationServiceConfigurationException;
import com.topcoder.registration.service.RegistrationServiceException;
import com.topcoder.registration.service.RegistrationServices;
import com.topcoder.registration.service.RegistrationValidationException;
import com.topcoder.registration.service.RegistrationValidator;
import com.topcoder.registration.service.RemovalResult;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.TeamServices;
import com.topcoder.registration.team.service.UnknownEntityException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.InvalidConfigException;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.templatesource.TemplateSourceException;
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
 * This class is a full implementation of the <code>RegistrationServices</code> interface. This
 * implementation makes use of a large array of components to accomplish its task of managing
 * registrations. First, it relies on the <code>RegistrationValidator</code> to perform
 * validations in both the <code>validateRegistration</code> and <code>registerForProject</code>
 * methods. It makes use of the <b>UserDataStorePersistence</b> component to retrieve full user
 * information. It uses the <b>Phase</b> and <b>Resource Management</b>, <b>Project Phases</b>,
 * <b>Project Services</b>,<b>Team Management</b>, and <b>Team Services</b> components to
 * create, get, find, and delete registration information.
 * </p>
 * <p>
 * To provide a good view as the steps are progressing in each method, the <b>Logging Wrapper</b>
 * component is used in each method. To configure this component, the <b>ConfigManager</b> and
 * <b>ObjectFactory</b> components are used.
 * </p>
 * <p>
 * Here is the sample configuration file for this class.
 *
 * <pre>
 *  &lt;Config name=&quot;com.topcoder.registration.service.impl.RegistrationServicesImpl&quot;&gt;
 *      &lt;Property name=&quot;specNamespace&quot;&gt;
 *          &lt;Value&gt;com.topcoder.util.objectfactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;validatorKey&quot;&gt;
 *          &lt;Value&gt;validatorKey&lt;/Value&gt;
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
 *      &lt;Property name=&quot;banManagerKey&quot;&gt;
 *          &lt;Value&gt;banManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *          &lt;Value&gt;teamManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;teamServicesKey&quot;&gt;
 *          &lt;Value&gt;teamServicesKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;contactMemberServiceKey&quot;&gt;
 *          &lt;Value&gt;contactMemberServiceKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;loggerName&quot;&gt;
 *          &lt;Value&gt;SystemLog&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;registrationPhaseId&quot;&gt;
 *          &lt;Value&gt;1&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;availableRoleIds&quot;&gt;
 *          &lt;Value&gt;1&lt;/Value&gt;
 *      &lt;Value&gt;2&lt;/Value&gt;
 *      &lt;/Property&gt;
 *          &lt;Property name=&quot;externalRoleIds&quot;&gt;
 *      &lt;Value&gt;3&lt;/Value&gt;
 *      &lt;Value&gt;4&lt;/Value&gt;
 *          &lt;/Property&gt;
 *      &lt;Property name=&quot;teamCaptainRoleId&quot;&gt;
 *      &lt;Value&gt;2&lt;/Value&gt;
 *          &lt;/Property&gt;
 *      &lt;Property name=&quot;operator&quot;&gt;
 *      &lt;Value&gt;1&lt;/Value&gt;
 *          &lt;/Property&gt;
 *      &lt;Property name=&quot;activeProjectStatusId&quot;&gt;
 *      &lt;Value&gt;2&lt;/Value&gt;
 *          &lt;/Property&gt;
 *      &lt;Property name=&quot;removalMessageTemplateName&quot;&gt;
 *          &lt;Value&gt;test_files/doc_gen_files/template.txt&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is immutable but operates on non thread safe objects, thus making it
 * potentially non thread safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RegistrationServicesImpl implements RegistrationServices {

    /**
     * <p>
     * Represents the default namespace used by the default constructor to access configuration info
     * in the construction.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.registration.service.impl.RegistrationServicesImpl";

    /**
     * <p>
     * Represents name of property key for namespace of
     * <code>ConfigManagerSpecificationFactory</code>.
     * </p>
     */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents name of property key for key of <code>RegistrationValidator</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String VALIDATOR_KEY = "validatorKey";

    /**
     * <p>
     * Represents name of property key for key of <code>UserRetrieval</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String USER_RETRIEVAL_KEY = "userRetrievalKey";

    /**
     * <p>
     * Represents name of property key for key of <code>ResourceManager</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String RESOURCE_MANAGER_KEY = "resourceManagerKey";

    /**
     * <p>
     * Represents name of property key for key of <code>ProjectServices</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String PROJECT_SERVICES_KEY = "projectServicesKey";

    /**
     * <p>
     * Represents name of property key for key of <code>PhaseManager</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String PHASE_MANAGER_KEY = "phaseManagerKey";

    /**
     * <p>
     * Represents name of property key for key of <code>BanManager</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String BAN_MANAGER_KEY = "banManagerKey";

    /**
     * <p>
     * Represents name of property key for key of <code>TeamManager</code> in
     * <code>ObjectFactory</code> configuration.
     */
    private static final String TEAM_MANAGER_KEY = "teamManagerKey";

    /**
     * <p>
     * Represents name of property key for key of <code>TeamServices</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String TEAM_SERVICES_KEY = "teamServicesKey";

    /**
     * <p>
     * Represents name of property key for key of <code>ContactMemberService</code> in
     * <code>ObjectFactory</code> configuration.
     * </p>
     */
    private static final String CONTACT_MEMBER_SERVICE_KEY = "contactMemberServiceKey";

    /**
     * <p>
     * Represents name of property key for logger name.
     * </p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents name of property key for ID of registration phase.
     * </p>
     */
    private static final String REGISTRATION_PHASE_ID = "registrationPhaseId";

    /**
     * <p>
     * Represents name of property key for available role ids.
     * </p>
     */
    private static final String AVAILABLE_ROLE_IDS = "availableRoleIds";

    /**
     * <p>
     * Represents name of property key for external role ids.
     * </p>
     */
    private static final String EXTERNAL_ROLE_IDS = "externalRoleIds";

    /**
     * <p>
     * Represents name of property key for ID of team captain role.
     * </p>
     */
    private static final String TEAM_CAPTAIN_ROLE_ID = "teamCaptainRoleId";

    /**
     * <p>
     * Represents name of property key for operator.
     * </p>
     */
    private static final String OPERATOR = "operator";

    /**
     * <p>
     * Represents name of property key for id of active project status.
     * </p>
     */
    private static final String ACTIVE_PROJECT_STATUS_ID = "activeProjectStatusId";

    /**
     * <p>
     * Represents name of property key for name of removal message template.
     * </p>
     */
    private static final String REMOVAL_MESSAGE_TEMPLATE_NAME = "removalMessageTemplateName";

    /**
     * <p>
     * Represents custom project name key.
     * </p>
     */
    private static final String PROJECT_NAME = "Project Name";

    /**
     * <p>
     * Represents the <code>RegistrationValidation</code> that will be used to validate a
     * registration. It is used in the <code>validateRegistration</code>. It is set in the
     * constructor to a non-null value, and will never change.
     * </p>
     */
    private final RegistrationValidator validator;

    /**
     * <p>
     * Represents the <code>UserRetrieval</code> instance that is used to retrieve the detailed
     * user information. It is used in the <code>registerForProject</code>,
     * <code>validateRegistration</code>, <code>getRegistration</code>,
     * <code>removeRegistration</code>, and <code>getRegisteredProjects</code> methods It is
     * set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final UserRetrieval userRetrieval;

    /**
     * <p>
     * Represents the <code>ResourceManager</code> instance that is used to manage a resource, and
     * obtain information about roles. It is used in <code>registerForProject</code> to create the
     * resource, in <code>removeRegistration</code> to remove the resource, and in the constructor
     * to verify that the configured available role IDs correspond to valid roles. It is set in the
     * constructor to a non-null value, and will never change.
     * </p>
     */
    private final ResourceManager resourceManager;

    /**
     * <p>
     * Represents the <code>ProjectServices</code> instance that is used to obtain information
     * about current projects and their resources. Often, the <code>ProjectServices</code> and
     * <code>ResourceManager</code> are interchangeable, but often <code>ProjectServices</code>
     * provides more convenient API for accessing resources. It is used in
     * <code>validateRegistration</code>, <code>getRegistration</code>,
     * <code>findAvailableRegistrationPositions</code>, <code>removeRegistration</code>,
     * <code>getRegisteredResources</code>, and <code>getRegisteredProjects</code>. It is set
     * in the constructor to a non-null value, and will never change.
     */
    private final ProjectServices projectServices;

    /**
     * <p>
     * Represents the <code>PhaseManager</code> instance that is used to obtain phase information
     * about a project. It is used in <code>findAvailableRegistrationPositions</code> method. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final PhaseManager phaseManager;

    /**
     * <p>
     * Represents the <code>TeamManager</code> instance that is used to obtain the team
     * information from a member resource. It is used in <code>removeRegistration</code> method.
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final TeamManager teamManager;

    /**
     * <p>
     * Represents the <code>TeamServices</code> instance that is used to remove a member from a
     * team while removing a registration. It is used in <code>removeRegistration</code> method.
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final TeamServices teamServices;

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
     * Represents the id of the phase that corresponds to the registration phase. It is used to
     * filter projects in the <code>findAvailableRegistrationPositions</code> to these that are in
     * the registration phase. It is set in the constructor to a non-negative value, and will never
     * change.
     * </p>
     */
    private final long registrationPhaseId;

    /**
     * <p>
     * Represents the roles that are available to all projects. It is set in the constructor. It is
     * used in the <code>findAvailableRegistrationPositions</code> method. It is set in the
     * constructor to a non-null array. Although there is no required restriction that it not be
     * empty, this is not expected. The array will not contain any null elements.
     * </p>
     */
    private final ResourceRole[] availableRoles;

    /**
     * <p>
     * Represents the <code>BanManager</code> instance that is used to manage bans on resources.
     * It is used in <code>removeRegistration</code> to add a ban on the resource being removed.
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final BanManager banManager;

    /**
     * <p>
     * Represents the <code>ContactMemberServices</code> instance that is used to send messages to
     * members. It is used in <code>removeRegistration</code> to notify the removed user of the
     * removal. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ContactMemberService contactMemberServices;

    /**
     * <p>
     * Represents the id of the project status that corresponds to the an active status of the
     * project. It is set in the constructor to a non-negative value, and will never change.
     * </p>
     */
    private final long activeProjectStatusId;

    /**
     * <p>
     * Represents the role IDs that are other than roles that refer to registered competitors to all
     * projects. It is set in the constructor to a non-null array. Although there is no required
     * restriction that it is not be empty, this is not expected. The array will not contain any
     * negative IDs.
     * </p>
     */
    private final long[] externalRoles;

    /**
     * <p>
     * Represents the id of a team captain resource role. It is set in the constructor to a
     * non-negative value, and will never change.
     * </p>
     */
    private final long teamCaptainRoleId;

    /**
     * <p>
     * The operator that will be used for logging and auditing. In <code>removeRegistration</code>,
     * it will also be used as the sender of the message to the removed resource.
     * </p>
     * <p>
     * It is set in the constructor to a non-negative value, and will never change.
     * </p>
     */
    private final long operator;

    /**
     * <p>
     * Represents the name of the operator.
     * </p>
     */
    private final String operatorName;

    /**
     * <p>
     * Represents the name of the template that will be used to generate a massage to send to a
     * member that this member has been removed. It is set in the constructor to a non-null/empty
     * value, and will never change.
     * </p>
     */
    private final String removalMessageTemplateName;

    /**
     * <p>
     * Default constructor.
     * </p>
     * @throws RegistrationServiceConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services
     */
    public RegistrationServicesImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Initializes this instance from configuration information from the <b>ConfigManager</b>. It
     * will use the <b>ObjectFactory</b> to create instances of required objects.
     * </p>
     * @param namespace
     *            the configuration namespace
     * @throws IllegalArgumentException
     *             if namespace is null or empty
     * @throws RegistrationServiceConfigurationException
     *             if any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services.
     */
    public RegistrationServicesImpl(String namespace) {
        Util.checkStrNotNullOrEmpty(namespace, "namespace");

        // gets the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();
        try {
            // gets namespace for ConfigManagerSpecificationFactory
            String specNamespace = cm.getString(namespace, SPEC_NAMESPACE);
            // creates an instance of ObjectFactory
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                specNamespace));

            // gets key for RegistrationValidator and creates an instance by ObjectFactory
            this.validator = (RegistrationValidator) createObject(cm, objectFactory, namespace,
                VALIDATOR_KEY);
            // gets key for UserRetrieval and creates an instance by ObjectFactory
            this.userRetrieval = (UserRetrieval) createObject(cm, objectFactory, namespace,
                USER_RETRIEVAL_KEY);
            // gets key for the ResourceManager and creates an instance by ObjectFactory
            this.resourceManager = (ResourceManager) createObject(cm, objectFactory, namespace,
                RESOURCE_MANAGER_KEY);
            // gets key for the ProjectServices and creates an instance by ObjectFactory
            this.projectServices = (ProjectServices) createObject(cm, objectFactory, namespace,
                PROJECT_SERVICES_KEY);
            // gets key for the PhaseManager and creates an instance by ObjectFactory
            this.phaseManager = (PhaseManager) createObject(cm, objectFactory, namespace,
                PHASE_MANAGER_KEY);
            // gets key for BanManager and creates an instance by ObjectFactory
            this.banManager = (BanManager) createObject(cm, objectFactory, namespace,
                BAN_MANAGER_KEY);
            // gets key for TeamManager and creates an instance by ObjectFactory
            this.teamManager = (TeamManager) createObject(cm, objectFactory, namespace,
                TEAM_MANAGER_KEY);
            // gets key for TeamServices and creates an instance by ObjectFactory
            this.teamServices = (TeamServices) createObject(cm, objectFactory, namespace,
                TEAM_SERVICES_KEY);
            // gets key for ContactMemberService and creates an instance by ObjectFactory
            this.contactMemberServices = (ContactMemberService) createObject(cm, objectFactory,
                namespace, CONTACT_MEMBER_SERVICE_KEY);

            // gets name of the log and gets the logger instance from LogManager if necessary
            String logName = cm.getString(namespace, LOGGER_NAME);
            this.logger = logName == null ? null : LogManager.getLog(logName);

            // gets ID of the registration phase and converts it to 'long' type
            this.registrationPhaseId = getLongValue(cm, namespace, REGISTRATION_PHASE_ID);

            // gets the IDs of the available roles and converts them to 'long' type and gets the
            // ResourceRole instances
            String[] availableRoleIds = cm.getStringArray(namespace, AVAILABLE_ROLE_IDS);
            long[] roleIds = Util.strArrayToLongArray(availableRoleIds);
            this.availableRoles = Util.getResourceRoles(this.resourceManager, roleIds);

            // gets the IDs of the roles external to registration and converts them to 'long' type
            String[] externalRoleIds = cm.getStringArray(namespace, EXTERNAL_ROLE_IDS);
            this.externalRoles = Util.strArrayToLongArray(externalRoleIds);

            // gets the ID of a team captain resource role and converts it to 'long' type
            this.teamCaptainRoleId = getLongValue(cm, namespace, TEAM_CAPTAIN_ROLE_ID);
            // gets the ID of a operator of this component
            this.operator = getLongValue(cm, namespace, OPERATOR);
            ExternalUser theOperator = userRetrieval.retrieveUser(operator);
            if (theOperator == null) {
                throw new RegistrationServiceConfigurationException(
                    "The operator ID in configuration has no corresponding user in the system.");
            }
            this.operatorName = theOperator.getHandle();

            // gets the ID of the active project status type
            this.activeProjectStatusId = getLongValue(cm, namespace, ACTIVE_PROJECT_STATUS_ID);

            // gets the name of the template to use for generating message for member removal
            this.removalMessageTemplateName = cm
                .getString(namespace, REMOVAL_MESSAGE_TEMPLATE_NAME);
            if (removalMessageTemplateName == null) {
                throw new RegistrationServiceConfigurationException(
                    "Configuration for RemovalMessageTemplateName is missing.");
            }
        } catch (UnknownNamespaceException ex) {
            throw new RegistrationServiceConfigurationException(
                "Given namespace can't be recognized by ConfigManager.", ex);
        } catch (SpecificationConfigurationException ex) {
            throw new RegistrationServiceConfigurationException(
                "SpecificationConfigurationException occurred when initializing ObjectFactory.", ex);
        } catch (IllegalReferenceException ex) {
            throw new RegistrationServiceConfigurationException(
                "IllegalReferenceException occurred when initializing ObjectFactory.", ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new RegistrationServiceConfigurationException(
                "The configuration for ObjectFactory is invalid.", ex);
        } catch (NumberFormatException ex) {
            throw new RegistrationServiceConfigurationException(
                "Long value in configuration can not be converted to 'long' type.", ex);
        } catch (IllegalArgumentException ex) {
            throw new RegistrationServiceConfigurationException(
                "Some configuration value for ObjectFactory is null or empty.", ex);
        } catch (ClassCastException ex) {
            throw new RegistrationServiceConfigurationException(
                "Object created by ObjectFactory can not be casted to specific type.", ex);
        } catch (RetrievalException ex) {
            throw new RegistrationServiceConfigurationException(
                "Error occurred when retrieving external user by its id.", ex);
        } catch (Exception ex) {
            throw new RegistrationServiceConfigurationException(
                "Some unexpected exceptions occurred.", ex);
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
     * @throws NumberFormatException
     *             if error occurred when converting string to 'long' value
     */
    private long getLongValue(ConfigManager cm, String namespace, String propertyName)
        throws UnknownNamespaceException {
        String val = cm.getString(namespace, propertyName);
        return Long.parseLong(val);
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
     * Logs before calling external Topcoder classes.
     * </p>
     * @param msg
     *            the logging message
     */
    private void logBefore(String msg) {
        log(Level.DEBUG, msg);
    }

    /**
     * <p>
     * Logs after calling external Topcoder classes.
     * </p>
     * @param msg
     *            the logging message
     */
    private void logAfter(String msg) {
        log(Level.DEBUG, msg);
    }

    /**
     * <p>
     * Registers the user as a new resource for the given project with the given role. Returns the
     * result of this process. If successful, returns the previous registration resource if one is
     * available. Otherwise, returns any available error messages. Note that validation is performed
     * on the registration information.
     * </p>
     * @return The result of the registration attempt, will never be null
     * @param registrationInfo
     *            RegistrationInfo containing the user to register in the given project under the
     *            given role
     * @throws IllegalArgumentException
     *             If registrationInfo is null, or contains negative IDs
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     * @throws RegistrationValidationException
     *             If any unexpected validation error occurs
     */
    public RegistrationResult registerForProject(RegistrationInfo registrationInfo) {
        log(Level.INFO, "Enters RegistrationServicesImpl#registerForProject method.");

        RegistrationResult result = null;
        try {
            // validates the registrationInfo
            result = validateRegistration(registrationInfo);
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in RegistrationServicesImpl#registerForProject.");
            throw ex;
        } catch (RegistrationValidationException ex) {
            log(Level.ERROR,
                "RegistrationValidationException occurred in RegistrationServicesImpl#registerForProject.");
            throw ex;
        } catch (RegistrationServiceException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServiceImpl#registerForProject.");
            throw ex;
        }

        // if validation successful, then do registering
        if (result.isSuccessful()) {
            try {
                // removes previous registration resource if necessary
                if (result.getPreviousRegistration() != null) {
                    logBefore("Starts calling ResourceManager#removeResource in RegistrationServicesImpl#"
                        + "registerForProject.");
                    resourceManager.removeResource(result.getPreviousRegistration(), operatorName);
                    logAfter("Finished calling ResourceManager#removeResource in RegistrationServicesImpl#"
                        + "registerForProject.");
                }
                // creates an instance of ResourceRole
                ResourceRole role = new ResourceRole(registrationInfo.getRoleId());
                // creates an instance of Resource
                Resource resource = new Resource();
                // sets the ResourceRole of this Resource instance
                resource.setResourceRole(role);
                // sets the project id of this Resource instance
                resource.setProject(new Long(registrationInfo.getProjectId()));

                // gets the external user corresponding to specific user id
                logBefore("Starts calling UserRetrieval#retrieveUser in RegistrationServicesImpl#registerForProject.");
                ExternalUser user = userRetrieval.retrieveUser(registrationInfo.getUserId());
                logAfter("Finished calling UserRetrieval#retrieveUser in RegistrationServicesImpl#registerForProject.");

                // populates custom properties of Resource
                // gets the user ID and sets it to Resource
                resource.setProperty(CustomResourceProperties.EXTERNAL_REFERENCE_ID, new Long(user
                    .getId()));

                // gets the handle and sets it to Resource
                resource.setProperty(CustomResourceProperties.HANDLE, user.getHandle());

                // gets the email address and sets it to Resource
                resource.setProperty(CustomResourceProperties.EMAIL, user.getEmail());

                // gets the email addressses the registration data
                resource.setProperty(CustomResourceProperties.REGISTRATION_DATE, new Date());

                // add this Resource instance into ResourceManager
                logBefore("Starts calling ResourceManager#updateResource in RegistrationServicesImpl#"
                    + "registerForProject.");
                resourceManager.updateResource(resource, operatorName);
                logAfter("Finished calling ResourceManager#updateResource in RegistrationServicesImpl#"
                    + "registerForProject.");
            } catch (ResourcePersistenceException ex) {
                log(Level.ERROR,
                    "RegistrationServiceException occurred in RegistrationServicesImpl#registerForProject.");
                throw new RegistrationServiceException(
                    "Error occurred when using ResourceManager to remove/update resources.", ex);
            } catch (RetrievalException ex) {
                log(Level.ERROR,
                    "RegistrationServiceException occurred in RegistrationServicesImpl#registerForProject.");
                throw new RegistrationServiceException(
                    "Error occurred when retrieving external user.", ex);
            }
        }

        log(Level.INFO, "Exits RegistrationServicesImpl#registerForProject method.");
        return result;
    }

    /**
     * <p>
     * Validates the registration info and, if successful, returns the previous registration
     * resource if one is available. Otherwise, returns any available error messages.
     * </p>
     * @return The result of the registration validation attempt. Will never be null.
     * @param registrationInfo
     *            RegistrationInfo containing the project, role, and user to validate
     * @throws IllegalArgumentException
     *             If registrationInfo is null or contains negative IDs
     * @throws RegistrationServiceException
     *             If any unexpected error occurs
     * @throws RegistrationValidationException
     *             If any unexpected validation error occurs
     * @throws ProjectServicesException
     *             if error occurred when operating ProjectServices instance
     */
    public RegistrationResult validateRegistration(RegistrationInfo registrationInfo) {
        log(Level.INFO, "Enters RegistrationServicesImpl#validateRegistration method.");

        try {
            Util.checkRegistrationInfo(registrationInfo, "registrationInfo");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in RegistrationServicesImpl#validateRegistration.");
            throw ex;
        }

        RegistrationResultImpl result = null;
        try {
            // gets the external user corresponding to specific user id
            logBefore("Starts calling UserRetrieval#retrieveUser in RegistrationServicesImpl#validateRegistration.");
            ExternalUser user = userRetrieval.retrieveUser(registrationInfo.getUserId());
            logAfter("Finished calling UserRetrieval#retrieveUser in RegistrationServicesImpl#validateRegistration.");

            // gets the FullProjectData
            logBefore("Starts calling ProjectServices#getFullProjectData method.");
            FullProjectData fullProjectData = projectServices.getFullProjectData(registrationInfo
                .getProjectId());
            logAfter("Finished calling ProjectServices#getFullProjectData method.");

            // gets the Resource instance corresponding to specific user
            Resource resource = getSpecificResource(user, fullProjectData);

            // validates the information
            logBefore("Starts calling RegistrationValidator#validate method.");
            OperationResult operationResult = validator.validate(registrationInfo, user,
                fullProjectData);
            logAfter("Finished calling RegistrationValidator#validate method.");

            // creates a new instance of RegistrationResult and sets it
            result = new RegistrationResultImpl();
            result.setSuccessful(operationResult.isSuccessful());
            result.setErrors(operationResult.getErrors());
            if (operationResult.isSuccessful()) {
                result.setPreviousRegistration(resource);
            }

        } catch (RetrievalException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#validateRegistration.");
            throw new RegistrationServiceException(
                "Error occurred when retrieving external user using UserRetrieval.", ex);
        } catch (ProjectServicesException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in RegistrationServicesImpl#validateRegistration.");
            throw ex;
        } catch (RegistrationValidationException ex) {
            log(Level.ERROR,
                "RegistrationValidationException occurred in RegistrationServicesImpl#validateRegistration.");
            throw ex;
        }

        log(Level.INFO, "Exits RegistrationServicesImpl#validateRegistration method.");
        return result;
    }

    /**
     * <p>
     * Gets the Resource instance that matches given external user.
     * </p>
     * @param user
     *            the external user
     * @param fullProjectData
     *            the FullProjectData instance
     * @return an instance of Resource or null
     * @throws RegistrationValidationException
     *             if more than one Resource instance matching given external user or some property
     *             values are invalid
     */
    private Resource getSpecificResource(ExternalUser user, FullProjectData fullProjectData) {
        List list = new ArrayList();

        // gets all resources in this project
        Resource[] resources = fullProjectData.getResources();
        try {
            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                if (!((new Long(user.getId())).equals(resource
                    .getProperty(CustomResourceProperties.EXTERNAL_REFERENCE_ID)))) {
                    continue;
                }
                if (!(user.getHandle()
                    .equals(resource.getProperty(CustomResourceProperties.HANDLE)))) {
                    continue;
                }
                if (!(user.getEmail().equals(resource.getProperty(CustomResourceProperties.EMAIL)))) {
                    continue;
                }
                if (!((Date) resource.getProperty(CustomResourceProperties.REGISTRATION_DATE))
                    .before(new Date())) {
                    continue;
                }
                // found it, adds it to the list
                list.add(resource);
            }
        } catch (ClassCastException ex) {
            throw new RegistrationValidationException(
                "Instance of Resource has invalid property value type.", ex);
        } catch (NullPointerException ex) {
            throw new RegistrationValidationException(
                "Instance of Resource missed some properties.", ex);
        }

        // there should be at most one result
        if (list.size() > 1) {
            throw new RegistrationValidationException(
                "There should be at most one Resource corresponding to given user.");
        }

        if (list.size() == 0) {
            return null;
        }
        return (Resource) list.get(0);
    }

    /**
     * <p>
     * Obtains the registration information for the user in the given project. In essence, it
     * retrieves the role the user has in the given project. It is possible that the user is not
     * registered in this project, upon which a null will be returned.
     * </p>
     * @return the registration information for the user in the given project, or null if no
     *         information is available for this user in this project
     * @param userId
     *            the ID of the user
     * @param projectId
     *            the ID of the project
     * @throws IllegalArgumentException
     *             if userId or projectId is negative
     * @throws RegistrationServiceException
     *             if any error occurs when retrieving external user or specific resource
     * @throws ProjectServicesException
     *             if error occurred when operating ProjectServices instance
     */
    public RegistrationInfo getRegistration(long userId, long projectId) {
        log(Level.INFO, "Enters RegistrationServicesImpl#getRegistration method.");

        try {
            Util.checkIDNotNegative(userId, "userId");
            Util.checkIDNotNegative(projectId, "projectId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in RegistrationServicesImpl#getRegistration.");
            throw ex;
        }

        RegistrationInfo info = null;
        try {
            // gets the external user corresponding to specific user id
            logBefore("Starts calling UserRetrieval#retrieveUser in RegistrationServicesImpl#getRegistration.");
            ExternalUser user = userRetrieval.retrieveUser(userId);
            logAfter("Finished calling UserRetrieval#retrieveUser in RegistrationServicesImpl#getRegistration.");

            // gets the FullProjectData
            logBefore("Starts calling ProjectServices#getFullProjectData method.");
            FullProjectData fullProjectData = projectServices.getFullProjectData(projectId);
            logAfter("Finished calling ProjectServices#getFullProjectData method.");

            // gets the Resource instance corresponding to specific user
            Resource resource = getSpecificResource(user, fullProjectData);
            // populates an new instance of RegistrationInfo
            if (resource != null) {
                info = new RegistrationInfoImpl(projectId, userId, resource.getResourceRole()
                    .getId());
            }
        } catch (RetrievalException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#getRegistration.");
            throw new RegistrationServiceException(
                "Error occurred when retrieving external user using UserRetrieval.", ex);
        } catch (ProjectServicesException ex) {
            log(Level.ERROR,
                "ProjectServiceException occurred in RegistrationServicesImpl#getRegistration.");
            throw ex;
        } catch (RegistrationValidationException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#getRegistration.");
            throw new RegistrationServiceException(
                "Error occurred when retrieving specific Resource.", ex);
        }

        log(Level.INFO, "Exists RegistrationServicesImpl#getRegistration method.");
        return info;
    }

    /**
     * <p>
     * Obtains all available positions in all active projects that fit the passed project category.
     * The position information will detail the roles one can participate in for a project, and the
     * phase details for each project. The project will be in registration phase.
     * </p>
     * @return Information about available positions in active projects that are part of the passed
     *         category
     * @param category
     *            the project category to determine which projects are checked
     * @throws IllegalArgumentException
     *             if category is null
     * @throws RegistrationServiceException
     *             if any unexpected error occurs
     * @throws ProjectServicesException
     *             if error occurred when operating ProjectServices instance
     */
    public RegistrationPosition[] findAvailableRegistrationPositions(ProjectCategory category) {
        log(Level.INFO,
            "Enters RegistrationServicesImpl#findAvailableRegistrationPositions method.");

        try {
            Util.checkObjNotNull(category, "category");
        } catch (IllegalArgumentException ex) {
            log(Level.INFO,
                "IllegalArgumentException occurred in RegistrationServicesImpl#findAvailableRegistrationPositions.");
            throw ex;
        }

        // builds the filter
        Filter filterForCategory = ProjectFilterUtility
            .buildCategoryIdEqualFilter(category.getId());
        Filter filterForStatus = ProjectFilterUtility
            .buildStatusIdEqualFilter(activeProjectStatusId);
        Filter filter = ProjectFilterUtility.buildAndFilter(filterForCategory, filterForStatus);

        // represents RegistrationPosition list
        List positions = new ArrayList();
        try {
            // finds full projects
            logBefore("Starts calling ProjectServices#findFullProjects method.");
            FullProjectData[] fullProjects = projectServices.findFullProjects(filter);
            logAfter("Finished calling ProjectServices#findFullProjects method.");

            // gets all projects' ids
            long[] ids = new long[fullProjects.length];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = fullProjects[i].getId();
            }

            // gets all phases
            logBefore("Starts calling PhaseManager#getPhases method.");
            com.topcoder.project.phases.Project[] phaseProjects = phaseManager.getPhases(ids);
            logAfter("Finished calling PhaseManager#getPhases method.");

            for (int i = 0; i < ids.length; i++) {
                Phase phase = getProjectRegistrationPhase(phaseProjects[i]);
                if (phase != null) {
                    positions.add(new RegistrationPositionImpl(fullProjects[i].getProjectHeader(),
                        phase, (ResourceRole[]) availableRoles.clone()));
                }
            }

        } catch (ProjectServicesException ex) {
            log(Level.ERROR,
                "ProjectServiceException occurred in RegistrationServicesImpl#findAvailableRegistrationPositions.");
            throw ex;
        } catch (PhaseManagementException ex) {
            log(Level.ERROR, "RegistrationServiceException occurred in RegistrationServicesImpl#"
                + "findAvailableRegistrationPositions.");
            throw new RegistrationServiceException(
                "Error occurred when retrieving phases by PhaseManager.", ex);
        }

        log(Level.INFO,
            "Exists RegistrationServicesImpl#findAvailableRegistrationPositions method.");
        return (RegistrationPosition[]) positions
            .toArray(new RegistrationPosition[positions.size()]);
    }

    /**
     * <p>
     * Gets given project's registration phase.
     * </p>
     * @param project
     *            the project
     * @return true is given project is in registration phase, false otherwise
     */
    private Phase getProjectRegistrationPhase(com.topcoder.project.phases.Project project) {
        if (project != null) {
            Phase[] phases = project.getAllPhases();
            for (int i = 0; i < phases.length; i++) {
                if (phases[i].getId() == registrationPhaseId) {
                    if (phases[i].getPhaseStatus().equals(PhaseStatus.OPEN)) {
                        return phases[i];
                    }
                    break;
                }
            }
        }
        return null;
    }

    /**
     * <p>
     * Removes the registration for the given user in the given project, and potentially bans the
     * resource for a period of days. The registration must match the given role. If the user does
     * not currently have a registration with this project, it will return an unsuccessful result
     * with a message stating that.
     * </p>
     * <p>
     * Template data for DocumentGenerator:
     *
     * <pre>
     *  &lt;DATA&gt;
     *      &lt;TEAM_NAME&gt;name&lt;/TEAM_NAME&gt;
     *      &lt;TEAM_DESCRIPTION&gt;description&lt;/TEAM_DESCRIPTION&gt;
     *      &lt;PROJECT_NAME&gt;project name&lt;/PROJECT_NAME&gt;
     *      &lt;HANDLE&gt;handle&lt;/HANDLE&gt;
     *      &lt;ROLE_NAME&gt;role name&lt;/ROLE_NAME&gt;
     *  &lt;/DATA&gt;
     * </pre>
     *
     * </p>
     * @param registrationInfo
     *            RegistrationInfo containing the user, project, and role to remove.
     * @param banDays
     *            The number of days the resource should be banned
     * @return The result of the removal attempt
     * @throws IllegalArgumentException
     *             if registrationInfo is null or contains negative IDs, or banDays is negative
     * @throws InvalidRoleException
     *             if the role in the passed RegistrationInfo does not match the role in the
     *             resource for this user in the project
     * @throws RegistrationServiceException
     *             if any unexpected error occurs
     * @throws UnknownEntityException
     *             if error occurred when operating TeamServices
     * @throws ProjectServicesException
     *             if error occurred when operating ProjectServices instance
     */
    public RemovalResult removeRegistration(RegistrationInfo registrationInfo, int banDays) {
        log(Level.INFO, "Enters RegistrationServicesImpl#removeRegistration method.");

        try {
            Util.checkRegistrationInfo(registrationInfo, "registrationInfo");
            if (banDays < 0) {
                throw new IllegalArgumentException(
                    "The parameter [banDays] should not be negative.");
            }
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in RegistrationServicesImpl#removeRegistration.");
            throw ex;
        }

        RemovalResultImpl result = null;
        try {
            // gets the external user corresponding to specific user id
            logBefore("Starts calling UserRetrieval#retrieveUser in RegistrationServicesImpl#removeRegistration.");
            ExternalUser user = userRetrieval.retrieveUser(registrationInfo.getUserId());
            logAfter("Finished calling UserRetrieval#retrieveUser in RegistrationServicesImpl#removeRegistration.");

            // gets the FullProjectData
            logBefore("Starts calling ProjectServices#getFullProjectData in RegistrationServicesImpl#"
                + "removeRegistration.");
            FullProjectData fullProjectData = projectServices.getFullProjectData(registrationInfo
                .getProjectId());
            logAfter("Finished calling ProjectServices#getFullProjectData in RegistrationServicesImpl#"
                + "removeRegistration.");

            // gets the Resource instance corresponding to specific user
            Resource resource = getSpecificResource(user, fullProjectData);

            OperationResult operationResult = null;
            if (resource != null) {
                if (resource.getResourceRole().getId() != registrationInfo.getRoleId()) {
                    log(Level.ERROR,
                        "InvalidRoleException occurred in RegistrationServicesImpl#removeRegistration.");
                    throw new InvalidRoleException("The role in the passed RegistrationInfo "
                        + "does not match the role in the resource for this user in the project.",
                        registrationInfo.getRoleId());
                }
                // represents the team name and team description
                String teamName = null;
                String teamDescription = null;

                if (resource.getResourceRole().getId() == teamCaptainRoleId) {
                    // gets the team of team header whose captainResourceId matches the
                    // resourceID
                    TeamHeader team = getTeam(fullProjectData, resource.getId());
                    // gets the team name and team description
                    teamName = team.getName();
                    teamDescription = team.getDescription();

                    // removes the team by TeamServices
                    logBefore("Starts calling TeamServices#removeTeam in RegistrationServicesImpl#removeRegistration.");
                    operationResult = teamServices.removeTeam(team.getTeamId(), operator);
                    logAfter("Finished calling TeamServices#removeTeam in RegistrationServicesImpl#"
                        + "removeRegistration.");
                } else {
                    // get the team header of which team this resource has registered
                    TeamHeader team = getTeamHeaderFromMemberResourceId(resource.getId(),
                        registrationInfo.getProjectId());
                    // gets the team name and team description
                    teamName = team.getName();
                    teamName = team.getDescription();

                    // removes the resource from persistence
                    logBefore("Starts calling ResourceManager#removeResource in RegistrationServicesImpl#"
                        + "removeRegistration.");
                    resourceManager.removeResource(resource, operatorName);
                    logAfter("Finished calling ResourceManager#removeResource in RegistrationServicesImpl#"
                        + "removeRegistration.");

                    // removes the resource from team
                    logBefore("Starts calling TeamServices#removeMember in RegistrationServicesImpl#"
                        + "removeRegistration.");
                    operationResult = teamServices.removeMember(resource.getId(), operator);
                    logAfter("Finished calling TeamServices#removeTeam in RegistrationServicesImpl#"
                        + "removeRegistration.");
                }

                // prepare contact message
                // gets instance of DocumentGenerator
                DocumentGenerator docGen = DocumentGenerator.getInstance();
                // gets the template
                Template template = docGen.getTemplate(removalMessageTemplateName);
                // builds the template data
                String templateData = buildTemplateData(teamName, teamDescription,
                    (String) fullProjectData.getProjectHeader().getProperty(PROJECT_NAME), user
                        .getHandle(), resource.getResourceRole().getName());
                // generates message text
                String msgText = docGen.applyTemplate(template, templateData);

                // populates a new Message instance
                Message msg = new Message();
                msg.setText(msgText);
                msg.setFromHandle(operatorName);
                msg.setToHandles(new String[] {user.getHandle()});
                msg.setProjectId(registrationInfo.getProjectId());
                msg.setProjectName((String) fullProjectData.getProjectHeader().getProperty(
                    PROJECT_NAME));
                msg.setTimeStamp(new Date());

                // sends message
                logBefore("Starts calling ContactMemberServices#sendMessage in RegistrationServicesImpl#"
                    + "removeRegistration.");
                contactMemberServices.sendMessage(msg);
                logAfter("Finished calling ContactMemberServices#sendMessage in RegistrationServicesImpl#"
                    + "removeRegistration.");
            }
            // bans the user for a few days
            logBefore("Starts calling BanManager#banUser in RegistrationServicesImpl#removeRegistration.");
            banManager.banUser(registrationInfo.getUserId(), banDays);
            logAfter("Finished calling BanManager#banUser in RegistrationServicesImpl#removeRegistration.");

            result = new RemovalResultImpl();
            if (operationResult != null) {
                result.setSuccessful(operationResult.isSuccessful());
                result.setErrors(operationResult.getErrors());
            }

        } catch (RetrievalException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException("Error occurred when retrieving external user.",
                ex);
        } catch (RegistrationValidationException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException(
                "Error occurred when retrieving the Resource for specific user.", ex);
        } catch (UnknownEntityException ex) {
            log(Level.ERROR,
                "UnknownEntityException occurred in RegistrationServicesImpl#removeRegistration.");
            throw ex;
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException("Error occurred removing Resource.", ex);
        } catch (ConfigManagerException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException(
                "Error occurred when retrieving instance of DocumentGenerator.", ex);
        } catch (InvalidConfigException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException(
                "Error occurred when retrieving instance of DocumentGenerator.", ex);
        } catch (TemplateFormatException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException("Error occurred when retrieving template.", ex);
        } catch (TemplateSourceException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException("Error occurred when retrieving template.", ex);
        } catch (TemplateDataFormatException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#removeRegistration.");
            throw new RegistrationServiceException("Error occurred when generating message text.",
                ex);
        } catch (ProjectServicesException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in RegistrationServicesImpl#removeRegistration.");
            throw ex;
        }

        log(Level.INFO, "Exits RegistrationServicesImpl#removeRegistration method.");
        return result;
    }

    /**
     * <p>
     * Gets the team header from member resource.
     * </p>
     * @param resourceId
     *            the memeber resource id
     * @param projectId
     *            the project id
     * @return the team header
     * @throws TeamPersistenceException
     *             if error occurred when retrieving team
     */
    private TeamHeader getTeamHeaderFromMemberResourceId(long resourceId, long projectId) {
        // builds a filter to search the position
        Filter filter = PositionFilterFactory.createResourceIdFilter(resourceId);
        // searches the team position
        logBefore("Starts calling TeamManager#findPositions method.");
        TeamPosition[] positions = teamManager.findPositions(filter);
        logAfter("Finished calling TeamManager#findPositions method.");

        // gets the teams by positions' ids
        for (int i = 0; i < positions.length; i++) {
            // get the team header from position id
            logBefore("Starts calling TeamManager#getTeamFromPosition method.");
            Team team = teamManager.getTeamFromPosition(positions[i].getPositionId());
            logAfter("Finished calling TeamManager#getTeamFromPosition method.");

            // checks if this team is in given project, if true, return the team header, otherwise
            // skip it
            if (team.getTeamHeader().getProjectId() == projectId) {
                return team.getTeamHeader();
            }
        }
        // never reach here
        return null;
    }

    /**
     * <p>
     * Builds template data for <code>DocumentGenerator</code>.
     * </p>
     * @param teamName
     *            the team name
     * @param teamDescription
     *            the team description
     * @param projectName
     *            the project name
     * @param handle
     *            the user handle
     * @param roleName
     *            name of the ResourceRole
     * @return the template data
     */
    private String buildTemplateData(String teamName, String teamDescription, String projectName,
        String handle, String roleName) {
        StringBuffer sb = new StringBuffer();
        sb.append("<DATA><TEAM_NAME>");
        sb.append(teamName);
        sb.append("</TEAM_NAME><TEAM_DESCRIPTION>");
        sb.append(teamDescription);
        sb.append("</TEAM_DESCRIPTION><PROJECT_NAME>");
        sb.append(projectName);
        sb.append("</PROJECT_NAME><HANDLE>");
        sb.append(handle);
        sb.append("</HANDLE><ROLE_NAME>");
        sb.append(roleName);
        sb.append("</ROLE_NAME></DATA>");

        return sb.toString();
    }

    /**
     * <p>
     * Gets the team header whose captainResourceId matching given resource id.
     * </p>
     * @param fullProjectData
     *            the FullProjectData instance
     * @param id
     *            the resource id
     * @return the team header whose captainResourceId matches given resource id
     */
    private TeamHeader getTeam(FullProjectData fullProjectData, long id) {
        TeamHeader[] teams = fullProjectData.getTeams();
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getCaptainResourceId() == id) {
                return teams[i];
            }
        }
        // never reach here
        return null;
    }

    /**
     * <p>
     * Gets all registered resources for the given project. It will return an array with zero to
     * many registrants, as <code>Resource</code> objects. The array will not contain null
     * elements.
     * </p>
     * @return all registered resources for the given project, or empty if none yet registered
     * @param projectId
     *            the ID of the project whose members we want to see
     * @throws IllegalArgumentException
     *             if projectId is negative
     * @throws RegistrationServiceException
     *             if any unexpected error occurs
     * @throws ProjectServicesException
     *             if error occurred when operating ProjectServices instance
     */
    public Resource[] getRegisteredResources(long projectId) {
        log(Level.INFO, "Enters RegistrationServicesImpl#getRegisteredResources.");

        try {
            Util.checkIDNotNegative(projectId, "projectId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in RegistrationServicesImpl#getRegisteredResources.");
            throw ex;
        }

        Resource[] resources = null;
        try {
            // gets the resources in given project
            logBefore("Starts calling ProjectServices#getFullProjectData method.");
            resources = projectServices.getFullProjectData(projectId).getResources();
            logAfter("Finished calling ProjectServices#getFullProjectData method.");
        } catch (ProjectServicesException ex) {
            log(Level.ERROR, "ProjectServicesException occurred when retrieving full project data.");
            throw ex;
        }
        // represents the result resource list
        List resultList = new ArrayList();
        for (int i = 0; i < resources.length; i++) {
            if (!isExternal(resources[i])) {
                resultList.add(resources[i]);
            }
        }

        log(Level.INFO, "Exits RegistrationServicesImpl#getRegisteredResources.");
        return (Resource[]) resultList.toArray(new Resource[resultList.size()]);
    }

    /**
     * <p>
     * Determines whether given resource is external.
     * </p>
     * @param resource
     *            the resource
     * @return true is given resource is external
     */
    private boolean isExternal(Resource resource) {
        for (int i = 0; i < externalRoles.length; i++) {
            if (resource.getResourceRole().getId() == externalRoles[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Gets all projects the user has registered for. It will return an array with zero to many
     * projects, as Project objects. The array will not contain null elements.
     * </p>
     * @return all projects the user has registered for, or empty if not registered in any
     * @param userId
     *            the ID of the user whose registered projects we want
     * @throws IllegalArgumentException
     *             if userId is negative
     * @throws RegistrationServiceException
     *             if any unexpected error occurs
     * @throws ProjectServicesException
     *             if error occurred when operating ProjectServices instance
     */
    public Project[] getRegisteredProjects(long userId) {
        log(Level.INFO, "Enters RegistrationServicesImpl#getRegisteredProjects method.");

        try {
            Util.checkIDNotNegative(userId, "userId");
        } catch (IllegalArgumentException ex) {
            log(Level.ERROR,
                "IllegalArgumentException occurred in RegistrationServicesImpl#getRegisteredProjects method.");
            throw ex;
        }

        List projectList = new ArrayList();
        try {
            // gets the external user corresponding to specific user id
            logBefore("Starts calling UserRetrieval#retrieveUser in RegistrationServicesImpl#getRegisteredProjects.");
            ExternalUser user = userRetrieval.retrieveUser(userId);
            logAfter("Finished calling UserRetrieval#retrieveUser in RegistrationServicesImpl#getRegisteredProjects.");

            // gets all active projects
            logBefore("Starts calling ProjectServices#findActiveProjects in RegistrationServicesImpl#"
                + "getRegisteredProjects.");
            FullProjectData[] projects = projectServices.findActiveProjects();
            logAfter("Finished calling ProjectServices#findActiveProjects in RegistrationServicesImpl#"
                + "getRegisteredProjects.");

            for (int i = 0; i < projects.length; i++) {
                try {
                    Resource resource = getSpecificResource(user, projects[i]);
                    if (resource != null) {
                        projectList.add(projects[i].getProjectHeader());
                    }
                } catch (RegistrationValidationException ex) {
                    // ignore it
                }
            }

        } catch (RetrievalException ex) {
            log(Level.ERROR,
                "RegistrationServiceException occurred in RegistrationServicesImpl#getRegisteredProjects.");
            throw new RegistrationServiceException("Error occurred when retrieving external user.",
                ex);
        } catch (ProjectServicesException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred RegistrationServicesImpl#getRegisteredProjects.");
            throw ex;
        }

        log(Level.INFO, "Exits RegistrationServicesImpl#getRegisteredProjects method.");
        return (Project[]) projectList.toArray(new Project[projectList.size()]);
    }
}
