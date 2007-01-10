/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.collection.typesafeenum.Enum;

import java.util.List;


/**
 * This class is an enumeration class and hence is thread-safe. This enumeration class defines the different category of
 * Reports possible.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportCategory extends Enum {
    /**
     * Represents the enumeration constant for the "TIME" category Report.
     */
    public static final ReportCategory TIME = new ReportCategory("TIME");

    /**
     * Represents the enumeration constant for the "EXPENSE" category Report.
     */
    public static final ReportCategory EXPENSE = new ReportCategory("EXPENSE");

    /**
     * Represents the enumeration constant for the "TIMEEXPENSE" category Report.
     */
    public static final ReportCategory TIMEEXPENSE = new ReportCategory("TIMEEXPENSE");

    /**
     * A String specifying the category name of this ReportCategory. This instance member will not be <tt>null</tt> in
     * an instantiated object.
     */
    private final String category;

    /**
     * Constructor. Initializes the instance members with the parameter value passed.
     *
     * @param category a String representing the category of the Report.
     */
    private ReportCategory(final String category) {
        this.category = category;
    }

    /**
     * Returns a String representing the category of the report.
     *
     * @return a String representing the category of the report
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns String representation of the Enum. The String representation returned is the identifying value of an
     * instance of this class.
     * <p/>
     * This will ensure that all enum instances of this type can be looked up using {@link
     * Enum#getEnumByStringValue(String, Class)}.
     *
     * @return String representation of the Enum
     */
    public String toString() {
        return category;
    }

    /**
     * Returns all the enumeration constants defined in this enumeration class as a List.
     *
     * @return all the enumeration constants defined in this enumeration class as an unmodifiable List.
     */
    public static List getReportCategories() {
        return getEnumList(ReportCategory.class);
    }

}
