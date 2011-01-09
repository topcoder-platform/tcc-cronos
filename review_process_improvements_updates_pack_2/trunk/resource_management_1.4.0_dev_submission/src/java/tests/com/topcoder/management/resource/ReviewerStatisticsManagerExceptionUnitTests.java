/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit tests for the class: ReviewerStatisticsManagerException.
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatisticsManagerExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>ReviewerStatisticsManagerException(String message)</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerExceptionAccuracy1() throws Exception {
        ReviewerStatisticsManagerException ce = new ReviewerStatisticsManagerException("test");
        assertEquals("message is incorrect.", "test", ce.getMessage());
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsManagerException(String message, Throwable cause)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        ReviewerStatisticsManagerException ce = new ReviewerStatisticsManagerException("error2", e);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsManagerException(String message, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerExceptionAccuracy3() throws Exception {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewerStatisticsManagerException ce = new ReviewerStatisticsManagerException("error2", data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }

    /**
     * Accuracy test of
     * <code>ReviewerStatisticsManagerException(String message, Throwable cause, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerExceptionAccuracy4() throws Exception {
        Exception e = new Exception("error1");
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewerStatisticsManagerException ce = new ReviewerStatisticsManagerException("error2", e, data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }

}
