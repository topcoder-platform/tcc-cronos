/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.accuracytests;

import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;

/**
 * A mock implementation of PhaseHanler for testing use.
 * 
 * @author LostHunter
 * @version 1.0
 *
 */
public class MockPhaseHandler implements PhaseHandler {

    public MockPhaseHandler() {
    }
    public boolean canPerform(Phase arg0) throws PhaseHandlingException {
        return true;
    }
    public void perform(Phase arg0, String arg1) throws PhaseHandlingException {
    }
}
