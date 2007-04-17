/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.persistence;

import java.util.Date;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactDAO;
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BaseTestCase;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;

/**
 * <p>This test case contains failure tests for <code>InformixContactDAO</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixContactDAOTestExp extends BaseTestCase {
    /**
     * <p>
     * Indicates whether the audit related tables have been cleared.
     * </p>
     */
    private static boolean auditCleared = false;
    /**
     * <p>
     * <code>InformixContactDAO</code> used in the test case.
     * </p>
     */
    private ContactDAO dao = null;

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
        dao = new InformixContactDAO();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        dao = null;
        this.closeLastJDBCConnections();
        super.tearDown();
    }
    /**
     * <p>
     * Test constructor <code>InformixContactDAO()</code>. Namespace is unknow, ConfigurationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixContactDAO1() throws Exception {
        try {
            this.removeConfigManagerNS();
            new InformixContactDAO();
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
            this.initialConfigManager();
        }
    }

    /**
     * <p>
     * Test constructor <code>InformixContactDAO(String)</code>. Namespace is unknow, ConfigurationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixContactDAO2() throws Exception {
        try {
            new InformixContactDAO("unknown namespace");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test constructor <code>InformixContactDAO(String, AuditManager)</code>.
     * Namespace is unknow, ConfigurationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixContactDAO3() throws Exception {
        try {
            new InformixContactDAO("unknown namespace", null);
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
    public void testAddContact1() throws Exception {
        try {
            this.dao.addContact(null, false);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testAddContact2() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setFirstName(this.getStringWithLengthGreaterThan100());
            this.dao.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>addContact()</code>.
     * Last Name is with length greater than 100, IPE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact3() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setLastName(this.getStringWithLengthGreaterThan100());
            this.dao.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testAddContact4() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setPhoneNumber(this.getStringWithLengthGreaterThan100());
            this.dao.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testAddContact5() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setEmailAddress(this.getStringWithLengthGreaterThan100());
            this.dao.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testAddContact6() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            this.dao.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testAddContact7() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.dao.addContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>addContact()</code>.
     * IDGenerator is exhausted, IDGenerationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContact_IDGenerationException()
        throws Exception {
        this.exhaustIDGenerator("ContactIDGenerator", 1);
        try {
            Contact contact = this.getContact();
            this.dao.addContact(contact, false);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            //good
            this.exhaustIDGenerator("ContactIDGenerator", 0);
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
            new InformixContactDAO("InformixContactDAO_Error_4").addContact(contact, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
            this.dao.addContact(contact, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
        }
    }
    /**
     * <p>
     * Test method <code>addContacts()</code>.
     * Given array contains null member, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts() throws Exception {
        try {
            this.dao.addContacts(new Contact[]{null}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>addContacts()</code>.
     * IDGenerator is exhausted, IDGenerationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddContacts_IDGenerationException()
        throws Exception {
        this.exhaustIDGenerator("ContactIDGenerator", 1);
        try {
            Contact contact = this.getContact();
            this.dao.addContacts(new Contact[]{contact}, false);
            fail("IDGenerationException expected");
        } catch (IDGenerationException e) {
            //good
            this.exhaustIDGenerator("ContactIDGenerator", 0);
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
            new InformixContactDAO("InformixContactDAO_Error_4").
            addContacts(new Contact[]{this.getContact(), contact}, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
            Contact contact = this.getContact();
            this.dao.addContacts(new Contact[]{contact}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
        }
    }

    /**
     * <p>
     * Test method <code>retrieveContact()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContact() throws Exception {
        try {
            this.dao.retrieveContact(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
            new InformixContactDAO("InformixContactDAO_Error_4").retrieveContact(1);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.retrieveContact(contact.getId());
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>retrieveContacts()</code>.
     * Given id is negative, IAE expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveContacts() throws Exception {
        try {
            this.dao.retrieveContacts(new long[]{-1});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
            new InformixContactDAO("InformixContactDAO_Error_4").retrieveContacts(new long[]{1});
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        Contact contact = this.getContact();
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.retrieveContacts(new long[]{contact.getId()});
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
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
    public void testRemoveContact() throws Exception {
        try {
            this.dao.removeContact(-1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.removeContact(contact.getId(), true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeContact(contact.getId(), false);
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
            new InformixContactDAO("InformixContactDAO_Error_4").removeContact(1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.removeContact(contact.getId(), false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
    public void testRemoveContacts() throws Exception {
        try {
            this.dao.removeContacts(new long[]{-1}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
        Contact contact = this.getContact();
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.removeContacts(new long[]{contact.getId()}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeContact(contact.getId(), false);
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
            new InformixContactDAO("InformixContactDAO_Error_4").removeContacts(new long[]{1}, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.removeContacts(new long[]{contact.getId()}, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
    public void testupdateContact1() throws Exception {
        try {
            this.dao.updateContact(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testUpdateContact2() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setFirstName(this.getStringWithLengthGreaterThan100());
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateContact3() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setLastName(this.getStringWithLengthGreaterThan100());
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateContact4() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setPhoneNumber(this.getStringWithLengthGreaterThan100());
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateContact5() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setEmailAddress(this.getStringWithLengthGreaterThan100());
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateContact6() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateContact7() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateContact8() throws Exception {
        try {
            Contact contact = this.getContact();
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testUpdateContact9() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            this.dao.updateContact(contact, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact.setFirstName("new name");
            this.dao.updateContact(contact, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeContact(contact.getId(), false);
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
            new InformixContactDAO("InformixContactDAO_Error_4").updateContact(contact, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            contact.setFirstName("new name");
            this.dao.updateContact(contact, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
            this.dao.updateContact(contact, false);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
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
        Contact contact = this.getContact();
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            contact.setFirstName("new name");
            this.dao.updateContacts(new Contact[]{contact}, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeContact(contact.getId(), false);
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
            new InformixContactDAO("InformixContactDAO_Error_4").updateContacts(new Contact[]{contact}, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            contact.setFirstName("new name");
            this.dao.updateContacts(new Contact[]{contact}, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setId(1);
            this.dao.updateContacts(new Contact[]{contact}, false);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            //good
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
            new InformixContactDAO("InformixContactDAO_Error_4").getAllContacts();
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.getAllContacts();
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
        }
    }
    /**
     * <p>
     * Test method <code>searchContacts()</code>.
     * Given filter is null, IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchContacts() throws Exception {
        try {
            this.dao.searchContacts(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>searchContacts()</code>.
     * Given filter is not searchable, PersistenceException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchContacts_PersistenceException() throws Exception {
        try {
            this.dao.searchContacts(new EqualToFilter("contact_id", new Long(1)));
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
        }
    }
    /**
     * <p>
     * Test method <code>searchContacts()</code>.
     * The searched contact is associated with mutiple types, AssociationException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchContacts_AssociationException() throws Exception {
        Contact contact = this.getContact();
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.searchContacts(ContactFilterFactory.createCreatedByFilter("user"));
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
    public void testassociate1() throws Exception {
        try {
            this.dao.associate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testassociate2() throws Exception {
        try {
            this.dao.associate(new Contact(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testassociate3() throws Exception {
        try {
            Contact contact = new Contact();
            this.dao.associate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate4() throws Exception {
        try {
            Contact contact = new Contact();
            contact.setId(1);
            this.dao.associate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate5() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationUser(null);
            this.dao.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate6() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.dao.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate7() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setModificationDate(new Date());
            contact.setCreationDate(null);
            this.dao.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testassociate8() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setCreationDate(new Date());
            contact.setModificationDate(new Date());
            contact.setModificationDate(null);
            this.dao.associate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.associate(contact, 1, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeContact(contact.getId(), false);
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
        this.dao.addContact(contact, false);
        try {
            contact.setId(contact.getId() + 1);
            this.dao.associate(contact, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.dao.removeContact(contact.getId() - 1, false);
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.associate(contact, 3, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
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
            new InformixContactDAO("InformixContactDAO_Error_4").associate(contact, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
    public void testdeassociate1() throws Exception {
        try {
            this.dao.deassociate(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testdeassociate2() throws Exception {
        try {
            this.dao.deassociate(new Contact(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
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
    public void testdeassociate3() throws Exception {
        try {
            Contact contact = new Contact();
            this.dao.deassociate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testdeassociate4() throws Exception {
        try {
            Contact contact = new Contact();
            contact.setId(1);
            this.dao.deassociate(contact, 1, true);
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
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
    public void testdeassociate5() throws Exception {
        try {
            Contact contact = this.getContact();
            contact.setModificationUser(null);
            this.dao.deassociate(contact, 1, false);
            fail("IPE expected");
        } catch (InvalidPropertyException e) {
            //good
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
        this.dao.addContact(contact, false);
        try {
            contact.setId(contact.getId() + 1);
            this.dao.deassociate(contact, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.dao.removeContact(contact.getId() - 1, false);
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("insert into contact_relation (creation_user,modification_user,"
            + "creation_date,modification_date,contact_id,contact_type_id,entity_id) values ('u','u',current,current,"
            + contact.getId() + ",1,2) ");
        try {
            this.dao.deassociate(contact, 1, false);
            fail("AssociationException expected");
        } catch (AssociationException e) {
            //good
            this.executeUpdate("delete from contact_relation");
            this.executeUpdate("delete from contact");
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
            new InformixContactDAO("InformixContactDAO_Error_4").deassociate(contact, 1, false);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            //good
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
        this.dao.addContact(contact, false);
        this.dao.associate(contact, 1, false);
        this.executeUpdate("delete from application_area where description = 'Project'");
        try {
            this.dao.deassociate(contact, 1, true);
            fail("AuditException expected");
        } catch (AuditException e) {
            //good
            this.executeUpdate("insert into application_area(app_area_id, description,"
                + "creation_date, creation_user, modification_date, modification_user) values (6, 'Project', current,"
                + " 'user', current, 'user');");
            this.dao.removeContact(contact.getId(), false);
        }
    }
}
