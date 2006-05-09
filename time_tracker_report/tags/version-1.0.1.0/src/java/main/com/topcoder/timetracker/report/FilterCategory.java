/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.collection.typesafeenum.Enum;

import java.util.List;


/**
 * This is an enumeration class and is hence Thread-Safe. This class defines the different categories of {@link Filter}
 * for the reports of this component.
 * <p/>
 * It is important to note that not all the FilterCategory defined here may be applicable for every {@link Report}.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class FilterCategory extends Enum {

    /**
     * Represents the enumeration constant for the filter for the "EMPLOYEE" column.
     */
    public static final FilterCategory EMPLOYEE = new FilterCategory("FILTER_EMPLOYEE");

    /**
     * Represents the enumeration constant for the filter for the "PROJECT" column.
     */
    public static final FilterCategory PROJECT = new FilterCategory("FILTER_PROJECT");

    /**
     * Represents the enumeration constant for the filter for the "CLIENT" column.
     */
    public static final FilterCategory CLIENT = new FilterCategory("FILTER_CLIENT");

    /**
     * Represents the enumeration constant for the filter for the "DATE" column.
     */
    public static final FilterCategory DATE = new FilterCategory("FILTER_DATE");

    /**
     * Represents the enumeration constant for the filter for the "BILLABLE" column.
     */
    public static final FilterCategory BILLABLE = new FilterCategory("FILTER_BILLABLE");

    /**
     * A String specifying the category of the Filter. This instance member will not be <tt>null</tt> in an instantiated
     * object.
     * <p/>
     * This value is used as key in the configuration for specifying the properties for the {@link Filter}s.
     */
    private final String category;

    /**
     * Constructor. Initializes the instance member with the argument passed.
     *
     * @param category the category value to be represented by the instance
     */
    private FilterCategory(final String category) {
        this.category = category;
    }

    /**
     * This method returns aString representing the value of this FilterCategory.
     * <p/>
     *
     * @return the category value of this instance
     */
    public String getCategoryValue() {
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
    public static List getFilterCategories() {
        return getEnumList(FilterCategory.class);
    }

}
