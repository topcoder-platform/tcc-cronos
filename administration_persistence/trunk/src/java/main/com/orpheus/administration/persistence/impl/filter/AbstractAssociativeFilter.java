/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Common base class of all associative implementations of <code>Filter</code>. This abstract class provides
 * concrete a {@link #addFilter addFilter} method to add a Filter to the associative filter. The constructor is
 * protected, since it is only used by its subclasses. It is meant to parallel the <code>Filter</code> in the
 * <i>Search Builder component</i>. The parallel is almost exact, but all implementations will ignore the
 * validation and cloning aspects. It also incorporates only the 1.3 version, and all deprecated items have been
 * removed.
 *
 * <p><strong>Thread Safety</strong>: The class is not thread-safe since it is mutable.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public abstract class AbstractAssociativeFilter implements Filter {
    /**
     * The list of associated filters. This member is initialized in the constructor and is mutable. It will
     * contain non-<code>null</code> <code>Filter</code> instances.
     */
    private List filters;

    /**
     * Creates a new <code>AbstractAssociativeFilter</code> with the two specified filters.
     *
     * @param firstFilter the first filter to associate
     * @param secondFilter the second filter to associate
     * @throws IllegalArgumentException if either filter is <code>null</code>
     */
    protected AbstractAssociativeFilter(Filter firstFilter, Filter secondFilter) {
        if (firstFilter == null) {
            throw new IllegalArgumentException("first filter must not be null");
        }

        if (secondFilter == null) {
            throw new IllegalArgumentException("second filter must not be null");
        }

        filters = new ArrayList();

        filters.add(firstFilter);
        filters.add(secondFilter);
    }

    /**
     * Creates a new instance of <code>AbstractAssociativeFilter</code> with the specified filter list.
     *
     * @param filters the filter list
     * @throws IllegalArgumentException if <code>filters</code> is <code>null</code>, empty, or contains anything
     *   but non-<code>null</code> <code>Filter</code> instances
     */
    protected AbstractAssociativeFilter(List filters) {
        setFilters(filters);
    }

    /**
     * Adds the specified filter to the association.
     *
     * @param filter the filter to add to the association
     * @throws IllegalArgumentException if <code>filter</code> is <code>null</code>
     */
    public void addFilter(Filter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("the filter must not be null");
        }

        filters.add(filter);
    }

    /**
     * Returns a list of the filters currently comprising the association. The returned list is a copy, so
     * modifying the list does not affect the association.
     *
     * @return a list of the filters currently comprising the association
     */
    public List getFilters() {
        return new ArrayList(filters);
    }

    /**
     * Returns a reference to the <code>filters</code> member, not a copy, for use by subclasses.
     *
     * @return a reference to the <code>filters</code> member
     */
    List getMutableFilters() {
        return filters;
    }

    /**
     * Replaces the filters in the association with the specified filters. The method makes a copy of the list, so
     * subsequent modifications to the list by the caller will not affect the association.
     *
     * @param filters the list of filters to set
     * @throws IllegalArgumentException if <code>filters</code> is <code>null</code>, empty or contains anything
     *   except for non-<code>null</code> instances of <code>Filter</code>
     */
    public void setFilters(List filters) {
        if (filters == null) {
            throw new IllegalArgumentException("filters list must not be null");
        }

        if (filters.isEmpty()) {
            throw new IllegalArgumentException("filters list must not be empty");
        }

        for (Iterator it = filters.iterator(); it.hasNext();) {
            try {
                Filter filter = (Filter) it.next();
                if (filter == null) {
                    throw new IllegalArgumentException("filters list must not contain any null elements");
                }
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException("filters list must contain only Filter objects");
            }
        }

        this.filters = new ArrayList(filters);
    }
}
