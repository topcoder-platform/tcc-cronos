/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * This class is an enumeration and hence is Thread-safe. This enumeration defines the different types of {@link
 * Filter}s possible as a part of this component.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 * @see Filter#getType()
 */
public class FilterType extends Enum {

    /**
     * Represents the enumeration constant for the filter of the type "RANGE".
     */
    public static final FilterType RANGE = new FilterType("RANGE");

    /**
     * Represents the enumeration constant for the filter of the type "EQUALITY".
     */
    public static final FilterType EQUALITY = new FilterType("EQUALITY");

    /**
     * A String specifying the type of the Filter. This instance member will not be <tt>null</tt> in an instantiated
     * object.
     */
    private final String type;

    /**
     * Constructor. Initializes the instance member with the argument passed.
     *
     * @param type the type of the filter
     */
    private FilterType(final String type) {
        this.type = type;
    }

    /**
     * Returns a String representing the type of the Filter.
     *
     * @return the type of the filter
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
}
