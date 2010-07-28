/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.failuretests;

import java.util.Arrays;
import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.actions.ViewSpecificationReviewActionResultData;
import com.topcoder.direct.specreview.services.SpecReviewComment;

/**
 * <p>
 * Failure tests for ViewSpecificationReviewActionResultData.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ViewSpecificationReviewActionResultDataFailureTest extends TestCase {

    /** Represents ViewSpecificationReviewActionResultData instance for test. */
    private ViewSpecificationReviewActionResultData instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ViewSpecificationReviewActionResultData();
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
     * Tests for setSpecificationReview() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setSpecificationReview1() throws Exception {
        try {
            instance.setSpecificationReview(null);
            fail("expects IAE");
        }catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * Tests for setSpecificationReviewStatus() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setSpecificationReviewStatus2() throws Exception {
        try {
            instance.setSpecificationReviewStatus(null);
            fail("expects IAE");
        }catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * Tests for setSpecReviewComments() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setSpecReviewComments1() throws Exception {
        try {
            instance.setSpecReviewComments(null);
            fail("expects IAE");
        }catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * Tests for setSpecReviewComments() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setSpecReviewComments2() throws Exception {
        try {
            instance.setSpecReviewComments(Arrays.asList((SpecReviewComment)null));
            fail("expects IAE");
        }catch (IllegalArgumentException e) {
            //pass
        }
    }
}
