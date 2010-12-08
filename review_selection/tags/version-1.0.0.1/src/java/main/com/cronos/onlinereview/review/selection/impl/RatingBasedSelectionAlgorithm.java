/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cronos.onlinereview.review.selection.Helper;
import com.cronos.onlinereview.review.selection.ReviewApplicationProcessor;
import com.cronos.onlinereview.review.selection.ReviewApplicationProcessorConfigurationException;
import com.cronos.onlinereview.review.selection.ReviewApplicationProcessorException;
import com.cronos.onlinereview.review.selection.ReviewSelection;
import com.cronos.onlinereview.review.selection.ReviewSelectionConfigurationException;
import com.cronos.onlinereview.review.selection.ReviewSelectionException;
import com.cronos.onlinereview.review.selection.ReviewSelectionResult;
import com.topcoder.apps.review.rboard.RBoardApplicationBean;
import com.topcoder.apps.review.rboard.RBoardRegistrationException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;
import com.topcoder.management.resource.ReviewerStatisticsManagerException;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class implements the ReviewSelection interface. It will select primary reviewer and secondary reviewers based
 * on the reviewer's past performance statistics and current workload. This class uses the ReviewApplicationProcessor
 * interface to update reviewer statistics and the WorkloadFactorCalculator interface to calculator workload factor.
 * </p>
 *
 * <Strong>Sample usage:</Strong>
 *
 * <pre>
 * // create a RatingBasedSelectionAlgorithm class and set the calculator and processor to it
 * RatingBasedSelectionAlgorithm reviewerSelection = new RatingBasedSelectionAlgorithm();
 * // configure the instance
 * reviewerSelection.configure(config);
 * .. // initialize other properties of reviewerSelection
 * // create a list of review applications
 * ReviewApplication[] applications = ...
 * // assume the application data is as the following table
 * // Note that this table does not show all the data related to the application, some data is omitted for simplicity
 *
 * Reviewer id | Eligibility points | Accept primary | Coefficient Value
 * 1   10  True    50
 * 2   10  True    40
 * 3   0   False   30
 * 4   0   False   20
 * // select reviewer based on their performance statistics and current workload
 * ReviewSelectionResult result = reviewerSelection.selectReviewers(applications);
 * // assume the above data is used, and default values are used for calculator, processor and reviewerSelection,
 * // based on the coefficient values, the id for primary reviewer should be 1, the id for secondary reviewers
 * // should be 2 and 3. The reviewer whose id is 4 should not be selected.
 *
 * Reviewer id | Result
 * 1   Primary Reviewer
 * 2   Secondary Reviewer
 * 3   Secondary Reviewer
 * 4   Not Selected
 *
 * // the selectReviewers() method will also restore eligibility points for unselected primary reviewer,
 * // so the reviewer whose id is 2 should have its eligibility points changed from 10 to 12
 *
 * Reviewer id | Eligibility points
 * 2   12
 * </pre>
 * <p>
 * Thread Safety: This class is immutable and thread-safe as long as the configure() method will be called just once
 * right after instantiation.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class RatingBasedSelectionAlgorithm implements ReviewSelection {
    /**
     * Represents the default timeline reliability subtractor.
     */
    public static final double DEFAULT_TIMELINE_RELIABILITY_SUBTRACTOR = 0.5;

    /**
     * Represents the default review phase name.
     */
    public static final String DEFAULT_REVIEW_PHASE_NAME = "review";

    /**
     * Represents the default secondary reviewers number.
     */
    public static final int DEFAULT_SECONDARY_REVIEWERS_NUMBER = 2;

    /**
     * Represents the default primary reviewer responsibility id.
     */
    public static final int DEFAULT_PRIMARY_REVIEWER_RESPONSIBILITY = 5;

    /**
     * Represents the default reviewer responsibility id.
     */
    public static final int DEFAULT_REVIEWER_RESPONSIBILITY = 4;

    /**
     * Represents the value indicate that the review system version for a project type is new, which means the
     * improved review process is applied.
     */
    public static final int REVIEW_SYSTEM_VERSION_NEW = 2;

    /**
     * Represents the name of this class used for logging.
     */
    private static final String CLASS_NAME = RatingBasedSelectionAlgorithm.class.getName();

    /**
     * <p>
     * Represents the reviewer statistics manager used to retrieve reviewer statistics.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to retrieve reviewer statistics.
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
     * Used by the selectReviewers() method to retrieve project object.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * Represents the phase manager used to retrieve project phase.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to retrieve project phase.
     * </p>
     */
    private PhaseManager phaseManager;

    /**
     * <p>
     * Represents the review application processor used to process reviewer selection result.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to process reviewer selection result.
     * </p>
     */
    private ReviewApplicationProcessor reviewApplicationProcessor;

    /**
     * <p>
     * Represents the review board application bean used to retrieve concurrent review number and assign reviewer.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to retrieve concurrent review number and assign reviewer.
     * </p>
     */
    private RBoardApplicationBean rboardApplicationBean;

    /**
     * <p>
     * Represents the workload factor calculator used to calculate workload factor.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to calculate workload factor.
     * </p>
     */
    private WorkloadFactorCalculator workloadFactorCalculator;

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
     * Represents the timeline reliability subtractor used to calculate reviewer coefficient.
     * </p>
     *
     * <p>
     * Equals to DEFAULT_TIMELINE_RELIABILITY_SUBTRACTOR, also can be initialized in the configure()
     *
     * : Should be positive value after initialized. Can not be changed after initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to calculate reviewer coefficient.
     * </p>
     */
    private double timelineReliabilitySubtractor = DEFAULT_TIMELINE_RELIABILITY_SUBTRACTOR;

    /**
     * <p>
     * Represents the review board data source name used to invoke methods in rboardApplicationBean.
     * </p>
     *
     * <p>
     * Should be initialized in the configure() method. Can not be null/empty after initialized. Can not be changed
     * after initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to invoke methods in rboardApplicationBean.
     * </p>
     */
    private String rboardDataSourceName;

    /**
     * <p>
     * Represents the primary reviewer responsibility id used to invoke the createRBoardApplication() method in *
     * rboardApplicationBean.
     * </p>
     *
     * <p>
     * Equals to DEFAULT_PRIMARY_REVIEWER_RESPONSIBILITY, also can be injected by spring. Should be positive value
     * after initialized. Can not be changed after initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to invoke the createRBoardApplication() method in * rboardApplicationBean.
     * </p>
     */
    private int primaryReviewerResponsibilityId = DEFAULT_PRIMARY_REVIEWER_RESPONSIBILITY;

    /**
     * <p>
     * Represents the reviewer responsibility id used to invoke the createRBoardApplication() method in *
     * rboardApplicationBean.
     * </p>
     *
     * <p>
     * Equals to DEFAULT_PRIMARY_REVIEWER_RESPONSIBILITY, also can be initialized in the configure()
     *
     * : Should be positive value after initialized. Can not be changed after initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to invoke the createRBoardApplication() method in * rboardApplicationBean.
     * </p>
     */
    private int reviewerResponsibilityId = DEFAULT_PRIMARY_REVIEWER_RESPONSIBILITY;

    /**
     * <p>
     * Represents the review phase name used to find the review phase object.
     * </p>
     *
     * <p>
     * Equals to DEFAULT_REVIEW_PHASE_NAME, also can be initialized in the configure() method. Can not be null/empty
     * after initialized. Can not be changed after initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to find the review phase object.
     * </p>
     */
    private String reviewPhaseName = DEFAULT_REVIEW_PHASE_NAME;

    /**
     * <p>
     * Represents the secondary reviewers number used to select secondary reviewers.
     * </p>
     *
     * <p>
     * Equals to DEFAULT_SECONDARY_REVIEWERS_NUMBER, also can be initialized in the configure() method. Should be
     * positive value after initialized. Can not be changed after initialized.
     * </p>
     *
     * <p>
     * Used by the selectReviewers() method to select secondary reviewers.
     * </p>
     */
    private int secondaryReviewersNumber = DEFAULT_SECONDARY_REVIEWERS_NUMBER;

    /**
     * Default no-argument constructor. Constructs a new RatingBasedSelectionAlgorithm instance.
     */
    public RatingBasedSelectionAlgorithm() {
    }

    /**
     * Select primary and secondary reviewers from the given review application list. Calculate coefficient for every
     * review application according to reviewers performance statistics and current workloads. Assign reviewers that
     * have the best coefficient value.
     *
     * @param applications an array of ReviewApplication object, from which the reviewer will be selected
     * @return The ReviewerSelectionResult object containing the selection result.
     * @throws IllegalArgumentException if given applications parameter is null/empty or contains null element or if
     *             all the application have the acceptPrimary field set to false, or if the applications array length
     *             is less than secondaryReviewerNumber + 1, or if applications contains different projectId or if
     *             applications contains duplicate reviewerId.
     * @throws IllegalStateException if any property not configured properly.
     * @throws ReviewSelectionException if any error occurs or to wrap underlying error.
     */
    public ReviewSelectionResult selectReviewers(ReviewApplication[] applications) throws ReviewSelectionException {
        long start = System.currentTimeMillis();
        final String signature = CLASS_NAME + ".selectReviewers(ReviewApplication[] applications)";

        Helper.checkState(log, "log", log, signature);
        Helper.logEntrance(log, signature, new String[] {"ReviewApplication"}, new Object[] {applications});
        checkReviewApplications(log, signature, applications, secondaryReviewersNumber);
        Helper.checkState(reviewerStatisticsManager, "reviewerStatisticsManager", log, signature);
        Helper.checkState(projectManager, "projectManager", log, signature);
        Helper.checkState(phaseManager, "phaseManager", log, signature);
        Helper.checkState(reviewApplicationProcessor, "reviewApplicationProcessor", log, signature);
        Helper.checkState(rboardApplicationBean, "rboardApplicationBean", log, signature);
        Helper.checkState(workloadFactorCalculator, "workloadFactorCalculator", log, signature);

        long projectTypeId = getProjectTypeId(applications[0], signature);
        List<ReviewerCoefficient> coefficients = calculateCoefficientValues(applications, signature, projectTypeId);

        ReviewApplication primaryApplication = findPrimaryReviewer(signature, coefficients);
        List<ReviewApplication> secondaryApplications = findSecondaryReviewers(coefficients);

        Phase reviewPhase = findReviewPhase(signature, primaryApplication);

        // assign primary reviewer
        try {
            rboardApplicationBean.createRBoardApplication(rboardDataSourceName, primaryApplication.getReviewerId(),
                primaryApplication.getProjectId(), primaryReviewerResponsibilityId, (int) reviewPhase.getId(),
                new Timestamp(0), (int) projectTypeId, true);
        } catch (RBoardRegistrationException e) {
            throw Helper.logException(log, signature, new ReviewSelectionException(
                "Error occurred when creating RBoardApplication for primaryApplication.", e));
        }
        // assign secondary reviewers
        for (ReviewApplication secondaryApplication : secondaryApplications) {
            try {
                rboardApplicationBean.createRBoardApplication(rboardDataSourceName, secondaryApplication
                    .getReviewerId(), secondaryApplication.getProjectId(), reviewerResponsibilityId,
                    (int) reviewPhase.getId(), new Timestamp(0), (int) projectTypeId, false);
            } catch (RBoardRegistrationException e) {
                throw Helper.logException(log, signature, new ReviewSelectionException(
                    "Error occurred when creating RBoardApplication for secondaryApplication.", e));
            }
        }

        // update review assignment statistics
        List<ReviewApplication> assignedApplications = new ArrayList<ReviewApplication>();
        assignedApplications.add(primaryApplication);
        for (ReviewApplication secondaryApplication : secondaryApplications) {
            assignedApplications.add(secondaryApplication);
        }
        try {
            reviewApplicationProcessor.updateReviewAssignmentStatistics(assignedApplications
                .toArray(new ReviewApplication[assignedApplications.size()]));
        } catch (ReviewApplicationProcessorException e) {
            throw Helper.logException(log, signature, new ReviewSelectionException(
                "Error occurred when updating ReviewAssignmentStatistics.", e));
        }

        // update unassigned reviewers statistics
        List<ReviewApplication> unassignedApplications = new ArrayList<ReviewApplication>();
        for (ReviewerCoefficient coefficient : coefficients) {
            unassignedApplications.add(coefficient.getReviewApplication());
        }
        try {
            reviewApplicationProcessor.updateUnassignedReviewersStatistics(unassignedApplications
                .toArray(new ReviewApplication[unassignedApplications.size()]));
        } catch (ReviewApplicationProcessorException e) {
            throw Helper.logException(log, signature, new ReviewSelectionException(
                "Error occurred when updating UnassignedReviewersStatistics.", e));
        }

        // log and return selection result
        ReviewSelectionResult result = new ReviewSelectionResult(primaryApplication, secondaryApplications
            .toArray(new ReviewApplication[secondaryApplications.size()]));
        Helper.logExit(log, signature, result, start);
        return result;
    }

    /**
     * Configures this instance with use of the given configuration object, all the properties are created via
     * objectFactory.
     *
     * @param config the configuration object containing ConfigurationObjectSpecificationFactory config.
     * @throws IllegalArgumentException if config is null
     * @throws ReviewSelectionConfigurationException if some error occurred when initializing an instance using the
     *             given configuration
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");

        ObjectFactory objectFactory = createObjectFactory(config);

        configureComplexTypeProperties(objectFactory);
        configureSimpleTypeProperties(config, objectFactory);

        // Configure members
        try {
            workloadFactorCalculator.configure(config);
        } catch (WorkloadFactorCalculatorConfigurationException e) {
            throw new ReviewSelectionConfigurationException(
                "Error occurred when configurating workloadFactorCalculator.", e);
        }
        try {
            reviewApplicationProcessor.configure(config);
        } catch (ReviewApplicationProcessorConfigurationException e) {
            throw new ReviewSelectionConfigurationException(
                "Error occurred when configuring the reviewApplicationProcessor.", e);
        }
    }

    /**
     * Configures the complex type properties with use of the given object factory.
     *
     * @param objectFactory the object factory
     * @throws ReviewSelectionConfigurationException if some error occurred when initializing an instance using the
     *             given configuration
     */
    private void configureComplexTypeProperties(ObjectFactory objectFactory) {
        try {
            reviewerStatisticsManager = (ReviewerStatisticsManager) createObject(objectFactory,
                "reviewerStatisticsManager");
        } catch (ClassCastException e) {
            throw new ReviewSelectionConfigurationException(
                "Failed to cast created object reviewerStatisticsManager to ReviewerStatisticsManager.", e);
        }
        try {
            projectManager = (ProjectManager) createObject(objectFactory, "projectManager");
        } catch (ClassCastException e) {
            throw new ReviewSelectionConfigurationException(
                "Failed to cast created object projectManager to ProjectManager.", e);
        }
        try {
            phaseManager = (PhaseManager) createObject(objectFactory, "phaseManager");
        } catch (ClassCastException e) {
            throw new ReviewSelectionConfigurationException(
                "Failed to cast created object phaseManager to PhaseManager.", e);
        }
        try {
            reviewApplicationProcessor = (ReviewApplicationProcessor) createObject(objectFactory,
                "reviewApplicationProcessor");
        } catch (ClassCastException e) {
            throw new ReviewSelectionConfigurationException(
                "Failed to cast created object reviewApplicationProcessor to ReviewApplicationProcessor.", e);
        }
        try {
            rboardApplicationBean = (RBoardApplicationBean) createObject(objectFactory, "rboardApplicationBean");
        } catch (ClassCastException e) {
            throw new ReviewSelectionConfigurationException(
                "Failed to cast created object rboardApplicationBean to RBoardApplicationBean.", e);
        }
        try {
            workloadFactorCalculator = (WorkloadFactorCalculator) createObject(objectFactory,
                "workloadFactorCalculator");
        } catch (ClassCastException e) {
            throw new ReviewSelectionConfigurationException(
                "Failed to cast created object workloadFactorCalculator to WorkloadFactorCalculator.", e);
        }
    }

    /**
     * Configures the simple type properties with use of the given object factory.
     *
     * @param config the configuration object containing the config of the object factory
     * @param objectFactory the object factory
     * @throws ReviewSelectionConfigurationException if some error occurred when initializing an instance using the
     *             given configuration
     */
    private void configureSimpleTypeProperties(ConfigurationObject config, ObjectFactory objectFactory) {
        String loggerName = null;
        if (containsChild(config, "loggerName")) {
            try {
                loggerName = (String) createObject(objectFactory, "loggerName");
            } catch (ClassCastException e) {
                throw new ReviewSelectionConfigurationException(
                    "Failed to cast created object loggerName to String.", e);
            }
        }
        if ((loggerName != null) && (!loggerName.trim().equals(""))) {
            log = LogFactory.getLog(loggerName);
        } else {
            log = LogFactory.getLog(this.getClass().getName());
        }

        if (containsChild(config, "timelineReliabilitySubtractor")) {
            try {
                timelineReliabilitySubtractor = (Double) createObject(objectFactory, "timelineReliabilitySubtractor");
            } catch (ClassCastException e) {
                throw new ReviewSelectionConfigurationException(
                    "Failed to cast created object timelineReliabilitySubtractor to Double.", e);
            }
            if (timelineReliabilitySubtractor <= 0) {
                throw new ReviewSelectionConfigurationException(
                    "Property timelineReliabilitySubtractor is not positive.");
            }
        } else {
            timelineReliabilitySubtractor = DEFAULT_TIMELINE_RELIABILITY_SUBTRACTOR;
        }

        try {
            rboardDataSourceName = (String) createObject(objectFactory, "rboardDataSourceName");
        } catch (ClassCastException e) {
            throw new ReviewSelectionConfigurationException(
                "Failed to cast created object rboardDataSourceName to String.", e);
        }
        if ((rboardDataSourceName == null) || (rboardDataSourceName.trim().equals(""))) {
            throw new ReviewSelectionConfigurationException("Property rboardDataSourceName can NOT be null or empty.");
        }

        if (containsChild(config, "primaryReviewerResponsibilityId")) {
            try {
                primaryReviewerResponsibilityId = (Integer) createObject(objectFactory,
                    "primaryReviewerResponsibilityId");
            } catch (ClassCastException e) {
                throw new ReviewSelectionConfigurationException(
                    "Failed to cast created object primaryReviewerResponsibilityId to Integer.", e);
            }
            if (primaryReviewerResponsibilityId <= 0) {
                throw new ReviewSelectionConfigurationException(
                    "Property primaryReviewerResponsibilityId is not positive.");
            }
        } else {
            primaryReviewerResponsibilityId = DEFAULT_PRIMARY_REVIEWER_RESPONSIBILITY;
        }

        if (containsChild(config, "reviewerResponsibilityId")) {
            try {
                reviewerResponsibilityId = (Integer) createObject(objectFactory, "reviewerResponsibilityId");
            } catch (ClassCastException e) {
                throw new ReviewSelectionConfigurationException(
                    "Failed to cast created object reviewerResponsibilityId to Integer.", e);
            }
            if (reviewerResponsibilityId <= 0) {
                throw new ReviewSelectionConfigurationException("Property reviewerResponsibilityId is not positive.");
            }
        } else {
            reviewerResponsibilityId = DEFAULT_REVIEWER_RESPONSIBILITY;
        }
        if (containsChild(config, "reviewPhaseName")) {
            try {
                reviewPhaseName = (String) createObject(objectFactory, "reviewPhaseName");
            } catch (ClassCastException e) {
                throw new ReviewSelectionConfigurationException(
                    "Failed to cast created object reviewPhaseName to String.", e);
            }
            if ((reviewPhaseName == null) || (reviewPhaseName.trim().equals(""))) {
                throw new ReviewSelectionConfigurationException("Property reviewPhaseName can NOT be null or empty.");
            }
        } else {
            reviewPhaseName = DEFAULT_REVIEW_PHASE_NAME;
        }

        if (containsChild(config, "secondaryReviewersNumber")) {
            try {
                secondaryReviewersNumber = (Integer) createObject(objectFactory, "secondaryReviewersNumber");
            } catch (ClassCastException e) {
                throw new ReviewSelectionConfigurationException(
                    "Failed to cast created object secondaryReviewersNumber to Integer.", e);
            }
            if (secondaryReviewersNumber <= 0) {
                throw new ReviewSelectionConfigurationException("Property secondaryReviewersNumber is not positive.");
            }
        } else {
            secondaryReviewersNumber = DEFAULT_SECONDARY_REVIEWERS_NUMBER;
        }
    }

    /**
     * Check the argument applications, throw and log IllegalArgumentException if given applications parameter is
     * null/empty or contains null element or if all the application have the acceptPrimary field set to false, or if
     * the applications array length is less than secondaryReviewerNumber + 1, or if applications contains different
     * projectId or if applications contains duplicate reviewerId.
     *
     * @param log the log object
     * @param signature the signature of the invoking function
     * @param applications the applications to check
     * @param secondaryReviewerNumber the number of secondaryReviewers
     * @throws IllegalArgumentException if given applications parameter is null/empty or contains null element or if
     *             all the application have the acceptPrimary field set to false, or if the applications array length
     *             is less than secondaryReviewerNumber + 1, or if applications contains different projectId or if
     *             applications contains duplicate reviewerId.
     */
    private static void checkReviewApplications(Log log, String signature, ReviewApplication[] applications,
        int secondaryReviewerNumber) {
        Helper.checkNull(applications, "applications", log, signature);
        if (applications.length == 0) {
            throw Helper.logException(log, signature, new IllegalArgumentException("The applications is empty."));
        }
        if (applications.length < secondaryReviewerNumber + 1) {
            throw Helper.logException(log, signature, new IllegalArgumentException(
                "The length of applications is less than secondaryReviewerNumber + 1."));
        }

        Helper.checkNull(applications[0], "element of application", log, signature);
        long projectId = applications[0].getProjectId();
        boolean hasPrimaryReviewer = false;
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

            if ((!hasPrimaryReviewer) && application.isAcceptPrimary()) {
                hasPrimaryReviewer = true;
            }
        }

        if (!hasPrimaryReviewer) {
            throw Helper.logException(log, signature, new IllegalArgumentException(
                "All the applications have the acceptPrimary field set to false."));
        }
    }

    /**
     * Create object factory from given config.
     *
     * @param config the configuration object
     * @return the object factory.
     * @throws ReviewSelectionConfigurationException if error occurred when creating the object factory.
     */
    private static ObjectFactory createObjectFactory(ConfigurationObject config) {
        ConfigurationObjectSpecificationFactory cosf;
        try {
            cosf = new ConfigurationObjectSpecificationFactory(config);
        } catch (IllegalReferenceException e) {
            throw new ReviewSelectionConfigurationException(
                "Error occurred when creating ConfigurationObjectSpecificationFactory from objectFactoryConfig.", e);
        } catch (SpecificationConfigurationException e) {
            throw new ReviewSelectionConfigurationException(
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
     * @throws ReviewSelectionConfigurationException if error occurred when getting the required child.
     */
    private static Object createObject(ObjectFactory objectFactory, String key) {
        // Create the object.
        Object object;
        try {
            object = objectFactory.createObject(key);
        } catch (InvalidClassSpecificationException e) {
            throw new ReviewSelectionConfigurationException("Error occurred when creating object with key " + key
                + ".", e);
        }
        return object;
    }

    /**
     * Check whether the config contains the child configuration.
     *
     * @param config the configuration object
     * @param childName the name of the property
     * @return whether the config contains the property.
     * @throws ReviewSelectionConfigurationException if error occurred when accessing the property.
     */
    private static boolean containsChild(ConfigurationObject config, String childName) {
        try {
            return config.containsChild(childName);
        } catch (ConfigurationAccessException e) {
            throw new ReviewSelectionConfigurationException("Error occurred when accessing child [" + childName
                + "].", e);
        }
    }

    /**
     * Calculate coefficient values and sort them for given applications.
     *
     * @param applications the applications to be calculated
     * @param signature the signature of the invoking method
     * @param projectTypeId the projectTypeId of the applications
     * @return the sorted coefficient values
     * @throws ReviewSelectionException if any error occurs.
     */
    private List<ReviewerCoefficient> calculateCoefficientValues(ReviewApplication[] applications,
        final String signature, long projectTypeId) throws ReviewSelectionException {
        // calculate coefficient value for every review application
        List<ReviewerCoefficient> coefficients = new ArrayList<ReviewerCoefficient>();
        for (ReviewApplication application : applications) {
            // get reviewer statistics
            ReviewerStatistics statistics;
            try {
                statistics = reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(application
                    .getReviewerId(), (int) projectTypeId);
            } catch (ReviewerStatisticsManagerException e) {
                throw Helper.logException(log, signature, new ReviewSelectionException(
                    "Error occurred when getting statistics for project with projectTypeId [" + projectTypeId
                        + "] of reviewer [" + application.getReviewerId() + "]"));
            }
            if (statistics == null) {
                throw Helper.logException(log, signature, new ReviewSelectionException(
                    "Unable to get ReviewerStatistics for reviewer " + application.getReviewerId()
                        + " and projectType " + projectTypeId + "."));
            }
            if (statistics.getTimelineReliability() <= timelineReliabilitySubtractor) {
                throw Helper.logException(log, signature, new ReviewSelectionException(
                        "The timelineReliability of ReviewerStatistics for reviewer " + application.getReviewerId()
                            + " and projectType " + projectTypeId
                            + " is not larger than timelineReliabilitySubtractor."));
            }
            if (statistics.getStatisticsType() != StatisticsType.AVERAGE) {
                throw Helper.logException(log, signature, new ReviewSelectionException(
                    "The statisticsType of reviewer " + application.getReviewerId() + " for projectType "
                        + projectTypeId + " doesn't equal to StatisticsType.AVERAGE."));
            }

            // get reviewer workload factor
            int activeReviewCount = (int) (rboardApplicationBean.getApplicationDelay(rboardDataSourceName,
                application.getReviewerId()) / RBoardApplicationBean.APPLICATION_DELAY_PER_ACTIVE_PROJECT);
            double workloadFactor = workloadFactorCalculator.calculateWorkloadFactor(activeReviewCount);
            // calculate coefficient
            // Average Coverage * Average Accuracy * (Average Timeline Reliability - 0.5) * Workload Factor
            double coefficientValue = statistics.getCoverage() * statistics.getAccuracy()
                * (statistics.getTimelineReliability() - timelineReliabilitySubtractor) * workloadFactor;
            coefficients.add(new ReviewerCoefficient(application, coefficientValue));
        }

        // sort reviewer application base on coefficient value
        Collections.sort(coefficients);
        return coefficients;
    }

    /**
     * Get projectTypeId of an application.
     *
     * @param application the ReviewApplication
     * @param signature the signature of the invoking method
     * @return the projectTypeId of the application
     * @throws ReviewSelectionException if any error occurs.
     */
    private long getProjectTypeId(ReviewApplication application, final String signature)
        throws ReviewSelectionException {
        // get projectTypeId for given project
        Project project;
        try {
            project = projectManager.getProject(application.getProjectId());
        } catch (PersistenceException e) {
            throw Helper.logException(log, signature, new ReviewSelectionException(
                "Error occurred when getting project " + application.getProjectId() + ".", e));
        }
        if (project == null) {
            throw Helper.logException(log, signature, new ReviewSelectionException("Unable to get project project "
                + application.getProjectId() + " from projectManager."));
        }
        ProjectType type = project.getProjectCategory().getProjectType();
        if (type.getReviewSystemVersion() != REVIEW_SYSTEM_VERSION_NEW) {
            throw Helper.logException(log, signature, new ReviewSelectionException(
                "The review system version of the project doesn't equal to REVIEW_SYSTEM_VERSION_NEW:"
                    + REVIEW_SYSTEM_VERSION_NEW + "."));
        }
        return type.getId();
    }

    /**
     * Find the secondary reviewers.
     *
     * @param coefficients the calculated and sorted coefficients of applications
     * @return the applications for secondary reviewer
     */
    private List<ReviewApplication> findSecondaryReviewers(List<ReviewerCoefficient> coefficients) {
        // find the secondary reviewers
        List<ReviewApplication> secondaryApplications = new ArrayList<ReviewApplication>();
        for (ReviewerCoefficient coefficient : coefficients) {
            if (secondaryApplications.size() >= secondaryReviewersNumber) {
                break;
            }
            secondaryApplications.add(coefficient.getReviewApplication());
        }
        return secondaryApplications;
    }

    /**
     * Find the primary reviewer.
     *
     * @param signature the signature of the invoking method
     * @param coefficients the calculated and sorted coefficients of applications
     * @return the application for primary reviewer
     * @throws ReviewSelectionException if any error occurs.
     */
    private ReviewApplication findPrimaryReviewer(final String signature, List<ReviewerCoefficient> coefficients)
        throws ReviewSelectionException {
        // find the primary reviewer
        ReviewApplication primaryApplication = null;
        for (Iterator<ReviewerCoefficient> i = coefficients.iterator(); i.hasNext();) {
            ReviewerCoefficient coefficient = i.next();
            if (coefficient.getReviewApplication().isAcceptPrimary()) {
                primaryApplication = coefficient.getReviewApplication();
                i.remove();
                break;
            }
        }
        if (primaryApplication == null) {
            throw Helper.logException(log, signature, new ReviewSelectionException(
                "Missing application for primary reviewer."));
        }
        return primaryApplication;
    }

    /**
     * Get the review phase of given application.
     *
     * @param signature the signature of the invoking method
     * @param primaryApplication the ReviewApplication
     * @return the review phase
     * @throws ReviewSelectionException if any error occurs.
     */
    private Phase findReviewPhase(final String signature, ReviewApplication primaryApplication)
        throws ReviewSelectionException {
        // find the review phase
        com.topcoder.project.phases.Project phaseProject;
        try {
            phaseProject = phaseManager.getPhases(primaryApplication.getProjectId());
        } catch (PhaseManagementException e) {
            throw Helper.logException(log, signature, new ReviewSelectionException(
                "Error occurred when getting phaseProject.", e));
        }
        Phase reviewPhase = null;
        for (Phase phase : phaseProject.getAllPhases()) {
            if (reviewPhaseName.equals(phase.getPhaseType().getName())) {
                reviewPhase = phase;
                break;
            }
        }
        if (reviewPhase == null) {
            throw Helper.logException(log, signature, new ReviewSelectionException("Unable to find review phase ["
                + reviewPhaseName + "]."));
        }
        return reviewPhase;
    }
}
