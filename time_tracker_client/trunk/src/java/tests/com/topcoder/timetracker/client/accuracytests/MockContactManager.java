/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.PersistenceException;

/**
 * <p>
 * This is a mock implementation of <code>ContactManager</code> and it is used to help accuracy tests.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class MockContactManager implements ContactManager {
    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param contact not used
     * @param doAudit not used
     */
    public void addContact(Contact contact, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param contacts not used
     * @param doAudit not used
     */
    public void addContacts(Contact[] contacts, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Returns a contact for testing purpose.
     * </p>
     *
     * @param id the contact id to get
     * @return the contact instance for testing purpose
     */
    public Contact retrieveContact(long id) {
        Contact contact = new Contact();
        contact.setId(id);
        contact.setEmailAddress("jess@topcoder.com");
        contact.setFirstName("jess");
        contact.setLastName("jin");

        return contact;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param ids not used
     * @return an empty array
     */
    public Contact[] retrieveContacts(long[] ids) {
        return new Contact[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param id not used
     * @param doAudit not used
     */
    public void removeContact(long id, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param ids not used
     * @param doAudit not used
     */
    public void removeContacts(long[] ids, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param contact not used
     * @param doAudit not used
     */
    public void updateContact(Contact contact, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param contacts not used
     * @param doAudit not used
     */
    public void updateContacts(Contact[] contacts, boolean doAudit) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return an empty array
     */
    public Contact[] getAllContacts() {
        return new Contact[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param filter not used
     * @return an empty array
     */
    public Contact[] searchContacts(Filter filter) {
        return new Contact[0];
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param contact not used
     * @param entityId not used
     * @param doAudit not used
     */
    public void associate(Contact contact, long entityId, boolean doAudit) {
        // empty
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param contact not used
     * @param entityId not used
     * @param doAudit not used
     */
    public void deassociate(Contact contact, long entityId, boolean doAudit) {
        // empty
    }

    public Contact[] retrieveContatcts(long[] ids) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }
}