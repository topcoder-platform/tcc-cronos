/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;

/**
 *
 * <p>
 * This is a mocked implementation of ContactManager.
 * </p>
 *
 * @author Achilles_2005
 * @version 3.2
 */
public class MockedContactManager implements ContactManager {

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public MockedContactManager() {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param contact the argument
     * @param doAudit the argument
     */
    public void addContact(Contact contact, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param contacts the argument
     * @param doAudit the argument
     */
    public void addContacts(Contact[] contacts, boolean doAudit) {
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param contact the argument
     * @param entityId the argument
     * @param doAudit the argument
     */
    public void associate(Contact contact, long entityId, boolean doAudit) {
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param contact the argument
     * @param entityId the argument
     * @param doAudit the argument
     */
    public void deassociate(Contact contact, long entityId, boolean doAudit) {
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @return null
     */
    public Contact[] getAllContacts() {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param id the argument
     * @param doAudit the argument
     */
    public void removeContact(long id, boolean doAudit) {

    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param ids the argument
     * @param doAudit the argument
     */
    public void removeContacts(long[] ids, boolean doAudit) {
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param id the argument
     * @return null
     */
    public Contact retrieveContact(long id) {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param ids the argument
     * @return null
     */
    public Contact[] retrieveContatcts(long[] ids) {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param filter the argument
     * @return the array with Contact
     */
    public Contact[] searchContacts(Filter filter) {
        return new Contact[] {new Contact()};
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param contact the argument
     * @param doAudit the argument
     */
    public void updateContact(Contact contact, boolean doAudit) {
    }

    /**
     * <p>
     * Mock method.
     * </p>
     *
     * @param contacts the argument
     * @param doAudit the argument
     */
    public void updateContacts(Contact[] contacts, boolean doAudit) {

    }

}
