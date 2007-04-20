/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.PersistenceException;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Defines a mock class which implements the <code>ContactManager</code> interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockContactManager implements ContactManager {
    /** Represents the contacts hold by this class. */
    private Map contacts = new HashMap();

    /** Represents the relationship between company and contact hold by this class. */
    private Map companyContacts = new HashMap();

    /** Represents the flag which will make all the methods of this mock class always throw exception. */
    private boolean alwaysThrowException;

    /** Represents the flag which will prevent the search and create method to throw exception. */
    private boolean prevent = false;

    /**
     * Creates a new MockContactManager object.
     *
     * @param alwaysThrowException the flag which will make all the methods of this mock class always throw exception.
     */
    public MockContactManager(boolean alwaysThrowException) {
        this.alwaysThrowException = alwaysThrowException;
    }

    /**
     * Creates a new MockContactManager object.
     *
     * @param alwaysThrowException the flag which will make all the methods of this mock class always throw exception.
     * @param prevent the flag which will prevent the search and create method to throw exception.
     */
    public MockContactManager(boolean alwaysThrowException, boolean prevent) {
        this.alwaysThrowException = alwaysThrowException;
        this.prevent = prevent;
    }

    /**
     * <p>
     * Get the contacts hold by this class.
     * </p>
     *
     * @return the contacts hold by this class.
     */
    public Contact[] getContacts() {
        return (Contact[]) contacts.values().toArray(new Contact[contacts.size()]);
    }

    /**
     * <p>
     * Get the contact hold by the given company.
     * </p>
     *
     * @param companyId the company id.
     *
     * @return the contact hold by this class.
     */
    public Contact getContact(long companyId) {
        return (Contact) this.contacts.get((Long) companyContacts.get(new Long(companyId)));
    }

    /**
     * <p>
     * Add the given contact.
     * </p>
     *
     * @param contact non null contact to be added.
     * @param doAudit whether this action should be audited.
     *
     * @throws IllegalArgumentException if the contact is null.
     * @throws PersistenceException if it is required to be thrown.
     */
    public void addContact(Contact contact, boolean doAudit) throws PersistenceException {
        if (contact == null) {
            throw new IllegalArgumentException("contact is null.");
        }

        if (alwaysThrowException && !prevent) {
            throw new PersistenceException("PersistenceException");
        }

        this.contacts.put(new Long(contact.getId()), contact);
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param contacts the non null contacts to be added
     * @param doAudit whether this action should be audited
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void addContacts(Contact[] contacts, boolean doAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Associate the contact with the entity.
     * </p>
     *
     * @param contact the non null contact.
     * @param entityId the non null positive id of entity.
     * @param doAudit whether this action should be audited.
     *
     * @throws IllegalArgumentException if the contact is null or the id is not positive.
     * @throws PersistenceException if it is required to be thrown.
     */
    public void associate(Contact contact, long entityId, boolean doAudit) throws PersistenceException {
        if (contact == null) {
            throw new IllegalArgumentException("contact is null.");
        }

        if (entityId <= 0) {
            throw new IllegalArgumentException("entityId is not positive.");
        }

        if (alwaysThrowException && !prevent) {
            throw new PersistenceException("PersistenceException");
        }

        this.companyContacts.put(new Long(entityId), new Long(contact.getId()));
    }

    /**
     * <p>
     * Deassociate the contact with the entity.
     * </p>
     *
     * @param contact the non null contact.
     * @param entityId the non null positive id of entity.
     * @param doAudit whether this action should be audited.
     *
     * @throws IllegalArgumentException if the contact is null or the id is not positive.
     * @throws PersistenceException if it is required to be thrown.
     */
    public void deassociate(Contact contact, long entityId, boolean doAudit) throws PersistenceException {
        if (contact == null) {
            throw new IllegalArgumentException("contact is null.");
        }

        if (entityId <= 0) {
            throw new IllegalArgumentException("entityId is not positive.");
        }

        if (alwaysThrowException) {
            throw new PersistenceException("PersistenceException");
        }

        this.companyContacts.remove(new Long(entityId));
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @return Non null, possible empty array containing all non null contacts.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Contact[] getAllContacts() {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Remove the Contact with given id.
     * </p>
     *
     * @param id the positive id of the Contact.
     * @param doAudit whether this action should be audited.
     *
     * @throws IllegalArgumentException if the id is not positive.
     * @throws PersistenceException if it is required to be thrown.
     */
    public void removeContact(long id, boolean doAudit) throws PersistenceException {
        if (id <= 0) {
            throw new IllegalArgumentException("entityId is not positive.");
        }

        if (alwaysThrowException) {
            throw new PersistenceException("PersistenceException");
        }

        this.contacts.remove(new Long(id));
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the Contacts
     * @param doAudit whether this action should be audited
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void removeContacts(long[] ids, boolean doAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param id the id of the contact to be retrieved
     *
     * @return the contact with the given id, null if the contact if not found
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Contact retrieveContact(long id) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the contacts
     *
     * @return the non null contacts with given id, the containing non null contacts
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public Contact[] retrieveContatcts(long[] ids) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Search the Contacts with the given Filter.
     * </p>
     *
     * @param filter non null filter
     *
     * @return non null, possible empty array containing all contacts with given condition
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws PersistenceException if it is required to be thrown.
     */
    public Contact[] searchContacts(Filter filter) throws PersistenceException {
        if (filter == null) {
            throw new IllegalArgumentException("filter is null.");
        }

        if (alwaysThrowException && !prevent) {
            throw new PersistenceException("PersistenceException");
        }

        return this.getContacts();
    }

    /**
     * <p>
     * Update the given Contact.
     * </p>
     *
     * @param contact the non null contact to be updated
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null
     * @throws PersistenceException if it is required to be thrown.
     */
    public void updateContact(Contact contact, boolean doAudit) throws PersistenceException {
        if (contact == null) {
            throw new IllegalArgumentException("contact is null.");
        }

        if (alwaysThrowException) {
            throw new PersistenceException("PersistenceException");
        }

        this.contacts.put(new Long(contact.getId()), contact);
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param contacts non null, possible emtpy array containing non null contact
     * @param doAudit whether this action should be audited
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void updateContacts(Contact[] contacts, boolean doAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    public Contact[] retrieveContacts(long[] arg0) throws PersistenceException, AssociationException {
        // TODO Auto-generated method stub
        return null;
    }
}
