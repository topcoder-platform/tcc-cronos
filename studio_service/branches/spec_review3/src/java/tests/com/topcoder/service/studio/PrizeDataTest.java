/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for PrizeData class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PrizeDataTest extends TestCase {
    /**
     * Bean to test.
     */
    private PrizeData target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(PrizeDataTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new PrizeData();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests setter/getter for place field.
     */
    public void testPlace() {
        assertEquals("default value", -1, target.getPlace());
        target.setPlace(35);
        assertEquals("new value", 35, target.getPlace());
    }

    /**
     * Tests setter/getter for amount field.
     */
    public void testAmount() {
        assertEquals("default value", -1.0, target.getAmount());
        target.setAmount(35.0);
        assertEquals("new value", 35.0, target.getAmount());
    }
}
