/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.Filter;
import com.orpheus.administration.persistence.SearchCriteriaDTO;

/**
 * <p>A simple data strucuture implementation of the <code>SearchCriteriaDTO</code> interface.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class SearchCriteriaDTOImpl implements SearchCriteriaDTO {
    /**
     * The search filter wrapped by this DTO. This member is initialized in the constructor, is immutable, and will
     * never be <code>null</code>.
     */
    private final Filter searchFilter;

    /**
     * Constructs a new <code>SearchCriteriaDTOImpl</code> wrapping the specified search filter.
     *
     * @param searchFilter the search filter
     * @throws IllegalArgumentException if <code>searchFilter</code> is <code>null</code>
     */
    public SearchCriteriaDTOImpl(Filter searchFilter) {
        if (searchFilter == null) {
            throw new IllegalArgumentException("search filter must not be null");
        }

        this.searchFilter = searchFilter;
    }

    /**
     * Returns the search filter.
     *
     * @return the search filter
     */
    public Filter getSearchFilter() {
        return this.searchFilter;
    }
}
