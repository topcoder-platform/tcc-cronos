/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;
import com.orpheus.administration.persistence.ParameterHelpers;

/**
 * <p>Abstract base class for a simple filters. It is serializable so it can be used in the Administration EJB
 * framework. It is meant to parallel <code>Filter</code> in the <i>Search Builder</i> component. The parallel is
 * almost exact, but all implementations will ignore the validation and cloning aspects. It also incorporates only
 * the 1.3 version, and all deprecated items have been removed.</p>
 *
 * <p><strong>Thread Safety</strong>: The class is thread-safe since it is immutable.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public abstract class AbstractSimpleFilter implements Filter {
    /**
     * The name of the field being filtered.
     */
    private String fieldName;

    /**
     * Object to use as the basis for comparison of the filter.
     */
    private Comparable value;

    /**
     * Object to use as the upper threshold of the filter.
     */
    private Comparable upperThreshold = null;

    /**
     * Object to use as the lower threshold of the filter.
     */
    private Comparable lowerThreshold = null;

    /**
     * An indication of whether the {@link #upperThreshold upper threshold} is inclusive.
     */
    private boolean isUpperThresholdInclusive = false;

    /**
     * An indication of whether the {@link #lowerThreshold lower threshold} is inclusive.
     */
    private boolean isLowerThresholdInclusive = false;

    /**
     * Creates a new <code>AbstractSimpleFilter</code> with the specified field name and value.
     *
     * @param name the field name
     * @param value the value to filter on
     * @throws IllegalArgumentException if either argument is <code>null</code>
     * @throws IllegalArgumentException if <code>name</code> is an empty string
     */
    protected AbstractSimpleFilter(String name, Comparable value) {
        ParameterHelpers.checkString(name, "simple filer name");

        if (value == null) {
            throw new IllegalArgumentException("filter value must not be null");
        }

        this.fieldName = name;
        this.value = value;
    }

    /**
     * Creates a new <code>AbstractSimpleFilter</code> with the specified field name and upper and lower bound
     * (inclusive).
     *
     * @param name the filter name
     * @param upper the upper bound (inclusive)
     * @param lower the lower bound (inclusive)
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @throws IllegalArgumentException if <code>name</code> is an empty string
     */
    public AbstractSimpleFilter(String name, Comparable upper, Comparable lower) {
        ParameterHelpers.checkString(name, "simple filer name");

        if (upper == null) {
            throw new IllegalArgumentException("upper boundary must not be null");
        }

        if (lower == null) {
            throw new IllegalArgumentException("lower boundary must not be null");
        }

        this.fieldName = name;
        setUpperThreshold(upper, true);
        setLowerThreshold(lower, true);
    }

    /**
     * Returns the name of the field being filtered.
     *
     * @return the name of the field being filtered
     */
    public String getName() {
        return this.fieldName;
    }

    /**
     * Returns the value being filtered on.
     *
     * @return the value being filtered on
     */
    public Comparable getValue() {
        return this.value;
    }

    /**
     * Returns the upper threshold.
     *
     * @return the upper threshold
     */
    public Comparable getUpperThreshold() {
        return this.upperThreshold;
    }

    /**
     * Sets the upper threshold.
     *
     * @param upperThreshold the new upper threshold
     * @param inclusive an indication of whether the upper threshold is inclusive
     */
    void setUpperThreshold(Comparable upperThreshold, boolean inclusive) {
        this.upperThreshold = upperThreshold;
        this.isUpperThresholdInclusive = inclusive;
    }

    /**
     * Returns the lower threshold.
     *
     * @return the lower threshold
     */
    public Comparable getLowerThreshold() {
        return this.lowerThreshold;
    }

    /**
     * Sets the lower threshold.
     *
     * @param lowerThreshold the new lower threshold
     * @param inclusive an indication of whether the lower threshold is inclusive
     */
    void setLowerThreshold(Comparable lowerThreshold, boolean inclusive) {
        this.lowerThreshold = lowerThreshold;
        this.isLowerThresholdInclusive = inclusive;
    }

    /**
     * Returns an indication of whether the {@link #getUpperThreshold upper threshold} is inclusive.
     *
     * @return <code>true</code> if the upper threshold is inclusive, otherwise <code>false</code>
     */
    public boolean isUpperInclusive() {
        return this.isUpperThresholdInclusive;
    }

    /**
     * Returns an indication of whether the {@link #getLowerThreshold lower threshold} is inclusive.
     *
     * @return <code>true</code> if the lower threshold is inclusive, otherwise <code>false</code>
     */
    public boolean isLowerInclusive() {
        return this.isLowerThresholdInclusive;
    }
}
