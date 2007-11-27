/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.util.Date;

import com.topcoder.timetracker.user.filterfactory.UserTypeFilterFactory;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link UserTypeFilterFactory}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeFilterFactoryFailureTests extends TestCase {

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createCompanyIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateCompanyIdFilter_ZeroId() {
        try {
            UserTypeFilterFactory.createCompanyIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createCompanyIdFilter(long)}</code> method.
     * </p>
     */
    public void testCreateCompanyIdFilter_NegativeId() {
        try {
            UserTypeFilterFactory.createCompanyIdFilter(-1);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createDescriptionFilter(String)}</code> method.
     * </p>
     */
    public void testCreateDescriptionFilter_NullDescription() {
        try {
            UserTypeFilterFactory.createDescriptionFilter(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createDescriptionFilter(String)}</code> method.
     * </p>
     */
    public void testCreateDescriptionFilter_EmptyDescription() {
        try {
            UserTypeFilterFactory.createDescriptionFilter("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createDescriptionFilter(String)}</code> method.
     * </p>
     */
    public void testCreateDescriptionFilter_TrimmedEmptyDescription() {
        try {
            UserTypeFilterFactory.createDescriptionFilter(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createCreationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateCreationUserFilter_NullCreationUser() {
        try {
            UserTypeFilterFactory.createCreationUserFilter(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createCreationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateCreationUserFilter_EmptyCreationUser() {
        try {
            UserTypeFilterFactory.createCreationUserFilter("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createCreationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateCreationUserFilter_TrimmedEmptyCreationUser() {
        try {
            UserTypeFilterFactory.createCreationUserFilter(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createModificationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_NullModificationUser() {
        try {
            UserTypeFilterFactory.createModificationUserFilter(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createModificationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_EmptyModificationUser() {
        try {
            UserTypeFilterFactory.createModificationUserFilter("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createModificationUserFilter(String)}</code> method.
     * </p>
     */
    public void testCreateModificationUserFilter_TrimmedEmptyModificationUser() {
        try {
            UserTypeFilterFactory.createModificationUserFilter(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_BothNull() {
        try {
            UserTypeFilterFactory.createCreationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createCreationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateCreationDateFilter_EndDateBeforeStartDate() {
        try {
            UserTypeFilterFactory.createCreationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                    .currentTimeMillis() - 1000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_BothNull() {
        try {
            UserTypeFilterFactory.createModificationDateFilter(null, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link UserTypeFilterFactory#createModificationDateFilter(Date, Date)}</code> method.
     * </p>
     */
    public void testCreateModificationDateFilter_EndDateBeforeStartDate() {
        try {
            UserTypeFilterFactory.createModificationDateFilter(new Date(System.currentTimeMillis()), new Date(System
                    .currentTimeMillis() - 1000));
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
