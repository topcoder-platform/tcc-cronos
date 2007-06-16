/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.criteria;

import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;

/**
 * <p>
 * This class represents a basic type of criteria for matching for NULL of a field. The field is
 * given by the user (the where clause expression will look like "field IS NULL").
 * </p>
 * <p>
 * <b>Thread Safety:</b> Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author George1
 * @version 3.2
 */
public class FieldNullCriteria implements Criteria {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 739029546825473218L;

    /**
     * Represents the field the match should be applied on. The field must be fully qualified
     * (table.field) because the search query joins more tables. Cannot be null, empty or all
     * spaces.
     */
    private final String field;

    /**
     * Represents the where clause of this criteria.
     */
    private String whereClause;

    /**
     * <p>
     * Creates a new instance of {@link FieldNullCriteria} class with given field. The field must be
     * fully qualified (table.field) because the search query joins more tables.
     * </p>
     *
     * @param field
     *            the field the match should be applied on.
     * @throws IllegalArgumentException
     *             if <code>field</code> is <code>null</code>, or an empty string.
     */
    public FieldNullCriteria(String field) {
        ExpenseEntryHelper.validateString(field, "field");
        this.field = field;
        this.whereClause = field + " IS NULL";
    }

    /**
     * Gets the field the match should be applied on.
     *
     * @return the field the match should be applied on.
     */
    public String getField() {
        return this.field;
    }

    /**
     * Gets the where clause expression that can be used in an SQL query to perform the filtering.
     *
     * @return the where clause expression that can be used in an SQL query to perform the
     *         filtering.
     */
    public String getWhereClause() {
        return this.whereClause;
    }

    /**
     * Gets the parameter values that should be used to fill the where clause expression returned by
     * the <code>getWhereClause</code> method. Since this criteria supposes only one value --
     * NULL, this method always returns an empty array.
     *
     * @return always returns empty array.
     */
    public Object[] getParameters() {
        return new Object[0];
    }
}
