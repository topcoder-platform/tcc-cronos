/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * This is a contract for managing phase data for project(s). This describes functionality for
 * manipulating phases with pluggable support for persistence CRUD. It manages connections, id
 * generation, and phase handler registry,
 * </p>
 * <p>
 * Thread-Safety There is no requirement to make this thread-safe. The assumption is that each
 * thread will have its own instance of a PhaseManager implementation.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface PhaseManager {
    /**
     * <p>
     * Update the Project phases.<br>
     * The identifiers of any new phases will be provided with ID Generator.<br>
     * Existing phase should continue to use the original identifiers. Other phases for the project
     * not specified in the input should be deleted. Project phases should be validated according to
     * the defined rules. The operator needs to be provided for auditing purpose.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request ( other than validation)
     * @throws PhaseValidationException if validation of any phase fails. it will fail on first such
     *             violation and immediately abort.
     * @throws IllegalArgumentException if project or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param project project to update
     * @param operator operator updating the project
     */
    public void updatePhases(Project project, String operator) throws PhaseManagementException,
        PhaseValidationException;

    /**
     * <p>
     * Get the project based on id. If not found then a null will be returned.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request ( other than validation)
     * @param project id of the project to fetch the phases for
     * @return Project for this id
     */
    public Project getPhases(long project) throws PhaseManagementException;

    /**
     * <p>
     * A more encompassing version of getting the phases. This is batch fetch, which should return
     * all the projects as identified by the ids. for each entry in the input array at index i if
     * the Project is not found then we return a null in the corresponding index in the output
     * array.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request
     * @throws IllegalArgumentException if the array input is null. It is ok to be empty in which
     *             case we simply return an empty Project[] array.
     * @param projects an array of ids of projects
     * @return all the project objects. One for each id.
     */
    public Project[] getPhases(long[] projects) throws PhaseManagementException;

    /**
     * <p>
     * Return all available Phase Types.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request
     * @return an array of PhaseTypes
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException;

    /**
     * <p>
     * Return all available Phase Statuses.
     * </p>
     * <p>
     * Thread-Safety
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request
     *             </p>
     * @return all phase statuses
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException;

    /**
     * <p>
     * Tests if the input phase can actually start.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request.
     * @throws IllegalArgumentException if phase is null.
     * @param phase phase to test
     * @return -
     */
    public boolean canStart(Phase phase) throws PhaseManagementException;

    /**
     * <p>
     * Starts the phase (puts it into a open status).
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request.
     * @throws IllegalArgumentException if phase or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param phase phase to start
     * @param operator operator for this phase.
     */
    public void start(Phase phase, String operator) throws PhaseManagementException;

    /**
     * <p>
     * Tests if the input phase can actually end.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request.
     * @throws IllegalArgumentException if phase.
     * @param phase phase to test
     * @return true if can be ended; false otherwise.
     */
    public boolean canEnd(Phase phase) throws PhaseManagementException;

    /**
     * <p>
     * Ends the phase (puts it into a closed status).
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request.
     * @throws IllegalArgumentException if phase or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param phase phase to end
     * @param operator operator associated with this action
     */
    public void end(Phase phase, String operator) throws PhaseManagementException;

    /**
     * <p>
     * Tests if the input phase can actually be cancelled.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request.
     * @throws IllegalArgumentException if phase is null.
     * @param phase phase to test
     * @return true if the phase can be cancelled.False otherwise.
     */
    public boolean canCancel(Phase phase) throws PhaseManagementException;

    /**
     * <p>
     * Cancels the phase (puts it into a closed status).
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws PhaseManagementException if any issues where encountered that prevented us from
     *             fulfilling this request.
     * @throws IllegalArgumentException if phase or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param phase phase to nacnel
     * @param operator operation associated with this action
     */
    public void cancel(Phase phase, String operator) throws PhaseManagementException;

    /**
     * <p>
     * Registers a PhaseHandler based on PhaseType and PhaseOperation.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws IllegalArgumentException if any input parameter is null.
     * @param handler Phase handler to register
     * @param type phase type
     * @param operation operation in phase (START, END. CANCEL currently)
     */
    public void registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum operation);

    /**
     * <p>
     * Removes the handler from the registry. Will return the actually removed handler if the
     * handler is found. Returns null otherwise.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws IllegalArgumentException if any input parameter is null.
     * @param type phase type
     * @param operation operation to associate this handler with
     * @return -
     */
    public PhaseHandler unregisterHandler(PhaseType type, PhaseOperationEnum operation);

    /**
     * <p>
     * Will return all the registered handlers in an array.
     * </p>
     * @return an array of all teh handlers currently in use
     */
    public PhaseHandler[] getAllHandlers();

    /**
     * <p>
     * For a given handler will return composite key (representing PhaseType/PhaseOperation) Will
     * return null if not found.
     * </p>
     * <p>
     * Thread-Safety
     * </p>
     * @throws IllegalArgumentException if any input parameter is null.
     * @param handler hanlder to get information about
     * @return handler registry info.
     */
    public HandlerRegistryInfo getHandlerRegistrationInfo(PhaseHandler handler);
}
