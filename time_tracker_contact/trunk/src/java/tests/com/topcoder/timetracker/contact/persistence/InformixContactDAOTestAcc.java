/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import java.util.Date;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactDAO;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>This test case contains accuracy tests for <code>InformixContactDAO</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixContactDAOTestAcc extends BaseTestCase {
    /**
     * <p>
     * <code>InformixContactDAO</code> used in the test case.
     * </p>
     */
    private ContactDAO contactDao = null;

    /**
     * <p>
     * <code>Contact</code> used in the test case.
     * </p>
     */
    private Contact contact = null;

    /**
     * <p>
     * <code>AuditManager</code> used in the test case.
     * </p>
     */
    private AuditManager auditManager = null;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        contactDao = new InformixContactDAO();
        contact = this.getContact();
        auditManager = (AuditManager) this.getField(contactDao, "auditManager");
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        auditManager = null;
        contactDao = null;
        contact = null;
        super.tearDown();
    }
    /**
     * <p>
     * Assert the connection name and IDGenerator name of given <code>InformixContactDAO</code>.
     * </p>
     * @param dao <code>InformixContactDAO</code> to assert
     * @throws Exception to JUnit
     */
    private void assertInformixContactDAO(ContactDAO dao) throws Exception {
        assertNotNull(dao);
        assertTrue(dao instanceof InformixContactDAO);
        assertEquals("InformixJNDIConnection", this.getField(dao, "connectionName"));
        IDGenerator idGenerator = (IDGenerator) this.getField(dao, "contactIDGenerator");
        assertEquals("ContactIDGenerator", idGenerator.getIDName());
    }

    /**
    * <p>
    * Test constructor <code>InformixContactDAO()</code>.
    * </p>
    *
    * @throws Exception to JUnit
    */
    public void testCtor1() throws Exception {
        assertInformixContactDAO(contactDao);
    }

    /**
     * <p>
     * Test constructor <code>InformixContactDAO()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        contactDao = new InformixContactDAO(InformixContactDAO.class.getName());
        assertInformixContactDAO(contactDao);
    }

    /**
     * <p>
     * Test constructor <code>InformixContactDAO()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        String namespace = InformixContactDAO.class.getName();
        contactDao = new InformixContactDAO(namespace, DAOHelper.getAuditManager(namespace));
        assertInformixContactDAO(contactDao);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>associate()</code>, <code>retrieveContact()</code>
     * and <code>removeContact()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationWithAudit() throws Exception {
        //Add an contact
        contactDao.addContact(contact, true);
        Contact addedContact = contactDao.retrieveContact(contact.getId());

        //Since no association, the contact type should be null
        assertNull(addedContact.getContactType());
        this.assertContactEquals(contact, addedContact);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 9, -1, -1, -1, -1);

        //Associate the contact with entity id 1
        contactDao.associate(contact, 1, false);
        addedContact = contactDao.retrieveContact(contact.getId());
        //The contact type should not be null after association
        assertEquals(contact.getContactType(), addedContact.getContactType());
        this.assertContactEquals(contact, addedContact);

        //Remove the contact
        contactDao.removeContact(contact.getId(), true);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 9, 1, -1, -1, -1);

        //The deletion of contact association should also be audited
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 1, -1, -1, -1);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>retrieveContact()</code>
     * and <code>removeContact()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testOperationWithoutAudit() throws Exception {
        contactDao.addContact(contact, false);
        this.assertContactEquals(contact, contactDao.retrieveContact(contact.getId()));
        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        contactDao.removeContact(contact.getId(), true);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContacts()</code>, <code>associate()</code>, <code>retrieveContacts()</code>
     * and <code>removeContacts()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationsWithAudit() throws Exception {
        Contact[] contacts = new Contact[2];
        contacts[0] = this.getContact();
        contacts[1] = this.getContact();

        //Add the contacts
        contactDao.addContacts(contacts, true);
        long ids[] = new long[]{contacts[0].getId(), contacts[1].getId()};
        Contact[] addedContacts = contactDao.retrieveContacts(ids);
        assertEquals(2, addedContacts.length);

        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 9, -1, -1, -1, -1);

        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 9, -1, -1, -1, -1);

        //Associate the contacts with entity id
        contactDao.associate(contacts[0], 1, false);
        contactDao.associate(contacts[1], 2, false);

        //Remove the contacts
        contactDao.removeContacts(ids, true);

        //Assert Audit record for contacts[0]
        headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 9, 1, -1, -1, -1);
        //The deletion of contact association should also be audited
        headers = this.getAuditRecord(auditManager, "contact_relation", contacts[0].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 1, -1, -1, -1);

        //Assert Audit record for contacts[1]
        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 9, 2, -1, -1, -1);
        //The deletion of contact association should also be audited
        headers = this.getAuditRecord(auditManager, "contact_relation", contacts[1].getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 2, -1, -1, -1);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContacts()</code> and <code>retrieveContacts()</code>
     * and <code>removeContacts()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBatchOperationsWithoutAudit() throws Exception {
        Contact[] contacts = new Contact[2];
        contacts[0] = this.getContact();
        contacts[1] = this.getContact();

        contactDao.addContacts(contacts, false);
        long ids[] = new long[]{contacts[0].getId(), contacts[1].getId()};
        Contact[] addedContacts = contactDao.retrieveContacts(ids);
        assertEquals(2, addedContacts.length);

        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        contactDao.removeContacts(ids, false);
        headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>updateContact()</code> and
     * <code>removeContact()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContactWithAudit() throws Exception {

        //Add an contact
        contactDao.addContact(contact, true);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 9, -1, -1, -1, -1);

        //Update the contact, not changed, there should be no audit records
        contactDao.updateContact(contact, true);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        contact.setFirstName("new first name");
        contact.setLastName("new last name");
        Thread.sleep(1500);
        //Update the contact again
        contactDao.updateContact(contact, true);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //3 details, first name is changed, last name is changed, modification date is changed
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.UPDATE, 3, -1, -1, -1, -1);

        Contact updatedContact = contactDao.retrieveContact(contact.getId());
        this.assertContactEquals(updatedContact, contact);

        //No association, no audit when removal
        contactDao.removeContact(contact.getId(), true);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>updateContact()</code> and
     * <code>removeContact()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContactWithoutAudit() throws Exception {

        //Add an contact
        contactDao.addContact(contact, false);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Update the contact, not changed
        contactDao.updateContact(contact, false);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        contact.setFirstName("new first name");
        contact.setLastName("new last name");
        //Update the contact again, first name is changed, last name is changed, modification date is changed
        contactDao.updateContact(contact, false);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 0, headers.length);

        Contact updatedContact = contactDao.retrieveContact(contact.getId());
        this.assertContactEquals(updatedContact, contact);

        //Remove the contact
        contactDao.removeContact(contact.getId(), false);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContacts()</code>, <code>updateContacts()</code> and
     * <code>removeContacts()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContactsWithAudit() throws Exception {
        Contact[] contacts = new Contact[2];
        contacts[0] = this.getContact();
        contacts[1] = this.getContact();

        //Add contacts
        contactDao.addContacts(contacts, true);

        //Update the contacts, not changed, there should be no audit records
        contactDao.updateContacts(contacts, true);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        contacts[0].setFirstName("new first name");
        contacts[1].setLastName("new last name");
        Thread.sleep(1500);
        //Update the contacts again
        contactDao.updateContacts(contacts, true);
        headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //2 details, city is changed, modification date is changed
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.UPDATE, 2, -1, -1, -1, -1);

        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //2 details, postal code is changed, modification date is changed
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.UPDATE, 2, -1, -1, -1, -1);

        Contact[] updatedContacts =
            contactDao.retrieveContacts(new long[]{contacts[0].getId(), contacts[1].getId()});
        this.assertContactEquals(updatedContacts[0], contacts[0]);
        this.assertContactEquals(updatedContacts[1], contacts[1]);

        //No association, no audit when removal
        contactDao.removeContacts(new long[]{contacts[0].getId(), contacts[1].getId()}, true);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContacts()</code>, <code>updateContacts()</code> and
     * <code>removeContacts()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContactsWithoutAudit() throws Exception {
        Contact[] contacts = new Contact[2];
        contacts[0] = this.getContact();
        contacts[1] = this.getContact();

        //Add an contacts
        contactDao.addContacts(contacts, false);

        //Update the contacts, not changed, there should be no audit records
        contactDao.updateContacts(contacts, false);
        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);
        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        contacts[0].setFirstName("new first name");
        contacts[1].setLastName("new last name");
        //Update the contacts again
        contactDao.updateContacts(contacts, false);
        headers = this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 0 header record", 0, headers.length);

        Contact[] updatedContacts =
            contactDao.retrieveContacts(new long[]{contacts[0].getId(), contacts[1].getId()});
        this.assertContactEquals(updatedContacts[0], contacts[0]);
        this.assertContactEquals(updatedContacts[1], contacts[1]);

        //No association, no audit when removal
        contactDao.removeContacts(new long[]{contacts[0].getId(), contacts[1].getId()}, false);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>associate()</code>, <code>deassociate()</code>
     * and <code>removeContact()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociationWithAudit() throws Exception {
        //Add an contact
        contactDao.addContact(contact, true);

        //Associate contact with entity id 2
        contactDao.associate(contact, 2, true);
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 7, 2, -1, -1, -1);

        //Associate contact with entity id 2 again, nothing happen
        contactDao.associate(contact, 2, true);
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.INSERT);
        //There are still one record for insertion
        assertEquals("There should be 1 header record", 1, headers.length);

        //Associate contact with entity id 3, previous association will be deleted first
        contactDao.associate(contact, 3, true);
        //Audti record for deletion of previous association
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 2, -1, -1, -1);
        //Now there are 2 records for insertion
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.INSERT);
        assertEquals("There should be 2 header records", 2, headers.length);
        //7 details
        this.assertAuditHeader(headers[1], ApplicationArea.TT_PROJECT, AuditType.INSERT, 7, 3, -1, -1, -1);

        //Deassociate contact with entity id 4, nothing happen
        contactDao.deassociate(contact, 4, true);
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        //There are still 1 record for deletion
        assertEquals("There should be 1 header record", 1, headers.length);

        //Deassociate contact with entity id 3
        contactDao.deassociate(contact, 3, true);
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        //Now there are 2 records for deletion
        assertEquals("There should be 2 header record", 2, headers.length);
        //7 details
        this.assertAuditHeader(headers[1], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 3, -1, -1, -1);

        contactDao.removeContact(contact.getId(), true);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        //No audit as no association
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test accuracy of method <code>addContact()</code>, <code>associate()</code>, <code>deassociate()</code>
     * and <code>removeContact()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAssociationWithoutAudit() throws Exception {
        //Add an contact
        contactDao.addContact(contact, false);

        //Associate contact with entity id 2
        contactDao.associate(contact, 2, false);
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Associate contact with entity id 2 again, nothing happen
        contactDao.associate(contact, 2, false);
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.INSERT);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Associate contact with entity id 3, previous association will be deleted first
        contactDao.associate(contact, 3, false);
        //Audti record for deletion of previous association
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Deassociate contact with entity id 4, nothing happen
        contactDao.deassociate(contact, 4, false);
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);

        //Deassociate contact with entity id 3
        contactDao.deassociate(contact, 3, false);
        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);

        contactDao.removeContact(contact.getId(), false);
        headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 0 header record", 0, headers.length);
    }

    /**
     * <p>
     * Test method <code>addContacts()</code>. Given array contains duplication(objects with same reference),
     * only one record should be inserted into database for the duplication objects.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddDuplicateContacts() throws Exception {
        Contact[] contacts = new Contact[4];
        contacts[0] = this.getContact();
        contacts[1] = contact; //contacts[0] and contacts[1] have different object references, both will be inserted
        contacts[2] = contact; //duplicate object reference
        contacts[3] = contact; //duplicate object reference

        int preCount = this.getAuditRecordsCount();
        contactDao.addContacts(contacts, true);
        this.assertAuditRecordsCount(preCount, 2);

        Contact[] addedContacts = contactDao.getAllContacts();
        assertEquals("There should be 2 contacts added", 2, addedContacts.length);
        this.assertContactEquals(contacts[0], addedContacts[0]);
        this.assertContactEquals(contacts[1], addedContacts[1]);

        //Audit record for contacts[0]
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 9, -1, -1, -1, -1);

        //Audit record for contacts[1]
        headers = this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.INSERT);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.INSERT, 9, -1, -1, -1, -1);

        contactDao.removeContacts(new long[]{contacts[0].getId(), contacts[1].getId()}, false);
    }

    /**
     * <p>
     * Test method <code>updateContacts()</code>. Given array contains duplication(objects with same reference),
     * it will be updated only once and only one audit record created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateDuplicateContacts() throws Exception {
        Contact[] contacts = new Contact[2];
        contacts[0] = this.getContact();
        contacts[1] = this.getContact();

        //Add the 2 contacts
        contactDao.addContacts(contacts, false);

        Contact[] contactsToBeUpdated = new Contact[4];
        contacts[0].setFirstName("new first name0"); //contacts[0] will be updated
        contactsToBeUpdated[0] = contacts[0];
        contacts[1].setFirstName("new first name1"); //contacts[1] will be updated
        contactsToBeUpdated[1] = contacts[1];

        contactsToBeUpdated[2] = contacts[0]; //duplicate object reference, contacts[0] will only be updated once

        contact.setId(contacts[1].getId()); //contacts[1] will be updated twice
        contact.setCreationDate(contacts[1].getCreationDate());
        contact.setFirstName("new first name2");
        contactsToBeUpdated[3] = contact;

        int preCount = this.getAuditRecordsCount();
        contactDao.updateContacts(contactsToBeUpdated, true);
        this.assertAuditRecordsCount(preCount, 3);

        //1 Audit record for contacts[0]
        AuditHeader[] headers =
            this.getAuditRecord(auditManager, "contact", contacts[0].getId(), AuditType.UPDATE);
        assertEquals("There should be 1 header record", 1, headers.length);

        //2 Audit records for contacts[1]
        headers =
            this.getAuditRecord(auditManager, "contact", contacts[1].getId(), AuditType.UPDATE);
        assertEquals("There should be 2 header record", 2, headers.length);

        Contact[] updatedContacts = contactDao.getAllContacts();
        this.assertContactEquals(contacts[0], updatedContacts[0]);
        this.assertContactEquals(contact, updatedContacts[1]);

        contactDao.removeContacts(new long[]{contacts[0].getId(), contacts[1].getId()}, false);
    }

    /**
     * <p>
     * Test <code>removeContacts()</code>. Remove the same id twice, the audit should be performed only once.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveDuplicateContacts() throws Exception {
        //Add an contact
        contactDao.addContact(contact, false);

        //Associate the contact, so audit will be performed when deleting
        contactDao.associate(contact, 3, false);

        int preCount = this.getAuditRecordsCount();
        contactDao.removeContacts(new long[]{contact.getId(), contact.getId()}, true);
        //One audit record for deletion of contact, one audit record for deletion of association
        this.assertAuditRecordsCount(preCount, 2);

        AuditHeader[] headers = this.getAuditRecord(auditManager, "contact", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //9 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 9, 3, -1, -1, -1);

        headers = this.getAuditRecord(auditManager, "contact_relation", contact.getId(), AuditType.DELETE);
        assertEquals("There should be 1 header record", 1, headers.length);
        //7 details
        this.assertAuditHeader(headers[0], ApplicationArea.TT_PROJECT, AuditType.DELETE, 7, 3, -1, -1, -1);
    }

    /**
     * <p>
     * Test <code>retrieveContacts()</code>. Given ids contains duplicate, returned array should contain
     * only one contact for the duplicate ids.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveDuplicateContacts() throws Exception {
        //Add an contact
        contactDao.addContact(contact, false);

        Contact[] retrievedContacts = contactDao.retrieveContacts(new long[]{contact.getId(), contact.getId()});

        assertEquals("Only 1 contact returned", 1, retrievedContacts.length);
        this.assertContactEquals(contact, retrievedContacts[0]);

        contactDao.removeContact(contact.getId(), false);
    }

    /**
     * <p>
     * Test <code>addeContacts()</code>, <code>updateContacts()</code>, <code>removeContacts()</code>
     * with empty passed in array. Nothing happen.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEmptyArray() throws Exception {
        int preCount = this.getAuditRecordsCount();
        contactDao.addContacts(new Contact[]{}, true);
        contactDao.updateContacts(new Contact[]{}, true);
        contactDao.removeContacts(new long[]{}, true);
        this.assertAuditRecordsCount(preCount, 0);
    }

    /**
     * <p>
     * Test <code>retrieveContact()</code>, <code>retrieveContacts()</code>,<code>getAllContact()</code>.
     * There is no contact in database.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieve() throws Exception {
        Contact[] contacts = contactDao.retrieveContacts(new long[]{1, 2});
        assertEquals("No contact", 0, contacts.length);
        contacts = contactDao.getAllContacts();
        assertEquals("No contact", 0, contacts.length);
        assertNull("No contact", contactDao.retrieveContact(1));
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
        contactDao.addContacts(contacts, false);
        Thread.sleep(1000);
        Date to = new Date();

        Contact[] results = contactDao.searchContacts(ContactFilterFactory.createCreatedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = contactDao.searchContacts(ContactFilterFactory.createModifiedInFilter(from, to));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        //upperThreshold is null
        results = contactDao.searchContacts(ContactFilterFactory.createCreatedInFilter(from, null));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        //lowerThreshold is null
        results = contactDao.searchContacts(ContactFilterFactory.createModifiedInFilter(null, to));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        contactDao.removeContacts(ids, false);
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

        contactDao.addContacts(contacts, false);

        Contact[] results = contactDao.searchContacts(ContactFilterFactory.createCreatedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = contactDao.searchContacts(ContactFilterFactory.createModifiedByFilter("user"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        contactDao.removeContacts(ids, false);
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

        contactDao.addContacts(contacts, false);

        Contact[] results = contactDao.searchContacts(ContactFilterFactory.createEmailAddressFilter("email"));
        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = contactDao.searchContacts(ContactFilterFactory.createPhoneNumberFilter("111111"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = contactDao.searchContacts(ContactFilterFactory.createNameKeywordFilter("name"));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        contactDao.removeContacts(ids, false);
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

        contactDao.addContacts(contacts, false);

        for (int i = 0; i < 10; i++) {
            ids[i] = contacts[i].getId();
            contactDao.associate(contacts[i], 1, false);
        }

        Contact[] results = contactDao.searchContacts(ContactFilterFactory.createTypeFilter(ContactType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        results = contactDao.searchContacts(ContactFilterFactory.createEntityIDFilter(1, ContactType.PROJECT));
        for (int i = 0; i < 10; i++) {
            this.assertContactEquals(contacts[i], results[i]);
        }

        contactDao.removeContacts(ids, false);
    }
}
