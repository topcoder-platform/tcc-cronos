/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>UserRole</code> enum class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserRoleUnitTest extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(UserRoleUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>UserRole</code>'s enumeration definition, to verify
     * that UserRole.NO_ACCESS exist.
     * </p>
     */
    public void testNO_ACCESS() {
        assertNotNull("The UserRole.NO_ACCESS should exist.",
            UserRole.valueOf("NO_ACCESS"));
    }

    /**
     * <p>
     * Accuracy test for <code>UserRole</code>'s enumeration definition, to verify
     * that UserRole.CONTRIBUTOR exist.
     * </p>
     */
    public void testCONTRIBUTOR() {
        assertNotNull("The UserRole.CONTRIBUTOR should exist.",
            UserRole.valueOf("CONTRIBUTOR"));
    }

    /**
     * <p>
     * Accuracy test for <code>UserRole</code>'s enumeration definition, to verify
     * that UserRole.MODERATOR exist.
     * </p>
     */
    public void testMODERATOR() {
        assertNotNull("The UserRole.MODERATOR should exist.",
            UserRole.valueOf("MODERATOR"));
    }

    /**
     * <p>
     * Failure test for <code>UserRole</code>'s enumeration definition, to verify
     * that some unexpected enums don't exist.
     * </p>
     */
    public void testOTHER() {
        try {
            UserRole.valueOf("OTHER");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
}
