/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

/**
 * <p>Extension of <code>AbstractSimpleFilter</code> to support <i>less than or equal to</i> relationships. This
 * class is serializable so that it can be used in the Administration EJB framework. It is meant to parallel the
 * <code>Filter</code> class in the <i>Search Builder</i> component. The parallel is almost exact, but all
 * implementations will ignore the validation and cloning aspects. It also incorporates only the 1.3 version, and
 * all deprecated items have been removed.
 *
 * <p><strong>Thread Safety</strong>: The class is immutable and therefore thread safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class LessThanOrEqualToFilter extends AbstractSimpleFilter {
    /**
     * Creates a new <code>LessThanOrEqualToFilter</code> to filter the specified named field by the specified
     * value.
     *
     * @param name the field name to filter
     * @param value the value to filter on
     * @throws IllegalArgumentException if either parameter is <code>null</code>
     * @throws IllegalArgumentException if <code>name</code> is an empty string
     */
    public LessThanOrEqualToFilter(String name, Comparable value) {
        super(name, value);
        setUpperThreshold(value, true);
    }
}
