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
import com.topcoder.timetracker.contact.persistence.InformixAddressDAO;

/**
 * <p>
 * Accuracy Unit test cases for InformixAddressDAO.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class InformixAddressDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * InformixAddressDAO instance for testing.
     * </p>
     */
    private InformixAddressDAO instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.AUDIT_CONFIG_FILE);
        AccuracyTestHelper.eJBConfig();
        AccuracyTestHelper.setUpDataBase();

        instance = new InformixAddressDAO();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;

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
        return new TestSuite(InformixAddressDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InformixAddressDAO#InformixAddressDAO(String) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        assertNotNull("Failed to create InformixAddressDAO instance.", new InformixAddressDAO(
            "com.topcoder.timetracker.contact.persistence.InformixAddressDAO"));
    }

    /**
     * <p>
     * Tests ctor InformixAddressDAO#InformixAddressDAO() for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create InformixAddressDAO instance.", instance);
    }

    /**
     * <p>
     * Tests ctor InformixAddressDAO#InformixAddressDAO(String,AuditManager) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        assertNotNull("Failed to create InformixAddressDAO instance.", new InformixAddressDAO(
            "com.topcoder.timetracker.contact.persistence.InformixAddressDAO", null));
    }

    /**
     * <p>
     * Tests InformixAddressDAO#addAddress(Address,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, instance.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests InformixAddressDAO#addAddresses(Address[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddresses(new Address[] {address}, false);
        AccuracyTestHelper.assertAddressEquals(address, instance.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests InformixAddressDAO#updateAddress(Address,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);

        address.setCity("new");
        instance.updateAddress(address, false);

        AccuracyTestHelper.assertAddressEquals(address, instance.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests InformixAddressDAO#updateAddresses(Address[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);

        address.setCity("new");
        instance.updateAddresses(new Address[] {address}, false);

        AccuracyTestHelper.assertAddressEquals(address, instance.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests InformixAddressDAO#retrieveAddress(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, instance.retrieveAddress(address.getId()));
    }

    /**
     * <p>
     * Tests InformixAddressDAO#retrieveAddresses(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, instance.retrieveAddresses(new long[] {address.getId()})[0]);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#getAllAddresses() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        AccuracyTestHelper.assertAddressEquals(address, instance.getAllAddresses()[0]);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#removeAddress(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddress() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        instance.removeAddress(address.getId(), false);

        assertEquals("Failed to remove address.", 0, instance.getAllAddresses().length);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#removeAddresses(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        instance.removeAddresses(new long[] {address.getId()}, false);

        assertEquals("Failed to remove address.", 0, instance.getAllAddresses().length);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#searchAddresses(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchAddresses() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);

        Address[] addresses = instance.searchAddresses(AddressFilterFactory.createCityFilter("city"));
        AccuracyTestHelper.assertAddressEquals(address, addresses[0]);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#associate(Address,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociate() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        instance.associate(address, 2, false);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#deassociate(Address,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeassociate() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        instance.associate(address, 2, false);
        instance.deassociate(address, 2, false);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#getAllStates() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllStates() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        State[] states = instance.getAllStates();

        assertNotNull("Failed to get all states.", states);
        assertEquals("Failed to get all states.", 2, states.length);
    }

    /**
     * <p>
     * Tests InformixAddressDAO#getAllCountries() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllCountries() throws Exception {
        Address address = AccuracyTestHelper.createAddress();
        instance.addAddress(address, false);
        Country[] countries = instance.getAllCountries();

        assertNotNull("Failed to get all countries.", countries);
        assertEquals("Failed to get all countries.", 2, countries.length);
    }

}