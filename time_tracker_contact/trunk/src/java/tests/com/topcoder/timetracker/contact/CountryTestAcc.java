/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>This test case contains accuracy tests for <code>Country</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CountryTestAcc extends BaseTestCase {
    /**
     * <p>
     * Test constructor.
     * </p>
     */
    public void testCtor() {
        assertNotNull(new Country());
    }

    /**
     * <p>
     * Test methods <code>setName()</code> and <code>getName()</code>.
     * </p>
     */
    public void testName() {
        Country country = new Country();
        country.setChanged(false);
        country.setName("new Name");
        assertEquals("new Name", country.getName());
        assertTrue(country.isChanged());
    }
}
