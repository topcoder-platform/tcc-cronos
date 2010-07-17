/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.actions.StartSpecificationReviewAction;

/**
 * <p>
 * Failure tests for StartSpecificationReviewAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class StartSpecificationReviewActionFailureTest extends TestCase {

    /** Represents StartSpecificationReviewAction instance for test. */
    private StartSpecificationReviewAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new StartSpecificationReviewAction();
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
     * Tests for setSpecificationReviewService() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setSpecificationReviewService1() throws Exception {
        try {
            instance.setSpecificationReviewService(null);
            fail("expects IAE");
        }catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * Tests for setContestServiceFacade() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setContestServiceFacade2() throws Exception {
        try {
            instance.setContestServiceFacade(null);
            fail("expects IAE");
        }catch (IllegalArgumentException e) {
            //pass
        }
    }
}
