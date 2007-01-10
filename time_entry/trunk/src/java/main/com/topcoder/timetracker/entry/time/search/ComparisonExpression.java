/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

/**
 * <p>
 * This class represents a comparison expression which simply, through equals() notEquals() lessThan()
 * lessThanOrEqual() greaterThan() greaterThanOrEqual methods, allows to combine search criteria and values. A typical
 * usage would be: ComparisonExpression expression = ComparisonExpression.equals(TimeEntryCriteria.CREATION_USER,
 * "Ivern"). The class defines 6 static methods in an effort to provide the simplest possible API for the user.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * <p>
 * Changes in 2.0: since the search criteria is not only for time entry.
 * In 2.0, added abstract class Criteria, entity's search criteria should extends from Criteria.
 * Changes the argument TimeEntryCriteria of constructors and getCriteria to Criteria.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @author arylio
 * @version 2.0
 * @since 1.1
 */
public class ComparisonExpression implements SearchExpression {
    /** Represents the string representation of the equals operator. */
    private static final String EQUALS_OPERATOR = "=";

    /** Represents the string representation of the notEquals operator. */
    private static final String NOT_EQUALS_OPERATOR = "<>";

    /** Represents the string representation of the lessThan operator. */
    private static final String LESS_THAN_OPERATOR = "<";

    /** Represents the string representation of the lessThanOrEqual operator. */
    private static final String LESS_THAN_OR_EQUAL_OPERATOR = "<=";

    /** Represents the string representation of the greaterThan operator. */
    private static final String GREATER_THAN_OPERATOR = ">";

    /** Represents the string representation of the greaterThanOrEqual operator. */
    private static final String GREATER_THAN_OR_EQUAL_OPERATOR = ">=";

    /**
     * <p>
     * Represents the <code>Criteria</code> used for this expression.
     * </p>
     */
    private Criteria criteria = null;

    /**
     * <p>
     * Represents the value used for this expression. The class itself will not validate the value itself, since the
     * interpretation of its meaning will be done at evaluation time.
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
    private ComparisonExpression() {
        // do nothing here
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>ComparisonExpression</code> instance using equals operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value value for this expression.
     *
     * @return a new <code>ComparisonExpression</code> instance using equals operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static ComparisonExpression equals(Criteria criteria, String value) {
        return createComparisonExpression(criteria, value, EQUALS_OPERATOR);
    }

    /**
     * <p>
     * Creates a new <code>ComparisonExpression</code> instance using given criteria, value and operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value value for this expression.
     * @param operator the given operator.
     *
     * @return a new <code>ComparisonExpression</code> instance using given criteria, value and operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    private static ComparisonExpression createComparisonExpression(Criteria criteria, String value,
        String operator) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria can not be null.");
        }

        if (value == null) {
            throw new IllegalArgumentException("value can not be null.");
        }

        ComparisonExpression comparisonExpression = new ComparisonExpression();
        comparisonExpression.criteria = criteria;
        comparisonExpression.value = value;
        comparisonExpression.operator = operator;
        comparisonExpression.expression = criteria.getName() + " " + operator + " " + value;

        return comparisonExpression;
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>ComparisonExpression</code> instance using notEquals operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value value for this expression.
     *
     * @return a new <code>ComparisonExpression</code> instance using notEquals operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static ComparisonExpression notEquals(Criteria criteria, String value) {
        return createComparisonExpression(criteria, value, NOT_EQUALS_OPERATOR);
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>ComparisonExpression</code> instance using greaterThan operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value value for this expression.
     *
     * @return a new <code>ComparisonExpression</code> instance using greaterThan operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static ComparisonExpression greaterThan(Criteria criteria, String value) {
        return createComparisonExpression(criteria, value, GREATER_THAN_OPERATOR);
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>ComparisonExpression</code> instance using greaterThanOrEquals
     * operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value value for this expression.
     *
     * @return a new <code>ComparisonExpression</code> instance using greaterThanOrEquals operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static ComparisonExpression greaterThanOrEquals(Criteria criteria, String value) {
        return createComparisonExpression(criteria, value, GREATER_THAN_OR_EQUAL_OPERATOR);
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>ComparisonExpression</code> instance using lessThan operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value value for this expression.
     *
     * @return a new <code>ComparisonExpression</code> instance using lessThan operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static ComparisonExpression lessThan(Criteria criteria, String value) {
        return createComparisonExpression(criteria, value, LESS_THAN_OPERATOR);
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>ComparisonExpression</code> instance using lessThanOrEquals
     * operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param value value for this expression.
     *
     * @return a new <code>ComparisonExpression</code> instance using lessThanOrEquals operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static ComparisonExpression lessThanOrEquals(Criteria criteria, String value) {
        return createComparisonExpression(criteria, value, LESS_THAN_OR_EQUAL_OPERATOR);
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
     * Gets the criteria part of the expression.
     * </p>
     *
     * @return the criteria part of the expression.
     */
    public Criteria getCriteria() {
        return this.criteria;
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
     * Gets the string representation of the expression operator.
     * </p>
     *
     * @return the string representation of the expression operator.
     */
    public String getOperator() {
        return this.operator;
    }
}
