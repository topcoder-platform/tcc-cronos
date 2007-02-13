/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class represents a Filter which of the type range. This filter defines that the values
 * the field should matched.
 * <p/>
 * This filter is one or multi valued.
 * <p/>
 * This class is not thread-safe, but since the application creates new objects of this class for each of the reports
 * and handles it in a thread-safe way, from the view of the entire component, this class will be used by the component
 * in a thread-safe manner.
 *
 * @author arylio
 * @version 2.0
 * @since 1.0
 */
public class InFilter implements Filter {
    /**
     * <p>
     * The value set the column value will be.
     * </p>
     */
    private final Set values = new HashSet();

    /**
     * Represents the Column which this Filter object is associated with.
     * <p/>
     * This instance member will not be <tt>null</tt> in an instantiated object.
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
     * Creates a new InFilter associated with the column specified.
     *
     * @param column   the column with which the Filter is associated
     * @param category the category with which the Filter is associated
     *
     * @throws NullPointerException if  any arg passed is <tt>null</tt>
     */
    public InFilter(final Column column, final FilterCategory category) {
        if (column == null) {
            throw new NullPointerException("The parameter named [column] was null.");
        }
        if (category == null) {
            throw new NullPointerException("The parameter named [category] was null.");
        }

        this.column = column;
        this.category = category;
    }

    /**
     * <p>
     * Add the specified value.
     * </p>
     *
     * @param value the value to added.
     */
    public void addFilterValue(final String value) {
        if (value == null) {
            throw new NullPointerException("The parameter named [value] was null.");
        }
        if (value.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [value] was an empty String.");
        }
        values.add(value);
    }

    /**
     * <p>
     * Get the values.
     * </p>
     *
     * @return the values.
     */
    public Set getValues() {
        return Collections.unmodifiableSet(values);
    }

    /**
     * <p>
     * Get the string represent.
     * </p>
     *
     * @return the string of value used in in clause.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        boolean first = true;
        for (Iterator itor = values.iterator(); itor.hasNext();) {
            if (!first) {
                buf.append(",");
            }
            buf.append((String) itor.next());
            first = false;
        }
        return buf.toString();
    }

    /**
     * Returns the type of the Filter. For this implementation it will return {@link FilterType#IN}.
     *
     * @return the type of this filter, i.e. {@link FilterType#IN}
     */
    public FilterType getType() {
        return FilterType.IN;
    }

    /**
     * Returns the column associated with this Filter object.
     *
     * @return the column associated with this Filter object.
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
