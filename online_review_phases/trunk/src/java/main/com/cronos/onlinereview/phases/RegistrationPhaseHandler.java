/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;

import com.cronos.onlinereview.phases.lookup.ResourceRoleLookupUtility;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;

import com.topcoder.project.phases.Phase;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection, email sending and the required number of
 * registrators. This class handle the registration phase. If the input is of other phase types,
 * PhaseNotSupportedException will be thrown.</p>
 *  <p>The registration phase can start whenever the dependencies are met and can stop when:
 *  <ul>
 *      <li>The dependencies are met</li>
 *      <li>The period has passed</li>
 *      <li>The number of registrations meets the required number.</li>
 *  </ul>
 *  </p>
 *  <p>There is no additional logic for executing this phase.</p>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class RegistrationPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.RegistrationPhaseHandler";

    /** constant for registration phase type. */
    private static final String PHASE_TYPE_REGISTRATION = "Registration";

    /**
     * Create a new instance of RegistrationPhaseHandler using the default namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public RegistrationPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of RegistrationPhaseHandler using the given namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public RegistrationPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.<p>If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions:</p>
     *  - The dependencies are met<p>If the input phase status is Open, then it will check if the phase can be
     * stopped using the following conditions:
     *  <ul>
     *      <li>The dependencies are met</li>
     *      <li>The period has passed</li>
     *      <li>The number of registrations meets the required number.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Registration" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_REGISTRATION);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            //return true if all dependencies have stopped and start time has been reached.
            return PhasesHelper.canPhaseStart(phase);
        } else {
            return (PhasesHelper.havePhaseDependenciesStopped(phase)
                    && PhasesHelper.reachedPhaseEndTime(phase)
                    && areRegistrationsEnough(phase));
        }
    }

    /**
     * Provides addtional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be send on start phase or end phase base
     * on configuration settings.<p>If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.</p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Registration" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_REGISTRATION);
        PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        sendEmail(phase);
    }

    /**
     * This method checks if the number of registrations meets the required number.
     *
     * @param phase the input phase to check.
     *
     * @return true if registrations are enough, false otherwise.
     *
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     */
    private boolean areRegistrationsEnough(Phase phase) throws PhaseHandlingException {
        int regNumber = PhasesHelper.getIntegerAttribute(phase, "Registration Number");
        Resource[] resources = searchResources(phase);
        return (regNumber <= resources.length);
    }

    /**
     * Helper method to search for resources with "Submitter" role and project id filters.
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
            long resourceRoleId = ResourceRoleLookupUtility.lookUpId(conn, "Submitter");
            Filter roleIdFilter = ResourceFilterBuilder.createResourceRoleIdFilter(resourceRoleId);
            Filter projectIdfilter = ResourceFilterBuilder.createProjectIdFilter(phase.getProject().getId());
            Filter fullFilter = SearchBundle.buildAndFilter(roleIdFilter, projectIdfilter);
            return getManagerHelper().getResourceManager().searchResources(fullFilter);
        } catch (SearchBuilderConfigurationException e) {
            throw new PhaseHandlingException("search builder configuration error", e);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("resource persistence error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("search builder error", e);
        } catch (SQLException e) {
            throw new PhaseHandlingException("error when looking up resource id.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
