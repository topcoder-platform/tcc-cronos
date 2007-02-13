/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import com.topcoder.timetracker.common.Contact;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>Contact</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestContactAccuracy extends TestCase {

    /**
     * Represents the Contact instance for test.
     */
    private static Contact target = new Contact();

    /**
     * Test constructor.
     *
     */
    public void testContact() {
        assertNotNull("The Contact instance should be created.", target);
    }

    /**
     * test method getFirstName.
     *
     */
    public void testGetFirstName() {
        assertNull("Null is expected.",  target.getFirstName());
    }

    /**
     * Test method setFirstName.
     *
     */
    public void testSetFirstName() {
        target.setFirstName("firstName");

        assertEquals("Equal is expected.", "firstName", target.getFirstName());
    }

    /**
     * Test method setFirstName.
     *
     */
    public void testSetFirstName_2() {
        target.setFirstName("firstName");

        assertEquals("Equal is expected.", "firstName", target.getFirstName());

        target.setChanged(false);


        target.setFirstName("fn");

        assertTrue("True is expected.", target.isChanged());
    }

    /**
     * Test method getLastName.
     *
     */
    public void testGetLastName() {
        assertNull("Null is expected.", target.getLastName());
    }

    /**
     * Test method setLastName.
     *
     */
    public void testSetLastName() {
        target.setLastName("last");

        assertEquals("Equal is expected.", "last", target.getLastName());
    }

    /**
     * Test method setLastName.
     *
     */
    public void testSetLastName_2() {
        target.setLastName("last");

        assertEquals("Equal is expected.", "last", target.getLastName());

        target.setChanged(false);


        target.setLastName("last name");

        assertTrue("true is expected.", target.isChanged());
    }

    /**
     * test method getPhoneNumber.
     *
     */
    public void testGetPhoneNumber() {
        assertNull("Null is expected.", target.getPhoneNumber());
    }

    /**
     * Test method setPhoneNumber.
     *
     */
    public void testSetPhoneNumber() {
        target.setPhoneNumber("phone");

        assertEquals("Equal is expected.", "phone", target.getPhoneNumber());
    }

    /**
     * Test method setPhoneNumber.
     *
     */
    public void testSetPhoneNumber_2() {
        target.setPhoneNumber("phone");

        assertEquals("Equal is expected.", "phone", target.getPhoneNumber());

        target.setChanged(false);

        target.setPhoneNumber("phoneNumber");

        assertTrue("True is expected.", target.isChanged());
    }

    /**
     * test method getEmailAddress.
     *
     */
    public void testGetEmailAddress() {
        assertNull("Null is expected.", target.getEmailAddress());
    }

    /**
     * test method setEmailAddress.
     *
     */
    public void testSetEmailAddress() {
        target.setEmailAddress("email@topcoder.com");

        assertEquals("Equal is expected.", "email@topcoder.com", target.getEmailAddress());
    }

    /**
     * test method setEmailAddress.
     *
     */
    public void testSetEmailAddress_2() {
        target.setEmailAddress("email@topcoder.com");

        assertEquals("Equal is expected.", "email@topcoder.com", target.getEmailAddress());

        target.setChanged(false);

        target.setEmailAddress("email2@topcoder.com");

        assertTrue("True is expected.", target.isChanged());
    }

}
