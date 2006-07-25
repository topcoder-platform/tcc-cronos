/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence.accuracytests;

import com.topcoder.management.scorecard.persistence.PersistenceException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>An accuracy test for {@link PersistenceException} class. Tests the class for producing the accurate results being
 * provided with valid input data.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PersistenceExceptionTest extends TestCase {

    /**
     * <p>A <code>String</code> providing the sample message to be used for testing.</p>
     */
    public static final String MESSAGE = "An error has been encountered";

    /**
     * <p>A <code>Throwable</code> providing the sample cause of an error to be used for testing.</p>
     */
    public static final Throwable CAUSE = new IllegalArgumentException("The argument is invalid");

    /**
     * <p>An instances of {@link PersistenceException} which are tested. These instances are initialized in
     * {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private PersistenceException[] testedInstances = null;

    /**
     * <p>Gets the test suite for {@link PersistenceException} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link PersistenceException} class.
     */
    public static Test suite() {
        return new TestSuite(PersistenceExceptionTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        this.testedInstances = new PersistenceException[2];
        this.testedInstances[0] = new PersistenceException(MESSAGE);
        this.testedInstances[1] = new PersistenceException(MESSAGE, CAUSE);
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstances = null;
    }

    /**
     * <p>Accuracy test. Tests the {@link PersistenceException#PersistenceException(String)} constructor for accurate
     * behavior.</p>
     *
     * <p>Verifies that the cause of the exception is not initialized and that the provided exception message is saved
     * properly.</p>
     */
    public void testConstructor_String() {
        assertNull("The cause must not be set", this.testedInstances[0].getCause());
        assertTrue("The message is not saved properly", this.testedInstances[0].getMessage().indexOf(MESSAGE) >= 0);
    }

    /**
     * <p>Accuracy test. Tests the {@link PersistenceException#PersistenceException(String, Throwable)} constructor for
     * accurate behavior.</p>
     *
     * <p>Verifies that the cause of the exception is saved as is and that the provided exception message is saved
     * properly.</p>
     */
    public void testConstructor_String_Throwable() {
        assertSame("The cause is not saved as is", CAUSE, this.testedInstances[1].getCause());
        assertTrue("The message is not saved properly", this.testedInstances[1].getMessage().indexOf(MESSAGE) >= 0);
    }
}
