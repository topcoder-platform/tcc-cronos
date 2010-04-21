/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.opensymphony.xwork2.XWorkTestCase;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.DelegatingValidatorContext;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * <p>
 * Unit test for <code>{@link DateAfterCurrentDateValidator}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class DateAfterCurrentDateValidatorUnitTests extends XWorkTestCase {

    /**
     * <p>
     * Represents the <code>DateAfterCurrentDateValidator</code> instance.
     * </p>
     */
    private DateAfterCurrentDateValidator dateAfterCurrentDateValidator;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DateAfterCurrentDateValidatorUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        dateAfterCurrentDateValidator = new DateAfterCurrentDateValidator();
        dateAfterCurrentDateValidator.setValueStack(ActionContext.getContext().getValueStack());
        dateAfterCurrentDateValidator.setValidatorContext(new DelegatingValidatorContext(new ValidationAwareSupport()));

    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        dateAfterCurrentDateValidator = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>DateAfterCurrentDateValidator()</code> constructor.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should be created.", dateAfterCurrentDateValidator);

        assertTrue("incorrect super class.", dateAfterCurrentDateValidator instanceof FieldValidatorSupport);
    }

    /**
     * <p>
     * Tests <code>validate(Object)</code> method.
     * </p>
     * <p>
     * Nothing will be done, if not get the value.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testValidate_notFound() throws Exception {
        SaveDraftContestAction saveDraftContestAction = new SaveDraftContestAction();

        ValueStack stack = ActionContext.getContext().getValueStack();
        stack.push(saveDraftContestAction);
        ActionContext.getContext().setValueStack(stack);

        dateAfterCurrentDateValidator.setFieldName("startDate");
        dateAfterCurrentDateValidator.validate(saveDraftContestAction);

        assertFalse("Should not have any field error", dateAfterCurrentDateValidator.getValidatorContext()
                .hasFieldErrors());
    }

    /**
     * <p>
     * Tests <code>validate(Object)</code> method.
     * </p>
     * <p>
     * Nothing will be done, if the field value is not Date type.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testValidate_notDate() throws Exception {
        SaveDraftContestAction saveDraftContestAction = new SaveDraftContestAction();
        ValueStack stack = ActionContext.getContext().getValueStack();
        stack.push(saveDraftContestAction);
        ActionContext.getContext().setValueStack(stack);

        dateAfterCurrentDateValidator.setFieldName("projectId");
        dateAfterCurrentDateValidator.validate(saveDraftContestAction);

        assertFalse("Should not have any field error", dateAfterCurrentDateValidator.getValidatorContext()
                .hasFieldErrors());

    }

    /**
     * <p>
     * Tests <code>validate(Object)</code> method.
     * </p>
     * <p>
     * If the date is before the current date, should add field error.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testValidate_before() throws Exception {
        SaveDraftContestAction saveDraftContestAction = new SaveDraftContestAction();
        saveDraftContestAction.setStartDate(new Date(System.currentTimeMillis() - 100));

        ValueStack stack = ActionContext.getContext().getValueStack();
        stack.push(saveDraftContestAction);
        ActionContext.getContext().setValueStack(stack);

        dateAfterCurrentDateValidator.setFieldName("startDate");
        dateAfterCurrentDateValidator.validate(saveDraftContestAction);

        assertTrue("field error is not set", dateAfterCurrentDateValidator.getValidatorContext().hasFieldErrors());
    }

    /**
     * <p>
     * Tests <code>validate(Object)</code> method.
     * </p>
     * <p>
     * If the date is after the current date, it is valid.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testValidate_after() throws Exception {
        SaveDraftContestAction saveDraftContestAction = new SaveDraftContestAction();
        saveDraftContestAction.setStartDate(new Date(System.currentTimeMillis() + 3600));

        ValueStack stack = ActionContext.getContext().getValueStack();
        stack.push(saveDraftContestAction);
        ActionContext.getContext().setValueStack(stack);

        dateAfterCurrentDateValidator.setFieldName("startDate");
        dateAfterCurrentDateValidator.validate(saveDraftContestAction);

        assertFalse("Should not have any field error", dateAfterCurrentDateValidator.getValidatorContext()
                .hasFieldErrors());
    }
}
