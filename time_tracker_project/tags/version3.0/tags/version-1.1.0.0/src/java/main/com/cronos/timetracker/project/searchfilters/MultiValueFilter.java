/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.searchfilters;

import java.util.Arrays;


/**
 * <p>
 * This class represents a search filter which compares the values of the specified field with the specified array of
 * values. The array should contain one or more values. It corresponds to the "IN" predicate in SQL query. The entity
 * is accepted if field value is equal to one of the values in the array.
 * </p>
 *
 * <p>
 * This class is immutable, i.e. thread-safe.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public class MultiValueFilter implements Filter {
    /**
     * <p>
     * The name of the field (usually name of column in DB table) to compare the values with. It is initialized in
     * constructor and then never changed. It cannot be null or empty string. Can be retrieved by the like-named
     * getter method.
     * </p>
     */
    private final String fieldName;

    /**
     * <p>
     * The array of values to compare equality with. It is initialized in constructor and then never changed. It cannot
     * be null or empty. The elements should be String instances or instances of either String or one of the wrapper
     * classes (those defined in java.lang package) for the primitive types, or BigDecimal or java.util.date. Note,
     * that all the elements should be of the same type. Can be retrieved by the like-named getter method.
     * </p>
     */
    private final Object[] values;

    /**
     * <p>
     * Constructs an instance of the class with the specified field of the entity, and the specified array of values to
     * compare with.
     * </p>
     *
     * <p>
     * The elements of the array should be String instances or instances of either String or one of the wrapper classes
     * (those defined in java.lang package) for the primitive types, or BigDecimal or java.util.date. Note, that all
     * the elements should be of the same type. A shallow copy of the array is made.
     * </p>
     *
     * @param fieldName the name of the field (usually name of column in DB table) to compare the values with.
     * @param values the array of values to compare equality with.
     *
     * @throws IllegalArgumentException if any of the parameters is null, or fieldName is empty string, or the values
     *         array has invalid elements.
     */
    public MultiValueFilter(String fieldName, Object[] values) {
        if (fieldName == null) {
            throw new IllegalArgumentException("fieldName is null");
        }
        if (fieldName.trim().length() == 0) {
            throw new IllegalArgumentException("fieldName is empty string");
        }
        if (values == null) {
            throw new IllegalArgumentException("values is null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("values is empty array");
        }
        if (Arrays.asList(values).contains(null)) {
            throw new IllegalArgumentException("values contains null element");
        }

        // check if the class of the values is one of the allowed types
        ValueFilter.checkValueType(values[0]);

        // check if values contains elements of the same type
        Class clazz = values[0].getClass();

        for (int i = 1; i < values.length; i++) {
            if (!clazz.equals(values[i].getClass())) {
                throw new IllegalArgumentException("values contains elements of different types");
            }
        }

        // copy the array
        this.fieldName = fieldName;
        this.values = (Object[]) values.clone();
    }

    /**
     * <p>
     * Returns the name of the field (usually name of column in DB table) to compare the values with.
     * </p>
     *
     * @return the name of the field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * <p>
     * Returns a copy of the array of values to compare equality with. The elements of the array should be String
     * instances or instances of either String or one of the wrapper classes (those defined in java.lang package) for
     * the primitive types, or BigDecimal or java.util.date. They are all of the same type.
     * </p>
     *
     * @return the array of values.
     */
    public Object[] getValues() {
        return (Object[]) values.clone();
    }
}
