/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import java.util.Date;

import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.MockTransaction;

/**
 * <p>This test case contains accuracy tests for <code>ContactManagerLocalDelegate</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactManagerLocalDelegateTestAcc extends BaseTestCase {
    /**
     * <p>
     * <code>Contact</code> used in the test case.
     * </p>
     */
    private Contact contact = null;

    /**
     * <p>
     * <code>MockTransaction</code> used in the test case.
     * </p>
     */
    private MockTransaction transaction = null;

    /**
     * <p>
     * Previous count of audit records.
     * </p>
     */
    private int preCount = 0;

    /**
     * <p>
     * <code>ContactManagerLocalDelegate</code> used in the test case.
     * </p>
     */
    private ContactManagerLocalDelegate localDelegate = null;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
        localDelegate = new ContactManagerLocalDelegate();
        preCount = this.getAuditRecordsCount();
        transaction = this.getTransaction();
        contact = this.getContact();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.unBind("java:comp/env/SpecificationNamespace");
        this.unBind("java:comp/env/AddressDAOKey");
        localDelegate = null;
        preCount = 0;
        transaction = null;
        contact = null;
        super.tearDown();
    }
    /**
     * Accuracy test for the constructor <code>ContactManagerLocalDelegate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCtor1() throws Exception {
        assertNotNull(this.localDelegate);
    }
    /**
     * Accuracy test for the constructor <code>ContactManagerLocalDelegate(String)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testCtor2() throws Exception {
        assertNotNull(new ContactManagerLocalDelegate(ContactManagerLocalDelegate.class.getName()));
    }
    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>retrieveContact()</code>
     * <code>updateContact()</code> and <code>removeContact()</code> with audit.
     * Since the type of contact is set, so audit will be performed when <code>addContact()</code>
     * and <code>updateContact()</code>.
     * But the contact is not associated with any entity, so no audit will be performed
     * when <code>removeContact()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationsWithAudit1() throws Exception {
        localDelegate.addContact(contact, true);
        assertFalse("Changed status should be false after adding", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Contact addedContact = localDelegate.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", addedContact.isChanged());
        this.assertContactEquals(contact, addedContact);

        //line2 is null-able
        contact.setFirstName("new name");
        localDelegate.updateContact(contact, true);
        assertFalse("Changed status should be false after updating", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Contact updatedContact = localDelegate.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", updatedContact.isChanged());
        this.assertContactEquals(contact, updatedContact);

        localDelegate.removeContact(contact.getId(), true);
        this.assertTransactionCommited(transaction);
        //contact is associated with no entity, no audit performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localDelegate.retrieveContact(contact.getId()));
    }

    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>retrieveContact()</code>
     * <code>updateContact()</code> and <code>removeContact()</code> with audit.
     * The contact is associated with an entity, audit will be performed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationsWithAudi2() throws Exception {
        //Add the contact first
        localDelegate.addContact(contact, true);
        assertFalse("Changed status should be false after adding", contact.isChanged());
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);
        this.assertTransactionCommited(transaction);

        Contact addedContact = localDelegate.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", addedContact.isChanged());
        this.assertContactEquals(contact, addedContact);

        //change city
        contact.setFirstName("new name");
        localDelegate.updateContact(contact, true);
        assertFalse("Changed status should be false after updating", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Contact updatedContact = localDelegate.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", updatedContact.isChanged());
        this.assertContactEquals(contact, updatedContact);

        //Associate the contact with enitity id 1, so audit will be performed when removing
        localDelegate.associate(contact, 1, true);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        localDelegate.removeContact(contact.getId(), true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed. Note the association will also be removed, so the increased audit count is 2
        preCount = this.assertAuditRecordsCount(preCount, 2);
        assertNull(localDelegate.retrieveContact(contact.getId()));
    }
    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>retrieveContact()</code>
     * <code>updateContact()</code> and <code>removeContact()</code> with audit.
     * The contact is associated with an entity, but doAudit flag is false, audit will not be performed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationsWithoutAudit() throws Exception {
        //Add the contact first
        localDelegate.addContact(contact, false);
        assertFalse("Changed status should be false after adding", contact.isChanged());
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        this.assertTransactionCommited(transaction);

        Contact addedContact = localDelegate.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", addedContact.isChanged());
        this.assertContactEquals(contact, addedContact);

        //Associate the contact with enitity id 1
        contact.setContactType(ContactType.USER);
        localDelegate.associate(contact, 1, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //change city
        contact.setFirstName("new name");
        localDelegate.updateContact(contact, false);
        assertFalse("Changed status should be false after updating", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        Contact updatedContact = localDelegate.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", updatedContact.isChanged());
        this.assertContactEquals(contact, updatedContact);

        localDelegate.removeContact(contact.getId(), false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localDelegate.retrieveContact(contact.getId()));
    }
    /**
     * <p>
     * Test accuracy of methods <code>associate()</code> and <code>deassociate()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociateWithAudit() throws Exception {
        //Add the contact first
        contact.setContactType(ContactType.USER);
        localDelegate.addContact(contact, true);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the contact with entity 1
        localDelegate.associate(contact, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the same contact with same entity 1 again, method is simply returned, no audit performed
        localDelegate.associate(contact, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same contact with another entity 2, the previous association should be deleted
        localDelegate.associate(contact, 2, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, one for deletion of the previous association, one for insertion of
        //new association
        preCount = this.assertAuditRecordsCount(preCount, 2);

        localDelegate.deassociate(contact, 3, true);
        //association does not exist, method is simply returned, no audit performed
        this.assertAuditRecordsCount(preCount, 0);

        //Deassociate contact with entity 2
        localDelegate.deassociate(contact, 2, true);
        //Audit should performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Remove the contact
        localDelegate.removeContact(contact.getId(), true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed as no association
        preCount = this.assertAuditRecordsCount(preCount, 0);
    }
    /**
     * <p>
     * Test accuracy of methods <code>associate()</code> and <code>deassociate()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociateWithoutAudit() throws Exception {
        //Add the contact first
        localDelegate.addContact(contact, false);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the contact with entity 1
        localDelegate.associate(contact, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same contact with same entity 1 again, method is simply returned, no audit performed
        localDelegate.associate(contact, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same contact with another entity 2, the previous association should be deleted
        localDelegate.associate(contact, 2, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        localDelegate.deassociate(contact, 3, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Deassociate contact with entity 2
        localDelegate.deassociate(contact, 2, false);
        //Audit should not be performed
        this.assertAuditRecordsCount(preCount, 0);

        //Remove the contact
        localDelegate.removeContact(contact.getId(), false);
        this.assertTransactionCommited(transaction);
    }
    /**
     * <p>
     * Test accuracy of method <code>addContacts()</code>, <code>getAllContacts()</code>,
     * <code>updateContacts()</code> and <code>removeContacts()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationWithAudit() throws Exception {
        Contact[] contactsToAdd = new Contact[10];
        for (int i = 0; i < 10; i++) {
            contactsToAdd[i] = this.getContact();
        }

        //Add 10 contacts
        localDelegate.addContacts(contactsToAdd, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        long ids[] = new long[10];

        //Get all added contacts
        Contact[] addedContacts = localDelegate.getAllContacts();
        assertTrue("There should be 10 contact", addedContacts.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contactsToAdd[i], addedContacts[i]);
            assertFalse("Changed status should be false after adding", contactsToAdd[i].isChanged());
            ids[i] = contactsToAdd[i].getId();
            addedContacts[i].setFirstName("new name" + i);
            addedContacts[i].setContactType(ContactType.CLIENT);
        }

        //update the 10 contacts
        localDelegate.updateContacts(addedContacts, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        //Get all updated contacts
        Contact[] updatedContacts = localDelegate.getAllContacts();
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(updatedContacts[i], addedContacts[i]);
            assertFalse("Changed status should be false after updating", addedContacts[i].isChanged());
        }

        //Remove the 10 contacts
        localDelegate.removeContacts(ids, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed, as no association
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no contact after removal
        assertTrue("There should be 0 contact", localDelegate.getAllContacts().length == 0);
    }
    /**
     * <p>
     * Test accuracy of method <code>addContacts()</code>, <code>getAllContacts()</code>,
     * <code>updateContacts()</code> and <code>removeContacts()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationWithoutAudit() throws Exception {
        Contact[] contactsToAdd = new Contact[10];
        for (int i = 0; i < 10; i++) {
            contactsToAdd[i] = this.getContact();
        }

        //Add 10 contacts
        localDelegate.addContacts(contactsToAdd, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        long ids[] = new long[10];

        //Get all added contacts
        Contact[] addedContacts = localDelegate.getAllContacts();
        assertTrue("There should be 10 contact", addedContacts.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contactsToAdd[i], addedContacts[i]);
            assertFalse("Changed status should be false after adding", contactsToAdd[i].isChanged());
            ids[i] = contactsToAdd[i].getId();
            addedContacts[i].setFirstName("new name" + i);
        }

        //update the 10 contacts
        localDelegate.updateContacts(addedContacts, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Get all updated contacts
        Contact[] updatedContacts = localDelegate.getAllContacts();
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(updatedContacts[i], addedContacts[i]);
            assertFalse("Changed status should be false after updating", addedContacts[i].isChanged());
        }

        //Remove the 10 contacts
        localDelegate.removeContacts(ids, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no contact after removal
        assertTrue("There should be 0 contact", localDelegate.getAllContacts().length == 0);
    }
    /**
     * <p>
     * Test accuracy of method <code>addContacts()</code>, <code>updateContacts()</code>
     * <code>retrieveContacts()</code> and <code>removeContacts()</code> with duplication in passed in array.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationWithDuplication() throws Exception {
        contact.setContactType(ContactType.COMPANY);
        Contact[] duplicateContacts = new Contact[10];
        for (int i = 0; i < 10; i++) {
            duplicateContacts[i] = contact;
        }

        //Add with duplication objects
        localDelegate.addContacts(duplicateContacts, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            ids[i] = duplicateContacts[i].getId();
        }

        //Retrieve with duplicate ids
        Contact[] addedContacts = localDelegate.retrieveContacts(ids);
        assertEquals("There should be only one record inserted into database", 1, addedContacts.length);
        this.assertContactEquals(contact, addedContacts[0]);

        contact.setFirstName("new name");

        //Update with duplication objects
        localDelegate.updateContacts(duplicateContacts, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the contact, so audit will be performed when removing
        localDelegate.associate(contact, 1, false);

        localDelegate.removeContacts(ids, true);
        this.assertTransactionCommited(transaction);
        //There should be only 2 audit record, including the record for deletion of association
        this.assertAuditRecordsCount(preCount, 2);

        //There should be no records after removal
        assertEquals(0, localDelegate.retrieveContacts(ids).length);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchContacts()</code>. Search contacts by creation/modification date.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch1() throws Exception {
        Contact[] contacts = new Contact[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            contacts[i] = this.getContact();
        }

        Date from = new Date();
        Thread.sleep(1000);
        localDelegate.addContacts(contacts, false);
        Thread.sleep(1000);
        Date to = new Date();

        Contact[] results = localDelegate.searchContacts(ContactFilterFactory.createCreatedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localDelegate.searchContacts(ContactFilterFactory.createModifiedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        //upperThreshold is null
        results = localDelegate.searchContacts(ContactFilterFactory.createCreatedInFilter(from, null));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        //lowerThreshold is null
        results = localDelegate.searchContacts(ContactFilterFactory.createModifiedInFilter(null, to));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localDelegate.removeContacts(ids, false);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchContacts()</code>. Search contacts by creation/modification user.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch2() throws Exception {
        Contact[] contacts = new Contact[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            contacts[i] = this.getContact();
        }

        localDelegate.addContacts(contacts, false);

        Contact[] results = localDelegate.searchContacts(ContactFilterFactory.createCreatedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localDelegate.searchContacts(ContactFilterFactory.createModifiedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localDelegate.removeContacts(ids, false);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchContacts()</code>. Search contacts by postal code, city, state and country.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch3() throws Exception {
        Contact[] contacts = new Contact[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            contacts[i] = this.getContact();
        }

        localDelegate.addContacts(contacts, false);

        Contact[] results = localDelegate.searchContacts(ContactFilterFactory.createEmailAddressFilter("email"));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localDelegate.searchContacts(ContactFilterFactory.createPhoneNumberFilter("111111"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localDelegate.searchContacts(ContactFilterFactory.createNameKeywordFilter("name"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localDelegate.removeContacts(ids, false);

        localDelegate.removeContacts(ids, false);
    }

    /**
     * <p>
     * Test accuracy of method <code>searchContacts()</code>. Search contacts by type and entity id.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearch4() throws Exception {
        Contact[] contacts = new Contact[10];
        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            contacts[i] = this.getContact();
        }

        localDelegate.addContacts(contacts, false);

        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            localDelegate.associate(contacts[i], 1, false);
        }

        Contact[] results = localDelegate.searchContacts(ContactFilterFactory.createTypeFilter(ContactType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localDelegate.searchContacts(ContactFilterFactory.createEntityIDFilter(1, ContactType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localDelegate.removeContacts(ids, false);
    }
}
