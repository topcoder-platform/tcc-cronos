/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.stresstests;

import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;

import com.topcoder.project.phases.Phase;

/**
 * Mock implementation of <code>PhaseHandler</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseHandler implements PhaseHandler {

    /**
     * The operator in the methods.
     */
    private String operator;

    /**
     * Gets the operator passed in the methods.
     *
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Mock implementation of canPerform(Phase).
     *
     * @param phase
     *            the Phase instance
     * @return indicates whether the phase can be performed
     * @throws PhaseHandlingException
     *             if any error occurs
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        if (phase.getPhaseType().getId() == 0) {
            return true;
        }

        return false;
    }

    /**
     * Mock implementation.
     *
     * @param phase
     *            the phase
     * @param operator
     *            the operator
     * @throws PhaseHandlingException
     *             if any error occurs
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        this.operator = operator;
    }
}
