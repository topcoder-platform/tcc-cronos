/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.contact.State;

/**
 * Mock AddressManager for tesing purpose.
 *
 * @author mittu
 * @version 3.2
 */
public class MockAddressManager implements AddressManager {
    /**
     * <p>
     * Represents a mock contact persistence.
     * </p>
     */
    private final Map addresses = new HashMap();

    /**
     * <p>
     * Represents the company-contact association.
     * </p>
     */
    private final Map association = new HashMap();

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#addAddress(com.topcoder.timetracker.contact.Address,
     *      boolean)
     */
    public void addAddress(Address address, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, IDGenerationException, AuditException {
        addresses.put(new Long(address.getId()), address);
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#addAddresses(com.topcoder.timetracker.contact.Address[],
     *      boolean)
     */
    public void addAddresses(Address[] addresses, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, IDGenerationException, AuditException, BatchOperationException {
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#associate(com.topcoder.timetracker.contact.Address,
     *      long, boolean)
     */
    public void associate(Address address, long entity_id, boolean doAudit) throws PersistenceException,
            AuditException {
        association.put(new Long(entity_id), new Long(address.getId()));
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#deassociate(com.topcoder.timetracker.contact.Address,
     *      long, boolean)
     */
    public void deassociate(Address address, long entity_id, boolean doAudit) throws PersistenceException,
            AuditException {
        association.remove(new Long(entity_id));
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#getAllAddresses()
     */
    public Address[] getAllAddresses() throws PersistenceException {
        return (Address[]) addresses.values().toArray(new Address[addresses.size()]);
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#getAllCountries()
     */
    public Country[] getAllCountries() throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#getAllStates()
     */
    public State[] getAllStates() throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#removeAddress(long, boolean)
     */
    public void removeAddress(long id, boolean doAudit) throws PersistenceException, AuditException {
        addresses.remove(new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#removeAddresses(long[], boolean)
     */
    public void removeAddresses(long[] ids, boolean doAudit) throws PersistenceException, AuditException,
            BatchOperationException {

    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#retrieveAddress(long)
     */
    public Address retrieveAddress(long id) throws PersistenceException {
        return (Address) addresses.get(new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#retrieveAddresses(long[])
     */
    public Address[] retrieveAddresses(long[] ids) throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#searchAddresses(com.topcoder.search.builder.filter.Filter)
     */
    public Address[] searchAddresses(Filter filter) throws PersistenceException {
        if (getAllAddresses().length != 0) {
            return new Address[] { getAllAddresses()[0] };
        }
        return null;
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#updateAddress(com.topcoder.timetracker.contact.Address,
     *      boolean)
     */
    public void updateAddress(Address address, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, AuditException, EntityNotFoundException {
        addresses.put(new Long(address.getId()), address);
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#updateAddresses(com.topcoder.timetracker.contact.Address[],
     *      boolean)
     */
    public void updateAddresses(Address[] addresses, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, AuditException, EntityNotFoundException, BatchOperationException {
    }

}
