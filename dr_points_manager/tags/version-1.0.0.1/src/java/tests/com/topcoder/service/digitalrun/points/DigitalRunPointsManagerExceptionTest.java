/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test cases for class DigitalRunPointsManagerException. All the method are tested.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsManagerExceptionTest extends TestCase {

    /** The error message used for testing. */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * An exception instance used to create the DigitalRunPointsManagerException.
     */
    private final Exception cause = new Exception();

    /** An exception data used to create the DigitalRunPointsManagerException. */
    private ExceptionData exceptionData;

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     * @return A test suite will be returned
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsManagerExceptionTest.class);
    }

    /**
     * Sets up testing environment.
     * @throws Exception
     *             when error occurs
     */
    @Override
    public void setUp() throws Exception {
        exceptionData = new ExceptionData();
        exceptionData.setApplicationCode("Application Code");
        exceptionData.setErrorCode("Error Code");
    }

    /**
     * Clears the testing environment.
     * @throws Exception
     *             when error occurs
     */
    @Override
    public void tearDown() throws Exception {
        exceptionData = null;
    }

    /**
     * Test method for DigitalRunPointsManagerException(String message).<br>
     * Target: tests the creation.<br>
     */
    public final void testCtor1_Accuracy() {
        DigitalRunPointsManagerException impl = new DigitalRunPointsManagerException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate DigitalRunPointsManagerException.", impl);
        assertTrue("DigitalRunPointsManagerException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, impl
                .getMessage());
    }

    /**
     * Test method for DigitalRunPointsManagerException(String message, Throwable cause).<br>
     * Target: tests the creation.<br>
     */
    public final void testCtor2_Accuracy() {
        DigitalRunPointsManagerException impl = new DigitalRunPointsManagerException(ERROR_MESSAGE, cause);
        assertNotNull("Unable to instantiate DigitalRunPointsManagerException.", impl);
        assertTrue("DigitalRunPointsManagerException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, impl
                .getMessage());
        assertEquals("The inner exception should match.", cause, impl.getCause());
    }

    /**
     * Test method for DigitalRunPointsManagerException(String message, ExceptionData data).<br>
     * Target: tests the creation.<br>
     */
    public final void testCtor3_Accuracy() {
        DigitalRunPointsManagerException impl = new DigitalRunPointsManagerException(ERROR_MESSAGE,
                exceptionData);
        assertNotNull("Unable to instantiate DigitalRunPointsManagerException.", impl);
        assertTrue("DigitalRunPointsManagerException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, impl
                .getMessage());
        assertEquals("The Application Code should match.", "Application Code", impl.getApplicationCode());
        assertEquals("The Error Code should match.", "Error Code", impl.getErrorCode());
    }

    /**
     * Test method for DigitalRunPointsManagerException(String message, Throwable cause,
     * ExceptionData data).<br>
     * Target: tests the creation.<br>
     */
    public void testCtor4_Accuracy() {
        DigitalRunPointsManagerException impl = new DigitalRunPointsManagerException(ERROR_MESSAGE, cause,
                exceptionData);
        assertNotNull("Unable to instantiate DigitalRunPointsManagerException.", impl);
        assertTrue("DigitalRunPointsManagerException should subclass BaseCriticalException",
                impl instanceof BaseCriticalException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, impl
                .getMessage());
        assertEquals("The inner exception should match.", cause, impl.getCause());
        assertEquals("The Application Code should match.", "Application Code", impl.getApplicationCode());
        assertEquals("The Error Code should match.", "Error Code", impl.getErrorCode());
    }

}
