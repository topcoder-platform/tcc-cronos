/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.timetracker.contact.State;

/**
 * This is a mocked class for interface AddressManager.
 *
 * @author Chenhong
 * @version 3.2
 */
public class MyAddressManager implements AddressManager {

    /**
     * A Map to hold all the address with key id and Address as its value.
     */
    private Map address = new HashMap();

    /**
     * <p>
     * Add an Address.
     * </p>
     *
     * @see com.topcoder.timetracker.contact.AddressManager#addAddress(com.topcoder.timetracker.contact.Address,
     *      boolean)
     */
    public void addAddress(Address a, boolean flag) throws PersistenceException, IDGenerationException,
            InvalidPropertyException, AuditException {

        address.put(new Long(a.getId()), a);
    }

    /**
     * Add a number of Address in to the map.
     *
     * @see com.topcoder.timetracker.contact.AddressManager#addAddresses(com.topcoder.timetracker.contact.Address[],
     *      boolean)
     */
    public void addAddresses(Address[] a, boolean arg1) throws InvalidPropertyException, IDGenerationException,
            AuditException, BatchOperationException {

        for (int i = 0; i < a.length; i++) {
            address.put(new Long(a[i].getId()), a[i]);
        }
    }

    /**
     *
     * @see com.topcoder.timetracker.contact.AddressManager#retrieveAddress(long)
     */
    public Address retrieveAddress(long id) throws PersistenceException {
        return (Address) address.get(new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#retrieveAddresses(long[])
     */
    public Address[] retrieveAddresses(long[] ids) throws PersistenceException {
        Address[] ret = new Address[ids.length];

        for (int i = 0; i < ids.length; i++) {
            ret[i] = retrieveAddress(ids[i]);
        }
        return ret;
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#removeAddress(long, boolean)
     */
    public void removeAddress(long id, boolean arg1) throws PersistenceException, AuditException {

        address.remove(new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#removeAddresses(long[], boolean)
     */
    public void removeAddresses(long[] id, boolean arg1) throws PersistenceException, AuditException,
            BatchOperationException {

        for (int i = 0; i < id.length; i++) {
            removeAddress(id[i], true);
        }
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#updateAddress(com.topcoder.timetracker.contact.Address,
     *      boolean)
     */
    public void updateAddress(Address a, boolean arg1) throws PersistenceException, InvalidPropertyException,
            AuditException, EntityNotFoundException {

        address.put(new Long(a.getId()), a);
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#updateAddresses(com.topcoder.timetracker.contact.Address[],
     *      boolean)
     */
    public void updateAddresses(Address[] a, boolean arg1) throws PersistenceException, EntityNotFoundException,
            InvalidPropertyException, AuditException, BatchOperationException {

        for (int i = 0; i < a.length; i++) {
            updateAddress(a[i], true);
        }
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#getAllAddresses()
     */
    public Address[] getAllAddresses() throws PersistenceException {
        return (Address[]) address.values().toArray(new Address[address.size()]);
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#searchAddresses(com.topcoder.search.builder.filter.Filter)
     */
    public Address[] searchAddresses(Filter arg0) throws PersistenceException {
        return (Address[]) address.values().toArray(new Address[address.size()]);
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#associate(com.topcoder.timetracker.contact.Address, long,
     *      boolean)
     */
    public void associate(Address a, long id, boolean arg2) throws PersistenceException, AuditException {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "insert into address_relation(entity_id, "
                + "address_type_id, creation_date, creation_user, modification_date, modification_user, "
                + "address_id) values (?, ?, CURRENT, USER, CURRENT, USER, ?)";
        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.setLong(2, AddressType.USER.getId());
            statement.setLong(3, a.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new PersistenceException("Exception raised when associate", e);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeStatement(statement);
        }
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#deassociate(com.topcoder.timetracker.contact.Address, long,
     *      boolean)
     */
    public void deassociate(Address a, long id, boolean arg2) throws PersistenceException, AuditException {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "delete from address_relation where "
                + "entity_id = ? and address_type_id = ? and address_id = ?";
        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.setLong(2, AddressType.USER.getId());
            statement.setLong(3, a.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new PersistenceException("Exception raised when deassociate", e);
        } finally {
            DBUtil.closeConnection(connection);
            DBUtil.closeStatement(statement);
        }
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#getAllStates()
     */
    public State[] getAllStates() throws PersistenceException {
        return new State[0];
    }

    /**
     * @see com.topcoder.timetracker.contact.AddressManager#getAllCountries()
     */
    public Country[] getAllCountries() throws PersistenceException {
        return new Country[0];
    }

}