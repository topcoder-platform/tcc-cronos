/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.filterfactory.UserStatusFilterFactory;

/**
 * Accuracy test cases for class <code>UserStatusFilterFactory </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserStatusFilterFactoryAccuracyTests extends TestCase {

    /**
     * Test createCompanyIdFilter(long companyId).
     */
    public void testCreateCompanyIdFilter() {
        assertNotNull(UserStatusFilterFactory.createCompanyIdFilter(3));
    }

    /**
     * Test {@link UserStatusFilterFactory#createDescriptionFilter(String)}.
     */
    public void testCreateDescriptionFilter() {
        assertNotNull(UserStatusFilterFactory.createDescriptionFilter("description"));
    }

    /**
     * Test {@link UserStatusFilterFactory#createActiveFilter(boolean)}.
     */
    public void testCreateActiveFilter() {
        assertNotNull(UserStatusFilterFactory.createActiveFilter(false));
    }

    /**
     * Test {@link UserStatusFilterFactory#createCreationDateFilter(Date, Date)}.
     */
    public void testCreationDateFilter() {
        Date start = new Date(2007, 1, 1);
        Date end = new Date(2007, 10, 1);
        assertNotNull(UserStatusFilterFactory.createCreationDateFilter(start, end));
    }

    /**
     * Test {@link UserStatusFilterFactory#createModificationDateFilter(Date, Date)}.
     */
    public void testCreateModificationDateFilter() {
        Date start = new Date(2007, 1, 1);
        Date end = new Date(2007, 10, 1);
        assertNotNull(UserStatusFilterFactory.createModificationDateFilter(start, end));
    }

    /**
     * Test {@link UserStatusFilterFactory#createCreationUserFilter(String)}.
     */
    public void testCreateCreationUserFilter() {
        assertNotNull(UserStatusFilterFactory.createCreationUserFilter("username"));
    }

    /**
     * Test {@link UserStatusFilterFactory#createModificationUserFilter(String)}.
     */
    public void testCreateModificationUserFilter() {
        assertNotNull(UserStatusFilterFactory.createModificationUserFilter("username"));
    }
}
