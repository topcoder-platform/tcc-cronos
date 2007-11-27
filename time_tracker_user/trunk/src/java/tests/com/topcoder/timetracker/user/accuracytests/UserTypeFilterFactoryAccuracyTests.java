/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.filterfactory.UserTypeFilterFactory;

/**
 * Accuracy test cases for class <code>UserTypeFilterFactory </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserTypeFilterFactoryAccuracyTests extends TestCase {

    /**
     * Test createCompanyIdFilter(long companyId).
     */
    public void testCreateCompanyIdFilter() {
        assertNotNull(UserTypeFilterFactory.createCompanyIdFilter(3));
    }

    /**
     * Test {@link UserTypeFilterFactory#createDescriptionFilter(String)}.
     */
    public void testCreateDescriptionFilter() {
        assertNotNull(UserTypeFilterFactory.createDescriptionFilter("description"));
    }

    /**
     * Test {@link UserTypeFilterFactory#createActiveFilter(boolean)}.
     */
    public void testCreateActiveFilter() {
        assertNotNull(UserTypeFilterFactory.createActiveFilter(false));
    }

    /**
     * Test {@link UserTypeFilterFactory#createCreationDateFilter(Date, Date)}.
     */
    public void testCreationDateFilter() {
        Date start = new Date(2007, 1, 1);
        Date end = new Date(2007, 10, 1);
        assertNotNull(UserTypeFilterFactory.createCreationDateFilter(start, end));
    }

    /**
     * Test {@link UserTypeFilterFactory#createModificationDateFilter(Date, Date)}.
     */
    public void testCreateModificationDateFilter() {
        Date start = new Date(2007, 1, 1);
        Date end = new Date(2007, 10, 1);
        assertNotNull(UserTypeFilterFactory.createModificationDateFilter(start, end));
    }

    /**
     * Test {@link UserTypeFilterFactory#createCreationUserFilter(String)}.
     */
    public void testCreateCreationUserFilter() {
        assertNotNull(UserTypeFilterFactory.createCreationUserFilter("username"));
    }

    /**
     * Test {@link UserTypeFilterFactory#createModificationUserFilter(String)}.
     */
    public void testCreateModificationUserFilter() {
        assertNotNull(UserTypeFilterFactory.createModificationUserFilter("username"));
    }
}
