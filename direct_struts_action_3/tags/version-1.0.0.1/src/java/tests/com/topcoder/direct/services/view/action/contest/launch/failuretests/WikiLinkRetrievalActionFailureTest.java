/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.services.view.action.contest.launch.WikiLinkRetrievalAction;

/**
 * <p>
 * Failure tests for WikiLinkRetrievalAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class WikiLinkRetrievalActionFailureTest extends TestCase {

    /** Represents WikiLinkRetrievalAction instance for test. */
    private WikiLinkRetrievalAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new WikiLinkRetrievalAction();
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
