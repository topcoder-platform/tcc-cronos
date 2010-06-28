/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.services.view.action.contest.launch.ActiveContestScheduleRetrievalAction;

/**
 * <p>
 * Failure tests for ActiveContestScheduleRetrievalAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ActiveContestScheduleRetrievalActionFailureTest extends TestCase {

    /** Represents ActiveContestScheduleRetrievalAction instance for test. */
    private ActiveContestScheduleRetrievalAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ActiveContestScheduleRetrievalAction();
        instance.prepare();
        ActionContext actionContext = new ActionContext(new HashMap<String, Object>());
        actionContext.setActionInvocation(new MockActionInvocation());
        ActionContext.setContext(actionContext);
    }

    /**
     * <p>
     * Clean up the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }
    /**
     * Tests for execute() with illegal state.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute() throws Exception {
        try {
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }
}
