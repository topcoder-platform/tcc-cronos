/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import com.topcoder.util.collection.typesafeenum.Enum;

import java.util.List;


/**
 * This is an Enumeration class and is thread-safe. This class defines the different types of Reports possible.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportType extends Enum {

    /**
     * Represents the enumeration constant for the "EMPLOYEE" type of Report.
     */
    public static final ReportType EMPLOYEE = new ReportType("EMPLOYEE");

    /**
     * Represents the enumeration constant for the "PROJECT" type of Report.
     */
    public static final ReportType PROJECT = new ReportType("PROJECT");

    /**
     * Represents the enumeration constant for the "CLIENT" type of Report.
     */
    public static final ReportType CLIENT = new ReportType("CLIENT");

    /**
     * A String specifying the type of the Report. This instance member will not be null in an instantiated object.
     */
    private final String type;

    /**
     * Constructor. Initializes the instance members with the parameter value passed.
     * <p/>
     *
     * @param type the type of the Report
     */
    private ReportType(final String type) {
        this.type = type;
    }

    /**
     * Returns a string representing the type of the report.
     *
     * @return the type of the Report
     */
    public String getType() {
        return type;
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
        return type;
    }

    /**
     * Returns all the enumeration constants defined in this enumeration class as a List.
     *
     * @return all the enumeration constants defined in this enumeration class as an unmodifiable List.
     */
    public static List getReportTypes() {
        return getEnumList(ReportType.class);
    }

}
