/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.accuracytests;

import com.topcoder.service.studio.submission.SubmissionManagementConfigurationException;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;


/**
 * The accuracy test for the exception {@link SubmissionManagementConfigurationException}.
 *
 * @author KLW
 * @version 1.0
 */
public class SubmissionManagementConfigurationExceptionAccuracyTest extends TestCase {
    /**
     * The accuracy test for the constructor
     * {@link SubmissionManagementConfigurationException#SubmissionManagementConfigurationException(String)}.
     */
    public void testCtor_1() {
        String message = "test";
        SubmissionManagementConfigurationException ep = new SubmissionManagementConfigurationException(message);
        assertNotNull("The instance should not be null.", ep);
        assertEquals("The message of the exception is incorrect.", message, ep.getMessage());
    }

    /**
     * The accuracy test for the constructor
     * {@link SubmissionManagementConfigurationException#SubmissionManagementConfigurationException(String, Throwable)}.
     */
    public void testCtor_2() {
        Throwable cause = new Exception();
        String message = "test";
        SubmissionManagementConfigurationException ep = new SubmissionManagementConfigurationException(message, cause);
        assertNotNull("The instance should not be null.", ep);
        assertEquals("The message of the exception is incorrect.", message, ep.getMessage());
        assertEquals("The inner cause of this exception is incorrect.", cause, ep.getCause());
    }

    /**
     * The accuracy test for the constructor
     * {@link SubmissionManagementConfigurationException#SubmissionManagementConfigurationException(String, com.topcoder.util.errorhandling.ExceptionData)}.
     */
    public void testCtor_3() {
        ExceptionData data = new ExceptionData();
        data.setErrorCode("888888");

        String message = "test";
        SubmissionManagementConfigurationException ep = new SubmissionManagementConfigurationException(message, data);
        assertNotNull("The instance should not be null.", ep);
        assertEquals("The message of the exception is incorrect.", message, ep.getMessage());
        assertEquals("The exception data of this exception is incorrect.", "888888", ep.getErrorCode());
    }

    /**
     * The accuracy test for the constructor
     * {@link SubmissionManagementConfigurationException#SubmissionManagementConfigurationException(String, Throwable, ExceptionData)}.
     */
    public void testCtor_4() {
        Throwable cause = new Exception();
        ExceptionData data = new ExceptionData();
        data.setErrorCode("888888");

        String message = "test";
        SubmissionManagementConfigurationException ep = new SubmissionManagementConfigurationException(message, cause,
                data);
        assertNotNull("The instance should not be null.", ep);
        assertEquals("The message of the exception is incorrect.", message, ep.getMessage());
        assertEquals("The inner cause of this exception is incorrect.", cause, ep.getCause());
        assertEquals("The exception data of this exception is incorrect.", "888888", ep.getErrorCode());
    }
}
