/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.util.Date;

import com.topcoder.timetracker.user.filterfactory.UserStatusFilterFactory;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link UserStatusFilterFactory}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatusFilterFactoryFailureTests extends TestCase {

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createCompanyIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateCompanyIdFilter_ZeroId() {
        try {
            UserStatusFilterFactory.createCompanyIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createCompanyIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateCompanyIdFilter_NegativeId() {
        try {
            UserStatusFilterFactory.createCompanyIdFilter(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createDescriptionFilter(String)}</code> method.
     * </p>
     */
    public void testCreateDescriptionFilter_NullDescription() {
        try {
            UserStatusFilterFactory.createDescriptionFilter(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createDescriptionFilter(String)}</code> method.
     * </p>
     */
    public void testCreateDescriptionFilter_EmptyDescription() {
        try {
            UserStatusFilterFactory.createDescriptionFilter("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createDescriptionFilter(String)}</code> method.
     * </p>
     */
    public void testCreateDescriptionFilter_TrimmedEmptyDescription() {
        try {
            UserStatusFilterFactory.createDescriptionFilter(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createCreationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateCreationUserFilter_NullCreationUser() {
        try {
            UserStatusFilterFactory.createCreationUserFilter(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createCreationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateCreationUserFilter_EmptyCreationUser() {
        try {
            UserStatusFilterFactory.createCreationUserFilter("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createCreationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateCreationUserFilter_TrimmedEmptyCreationUser() {
        try {
            UserStatusFilterFactory.createCreationUserFilter(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createModificationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_NullModificationUser() {
        try {
            UserStatusFilterFactory.createModificationUserFilter(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createModificationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_EmptyModificationUser() {
        try {
            UserStatusFilterFactory.createModificationUserFilter("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createModificationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_TrimmedEmptyModificationUser() {
        try {
            UserStatusFilterFactory.createModificationUserFilter(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_BothNull() {
        try {
            UserStatusFilterFactory.createCreationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_EndDateBeforeStartDate() {
        try {
            UserStatusFilterFactory.createCreationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                    .currentTimeMillis() - 1000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_BothNull() {
        try {
            UserStatusFilterFactory.createModificationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserStatusFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_EndDateBeforeStartDate() {
        try {
            UserStatusFilterFactory.createModificationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                    .currentTimeMillis() - 10000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
