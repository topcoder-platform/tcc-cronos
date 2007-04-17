/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.Map.Entry;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.BaseFilterFactory;
import com.topcoder.timetracker.entry.time.StringMatchType;

/**
 * <p>
 * This is an implementation of the <code>BaseFilterFactory</code> that may be used for building
 * searches in the database.
 * </p>
 *
 * <p>
 * It maintains a set of column names that are necessary for the filter criterion that it supports,
 * and builds filters according to the specified column names.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and so thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class DbBaseFilterFactory implements BaseFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the Creation Date.
     * </p>
     */
    public static final String CREATION_DATE_COLUMN_NAME = "CREATION_DATE_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Modification Date.
     * </p>
     */
    public static final String MODIFICATION_DATE_COLUMN_NAME = "MODIFICATION_DATE_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Creation User.
     * </p>
     */
    public static final String CREATION_USER_COLUMN_NAME = "CREATION_USER_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Modification User.
     * </p>
     */
    public static final String MODIFICATION_USER_COLUMN_NAME = "MODIFICATION_USER_COLUMN_NAME";

    /**
     * <p>
     * This is a mapping of column names to use.
     * </p>
     *
     * <p>
     * The FilterFactory will use these column names when determining the column name to use when
     * providing a <code>Filter</code>.
     * </p>
     *
     * <p>
     * It is set in the constructor and not changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final Map columnNames;

    /**
     * <p>
     * Creates a <code>DbBaseFilterFactory</code> with the specified column definitions.
     * </p>
     *
     * @param columnNames The column definitions to use.
     *
     * @throws IllegalArgumentException if columnNames contains null or empty String keys or values,
     * or if it is missing a Map Entry for the static constants defined in this class.
     */
    public DbBaseFilterFactory(Map columnNames) {
        Util.checkNull(columnNames, "columnNames");
        for (Iterator it = columnNames.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            checkString(entry.getKey(), "Some key in " + columnNames);
            checkString(entry.getValue(), "Some value in " + columnNames);
        }

        Util.checkMapForKeys(columnNames, new String[] {CREATION_DATE_COLUMN_NAME, MODIFICATION_DATE_COLUMN_NAME,
            CREATION_USER_COLUMN_NAME, MODIFICATION_USER_COLUMN_NAME});

        this.columnNames = new HashMap(columnNames);
    }

    /**
     * <p>
     * This method checks the given the argument.
     * </p>
     *
     * @param value the argument to check
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException if the value is null or not a string, or when it is a string, it is
     * empty
     */
    private void checkString(Object value, String name) {
        if (value instanceof String) {
            if (((String) value).trim().length() == 0) {
                throw new IllegalArgumentException(name + " is a empty string.");
            }
        } else {
            throw new IllegalArgumentException(name + " is " + ((value == null) ? "null." : " not a string."));
        }
    }

    /**
     * <p>
     * Creates a <code>Filter</code> based on the Creation Date of the entity.
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
     * If the <code>rangeStart</code> is null, then the filter should search the records with creation
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with creation
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with creation date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid
     * (e.g. rangeStart &gt; rangeEnd), or if both arguments are null.
     */
    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createRangeFilter((String) columnNames.get(CREATION_DATE_COLUMN_NAME), rangeStart, rangeEnd);
    }

    /**
     * <p>
     * Creates a <code>Filter</code> based on the Modification Date of the entity.
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
     * If the <code>rangeStart</code> is null, then the filter should search the records with modification
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with modification
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with modification date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid
     * (eg. rangeStart &gt; rangeEnd), or if both arguments are null.
     */
    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createRangeFilter((String) columnNames.get(MODIFICATION_DATE_COLUMN_NAME), rangeStart, rangeEnd);
    }

    /**
     * <p>
     * Creates a <code>Filter</code> based on the Creation User of the entity.
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
     * @param username The username used for searching.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or username is null or an empty String.
     */
    public Filter createCreationUserFilter(String username, StringMatchType matchType) {
        Util.checkString(username, "username");

        return Util.createFilter(matchType, (String) columnNames.get(CREATION_USER_COLUMN_NAME), username);
    }

    /**
     * <p>
     * Creates a <code>Filter</code> based on the Modification User of the entity.
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
     * @param username The username used for searching.
     * @param matchType The enum to specify the method of matching the Strings
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if matchType is null or username is null or an empty String.
     */
    public Filter createModificationUserFilter(String username, StringMatchType matchType) {
        Util.checkString(username, "username");

        return Util.createFilter(matchType, (String) columnNames.get(MODIFICATION_USER_COLUMN_NAME), username);

    }

    /**
     * <p>
     * Protected getter for the column names.
     * </p>
     *
     * <p>
     * It is protected in order for the subclasses to be able to access the map.
     * </p>
     *
     * @return the column names
     */
    protected Map getColumnNames() {
        return columnNames;
    }
}