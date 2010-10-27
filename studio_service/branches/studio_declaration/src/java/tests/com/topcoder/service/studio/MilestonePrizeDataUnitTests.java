/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>MilestonePrizeData</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MilestonePrizeDataUnitTests extends TestCase {
    /**
     * <p>
     * Represents MilestonePrizeData instance to test against.
     * </p>
     */
    private MilestonePrizeData instance = null;

    /**
     * Set Up the test environment before testing.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new MilestonePrizeData();
    }

    /**
     * Clean up the test environment after testing.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link MilestonePrizeData#MilestonePrizeData()}. It
     * verifies the new instance is created.
     * </p>
     */
    public void testMilestonePrizeData() {
        assertNotNull("Unable to instantiate MilestonePrizeData", instance);
    }

    /**
     * <p>
     * Test method for {@link MilestonePrizeData#getAmount()}. It verifies the
     * returned value is correct.
     * </p>
     */
    public void testGetAmount() {
        assertNull("Incorrect default value for amount", instance.getAmount());

        // set a value
        instance.setAmount(8.888D);
        assertEquals("Incorrect amount", 8.888D, instance.getAmount());
    }

    /**
     * <p>
     * Test method for {@link MilestonePrizeData#setAmount(Double)}. It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetAmount() {
        // set a value
        instance.setAmount(8.888D);
        assertEquals("Incorrect amount", 8.888D, instance.getAmount());

        // set to null
        instance.setAmount(null);
        assertNull("Incorrect amount after set to null", instance.getAmount());
    }

    /**
     * <p>
     * Test method for {@link MilestonePrizeData#getNumberOfSubmissions()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetNumberOfSubmissions() {
        assertNull("Incorrect default value for numberOfSubmissions", instance.getNumberOfSubmissions());

        // set a value
        instance.setNumberOfSubmissions(new Integer(666666));
        assertEquals("Incorrect numberOfSubmissions", new Integer(666666), instance.getNumberOfSubmissions());
    }

    /**
     * <p>
     * Test method for
     * {@link MilestonePrizeData#setNumberOfSubmissions(Integer)}. It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testSetNumberOfSubmissions() {
        // set a value
        instance.setNumberOfSubmissions(new Integer(666666));
        assertEquals("Incorrect numberOfSubmissions", new Integer(666666), instance.getNumberOfSubmissions());

        // set to null
        instance.setNumberOfSubmissions(null);
        assertNull("Incorrect numberOfSubmissions after set to null", instance.getNumberOfSubmissions());
    }

}
