/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>ContestNotFoundException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestNotFoundExceptionTest extends TestCase {

    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Represents the contest id for testing.
     * </p>
     */
    private static final long CONTEST_ID = 1;

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestNotFoundException(String)</code>, expects the instance is
     * created properly.
     * </p>
     */
    public void testCtor1Accuracy() {
        ContestNotFoundException exception = new ContestNotFoundException(MESSAGE, CONTEST_ID);

        assertNotNull("Failed to create the ContestNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the contest id is set properly.", CONTEST_ID, exception.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestNotFoundException(String, Throwable)</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor2Accuracy() {
        Throwable cause = new Throwable();
        ContestNotFoundException exception = new ContestNotFoundException(MESSAGE, cause, CONTEST_ID);

        assertNotNull("Failed to create the ContestNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
        assertEquals("Expects the contest id is set properly.", CONTEST_ID, exception.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestNotFoundException(String, ExceptionData)</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor3Accuracy() {
        ExceptionData data = new ExceptionData();
        ContestNotFoundException exception = new ContestNotFoundException(MESSAGE, data, CONTEST_ID);

        assertNotNull("Failed to create the ContestNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
        assertEquals("Expects the contest id is set properly.", CONTEST_ID, exception.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestNotFoundException(String, Throwable, ExceptionData)</code>,
     * expects the instance is created properly.
     * </p>
     */
    public void testCtor4Accuracy() {
        Throwable cause = new Throwable();
        ExceptionData data = new ExceptionData();
        ContestNotFoundException exception = new ContestNotFoundException(MESSAGE, cause, data, CONTEST_ID);

        assertNotNull("Failed to create the ContestNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
        assertEquals("Expects the contest id is set properly.", CONTEST_ID, exception.getContestId());
    }

    /**
     * <p>
     * Inheritance test, expects <code>ContestNotFoundException</code> subclasses
     * <code>DirectServiceFacadeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Expects ContestNotFoundException subclass DirectServiceFacadeException.",
                new ContestNotFoundException(MESSAGE, CONTEST_ID) instanceof DirectServiceFacadeException);
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestId</code> method, expects the contest id is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestIdAccuracy() throws Exception {
        assertEquals("Expects the contest id is returned properly.", CONTEST_ID, new ContestNotFoundException(
                MESSAGE, CONTEST_ID).getContestId());
    }
}