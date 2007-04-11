/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>CategoryValidationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class CategoryValidationExceptionTests extends TestCase {
    /**
     * Tests that <code>CategoryValidationException</code> inherits from <code>RoleCategoryPersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("CategoryValidationException should inherit from RoleCategoryPersistenceException",
                   RoleCategoryPersistenceException.class.isAssignableFrom(CategoryValidationException.class));
    }

    /**
     * Tests the first constructor and the <code>getMessage</code> method.
     */
    public void test_ctor1() {
        assertEquals("getMessage() should return the message passed to the constructor", "message",
                     new CategoryValidationException("message").getMessage());
    }
}
