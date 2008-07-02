/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link DigitalRunPointsOperation} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsOperationTest extends TestCase {

    /**
     * Represents the <code>DigitalRunPointsOperation</code> instance to test.
     */
    private DigitalRunPointsOperation digitalRunPointsOperation = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        digitalRunPointsOperation = new DigitalRunPointsOperation();
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
        digitalRunPointsOperation = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsOperationTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPointsOperation#DigitalRunPointsOperation()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_DigitalRunPointsOperation() {
        //check for null
        assertNotNull("DigitalRunPointsOperation creation failed", digitalRunPointsOperation);
    }
}
