/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.contact.State;


/**
 * This interface defines the contract for the complete management of an address. It provides single and batch CRUD
 * operations as well as the means to associate or disassociate an address from an entity. It also provides the
 * ability to audit each operation, if so desired. It has one implementation in this design:
 * AddressManagerLocalDelegate.
 *
 * <p>
 * <strong>Thread Safety</strong>
 * </p>
 * Implementations need not necessarily be thread safe. Each implementation should specify whether it is thread-safe or
 * not. The application should pick the correct implementation for it's requirements.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockAddressManager implements AddressManager {
    /**
     * <p>
     * Add the given Address.
     * </p>
     *
     * @param address non null address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null
     * @throws PersistenceException if it is thrown by the localEJB
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws AuditException if exception occurs when audit
     */
    public void addAddress(Address address, boolean doAudit)
        throws PersistenceException, IDGenerationException, InvalidPropertyException, AuditException {
    }

    /**
     * <p>
     * Add the given addresses.
     * </p>
     *
     * @param addresses non null, possible empty addresses
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the client is null or any client contained is null
     */
    public void addAddresses(Address[] addresses, boolean doAudit) {
    }

    /**
     * <p>
     * Retrieve the given address.
     * </p>
     *
     * @param id the id of the address to be retrieved
     *
     * @return the address with the given id, null if the address if not found
     *
     * @throws IllegalArgumentException if the not positive
     * @throws PersistenceException if it is thrown by the localEJB
     */
    public Address retrieveAddress(long id) throws PersistenceException {
        Address add = new Address();
        add.setId(id);
        return add;
    }

    /**
     * <p>
     * Retrieve the given addresses.
     * </p>
     *
     * @param ids the non null, possible empty ids of the addresses
     *
     * @return the non null addresses with given id, the containing non null addresses
     *
     * @throws IllegalArgumentException if ids is null or any id not positive.
     */
    public Address[] retrieveAddresses(long[] ids) {
        return null;
    }

    /**
     * <p>
     * Remove the address with given id.
     * </p>
     *
     * @param id the id of the address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the id not positive
     */
    public void removeAddress(long id, boolean doAudit) {
    }

    /**
     * <p>
     * Remove the addresses with given&nbsp;ids.
     * </p>
     *
     * @param ids the non null, possible empty ids of the addresses
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if any id not positive
     */
    public void removeAddresses(long[] ids, boolean doAudit) {
    }

    /**
     * <p>
     * Update the given address
     * </p>
     *
     * @param address non null address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null
     * @throws EntityNotFoundException if the address can't be found
     */
    public void updateAddress(Address address, boolean doAudit) {
    }

    /**
     * <p>
     * Update the given addresses
     * </p>
     *
     * @param addresses non null address
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if any address is null
     * @throws PersistenceException if it is thrown by the localEJB
     * @throws InvalidPropertyException if the properties of given address is invalid
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the address can't be found
     * @throws BatchOperationException if any operation in the batch is failed
     */
    public void updateAddresses(Address[] addresses, boolean doAudit) {
    }

    /**
     * <p>
     * Retrieve all the addresses
     * </p>
     *
     * @return Non null, possible empty array containing all non null addresses
     *
     * @throws PersistenceException if it is thrown by the localEJB
     */
    public Address[] getAllAddresses() {
        return null;
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
     * @throws PersistenceException if any exception occurs
     * @throws IllegalArgumentException if filter is null
     */
    public Address[] searchAddresses(Filter filter) {
        return null;
    }

    /**
     * <p>
     * Associate the address with the entity.
     * </p>
     *
     * @param address non null address
     * @param entity_id the non null id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null or the id not positive
     * @throws PersistenceException if any exception occurs
     * @throws AuditException if exception occurs when audit
     */
    public void associate(Address address, long entity_id, boolean doAudit) {
    }

    /**
     * <p>
     * Deassociate the address with the entity.
     * </p>
     *
     * @param address non null address
     * @param entity_id the non null id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the address is null or the id not positive
     * @throws PersistenceException if any exception occurs
     * @throws AuditException if exception occurs when audit
     */
    public void deassociate(Address address, long entity_id, boolean doAudit) {
    }

    /**
     * <p>
     * Retrieves all states.
     * </p>
     *
     * @return non null, possible empty array containing all the non null states
     *
     * @throws PersistenceException if any exception occurs
     */
    public State[] getAllStates() {
        return null;
    }

    /**
     * <p>
     * Retrieves all countries
     * </p>
     *
     * @return non null, possible empty array containing all the non null countries
     *
     * @throws PersistenceException if any exception occurs
     */
    public Country[] getAllCountries() {
        return null;
    }
}
