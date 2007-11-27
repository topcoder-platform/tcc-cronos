/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.timetracker.user.UserType;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link UserStatus}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeFailureTests extends TestCase {

    /**
     * <p>
     * Represents the UserType instance used for testing.
     * </p>
     */
    private UserType userType = new UserType();

    /**
     * <p>
     * Failure test for <code>{@link UserType#setDescription(String)}</code> method.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        try {
            userType.setDescription(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserType#setDescription(String)}</code> method.
     * </p>
     */
    public void testSetDescription_EmptyDescription() {
        try {
            userType.setDescription("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserType#setDescription(String)}</code> method.
     * </p>
     */
    public void testSetDescription_TrimmedEmptyDescription() {
        try {
            userType.setDescription("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserType#setCompanyId(long)}</code> method.
     * </p>
     */
    public void testSetCompanyId_NegativeId() {
        try {
            userType.setCompanyId(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserType#setCompanyId(long)}</code> method.
     * </p>
     */
    public void testSetCompanyId_ZeroId() {
        try {
            userType.setCompanyId(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
