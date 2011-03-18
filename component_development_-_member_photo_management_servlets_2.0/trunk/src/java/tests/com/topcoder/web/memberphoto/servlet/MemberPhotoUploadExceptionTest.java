/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>Unit tests for <code>MemberPhotoUploadException</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoUploadExceptionTest extends TestCase {
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
        return new TestSuite(MemberPhotoUploadExceptionTest.class);
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoUploadException(String)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStr() {
        MemberPhotoUploadException exception = new MemberPhotoUploadException(MESSAGE);
        assertNotNull("Unable to create MemberPhotoUploadException instance.", exception);
        assertTrue("Should be instance of MemberPhotoUploadException.",
            exception instanceof MemberPhotoUploadException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoUploadException(String)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null message.</p>
     */
    public void testCtorStr_Null() {
        MemberPhotoUploadException exception = new MemberPhotoUploadException((String) null);
        assertNotNull("Unable to create MemberPhotoUploadException instance.", exception);
        assertTrue("Should be instance of MemberPhotoUploadException.",
            exception instanceof MemberPhotoUploadException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoUploadException(String, Throwable)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStrThrowable() {
        MemberPhotoUploadException exception = new MemberPhotoUploadException(MESSAGE, CAUSE);
        assertNotNull("Unable to create MemberPhotoUploadException instance.", exception);
        assertTrue("Should be instance of MemberPhotoUploadException.",
            exception instanceof MemberPhotoUploadException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getRootCause());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberPhotoUploadException(String, Throwable)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null message and cause.</p>
     */
    public void testCtorStrThrowable_Null() {
        MemberPhotoUploadException exception = new MemberPhotoUploadException(null, (Throwable) null);
        assertNotNull("Unable to create MemberPhotoUploadException instance.", exception);
        assertTrue("Should be instance of MemberPhotoUploadException.",
            exception instanceof MemberPhotoUploadException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getRootCause());
    }
}
