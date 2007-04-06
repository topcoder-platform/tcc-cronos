/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.contact.State;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for MappedUserFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class MappedUserFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * MappedUserFilterFactory instance for testing.
     * </p>
     */
    private MappedUserFilterFactory instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        Map columnNames = new HashMap();
        columnNames.put(MappedUserFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(MappedUserFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(MappedUserFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(MappedUserFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(MappedUserFilterFactory.USERNAME_COLUMN_NAME, "user_name");
        columnNames.put(MappedUserFilterFactory.PASSWORD_COLUMN_NAME, "password");
        columnNames.put(MappedUserFilterFactory.LAST_NAME_COLUMN_NAME, "last_name");
        columnNames.put(MappedUserFilterFactory.FIRST_NAME_COLUMN_NAME, "first_name");
        columnNames.put(MappedUserFilterFactory.PHONE_NUMBER_COLUMN_NAME, "phone");
        columnNames.put(MappedUserFilterFactory.EMAIL_COLUMN_NAME, "email");
        columnNames.put(MappedUserFilterFactory.ADDRESS1_COLUMN_NAME, "line1");
        columnNames.put(MappedUserFilterFactory.ADDRESS2_COLUMN_NAME, "line2");
        columnNames.put(MappedUserFilterFactory.CITY_COLUMN_NAME, "city");
        columnNames.put(MappedUserFilterFactory.STATE_COLUMN_NAME, "state_name_id");
        columnNames.put(MappedUserFilterFactory.ZIPCODE_COLUMN_NAME, "zip_code");
        columnNames.put(MappedUserFilterFactory.COMPANY_ID_COLUMN_NAME, "country_name_id");
        columnNames.put(MappedUserFilterFactory.STATUS_COLUMN_NAME, "account_status_id");

        instance = new MappedUserFilterFactory(columnNames);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(MappedUserFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create MappedUserFilterFactory instance.", instance);
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createUsernameFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateUsernameFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createUsernameFilter(StringMatchType.EXACT_MATCH, "name");
        assertEquals("Failed to create user name filter.", "user_name", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPasswordFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreatePasswordFilter() {
        LikeFilter filter = (LikeFilter) instance.createPasswordFilter(StringMatchType.STARTS_WITH, "password");
        assertEquals("Failed to create password filter.", "password", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createLastNameFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateLastNameFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createLastNameFilter(StringMatchType.EXACT_MATCH, "lastName");
        assertEquals("Failed to create last name filter.", "last_name", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createFirstNameFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateFirstNameFilter() {
        LikeFilter filter = (LikeFilter) instance.createFirstNameFilter(StringMatchType.STARTS_WITH, "firstName");
        assertEquals("Failed to create first name filter.", "first_name", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreatePhoneNumberFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createPhoneNumberFilter(StringMatchType.EXACT_MATCH,
            "phoneNumber");
        assertEquals("Failed to create phone number filter.", "phone", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createEmailFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateEmailFilter() {
        LikeFilter filter = (LikeFilter) instance.createEmailFilter(StringMatchType.ENDS_WITH, "email");
        assertEquals("Failed to create email filter.", "email", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createAddressFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateAddressFilter() {
        OrFilter filter = (OrFilter) instance.createAddressFilter(StringMatchType.EXACT_MATCH, "address");
        List list = filter.getFilters();
        assertEquals("Failed to create address filter.", "line1", ((EqualToFilter) list.get(0)).getName());
        assertEquals("Failed to create address filter.", "line2", ((EqualToFilter) list.get(1)).getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCityFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateCityFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createCityFilter(StringMatchType.EXACT_MATCH, "city");
        assertEquals("Failed to create city filter.", "city", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createStateFilter(State) for accuracy.
     * </p>
     */
    public void testCreateStateFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createStateFilter(new State());
        assertEquals("Failed to create state filter.", "state_name_id", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createZipCodeFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateZipCodeFilter() {
        LikeFilter filter = (LikeFilter) instance.createZipCodeFilter(StringMatchType.ENDS_WITH, "zipCode");
        assertEquals("Failed to create zipCode filter.", "zip_code", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createCompanyIdFilter(1);
        assertEquals("Failed to create company id filter.", "country_name_id", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createStatusFilter(Status) for accuracy.
     * </p>
     */
    public void testCreateStatusFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createStatusFilter(Status.INACTIVE);
        assertEquals("Failed to create status filter.", "account_status_id", filter.getName());
    }

}