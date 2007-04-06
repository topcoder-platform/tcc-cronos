/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.timetracker.user.User;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link User}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UserFailureTests extends TestCase {
    /**
     * <p>
     * Represents the User instance used in testing.
     * </p>
     */
    private User user = new User();

    /**
     * <p>
     * Failure test for <code>{@link User#setPassword(String)}</code> method.
     * </p>
     */
    public void testSetPassword_NullPassword() {
        try {
            user.setPassword(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setPassword(String)}</code> method.
     * </p>
     */
    public void testSetPassword_EmptyPassword() {
        try {
            user.setPassword("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setPassword(String)}</code> method.
     * </p>
     */
    public void testSetPassword_TrimmedEmptyPassword() {
        try {
            user.setPassword(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setUsername(String)}</code> method.
     * </p>
     */
    public void testSetUsername_NullUsername() {
        try {
            user.setUsername(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setUsername(String)}</code> method.
     * </p>
     */
    public void testSetUsername_EmptyUsername() {
        try {
            user.setUsername("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setUsername(String)}</code> method.
     * </p>
     */
    public void testSetUsername_TrimmedEmptyUsername() {
        try {
            user.setUsername("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setCompanyId(long)}</code> method.
     * </p>
     */
    public void testSetCompanyId_NegativeId() {
        try {
            user.setCompanyId(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setCompanyId(long)}</code> method.
     * </p>
     */
    public void testSetCompanyId_ZeroId() {
        try {
            user.setCompanyId(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setContact(com.topcoder.timetracker.contact.Contact)}</code> method.
     * </p>
     */
    public void testSetContact_NullContact() {
        try {
            user.setContact(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setAddress(com.topcoder.timetracker.contact.Address)}</code> method.
     * </p>
     */
    public void testSetAddress_NullAddress() {
        try {
            user.setAddress(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setStatus(com.topcoder.timetracker.user.Status)}</code> method.
     * </p>
     */
    public void testSetStatus_NullStatus() {
        try {
            user.setStatus(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setAlgorithmName(String)}</code> method.
     * </p>
     */
    public void testSetAlgorithmName_EmptyAlgorithmName() {
        try {
            user.setAlgorithmName("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link User#setAlgorithmName(String)}</code> method.
     * </p>
     */
    public void testSetAlgorithmName_TrimmedEmptyAlgorithmName() {
        try {
            user.setAlgorithmName("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
