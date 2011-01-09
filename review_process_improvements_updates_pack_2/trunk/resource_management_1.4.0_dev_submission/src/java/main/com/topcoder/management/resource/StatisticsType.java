/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This class represents the statistics type enum. It's extends from Enum class in Typesafe Enum component.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: it's immutable and thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class StatisticsType extends Enum {
    /**
     * Represents the enum value for AVERGE statistics type.
     */
    public static final StatisticsType AVERAGE = new StatisticsType("AVERAGE");

    /**
     * Represents the enum value for HISTORY statistics type.
     */
    public static final StatisticsType HISTORY = new StatisticsType("HISTORY");

    /**
     * Holds the name of the enumeration.
     */
    private final String name;

    /**
     * Creates an instance of this class.
     *
     * @param name the statistics type name
     */
    private StatisticsType(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the enumeration.
     *
     * @return the name of the enumeration
     */
    public String getName() {
        return name;
    }
}
