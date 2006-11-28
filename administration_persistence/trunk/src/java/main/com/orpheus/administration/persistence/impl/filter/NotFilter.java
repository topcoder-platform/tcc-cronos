/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;

/**
 * <p>An implementation of <code>Filter</code> supporting <i>not</i> search criterion. It is serializable so it can
 * be used in the Administration EJB framework. It is meant to parallel the Filter in the Search Builder
 * component. The parallel is almost exact, but all implementations will ignore the validation and cloning
 * aspects. It also incorporates only 1.3 version, and all deprecated items have been removed. </p>
 *
 * <p><strong>Thread Safety</strong>: The class is immutable and therefore thread safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class NotFilter implements Filter {
    /**
     * The filter to be negated.
     */
    private final Filter filterToNegate;

    /**
     * Creates a new <code>NotFilter</code> negating the specified filter.
     *
     * @param filterToNegate the filter to negate
     * @throws IllegalArgumentException if <code>filterToNegate</code> is <code>null</code>
     */
    public NotFilter(Filter filterToNegate) {
        if (filterToNegate == null) {
            throw new IllegalArgumentException("the filter to negate must not be null");
        }

        this.filterToNegate = filterToNegate;
    }

    /**
     * Returns the filter to be negated.
     *
     * @return the filter to be negated
     */
    public Filter getFilter() {
        return filterToNegate;
    }
}
