/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate;

/**
 * <p>
 * Accuracy Unit test cases for ContactBean.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ContactBeanAccuracyTests extends TestCase {
    /**
     * <p>
     * ContactManagerLocalDelegate instance for testing.
     * </p>
     */
    private ContactManagerLocalDelegate delegate;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
    	AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.AUDIT_CONFIG_FILE);
        AccuracyTestHelper.eJBConfig();
        AccuracyTestHelper.setUpDataBase();

        delegate = new ContactManagerLocalDelegate();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        delegate = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ContactBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor ContactBean#ContactBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ContactBean instance.", delegate);
    }

    /**
     * <p>
     * Tests ContactBean#associate(Contact,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociate() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        delegate.associate(contact, 2, false);
    }

    /**
     * <p>
     * Tests ContactBean#deassociate(Contact,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeassociate() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        delegate.associate(contact, 2, false);
        delegate.deassociate(contact, 2, false);
    }

    /**
     * <p>
     * Tests ContactBean#addContact(Contact,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        AccuracyTestHelper.assertContactEquals(contact, delegate.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests ContactBean#addContacts(Contact[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContacts(new Contact[] {contact}, false);
        AccuracyTestHelper.assertContactEquals(contact, delegate.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests ContactBean#retrieveContact(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        AccuracyTestHelper.assertContactEquals(contact, delegate.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests ContactBean#retrieveContacts(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        AccuracyTestHelper.assertContactEquals(contact, delegate.retrieveContacts(new long[] {contact.getId()})[0]);
    }

    /**
     * <p>
     * Tests ContactBean#removeContact(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        delegate.removeContact(contact.getId(), false);

        assertEquals("Failed to remove contact.", 0, delegate.getAllContacts().length);
    }

    /**
     * <p>
     * Tests ContactBean#removeContacts(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        delegate.removeContacts(new long[] {contact.getId()}, false);

        assertEquals("Failed to remove contact.", 0, delegate.getAllContacts().length);
    }

    /**
     * <p>
     * Tests ContactBean#updateContact(Contact,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);

        contact.setFirstName("new");
        delegate.updateContact(contact, false);

        AccuracyTestHelper.assertContactEquals(contact, delegate.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests ContactBean#updateContacts(Contact[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);

        contact.setFirstName("new");
        delegate.updateContacts(new Contact[] {contact}, false);

        AccuracyTestHelper.assertContactEquals(contact, delegate.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests ContactBean#getAllContacts() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);
        delegate.removeContact(contact.getId(), false);

        assertEquals("Failed to remove contact.", 0, delegate.getAllContacts().length);
    }

    /**
     * <p>
     * Tests ContactBean#searchContacts(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        delegate.addContact(contact, false);

        Contact[] contacts = delegate.searchContacts(ContactFilterFactory.createPhoneNumberFilter("1234567890"));

        AccuracyTestHelper.assertContactEquals(contact, contacts[0]);
    }

}