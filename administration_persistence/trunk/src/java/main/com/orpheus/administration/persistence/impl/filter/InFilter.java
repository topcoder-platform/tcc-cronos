/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import com.orpheus.administration.persistence.Filter;
import com.orpheus.administration.persistence.ParameterHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An implementation of <code>Filter</code> to support <i>in</i> search criteria. This class is serializable so
 * that it can be used in the Administration EJB framework. It is meant to parallel the <code>Filter</code> class
 * in the <i>Search Builder</i> component. The parallel is almost exact, but all implementations will ignore the
 * validation and cloning aspects. It also incorporates only the 1.3 version, and all deprecated items have been
 * removed.
 *
 * <p><strong>Thread Safety</strong>: The class is immutable and therefore thread safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class InFilter implements Filter {
    /**
     * The name of the field to filter.
     */
    private final String fieldName;

    /**
     * List of non-<code>null</code> <code>Comparable</code> objects representing the value(s) to filter on.
     */
    private final List values;

    /**
     * Creates a new <code>InFilter</code> with the specified field name and values. The values array is copied by
     * the constructor, so subsequent changes made to the array by the caller will not affect the filter.
     *
     * @param name the name of the field to filter
     * @param values the values on which to filter
     * @throws IllegalArgumentException if either parameter is <code>null</code>
     * @throws IllegalArgumentException if <code>name</code> is an empty string
     * @throws IllegalArgumentException if <code>values</code> contains any <code>null</code> elements or any
     *   elements that do not implement <code>Comparable</code>
     */
    public InFilter(String name, List values) {
        ParameterHelpers.checkString(name, "field name");

        if (values == null) {
            throw new IllegalArgumentException("values must not be null");
        }

        if (values.isEmpty()) {
            throw new IllegalArgumentException("values must not be an empty list");
        }

        try {
            for (Iterator it = values.iterator(); it.hasNext();) {
                if (((Comparable) it.next()) == null) {
                    throw new IllegalArgumentException("values must not contain any null elements");
                }
            }
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("values must contain only Comparable objects");
        }

        this.fieldName = name;
        this.values = new ArrayList(values);
    }

    /**
     * Returns the name of the field being filtered.
     *
     * @return the name of the field being filtered
     */
    public String getName() {
        return this.fieldName;
    }

    /**
     * Returns the list of values on which to filter. A copy of the list is returned, so changes made to the
     * returned list by the caller will not affect the filter.
     *
     * @return the list of values on which to filter
     */
    public List getList() {
        return new ArrayList(values);
    }
}
