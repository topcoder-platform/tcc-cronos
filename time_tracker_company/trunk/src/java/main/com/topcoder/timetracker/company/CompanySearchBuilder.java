/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;

import java.util.Arrays;
import java.util.Date;


/**
 * <p>
 * This is a convenience class that may be used to build filters for performing searches in the CompanyDAO. Users may
 * call the different methods to set the criteria for the filter and finally retrieve the filter for use via the
 * buildFilter() method.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This is a stateful class, since it can be modified by the various method calls. It is not
 * thread safe, and multiple threads should work with their own instance of the search builder.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class CompanySearchBuilder {
    /**
     * <p>
     * An AndFilter that is used to aggregate the different criteria that the user has defined from calling the
     * different methods of this class.
     * </p>
     *
     * <p>
     * Initialized In: First method call to a method that isn't buildFilter/reset will cause this to be initialized to
     * a new AndFilter with a List containing the single filter that was built in the respective method call, accessed
     * In: buildFilter (a clone of the Filter is returned), modified In: reset() method will make this uninitialized
     * again and utilized In: All methods
     * </p>
     */
    private AndFilter andFilter;

    /**
     * <p>
     * Default constructor. Do nothing.
     * </p>
     */
    public CompanySearchBuilder() {
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company Name contains the given string.
     * </p>
     *
     * @param name The string to search for in the company name.
     *
     * @throws IllegalArgumentException if the value is null or an empty String.
     */
    public void hasCompanyName(String name) {
        TimeTrackerCompanyHelper.validateString(name, "name");
        andFilter(new LikeFilter(CompanyDAO.SEARCH_COMPANY_NAME, LikeFilter.CONTAIN_TAGS + name));
    }

    /**
     * <p>
     * Add the given filter into the andFilter of this builder. If the andFilter is not initialized, a new AndFilter
     * will be created with the given filter.
     * </p>
     *
     * @param filterToAnd the filter to and.
     */
    private void andFilter(Filter filterToAnd) {
        if (andFilter == null) {
            andFilter = new AndFilter(Arrays.asList(new Filter[] {filterToAnd}));
        } else {
            andFilter.addFilter(filterToAnd);
        }
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company Contact's First Name contains the given string.
     * </p>
     *
     * @param firstName The firstName String to search for.
     *
     * @throws IllegalArgumentException if the firstName is null or an empty String.
     */
    public void hasContactFirstName(String firstName) {
        TimeTrackerCompanyHelper.validateString(firstName, "firstName");
        andFilter(new LikeFilter(CompanyDAO.SEARCH_CONTACT_FIRST_NAME, LikeFilter.CONTAIN_TAGS + firstName));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company Contact's Last Name contains the given string.
     * </p>
     *
     * @param lastName The lastName String to search.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasContactLastName(String lastName) {
        TimeTrackerCompanyHelper.validateString(lastName, "lastName");
        andFilter(new LikeFilter(CompanyDAO.SEARCH_CONTACT_LAST_NAME, LikeFilter.CONTAIN_TAGS + lastName));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company Contact's Phone Number is equivalent to the given string.
     * </p>
     *
     * @param phoneNumber The phoneNumber to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasContactPhoneNumber(String phoneNumber) {
        TimeTrackerCompanyHelper.validateString(phoneNumber, "phoneNumber");
        andFilter(new EqualToFilter(CompanyDAO.SEARCH_CONTACT_PHONE, phoneNumber));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company Contact's Email Address is equivalent to the given string.
     * </p>
     *
     * @param email The email to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasContactEmail(String email) {
        TimeTrackerCompanyHelper.validateString(email, "email");
        andFilter(new EqualToFilter(CompanyDAO.SEARCH_CONTACT_EMAIL, email));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company's Street Address (line 1 or line 2) contains the given string.
     * </p>
     *
     * @param streetAddress The Street address to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasStreetAddress(String streetAddress) {
        TimeTrackerCompanyHelper.validateString(streetAddress, "streetAddress");

        Filter filter1 = new LikeFilter(CompanyDAO.SEARCH_STREET_ADDRESS1, LikeFilter.CONTAIN_TAGS + streetAddress);
        Filter filter2 = new LikeFilter(CompanyDAO.SEARCH_STREET_ADDRESS2, LikeFilter.CONTAIN_TAGS + streetAddress);
        andFilter(new OrFilter(filter1, filter2));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company's City is equivalent to the given string.
     * </p>
     *
     * @param city The City to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasCity(String city) {
        TimeTrackerCompanyHelper.validateString(city, "city");
        andFilter(new EqualToFilter(CompanyDAO.SEARCH_CITY, city));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company's State is equivalent to the given string.
     * </p>
     *
     * @param state The state to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasState(String state) {
        TimeTrackerCompanyHelper.validateString(state, "state");
        andFilter(new EqualToFilter(CompanyDAO.SEARCH_STATE, state));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies whose
     * Company's Zip Code is equivalent to the given string.
     * </p>
     *
     * @param zipCode The zipCode to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void hasZipCode(String zipCode) {
        TimeTrackerCompanyHelper.validateString(zipCode, "zipCode");
        andFilter(new EqualToFilter(CompanyDAO.SEARCH_ZIP_CODE, zipCode));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies that were
     * created within the given Date Range. Nulls may be provided to either the upper or lower bounds to indicate that
     * only one bound needs to be provided.
     * </p>
     *
     * @param startDate The starting date to search for.
     * @param endDate The end date to search for.
     *
     * @throws IllegalArgumentException if BOTH parameters are null, or if endDate is less than startDate.
     */
    public void createdWithinDateRange(Date startDate, Date endDate) {
        andFilter(buildDateRangeFilter(CompanyDAO.SEARCH_CREATED_DATE, startDate, endDate));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies that were
     * created by the given username.
     * </p>
     *
     * @param username The username to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void createdByUser(String username) {
        TimeTrackerCompanyHelper.validateString(username, "username");
        andFilter(new EqualToFilter(CompanyDAO.SEARCH_CREATED_USER, username));
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies that was
     * last modified within the given date range. Nulls may be provided to either the upper or lower bounds to
     * indicate that only one bound needs to be provided.
     * </p>
     *
     * @param startDate The starting date to search for.
     * @param endDate The end date to search for.
     *
     * @throws IllegalArgumentException if BOTH parameters are null, or if endDate is less than startDate.
     */
    public void modifiedWithinDateRange(Date startDate, Date endDate) {
        andFilter(buildDateRangeFilter(CompanyDAO.SEARCH_MODIFICATION_DATE, startDate, endDate));
    }

    /**
     * <p>
     * Build the filter whose value is within the given date range.
     * </p>
     *
     * @param name the filed name of the search criterion.
     * @param startDate the start date.
     * @param endDate the end date.
     *
     * @return the filter.
     *
     * @throws IllegalArgumentException if BOTH parameters are null, or if endDate is less than startDate.
     */
    private Filter buildDateRangeFilter(String name, Date startDate, Date endDate) {
        if ((startDate == null) && (endDate == null)) {
            throw new IllegalArgumentException("Both the startDate and endDate are null.");
        }

        if (startDate != null && endDate != null) {
            if (endDate.before(startDate)) {
                throw new IllegalArgumentException("EndDate is less than startDate.");
            }
        }

        Filter filter = null;

        if (startDate == null) {
            filter = new LessThanOrEqualToFilter(name, endDate);
        } else if (endDate == null) {
            filter = new GreaterThanOrEqualToFilter(name, startDate);
        } else {
            filter = new BetweenFilter(name, endDate, startDate);
        }

        return filter;
    }

    /**
     * <p>
     * This method is called to indicate that the built search filter should restrict the search to companies that was
     * last modified by the provided user.
     * </p>
     *
     * @param username The username to search for.
     *
     * @throws IllegalArgumentException if the parameter is null or an empty String.
     */
    public void modifiedByUser(String username) {
        TimeTrackerCompanyHelper.validateString(username, "username");
        andFilter(new EqualToFilter(CompanyDAO.SEARCH_MODIFICATION_USER, username));
    }

    /**
     * <p>
     * Retrieves the actual Search Builder filter built according to the previous method calls that defined the search
     * criterion to use. Note that this doesn't reset the state of the builder, and if a new set of criteria needs to
     * be used, then a call to reset() will be necessary.
     * </p>
     *
     * @return A Search Filter that may be used in the CompanyDAO.
     *
     * @throws IllegalStateException if no criteria has yet been set and the andFilter is null.
     */
    public Filter buildFilter() {
        if (andFilter == null) {
            throw new IllegalStateException("No criteria has yet been set.");
        }

        return (Filter) andFilter.clone();
    }

    /**
     * <p>
     * Resets any criterion that were specified in the previous method calls. It will be as if the CompanySearchBuilder
     * was newly constructed.
     * </p>
     */
    public void reset() {
        andFilter = null;
    }
}
