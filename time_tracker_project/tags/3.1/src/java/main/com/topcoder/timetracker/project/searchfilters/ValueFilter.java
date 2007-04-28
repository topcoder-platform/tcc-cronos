/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.searchfilters;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * This class represents a search filter which compares the values of the specified field with the specified value,
 * using a specific CompareOperation such as equal to, greater than or less than. The entity is accepted if the
 * operation returns true value, and is not accepted otherwise.
 * </p>
 *
 * <p>
 * This class is immutable, i.e. thread-safe.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public class ValueFilter implements Filter {
    /**
     * <p>
     * The classes that the value can belong to. It consists of the wrapper classes for the primitive types, String,
     * BigDecimal or java.util.date.
     * </p>
     */
    private static final List VALUE_CLASSES;

    /**
     * <p>
     * Initializes the value classes as a List to prevent the conversion from an array every time a check is made.
     * </p>
     */
    static {
        final Class[] valueClasses = {
            Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class,
            Double.class, BigDecimal.class, Date.class, String.class
        };
        VALUE_CLASSES = Arrays.asList(valueClasses);
    }

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
     * This value to compare using the specific CompareOperation. It is initialized in constructor and then never
     * changed. It cannot be null. It can be an instance of either String or one of the wrapper classes (those defined
     * in java.lang package) for the primitive types, or BigDecimal or java.util.date. Can be retrieved by the
     * like-named getter method.
     * </p>
     */
    private final Object value;

    /**
     * <p>
     * Represents an operation to be used to compare the value with the specified field of entity. It is initialized in
     * constructor and then never changed. It cannot be null. Can be retrieved by the like-named getter method.
     * </p>
     */
    private final CompareOperation operation;

    /**
     * <p>
     * Constructs an instance of the class with the specified field of the entity, the specified value to compare with,
     * and the CompareOperation to be used for comparing.
     * </p>
     *
     * <p>
     * The value should be String instance or instance of either String or one of the wrapper classes (those defined in
     * java.lang package) for the primitive types, or BigDecimal or java.util.date.
     * </p>
     *
     * @param operation an operation to be used to compare the value with the specified field of entity.
     * @param fieldName the name of the field (usually name of column in DB table) to compare the values with.
     * @param value the value to compare using the specific CompareOperation.
     *
     * @throws IllegalArgumentException if any of the parameters is null, or fieldName is empty string, or the value is
     *         of the invalid type.
     */
    public ValueFilter(CompareOperation operation, String fieldName, Object value) {
        if (operation == null) {
            throw new IllegalArgumentException("operation is null");
        }
        if (fieldName == null) {
            throw new IllegalArgumentException("fieldName is null");
        }
        if (fieldName.trim().length() == 0) {
            throw new IllegalArgumentException("fieldName is empty string");
        }
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }

        // check if the class of the value is one of the allowed types
        checkValueType(value);

        this.operation = operation;
        this.fieldName = fieldName;
        this.value = value;
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
     * Returns the value to compare using the specific CompareOperation. It should be String instance or instance of
     * either String or one of the wrapper classes (those defined in java.lang package) for the primitive types, or
     * BigDecimal or java.util.date.
     * </p>
     *
     * @return the value to compare.
     */
    public Object getValue() {
        return value;
    }

    /**
     * <p>
     * Returns the operation to be used to compare the value with the specified field of entity.
     * </p>
     *
     * @return the compare operation.
     */
    public CompareOperation getOperation() {
        return operation;
    }

    /**
     * <p>
     * Checks if the class of the value is one of the allowed types.
     * </p>
     *
     * @param value the value to check.
     *
     * @throws IllegalArgumentException if the value is of the invalid type.
     */
    static void checkValueType(Object value) {
        Class clazz = value.getClass();

        if (!VALUE_CLASSES.contains(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not allowed");
        }
    }
}
