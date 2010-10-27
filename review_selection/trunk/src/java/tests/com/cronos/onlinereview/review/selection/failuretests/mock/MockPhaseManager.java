/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.mock;

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
 * Mock implementation of PhaseManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseManager implements PhaseManager {
    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @return false.
     */
    public boolean canCancel(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @return false.
     */
    public boolean canEnd(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @return false.
     */
    public boolean canStart(Phase arg0) throws PhaseManagementException {
        return false;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @param arg1
     *            another arg.
     */
    public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @param arg1
     *            another arg.
     */
    public void end(Phase arg0, String arg1) throws PhaseManagementException {
    }

    /**
     * Empty.
     *
     * @return null.
     */
    public PhaseHandler[] getAllHandlers() {
        return null;
    }

    /**
     * Empty.
     *
     * @return null.
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        return null;
    }

    /**
     * Empty.
     *
     * @return null.
     */
    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        return null;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @return null.
     */
    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        return null;
    }

    /**
     * Empty.
     *
     * @return null.
     */
    public PhaseValidator getPhaseValidator() {
        return null;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @return null.
     */
    public Project getPhases(long arg0) throws PhaseManagementException {
        return null;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @return null.
     */
    public Project[] getPhases(long[] arg0) throws PhaseManagementException {
        return null;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @param arg1
     *            another arg.
     * @param arg2
     *            third arg.
     */
    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {

    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     */
    public void setPhaseValidator(PhaseValidator arg0) {
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @param arg1
     *            another arg.
     */
    public void start(Phase arg0, String arg1) throws PhaseManagementException {

    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @param arg1
     *            another arg.
     * @return null.
     */
    public PhaseHandler unregisterHandler(PhaseType arg0, PhaseOperationEnum arg1) {
        return null;
    }

    /**
     * Empty.
     *
     * @param arg0
     *            one arg.
     * @param arg1
     *            another arg.
     */
    public void updatePhases(Project arg0, String arg1) throws PhaseManagementException {
    }
}
