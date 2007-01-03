/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.Contact;
import com.cronos.timetracker.common.EncryptionRepository;
import com.cronos.timetracker.company.Company;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>Company</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestCompanyAccuracy extends TestCase {

    /**
     * Represents the Company instance for test.
     */
    private static Company test = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        test = new Company();
        EncryptionRepository.getInstance().registerAlgorithm("default", new SimpleAlgorithm());
    }

    /**
     * Test constructor.
     *
     */
    public void testContact() {
        assertNotNull("The Company instance should be created.", test);
    }

    /**
     * test method getCompanyName.
     *
     */
    public void testGetCompanyName() {
        assertNull("Null is expected.", test.getCompanyName());
    }

    /**
     * Test method setCompanyName.
     *
     */
    public void testSetCompanyName() {
        test.setCompanyName("topcoder");

        assertEquals("Equal is expected.", "topcoder", test.getCompanyName());
    }

    /**
     * Test method setCompanyName.
     *
     */
    public void testSetCompanyName_2() {
        test.setCompanyName("topcoder");

        assertEquals("Equal is expected.", "topcoder", test.getCompanyName());

        test.setChanged(false);

        test.setCompanyName("tc");

        assertTrue("True is expectd.", test.isChanged());
    }

    /**
     * test method getAddress.
     *
     */
    public void testGetAddress() {
        assertNull("Null is expected.", test.getAddress());
    }

    /**
     * test method setAddress.
     *
     */
    public void testSetAddress() {
        Address address = new Address();
        test.setAddress(address);

        assertEquals("Equal is expected.", address, test.getAddress());
    }

    /**
     * test method setAddress.
     *
     */
    public void testSetAddress_2() {
        Address address = new Address();
        test.setAddress(address);

        assertEquals("Equal is expected.", address, test.getAddress());

        address = new Address();
        address.setId(1000);

        test.setChanged(false);

        test.setAddress(address);

        assertTrue("True is expected.", test.isChanged());
    }

    /**
     * Test method getContact.
     *
     */
    public void testGetContact() {
        assertNull("Null is expected.", test.getContact());
    }

    /**
     * Test method setContact.
     *
     */
    public void testSetContact() {
        Contact contact = new Contact();

        test.setContact(contact);

        assertEquals("Equal is expected.", contact, test.getContact());
    }

    /**
     * Test method setContact.
     *
     */
    public void testSetContact_2() {
        Contact contact = new Contact();

        test.setContact(contact);

        assertEquals("Equal is expected.", contact, test.getContact());

        contact = new Contact();
        contact.setId(1000);

        test.setChanged(false);

        test.setContact(contact);

        assertTrue("True is expected.", test.isChanged());
    }

    /**
     * Test method getPasscode.
     *
     */
    public void testGetPasscode() {
        assertNull("Null is expected.", test.getPasscode());
    }

    /**
     * test method setPasscode.
     *
     */
    public void testSetPasscode() {
        test.setAlgorithmName("default");

        test.setPasscode("pass");

        System.out.println(test.getPasscode());
        assertEquals("Equal is expected.", "pass", test.getPasscode());
    }

    /**
     * test method setPasscode.
     *
     */
    public void testSetPasscode_2() {
        test.setAlgorithmName("default");

        test.setPasscode("pass");

        assertEquals("Equal is expected.", "pass", test.getPasscode());

        test.setChanged(false);

        test.setPasscode("p");

        assertTrue("true is expected.", test.isChanged());
    }

    /**
     * test method getAlgorithmName.
     *
     */
    public void testGetAlgorithmName() {
        assertNull("Null is expected.", test.getAlgorithmName());
    }

    /**
     * Test method setAlgorithmName.
     *
     */
    public void testSetAlgorithmName() {
        test.setAlgorithmName("default");

        assertEquals("equal is expected.", "default", test.getAlgorithmName());
    }
}
