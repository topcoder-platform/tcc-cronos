/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate;
import com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate;

/**
 * <p>Demonstrates the functionality of the <code>TimeTracker Contact</code> component.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTest extends BaseTestCase {

    /**
     * <p>
     * Setup the test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.setupDB();
        this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS");
        this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
        this.bind("java:comp/env/ContactDAOKey", "ContactDAO");
    }

    /**
     * <p>
     * Reset the system properties.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.unBind("java:comp/env/SpecificationNamespace");
        this.unBind("java:comp/env/AddressDAOKey");
        this.unBind("java:comp/env/AddressDAOKey");
        this.wrapDB();
        super.tearDown();
    }

    /**
     * <p>
     * Demonstrates usage of the Contact component.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testContactDemo() throws Exception {

        // Create a new ContactManagerLocalDelegate, the default namespace will be used
        ContactManagerLocalDelegate manager = new ContactManagerLocalDelegate();

        // Create a new ContactManagerLocalDelegate with namespace
        ContactManagerLocalDelegate manager2 = new ContactManagerLocalDelegate(
            "com.topcoder.timetracker.contact.ejb.ContactManagerLocalDelegate");

        // Add the contacts by contact manager, contacts is Contact[], and this action will be audited
        Contact[] contacts = createContacts();
        manager.addContacts(contacts, true);

        // or add a single contact (no auditing)
        manager.addContact(contacts[0], false);

        // get all contacts by manager
        contacts = manager.getAllContacts();

        // remove one contact, this action will be audited
        manager.removeContact(contacts[1].getId(), true);

        // change the properties of a contact
        contacts[0].setFirstName("updated name");
        contacts[0].setEmailAddress("email@updated.com");

        // associate a contact with a company
        contacts[0].setContactType(ContactType.COMPANY);
        manager.associate(contacts[0], 2, true);

        // Update the contact, this action will be audited
        manager.updateContact(contacts[0], true);

        // Create FILTER with for an email address
        Filter myFilter = ContactFilterFactory.createEmailAddressFilter("email@updated.com");

        // search with this FILTER, the result will include the contacts whose email is the same
        contacts = manager2.searchContacts(myFilter);

        // deassociate a contact with a company
        manager.deassociate(contacts[0], 2, true);

        System.out.println(contacts[0].getFirstName());
        System.out.println(contacts[0].getEmailAddress());
    }

    /**
     * <p>
     * Demonstrates usage of the Contact component's Address Manager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddressDemo() throws Exception {

        // Create a new AddressManagerLocalDelegate, the default namespace will be used
        AddressManagerLocalDelegate manager = new AddressManagerLocalDelegate();

        // Create a new AddressManagerLocalDelegate with namespace
        AddressManagerLocalDelegate manager2 = new AddressManagerLocalDelegate(
            "com.topcoder.timetracker.contact.ejb.AddressManagerLocalDelegate");

        // Add the addresses by address manager, and this action will be audited
        Address[] addresses = createAddresses();
        manager.addAddresses(addresses, true);

        // or add a single address (no auditing)
        manager.addAddress(addresses[0], false);

        // get all addresses by manager
        addresses = manager.getAllAddresses();

        // remove one address, this action will be audited
        manager.removeAddress(addresses[1].getId(), true);

        // change the properties of a address
        addresses[0].setLine1("updated address");
        addresses[0].setModificationUser("modifier");

        // associate an address with a company
        addresses[0].setAddressType(AddressType.COMPANY);
        manager.associate(addresses[0], 2, true);

        // Update the address, this action will be audited
        manager.updateAddress(addresses[0], true);

        // Create FILTER with the user
        Filter myFilter = AddressFilterFactory.createModifiedByFilter("modifier");

        // search with this FILTER, the result will include the addresses modified by the given user
        addresses = manager.searchAddresses(myFilter);
        System.out.println(addresses[0].getLine1());

        // deassociate an address with a company
        manager.deassociate(addresses[0], 2, true);

        // Get all states
        State[] states = manager2.getAllStates();
        System.out.println("States length : " + states.length);

        // Get all countries
        Country[] countries = manager2.getAllCountries();
        System.out.println("Countries length : " + countries.length);
    }

    /**
     * <p>
     * Creates an array of demo address.
     * </p>
     *
     * @return an array of sample address.
     */
    private Address[] createAddresses() {
        return new Address[] {this.getAddress(), this.getAddress()};
    }

    /**
     * <p>
     * Creates an array of demo contact.
     * </p>
     *
     * @return an array of sample contact.
     */
    private Contact[] createContacts() {
        return new Contact[] {this.getContact(), this.getContact()};
    }
}
