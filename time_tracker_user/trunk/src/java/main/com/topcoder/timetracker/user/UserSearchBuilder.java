/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.timetracker.common.Utils;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;

/**
 * <p>
 * This is a convenience class that may be used to build filters for performing searches in the UserDAO. Users may
 * call the different methods to set the criteria for the filter and finally retrieve the filter for use via the
 * buildFilter() method.
 * </p>
 * <p>
 * Thread Safety: - This is a stateful class, since it can be modified by the various method calls. It is not
 * thread safe, and multiple threads should work with their own instance of the search builder.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class UserSearchBuilder {

    /**
     * <p>
     * An AndFilter that is used to aggregate the different criteria that the user has defined from calling the
     * different methods of this class.
     * </p>
     * <p>
     * Initialized In: First method call to a method that isn't buildFilter/reset will cause this to be initialized
     * to a new AndFilter with a List containing the single filter that was built in the respective method call.
     * </p>
     * <p>
     * Accessed In: buildFilter (a clone of the Filter is returned)
     * </p>
     * <p>
     * Modified In: reset() method will make this uninitialized again
     * </p>
     * <p>
     * Utilized In: All methods
     * </p>
     *
     */
    private AndFilter andFilter;

    /**
     * <p>
     * This is the algorithm name for the encryption algorithm to be used when building a filter for the user's
     * password.
     * </p>
     * <p>
     * The algorithm name is used to retrieve the encryption algorithm from the Encryption component. It is
     * expected that the Encryption component would have been initialized with the encryption keys (if necessary)
     * prior to using this component.
     * </p>
     * <p>
     * It may be null, and in such a case, password queries will not be supported.
     * </p>
     * <p>
     * Initialized In: Constructor.
     * </p>
     * <p>
     * Utilized In: hasPassword
     * </p>
     *
     */
    private String algorithmName;

    /**
     * <p>
     * Default constructor. Such a constructor will not be able to support password queries.
     * </p>
     *
     */
    public UserSearchBuilder() {
        // empty
    }

    /**
     * <p>
     * Constructor that accepts an algorithmName to use when building a search filter that involves a password.
     * </p>
     *
     *
     * @param algorithmName The encryption algorithm to use when searching via password.
     * @throws IllegalArgumentException if the algorithmName is null or an empty String.
     */
    public UserSearchBuilder(String algorithmName) {
        Utils.checkString(algorithmName, "algorithmName", false);
        this.algorithmName = algorithmName;
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose Username is equivalent to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_USERNAME as the field name and the provided value as the search value. - If the andFilter is
     * not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param username The username to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasUsername(String username) {
        Utils.checkString(username, "username", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_USERNAME, username));
    }

    /**
     * <p>
     * Adds given filter to <code>andFilter</code>. It <code>andFilter</code> is <code>null</code>, new
     * instance will be created.
     * </p>
     *
     * @param filter the filter to be added.
     */
    private void addToFilter(Filter filter) {
        if (andFilter == null) {
            List list = new ArrayList();
            list.add(filter);
            andFilter = new AndFilter(list);
        } else {
            andFilter.addFilter(filter);
        }

    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose Password is equivalent to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Use the algorithmName to retrieve the encryption algorithm and encrypt the provided
     * password value. - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_PASSWORD as the field name and the encrypted value as the search value. - If the andFilter is
     * not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param password The password to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     * @throws IllegalStateException if the builder was not initialized with an encryption algorithm.
     * @throws com.topcoder.encryption.EncryptionException if error occurs during password encryption.
     */
    public void hasPassword(String password) {
        Utils.checkString(password, "password", false);
        if (algorithmName == null) {
            throw new IllegalStateException("The encryption algorithm is not set.");
        }

        String encrypted = Utils.encrypt(algorithmName, password);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_PASSWORD, encrypted));
    }

    /**
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose First Name contains the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> LikeFilter with
     * UserDAO.SEARCH_FIRST_NAME as the field name and the provided value as the search value. - If the andFilter
     * is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param firstName The name to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasFirstName(String firstName) {
        Utils.checkString(firstName, "firstName", false);
        addToFilter(new LikeFilter(UserDAO.SEARCH_FIRST_NAME, firstName));
    }

    /**
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose Last Name contains the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> LikeFilter with
     * UserDAO.SEARCH_LAST_NAME as the field name and the provided value as the search value. - If the andFilter is
     * not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param lastName The name to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasLastName(String lastName) {
        Utils.checkString(lastName, "lastName", false);
        addToFilter(new LikeFilter(UserDAO.SEARCH_LAST_NAME, lastName));
    }

    /**
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose Phone Number is equal to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_PHONE_NUMBER as the field name and the provided value as the search value. - If the andFilter
     * is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param phoneNumber The phone number to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasPhoneNumber(String phoneNumber) {
        Utils.checkString(phoneNumber, "phoneNumber", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_PHONE_NUMBER, phoneNumber));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose Email Address is equal to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_EMAIL as the field name and the provided value as the search value. - If the andFilter is not
     * initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param email The email to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasEmail(String email) {
        Utils.checkString(email, "email", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_EMAIL, email));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose Street Address (line 1 or line 2) contains to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> An OrFilter containing
     * the 2 filters below: -> LikeFilter with UserDAO.SEARCH_STREET_ADDRESS1 as the field name and the provided
     * value as the search value. -> LikeFilter with UserDAO.SEARCH_STREET_ADDRESS2 as the field name and the
     * provided value as the search value. - If the andFilter is not initialized, then initialize it with an
     * AndFilter containing the built filter.
     * </p>
     *
     *
     * @param address The address to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasStreetAddress(String address) {
        Utils.checkString(address, "address", false);
        Filter address1 = new LikeFilter(UserDAO.SEARCH_STREET_ADDRESS1, address);
        Filter address2 = new LikeFilter(UserDAO.SEARCH_STREET_ADDRESS2, address);
        addToFilter(new OrFilter(address1, address2));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose City is equal to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_CITY as the field name and the provided value as the search value. - If the andFilter is not
     * initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param city The city to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasCity(String city) {
        Utils.checkString(city, "city", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_CITY, city));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose State is equal to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_STATE as the field name and the provided value as the search value. - If the andFilter is not
     * initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param state The state to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasState(String state) {
        Utils.checkString(state, "state", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_STATE, state));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users whose Zip Code is equal to the given string.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_ZIP_CODE as the field name and the provided value as the search value. - If the andFilter is
     * not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param zipCode The zipCode to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasZipCode(String zipCode) {
        Utils.checkString(zipCode, "zipCode", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_ZIP_CODE, zipCode));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users that were created within the given Date Range. Nulls may be provided to either the upper or lower
     * bounds to indicate that only one bound needs to be provided.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> The filter that is built
     * may either be a GreaterThanFilter, LessThanFilter or BetweenFilter depending on whether the startDate or
     * endDate or none of them are null. The field name to be used is UserDAO.SEARCH_CREATED_DATE. - If the
     * andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     *
     * @param startDate The starting date to search for.
     * @param endDate The end date to search for.
     * @throws IllegalArgumentException if BOTH parameters are null, or if endDate is less than startDate.
     */
    public void createdWithinDateRange(Date startDate, Date endDate) {
        addToFilter(Utils.createRangerFilter(UserDAO.SEARCH_CREATED_DATE, startDate, endDate));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users that were created by the given username.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_CREATED_USER as the field name and the provided value as the search value. - If the andFilter
     * is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     *
     * @param username The username to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void createdByUser(String username) {
        Utils.checkString(username, "username", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_CREATED_USER, username));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * User's that was last modified within the given date range.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> The filter that is built
     * may either be a GreaterThanFilter, LessThanFilter or BetweenFilter depending on whether the startDate or
     * endDate or none of them are null. The field name to be used is UserDAO.SEARCH_MODIFICATION_DATE. - If the
     * andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     *
     * @param startDate The starting date to search for.
     * @param endDate The end date to search for.
     * @throws IllegalArgumentException if BOTH parameters are null, or if endDate is less than startDate.
     */
    public void modifiedWithinDateRange(Date startDate, Date endDate) {
        addToFilter(Utils.createRangerFilter(UserDAO.SEARCH_MODIFICATION_DATE, startDate, endDate));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to Time Tracker
     * Users that was last modified by the provided user.
     * </p>
     * <p>
     * Implementation Notes: - Build the following filter and add it to the andFilter: -> EqualsFilter with
     * UserDAO.SEARCH_MODIFICATION_USER as the field name and the provided value as the search value. - If the
     * andFilter is not initialized, then initialize it with an AndFilter containing the built filter.
     * </p>
     *
     *
     * @param username The username to search for.
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void modifiedByUser(String username) {
        Utils.checkString(username, "username", false);
        addToFilter(new EqualToFilter(UserDAO.SEARCH_MODIFICATION_USER, username));
    }

    /**
     * <p>
     * Retrieves the actual Search Builder filter built according to the previous method calls that defined the
     * search criterion to use. Note that this doesn't reset the state of the builder, and if a new set of criteria
     * needs to be used, then a call to reset() will be necessary.
     * </p>
     *
     * @return A Search Filter that may be used in the UserDAO.
     * @throws IllegalStateException if no criteria has yet been set and the andFilter is null.
     */
    public Filter buildFilter() {
        if ((andFilter == null) || (andFilter.getFilters().size() == 0)) {
            throw new IllegalStateException("The filter has no criteries.");
        }

        return (Filter) andFilter.clone();
    }

    /**
     * <p>
     * Resets any criterion that were specified in the previous method calls. It will be as if the
     * CompanySearchBuilder was newly constructed.
     * </p>
     *
     */
    public void reset() {
        andFilter = null;
    }
}
