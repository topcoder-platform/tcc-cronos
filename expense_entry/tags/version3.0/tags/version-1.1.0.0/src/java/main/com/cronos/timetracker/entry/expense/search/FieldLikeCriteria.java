/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.search;

/**
 * <p>
 * This class represents a basic type of criteria for a like type of match on a field (like as in the SQL like clause).
 * Both the field and like pattern are given by the user (the where clause expression will be "field like pattern").
 * The field must be fully qualified (table.field) because the search query joins more tables. The class defines a
 * constant and a static method for the field the requirements specifically mention (description). This is done in an
 * effort to provide the simplest possible API for the user.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @version 1.1
 */
public class FieldLikeCriteria implements Criteria {
    /** Represents the constant for the description field in the expense entry table. */
    public static final String DESCRIPTION_FIELD = "ExpenseEntries.Description";

    /**
     * Represents the field the match should be applied on. The field must be fully qualified (table.field) because the
     * search query joins more tables.
     */
    private final String field;

    /**
     * Represents the pattern that the field should be matched against (using like, can contain the usual %
     * characters).
     */
    private final String pattern;

    /** Represents the where clause of this criteria. */
    private final String whereClause;

    /** Represents the parameters of this criteria. */
    private final Object[] parameters;

    /**
     * <p>
     * Creates a new instance of <code>FieldLikeCriteria</code> class with given field and pattern. The field must be
     * fully qualified (table.field) because the search query joins more tables.
     * </p>
     *
     * @param field the field the match should be applied on.
     * @param pattern the pattern that the field should be matched against.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>, empty or all spaces.
     */
    public FieldLikeCriteria(String field, String pattern) {
        // arguments validation
        if (field == null) {
            throw new IllegalArgumentException("field can not be null.");
        }

        if (field.trim().length() == 0) {
            throw new IllegalArgumentException("field can not be empty string.");
        }

        if (pattern == null) {
            throw new IllegalArgumentException("pattern can not be null.");
        }

        if (pattern.trim().length() == 0) {
            throw new IllegalArgumentException("pattern can not be empty string.");
        }

        // assign the private fields
        this.field = field;
        this.pattern = pattern;
        this.whereClause = field + " like ?";
        this.parameters = new String[] {pattern};
    }

    /**
     * Gets the field the match should be applied on.
     *
     * @return the field the match should be applied on.
     */
    public String getField() {
        return field;
    }

    /**
     * Gets the pattern the field should be matched against.
     *
     * @return the pattern the field should be matched against.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Gets the where clause expression that can be used in an SQL query to perform the filtering. The expression is
     * expected to contain ? characters where the criteria parameters should be inserted. This is needed because
     * <code>PreparedStatement</code> can be used to execute the query.
     *
     * @return the where clause expression that can be used in an SQL query to perform the filtering.
     */
    public String getWhereClause() {
        return this.whereClause;
    }

    /**
     * Gets the parameter values that should be used to fill the where clause expression returned by the
     * <code>getWhereClause</code> method. The parameter values should be inserted in place of the ? characters in the
     * where clause expression. The number of parameters should be equal to the number of ? characters in the where
     * clause expression.
     *
     * @return the parameters used in the expression.
     */
    public Object[] getParameters() {
        return this.parameters;
    }

    /**
     * Static shortcut method for creating a description contains match criteria (looks for descriptions containing the
     * given value).
     *
     * @param value the value (cannot be empty but can be all spaces).
     *
     * @return the created description contains match criteria instance.
     *
     * @throws IllegalArgumentException if value argument is <code>null</code> or empty(length of zero).
     */
    public static FieldLikeCriteria getDescriptionContainsCriteria(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value can not be null.");
        }

        if (value.length() == 0) {
            throw new IllegalArgumentException("value can not be length of zero.");
        }

        return new FieldLikeCriteria(DESCRIPTION_FIELD, "%" + value + "%");
    }
}
