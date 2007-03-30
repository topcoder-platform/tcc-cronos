/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * DateFormatContext holds the date format attributes like date pattern, time zone, locale, etc.
 * which will be used by <c>XMLMessage</c> concrete implementations to format the timestamp
 * in their implementations for <c>toXMLString(DateFormatContext)</c> method.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is not thread safe since it’s mutable.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class DateFormatContext {

    /**
     * The key used to get the locale.
     */
    public static final String LOCALE_KEY = "locale";

    /**
     * The key used to get the time zone.
     */
    public static final String TIMEZONE_KEY = "time_zone";

    /**
     * The key used to get the date format.
     */
    public static final String DATE_FORMAT_KEY = "date_format";

    /**
     * Represents the map which holds the date format context attributes.
     * The key is the non-null and non-empty string.
     * The value is the non-null and non-empty string.
     */
    private Map attributes = new HashMap();

    /**
     * Creates a new instance of the class.
     */
    public DateFormatContext() {
        // Empty constructor.
    }

    /**
     * Get all the context attributes.
     *
     * @return A copy of all the context attributes.
     */
    public Map getAttributes() {
        return new HashMap(attributes);
    }

    /**
     * Get the value for the specified attribute name.
     *
     * @param name The attribute name.
     * @return The attribute value.
     * @throws IllegalArgumentException If <c>name</c> argument is null, or empty string.
     */
    public String getAttribute(String name) {
        Helper.validateNotNullOrEmpty(name, "name");
        return (String) attributes.get(name);
    }

    /**
     * Removes the attribute with the specified name.
     *
     * @param name The attribute name.
     * @return The value for the removed attribute.
     * @throws IllegalArgumentException if argument is null, or empty string.
     */
    public String removeAttribute(String name) {
        Helper.validateNotNullOrEmpty(name, "name");
        return (String) attributes.remove(name);
    }

    /**
     * Adds the attribute name and value to the attributes of this context.
     * If there is already an attribute with the given key between the attributes
     * of the context, then its value will be replaced with the specified <c>value</c>
     * of the method.
     *
     * @param name  The attribute's name.
     * @param value The attribute's value.
     * @throws IllegalArgumentException If any of the arguments is null, or empty string
     */
    public void addAttribute(String name, String value) {
        Helper.validateNotNullOrEmpty(name, "name");
        Helper.validateNotNullOrEmpty(value, "value");

        attributes.put(name, value);
    }

    /**
     * Check if the attribute with the given name exists.
     *
     * @param name The attribute's name.
     * @return true if the attribute exists, false otherwise.
     * @throws IllegalArgumentException If the specified argument is null, or empty string.
     */
    public boolean contains(String name) {
        Helper.validateNotNullOrEmpty(name, "name");
        return attributes.containsKey(name);
    }
}
