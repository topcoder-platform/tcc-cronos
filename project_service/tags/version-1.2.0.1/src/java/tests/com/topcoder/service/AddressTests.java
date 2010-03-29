/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service;

import junit.framework.TestCase;

import com.topcoder.service.user.Address;

/**
 * <p>
 * <code>Address</code> tests.
 * </p>
 *
 * @author ernestobf
 * @version 1.1
 */
public class AddressTests extends TestCase {

    /**
     * This tests the Address constructor.
     */
    public void testCtor() {
        assertNotNull("Failed to instantiate?", new Address());
    }

    /**
     * This tests the accessor methods.
     */
    public void testSettersGetters() {
        Address address = new Address();

        address.setAddress1("address1");
        assertEquals("setter/getter failed", "address1", address.getAddress1());

        address.setAddress2("address2");
        assertEquals("setter/getter failed", "address2", address.getAddress2());

        address.setAddress3("address3");
        assertEquals("setter/getter failed", "address3", address.getAddress3());

        address.setCountryCode("cc");
        assertEquals("setter/getter failed", "cc", address.getCountryCode());

        address.setCity("city");
        assertEquals("setter/getter failed", "city", address.getCity());

        address.setStateCode("sc");
        assertEquals("setter/getter failed", "sc", address.getStateCode());

        address.setProvince("prov");
        assertEquals("setter/getter failed", "prov", address.getProvince());

        address.setZip("zip");
        assertEquals("setter/getter failed", "zip", address.getZip());
    }
}
