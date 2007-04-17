/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>This test case contains failure tests for <code>Country</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CountryTestExp extends BaseTestCase {

    /**
     * <p>
     * Test method <code>setName()</code>. Given name is null, IAE expected.
     * </p>
     */
    public void testSetName1() {
        try {
            new Country().setName(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setName()</code>. Given name is empty, IAE expected.
     * </p>
     */
    public void testSetName2() {
        try {
            new Country().setName(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

}
