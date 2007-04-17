/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;

/**
 * <p>
 * This class implements AddressManager interface.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockAddressManager implements AddressManager {

    /**
     * <p>
     * Adds the given Address.
     * </p>
     *
     * @param address the address to add
     * @param doAudit whether this action should be audited
     */
    public void addAddress(Address address, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Adds the given addresses.
     * </p>
     *
     * @param addresses the address array to add
     * @param doAudit whether this action should be audited
     */
    public void addAddresses(Address[] addresses, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Retrieves the given address.
     * </p>
     *
     * @return the address with the given id
     * @param id the id of the address to be retrieved
     */
    public Address retrieveAddress(long id) {
        Address address = new Address();
        address.setId(id);

        return address;
    }

    /**
     * <p>
     * Retrieves the given addresses.
     * </p>
     *
     * @return all the addresses for the ids
     * @param ids the address id array
     */
    public Address[] retrieveAddresses(long[] ids) {
        return new Address[0];

    }

    /**
     * <p>
     * Removes the address with given id.
     * </p>
     *
     * @param id the address id to remove
     * @param doAudit whether this action should be audited
     */
    public void removeAddress(long id, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Removes the addresses with given ids.
     * </p>
     *
     * @param ids the address id array to remove
     * @param doAudit whether this action should be audited
     */
    public void removeAddresses(long[] ids, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Updates the given address.
     * </p>
     *
     * @param address the address to update
     * @param doAudit whether this action should be audited
     */
    public void updateAddress(Address address, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Updates the given addresses.
     * </p>
     *
     * @param addresses the address array to update
     * @param doAudit whether this action should be audited
     */
    public void updateAddresses(Address[] addresses, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Retrieves all the addresses.
     * </p>
     *
     * @return all the addresses in this manager
     */
    public Address[] getAllAddresses() {
        return new Address[0];
    }

    /**
     * <p>
     * Searches the addresses with the given Filter.
     * </p>
     *
     * @return all the addresses in this manager
     * @param filter the filter
     */
    public Address[] searchAddresses(Filter filter) {
        return new Address[0];
    }

    /**
     * <p>
     * Associates the address with the entity.
     * </p>
     *
     * @param address the address to associate
     * @param entityId the entity id
     * @param doAudit whether this action should be audited
     *
     */
    public void associate(Address address, long entityId, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Deassociates the address with the entity.
     * </p>
     *
     * @param address the address to deassociate
     * @param entityId the entity id
     * @param doAudit whether this action should be audited
     *
     */
    public void deassociate(Address address, long entityId, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Retrieves all states.
     * </p>
     *
     * @return all the states in this manager
     */
    public State[] getAllStates() {
        return new State[0];
    }

    /**
     * <p>
     * Retrieves all countries.
     * </p>
     *
     * @return all the countries in this manager
     */
    public Country[] getAllCountries() {
        return new Country[0];
    }
}
