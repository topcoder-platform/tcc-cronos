/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit tests for the class: ReviewerStatisticsConfigurationException.
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatisticsConfigurationExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>ReviewerStatisticsConfigurationException(String message)</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsConfigurationExceptionAccuracy1() throws Exception {
        ReviewerStatisticsConfigurationException ce = new ReviewerStatisticsConfigurationException("test");
        assertEquals("message is incorrect.", "test", ce.getMessage());
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsConfigurationException(String message, Throwable cause)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsConfigurationExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        ReviewerStatisticsConfigurationException ce = new ReviewerStatisticsConfigurationException("error2", e);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsConfigurationException(String message, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsConfigurationExceptionAccuracy3() throws Exception {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewerStatisticsConfigurationException ce = new ReviewerStatisticsConfigurationException("error2", data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }

    /**
     * Accuracy test of
     * <code>ReviewerStatisticsConfigurationException(String message, Throwable cause, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsConfigurationExceptionAccuracy4() throws Exception {
        Exception e = new Exception("error1");
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewerStatisticsConfigurationException ce = new ReviewerStatisticsConfigurationException("error2", e, data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }
}
