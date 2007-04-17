/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.persistence.InformixAddressDAO;
import com.topcoder.timetracker.contact.persistence.InformixContactDAO;

import junit.framework.TestCase;

import java.sql.Connection;
import java.util.Date;


/**
 * <p>Failure test cases for the class InformixContactDAO.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class InformixContactDAOFailureTests extends TestCase {
    /** InformixContactDAO instance to test against. */
    private InformixContactDAO dao = null;

    /** Database connection. */
    private Connection conn = null;

    /** AuditManager for testing. */
    private AuditManager auditManager = null;

    /**
     * Setup the environment.
     *
     * @throws Exception into JUnit
     */
    protected void setUp() throws Exception {
        auditManager = new MockAuditManager();
        TestHelper.setUpConfiguration();
        conn = TestHelper.getConnection();
        TestHelper.insertData(conn);
        dao = new InformixContactDAO(TestHelper.CONTACT_DAO_NS, auditManager);
    }

    /**
     * Test ctor with null value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullNamespace() throws Exception {
        try {
            new InformixContactDAO(null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with empty value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyNamespace() throws Exception {
        try {
            new InformixContactDAO(" ");
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with not exist value, iae expected.
     */
    public void testCtor_notExistNamespace() {
        try {
            new InformixAddressDAO("notExist");
            fail("The namespace does not exist.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test ctor with null value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullNamespace2() throws Exception {
        try {
            new InformixContactDAO(null, null);
            fail("The namespace is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with empty value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyNamespace2() throws Exception {
        try {
            new InformixContactDAO(" ", null);
            fail("The namespace is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test ctor with not exist value, iae expected.
     */
    public void testCtor_notExistNamespace2() {
        try {
            new InformixContactDAO("notExist", null);
            fail("The namespace does not exist.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Create InformixContactDAO with invalid configuration.
     *
     * @param namespace the namespace to create InformixContactDAO instance
     */
    private void createWith_invalidConfiguration(String namespace) {
        try {
            new InformixContactDAO(namespace, null);
            fail("The namespace is invalid.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test the ctor with invalid configuration, the property 'connection_factory_namespace' is missing.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration1() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure1");
    }

    /**
     * Test the ctor with invalid configuration, the property 'connection_factory_namespace''s value is invalid, it
     * does not exist. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration2() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure2");
    }

    /**
     * Test the ctor with invalid configuration, the property 'connection_name''s value is invalid.
     * @throws Exception into Junit
     */
    public void testCtor_invalidConfiguration3() throws Exception{
    	 new InformixContactDAO("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure3",auditManager);
    }

    /**
     * Test the ctor with invalid configuration, the property 'idgenerator_name' is missing, it does not exist.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration4() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure4");
    }

    /**
     * Test the ctor with invalid configuration, the property 'search_bundle_name' is missing, it does not exist.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration5() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure5");
    }

    /**
     * Test the ctor with invalid configuration, the property 'search_bundle_name''s value is invalid, it does not
     * exist. ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration8()throws Exception {
    	createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure8");
    }

    /**
     * Test the ctor with invalid configuration, the property 'search_bundle_namespace' is missing, it does not exist.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration6() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure6");
    }

    /**
     * Test the ctor with invalid configuration, the property 'search_bundle_namespace' is invalid, it does not exist.
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration7() {
        createWith_invalidConfiguration("com.topcoder.timetracker.contact.persistence.InformixContactDAO.failure7");
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with null contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_nullContact() throws Exception {
        try {
            this.dao.addContact(null, false);
            fail("The contact to add is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact() throws Exception {
        try {
            this.dao.addContact(TestHelper.getContact(1), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact2() throws Exception {
        try {
            this.dao.addContact(TestHelper.getContact(2), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact3() throws Exception {
        try {
            this.dao.addContact(TestHelper.getContact(3), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact4() throws Exception {
        try {
            this.dao.addContact(TestHelper.getContact(4), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with contact without contact_type.
     * Seems you don't need to thrown InvalidPropertyException.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact5() throws Exception {
         this.dao.addContact(TestHelper.getContact(5), false);
        
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact6() throws Exception {
        try {
            this.dao.addContact(TestHelper.getContact(6), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact7() throws Exception {
        try {
            this.dao.addContact(TestHelper.getContact(7), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test addContacts(Contact[] contacts, boolean doAudit) with null contacts array, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContacts_nullContactArray() throws Exception {
        try {
            this.dao.addContacts(null, false);
            fail("The contacts is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test addContacts(Contact[] contacts, boolean doAudit) with null contacts array, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContacts_nullElementContactArray()
        throws Exception {
        try {
            this.dao.addContacts(new Contact[] { null }, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test retrieveContact(long id) with negitive id, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRetrieveContact_negativeId() throws Exception {
        try {
            this.dao.retrieveContact(-1);
            fail("The contact id is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test retrieveContacts(long[] ids) with null ids. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRetrieveContact_nullIds() throws Exception {
        try {
            this.dao.retrieveContacts(null);
            fail("The ids is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test retrieveContacts(long[] ids) with negative id, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRetrieveContacts_negativeId() throws Exception {
        try {
            this.dao.retrieveContacts(new long[] { 0, -1 });
            fail("The ids is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test removeContacts(long[] ids, boolean doAudit)  with negative id, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContact_negativeId() throws Exception {
        try {
            this.dao.removeContact(-1, false);
            fail("The id is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test removeContacts(long[] ids, boolean doAudit)  with negative id, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContacts_notPositiveId() throws Exception {
        try {
            this.dao.removeContacts(new long[] { 0 }, false);
            fail("The id is not positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test removeContact(long id, boolean doAudit)  with null ids, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContacts_nullIds() throws Exception {
        try {
            this.dao.removeContacts(null, false);
            fail("The ids is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the removeContact method.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContact_hasAssociate() throws Exception {
        //no exception should get thrown
        dao.removeContacts(new long[] { 1, 2, 3 }, false);

        //create contact
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        //create associate
        this.dao.associate(created, 1, false);

        this.dao.removeContact(created.getId(), false);
           
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with null contact, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_nullContact() throws Exception {
        try {
            this.dao.updateContact(null, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(1);
        toUpdate.setId(created.getId());

        try {
            this.dao.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact2() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(2);
        toUpdate.setId(created.getId());

        try {
            this.dao.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact3() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(3);
        toUpdate.setId(created.getId());

        try {
            this.dao.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact4() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(4);
        toUpdate.setId(created.getId());

        try {
            this.dao.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact5() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(5);
        toUpdate.setId(created.getId());

        try {
            this.dao.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact6() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(6);
        toUpdate.setId(created.getId());

        try {
            this.dao.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact7() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(7);
        toUpdate.setId(created.getId());

        try {
            this.dao.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test updateContact with not exist contact, EntityNotFoundException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_notExist() throws Exception {
    	Contact to = TestHelper.getContact(9);
    	to.setId(100);
    	to.setCreationDate(new Date());
    	to.setModificationDate(new Date());
        try {
            this.dao.updateContact(  to, false);
            fail("The contact to update does not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }
    }

    /**
     * Test searchContacts(Filter filter) with null filter, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testSearchContact_null() throws Exception {
        try {
            this.dao.searchContacts(null);
            fail("The filter is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with null contact value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_nullContact() throws Exception {
        try {
            this.dao.associate(null, 1, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with null contact value, iae expected.
     */
    public void testAssociate_notPositiveID() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        try {
            this.dao.associate(created, 0, false);
            fail("The entity id  is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with null 
     * contact value, InvalidPropertyException expected.
     */
    public void testAssociate_nullAddressType() throws Exception {
        Contact created = TestHelper.getContact(5);
        created.setModificationDate(new java.util.Date());
        created.setCreationDate(new java.util.Date());
        
        try {
            this.dao.associate(created, 1, false);
            fail("The contact has not address type.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }
    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with not exist contact, iae expected.
     */
    public void testAssociate_notExistContact() throws Exception {
        Contact created = TestHelper.getContact(8);
        created.setId(100);
        created.setCreationDate(new Date());
        created.setModificationDate(new Date());
        try {
            this.dao.associate(created, 1, false);
            fail("The contact does not exist.");
        } catch (AssociationException e) {
            //good
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) , the contact has already associated with
     * another entity.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_already() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);
        this.dao.associate(created, 1, false);
        this.dao.associate(created, 2, false);
   }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) , the entity has already associated with another
     * contact, still ok.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_already2() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);
        this.dao.associate(created, 1, false);

        Contact anotherContact = TestHelper.getContact(8);
        this.dao.addContact(anotherContact, false);
        this.dao.associate(anotherContact, 1, false);
    }

    /**
     * Test deassociate(Contact contact, long entityId, boolean doAudit) with null value contact, iae expected.
     */
    public void testDeassociate_nullContact() throws Exception {
        try {
            this.dao.deassociate(null, 1, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }


    /**
     * Test deassociate(Contact contact, long entityId, boolean doAudit) with not positive entity id, iae expected.
     */
    public void testDeassociate_notPositiveID() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.dao.addContact(created, false);

        try {
            this.dao.deassociate(created, 0, false);
            fail("The entity id  is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test deassociate method. Accuracy test case.
     *
     * @throws Exception into Junit
     */
    public void testDeassociate() throws Exception {
        //create
        Contact contact = TestHelper.getContact(8);
        this.dao.addContact(contact, false);

        //retrieve
        
        contact.setEmailAddress("wishingbone@topcoder.com");
        //update
        this.dao.updateContact(contact, true);

        //associate
        this.dao.associate(contact, 1, true);

        //deassociate
        this.dao.deassociate(contact, 2, true);
        //    	deassociate
        this.dao.deassociate(contact, 1, true);

        //remove it
        this.dao.removeContact(contact.getId(), true);
    }

    /**
     * Clear the environment.
     *
     * @throws Exception into JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTable(conn);
        conn.close();
        TestHelper.clearConfiguration();
    }
}
