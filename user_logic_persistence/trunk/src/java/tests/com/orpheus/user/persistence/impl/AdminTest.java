/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the Admin class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class AdminTest extends TestCase {

    /**
     * <p>
     * Tests the Admin(long id) constructor with a valid positive argument. The
     * newly created instance should not be null. The return value of the
     * getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidPositiveArg() {
        long id = 85837850;
        Admin admin = new Admin(id);
        assertNotNull("The Admin instance should not be null", admin);
        assertEquals("The ID is incorrect", id, admin.getId());
    }

    /**
     * <p>
     * Tests the Admin(long id) constructor with a valid positive argument. The
     * newly created instance should not be null. The return value of the
     * getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidNegativeArg() {
        long id = -85837850;
        Admin admin = new Admin(id);
        assertNotNull("The Admin instance should not be null", admin);
        assertEquals("The ID is incorrect", id, admin.getId());
    }

    /**
     * <p>
     * Tests that Admin is a subclass of User.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Admin should be a subclass of User", new Admin(0) instanceof User);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(AdminTest.class);
    }

}
