/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.Contact;
import com.cronos.timetracker.common.EncryptionRepository;
import com.cronos.timetracker.user.AccountStatus;
import com.cronos.timetracker.user.User;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>User</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestUserAccuracy extends TestCase {

    /**
     * Represents the User instance for test.
     */
    private User test = new User();

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        EncryptionRepository.getInstance().registerAlgorithm("simple", new SimpleAlgorithm());

        test.setAlgorithmName("simple");

    }

    /**
     * Test constructor.
     *
     */
    public void testUser() {
        assertNotNull("Should not be null.", test);
    }

    /**
     * Test method getCompanyId.
     *
     */
    public void testGetCompanyId() {
        assertEquals("Equal is expected.", 0, test.getCompanyId());
    }

    /**
     * Test method setCompanyId.
     *
     */
    public void testSetCompanyId() {
        test.setCompanyId(1);

        assertEquals("Equal is expected.", 1, test.getCompanyId());
    }

    /**
     * Test method setCompanyId.
     *
     */
    public void testSetCompanyId_2() {
        test.setCompanyId(1);

        assertEquals("Equal is expected.", 1, test.getCompanyId());

        test.setChanged(false);

        test.setCompanyId(10);

        assertTrue("True is expected.", test.isChanged());
    }

    /**
     * Test method getUserName.
     *
     */
    public void testGetUsername() {
        assertNull("Should be null.", test.getUsername());
    }

    /**
     * Test method setUserName.
     *
     */
    public void testSetUsername() {
        test.setUsername("user");

        assertEquals("Equal is expected.", "user", test.getUsername());
    }

    /**
     * Test method setUserName.
     *
     */
    public void testSetUsername_2() {
        test.setUsername("user");

        assertEquals("Equal is expected.", "user", test.getUsername());

        test.setChanged(false);

        test.setUsername("u");

        assertTrue("True is expected.", test.isChanged());
    }

    /**
     * Test method getPassword.
     *
     */
    public void testGetPassword() {
        assertNull("Should be null.", test.getPassword());
    }

    /**
     * Test method setPassword.
     *
     */
    public void testSetPassword() {
        test.setPassword("psw");

        assertEquals("Equal is expected.", "psw", test.getPassword());
    }

    /**
     * Test method setPassword.
     *
     */
    public void testSetPassword_2() {
        test.setPassword("psw");

        assertEquals("Equal is expected.", "psw", test.getPassword());

        test.setChanged(false);
        test.setPassword("ps");

        assertTrue("True is expected.", test.isChanged());
    }

    /**
     * Test method getAccountStatus.
     *
     */
    public void testGetAccountStatus() {
        assertNull("Should be null.", test.getAccountStatus());
    }

    /**
     * Test method setAccountStatus.
     *
     */
    public void testSetAccountStatus() {
        AccountStatus status = new AccountStatus();

        test.setAccountStatus(status);

        assertEquals("Equal is expected.", status, test.getAccountStatus());
    }

    /**
     * Test method getContactInfo.
     *
     */
    public void testGetContactInfo() {
        assertNull("Should be null.", test.getContactInfo());
    }

    /**
     * Test method setContactInfo.
     *
     */
    public void testSetContactInfo() {
        Contact contact = new Contact();

        test.setContactInfo(contact);

        assertEquals("Equal is expected.", contact, test.getContactInfo());
    }

    /**
     * test method getAddress.
     *
     */
    public void testGetAddress() {
        assertNull("Should be null.", test.getAddress());
    }

    /**
     * Test method setAddress.
     *
     */
    public void testSetAddress() {
        Address address = new Address();
        test.setAddress(address);

        assertEquals("Equal is expected.", address, test.getAddress());
    }

    /**
     * Test method getAlgorithmName.
     *
     */
    public void testGetAlgorithmName() {
        assertEquals("Equal is expected.", "simple", test.getAlgorithmName());
    }

    /**
     * Test method setAlgorithmName.
     *
     */
    public void testSetAlgorithmName() {
        test.setChanged(false);

        test.setAlgorithmName("simple");

        assertFalse("False is expected.", test.isChanged());
    }

    /**
     * test method getFirstName.
     *
     */
    public void testGetFirstName() {
        assertNull("Null is expected.", test.getFirstName());
    }

    /**
     * Test method setFirstName.
     *
     */
    public void testSetFirstName() {
        test.setFirstName("firstName");

        assertEquals("Equal is expected.", "firstName", test.getFirstName());
    }

    /**
     * Test method getLastName.
     *
     */
    public void testGetLastName() {
        assertNull("Null is expected.", test.getLastName());
    }

    /**
     * Test method setLastName.
     *
     */
    public void testSetLastName() {
        test.setLastName("last");

        assertEquals("Equal is expected.", "last", test.getLastName());
    }

    /**
     * test method getPhoneNumber.
     *
     */
    public void testGetPhoneNumber() {
        assertNull("Null is expected.", test.getPhoneNumber());
    }

    /**
     * Test method setPhoneNumber.
     *
     */
    public void testSetPhoneNumber() {
        test.setPhoneNumber("phone");

        assertEquals("Equal is expected.", "phone", test.getPhoneNumber());
    }

    /**
     * test method getEmailAddress.
     *
     */
    public void testGetEmailAddress() {
        assertNull("Null is expected.", test.getEmailAddress());
    }

    /**
     * test method setEmailAddress.
     *
     */
    public void testSetEmailAddress() {
        test.setEmailAddress("email@topcoder.com");

        assertEquals("Equal is expected.", "email@topcoder.com", test.getEmailAddress());
    }

}
