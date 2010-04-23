/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import com.topcoder.service.actions.StudioOrSoftwareContestAction;

/**
 * Mock <code>StudioOrSoftwareContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class FailureStudioOrSoftwareContestAction extends StudioOrSoftwareContestAction {

    /**
     * contest id.
     */
    public static long contestId;

    /**
     * Project id.
     */
    public static long projectId;

    /**
     * Gets contest id.
     * @return contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Gets project id.
     * @return project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Prepares the action.
     */
    public void prepare() {
        super.prepare();
        this.setContestId(this.contestId);
        this.setProjectId(this.projectId);
    }
}
