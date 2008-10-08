/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import junit.framework.TestCase;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.webservices.usermapping.impl.UserProjectMapping;

/**
 * This class contains unit tests for <code>UserProjectMapping</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestUserProjectMapping extends TestCase {
    /**
     * Represents the <code>UserProjectMapping</code> instance for test.
     */
    private UserProjectMapping mapping = null;

    /**
     * Set Up the test environment before testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        mapping = new UserProjectMapping();
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
     * Function test : Tests <code>UserProjectMapping()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testUserProjectMappingAccuracy() throws Exception {
        assertNotNull("Should not be null.", mapping);
        assertTrue("should be type of AuditableEntity.", mapping instanceof AuditableEntity);
    }

    /**
     * Function test : Tests <code>getProjectId()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetProjectIdAccuracy() throws Exception {
        assertEquals("Should be 0.", 0, mapping.getProjectId());
    }

    /**
     * Function test : Tests <code>setProjectId(long projectId)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetProjectIdAccuracy() throws Exception {
        mapping.setProjectId(121);
        assertEquals("Should be 121.", 121, mapping.getProjectId());
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
        mapping.setUserId(1211);
        assertEquals("Should be 1211.", 1211, mapping.getUserId());
    }

}