/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>RoleCategoryPersistenceException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class RoleCategoryPersistenceExceptionTests extends TestCase {
    /**
     * Tests that <code>RoleCategoryPersistenceException</code> inherits from <code>BaseException</code>.
     */
    public void test_inheritance() {
        assertTrue("RoleCategoryPersistenceException should inherit from BaseException",
                   BaseException.class.isAssignableFrom(RoleCategoryPersistenceException.class));
    }

    /**
     * Tests the first constructor and the <code>getMessage</code> and <code>getCause</code> methods.
     */
    public void test_ctor1() {
        Throwable ex = new RuntimeException();
        RoleCategoryPersistenceException exception = new RoleCategoryPersistenceException("message", ex);
        assertTrue("getMessage() should contain the message passed to the constructor",
                   exception.getMessage().indexOf("message") > -1);
        assertEquals("getCause() should return the cause passed to the constructor", ex, exception.getCause());
    }

    /**
     * Tests the second constructor and the <code>getMessage</code> method.
     */
    public void test_ctor2() {
        assertEquals("getMessage() should return the message passed to the constructor", "message",
                     new RoleCategoryPersistenceException("message").getMessage());
    }
}
