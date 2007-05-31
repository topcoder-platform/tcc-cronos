/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.criteria;

import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;

/**
 * <p>
 * This class represents a basic type of criteria for a like type of match on a field (like as in the SQL like clause).
 * Both the field and like pattern are given by the user (the where clause expression will be "field like pattern").
 * The field must be fully qualified (table.field) because the search query joins more tables. The class defines 3
 * constants and 3 static methods for the fields the requirements specifically mention (description). This is done in
 * an effort to provide the simplest possible API for the user.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class FieldLikeCriteria implements Criteria {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 377012624336202616L;

    /**
     * Represents the constant for the description field in the expense entry table.
     */
    public static final String DESCRIPTION_FIELD = "expense_entry.description";

    /**
     * Represents the constant for the description field in the expense status table.
     */
    public static final String EXPENSE_STATUS_DESCRIPTION_FIELD = "expense_status.description";

    /**
     * Represents the constant for the description field in the expense type table.
     */
    public static final String EXPENSE_TYPE_DESCRIPTION_FIELD = "expense_type.description";

    /**
     * <p>
     * Represents the field the match should be applied on. The field must be fully qualified (table.field) because the
     * search query joins more tables. Cannot be null, empty or all spaces.
     * </p>
     */
    private final String field;

    /**
     * <p>
     * Represents the pattern that the field should be matched against (using like, can contain the usual %
     * characters). Cannot be null, empty or all spaces.
     * </p>
     */
    private final String pattern;

    /** Represents the where clause of this criteria. */
    private String whereClause;

    /** Represents the parameters of this criteria. */
    private Object[] parameters;

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
        ExpenseEntryHelper.validateString(field, "field");
        ExpenseEntryHelper.validateString(pattern, "pattern");

        this.field = field;
        this.pattern = pattern;
    }

    /**
     * <p>
     * Gets the field the match should be applied on.
     * </p>
     *
     * @return the field the match should be applied on.
     */
    public String getField() {
        return field;
    }

    /**
     * <p>
     * Gets the pattern the field should be matched against.
     * </p>
     *
     * @return the pattern the field should be matched against.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * <p>
     * Gets the where clause expression that can be used in an SQL query to perform the filtering. The expression is
     * expected to contain ? characters where the criteria parameters should be inserted. This is needed because
     * <code>PreparedStatement</code> can be used to execute the query.
     * </p>
     *
     * @return the where clause expression that can be used in an SQL query to perform the filtering.
     */
    public String getWhereClause() {
        if (whereClause == null) {
            this.whereClause = field + " like ?";
        }

        return whereClause;
    }

    /**
     * <p>
     * Gets the parameter values that should be used to fill the where clause expression returned by the
     * <code>getWhereClause</code> method. The parameter values should be inserted in place of the ? characters in the
     * where clause expression. The number of parameters should be equal to the number of ? characters in the where
     * clause expression.
     * </p>
     *
     * @return the parameters used in the expression.
     */
    public Object[] getParameters() {
        if (parameters == null) {
            this.parameters = new String[] {pattern};
        }

        return (Object[]) parameters.clone();
    }

    /**
     * <p>
     * Static shortcut method for creating a description contains match criteria (looks for descriptions containing the
     * given value).
     * </p>
     *
     * @param value the value (cannot be empty but can be all spaces).
     *
     * @return the created description contains match criteria instance.
     *
     * @throws IllegalArgumentException if value argument is <code>null</code> or empty(length of zero).
     */
    public static FieldLikeCriteria getDescriptionContainsCriteria(String value) {
        return getExpenseTypeDescriptionContainsCriteria(DESCRIPTION_FIELD, value);
    }

    /**
     * <p>
     * Static shortcut method for creating a description of expense status contains match criteria (looks for
     * descriptions containing the given value).
     * </p>
     *
     * @param value the value (cannot be empty but can be all spaces).
     *
     * @return the created description contains match criteria instance.
     *
     * @throws IllegalArgumentException if value argument is <code>null</code> or empty(length of zero).
     */
    public static FieldLikeCriteria getExpenseStatusDescriptionContainsCriteria(String value) {
        return getExpenseTypeDescriptionContainsCriteria(EXPENSE_STATUS_DESCRIPTION_FIELD, value);
    }

    /**
     * Static shortcut method for creating a description of expense status contains match criteria (looks for
     * descriptions containing the given value).
     *
     * @param value the value (cannot be empty but can be all spaces).
     *
     * @return the created description contains match criteria instance.
     *
     * @throws IllegalArgumentException if value argument is <code>null</code> or empty(length of zero).
     */
    public static FieldLikeCriteria getExpenseTypeDescriptionContainsCriteria(String value) {
        return getExpenseTypeDescriptionContainsCriteria(EXPENSE_TYPE_DESCRIPTION_FIELD, value);
    }

    /**
     * Static helper method for creating a given description which contains match criteria (looks for descriptions
     * containing the given value).
     *
     * @param description the given description.
     * @param value the value (cannot be empty but can be all spaces).
     *
     * @return the created description contains match criteria instance.
     *
     * @throws IllegalArgumentException if value argument is <code>null</code> or empty(length of zero).
     */
    private static FieldLikeCriteria getExpenseTypeDescriptionContainsCriteria(String description, String value) {
        ExpenseEntryHelper.validateNotNull(value, "value");

        if (value.length() == 0) {
            throw new IllegalArgumentException("value can not be length of zero.");
        }

        return new FieldLikeCriteria(description, "%" + value + "%");
    }
}
