/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.stresstests;

import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ejb.AddressBean;
import com.topcoder.timetracker.contact.ejb.AddressLocal;
import com.topcoder.timetracker.contact.ejb.AddressLocalHome;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate;
import com.topcoder.timetracker.contact.ejb.ContactBean;
import com.topcoder.timetracker.contact.ejb.ContactHomeLocal;
import com.topcoder.timetracker.contact.ejb.ContactLocal;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate;

/**
 * <p>
 * This class is the stress test for Time Tracker Contact.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class ContactStressTest extends TestCase {

    /**
     * The looping count for testing.
     */
    private static final int COUNT = 100;

    /**
     * The mockup context.
     */
    private static InitialContext context;

    /**
     * The mockup container.
     */
    private static MockContainer mockContainer;

    /**
     * Setup the test.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        StressTestHelper.clearConfig();
        StressTestHelper.initConfig();
        StressTestHelper.initDB();
        MockContextFactory.revertSetAsInitial();
        MockContextFactory.setAsInitial();
        context = new InitialContext();
        mockContainer = new MockContainer(context);
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
        context.rebind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        context.rebind("java:comp/env/AddressDAOKey", "AddressDAO");
        context.rebind("java:comp/env/ContactDAOKey", "ContactDAO");
        deployBean("java:comp/env/ejb/ContactLocal", ContactHomeLocal.class, ContactLocal.class, ContactBean.class);
        deployBean("java:comp/env/ejb/AddressLocal", AddressLocalHome.class, AddressLocal.class, AddressBean.class);
    }

    /**
     * Reset the system properties.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        MockContextFactory.revertSetAsInitial();
        StressTestHelper.clearConfig();
    }

    /**
     * Deploys the bean with the given properties to the current mock container.
     *
     * @param jndi the jndi name
     * @param home the home interface
     * @param beanInterface the bean interface
     * @param beanClass the class for the bean
     * @throws Exception to JUnit
     */
    protected void deployBean(String jndi, Class home, Class beanInterface, Class beanClass) throws Exception {
        SessionBeanDescriptor descriptor = new SessionBeanDescriptor(jndi, home, beanInterface, beanClass);
        mockContainer.deploy(descriptor);
    }

    /**
     * <p>
     * This method tests the creation of Contact Manager in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testContactStressForCreatingManager() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            ContactManagerLocalDelegate manager = new ContactManagerLocalDelegate();
            ContactManagerLocalDelegate manager2 = new ContactManagerLocalDelegate(
                "com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate");
        }

        long endTime = System.currentTimeMillis();

        System.out.println("The stress test for creating manager costs " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of adding contacts in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testContactStressForAddingContacts() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; ++i) {

            ContactManagerLocalDelegate manager = new ContactManagerLocalDelegate();
            ContactManagerLocalDelegate manager2 = new ContactManagerLocalDelegate(
                "com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate");

            Contact[] contacts = createContacts();
            manager.addContacts(contacts, false);
            manager.addContact(contacts[0], true);

        }

        long endTime = System.currentTimeMillis();
        System.out
            .println("The stress test for adding contacts costs " + (endTime - startTime) + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of removing contacts in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testContactStressForRemovingContacts() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; ++i) {
            ContactManagerLocalDelegate manager = new ContactManagerLocalDelegate();
            ContactManagerLocalDelegate manager2 = new ContactManagerLocalDelegate(
                "com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate");

            Contact[] contacts = createContacts();
            manager.addContacts(contacts, false);
            manager.addContact(contacts[0], true);

            manager.removeContact(contacts[1].getId(), true);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for removing contacts costs " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of getting all contacts in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testContactStressForGettingAllContacts() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; ++i) {
            ContactManagerLocalDelegate manager = new ContactManagerLocalDelegate();
            ContactManagerLocalDelegate manager2 = new ContactManagerLocalDelegate(
                "com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate");

            Contact[] contacts = createContacts();
            manager.addContacts(contacts, true);
            manager.addContact(contacts[0], false);

            contacts = manager.getAllContacts();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for getting all contacts costs " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of creating address manager in high stress.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContactStressForCreatingAddressManager() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            AddressManagerLocalDelegate manager = new AddressManagerLocalDelegate();
            AddressManagerLocalDelegate manager2 = new AddressManagerLocalDelegate(
                "com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for creating address manager costs " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of adding address in high stress.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContactStressForAddingAddress() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            AddressManagerLocalDelegate manager = new AddressManagerLocalDelegate();
            AddressManagerLocalDelegate manager2 = new AddressManagerLocalDelegate(
                "com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate");

            Address[] addresses = createAddresses();
            manager.addAddresses(addresses, true);
            manager.addAddress(addresses[0], false);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for adding address costs " + (endTime - startTime) + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of removing address in high stress.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContactStressForRemovingAddress() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            AddressManagerLocalDelegate manager = new AddressManagerLocalDelegate();
            AddressManagerLocalDelegate manager2 = new AddressManagerLocalDelegate(
                "com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate");

            Address[] addresses = createAddresses();
            manager.addAddresses(addresses, false);
            manager.addAddress(addresses[0], true);

            manager.removeAddress(addresses[1].getId(), true);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test for removing address costs " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * Creates a demo address.
     *
     * @return a sample address.
     */
    private Address[] createAddresses() {
        return new Address[]{StressTestHelper.createAddress1(), StressTestHelper.createAddress1() };
    }

    /**
     * Creates a demo contact.
     *
     * @return a sample contact.
     */
    private Contact[] createContacts() {
        return new Contact[]{StressTestHelper.createContact1(), StressTestHelper.createContact1() };
    }
}
