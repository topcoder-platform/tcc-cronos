/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

/**
 * <p>
 * This class represents a boolean expression which simply, through and() or() not() methods, allows to combine other
 * search expressions. A typical usage would be: BooleanExpression expression = BooleanExpression.and(expressionA,
 * expressionB);  The class defines 3 static methods in an effort to provide the simplest possible API for the user.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class BooleanExpression implements SearchExpression {
    /** Represents the string representation of the and operator. */
    private static final String AND_OPERATOR = "And";

    /** Represents the string representation of the or operator. */
    private static final String OR_OPERATOR = "Or";

    /** Represents the string representation of the not operator. */
    private static final String NOT_OPERATOR = "Not";

    /**
     * <p>
     * Represents the left and right expressions for AND and OR operations; the inner expression for NOT operatoin.
     * </p>
     */
    private SearchExpression[] innerExpressions = null;

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
    private BooleanExpression() {
        // do nothing here
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>BooleanExpression</code> instance using AND operator.
     * </p>
     *
     * @param left the left subexpression.
     * @param right the right subexpression.
     *
     * @return a new <code>BooleanExpression</code> instance using AND operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static BooleanExpression and(SearchExpression left, SearchExpression right) {
        return createBooleanExpression(new SearchExpression[] {left, right}, AND_OPERATOR);
    }

    /**
     * <p>
     * Creates a new <code>BooleanExpression</code> instance using given search expressions and operator.
     * </p>
     *
     * @param searchExpressions the given search expressions.
     * @param operator the given operator.
     *
     * @return a new <code>BooleanExpression</code> instance using given search expressions and operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    private static BooleanExpression createBooleanExpression(SearchExpression[] searchExpressions, String operator) {
        for (int i = 0; i < searchExpressions.length; i++) {
            if (searchExpressions[i] == null) {
                throw new IllegalArgumentException("The search expression can not be null.");
            }
        }

        BooleanExpression booleanExpression = new BooleanExpression();
        booleanExpression.innerExpressions = searchExpressions;
        booleanExpression.operator = operator;

        if (searchExpressions.length == 2) {
            // in case of and, or operator
            booleanExpression.expression = "( " + searchExpressions[0].getSearchExpressionString() + " " + operator
                + " " + searchExpressions[1].getSearchExpressionString() + " )";
        } else {
            // in case of not operator
            booleanExpression.expression = operator + " " + searchExpressions[0].getSearchExpressionString();
        }

        return booleanExpression;
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>BooleanExpression</code> instance using OR operator.
     * </p>
     *
     * @param left the left subexpression.
     * @param right the right subexpression.
     *
     * @return a new <code>BooleanExpression</code> instance using OR operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static BooleanExpression or(SearchExpression left, SearchExpression right) {
        return createBooleanExpression(new SearchExpression[] {left, right}, OR_OPERATOR);
    }

    /**
     * <p>
     * Static shortcut method for creating a new <code>BooleanExpression</code> instance using NOT operator.
     * </p>
     *
     * @param expressionToNegate the expression to apply a negation to.
     *
     * @return a new <code>BooleanExpression</code> instance using NOT operator.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static BooleanExpression not(SearchExpression expressionToNegate) {
        return createBooleanExpression(new SearchExpression[] {expressionToNegate}, NOT_OPERATOR);
    }

    /**
     * <p>
     * Gets the inner expressions array. No need to copy.
     * </p>
     *
     * @return the inner expressions array.
     */
    public SearchExpression[] getInnerExpressions() {
        return this.innerExpressions;
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
