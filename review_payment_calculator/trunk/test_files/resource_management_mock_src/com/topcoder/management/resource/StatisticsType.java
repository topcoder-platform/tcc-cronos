package com.topcoder.management.resource;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * This class represents the statistics type enum. It's extends from Enum class in Typesafe Enum component.
 * 
 * Thread Safety: it's immutable and thread safe.
 */
public class StatisticsType extends Enum {
    private String name;
    
    /**
     * Represents the enum value for AVERGE statistics type.
     */
    public static final StatisticsType AVERGAE = new StatisticsType("AVERAGE");

    /**
     * Represents the enum value for HISTORY statistics type.
     */
    public static final StatisticsType HISTORY = new StatisticsType("HISTORY");

    /**
     * Creates an instance of this class.
     * 
     * ###Param
     * name - the statistics type name
     * 
     * ###Impl
     * super(name);
     * 
     * ###Exceptions
     * None
     * @param name 
     */
    private StatisticsType(String name) {
        this.name = name;
    }
}

