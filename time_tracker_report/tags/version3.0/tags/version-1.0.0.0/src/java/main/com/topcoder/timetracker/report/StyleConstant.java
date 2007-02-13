/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.collection.typesafeenum.Enum;

import java.util.List;


/**
 * This is an enumeration and hence the class is Thread-safe. This class defines constants for the property names for
 * the CSS Styles specified in the configuration file for the Report(Time_Tracker_Report.xml file).
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class StyleConstant extends Enum {

    /**
     * Represents the Style constant which will be used as the property name for specifying the CSS style for the HTML
     * Table tag.
     */
    public static final StyleConstant TABLE_STYLE = new StyleConstant("TABLE_STYLE");

    /**
     * Represents the Style constant which will be used as the property name for specifying the CSS style for the HTML
     * "TH" tag.
     */
    public static final StyleConstant TH_STYLE = new StyleConstant("TH_STYLE");

    /**
     * Represents the Style constant which will be used as the property name for specifying the CSS style for the HTML
     * "TR" tag.
     */
    public static final StyleConstant TR_STYLE = new StyleConstant("TR_STYLE");

    /**
     * Represents the Style constant which will be used as the property name for specifying the CSS style for the HTML
     * "TD" tag.
     */
    public static final StyleConstant TD_STYLE = new StyleConstant("TD_STYLE");

    /**
     * A String specifying the name of the Style constant. This instance member will not be null in an instantiated
     * object.
     * <p/>
     * This value is used as the {@link com.topcoder.util.config.ConfigManager} property name for specifying the CSS
     * Styles for the Report.
     */
    private final String name;

    /**
     * Constructor. Initializes the instance members with the parameter value passed.
     *
     * @param name the name of the Style Constant
     */
    private StyleConstant(final String name) {
        this.name = name;
    }

    /**
     * Returns a String representing the name of the Style constant.
     *
     * @return a String representing the name of the Style constant.
     */
    public String getName() {
        return name;
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
        return name;
    }

    /**
     * Returns all the enumeration constants defined in this enumeration class as a List.
     *
     * @return all the enumeration constants defined in this enumeration class as an unmodifiable List.
     */
    public static List getStyleConstants() {
        return getEnumList(StyleConstant.class);
    }

}
