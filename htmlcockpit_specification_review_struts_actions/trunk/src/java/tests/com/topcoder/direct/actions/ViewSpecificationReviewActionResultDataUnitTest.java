/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.specreview.services.SpecReviewComment;
import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewStatus;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>ViewSpecificationReviewActionResultData</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewSpecificationReviewActionResultDataUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private ViewSpecificationReviewActionResultData instance;

    /**
     * Sets up the environment.
     */
    @Before
    public void setUp() {
        instance = new ViewSpecificationReviewActionResultData();
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
     * Accuracy test for getSpecificationReview. Verifies the returned value is correct.
     */
    @Test
    public void test_getSpecificationReview_Accuracy() {
        SpecificationReview test = new SpecificationReview();
        instance.setSpecificationReview(test);
        assertSame("getter is wrong", test, instance.getSpecificationReview());
    }

    /**
     * Accuracy test for setSpecificationReview. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSpecificationReview_Accuracy() {
        SpecificationReview test = new SpecificationReview();
        instance.setSpecificationReview(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "specificationReview");
    }

    /**
     * Failure test for setSpecificationReview. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setSpecificationReview_Null() {
        try {
            instance.setSpecificationReview(null);
            fail("IllegalArgumentSpecificationReview is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getSpecificationReviewStatus. Verifies the returned value is correct.
     */
    @Test
    public void test_getSpecificationReviewStatus_Accuracy() {
        SpecificationReviewStatus test = SpecificationReviewStatus.WAITING_FOR_FIXES;
        instance.setSpecificationReviewStatus(test);
        assertEquals("getter is wrong", test, instance.getSpecificationReviewStatus());
    }

    /**
     * Accuracy test for setSpecificationReviewStatus. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSpecificationReviewStatus_Accuracy() {
        SpecificationReviewStatus test = SpecificationReviewStatus.PENDING_REVIEW;
        instance.setSpecificationReviewStatus(test);
        TestHelper.assertFieldEquals(instance.getClass(), test, instance, "specificationReviewStatus");
    }

    /**
     * Failure test for setSpecificationReviewStatus. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setSpecificationReviewStatus_Null() {
        try {
            instance.setSpecificationReviewStatus(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getSpecReviewComments. Verifies the returned value is correct.
     */
    @Test
    public void test_getSpecReviewComments_Accuracy() {
        List<SpecReviewComment> test = new ArrayList<SpecReviewComment>();
        instance.setSpecReviewComments(test);
        assertSame("getter is wrong", test, instance.getSpecReviewComments());
    }

    /**
     * Accuracy test for setSpecReviewComments. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSpecReviewComments_Accuracy() {
        List<SpecReviewComment> test = new ArrayList<SpecReviewComment>();
        instance.setSpecReviewComments(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "specReviewComments");
    }

    /**
     * Failure test for setSpecReviewComments. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setSpecReviewComments_Null() {
        try {
            instance.setSpecReviewComments(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setSpecReviewComments. The value contains null elements, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setSpecReviewComments_NullElements() {
        try {
            List<SpecReviewComment> list = new ArrayList<SpecReviewComment>();
            list.add(new SpecReviewComment());
            list.add(null);
            instance.setSpecReviewComments(list);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
