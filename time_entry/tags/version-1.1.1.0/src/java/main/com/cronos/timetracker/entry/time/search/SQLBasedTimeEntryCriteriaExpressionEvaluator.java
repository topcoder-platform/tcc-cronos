/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import java.util.List;

import com.cronos.timetracker.entry.time.DAOActionException;
import com.cronos.timetracker.entry.time.TimeEntry;
import com.cronos.timetracker.entry.time.TimeEntryDAO;


/**
 * <p>
 * This is a simple, SQL based evaluator which takes in a SearchExpression, converts it into some SQL statements, and
 * submits it to the data base and returns (hopefully) an array of TimeEntry Objects that have met the input criteria.
 * If the criteria were invalid or the search expression evaluation has failed then a SearchException will be thrown.
 * thread-Safe since it is immutable
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class SQLBasedTimeEntryCriteriaExpressionEvaluator implements ExpressionEvaluator {
    /**
     * <p>
     * Represents the time entry dao that will be used to execute the final query created from the search expression.
     * It is set in the constructor and never changed after that.
     * </p>
     */
    private TimeEntryDAO timeEntryDAO = null;

    /**
     * <p>
     * Constructor. Accepts a dao representing the TimeEntryDAO which will be used here to execute the  actual sql
     * query through its +getList(whereClause:String) : List method. This way we do not have to worry about
     * connections or any other aspects of the query. It is strongly typed on purpose. #throws
     * IllegalArgumentException If the input dao is null.
     * </p>
     *
     * @param timeEntryDAO the dao taht will be used to evaluate the sql for the search expression
     */
    public SQLBasedTimeEntryCriteriaExpressionEvaluator(TimeEntryDAO timeEntryDAO) {
        if (timeEntryDAO == null) {
            throw new IllegalArgumentException("timeEntryDAO can not be null.");
        }

        this.timeEntryDAO = timeEntryDAO;
    }

    /**
     * <p>
     * Evaluates the possibly compound expression passed in. Once the search string has been created, it will be used
     * to fetch the entries via the getList method of <code>TimeEntryDAO</code> class.
     * </p>
     *
     * @param expression expression to evaluate
     *
     * @return result as an Object array
     *
     * @exception SearchException If the search fails.
     */
    public Object[] evaluate(SearchExpression expression) throws SearchException {
        if (expression == null) {
            throw new IllegalArgumentException("expression can not be null.");
        }

        try {
            List ret = timeEntryDAO.getList(this.getSearchExpressionString(expression).toString());

            return (TimeEntry[]) ret.toArray(new TimeEntry[ret.size()]);
        } catch (DAOActionException e) {
            throw new SearchException("Failed in the search.", e);
        }
    }

    /**
     * Gets the expression string using given search expression. If the search expression is boolean expression, should
     * re-curse it.
     *
     * @param expression the given search expression to get the expression string.
     *
     * @return the expression string using given search expression
     */
    private StringBuffer getSearchExpressionString(SearchExpression expression) {
        StringBuffer buffer = new StringBuffer();

        if (expression instanceof BooleanExpression) {
            // if it is the BooleanExpression, re-curse it
            SearchExpression[] innerExpressions = ((BooleanExpression) expression).getInnerExpressions();

            if (innerExpressions.length == 1) {
                // not condition
                buffer.append(expression.getOperator()).append(" ").append(getSearchExpressionString(
                        innerExpressions[0]));
            } else {
                // and, or condition
                buffer.append("( ").append(getSearchExpressionString(innerExpressions[0])).append(" ");
                buffer.append(expression.getOperator()).append(" ")
                      .append(getSearchExpressionString(innerExpressions[1])).append(" )");
            }
        } else if (expression instanceof RangeExpression) {
            buffer.append(((RangeExpression) expression).getCriteria().getName());

            if (expression.getOperator().equals("[...")) {
                // from operator
                buffer.append(" >= ").append(((RangeExpression) expression).getFromValue());
            } else if (expression.getOperator().equals("...]")) {
                // to operator
                buffer.append(" <= ").append(((RangeExpression) expression).getToValue());
            } else {
                // fromTo operation
                buffer.append(" between ").append(((RangeExpression) expression).getFromValue());
                buffer.append(" and ").append(((RangeExpression) expression).getToValue());
            }
        } else {
            buffer.append(expression.getSearchExpressionString());
        }

        return buffer;
    }
}
