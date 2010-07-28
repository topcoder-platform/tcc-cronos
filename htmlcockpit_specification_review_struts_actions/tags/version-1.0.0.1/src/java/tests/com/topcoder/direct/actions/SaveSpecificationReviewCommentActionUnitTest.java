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
import com.topcoder.service.actions.AggregateDataModel;
import com.topcoder.service.actions.ValidationErrors;

/**
 * <p>
 * Unit tests for <code>SaveSpecificationReviewCommentAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SaveSpecificationReviewCommentActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private SaveSpecificationReviewCommentAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     */
    private ActionProxy prepareActionProxy() {
        ActionProxy proxy = getActionProxy("/saveSpecificationReviewCommentAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof SaveSpecificationReviewCommentAction);

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
        instance = new SaveSpecificationReviewCommentAction();
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
     * Accuracy test for execute. Verifies that new specification review comment is added successfully
     * and stored in the model.
     */
    @Test
    public void test_execute_Accuracy1() {
        instance.setComment("topcoder comment");
        instance.setQuestionId(1);
        instance.setContestId(1);
        instance.setAction("add");

        // execute the action and verify the return value
        assertEquals("return value from execute method is wrong", Action.SUCCESS, instance.execute());

        // make sure comment was added to model
        assertNotNull("result in model should not be null", instance.getModel().getData("result"));
        assertTrue("result in model is wrong type",
            instance.getModel().getData("result") instanceof SaveSpecificationReviewCommentActionResultData);
        SaveSpecificationReviewCommentActionResultData result =
            (SaveSpecificationReviewCommentActionResultData) instance.getModel().getData("result");
        assertNotNull("UserComment should have been added to model", result.getUserComment());
        assertEquals("comment id is wrong", 3, result.getUserComment().getCommentId());
        assertEquals("comment text is wrong", "topcoder comment", result.getUserComment().getComment());
    }

    /**
     * Accuracy test for execute. Verifies that existing specification review comment is updated successfully
     * and stored in the model.
     */
    @Test
    public void test_execute_Accuracy2() {
        // add a new comment
        test_execute_Accuracy1();

        // prepare to update the comment
        instance.setComment("updated comment");
        instance.setAction("update");
        instance.setCommentId(2);

        // execute the action and verify the return value
        assertEquals("return value from execute method is wrong", Action.SUCCESS, instance.execute());

        // make sure updated comment was added to model
        assertNotNull("result in model should not be null", instance.getModel().getData("result"));
        assertTrue("result in model is wrong type",
            instance.getModel().getData("result") instanceof SaveSpecificationReviewCommentActionResultData);
        SaveSpecificationReviewCommentActionResultData result =
            (SaveSpecificationReviewCommentActionResultData) instance.getModel().getData("result");
        assertNotNull("UserComment should have been added to model", result.getUserComment());
        assertEquals("comment id is wrong", 2, result.getUserComment().getCommentId());
        assertEquals("comment text is wrong", "updated comment", result.getUserComment().getComment());
    }

    /**
     * Failure test for execute. The SpecReviewCommentService hasn't been injected, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure1() {
        instance = new SaveSpecificationReviewCommentAction();
        instance.setAction("add");
        instance.setComment("test");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, SaveSpecificationReviewCommentActionException.class);
    }

    /**
     * Failure test for execute. An error occurred during persistence operation, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure2() {
        MockSpecReviewCommentService.setTriggerException(true);
        instance.setAction("add");
        instance.setComment("test");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, SaveSpecificationReviewCommentActionException.class);
    }

    /**
     * Failure test for execute. The action value is null, so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure3() {
        instance.setAction(null);
        instance.setComment("test");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, SaveSpecificationReviewCommentActionException.class);
    }

    /**
     * Failure test for execute. The action value is not 'add' or 'update', so exception
     * should be stored in model and Action.ERROR should be returned.
     */
    @Test
    public void test_execute_Failure4() {
        instance.setAction("fake");
        instance.setComment("test");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure exception was stored in model and check that it is proper type
        TestHelper.assertModelException(instance, SaveSpecificationReviewCommentActionException.class);
    }

    /**
     * Failure test for execute. The comment value is null, so validation
     * error should be present for that error.
     */
    @Test
    public void test_execute_Failure5() {
        instance.setAction("add");
        instance.setComment(null);
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure error was stored correctly in model
        AggregateDataModel model = instance.getModel();
        assertNotNull("aggregate data model should not be null", model);
        assertNotNull("validation errors should not be null", model
            .getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY));
        ValidationErrors validationErrors =
            (ValidationErrors) model.getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY);
        assertNotNull("errors array in validation errors object should not be null", validationErrors.getErrors());
        assertEquals("validation errors object contains wrong number of error elements", 1, validationErrors
            .getErrors().length);
        TestHelper.assertValidationErrorRecord(validationErrors.getErrors()[0], "comment",
            new String[] {"comment cannot be null/empty."});
    }

    /**
     * Failure test for execute. The comment value is empty, so validation
     * error should be present for that error.
     */
    @Test
    public void test_execute_Failure6() {
        instance.setAction("add");
        instance.setComment("    ");
        assertEquals("return value from execute method is wrong", Action.ERROR, instance.execute());

        // make sure error was stored correctly in model
        AggregateDataModel model = instance.getModel();
        assertNotNull("aggregate data model should not be null", model);
        assertNotNull("validation errors should not be null", model
            .getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY));
        ValidationErrors validationErrors =
            (ValidationErrors) model.getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY);
        assertNotNull("errors array in validation errors object should not be null", validationErrors.getErrors());
        assertEquals("validation errors object contains wrong number of error elements", 1, validationErrors
            .getErrors().length);
        TestHelper.assertValidationErrorRecord(validationErrors.getErrors()[0], "comment",
            new String[] {"comment cannot be null/empty."});
    }

    /**
     * Accuracy test for getQuestionId. Verifies the returned value is correct.
     */
    @Test
    public void test_getQuestionId_Accuracy() {
        int test = 1;
        instance.setQuestionId(test);
        assertEquals("getter is wrong", test, instance.getQuestionId());
    }

    /**
     * Accuracy test for setQuestionId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setQuestionId_Accuracy() {
        long test = 1;
        instance.setQuestionId(test);
        TestHelper.assertFieldEquals(instance.getClass(), test, instance, "questionId");
    }

    /**
     * Accuracy test for getCommentId. Verifies the returned value is correct.
     */
    @Test
    public void test_getCommentId_Accuracy() {
        int test = 1;
        instance.setCommentId(test);
        assertEquals("getter is wrong", test, instance.getCommentId());
    }

    /**
     * Accuracy test for setCommentId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setCommentId_Accuracy() {
        long test = 1;
        instance.setCommentId(test);
        TestHelper.assertFieldEquals(instance.getClass(), test, instance, "commentId");
    }

    /**
     * Accuracy test for getComment. Verifies the returned value is correct.
     */
    @Test
    public void test_getComment_Accuracy() {
        String test = "test";
        instance.setComment(test);
        assertEquals("getter is wrong", test, instance.getComment());
    }

    /**
     * Accuracy test for setComment. Verifies the assigned value is correct.
     */
    @Test
    public void test_setComment_Accuracy() {
        String test = "test";
        instance.setComment(test);
        TestHelper.assertFieldEquals(instance.getClass(), test, instance, "comment");
    }

    /**
     * Accuracy test for getAction. Verifies the returned value is correct.
     */
    @Test
    public void test_getAction_Accuracy() {
        String test = "test";
        instance.setAction(test);
        assertEquals("getter is wrong", test, instance.getAction());
    }

    /**
     * Accuracy test for setAction. Verifies the assigned value is correct.
     */
    @Test
    public void test_setAction_Accuracy() {
        String test = "test";
        instance.setAction(test);
        TestHelper.assertFieldEquals(instance.getClass(), test, instance, "action");
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
