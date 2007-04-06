/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.User;

/**
 * <p>
 * Accuracy Unit test cases for User.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class UserAccuracyTests extends TestCase {
    /**
     * <p>
     * User instance for testing.
     * </p>
     */
    private User instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new User();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor User#User() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create User instance.", instance);
    }

    /**
     * <p>
     * Tests User#setPassword(String) for accuracy.
     * </p>
     */
    public void testSetPassword() {
        instance.setPassword("password");
        assertEquals("Failed to set the password.", "password", instance.getPassword());
    }

    /**
     * <p>
     * Tests User#getUsername() for accuracy.
     * </p>
     */
    public void testGetUsername() {
        instance.setUsername("name");
        assertEquals("Failed to get the user name.", "name", instance.getUsername());
    }

    /**
     * <p>
     * Tests User#setUsername(String) for accuracy.
     * </p>
     */
    public void testSetUsername() {
        instance.setUsername("name");
        assertEquals("Failed to set the user name.", "name", instance.getUsername());
    }

    /**
     * <p>
     * Tests User#getCompanyId() for accuracy.
     * </p>
     */
    public void testGetCompanyId() {
        instance.setCompanyId(1);
        assertEquals("Failed to get the company id.", 1, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests User#setCompanyId(long) for accuracy.
     * </p>
     */
    public void testSetCompanyId() {
        instance.setCompanyId(1);
        assertEquals("Failed to set the company id.", 1, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests User#getContact() for accuracy.
     * </p>
     */
    public void testGetContact() {
        Contact contact = new Contact();
        instance.setContact(contact);
        assertSame("Failed to get the contact.", contact, instance.getContact());
    }

    /**
     * <p>
     * Tests User#setContact(Contact) for accuracy.
     * </p>
     */
    public void testSetContact() {
        Contact contact = new Contact();
        instance.setContact(contact);
        assertSame("Failed to set the contact.", contact, instance.getContact());
    }

    /**
     * <p>
     * Tests User#getAlgorithmName() for accuracy.
     * </p>
     */
    public void testGetAlgorithmName() {
        instance.setAlgorithmName("algorithmName");
        assertEquals("Failed to get the algorithm name.", "algorithmName", instance.getAlgorithmName());
    }

    /**
     * <p>
     * Tests User#setAlgorithmName(String) for accuracy.
     * </p>
     */
    public void testSetAlgorithmName() {
        instance.setAlgorithmName("algorithmName");
        assertEquals("Failed to set the algorithm name.", "algorithmName", instance.getAlgorithmName());
    }

    /**
     * <p>
     * Tests User#getAddress() for accuracy.
     * </p>
     */
    public void testGetAddress() {
        Address address = new Address();
        instance.setAddress(address);
        assertSame("Failed to get the address.", address, instance.getAddress());
    }

    /**
     * <p>
     * Tests User#getPassword() for accuracy.
     * </p>
     */
    public void testGetPassword() {
        instance.setPassword("password");
        assertEquals("Failed to set the password.", "password", instance.getPassword());
    }

    /**
     * <p>
     * Tests User#getStatus() for accuracy.
     * </p>
     */
    public void testGetStatus() {
        instance.setStatus(Status.LOCKED);
        assertEquals("Failed to get the status.", Status.LOCKED, instance.getStatus());
    }

    /**
     * <p>
     * Tests User#setStatus(Status) for accuracy.
     * </p>
     */
    public void testSetStatus() {
        instance.setStatus(Status.LOCKED);
        assertEquals("Failed to set the status.", Status.LOCKED, instance.getStatus());
    }

    /**
     * <p>
     * Tests User#setAddress(Address) for accuracy.
     * </p>
     */
    public void testSetAddress() {
        Address address = new Address();
        instance.setAddress(address);
        assertSame("Failed to set the address.", address, instance.getAddress());
    }

}