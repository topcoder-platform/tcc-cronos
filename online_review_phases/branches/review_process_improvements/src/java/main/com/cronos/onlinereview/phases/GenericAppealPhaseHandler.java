/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cronos.onlinereview.phases.lookup.ResourceRoleLookupUtility;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.project.phases.Phase;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This handler is responsible for checking whether the an appeal phase can be performed and performing the
 * phase. The appeal phase can be any phase that involves submitting appeals from the submitters and secondary
 * reviewers. Currently there are First Appeal and Second Appeal phases in the new OR system. These phases are
 * exactly the same except their phase dependency. Therefore one generic appeal phase handler is enough. It
 * extends <code>AbstractPhaseHandler</code> to leverage the various services provided by the base class.
 * Logging is done with the Logging Wrapper.
 * </p>
 * <p>
 * Usage: please see "test_files/config/Phase_Handler_1_5.xml". The namespace is
 * "com.cronos.onlinereview.phases.FirstAppealPhaseHandler".
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe because it's immutable.
 * </p>
 *
 * <p>
 * Version 1.6 (Online Review Update Review Management Process assembly 4) Change notes:
 * <ol>
 *   <li>Removed <code>secondaryReviewerCount</code> property.</li>
 *   <li>Updated {@link #canCloseAppealsEarly(ResourceManager, UploadManager, Connection, long)} method, the number of
 *   reviewer is dynamic read from database.</li>
 * </ol>
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.6
 * @since 1.5
 */
public class GenericAppealPhaseHandler extends AbstractPhaseHandler {

    /**
     * <p>
     * Represents the default configuration namespace of this class.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It is final and won't change
     * once it is initialized as part of variable declaration to:
     * "com.cronos.onlinereview.phases.GenericAppealPhaseHandler".
     * </p>
     * <p>
     * Usage: It is used in {@link #GenericAppealPhaseHandler()} (for initialization).
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.GenericAppealPhaseHandler";

    /**
     * <p>
     * The logger used for logging.
     * </p>
     * <p>
     * LegalValue: It cannot be null. Initialization and Mutability: It is final and won't change once it is
     * initialized as part of variable declaration to:
     * LogFactory.getLog(GenericAppealPhaseHandler.class.getName()).
     * </p>
     * <p>
     * Usage: It is used throughout the class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogFactory.getLog(GenericAppealPhaseHandler.class.getName());

    /**
     * <p>
     * The submitter role name.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #GenericAppealPhaseHandler()}(for initialization),
     * {@link #canCloseAppealsEarly()}.
     * </p>
     */
    private final String submitterRoleName;

    /**
     * <p>
     * The secondary reviewer role name.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #GenericAppealPhaseHandler()} (for initialization),
     * {@link #canCloseAppealsEarly()}.
     * </p>
     */
    private final String secondaryReviewerRoleName;

    /**
     * <p>
     * The type of the phase that this handler should deal with, such as "First Appeal" or "Second Appeal".
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #canPerform()}, {@link #GenericAppealPhaseHandler()} (for initialization).
     * </p>
     */
    private final String phaseType;

    /**
     * <p>
     * Create an instance of the class with the default namespace.
     * </p>
     *
     * @throws ConfigurationException
     *             if any error occurs
     */
    public GenericAppealPhaseHandler() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Create an instance of the class.
     * </p>
     *
     * @param namespace
     *            the configuration namespace.
     * @throws IllegalArgumentException
     *             if the namespace is null or empty.
     * @throws ConfigurationException
     *             if any error occurs
     */
    public GenericAppealPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);

        submitterRoleName = PhasesHelper.getPropertyValue(namespace, "submitterRoleName", true);
        secondaryReviewerRoleName = PhasesHelper.getPropertyValue(namespace, "secondaryReviewerRoleName",
            true);
        phaseType = PhasesHelper.getPropertyValue(namespace, "phaseType", true);
    }

    /**
     * <p>
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed.
     * </p>
     * <p>
     * This method will be called by <code>canStart()</code> and <code>canEnd()</code> methods of
     * <code>PhaseManager</code> implementations in Phase Management component.
     * </p>
     *
     * @param phase
     *            The input phase to check
     * @return True if the input phase can be executed, false otherwise.
     * @throws IllegalArgumentException
     *             if phase is null;
     * @throws PhaseNotSupportedException
     *             if the input phase type doesn't equal to this.phaseType
     * @throws PhaseHandlingException
     *             if there is any error occurred
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");

        try {
            PhasesHelper.checkPhaseType(phase, this.phaseType);

            // check if the phase is to start:
            boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
            if (toStart) {
                return PhasesHelper.canPhaseStart(phase);
            }

            if (!PhasesHelper.arePhaseDependenciesMet(phase, false)) {
                return false;
            }

            if (PhasesHelper.reachedPhaseEndTime(phase)) {
                return true;
            }

            Connection conn = null;

            try {
                conn = createConnection();

                // check if all submitters agreed to close appeals phase
                // early
                return canCloseAppealsEarly(getManagerHelper().getResourceManager(), getManagerHelper()
                    .getUploadManager(), conn, phase.getProject().getId());
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, null, phase.getProject().getId());
        }

    }

    /**
     * <p>
     * Returns whether all submitters and secondary reviewers agreed to early appeals phase completion.
     * </p>
     *
     * @param resourceManager
     *            ResourceManager instance.
     * @param uploadManager
     *            upload manager instance.
     * @param conn
     *            connection to connect to database.
     * @param projectId
     *            project id.
     * @return true if all submitters and secondary reviewers agreed to early
     *         appeals phase completion
     * @throws PhaseHandlingException
     *             if an error occurs when searching for resource.
     */
    private boolean canCloseAppealsEarly(ResourceManager resourceManager, UploadManager uploadManager,
        Connection conn, long projectId) throws PhaseHandlingException {
        try {
            // This block checks if all submitters have closed appeal early
            long submitterRoleId = ResourceRoleLookupUtility.lookUpId(conn, submitterRoleName);

            AndFilter fullFilter = createNewAndFilter(submitterRoleId, projectId);

            Resource[] earlyAppealCompletionsSubmitters = resourceManager.searchResources(fullFilter);

            // move resource ids to a hashset to speed up lookup
            Set<Long> earlyAppealResourceIds = new HashSet<Long>(earlyAppealCompletionsSubmitters.length);

            for (Resource r : earlyAppealCompletionsSubmitters) {
                earlyAppealResourceIds.add(r.getId());
            }

            // check all submitters with active submission statuses (this will
            // leave out failed screening and deleted)
            Submission[] activeSubmissions = PhasesHelper.searchActiveSubmissions(uploadManager, conn,
                projectId, PhasesHelper.CONTEST_SUBMISSION_TYPE);

            for (Submission s : activeSubmissions) {
                if (!earlyAppealResourceIds.contains(new Long(s.getUpload().getOwner()))) {
                    // there is one submission whose corresponding submitter or
                    // reviewer's appeal is not
                    // closed early therefore
                    return false;
                }
            }

            // This block checks if all secondary reviewers have closed appeal
            // early
            long secondaryReviewerRoleId = ResourceRoleLookupUtility
                .lookUpId(conn, secondaryReviewerRoleName);

            fullFilter = new AndFilter(Arrays.asList(new Filter[]{
                    ResourceFilterBuilder.createResourceRoleIdFilter(secondaryReviewerRoleId),
                    ResourceFilterBuilder.createProjectIdFilter(projectId)}));
            Resource[] secondaryReviewers = resourceManager.searchResources(fullFilter);
            for (Resource resource : secondaryReviewers) {
                if (!"Yes".equalsIgnoreCase(resource.getProperty("Appeals Completed Early"))) {
                    return false;
                }
            }

            return true;
        } catch (ResourcePersistenceException ex) {
            throw new PhaseHandlingException("Problem when retrieving resource.", ex);
        } catch (SQLException ex) {
            throw new PhaseHandlingException("Problem when looking up id.", ex);
        } catch (SearchBuilderException ex) {
            throw new PhaseHandlingException("Problem with search builder.", ex);
        }

    }

    /**
     * <p>
     * Provides additional logic to execute a phase. This method will be called by <code>start()</code> and
     * <code>end()</code> methods of <code>PhaseManager</code> implementations in Phase Management component.
     * </p>
     * <p>
     * This method can send email to a group of users associated with timeline notification for the project.
     * The email can be send on start phase or end phase base on configuration settings.
     * </p>
     *
     * @param phase
     *            The input phase to check.
     * @param operation
     *            The operator that execute the phase.
     * @throws IllegalArgumentException
     *             if phase is null; operation is null or empty.
     * @throws PhaseNotSupportedException
     *             if the input phase type doesn't equal to this.phaseType
     * @throws PhaseHandlingException
     *             if an error occurs when searching for resource.
     */
    public void perform(Phase phase, String operation) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operation, "operation");
        try {
            PhasesHelper.checkPhaseType(phase, this.phaseType);
            PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

            Map<String, Object> values = new HashMap<String, Object>();

            // puts the submissions info/scores into the values map
            values.put("SUBMITTER", PhasesHelper.getSubmitterValueArray(createConnection(),
                getManagerHelper(), phase.getProject().getId(), true));
            sendEmail(phase, values);
        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, operation, phase.getProject().getId());
        }
    }

    /**
     * <p>
     * Creates the new AndFilter instance.
     * </p>
     *
     * @param roleID
     *            the role id
     * @param projectId
     *            the project id
     * @return the new AndFilter instance.
     */
    private AndFilter createNewAndFilter(long roleID, long projectId) {
        return new AndFilter(Arrays.asList(new Filter[]{
            ResourceFilterBuilder.createResourceRoleIdFilter(roleID),
            ResourceFilterBuilder.createProjectIdFilter(projectId),
            ResourceFilterBuilder.createExtensionPropertyNameFilter("Appeals Completed Early"),
            ResourceFilterBuilder.createExtensionPropertyValueFilter("Yes")}));
    }

}
