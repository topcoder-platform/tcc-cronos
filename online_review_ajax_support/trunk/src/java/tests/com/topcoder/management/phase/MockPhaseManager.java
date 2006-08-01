/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import java.util.ArrayList;

import com.topcoder.project.phases.MockPhase;
import com.topcoder.project.phases.MockPhaseStatus;
import com.topcoder.project.phases.MockPhaseType;
import com.topcoder.project.phases.MockProject;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * Mock implementation of <code>PhaseManager</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseManager implements PhaseManager {

    /**
     * All the types.
     */
    private ArrayList types = new ArrayList();

    /**
     * All the statuses.
     */
    private ArrayList statuses = new ArrayList();

    /**
     * The constructor.
     *
     */
    public MockPhaseManager() {
        PhaseStatus open = new MockPhaseStatus();
        open.setId(1);
        open.setName("Open");
        statuses.add(open);

        PhaseType review = new MockPhaseType();
        review.setId(1);
        review.setName("Review");

        PhaseType appeal = new MockPhaseType();
        appeal.setId(2);
        appeal.setName("Appeals");

        PhaseType response = new MockPhaseType();
        response.setId(3);
        response.setName("Appeals Response");

        types.add(review);
        types.add(appeal);
        types.add(response);
    }
    /**
     * Return whether the phase can be cancel or not.
     * @param phase the phase
     * @return always false
     */
    public boolean canCancel(Phase phase) {
        return false;
    }

    /**
     * Return whether the phase can be end or not.
     * @param phase the phase
     * @return always false
     */
    public boolean canEnd(Phase phase) {
        return false;
    }

    /**
     * Return whether the phase can be start or not.
     * @param phase the phase
     * @return always false
     */
    public boolean canStart(Phase phase) {
        return false;
    }

    /**
     * Cancel a phase.
     * @param phase the phase to cancel
     * @param operator the operator
     */
    public void cancel(Phase phase, String operator) {
    }

    /**
     * End a phase.
     * @param phase the phase to cancel
     * @param operator the operator
     */
    public void end(Phase phase, String operator) {
    }

    /**
     * Get all the phase statuses.
     * @return all the phase statuses.
     */
    public PhaseStatus[] getAllPhaseStatuses() {

        return (PhaseStatus[]) statuses.toArray(new PhaseStatus[0]);
    }

    /**
     * Get all the phase types.
     * @return all the phase types.
     */
    public PhaseType[] getAllPhaseTypes() {

        return (PhaseType[]) types.toArray(new PhaseType[0]);
    }

    /**
     * Get the Project.
     * @param p the project id
     * @return the project
     */
    public Project getPhases(long p) {
        Project project = new MockProject();
        project.setId(1);

        // review
        MockPhase phase = new MockPhase();
        phase.setId(1);

        PhaseType type = new MockPhaseType();
        type.setId(1);
        type.setName("Review");

        phase.setPhaseType(type);

        // appeal
        MockPhase appeal = new MockPhase();
        appeal.setId(2);

        PhaseType appealType = new MockPhaseType();
        appealType.setId(2);
        appealType.setName("Appeals");

        appeal.setPhaseType(appealType);

        // appeal response
        MockPhase response = new MockPhase();
        response.setId(3);

        PhaseType responseType = new MockPhaseType();
        responseType.setId(3);
        responseType.setName("Appeals Response");

        response.setPhaseType(responseType);

        project.addPhase(phase);
        project.addPhase(appeal);
        project.addPhase(response);
        return project;
    }

    /**
     * Get the phases.
     * @param projects the project ids.
     * @return the project objects
     */
    public Project[] getPhases(long[] projects) {
        return null;
    }

    /**
     * Start a phase.
     * @param phase the phase to start
     * @param operator the operator
     */
    public void start(Phase phase, String operator) {
    }

    /**
     * Update the phases.
     * @param project the project to update
     * @param operator the operator
     */
    public void updatePhases(Project project, String operator) {
    }

    /**
     * Clear the types.
     */
    public void removeType() {
        this.types.clear();
    }

    /**
     * Clear the statuses.
     */
    public void removeStatuses() {
        this.statuses.clear();
    }
}
