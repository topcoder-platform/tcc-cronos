/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.database.statustracker.persistence.StatusTrackerPersistenceException;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>EntityStatusValidationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class EntityStatusValidationExceptionTests extends TestCase {
    /**
     * Tests that <code>EntityStatusValidationException</code> inherits from
     * <code>StatusTrackerPersistenceException</code>.
     */
    public void test_inheritance() {
        assertTrue("EntityStatusValidationException should inherit from StatusTrackerPersistenceException",
                   StatusTrackerPersistenceException.class.isAssignableFrom(EntityStatusValidationException.class));
    }

    /**
     * Tests the first constructor and the <code>getMessage</code> method.
     */
    public void test_ctor1() {
        assertEquals("getMessage() should return the message passed to the constructor", "message",
                     new EntityStatusValidationException("message").getMessage());
    }
}
