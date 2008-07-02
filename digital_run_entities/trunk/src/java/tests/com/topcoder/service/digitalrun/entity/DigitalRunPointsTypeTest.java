/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link DigitalRunPointsType} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsTypeTest extends TestCase {

    /**
     * Represents the <code>DigitalRunPointsType</code> instance to test.
     */
    private DigitalRunPointsType digitalRunPointsType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        digitalRunPointsType = new DigitalRunPointsType();
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
        digitalRunPointsType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPointsType#DigitalRunPointsType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_DigitalRunPointsType() {
        //check for null
        assertNotNull("DigitalRunPointsType creation failed", digitalRunPointsType);
    }
}
