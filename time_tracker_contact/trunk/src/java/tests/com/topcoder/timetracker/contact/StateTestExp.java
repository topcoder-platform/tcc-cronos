/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>This test case contains failure tests for <code>State</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class StateTestExp extends BaseTestCase {
    /**
     * <p>
     * Test method <code>setName()</code>. Given name is null, IAE expected.
     * </p>
     */
    public void testSetName1() {
        try {
            new State().setName(null);
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
            new State().setName(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setAbbreviation()</code>. Given abbreviation is null, IAE expected.
     * </p>
     */
    public void testSetAbbreviation1() {
        try {
            new State().setAbbreviation(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setAbbreviation()</code>. Given abbreviation is empty, IAE expected.
     * </p>
     */
    public void testSetAbbreviation2() {
        try {
            new State().setAbbreviation(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}
