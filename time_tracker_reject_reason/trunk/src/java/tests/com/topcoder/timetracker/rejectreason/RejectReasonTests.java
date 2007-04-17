/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.timetracker.common.TimeTrackerBean;

import junit.framework.TestCase;


/**
 * Unit test for <code>RejectReason</code> class. Instantiation tests and basic functionalities tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonTests extends TestCase {
    /** The RejectReason instance to be tested. */
    private RejectReason reason = new RejectReason();

    /**
     * Tests constructor, verifies the RejectEmail is instantiated correctly.
     */
    public void testConstructor() {
        assertNotNull("Unable to instantiate RejectReason.", reason);
        assertTrue("RejectReason should be an instance of TimeTrackerBean.", reason instanceof TimeTrackerBean);
    }

    /**
     * Tests getCompanyId method before the company id is set. Verifies an id less than or equals to zero is returned.
     */
    public void testGetCompanyId_beforeSet() {
        assertTrue("The getCompanyId does not function correctly.", reason.getCompanyId() <= 0);
    }

    /**
     * Tests setCompanyId method with a zero id. IllegalArgumentException should be thrown.
     */
    public void testSetCompanyId_zero() {
        try {
            reason.setCompanyId(0);
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
            reason.setCompanyId(-1);
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
        reason.setCompanyId(id);
        assertEquals("Either setCompanyId or getCompanyId does not function correctly.", id, reason.getCompanyId());
    }

    /**
     * Tests getDescription method before the description is set. Verifies null is returned.
     */
    public void testGetDescription_beforeSet() {
        assertEquals("The getDescription does not function correctly.", null, reason.getDescription());
    }

    /**
     * Tests setDescription method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testSetDescription_null() {
        try {
            reason.setDescription(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests setDescription method with an empty string. IllegalArgumentException should be thrown.
     */
    public void testSetDescription_empty() {
        try {
            reason.setDescription(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests setDescription and getDescription methods accuracy.
     */
    public void testSetAndGetDescription_accuracy() {
        String description = "this is a description";
        reason.setDescription(description);
        assertEquals("Either setDescription or getDescription does not function correctly.", description,
            reason.getDescription());
    }

    /**
     * Tests getActive method before the active status is set. Verifies false is returned.
     */
    public void testGetActive_beforeSet() {
        assertEquals("The getActive does not function correctly.", false, reason.getActive());
    }

    /**
     * Tests setActive and getActive methods accuracy.
     */
    public void testSetAndGetActive_accuracy() {
        reason.setActive(true);
        assertEquals("Either setActive or getActive does not function correctly.", true, reason.getActive());
        reason.setActive(false);
        assertEquals("Either setActive or getActive does not function correctly.", false, reason.getActive());
    }

    /**
     * Tests isActive method accuracy.
     */
    public void testIsActive_accuracy() {
        reason.setActive(true);
        assertEquals("The method isActive does not function correctly.", true, reason.isActive());
        reason.setActive(false);
        assertEquals("The method isActive does not function correctly.", false, reason.isActive());
    }
}
