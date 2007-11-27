/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.timetracker.user.UserStatus;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>UserStatus </code>.
 * 
 * @author Chenhong
 * @version 3.2.1
 */
public class TestUserStatusAccuracy extends TestCase {

    /**
     * Represents the UserStatus instance for testing.
     */
    private UserStatus status = null;
    
    /**
     * Setup.
     */
    public void setUp() {
        status = new UserStatus();
    }
    
    /**
     * Test the ctor.
     *
     */
    public void testUserStatus() {
        assertNotNull("Should not be null.", status);
    }

    /**
     * Test getDescription
     *
     */
    public void testGetDescription() {
        assertNull("Should be null.", status.getDescription());
    }

    /**
     * Test setDescription(String description)
     *
     */
    public void testSetDescription() {
        status.setDescription("d");
        assertEquals("Equal is expected.", "d", status.getDescription());
        assertTrue(status.isChanged());
    }

    /**
     * Test isActive.
     *
     */
    public void testIsActive() {
        assertFalse(status.isActive());
    }

    /**
     * Test setActive.
     *
     */
    public void testSetActive() {
        status.setActive(true);
        assertTrue(status.isActive());
        assertTrue(status.isChanged());
    }

    /**
     * Test long getCompanyId()
     *
     */
    public void testGetCompanyId() {
        assertEquals(0, status.getCompanyId());
    }

    /**
     * Test setCompanyId(long companyId)
     *
     */
    public void testSetCompanyId() {
        status.setCompanyId(10);
        assertEquals(10, status.getCompanyId());
    }

}
