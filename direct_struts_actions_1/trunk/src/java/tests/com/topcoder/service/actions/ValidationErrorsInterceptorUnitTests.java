/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.XWorkTestCase;
import com.opensymphony.xwork2.mock.MockActionInvocation;

/**
 * <p>
 * Unit test for <code>{@link ValidationErrorsInterceptor}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class ValidationErrorsInterceptorUnitTests extends XWorkTestCase {

    /**
     * <p>
     * Represents the <code>ValidationErrorsInterceptor</code> instance.
     * </p>
     */
    private ValidationErrorsInterceptor validationErrorsInterceptor;

    /**
     * <p>
     * Represents the parameters.
     * </p>
     */
    private Map<String, Object> params;

    /**
     * <p>
     * Represents the session.
     * </p>
     */
    private Map<String, Object> session;

    /**
     * <p>
     * Represents the context for action.
     * </p>
     */
    private ActionContext actionContext;

    /**
     * <p>
     * Represents mocked action invocation.
     * </p>
     */
    private MockActionInvocation mockActionInvocation;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ValidationErrorsInterceptorUnitTests.class);
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

        validationErrorsInterceptor = new ValidationErrorsInterceptor();
        validationErrorsInterceptor.init();

        params = new HashMap<String, Object>();
        session = new HashMap<String, Object>();

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put(ActionContext.PARAMETERS, params);
        ctx.put(ActionContext.SESSION, session);
        actionContext = new ActionContext(ctx);

        mockActionInvocation = new MockActionInvocation();
        mockActionInvocation.setInvocationContext(actionContext);
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
        validationErrorsInterceptor.destroy();
        validationErrorsInterceptor = null;
        actionContext = null;
        params = null;
        session = null;
        mockActionInvocation = null;
    }

    /**
     * <p>
     * Tests the <code>ValidationErrorsInterceptor()</code> constructor.
     * </p>
     * <p>
     * Instance should be created successfully all the time.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Should never null.", validationErrorsInterceptor);
    }

    /**
     * <p>
     * Tests the <code>intercept(ActionInvocation)</code> method.
     * </p>
     * <p>
     * If the actionInvocation is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testIntercept_null_actionInvocation() throws Exception {
        try {
            validationErrorsInterceptor.intercept(null);

            fail("If the actionInvocation is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>intercept(ActionInvocation)</code> method.
     * </p>
     * <p>
     * If the action is not BaseDirectStrutsAction type, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("serial")
    public void testIntercept_notBaseDirectStrutsAction() throws Exception {
        mockActionInvocation.setAction(new AbstractAction() {
        });
        try {
            validationErrorsInterceptor.intercept(mockActionInvocation);

            fail("If the action is not BaseDirectStrutsAction type, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>intercept(ActionInvocation)</code> method.
     * </p>
     * <p>
     * if there are no validation errors, the result should not be set.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("serial")
    public void testIntercept_noValidationErrors() throws Exception {
        BaseDirectStrutsAction baseDirectStrutsAction = new BaseDirectStrutsAction() {

            @Override
            protected void executeAction() throws Exception {
            }
        };
        baseDirectStrutsAction.prepare();

        mockActionInvocation.setAction(baseDirectStrutsAction);

        validationErrorsInterceptor.intercept(mockActionInvocation);

        assertNull("the result should be null.", baseDirectStrutsAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>intercept(ActionInvocation)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("serial")
    public void testIntercept_withValidataionErrors1() throws Exception {
        BaseDirectStrutsAction baseDirectStrutsAction = new BaseDirectStrutsAction() {

            @Override
            protected void executeAction() throws Exception {
            }
        };
        baseDirectStrutsAction.prepare();
        for (int i = 1; i < 5; i++) {
            baseDirectStrutsAction.addFieldError("field" + i, "invalid field value");
        }

        mockActionInvocation.setAction(baseDirectStrutsAction);

        validationErrorsInterceptor.intercept(mockActionInvocation);

        assertNotNull("the result shouldn't be null.", baseDirectStrutsAction.getResult());

        assertTrue("the result type is incorrrect.", baseDirectStrutsAction.getResult() instanceof ValidationErrors);
        assertEquals("incorrect number of error records", 4, ((ValidationErrors) baseDirectStrutsAction.getResult())
                .getErrors().length);
    }


    /**
     * <p>
     * Tests the <code>intercept(ActionInvocation)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("serial")
    public void testIntercept_withValidataionErrors2() throws Exception {
        BaseDirectStrutsAction baseDirectStrutsAction = new BaseDirectStrutsAction() {

            @Override
            protected void executeAction() throws Exception {
            }
        };
        baseDirectStrutsAction.prepare();
        for (int i = 1; i < 5; i++) {
            baseDirectStrutsAction.addFieldError("field", "invalid field value");
        }

        mockActionInvocation.setAction(baseDirectStrutsAction);

        validationErrorsInterceptor.intercept(mockActionInvocation);

        assertNotNull("the result shouldn't be null.", baseDirectStrutsAction.getResult());

        assertTrue("the result type is incorrrect.", baseDirectStrutsAction.getResult() instanceof ValidationErrors);
        assertEquals("incorrect number of error records", 1, ((ValidationErrors) baseDirectStrutsAction.getResult())
                .getErrors().length);
    }
}
