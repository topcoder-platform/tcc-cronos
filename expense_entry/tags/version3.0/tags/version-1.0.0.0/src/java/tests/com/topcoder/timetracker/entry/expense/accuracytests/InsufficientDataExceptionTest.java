/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.InsufficientDataException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class contains the accuracy unit tests for InsufficientDataException.java
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class InsufficientDataExceptionTest extends TestCase {
    /**
     * <p>
     * Creates an instance for the Test.
     * </p>
     *
     * @param name the name of the TestCase.
     */
    public InsufficientDataExceptionTest(String name) {
        super(name);
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(InsufficientDataExceptionTest.class);
    }

    /**
     * <p>
     * Tests accuracy of the constructor InsufficientDataException(String msg)
     * </p>
     */
    public void testConstructorAccuracy() {
        InsufficientDataException exp = new InsufficientDataException("msg");
        assertTrue(exp.getMessage().indexOf("msg") != -1);
    }

    /**
     * <p>
     * Tests accuracy of the constructor InsufficientDataException(String msg, Throwable cause)
     * </p>
     */
    public void testConstructorTwoAccuracy() {
        InsufficientDataException exp = new InsufficientDataException("msg", new NullPointerException());
        assertTrue(exp.getMessage().indexOf("msg") != -1);
        assertTrue(exp.getCause() instanceof NullPointerException);
    }
}
