/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>ProjectNotFoundException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectNotFoundExceptionTest extends TestCase {

    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Represents the project id for testing.
     * </p>
     */
    private static final long PROJECT_ID = 1;

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectNotFoundException(String)</code>, expects the instance is
     * created properly.
     * </p>
     */
    public void testCtor1Accuracy() {
        ProjectNotFoundException exception = new ProjectNotFoundException(MESSAGE, PROJECT_ID);

        assertNotNull("Failed to create the ProjectNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the project id is set properly.", PROJECT_ID, exception.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectNotFoundException(String, Throwable)</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor2Accuracy() {
        Throwable cause = new Throwable();
        ProjectNotFoundException exception = new ProjectNotFoundException(MESSAGE, cause, PROJECT_ID);

        assertNotNull("Failed to create the ProjectNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
        assertEquals("Expects the project id is set properly.", PROJECT_ID, exception.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectNotFoundException(String, ExceptionData)</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor3Accuracy() {
        ExceptionData data = new ExceptionData();
        ProjectNotFoundException exception = new ProjectNotFoundException(MESSAGE, data, PROJECT_ID);

        assertNotNull("Failed to create the ProjectNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
        assertEquals("Expects the project id is set properly.", PROJECT_ID, exception.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectNotFoundException(String, Throwable, ExceptionData)</code>,
     * expects the instance is created properly.
     * </p>
     */
    public void testCtor4Accuracy() {
        Throwable cause = new Throwable();
        ExceptionData data = new ExceptionData();
        ProjectNotFoundException exception = new ProjectNotFoundException(MESSAGE, cause, data, PROJECT_ID);

        assertNotNull("Failed to create the ProjectNotFoundException instance.", exception);
        assertEquals("Expects the message is propagated properly.", MESSAGE, exception.getMessage());
        assertEquals("Expects the cause is propagated properly.", cause, exception.getCause());
        assertEquals("Expects the exception data is propagated properly.", data.getErrorCode(), exception
                .getErrorCode());
        assertEquals("Expects the project id is set properly.", PROJECT_ID, exception.getProjectId());
    }

    /**
     * <p>
     * Inheritance test, expects <code>ProjectNotFoundException</code> subclasses
     * <code>DirectServiceFacadeException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Expects ProjectNotFoundException subclass DirectServiceFacadeException.",
                new ProjectNotFoundException(MESSAGE, PROJECT_ID) instanceof DirectServiceFacadeException);
    }

    /**
     * <p>
     * Accuracy test for the <code>getProjectId</code> method, expects the project id is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectIdAccuracy() throws Exception {
        assertEquals("Expects the project id is returned properly.", PROJECT_ID, new ProjectNotFoundException(
                MESSAGE, PROJECT_ID).getProjectId());
    }
}