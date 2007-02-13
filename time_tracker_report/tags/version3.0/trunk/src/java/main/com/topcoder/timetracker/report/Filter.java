/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

/**
 * This interface defines the behavior of a Filter. A Filter is responsible for a particular {@link Column} and a Filter
 * also has a specific {@link FilterType}, which defines its nature(equality filter or range filter or greater than
 * filter etc.) and a {@link FilterCategory} which expresses on what aspect of the data the filter applies.
 * <p/>
 * The implementors of this interface provide the behavior required based on its type.
 * <p/>
 * The implementors of this interface may not be thread-safe. The component creates new Filter objects and ensures that
 * it uses the Filter objects in a thread-safe manner.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public interface Filter {
    /**
     * Returns the type of the Filter. The value is one among the values defined by the {@link FilterType} enumeration.
     *
     * @return the non-<tt>null</tt> type of the Filter
     */
    public FilterType getType();

    /**
     * Returns the {@link Column} with which this Filter is associated. The values for the column will be filtered based
     * on this filter.
     *
     * @return the non-<tt>null</tt> Column with which this Filter is associated
     */
    public Column getColumn();

    /**
     * Returns the {@link FilterCategory} of this Filter, which specifies on which aspect of the feport this filter
     * applies.
     *
     * @return the non-<tt>null</tt> FilterCategory of this Filter
     */
    public FilterCategory getCategory();
}


