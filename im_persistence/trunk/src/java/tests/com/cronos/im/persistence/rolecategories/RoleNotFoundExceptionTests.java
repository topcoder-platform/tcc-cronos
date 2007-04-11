/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>RoleNotFoundException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class RoleNotFoundExceptionTests extends TestCase {
    /**
     * Tests that <code>RoleNotFoundException</code> inherits from <code>RoleCategoryPersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("RoleNotFoundException should inherit from RoleCategoryPersistenceException",
                   RoleCategoryPersistenceException.class.isAssignableFrom(RoleNotFoundException.class));
    }

    /**
     * Tests the first constructor and the <code>getMessage</code> method.
     */
    public void test_ctor1() {
        assertEquals("getMessage() should return the message passed to the constructor", "message",
                     new RoleNotFoundException("message").getMessage());
    }
}
