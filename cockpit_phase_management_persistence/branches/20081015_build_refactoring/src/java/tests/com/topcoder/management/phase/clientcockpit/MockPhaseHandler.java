/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;

import com.topcoder.project.phases.Phase;


/**
 * <p>
 * Mock implementation of <code>PhaseHandler</code> to be used in testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseHandler implements PhaseHandler {
    /**
     * <p>
     * Represents flag indicating whether the perform method is invoked.
     * </p>
     */
    private boolean performed = false;

    /**
     * <p>
     * The handler will make the decision as to whether the start, end or cancel operations can be performed
     * for the specified phase.
     * </p>
     *
     * @param phase phase to test.
     * @return <code>true</code> always.
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>
     * @throws PhaseHandlingException if an exception occurs while determining whether the operation can be
     *         performed. It is never thrown.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        if (phase == null) {
            throw new IllegalArgumentException("phase is null");
        }

        return true;
    }

    /**
     * <p>
     * Extra logic to be used when the phase is starting, ending or canceling. This method does nothing,
     * except it sets the flag which indicates is has been invoked.
     * </p>
     *
     * @param phase phase to apply an operation to.
     * @param operator operator applying.
     * @throws IllegalArgumentException if any argument is <code>null</code>, or if <code>operator</code>
     *         is an empty string
     * @throws PhaseHandlingException DOCUMENT ME!
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        if (phase == null) {
            throw new IllegalArgumentException("phase is null");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator is null");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator is empty");
        }

        performed = true;
    }

    /**
     * <p>
     * Checks whether the perform() method has been invoked or not.
     * </p>
     *
     * @return <code>true</code> if perform() method is invoked.
     */
    public boolean isPerformed() {
        return performed;
    }
}
