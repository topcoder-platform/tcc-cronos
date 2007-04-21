/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import java.util.Date;

import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.MockTransaction;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>This test case contains failure tests for <code>ContactManagerLocalDelegate</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactManagerLocalDelegateTestExp extends BaseTestCase {
    /**
     * <p>
     * Indicates whether the audit related tables have been cleared.
     * </p>
     */
    private static boolean auditCleared = false;
    /**
     * <p>
     * <code>MockTransaction</code> used in the test case.
     * </p>
     */
    private MockTransaction transaction = null;

    /**
     * <p>
     * <code>ContactManagerLocalDelegate</code> used in the test case.
     * </p>
     */
    private ContactManager localDelegate = null;

    /**
     * <p>
     * The records count of <em>audit</em> table.
     * </p>
     */
    private int preAuditCount = 0;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        if (!auditCleared) {
            this.executeUpdate("delete from audit_detail");
            this.executeUpdate("delete from audit");
            auditCleared = true;
        }
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
        localDelegate = new ContactManagerLocalDelegate();
        transaction = this.getTransaction();
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
        this.unBind("java:comp/env/ContactDAOKey");
        localDelegate = null;
        transaction = null;
        super.tearDown();
    }
    /**
     * <p>
     * Get <code>ContactManager</code> which will refer to a wrong datasource.
     * </p>
     *
     * @return <code>ContactManager</code>.
     *
     * @throws Exception to JUnit.
     */
    private ContactManager getWrongContactDelegate() throws Exception {
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_6");
        this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
        return new ContactManagerLocalDelegate();
    }
    /**
     * <p>
     * Given namespace is null, IAE expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        try {
            new ContactManagerLocalDelegate(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Given namespace is empty, IAE expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new ContactManagerLocalDelegate(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Given namespace is unknown, ConfigurationException expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        try {
            new ContactManagerLocalDelegate("unknown");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Required property value is missing, ConfigurationException expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor4() throws Exception {
        try {
            new ContactManagerLocalDelegate("ContactManagerLocalDelegate_Error0");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * JNDI reference is wrong, ConfigurationException expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor5() throws Exception {
        try {
            new ContactManagerLocalDelegate("ContactManagerLocalDelegate_Error1");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * JNDI name is referencing to null, ConfigurationException expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor6() throws Exception {
        try {
            this.bind("JNDI", null);
            new ContactManagerLocalDelegate("ContactManagerLocalDelegate_Error1");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * JNDI name is referencing to wrong class type, ConfigurationException expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor7() throws Exception {
        try {
            this.bind("JNDI", new String("JNDI"));
            new ContactManagerLocalDelegate("ContactManagerLocalDelegate_Error1");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Error occurs while creating EJB, ConfigurationException expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor8() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "UnknownObjectFactoryNS");
            new ContactManagerLocalDelegate();
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Given contact is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IAE() throws Exception {
        try {
            this.localDelegate.addContact(null, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IPE1() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            this.localDelegate.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IPE2() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.localDelegate.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Id got from <code>IDGenerator</code> is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IDGenerationException() throws Exception {
        long id = IDGeneratorFactory.getIDGenerator("ContactIDGenerator").getNextID();
        try {
            this.updateIDGenerator("ContactIDGenerator", -1);
            this.localDelegate.addContact(this.getContact(), false);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.updateIDGenerator("ContactIDGenerator", id + 1);
        }
    }
    /**
     * <p>
     * Test method <code>addContact()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_PersistenceException()
        throws Exception {
        try {
            Contact contact = this.getContact();
            this.getWrongContactDelegate().addContact(contact, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_AuditException()
        throws Exception {
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            Contact contact = this.getContact();
            this.localDelegate.addContact(contact, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>addContacts()</code>.
     * Given contact is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts_IAE() throws Exception {
        try {
            this.localDelegate.addContacts(new Contact[]{null}, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>addContacts()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts_IPE() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            this.localDelegate.addContacts(new Contact[]{contact}, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Id got from <code>IDGenerator</code> is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts_IDGenerationException() throws Exception {
        int preContactCount = this.getContactRecordsCount();
        long id = IDGeneratorFactory.getIDGenerator("ContactIDGenerator").getNextID();
        try {
            this.updateIDGenerator("ContactIDGenerator", Long.MAX_VALUE - 1);
            this.localDelegate.addContacts(new Contact[]{this.getContact(), this.getContact(), this.getContact()},
                true);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.assertContactRecordsCount(preContactCount, 0);
            this.updateIDGenerator("ContactIDGenerator", id + 1);
        }
    }
    /**
     * <p>
     * Test method <code>addContacts()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts_PersistenceException()
        throws Exception {
        int preContactCount = this.getContactRecordsCount();
        try {
            Contact contact = this.getContact();
            this.getWrongContactDelegate().addContacts(new Contact[]{this.getContact(), this.getContact(), contact},
                true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.assertContactRecordsCount(preContactCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>addContacts()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts_AuditException()
        throws Exception {
        int preContactCount = this.getContactRecordsCount();
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            Contact contact1 = this.getContact();
            contact1.setContactType(ContactType.CLIENT);
            Contact contact2 = this.getContact();
            contact2.setContactType(ContactType.COMPANY);
            Contact contact3 = this.getContact();
            contact3.setContactType(ContactType.USER);
            Contact contact4 = this.getContact();
            contact4.setContactType(ContactType.PROJECT);
            this.localDelegate.addContacts(new Contact[]{contact1, contact2, contact3, contact4}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.assertContactRecordsCount(preContactCount, 0);
        }
    }

    /**
     * <p>
     * Test method <code>retrieveContact()</code>.
     * Id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContact_IAE() throws Exception {
        try {
            this.localDelegate.retrieveContact(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveContact()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContact_PersistenceException() throws Exception {
        try {
            this.getWrongContactDelegate().retrieveContact(1);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveContact()</code>.
     * Contact is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContact_AssociationException() throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.localDelegate.retrieveContact(contact.getId());
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>retrieveContacts()</code>.
     * Id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContacts_IAE() throws Exception {
        try {
            this.localDelegate.retrieveContacts(new long[]{-1});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveContacts()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContacts_PersistenceException() throws Exception {
        try {
            this.getWrongContactDelegate().retrieveContacts(new long[]{1});
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>retrieveContacts()</code>.
     * Contact is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContacts_AssociationException() throws Exception {
        Contact contact1 = this.getContact();
        this.localDelegate.addContact(contact1, false);
        this.localDelegate.associate(contact1, 1, false);
        Contact contact2 = this.getContact();
        this.localDelegate.addContact(contact2, false);
        this.localDelegate.associate(contact2, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact2.getId() + ",1,2) ");
        try {
            this.localDelegate.retrieveContacts(new long[]{contact1.getId(), contact2.getId()});
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Given contact is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContact_IAE() throws Exception {
        try {
            this.localDelegate.updateContact(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE1() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            this.localDelegate.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE2() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.localDelegate.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Creation date is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE3() throws Exception {
        try {
            Contact contact = this.getContact();
            this.localDelegate.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Id is negative, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE4() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            this.localDelegate.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContact_AuditException()
        throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact.setFirstName("new name");
            this.localDelegate.updateContact(contact, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.localDelegate.removeContact(contact.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContact_PersistenceException() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setId(1);
            this.getWrongContactDelegate().updateContact(contact, true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Contact is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContact_AssociationException() throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            contact.setFirstName("new name");
            this.localDelegate.updateContact(contact, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * No exsiting record in database, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContact_EntityNotFoundException() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setId(1);
            this.localDelegate.updateContact(contact, true);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>updateContacts()</code>.
     * Given contact is null, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContacts_IAE() throws Exception {
        try {
            this.localDelegate.updateContacts(new Contact[]{null}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateContacts()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContacts_IPE() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.localDelegate.updateContacts(new Contact[]{contact}, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateContacts()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContacts_AuditException()
        throws Exception {
        Contact contact1 = this.getContact();
        contact1.setContactType(ContactType.CLIENT);
        Contact contact2 = this.getContact();
        contact2.setContactType(ContactType.COMPANY);
        Contact contact3 = this.getContact();
        contact3.setContactType(ContactType.USER);
        Contact contact4 = this.getContact();
        contact4.setContactType(ContactType.PROJECT);
        this.localDelegate.addContact(contact1, false);
        this.localDelegate.addContact(contact2, false);
        this.localDelegate.addContact(contact3, false);
        this.localDelegate.addContact(contact4, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact1.setFirstName("new name");
            contact2.setFirstName("new name");
            contact3.setFirstName("new name");
            contact4.setFirstName("new name");
            this.localDelegate.updateContacts(new Contact[]{contact1, contact2, contact3, contact4}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>updateContacts()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContacts_PersistenceException() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setId(1);
            this.getWrongContactDelegate().updateContacts(new Contact[]{contact}, true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>updateContacts()</code>.
     * Contact is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContacts_AssociationException() throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            contact.setFirstName("new name");
            this.localDelegate.updateContacts(new Contact[]{contact}, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>updateContacts()</code>.
     * No exsiting record in database, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testupdateContacts_EntityNotFoundException() throws Exception {
        Contact contact1 = this.getContact();
        this.localDelegate.addContact(contact1, false);
        contact1.setFirstName("new name");
        Contact contact2 = this.getContact();
        this.localDelegate.addContact(contact2, false);
        contact2.setFirstName("new name");
        Contact contact3 = this.getContact();
        contact3.setCreationDate(new Date());
        contact3.setId(contact2.getId() + 2); // does not exist
        try {
            this.localDelegate.updateContacts(new Contact[]{contact1, contact2, contact3}, true);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
        }
    }
    /**
     * <p>
     * Test method <code>removeContact()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContact_IAE() throws Exception {
        try {
            this.localDelegate.removeContact(-1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeContact()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContact_AuditException()
        throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.localDelegate.removeContact(contact.getId(), true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.localDelegate.removeContact(contact.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>removeContact()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContact_PersistenceException() throws Exception {
        try {
            this.getWrongContactDelegate().removeContact(1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeContact()</code>.
     * Contact is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContact_AssociationException() throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.localDelegate.removeContact(contact.getId(), true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>removeContacts()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContacts_IAE() throws Exception {
        try {
            this.localDelegate.removeContacts(new long[]{-1}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeContacts()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContacts_AuditException()
        throws Exception {
        Contact contact1 = this.getContact();
        contact1.setContactType(ContactType.CLIENT);
        Contact contact2 = this.getContact();
        contact2.setContactType(ContactType.COMPANY);
        Contact contact3 = this.getContact();
        contact3.setContactType(ContactType.USER);
        Contact contact4 = this.getContact();
        contact4.setContactType(ContactType.PROJECT);

        this.localDelegate.addContact(contact1, false);
        this.localDelegate.addContact(contact2, false);
        this.localDelegate.addContact(contact3, false);
        this.localDelegate.addContact(contact4, false);
        this.localDelegate.associate(contact1, 1, false);
        this.localDelegate.associate(contact2, 1, false);
        this.localDelegate.associate(contact3, 1, false);
        this.localDelegate.associate(contact4, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.localDelegate.removeContacts(new long[]{contact1.getId(), contact2.getId(), contact3.getId(),
                contact4.getId()}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>removeContacts()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContacts_PersistenceException() throws Exception {
        try {
            this.getWrongContactDelegate().removeContacts(new long[]{1}, true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>removeContacts()</code>.
     * Contact is associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveContacts_AssociationException() throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.localDelegate.removeContacts(new long[]{contact.getId()}, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>getAllContacts()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllContacts_PersistenceException() throws Exception {
        try {
            this.getWrongContactDelegate().getAllContacts();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>getAllContacts()</code>.
     * The contact is associated with mutiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllContacts_AssociationException() throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.localDelegate.getAllContacts();
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given contact is null, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IAE1() throws Exception {
        try {
            this.localDelegate.associate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given entity id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IAE2() throws Exception {
        try {
            this.localDelegate.associate(new Contact(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Id of contact is negative, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE1() throws Exception {
        try {
            Contact contact = new Contact();
            this.localDelegate.associate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Type of contact is null, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE2() throws Exception {
        try {
            Contact contact = new Contact();
            contact.setId(1);
            this.localDelegate.associate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Creation user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE3() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            this.localDelegate.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>associate()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE4() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.localDelegate.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Creation date is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE5() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setModificationDate(new Date());
            contact.setCreationDate(null);
            this.localDelegate.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>associate()</code>.
     * Modification date is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_IPE6() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setModificationDate(new Date());
            contact.setModificationDate(null);
            this.localDelegate.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_AuditException()
        throws Exception {
        Contact contact = this.getContact();
        contact.setContactType(ContactType.USER);
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact.setContactType(ContactType.PROJECT);
            this.localDelegate.associate(contact, 2, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.localDelegate.removeContact(contact.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given contact is not an existing record in database, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_AssociationException1()
        throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        try {
            contact.setId(contact.getId() + 1);
            this.localDelegate.associate(contact, 1, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.localDelegate.removeContact(contact.getId() - 1, false);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given contact is currently associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_AssociationException2()
        throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.localDelegate.associate(contact, 3, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testassociate_PersistenceException() throws Exception {
        Contact contact = this.getContact();
        contact.setId(1);
        contact.setCreationDate(new Date());
        contact.setModificationDate(new Date());
        try {
            this.getWrongContactDelegate().associate(contact, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Given contact is null, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IAE1() throws Exception {
        try {
            this.localDelegate.deassociate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Given entity id is negative, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IAE2() throws Exception {
        try {
            this.localDelegate.deassociate(new Contact(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Id of contact is negative, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IPE1() throws Exception {
        try {
            Contact contact = new Contact();
            this.localDelegate.deassociate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Type of contact is null, InvalidPropertyException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IPE2() throws Exception {
        try {
            Contact contact = new Contact();
            contact.setId(1);
            this.localDelegate.deassociate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Modification user is null, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_IPE3() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.localDelegate.deassociate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Given contact is not an existing record in database, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_AssociationException1()
        throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        try {
            contact.setId(contact.getId() + 1);
            this.localDelegate.deassociate(contact, 1, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.localDelegate.removeContact(contact.getId() - 1, false);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * Given contact is currently associated with multiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_AssociationException2()
        throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.localDelegate.deassociate(contact, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>associate()</code>.
     * Foreign key constraint violated on application area id, AuditException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_AuditException()
        throws Exception {
        Contact contact = this.getContact();
        this.localDelegate.addContact(contact, false);
        this.localDelegate.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.localDelegate.deassociate(contact, 1, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.localDelegate.removeContact(contact.getId(), false);
        }
    }
    /**
     * <p>
     * Test method <code>deassociate()</code>.
     * A wrong datasource which always throws SQLException when getting connection is binded,
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testdeassociate_PersistenceException() throws Exception {
        Contact contact = this.getContact();
        contact.setId(1);
        try {
            this.getWrongContactDelegate().deassociate(contact, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
}
