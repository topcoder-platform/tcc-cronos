/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.services.view.action.contest.launch.ProjectContestDataRetrievalAction;

/**
 * <p>
 * Failure tests for ProjectContestDataRetrievalAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ProjectContestDataRetrievalActionFailureTest extends TestCase {

    /** Represents ProjectContestDataRetrievalAction instance for test. */
    private ProjectContestDataRetrievalAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ProjectContestDataRetrievalAction();
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
