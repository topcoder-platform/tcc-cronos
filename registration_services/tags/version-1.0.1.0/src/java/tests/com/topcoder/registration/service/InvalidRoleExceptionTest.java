/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>InvalidRoleException</code>.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class InvalidRoleExceptionTest extends TestCase {

    /**
     * <p>
     * Test for constructor.
     * </p>
     * <p>
     * Tests it for accuracy, a non-null instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        InvalidRoleException ex = new InvalidRoleException("msg", 1);

        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Message mismatched.", "msg", ex.getMessage());
        assertEquals("Role id mismatched.", 1, ex.getRoleId());
    }

    /**
     * <p>
     * Test for <code>getRoleId</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, roleId=1 should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRoleID() throws Exception {
        InvalidRoleException ex = new InvalidRoleException("msg", 1);

        assertEquals("Role id mismatched.", 1, ex.getRoleId());
    }
}
