/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;

/**
 * A mocked contact manager instance for testing.
 *
 * @author Chenhong
 * @version 3.2
 */
public class MyContactManager implements ContactManager {

    /**
     * Represents the the Contact Map. This will store the Contact instance with id as key and instance as value.
     */
    private Map contacts = new HashMap();

    /**
     * <p>
     * Add a Contact instance.
     * </p>
     */
    public void addContact(Contact contact, boolean arg1) throws PersistenceException, IDGenerationException,
            InvalidPropertyException, AuditException {

        contacts.put(new Long(contact.getId()), contact);
    }

    /**
     * <p>
     * Add a list of Contact instances.
     * </p>
     */
    public void addContacts(Contact[] c, boolean arg1) throws InvalidPropertyException, PersistenceException,
            IDGenerationException, AuditException, BatchOperationException {

        for (int i = 0; i < c.length; i++) {
            addContact(c[i], arg1);
        }
    }

    /**
     * <p>
     * Get the Contact instance with id.
     * </p>
     */
    public Contact retrieveContact(long id) throws PersistenceException {
        return (Contact) contacts.get(new Long(id));
    }

    /**
     * <p>
     * Get a list of Contact instance with a list of id.
     * </p>
     */
    public Contact[] retrieveContatcts(long[] ids) throws PersistenceException {
        Contact[] ret = new Contact[ids.length];

        for (int i = 0; i < ids.length; i++) {
            ret[i] = (Contact) contacts.get(new Long(ids[i]));
        }

        return ret;
    }

    /**
     * Remove Contact instance with id.
     */
    public void removeContact(long id, boolean arg1) throws PersistenceException, AuditException {

        contacts.remove(new Long(id));
    }

    /**
     * Remove a list of Contact with a list of id.
     */
    public void removeContacts(long[] ids, boolean arg1) throws PersistenceException, AuditException,
            BatchOperationException {

        for (int i = 0; i < ids.length; i++) {
            contacts.remove(new Long(ids[i]));
        }
    }

    /**
     * Update the Contact instance.
     */
    public void updateContact(Contact contact, boolean arg1) throws PersistenceException, InvalidPropertyException,
            AuditException, EntityNotFoundException {

        contacts.put(new Long(contact.getId()), contact);
    }

    /**
     * Update a list of Contact instances.
     */
    public void updateContacts(Contact[] c, boolean arg1) throws PersistenceException, InvalidPropertyException,
            AuditException, EntityNotFoundException, BatchOperationException {

        for (int i = 0; i < c.length; i++) {
            updateContact(c[i], false);
        }

    }

    /**
     * Get all Contact instances.
     */
    public Contact[] getAllContacts() throws PersistenceException {

        return (Contact[]) contacts.values().toArray(new Contact[contacts.size()]);
    }

    /**
     * Search the Contacts with filter.
     *
     * <p>
     * But in this implementation, we return all Contact instances.
     * </p>
     */
    public Contact[] searchContacts(Filter arg0) throws PersistenceException {

        return (Contact[]) contacts.values().toArray(new Contact[contacts.size()]);
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#associate(com.topcoder.timetracker.contact.Contact, long,
     *      boolean)
     */
    public void associate(Contact contact, long id, boolean arg2) throws PersistenceException, AuditException {

        Connection connection = null;
        PreparedStatement statement = null;

        String query = "insert into contact_relation(entity_id, "
                + "contact_type_id, creation_date, creation_user, modification_date, modification_user, contact_id)"
                + " values (?, ?, CURRENT, USER, CURRENT, USER, ?)";

        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.setLong(2, ContactType.USER.getId());
            statement.setLong(3, contact.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new PersistenceException("Failed to associate", e);
        } finally {
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#deassociate(com.topcoder.timetracker.contact.Contact, long,
     *      boolean)
     */
    public void deassociate(Contact contact, long id, boolean arg2) throws PersistenceException, AuditException {

        Connection connection = null;
        PreparedStatement statement = null;

        String query = "delete from contact_relation where "
                + "entity_id = ? and contact_type_id = ? and contact_id = ?";

        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(true);
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.setLong(2, ContactType.USER.getId());
            statement.setLong(3, contact.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new PersistenceException("Failed to associate", e);
        } finally {
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }
    }

}