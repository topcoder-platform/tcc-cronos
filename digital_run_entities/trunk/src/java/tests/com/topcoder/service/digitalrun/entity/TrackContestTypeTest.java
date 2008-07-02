/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link TrackContestType} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TrackContestTypeTest extends TestCase {

    /**
     * Represents the <code>TrackContestType</code> instance to test.
     */
    private TrackContestType trackContestType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        trackContestType = new TrackContestType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        trackContestType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(TrackContestTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link TrackContestType#TrackContestType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_TrackContestType() {
        //check for null
        assertNotNull("TrackContestType creation failed", trackContestType);
    }
}
