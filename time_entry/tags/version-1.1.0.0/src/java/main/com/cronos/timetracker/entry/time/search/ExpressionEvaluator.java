/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

/**
 * <p>
 * This is a general contract for running search expressions. It simply takes a search expression, evaluates it and
 * returns the search results (which is an array of Objects),
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public interface ExpressionEvaluator {
    /**
     * <p>
     * The implementation of this method will use the input search expression and perform a search on some specific
     * domain.
     * </p>
     *
     * @param expression expression to evaluate.
     *
     * @return the search results as an Object array.
     *
     * @throws SearchException If the search fails.
     */
    Object[] evaluate(SearchExpression expression) throws SearchException;
}
