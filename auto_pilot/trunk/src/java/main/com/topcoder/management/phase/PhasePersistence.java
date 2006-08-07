/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * This is a persistence contract for phase persistence operations. <br>
 * This is broken down into APIs that deal with basic CRUD of phase management (such as create a
 * phase, delete a phase, etc...) But we also have methods that work with bulk CRUD (such as get ALL
 * phase types or statuses, or updating a list of phases, or deleting a list of phases)<br>
 * We also have methods, which allow for retrieval of all phases linked to a particular Project (or
 * batch version) <br>
 * This will plug in, into the PhaseManager so that the user doesn't have to be aware of actual
 * persistence operations done on behalf of the manager.
 * </p>
 * <p>
 * Thread-Safety Implementation should strive to ensure thread safety for operations. For example
 * for db implementations connections should be used locally to each method invocation so that there
 * is no issue with multiple threads hitting different/same method.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface PhasePersistence {
    /**
     * <p>
     * Implementations should return project instance for the given id. If the project cannot be
     * found then a null is returned.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @param projectId id of the project to fetch
     * @return Project instance from persistence
     */
    public com.topcoder.project.phases.Project getProjectPhases(long projectId) throws PhasePersistenceException;

    /**
     * <p>
     * Implementations should return an array of project instances for the given input array of
     * project ids. If any if the projects in the input array cannot be found then a null is
     * returned in the return array in that exact index. Returns are positional thus id at index 2
     * of input is represented by a Project instance at index 2 of output array.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws IllegalArgumentException if the array input is null. It is ok to be empty in which
     *             case we simply return an empty Project[] array.
     * @param projectIds A collection of ids of the project to fetch
     * @return an array of Projects that were fetched.
     */
    public Project[] getProjectPhases(long[] projectIds) throws PhasePersistenceException;

    /**
     * <p>
     * Implementations should return all the PhaseType(s) present in the storage.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @return an array of all the PhaseTypes availabe at this time
     */
    public PhaseType[] getAllPhaseTypes() throws PhasePersistenceException;

    /**
     * <p>
     * Implementations should return all the PhaseStatus(s) in the storage.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @return an array of all PhaseStatus instances
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhasePersistenceException;

    /**
     * <p>
     * Implementations should create a new phase entity in persistence. If the entity already exists
     * then an exception will be thrown.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws PhasePersistenceException if the phase already exists in storage.
     * @throws IllegalArgumentException if phase or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param phase phase to create
     * @param operator create/modify user
     */
    public void createPhase(Phase phase, String operator) throws PhasePersistenceException;

    /**
     * <p>
     * This represents is a batch create operation. Implementations should consider transaction
     * control here.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws PhasePersistenceException if any of the phases already exists in storage.
     * @throws IllegalArgumentException if phases array or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param phases an array of phases to create
     * @param operator create/modify user
     */
    public void createPhases(Phase[] phases, String operator) throws PhasePersistenceException;

    /**
     * <p>
     * Implementations should Read/Fetch a specific phase from the data store. If not found return a
     * null.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @param phaseId id of the phase we want
     * @return Phase
     */
    public Phase getPhase(long phaseId) throws PhasePersistenceException;

    /**
     * <p>
     * This represents a batch version of the Read/Fetch a specific phase from the data store.<br>
     * For each entry in the input array at index i, if the phase at index I is not found in
     * persistence then we return a null in the corresponding ith slot in the output array.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws IllegalArgumentException if the array input is null. It is ok to be empty in which
     *             case we simply return an empty Phase[] array.
     * @param phaseIds an array of collection ids to fetch
     * @return an Array of fetched Phase instances
     */
    public Phase[] getPhases(long[] phaseIds) throws PhasePersistenceException;

    /**
     * <p>
     * This will update the input Phase in persistence.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws PhasePersistenceException if the phase we are trying to update doesn't exist.
     * @throws IllegalArgumentException if phase or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param phase phase to update
     * @param operator create/modify user
     */
    public void updatePhase(Phase phase, String operator) throws PhasePersistenceException;

    /**
     * <p>
     * This represents a batch version of the single phase Update. Will need to rollback the whole
     * update if any of the phase updates fail. Should be done in one transaction.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws PhasePersistenceException if the phase we are trying to update doesn't exist.
     * @throws IllegalArgumentException if phases array or operator is null.
     * @throws IllegalArgumentException if operator is an empty string.
     * @param phases an array of phases to update
     * @param operator create/modify user
     */
    public void updatePhases(Phase[] phases, String operator) throws PhasePersistenceException;

    /**
     * <p>
     * This represents a delete of a phase from the data store. If the element doesn't exist, we
     * silently do nothing.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws IllegalArgumentException if phase is null.
     * @param phase phase to delete
     */
    public void deletePhase(Phase phase) throws PhasePersistenceException;

    /**
     * <p>
     * Batch version of a single "Delete a phase from the data store". If the element doesn't exist,
     * we silently do nothing and continue deleting the other elements.
     * </p>
     * @throws PhasePersistenceException if there are any persistence issues. We should wrap the
     *             cause in this.
     * @throws IllegalArgumentException if phases array is null.
     * @param phases phases to delete.
     */
    public void deletePhases(Phase[] phases) throws PhasePersistenceException;

    /**
     * <p>
     * Implementations should test if the input phase is a new phase (i.e. needs its id to be set)
     * </p>
     * @throws IllegalArgumentException if phase is null.
     * @param phase Phase object to tests if it is new
     * @return true if the applied test woked; true otherwise.
     */
    public boolean isNewPhase(Phase phase);

    /**
     * <p>
     * Implementations should test if the input dependency is a new dependency (i.e. needs its id to
     * be set)
     * </p>
     * @throws IllegalArgumentException if dependency is null.
     * @param dependency Dependency to check for being new.
     * @return true if new; false otherswise.
     */
    public boolean isNewDependency(Dependency dependency);
}
