/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

/**
 * <p>
 * This class represents a comparison expression which simply, through a single contains() method, allows to specify a
 * search criteria and a partial value to be matched in that field as a substring. A typical usage would be:
 * SubstringExpression expression = SubstringExpression.contains(TimeEntryCriteria.DESCRIPTION, "free"). The class
 * defines one static method in an effort to provide the simplest possible API for the user.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class SubstringExpression implements SearchExpression {
    /**
     * <p>
     * Represents the <code>TimeEntryCriteria</code> used for this expression.
     * </p>
     */
    private TimeEntryCriteria criteria = null;

    /**
     * <p>
     * Represents the substring to look for.
     * </p>
     */
    private String value = null;

    /**
     * <p>
     * Represents the string representation of the expression operator.
     * </p>
     */
    private String operator = null;

    /**
     * <p>
     * Represents the expression string to filter time entries at the SQL query level.
     * </p>
     */
    private String expression = null;

    /**
     * <p>
     * Private constructor to prevent outside instantiation.
     * </p>
     */
    private SubstringExpression() {
        // do nothing here
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>SubstringExpression</code> instance using contains operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value the substring to look for.
     *
     * @return a new <code>SubstringExpression</code> instance using contains operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static SubstringExpression contains(TimeEntryCriteria criteria, String value) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria can not be null.");
        }

        if (value == null) {
            throw new IllegalArgumentException("value can not be null.");
        }

        SubstringExpression substringExpression = new SubstringExpression();
        substringExpression.criteria = criteria;
        substringExpression.value = value;
        substringExpression.operator = "LIKE";
        substringExpression.expression = criteria.getName() + " " + substringExpression.operator + " '%" + value
            + "%'";

        return substringExpression;
    }

    /**
     * <p>
     * Gets the value part of the expression.
     * </p>
     *
     * @return the value part of the expression.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * <p>
     * Gets the expression string to filter time entries at the SQL query level.
     * </p>
     *
     * @return the expression string to filter time entries at the SQL query level.
     */
    public String getSearchExpressionString() {
        return this.expression;
    }

    /**
     * <p>
     * Gets the criteria part of the expression.
     * </p>
     *
     * @return the criteria part of the expression.
     */
    public TimeEntryCriteria getCriteria() {
        return this.criteria;
    }

    /**
     * <p>
     * Gets the string representation of the expression operator.
     * </p>
     *
     * @return the string representation of the expression operator.
     */
    public String getOperator() {
        return this.operator;
    }
}
