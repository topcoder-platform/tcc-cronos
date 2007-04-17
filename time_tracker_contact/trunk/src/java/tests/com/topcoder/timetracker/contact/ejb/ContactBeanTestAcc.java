/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import java.lang.reflect.Proxy;
import java.util.Date;

import javax.ejb.SessionContext;

import org.mockejb.MockEjbObject;

import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactDAO;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.MockTransaction;

/**
 * <p>This test case contains accuracy tests for <code>ContactBean</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactBeanTestAcc extends BaseTestCase {
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
     * <code>ContactLocal</code> used in the test case.
     * </p>
     */
    private ContactLocal localBean = null;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        localBean = this.getContactLocal();
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
        localBean.remove();
        localBean = null;
        preCount = 0;
        transaction = null;
        contact = null;
        super.tearDown();
    }
    /**
     * Accuracy test for the method <code>ejbRemove()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbRemove_Accuracy() throws Exception {
        new ContactBean().ejbRemove();
    }

    /**
     * Accuracy test for the method <code>ejbActivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbActivate_Accuracy() throws Exception {
        new ContactBean().ejbActivate();
    }

    /**
     * Accuracy test for the method <code>ejbPassivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbPassivate_Accuracy() throws Exception {
        new ContactBean().ejbPassivate();
    }

    /**
     * Accuracy test for the method <code>setSessionContext(SessionContext)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetSessionContext_Accuracy() throws Exception {
        SessionContext context = ((MockEjbObject) Proxy.getInvocationHandler(localBean)).getEjbContext();
        ContactBean bean = new ContactBean();

        bean.setSessionContext(context);
        assertEquals("The context should be set properly.", context, this.getField(bean, "sessionContext"));
    }

    /**
     * <p>
     * Test accuracy of ctor.
     * </p>
     */
    public void testCtor() {
        assertNotNull("ContactBean should be instantiated", new ContactBean());
    }

    /**
     * <p>
     * Test accuracy of method <code>ejbCreate()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate() throws Exception {
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
        ContactBean bean = new ContactBean();
        bean.ejbCreate();
        assertTrue(this.getField(bean, "contactDAO") instanceof ContactDAO);
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
        localBean.addContact(contact, true);
        assertFalse("Changed status should be false after adding", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Contact addedContact = localBean.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", addedContact.isChanged());
        this.assertContactEquals(contact, addedContact);

        //line2 is null-able
        contact.setFirstName("new name");
        localBean.updateContact(contact, true);
        assertFalse("Changed status should be false after updating", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Contact updatedContact = localBean.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", updatedContact.isChanged());
        this.assertContactEquals(contact, updatedContact);

        localBean.removeContact(contact.getId(), true);
        this.assertTransactionCommited(transaction);
        //contact is associated with no entity, no audit performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localBean.retrieveContact(contact.getId()));
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
        localBean.addContact(contact, true);
        assertFalse("Changed status should be false after adding", contact.isChanged());
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);
        this.assertTransactionCommited(transaction);

        Contact addedContact = localBean.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", addedContact.isChanged());
        this.assertContactEquals(contact, addedContact);

        //change city
        contact.setFirstName("new name");
        localBean.updateContact(contact, true);
        assertFalse("Changed status should be false after updating", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        Contact updatedContact = localBean.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", updatedContact.isChanged());
        this.assertContactEquals(contact, updatedContact);

        //Associate the contact with enitity id 1, so audit will be performed when removing
        localBean.associate(contact, 1, true);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        localBean.removeContact(contact.getId(), true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed. Note the association will also be removed, so the increased audit count is 2
        preCount = this.assertAuditRecordsCount(preCount, 2);
        assertNull(localBean.retrieveContact(contact.getId()));
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
        localBean.addContact(contact, false);
        assertFalse("Changed status should be false after adding", contact.isChanged());
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        this.assertTransactionCommited(transaction);

        Contact addedContact = localBean.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", addedContact.isChanged());
        this.assertContactEquals(contact, addedContact);

        //Associate the contact with enitity id 1
        contact.setContactType(ContactType.USER);
        localBean.associate(contact, 1, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //change city
        contact.setFirstName("new name");
        localBean.updateContact(contact, false);
        assertFalse("Changed status should be false after updating", contact.isChanged());
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        Contact updatedContact = localBean.retrieveContact(contact.getId());
        assertFalse("Changed status should be false after retrieving", updatedContact.isChanged());
        this.assertContactEquals(contact, updatedContact);

        localBean.removeContact(contact.getId(), false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);
        assertNull(localBean.retrieveContact(contact.getId()));
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
        localBean.addContact(contact, true);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the contact with entity 1
        localBean.associate(contact, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the same contact with same entity 1 again, method is simply returned, no audit performed
        localBean.associate(contact, 1, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same contact with another entity 2, the previous association should be deleted
        localBean.associate(contact, 2, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, one for deletion of the previous association, one for insertion of
        //new association
        preCount = this.assertAuditRecordsCount(preCount, 2);

        localBean.deassociate(contact, 3, true);
        //association does not exist, method is simply returned, no audit performed
        this.assertAuditRecordsCount(preCount, 0);

        //Deassociate contact with entity 2
        localBean.deassociate(contact, 2, true);
        //Audit should performed
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Remove the contact
        localBean.removeContact(contact.getId(), true);
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
        localBean.addContact(contact, false);
        this.assertTransactionCommited(transaction);

        preCount = this.getAuditRecordsCount();

        //Associate the contact with entity 1
        localBean.associate(contact, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same contact with same entity 1 again, method is simply returned, no audit performed
        localBean.associate(contact, 1, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Associate the same contact with another entity 2, the previous association should be deleted
        localBean.associate(contact, 2, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        localBean.deassociate(contact, 3, false);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Deassociate contact with entity 2
        localBean.deassociate(contact, 2, false);
        //Audit should not be performed
        this.assertAuditRecordsCount(preCount, 0);

        //Remove the contact
        localBean.removeContact(contact.getId(), false);
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
        localBean.addContacts(contactsToAdd, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        long ids[] = new long[10];

        //Get all added contacts
        Contact[] addedContacts = localBean.getAllContacts();
        assertTrue("There should be 10 contact", addedContacts.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contactsToAdd[i], addedContacts[i]);
            assertFalse("Changed status should be false after adding", contactsToAdd[i].isChanged());
            ids[i] = contactsToAdd[i].getId();
            addedContacts[i].setFirstName("new name" + i);
            addedContacts[i].setContactType(ContactType.CLIENT);
        }

        //update the 10 contacts
        localBean.updateContacts(addedContacts, true);
        this.assertTransactionCommited(transaction);
        //Audit should be performed, there should be 10 audit records
        preCount = this.assertAuditRecordsCount(preCount, 10);

        //Get all updated contacts
        Contact[] updatedContacts = localBean.getAllContacts();
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(updatedContacts[i], addedContacts[i]);
            assertFalse("Changed status should be false after updating", addedContacts[i].isChanged());
        }

        //Remove the 10 contacts
        localBean.removeContacts(ids, true);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed, as no association
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no contact after removal
        assertTrue("There should be 0 contact", localBean.getAllContacts().length == 0);
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
        localBean.addContacts(contactsToAdd, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        long ids[] = new long[10];

        //Get all added contacts
        Contact[] addedContacts = localBean.getAllContacts();
        assertTrue("There should be 10 contact", addedContacts.length == 10);

        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contactsToAdd[i], addedContacts[i]);
            assertFalse("Changed status should be false after adding", contactsToAdd[i].isChanged());
            ids[i] = contactsToAdd[i].getId();
            addedContacts[i].setFirstName("new name" + i);
        }

        //update the 10 contacts
        localBean.updateContacts(addedContacts, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //Get all updated contacts
        Contact[] updatedContacts = localBean.getAllContacts();
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(updatedContacts[i], addedContacts[i]);
            assertFalse("Changed status should be false after updating", addedContacts[i].isChanged());
        }

        //Remove the 10 contacts
        localBean.removeContacts(ids, false);
        this.assertTransactionCommited(transaction);
        //Audit should not be performed
        preCount = this.assertAuditRecordsCount(preCount, 0);

        //There should be no contact after removal
        assertTrue("There should be 0 contact", localBean.getAllContacts().length == 0);
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
        localBean.addContacts(duplicateContacts, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        long ids[] = new long[10];
        for (int i = 0; i < 10; i++) {
            ids[i] = duplicateContacts[i].getId();
        }

        //Retrieve with duplicate ids
        Contact[] addedContacts = localBean.retrieveContacts(ids);
        assertEquals("There should be only one record inserted into database", 1, addedContacts.length);
        this.assertContactEquals(contact, addedContacts[0]);

        contact.setFirstName("new name");

        //Update with duplication objects
        localBean.updateContacts(duplicateContacts, true);
        this.assertTransactionCommited(transaction);
        //There should be only one audit record
        preCount = this.assertAuditRecordsCount(preCount, 1);

        //Associate the contact, so audit will be performed when removing
        localBean.associate(contact, 1, false);

        localBean.removeContacts(ids, true);
        this.assertTransactionCommited(transaction);
        //There should be only 2 audit record, including the record for deletion of association
        this.assertAuditRecordsCount(preCount, 2);

        //There should be no records after removal
        assertEquals(0, localBean.retrieveContacts(ids).length);
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
        localBean.addContacts(contacts, false);
        Thread.sleep(1000);
        Date to = new Date();

        Contact[] results = localBean.searchContacts(ContactFilterFactory.createCreatedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localBean.searchContacts(ContactFilterFactory.createModifiedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        //upperThreshold is null
        results = localBean.searchContacts(ContactFilterFactory.createCreatedInFilter(from, null));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        //lowerThreshold is null
        results = localBean.searchContacts(ContactFilterFactory.createModifiedInFilter(null, to));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localBean.removeContacts(ids, false);
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

        localBean.addContacts(contacts, false);

        Contact[] results = localBean.searchContacts(ContactFilterFactory.createCreatedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localBean.searchContacts(ContactFilterFactory.createModifiedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localBean.removeContacts(ids, false);
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

        localBean.addContacts(contacts, false);

        Contact[] results = localBean.searchContacts(ContactFilterFactory.createEmailAddressFilter("email"));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localBean.searchContacts(ContactFilterFactory.createPhoneNumberFilter("111111"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localBean.searchContacts(ContactFilterFactory.createNameKeywordFilter("name"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localBean.removeContacts(ids, false);

        localBean.removeContacts(ids, false);
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

        localBean.addContacts(contacts, false);

        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            localBean.associate(contacts[i], 1, false);
        }

        Contact[] results = localBean.searchContacts(ContactFilterFactory.createTypeFilter(ContactType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = localBean.searchContacts(ContactFilterFactory.createEntityIDFilter(1, ContactType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        localBean.removeContacts(ids, false);
    }
}
