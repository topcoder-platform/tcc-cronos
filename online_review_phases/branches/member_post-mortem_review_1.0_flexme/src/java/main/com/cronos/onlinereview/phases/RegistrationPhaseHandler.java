/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import com.cronos.onlinereview.phases.lookup.ResourceRoleLookupUtility;
import com.topcoder.management.phase.ContestDependencyAutomation;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a
 * phase can be executed and to add extra logic to execute a phase. It will be
 * used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection, email
 * sending and the required number of registrations. This class handle the
 * registration phase. If the input is of other phase types,
 * PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The registration phase can start whenever the dependencies are met and can
 * stop when:
 * <ul>
 * <li>The dependencies are met</li>
 * <li>The period has passed</li>
 * <li>The number of registrations meets the required number.</li>
 *  <li>The parent projects (if any) are completed.</li>
 * </ul>
 * </p>
 * <p>
 * There is no additional logic for executing this phase.
 * </p>
 * <p>
 *
 * <p>
 * Update in version 1.1.: Modify the <code>perform</code> method to add a
 * post-mortem phase where there is no registration at phase end.
 * </p>
 *
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>
 * Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager, etc).
 * </li>
 * <li>
 * Support for more information in the email generated: for stop, the number of registrant and info about registrant.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (Online Review End Of Project Analysis Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #perform(Phase, String)} method to use updated
 *     {@link PhasesHelper#insertPostMortemPhase(Project , Phase, ManagerHelper, String)} method for creating
 *     <code>Post-Mortem</code> phase.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 *
 * @author tuenm, bose_java, argolite, waits, TCSDEVELOPER
 * @version 1.2
 */
public class RegistrationPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default
     * constructor to load configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.RegistrationPhaseHandler";

    /** constant for registration phase type. */
    private static final String PHASE_TYPE_REGISTRATION = "Registration";

    /**
     * Create a new instance of RegistrationPhaseHandler using the default
     * namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading
     *         configuration settings.
     */
    public RegistrationPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of RegistrationPhaseHandler using the given
     * namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading
     *         configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public RegistrationPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check
     * the phase status to see what will be executed. This method will be called
     * by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase Management component.
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions:
     * </p>
     * - The dependencies are met
     * <p>
     * If the input phase status is Open, then it will check if the phase can be
     * stopped using the following conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>The period has passed</li>
     * <li>The number of registrations meets the required number.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.
     * </p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not
     *         "Registration" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_REGISTRATION);

        // will throw exception if phase status is neither "Scheduled" nor
        // "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        long projectId = phase.getProject().getId();
        try {
            if (toStart) {
                // version 1.3
                //return true if all dependencies have stopped and start time has been reached.
                boolean canStart = PhasesHelper.canPhaseStart(phase);
                boolean allParentProjectsCompleted
                    = PhasesHelper.areParentProjectsCompleted(projectId, getManagerHelper(), createConnection());
                if (canStart) {
                    if (allParentProjectsCompleted) {
                        return true;
                    } else {
                        // Extend phase start time with 24 hours from now to wait for parent projects completion
                        Date newScheduledStartTime = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L);
                        phase.setScheduledStartDate(newScheduledStartTime);
                        phase.setScheduledEndDate(new Date(newScheduledStartTime.getTime() + phase.getLength()));
                        phase.getProject().setStartDate(newScheduledStartTime);
                        recalculateScheduledDates(phase.getProject().getAllPhases());

                        // Adjust timelines for depending projects as well
                        PhaseManager phaseManager = getManagerHelper().getPhaseManager();
                        ContestDependencyAutomation auto
                            = new ContestDependencyAutomation(phaseManager,
                                getManagerHelper().getProjectManager(), getManagerHelper().getProjectLinkManager());
                        List<Phase[]> affectedChildProjectPhases
                            = auto.adjustDependingProjectPhases(phase.getProject().getAllPhases());
                        for (Phase[] affectedProjectPhases : affectedChildProjectPhases) {
                            phaseManager.updatePhases(affectedProjectPhases[0].getProject(), "0");
                        }

                        getManagerHelper().getPhaseManager().updatePhases(phase.getProject(), "0");
                    }
                }
                // Either project start time hasn't been reached yet or not all parent projects are completed
                return false;
            } else {
                boolean dependencyMet = PhasesHelper.arePhaseDependenciesMet(phase,
                                false);
                boolean reachedEndTime = PhasesHelper.reachedPhaseEndTime(phase);

                // version 1.1 : can stop if registration is empty
                return (dependencyMet && reachedEndTime
                                      && (areRegistrationsEnough(phase) || isRegistrationEmpty(phase, null)));
            }
        } catch (PersistenceException e) {
            throw new PhaseHandlingException("Failed to get the links to parent projects for project: " + projectId, e);
        } catch (PhaseManagementException e) {
            throw new PhaseHandlingException("Failed to get the links to parent projects for project: " + projectId, e);
        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when connecting to database", e);
        }
    }

    /**
     * Provides addtional logic to execute a phase. This method will be called
     * by start() and end() methods of PhaseManager implementations in Phase
     * Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be
     * send on start phase or end phase base on configuration settings.
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.
     * </p>
     *
     * <p>
     * Update in version 1.1: Add a new post-mortem phase if there is no
     * registration when phase ends.
     * </p>
     *
     * <p>
     * Update in version 1.2: Support for more information in the email generated,
     * for stop, the number of registrant and info about registrant.
     * </p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not
     *         "Registration" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty
     *         string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_REGISTRATION);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        Map<String, Object> values = new HashMap<String, Object>();
        if (!toStart && isRegistrationEmpty(phase, values)) {
            // if no registration, insert post-Mortem phase
            PhasesHelper.insertPostMortemPhase(phase.getProject(), phase, getManagerHelper(), operator);
        }

        sendEmail(phase, values);
    }

    /**
     * This method checks whether the registration phase has empty
     * registrations, if yes, return true.
     *
     * <p>
     * Update in version 1.2: Support for more information in the email generated,
     * for stop, the number of registrant and info about registrant.
     * </p>
     *
     * @param phase the phase to check.
     * @param values the values map to hold the information for email generation
     * @return true if there is no registrations, false otherwise.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     */
    private boolean isRegistrationEmpty(Phase phase, Map<String, Object> values) throws PhaseHandlingException {
        Resource[] resources = searchResources(phase);

        //support adding more information in the email from version 1.2
        if (values != null) {
            values.put("N_REGISTRANTS", resources.length);
            values.put("REGISTRANT", registrantInfo(resources));
        }

        if (resources.length == 0) {
            // there is no registrations, return true
            return true;
        }

        return false;
    }


    /**
     * <p>
     * Constructs the values map list for email generation. All the registrants information will be listed.
     * </p>
     * @param resources registrant list, not null
     * @return List of map values, not null, could be empty
     * @since 1.2
     */
    private List<Map<String, Object>> registrantInfo(Resource[] resources) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Resource resource : resources) {
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("REGISTRANT_HANDLE", PhasesHelper.notNullValue(resource.getProperty("Handle")));
            values.put("REGISTRANT_RELIABILITY", PhasesHelper.notNullValue(resource.getProperty("Reliability")));
            values.put("REGISTRANT_RATING", PhasesHelper.notNullValue(resource.getProperty("Rating")));
            result.add(values);
        }
        return result;
    }

    /**
     * This method checks if the number of registrations meets the required
     * number.
     *
     * @param phase the input phase to check.
     *
     * @return true if registrations are enough, false otherwise.
     *
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     */
    private boolean areRegistrationsEnough(Phase phase) throws PhaseHandlingException {
        if (phase.getAttribute("Registration Number") == null) {
            return true;
        }

        int regNumber = PhasesHelper.getIntegerAttribute(phase,
                        "Registration Number");

        Resource[] resources = searchResources(phase);
        return (regNumber <= resources.length);
    }

    /**
     * Helper method to search for resources with "Submitter" role and project
     * id filters.
     *
     * @param phase phase instance.
     *
     * @return Resource[] array.
     *
     * @throws PhaseHandlingException in case of any error while retrieving.
     */
    private Resource[] searchResources(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();
            long resourceRoleId = ResourceRoleLookupUtility.lookUpId(conn,
                            "Submitter");
            Filter roleIdFilter = ResourceFilterBuilder
                            .createResourceRoleIdFilter(resourceRoleId);
            Filter projectIdfilter = ResourceFilterBuilder
                            .createProjectIdFilter(phase.getProject().getId());
            Filter fullFilter = SearchBundle.buildAndFilter(roleIdFilter,
                            projectIdfilter);
            return getManagerHelper().getResourceManager().searchResources(
                            fullFilter);
        } catch (SearchBuilderConfigurationException e) {
            throw new PhaseHandlingException(
                            "search builder configuration error", e);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("resource persistence error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("search builder error", e);
        } catch (SQLException e) {
            throw new PhaseHandlingException(
                            "error when looking up resource id.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * <p>Recalculates scheduled start date and end date for all phases when a phase is moved.</p>
     *
     * @param allPhases all the phases for the project.
     * @since 1.1
     */
    private void recalculateScheduledDates(Phase[] allPhases) {
        for (int i = 0; i < allPhases.length; ++i) {
            Phase phase = allPhases[i];
            Date newStartDate = phase.calcStartDate();
            Date newEndDate = phase.calcEndDate();
            phase.setScheduledStartDate(newStartDate);
            phase.setScheduledEndDate(newEndDate);
        }
    }
}
