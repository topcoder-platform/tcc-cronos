/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.stresstests;

import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.project.phases.Phase;

/**
 * Mock class for stress test.
 *
 * @author extra
 * @version 1.0
 */
public class NullPhaseHandler implements PhaseHandler {
    /**
     * The passed.
     */
    private boolean passed = false;

    /**
     * Empty constructor.
     */
    public NullPhaseHandler() {
        // do nothing
    }

    /**
     * Returns whether or not the test should pass.
     *
     * @return whether or not the test should pass
     */
    boolean getPassed() {
        return passed;
    }

    /**
     * Sets whether or not the test should pass.
     *
     * @param passed
     *            whether or not the test should pass
     */
    void setPassed(boolean passed) {
        this.passed = passed;
    }

    /**
     * Always returns <code>true</code>.
     *
     * @param phase
     *            the phaes to perform
     * @return <code>true</code>
     */
    public boolean canPerform(Phase phase) {
        return true;
    }

    /**
     * Does nothing.
     *
     * @param phase
     *            the phase
     * @param operator
     *            the operator
     */
    public void perform(Phase phase, String operator) {
    }
}
