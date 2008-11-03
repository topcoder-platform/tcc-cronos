/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;

/**
 * Mock of PhaseHandler for failure tests.
 * @author hfx
 * @version 1.0
 */
public class FailurePhaseHandler extends MockPhaseHandler {
    /**
     * check can perform.
     */
    public boolean canPerform(Phase arg0) throws PhaseHandlingException {
        throw new PhaseHandlingException("");
    }
    /**
     * perform.
     */
    public void perform(Phase arg0, String arg1) throws PhaseHandlingException {
        throw new PhaseHandlingException("");
    }
    
}
