/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.stresstests;

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
 * Mock contact manager class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockContactManager implements ContactManager {
    /**
     * <p>
     * Add the given Contacts.
     * </p>
     *
     * @param contact non null contact to be added
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null
     * @throws PersistenceException if it is thrown by the localEJB
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws InvalidPropertyException if the properties of given Client is invalid
     * @throws AuditException if exception occurs when audit
     */
    public void addContact(Contact contact, boolean doAudit) {
    }

    /**
     * <p>
     * Add the given Contacts
     * </p>
     *
     * @param contacts the non null contacts to be added
     * @param doAudit whether this action should be audited
     *
     * @throws InvalidPropertyException if the properties of given Client is invalid
     * @throws IllegalArgumentException if the client is null or any client contained is null
     * @throws PersistenceException if it is thrown by the localEJB
     * @throws IDGenerationException if any exception occurs while generating ID
     * @throws AuditException if exception occurs when audit
     * @throws BatchOperationException if any operation in the batch is failed
     */
    public void addContacts(Contact[] contacts, boolean doAudit) {
    }

    /**
     * <p>
     * Retrieve the given Contact
     * </p>
     *
     * @param id the id of the contact to be retrieved
     *
     * @return the contact with the given id, null if the contact if not found
     *
     * @throws IllegalArgumentException if the id small or equal 0
     * @throws PersistenceException if it is thrown by the localEJB
     */
    public Contact retrieveContact(long id) throws PersistenceException {
        Contact contact = new Contact();
        contact.setEmailAddress("zk@db.org");
        contact.setFirstName("Firstname");
        contact.setLastName("LastName");
        contact.setId(id);

        return contact;
    }

    /**
     * <p>
     * Retrieve the given Contacts.
     * </p>
     *
     * @param ids the non null, possible empty greater 0 ids of the contacts
     *
     * @return the non null contacts with given id, the containing non null contacts
     *
     * @throws PersistenceException if it is thrown by the localEJB
     * @throws IllegalArgumentException if ids is null or any id small or equal 0
     */
    public Contact[] retrieveContacts(long[] ids) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Remove the Contact with given id.
     * </p>
     *
     * @param id the >0 id of the Contact
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the id small or eqaul 0
     * @throws PersistenceException if any exception occurs
     * @throws AuditException if exception occurs when audit
     */
    public void removeContact(long id, boolean doAudit)
        throws PersistenceException, AuditException {
    }

    /**
     * <p>
     * Remove the Contacts with given&nbsp;ids.
     * </p>
     *
     * @param ids the non null, possible empty greater 0 ids of the Contacts
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if any id small or equal 0
     * @throws PersistenceException if any exception occurs
     * @throws AuditException if exception occurs when audit
     * @throws BatchOperationException if any operation in the batch is failed
     */
    public void removeContacts(long[] ids, boolean doAudit)
        throws PersistenceException, AuditException, BatchOperationException {
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
     * @throws PersistenceException if it is thrown by the localEJB
     * @throws InvalidPropertyException if the properties of given contact is invalid
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the contact can't be found
     */
    public void updateContact(Contact contact, boolean doAudit) {
    }

    /**
     * <p>
     * Update the given Contacts.
     * </p>
     *
     * @param contacts non null, possible emtpy array containing non null contact
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if any client is null
     * @throws PersistenceException if it is thrown by the localEJB
     * @throws InvalidPropertyException if the properties of given contact is invalid
     * @throws AuditException if exception occurs when audit
     * @throws EntityNotFoundException if the contact can't be found
     * @throws BatchOperationException if any operation in the batch is failed
     */
    public void updateContacts(Contact[] contacts, boolean doAudit)
        throws PersistenceException, InvalidPropertyException, AuditException, EntityNotFoundException,
            BatchOperationException {
    }

    /**
     * <p>
     * Retrieve all the Contacts.
     * </p>
     *
     * @return Non null, possible empty array containing all non null contacts
     *
     * @throws PersistenceException if it is thrown by the localEJB
     */
    public Contact[] getAllContacts() throws PersistenceException {
        return null;
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
     * @throws PersistenceException if any exception occurs
     * @throws IllegalArgumentException if filter is null
     */
    public Contact[] searchContacts(Filter filter) throws PersistenceException {
        return null;
    }

    /**
     * <p>
     * Associate the contact with the entity.
     * </p>
     *
     * @param contact the non null contact
     * @param entity_id the non null greater 0 id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null or the id small or equal 0
     * @throws PersistenceException if any exception occurs
     * @throws AuditException if exception occurs when audit
     */
    public void associate(Contact contact, long entity_id, boolean doAudit) {
    }

    /**
     * <p>
     * Deassociate the contact with the entity.
     * </p>
     *
     * @param contact the non null contact
     * @param entity_id the non null greator0 id of entity
     * @param doAudit whether this action should be audited
     *
     * @throws IllegalArgumentException if the contact is null or the id not positive
     * @throws PersistenceException if any exception occurs
     * @throws AuditException if exception occurs when audit
     */
    public void deassociate(Contact contact, long entity_id, boolean doAudit) {
    }
}
