/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for <code>BuildFileProvisionException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BuildFileProvisionExceptionTest extends TestCase {
    /**
     * <p>
     * Represents the <code>ExceptionData</code> instance used to test against.
     * </p>
     */
    private ExceptionData data;

    /**
     * <p>
     * Represents the <code>Throwable</code> instance used to test against.
     * </p>
     */
    private Throwable cause;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        data = new ExceptionData();
        cause = new NullPointerException();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        data = null;
        cause = null;
    }

    /**
     * <p>
     * Gets the test suite for <code>BuildFileProvisionException</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>BuildFileProvisionException</code> class.
     */
    public static Test suite() {
        return new TestSuite(BuildFileProvisionExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BuildFileProvisionException(String)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtorWithMsg() {
        BuildFileProvisionException exception = new BuildFileProvisionException("test_message");
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test_message'", "test_message", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BuildFileProvisionException(String,Throwable)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtorWithMsgCause() {
        BuildFileProvisionException exception = new BuildFileProvisionException("test_message", cause);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test_message'", "test_message", exception.getMessage());
        assertEquals("Cause should be set correctly", cause, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BuildFileProvisionException(String,ExceptionData)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtorWithMsgData() {
        BuildFileProvisionException exception = new BuildFileProvisionException("test_message", data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test_message'", "test_message", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BuildFileProvisionException(String,Throwable,ExceptionData)</code>.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtorWithMsgCauseData() {
        BuildFileProvisionException exception = new BuildFileProvisionException("test_message", cause, data);
        assertNotNull("Instance should be created", exception);
        assertEquals("Return value should be 'test_message'", "test_message", exception.getMessage());
        assertEquals("Cause should be set correctly", cause, exception.getCause());
    }
}
