/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Mock implemetation of PhaseManager.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockPhaseManager implements PhaseManager {

    /**
     * @see com.topcoder.management.phase.PhaseManager#updatePhases(com.topcoder.project.phases.Project,
     *      java.lang.String)
     */
    public void updatePhases(Project project, String operator) throws PhaseManagementException {
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#getPhases(long)
     */
    public Project getPhases(long project) throws PhaseManagementException {
        return null;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#getPhases(long[])
     */
    public Project[] getPhases(long[] projects) throws PhaseManagementException {
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(101);
        Phase phase = new Phase(project, 300000);
        phase.setId(1);
        phase.setPhaseStatus(PhaseStatus.OPEN);

        project.addPhase(phase);

        return new Project[] { project };
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#getAllPhaseTypes()
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return null;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#getAllPhaseStatuses()
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return null;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#canStart(com.topcoder.project.phases.Phase)
     */
    public boolean canStart(Phase phase) throws PhaseManagementException {
        return false;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#start(com.topcoder.project.phases.Phase,
     *      java.lang.String)
     */
    public void start(Phase phase, String operator) throws PhaseManagementException {
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#canEnd(com.topcoder.project.phases.Phase)
     */
    public boolean canEnd(Phase phase) throws PhaseManagementException {
        return false;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#end(com.topcoder.project.phases.Phase,
     *      java.lang.String)
     */
    public void end(Phase phase, String operator) throws PhaseManagementException {
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#canCancel(com.topcoder.project.phases.Phase)
     */
    public boolean canCancel(Phase phase) throws PhaseManagementException {
        return false;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#cancel(com.topcoder.project.phases.Phase,
     *      java.lang.String)
     */
    public void cancel(Phase phase, String operator) throws PhaseManagementException {
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#registerHandler(
     *      com.topcoder.management.phase.PhaseHandler, com.topcoder.project.phases.PhaseType,
     *      com.topcoder.management.phase.PhaseOperationEnum)
     */
    public void registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum operation) {
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#unregisterHandler(
     *      com.topcoder.project.phases.PhaseType, com.topcoder.management.phase.PhaseOperationEnum)
     */
    public PhaseHandler unregisterHandler(PhaseType type, PhaseOperationEnum operation) {
        return null;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#getAllHandlers()
     */
    public PhaseHandler[] getAllHandlers() {
        return null;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#getHandlerRegistrationInfo(com.topcoder.management.phase.PhaseHandler)
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler handler) {
        return null;
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#setPhaseValidator(com.topcoder.management.phase.PhaseValidator)
     */
    public void setPhaseValidator(PhaseValidator phaseValidator) {
    }

    /**
     * @see com.topcoder.management.phase.PhaseManager#getPhaseValidator()
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }

}
