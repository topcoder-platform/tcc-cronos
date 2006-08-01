/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import java.util.Date;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;


/**
 * Mock class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhase extends Phase {
    private int id = -1;
    public MockPhase() {
        
    }
    public MockPhase(int id) {
        this.id = id;
    }
    public PhaseType getPhaseType() {
        if (id == 1) {
            return new MockPhaseType(2);
        }
        return new MockPhaseType();
    }
    public long getId() {
        
        return 1;
    }
    public PhaseStatus getPhaseStatus() {
        return new MockPhaseStatus();
    } 
    public Date getFixedStartDate() {
        return new Date();
    }
    public Date calcEndDate() {
        return new Date();
    }
}
