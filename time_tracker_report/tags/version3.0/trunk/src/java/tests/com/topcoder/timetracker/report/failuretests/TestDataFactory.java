/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.BasicColumnDecorator;
import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.FilterCategory;
import com.topcoder.timetracker.report.RangeFilter;
import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportType;
import com.topcoder.timetracker.report.StyleConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A factory producing the sample data which could be used for testing purposes. This class provides a set of static
 * constants and a set of static methods producing the sample data. Note that the methods produce a new copy of sample
 * data on each method call.</p>
 *
 * @author  isv
 * @version 2.0
 */
public class TestDataFactory {

    /**
     *
     */
    public static final Object NULL = null;

    /**
     * <p>A zero-length <code>String</code>.</p>
     */
    public static final String ZERO_LENGTH_STRING = "";

    /**
     *
     */
    public static final String WHITESPACE_ONLY_STRING = " \t \n \t ";

    /**
     *
     */
    public static final List EMPTY_LIST = new ArrayList();

    /**
     *
     */
    public static final Set EMPTY_SET = new HashSet();

    /**
     *
     */
    public static final Map EMPTY_MAP = new HashMap();

    /**
     *
     */
    public static final String UNKNOWN_REPORT_FORMAT = "UnknownReportFormat";

    /**
     *
     */
    public static final String VALID_REPORT_FORMAT = "HTML";

    /**
     *
     */
    public static final ReportCategory VALID_REPORT_CATEGORY = ReportCategory.TIME;

    /**
     *
     */
    public static final ReportType VALID_REPORT_TYPE = ReportType.CLIENT;

    /**
     *
     */
    public static final Column VALID_COLUMN = Column.CLIENT;

    /**
     *
     */
    public static final FilterCategory VALID_FILTER_CATEGORY = FilterCategory.CLIENT;

    /**
     *
     */
    public static final String VALID_LOWER_RANGE = "1";

    /**
     *
     */
    public static final String VALID_UPPER_RANGE = "2";

    /**
     *
     */
    public static final String VALID_FILTER_VALUE = "3";

    /**
     *
     */
    public static final String VALID_PREFIX = "==";

    /**
     *
     */
    public static final String VALID_SUFFIX = "**";

    /**
     *
     */
    public static final String VALID_COLUMN_NAME = "Client";

    /**
     *
     */
    public static final String VALID_DISPLAY_LABEL = "Project";

    /**
     *
     */
    public static final String VALID_START_DATE_FILTER_PARAM_NAME = "StartDate";

    /**
     *
     */
    public static final String VALID_END_DATE_FILTER_PARAM_NAME = "EndDate";

    /**
     *
     */
    public static final String VALID_REPORT_TYPE_PARAM_NAME = "ReportType";

    /**
     *
     */
    public static final String VALID_REPORT_CATEGORY_PARAM_NAME = "ReportCategory";

    /**
     *
     */
    public static final String VALID_BILLABLE_FILTER_PARAM_NAME = "BillableFilter";

    /**
     *
     */
    public static final String VALID_CLIENT_FILTER_PARAM_NAME = "ClientFilter";

    /**
     *
     */
    public static final String VALID_EMPLOYEE_FILTER_PARAM_NAME = "EmployeeFilter";

    /**
     *
     */
    public static final String VALID_PROJECT_FILTER_PARAM_NAME = "ProjectFilter";

    /**
     *
     */
    public static final String VALID_REPORT_TAG_NAMESPACE = "ProjectFilter";

    /**
     *
     */
    public static final String VALID_REPORT_CONFIGURATION_NAMESPACE =
        "com.topcoder.timetracker.report.ReportConfiguration";

    /**
     *
     */
    public static final String VALID_DB_HANDLER_FACTORY_NAMESPACE = "com.topcoder.timetracker.report.DBHandlers";


    /**
     * <p>Generates a new list which contains an invalid element (of correct type) among the elements of
     * <code>Filter</code> type.</p>
     *
     * @return a <code>List</code> containing an invalid element among the elements of <code>Filter</code> type.
     */
    public static List getFiltersListWithNullElement() {
        List result = new ArrayList();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.add(null);
            } else {
                result.add(new RangeFilter(TestDataFactory.VALID_COLUMN, TestDataFactory.VALID_FILTER_CATEGORY));
            }
        }
        return result;
    }

    /**
     * <p>Generates a new list which contains an invalid element (of correct type) among the elements of
     * <code>Filter</code> type.</p>
     *
     * @return a <code>List</code> containing an invalid element among the elements of <code>Filter</code> type.
     */
    public static List getFiltersListWithNonFilterElement() {
        List result = new ArrayList();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.add(new Object());
            } else {
                result.add(new RangeFilter(TestDataFactory.VALID_COLUMN, TestDataFactory.VALID_FILTER_CATEGORY));
            }
        }
        return result;
    }

    /**
     * <p>Generates a new list which contains an invalid element (of correct type) among the elements of
     * <code>ColumnDecorator</code> type.</p>
     *
     * @return a <code>List</code> containing an invalid element among the elements of <code>ColumnDecorator</code>
     *         type.
     */
    public static List getColumnDecoratorsListWithNullElement() {
        List result = new ArrayList();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.add(null);
            } else {
                result.add(new BasicColumnDecorator(TestDataFactory.VALID_COLUMN_NAME,
                    TestDataFactory.VALID_DISPLAY_LABEL));
            }
        }
        return result;
    }

    /**
     * <p>Generates a new list which contains an invalid element (of correct type) among the elements of
     * <code>ColumnDecorator</code> type.</p>
     *
     * @return a <code>List</code> containing an invalid element among the elements of <code>ColumnDecorator</code>
     *         type.
     */
    public static List getColumnDecoratorsListWithNonColumnDecoratorElement() {
        List result = new ArrayList();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.add(new Object());
            } else {
                result.add(new BasicColumnDecorator(TestDataFactory.VALID_COLUMN_NAME,
                    TestDataFactory.VALID_DISPLAY_LABEL));
            }
        }
        return result;
    }

    /**
     * <p>Generates a new map which contains an invalid key (of correct type) among the keys of
     * <code>StyleConstant</code> type.</p>
     *
     * @return a <code>Map</code> containing an invalid key among the keys of <code>StyleConstant</code> type.
     */
    public static Map getStylesMapWithNullKey() {
        Map result = new HashMap();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.put(null, "String");
            } else {
                result.put(StyleConstant.TABLE_STYLE, "String");
            }
        }
        return result;
    }

    /**
     * <p>Generates a new map which contains an invalid key (of correct type) among the keys of
     * <code>StyleConstant</code> type.</p>
     *
     * @return a <code>Map</code> containing an invalid key among the keys of <code>StyleConstant</code> type.
     */
    public static Map getStylesMapWithNonStyleConstantKey() {
        Map result = new HashMap();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.put(new Object(), "String");
            } else {
                result.put(StyleConstant.TABLE_STYLE, "String");
            }
        }
        return result;
    }

    /**
     * <p>Generates a new map which contains an invalid value (of correct type) among the values of <code>String</code>
     * type.</p>
     *
     * @return a <code>Map</code> containing an invalid key among the values of <code>String</code> type.
     */
    public static Map getStylesMapWithNullValue() {
        Map result = new HashMap();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.put(StyleConstant.TABLE_STYLE, null);
            } else {
                result.put(StyleConstant.TABLE_STYLE, "String");
            }
        }
        return result;
    }

    /**
     * <p>Generates a new map which contains an invalid value (of correct type) among the values of <code>String</code>
     * type.</p>
     *
     * @return a <code>Map</code> containing an invalid key among the values of <code>String</code> type.
     */
    public static Map getStylesMapWithNonStringValue() {
        Map result = new HashMap();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                result.put(StyleConstant.TABLE_STYLE, new Object());
            } else {
                result.put(StyleConstant.TABLE_STYLE, "String");
            }
        }
        return result;
    }
}
