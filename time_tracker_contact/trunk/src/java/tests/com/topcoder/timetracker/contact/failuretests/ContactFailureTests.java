/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.timetracker.contact.Contact;

import junit.framework.TestCase;


/**
 * <p>Failure test cases for the class Contact.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class ContactFailureTests extends TestCase {
    /** Contact instance to test against. */
    private Contact contact = null;

    /**
     * Create instances.
     */
    protected void setUp() throws Exception {
        contact = new Contact();
    }

    /**
     * <p>
     * Test method <code>setFirstName()</code>. Given string is null, IAE expected.
     * </p>
     */
    public void testSetFirstName_NullString() {
        try {
            contact.setFirstName(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setFirstName()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetFirstName_EmptyString() {
        try {
            contact.setFirstName(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setLastName()</code>. Given string is null, IAE expected.
     * </p>
     */
    public void testSetLastName_NullString() {
        try {
            contact.setLastName(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setLastName()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetLastName_EmptyString() {
        try {
            contact.setLastName(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setPhoneNumber()</code>. Given string is null, IAE expected.
     * </p>
     */
    public void testSetPhoneNumber_NullString() {
        try {
            contact.setPhoneNumber(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setPhoneNumber()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetPhoneNumber_EmptyString() {
        try {
            contact.setPhoneNumber(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setEmailAddress()</code>. Given string is null, IAE expected.
     * </p>
     */
    public void testSetEmailAddress_NullString() {
        try {
            contact.setEmailAddress(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setEmailAddress()</code>. Given string is empty, IAE expected.
     * </p>
     */
    public void testSetEmailAddress_EmptyString() {
        try {
            contact.setEmailAddress(" ");
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>setContactType()</code>. Given contact type is null, IAE expected.
     * </p>
     */
    public void testSetContactType() {
        try {
            contact.setContactType(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}
