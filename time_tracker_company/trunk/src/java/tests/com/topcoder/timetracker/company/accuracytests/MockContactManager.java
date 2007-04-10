/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.accuracytests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;

/**
 * Mock ContactManager for testing.
 *
 * @author mittu
 * @version 3.2
 */
public class MockContactManager implements ContactManager {
    /**
     * <p>
     * Represents a mock contact persistence.
     * </p>
     */
    private final Map contacts = new HashMap();

    /**
     * <p>
     * Represents the company-contact association.
     * </p>
     */
    private final Map association = new HashMap();

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#addContact(com.topcoder.timetracker.contact.Contact,
     *      boolean)
     */
    public void addContact(Contact contact, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, IDGenerationException, AuditException {
        contacts.put(new Long(contact.getId()), contact);
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#addContacts(com.topcoder.timetracker.contact.Contact[],
     *      boolean)
     */
    public void addContacts(Contact[] contacts, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, IDGenerationException, AuditException, BatchOperationException {
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#associate(com.topcoder.timetracker.contact.Contact,
     *      long, boolean)
     */
    public void associate(Contact contact, long entity_id, boolean doAudit) throws PersistenceException,
            AuditException {
        association.put(new Long(entity_id), new Long(contact.getId()));
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#deassociate(com.topcoder.timetracker.contact.Contact,
     *      long, boolean)
     */
    public void deassociate(Contact contact, long entity_id, boolean doAudit) throws PersistenceException,
            AuditException {
        association.remove(new Long(entity_id));
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#getAllContacts()
     */
    public Contact[] getAllContacts() throws PersistenceException {
        return (Contact[]) contacts.values().toArray(new Contact[contacts.size()]);
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#removeContact(long, boolean)
     */
    public void removeContact(long id, boolean doAudit) throws PersistenceException {
        contacts.remove(new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#removeContacts(long[], boolean)
     */
    public void removeContacts(long[] ids, boolean doAudit) throws PersistenceException,
            BatchOperationException {
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#retrieveContact(long)
     */
    public Contact retrieveContact(long id) throws PersistenceException {
        return (Contact) contacts.get(new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#retrieveContatcts(long[])
     */
    public Contact[] retrieveContatcts(long[] ids) throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#searchContacts(com.topcoder.search.builder.filter.Filter)
     */
    public Contact[] searchContacts(Filter filter) throws PersistenceException {
        if (getAllContacts().length != 0) {
            return new Contact[] {getAllContacts()[0]};
        }
        return getAllContacts();
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#updateContact(com.topcoder.timetracker.contact.Contact,
     *      boolean)
     */
    public void updateContact(Contact contact, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, AuditException, EntityNotFoundException {
        contacts.put(new Long(contact.getId()), contact);
    }

    /**
     * @see com.topcoder.timetracker.contact.ContactManager#updateContacts(com.topcoder.timetracker.contact.Contact[],
     *      boolean)
     */
    public void updateContacts(Contact[] contacts, boolean doAudit) throws InvalidPropertyException,
            PersistenceException, AuditException, EntityNotFoundException, BatchOperationException {
    }
}
