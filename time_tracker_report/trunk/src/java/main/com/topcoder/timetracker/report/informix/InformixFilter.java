/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.Helper;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * <p>
 * This class provides several filters for filter the report results using Search Builder Component.
 * For create other filters is sufficient to add the name of criterion to configuration file and
 * directly instantiate the filter using the filter's classes. This facade is only for simplify the
 * usage of this component.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> this class is thread safe because have no state.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class InformixFilter {

    /**
     * <p>
     * This is the private constructor for preventing it from being called.
     * </p>
     */
    private InformixFilter() {
        // empty
    }

    /**
     * <p>
     * Retrieves the filter that filters the reports by a list of given company IDs.
     * </p>
     *
     * @param idCompanies the list of company IDs.
     *
     * @return the filter that filter the report by a list of given company IDs.
     *
     * @throws IllegalArgumentException if <code>idCompanies</code> is null or empty array.
     */
    public static Filter getFilterCompanies(long[] idCompanies) {
        Helper.checkArrayNotEmpty(idCompanies, "idCompanies");

        List list = new ArrayList();
        for (int i = 0; i < idCompanies.length; i++) {
            list.add(new Long(idCompanies[i]));
        }
        return new InFilter("company id", list);
    }

    /**
     * <p>
     * Retrieves the filter that filters the reports by a list of project IDs.
     * </p>
     *
     * @param idProjects the list of project IDs.
     *
     * @return the filter that filter the report by a list of project IDs.
     *
     * @throws IllegalArgumentException if <code>idProjects</code> is null or empty array.
     */
    public static Filter getFilterProjects(long[] idProjects) {
        Helper.checkArrayNotEmpty(idProjects, "idProjects");
        List list = new ArrayList();
        for (int i = 0; i < idProjects.length; i++) {
            list.add(new Long(idProjects[i]));
        }

        return new InFilter("project id", list);
    }

    /**
     * <p>
     * Retrieves the filter that filters the reports by a list of client IDs.
     * </p>
     *
     *
     * @param idClients the list of client IDs
     *
     * @return the filter that filter the report by a list of client IDs.
     *
     * @throws IllegalArgumentException if <code>idClients</code> is null or empty array.
     */
    public static Filter getFilterClients(long[] idClients) {
        Helper.checkArrayNotEmpty(idClients, "idClients");
        List list = new ArrayList();
        for (int i = 0; i < idClients.length; i++) {
            list.add(new Long(idClients[i]));
        }
        return new InFilter("client id", list);
    }

    /**
     * <p>
     * Retrieves the filter that filters the reports by the entry date within a given inclusive
     * range. This is valid for Expense entries, FixedBilling entries and Time entries.
     * </p>
     *
     * @param from the initial date, if null the range is considered open-ended
     * @param to the final date, if null the range is considered open-ended
     *
     * @return the filter that filter the report by the entry date within a given inclusive range. *
     * @throws IllegalArgumentException if <code>from</code> and <code>to</code> are both null,
     *         or <code>from</code> is some date after <code>to</code>.
     */
    public static Filter getFilterEntryDate(Date from, Date to) {
        // both 'from' and 'to' are null
        if (from == null && to == null) {
            throw new IllegalArgumentException("'from' and 'to' cannot be both null.");
        }

        // one of 'from' and 'to' is null
        if (from == null) {
            return new LessThanOrEqualToFilter("entry date", new java.sql.Date(to.getTime()));
        } else if (to == null) {
            return new GreaterThanOrEqualToFilter("entry date", new java.sql.Date(from.getTime()));
        }

        // both 'from' and 'to' are not null
        if (from.after(to)) {
            throw new IllegalArgumentException("'from' cannot be after 'to'.");
        }
        return new BetweenFilter("entry date", new java.sql.Date(to.getTime()), new java.sql.Date(from.getTime()));
    }

    /**
     * <p>
     * Retrieves the filter that filters the reports by the type of entry. This is valid for
     * Expense, and Time entries.
     * </p>
     *
     * @param type the type of the entries which is used for filtering
     *
     * @return the filter which will be used for filtering the type of the entries.
     */
    public static Filter getFilterType(long type) {
        return new EqualToFilter("type", new Long(type));
    }

    /**
     * <p>
     * Retrieves the filter that filters the reports by the status of entry. This is valid for
     * Expense, FixedBilling, and Time entries.
     * </p>
     *
     * @param status the status of the entries which is used for filtering
     *
     * @return the filter which will be used for filtering the status of the entries.
     *
     */
    public static Filter getFilterStatus(long status) {
        return new EqualToFilter("status", new Long(status));
    }

    /**
     * <p>
     * Retrieves the filter that filters the reports by a list of given usernames.
     * </p>
     *
     * @param userNames the usernames used for filtering
     *
     * @return the filter that filter the report by a list of given usernames.
     *
     * @throws IllegalArgumentException if <code>userNames</code> is null, or empty, or contains
     *         null element.
     */
    public static Filter getFilterUsernames(String[] userNames) {
        Helper.checkArrayNotEmptyNotNull(userNames, "userNames");
        return new InFilter("username", Arrays.asList(userNames));
    }
}
