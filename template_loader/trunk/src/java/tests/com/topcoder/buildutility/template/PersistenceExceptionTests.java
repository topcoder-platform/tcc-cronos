/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>Unit test cases for PersistenceException.</p>
 *
 * <p>This class is pretty simple. The test cases simply verifies the exception can be instantiated with the
 * error message and cause properly propagated, and that it comes with correct inheritance.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class PersistenceExceptionTests extends TestCase {

    /**
     * <p>The error message used for testing.</p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>Creation test.</p>
     *
     * <p>Verifies the error message is properly propagated.</p>
     */
    public void testPersistenceException1() {
        PersistenceException pe = new PersistenceException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate PersistenceException.", pe);
        assertEquals("Error message is not properly propagated to super class.",
            ERROR_MESSAGE, pe.getMessage());
    }

    /**
     * <p>Creation test.</p>
     *
     * <p>Verifies the error cause is properly propagated.</p>
     */
    public void testPersistenceException2() {
        Throwable cause = new Exception();
        PersistenceException pe = new PersistenceException(cause);

        assertNotNull("Unable to instantiate PersistenceException.", pe);
        assertEquals("Cause is not properly propagated to super class.",
            cause, pe.getCause());
    }

    /**
     * <p>Creation test.</p>
     *
     * <p>Verifies the error message and the cause are properly propagated.</p>
     */
    public void testPersistenceException3() {
        Throwable cause = new Exception();
        PersistenceException pe = new PersistenceException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate PersistenceException.", pe);
        assertEquals("Cause is not properly propagated to super class.",
            cause, pe.getCause());
    }

    /**
     * <p>Inheritance test.</p>
     *
     * <p>Verifies PersistenceException subclasses BaseException.</p>
     */
    public void testPersistenceExceptionInheritance1() {
        assertTrue("PersistenceException does not subclass BaseException.",
            new PersistenceException(ERROR_MESSAGE) instanceof BaseException);
    }

    /**
     * <p>Inheritance test.</p>
     *
     * <p>Verifies PersistenceException subclasses BaseException.</p>
     */
    public void testPersistenceExceptionInheritance2() {
        assertTrue("PersistenceException does not subclass BaseException.",
            new PersistenceException(new Exception()) instanceof BaseException);
    }

    /**
     * <p>Inheritance test.</p>
     *
     * <p>Verifies PersistenceException subclasses BaseException.</p>
     */
    public void testPersistenceExceptionInheritance3() {
        assertTrue("PersistenceException does not subclass BaseException.",
            new PersistenceException(ERROR_MESSAGE, new Exception()) instanceof BaseException);
    }

}
