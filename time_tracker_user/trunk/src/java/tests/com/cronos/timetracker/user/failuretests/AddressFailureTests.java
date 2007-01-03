/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.common.Address;

/**
 * <p>
 * Failure unit test cases for the Address class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class AddressFailureTests extends TestCase {

    /**
     * <p>
     * The Address instance used for testing.
     * </p>
     */
    private Address address = null;

    /**
     * <p>
     * Creates Address instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        address = new Address();
    }

    /**
     * <p>
     * Tests setCity(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCity1() {
        try {
            address.setCity(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setLine1(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetLine1_1() {
        try {
            address.setLine1(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setLine1(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetLine2_1() {
        try {
            address.setLine2(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setState(State) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetState1() {
        try {
            address.setState(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setZipCode(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetZipCode1() {
        try {
            address.setZipCode(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
