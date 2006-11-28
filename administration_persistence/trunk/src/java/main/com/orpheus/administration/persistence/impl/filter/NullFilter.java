/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

/**
 * <p>A subclass of <code>AbstractSimpleFilter</code> that can be used to search for <code>null</code>
 * fields. Depending on the search environment, it may be used to search for explicitly <code>null</code> values,
 * or for search fields when a value is not present. It is serializable so it can
 * be used in the Administration EJB framework. It is meant to parallel the Filter in the Search Builder
 * component. The parallel is almost exact, but all implementations will ignore the validation and cloning
 * aspects. It also incorporates only 1.3 version, and all deprecated items have been removed. </p>
 *
 * <p><strong>Thread Safety</strong>: The class is immutable and therefore thread safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class NullFilter extends AbstractSimpleFilter {
    /**
     * Constructs a <code>NullFilter</code> for the specified field.
     *
     * @param name the field name to filter
     * @throws IllegalArgumentException if <code>name</code> is <code>null</code> or an empty string
     */
    public NullFilter(String name) {
        super(name, "null");
    }

    /**
     * Always returns <code>null</code> to signify that no value is associated with this filter.
     *
     * @return <code>null</code>
     */
    public Comparable getValue() {
        return null;
    }
}
