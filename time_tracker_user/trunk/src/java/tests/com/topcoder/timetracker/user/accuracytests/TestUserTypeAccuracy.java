/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.timetracker.user.UserType;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>UserType </code>.
 * 
 * @author Chenhong
 * @version 3.2.1
 */
public class TestUserTypeAccuracy extends TestCase {

    /**
     * Represents the UserType instance for testing.
     */
    private UserType status = null;
    
    /**
     * Setup.
     */
    public void setUp() {
        status = new UserType();
    }
    
    /**
     * Test the ctor.
     *
     */
    public void testUserType() {
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
