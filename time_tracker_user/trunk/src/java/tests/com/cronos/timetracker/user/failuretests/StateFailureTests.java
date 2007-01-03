/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.common.State;

/**
 * <p>
 * Failure unit test cases for the State class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class StateFailureTests extends TestCase {

    /**
     * <p>
     * Tests setAbbreviation(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAbbreviation1() {
        try {
            (new State()).setName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetName1() {
        try {
            (new State()).setName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
