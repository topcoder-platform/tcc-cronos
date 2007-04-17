/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>This test case contains accuracy tests for <code>Contact</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactTestAcc extends BaseTestCase {

    /**
     * <p>
     * Instance of <code>Contact</code> used in the test case.
     * </p>
     */
    private Contact contact = this.getContact();

    /**
     * <p>
     * Test constructor.
     * </p>
     */
    public void testCtor() {
        assertNotNull(this.contact);
    }

    /**
     * <p>
     * Test methods <code>setFirstName()</code> and <code>getFirstName()</code>.
     * </p>
     */
    public void testFirstName() {
        this.contact.setChanged(false);
        this.contact.setFirstName("new FirstName");
        assertEquals("new FirstName", contact.getFirstName());
        assertTrue(this.contact.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setLastName()</code> and <code>getLastName()</code>.
     * </p>
     */
    public void testLastName() {
        this.contact.setChanged(false);
        this.contact.setLastName("new LastName");
        assertEquals("new LastName", contact.getLastName());
        assertTrue(this.contact.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setPhoneNumber()</code> and <code>getPhoneNumber()</code>.
     * </p>
     */
    public void testPhoneNumber() {
        this.contact.setChanged(false);
        this.contact.setPhoneNumber("new PhoneNumber");
        assertEquals("new PhoneNumber", contact.getPhoneNumber());
        assertTrue(this.contact.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setEmailAddress()</code> and <code>getEmailAddress()</code>.
     * </p>
     */
    public void testEmailAddress() {
        this.contact.setChanged(false);
        this.contact.setEmailAddress("new EmailAddress");
        assertEquals("new EmailAddress", contact.getEmailAddress());
        assertTrue(this.contact.isChanged());
    }

    /**
     * <p>
     * Test methods <code>setContactType()</code> and <code>getContactType()</code>.
     * </p>
     */
    public void testContactType() {
        this.contact.setChanged(false);
        this.contact.setContactType(ContactType.CLIENT);
        assertEquals(ContactType.CLIENT, contact.getContactType());
        assertTrue(this.contact.isChanged());
    }
}
