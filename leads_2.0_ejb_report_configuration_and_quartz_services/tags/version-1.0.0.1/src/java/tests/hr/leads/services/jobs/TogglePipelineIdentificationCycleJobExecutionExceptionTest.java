/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.jobs;

import junit.framework.TestCase;

/**
 * <p>
 * All tests for <code>TogglePipelineIdentificationCycleJobExecutionException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleJobExecutionExceptionTest extends TestCase {

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
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException()}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionException() {
        TogglePipelineIdentificationCycleJobExecutionException exception =
            new TogglePipelineIdentificationCycleJobExecutionException();
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException()}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionCanThrow() {
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleJobExecutionException();
        } catch (TogglePipelineIdentificationCycleJobExecutionException e) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(String)}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionString() {
        TogglePipelineIdentificationCycleJobExecutionException exception =
            new TogglePipelineIdentificationCycleJobExecutionException(
                "error occurs.");
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(String)}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionStringCanThrow() {
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleJobExecutionException("error occurs.");
        } catch (TogglePipelineIdentificationCycleJobExecutionException e) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(String)}
     * .
     * </p>
     * <p>
     * Tests if message is correctly set.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionStringAccuracy() {
        TogglePipelineIdentificationCycleJobExecutionException exception =
            new TogglePipelineIdentificationCycleJobExecutionException(
                "error occurs.");
        assertEquals("invalid message.", "error occurs.", exception
                .getMessage());
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionThrowable() {
        IllegalArgumentException e = new IllegalArgumentException();
        TogglePipelineIdentificationCycleJobExecutionException exception =
            new TogglePipelineIdentificationCycleJobExecutionException(
                e);
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionThrowableCanThrow() {
        IllegalArgumentException e = new IllegalArgumentException();
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleJobExecutionException(e);
        } catch (TogglePipelineIdentificationCycleJobExecutionException e1) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(String, Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the constructor successfully creates the instance.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionStringThrowable() {
        IllegalArgumentException e = new IllegalArgumentException();
        TogglePipelineIdentificationCycleJobExecutionException exception =
            new TogglePipelineIdentificationCycleJobExecutionException(
                "error occurs.", e);
        assertNotNull("The exception should not be null.", exception);
        assertTrue("Should be instance of Exceptioin.",
                exception instanceof Exception);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(String, Throwable)}
     * .
     * </p>
     * <p>
     * Tests if the exception can be thrown and caught.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionStringThrowableCanThrow() {
        IllegalArgumentException e = new IllegalArgumentException();
        boolean caught = false;
        try {
            throw new TogglePipelineIdentificationCycleJobExecutionException("error occurs.", e);
        } catch (TogglePipelineIdentificationCycleJobExecutionException e1) {
            // success
            caught = true;
        }
        assertTrue("The exception should be thrown.", caught);
    }

    /**
     * <p>
     * Tests method:
     * {@link TogglePipelineIdentificationCycleJobExecutionException
     * #TogglePipelineIdentificationCycleJobExecutionException(String, Throwable)}
     * .
     * </p>
     * <p>
     * Tests if message is correctly set.
     * </p>
     */
    public void testTogglePipelineIdentificationCycleJobExecutionExceptionStringThrowableAccuracy() {
        IllegalArgumentException e = new IllegalArgumentException();
        TogglePipelineIdentificationCycleJobExecutionException exception =
            new TogglePipelineIdentificationCycleJobExecutionException(
                "error occurs.", e);
        assertEquals("invalid message.", "error occurs.", exception
                .getMessage());
    }

}
