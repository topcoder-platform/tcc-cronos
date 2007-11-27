/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.filterfactory;

import com.topcoder.timetracker.contact.State;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.Util;

import java.util.Map;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.user.Status;

/**
 * <p>
 * This is an implementation of the <code>UserFilterFactory</code> that may be used for building searches.
 * </p>
 *
 * <p>
 * It maintains a set of column names that are necessary for the filter criterion that it supports, and builds
 * filters according to the specified column names.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>UserManager</code> or
 * <code>UserDAO</code> implementation that produced this <code>UserFilterFactory</code> instance.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this factory with any of the
 * Composite Filters in the Search Builder Component (<code>AndFilter</code>, <code>OrFilter</code>, etc.)
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and so is thread-safe.
 * </p>
 *
 * <p>
 * <strong>Version 3.2.1</strong> adds filter for user status and user type.
 * </p>
 *
 * @author ShindouHikaru, biotrail, George1, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class MappedUserFilterFactory extends MappedBaseFilterFactory implements UserFilterFactory {

    /**
     * <p>
     * This is the map key to use to specify the column name for the username.
     * </p>
     */
    public static final String USERNAME_COLUMN_NAME = "USERNAME_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the password.
     * </p>
     */
    public static final String PASSWORD_COLUMN_NAME = "PASSWORD_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the last name.
     * </p>
     */
    public static final String LAST_NAME_COLUMN_NAME = "LAST_NAME_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the first name.
     * </p>
     */
    public static final String FIRST_NAME_COLUMN_NAME = "FIRST_NAME_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the phone number.
     * </p>
     */
    public static final String PHONE_NUMBER_COLUMN_NAME = "PHONE_NUMBER_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the email address.
     * </p>
     *
     */
    public static final String EMAIL_COLUMN_NAME = "EMAIL_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the address (line 1).
     * </p>
     *
     */
    public static final String ADDRESS1_COLUMN_NAME = "ADDRESS1_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the address (line 2).
     * </p>
     */
    public static final String ADDRESS2_COLUMN_NAME = "ADDRESS2_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the city.
     * </p>
     */
    public static final String CITY_COLUMN_NAME = "CITY_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the state.
     * </p>
     */
    public static final String STATE_COLUMN_NAME = "STATE_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the zip code.
     * </p>
     */
    public static final String ZIPCODE_COLUMN_NAME = "ZIPCODE_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the company id.
     * </p>
     */
    public static final String COMPANY_ID_COLUMN_NAME = "COMPANY_ID_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the account status.
     * </p>
     */
    public static final String STATUS_COLUMN_NAME = "STATUS_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the user status id.
     * </p>
     *
     * @since 3.2.1
     */
    public static final String USER_STATUS_ID_COLUMN_NAME = "USER_STATUS_ID_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the user status name.
     * </p>
     *
     * @since 3.2.1
     */
    public static final String USER_STATUS_NAME_COLUMN_NAME = "USER_STATUS_NAME_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the user type id.
     * </p>
     *
     * @since 3.2.1
     */
    public static final String USER_TYPE_ID_COLUMN_NAME = "USER_TYPE_ID_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the user type name.
     * </p>
     *
     * @since 3.2.1
     */
    public static final String USER_TYPE_NAME_COLUMN_NAME = "USER_TYPE_NAME_COLUMN_NAME";

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -2174607764064209426L;

    /**
     * <p>
     * Creates a <code>DbUserFilterFactory</code> with the specified column definitions.
     * </p>
     * <p>
     * Note, a defensive copy is made to make sure it is not modified afterwards.
     * </p>
     *
     * @param columnNames
     *            The column definitions to use.
     *
     * @throws IllegalArgumentException
     *             if columnNames contains null or empty String keys or values, or if it is missing a Map Entry for
     *             the static constants defined in this class.
     */
    public MappedUserFilterFactory(Map columnNames) {
        super(columnNames);

        Util.checkMapForKeys(columnNames, new String[] {USERNAME_COLUMN_NAME, PASSWORD_COLUMN_NAME,
            LAST_NAME_COLUMN_NAME, FIRST_NAME_COLUMN_NAME, PHONE_NUMBER_COLUMN_NAME, EMAIL_COLUMN_NAME,
            ADDRESS1_COLUMN_NAME, ADDRESS2_COLUMN_NAME, CITY_COLUMN_NAME, STATE_COLUMN_NAME,
            ZIPCODE_COLUMN_NAME, COMPANY_ID_COLUMN_NAME, STATUS_COLUMN_NAME, USER_STATUS_ID_COLUMN_NAME,
            USER_STATUS_NAME_COLUMN_NAME, USER_TYPE_ID_COLUMN_NAME, USER_TYPE_NAME_COLUMN_NAME});
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the User's username.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param username
     *            The username of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createUsernameFilter(StringMatchType matchType, String username) {
        Util.checkString(username, "username");
        return Util.createFilter(matchType, (String) getColumnNames().get(USERNAME_COLUMN_NAME), username);
    }

    /**
     * <p>
     * This creates a Filter that will select Users based on the User's password.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param password
     *            The password of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createPasswordFilter(StringMatchType matchType, String password) {
        Util.checkString(password, "password");
        return Util.createFilter(matchType, (String) getColumnNames().get(PASSWORD_COLUMN_NAME), password);
    }

    /**
     * <p>
     * This creates a Filter that will select Users based on the last name of the user.
     * </p>
     *
     * <p>
     * The last name is part of the Contact information.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param lastName
     *            The last name of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createLastNameFilter(StringMatchType matchType, String lastName) {
        Util.checkString(lastName, "lastName");
        return Util.createFilter(matchType, (String) getColumnNames().get(LAST_NAME_COLUMN_NAME), lastName);
    }

    /**
     * <p>
     * This creates a Filter that will select Users based on the first name of the user.
     * </p>
     *
     * <p>
     * The first name is part of the Contact information.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param firstName
     *            The first name of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createFirstNameFilter(StringMatchType matchType, String firstName) {
        Util.checkString(firstName, "firstName");
        return Util.createFilter(matchType, (String) getColumnNames().get(FIRST_NAME_COLUMN_NAME), firstName);
    }

    /**
     * <p>
     * This creates a Filter that will select Users based on the Phone Number of the user.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param phoneNumber
     *            The phone number of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createPhoneNumberFilter(StringMatchType matchType, String phoneNumber) {
        Util.checkString(phoneNumber, "phoneNumber");
        return Util.createFilter(matchType, (String) getColumnNames().get(PHONE_NUMBER_COLUMN_NAME),
            phoneNumber);
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the email address of the user.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param email
     *            The user id of the worker.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createEmailFilter(StringMatchType matchType, String email) {
        Util.checkString(email, "email");
        return Util.createFilter(matchType, (String) getColumnNames().get(EMAIL_COLUMN_NAME), email);
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the address of the user.
     * </p>
     *
     * <p>
     * A match can be performed on either the first or second line of the address.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param address
     *            The address of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createAddressFilter(StringMatchType matchType, String address) {
        Util.checkString(address, "address");
        Map columnsMap = getColumnNames();
        return new OrFilter(Util.createFilter(matchType, (String) columnsMap.get(ADDRESS1_COLUMN_NAME),
            address), Util.createFilter(matchType, (String) columnsMap.get(ADDRESS2_COLUMN_NAME), address));
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the city where the user is based.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param city
     *            The city of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createCityFilter(StringMatchType matchType, String city) {
        Util.checkString(city, "city");
        return Util.createFilter(matchType, (String) getColumnNames().get(CITY_COLUMN_NAME), city);
    }

    /**
     * <p>
     * This creates a Filter that will select Projects based on the state where the user is based.
     * </p>
     *
     * @param state
     *            The state of the user.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the state is null.
     */
    public Filter createStateFilter(State state) {
        Util.checkNull(state, "state");

        return new EqualToFilter((String) getColumnNames().get(STATE_COLUMN_NAME), new Long(state.getId()));
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the zip code of the user.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>. For
     * {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the provided
     * value. For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the provided
     * value. For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the provided
     * value.
     * </p>
     *
     * @param zipCode
     *            The zip code of the user.
     * @param matchType
     *            The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String.
     */
    public Filter createZipCodeFilter(StringMatchType matchType, String zipCode) {
        Util.checkString(zipCode, "zipCode");

        return Util.createFilter(matchType, (String) getColumnNames().get(ZIPCODE_COLUMN_NAME), zipCode);
    }

    /**
     * <p>
     * This creates a Filter that will select User based on the id of the Time Tracker Company that is related to
     * the user.
     * </p>
     *
     * @param companyId
     *            the id of the Time Tracker Company to search.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     */
    public Filter createCompanyIdFilter(long companyId) {
        return Util.createLongFilter(companyId, "companyId", (String) getColumnNames().get(
            COMPANY_ID_COLUMN_NAME));
    }

    /**
     * <p>
     * This creates a Filter that will select Users based on the Status of the user.
     * </p>
     *
     * @param status
     *            the Status of the user.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the status is null.
     */
    public Filter createStatusFilter(Status status) {
        Util.checkNull(status, "status");

        return new EqualToFilter((String) getColumnNames().get(STATUS_COLUMN_NAME), new Long(status.getId()));
    }

    /**
     * <p>
     * Creates a Filter that will select Users based on their user status. User status is specified by its ID.
     * </p>
     *
     * @param userStatusId
     *            An ID of the user status to filter users by.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if parameter userStatusId is &lt;= 0.
     * @since 3.2.1
     */
    public Filter createUserStatusIdFilter(long userStatusId) {
        return Util.createLongFilter(userStatusId, "userStatusId", (String) getColumnNames().get(
            USER_STATUS_ID_COLUMN_NAME));
    }

    /**
     * <p>
     * Creates a Filter that will select users based on their user status. User status is specified by its full
     * description, case sensitive.
     * </p>
     *
     * @param userStatusName
     *            Full description of the user status to filter users by.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if parameter userStatusName is null or empty (trimmed) string.
     * @since 3.2.1
     */
    public Filter createUserStatusNameFilter(String userStatusName) {
        return Util.createStringFilter(userStatusName, "userStatusName", (String) getColumnNames().get(
            USER_STATUS_NAME_COLUMN_NAME));
    }

    /**
     * <p>
     * Creates a Filter that will select Users based on their user type. User type is specified by its ID.
     * </p>
     *
     * @param userTypeId
     *            An ID of the user type to filter users by.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if parameter userTypeId is &lt;= 0.
     * @since 3.2.1
     */
    public Filter createUserTypeIdFilter(long userTypeId) {
        return Util.createLongFilter(userTypeId, "userTypeId", (String) getColumnNames().get(
            USER_TYPE_ID_COLUMN_NAME));
    }

    /**
     * <p>
     * Creates a <code>Filter</code> that will select Users based on their user type. User type is specified by
     * its full description, case sensitive.
     * </p>
     *
     * @param userTypeName
     *            Full description of the user type to filter users by.
     * @return A filter that will be based off the specified criteria.
     * @throws IllegalArgumentException
     *             if parameter userTypeName is null or empty (trimmed) string.
     * @since 3.2.1
     */
    public Filter createUserTypeNameFilter(String userTypeName) {
        return Util.createStringFilter(userTypeName, "userTypeName", (String) getColumnNames().get(
            USER_TYPE_NAME_COLUMN_NAME));
    }

}
