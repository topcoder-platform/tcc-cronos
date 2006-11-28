/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

/**
 * <p>Extension of <code>AbstractSimpleFilter</code> to support <i>between</i> relationships. This class is
 * serializable so that it can be used in the Administration EJB framework. It is meant to parallel the
 * <code>Filter</code> class in the <i>Search Builder</i> component. The parallel is almost exact, but all
 * implementations will ignore the validation and cloning aspects. It also incorporates only the 1.3 version, and
 * all deprecated items have been removed.
 *
 * <p><strong>Thread Safety</strong>: The class is immutable and therefore thread safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class BetweenFilter extends AbstractSimpleFilter {
    /**
     * <p>Creates a new <code>BetweenFilter</code> that will filter the specified field within the specified values
     * (inclusive).
     *
     * @param name the field name of the search criterion
     * @param upper the upper threshold to filter (inclusive)
     * @param lower the lower threshold to filter (inclusive)
     * @throws IllegalArgumentException if any parameter is <code>null</code>
     * @throws IllegalArgumentException if <code>name</code> is an empty string
     */
    public BetweenFilter(String name, Comparable upper, Comparable lower) {
        super(name, upper, lower);
    }
}
