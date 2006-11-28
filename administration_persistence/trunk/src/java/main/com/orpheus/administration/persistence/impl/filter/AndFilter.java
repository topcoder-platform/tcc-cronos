/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;

import java.util.List;

/**
 * <p>Extension of <code>AbstractAssociativeFilter</code> to support <i>AND</i> relationships. This class is
 * serializable so that it can be used in the Administration EJB framework. It is meant to parallel the
 * <code>Filter</code> class in the <i>Search Builder</i> component. The parallel is almost exact, but all
 * implementations will ignore the validation and cloning aspects. It also incorporates only the 1.3 version, and
 * all deprecated items have been removed.
 *
 * <p><strong>Thread Safety</strong>: The class is not thread-safe since it is mutable.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class AndFilter extends AbstractAssociativeFilter {
    /**
     * Creates a new <code>AndFilter</code> joining the two specified filters.
     *
     * @param augend the first filter in the association
     * @param addend the second filter in the associatoin
     * @throws IllegalArgumentException if either argument is <code>null</code>
     */
    public AndFilter(Filter augend, Filter addend) {
        super(augend, addend);
    }

    /**
     * Creates a new <code>AndFilter</code> joining the specified list of filters. The list is copied by the
     * constructor, so any subsequent modifications made to the list by the caller will not affect the association.
     *
     * @param filters the list of filters to associate
     * @throws IllegalArgumentException if <code>filters</code> is <code>null</code>, empty, or contains anything
     *   except for non-<code>null</code> instances of <code>Filter</code>
     */
    public AndFilter(List filters) {
        super(filters);
    }
}
