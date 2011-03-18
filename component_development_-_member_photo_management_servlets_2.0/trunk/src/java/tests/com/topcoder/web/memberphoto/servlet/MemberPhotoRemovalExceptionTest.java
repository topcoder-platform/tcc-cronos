/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>Unit tests for <code>MemberPhotoRemovalException</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoRemovalExceptionTest extends TestCase {
    /**
     * <p>The error message used for testing.</p>
     */
    private static final String MESSAGE = "the error message";

    /**
     * <p>The inner exception for testing.</p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoRemovalExceptionTest.class);
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoRemovalException(String)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStr() {
        MemberPhotoRemovalException exception = new MemberPhotoRemovalException(MESSAGE);
        assertNotNull("Unable to create MemberPhotoRemovalException instance.", exception);
        assertTrue("Should be instance of MemberPhotoRemovalException.",
            exception instanceof MemberPhotoRemovalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoRemovalException(String)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null message.</p>
     */
    public void testCtorStr_Null() {
        MemberPhotoRemovalException exception = new MemberPhotoRemovalException((String) null);
        assertNotNull("Unable to create MemberPhotoRemovalException instance.", exception);
        assertTrue("Should be instance of MemberPhotoRemovalException.",
            exception instanceof MemberPhotoRemovalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoRemovalException(String, Throwable)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStrThrowable() {
        MemberPhotoRemovalException exception = new MemberPhotoRemovalException(MESSAGE, CAUSE);
        assertNotNull("Unable to create MemberPhotoRemovalException instance.", exception);
        assertTrue("Should be instance of MemberPhotoRemovalException.",
            exception instanceof MemberPhotoRemovalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getRootCause());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoRemovalException(String, Throwable)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null message and cause.</p>
     */
    public void testCtorStrThrowable_Null() {
        MemberPhotoRemovalException exception = new MemberPhotoRemovalException(null, (Throwable) null);
        assertNotNull("Unable to create MemberPhotoRemovalException instance.", exception);
        assertTrue("Should be instance of MemberPhotoRemovalException.",
            exception instanceof MemberPhotoRemovalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getRootCause());
    }
}
