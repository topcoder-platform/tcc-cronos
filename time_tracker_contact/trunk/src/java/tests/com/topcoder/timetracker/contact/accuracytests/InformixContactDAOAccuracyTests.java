/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.persistence.InformixContactDAO;

/**
 * <p>
 * Accuracy Unit test cases for InformixContactDAO.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class InformixContactDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * InformixContactDAO instance for testing.
     * </p>
     */
    private InformixContactDAO instance;

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

        instance = new InformixContactDAO();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;

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
        return new TestSuite(InformixContactDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InformixContactDAO#InformixContactDAO(String) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        assertNotNull("Failed to create InformixContactDAO instance.", new InformixContactDAO(
            "com.topcoder.timetracker.contact.persistence.InformixContactDAO"));
    }

    /**
     * <p>
     * Tests ctor InformixContactDAO#InformixContactDAO() for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create InformixContactDAO instance.", instance);
    }

    /**
     * <p>
     * Tests ctor InformixContactDAO#InformixContactDAO(String,AuditManager) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        assertNotNull("Failed to create InformixContactDAO instance.", new InformixContactDAO(
            "com.topcoder.timetracker.contact.persistence.InformixContactDAO", null));
    }

    /**
     * <p>
     * Tests InformixContactDAO#associate(Contact,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociate() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        instance.associate(contact, 2, false);
    }

    /**
     * <p>
     * Tests InformixContactDAO#deassociate(Contact,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeassociate() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        instance.associate(contact, 2, false);
        instance.deassociate(contact, 2, false);
    }

    /**
     * <p>
     * Tests InformixContactDAO#addContact(Contact,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        AccuracyTestHelper.assertContactEquals(contact, instance.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests InformixContactDAO#addContacts(Contact[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContacts(new Contact[] {contact}, false);
        AccuracyTestHelper.assertContactEquals(contact, instance.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests InformixContactDAO#retrieveContact(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        AccuracyTestHelper.assertContactEquals(contact, instance.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests InformixContactDAO#retrieveContacts(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        AccuracyTestHelper.assertContactEquals(contact, instance.retrieveContacts(new long[] {contact.getId()})[0]);
    }

    /**
     * <p>
     * Tests InformixContactDAO#removeContact(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        instance.removeContact(contact.getId(), false);

        assertEquals("Failed to remove contact.", 0, instance.getAllContacts().length);
    }

    /**
     * <p>
     * Tests InformixContactDAO#removeContacts(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        instance.removeContacts(new long[] {contact.getId()}, false);

        assertEquals("Failed to remove contact.", 0, instance.getAllContacts().length);
    }

    /**
     * <p>
     * Tests InformixContactDAO#updateContact(Contact,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);

        contact.setFirstName("new");
        instance.updateContact(contact, false);

        AccuracyTestHelper.assertContactEquals(contact, instance.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests InformixContactDAO#updateContacts(Contact[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);

        contact.setFirstName("new");
        instance.updateContacts(new Contact[] {contact}, false);

        AccuracyTestHelper.assertContactEquals(contact, instance.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Tests InformixContactDAO#getAllContacts() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);
        instance.removeContact(contact.getId(), false);

        assertEquals("Failed to remove contact.", 0, instance.getAllContacts().length);
    }

    /**
     * <p>
     * Tests InformixContactDAO#searchContacts(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchContacts() throws Exception {
        Contact contact = AccuracyTestHelper.createContact();
        instance.addContact(contact, false);

        Contact[] contacts = instance.searchContacts(ContactFilterFactory.createPhoneNumberFilter("1234567890"));

        AccuracyTestHelper.assertContactEquals(contact, contacts[0]);
    }

}