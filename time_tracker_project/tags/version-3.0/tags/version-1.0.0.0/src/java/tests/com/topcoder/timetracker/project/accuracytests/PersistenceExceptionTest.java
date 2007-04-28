/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.project.accuracytests;

import com.topcoder.timetracker.project.persistence.PersistenceException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class contains the accuracy unit tests for PersistenceException.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class PersistenceExceptionTest extends TestCase {
    /**
     * <p>
     * Creates an instance for the Test.
     * </p>
     *
     * @param name the name of the TestCase.
     */
    public PersistenceExceptionTest(String name) {
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
        return new TestSuite(PersistenceExceptionTest.class);
    }

    /**
     * <p>
     * Tests accuracy of the constructor PersistenceException(String msg).
     * </p>
     */
    public void testConstructorAccuracy() {
        PersistenceException exp = new PersistenceException("msg");
        assertTrue(exp.getMessage().indexOf("msg") != -1);
    }

    /**
     * <p>
     * Tests accuracy of the constructor ConfigurationException(String msg, Throwable cause).
     * </p>
     */
    public void testConstructorTwoAccuracy() {
        PersistenceException exp = new PersistenceException("msg", new NullPointerException());
        assertTrue(exp.getMessage().indexOf("msg") != -1);
        assertTrue(exp.getCause() instanceof NullPointerException);
    }
}
