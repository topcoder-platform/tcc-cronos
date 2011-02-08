/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.manager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Tests the functionality of {@link MemberPhotoManagementException} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class MemberPhotoManagementExceptionTest extends TestCase {

    /**
     * Error message used for testing.
     */
    private static final String ERROR_MSG = "Unit test error";

    /**
     * The exception data used for testing.
     */
    private ExceptionData data;

    /**
     * The inner exception used.
     */
    private Exception exception;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        exception = new Exception();
        data = new ExceptionData();
        data.setInformation("KEY", "VALUE");
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoManagementExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagementException#MemberPhotoManagementException(String)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagementException() {
        MemberPhotoManagementException memberPhotoManagementException = new MemberPhotoManagementException(
                ERROR_MSG);
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException);
        assertEquals("MemberPhotoManagementException creation failed", ERROR_MSG, memberPhotoManagementException
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoManagementException#MemberPhotoManagementException(String, Throwable)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagementException1() {
        MemberPhotoManagementException memberPhotoManagementException = new MemberPhotoManagementException(
                ERROR_MSG, exception);
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException);
        assertEquals("MemberPhotoManagementException creation failed", ERROR_MSG, memberPhotoManagementException
                .getMessage());
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException.getCause());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link MemberPhotoManagementException#MemberPhotoManagementException(String, ExceptionData)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagementException2() {
        MemberPhotoManagementException memberPhotoManagementException = new MemberPhotoManagementException(
                ERROR_MSG, data);
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException);
        assertEquals("MemberPhotoManagementException creation failed", ERROR_MSG, memberPhotoManagementException
                .getMessage());
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException
                .getInformation("KEY"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link MemberPhotoManagementException#MemberPhotoManagementException(String, Throwable, ExceptionData)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoManagementException3() {
        MemberPhotoManagementException memberPhotoManagementException = new MemberPhotoManagementException(
                ERROR_MSG, exception, data);
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException);
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException.getCause());
        assertEquals("MemberPhotoManagementException creation failed", ERROR_MSG, memberPhotoManagementException
                .getMessage());
        assertNotNull("MemberPhotoManagementException creation failed", memberPhotoManagementException
                .getInformation("KEY"));
    }
}
