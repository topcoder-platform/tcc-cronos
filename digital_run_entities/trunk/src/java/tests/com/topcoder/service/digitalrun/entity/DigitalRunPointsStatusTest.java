/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link DigitalRunPointsStatus} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsStatusTest extends TestCase {

    /**
     * Represents the <code>DigitalRunPointsStatus</code> instance to test.
     */
    private DigitalRunPointsStatus digitalRunPointsStatus = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        digitalRunPointsStatus = new DigitalRunPointsStatus();
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
        digitalRunPointsStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsStatusTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPointsStatus#DigitalRunPointsStatus()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_DigitalRunPointsStatus() {
        //check for null
        assertNotNull("DigitalRunPointsStatus creation failed", digitalRunPointsStatus);
    }
}
