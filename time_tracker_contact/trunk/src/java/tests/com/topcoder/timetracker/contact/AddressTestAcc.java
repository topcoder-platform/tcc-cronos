/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>This test case contains accuracy tests for <code>Address</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AddressTestAcc extends BaseTestCase {
    /**
     * <p>
     * Instance of <code>Address</code> used in the test case.
     * </p>
     */
    private Address address = this.getAddress();

    /**
     * <p>
     * Test constructor.
     * </p>
     */
    public void testCtor() {
        assertNotNull(this.address);
    }

    /**
     * <p>
     * Test methods <code>setLine1()</code> and <code>getLine1()</code>.
     * </p>
     */
    public void testLine1() {
        this.address.setChanged(false);
        this.address.setLine1("new line1");
        assertEquals("new line1", address.getLine1());
        assertTrue(this.address.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setLine2()</code> and <code>getLine2()</code>.
     * </p>
     */
    public void testLine2() {
        this.address.setChanged(false);
        this.address.setLine2("new line2");
        assertEquals("new line2", address.getLine2());
        assertTrue(this.address.isChanged());

        this.address.setChanged(false);
        this.address.setLine2(null);
        assertNull(this.address.getLine2());
        assertTrue(this.address.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setCity()</code> and <code>getCity()</code>.
     * </p>
     */
    public void testCity() {
        this.address.setChanged(false);
        this.address.setCity("new city");
        assertEquals("new city", address.getCity());
        assertTrue(this.address.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setPostalCode()</code> and <code>getPostalCode()</code>.
     * </p>
     */
    public void testPostalCode() {
        this.address.setChanged(false);
        this.address.setPostalCode("new postal");
        assertEquals("new postal", address.getPostalCode());
        assertTrue(this.address.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setAddressType()</code> and <code>getAddressType()</code>.
     * </p>
     */
    public void testAddressType() {
        this.address.setChanged(false);
        this.address.setAddressType(AddressType.CLIENT);
        assertEquals(AddressType.CLIENT, address.getAddressType());
        assertTrue(this.address.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setCountry()</code> and <code>getCountry()</code>.
     * </p>
     */
    public void testCountry() {
        Country country = new Country();
        country.setId(2);
        this.address.setChanged(false);
        this.address.setCountry(country);
        assertEquals(country, this.address.getCountry());
        assertTrue(this.address.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setState()</code> and <code>getState()</code>.
     * </p>
     */
    public void testState() {
        State state = new State();
        state.setId(2);
        this.address.setChanged(false);
        this.address.setState(state);
        assertEquals(state, this.address.getState());
        assertTrue(this.address.isChanged());
    }
}
