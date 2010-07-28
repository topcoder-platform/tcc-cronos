/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import org.junit.Test;

import com.opensymphony.xwork2.Action;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>Helper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTest extends TestCase {

    /**
     * Accuracy test for handleActionError. The exception should be added to model and Action.ERROR should
     * be returned.
     */
    @Test
    public void test_handleActionError_Accuracy() {
        StartSpecificationReviewAction action = new StartSpecificationReviewAction();
        StartSpecificationReviewActionException ex = new StartSpecificationReviewActionException("test");
        assertEquals("the return value is wrong", Action.ERROR, Helper.handleActionError(action, ex));

        // make sure exception was added to model
        assertSame("exception should have been added to model", ex, action.getModel().getData(
            SpecificationReviewAction.DEFAULT_EXCEPTION_KEY));
    }

}
