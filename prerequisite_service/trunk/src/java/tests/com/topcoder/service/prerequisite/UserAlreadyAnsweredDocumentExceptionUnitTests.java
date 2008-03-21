/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import com.topcoder.service.prerequisite.ejb.UserAlreadyAnsweredDocumentFault;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link UserAlreadyAnsweredDocumentException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserAlreadyAnsweredDocumentExceptionUnitTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Throwable CAUSE = new Exception("UnitTest");

    /**
     * <p>
     * Represents the fault info.
     * </p>
     */
    private static final UserAlreadyAnsweredDocumentFault FAULT_INFO = new UserAlreadyAnsweredDocumentFault();

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UserAlreadyAnsweredDocumentExceptionUnitTests.class);
    }

    /**
     * <p>
     * Unit test <code>UserAlreadyAnsweredDocumentException(String, UserAlreadyAnsweredDocumentFault)</code>
     * constructor.
     * </p>
     * <p>
     * The detail error message and the fault info should be correct.
     * </p>
     */
    public void testUserAlreadyAnsweredDocumentException_accuracy1() {
        // Construct UserAlreadyAnsweredDocumentException with a detail message and fault info
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE,
                FAULT_INFO);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertEquals("Incorrect user id.", -1, exception.getUserIdAlreadyAnsweredDocument());
    }

    /**
     * <p>
     * Unit test <code>UserAlreadyAnsweredDocumentException(String, UserAlreadyAnsweredDocumentFault, Throwable)</code>
     * constructor.
     * </p>
     * <p>
     * The detail error message, fault info and the original cause of error should be correct.
     * </p>
     */
    public void testUserAlreadyAnsweredDocumentException_accuracy2() {
        // Construct UserAlreadyAnsweredDocumentException with a detail message, fault info and a cause
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE,
                FAULT_INFO, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertEquals("Incorrect user id.", -1, exception.getUserIdAlreadyAnsweredDocument());
    }

    /**
     * <p>
     * Unit test <code>UserAlreadyAnsweredDocumentException(String, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the user id should be correct.
     * </p>
     */
    public void testUserAlreadyAnsweredDocumentException_accuracy3() {
        // Construct UserAlreadyAnsweredDocumentException with a detail message
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertEquals("Incorrect user id.", 1, exception.getUserIdAlreadyAnsweredDocument());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>UserAlreadyAnsweredDocumentException(String, Throwable, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the original cause of error and user id should be correct.
     * </p>
     */
    public void testUserAlreadyAnsweredDocumentException_accuracy4() {
        // Construct UserAlreadyAnsweredDocumentException with a detail message, a cause and user id.
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE,
                CAUSE, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertEquals("Incorrect user id.", 1, exception.getUserIdAlreadyAnsweredDocument());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>UserAlreadyAnsweredDocumentException(String, ExceptionData, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the exception data and user id should be correct.
     * </p>
     */
    public void testUserAlreadyAnsweredDocumentException_accuracy5() {
        // Construct UserAlreadyAnsweredDocumentException with a detail message, a cause and user id
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE,
                EXCEPTION_DATA, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
        assertEquals("Incorrect user id.", 1, exception.getUserIdAlreadyAnsweredDocument());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>UserAlreadyAnsweredDocumentException(String, Throwable, ExceptionData, long)</code>
     * constructor.
     * </p>
     * <p>
     * The detail error message, the cause exception, the exception data and user id should be correct.
     * </p>
     */
    public void testUserAlreadyAnsweredDocumentException_accuracy6() {
        // Construct UserAlreadyAnsweredDocumentException with a detail message, a cause, exception data and user id
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE,
                CAUSE, EXCEPTION_DATA, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertEquals("Incorrect user id.", 1, exception.getUserIdAlreadyAnsweredDocument());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test for <code>UserAlreadyAnsweredDocumentException#getUserIdAlreadyAnsweredDocument()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserIdAlreadyAnsweredDocument_accuracy() throws Exception {
        // Construct UserAlreadyAnsweredDocumentException with a detail message and fault info
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE,
                FAULT_INFO);
        assertEquals("Incorrect user id.", -1, exception.getUserIdAlreadyAnsweredDocument());

        UnitTestHelper.setFieldValue(exception, "userIdAlreadyAnsweredDocument", 1L);
        assertEquals("Incorrect user id.", 1, exception.getUserIdAlreadyAnsweredDocument());
    }

    /**
     * <p>
     * Unit test for <code>UserAlreadyAnsweredDocumentException#getFaultInfo()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFaultInfo_accuracy() throws Exception {
        // Construct UserAlreadyAnsweredDocumentException with a detail message
        UserAlreadyAnsweredDocumentException exception = new UserAlreadyAnsweredDocumentException(DETAIL_MESSAGE, 1);
        assertNull("The fault info should be null.", exception.getFaultInfo());

        UnitTestHelper.setFieldValue(exception, "faultInfo", FAULT_INFO);
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
    }
}
