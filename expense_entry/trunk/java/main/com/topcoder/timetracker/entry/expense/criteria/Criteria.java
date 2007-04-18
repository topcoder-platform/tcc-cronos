/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.criteria;

import java.io.Serializable;

/**
 * <p>
 * This interface abstracts a criteria used for searching expense entries.
 * </p>
 *
 * <p>
 * The criterias are database oriented, approach chosen for speed by being able to filter expense entries at the SQL
 * query level, thus avoiding the need to bring all expense entries into memory. The criteria implementations are used
 * to build the where clause of an SQL query on the expense entry table. And the clause may have some given parameters
 * in which way <code>PreparedStatement</code> can be used.
 * </p>
 *
 * <p>
 * Here is the the example of where clause: "some_column = ?", and the corresponding parameters is new String[] {"1"}.
 * Then the <code>PreparedStatement</code> can use these where clause and parameters to do the database query.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Implementations need not necessarily be thread safe.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public interface Criteria extends Serializable {
    /**
     * <p>
     * Gets the where clause expression that can be used in an SQL query to perform the filtering. The expression is
     * expected to contain ? characters where the criteria parameters should be inserted. This is needed because
     * <code>PreparedStatement</code> can be used to execute the query.
     * </p>
     *
     * @return the where clause expression that can be used in an SQL query to perform the filtering.
     */
    String getWhereClause();

    /**
     * <p>
     * Gets the parameter values that should be used to fill the where clause expression returned by the
     * <code>getWhereClause</code> method. The parameter values should be inserted in place of the ? characters in the
     * where clause expression. The number of parameters should be equal to the number of ? characters in the where
     * clause expression.
     * </p>
     *
     * @return the parameters used in the expression
     */
    Object[] getParameters();
}
