/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.searchfilters;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This class is an enumeration type, representing the binary logical operations to be used by the
 * BinaryOperationFilter. It corresponds to the operator in the "WHERE" clause in SQL query.
 * </p>
 *
 * <p>
 * This class is immutable, i.e. thread-safe.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public final class BinaryOperation extends Enum {
    /**
     * <p>
     * This constant represents the logical "AND" operation. The result filter accepts only the entities when the
     * conditions of both operands are true.
     * </p>
     */
    public static final BinaryOperation AND = new BinaryOperation("AND");

    /**
     * <p>
     * This constant represents the logical "OR" operation. The result filter accepts only the entities when the
     * condition of any operand is true.
     * </p>
     */
    public static final BinaryOperation OR = new BinaryOperation("OR");

    /**
     * <p>
     * The name of a particular binary logical operation represented by this class. It is initialized in the
     * constructor and then never changed. Cannot be null or empty string.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Constructs an instance of the class with the specified name. It is made private to prevent outside
     * instantiation.
     * </p>
     *
     * @param name the name of a binary logical operation, must not be null or empty string.
     */
    private BinaryOperation(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Returns a string representation of this BinaryOperation instance.
     * </p>
     *
     * @return the string representation.
     */
    public String toString() {
        return name;
    }
}
