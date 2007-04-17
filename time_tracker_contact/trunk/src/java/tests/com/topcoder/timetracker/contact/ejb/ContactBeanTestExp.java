/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.ejb;

import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.InitialContext;

import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.MockTransaction;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>This test case contains failure tests for <code>ContactBean</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactBeanTestExp extends BaseTestCase {
    /**
     * <p>
     * Indicates whether the audit related tables have been cleared.
     * </p>
     */
    private static boolean auditCleared = false;
    /**
     * <p>
     * <code>ContactLocal</code> used in the test case.
     * </p>
     */
    private static ContactLocal localBean = null;
    /**
     * <p>
     * <code>MockTransaction</code> used in the test case.
     * </p>
     */
    private MockTransaction transaction = null;
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
            localBean = this.getContactLocal();
            this.executeUpdate("delete from audit_detail");
            this.executeUpdate("delete from audit");
            auditCleared = true;
        }
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
        transaction = null;
        super.tearDown();
    }

    /**
     * <p>
     * Get <code>ContactLocal</code> which will refer to a wrong datasource.
     * </p>
     *
     * @return <code>ContactLocal</code>.
     *
     * @throws Exception to JUnit.
     */
    private ContactLocal getWrongContactLocal() throws Exception {
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_6");
        this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
        ContactHomeLocal localHome = (ContactHomeLocal) new InitialContext().lookup("java:comp/env/ejb/ContactLocal");
        return localHome.create();
    }
    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Unknown namespace for Object Factory.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate1() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "UnknownObjectFactoryNS");
            new ContactBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Object Factory points to an unknown namspace for ContactDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate2() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_1");
            this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
            new ContactBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Object Factory points to an invalid class type.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate3() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_2");
            this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
            new ContactBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Wrong object binded to JNDI name.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate4() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", new Long(1));
            this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
            new ContactBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Null binded to JNDI name.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate5() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", null);
            this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
            new ContactBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
    /**
     * <p>
     * Failure test of method <code>ejbCreate()</code>.
     * Null binded to JNDI name.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate6() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
            this.bind("java:comp/env/ContactDAOKey", null);
            new ContactBean().ejbCreate();
            fail("CreateException expected");
        } catch (CreateException e) {
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
            localBean.addContact(null, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>addContact()</code>.
     * First name is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IPE1() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setFirstName(this.getStringWithLengthGreaterThan100());
            localBean.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Last name is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IPE2() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setLastName(this.getStringWithLengthGreaterThan100());
            localBean.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Phone is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IPE3() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setPhoneNumber(this.getStringWithLengthGreaterThan100());
            localBean.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Email is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IPE4() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setEmailAddress(this.getStringWithLengthGreaterThan100());
            localBean.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
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
    public void testAddContact_IPE5() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            localBean.addContact(contact, false);
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
    public void testAddContact_IPE6() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            localBean.addContact(contact, false);
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
            localBean.addContact(this.getContact(), false);
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
            this.getWrongContactLocal().addContact(contact, false);
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
            localBean.addContact(contact, true);
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
            localBean.addContacts(new Contact[]{null}, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>addContact()</code>.
     * FirstName is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts_IPE() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setFirstName(this.getStringWithLengthGreaterThan100());
            localBean.addContacts(new Contact[]{contact}, false);
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
        long id = IDGeneratorFactory.getIDGenerator("ContactIDGenerator").getNextID();
        try {
            this.updateIDGenerator("ContactIDGenerator", Long.MAX_VALUE - 1);
            localBean.addContacts(new Contact[]{this.getContact(), this.getContact(), this.getContact()},
                true);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            this.assertTransactionRollbacked(transaction);
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
        try {
            Contact contact = this.getContact();
            this.getWrongContactLocal().addContacts(new Contact[]{this.getContact(), this.getContact(), contact}, true);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
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
            localBean.addContacts(new Contact[]{contact1, contact2, contact3, contact4}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.assertTransactionRollbacked(transaction);
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
            localBean.retrieveContact(-1);
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
            this.getWrongContactLocal().retrieveContact(1);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            localBean.retrieveContact(contact.getId());
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
            localBean.retrieveContacts(new long[]{-1});
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
            this.getWrongContactLocal().retrieveContacts(new long[]{1});
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
        localBean.addContact(contact1, false);
        localBean.associate(contact1, 1, false);
        Contact contact2 = this.getContact();
        localBean.addContact(contact2, false);
        localBean.associate(contact2, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact2.getId() + ",1,2) ");
        try {
            localBean.retrieveContacts(new long[]{contact1.getId(), contact2.getId()});
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
            localBean.updateContact(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * First name is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE1() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setFirstName(this.getStringWithLengthGreaterThan100());
            localBean.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Last name is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE2() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setLastName(this.getStringWithLengthGreaterThan100());
            localBean.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Phone is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE3() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setPhoneNumber(this.getStringWithLengthGreaterThan100());
            localBean.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }

    /**
     * <p>
     * Test method <code>updateContact()</code>.
     * Email is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContact_IPE4() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setEmailAddress(this.getStringWithLengthGreaterThan100());
            localBean.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
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
    public void testUpdateContact_IPE5() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            localBean.updateContact(contact, false);
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
    public void testUpdateContact_IPE6() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            localBean.updateContact(contact, false);
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
    public void testUpdateContact_IPE7() throws Exception {
        try {
            Contact contact = this.getContact();
            localBean.updateContact(contact, false);
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
    public void testUpdateContact_IPE8() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            localBean.updateContact(contact, false);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact.setFirstName("new name");
            localBean.updateContact(contact, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeContact(contact.getId(), false);
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
            this.getWrongContactLocal().updateContact(contact, true);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            contact.setFirstName("new name");
            localBean.updateContact(contact, true);
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
            localBean.updateContact(contact, true);
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
            localBean.updateContacts(new Contact[]{null}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
    /**
     * <p>
     * Test method <code>updateContacts()</code>.
     * First name is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateContacts_IPE() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setFirstName(this.getStringWithLengthGreaterThan100());
            localBean.updateContacts(new Contact[]{contact}, false);
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
        localBean.addContact(contact1, false);
        localBean.addContact(contact2, false);
        localBean.addContact(contact3, false);
        localBean.addContact(contact4, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact1.setFirstName("new name");
            contact2.setFirstName("new name");
            contact3.setFirstName("new name");
            contact4.setFirstName("new name");
            localBean.updateContacts(new Contact[]{contact1, contact2, contact3, contact4}, true);
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
            this.getWrongContactLocal().updateContacts(new Contact[]{contact}, true);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            contact.setFirstName("new name");
            localBean.updateContacts(new Contact[]{contact}, true);
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
        localBean.addContact(contact1, false);
        contact1.setFirstName("new name");
        Contact contact2 = this.getContact();
        localBean.addContact(contact2, false);
        contact2.setFirstName("new name");
        Contact contact3 = this.getContact();
        contact3.setCreationDate(new Date());
        contact3.setId(contact2.getId() + 2); // does not exist
        try {
            localBean.updateContacts(new Contact[]{contact1, contact2, contact3}, true);
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
            localBean.removeContact(-1, false);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            localBean.removeContact(contact.getId(), true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeContact(contact.getId(), false);
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
            this.getWrongContactLocal().removeContact(1, false);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            localBean.removeContact(contact.getId(), true);
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
            localBean.removeContacts(new long[]{-1}, false);
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

        localBean.addContact(contact1, false);
        localBean.addContact(contact2, false);
        localBean.addContact(contact3, false);
        localBean.addContact(contact4, false);
        localBean.associate(contact1, 1, false);
        localBean.associate(contact2, 1, false);
        localBean.associate(contact3, 1, false);
        localBean.associate(contact4, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            localBean.removeContacts(new long[]{contact1.getId(), contact2.getId(), contact3.getId(),
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
            this.getWrongContactLocal().removeContacts(new long[]{1}, true);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            localBean.removeContacts(new long[]{contact.getId()}, true);
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
            this.getWrongContactLocal().getAllContacts();
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            localBean.getAllContacts();
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
            localBean.associate(null, 1, true);
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
            localBean.associate(new Contact(), -1, true);
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
            localBean.associate(contact, 1, true);
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
            localBean.associate(contact, 1, true);
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
            localBean.associate(contact, 1, false);
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
            localBean.associate(contact, 1, false);
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
            localBean.associate(contact, 1, false);
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
            localBean.associate(contact, 1, false);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact.setContactType(ContactType.PROJECT);
            localBean.associate(contact, 2, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeContact(contact.getId(), false);
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
        localBean.addContact(contact, false);
        try {
            contact.setId(contact.getId() + 1);
            localBean.associate(contact, 1, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            localBean.removeContact(contact.getId() - 1, false);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            localBean.associate(contact, 3, false);
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
            this.getWrongContactLocal().associate(contact, 1, false);
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
            localBean.deassociate(null, 1, true);
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
            localBean.deassociate(new Contact(), -1, true);
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
            localBean.deassociate(contact, 1, true);
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
            localBean.deassociate(contact, 1, true);
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
            localBean.deassociate(contact, 1, false);
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
        localBean.addContact(contact, false);
        try {
            contact.setId(contact.getId() + 1);
            localBean.deassociate(contact, 1, true);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            localBean.removeContact(contact.getId() - 1, false);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            localBean.deassociate(contact, 1, false);
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
        localBean.addContact(contact, false);
        localBean.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            localBean.deassociate(contact, 1, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.assertTransactionRollbacked(transaction);
            this.assertAuditRecordsCount(preAuditCount, 0);
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            localBean.removeContact(contact.getId(), false);
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
            this.getWrongContactLocal().deassociate(contact, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
            this.assertTransactionRollbacked(transaction);
        }
    }
}
