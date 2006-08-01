/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.project.phases.PhaseStatus;


/**
 * Mock class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseStatus extends PhaseStatus {

    public long getId() {
        return 999990;
    }

    public String getName() {
        return "Open";
    }
}
