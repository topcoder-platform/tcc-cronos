/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.registration.service.RegistrationValidator;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.cronos.onlinereview.external.ExternalUser;
import com.topcoder.management.ban.BanManager;
import com.topcoder.management.team.TeamManager;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class is a full implementation of the <code>RegistrationValidator</code>
 * interface. This implementation makes use of a large array of components to
 * accomplish its task of validating data. It relies on the
 * <code>TeamManager</code> component to get team information. It makes use of
 * the <code>ProjectServices</code> component to get project information. It
 * uses the <code>BanManager</code> component to get ban information.
 * </p>
 *
 * <p>
 * To provide a good view as the steps are progressing in each method, the
 * <b>Logging Wrapper</b> component is used in each method. To configure this
 * component, the <b>ConfigManager</b> and <b>ObjectFactory</b> components are
 * used.
 * </p>
 * <p>
 * Here is the sample configuration file for this class.
 *
 * <pre>
 *                &lt;Config name=&quot;TestConfig&quot;&gt;
 *                    &lt;Property name=&quot;specNamespace&quot;&gt;
 *                        &lt;Value&gt;com.topcoder.specify&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;validatorKey&quot;&gt;
 *                        &lt;Value&gt;myValidator&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *                        &lt;Value&gt;myTeamManager&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;projectServicesKey&quot;&gt;
 *                        &lt;Value&gt;myProjectServices&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;banManagerKey&quot;&gt;
 *                        &lt;Value&gt;myBanManager&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                    &lt;Property name=&quot;loggerName&quot;&gt;
 *                        &lt;Value&gt;myLog&lt;/Value&gt;
 *                    &lt;/Property&gt;
 *                &lt;/Config&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable but operates on non thread safe
 * objects, thus making it potentially non thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DataValidationRegistrationValidator implements
        RegistrationValidator {

    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private static final String CLASS_NAME = "DataValidationRegistrationValidator";

    /**
     * Represents the default namespace used by the default constructor to
     * access configuration info in the construction.
     *
     */
    private static final String DEFAULT_NAMESPACE =
        "com.topcoder.registration.validation.DataValidationRegistrationValidator";

    /**
     * <p>
     * Name of the property that contains namespace to load object factory
     * information from.
     * </p>
     * <p>
     * This property is required. The value of this property should be any valid
     * namespace for an Object Factory.
     * </p>
     */
    private static final String OBJECT_FACTORY_PROPERTYNAME = "specNamespace";

    /**
     * <p>
     * Name of the property that stores the configurable validator key to pass
     * to the object factory.
     * </p>
     * <p>
     * This property is required. The value of this property should be any valid
     * object factory key.
     * </p>
     *
     */
    private static final String VALIDATOR_KEY_PROPERTYNAME = "validatorKey";

    /**
     * <p>
     * Name of the property that stores the team manager key to pass to the
     * object factory.
     * </p>
     * <p>
     * This property is required. The value of this property should be any valid
     * object factory key.
     * </p>
     *
     */
    private static final String TEAM_MANAGER_KEY_PROPERTYNAME = "teamManagerKey";

    /**
     * <p>
     * Name of the property that stores the project services key to pass to the
     * object factory.
     * </p>
     * <p>
     * This property is required. The value of this property should be any valid
     * object factory key.
     * </p>
     *
     */
    private static final String PROJECT_SERVICES_KEY_PROPERTYNAME = "projectServicesKey";

    /**
     * <p>
     * Name of the property that stores the ban manager key to pass to the
     * object factory.
     * </p>
     * <p>
     * This property is required. The value of this property should be any valid
     * object factory key.
     * </p>
     *
     */
    private static final String BAN_MANAGER_KEY_PROPERTYNAME = "banManagerKey";

    /**
     * <p>
     * Name of the property that stores the name of the log to get from the
     * LogManager.
     * </p>
     * <p>
     * This property is optional. The value of this property should be any valid
     * object factory key.
     * </p>
     *
     */
    private static final String LOGGERNAME_PROPERTYNAME = "loggerName";

    /**
     * <p>
     * Represents the TeamManager instance that is used to retrieve teams.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private final TeamManager teamManager;

    /**
     * <p>
     * Represents the ProjectServices instance that is used to obtain
     * information about current projects.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private final ProjectServices projectServices;

    /**
     * <p>
     * Represents the BanManager instance that is used to check bans on
     * resources.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private final BanManager banManager;

    /**
     * <p>
     * Used extensively in this class to log information. This will include
     * logging method entry and exit, errors, debug information for calls to
     * other components, etc.
     * </p>
     * <p>
     * Note that logging is optional and can be null, in which case, no logging
     * will take place. It will be set in the constructor and will not change.
     * </p>
     *
     */
    private final Log logger;

    /**
     * <p>
     * Represents the actual validator that will perform the validation.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private final ConfigurableValidator validator;

    /**
     * Default constructor. Simply delegates to this(DEFAULT_NAMESPACE).
     *
     *
     * @throws RegistrationValidationConfigurationException
     *             If any configuration error occurs, such as unknown namespace,
     *             or missing required values, or errors while constructing the
     *             managers and services.
     */
    public DataValidationRegistrationValidator() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Initializes this instance from configuration information from the
     * <b>ConfigManager</b>. It will use the <b>ObjectFactory</b> to create
     * instances of required objects.
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws RegistrationValidationConfigurationException
     *             If any configuration error occurs, such as unknown namespace,
     *             or missing required values, or errors while constructing the
     *             managers and services.
     */
    public DataValidationRegistrationValidator(String namespace) {
        RegistrationValidationHelper.validateStringNotNullOrEmpty(namespace,
                "namespace");
        try {
            // loads namespace for object factory
            String objectFactoryNamespace = RegistrationValidationHelper
                    .getString(namespace, OBJECT_FACTORY_PROPERTYNAME, true);

            // creates object factory from the given namespace
            ObjectFactory factory = new ObjectFactory(
                    new ConfigManagerSpecificationFactory(
                            objectFactoryNamespace));

            // creates instance of TeamManager, ProjectServices, BanManager and
            // ConfigurableValidator using object factory
            this.banManager = (BanManager) RegistrationValidationHelper
                    .createObjectFromObjectFactory(namespace,
                            BAN_MANAGER_KEY_PROPERTYNAME, factory,
                            BanManager.class, true);

            this.projectServices = (ProjectServices) RegistrationValidationHelper
                    .createObjectFromObjectFactory(namespace,
                            PROJECT_SERVICES_KEY_PROPERTYNAME, factory,
                            ProjectServices.class, true);

            this.teamManager = (TeamManager) RegistrationValidationHelper
                    .createObjectFromObjectFactory(namespace,
                            TEAM_MANAGER_KEY_PROPERTYNAME, factory,
                            TeamManager.class, true);

            this.validator = (ConfigurableValidator) RegistrationValidationHelper
                    .createObjectFromObjectFactory(namespace,
                            VALIDATOR_KEY_PROPERTYNAME, factory,
                            ConfigurableValidator.class, true);

            // If loggerName property value is not null, sets the logger to be
            // LogManager.getLog(loggerName),
            // Else sets the logger to be null.
            String loggerName = RegistrationValidationHelper.getString(
                    namespace, LOGGERNAME_PROPERTYNAME, false);
            if (loggerName == null) {
                this.logger = null;
            } else {
                this.logger = LogManager.getLog(loggerName);
            }

            // Sets this RegistrationValidator instance to the validator so all
            // inner validators have access to this registrationValidator's
            // services.
            this.validator.setRegistrationValidator(this);

        } catch (SpecificationConfigurationException sce) {
            throw new RegistrationValidationConfigurationException(
                    "Some problem occurs while creating the ConfigManagerSpecificationFactory",
                    sce);
        } catch (IllegalReferenceException ire) {
            throw new RegistrationValidationConfigurationException(
                    "Some problem occurs while creating the ConfigManagerSpecificationFactory",
                    ire);
        }

    }

    /**
     * Sets all passed values to the corresponding fields. Log can be null. Sets
     * this RegistrationValidator instance to the validator to make sure all
     * validators have access to this registration validator manager.
     *
     * @param teamManager
     *            the TeamManager instance that is used to retrieve teams
     * @param projectServices
     *            the ProjectServices instance that is used to obtain
     *            information about current projects
     * @param banManager
     *            the BanManager instance that is used to check bans on
     *            resources
     * @param logger
     *            used to log information
     * @param validator
     *            the actual validator that will perform the validation
     * @throws IllegalArgumentException
     *             if teamManager or projectServices or banManager or validator
     *             is null
     */
    public DataValidationRegistrationValidator(TeamManager teamManager,
            ProjectServices projectServices, BanManager banManager, Log logger,
            ConfigurableValidator validator) {
        RegistrationValidationHelper
                .validateNotNull(teamManager, "teamManager");
        RegistrationValidationHelper.validateNotNull(projectServices,
                "projectServices");
        RegistrationValidationHelper.validateNotNull(banManager, "banManager");
        RegistrationValidationHelper.validateNotNull(validator, "validator");
        this.teamManager = teamManager;
        this.projectServices = projectServices;
        this.banManager = banManager;
        this.logger = logger;
        this.validator = validator;
        this.validator.setRegistrationValidator(this);
    }

    /**
     * Performs the validation on the passed data. No parameter can be null.
     *
     *
     * @return OperationResult the result of the registration validation.
     * @param registration
     *            The registration information
     * @param user
     *            The full information about the user associated with the
     *            registration
     * @param project
     *            The full project data related to the registration
     * @throws IllegalArgumentException
     *             if any parameter is null
     * @throws ValidationProcessingException
     *             If any unexpected error occurs
     */
    public OperationResult validate(RegistrationInfo registration,
            ExternalUser user, FullProjectData project) {
        // log entrance
        String methodName = CLASS_NAME
                + ".validate(RegistrationInfo registration, ExternalUser user, FullProjectData project)";
        RegistrationValidationHelper.log(logger, Level.INFO, "enter " + methodName);

        RegistrationValidationHelper.validateNotNull(registration,
                "registration");
        RegistrationValidationHelper.validateNotNull(user, "user");
        RegistrationValidationHelper.validateNotNull(project, "project");

        ValidationInfo validationInfo = new ValidationInfo();
        validationInfo.setUser(user);
        validationInfo.setRegistration(registration);
        validationInfo.setProject(project);

        String[] messages = validator.getAllMessages(validationInfo);

        if (messages != null) {
            for (int i = 0; i < messages.length; i++) {
                RegistrationValidationHelper.log(logger, Level.DEBUG,
                    "the value of messages[" + i + "]" + " is [" + messages + "]");
            }
        } else {
            RegistrationValidationHelper.log(logger, Level.DEBUG, "the messages is null");
        }

        OperationResult result = new OperationResultImpl((messages == null),
                messages);
        // Log exit
        RegistrationValidationHelper.log(logger, Level.INFO, "exit " + methodName);
        return result;
    }

    /**
     * Gets the TeamManager instance. Will not be null.
     *
     * @return the TeamManager instance
     */
    public TeamManager getTeamManager() {
        return teamManager;
    }

    /**
     * Gets the ProjectServices instance. Will not be null.
     *
     * @return the ProjectServices instance
     */
    public ProjectServices getProjectServices() {
        return projectServices;
    }

    /**
     * Gets the BanManager instance. Will not be null.
     *
     * @return the BanManager instance
     */
    public BanManager getBanManager() {
        return banManager;
    }

    /**
     * Gets the Log instance. Could be null.
     *
     * @return the Log instance
     */
    public Log getLog() {
        return logger;
    }

    /**
     * <p>
     * Gets the inner configurable object validator instance. Will not be null.
     * </p>
     *
     *
     * @return the inner configurable object validator instance.
     */
    public ConfigurableValidator getValidator() {
        return validator;
    }

}
