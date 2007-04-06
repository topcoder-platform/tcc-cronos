/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.State;

/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Time Tracker Users.
 * </p>
 *
 * <p>
 * It offers a convenient way of specifying search criteria to use.
 * </p>
 *
 * <p>
 * The factory is capable of producing filters that conform to a specific schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>UserManager</code> or
 * <code>UserDAO</code> implementation that produced this <code>UserFilterFactory</code> instance.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this
 * factory with any of the Composite Filters in the Search Builder Component (<code>AndFilter</code>,
 * <code>OrFilter</code>, etc.)
 * </p>
 *
 * <p>
 * Note that all ranges specified are inclusive of the boundaries.
 * </p>
 *
 * <p>
 * Thread Safety: Implementation of this interface are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface UserFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the User's username.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     *
     * @param username The user name of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or user name is null or an empty String.
     */
    public Filter createUsernameFilter(StringMatchType matchType, String username);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the User's password.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param password The password of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or password is null or an empty String.
     */
    public Filter createPasswordFilter(StringMatchType matchType, String password);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the last name of the user.
     * The last name is part of the Contact information.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param lastName The last name of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or lastName is null or an empty String.
     */
    public Filter createLastNameFilter(StringMatchType matchType, String lastName);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the first name of the user.
     * The first name is part of the Contact information.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param firstName The first name of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or firstName is null or an empty String.
     */
    public Filter createFirstNameFilter(StringMatchType matchType, String firstName);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the Phone Number of the user.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param phoneNumber The phone number of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or phoneNumber is null or an empty String.
     */
    public Filter createPhoneNumberFilter(StringMatchType matchType, String phoneNumber);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the email address of the user.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param email The user id of the worker.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or email is null or an empty String.
     */
    public Filter createEmailFilter(StringMatchType matchType, String email);

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
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param address The address of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or address is null or an empty String.
     */
    public Filter createAddressFilter(StringMatchType matchType, String address);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the city where the user is based.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param city The city of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if city is null or an empty String.
     */
    public Filter createCityFilter(StringMatchType matchType, String city);

    /**
     * <p>
     * This creates a Filter that will select Users based on the state where the user is based.
     * </p>
     *
     * @param state The state of the user.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if state is null
     */
    public Filter createStateFilter(State state);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the zip code of the user.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param zipCode The zip code of the user.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or zipCode is null or an empty String.
     */
    public Filter createZipCodeFilter(StringMatchType matchType, String zipCode);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select User based on the id of the Time Tracker
     * Company that is related to the user.
     * </p>
     *
     * @param companyId the id of the Time Tracker Company to search.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createCompanyIdFilter(long companyId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Users based on the Status of the user.
     * </p>
     *
     * @param status the Status of the user.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the status is null.
     */
    public Filter createStatusFilter(Status status);
}
