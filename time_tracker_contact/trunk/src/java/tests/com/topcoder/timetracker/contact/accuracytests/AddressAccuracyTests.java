/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;

/**
 * <p>
 * Accuracy Unit test cases for Address.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class AddressAccuracyTests extends TestCase {
    /**
     * <p>
     * Address instance for testing.
     * </p>
     */
    private Address instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new Address();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AddressAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor Address#Address() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create Address instance.", instance);
    }

    /**
     * <p>
     * Tests Address#getLine1() for accuracy.
     * </p>
     */
    public void testGetLine1() {
        instance.setLine1("line1");
        assertEquals("Failed to get the line1.", "line1", instance.getLine1());
    }

    /**
     * <p>
     * Tests Address#setLine1(String) for accuracy.
     * </p>
     */
    public void testSetLine1() {
        instance.setLine1("line1");
        assertEquals("Failed to set the line1.", "line1", instance.getLine1());
    }

    /**
     * <p>
     * Tests Address#getLine2() for accuracy.
     * </p>
     */
    public void testGetLine2() {
        instance.setLine2("line2");
        assertEquals("Failed to get the line2.", "line2", instance.getLine2());
    }

    /**
     * <p>
     * Tests Address#setLine2(String) for accuracy.
     * </p>
     */
    public void testSetLine2() {
        instance.setLine2("line2");
        assertEquals("Failed to set the line2.", "line2", instance.getLine2());
    }

    /**
     * <p>
     * Tests Address#getCitry() for accuracy.
     * </p>
     */
    public void testGetCitry() {
        instance.setCity("city");
        assertEquals("Failed to get the city.", "city", instance.getCity());
    }

    /**
     * <p>
     * Tests Address#setCity(String) for accuracy.
     * </p>
     */
    public void testSetCity() {
        instance.setCity("city");
        assertEquals("Failed to set the city.", "city", instance.getCity());
    }

    /**
     * <p>
     * Tests Address#getPostalCode() for accuracy.
     * </p>
     */
    public void testGetPostalCode() {
        instance.setPostalCode("postalCode");
        assertEquals("Failed to get the postal code.", "postalCode", instance.getPostalCode());
    }

    /**
     * <p>
     * Tests Address#setPostalCode(String) for accuracy.
     * </p>
     */
    public void testSetPostalCode() {
        instance.setPostalCode("postalCode");
        assertEquals("Failed to set the postal code.", "postalCode", instance.getPostalCode());
    }

    /**
     * <p>
     * Tests Address#setCountry(Country) for accuracy.
     * </p>
     */
    public void testSetCountry() {
        Country country = new Country();
        instance.setCountry(country);
        assertSame("Failed to set the country.", country, instance.getCountry());
    }

    /**
     * <p>
     * Tests Address#getAddressType() for accuracy.
     * </p>
     */
    public void testGetAddressType() {
        instance.setAddressType(AddressType.CLIENT);
        assertEquals("Failed to get the address type.", AddressType.CLIENT, instance.getAddressType());
    }

    /**
     * <p>
     * Tests Address#setAddressType(AddressType) for accuracy.
     * </p>
     */
    public void testSetAddressType() {
        instance.setAddressType(AddressType.CLIENT);
        assertEquals("Failed to set the address type.", AddressType.CLIENT, instance.getAddressType());
    }

    /**
     * <p>
     * Tests Address#getState() for accuracy.
     * </p>
     */
    public void testGetState() {
        State state = new State();
        instance.setState(state);
        assertSame("Failed to get the state.", state, instance.getState());
    }

    /**
     * <p>
     * Tests Address#getCountry() for accuracy.
     * </p>
     */
    public void testGetCountry() {
        Country country = new Country();
        instance.setCountry(country);
        assertSame("Failed to get the country.", country, instance.getCountry());
    }

    /**
     * <p>
     * Tests Address#setState(State) for accuracy.
     * </p>
     */
    public void testSetState() {
        State state = new State();
        instance.setState(state);
        assertSame("Failed to set the state.", state, instance.getState());
    }

}