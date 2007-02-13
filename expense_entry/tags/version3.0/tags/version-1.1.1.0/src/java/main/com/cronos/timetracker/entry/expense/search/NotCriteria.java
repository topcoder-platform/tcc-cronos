/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.search;

/**
 * <p>
 * This class represents a special type of criteria that simply negates the expression of another criteria. It contains
 * another criteria and the where clause return method delegates the call to that criteria and surrounds the result
 * with brackets and prefixes it with NOT. The parameters are the same as the contained criteria (since no new
 * parameters are inserted).
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @version 1.1
 */
public class NotCriteria implements Criteria {
    /** Represents the contained criteria which will be negated. */
    private final Criteria criteria;

    /** Represents the where clause of this criteria. */
    private final String whereClause;

    /**
     * <p>
     * Creates a new instance of <code>NotCriteria</code> class with given criteria which will be negated.
     * </p>
     *
     * @param criteria the contained criteria which will be negated.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>.
     */
    public NotCriteria(Criteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria can not be null.");
        }

        this.criteria = criteria;
        this.whereClause = "NOT (" + criteria.getWhereClause() + ")";
    }

    /**
     * Gets the contained criteria which will be negated.
     *
     * @return the contained criteria which will be negated.
     */
    public Criteria getCriteria() {
        return this.criteria;
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
     * @return the parameters used in the expression
     */
    public Object[] getParameters() {
        return criteria.getParameters();
    }
}
