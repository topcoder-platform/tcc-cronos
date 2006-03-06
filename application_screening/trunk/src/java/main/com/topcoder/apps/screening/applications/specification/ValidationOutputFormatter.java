/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ValidationOutputFormatter.java
 */
package com.topcoder.apps.screening.applications.specification;

/**
 * <p>
 * This interface declares a formatting entity. Formatters should be able to accept array of validation output
 * containing both error information and reports and convert this data to a human readable representation.
 * Each concrete formatter can define its own output format.
 * </p>
 *
 * <p>
 * Thread-Safety: all implementations of this interface are expected to be thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public interface ValidationOutputFormatter {
    /**
     * <p>
     * This is a declaration of the formatting method. The method should format the given validation output to a
     * human readable representation. The behavior of this method is implementation dependent.
     * </p>
     *
     * @param output the data to format
     * @return the formatted data
     * @throws FormatterException       if the formatting fails because of any reason
     * @throws IllegalArgumentException if the array or any item in the array is null
     */
    public String[] format(ValidationOutput[] output) throws FormatterException;
}









