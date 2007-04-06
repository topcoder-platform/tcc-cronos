/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.filterfactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.contact.State;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for MappedUserFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MappedUserFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The MappedUserFilterFactory instance for testing.
     * </p>
     */
    private MappedUserFilterFactory factory;

    /**
     * <p>
     * The columnNames map for testing.
     * </p>
     */
    private Map columnNames;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        columnNames = new HashMap();
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

        factory = new MappedUserFilterFactory(columnNames);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        factory = null;
        columnNames = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(MappedUserFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created MappedUserFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new MappedUserFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullColumnNames() {
        try {
            new MappedUserFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullKey() {
        columnNames.put(null, "email");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyKey() {
        columnNames.put(" ", "email");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_KeyNotString() {
        columnNames.put(new Long(8), "email");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some keys are missing and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_MissingKey() {
        columnNames.remove(MappedUserFilterFactory.ADDRESS1_COLUMN_NAME);
        try {
            new MappedUserFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullValue() {
        columnNames.put(MappedUserFilterFactory.ADDRESS1_COLUMN_NAME, null);
        try {
            new MappedUserFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyValue() {
        columnNames.put(MappedUserFilterFactory.ADDRESS1_COLUMN_NAME, " ");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedUserFilterFactory#MappedUserFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_ValueNotString() {
        columnNames.put(MappedUserFilterFactory.ADDRESS1_COLUMN_NAME, new Long(8));
        try {
            new MappedUserFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createUsernameFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createUsernameFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateUsernameFilter() {
        LikeFilter filter = (LikeFilter) factory.createUsernameFilter(StringMatchType.ENDS_WITH, "username");
        assertEquals("Failed to create the user name filter correctly.", "user_name", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createUsernameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateUsernameFilter_NullMatchType() {
        try {
            factory.createUsernameFilter(null, "username");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createUsernameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateUsernameFilter_NullUsername() {
        try {
            factory.createUsernameFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createUsernameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateUsernameFilter_EmptyUsername() {
        try {
            factory.createUsernameFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPasswordFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createPasswordFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreatePasswordFilter() {
        LikeFilter filter = (LikeFilter) factory.createPasswordFilter(StringMatchType.ENDS_WITH, "password");
        assertEquals("Failed to create the password filter correctly.", "password", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPasswordFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreatePasswordFilter_NullMatchType() {
        try {
            factory.createPasswordFilter(null, "password");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPasswordFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreatePasswordFilter_NullPassword() {
        try {
            factory.createPasswordFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPasswordFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreatePasswordFilter_EmptyPassword() {
        try {
            factory.createPasswordFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createLastNameFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createLastNameFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateLastNameFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createLastNameFilter(StringMatchType.EXACT_MATCH, "lastName");
        assertEquals("Failed to create the last name filter correctly.", "last_name", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createLastNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateLastNameFilter_NullMatchType() {
        try {
            factory.createLastNameFilter(null, "lastName");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createLastNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when lastName is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateLastNameFilter_NullLastName() {
        try {
            factory.createLastNameFilter(StringMatchType.STARTS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createLastNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when lastName is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateLastNameFilter_EmptyLastName() {
        try {
            factory.createLastNameFilter(StringMatchType.STARTS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createFirstNameFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createFirstNameFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateFirstNameFilter() {
        LikeFilter filter = (LikeFilter) factory.createFirstNameFilter(StringMatchType.ENDS_WITH, "firstName");
        assertEquals("Failed to create the first name filter correctly.", "first_name", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createFirstNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateFirstNameFilter_NullMatchType() {
        try {
            factory.createFirstNameFilter(null, "firstName");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createFirstNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when firstName is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateFirstNameFilter_NullFirstName() {
        try {
            factory.createFirstNameFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createFirstNameFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when firstName is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateFirstNameFilter_EmptyFirstName() {
        try {
            factory.createFirstNameFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreatePhoneNumberFilter() {
        LikeFilter filter = (LikeFilter) factory.createPhoneNumberFilter(StringMatchType.ENDS_WITH, "phoneNumber");
        assertEquals("Failed to create the phone number filter correctly.", "phone", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreatePhoneNumberFilter_NullMatchType() {
        try {
            factory.createPhoneNumberFilter(null, "phoneNumber");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when phoneNumber is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreatePhoneNumberFilter_NullPhoneNumber() {
        try {
            factory.createPhoneNumberFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when phoneNumber is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreatePhoneNumberFilter_EmptyPhoneNumber() {
        try {
            factory.createPhoneNumberFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createEmailFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createEmailFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateEmailFilter() {
        LikeFilter filter = (LikeFilter) factory.createEmailFilter(StringMatchType.ENDS_WITH, "email");
        assertEquals("Failed to create the email filter correctly.", "email", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createEmailFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEmailFilter_NullMatchType() {
        try {
            factory.createEmailFilter(null, "email");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createEmailFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when email is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEmailFilter_NullEmail() {
        try {
            factory.createEmailFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createEmailFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when email is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEmailFilter_EmptyEmail() {
        try {
            factory.createEmailFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createAddressFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createAddressFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateAddressFilter() {
        OrFilter filter = (OrFilter) factory.createAddressFilter(StringMatchType.ENDS_WITH, "address");
        List list = filter.getFilters();

        assertEquals("Expected the size of the list is two.", 2, list.size());
        assertEquals("Failed to create the address filter correctly.", "line1", ((LikeFilter) list.get(0)).getName());
        assertEquals("Failed to create the address filter correctly.", "line2", ((LikeFilter) list.get(1)).getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createAddressFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateAddressFilter_NullMatchType() {
        try {
            factory.createAddressFilter(null, "address");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createAddressFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when address is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateAddressFilter_NullAddress() {
        try {
            factory.createAddressFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createAddressFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when address is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateAddressFilter_EmptyAddress() {
        try {
            factory.createAddressFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCityFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createCityFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateCityFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createCityFilter(StringMatchType.EXACT_MATCH, "city");
        assertEquals("Failed to create the city filter correctly.", "city", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCityFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCityFilter_NullMatchType() {
        try {
            factory.createCityFilter(null, "city");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCityFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when city is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCityFilter_NullCity() {
        try {
            factory.createCityFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCityFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when city is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCityFilter_EmptyCity() {
        try {
            factory.createCityFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createStateFilter(State) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createStateFilter(State) is correct.
     * </p>
     */
    public void testCreateStateFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createStateFilter(new State());
        assertEquals("Failed to create the state filter correctly.", "state_name_id", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createStateFilter(State) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when state is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateStateFilter_NullState() {
        try {
            factory.createStateFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createZipCodeFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createZipCodeFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateZipCodeFilter() {
        LikeFilter filter = (LikeFilter) factory.createZipCodeFilter(StringMatchType.ENDS_WITH, "zipCode");
        assertEquals("Failed to create the zipcode filter correctly.", "zip_code", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createZipCodeFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateZipCodeFilter_NullMatchType() {
        try {
            factory.createZipCodeFilter(null, "zipCode");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createZipCodeFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when zipCode is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateZipCodeFilter_NullZipCode() {
        try {
            factory.createZipCodeFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createZipCodeFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when zipCode is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateZipCodeFilter_EmptyZipCode() {
        try {
            factory.createZipCodeFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createCompanyIdFilter(long) is correct.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createCompanyIdFilter(8);
        assertEquals("Failed to create the company id filter correctly.", "country_name_id", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is not positive and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCompanyIdFilter_NegativeId() {
        try {
            factory.createCompanyIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createStatusFilter(Status) for failure.
     * </p>
     *
     * <p>
     * It verifies MappedUserFilterFactory#createStatusFilter(Status) is correct.
     * </p>
     */
    public void testCreateStatusFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createStatusFilter(Status.ACTIVE);
        assertEquals("Failed to create the status filter correctly.", "account_status_id", filter.getName());
    }

    /**
     * <p>
     * Tests MappedUserFilterFactory#createStatusFilter(Status) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when status is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateStatusFilter_NullStatus() {
        try {
            factory.createStatusFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}