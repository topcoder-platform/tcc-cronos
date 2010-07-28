/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.review.specification.SpecificationReviewService;

/**
 * <p>
 * Unit tests for <code>ResubmitSpecificationReviewAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResubmitSpecificationReviewActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private ResubmitSpecificationReviewAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     */
    private ActionProxy prepareActionProxy() {
        ActionProxy proxy = getActionProxy("/resubmitSpecificationReviewAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof ResubmitSpecificationReviewAction);

        return proxy;
    }

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        TestHelper.setupEnvironment();
        instance = new ResubmitSpecificationReviewAction();
        instance.setSpecificationReviewService(new MockSpecificationReviewService());
        TestHelper.prepareSessionMap(instance);
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        TestHelper.cleanupEnvironment();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     */
    @Test
    public void testInheritance() {
        assertTrue("inheritance is wrong", instance instanceof SpecificationReviewAction);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);

        // test in struts environment
        assertNotNull("unable to create class in struts environment", prepareActionProxy());
    }

    /**
     * Accuracy test for execute. Verifies that the specification review is resubmitted successfully.
     */
    @Test
    public void test_execute_Accuracy1() {
        instance.setContestId(1);
        instance.setContent("test specification review content");

        // execute the action and verify the return value
        assertEquals("return value from execute method is wrong", Action.SUCCESS, instance.execute());

        // make sure specification review was resubmitted and content is correct
        String content =
            MockSpecificationReviewService.getSubmittedSpecificationReviews().get(instance.getContestId());
        assertNotNull("the specification review was not resubmitted", content);
        assertEquals("the content is wrong for the resubmitted specification review", instance.getContent(), content);
    }

    /**
     * Failure test for execute. The SpecificationReviewService hasn't been injected, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure1() {
        instance = new ResubmitSpecificationReviewAction();
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, ResubmitSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. An error occurred during persistence operation, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure2() {
        MockSpecificationReviewService.setTriggerException(true);
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, ResubmitSpecificationReviewActionException.class);
    }

    /**
     * Accuracy test for getContent. Verifies the returned value is correct.
     */
    @Test
    public void test_getContent_Accuracy() {
        String test = "test";
        instance.setContent(test);
        assertEquals("getter is wrong", test, instance.getContent());
    }

    /**
     * Accuracy test for setContent. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContent_Accuracy() {
        String test = "test";
        instance.setContent(test);
        TestHelper.assertFieldEquals(instance.getClass(), test, instance, "content");
    }

    /**
     * Failure test for setContent. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setContent_Null() {
        try {
            instance.setContent(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setContent. The value is empty, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setContent_Empty() {
        try {
            instance.setContent("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getSpecificationReviewService. Verifies the returned value is correct.
     */
    @Test
    public void test_getSpecificationReviewService_Accuracy() {
        SpecificationReviewService test = new MockSpecificationReviewService();
        instance.setSpecificationReviewService(test);
        assertSame("getter is wrong", test, instance.getSpecificationReviewService());
    }

    /**
     * Accuracy test for setSpecificationReviewService. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSpecificationReviewService_Accuracy() {
        SpecificationReviewService test = new MockSpecificationReviewService();
        instance.setSpecificationReviewService(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "specificationReviewService");
    }

    /**
     * Failure test for setSpecificationReviewService. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setSpecificationReviewService_Null() {
        try {
            instance.setSpecificationReviewService(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
