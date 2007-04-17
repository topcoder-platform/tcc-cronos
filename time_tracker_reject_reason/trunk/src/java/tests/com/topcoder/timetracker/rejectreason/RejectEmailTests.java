/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.timetracker.common.TimeTrackerBean;

import junit.framework.TestCase;


/**
 * Unit test for <code>RejectEmail</code> class. Instantiation tests and basic functionalities tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailTests extends TestCase {
    /** The RejectEmail instance to be tested. */
    private RejectEmail email = new RejectEmail();

    /**
     * Tests constructor, verifies the RejectEmail is instantiated correctly.
     */
    public void testConstructor() {
        assertNotNull("Unable to instantiate RejectEmail.", email);
        assertTrue("RejectEmail should be an instance of TimeTrackerBean.", email instanceof TimeTrackerBean);
    }

    /**
     * Tests getCompanyId method before the company id is set. Verifies an id less than or equals to zero is returned.
     */
    public void testGetCompanyId_beforeSet() {
        assertTrue("The getCompanyId does not function correctly.", email.getCompanyId() <= 0);
    }

    /**
     * Tests setCompanyId method with a zero id. IllegalArgumentException should be thrown.
     */
    public void testSetCompanyId_zero() {
        try {
            email.setCompanyId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests setCompanyId method with a negative id. IllegalArgumentException should be thrown.
     */
    public void testSetCompanyId_negative() {
        try {
            email.setCompanyId(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests setCompanyId and getCompanyId methods accuracy.
     */
    public void testSetAndGetCompanyId_accuracy() {
        int id = 89;
        email.setCompanyId(id);
        assertEquals("Either setCompanyId or getCompanyId does not function correctly.", id, email.getCompanyId());
    }

    /**
     * Tests getBody method before the body is set. Verifies null is returned.
     */
    public void testGetBody_beforeSet() {
        assertEquals("The getBody does not function correctly.", null, email.getBody());
    }

    /**
     * Tests setBody method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testSetBody_null() {
        try {
            email.setBody(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests setBody method with an empty string. IllegalArgumentException should be thrown.
     */
    public void testSetBody_empty() {
        try {
            email.setBody(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests setBody and getBody methods accuracy.
     */
    public void testSetAndGetBody_accuracy() {
        String body = "this is a body";
        email.setBody(body);
        assertEquals("Either setBody or getBody does not function correctly.", body, email.getBody());
    }
}
