/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence.logging;

import junit.framework.TestCase;

/**
 * <p>
 * Test case for LogMessage.
 * </p>
 * @author TCDEVELOPER
 * @version 1.1
 */
public class LogMessageTest extends TestCase {

    /**
     * <p>
     * The instance for test use.
     * </p>
     */
    LogMessage logMessage;

    /**
     * <p>
     * Test case for {@link LogMessage#LogMessage(Long, String, String, Throwable)},
     * {@link LogMessage#getError()}, {@link LogMessage#getMessage()},
     * {@link LogMessage#getOperator()}, {@link LogMessage#getReviewId()}. </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1() {
        LogMessage logMessage1 =
                new LogMessage(new Long(1), "operator", "message", new Exception("e"));
        assertEquals("id is wrong", new Long(1), logMessage1.getReviewId());
        assertEquals("operator is wrong", "operator", logMessage1.getOperator());
        assertEquals("message is wrong", "message", logMessage1.getMessage());
        assertEquals("exception is wrong", "java.lang.Exception: e", logMessage1.getError()
                .toString());
    }

    /**
     * <p>
     * Test case for {@link LogMessage#LogMessage(Long, String, String)},
     * {@link LogMessage#getError()}, {@link LogMessage#getMessage()},
     * {@link LogMessage#getOperator()}, {@link LogMessage#getReviewId()}. </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2() {
        LogMessage logMessage1 = new LogMessage(new Long(1), "operator", "message");
        assertEquals("id is wrong", new Long(1), logMessage1.getReviewId());
        assertEquals("operator is wrong", "operator", logMessage1.getOperator());
        assertEquals("message is wrong", "message", logMessage1.getMessage());
        assertNull("exception is wrong", logMessage1.getError());
    }

    /**
     * <p>
     * Test case for {@link LogMessage#getLogMessage()}. </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetLogMessage1() {
        LogMessage logMessage1 = new LogMessage(new Long(1), "operator", "message");
        assertEquals("get log message error", "operator: operatorreviewId: 1 - message",
                logMessage1.getLogMessage());
    }

    /**
     * <p>
     * Test case for {@link LogMessage#toString()}. </p>
     * @throws Exception
     *             to JUnit
     */
    public void testtoString() {
        LogMessage logMessage1 = new LogMessage(new Long(1), "operator", "message");
        assertEquals("get log message error", "operator: operatorreviewId: 1 - message",
                logMessage1.toString());
    }

}
