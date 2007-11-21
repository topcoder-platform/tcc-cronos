/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.criteria;

import java.util.List;

import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;

/**
 * <p>
 * This class represents a basic type of criteria for matching a field to several values (realized with the use of
 * IN-operator in SQL). The name of the field ad the list of possible values are specified by the user (the where clause
 * expression will look like "field IN (comma-separated-list-of-values)").
 * </p>
 * <p>
 * <b>Thread Safety:</b> Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author George1
 * @version 3.2
 */
public class FieldInCriteria implements Criteria {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 8107160470184521441L;

    /**
     * Represents the field the match should be applied on. The field must be fully qualified (table.field) because the
     * search query joins several tables. Cannot be <code>null</code>, empty empty string.
     */
    private final String field;

    /**
     * Represents the values that the field should be matched against (using IN operator). Cannot be <code>null</code>
     * or empty list.
     */
    private final List values;

    /**
     * Represents the where clause of this criteria.
     */
    private String whereClause;

    /**
     * Represents the parameters of this criteria.
     */
    private Object[] parameters;

    /**
     * <p>
     * Creates a new instance of <code>FieldInCriteria</code> class with given field and list
     * values. The field must be fully qualified (table.field) because the search query joins
     * several tables.
     * </p>
     *
     * @param field
     *            the field the match should be applied on.
     * @param values
     *            the list pf values that the field should be matched against.
     * @throws IllegalArgumentException
     *             if <code>field</code> is <code>null</code> or an empty string, or if
     *             <code>values</code> is <code>null</code> or empty.
     */
    public FieldInCriteria(String field, List values) {
        ExpenseEntryHelper.validateString(field, "field");
        ExpenseEntryHelper.validateObjectList(values, "values", 1);
        this.field = field;
        this.values = values;
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
     * Gets the list of values that the field should be matched against.
     *
     * @return the values that the field should be matched against.
     */
    public Object getValues() {
        return this.values;
    }

    /**
     * Gets the where clause expression that can be used in an SQL query to perform the filtering. The expression is
     * expected to contain ? characters where the criteria parameters should be inserted. This is needed because
     * <code>PreparedStatement</code> can be used to execute the query.
     *
     * @return the where clause expression that can be used in an SQL query to perform the filtering.
     */
    public String getWhereClause() {
        if (this.whereClause == null) {
            StringBuffer clause = new StringBuffer(field);

            clause.append(" IN (");
            for (int i = this.values.size()-1; i >= 0; --i) {
                clause.append('?');
                if (i != 0) {
                    clause.append(',');
                }
            }

            clause.append(')');
            this.whereClause = clause.toString();
        }

        return this.whereClause;
    }

    /**
     * Gets the parameter values that should be used to fill the WHERE clause expression returned by the
     * <code>getWhereClause</code> method. The parameter values should be inserted in place of the ? characters in the
     * WHERE clause expression. The number of parameters should be equal to the number of ? characters in the WHERE
     * clause expression.
     *
     * @return the parameters used in the expression.
     */
    public Object[] getParameters() {
        if (this.parameters == null) {
            this.parameters = this.values.toArray();
        }
        return this.parameters;
    }
}
