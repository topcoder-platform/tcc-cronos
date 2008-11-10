/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;

/**
 * <p>
 * Mock implementation of PhaseHandler, it's used for test.
 * </p>
 * @author hfx
 * @version 1.0
 */
public class MockPhaseHandler implements PhaseHandler {

    /**
     * The implement of PhaseHandler.
     * @param arg0
     *            the parameter
     * @return the result
     * @throws PhaseHandlingException
     *             the exception
     */
    public boolean canPerform(Phase arg0) throws PhaseHandlingException {
        return false;
    }

    /**
     * The implement of PhaseHandler.
     * @param arg0
     *            the parameter
     * @param arg1
     *            the parameter
     * @throws PhaseHandlingException
     *             the exception
     */
    public void perform(Phase arg0, String arg1) throws PhaseHandlingException {
    }

}
