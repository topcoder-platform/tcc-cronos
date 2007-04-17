/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>This test case contains failure tests for <code>Address</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AddressTestExp extends BaseTestCase {
    /**
     * <p>
     * Instance of <code>Address</code> used in the test case.
     * </p>
     */
    private Address address = this.getAddress();
    /**
     * <p>
     * Test method <code>setLine1()</code>. Given string is null, IAE expected.
     * </p>
     */
    public void testSetLine1_NullString() {
        try {
            address.setLine1(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>setLine1()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetLine1_EmptyString() {
        try {
            address.setLine1(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setLine2()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetLine2_EmptyString() {
        try {
            address.setLine2(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setCity()</code>. Given string is null, IAE expected.
     * </p>
     */
    public void testSetCity_NullString() {
        try {
            address.setCity(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>setCity()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetCity_EmptyString() {
        try {
            address.setCity(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>setPostalCode()</code>. Given string is null, IAE expected.
     * </p>
     */
    public void testSetPostalCode_NullString() {
        try {
            address.setPostalCode(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>setPostalCode()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetPostalCode_EmptyString() {
        try {
            address.setPostalCode(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>setCountry()</code>. Given country is null, IAE expected.
     * </p>
     */
    public void testSetCountry() {
        try {
            address.setCountry(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setState()</code>. Given state is null, IAE expected.
     * </p>
     */
    public void testSetState() {
        try {
            address.setState(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>setAddressType()</code>. Given address type is null, IAE expected.
     * </p>
     */
    public void testSetAddressType() {
        try {
            address.setAddressType(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}
