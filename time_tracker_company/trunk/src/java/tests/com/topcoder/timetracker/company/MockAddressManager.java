/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.contact.State;


/**
 * <p>
 * Defines a mock class which implements the <code>AddressManager</code> interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockAddressManager implements AddressManager {
    /** Represents the addresses hold by this class. */
    private Map addresses = new HashMap();

    /** Represents the relationship between company and address hold by this class. */
    private Map companyAddresses = new HashMap();

    /** Represents the flag which will make all the methods of this mock class always throw exception. */
    private boolean alwaysThrowException;

    /** Represents the flag which will prevent the search and create method to throw exception. */
    private boolean prevent = false;


    /**
     * Creates a new MockAddressManager object.
     *
     * @param alwaysThrowException the flag which will make all the methods of this mock class always throw exception.
     */
    public MockAddressManager(boolean alwaysThrowException) {
        this.alwaysThrowException = alwaysThrowException;
    }

    /**
     * Creates a new MockAddressManager object.
     *
     * @param alwaysThrowException the flag which will make all the methods of this mock class always throw exception.
     * @param prevent the flag which will prevent the search and create method to throw exception.
     */
    public MockAddressManager(boolean alwaysThrowException, boolean prevent) {
        this.alwaysThrowException = alwaysThrowException;
        this.prevent = prevent;
    }

    /**
     * <p>
     * Get the addresses hold by this class.
     * </p>
     *
     * @return the addresses hold by this class.
     */
    public Address[] getAddresses() {
        return (Address[]) addresses.values().toArray(new Address[addresses.size()]);
    }

    /**
     * <p>
     * Get the address hold by the given company.
     * </p>
     *
     * @param companyId the company id.
     *
     * @return the address hold by this class.
     */
    public Address getAddress(long companyId) {
        return (Address) this.addresses.get((Long) companyAddresses.get(new Long(companyId)));
    }

    /**
     * <p>
     * Add the given Address.
     * </p>
     *
     * @param address non null address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null
     * @throws PersistenceException if it is required to be thrown.
     */
    public void addAddress(Address address, boolean doAudit) throws PersistenceException {
        if (address == null) {
            throw new IllegalArgumentException("address is null.");
        }

        if (alwaysThrowException && !prevent) {
            throw new PersistenceException("PersistenceException");
        }

        this.addresses.put(new Long(address.getId()), address);
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param addresses non null, possible empty addresses
     * @param doAudit whether this action should be audited
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void addAddresses(Address[] addresses, boolean doAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Associate the address with the entity.
     * </p>
     *
     * @param address the non null address.
     * @param entityId the non null positive id of entity.
     * @param doAudit whether this action should be audited.
     *
     * @throws IllegalArgumentException if the address is null or the id is not positive.
     * @throws PersistenceException if it is required to be thrown.
     */
    public void associate(Address address, long entityId, boolean doAudit) throws PersistenceException {
        if (address == null) {
            throw new IllegalArgumentException("address is null.");
        }

        if (entityId <= 0) {
            throw new IllegalArgumentException("entityId is not positive.");
        }

        if (alwaysThrowException && !prevent) {
            throw new PersistenceException("PersistenceException");
        }

        this.companyAddresses.put(new Long(entityId), new Long(address.getId()));
    }

    /**
     * <p>
     * Deassociate the address with the entity.
     * </p>
     *
     * @param address non null address
     * @param entityId the non null positive id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null or the id is not positive.
     * @throws PersistenceException if it is required to be thrown.
     */
    public void deassociate(Address address, long entityId, boolean doAudit) throws PersistenceException {
        if (address == null) {
            throw new IllegalArgumentException("address is null.");
        }

        if (entityId <= 0) {
            throw new IllegalArgumentException("entityId is not positive.");
        }

        if (alwaysThrowException) {
            throw new PersistenceException("PersistenceException");
        }

        this.companyAddresses.remove(new Long(entityId));
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @return Non null, possible empty array containing all non null addresses
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Address[] getAllAddresses() {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @return non null, possible empty array containing all the non null countries
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Country[] getAllCountries() {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @return non null, possible empty array containing all the non null states
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public State[] getAllStates() {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Remove the address with given id.
     * </p>
     *
     * @param id the positive id of the address.
     * @param doAudit whether this action should be audited.
     *
     * @throws IllegalArgumentException if the id is not positive.
     * @throws PersistenceException if it is required to be thrown.
     */
    public void removeAddress(long id, boolean doAudit) throws PersistenceException {
        if (id <= 0) {
            throw new IllegalArgumentException("entityId is not positive.");
        }

        if (alwaysThrowException) {
            throw new PersistenceException("PersistenceException");
        }

        this.addresses.remove(new Long(id));
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the addresses
     * @param doAudit whether this action should be audited
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void removeAddresses(long[] ids, boolean doAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param id the id of the address to be retrieved
     *
     * @return the address with the given id, null if the address if not found
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Address retrieveAddress(long id) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the addresses
     *
     * @return the non null addresses with given id, the containing non null addresses
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Address[] retrieveAddresses(long[] ids) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Search the addresses with the given Filter.
     * </p>
     *
     * @param filter non null filter
     *
     * @return non null, possible empty array containing all addresses with given condition
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws PersistenceException if it is required to be thrown.
     */
    public Address[] searchAddresses(Filter filter) throws PersistenceException {
        if (filter == null) {
            throw new IllegalArgumentException("filter is null.");
        }

        if (alwaysThrowException && !prevent) {
            throw new PersistenceException("PersistenceException");
        }

        return this.getAddresses();
    }

    /**
     * <p>
     * Update the given address.
     * </p>
     *
     * @param address the non null address to be updated
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null
     * @throws PersistenceException if it is required to be thrown.
     */
    public void updateAddress(Address address, boolean doAudit) throws PersistenceException {
        if (address == null) {
            throw new IllegalArgumentException("address is null.");
        }

        if (alwaysThrowException) {
            throw new PersistenceException("PersistenceException");
        }

        this.addresses.put(new Long(address.getId()), address);
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param addresses non null address
     * @param doAudit whether this action should be audited
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void updateAddresses(Address[] addresses, boolean doAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }
}
