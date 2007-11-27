/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

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
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.PersistenceException;

/**
 * <p>
 * This class implements ContactManager interface. It is only used for testing.
 * </p>
 *
 * @author biotrail
 * @version 3.2
 */
public class MockContactManager implements ContactManager {
    /**
     * <p>
     * Represents the sql script to insert value to contact relation table.
     * </p>
     */
    private static final String INSERT_CONTACT_RELATION =
        "insert into contact_relation(entity_id, "
            + "contact_type_id, creation_date, creation_user, modification_date, modification_user, contact_id)"
            + " values (?, ?, CURRENT, USER, CURRENT, USER, ?)";

    /**
     * <p>
     * Represents the sql script to delete value from contact relation table.
     * </p>
     */
    private static final String DELETE_CONTACT_RELATION =
        "delete from contact_relation where " + "entity_id = ? and contact_type_id = ? and contact_id = ?";

    /**
     * <p>
     * Represents the contact id for the next add operation.
     * </p>
     */
    private static int nextId = 100;

    /**
     * <p>
     * Represents the mapping from the contact id to the <code>Contact</code> instance.
     * </p>
     *
     * <p>
     * It is used to cache all the contacts for this address manager.
     * </p>
     */
    private Map contactMap = new HashMap();

    /**
     * <p>
     * Constructs a <code>MockContactManager</code> instance.
     * </p>
     */
    public MockContactManager() {
        Contact contact = TestHelper.createTestingContact();
        contact.setId(1);

        contactMap.put(new Long(1), contact);
    }

    /**
     * <p>
     * Adds the given contact.
     * </p>
     *
     * @param contact
     *            the contact to add
     * @param doAudit
     *            whether this action should be audited
     */
    public void addContact(Contact contact, boolean doAudit) {
        contact.setId(nextId++);
        contactMap.put(new Long(contact.getId()), contact);
    }

    /**
     * <p>
     * Adds the given contacts.
     * </p>
     *
     * @param contacts
     *            the contact array to add
     * @param doAudit
     *            whether this action should be audited
     */
    public void addContacts(Contact[] contacts, boolean doAudit) {
        for (int i = 0; i < contacts.length; i++) {
            contacts[i].setId(nextId++);
            contactMap.put(new Long(contacts[i].getId()), contacts[i]);
        }
    }

    /**
     * <p>
     * Retrieves the given contact.
     * </p>
     *
     * @return the contact with the given id
     * @param id
     *            the id of the contact to be retrieved
     */
    public Contact retrieveContact(long id) {
        Contact contact = (Contact) contactMap.get(new Long(id));
        if (contact == null) {
            contact = TestHelper.createTestingContact();
            contact.setId(id);
        }

        return contact;
    }

    /**
     * <p>
     * Retrieves the given contacts.
     * </p>
     *
     * @return all the contacts for the ids
     * @param ids
     *            the contact id array
     */
    public Contact[] retrieveContacts(long[] ids) {
        Contact[] contacts = new Contact[ids.length];
        for (int i = 0; i < ids.length; i++) {
            contacts[i] = retrieveContact(ids[i]);
        }

        return contacts;
    }

    /**
     * <p>
     * Removes the contact with given id.
     * </p>
     *
     * @param id
     *            the contact id to remove
     * @param doAudit
     *            whether this action should be audited
     */
    public void removeContact(long id, boolean doAudit) {
        contactMap.remove(new Long(id));
    }

    /**
     * <p>
     * Removes the contacts with given ids.
     * </p>
     *
     * @param ids
     *            the contact id array to remove
     * @param doAudit
     *            whether this action should be audited
     */
    public void removeContacts(long[] ids, boolean doAudit) {
        for (int i = 0; i < ids.length; i++) {
            contactMap.remove(new Long(ids[i]));
        }
    }

    /**
     * <p>
     * Updates the given contact.
     * </p>
     *
     * @param contact
     *            the contact to update
     * @param doAudit
     *            whether this action should be audited
     */
    public void updateContact(Contact contact, boolean doAudit) {
        contactMap.put(new Long(contact.getId()), contact);
    }

    /**
     * <p>
     * Updates the given contacts.
     * </p>
     *
     * @param contacts
     *            the contact array to update
     * @param doAudit
     *            whether this action should be audited
     */
    public void updateContacts(Contact[] contacts, boolean doAudit) {
        for (int i = 0; i < contacts.length; i++) {
            contactMap.put(new Long(contacts[i].getId()), contacts[i]);
        }
    }

    /**
     * <p>
     * Retrieves all the contacts.
     * </p>
     *
     * @return all the contacts in this manager
     */
    public Contact[] getAllContacts() {
        Collection values = contactMap.values();
        return (Contact[]) values.toArray(new Contact[values.size()]);
    }

    /**
     * <p>
     * Searches the contacts with the given Filter.
     * </p>
     *
     * @return all the contacts in this manager
     * @param filter
     *            the filter
     */
    public Contact[] searchContacts(Filter filter) {
        return getAllContacts();
    }

    /**
     * <p>
     * Associates the contact with the entity.
     * </p>
     *
     * @param contact
     *            the contact to associate
     * @param entityId
     *            the entity id
     * @param doAudit
     *            whether this action should be audited
     *
     * @throws PersistenceException
     *             if any exception occurs
     */
    public void associate(Contact contact, long entityId, boolean doAudit) throws PersistenceException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = TestHelper.getConnection();
            conn.setAutoCommit(true);
            pstmt = conn.prepareStatement(INSERT_CONTACT_RELATION);
            pstmt.setLong(1, entityId);
            pstmt.setLong(2, ContactType.USER.getId());
            pstmt.setLong(3, contact.getId());

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
            TestHelper.closeStatement(pstmt);
            TestHelper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Deassociates the contact with the entity.
     * </p>
     *
     * @param contact
     *            the contact to deassociate
     * @param entityId
     *            the entity id
     * @param doAudit
     *            whether this action should be audited
     *
     * @throws PersistenceException
     *             if any exception occurs
     */
    public void deassociate(Contact contact, long entityId, boolean doAudit) throws PersistenceException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = TestHelper.getConnection();
            conn.setAutoCommit(true);
            pstmt = conn.prepareStatement(DELETE_CONTACT_RELATION);
            pstmt.setLong(1, entityId);
            pstmt.setLong(2, ContactType.USER.getId());
            pstmt.setLong(3, contact.getId());

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
            TestHelper.closeStatement(pstmt);
            TestHelper.closeConnection(conn);
        }
    }

}
