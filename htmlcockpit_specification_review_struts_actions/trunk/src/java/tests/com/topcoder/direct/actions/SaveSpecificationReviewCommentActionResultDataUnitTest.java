/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.specreview.services.UserComment;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>SaveSpecificationReviewCommentActionResultData</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SaveSpecificationReviewCommentActionResultDataUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private SaveSpecificationReviewCommentActionResultData instance;

    /**
     * Sets up the environment.
     */
    @Before
    public void setUp() {
        instance = new SaveSpecificationReviewCommentActionResultData();
    }

    /**
     * Tears down the environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getUserComment. Verifies the returned value is correct.
     */
    @Test
    public void test_getUserComment_Accuracy() {
        UserComment test = new UserComment();
        instance.setUserComment(test);
        assertSame("getter is wrong", test, instance.getUserComment());
    }

    /**
     * Accuracy test for setUserComment. Verifies the assigned value is correct.
     */
    @Test
    public void test_setUserComment_Accuracy() {
        UserComment test = new UserComment();
        instance.setUserComment(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "userComment");
    }

    /**
     * Failure test for setUserComment. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setUserComment_Null() {
        try {
            instance.setUserComment(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
