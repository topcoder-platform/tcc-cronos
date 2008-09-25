/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import junit.framework.TestCase;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.webservices.usermapping.impl.UserClientMapping;

/**
 * This class contains unit tests for <code>UserClientMapping</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestUserClientMapping extends TestCase {
    /**
     * Represents the <code>UserClientMapping</code> instance for test.
     */
    private UserClientMapping mapping = null;

    /**
     * Set Up the test environment before testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        mapping = new UserClientMapping();
    }

    /**
     * Clean up the test environment after testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        mapping = null;
    }

    /**
     * Function test : Tests <code>UserClientMapping()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testUserClientMappingAccuracy() throws Exception {
        assertNotNull("Should not be null..", mapping);
        assertTrue("should be type of AuditableEntity.", mapping instanceof AuditableEntity);
    }

    /**
     * Function test : Tests <code>getClientId()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetClientIdAccuracy() throws Exception {
        assertEquals("Should be 0.", 0, mapping.getClientId());
    }

    /**
     * Function test : Tests <code>setClientId(long clientId)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetClientIdAccuracy() throws Exception {
        mapping.setClientId(12);
        assertEquals("Should be 12.", 12, mapping.getClientId());
    }

    /**
     * Function test : Tests <code>getUserId()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetUserIdAccuracy() throws Exception {
        assertEquals("Should be 0.", 0, mapping.getUserId());
    }

    /**
     * Function test : Tests <code>setUserId(long userId)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetUserIdAccuracy() throws Exception {
        mapping.setUserId(121);
        assertEquals("Should be 121.", 121, mapping.getUserId());
    }

}