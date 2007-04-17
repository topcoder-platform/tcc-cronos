/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.contact.ContactType;

/**
 * <p>
 * Accuracy Unit test cases for Contact.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ContactAccuracyTests extends TestCase {
    /**
     * <p>
     * Contact instance for testing.
     * </p>
     */
    private Contact instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new Contact();
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
        return new TestSuite(ContactAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor Contact#Contact() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create Contact instance.", instance);
    }

    /**
     * <p>
     * Tests Contact#getFirstName() for accuracy.
     * </p>
     */
    public void testGetFirstName() {
        instance.setFirstName("firstName");
        assertEquals("Failed to get the first name.", "firstName", instance.getFirstName());
    }

    /**
     * <p>
     * Tests Contact#setFirstName(String) for accuracy.
     * </p>
     */
    public void testSetFirstName() {
        instance.setFirstName("firstName");
        assertEquals("Failed to set the first name.", "firstName", instance.getFirstName());
    }

    /**
     * <p>
     * Tests Contact#getLastName() for accuracy.
     * </p>
     */
    public void testGetLastName() {
        instance.setLastName("lastName");
        assertEquals("Failed to get the last name.", "lastName", instance.getLastName());
    }

    /**
     * <p>
     * Tests Contact#setLastName(String) for accuracy.
     * </p>
     */
    public void testSetLastName() {
        instance.setLastName("lastName");
        assertEquals("Failed to set the last name.", "lastName", instance.getLastName());
    }

    /**
     * <p>
     * Tests Contact#getPhoneNumber() for accuracy.
     * </p>
     */
    public void testGetPhoneNumber() {
        instance.setPhoneNumber("phoneNumber");
        assertEquals("Failed to get the phone number.", "phoneNumber", instance.getPhoneNumber());
    }

    /**
     * <p>
     * Tests Contact#setPhoneNumber(String) for accuracy.
     * </p>
     */
    public void testSetPhoneNumber() {
        instance.setPhoneNumber("phoneNumber");
        assertEquals("Failed to set the phone number.", "phoneNumber", instance.getPhoneNumber());
    }

    /**
     * <p>
     * Tests Contact#getEmailAddress() for accuracy.
     * </p>
     */
    public void testGetEmailAddress() {
        instance.setEmailAddress("emailAddress");
        assertEquals("Failed to get the email address.", "emailAddress", instance.getEmailAddress());
    }

    /**
     * <p>
     * Tests Contact#setEmailAddress(String) for accuracy.
     * </p>
     */
    public void testSetEmailAddress() {
        instance.setEmailAddress("emailAddress");
        assertEquals("Failed to set the email address.", "emailAddress", instance.getEmailAddress());
    }

    /**
     * <p>
     * Tests Contact#getContactType() for accuracy.
     * </p>
     */
    public void testGetContactType() {
        instance.setContactType(ContactType.CLIENT);
        assertEquals("Failed to get the contact type.", ContactType.CLIENT, instance.getContactType());
    }

    /**
     * <p>
     * Tests Contact#setContactType(ContactType) for accuracy.
     * </p>
     */
    public void testSetContactType() {
        instance.setContactType(ContactType.CLIENT);
        assertEquals("Failed to set the contact type.", ContactType.CLIENT, instance.getContactType());
    }

}