/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;

/**
 *
 * <p>
 * This is a mocked implementation of AddressManager.
 * </p>
 *
 * @author Achilles_2005
 * @version 3.2
 */
public class MockedAddressManager implements AddressManager {

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public MockedAddressManager() {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param address the argument
     * @param doAudit the argument
     */
    public void addAddress(Address address, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param addresses the argument
     * @param doAudit the argument
     */
    public void addAddresses(Address[] addresses, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param address the argument
     * @param entityId the argument
     * @param doAudit the argument
     */
    public void associate(Address address, long entityId, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param address the argument
     * @param entityId the argument
     * @param doAudit the argument
     */
    public void deassociate(Address address, long entityId, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null
     */
    public Address[] getAllAddresses() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null
     */
    public Country[] getAllCountries() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null
     */
    public State[] getAllStates() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param id the argument
     * @param doAudit the argument
     */
    public void removeAddress(long id, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param ids the argument
     * @param doAudit the argument
     */
    public void removeAddresses(long[] ids, boolean doAudit) {
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param id the argument
     * @return null
     */
    public Address retrieveAddress(long id) {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param ids the argument
     * @return null
     */
    public Address[] retrieveAddresses(long[] ids) {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param filter the argument
     * @return the array with Address
     */
    public Address[] searchAddresses(Filter filter) {
        return new Address[] {new Address()};
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param address the argument
     * @param doAudit the argument
     */
    public void updateAddress(Address address, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param addresses the argument
     * @param doAudit the argument
     */
    public void updateAddresses(Address[] addresses, boolean doAudit) {

    }
}
