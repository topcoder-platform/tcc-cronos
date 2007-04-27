/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;

import com.topcoder.timetracker.common.TimeTrackerBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;


/**
 * The helper class.
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public final class Helper {
    /**
     * The private constructor.
     *
     */
    private Helper() { }
    /**
     * Close the resources.
     *
     * @param conn The Connection instance.
     * @param state The Statement instance.
     * @param rs The ResultSet instance.
     */
    public static void closeResources(Connection conn, Statement state, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqle) {
                //Just ignore the exception when closing.
            }
        }

        if (state != null) {
            try {
                state.close();
            } catch (SQLException sqle) {
                //Just ignore the exception when closing.
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException sqle) {
                //Just ignore the exception when closing.
            }
        }
    }

    /**
     * Check whether the given long value is greater than 0.
     *
     * @param name the given value's name.
     * @param value the given value need to be checked.
     *
     * @throws IllegalArgumentException if the value is less or equal to 0.
     */
    public static void checkLongValue(String name, long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("The " + name + " should be greater than 0.");
        }
    }

    /**
     * Check whether the given long value is less than 0.
     *
     * @param name the given value's name.
     * @param value the given value need to be checked.
     *
     * @throws IllegalArgumentException if the value is less than 0.
     */
    public static void checkLongValueLessThan(String name, long value) {
        if (value < 0) {
            throw new IllegalArgumentException("The " + name + " should be less than 0.");
        }
    }

    /**
     * Check whether the given double value is less than 0.
     *
     * @param name the given value's name.
     * @param value the given value need to be checked.
     *
     * @throws IllegalArgumentException if the value is less than 0.
     */
    public static void checkDoubleValueLessThan(String name, double value) {
        if (value < 0) {
            throw new IllegalArgumentException("The " + name + " should be less than 0.");
        }
    }

    /**
     * Check whether the given object is null.
     *
     * @param name the given object's name
     * @param value the given object need to be checked
     *
     * @throws IllegalArgumentException if the given object is null
     */
    public static void checkNull(String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("the " + name + " should not be null.");
        }
    }

    /**
     * Check whether the given String is null or empty.
     *
     * @param name the given object's name
     * @param value the given object need to be checked
     *
     * @throws IllegalArgumentException if the given String is null or empty
     */
    public static void checkNullOrEmpty(String name, String value) {
        checkNull(name, value);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException("the " + name + " should not be empty.");
        }
    }

    /**
     * Check whether the map contains given keys.
     *
     * @param columnNames the map need to be checked.
     * @param keys the keys need to be checked.
     *
     * @throws IllegalArgumentException if null key, empty key, null value, empty value or not contains key.
     */
    public static void checkColumnNames(Map columnNames, String[] keys) {
        checkNull("columnNames", columnNames);

        for (Iterator itr = columnNames.keySet().iterator(); itr.hasNext();) {
            Object obj = itr.next();

            if ((obj == null) || !(obj instanceof String) || (((String) obj).trim().length() == 0)) {
                throw new IllegalArgumentException("The columnNames should not contain not String or empty keys.");
            }
        }

        for (Iterator itr = columnNames.values().iterator(); itr.hasNext();) {
            Object obj = itr.next();

            if ((obj == null) || !(obj instanceof String) || (((String) obj).trim().length() == 0)) {
                throw new IllegalArgumentException("The columnNames should not contain not String or empty values.");
            }
        }

        isContainKey(columnNames, keys);
    }

    /**
     * Check whether the map contains given keys.
     *
     * @param columnNames the map need to be checked.
     * @param keys the keys need to be checked.
     *
     * @throws IllegalArgumentException if not contains key.
     */
    public static void isContainKey(Map columnNames, String[] keys) {
        checkNull("columnNames", columnNames);
        checkNull("keys", keys);
        for (int i = 0; i < keys.length; i++) {
            if (!columnNames.containsKey(keys[i])) {
                throw new IllegalArgumentException("The columnNames should contain " + keys[i] + " key.");
            }
        }
    }

    /**
     * Check whether the dates are valid.
     *
     * @param rangeStart the start Date.
     * @param rangeEnd the end Date.
     *
     * @throws IllegalArgumentException if the given dates are invalid.
     */
    public static void checkDateRange(Date rangeStart, Date rangeEnd) {
        if ((rangeStart == null) && (rangeEnd == null)) {
            throw new IllegalArgumentException("At least one of the date should not be null.");
        }

        if ((rangeStart != null) && (rangeEnd != null)) {
            if (rangeStart.after(rangeEnd)) {
                throw new IllegalArgumentException("The range specified is invalid.");
            }
        }
    }

    /**
     * Check whether the values are valid.
     *
     * @param rangeStart the start value.
     * @param rangeEnd the end value.
     *
     * @throws IllegalArgumentException if the given values are invalid.
     */
    public static void checkDoubleRange(double rangeStart, double rangeEnd) {
        if ((rangeStart == Double.MIN_VALUE) && (rangeEnd == Double.MAX_VALUE)) {
            throw new IllegalArgumentException("At least one of the value should be specified.");
        }

        if (rangeStart > rangeEnd) {
            throw new IllegalArgumentException("The range specified is invalid.");
        }
    }

    /**
     * Create a date range filter.
     *
     * @param name the name.
     * @param rangeStart the start date.
     * @param rangeEnd the end date.
     *
     * @return a Filter with given range.
     *
     * @throws IllegalArgumentException if the given values are invalid.
     */
    public static Filter createDateRangeFilter(String name, Date rangeStart, Date rangeEnd) {
        checkDateRange(rangeStart, rangeEnd);

        if ((rangeStart != null) && (rangeEnd == null)) {
            return new GreaterThanOrEqualToFilter(name, rangeStart);
        } else if ((rangeStart == null) && (rangeEnd != null)) {
            return new LessThanOrEqualToFilter(name, rangeEnd);
        } else {
            return new BetweenFilter(name, rangeStart, rangeEnd);
        }
    }

    /**
     * Create a String value filter.
     *
     * @param name the name.
     * @param value the String value.
     * @param matchType the StringMatchType.
     *
     * @return a Filter with given value.
     *
     * @throws IllegalArgumentException if the given values are invalid.
     */
    public static Filter createFilterByMatchType(String name, String value, StringMatchType matchType) {
        checkNullOrEmpty(name, value);
        checkNull("matchType", matchType);

        if (matchType.equals(StringMatchType.EXACT_MATCH)) {
            return new EqualToFilter(name, value);
        } else {
            return new LikeFilter(name, matchType + value);
        }
    }

    /**
     * Create a Range filter.
     *
     * @param name the name.
     * @param rangeStart the start value.
     * @param rangeEnd the end value.
     *
     * @return a Filter with given values.
     *
     * @throws IllegalArgumentException if the given values are invalid.
     */
    public static Filter createDoubleRangeFilter(String name, double rangeStart, double rangeEnd) {
        checkDoubleRange(rangeStart, rangeEnd);

        if ((rangeStart != Double.MIN_VALUE) && (rangeEnd == Double.MAX_VALUE)) {
            return new GreaterThanOrEqualToFilter(name, new Double(rangeStart));
        } else if ((rangeStart == Double.MIN_VALUE) && (rangeEnd != Double.MAX_VALUE)) {
            return new LessThanOrEqualToFilter(name, new Double(rangeEnd));
        } else {
            return new BetweenFilter(name, new Double(rangeStart), new Double(rangeEnd));
        }
    }

    /**
     * Check the id array.
     *
     * @param name the name.
     * @param ids the id array.
     *
     * @throws IllegalArgumentException if array is null or &lt;=0.
     */
    public static void checkIdArray(String name, long[] ids) {
        if (ids == null) {
            throw new IllegalArgumentException("The " + name + " should not be null.");
        }

        if (ids.length == 0) {
            throw new IllegalArgumentException("The array should not be null.");
        }

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] <= 0) {
                throw new IllegalArgumentException("The " + name + "[" + i + "] should be greater than 0.");
            }
        }
    }

    /**
     * Check whether the given String is empty.
     *
     * @param name the name.
     * @param value the value need to be checked.
     *
     * @throws IllegalArgumentException if the value is not null but empty.
     */
    public static void checkNotEmpty(String name, String value) {
        if (value != null) {
            if (value.trim().length() == 0) {
                throw new IllegalArgumentException("The " + name + " should not be empty.");
            }
        }
    }

    /**
     * Check whether the given object is null.
     *
     * @param name the given object's name
     * @param value the given object need to be checked
     *
     * @throws IllegalArgumentException if the given object is null
     */
    public static void checkRequiredColumn(String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("the " + name + " should not be null.");
        }
    }

    /**
     * Check whether the given beans array's length is equals to the ids' length. If not, throw exception.
     *
     * @param beans the TimeTrackerBean array.
     * @param ids the id array.
     *
     * @throws BatchOperationException if the beans array's length is not equals to the ids' length.
     */
    public static void checkResultItems(TimeTrackerBean[] beans, long[] ids)
        throws BatchOperationException {
        checkNull("beans", beans);
        checkNull("ids", ids);
        if (beans.length != ids.length) {
            Exception[] exceptions = new Exception[ids.length];

            for (int i = 0; i < ids.length; i++) {
                boolean existFlag = false;

                for (int j = 0; j < beans.length; j++) {
                    if (beans[j].getId() == ids[i]) {
                        existFlag = true;

                        break;
                    }
                }

                if (!existFlag) {
                    exceptions[i] = new UnrecognizedEntityException(ids[i]);
                }
            }

            throw new BatchOperationException("Unable to find some id in the table.", exceptions);
        }
    }

    /**
     * Check whether the given Object array is null or contain null item.
     *
     * @param name the array's name.
     * @param obj the Object array.
     *
     * @throws IllegalArgumentException if object array is null or contains null.
     */
    public static void checkObjectArray(String name, Object[] obj) {
        checkNull(name, obj);

        for (int i = 0; i < obj.length; i++) {
            checkNull(name + "[" + i + "]", obj[i]);
        }
    }
    /**
     * Check whether the given long array contains duplicate id.
     *
     * @param ids the long array.
     * @throws IllegalArgumentException if the array contains duplicate id.
     */
    public static void checkDuplicateId(long[] ids) {
        checkNull("ids", ids);
        for (int i = 0; i < ids.length; i++) {
            for (int j = 0; j < ids.length; j++) {
                if ((i != j) && (ids[i] == ids[j])) {
                    throw new IllegalArgumentException("The given array contains duplicate id " + ids[i] + ".");
                }
            }
        }
    }
    /**
     * Create the Statement.
     *
     * @param conn the Connection instance.
     * @param state the Statement instance.
     * @param ids the id array.
     * @param sql the sql sentence.
     *
     * @return state a PreparedStatement instance.
     *
     * @throws SQLException if any error occurs.
     */
    public static PreparedStatement createInStatement(Connection conn, PreparedStatement state, long[] ids, String sql)
        throws SQLException {
        StringBuffer sqlBuff = new StringBuffer(sql);

        for (int i = 0; i < ids.length; i++) {
            sqlBuff.append("?");

            if (i < (ids.length - 1)) {
                sqlBuff.append(",");
            } else {
                sqlBuff.append(")");
            }
        }

        state = conn.prepareStatement(sqlBuff.toString());

        for (int i = 0; i < ids.length; i++) {
            state.setLong(i + 1, ids[i]);
        }

        return state;
    }
}
