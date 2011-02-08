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
 * Tests the functionality of {@link MemberPhotoDAOException} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class MemberPhotoDAOExceptionTest extends TestCase {

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
        return new TestSuite(MemberPhotoDAOExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoDAOException#MemberPhotoDAOException(String)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoDAOException() {
        MemberPhotoDAOException memberPhotoDAOException = new MemberPhotoDAOException(ERROR_MSG);
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException);
        assertEquals("MemberPhotoDAOException creation failed", ERROR_MSG, memberPhotoDAOException.getMessage());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoDAOException#MemberPhotoDAOException(String, Throwable)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoDAOException1() {
        MemberPhotoDAOException memberPhotoDAOException = new MemberPhotoDAOException(ERROR_MSG, exception);
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException);
        assertEquals("MemberPhotoDAOException creation failed", ERROR_MSG, memberPhotoDAOException.getMessage());
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException.getCause());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoDAOException#MemberPhotoDAOException(String, ExceptionData)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoDAOException2() {
        MemberPhotoDAOException memberPhotoDAOException = new MemberPhotoDAOException(ERROR_MSG, data);
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException);
        assertEquals("MemberPhotoDAOException creation failed", ERROR_MSG, memberPhotoDAOException.getMessage());
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException.getInformation("KEY"));
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoDAOException#MemberPhotoDAOException(String, Throwable, ExceptionData)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoDAOException3() {
        MemberPhotoDAOException memberPhotoDAOException = new MemberPhotoDAOException(ERROR_MSG, exception, data);
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException);
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException.getCause());
        assertEquals("MemberPhotoDAOException creation failed", ERROR_MSG, memberPhotoDAOException.getMessage());
        assertNotNull("MemberPhotoDAOException creation failed", memberPhotoDAOException.getInformation("KEY"));
    }
}
