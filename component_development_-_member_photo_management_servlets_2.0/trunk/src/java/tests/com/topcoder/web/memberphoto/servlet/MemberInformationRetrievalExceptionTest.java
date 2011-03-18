/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>Unit tests for <code>MemberInformationRetrievalException</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberInformationRetrievalExceptionTest extends TestCase {
    /**
     * <p>The error message used for testing.</p>
     */
    private static final String MESSAGE = "the error message";

    /**
     * <p>The ExceptionData used for testing.</p>
     */
    private static final ExceptionData DATA = new ExceptionData();

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
        return new TestSuite(MemberInformationRetrievalExceptionTest.class);
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStr() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException(MESSAGE);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null message.</p>
     */
    public void testCtorStr_Null() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException((String) null);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String,
     * ExceptionData)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStrExp() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException(MESSAGE, DATA);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String,
     * ExceptionData)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null message and exception data.</p>
     */
    public void testCtorStrExp_Null() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException(null,
                (ExceptionData) null);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String, Throwable)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStrThrowable() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException(MESSAGE, CAUSE);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String, Throwable)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null message and cause.</p>
     */
    public void testCtorStrThrowable_Null() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException(null, (Throwable) null);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String, Throwable,
     * ExceptionData)</code>.</p>
     *  <p>Verify that the exception can be created successfully.</p>
     */
    public void testCtorStrThrowableExp() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException(MESSAGE, CAUSE, DATA);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>Accuracy test for constructor <code>MemberInformationRetrievalException(String, Throwable,
     * ExceptionData)</code>.</p>
     *  <p>Verify that the exception can be created successfully with null arguments.</p>
     */
    public void testCtorStrThrowableExp_Null() {
        MemberInformationRetrievalException exception = new MemberInformationRetrievalException(null, null, null);
        assertNotNull("Unable to create MemberInformationRetrievalException instance.", exception);
        assertTrue("Should be instance of MemberInformationRetrievalException.",
            exception instanceof MemberInformationRetrievalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
