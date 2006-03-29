/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

/**
 * <p>
 * This class represents a comparison expression which simply, through from() fromTo() to(), allows to specify a search
 * criteria and one of two range values. A typical usage would be: RangeExpression expression =
 * RangeExpression.fromTo(TimeEntryCriteria.HOURS, 10, 25). The class defines 3 static methods in an effort to provide
 * the simplest possible API for the user.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class RangeExpression implements SearchExpression {
    /** Represents the string representation of the from operator. */
    private static final String FROM_OPERATOR = "[...";

    /** Represents the string representation of the to operator. */
    private static final String TO_OPERATOR = "...]";

    /** Represents the string representation of the fromTo operator. */
    private static final String FROMTO_OPERATOR = "[...]";

    /**
     * <p>
     * Represents the <code>TimeEntryCriteria</code> used for this expression.
     * </p>
     */
    private TimeEntryCriteria criteria = null;

    /**
     * <p>
     * Represents the from value used in some of the range expressions. The class itself will not validate the value,
     * since the interpretation of its meaning will be done at evaluation time.
     * </p>
     */
    private String from = null;

    /**
     * <p>
     * Represents the to value used in some of the range expressions. The class itself will not validate the value,
     * since the interpretation of its meaning will be done at evaluation time.
     * </p>
     */
    private String to = null;

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
    private RangeExpression() {
        // do nothing here
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>RangeExpression</code> instance using fromto operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param from the from value.
     * @param to the to value.
     *
     * @return a new <code>RangeExpression</code> instance using fromto operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static RangeExpression fromTo(TimeEntryCriteria criteria, String from, String to) {
        return createRangeExpression(criteria, from, to, FROMTO_OPERATOR);
    }

    /**
     * <p>
     * Creates a new <code>RangeExpression</code> instance using given criteria, from, to and operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param from the from value.
     * @param to the to value.
     * @param operator the given operator.
     *
     * @return a new <code>RangeExpression</code> instance using given criteria, from, to and operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    private static RangeExpression createRangeExpression(TimeEntryCriteria criteria, String from, String to,
        String operator) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria can not be null.");
        }

        if ((from == null) && !operator.equals(TO_OPERATOR)) {
            throw new IllegalArgumentException("from can not be null.");
        }

        if ((to == null) && !operator.equals(FROM_OPERATOR)) {
            throw new IllegalArgumentException("to can not be null.");
        }

        RangeExpression rangeExpression = new RangeExpression();
        rangeExpression.criteria = criteria;
        rangeExpression.from = from;
        rangeExpression.to = to;
        rangeExpression.operator = operator;

        StringBuffer buffer = new StringBuffer();
        buffer.append(criteria.getName()).append(" ");

        if (from != null) {
            buffer.append(from).append(" ");
        }

        buffer.append(operator);

        if (to != null) {
            buffer.append(" ").append(to);
        }

        rangeExpression.expression = buffer.toString();

        return rangeExpression;
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>RangeExpression</code> instance using from operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param from the from value.
     *
     * @return a new <code>RangeExpression</code> instance using from operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static RangeExpression from(TimeEntryCriteria criteria, String from) {
        return createRangeExpression(criteria, from, null, FROM_OPERATOR);
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>RangeExpression</code> instance using to operator.
     * </p>
     *
     * @param criteria criteria of this expression.
     * @param to the to value.
     *
     * @return a new <code>RangeExpression</code> instance using to operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static RangeExpression to(TimeEntryCriteria criteria, String to) {
        return createRangeExpression(criteria, null, to, TO_OPERATOR);
    }

    /**
     * <p>
     * Gets the from value part of the expression.
     * </p>
     *
     * @return the from value part of the expression.
     */
    public String getFromValue() {
        return this.from;
    }

    /**
     * <p>
     * Gets the to value part of the expression.
     * </p>
     *
     * @return the to value part of the expression.
     */
    public String getToValue() {
        return this.to;
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
