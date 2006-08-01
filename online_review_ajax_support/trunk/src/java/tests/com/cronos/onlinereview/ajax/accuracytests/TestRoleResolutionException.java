/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.cronos.onlinereview.ajax.handlers.RoleResolutionException;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;


/**
 * Tests for RoleResolutionException class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestRoleResolutionException extends TestCase {
    /**
     * Tests RoleResolutionException() method with accuracy state.
     */
    public void testRoleResolutionException1Accuracy() {
        RoleResolutionException ce = new RoleResolutionException();
        assertNotNull("creting RoleResolutionException fails.", ce);
        assertTrue(ce instanceof BaseException);
    }

    /**
     * Tests RoleResolutionException(String message) method with accuracy state.
     */
    public void testRoleResolutionException2Accuracy() {
        RoleResolutionException ce = new RoleResolutionException("msg");
        assertNotNull("creting RoleResolutionException fails.", ce);
        assertTrue("creting RoleResolutionException fails.", ce instanceof BaseException);
        assertEquals("creting RoleResolutionException fails.", "msg", ce.getMessage());
    }

    /**
     * Tests RoleResolutionException(Throwable cause) method with accuracy state.
     */
    public void testRoleResolutionException3Accuracy() {
        Exception e = new IllegalArgumentException("msg");
        RoleResolutionException ce = new RoleResolutionException(e);
        assertNotNull("creting RoleResolutionException fails.", ce);
        assertTrue("creting RoleResolutionException fails.", ce instanceof BaseException);
        assertEquals("creting RoleResolutionException fails.", e, ce.getCause());
    }

    /**
     * Tests RoleResolutionException(String message, Throwable cause) method with accuracy state.
     */
    public void testRoleResolutionException4Accuracy() {
        Exception e = new IllegalArgumentException("msg2");
        RoleResolutionException ce = new RoleResolutionException("msg", e);
        assertNotNull("creting RoleResolutionException fails.", ce);
        assertTrue("creting RoleResolutionException fails.", ce instanceof BaseException);
        assertEquals("creting RoleResolutionException fails.", "msg" + ", caused by " + "msg2",
            ce.getMessage());
        assertEquals("creting RoleResolutionException fails.", e, ce.getCause());
    }
}
