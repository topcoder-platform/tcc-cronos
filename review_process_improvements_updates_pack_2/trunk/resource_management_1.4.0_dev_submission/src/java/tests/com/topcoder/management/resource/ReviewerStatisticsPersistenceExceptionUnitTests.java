/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit tests for the class: ReviewerStatisticsPersistenceException.
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatisticsPersistenceExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>ReviewerStatisticsPersistenceException(String message)</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsPersistenceExceptionAccuracy1() throws Exception {
        ReviewerStatisticsPersistenceException ce = new ReviewerStatisticsPersistenceException("test");
        assertEquals("message is incorrect.", "test", ce.getMessage());
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsPersistenceException(String message, Throwable cause)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsPersistenceExceptionAccuracy2() throws Exception {
        Exception e = new Exception("error1");
        ReviewerStatisticsPersistenceException ce = new ReviewerStatisticsPersistenceException("error2", e);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsPersistenceException(String message, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsPersistenceExceptionAccuracy3() throws Exception {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewerStatisticsPersistenceException ce = new ReviewerStatisticsPersistenceException("error2", data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }

    /**
     * Accuracy test of
     * <code>ReviewerStatisticsPersistenceException(String message, Throwable cause, ExcetptionData data)</code>
     * constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsPersistenceExceptionAccuracy4() throws Exception {
        Exception e = new Exception("error1");
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");
        ReviewerStatisticsPersistenceException ce = new ReviewerStatisticsPersistenceException("error2", e, data);
        assertEquals("message is incorrect.", "error2", ce.getMessage());
        assertEquals("cause is incorrect.", e, ce.getCause());
        assertEquals("data is incorrect.", "code", ce.getApplicationCode());
    }
}
