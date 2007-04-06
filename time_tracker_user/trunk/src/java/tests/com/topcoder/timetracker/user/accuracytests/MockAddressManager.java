/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.AddressType;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.PersistenceException;
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
     * Represents the sql script to insert value to address relation table.
     * </p>
     */
    private static final String INSERT_ADDRESS_RELATION = "insert into address_relation(entity_id, "
        + "address_type_id, creation_date, creation_user, modification_date, modification_user, "
        + "address_id) values (?, ?, CURRENT, USER, CURRENT, USER, ?)";

    /**
     * <p>
     * Represents the sql script to delete value from address relation table.
     * </p>
     */
    private static final String DELETE_ADDRESS_RELATION = "delete from address_relation where "
        + "entity_id = ? and address_type_id = ? and address_id = ?";

    /**
     * <p>
     * Represents the address id for the next add operation.
     * </p>
     */
    private static int nextId = 100;

    /**
     * <p>
     * Represents the mapping from the address id to the <code>Address</code> instance.
     * </p>
     *
     * <p>
     * It is used to cache all the addresses for this address manager.
     * </p>
     */
    private Map addressMap = new HashMap();

    /**
     * <p>
     * Constructs a <code>MockAddressManager</code> instance.
     * </p>
     */
    public MockAddressManager() {
        Address address = AccuracyTestHelper.createAddress();
        address.setId(1);

        addressMap.put(new Long(1), address);
    }

    /**
     * <p>
     * Adds the given Address.
     * </p>
     *
     * @param address the address to add
     * @param doAudit whether this action should be audited
     */
    public void addAddress(Address address, boolean doAudit) {
        address.setId(nextId++);
        addressMap.put(new Long(address.getId()), address);
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
        for (int i = 0; i < addresses.length; i++) {
            addresses[i].setId(nextId++);
            addressMap.put(new Long(addresses[i].getId()), addresses[i]);
        }
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
        Address address = (Address) addressMap.get(new Long(id));
        if (address == null) {
            address = AccuracyTestHelper.createAddress();
            address.setId(id);
        }

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
        Address[] addresses = new Address[ids.length];
        for (int i = 0; i < ids.length; i++) {
            addresses[i] = retrieveAddress(ids[i]);
        }

        return addresses;
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
        addressMap.remove(new Long(id));
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
        for (int i = 0; i < ids.length; i++) {
            addressMap.remove(new Long(ids[i]));
        }
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
        addressMap.put(new Long(address.getId()), address);
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
        for (int i = 0; i < addresses.length; i++) {
            addressMap.put(new Long(addresses[i].getId()), addresses[i]);
        }
    }

    /**
     * <p>
     * Retrieves all the addresses.
     * </p>
     *
     * @return all the addresses in this manager
     */
    public Address[] getAllAddresses() {
        Collection values = addressMap.values();
        return (Address[]) values.toArray(new Address[values.size()]);
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
        return getAllAddresses();
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
     * @throws PersistenceException if any exception occurs
     */
    public void associate(Address address, long entityId, boolean doAudit) throws PersistenceException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = AccuracyTestHelper.getConnection();
            conn.setAutoCommit(true);
            pstmt = conn.prepareStatement(INSERT_ADDRESS_RELATION);
            pstmt.setLong(1, entityId);
            pstmt.setLong(2, AddressType.USER.getId());
            pstmt.setLong(3, address.getId());

            pstmt.executeUpdate();
        } catch (UnknownConnectionException e) {
            throw new PersistenceException("UnknownConnectionException occurs.", e);
        } catch (DBConnectionException e) {
            throw new PersistenceException("DBConnectionException occurs.", e);
        } catch (ConfigurationException e) {
            throw new PersistenceException("ConfigurationException occurs.", e);
        } catch (SQLException e) {
            throw new PersistenceException("SQLException occurs.", e);
        } finally {
            AccuracyTestHelper.closeStatement(pstmt);
            AccuracyTestHelper.closeConnection(conn);
        }
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
     * @throws PersistenceException if any exception occurs
     */
    public void deassociate(Address address, long entityId, boolean doAudit) throws PersistenceException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = AccuracyTestHelper.getConnection();
            conn.setAutoCommit(true);
            pstmt = conn.prepareStatement(DELETE_ADDRESS_RELATION);
            pstmt.setLong(1, entityId);
            pstmt.setLong(2, AddressType.USER.getId());
            pstmt.setLong(3, address.getId());

            pstmt.executeUpdate();
        } catch (UnknownConnectionException e) {
            throw new PersistenceException("UnknownConnectionException occurs.", e);
        } catch (DBConnectionException e) {
            throw new PersistenceException("DBConnectionException occurs.", e);
        } catch (ConfigurationException e) {
            throw new PersistenceException("ConfigurationException occurs.", e);
        } catch (SQLException e) {
            throw new PersistenceException("SQLException occurs.", e);
        } finally {
            AccuracyTestHelper.closeStatement(pstmt);
            AccuracyTestHelper.closeConnection(conn);
        }
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
