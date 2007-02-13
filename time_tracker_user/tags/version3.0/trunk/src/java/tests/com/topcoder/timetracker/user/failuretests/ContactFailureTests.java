/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.Contact;

/**
 * <p>
 * Failure unit test cases for the Contact class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class ContactFailureTests extends TestCase {

    /**
     * <p>
     * The Contact instance used for testing.
     * </p>
     */
    private Contact contact = null;

    /**
     * <p>
     * Creates Contact instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        contact = new Contact();
    }

    /**
     * <p>
     * Tests setEmailAddress(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetEmailAddress1() {
        try {
            contact.setEmailAddress(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setFirstName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetFirstName1() {
        try {
            contact.setFirstName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setLastName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetLastName1() {
        try {
            contact.setLastName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setPhoneNumber(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetPhoneNumber1() {
        try {
            contact.setPhoneNumber(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
