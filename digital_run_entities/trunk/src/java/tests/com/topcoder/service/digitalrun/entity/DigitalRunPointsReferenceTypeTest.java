/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link DigitalRunPointsReferenceType} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsReferenceTypeTest extends TestCase {

    /**
     * Represents the <code>DigitalRunPointsReferenceType</code> instance to test.
     */
    private DigitalRunPointsReferenceType digitalRunPointsReferenceType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
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
        digitalRunPointsReferenceType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsReferenceTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPointsReferenceType#DigitalRunPointsReferenceType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_DigitalRunPointsReferenceType() {
        //check for null
        assertNotNull("DigitalRunPointsReferenceType creation failed", digitalRunPointsReferenceType);
    }
}
