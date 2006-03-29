/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.searchfilters;

/**
 * <p>
 * This class represents a search filter that negates a search filter. It performs the logical "NOT" operation on the
 * specified filter. That means, that this filter will accept only these values, which are not accepted by the
 * specified filter.
 * </p>
 *
 * <p>
 * This class is immutable, i.e. thread-safe.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public class NotFilter implements Filter {
    /**
     * <p>
     * The filter to be negated, which is the operand of the logical "NOT" filter represented by this class. It is
     * initialized in constructor and then never changed. It cannot be null. Can be retrieved by the like-named getter
     * method.
     * </p>
     */
    private final Filter operand;

    /**
     * <p>
     * Constructs an instance of the class with the specified filter to be negated.
     * </p>
     *
     * @param operand the filter to be negated.
     *
     * @throws IllegalArgumentException if the operand is null.
     */
    public NotFilter(Filter operand) {
        if (operand == null) {
            throw new IllegalArgumentException("operand is null");
        }
        this.operand = operand;
    }

    /**
     * <p>
     * Returns the filter to be negated. This is the operand of the logical "NOT" filter represented by this class.
     * </p>
     *
     * @return the filter to be negated.
     */
    public Filter getOperand() {
        return operand;
    }
}
