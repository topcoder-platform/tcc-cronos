/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.Helper;
import com.topcoder.reliability.ProjectCategoryNotSupportedException;
import com.topcoder.reliability.ReliabilityCalculator;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This class is an implementation of ReliabilityCalculator that uses pluggable ReliabilityDataPersistence instance to
 * access user participation data in persistence and write calculated user reliability data to persistence, and
 * pluggable per project category ResolutionDateDetector and UserReliabilityCalculator instances to detect resolution
 * dates and calculate actual reliability ratings for each user respectively. In the context of this implementation
 * "resolution dates" are the moments when the information required to calculate the reliability comes in.
 * </p>
 *
 * <p>
 * <em>Usage:</em>
 * <pre>
 * // Create an instance of ReliabilityCalculatorImpl
 * ReliabilityCalculator reliabilityCalculator = new ReliabilityCalculatorImpl();
 *
 * // Configure reliability calculator
 * ConfigurationObject config = calculatorConfig;
 * reliabilityCalculator.configure(config);
 *
 * // Calculate reliability for &quot;Design&quot; project category (with ID=1) and
 * // update current reliability ratings for all users
 * reliabilityCalculator.calculate(1, true);
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe. It uses ReliabilityDataPersistence,
 * UserReliabilityCalculator and ResolutionDateDetector instances that are not required to be thread safe. It's
 * assumed that configure() method will be called just once right after instantiation, before calling any business
 * methods.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class ReliabilityCalculatorImpl implements ReliabilityCalculator {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ReliabilityCalculatorImpl.class.getName();

    /**
     * <p>
     * Represents the timestamp format 'yyyy-MM-dd HH:mm'.
     * </p>
     */
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * <p>
     * Represents the child key 'reliabilityDataPersistenceConfig'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_CONFIG = "reliabilityDataPersistenceConfig";

    /**
     * <p>
     * Represents the prefix of child key 'projectCategoryConfigXXX'.
     * </p>
     */
    private static final String KEY_PC_CONFIG_PREFIX = "projectCategoryConfig";

    /**
     * <p>
     * Represents the child key 'userReliabilityCalculatorConfig'.
     * </p>
     */
    private static final String KEY_CALCULATOR_CONFIG = "userReliabilityCalculatorConfig";

    /**
     * <p>
     * Represents the child key 'resolutionDateDetectorConfig'.
     * </p>
     */
    private static final String KEY_DETECTOR_CONFIG = "resolutionDateDetectorConfig";

    /**
     * <p>
     * Represents the property key 'reliabilityDataPersistenceKey'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_KEY = "reliabilityDataPersistenceKey";

    /**
     * <p>
     * Represents the property key 'participationDataComparatorKey'.
     * </p>
     */
    private static final String KEY_COMPARATOR_KEY = "participationDataComparatorKey";

    /**
     * <p>
     * Represents the property key 'projectCategoryIds'.
     * </p>
     */
    private static final String KEY_PC_IDS = "projectCategoryIds";

    /**
     * <p>
     * Represents the property key 'reliabilityStartDate'.
     * </p>
     */
    private static final String KEY_START_DATE = "reliabilityStartDate";

    /**
     * <p>
     * Represents the property key 'userReliabilityCalculatorKey'.
     * </p>
     */
    private static final String KEY_CALCULATOR_KEY = "userReliabilityCalculatorKey";

    /**
     * <p>
     * Represents the property key 'resolutionDateDetectorKey'.
     * </p>
     */
    private static final String KEY_DETECTOR_KEY = "resolutionDateDetectorKey";

    /**
     * <p>
     * The mapping from project category ID to the ProjectCategoryParams entity that holds reliability calculation
     * specific parameters for this project category.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null, cannot contain null/not
     * positive key or null value after initialization. Is used in calculate().
     * </p>
     */
    private Map<Long, ProjectCategoryParams> projectCategoryParamsById;

    /**
     * <p>
     * The reliability data persistence to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in calculate().
     * </p>
     */
    private ReliabilityDataPersistence reliabilityDataPersistence;

    /**
     * <p>
     * The participation data comparator to be used by this class for sorting UserProjectParticipationData instances.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in calculate().
     * </p>
     */
    private UserProjectParticipationDataComparator participationDataComparator;

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     *
     * <p>
     * Is initialized in the configure() method and never changed after that. If is null, logging is not performed. Is
     * used in calculate().
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Creates an instance of ReliabilityCalculatorImpl.
     * </p>
     */
    public ReliabilityCalculatorImpl() {
        // Empty
    }

    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     * @throws IllegalArgumentException
     *             if config is <code>null</code>.
     * @throws ReliabilityCalculatorConfigurationException
     *             if some error occurred when initializing an instance using the given configuration.
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");

        // Get logger
        log = Helper.getLog(config);

        try {

            // Create object factory
            ObjectFactory objectFactory = Helper.getObjectFactory(config);

            // Create reliability data persistence
            reliabilityDataPersistence = Helper.createObject(ReliabilityDataPersistence.class, objectFactory, config,
                KEY_PERSISTENCE_KEY, KEY_PERSISTENCE_CONFIG);
            // Create participation data comparator
            participationDataComparator = Helper.createObject(UserProjectParticipationDataComparator.class,
                objectFactory, config, KEY_COMPARATOR_KEY);

            projectCategoryParamsById = new HashMap<Long, ProjectCategoryParams>();
            DateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);

            for (String childName : config.getAllChildrenNames()) {
                if (childName.startsWith(KEY_PC_CONFIG_PREFIX)) {
                    // Get configuration for specific project categories
                    ConfigurationObject projectCategoryConfig = Helper.getChildConfig(config, childName);

                    // Get IDs of project categories
                    long[] projectCategoryIds = Helper.getPropertyPositiveValues(projectCategoryConfig, KEY_PC_IDS);

                    // Create project category parameters instance
                    ProjectCategoryParams projectCategoryParams = new ProjectCategoryParams();

                    // Get start date of reliability calculation for these project categories
                    String reliabilityStartDateStr =
                        Helper.getProperty(projectCategoryConfig, KEY_START_DATE, true);
                    try {
                        // Set reliability start date to the parameters instance
                        projectCategoryParams.setReliabilityStartDate(timestampFormat.parse(reliabilityStartDateStr));
                    } catch (ParseException e) {
                        throw new ReliabilityCalculatorConfigurationException("The value '" + reliabilityStartDateStr
                            + "' is not in format 'yyyy-MM-dd HH:mm'.", e);
                    }
                    // Set user reliability calculator to the parameters instance
                    projectCategoryParams.setUserReliabilityCalculator((UserReliabilityCalculator) Helper
                        .createObject(UserReliabilityCalculator.class, objectFactory, projectCategoryConfig,
                            KEY_CALCULATOR_KEY, KEY_CALCULATOR_CONFIG));
                    // Set resolution date detector to the parameters instance
                    projectCategoryParams.setResolutionDateDetector((ResolutionDateDetector) Helper.createObject(
                        ResolutionDateDetector.class, objectFactory, projectCategoryConfig, KEY_DETECTOR_KEY,
                        KEY_DETECTOR_CONFIG));

                    for (long projectCategoryId : projectCategoryIds) {
                        // Put project category ID and the corresponding parameters to the map:
                        projectCategoryParamsById.put(projectCategoryId, projectCategoryParams);
                    }
                }
            }

            if (projectCategoryParamsById.isEmpty()) {
                throw new ReliabilityCalculatorConfigurationException(
                    "There should be at least one project category.");
            }
        } catch (ConfigurationAccessException e) {
            throw new ReliabilityCalculatorConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * <p>
     * Calculates the reliability ratings for project category with the specified ID. Optionally updates the current
     * reliability ratings for all users.
     *</p>
     *
     * @param updateCurrentReliability
     *            <code>true</code> if current reliability must be updated for all users; <code>false</code>
     *            otherwise.
     * @param projectCategoryId
     *            the ID of the project category.
     *
     * @throws IllegalArgumentException
     *             if projectCategoryId &lt;= 0.
     * @throws IllegalStateException
     *             if this reliability calculator was not properly configured.
     * @throws ProjectCategoryNotSupportedException
     *             if project category with the given ID is not supported by this reliability calculator.
     * @throws ReliabilityDataPersistenceException
     *             if some error occurred when accessing the persistence.
     */
    public void calculate(long projectCategoryId, boolean updateCurrentReliability)
        throws ProjectCategoryNotSupportedException, ReliabilityDataPersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("calculate(long projectCategoryId, boolean updateCurrentReliability)");

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectCategoryId", "updateCurrentReliability"},
            new Object[] {projectCategoryId, updateCurrentReliability});

        try {
            Helper.checkPositive(projectCategoryId, "projectCategoryId");

            Helper.checkState(projectCategoryParamsById == null,
                "This reliability calculator was not properly configured.");

            // Delegate to the helper
            calculateHelper(signature, projectCategoryId, updateCurrentReliability);

            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (ProjectCategoryNotSupportedException e) {
            // Log exception
            throw Helper.logException(log, signature, e,
                "ProjectCategoryNotSupportedException is thrown when calculating the reliability ratings.");
        } catch (ReliabilityDataPersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e,
                "ReliabilityDataPersistenceException is thrown when calculating the reliability ratings.");
        }
    }

    /**
     * <p>
     * Calculates the reliability ratings for project category with the specified ID. Optionally updates the current
     * reliability ratings for all users.
     * </p>
     *
     * @param signature
     *            the method.
     * @param updateCurrentReliability
     *            <code>true</code> if current reliability must be updated for all users; <code>false</code>
     *            otherwise.
     * @param projectCategoryId
     *            the ID of the project category.
     *
     * @throws IllegalArgumentException
     *             if projectCategoryId &lt;= 0.
     * @throws IllegalStateException
     *             if this reliability calculator was not properly configured.
     * @throws ProjectCategoryNotSupportedException
     *             if project category with the given ID is not supported by this reliability calculator.
     * @throws ReliabilityDataPersistenceException
     *             if some error occurred when accessing the persistence.
     */
    private void calculateHelper(String signature, long projectCategoryId, boolean updateCurrentReliability)
        throws ProjectCategoryNotSupportedException, ReliabilityDataPersistenceException {
        // Get parameters for the specified project category
        ProjectCategoryParams projectCategoryParams = projectCategoryParamsById.get(projectCategoryId);
        if (projectCategoryParams == null) {
            throw new ProjectCategoryNotSupportedException("Project category with the ID '" + projectCategoryId
                + "' is not supported by this reliability calculator.", projectCategoryId);
        }

        // Open the connection to persistence
        reliabilityDataPersistence.open();

        // Get reliability calculation start date
        Date startDate = projectCategoryParams.getReliabilityStartDate();
        // Get user reliability calculator to be used
        UserReliabilityCalculator userReliabilityCalculator = projectCategoryParams.getUserReliabilityCalculator();
        // Get resolution date detector to be used
        ResolutionDateDetector resolutionDateDetector = projectCategoryParams.getResolutionDateDetector();

        try {
            // Get IDs of all users that have reliability rating for this project category
            List<Long> userIds = reliabilityDataPersistence.getIdsOfUsersWithReliability(projectCategoryId,
                startDate);

            for (long userId : userIds) {
                try {
                    // Get user participation data for this project category:
                    List<UserProjectParticipationData> userParticipationData = reliabilityDataPersistence
                        .getUserParticipationData(userId, projectCategoryId, startDate);
                    // Create a list for projects for which resolution date can be detected
                    List<UserProjectParticipationData> resolvedProjects =
                        new ArrayList<UserProjectParticipationData>();
                    for (UserProjectParticipationData projectData : userParticipationData) {
                        // Detect the resolution date
                        resolutionDateDetector.detect(projectData);
                        // Get the resolution date
                        Date resolutionDate = projectData.getResolutionDate();
                        if (resolutionDate != null) {
                            // Add the project data to the list
                            resolvedProjects.add(projectData);
                        }
                    }
                    // Sort the projects using the configured comparator
                    Collections.sort(resolvedProjects, participationDataComparator);
                    // Calculate the reliability ratings for all projects of this user
                    List<UserProjectReliabilityData> reliabilityDataList = userReliabilityCalculator
                        .calculate(resolvedProjects);
                    // Save user reliability data to persistence:
                    reliabilityDataPersistence.saveUserReliabilityData(reliabilityDataList);
                    if (updateCurrentReliability && (!reliabilityDataList.isEmpty())) {
                        // Get reliability data for the last resolved project:
                        UserProjectReliabilityData lastProjectReliabilityData = reliabilityDataList
                            .get(reliabilityDataList.size() - 1);
                        // Get reliability after the the last resolved project:
                        double reliability = lastProjectReliabilityData.getReliabilityAfterResolution();
                        // Update the current user reliability in persistence:
                        reliabilityDataPersistence.updateCurrentUserReliability(userId, projectCategoryId,
                            reliability);
                    }
                } catch (UserReliabilityCalculationException e) {
                    // Log exception
                    Helper.logException(log, signature, e,
                        "UserReliabilityCalculationException is thrown (will be ignored).");

                    // Ignore
                } catch (ResolutionDateDetectionException e) {
                    // Log exception
                    Helper.logException(log, signature, e,
                        "ResolutionDateDetectionException is thrown (will be ignored).");

                    // Ignore
                }
            }
        } finally {

            // Close the reliability data persistence
            reliabilityDataPersistence.close();
        }
    }

    /**
     * <p>
     * Gets the signature for given method for logging.
     * </p>
     *
     * @param method
     *            the method name.
     *
     * @return the signature for given method.
     */
    private static String getSignature(String method) {
        return CLASS_NAME + Helper.DOT + method;
    }

    /**
     * <p>
     * This is an inner class of ReliabilityCalculatorImpl that is a container for reliability calculation parameters
     * for specific project category. It is a simple JavaBean (POJO) that provides getters and setters for all private
     * attributes and performs no argument validation in the setters.
     *</p>
     *
     * <p>
     * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
     * </p>
     *
     * @author saarixx, sparemax
     * @version 1.0
     */
    private class ProjectCategoryParams {
        /**
         * <p>
         * The date when reliability for this project category started to be counted.
         * </p>
         *
         * <p>
         * Has getter and setter.
         * </p>
         */
        private Date reliabilityStartDate;

        /**
         * <p>
         * The user reliability calculator to be used for calculating reliability for this project category.
         * </p>
         *
         * <p>
         * Has getter and setter.
         * </p>
         */
        private UserReliabilityCalculator userReliabilityCalculator;

        /**
         * <p>
         * The resolution date detector for this project category.
         * </p>
         *
         * <p>
         * Has getter and setter.
         * </p>
         */
        private ResolutionDateDetector resolutionDateDetector;

        /**
         * <p>
         * Creates an instance of ProjectCategoryParams.
         * </p>
         */
        public ProjectCategoryParams() {
            // Empty
        }

        /**
         * <p>
         * Gets the date when reliability for this project category started to be counted.
         * </p>
         *
         * @return the date when reliability for this project category started to be counted.
         */
        public Date getReliabilityStartDate() {
            return reliabilityStartDate;
        }

        /**
         * <p>
         * Sets the date when reliability for this project category started to be counted.
         * </p>
         *
         * @param reliabilityStartDate
         *            the date when reliability for this project category started to be counted.
         */
        public void setReliabilityStartDate(Date reliabilityStartDate) {
            this.reliabilityStartDate = reliabilityStartDate;
        }

        /**
         * <p>
         * Gets the user reliability calculator to be used for calculating reliability for this project category.
         * </p>
         *
         * @return the user reliability calculator to be used for calculating reliability for this project category.
         */
        public UserReliabilityCalculator getUserReliabilityCalculator() {
            return userReliabilityCalculator;
        }

        /**
         * <p>
         * Sets the user reliability calculator to be used for calculating reliability for this project category.
         * </p>
         *
         * @param userReliabilityCalculator
         *            the user reliability calculator to be used for calculating reliability for this project
         *            category.
         */
        public void setUserReliabilityCalculator(UserReliabilityCalculator userReliabilityCalculator) {
            this.userReliabilityCalculator = userReliabilityCalculator;
        }

        /**
         * <p>
         * Gets the resolution date detector for this project category.
         * </p>
         *
         * @return the resolution date detector for this project category.
         */
        public ResolutionDateDetector getResolutionDateDetector() {
            return resolutionDateDetector;
        }

        /**
         * <p>
         * Sets the resolution date detector for this project category.
         * </p>
         *
         * @param resolutionDateDetector
         *            the resolution date detector for this project category.
         */
        public void setResolutionDateDetector(ResolutionDateDetector resolutionDateDetector) {
            this.resolutionDateDetector = resolutionDateDetector;
        }
    }
}

