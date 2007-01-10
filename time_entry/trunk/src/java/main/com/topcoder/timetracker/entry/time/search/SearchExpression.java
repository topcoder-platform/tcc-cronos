/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

/**
 * <p>
 * This interface abstracts a criteria used for searching time entries.
 * </p>
 *
 * <p>
 * The criterias are database oriented, approach chosen for speed by being able to filter time entries at the SQL query
 * level, thus avoiding the need to bring all time entries into memory. The criteria implementations are used to build
 * the expression of an SQL query on the time entry table.
 * </p>
 *
 * <p>
 * Here is the the example of the expression: "some_column like some_value".
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public interface SearchExpression {
    /**
     * <p>
     * Gets the expression string to filter time entries at the SQL query level.
     * </p>
     *
     * @return the expression string to filter time entries at the SQL query level.
     */
    String getSearchExpressionString();

    /**
     * <p>
     * Gets the string representation of the expression operator.
     * </p>
     *
     * @return the string representation of the expression operator.
     */
    String getOperator();
}
