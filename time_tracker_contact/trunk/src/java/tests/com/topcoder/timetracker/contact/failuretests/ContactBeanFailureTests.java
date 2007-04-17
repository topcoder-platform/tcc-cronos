/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import java.sql.Connection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.TransactionManager;
import org.mockejb.TransactionPolicy;
import org.mockejb.interceptor.AspectSystemFactory;
import org.mockejb.interceptor.ClassPointcut;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.contact.AssociationException;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.ejb.ContactBean;
import com.topcoder.timetracker.contact.ejb.ContactHomeLocal;
import com.topcoder.timetracker.contact.ejb.ContactLocal;


/**
 * <p>Failure test cases for the class ContactLocal.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class ContactBeanFailureTests extends TestCase {
    /** The ContactLocal. */
    private ContactLocal contact = null;

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

        deployEJB(context);

        this.contact = lookUpEJB();
    }

    /**
     * Lookup ejb.
     *
     * @return AddressLocal instance
     *
     * @throws NamingException fails to find ejb
     * @throws CreateException fails to find ejb
     */
    private ContactLocal lookUpEJB() throws NamingException, CreateException {
        // Lookup the home
    	ContactHomeLocal entryLocalHomeObj = (ContactHomeLocal)context.lookup("timetracker/contactEJB");

        // create the bean
        return entryLocalHomeObj.create();
    }

    /**
     * Deploy the ejb with the context
     *
     * @param context context to deploy
     *
     * @throws NamingException fails to deploy ejb
     */
    public static void deployEJB(Context context) throws NamingException {
        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("timetracker/contactEJB",
                ContactHomeLocal.class, ContactLocal.class, ContactBean.class);
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);
        AspectSystemFactory.getAspectSystem().addFirst(new ClassPointcut(ContactBean.class, false),
                new TransactionManager(TransactionPolicy.REQUIRED));
    }

    /**
     * Test ejb create method. One property is missing, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext() throws Exception {
        context.unbind("java:comp/env/SpecificationNamespace");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * Test ejb create method. One property is missing, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext1() throws Exception {
        context.unbind("java:comp/env/ContactDAOKey");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * Test ejb create method. One property is invalid, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext2() throws Exception {
        context.rebind("java:comp/env/ContactDAOKey", "invalid");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * Test ejb create method. One property is invalid, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext4() throws Exception {
        context.rebind("java:comp/env/ContactDAOKey", "addressDAO");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
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
            this.contact.addContact(null, false);
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
            this.contact.addContact(TestHelper.getContact(1), false);
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
            this.contact.addContact(TestHelper.getContact(2), false);
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
            this.contact.addContact(TestHelper.getContact(3), false);
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
            this.contact.addContact(TestHelper.getContact(4), false);
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
            this.contact.addContact(TestHelper.getContact(6), false);
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
            this.contact.addContact(TestHelper.getContact(7), false);
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
            this.contact.addContacts(null, false);
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
            this.contact.addContacts(new Contact[] { null }, false);
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
            this.contact.retrieveContact(-1);
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
            this.contact.retrieveContacts(null);
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
            this.contact.retrieveContacts(new long[] { 0, -1 });
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
            this.contact.removeContact(-1, false);
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
            this.contact.removeContacts(new long[] { 0 }, false);
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
            this.contact.removeContacts(null, false);
            fail("The ids is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test the removeContact method.
     *
     * @throws Exception into Junit
     */
    public void testRemoveContact_hasAssociate() throws Exception {
        //no exception should get thrown
        contact.removeContacts(new long[] { 1, 2, 3 }, false);

        //create contact
        Contact created = TestHelper.getContact(8);
        this.contact.addContact(created, false);

        //create associate
        this.contact.associate(created, 1, false);

        //delete the contact
        this.contact.removeContact(created.getId(), false);
           
    }

    /**
     * Test updateContact(Contact contact, boolean doAudit) with null contact, iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateContact_nullContact() throws Exception {
        try {
            this.contact.updateContact(null, false);
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
        this.contact.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(1);
        toUpdate.setId(created.getId());

        try {
            this.contact.updateContact(toUpdate, false);
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
        this.contact.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(2);
        toUpdate.setId(created.getId());

        try {
            this.contact.updateContact(toUpdate, false);
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
        this.contact.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(3);
        toUpdate.setId(created.getId());

        try {
            this.contact.updateContact(toUpdate, false);
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
        this.contact.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(4);
        toUpdate.setId(created.getId());

        try {
            this.contact.updateContact(toUpdate, false);
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
        this.contact.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(5);
        toUpdate.setId(created.getId());

        try {
            this.contact.updateContact(toUpdate, false);
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
        this.contact.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(6);
        toUpdate.setId(created.getId());

        try {
            this.contact.updateContact(toUpdate, false);
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
        this.contact.addContact(created, false);

        Contact toUpdate = TestHelper.getContact(7);
        toUpdate.setId(created.getId());

        try {
            this.contact.updateContact(toUpdate, false);
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
    	to.setCreationDate(new Date());
    	to.setModificationDate(new Date());
        try {
            this.contact.updateContact(to, false);
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
            this.contact.searchContacts(null);
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
            this.contact.associate(null, 1, false);
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
        this.contact.addContact(created, false);

        try {
            this.contact.associate(created, 0, false);
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
        created.setId(100);
        created.setCreationDate(new Date());
        created.setModificationDate(new Date());
        try {
            this.contact.associate(created, 1, false);
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
            this.contact.deassociate(null, 1, false);
            fail("The contact is null.");
        } catch (IllegalArgumentException e) {
            AddressBeanFailureTests.assertTransactionRollbacked(userTransaction);
        }
    }

    /**
     * Test associate(Contact contact, long entityId, boolean doAudit) with null contact value, InvalidPropertyException expected.
     */
    public void testAssociate_nullAddressType() throws Exception {
        Contact created = TestHelper.getContact(5);
        created.setModificationDate(new java.util.Date());
        created.setCreationDate(new java.util.Date());
        
        try {
            this.contact.associate(created, 1, false);
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
        this.contact.addContact(created, false);

        try {
            this.contact.deassociate(created, 0, false);
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
        Contact created = TestHelper.getContact(8);
        this.contact.addContact(created, false);

       
        created.setEmailAddress("wishingbone@topcoder.com");
        //update
        this.contact.updateContact(created, true);

        //associate
        this.contact.associate(created, 1, true);

        //deassociate
        this.contact.deassociate(created, 2, true);
        //    	deassociate
        this.contact.deassociate(created, 1, true);

        //remove it
        this.contact.removeContact(created.getId(), true);
    }

    /**
     * Test ejb create method. One property is invalid, CreateException expected.
     *
     * @throws Exception into JUnit
     */
    public void testEJBCreate_invalidContext3() throws Exception {
        context.rebind("java:comp/env/SpecificationNamespace", "notExist");
        deployEJB(context);

        try {
            lookUpEJB();
            fail("Fails to create ejb.");
        } catch (CreateException e) {
            //good
        }
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
