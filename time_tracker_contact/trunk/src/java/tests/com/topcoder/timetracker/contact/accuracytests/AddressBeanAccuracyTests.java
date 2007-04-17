/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate;

/**
 * <p>
 * Accuracy Unit test cases for AddressBean.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class AddressBeanAccuracyTests extends TestCase {
    /**
     * <p>
     * AddressManagerLocalDelegate instance for testing.
     * </p>
     */
    private AddressManagerLocalDelegate delegate;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
    	AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.AUDIT_CONFIG_FILE);
        AccuracyTestHelper.eJBConfig();
        AccuracyTestHelper.setUpDataBase();

        delegate = new AddressManagerLocalDelegate();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        delegate = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AddressBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor AddressBean#AddressBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create AddressBean instance.", delegate);
    }

    /**
     * <p>
     * Tests AddressBean#addAddress(Address,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, delegate.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests AddressBean#addAddresses(Address[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddresses(new Address[] {address}, false);
        AccuracyTestHelper.assertAddressEquals(address, delegate.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests AddressBean#updateAddress(Address,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);

        address.setCity("new");
        delegate.updateAddress(address, false);

        AccuracyTestHelper.assertAddressEquals(address, delegate.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests AddressBean#updateAddresses(Address[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);

        address.setCity("new");
        delegate.updateAddresses(new Address[] {address}, false);

        AccuracyTestHelper.assertAddressEquals(address, delegate.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests AddressBean#retrieveAddress(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, delegate.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests AddressBean#retrieveAddresses(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, delegate.retrieveAddresses(new long[] {address.getId()})[0]);
    }

    /**
     * <p>
     * Tests AddressBean#getAllAddresses() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, delegate.getAllAddresses()[0]);
    }

    /**
     * <p>
     * Tests AddressBean#removeAddress(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        delegate.removeAddress(address.getId(), false);

        assertEquals("Failed to remove address.", 0, delegate.getAllAddresses().length);
    }

    /**
     * <p>
     * Tests AddressBean#removeAddresses(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        delegate.removeAddresses(new long[] {address.getId()}, false);

        assertEquals("Failed to remove address.", 0, delegate.getAllAddresses().length);
    }

    /**
     * <p>
     * Tests AddressBean#searchAddresses(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);

        Address[] addresses = delegate.searchAddresses(AddressFilterFactory.createCityFilter("city"));
        AccuracyTestHelper.assertAddressEquals(address, addresses[0]);
    }

    /**
     * <p>
     * Tests AddressBean#associate(Address,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociate() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        delegate.associate(address, 2, false);
    }

    /**
     * <p>
     * Tests AddressBean#deassociate(Address,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeassociate() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        delegate.associate(address, 2, false);
        delegate.deassociate(address, 2, false);
    }

    /**
     * <p>
     * Tests AddressBean#getAllStates() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllStates() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        State[] states = delegate.getAllStates();

        assertNotNull("Failed to get all states.", states);
        assertEquals("Failed to get all states.", 2, states.length);
    }

    /**
     * <p>
     * Tests AddressBean#getAllCountries() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllCountries() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        delegate.addAddress(address, false);
        Country[] countries = delegate.getAllCountries();

        assertNotNull("Failed to get all countries.", countries);
        assertEquals("Failed to get all countries.", 2, countries.length);
    }

}