/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.actions.SaveSpecificationReviewCommentAction;

/**
 * <p>
 * Failure tests for SaveSpecificationReviewCommentAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class SaveSpecificationReviewCommentActionFailureTest extends TestCase {

    /** Represents SaveSpecificationReviewCommentAction instance for test. */
    private SaveSpecificationReviewCommentAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new SaveSpecificationReviewCommentAction();
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
     * Tests for setSpecReviewCommentService() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setSpecReviewCommentService1() throws Exception {
        try {
            instance.setSpecReviewCommentService(null);
            fail("expects IAE");
        }catch (IllegalArgumentException e) {
            //pass
        }
    }
}
