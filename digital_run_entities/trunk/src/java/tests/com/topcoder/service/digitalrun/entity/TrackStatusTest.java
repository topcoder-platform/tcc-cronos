/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link TrackStatus} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TrackStatusTest extends TestCase {

    /**
     * Represents the <code>TrackStatus</code> instance to test.
     */
    private TrackStatus trackStatus = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        trackStatus = new TrackStatus();
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
        trackStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(TrackStatusTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link TrackStatus#TrackStatus()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_TrackStatus() {
        //check for null
        assertNotNull("TrackStatus creation failed", trackStatus);
    }
}
