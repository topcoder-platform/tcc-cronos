/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.direct.specreview.services.SpecReviewCommentService;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.review.specification.SpecificationReviewStatus;

/**
 * <p>
 * Unit tests for <code>ViewSpecificationReviewAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewSpecificationReviewActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private ViewSpecificationReviewAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     */
    private ActionProxy prepareActionProxy() {
        ActionProxy proxy = getActionProxy("/viewSpecificationReviewAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof ViewSpecificationReviewAction);

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
        instance = new ViewSpecificationReviewAction();
        instance.setSpecificationReviewService(new MockSpecificationReviewService());
        instance.setSpecReviewCommentService(new MockSpecReviewCommentService());
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
     * Accuracy test for execute. Verifies that proper specification review data is fetched
     * and stored in model.
     */
    @Test
    public void test_execute_Accuracy1() {
        instance.setContestId(1);
        instance.setStudio(true);

        // execute the action and verify the return value
        assertEquals("return value from execute method is wrong", Action.SUCCESS, instance.execute());

        // make sure data is correct in model
        assertNotNull("result in model should not be null", instance.getModel().getData("result"));
        assertTrue("result in model is wrong type",
            instance.getModel().getData("result") instanceof ViewSpecificationReviewActionResultData);
        ViewSpecificationReviewActionResultData result =
            (ViewSpecificationReviewActionResultData) instance.getModel().getData("result");
        assertEquals("review id is wrong", 1, result.getSpecificationReview().getReview().getId());
        assertEquals("specification review status is wrong", SpecificationReviewStatus.PENDING_REVIEW, result
            .getSpecificationReviewStatus());
        assertNotNull("spec review comments should have been fetched", result.getSpecReviewComments());
        assertEquals("wrong number of spec review comments fetched", 2, result.getSpecReviewComments().size());
        assertNotNull("user comments should be present for first spec review comment", result.getSpecReviewComments()
            .get(0).getComments());
        assertEquals("wrong number of user comments present for first spec review comment", 2,
            result.getSpecReviewComments().get(0).getComments().size());
        assertEquals("wrong user comment present for first spec review comment", 1,
            result.getSpecReviewComments().get(0).getComments().get(0).getCommentId());
        assertEquals("wrong user comment present for first spec review comment", 2,
            result.getSpecReviewComments().get(0).getComments().get(1).getCommentId());
        assertEquals("wrong user comment present for second spec review comment", 3,
            result.getSpecReviewComments().get(1).getComments().get(0).getCommentId());
        assertEquals("wrong user comment present for second spec review comment", 4,
            result.getSpecReviewComments().get(1).getComments().get(1).getCommentId());
    }

    /**
     * Failure test for execute. The SpecificationReviewService hasn't been injected, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure1() {
        instance = new ViewSpecificationReviewAction();
        instance.setSpecReviewCommentService(new MockSpecReviewCommentService());
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, ViewSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. The SpecReviewCommentService hasn't been injected, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure2() {
        instance = new ViewSpecificationReviewAction();
        instance.setSpecificationReviewService(new MockSpecificationReviewService());
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, ViewSpecificationReviewActionException.class);
    }

    /**
     * Failure test for execute. An error occurred during persistence operation, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure3() {
        MockSpecReviewCommentService.setTriggerException(true);
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, ViewSpecificationReviewActionException.class);
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

    /**
     * Accuracy test for getSpecReviewCommentService. Verifies the returned value is correct.
     */
    @Test
    public void test_getSpecReviewCommentService_Accuracy() {
        SpecReviewCommentService test = new MockSpecReviewCommentService();
        instance.setSpecReviewCommentService(test);
        assertSame("getter is wrong", test, instance.getSpecReviewCommentService());
    }

    /**
     * Accuracy test for setSpecReviewCommentService. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSpecReviewCommentService_Accuracy() {
        SpecReviewCommentService test = new MockSpecReviewCommentService();
        instance.setSpecReviewCommentService(test);
        TestHelper.assertFieldSame(instance.getClass(), test, instance, "specReviewCommentService");
    }

    /**
     * Failure test for setSpecReviewCommentService. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setSpecReviewCommentService_Null() {
        try {
            instance.setSpecReviewCommentService(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
