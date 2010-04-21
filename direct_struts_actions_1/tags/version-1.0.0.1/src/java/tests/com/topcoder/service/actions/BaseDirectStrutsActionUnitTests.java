/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import com.opensymphony.xwork2.Action;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link BaseDirectStrutsAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class BaseDirectStrutsActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>BaseDirectStrutsAction</code> instance.
     * </p>
     */
    private BaseDirectStrutsAction baseDirectStrutsAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(BaseDirectStrutsActionUnitTests.class);
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

        baseDirectStrutsAction = new BaseDirectStrutsAction() {

            /**
             * <p>
             * Represents the unique serial version id.
             * </p>
             */
            private static final long serialVersionUID = -4633766887769883177L;

            @Override
            protected void executeAction() throws Exception {
            }
        };

        baseDirectStrutsAction.prepare();
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
        baseDirectStrutsAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>BaseDirectStrutsAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", baseDirectStrutsAction);

        assertTrue("invalid super class.", baseDirectStrutsAction instanceof AbstractAction);
    }

    /**
     * <p>
     * Tests the <code>prepare()</code> method.
     * </p>
     * <p>
     * The model field should be initialized.
     * </p>
     */
    public void testPrepare() {
        assertNotNull("model field not initialized.", baseDirectStrutsAction.getModel());
    }

    /**
     * <p>
     * Tests the <code>getResult()</code> method.
     * </p>
     * <p>
     * By default, there is no result present.
     * </p>
     */
    public void testGetResult_default() {
        assertNull("The result should be null", baseDirectStrutsAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>setResult(Object)</code> method.
     * </p>
     * <p>
     * The specified result object should be set internally.
     * </p>
     */
    public void testSetResult_accuracy1() {
        baseDirectStrutsAction.setResult(null);

        assertNull("The result should be null", baseDirectStrutsAction.getResult());

    }

    /**
     * <p>
     * Tests the <code>setResult(Object)</code> method.
     * </p>
     * <p>
     * The specified result object should be set internally.
     * </p>
     */
    public void testSetResult_accuracy2() {
        Object obj = new Object();
        baseDirectStrutsAction.setResult(obj);

        assertSame("The result is not set.", obj, baseDirectStrutsAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>execute()</code> method.
     * </p>
     * <p>
     * If no exception thrown, no result will be present, and the return value should {@link Action#SUCCESS}.
     * </p>
     */
    public void testExecute_accuracy1() {
        String result = baseDirectStrutsAction.execute();

        assertEquals("incorrect return.", Action.SUCCESS, result);
        assertNull("The result should not be present", baseDirectStrutsAction.getResult());
    }

    /**
     * <p>
     * Tests the <code>execute()</code> method.
     * </p>
     * <p>
     * If exception raised, the exception should be set as result, and the return value should {@link Action#SUCCESS}.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecute_accuracy2() throws Exception {
        final Exception exception = new Exception();
        baseDirectStrutsAction = new BaseDirectStrutsAction() {

            /**
             * <p>
             * Represents the unique serial version id.
             * </p>
             */
            private static final long serialVersionUID = -4633766887769883177L;

            @Override
            protected void executeAction() throws Exception {
                throw exception;
            }
        };
        baseDirectStrutsAction.prepare();

        String result = baseDirectStrutsAction.execute();

        assertEquals("incorrect return.", Action.SUCCESS, result);
        assertSame("The result should be present", exception, baseDirectStrutsAction.getResult());
    }
}
