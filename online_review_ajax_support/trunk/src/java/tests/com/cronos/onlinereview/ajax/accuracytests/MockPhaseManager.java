/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.phase.PhaseManager;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;


/**
 * Mock class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseManager implements PhaseManager {

    public void updatePhases(Project project, String operator) {
    }

    public Project getPhases(long project) {
        return new MockPhaseProject();
    }

    public Project[] getPhases(long[] projects) {
        return null;
    }

    public PhaseType[] getAllPhaseTypes() {
        return new PhaseType[] {new MockPhaseType(), new MockPhaseType(1), new MockPhaseType(2), new MockPhaseType(3), new MockPhaseType(4)};
    }

    public PhaseStatus[] getAllPhaseStatuses() {
        return new PhaseStatus[]{new MockPhaseStatus()};
    }

    public boolean canStart(Phase phase) {
        return false;
    }

    public void start(Phase phase, String operator) {
    }

    public boolean canEnd(Phase phase) {
        return false;
    }

    public void end(Phase phase, String operator) {
    }

    public boolean canCancel(Phase phase) {
        return false;
    }

    public void cancel(Phase phase, String operator) {
    }
}
