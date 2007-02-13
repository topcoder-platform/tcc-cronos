/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class represents a Filter which of the type "equality". This filter defines that the values which are equal to
 * the Filter value specified needs to be selected.
 * <p/>
 * This filter is multi valued and can define set of equality values to be used.
 * <p/>
 * This class is not thread-safe, but since the application creates new objects of this class for each of the reports
 * and handles it in a thread-safe way, from the view of the entire component, this class will be used by the component
 * in a thread-safe manner.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class EqualityFilter implements Filter {

    /**
     * Represents a List of values for the Filter, that needs to be checked for the equality condition. This List cannot
     * hold <tt>null</tt> elements and will not be empty.
     */
    private final List filterValues;

    /**
     * Represents the Column with which this Filter object is associated
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
     * <p/>
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
     * Creates a new EqualityFilter for the column specified.
     *
     * @param column   the column with which the Filter is associated
     * @param category the category with which the Filter is associated
     *
     * @throws NullPointerException if  any arg passed is <tt>null</tt>
     */
    public EqualityFilter(final Column column, final FilterCategory category) {
        if (column == null) {
            throw new NullPointerException("The argument named [column] was null.");
        }
        if (category == null) {
            throw new NullPointerException("The parameter named [category] was null.");
        }

        this.column = column;
        this.category = category;
        filterValues = new ArrayList();
    }

    /**
     * Adds the specified to filterValue to this List of Filter values.
     *
     * @param filterValue the value for this filter against which the equality check is done
     *
     * @throws NullPointerException     if the filterValue is <tt>null</tt>
     * @throws IllegalArgumentException if the filterValue is an empty (trim'd) String
     */
    public void addFilterValue(final String filterValue) {
        if (filterValue == null) {
            throw new NullPointerException("The parameter named [filterValue] was null.");
        }
        if (filterValue.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [filterValue] was an empty String.");
        }

        filterValues.add(filterValue);
    }

    /**
     * Returns a List of values for the Filter against which the equality checks will be done.
     *
     * @return the unmodifiable list of values that apply to this filter
     */
    public List getFilterValues() {
        return Collections.unmodifiableList(filterValues);
    }

    /**
     * Returns the type of the Filter. For this implementation it will return {@link FilterType#EQUALITY}
     *
     * @return the type of this filter, i.e. {@link FilterType#EQUALITY}
     */
    public FilterType getType() {
        return FilterType.EQUALITY;
    }

    /**
     * Returns the column associated with this Filter object.
     *
     * @return the column associated with this Filter object
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
