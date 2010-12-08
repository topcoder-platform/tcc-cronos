/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import com.cronos.onlinereview.review.selection.Helper;
import com.cronos.onlinereview.review.selection.ReviewBoardApplicationListener;
import com.cronos.onlinereview.review.selection.ReviewBoardApplicationListenerConfigurationException;
import com.cronos.onlinereview.review.selection.ReviewBoardApplicationListenerException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ApplicationsManager;
import com.topcoder.management.project.ApplicationsManagerException;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class implements the ReviewBoardApplicationListener interface. It will check to see whether the reviewer
 * applications are sufficient, if such case happens, it will use the PhaseManager to close the review registration
 * phase.
 * </p>
 *
 * <Strong>Sample usage:</Strong>
 * <pre>
 * // create a DefaultReviewBoardApplicationListener
 * DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener()
 * // configure the instance
 * listener.configure(config);
 * // assume review positions are not filled at the completion of the review registration phase
 * and the application data is as the following table
 * // Note that this table does not show all the data related to the application, some data is
 * omitted for simplicity
 * Reviewer id | Accept primary
 *      8           False
 *      9           False
 * // a new reviewer just register for this project
 * ReviewApplication application1 = new ReviewApplication();
 * ... // initialized the properties of application1
 * // assume the new review application has the following data:
 * Reviewer id | Accept primary
 *      10           False
 * // the listener will be invoked
 * listener.applicationReceived(application1);
 * // although there are 3 applications now, since none of these reviewer would like to be
 * primary reviewer, the registration phase should not be ended
 * // after that a new reviewer register for this project and he/she would accept the
 * responsibility of primary reviewer
 * ReviewApplication application2 = new ReviewApplication();
 * ... // initialized the properties of application2
 * // assume the new review application has the following data:
 * Reviewer id | Accept primary
 *      11           False
 * // the listener will be invoked again
 * listener.applicationReceived(application2);
 * // since we have 4 reviewers and there is one primary reviewer, the registration phase should
 * be ended by the listener
 * </pre>
 * <p>
 * Thread Safety: This class is immutable and thread-safe as long as the configure() method will be called just once
 * right after instantiation.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class DefaultReviewBoardApplicationListener implements ReviewBoardApplicationListener {
    /**
     * Represents the default reviewers number.
     */
    public static final int DEFAULT_REVIEWERS_NUMBER = 3;

    /**
     * Represents the key for the default registration phase name.
     */
    public static final String DEFAULT_REGISTRATION_PHASE_NAME = "review registration";

    /**
     * Represents the name of this class used for logging.
     */
    private static final String CLASS_NAME = DefaultReviewBoardApplicationListener.class.getName();

    /**
     * <p>
     * Represents the phase manager used to retrieve project phase.
     * </p>
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     * <p>
     * Used by the applicationReceived() method to retrieve project phase.
     * </p>
     */
    private PhaseManager phaseManager;

    /**
     * <p>
     * Represents the review applications manager used to retrieve review applications.
     * </p>
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     * <p>
     * Used by the applicationReceived() method to retrieve review applications.
     * </p>
     */
    private ApplicationsManager reviewApplicationsManager;

    /**
     * <p>
     * Represents the log object used to write log.
     * </p>
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     * <p>
     * Used by the applicationReceived() method to write log.
     * <p>
     */
    private Log log;

    /**
     * <p>
     * Represents the reviewers number used to determine if the registration phase can be ended.
     * </p>
     * <p>
     * Equals to DEFAULT_REVIEWERS_NUMBER, also can be initialized in the configure() method. Should be positive value
     * after initialized. Can not be changed after initialized.
     * </p>
     * <p>
     * Used by the applicationReceived() method to determine if the registration phase can be ended.
     * </p>
     */
    private int reviewersNumber = DEFAULT_REVIEWERS_NUMBER;

    /**
     * <p>
     * Represents the registration phase name used to retrieve registration phase.
     * </p>
     * <p>
     * Equals to DEFAULT_REGISTRATION_PHASE_NAME, also can be initialized in the configure() method. Can not be
     * null/empty after initialized. Can not be changed after initialized.
     * </p>
     * <p>
     * Used by the applicationReceived() method to retrieve registration phase.
     * </p>
     */
    private String registrationPhaseTypeName = DEFAULT_REGISTRATION_PHASE_NAME;

    /**
     * <p>
     * Represents the registration phase operator used as a parameter to end registration phase.
     * </p>
     * <p>
     * Should be initialized in the configure() method. Can not be null/empty after initialized. Can not be changed
     * after initialized.
     * </p>
     * <p>
     * Used by the applicationReceived() method to end registration phase.
     * </p>
     */
    private String registrationPhaseOperator;

    /**
     * Default no-argument constructor. Constructs a new DefaultReviewBoardApplicationListener instance.
     */
    public DefaultReviewBoardApplicationListener() {
    }

    /**
     * Handle the review registration event. Check that whether we have sufficient review applications, and there's at
     * least one primary review application, if that's the case, close the registration phase if it's still opening.
     *
     * @param application the review application for the review registration event.
     * @throws IllegalArgumentException if given application is null.
     * @throws IllegalStateException if any property not configured properly.
     * @throws ReviewBoardApplicationListenerException if any error occurs or to wrap underlying error.
     */
    public void applicationReceived(ReviewApplication application) throws ReviewBoardApplicationListenerException {
        long start = System.currentTimeMillis();
        final String signature = CLASS_NAME + ".applicationReceived(ReviewApplication application)";

        Helper.checkState(log, "log", log, signature);
        Helper.logEntrance(log, signature, new String[] {"ReviewApplication"}, new Object[] {application});
        Helper.checkNull(application, "application", log, signature);
        Helper.checkState(phaseManager, "phaseManager", log, signature);
        Helper.checkState(reviewApplicationsManager, "reviewApplicationsManager", log, signature);
        Helper.checkState(registrationPhaseOperator, "registrationPhaseOperator", log, signature);

        // find all the review applications for the same project
        ReviewApplication[] applications;
        try {
            applications = reviewApplicationsManager.getAllApplications(application.getProjectId());
        } catch (ApplicationsManagerException e) {
            throw Helper.logException(log, signature, new ReviewBoardApplicationListenerException(
                "Unable to get applications from reviewApplicationsManager.", e));
        }

        // check whether we have sufficient number of reviewers
        if (applications.length < reviewersNumber) {
            Helper.logExit(log, signature, null, start);
            return;
        }

        // check if at least one reviewer wants to be primary reviewer
        boolean acceptPrimary = false;
        for (ReviewApplication theApplication : applications) {
            acceptPrimary |= theApplication.isAcceptPrimary();
        }
        if (!acceptPrimary) {
            Helper.logExit(log, signature, null, start);
            return;
        }

        // find the registration phase
        Project project;
        try {
            project = phaseManager.getPhases(application.getProjectId());
        } catch (PhaseManagementException e) {
            throw Helper.logException(log, signature, new ReviewBoardApplicationListenerException(
                "Error occurred when getting the phaseProject from phaseManager.", e));
        }
        if (project == null) {
            throw Helper.logException(log, signature, new ReviewBoardApplicationListenerException(
                "Unable to get project from phaseManager with id [" + application.getProjectId() + "]."));
        }
        Phase registrationPhase = null;
        for (Phase phase : project.getAllPhases()) {
            if (registrationPhaseTypeName.equals(phase.getPhaseType().getName())) {
                registrationPhase = phase;
                break;
            }
        }
        if (registrationPhase == null) {
            throw Helper.logException(log, signature, new ReviewBoardApplicationListenerException(
                "Unable to find the registration phase in the project."));
        }

        // try to end the registration phase
        try {
            if (phaseManager.canEnd(registrationPhase)) {
                phaseManager.end(registrationPhase, registrationPhaseOperator);
            }
        } catch (PhaseManagementException e) {
            throw Helper.logException(log, signature, new ReviewBoardApplicationListenerException(
                "Error occurred when ending the registration phase.", e));
        }

        Helper.logExit(log, signature, null, start);
    }

    /**
     * Configures this instance with use of the given configuration object, all the properties are created via
     * objectFactory.
     *
     * @param config the configuration object containing ConfigurationObjectSpecificationFactory config.
     * @throws IllegalArgumentException if config is null
     * @throws ReviewBoardApplicationListenerConfigurationException if some error occurred when initializing an
     *             instance using the given configuration
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");

        ObjectFactory objectFactory = createObjectFactory(config);
        try {
            phaseManager = (PhaseManager) createObject(objectFactory, "phaseManager");
        } catch (ClassCastException e) {
            throw new ReviewBoardApplicationListenerConfigurationException(
                "Failed to cast created object phaseManager to PhaseManager.", e);
        }
        try {
            reviewApplicationsManager = (ApplicationsManager) createObject(objectFactory, "reviewApplicationsManager");
        } catch (ClassCastException e) {
            throw new ReviewBoardApplicationListenerConfigurationException(
                "Failed to cast created object reviewApplicationsManager to ApplicationsManager.", e);
        }
        String loggerName = null;
        if (containsChild(config, "loggerName")) {
            try {
                loggerName = (String) createObject(objectFactory, "loggerName");
            } catch (ClassCastException e) {
                throw new ReviewBoardApplicationListenerConfigurationException(
                    "Failed to cast created object loggerName to String.", e);
            }
        }
        if ((loggerName != null) && (!loggerName.trim().equals(""))) {
            log = LogFactory.getLog(loggerName);
        } else {
            log = LogFactory.getLog(this.getClass().getName());
        }

        if (containsChild(config, "reviewersNumber")) {
            try {
                reviewersNumber = (Integer) createObject(objectFactory, "reviewersNumber");
            } catch (ClassCastException e) {
                throw new ReviewBoardApplicationListenerConfigurationException(
                    "Failed to cast created object reviewersNumber to Integer.", e);
            }
            if (reviewersNumber <= 0) {
                throw new ReviewBoardApplicationListenerConfigurationException(
                    "Property reviewersNumber is not positive.");
            }
        } else {
            reviewersNumber = DEFAULT_REVIEWERS_NUMBER;
        }

        if (containsChild(config, "registrationPhaseTypeName")) {
            registrationPhaseTypeName = getStringProperty(objectFactory, "registrationPhaseTypeName");
        } else {
            registrationPhaseTypeName = DEFAULT_REGISTRATION_PHASE_NAME;
        }

        registrationPhaseOperator = getStringProperty(objectFactory, "registrationPhaseOperator");
    }

    /**
     * Create a string property via objectFactory.
     *
     * @param objectFactory the object factory.
     * @param propertyName the name of the property.
     * @return The created object.
     * @throws ReviewBoardApplicationListenerConfigurationException if error occurred when creating the property.
     */
    private String getStringProperty(ObjectFactory objectFactory, String propertyName) {
        String propertyValue;
        try {
            propertyValue = (String) createObject(objectFactory, propertyName);
        } catch (ClassCastException e) {
            throw new ReviewBoardApplicationListenerConfigurationException("Failed to cast created object "
                + propertyName + " to String.", e);
        }
        if (propertyValue.trim().equals("")) {
            throw new ReviewBoardApplicationListenerConfigurationException("Property " + propertyName
                + " can NOT be empty.");
        }
        return propertyValue;
    }

    /**
     * Create object factory from given config.
     *
     * @param config the configuration object
     * @return the object factory.
     * @throws ReviewBoardApplicationListenerConfigurationException if error occurred when creating the object
     *             factory.
     */
    private static ObjectFactory createObjectFactory(ConfigurationObject config) {
        ConfigurationObjectSpecificationFactory cosf;
        try {
            cosf = new ConfigurationObjectSpecificationFactory(config);
        } catch (IllegalReferenceException e) {
            throw new ReviewBoardApplicationListenerConfigurationException(
                "Error occurred when creating ConfigurationObjectSpecificationFactory config.", e);
        } catch (SpecificationConfigurationException e) {
            throw new ReviewBoardApplicationListenerConfigurationException(
                "Error occurred when creating ConfigurationObjectSpecificationFactory config.", e);
        }
        return new ObjectFactory(cosf);
    }

    /**
     * Create an object with the given key.
     *
     * @param objectFactory the object factory
     * @param key the key to create the object
     * @return the created object.
     * @throws ReviewBoardApplicationListenerConfigurationException if error occurred when creating the object.
     */
    private static Object createObject(ObjectFactory objectFactory, String key) {
        // Create the object.
        Object object;
        try {
            object = objectFactory.createObject(key);
        } catch (InvalidClassSpecificationException e) {
            throw new ReviewBoardApplicationListenerConfigurationException(
                "Error occurred when creating object with key " + key + ".", e);
        }
        return object;
    }

    /**
     * Check whether the config contains the child configuration.
     *
     * @param config the configuration object
     * @param childName the name of the property
     * @return whether the config contains the property.
     * @throws ReviewBoardApplicationListenerConfigurationException if error occurred when accessing the property.
     */
    private static boolean containsChild(ConfigurationObject config, String childName) {
        try {
            return config.containsChild(childName);
        } catch (ConfigurationAccessException e) {
            throw new ReviewBoardApplicationListenerConfigurationException("Error occurred when accessing child ["
                + childName + "].", e);
        }
    }
}
