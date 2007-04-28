/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.StringMatchType;

import java.util.Date;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectFilterFactory;

/**
 * <p>
 * This is an implementation of the <code>ProjectFilterFactory</code> that may be used for
 * building searches in the database.
 * </p>
 *
 * <p>
 * It maintains a set of column names that are necessary for the filter criterion that it supports, and builds filters
 * according to the specified column names.
 * </p>
 *
 * <p>
 * It extends <code>DbBaseFilterFactory</code> to implement the base functionality that is needed.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and so thread safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectFilterFactory extends DbBaseFilterFactory implements ProjectFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the Company Id.
     * </p>
     */
    public static final String COMPANY_ID_COLUMN_NAME = "COMPANY_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Client Id.
     * </p>
     */
    public static final String CLIENT_ID_COLUMN_NAME = "CLIENT_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Project Name.
     * </p>
     */
    public static final String PROJECT_NAME_COLUMN_NAME = "PROJECT_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Project Start Date.
     * </p>
     */
    public static final String PROJECT_START_DATE_COLUMN_NAME = "PROJECT_START_DATE";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Project End Date.
     * </p>
     */
    public static final String PROJECT_END_DATE_COLUMN_NAME = "PROJECT_END_DATE";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Project Manager Id.
     * </p>
     */
    public static final String PROJECT_MANAGER_ID_COLUMN_NAME = "PROJECT_MANAGER_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Project Worker Id.
     * </p>
     */
    public static final String PROJECT_WORKER_ID_COLUMN_NAME = "PROJECT_WORKER_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Time Entry  Id.
     * </p>
     */
    public static final String PROJECT_TIME_ENTRY_COLUMN_NAME = "PROJECT_TIME_ENTRY";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Expense Entry  Id.
     * </p>
     */
    public static final String PROJECT_EXPENSE_ENTRY_COLUMN_NAME = "PROJECT_EXPENSE_ENTRY";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Fixed Bill Id.
     * </p>
     */
    public static final String PROJECT_FIXED_BILL_ENTRY_COLUMN_NAME = "PROJECT_FIXED_BILL_ENTRY";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Active Column.
     * </p>
     */
    public static final String PROJECT_ACTIVE_COLUMN_NAME = "PROJECT_ACTIVE";

    /**
     * <p>
     * Creates a <code>DbProjectFilterFactory</code> instance.
     * </p>
     */
    public DbProjectFilterFactory() {
        // empty
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the id of the
     * Time Tracker Company that owns the project.
     * </p>
     *
     * @param companyId the id of the Time Tracker Company that owns the project.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createCompanyIdFilter(long companyId) {
        Util.checkIdValue(companyId, "company");

        return new EqualToFilter(COMPANY_ID_COLUMN_NAME, new Long(companyId));
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the id of the Time
     * Tracker Client for which work is being done.
     * </p>
     *
     * @param clientId the id of the Time Tracker Client for which work is being done.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createClientIdFilter(long clientId) {
        Util.checkIdValue(clientId, "client");

        return new EqualToFilter(CLIENT_ID_COLUMN_NAME, new Long(clientId));
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the name of the project.
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
     * @param name the name of the project.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String.
     */
    public Filter createNameFilter(StringMatchType matchType, String name) {
        Util.checkString(name, "name");

        return Util.createFilter(matchType, PROJECT_NAME_COLUMN_NAME, name);
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the start date of the Project.
     * </p>
     *
     * <p>
     * A date range that may be open-ended can be specified.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with start
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with start
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with start date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart &gt; rangeEnd),
     * or if both arguments are null.
     */
    public Filter createStartDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createRangeFilter(PROJECT_START_DATE_COLUMN_NAME, rangeStart, rangeEnd);
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on the end date of the Project.
     * </p>
     *
     * <p>
     * A date range that may be open-ended can be specified.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with end
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with end
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with end date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart &gt; rangeEnd),
     * or if both arguments are null.
     */
    public Filter createEndDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createRangeFilter(PROJECT_END_DATE_COLUMN_NAME, rangeStart, rangeEnd);
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether a project manager
     * with the provided user id has been assigned to it.
     * </p>
     *
     * @param managerId The user id of the project manager.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createContainsProjectManagerFilter(long managerId) {
        Util.checkIdValue(managerId, "manager");

        return new EqualToFilter(PROJECT_MANAGER_ID_COLUMN_NAME, new Long(managerId));
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether a project worker
     * with the provided user id has been assigned to it.
     * </p>
     *
     * @param workerId The user id of the worker.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createContainsProjectWorkerFilter(long workerId) {
        Util.checkIdValue(workerId, "worker");

        return new EqualToFilter(PROJECT_WORKER_ID_COLUMN_NAME, new Long(workerId));
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether an Entry
     * (<tt>Time</tt>/<tt>Expense</tt>/<tt>Fixed Billing</tt>) with the provided id has been
     * associated with it.
     * </p>
     *
     * <p>
     * Note, if the entry type is unknown, then <code>null</code> will be returned.
     * </p>
     *
     * @param entryId The id of the entry.
     * @param type The type of the entry.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the entryId &lt;= 0 or if the type is null.
     */
    public Filter createContainsEntryFilter(long entryId, EntryType type) {
        Util.checkIdValue(entryId, "entry");
        Util.checkNull(type, "type");

        if (type.equals(EntryType.EXPENSE_ENTRY)) {
            return new EqualToFilter(PROJECT_EXPENSE_ENTRY_COLUMN_NAME, new Long(entryId));
        }

        if (type.equals(EntryType.FIXED_BILLING_ENTRY)) {
            return new EqualToFilter(PROJECT_FIXED_BILL_ENTRY_COLUMN_NAME, new Long(entryId));
        }

        if (type.equals(EntryType.TIME_ENTRY)) {
            return new EqualToFilter(PROJECT_TIME_ENTRY_COLUMN_NAME, new Long(entryId));
        }

        // this is not reached in the current implementation
        return null;
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select Projects based on whether it is active or not.
     * </p>
     *
     * @param isActive Whether the project is active or not.
     * @return A filter that will be based off the specified criteria.
     */
    public Filter createIsActiveFilter(boolean isActive) {
        return new EqualToFilter(PROJECT_ACTIVE_COLUMN_NAME, new Long(isActive ? 1 : 0));
    }
}
