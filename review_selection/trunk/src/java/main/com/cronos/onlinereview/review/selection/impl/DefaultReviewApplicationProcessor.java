/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import java.util.HashSet;
import java.util.Set;

import com.cronos.onlinereview.review.selection.Helper;
import com.cronos.onlinereview.review.selection.ReviewApplicationProcessor;
import com.cronos.onlinereview.review.selection.ReviewApplicationProcessorConfigurationException;
import com.cronos.onlinereview.review.selection.ReviewApplicationProcessorException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;
import com.topcoder.management.resource.ReviewerStatisticsManagerException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class implements the ReviewApplicationProcessor interface. It will restore eligibility points for unassigned
 * primary reviewer applications.
 * </p>
 *
 * <Strong>Sample usage:</Strong>
 * <pre>
 * // create a DefaultReviewApplicationProcessor
 * DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
 * // configure the instance
 * processor.configure(config);
 * // create a list of unassigned review applications
 * ReviewApplication[] applications = ...
 * // assume the application data is as the following table
 * // Note that this table does not show all the data related to the application, some data is omitted for simplicity
 *
 * Reviewer id | Eligibility points | Accept primary
 * 5   10  True
 * 6   8   True
 * 7   0   False
 * // process unassigned reviewers
 * processor.updateUnassignedReviewersStatistics(applications);
 * // assume the above data is used, and default values are used for processor,
 * // the reviewers whose id are 5 and 6 will have their eligibility points restored to 12 and 10 respectively.
 * // As to the reviewer whose id is 7, the eligibility points will remain 0 because
 * // he/she didn&apos;t apply for primary reviewer.
 *
 * Reviewer id | Eligibility points
 * 5   12
 * 6   10
 *
 * // create a list of assigned review applications
 * ReviewApplication[] applications = ...
 * // process unassigned reviewers
 * processor.updateReviewAssignmentStatistics(applications);
 * // nothing will happen since the current implementation of this method is blank
 * </pre>
 * <p>
 * Thread Safety: This class is immutable and thread-safe as long as the configure() method will be called just once
 * right after instantiation.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class DefaultReviewApplicationProcessor implements ReviewApplicationProcessor {
    /**
     * Represents the default primary eligibility points.
     */
    public static final double DEFAULT_PRIMARY_ELIGIBILITY_POINTS = 2.0;

    /**
     * Represents the name of this class used for logging.
     */
    private static final String CLASS_NAME = DefaultReviewApplicationProcessor.class.getName();

    /**
     * <p>
     * Represents the reviewer statistics manager used to retrieve and update reviewer statistics.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the updateUnassignedReviewersStatistics() method to retrieve and update reviewer statistics.
     * </p>
     */
    private ReviewerStatisticsManager reviewerStatisticsManager;

    /**
     * <p>
     * Represents the project manager used to retrieve project object.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the updateUnassignedReviewersStatistics() method to retrieve project object.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * Represents the log object used to write log.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to write log.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Represents the primary eligibility points used to restore the primary eligibility points in reviewer *
     * statistics.
     * </p>
     *
     * <p>
     * Equals to DEFAULT_PRIMARY_ELIGIBILITY_POINTS, also can be initialized in the configure() method. Should be
     * positive value after initialized. Can not be changed after initialized.
     * </p>
     *
     * <p>
     * Used by the updateUnassignedReviewersStatistics() method to restore the primary eligibility points in *
     * reviewer statistics.
     * </p>
     */
    private double primaryEligibilityPoints = DEFAULT_PRIMARY_ELIGIBILITY_POINTS;

    /**
     * Default no-argument constructor. Constructs a new DefaultReviewApplicationProcessor instance.
     */
    public DefaultReviewApplicationProcessor() {
    }

    /**
     * Process the assigned application to update reviewer statistics.
     *
     * @param applications an array of application to be processed.
     * @throws IllegalArgumentException if applications is null/empty, or applications contains null element, or if
     *             applications contains different projectId or if applications contains duplicate reviewerId.
     * @throws IllegalStateException if any property not configured properly.
     */
    public void updateReviewAssignmentStatistics(ReviewApplication[] applications) {
        long start = System.currentTimeMillis();
        final String signature = CLASS_NAME + ".updateReviewAssignmentStatistics(ReviewApplication[] applications)";

        // Create the log if the log is null, which means the configure method haven't been called yet.
        // Use this created log to log the entrance of this method and the invalid state exception.
        if (log == null) {
            log = LogFactory.getLog(this.getClass().getName());
        }

        Helper.logEntrance(log, signature, new String[] {"ReviewApplication"}, new Object[] {applications});
        checkReviewApplications(log, signature, applications);
        Helper.checkState(reviewerStatisticsManager, "reviewerStatisticsManager", log, signature);
        Helper.checkState(projectManager, "projectManager", log, signature);
        Helper.logExit(log, signature, null, start);
    }

    /**
     * Process the unassigned application to update reviewer statistics.
     *
     * @param applications an array of application to be processed.
     * @throws IllegalArgumentException if applications is null/empty, or applications contains null element, or if
     *             applications contains different projectId or if applications contains duplicate reviewerId.
     * @throws IllegalStateException if any property not configured properly.
     * @throws ReviewApplicationProcessorException if any error occurs.
     */
    public void updateUnassignedReviewersStatistics(ReviewApplication[] applications)
        throws ReviewApplicationProcessorException {
        long start = System.currentTimeMillis();
        final String signature = CLASS_NAME
            + ".updateUnassignedReviewersStatistics(ReviewApplication[] applications)";

        Helper.checkState(log, "log", log, signature);
        Helper.logEntrance(log, signature, new String[] {"ReviewApplication"}, new Object[] {applications});
        checkReviewApplications(log, signature, applications);
        Helper.checkState(reviewerStatisticsManager, "reviewerStatisticsManager", log, signature);
        Helper.checkState(projectManager, "projectManager", log, signature);

        // get projectTypeId for the project of these applications
        Project project;
        try {
            project = projectManager.getProject(applications[0].getProjectId());
        } catch (PersistenceException e) {
            throw Helper.logException(log, signature, new ReviewApplicationProcessorException(
                "Error occurred when getting project with id " + applications[0].getProjectId()
                    + " from projectManager", e));
        }
        if (project == null) {
            throw Helper.logException(log, signature, new ReviewApplicationProcessorException(
                "Unable to get project with id " + applications[0].getProjectId() + " from projectManager."));
        }
        long projectTypeId = project.getProjectCategory().getProjectType().getId();
        if (projectTypeId > Integer.MAX_VALUE) {
            throw Helper.logException(log, signature, new ReviewApplicationProcessorException("ProjectTypeId "
                + projectTypeId + " cannot be cast to int without changing its value."));
        }

        // check every review application
        for (ReviewApplication application : applications) {
            // issue back (restore) the primary eligibility review points to the primary applications that were not
            // selected
            if (application.isAcceptPrimary()) {
                // get reviewer statistics
                ReviewerStatistics statistics;
                try {
                    statistics = reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(application
                        .getReviewerId(), (int) projectTypeId);
                } catch (ReviewerStatisticsManagerException e) {
                    throw Helper.logException(log, signature, new ReviewApplicationProcessorException(
                        "Error occurred when getting statistics for project with projectTypeId [" + projectTypeId
                            + "] of reviewer [" + application.getReviewerId() + "]"));
                }
                if (statistics == null) {
                    throw Helper.logException(log, signature, new ReviewApplicationProcessorException(
                        "Unable to fetch statistics for project with projectTypeId [" + projectTypeId
                            + "] of reviewer [" + application.getReviewerId() + "]"));
                }

                // update eligibility points
                statistics.setEligibilityPoints(statistics.getEligibilityPoints() + primaryEligibilityPoints);
                try {
                    reviewerStatisticsManager.update(statistics);
                } catch (ReviewerStatisticsManagerException e) {
                    throw Helper.logException(log, signature, new ReviewApplicationProcessorException(
                        "Error occurred when updating statistics for project with projectTypeId [" + projectTypeId
                            + "] of reviewer [" + application.getReviewerId() + "]"));
                }
            }
        }
        Helper.logExit(log, signature, null, start);
    }

    /**
     * Configures this instance with use of the given configuration object, all the properties are created via
     * objectFactory.
     *
     * @param config the configuration object containing ConfigurationObjectSpecificationFactory config.
     * @throws IllegalArgumentException if config is null
     * @throws ReviewApplicationProcessorConfigurationException if some error occurred when initializing an instance
     *             using the given configuration
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");

        ObjectFactory objectFactory = createObjectFactory(config);
        try {
            reviewerStatisticsManager = (ReviewerStatisticsManager) createObject(objectFactory,
                "reviewerStatisticsManager");
        } catch (ClassCastException e) {
            throw new ReviewApplicationProcessorConfigurationException(
                "Failed to cast created object reviewerStatisticsManager to ReviewerStatisticsManager.", e);
        }
        try {
            projectManager = (ProjectManager) createObject(objectFactory, "projectManager");
        } catch (ClassCastException e) {
            throw new ReviewApplicationProcessorConfigurationException(
                "Failed to cast created object projectManager to ProjectManager.", e);
        }
        String loggerName = null;
        if (containsChild(config, "loggerName")) {
            try {
                loggerName = (String) createObject(objectFactory, "loggerName");
            } catch (ClassCastException e) {
                throw new ReviewApplicationProcessorConfigurationException(
                    "Failed to cast created object loggerName to String.", e);
            }
        }
        if ((loggerName != null) && (!loggerName.trim().equals(""))) {
            log = LogFactory.getLog(loggerName);
        } else {
            log = LogFactory.getLog(this.getClass().getName());
        }

        if (containsChild(config, "primaryEligibilityPoints")) {
            try {
                primaryEligibilityPoints = (Double) createObject(objectFactory, "primaryEligibilityPoints");
            } catch (ClassCastException e) {
                throw new ReviewApplicationProcessorConfigurationException(
                    "Failed to cast created object primaryEligibilityPoints to Double.", e);
            }
            if (primaryEligibilityPoints <= 0) {
                throw new ReviewApplicationProcessorConfigurationException(
                    "Property primaryEligibilityPoints is not positive.");
            }
        } else {
            primaryEligibilityPoints = DEFAULT_PRIMARY_ELIGIBILITY_POINTS;
        }
    }

    /**
     * Check the argument applications, throw and log IllegalArgumentException if applications is null/empty, or
     * applications contains null element, or if applications contains different projectId or if applications contains
     * duplicate reviewerId.
     *
     * @param log the log object
     * @param signature the signature of the invoking function
     * @param applications the applications to check
     * @throws IllegalArgumentException if applications is null/empty, or applications contains null element, or if
     *             applications contains different projectId or if applications contains duplicate reviewerId.
     */
    private static void checkReviewApplications(Log log, String signature, ReviewApplication[] applications) {
        Helper.checkNull(applications, "applications", log, signature);
        if (applications.length == 0) {
            throw Helper.logException(log, signature, new IllegalArgumentException("The applications is empty."));
        }

        Helper.checkNull(applications[0], "element of application", log, signature);
        long projectId = applications[0].getProjectId();

        Set<Long> reviewerIdSet = new HashSet<Long>();
        for (ReviewApplication application : applications) {
            Helper.checkNull(application, "element of application", log, signature);
            if (application.getProjectId() != projectId) {
                throw Helper.logException(log, signature, new IllegalArgumentException(
                    "The applications contains different projectId."));
            }

            if (reviewerIdSet.contains(application.getReviewerId())) {
                throw Helper.logException(log, signature, new IllegalArgumentException(
                    "The applications contains duplicate reviewerId " + application.getReviewerId() + "."));
            }
            reviewerIdSet.add(application.getReviewerId());
        }
    }

    /**
     * Create object factory from given config.
     *
     * @param config the configuration object
     * @return the object factory.
     * @throws ReviewApplicationProcessorConfigurationException if error occurred when creating the object factory.
     */
    private static ObjectFactory createObjectFactory(ConfigurationObject config) {
        ConfigurationObjectSpecificationFactory cosf;
        try {
            cosf = new ConfigurationObjectSpecificationFactory(config);
        } catch (IllegalReferenceException e) {
            throw new ReviewApplicationProcessorConfigurationException(
                "Error occurred when creating ConfigurationObjectSpecificationFactory from objectFactoryConfig.", e);
        } catch (SpecificationConfigurationException e) {
            throw new ReviewApplicationProcessorConfigurationException(
                "Error occurred when creating ConfigurationObjectSpecificationFactory from objectFactoryConfig.", e);
        }
        return new ObjectFactory(cosf);
    }

    /**
     * Create an object with the given key.
     *
     * @param objectFactory the object factory
     * @param key the key to create the object
     * @return The created object.
     * @throws ReviewApplicationProcessorConfigurationException if error occurred when getting the required child.
     */
    private static Object createObject(ObjectFactory objectFactory, String key) {
        // Create the object.
        Object object;
        try {
            object = objectFactory.createObject(key);
        } catch (InvalidClassSpecificationException e) {
            throw new ReviewApplicationProcessorConfigurationException(
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
     * @throws ReviewApplicationProcessorConfigurationException if error occurred when accessing the property.
     */
    private static boolean containsChild(ConfigurationObject config, String childName) {
        try {
            return config.containsChild(childName);
        } catch (ConfigurationAccessException e) {
            throw new ReviewApplicationProcessorConfigurationException("Error occurred when accessing child ["
                + childName + "].", e);
        }
    }
}
