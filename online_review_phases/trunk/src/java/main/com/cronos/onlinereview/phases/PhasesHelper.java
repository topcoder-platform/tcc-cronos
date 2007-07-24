/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cronos.onlinereview.autoscreening.management.ScreeningTask;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;
import com.cronos.onlinereview.phases.lookup.ResourceRoleLookupUtility;
import com.cronos.onlinereview.phases.lookup.SubmissionStatusLookupUtility;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;


/**
 * <p>A class having helper methods to perform argument validation and other phase related methods used by the
 * PhaseHandler implementations.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
final class PhasesHelper {
    /** Constant for reviewer role names to be used when searching for reviewer resources and review scorecards. */
    static final String[] REVIEWER_ROLE_NAMES = new String[] {"Reviewer", "Accuracy Reviewer",
        "Failure Reviewer", "Stress Reviewer"};

    /** constant for "Scheduled" phase status. */
    private static final String PHASE_STATUS_SCHEDULED = "Scheduled";

    /** constant for "Open" phase status. */
    private static final String PHASE_STATUS_OPEN = "Open";

    /** constant for "Closed" phase status. */
    private static final String PHASE_STATUS_CLOSED = "Closed";

    /** an array of comment types which denote that a comment is a reviewer comment.  */
    private static final String[] REVIEWER_COMMENT_TYPES = new String[] {"Comment", "Required", "Recommended"};

    /**
     * private to prevent instantiation.
     */
    private PhasesHelper() {
        //do nothing.
    }

    /**
     * Checks whether the given Object is null and throws IllegalArgumentException if yes.
     *
     * @param arg the argument to check
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException if the given Object is null
     */
    static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Checks whether the given String is null or empty.
     *
     * @param arg the String to check
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException if the given string is null or empty
     */
    static void checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * Returns true if given string is either null or empty, false otherwise.
     *
     * @param str string to check.
     *
     * @return true if given string is either null or empty, false otherwise.
     */
    static boolean isStringNullOrEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    /**
     * Helper method to retrieve a property value from given configuration namespace. If the isRequired flag is
     * true and if the property does not exist, then this method throws a ConfigurationException.
     *
     * @param namespace configuration namespace to use.
     * @param propertyName name of the property.
     * @param isRequired whether property is required.
     *
     * @return value for given property name.
     *
     * @throws ConfigurationException if namespace is unknown or if required property does not exist.
     */
    static String getPropertyValue(String namespace, String propertyName, boolean isRequired)
        throws ConfigurationException {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            String value = configManager.getString(namespace, propertyName);

            if (isRequired && isStringNullOrEmpty(value)) {
                throw new ConfigurationException("Property '" + propertyName + "' must have a value.");
            }

            return value;
        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException("Namespace '" + namespace + "' does not exist.", ex);
        }
    }

    /**
     * Helper method to retrieve a property value from given configuration namespace. If the property does not
     * exist, then the default value is returned.
     *
     * @param namespace configuration namespace to use.
     * @param propertyName name of the property.
     * @param defaultValue value to be used in case not specified by user.
     *
     * @return value for given property name, or defaultValue if not specified.
     *
     * @throws ConfigurationException if namespace is unknown.
     */
    static String getPropertyValue(String namespace, String propertyName, String defaultValue)
        throws ConfigurationException {
        String value = getPropertyValue(namespace, propertyName, false);

        if (isStringNullOrEmpty(value)) {
            return defaultValue;
        } else {
            return value;
        }
    }

    /**
     * Returns true if the property by the given name exists in the namespace, false otherwise.
     *
     * @param namespace configuration namespace to use.
     * @param propertyName name of the property.
     *
     * @return true if the property by the given name exists in the namespace, false otherwise.
     *
     * @throws ConfigurationException if namespace is unknown.
     */
    static boolean doesPropertyExist(String namespace, String propertyName)
        throws ConfigurationException {
        try {
            ConfigManager configManager = ConfigManager.getInstance();
            Enumeration propNames = configManager.getPropertyNames(namespace);
            while (propNames.hasMoreElements()) {
                if (propNames.nextElement().equals(propertyName)) {
                    return true;
                }
            }
            return false;
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Namespace '" + namespace + "' does not exist.", e);
        }
    }

    /**
     * A helper method to create an instance of DBConnectionFactory. This method retrieves the value for
     * connection factory namespace from the given property name and namespace and uses the same to create an instance
     * of DBConnectionFactoryImpl.
     *
     * @param namespace configuration namespace to use.
     * @param connFactoryNSPropName name of property which holds connection factory namespace value.
     *
     * @return DBConnectionFactory instance.
     *
     * @throws ConfigurationException if property is missing or if there was an error when instantiating
     *         DBConnectionFactory.
     */
    static DBConnectionFactory createDBConnectionFactory(String namespace, String connFactoryNSPropName)
        throws ConfigurationException {
        String connectionFactoryNS = getPropertyValue(namespace, connFactoryNSPropName, true);

        try {
            return new DBConnectionFactoryImpl(connectionFactoryNS);
        } catch (UnknownConnectionException ex) {
            throw new ConfigurationException("Could not instantiate DBConnectionFactoryImpl", ex);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException ex) {
            throw new ConfigurationException("Could not instantiate DBConnectionFactoryImpl", ex);
        }
    }

    /**
     * Verifies that the phase is of desired type. Throws PhaseNotSupportedException if not.
     *
     * @param phase phase to check.
     * @param type desired phase type.
     *
     * @throws PhaseNotSupportedException if phase is not of desired type.
     */
    static void checkPhaseType(Phase phase, String type)
        throws PhaseNotSupportedException {
        String givenPhaseType = phase.getPhaseType().getName();
        if (!type.equals(givenPhaseType)) {
            throw new PhaseNotSupportedException("Phase must be of type " + type
                    + ". It is of type " + givenPhaseType);
        }
    }

    /**
     * Returns true if phase status is "Scheduled", false if status is "Open" and throws PhaseHandlingException
     * for any other status value.
     *
     * @param phaseStatus the phase status.
     *
     * @return true if phase status is "Scheduled", false if status is "Open".
     *
     * @throws PhaseHandlingException if phase status is neither "Scheduled" nor "Open".
     */
    static boolean checkPhaseStatus(PhaseStatus phaseStatus)
        throws PhaseHandlingException {
        checkNull(phaseStatus, "phaseStatus");

        if (isPhaseToStart(phaseStatus)) {
            return true;
        } else if (isPhaseToEnd(phaseStatus)) {
            return false;
        } else {
            throw new PhaseHandlingException("Phase status '" + phaseStatus.getName() + "' is not valid.");
        }
    }

    /**
     * Returns whether the phase is to end or not by checking if status is "Scheduled".
     *
     * @param status the phase status.
     *
     * @return true if status is "Scheduled", false otherwise.
     */
    static boolean isPhaseToStart(PhaseStatus status) {
        return (PHASE_STATUS_SCHEDULED.equals(status.getName()));
    }

    /**
     * Returns whether the phase is to end or not by checking if status is "Open".
     *
     * @param status the phase status.
     *
     * @return true if status is "Open", false otherwise.
     */
    static boolean isPhaseToEnd(PhaseStatus status) {
        return isPhaseOpen(status);
    }

    /**
     * Returns if phase is closed, i.e. has status "Closed".
     *
     * @param status the phase status.
     *
     * @return true if phase status is "Closed", false otherwise.
     */
    static boolean isPhaseClosed(PhaseStatus status) {
        return (PHASE_STATUS_CLOSED.equals(status.getName()));
    }

    /**
     * Returns if phase has started, i.e. has status "Open".
     *
     * @param status the phase status.
     *
     * @return true if phase status is "Closed", false otherwise.
     */
    static boolean isPhaseOpen(PhaseStatus status) {
        return (PHASE_STATUS_OPEN.equals(status.getName()));
    }

    /**
     * Returns true if all the dependencies of the given phase have started/stopped based on the type of dependency,
     * or if the phase has no dependencies.
     *
     * @param phase the phase to check.
     * @param bPhaseStarting true if phase is starting, false if phase is ending.
     *
     * @return true if all the dependencies of the given phase have stopped or if the phase has no dependencies.
     */
    static boolean arePhaseDependenciesMet(Phase phase, boolean bPhaseStarting) {
        Dependency[] dependencies = phase.getAllDependencies();

        if ((dependencies == null) || (dependencies.length == 0)) {
            return true;
        }

        for (int i = 0; i < dependencies.length; i++) {
            //get the dependency phase.
            Phase dependency = dependencies[i].getDependency();

            if (bPhaseStarting) {
                if (dependencies[i].isDependencyStart() && dependencies[i].isDependentStart()) {
                    //S2S dependencies should be started
                    if (!isPhaseOpen(dependency.getPhaseStatus())) {
                        return false;
                    }
                } else if (!dependencies[i].isDependencyStart() && dependencies[i].isDependentStart()) {
                    //S2F dependencies should be closed
                    if (!isPhaseClosed(dependency.getPhaseStatus())) {
                        return false;
                    }
                }
            } else {
                if (dependencies[i].isDependencyStart() && !dependencies[i].isDependentStart()) {
                    //F2S dependencies should be started
                    if (!isPhaseOpen(dependency.getPhaseStatus())) {
                        return false;
                    }
                } else if (!dependencies[i].isDependencyStart() && !dependencies[i].isDependentStart()) {
                    //F2F dependencies should be closed
                    if (!isPhaseClosed(dependency.getPhaseStatus())) {
                        return false;
                    }
                }
            }
        }

        //all are met.
        return true;
    }

    /**
     * Returns true if current time is later or equal to the start time of the given phase. This will return
     * true in case phase.calcStartDate() returns null.
     *
     * @param phase the phase to check.
     *
     * @return true if current time is later or equal to the start time of the given phase.
     */
    static boolean reachedPhaseStartTime(Phase phase) {
        Date startDate = phase.calcStartDate();

        if (startDate == null) {
            return true;
        } else {
            return (!new Date().before(phase.calcStartDate()));
        }
    }

    /**
     * Returns true if current time is later or equal to the end time of the given phase.
     *
     * @param phase the phase to check.
     *
     * @return true if current time is later or equal to the end time of the given phase.
     */
    static boolean reachedPhaseEndTime(Phase phase) {
        return (!new Date().before(phase.calcEndDate()));
    }

    /**
     * This method is used to check if a phase can start. It checks for following:<br/>
     * 1. if phase dependencies have been met.<br/>
     * 2. if start time has been reached.<br/>
     * The method will return true only if both the conditions are true, false otherwise.
     *
     * @param phase the phase instance to start.
     *
     * @return true if a phase can start, false otherwise.
     */
    static boolean canPhaseStart(Phase phase) {
        return (PhasesHelper.arePhaseDependenciesMet(phase, true)
            && PhasesHelper.reachedPhaseStartTime(phase));
    }

    /**
     * Helper method to close the connection. Throws PhaseHandlingException if connection could not be closed.
     *
     * @param conn connection to close.
     *
     * @throws PhaseHandlingException if connection couldnot be closed.
     */
    static void closeConnection(Connection conn) throws PhaseHandlingException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new PhaseHandlingException("Could not close connection", ex);
            }
        }
    }

    /**
     * Helper method to find a backward phase or forward phase from a given phase with given phase type.
     *
     * @param phase phase to search from.
     * @param phaseType phase type to search.
     * @param forward true to search forwards, false to search backwards.
     * @param required whether the target phase is required.
     *
     * @return nearest backward or forward phase.
     *
     * @throws PhaseHandlingException if no such phase exists.
     */
    static Phase locatePhase(Phase phase, String phaseType, boolean forward, boolean required)
        throws PhaseHandlingException {
        //get all phases for the project
        Phase[] phases = phase.getProject().getAllPhases();
        int currentPhaseIndex = 0;

        for (int i = 0; i < phases.length; i++) {
            if (phase.getId() == phases[i].getId()) {
                currentPhaseIndex = i;

                break;
            }
        }

        if (forward) {
            //get the next phase with desired type
            for (int i = currentPhaseIndex + 1; i < phases.length; i++) {
                if (phaseType.equals(phases[i].getPhaseType().getName())) {
                    return phases[i];
                }
            }
        } else {
            //get the previous phase with desired type
            for (int i = currentPhaseIndex - 1; i >= 0; i--) {
                if (phaseType.equals(phases[i].getPhaseType().getName())) {
                    return phases[i];
                }
            }
        }

        //could not find phase with desired type...
        if (required) {
            throw new PhaseHandlingException("Could not find nearest phase of type " + phaseType);
        } else {
            return null;
        }
    }

    /**
     * Returns all the reviews for a phase based on resource role names.
     *
     * @param conn Connection to use to lookup resource role id.
     * @param managerHelper ManagerHelper instance.
     * @param phaseId phase id to be used as filter.
     * @param resourceRoleNames resource role names to be used as filter.
     * @param submissionId submission id to be used as filter when searching for reviews.
     *
     * @return Review[] which match filter conditions.
     *
     * @throws PhaseHandlingException if there was an error during retrieval.
     * @throws SQLException in case of error when looking up resource role id.
     */
    static Review[] searchReviewsForResourceRoles(Connection conn, ManagerHelper managerHelper, long phaseId,
        String[] resourceRoleNames, Long submissionId)
            throws PhaseHandlingException, SQLException {
        try {
            //look up ids for resource role names
            List resourceRoleIds = new ArrayList();
            for (int i = 0; i < resourceRoleNames.length; i++) {
                resourceRoleIds.add(new Long(ResourceRoleLookupUtility.lookUpId(conn, resourceRoleNames[i])));
            }
            Filter resourceRoleFilter = SearchBundle.buildInFilter(ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME,
                resourceRoleIds);
            Filter phaseIdFilter = ResourceFilterBuilder.createPhaseIdFilter(phaseId);
            Filter fullFilter = SearchBundle.buildAndFilter(resourceRoleFilter, phaseIdFilter);
            Resource[] reviewers = managerHelper.getResourceManager().searchResources(fullFilter);
            if (reviewers.length == 0) {
                return new Review[0];
            }

            //create reviewer ids array
            Long[] reviewerIds = new Long[reviewers.length];

            for (int i = 0; i < reviewers.length; i++) {
                reviewerIds[i] = new Long(reviewers[i].getId());
            }

            Filter reviewFilter = SearchBundle.buildInFilter("reviewer", Arrays.asList(reviewerIds));
            Filter fullReviewFilter = reviewFilter;
            //if submission id filter is given, add it as filter condition
            if (submissionId != null) {
                Filter submissionFilter = SearchBundle.buildEqualToFilter("submission", submissionId);
                fullReviewFilter = SearchBundle.buildAndFilter(reviewFilter, submissionFilter);
            }

            return managerHelper.getReviewManager().searchReviews(fullReviewFilter, true);
        } catch (SearchBuilderConfigurationException e) {
            throw new PhaseHandlingException("Problem with search builder configuration", e);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("Problem with resource retrieval", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem with search builder", e);
        } catch (ReviewManagementException e) {
            throw new PhaseHandlingException("Problem with review retrieval", e);
        }
    }

    /**
     * Gets the scorecard minimum score from the given review.
     *
     * @param scorecardManager ScorecardManager instance.
     * @param review Review instance.
     *
     * @return the scorecard minimum score from the given review.
     *
     * @throws PhaseHandlingException if a problem occurs when retrieving scorecard.
     */
    static float getScorecardMinimumScore(ScorecardManager scorecardManager, Review review)
        throws PhaseHandlingException {
        long scorecardId = review.getScorecard();

        try {
            Scorecard[] scoreCards = scorecardManager.getScorecards(new long[]{scorecardId}, false);
            if (scoreCards.length == 0) {
                throw new PhaseHandlingException("No scorecards found for scorecard id: " + scorecardId);
            }
            Scorecard scoreCard = scoreCards[0];

            return scoreCard.getMinScore();
        } catch (PersistenceException e) {
            throw new PhaseHandlingException("Problem with scorecard retrieval", e);
        }
    }

    /**
     * retrieves all the submissions for the given project id.
     *
     * @param uploadManager UploadManager instance to use for searching.
     * @param projectId project id.
     *
     * @return submissions for the given project id.
     *
     * @throws PhaseHandlingException if an error occurs during retrieval.
     */
    static Submission[] searchSubmissionsForProject(UploadManager uploadManager, long projectId)
        throws PhaseHandlingException {
        Filter submissionFilter = SubmissionFilterBuilder.createProjectIdFilter(projectId);

        try {
            return uploadManager.searchSubmissions(submissionFilter);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("There was a submission retrieval error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a search builder error", e);
        }
    }

    /**
     * searches for resources based on resource role names and phase id filters.
     *
     * @param managerHelper ManagerHelper instance.
     * @param conn connection to connect to db with.
     * @param resourceRoleNames array of resource role names.
     * @param phaseId phase id.
     *
     * @return Resource[] which match search criteria.
     *
     * @throws PhaseHandlingException if an error occurs during retrieval.
     */
    static Resource[] searchResourcesForRoleNames(ManagerHelper managerHelper, Connection conn,
        String[] resourceRoleNames, long phaseId) throws PhaseHandlingException {
        List resourceRoleIds = new ArrayList();

        try {
            for (int i = 0; i < resourceRoleNames.length; i++) {
                resourceRoleIds.add(new Long(ResourceRoleLookupUtility.lookUpId(conn, resourceRoleNames[i])));
            }

            Filter resourceRoleFilter = SearchBundle.buildInFilter(
                ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, resourceRoleIds);
            Filter phaseIdFilter = ResourceFilterBuilder.createPhaseIdFilter(phaseId);
            Filter fullFilter = SearchBundle.buildAndFilter(resourceRoleFilter, phaseIdFilter);

            return managerHelper.getResourceManager().searchResources(fullFilter);
        } catch (SQLException e) {
            throw new PhaseHandlingException("There was a database connection error", e);
        } catch (SearchBuilderConfigurationException e) {
            throw new PhaseHandlingException("Problem with search builder configuration", e);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("There was a resource retrieval error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem with search builder", e);
        }
    }

    /**
     * A utility method to get the integer value for the given phase attribute. This method throws
     * PhaseHandlingException if the attribute value is null or could not be parsed into an integer.
     *
     * @param phase phase instance.
     * @param attrName name of attribute.
     *
     * @return integer value for the given phase attribute.
     *
     * @throws PhaseHandlingException if the attribute value is null or could not be parsed into an integer.
     */
    static int getIntegerAttribute(Phase phase, String attrName)
        throws PhaseHandlingException {
        String sValue = (String) phase.getAttribute(attrName);

        if (sValue == null) {
            throw new PhaseHandlingException("Phase attribute '" + attrName + "' was null.");
        }

        try {
            return Integer.parseInt(sValue);
        } catch (NumberFormatException e) {
            throw new PhaseHandlingException("Phase attribute '" + attrName + "' was non-integer:" + sValue, e);
        }
    }

    /**
     * Returns whether the screening is of manual type by checking the "Manual Screening" phase attribute.
     *
     * @param phase the phase instance.
     *
     * @return true if screening is of manual type, false otherwise.
     */
    static boolean isScreeningManual(Phase phase) {
        String screeningType = (String) phase.getAttribute("Manual Screening");

        return ((screeningType != null) && screeningType.equals("Yes"));
    }

    /**
     * Helper method to get all the screening tasks for the project.
     *
     * @param managerHelper ManagerHelper intance.
     * @param phase phase instance.
     *
     * @return ScreeningTask[] array that meet search criteria.
     *
     * @throws PhaseHandlingException in case of error when retrieving data.
     */
    static ScreeningTask[] getScreeningTasks(ManagerHelper managerHelper, Phase phase)
        throws PhaseHandlingException {
        try {
            
            //get the submissions for the project
            Submission[] submissions = searchSubmissionsForProject(managerHelper.getUploadManager(),
                phase.getProject().getId());
            //System.out.println("Getting Screening Tasks for project - " + phase.getProject().getId());
            //System.out.print("Submission IDs : [");
            //get upload ids for all submissions
            long[] uploadIds = new long[submissions.length];
            for (int i = 0; i < submissions.length; i++) {
                uploadIds[i] = submissions[i].getUpload().getId();
                //System.out.print(submissions[i].getId() + ":" + uploadIds[i] + ", ");
            }
            //System.out.println("]");
            //get screening tasks for the upload ids
            if (uploadIds.length == 0) {
                return new ScreeningTask[]{};
            } else {
                return managerHelper.getScreeningManager().getScreeningTasks(uploadIds);
            }
        } catch (ScreeningTaskDoesNotExistException e) {
            throw new PhaseHandlingException("There was a screening retrieval error", e);
        } catch (com.cronos.onlinereview.autoscreening.management.PersistenceException e) {
            throw new PhaseHandlingException("There was a screening retrieval error", e);
        }
    }

    /**
     * utility method to get a SubmissionStatus object for the given status name.
     *
     * @param uploadManager UploadManager instance used to search for submission status.
     * @param statusName submission status name.
     *
     * @return a SubmissionStatus object for the given status name.
     *
     * @throws PhaseHandlingException if submission status could not be found.
     */
    static SubmissionStatus getSubmissionStatus(UploadManager uploadManager, String statusName)
        throws PhaseHandlingException {
        SubmissionStatus[] statuses = null;
        try {
            statuses = uploadManager.getAllSubmissionStatuses();
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Error finding submission status with name: " + statusName, e);
        }
        for (int i = 0; i < statuses.length; i++) {
            if (statusName.equals(statuses[i].getName())) {
                return statuses[i];
            }
        }
        throw new PhaseHandlingException("Could not find submission status with name: " + statusName);
    }

    /**
     * retrieves all active submissions for the given project id.
     *
     * @param uploadManager UploadManager instance to use for searching.
     * @param conn the connection.
     * @param projectId project id.
     *
     * @return all active submissions for the given project id.
     *
     * @throws PhaseHandlingException if an error occurs during retrieval.
     * @throws SQLException if an error occured when looking up id.
     */
    static Submission[] searchActiveSubmissions(UploadManager uploadManager, Connection conn, long projectId)
        throws PhaseHandlingException, SQLException {
        //first get submission status id for "Active" status
        long activeStatusId = SubmissionStatusLookupUtility.lookUpId(conn, "Active");
        //then search for submissions
        Filter projectIdFilter = SubmissionFilterBuilder.createProjectIdFilter(projectId);
        Filter submissionActiveStatusFilter = SubmissionFilterBuilder.createSubmissionStatusIdFilter(activeStatusId);
        Filter fullFilter = SearchBundle.buildAndFilter(projectIdFilter, submissionActiveStatusFilter);
        try {
            return uploadManager.searchSubmissions(fullFilter);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("There was a submission retrieval error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a search builder error", e);
        }
    }

    /**
     * retrieves all active/failed screening submissions for the given project id.
     *
     * @param uploadManager UploadManager instance to use for searching.
     * @param conn the connection.
     * @param projectId project id.
     *
     * @return all active submissions for the given project id.
     *
     * @throws PhaseHandlingException if an error occurs during retrieval.
     * @throws SQLException if an error occured when looking up id.
     */
    static Submission[] searchAllUndeletedSubmissions(UploadManager uploadManager, Connection conn, long projectId)
        throws PhaseHandlingException, SQLException {
        // OrChange - Modified to take all submissions for the placement calculation
        //first get submission status id for "Active" status
        long activeStatusId = SubmissionStatusLookupUtility.lookUpId(conn, "Active");
        long failedScreening = SubmissionStatusLookupUtility.lookUpId(conn, "Failed Screening");
        //then search for submissions
        Filter projectIdFilter = SubmissionFilterBuilder.createProjectIdFilter(projectId);
        Filter submissionActiveStatusFilter = SubmissionFilterBuilder.createSubmissionStatusIdFilter(activeStatusId);
        Filter submissionFailedScreeningStatusFilter = SubmissionFilterBuilder.createSubmissionStatusIdFilter(failedScreening);
        // build the status OR filter
        Filter submissionStatusFilter = SearchBundle.buildOrFilter(submissionActiveStatusFilter, submissionFailedScreeningStatusFilter);
        Filter fullFilter = SearchBundle.buildAndFilter(projectIdFilter, submissionStatusFilter);

        try {
            return uploadManager.searchSubmissions(fullFilter);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("There was a submission retrieval error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a search builder error", e);
        }
    }

    /**
     * This method checks if the winning submission has one aggregated review scorecard committed and returns
     * the same.
     *
     * @param managerHelper ManagerHelper instance.
     * @param aggPhaseId aggregation phase id.
     *
     * @return aggregated review scorecard, null if does not exist.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving data or if there are multiple scorecards.
     * @throws SQLException if an error occurs when looking up resource role id.
     */
    static Review getAggregationWorksheet(Connection conn, ManagerHelper managerHelper, long aggPhaseId)
        throws PhaseHandlingException, SQLException {
        //Search the aggregated review scorecard
        Review[] reviews = searchReviewsForResourceRoles(conn, managerHelper, aggPhaseId,
            new String[] {"Aggregator"}, null);

        if (reviews.length == 0) {
            return null;
        } else if (reviews.length == 1) {
            return reviews[0];
        } else {
            throw new PhaseHandlingException("Cannot have multiple aggregation scorecards.");
        }
    }

    /**
     * returns the final review worksheet for the given final review phase id.
     *
     * @param managerHelper ManagerHelper instance.
     * @param finalReviewPhaseId final review phase id.
     *
     * @return the final review worksheet, or null if not existing.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving data.
     * @throws SQLException if an error occurs when looking up resource role id.
     */
    static Review getFinalReviewWorksheet(Connection conn, ManagerHelper managerHelper, long finalReviewPhaseId)
        throws PhaseHandlingException, SQLException {
        Review[] reviews = searchReviewsForResourceRoles(conn, managerHelper, finalReviewPhaseId,
                new String[] { "Final Reviewer" }, null);

        if (reviews.length == 0) {
            return null;
        } else if (reviews.length == 1) {
            return reviews[0];
        } else {
            throw new PhaseHandlingException("Multiple final review worksheets found");
        }
    }

    /**
     * utility method to create a PhaseType instance with given name.
     *
     * @param phaseManager PhaseManager instance used to search for submission status.
     * @param typeName phase type name.
     *
     * @return PhaseType instance.
     *
     * @throws PhaseHandlingException if phase status could not be found.
     */
    static PhaseType getPhaseType(PhaseManager phaseManager, String typeName)
        throws PhaseHandlingException {
        PhaseType[] types = null;
        try {
            types = phaseManager.getAllPhaseTypes();
        } catch (PhaseManagementException e) {
            throw new PhaseHandlingException("Error finding phase type with name: " + typeName, e);
        }
        for (int i = 0; i < types.length; i++) {
            if (typeName.equals(types[i].getName())) {
                return types[i];
            }
        }
        throw new PhaseHandlingException("Could not find phase type with name: " + typeName);
    }

    /**
     * utility method to create a PhaseStatus instance with given name.
     *
     * @param phaseManager PhaseManager instance used to search for submission status.
     * @param statusName phase status name.
     *
     * @return PhaseStatus instance.
     *
     * @throws PhaseHandlingException if phase status could not be found.
     */
    static PhaseStatus getPhaseStatus(PhaseManager phaseManager, String statusName)
        throws PhaseHandlingException {
        PhaseStatus[] statuses = null;
        try {
            statuses = phaseManager.getAllPhaseStatuses();
        } catch (PhaseManagementException e) {
            throw new PhaseHandlingException("Error finding phase status with name: " + statusName, e);
        }
        for (int i = 0; i < statuses.length; i++) {
            if (statusName.equals(statuses[i].getName())) {
                return statuses[i];
            }
        }
        throw new PhaseHandlingException("Could not find phase status with name: " + statusName);
    }

    /**
     * utility method to create a CommentType instance with given name.
     *
     * @param reviewManager ReviewManager instance used to search for comment type.
     * @param typeName comment type name.
     *
     * @return CommentType instance.
     *
     * @throws PhaseHandlingException if comment type could not be found.
     */
    static CommentType getCommentType(ReviewManager reviewManager, String typeName)
        throws PhaseHandlingException {
        CommentType[] types = null;
        try {
            types = reviewManager.getAllCommentTypes();
        } catch (ReviewManagementException e) {
            throw new PhaseHandlingException("Error finding comment type with name: " + typeName, e);
        }
        for (int i = 0; i < types.length; i++) {
            if (typeName.equals(types[i].getName())) {
                return types[i];
            }
        }
        throw new PhaseHandlingException("Could not find comment type with name: " + typeName);
    }
    
    /**
     * utility method to create a UploadStatus instance with given name.
     *
     * @param uploadManager UploadManager instance used to search for submission status.
     * @param statusName upload status name.
     *
     * @return UploadStatus instance.
     *
     * @throws PhaseHandlingException if submission status could not be found.
     */
    static UploadStatus getUploadStatus(UploadManager uploadManager, String statusName)
        throws PhaseHandlingException {
        UploadStatus[] statuses = null;
        try {
            statuses = uploadManager.getAllUploadStatuses();
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Error finding upload status with name: " + statusName, e);
        }
        for (int i = 0; i < statuses.length; i++) {
            if (statusName.equals(statuses[i].getName())) {
                return statuses[i];
            }
        }
        throw new PhaseHandlingException("Could not find upload status with name: " + statusName);
    }

    /**
     * Returns the winning submitter for the given project id.
     *
     * @param resourceManager ResourceManager instance.
     * @param projectManager {@link ProjectManager} instance
     * @param conn connection to connect to db with.
     * @param projectId project id.
     *
     * @return the winning submiter Resource.
     *
     * @throws SQLException if an error occurs when connecting to db.
     * @throws PhaseHandlingException if an error occurs when searching for resource.
     */
    static Resource getWinningSubmitter(ResourceManager resourceManager, ProjectManager projectManager, Connection conn, long projectId)
        	throws PhaseHandlingException {
        try {
        	com.topcoder.management.project.Project project = projectManager.getProject(projectId);
        	String winnerId = (String) project.getProperty("Winner External Reference ID");
        	if (winnerId != null) {
        		long submitterRoleId = ResourceRoleLookupUtility.lookUpId(conn, "Submitter");
                ResourceFilterBuilder.createExtensionPropertyNameFilter("External Reference ID");
                
                AndFilter fullFilter = new AndFilter(Arrays.asList(new Filter[] {
                		ResourceFilterBuilder.createResourceRoleIdFilter(submitterRoleId), 
                		ResourceFilterBuilder.createProjectIdFilter(projectId),
                		ResourceFilterBuilder.createExtensionPropertyNameFilter("External Reference ID"),
                		ResourceFilterBuilder.createExtensionPropertyValueFilter(winnerId)
                	}));
                		

                Resource[] submitters = resourceManager.searchResources(fullFilter);
                if (submitters.length > 0) {
                	return submitters[0];
                }
        		return null;
        	}
            return null;
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("Problem when retrieving resource", e);
        } catch (com.topcoder.management.project.PersistenceException e) {
        	throw new PhaseHandlingException("Problem retrieving project id: " + projectId, e);
		} catch (SQLException e) {
			throw new PhaseHandlingException("Problem when looking up id", e);
		} catch (SearchBuilderConfigurationException e) {
			throw new PhaseHandlingException("Problem with search builder configuration", e);
		} catch (SearchBuilderException e) {
			throw new PhaseHandlingException("Problem with search builder", e);
		}
    }

    /**
     * <p>
     * Helper method to add new phases of given type to the given project. This method does the following:
     * <ol>
     * <li>finds the index of given phase in the current phases array of the project.</li>
     * <li>finds the lengths of current phases of the given types.</li>
     * <li>creates new Phase instance with given type and status</li>
     * <li>creates a new Phases array with additional elements for new phase instances.</li>
     * <li>removes all phases of the project.</li>
     * <li>adds each Phase from the new Phases array to the project.</li>
     * </ol>
     * </p>
     *
     * @param currentPrj project to add/remove phases from.
     * @param currentPhase current phase instance.
     * @param newPhaseTypes types of new phases to create.
     * @param newPhaseStatus the status to set for all the phases.
     *
     * @return returns the index of the current phase in the phases array.
     *
     * @throws PhaseManagementException if there was a problem persisting the phases.
     */
    static int createNewPhases(Project currentPrj, Phase currentPhase, PhaseType[] newPhaseTypes,
            PhaseStatus newPhaseStatus, PhaseManager phaseManager, String operator) {
        //find current phase index and also the lengths of aggregation and aggregation review phases.
        Phase[] phases = currentPrj.getAllPhases();
        int currentPhaseIndex = 0;
        long newPhaseLengths[] = new long[newPhaseTypes.length];

        for (int i = 0; i < phases.length; i++) {
            if (phases[i].getId() == currentPhase.getId()) {
                currentPhaseIndex = i;
            }

            //find the lengths of corresponding phase types
            for (int p = 0; p < newPhaseTypes.length; p++) {
                if (phases[i].getPhaseType().getId() == newPhaseTypes[p].getId()) {
                    newPhaseLengths[p] = phases[i].getLength();
                }
            }
        }

        //create new phases array which will hold the new phase order
        Phase[] newPhases = new Phase[phases.length + newPhaseTypes.length];

        //set the old phases into the new phases array
        for (int i = 0; i < phases.length; i++) {
            if (i > currentPhaseIndex) {
                newPhases[i + newPhaseTypes.length] = phases[i];
            } else {
                newPhases[i] = phases[i];
            }
        }

        //create new phases for each phase type...
        for (int p = 0; p < newPhaseTypes.length; p++) {
            Phase newPhase = new Phase(currentPrj, newPhaseLengths[p]);
            newPhase.setPhaseStatus(newPhaseStatus);
            newPhase.setPhaseType(newPhaseTypes[p]);

            //the new phase is dependent on the earlier phase
            newPhase.addDependency(new Dependency(newPhases[currentPhaseIndex + p], newPhase, false, true, 0));

            newPhases[currentPhaseIndex + (p + 1)] = newPhase;
        }

        //if there was a phase after the new phases, change the dependencies of that phase
        //to move to last new phase.
        if (newPhases.length > currentPhaseIndex + newPhaseTypes.length + 1) {
            Phase afterPhase = newPhases[currentPhaseIndex + newPhaseTypes.length + 1];
            Phase lastNewPhase = newPhases[currentPhaseIndex + newPhaseTypes.length];

            Dependency[] dependencies = afterPhase.getAllDependencies();
            for (int d = 0; d < dependencies.length; d++) {
                Dependency dependency = dependencies[d];
                dependency.getDependent().removeDependency(dependency);
                dependency.getDependent().addDependency(new Dependency(lastNewPhase,
                        dependency.getDependent(), dependency.isDependencyStart(), dependency.isDependentStart(),
                        dependency.getLagTime()));
            }
        }

        //add the new phases to the project
        for (int p = 0; p < newPhaseTypes.length; p++) {
            Phase newPhase = newPhases[currentPhaseIndex + (p + 1)];
            currentPrj.addPhase(newPhase);
        }

        //set the scheduled start and end times after dependencies are changed
        for (int p = 0; p < newPhases.length; p++) {
            Phase phase = newPhases[p];
            phase.setScheduledStartDate(phase.calcStartDate());
            phase.setScheduledEndDate(phase.calcEndDate());
        }

        return currentPhaseIndex;
    }

    /**
     * Creates an aggregator or Final Reviewer resource. This method is called when a new aggregation/review or new
     * finalfix/review cycle is inserted when aggregation of final review worksheet is rejected. It simply copies the
     * old aggregator/final reviewer properties, except for the id and phase id and inserts the new resource
     * in the database.
     *
     * @param oldPhase the aggregation or final review phase instance.
     * @param managerHelper ManagerHelper instance.
     * @param conn connection to use.
     * @param roleName "Aggregator" or "Final Reviewer".
     * @param newPhaseId the new phase id the new resource is to be associated with.
     * @param operator operator name.
     *
     * @return the id of the newly created resource.
     *
     * @throws PhaseHandlingException
     */
    static long createAggregatorOrFinalReviewer(Phase oldPhase, ManagerHelper managerHelper,
            Connection conn, String roleName, long newPhaseId, String operator) throws PhaseHandlingException {

        //search for the old "Aggregator" or "Final Reviewer" resource
        Resource[] resources = PhasesHelper.searchResourcesForRoleNames(managerHelper, conn,
                new String[] { roleName }, oldPhase.getId());
        Resource oldResource = resources[0];

        //copy resource properties
        Resource newResource = new Resource();
        newResource.setProject(oldResource.getProject());
        newResource.setResourceRole(oldResource.getResourceRole());
        // OrChange - modified to set the submissions
        newResource.setSubmissions(oldResource.getSubmissions());
        Map properties = oldResource.getAllProperties();
        if (properties != null && !properties.isEmpty()) {
            Set entries = properties.entrySet();
            for (Iterator itr = entries.iterator(); itr.hasNext();) {
                Map.Entry entry = (Map.Entry) itr.next();
                newResource.setProperty((String) entry.getKey(), entry.getValue());
            }
        }

        //set phase id
        newResource.setPhase(new Long(newPhaseId));

        //update resource into persistence.
        try {
            managerHelper.getResourceManager().updateResource(newResource, operator);
            return newResource.getId();
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("Problem when persisting resource with role:" + roleName, e);
        }
    }

    /**
     * copies the comments from one worksheet to another. Which comments are copied are determined by the
     * typesToCopy and extraInfoToCheck parameters.
     *
     * @param fromWorkSheet source worksheet for the comments.
     * @param toWorkSheet destination worksheet.
     * @param typesToCopy types of comments to copy.
     * @param extraInfoToCheck extra info to check the comment against.
     */
    static void copyComments(Review fromWorkSheet, Review toWorkSheet, String[] typesToCopy, String extraInfoToCheck) {
        Comment[] comments = fromWorkSheet.getAllComments();
        //copy all comments with given type and extra info.
        for (int c = 0; c < comments.length; c++) {
            Comment comment = comments[c];
            if (isCommentToBeCopied(comment, typesToCopy, extraInfoToCheck)) {
                toWorkSheet.addComment(copyComment(comment));
            }
        }

    }

    /**
     * This helper method copies the review items from one worksheet to another. It will also copy
     * the comments for each review item from one worksheet to another. Which comments are copied are determined by the
     * typesToCopy.
     *
     * @param fromWorkSheet source worksheet for the comments.
     * @param toWorkSheet destination worksheet.
     * @param typesToCopy types of comments to copy.
     */
    static void copyReviewItems(Review fromWorkSheet, Review toWorkSheet, String[] typesToCopy) {
        Item[] reviewItems = fromWorkSheet.getAllItems();
        for (int r = 0; r < reviewItems.length; r++) {
            Item item = reviewItems[r];

            //create a new review item and copy all properties
            Item newItem = new Item(item.getId());
            newItem.setDocument(item.getDocument());
            newItem.setQuestion(item.getQuestion());
            newItem.setAnswer(item.getAnswer());

            //copy all comments with given type and extra info.
            Comment[] comments = item.getAllComments();
            for (int c = 0; c < comments.length; c++) {
                Comment comment = comments[c];
                if (isCommentToBeCopied(comment, typesToCopy, null)) {
                    newItem.addComment(copyComment(comment));
                }
            }

            //add the item to the destination worksheet
            toWorkSheet.addItem(newItem);
        }
    }

    /**
     * This helper method copies the review items from one worksheet to another. It will also copy
     * the comments for each review item from one worksheet to another. Only reviewer item comments which are marked
     * as "Accept" will be copied. Once such an item comment is found, the follow-up comments are copied until
     * the next reviewer item is found which is not accepted.
     *
     * @param fromWorkSheet source worksheet for the comments.
     * @param toWorkSheet destination worksheet.
     */
    static void copyFinalReviewItems(Review fromWorkSheet, Review toWorkSheet) {

        Item[] reviewItems = fromWorkSheet.getAllItems();
        for (int r = 0; r < reviewItems.length; r++) {
            Item item = reviewItems[r];

            //create a new review item and copy all properties
            Item newItem = new Item(item.getId());
            newItem.setDocument(item.getDocument());
            newItem.setQuestion(item.getQuestion());
            newItem.setAnswer(item.getAnswer());

            //copy all comments with given type and extra info.
            Comment[] comments = item.getAllComments();
            boolean copy = false;
            for (int c = 0; c < comments.length; c++) {
                Comment comment = comments[c];
                //if it is a reviewer comment
                if (isReviewerComment(comment)) {
                    //mark copy flag as true or false based on whether it is accepted.
                    copy = "Accept".equals(comment.getExtraInfo());
                }
                //if copy flag is marked true, then copy all comments.
                if (copy) {
                    newItem.addComment(copyComment(comment));
                }
            }

            //add the item to the destination worksheet
            toWorkSheet.addItem(newItem);
        }
    }

    /**
     * Deep clone a review effectly making all items new.
     *
     * @param review the review to be cloned.
     * @return the cloned review.
     */
    static Review cloneReview(Review review) {
        Review copiedReview = new Review();
        copiedReview.setAuthor(review.getAuthor());
        copiedReview.setCommitted(review.isCommitted());
        copiedReview.setScore(review.getScore());
        copiedReview.setScorecard(review.getScorecard());
        copiedReview.setSubmission(review.getSubmission());

        Comment[] comments = review.getAllComments();
        for (int i = 0; i < comments.length; ++i) {
            Comment copiedComment = new Comment();
            copiedComment.setAuthor(comments[i].getAuthor());
            copiedComment.setComment(comments[i].getComment());
            copiedComment.setCommentType(comments[i].getCommentType());
            copiedComment.setExtraInfo(comments[i].getExtraInfo());
            copiedReview.addComment(copiedComment);
        }

        Item[] items = review.getAllItems();
        for (int i = 0; i < items.length; ++i) {
            Item copiedItem = new Item();
            copiedItem.setAnswer(items[i].getAnswer());
            copiedItem.setDocument(items[i].getDocument());
            copiedItem.setQuestion(items[i].getQuestion());
            copiedReview.addItem(copiedItem);

            comments = items[i].getAllComments();
            for (int j = 0; j < comments.length; ++j) {
                Comment copiedComment = new Comment();
                copiedComment.setAuthor(comments[j].getAuthor());
                copiedComment.setComment(comments[j].getComment());
                copiedComment.setCommentType(comments[j].getCommentType());
                copiedComment.setExtraInfo(comments[j].getExtraInfo());
                copiedItem.addComment(copiedComment);
            }
        }

        return copiedReview;
    }

    /**
     * Returns true if the comment is a reviewer comment, false otherwise. The comment is said to be
     * a reviewer comment if it is one of the REVIEWER_COMMENT_TYPES elements.
     *
     * @param comment comment to check.
     *
     * @return true if the comment is a reviewer comment, false otherwise.
     */
    private static boolean isReviewerComment(Comment comment) {
        String commentType = comment.getCommentType().getName();
        for (int i = 0; i < REVIEWER_COMMENT_TYPES.length; i++) {
            if (commentType.equals(REVIEWER_COMMENT_TYPES[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a new comment which is a copy of the given comment, only with no extra info set.
     *
     * @param comment comment to be copied.
     *
     * @return a new comment which is a copy of the given comment.
     */
    private static Comment copyComment(Comment comment) {
        Comment newComment = new Comment(comment.getId());
        newComment.setAuthor(comment.getAuthor());
        newComment.setComment(comment.getComment());
        newComment.setCommentType(comment.getCommentType());
        return newComment;
    }

    /**
     * checks if the comment is to be copied i.e. is one of the comment types that have to be copied to the
     * review worksheet.
     *
     * @param comment comment to check.
     * @param typesToCopy types of comments to copy.
     * @param extraInfoToCheck extra info to check the comment against.
     *
     * @return true if it is to be copied, false otherwise.
     */
    private static boolean isCommentToBeCopied(Comment comment, String[] typesToCopy, String extraInfoToCheck) {
        String commentType = comment.getCommentType().getName();
        for (int i = 0; i < typesToCopy.length; i++) {
            //return true if it is one of the types to be copied and it is marked as "Accept"
            if (commentType.equals(typesToCopy[i])) {
                if (extraInfoToCheck != null) {
                    if (extraInfoToCheck.equals(comment.getExtraInfo())) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
