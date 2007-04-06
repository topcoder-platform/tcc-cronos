/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.timetracker.contact.State;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link MappedUserFilterFactory}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MappedUserFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * The MappedUserFilterFactory instance for testing.
     * </p>
     */
    private MappedUserFilterFactory mappedUserFilterFactory;

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

        mappedUserFilterFactory = new MappedUserFilterFactory(columnNames);
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_NullMap() {
        try {
            new MappedUserFilterFactory(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_MapContainsNullKey() {
        columnNames.put(null, "column");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_MapContainsEmptyKey() {
        columnNames.put("", "column");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_MapContainsTrimmedEmptyKey() {
        columnNames.put("  ", "column");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_MapContainsNullValue() {
        columnNames.put("key", null);
        try {
            new MappedUserFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_MapContainsEmptyValue() {
        columnNames.put("key", "");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_MapContainsTrimmedEmptyValue() {
        columnNames.put("key", " ");
        try {
            new MappedUserFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#MappedUserFilterFactory(Map)}</code> method.
     * </p>
     */
    public void testMappedUserFilterFactory_MissingRequiredAttribute() {
        columnNames.remove(MappedUserFilterFactory.CREATION_DATE_COLUMN_NAME);
        try {
            new MappedUserFilterFactory(columnNames);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createUsernameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateUsernameFilter_NullUserName() {
        try {
            mappedUserFilterFactory.createUsernameFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createUsernameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateUsernameFilter_EmptyUserName() {
        try {
            mappedUserFilterFactory.createUsernameFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createUsernameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateUsernameFilter_TrimmedEmptyUserName() {
        try {
            mappedUserFilterFactory.createUsernameFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createPasswordFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreatePasswordFilter_NullPassword() {
        try {
            mappedUserFilterFactory.createPasswordFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createPasswordFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreatePasswordFilter_EmptyPassword() {
        try {
            mappedUserFilterFactory.createPasswordFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createPasswordFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreatePasswordFilter_TrimmedEmptyPassword() {
        try {
            mappedUserFilterFactory.createPasswordFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createLastNameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateLastNameFilter_NullLastName() {
        try {
            mappedUserFilterFactory.createLastNameFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createLastNameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateLastNameFilter_EmptyLastName() {
        try {
            mappedUserFilterFactory.createLastNameFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createLastNameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateLastNameFilter_TrimmedEmptyLastName() {
        try {
            mappedUserFilterFactory.createLastNameFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createFirstNameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateFirstNameFilter_NullFirstName() {
        try {
            mappedUserFilterFactory.createFirstNameFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createFirstNameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateFirstNameFilter_EmptyFirstName() {
        try {
            mappedUserFilterFactory.createFirstNameFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createFirstNameFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateFirstNameFilter_TrimmedEmptyFirstName() {
        try {
            mappedUserFilterFactory.createFirstNameFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreatePhoneNumberFilter_NullPhoneNumber() {
        try {
            mappedUserFilterFactory.createPhoneNumberFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreatePhoneNumberFilter_EmptyPhoneNumber() {
        try {
            mappedUserFilterFactory.createPhoneNumberFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createPhoneNumberFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreatePhoneNumberFilter_TrimmedEmptyPhoneNumber() {
        try {
            mappedUserFilterFactory.createPhoneNumberFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createEmailFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateEmailFilter_NullEmail() {
        try {
            mappedUserFilterFactory.createEmailFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createEmailFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateEmailFilter_EmptyEmail() {
        try {
            mappedUserFilterFactory.createEmailFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createEmailFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateEmailFilter_TrimmedEmptyEmail() {
        try {
            mappedUserFilterFactory.createEmailFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createAddressFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateAddressFilter_NullAddress() {
        try {
            mappedUserFilterFactory.createAddressFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createAddressFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateAddressFilter_EmptyAddress() {
        try {
            mappedUserFilterFactory.createAddressFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createAddressFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateAddressFilter_TrimmedEmptyAddress() {
        try {
            mappedUserFilterFactory.createAddressFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createCityFilter(StringMatchType, String)}</code> method.
     * </p>
     */
    public void testCreateCityFilter_NullCity() {
        try {
            mappedUserFilterFactory.createCityFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createCityFilter(StringMatchType, String)}</code> method.
     * </p>
     */
    public void testCreateCityFilter_EmptyCity() {
        try {
            mappedUserFilterFactory.createCityFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createCityFilter(StringMatchType, String)}</code> method.
     * </p>
     */
    public void testCreateCityFilter_TrimmedEmptyCity() {
        try {
            mappedUserFilterFactory.createCityFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createStateFilter(State)}</code> method.
     * </p>
     */
    public void testCreateStateFilter_NullState() {
        try {
            mappedUserFilterFactory.createStateFilter(null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createStatusFilter(Status)}</code> method.
     * </p>
     */
    public void testCreateStatusFilter_NullStatus() {
        try {
            mappedUserFilterFactory.createStatusFilter(null);
            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createZipCodeFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateZipCodeFilter_NullZipCode() {
        try {
            mappedUserFilterFactory.createZipCodeFilter(StringMatchType.EXACT_MATCH, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createZipCodeFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateZipCodeFilter_EmptyZipCode() {
        try {
            mappedUserFilterFactory.createZipCodeFilter(StringMatchType.EXACT_MATCH, "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createZipCodeFilter(StringMatchType, String)}</code>
     * method.
     * </p>
     */
    public void testCreateZipCodeFilter_TrimmedEmptyZipCode() {
        try {
            mappedUserFilterFactory.createZipCodeFilter(StringMatchType.EXACT_MATCH, " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createCityFilter(StringMatchType, String)F}</code>
     * method.
     * </p>
     */
    public void testCreateCompanyIdFilter_ZeroId() {
        try {
            mappedUserFilterFactory.createCompanyIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link MappedUserFilterFactory#createCityFilter(StringMatchType, String)F}</code>
     * method.
     * </p>
     */
    public void testCreateCompanyIdFilter_NegativeId() {
        try {
            mappedUserFilterFactory.createCompanyIdFilter(0);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
