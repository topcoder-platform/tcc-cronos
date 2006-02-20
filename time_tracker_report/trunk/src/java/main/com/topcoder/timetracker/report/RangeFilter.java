/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class represents a Filter which of the type range. This filter defines that the values within the range
 * specified by the lower and upper bound needs to be selected.
 * <p/>
 * This filter is multi valued and can define set of ranges to be used.
 * <p/>
 * This class is not thread-safe, but since the application creates new objects of this class for each of the reports
 * and handles it in a thread-safe way, from the view of the entire component, this class will be used by the component
 * in a thread-safe manner.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class RangeFilter implements Filter {

    /**
     * A List containing all the lower range values for the filter. This instance member cannot be <tt>null</tt>. This
     * List cannot hold <tt>null</tt> elements.
     */
    private final List lowerBounds;

    /**
     * A List containing all the upper range values for the filter. This instance member cannot be <tt>null</tt>. This
     * List cannot hold <tt>null</tt> elements.
     */
    private final List upperBounds;

    /**
     * Represents the Column which this Filter object is associated with.
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     */
    private final Column column;
    /**
     * Represents the {@link FilterCategory} with which this Filter object is associated
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     * <p/>
     */
    private final FilterCategory category;

    /**
     * Creates a new RangeFilter associated with the column specified.
     *
     * @param column   the column with which the Filter is associated
     * @param category the category with which the Filter is associated
     *
     * @throws NullPointerException if  any arg passed is <tt>null</tt>
     */
    public RangeFilter(final Column column, final FilterCategory category) {
        if (column == null) {
            throw new NullPointerException("The parameter named [column] was null.");
        }
        if (category == null) {
            throw new NullPointerException("The parameter named [category] was null.");
        }

        this.column = column;
        this.category = category;
        lowerBounds = new ArrayList();
        upperBounds = new ArrayList();
    }

    /**
     * Adds the specified range specified as lower bound and upper bound to the range list of this Filter.
     *
     * @param lower the lower bound of the range
     * @param upper the upper bound of the range
     *
     * @throws NullPointerException     if any arg is <tt>null</tt>
     * @throws IllegalArgumentException if any arg is an empty (trim'd) String
     */
    public void addFilterRange(final String lower, final String upper) {
        if (lower == null) {
            throw new NullPointerException("The parameter named [lower] was null.");
        }
        if (upper == null) {
            throw new NullPointerException("The parameter named [upper] was null.");
        }
        if (lower.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [lower] was an empty String.");
        }
        if (upper.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [upper] was an empty String.");
        }
        lowerBounds.add(lower);
        upperBounds.add(upper);
    }

    /**
     * Returns a List containing the Lower bound values for this Filter.
     *
     * @return an unmodifiable List containing the Lower range values for this Filter.
     */
    public List getLowerRangeValues() {
        return Collections.unmodifiableList(lowerBounds);
    }

    /**
     * Returns a List containing the Upper bound values for this Filter.
     *
     * @return an unmodifiable List containing the Lower range values for this Filter.
     */
    public List getUpperRangeValues() {
        return Collections.unmodifiableList(upperBounds);
    }

    /**
     * Returns the type of the Filter. For this implementation it will return {@link FilterType#RANGE}.
     *
     * @return the type of this filter, i.e. {@link FilterType#RANGE}
     */
    public FilterType getType() {
        return FilterType.RANGE;
    }

    /**
     * Returns the column associated with this Filter object.
     *
     * @return the column associated with this Filter object.
     */
    public Column getColumn() {
        return column;
    }

    /**
     * Returns the {@link FilterCategory} of this Filter.
     *
     * @return the non-<tt>null</tt> FilterCategory of this Filter
     */
    public FilterCategory getCategory() {
        return category;
    }

}
