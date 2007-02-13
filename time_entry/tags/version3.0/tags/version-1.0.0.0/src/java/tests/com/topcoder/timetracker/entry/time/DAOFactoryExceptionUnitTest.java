/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit test cases for DAOFactoryException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DAOFactoryExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(DAOFactoryExceptionUnitTest.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testDAOFactoryException1() {
        DAOFactoryException ce = new DAOFactoryException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate DAOFactoryException.", ce);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testDAOFactoryException2() {
        Throwable cause = new Exception();
        DAOFactoryException ce = new DAOFactoryException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate DAOFactoryException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DAOFactoryException subclasses BaseException.
     * </p>
     */
    public void testDAOFactoryExceptionInheritance() {
        assertTrue("DAOFactoryException does not subclass BaseException.",
            new DAOFactoryException(ERROR_MESSAGE) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DAOFactoryException subclasses BaseException.
     * </p>
     */
    public void testDAOFactoryExceptionInheritance2() {
        assertTrue("DAOFactoryException does not subclass BaseException.",
            new DAOFactoryException(ERROR_MESSAGE, new Exception()) instanceof BaseException);
    }
}
