/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.cronos.onlinereview.review.statistics.AccuracyCalculationException;
import com.cronos.onlinereview.review.statistics.AccuracyCalculator;
import com.cronos.onlinereview.review.statistics.CoverageCalculationException;
import com.cronos.onlinereview.review.statistics.CoverageCalculator;
import com.cronos.onlinereview.review.statistics.PersistenceException;
import com.cronos.onlinereview.review.statistics.ProjectNotFoundException;
import com.cronos.onlinereview.review.statistics.StatisticsCalculator;
import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.cronos.onlinereview.review.statistics.StatisticsCalculatorException;
import com.cronos.onlinereview.review.statistics.TimelineReliabilityCalculationException;
import com.cronos.onlinereview.review.statistics.TimelineReliabilityCalculator;
import com.cronos.onlinereview.review.statistics.handler.StatisticsPostCalculationHandler;
import com.cronos.onlinereview.review.statistics.handler.StatisticsPostCalculationHandlerException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.DeliverableManager;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is the main class of this component. It is an implementation of StatisticsCalculator that extracts all review
 * specific data from persistence using managers and uses aggregated implementations of TimelineReliabilityCalculator,
 * CoverageCalculator and AccuracyCalculator to perform the actual calculation of each reviewer statistics
 * coefficient.
 * </p>
 *
 * <p>
 * ReviewerStatisticsCalculator performs distribution of eligibility points among all secondary reviewers,
 * proportional to their total eligibility points. This class allows to register and unregister the statistics post
 * calculation handlers that are called after each reviewer statistics instance is calculated. This class uses Logging
 * Wrapper logger to perform logging of errors and debug information.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is immutable and thread safe assuming that configure() method will be called just
 * once right after instantiation, before calling calculateStatistics() method. Since all managers used by this class
 * are not thread safe, the additional synchronization on "this" instance is used when accessing them. The Log and
 * calculator instances used by this class are thread safe.
 * </p>
 *
 * <pre>
 * // Create an instance of ReviewerStatisticsCalculator
 * ReviewerStatisticsCalculator reviewerStatisticsCalculator = new ReviewerStatisticsCalculator();
 *
 * // Get configuration for ReviewerStatisticsCalculator
 * ConfigurationObject config = TestsHelper.loadConfig(&quot;config.xml&quot;).getChild(
 *     &quot;com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator&quot;);
 *
 * // Configure the calculator
 * reviewerStatisticsCalculator.configure(config);
 *
 * // Register post calculation handler - change to real implementation
 * StatisticsPostCalculationHandler handler = new StatisticsPostCalculationHandlerImpl();
 * reviewerStatisticsCalculator.registerPostCalculationHandler(handler);
 *
 * // Unregister this post calculation handler
 * reviewerStatisticsCalculator.unregisterPostCalculationHandler(handler);
 * // Calculate statistics for project with ID=1,
 * // distribute 2 eligibility points among secondary reviewers
 * ReviewerStatistics[] reviewerStatisticses = reviewerStatisticsCalculator.calculateStatistics(1, 2);
 * </pre>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ReviewerStatisticsCalculator implements StatisticsCalculator {
    /**
     * Class name.
     */
    private static final String CLASS_NAME = ReviewerStatisticsCalculator.class.getName();

    /**
     * <p>
     * The list of registered statistics post calculation handlers.
     * </p>
     *
     * <p>
     * Collection instance is initialized in the constructor and never changed after that. Its content can be modified
     * in registerPostCalculationHandler() and unregisterPostCalculationHandler(). Is used in calculateStatistics().
     * </p>
     */
    private final Set<StatisticsPostCalculationHandler> handlers;

    /**
     * <p>
     * The timeline reliability calculator to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in calculateReliability().
     * </p>
     */
    private TimelineReliabilityCalculator timelineReliabilityCalculator;

    /**
     * <p>
     * The coverage calculator to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in calculateStatistics().
     * </p>
     */
    private CoverageCalculator coverageCalculator;

    /**
     * <p>
     * The accuracy calculator to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in calculateStatistics().
     * </p>
     */
    private AccuracyCalculator accuracyCalculator;

    /**
     * <p>
     * The resource manager to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in getSecondaryReviewerIds(), getPrimaryReviewerId() and getReviewerResourceIds().
     * </p>
     *
     * <p>
     * <b>Thread safety</b>: Implementations of this manager interface are not required to be thread safe. Thus to
     * make ReviewerStatisticsCalculator thread safe, all calls to this manager's methods must be synchronized on
     * "this" instance.
     * </p>
     */
    private ResourceManager resourceManager;

    /**
     * <p>
     * The phase manager to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in calculateStatistics().
     * </p>
     *
     * <p>
     * <b>Thread safety</b>: Implementations of this manager interface are not required to be thread safe. Thus to
     * make ReviewerStatisticsCalculator thread safe, all calls to this manager's methods must be synchronized on
     * "this" instance.
     * </p>
     */
    private PhaseManager phaseManager;

    /**
     * <p>
     * The deliverable manager to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in getDeliverable().
     * </p>
     *
     * <p>
     * <b>Thread safety</b>: Implementations of this manager interface are not required to be thread safe. Thus to
     * make ReviewerStatisticsCalculator thread safe, all calls to this manager's methods must be synchronized on
     * "this" instance.
     * </p>
     */
    private DeliverableManager deliverableManager;

    /**
     * <p>
     * The review manager to be used by this class.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Cannot be null after initialization. Is used
     * in getReviewsForReviewer().
     * </p>
     *
     * <p>
     * Thread safety: Implementations of this manager interface are not required to be thread safe. Thus to make
     * ReviewerStatisticsCalculator thread safe, all calls to this manager's methods must be synchronized on "this"
     * instance.
     * </p>
     */
    private ReviewManager reviewManager;

    /**
     * <p>
     * The project manager to be used by this class. Is initialized in configure() method and never changed after
     * that. Cannot be null after initialization. Is used in calculateStatistics().
     * </p>
     *
     * <p>
     * Thread safety: Implementations of this manager interface are not required to be thread safe. Thus to make
     * ReviewerStatisticsCalculator thread safe, all calls to this manager's methods must be synchronized on "this"
     * instance.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * The ID of the screening phase type.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in calculateStatistics().
     * </p>
     */
    private long screeningPhaseTypeId;

    /**
     * <p>
     * The ID of the secondary reviewer review phase type.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in calculateStatistics().
     * </p>
     */
    private long secondaryReviewerReviewPhaseTypeId;

    /**
     * <p>
     * The ID of the primary review evaluation phase type.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in calculateStatistics().
     * </p>
     */
    private long primaryReviewEvaluationPhaseTypeId;

    /**
     * <p>
     * The ID of the primary review appeals response phase type.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in calculateStatistics().
     * </p>
     */
    private long primaryReviewAppealsResponsePhaseTypeId;

    /**
     * <p>
     * The ID of the aggregation review phase type.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in calculateReliability().
     * </p>
     */
    private long aggregationReviewPhaseTypeId;

    /**
     * <p>
     * The ID of the final review phase type.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in calculateStatistics().
     * </p>
     */
    private long finalReviewPhaseTypeId;

    /**
     * <p>
     * The ID of the secondary reviewer resource role.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in getSecondaryReviewerIds().
     * </p>
     */
    private long secondaryReviewerResourceRoleId;

    /**
     * <p>
     * The ID of the primary review evaluator resource role.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. Must be positive after initialization. Is
     * used in getPrimaryReviewerId().
     * </p>
     */
    private long primaryReviewEvaluatorResourceRoleId;

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     *
     * <p>
     * Is initialized in configure() method and never changed after that. If is null after initialization, logging is
     * not performed. Is used in calculateStatistics().
     * </p>
     */
    private Log log;

    /**
     * <p>
     * The signature of the method to be logged.
     * </p>
     */
    private String signature;

    /**
     * Creates an instance of ReviewerStatisticsCalculator.
     */
    public ReviewerStatisticsCalculator() {
        handlers = Collections.synchronizedSet(new LinkedHashSet<StatisticsPostCalculationHandler>());
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config the configuration object
     *
     * @throws IllegalArgumentException if the configuration object is null
     * @throws StatisticsCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");

        // Get logger name from the configuration:
        try {
            String loggerName = (String) config.getPropertyValue("loggerName");
            Helper.checkEmptyLogger(loggerName, "loggerName");

            // Get Log instance to be used by this class
            if (loggerName != null) {
                log = LogFactory.getLog(loggerName);
            }

            // Get object factory configuration
            ConfigurationObject objectFactoryConfig = config.getChild("objectFactoryConfig");

            // Create configuration object specification factory
            ConfigurationObjectSpecificationFactory cosf = new ConfigurationObjectSpecificationFactory(
                objectFactoryConfig);

            // Create object factory
            ObjectFactory objectFactory = new ObjectFactory(cosf);

            // Configure calculator
            configureCalculators(config, objectFactory);

            // Create resource manager with OF
            resourceManager = (ResourceManager) createObject(config, objectFactory, "resourceManagerKey");

            // Create phase manager with OF
            phaseManager = (PhaseManager) createObject(config, objectFactory, "phaseManagerKey");

            // Create deliverable manager with OF
            deliverableManager = (DeliverableManager) createObject(config, objectFactory, "deliverableManagerKey");

            // Create review manager with OF
            reviewManager = (ReviewManager) createObject(config, objectFactory, "reviewManagerKey");

            // Create project manager with OF
            projectManager = (ProjectManager) createObject(config, objectFactory, "projectManagerKey");

            // Get screening phase type from configuration
            screeningPhaseTypeId = createId(config, objectFactory, "screeningPhaseTypeId");

            // Get aggregation review phase type from configuration
            aggregationReviewPhaseTypeId = createId(config, objectFactory, "aggregationReviewPhaseTypeId");

            // Get secondary reviewer review phase type from configuration
            secondaryReviewerReviewPhaseTypeId = createId(config, objectFactory, "secondaryReviewerReviewPhaseTypeId");

            // Get primary reviewer review phase type from configuration
            primaryReviewEvaluationPhaseTypeId = createId(config, objectFactory, "primaryReviewEvaluationPhaseTypeId");

            // Get secondary appeals response phase type from configuration
            primaryReviewAppealsResponsePhaseTypeId = createId(config, objectFactory,
                "primaryReviewAppealsResponsePhaseTypeId");

            // Get secondary final review phase type from configuration
            finalReviewPhaseTypeId = createId(config, objectFactory, "finalReviewPhaseTypeId");

            // Get secondary reviewer resource role id from configuration
            secondaryReviewerResourceRoleId = createId(config, objectFactory, "secondaryReviewerResourceRoleId");

            // Get primary review evaluator resource id from configuration
            primaryReviewEvaluatorResourceRoleId = createId(config, objectFactory,
                "primaryReviewEvaluatorResourceRoleId");
        } catch (ClassCastException e) {
            throw new StatisticsCalculatorConfigurationException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new StatisticsCalculatorConfigurationException(e.getMessage(), e);
        } catch (ConfigurationAccessException e) {
            throw new StatisticsCalculatorConfigurationException(e.getMessage(), e);
        } catch (IllegalReferenceException e) {
            throw new StatisticsCalculatorConfigurationException(e.getMessage(), e);
        } catch (SpecificationConfigurationException e) {
            throw new StatisticsCalculatorConfigurationException(e.getMessage(), e);
        } catch (InvalidClassSpecificationException e) {
            throw new StatisticsCalculatorConfigurationException(e.getMessage(), e);
        }
    }

    /**
     * Create and configure all calculators from configuration file.
     *
     * @param config the configuration object
     * @param objectFactory the object factory
     *
     * @throws ConfigurationAccessException if any error occurs in the configuration object
     * @throws InvalidClassSpecificationException if any error in the object factory
     * @throws StatisticsCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration
     */
    private void configureCalculators(ConfigurationObject config, ObjectFactory objectFactory)
        throws ConfigurationAccessException, InvalidClassSpecificationException {
        // Create timeline reliability calculator with OF
        timelineReliabilityCalculator = (TimelineReliabilityCalculator) createObject(config, objectFactory,
            "timelineReliabilityCalculatorKey");

        // Get timeline reliability calculator configuration
        ConfigurationObject timelineReliabilityCalculatorConfig = config
            .getChild("timelineReliabilityCalculatorConfig");
        Helper.checkNull(timelineReliabilityCalculatorConfig, "timelineReliabilityCalculatorConfig");

        // Configure the timeline reliability calculator
        timelineReliabilityCalculator.configure(timelineReliabilityCalculatorConfig);

        // Create coverage calculator with OF
        coverageCalculator = (CoverageCalculator) createObject(config, objectFactory, "coverageCalculatorKey");

        // Get coverage calculator configuration
        ConfigurationObject coverageCalculatorConfig = config.getChild("coverageCalculatorConfig");
        Helper.checkNull(coverageCalculatorConfig, "coverageCalculatorConfig");

        // Configure the coverage calculator
        coverageCalculator.configure(coverageCalculatorConfig);

        // Create accuracy calculator with OF
        accuracyCalculator = (AccuracyCalculator) createObject(config, objectFactory, "accuracyCalculatorKey");

        // Get accuracy calculator configuration
        ConfigurationObject accuracyCalculatorConfig = config.getChild("accuracyCalculatorConfig");
        Helper.checkNull(accuracyCalculatorConfig, "accuracyCalculatorConfig");

        // Configure the accuracy calculator
        accuracyCalculator.configure(accuracyCalculatorConfig);
    }

    /**
     * Create object from key. If property value is invalid will throw exception (e.g. null or empty).
     *
     * @param config the configuration object
     * @param objectFactory the object factory
     * @param key the calculator key in configuration file
     *
     * @return the configure calculator
     *
     * @throws ConfigurationAccessException if any error occurs in the configuration object
     * @throws InvalidClassSpecificationException if any error in the object factory
     * @throws StatisticsCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration
     */
    private Object createObject(ConfigurationObject config, ObjectFactory objectFactory, String key)
        throws ConfigurationAccessException, InvalidClassSpecificationException {
        // Check required key is present
        String objectKey = (String) config.getPropertyValue(key);
        Helper.checkNullOrEmpty(log, signature, objectKey, key);

        return objectFactory.createObject(objectKey);
    }

    /**
     * Create id from key. If property value is invalid will throw exception (e.g. null or not positive).
     *
     * @param config the configuration object
     * @param objectFactory the object factory
     * @param key the id key in configuration file
     *
     * @return the configure calculator
     *
     * @throws ConfigurationAccessException if any error occurs in the configuration object
     * @throws InvalidClassSpecificationException if any error in the object factory
     * @throws StatisticsCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration
     */
    private long createId(ConfigurationObject config, ObjectFactory objectFactory, String key)
        throws ConfigurationAccessException, InvalidClassSpecificationException {
        // Check required key is present
        String strId = (String) config.getPropertyValue(key);
        Helper.checkNullOrEmpty(log, signature, strId, key);

        // Check if id value is positive
        long id = Long.parseLong(strId);
        Helper.checkPositive(id, key);

        return id;
    }

    /**
     * Calculates reviewer statistics for the project with the specified ID using the given eligibility points pool
     * size. Statistics data are calculated for the primary reviewer and all secondary reviewers. Below is the
     * high-level overview of operations performed in this method:
     *
     * @param projectId the ID of the project
     * @param eligibilityPointsPool the eligibility points pool for this contest
     *
     * @return the calculated reviewer statistics (not null, doesn't contain null)
     *
     * @throws IllegalArgumentException if projectId <= 0 or eligibilityPointsPool < 0
     * @throws IllegalStateException if calculator was not configured properly via configure() method
     *             (primaryReviewEvaluatorResourceRoleId equals to 0)
     * @throws ProjectNotFoundException if project with the given ID doesn't exist in persistence
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     * @throws TimelineReliabilityCalculationException if some error occurred when performing the calculation of
     *             timeline reliability coefficients
     * @throws CoverageCalculationException if some error occurred when performing the calculation of coverage
     *             coefficients
     * @throws AccuracyCalculationException if some error occurred when performing the calculation of accuracy
     *             coefficients
     * @throws StatisticsCalculatorException if some other error occurred
     */
    public ReviewerStatistics[] calculateStatistics(long projectId, double eligibilityPointsPool)
        throws StatisticsCalculatorException {
        Date start = new Date();
        signature = CLASS_NAME + ".calculateStatistics(long projectId, double eligibilityPointsPool)";

        Helper.checkPositive(log, signature, projectId, "projectId");
        Helper.checkNegative(log, signature, eligibilityPointsPool, "eligibilityPointsPool");

        Helper.logEntrance(log, signature, new String[] {"projectId", "eligibilityPointsPool"}, new Object[] {
            projectId, eligibilityPointsPool});

        Helper.checkState(log, signature, (primaryReviewEvaluatorResourceRoleId == 0) ? null
            : primaryReviewEvaluatorResourceRoleId, "primaryReviewEvaluatorResourceRoleId");

        // Get project category
        ProjectCategory projectCategory = getProjectCategory(projectId);

        // Get project category ID (it represents the contest type, e.g. Conceptualization, Specification,
        // Architecture, Design, etc)
        long projectCategoryId = projectCategory.getId();

        // Get all phases for this project
        List<Phase> finalReviewPhases = new ArrayList<Phase>();

        // Update phases value
        List<Phase> paramPhases;
        try {
            paramPhases = updatePhases(projectId, finalReviewPhases);
        } catch (PersistenceException e) {
            throw Helper.logException(log, signature, e, "Error accessing project's information from database.");
        }

        /*
         * result.add(screeningPhase); result.add(secondaryReviewerReviewPhase);
         * result.add(primaryReviewEvaluationPhase); result.add(primaryReviewAppealsResponsePhase);
         * result.add(aggregationReviewPhase);
         */

        Phase secondaryReviewerReviewPhase = paramPhases.get(1);
        Phase primaryReviewEvaluationPhase = paramPhases.get(2);
        Phase primaryReviewAppealsResponsePhase = paramPhases.get(3);

        // Get ID of the primary review evaluation phase
        long primaryReviewEvaluationPhaseId = primaryReviewEvaluationPhase.getId();
        // Get user ID of the primary reviewer
        long primaryReviewerId = getPrimaryReviewerId(primaryReviewEvaluationPhaseId);

        // Get ID of the secondary reviewer review phase
        long secondaryReviewerReviewPhaseId = secondaryReviewerReviewPhase.getId();
        // Get user IDs of secondary reviewers
        List<Long> secondaryReviewerIds = getSecondaryReviewerIds(secondaryReviewerReviewPhaseId);

        // Get ID of the primary review appeals response phase
        long primaryReviewAppealsResponsePhaseId = primaryReviewAppealsResponsePhase.getId();
        // Get all evaluated reviews
        Review[][] evaluatedReviews = createEvaluatedReviews(projectId, primaryReviewerId, secondaryReviewerIds,
            primaryReviewAppealsResponsePhaseId, secondaryReviewerReviewPhaseId);

        // Get all reviewer statistics
        List<ReviewerStatistics> allReviewerStatistics = createAllReviewerStatistics(projectId, projectCategoryId,
            primaryReviewerId, secondaryReviewerIds);

        doCalculations(projectId, evaluatedReviews, allReviewerStatistics, primaryReviewerId, secondaryReviewerIds,
            paramPhases, finalReviewPhases);

        try {
            finalizeCalculation(allReviewerStatistics, eligibilityPointsPool);
        } catch (StatisticsPostCalculationHandlerException e) {
            throw Helper.logException(log, signature, new StatisticsCalculatorException(e.getMessage(), e),
                "StatisticsPostCalculationHandlerException was thrown while calculation finalization.");
        }

        // Convert the list to an array:
        ReviewerStatistics[] result = allReviewerStatistics.toArray(new ReviewerStatistics[allReviewerStatistics
            .size()]);

        Helper.logExit(log, signature, new Object[] {result}, start);

        return result;
    }

    /**
     * Gets the project category from project id.
     *
     * @param projectId the ID of the project
     *
     * @return the project category
     *
     * @throws ProjectNotFoundException if project with the given ID doesn't exist in persistence
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     */
    private ProjectCategory getProjectCategory(long projectId) throws PersistenceException, ProjectNotFoundException {
        // Get project details using the manager
        Project project;
        try {
            project = projectManager.getProject(projectId);
        } catch (com.topcoder.management.project.PersistenceException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error accessing project's information from database.");
        }

        if (project == null) {
            throw Helper.logException(log, signature, new ProjectNotFoundException("'project' cannot be null",
                projectId), "Project with id " + projectId + " is null.");
        }

        return project.getProjectCategory();
    }

    /**
     * Update phase information.
     *
     * @param projectId the ID of the project
     * @param finalReviewPhases the final review phases
     *
     * @return a list of phases
     *
     * @throws StatisticsCalculatorException if any error accessing the phase from manager
     */
    private List<Phase> updatePhases(long projectId, List<Phase> finalReviewPhases)
        throws StatisticsCalculatorException {
        Phase screeningPhase = null;
        Phase secondaryReviewerReviewPhase = null;
        Phase primaryReviewEvaluationPhase = null;
        Phase primaryReviewAppealsResponsePhase = null;
        Phase aggregationReviewPhase = null;

        // Get phases project using the manager
        com.topcoder.project.phases.Project phasesProject;
        try {
            phasesProject = phaseManager.getPhases(projectId);
            if (phasesProject == null) {
                throw new PersistenceException("No phases for project " + projectId + " were found in persistence.");
            }
        } catch (PhaseManagementException e) {
            throw new PersistenceException(e.getMessage(), e);
        }

        Phase[] allPhases = phasesProject.getAllPhases();
        for (Phase phase : allPhases) {
            // Get phase type of the next phase
            PhaseType phaseType = phase.getPhaseType();
            // Get phase type ID
            long phaseTypeId = phaseType.getId();
            if (phaseTypeId == screeningPhaseTypeId) {
                screeningPhase = phase;
            } else if (phaseTypeId == secondaryReviewerReviewPhaseTypeId) {
                secondaryReviewerReviewPhase = phase;
            } else if (phaseTypeId == primaryReviewEvaluationPhaseTypeId) {
                primaryReviewEvaluationPhase = phase;
            } else if (phaseTypeId == primaryReviewAppealsResponsePhaseTypeId) {
                primaryReviewAppealsResponsePhase = phase;
            } else if (phaseTypeId == aggregationReviewPhaseTypeId) {
                aggregationReviewPhase = phase;
            } else if (phaseTypeId == finalReviewPhaseTypeId) {
                finalReviewPhases.add(phase);
            }
        }

        if (screeningPhase == null || secondaryReviewerReviewPhase == null || primaryReviewEvaluationPhase == null
            || primaryReviewAppealsResponsePhase == null || aggregationReviewPhase == null) {
            throw new PersistenceException("Project's phases were not found in persistence, projectId = " + projectId
                + ".");
        }

        List<Phase> result = new ArrayList<Phase>();
        result.add(screeningPhase);
        result.add(secondaryReviewerReviewPhase);
        result.add(primaryReviewEvaluationPhase);
        result.add(primaryReviewAppealsResponsePhase);
        result.add(aggregationReviewPhase);

        return result;
    }

    /**
     * Registers the given post calculation handler. If this handler is already registered, the method does nothing.
     *
     * @param handler the statistics post calculation handler to be registered
     *
     * @throws IllegalArgumentException if handler is null
     */
    public void registerPostCalculationHandler(StatisticsPostCalculationHandler handler) {
        Helper.checkNull(handler, "handler");

        handlers.add(handler);
    }

    /**
     * Unregisters the given post calculation handler. If this handler was not previously registered, the method does
     * nothing.
     *
     * @param handler the statistics post calculation handler to be unregistered
     *
     * @throws IllegalArgumentException if handler is null
     */
    public void unregisterPostCalculationHandler(StatisticsPostCalculationHandler handler) {
        Helper.checkNull(handler, "handler");

        handlers.remove(handler);
    }

    /**
     * Retrieves the user ID of the primary reviewer.
     *
     * @param primaryReviewEvaluationPhaseId the ID of the primary review evaluation phase
     *
     * @return the retrieved primary reviewer ID
     *
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     */
    private long getPrimaryReviewerId(long primaryReviewEvaluationPhaseId) throws PersistenceException {
        // Create filter for matching phase ID
        Filter phaseFilter = ResourceFilterBuilder.createPhaseIdFilter(primaryReviewEvaluationPhaseId);
        // Create filter for matching primary reviewer resource role
        Filter resourceRoleFilter = ResourceFilterBuilder
            .createResourceRoleIdFilter(primaryReviewEvaluatorResourceRoleId);

        // Create combined filter:
        Filter filter = new AndFilter(phaseFilter, resourceRoleFilter);

        // Search for resources using the created filter:
        Resource[] resources;
        try {
            resources = resourceManager.searchResources(filter);
        } catch (SearchBuilderConfigurationException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        } catch (SearchBuilderException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        } catch (ResourcePersistenceException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        }

        if (resources.length != 1) {
            throw Helper.logException(log, signature,
                new PersistenceException("'resources' should be equal to one."),
                "'resources' should be equal to one.");
        }

        Resource resource = resources[0];

        // Get value of property that holds user ID converted to string:
        String resultStr = (String) resource.getProperty("External Reference ID");

        // Parse user ID from this property value
        long result = Long.parseLong(resultStr);

        return result;
    }

    /**
     * Retrieves the user IDs of all secondary reviewers.
     *
     * @param secondaryReviewerReviewPhaseId the ID of the secondary reviewer review phase
     *
     * @return the retrieved secondary reviewer IDs (not null, doesn't contain null)
     *
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     */
    private List<Long> getSecondaryReviewerIds(long secondaryReviewerReviewPhaseId) throws PersistenceException {
        // Create filter for matching phase ID:
        Filter phaseFilter = ResourceFilterBuilder.createPhaseIdFilter(secondaryReviewerReviewPhaseId);

        // Create filter for matching secondary reviewer roles:
        Filter resourceRoleFilter = ResourceFilterBuilder.createResourceRoleIdFilter(secondaryReviewerResourceRoleId);

        // Create combined filter:
        Filter filter = new AndFilter(phaseFilter, resourceRoleFilter);

        // Search for resources using the created filter:
        Resource[] resources;
        try {
            resources = resourceManager.searchResources(filter);
        } catch (SearchBuilderConfigurationException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        } catch (SearchBuilderException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        } catch (ResourcePersistenceException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        }

        // Create a result list
        List<Long> result = new ArrayList<Long>();

        for (Resource resource : resources) {
            // Get value of property that holds user ID converted to string:
            String reviewerIdStr = (String) resource.getProperty("External Reference ID");

            // Parse user ID from the string and add it to the list:
            result.add(Long.parseLong(reviewerIdStr));
        }

        if (result.size() == 0) {
            throw Helper.logException(log, signature, new PersistenceException(
                "At least one secondary reviewer must exist."), "At least one secondary reviewer must exist.");
        }

        return result;
    }

    /**
     * Create evaluated reviews.
     *
     * @param projectId the project id
     * @param primaryReviewerId the primary reviewer id
     * @param secondaryReviewerIds the secondary reviewer id
     * @param primaryReviewAppealsResponsePhaseId the primary reviewer appeals response id
     * @param secondaryReviewerReviewPhaseId the secondary reviewer review phase id
     *
     * @return the review object
     *
     * @throws PersistenceException if any error occurs
     */
    private Review[][] createEvaluatedReviews(long projectId, long primaryReviewerId,
        List<Long> secondaryReviewerIds, long primaryReviewAppealsResponsePhaseId, long secondaryReviewerReviewPhaseId)
        throws PersistenceException {
        // Each element in the outer array corresponds to one secondary reviewer; inner array contains all reviews
        // for one reviewer (one review per each submission)
        Review[][] initialReviews = new Review[secondaryReviewerIds.size()][];
        int i = 0;
        try {
            for (Long reviewerId : secondaryReviewerIds) {
                // Get reviews for this reviewer
                Review[] reviewsForReviewer = getReviewsForReviewer(reviewerId, secondaryReviewerReviewPhaseId, null);
                initialReviews[i++] = reviewsForReviewer;
            }
            Review[][] evaluatedReviews = getEvaluatedReviews(primaryReviewerId, primaryReviewAppealsResponsePhaseId,
                initialReviews);

            return evaluatedReviews;
        } catch (PersistenceException e) {
            throw Helper.logException(log, signature, e, "Error creating evaluated review.");
        }
    }

    /**
     * Retrieves the reviews for reviewer with the specified user ID submitted during the specified phase for the
     * specified submission (optional).
     *
     * @param phaseId the ID of the phase
     * @param userId the user ID of the reviewer
     * @param submissionId the ID of the submission (null if reviews for all submissions must be retrieved)
     *
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     *
     * @return the retrieved reviews (not null, doesn't contain null)
     */
    private Review[] getReviewsForReviewer(long userId, long phaseId, Long submissionId) throws PersistenceException {
        // Get IDs of resources associated with this reviewer
        List<Long> reviewerResourceIds = getReviewerResourceIds(userId, phaseId);

        // Create filter for matching reviewer resources:
        Filter filter = new InFilter("reviewer", reviewerResourceIds);

        if (submissionId != null) {
            // Create filter for matching submission ID
            Filter submissionFilter = new EqualToFilter("submission", submissionId);

            // Combine two filters
            filter = new AndFilter(filter, submissionFilter);
        }

        // Search for complete reviews using the created filter:
        Review[] reviews;
        try {
            reviews = reviewManager.searchReviews(filter, true);
        } catch (ReviewManagementException e) {
            throw Helper.logException(log, CLASS_NAME, new PersistenceException(e.getMessage(), e),
                "Error retrieving reviews.");
        }

        return reviews;
    }

    /**
     * Retrieves the reviews for Primary Review Appeals Response phase that correspond to the given reviewers for
     * Secondary Reviewer Review phase. Note that each pair of reviews have the same submission ID specified and are
     * associated with the same reviewer user ID (via different resources with different resource roles).
     *
     * @param initialReviews the initial reviews (for Secondary Reviewer Review phase)
     * @param primaryReviewAppealsResponsePhaseId the ID of the primary review appeals response phase
     * @param primaryReviewerId the user ID of the primary reviewer
     *
     * @return the retrieved evaluated reviews (not null, doesn't contain null, inner arrays also doesn't contain null
     *         elements, number of elements in inner and outer arrays fully correspond to the input initialReviews;
     *         [i][j]-th element in the output corresponds to [i][j]-th element in initialReviews)
     *
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     */
    private Review[][] getEvaluatedReviews(long primaryReviewerId, long primaryReviewAppealsResponsePhaseId,
        Review[][] initialReviews) throws PersistenceException {
        int reviewersNum = initialReviews.length;
        int submissionsNum = initialReviews[0].length;

        Review[][] result = new Review[reviewersNum][];
        for (int i = 0; i < reviewersNum; i++) {
            result[i] = new Review[submissionsNum];
            for (int j = 0; j < submissionsNum; j++) {
                Review initialReview = initialReviews[i][j];

                // Get ID of the associated submission for this initial review
                long submissionId = initialReview.getSubmission();

                // Get final review (submitted during the Primary Review Appeals Response phase) for this submission
                Review[] reviews = getReviewsForReviewer(primaryReviewerId, primaryReviewAppealsResponsePhaseId,
                    submissionId);
                if (reviews.length != 1) {
                    throw new PersistenceException("Reviews lenght cannot be different of 1, primaryReviewerId = "
                        + primaryReviewerId + ", primaryReviewAppealsResponsePhaseId = "
                        + primaryReviewAppealsResponsePhaseId + ", submissionId = " + submissionId + ".");
                }

                result[i][j] = reviews[0];
            }
        }

        return result;
    }

    /**
     * Create all reviewer statistics.
     *
     * @param projectId the project id
     * @param projectCategoryId the retrieve project category id
     * @param primaryReviewerId the primary reviewer id
     * @param secondaryReviewerIds the secondaries reviewers id
     *
     * @return the list with reviewer statistics
     */
    private List<ReviewerStatistics> createAllReviewerStatistics(long projectId, long projectCategoryId,
        long primaryReviewerId, List<Long> secondaryReviewerIds) {
        int secondaryReviewerNum = secondaryReviewerIds.size();

        // Create a list for calculated reviewer statistics:
        List<ReviewerStatistics> allReviewerStatistics = new ArrayList<ReviewerStatistics>();
        for (int i = 0; i <= secondaryReviewerNum; i++) {
            // Create reviewer statistics instance
            ReviewerStatistics reviewerStatistics = new ReviewerStatistics();
            // Set statistic type to HISTORY
            reviewerStatistics.setStatisticsType(StatisticsType.HISTORY);
            // Set project ID to the reviewer statistics
            reviewerStatistics.setProjectId(projectId);
            // Set competition type ID to the reviewer statistics
            reviewerStatistics.setCompetitionTypeId(projectCategoryId);

            long reviewerId;
            if (i == 0) {
                reviewerId = primaryReviewerId;
                // Set accuracy coefficient equal to -1 for primary reviewer
                reviewerStatistics.setAccuracy(-1);
                // Set coverage coefficient equal to -1 for primary reviewer
                reviewerStatistics.setCoverage(-1);
            } else {
                // Get the ID of the next secondary reviewer
                reviewerId = secondaryReviewerIds.get(i - 1);
            }
            // Set reviewer ID to the reviewer statistics
            reviewerStatistics.setReviewerId(reviewerId);
            // Add reviewer statistics to the list
            allReviewerStatistics.add(reviewerStatistics);
        }

        return allReviewerStatistics;
    }

    /**
     * Perform all calculations.
     *
     * @param projectId the project id
     * @param evaluatedReviews the reviews under evaluation
     * @param allReviewerStatistics statistics from all reviewers
     * @param primaryReviewerId the primary reviewer id
     * @param secondaryReviewerIds the secondary reviewer ids
     * @param paramList the list with all phases
     * @param finalReviewPhases the list with final review phases
     *
     * @throws StatisticsCalculatorException if any error occurs during calculation
     */
    private void doCalculations(long projectId, Review[][] evaluatedReviews,
        List<ReviewerStatistics> allReviewerStatistics, long primaryReviewerId, List<Long> secondaryReviewerIds,
        List<Phase> paramList, List<Phase> finalReviewPhases) throws StatisticsCalculatorException {
        Phase screeningPhase = paramList.get(0);
        Phase secondaryReviewerReviewPhase = paramList.get(1);
        Phase primaryReviewEvaluationPhase = paramList.get(2);
        Phase primaryReviewAppealsResponsePhase = paramList.get(3);
        Phase aggregationReviewPhase = paramList.get(4);

        // Get reviewer statistics of the primary reviewer
        ReviewerStatistics primaryReviewerStatistics = allReviewerStatistics.get(0);

        // Create a list for phases that affect reliability of the primary reviewer
        List<Phase> primaryReviewerPhases = new ArrayList<Phase>();

        // Add screening phase to the list
        primaryReviewerPhases.add(screeningPhase);

        // Add primary review evaluation phase to the list
        primaryReviewerPhases.add(primaryReviewEvaluationPhase);

        // Add primary review appeals response phase to the list
        primaryReviewerPhases.add(primaryReviewAppealsResponsePhase);

        // Add all final review phases to the list
        primaryReviewerPhases.addAll(finalReviewPhases);

        // Calculate timeline reliability for the primary reviewer
        double primaryReviewerTimelineReliablity;
        try {
            primaryReviewerTimelineReliablity = calculateReliability(primaryReviewerId, projectId,
                primaryReviewerPhases);
        } catch (StatisticsCalculatorException e) {
            throw Helper.logException(log, signature, e, "Error calculating reliability.");
        }

        // Set timeline reliability to the statistics
        primaryReviewerStatistics.setTimelineReliability(primaryReviewerTimelineReliablity);

        // Calculate coverage coefficients for all secondary reviewers
        double[] coverageValues;
        try {
            coverageValues = coverageCalculator.calculateCoverage(evaluatedReviews);
        } catch (CoverageCalculationException e) {
            throw Helper.logException(log, signature, e, "Error calculating coverage.");
        }

        // Calculate accuracy coefficients for all secondary reviewers
        double[] accuracyValues;
        try {
            accuracyValues = accuracyCalculator.calculateAccuracy(evaluatedReviews);
        } catch (AccuracyCalculationException e) {
            throw Helper.logException(log, signature, e, "Error calculating accuracy.");
        }

        // Create a list for phases that affect reliability of the secondary reviewers
        List<Phase> secondaryReviewerPhases = new ArrayList<Phase>();

        // Add secondary reviewer review phase to the list
        secondaryReviewerPhases.add(secondaryReviewerReviewPhase);

        // Add aggregation review phase to the list
        secondaryReviewerPhases.add(aggregationReviewPhase);

        int secondaryReviewerNum = secondaryReviewerIds.size();
        for (int i = 0; i < secondaryReviewerNum; i++) {
            // Get user ID of the next secondary reviewer
            long secondaryReviewerId = secondaryReviewerIds.get(i);
            // Get statistics for this reviewer from the list
            // the first statistics in the list is for the primary reviewer
            ReviewerStatistics secondaryReviewerStatistics = allReviewerStatistics.get(i + 1);

            // Calculate timeline reliability for this secondary reviewer
            double secondaryReviewerTimelineReliablity;
            try {
                secondaryReviewerTimelineReliablity = calculateReliability(secondaryReviewerId, projectId,
                    secondaryReviewerPhases);
            } catch (StatisticsCalculatorException e) {
                throw Helper.logException(log, signature, e, "Error calculating reliability.");
            }

            // Set timeline reliability to the statistics
            secondaryReviewerStatistics.setTimelineReliability(secondaryReviewerTimelineReliablity);
            // Set accuracy coefficient to the statistics
            secondaryReviewerStatistics.setAccuracy(accuracyValues[i]);
            // Set coverage coefficient to the statistics
            secondaryReviewerStatistics.setCoverage(coverageValues[i]);
        }
    }

    /**
     * Finalize calculations.
     *
     * @param allReviewerStatistics statistics from all reviewers
     * @param eligibilityPointsPool the eligibility points pool for this contest
     *
     * @throws StatisticsPostCalculationHandlerException if any error occurs during calculation.
     */
    private void finalizeCalculation(List<ReviewerStatistics> allReviewerStatistics, double eligibilityPointsPool)
        throws StatisticsPostCalculationHandlerException {
        for (ReviewerStatistics reviewerStatistics : allReviewerStatistics) {
            // Calculate and set total evaluation coefficient to the statistics
            reviewerStatistics.setTotalEvaluationCoefficient(reviewerStatistics.getTimelineReliability()
                * reviewerStatistics.getCoverage() * reviewerStatistics.getAccuracy());
        }
        // Get secondary reviewers only from the list
        List<ReviewerStatistics> secondaryReviewersStatistics = allReviewerStatistics.subList(1,
            allReviewerStatistics.size());

        // Distribute eligibility points among secondary reviewers
        distributeEligibilityPoints(secondaryReviewersStatistics, eligibilityPointsPool);

        for (StatisticsPostCalculationHandler handler : handlers) {
            for (ReviewerStatistics reviewerStatistics : allReviewerStatistics) {
                // Call registered handler passing the calculated statistics to it
                handler.handleStatisticsResult(reviewerStatistics);
            }
        }
    }

    /**
     * Distributes the given eligibility points among the secondary reviewers that have the specified statistics for
     * specific project.
     *
     * @param reviewerStatisticsList the statistics of reviewers among which eligibility points must be distributed
     * @param eligibilityPointsPool the number of eligibility points to be distributed
     */
    private static void distributeEligibilityPoints(List<ReviewerStatistics> reviewerStatisticsList,
        double eligibilityPointsPool) {
        // sum of total evaluation coefficients among all secondary reviewers
        double totalEvaluationCoefficientSum = 0;

        for (ReviewerStatistics reviewerStatistics : reviewerStatisticsList) {
            // Get total evaluation coefficient for the next reviewer
            double totalEvaluationCoefficient = reviewerStatistics.getTotalEvaluationCoefficient();
            totalEvaluationCoefficientSum += totalEvaluationCoefficient;
        }

        if (totalEvaluationCoefficientSum == 0) {
            return;
        }

        for (ReviewerStatistics reviewerStatistics : reviewerStatisticsList) {
            // Get total evaluation coefficient for the next reviewer
            double totalEvaluationCoefficient = reviewerStatistics.getTotalEvaluationCoefficient();
            // Calculate the number of eligibility points to be awarded to this reviewer
            double eligibilityPoints = eligibilityPointsPool * totalEvaluationCoefficient
                / totalEvaluationCoefficientSum;
            // Set eligibility points to ReviewerStatistics
            reviewerStatistics.setEligibilityPoints(eligibilityPoints);
        }
    }

    /**
     * Retrieves the complete deliverables for the specified project ID, phase type ID and resource IDs. The given
     * resource IDs represent the users that have some resource role in the phase with the specified type (e.g.
     * phaseTypeId can be equal to secondaryReviewerReviewPhaseTypeId, and then resourceIds will be resourceIds
     * associated with some secondary reviewer).
     *
     * @param phaseId the ID of the phase
     * @param projectId the ID of the project
     * @param resourceIds the IDs of the user resources
     *
     * @return the retrieved deliverables (not null, doesn't contain null)
     *
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     */
    private Deliverable[] getDeliverables(long projectId, long phaseId, List<Long> resourceIds)
        throws PersistenceException {
        // Create a filter that matches the project ID
        Filter projectFilter = new EqualToFilter("project_id", projectId);

        // Create a filter that matches the phase type ID ("phase_id" is not a typo here!)
        Filter phaseTypeFilter = new EqualToFilter("phase_id", phaseId);

        // Create a filter that matches the resource IDs
        Filter resourceFilter = new InFilter("resource_id", resourceIds);

        // Create a list for all created filters
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(projectFilter);
        filters.add(phaseTypeFilter);
        filters.add(resourceFilter);

        // Create filter than combines all conditions
        Filter filter = new AndFilter(filters);
        try {
            Deliverable[] result = deliverableManager.searchDeliverables(filter, true);

            return result;
        } catch (DeliverablePersistenceException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (SearchBuilderException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (DeliverableCheckingException e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    /**
     * Calculates the timeline reliability coefficient for the specified user taking the specified project phases into
     * account.
     *
     * @param userId the ID of the user
     * @param projectId the ID of the project
     * @param phases the phases to be taken into account
     *
     * @return the calculated timeline reliability coefficient (should be in the range [0 .. 1])
     *
     * @throws StatisticsCalculatorException if some other error occurred
     */
    private double calculateReliability(long userId, long projectId, List<Phase> phases)
        throws StatisticsCalculatorException {
        // Create list for deadlines of phases
        List<Date> deadlinesList = new ArrayList<Date>();
        // Create list for dates when user deliverables were committed
        List<Date> committedList = new ArrayList<Date>();
        // Create list for phase type IDs
        List<Long> phaseTypeIdList = new ArrayList<Long>();

        // Calculate the reliability using the aggregated calculator
        try {
            for (Phase phase : phases) {
                calcluateList(phase, userId, projectId, deadlinesList, committedList, phaseTypeIdList);
            }

            // Convert list to an array
            Date[] deadlines = deadlinesList.toArray(new Date[deadlinesList.size()]);
            // Convert list to an array
            Date[] committed = committedList.toArray(new Date[committedList.size()]);
            // Convert list to an array
            long[] phaseTypeIds = toLongArray(phaseTypeIdList);

            double result = timelineReliabilityCalculator.calculateReliability(deadlines, committed, phaseTypeIds);

            return result;
        } catch (NullPointerException e) {
            throw new StatisticsCalculatorException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new StatisticsCalculatorException(e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new StatisticsCalculatorException(e.getMessage(), e);
        }
    }

    /**
     * Calculate list from phases and user and project ids.
     *
     * @param phase the phase to be used
     * @param userId the ID of the user
     * @param projectId the ID of the project
     * @param deadlinesList the list for deadlines of phases
     * @param committedList the list for dates when user deliverables were committed
     * @param phaseTypeIdList the the phases to be taken into account
     *
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     */
    private void calcluateList(Phase phase, long userId, long projectId, List<Date> deadlinesList,
        List<Date> committedList, List<Long> phaseTypeIdList) throws PersistenceException {
        // Get ID of the phase
        long phaseId = phase.getId();

        // Get phase type
        PhaseType phaseType = phase.getPhaseType();

        // Get ID of the phase type
        long phaseTypeId = phaseType.getId();

        // Get scheduled start date of the phase
        Date scheduledStartDate = phase.getScheduledStartDate();

        // Get scheduled end date of the phase
        Date scheduledEndDate = phase.getScheduledEndDate();

        // Calculate the scheduled duration of the phase in milliseconds
        long phaseScheduledDuration = scheduledEndDate.getTime() - scheduledStartDate.getTime();

        // Get actual start date of the phase (can be changed because previous phases are late)
        Date actualStartDate = phase.getActualStartDate();

        // Calculate the expected phase deadline taking shift or previous phases into account
        Date deadline = new Date(actualStartDate.getTime() + phaseScheduledDuration);

        // Get resource IDs for this user and the current phase
        List<Long> userResourceIds = getReviewerResourceIds(userId, phaseId);

        // Get deliverables submitted by this user during the current phase
        Deliverable[] deliverables = getDeliverables(projectId, phaseId, userResourceIds);
        if (deliverables.length == 0) {
            if (phaseTypeId != aggregationReviewPhaseTypeId) {
                throw new PersistenceException("Deliverable cannot be empty if phase type "
                    + "is different from aggregation, projectId = " + projectId + ", phaseId = " + phaseId
                    + ", userResourceIds = " + userResourceIds + ".");
            }
            return;
        }

        Date maxCompletionDate = null;
        for (Deliverable deliverable : deliverables) {
            // Get completion date of the deliverable
            Date completionDate = deliverable.getCompletionDate();
            if (maxCompletionDate == null || completionDate.after(maxCompletionDate)) {
                maxCompletionDate = completionDate;
            }
        }

        // Add commitment deadline to the list
        deadlinesList.add(deadline);

        // Add actual commitment date to the list
        committedList.add(maxCompletionDate);

        // Add phase type ID to the list
        phaseTypeIdList.add(phaseTypeId);
    }

    /**
     * Retrieves the IDs of resources for the specified reviewer user and phase.
     *
     * @param userId the user ID of the reviewer
     * @param phaseId the ID of the phase
     *
     * @return the retrieved reviewer resource IDs (not null, doesn't contain null)
     *
     * @throws PersistenceException if some error occurred when accessing the data in persistence or data retrieved
     *             from persistence is logically incorrect
     */
    private List<Long> getReviewerResourceIds(long userId, long phaseId) throws PersistenceException {
        // Create extension property name filter (this name is the name of the property that holds user ID)
        Filter userIdPropertyFilter = ResourceFilterBuilder
            .createExtensionPropertyNameFilter("External Reference ID");

        // Create extension property value filter (the value is the user ID converted to string)
        Filter userIdValueFilter = ResourceFilterBuilder.createExtensionPropertyValueFilter(Long.toString(userId));

        // Create phase ID filter
        Filter phaseFilter = ResourceFilterBuilder.createPhaseIdFilter(phaseId);

        // Create a list for filters
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(userIdPropertyFilter);
        filters.add(userIdValueFilter);
        filters.add(phaseFilter);

        // Create a combined filter for all conditions
        Filter filter = new AndFilter(filters);

        // Search for resources using the created filter
        Resource[] resources;
        try {
            resources = resourceManager.searchResources(filter);
        } catch (SearchBuilderConfigurationException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        } catch (SearchBuilderException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        } catch (ResourcePersistenceException e) {
            throw Helper.logException(log, signature, new PersistenceException(e.getMessage(), e),
                "Error getting secondary reviewers ids");
        }

        // Create a result list
        List<Long> result = new ArrayList<Long>();
        for (Resource resource : resources) {
            long resourceId = resource.getId();
            result.add(resourceId);
        }

        return result;
    }

    /**
     * Returns an array of <code>long</code> containing all of the <code>Long</code> elements in this list in proper
     * sequence.
     *
     * @param list the list to be converted
     *
     * @return an array containing the elements of this list
     */
    private long[] toLongArray(List<Long> list) {
        long[] ret = new long[list.size()];

        int i = 0;
        for (Long e : list) {
            ret[i++] = e.intValue();
        }

        return ret;
    }
}
