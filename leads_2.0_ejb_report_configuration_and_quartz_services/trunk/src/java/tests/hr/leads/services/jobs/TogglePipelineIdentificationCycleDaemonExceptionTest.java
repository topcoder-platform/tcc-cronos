/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

import junit.framework.TestCase;

/**
 * <p>
 * All tests for <code>LeadsServiceConfigurationException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleDaemonExceptionTest extends
        TestCase {

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException#TogglePipelineIdentificationCycleDaemonException()}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonException() {
        TogglePipelineIdentificationCycleDaemonException exception =
            new TogglePipelineIdentificationCycleDaemonException();
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException#TogglePipelineIdentificationCycleDaemonException()}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionCanThrow() {
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleDaemonException();
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException#
     * TogglePipelineIdentificationCycleDaemonException(String)}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionString() {
        TogglePipelineIdentificationCycleDaemonException exception =
            new TogglePipelineIdentificationCycleDaemonException(
                "error occurs.");
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException#TogglePipelineIdentificationCycleDaemonException(String)}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionStringCanThrow() {
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "error occurs.");
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException
     * #TogglePipelineIdentificationCycleDaemonException(String)}
     * .
     * </p>
     * <p>
     * Tests if message is correctly set.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionStringAccuracy() {
        TogglePipelineIdentificationCycleDaemonException exception =
            new TogglePipelineIdentificationCycleDaemonException(
                "error occurs.");
        assertEquals("invalid message.", "error occurs.", exception
                .getMessage());
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException#
     * TogglePipelineIdentificationCycleDaemonException(Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionThrowable() {
        IllegalArgumentException e = new IllegalArgumentException();
        TogglePipelineIdentificationCycleDaemonException exception =
            new TogglePipelineIdentificationCycleDaemonException(e);
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException
     * #TogglePipelineIdentificationCycleDaemonException(Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionThrowableCanThrow() {
        IllegalArgumentException e = new IllegalArgumentException();
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleDaemonException(e);
        } catch (TogglePipelineIdentificationCycleDaemonException e1) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException
     * #TogglePipelineIdentificationCycleDaemonException(String, Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionStringThrowable() {
        IllegalArgumentException e = new IllegalArgumentException();
        TogglePipelineIdentificationCycleDaemonException exception =
            new TogglePipelineIdentificationCycleDaemonException("error occurs.", e);
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException
     * #TogglePipelineIdentificationCycleDaemonException(String, Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionStringThrowableCanThrow() {
        IllegalArgumentException e = new IllegalArgumentException();
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleDaemonException(
                    "error occurs.", e);
        } catch (TogglePipelineIdentificationCycleDaemonException e1) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleDaemonException
     * #TogglePipelineIdentificationCycleDaemonException(String, Throwable)}
     * .
     * </p>
     * <p>
     * Tests if message is correctly set.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleDaemonExceptionStringThrowableAccuracy() {
        IllegalArgumentException e = new IllegalArgumentException();
        TogglePipelineIdentificationCycleDaemonException exception =
            new TogglePipelineIdentificationCycleDaemonException(
                "error occurs.", e);
        assertEquals("invalid message.", "error occurs.", exception
                .getMessage());
    }

}
