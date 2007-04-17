/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.mockrunner.mock.ejb.MockUserTransaction;

import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate;

import junit.framework.TestCase;

import org.mockejb.jndi.MockContextFactory;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>Failure test cases for the class ContactManagerLocalDelegate.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class ContactManagerLocalDelegateFailureTests extends TestCase {
    /** ContactManagerLocalDelegate to test against. */
    private ContactManagerLocalDelegate delegate = null;

    /** State of this test case. These variables are initialized by setUp method */
    private Context context;

    /** Database connection. */
    private Connection conn = null;
    /**
     * The MockUserTransaction.
     */
    private MockUserTransaction userTransaction = new MockUserTransaction();
    /**
     * setup environment
     */
    protected void setUp() throws Exception {
        // clear the talble and setup the environment.
        TestHelper.clearConfiguration();
        TestHelper.setUpConfiguration();
        conn = TestHelper.getConnection();
        TestHelper.clearTable(conn);
        TestHelper.insertData(conn);

        /*
         * We need to set MockContextFactory as our JNDI provider. This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        context = new InitialContext();
        context.rebind("java:comp/env/SpecificationNamespace", TestHelper.OF_NAMESPACE);
        context.rebind("java:comp/env/ContactDAOKey", "contactDAO");

        context.rebind("javax.transaction.UserTransaction", userTransaction);

        ContactBeanFailureTests.deployEJB(context);

        this.delegate = new ContactManagerLocalDelegate();
    }

    /**
     * Test the ctor with null namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_nullNS() throws Exception {
        try {
            new ContactManagerLocalDelegate(null);
            fail("The ns is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the ctor with empty namespace, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testCtor_emptyNS() throws Exception {
        try {
            new ContactManagerLocalDelegate(" ");
            fail("The ns is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the ctor with invalid namespace, ConfigurationException expected.
     */
    public void testCtor_invalidNS() {
        try {
            new ContactManagerLocalDelegate("com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate.failure1");
            fail("The ns is invalid.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test the ctor with invalid namespace, ConfigurationException expected.
     */
    public void testCtor_invalidNS2() {
        try {
            new ContactManagerLocalDelegate("com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate.failure2");
            fail("The ns is invalid.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with null contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_nullContact() throws Exception {
        try {
            this.delegate.addContact(null, false);
            fail("The contact to add is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact() throws Exception {
        try {
            this.delegate.addContact(TestHelper.getContact(1), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact2() throws Exception {
        try {
            this.delegate.addContact(TestHelper.getContact(2), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact3() throws Exception {
        try {
            this.delegate.addContact(TestHelper.getContact(3), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact4() throws Exception {
        try {
            this.delegate.addContact(TestHelper.getContact(4), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact6() throws Exception {
        try {
            this.delegate.addContact(TestHelper.getContact(6), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addContact(Contact contact, boolean doAudit) with invalid contact, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContact_invalidContact7() throws Exception {
        try {
            this.delegate.addContact(TestHelper.getContact(7), false);
            fail("The contact to add is null.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test addContacts(Contact[] contacts, boolean doAudit) with null contacts array, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAddContacts_nullContactArray() throws Exception {
        try {
            this.delegate.addContacts(null, false);
            fail("The contacts is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
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
            this.delegate.addContacts(new Contact[] { null }, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test retrieveContact(long id) with negitive id, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRetrieveContact_negativeId() throws Exception {
        try {
            this.delegate.retrieveContact(-1);
            fail("The contact id is negative.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test retrieveContacts(long[] ids) with null ids. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRetrieveContact_nullIds() throws Exception {
        try {
            this.delegate.retrieveContacts(null);
            fail("The ids is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test retrieveContacts(long[] ids) with negative id, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testRetrieveContacts_negativeId() throws Exception {
        try {
            this.delegate.retrieveContacts(new long[] { 0, -1 });
            fail("The ids is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test removeContacts(long[] ids, boolean doAudit)  with negative id, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContact_negativeId() throws Exception {
        try {
            this.delegate.removeContact(-1, false);
            fail("The id is negative.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test removeContacts(long[] ids, boolean doAudit)  with negative id, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContacts_notPositiveId() throws Exception {
        try {
            this.delegate.removeContacts(new long[] { 0 }, false);
            fail("The id is not positive.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test removeContact(long id, boolean doAudit)  with null ids, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContacts_nullIds() throws Exception {
        try {
            this.delegate.removeContacts(null, false);
            fail("The ids is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with null contact, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_nullContact() throws Exception {
        try {
            this.delegate.updateContact(null, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(1);
        toUpdate.setId(created.getId());

        try {
            this.delegate.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact2() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(2);
        toUpdate.setId(created.getId());

        try {
            this.delegate.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact3() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(3);
        toUpdate.setId(created.getId());

        try {
            this.delegate.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact4() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(4);
        toUpdate.setId(created.getId());

        try {
            this.delegate.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact5() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(5);
        toUpdate.setId(created.getId());

        try {
            this.delegate.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact6() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(6);
        toUpdate.setId(created.getId());

        try {
            this.delegate.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with invalid contact, InvalidPropertyException expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_invalidContact7() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(7);
        toUpdate.setId(created.getId());

        try {
            this.delegate.updateContact(toUpdate, false);
            fail("The contact is invalid.");
        } catch (InvalidPropertyException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
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
    	to.setCreationDate(new java.util.Date());
    	to.setModificationDate(new java.util.Date());
    	try {
            this.delegate.updateContact(to, false);
            fail("The contact to update does not exist.");
        } catch (EntityNotFoundException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test searchContacts(Filter filter) with null filter, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testSearchContact_null() throws Exception {
        try {
            this.delegate.searchContacts(null);
            fail("The filter is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with null contact value, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testAssociate_nullContact() throws Exception {
        try {
            this.delegate.associate(null, 1, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with null contact value, iae expected.
     */
    public void testAssociate_notPositiveID() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        try {
            this.delegate.associate(created, 0, false);
            fail("The entity id  is negative.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with not exist contact, iae expected.
     */
    public void testAssociate_notExistContact() throws Exception {
        Contact created = TestHelper.getContact(8);
        created.setId(1000);
        created.setCreationDate(new java.util.Date());
        created.setModificationDate(new java.util.Date());
        try {
            this.delegate.associate(created, 1, false);
            fail("The contact does not exist.");
        } catch (AssociationException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

  
    /**
     * Test deassociate(Contact contact, long entityId, boolean doAudit) with null value contact, iae expected.
     */
    public void testDeassociate_nullContact() throws Exception {
        try {
            this.delegate.deassociate(null, 1, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with null 
     * contact value,InvalidPropertyException expected.
     */
    public void testAssociate_nullAddressType() throws Exception {
        Contact created = TestHelper.getContact(5);
        created.setModificationDate(new java.util.Date());
        created.setCreationDate(new java.util.Date());
        
        try {
            this.delegate.associate(created, 1, false);
            fail("The contact has not address type.");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * Test deassociate(Contact contact, long entityId, boolean doAudit) with not positive entity id, iae expected.
     */
    public void testDeassociate_notPositiveID() throws Exception {
        Contact created = TestHelper.getContact(8);
        this.delegate.addContact(created, false);

        try {
            this.delegate.deassociate(created, 0, false);
            fail("The entity id  is negative.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
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
        this.delegate.addContact(contact, false);

        //retrieve
        
        contact.setEmailAddress("wishingbone@topcoder.com");
        //update
        this.delegate.updateContact(contact, true);

        //associate
        this.delegate.associate(contact, 1, true);

        //deassociate
        this.delegate.deassociate(contact, 2, true);
        //    	deassociate
        this.delegate.deassociate(contact, 1, true);

        //remove it
        this.delegate.removeContact(contact.getId(), true);
    }

    /**
     * Clear the environment.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTable(conn);

        if (conn != null) {
            conn.close();
        }

        TestHelper.clearConfiguration();
    }
}
