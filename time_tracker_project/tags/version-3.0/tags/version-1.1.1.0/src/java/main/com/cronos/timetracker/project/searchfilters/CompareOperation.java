/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.searchfilters;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This class is an enumeration type, representing the compare operations to be used by the ValueFilter. It corresponds
 * to the operator in the "WHERE" clause in SQL query.
 * </p>
 *
 * <p>
 * This class is immutable, i.e. thread-safe.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public final class CompareOperation extends Enum {
    /**
     * <p>
     * This constant represents the "=" operation. The result filter accepts only the entities when the value of the
     * specified field is equal to the specified value.
     * </p>
     */
    public static final CompareOperation EQUAL = new CompareOperation("=");

    /**
     * <p>
     * This constant represents the "&lt;" operation. The result filter accepts only the entities when the value of the
     * specified field is less than the specified value.
     * </p>
     */
    public static final CompareOperation LESS = new CompareOperation("<");

    /**
     * <p>
     * This constant represents the "&lt;=" operation. The result filter accepts only the entities when the value of
     * the specified field is less than or equal to the specified value.
     * </p>
     */
    public static final CompareOperation LESS_OR_EQUAL = new CompareOperation("<=");

    /**
     * <p>
     * This constant represents the "&gt;" operation. The result filter accepts only the entities when the value of the
     * specified field is greater than the specified value.
     * </p>
     */
    public static final CompareOperation GREATER = new CompareOperation(">");

    /**
     * <p>
     * This constant represents the "&gt;=" operation. The result filter accepts only the entities when the value of
     * the specified field is greater than or equal to the specified value.
     * </p>
     */
    public static final CompareOperation GREATER_OR_EQUAL = new CompareOperation(">=");

    /**
     * <p>
     * This constant represents the "like" operation. The result filter accepts only the entities when the value of the
     * specified field matches the specified pattern. The pattern may contain any combination of such wildcards:
     *
     * <ul>
     * <li>
     * Underline symbol (_), which can be used to denote any single character in the value to test.
     * </li>
     * <li>
     * Percent sign (%), which replaces any string of zero or more characters in the value to test.
     * </li>
     * </ul>
     *
     * If you need to explicitly specify "_" or "%" characters in the pattern string, you should write them with "\"
     * escape character, e.g. "\_", "\%".
     * </p>
     */
    public static final CompareOperation LIKE = new CompareOperation("like");

    /**
     * <p>
     * The name of a particular compare operation represented by this class. It is initialized in the constructor and
     * then never changed. Cannot be null or empty string.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Constructs an instance of the class with the specified name. It is made private to prevent outside
     * instantiation.
     * </p>
     *
     * @param name the name of a compare operation, must not be null or empty string.
     */
    private CompareOperation(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Returns a string representation of this CompareOperation instance.
     * </p>
     *
     * @return the string representation.
     */
    public String toString() {
        return name;
    }
}
