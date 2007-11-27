/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.timetracker.user.UserStatus;

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
public class UserStatusFailureTests extends TestCase {

    /**
     * <p>
     * Represents the UserStatus instance used for testing.
     * </p>
     */
    private UserStatus userStatus = new UserStatus();

    /**
     * <p>
     * Failure test for <code>{@link UserStatus#setDescription(String)}</code> method.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        try {
            userStatus.setDescription(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatus#setDescription(String)}</code> method.
     * </p>
     */
    public void testSetDescription_EmptyDescription() {
        try {
            userStatus.setDescription("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatus#setDescription(String)}</code> method.
     * </p>
     */
    public void testSetDescription_TrimmedEmptyDescription() {
        try {
            userStatus.setDescription("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatus#setCompanyId(long)}</code> method.
     * </p>
     */
    public void testSetCompanyId_NegativeId() {
        try {
            userStatus.setCompanyId(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatus#setCompanyId(long)}</code> method.
     * </p>
     */
    public void testSetCompanyId_ZeroId() {
        try {
            userStatus.setCompanyId(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
