/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.actions.GetDocumentsContestAction;
import com.topcoder.service.actions.StudioOrSoftwareContestAction;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * Accuracy tests for StudioOrSoftwareContestAction.
 * @author onsky
 * @version 1.0
 */
public class StudioOrSoftwareContestActionTests extends TestCase {
    /**
     * <p>Represents StudioOrSoftwareContestAction instance for testing.</p>
     */
    private StudioOrSoftwareContestAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new GetDocumentsContestAction();
        ContestServiceFacade facade = new MockContestServiceFacade();
        instance.setContestServiceFacade(facade);
        instance.prepare();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for executeAction.
     */
    public void test_executeAction() {
        try {
        	instance.executeAction();
        	// pass
        } catch (Exception e) {
        	fail("no exception expected");
        }
    }

    /**
     * Accuracy test for setProjectId.
     */
    public void test_setProjectId() {
        assertEquals("must be 0 by default", 0, instance.getProjectId());
        instance.setProjectId(1l);
        assertEquals("Incorrect value after set a new one", 1l, instance.getProjectId());
    }

    /**
     * Accuracy test for setContestId.
     */
    public void test_setContestId() {
        assertEquals("must be 0 by default", 0, instance.getContestId());
        instance.setContestId(2l);
        assertEquals("Incorrect value after set a new one", 2l, instance.getContestId());
    }
}
