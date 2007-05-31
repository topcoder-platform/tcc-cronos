/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.criteria;

import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * This class represents a special type of rule that is an aggregation over two or more rules. The aggregation type can
 * be AND and OR with the usual boolean logic significance. The class has an attribute and an accessor for getting the
 * contained criterias. The keyword for the aggregation type is parameterized. However, considering the current SQL
 * standard, only AND or OR are expected to be used (constants are static creation methods are defined for them). But
 * maybe some database implementation might implement other aggregation types (such as XOR for example).
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class CompositeCriteria implements Criteria {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -716274716914347896L;

    /**
     * Represents the constant for the AND boolean operator keyword.
     */
    public static final String AND_COMPOSITION = "AND";

    /**
     * Represents the constant for the OR boolean operator keyword.
     */
    public static final String OR_COMPOSITION = "OR";

    /**
     * Represents the keyword to be used for obtaining the aggregated where clause. Cannot be null or empty.
     */
    private final String compositionKeyword;

    /**
     * The contained criteria instances. Cannot be null or have null elements, the size should be at least 2.
     */
    private final Criteria[] criteria;

    /**
     * Represents the where clause of this criteria.
     */
    private String whereClause = null;

    /**
     * Represents the parameters of this criteria.
     */
    private Object[] parameters = null;

    /**
     * <p>
     * Creates a new instance of <code>CompositeCriteria</code> class with given composition keyword and criterias
     * collection. Note, the input criteria collection will be shallow copied.
     * </p>
     *
     * @param compositionKeyword the keyword to be used for obtaining the aggregated where clause.
     * @param criteria contained criteria instances and its size should be at least 2.
     *
     * @throws IllegalArgumentException if any argument or any array element is <code>null</code>, or the
     *         compositionKeyword is empty string, or the size of the array is less than 2.
     */
    public CompositeCriteria(String compositionKeyword, Criteria[] criteria) {
        ExpenseEntryHelper.validateString(compositionKeyword, "compositionKeyword");
        ExpenseEntryHelper.validateObjectArray(criteria, "criteria", 2);

        this.compositionKeyword = compositionKeyword;
        this.criteria = (Criteria[]) criteria.clone();
    }

    /**
     * <p>
     * Gets the keyword to be used for obtaining the aggregated where clause.
     * </p>
     *
     * @return the keyword to be used for obtaining the aggregated where clause.
     */
    public String getCompositionKeyword() {
        return compositionKeyword;
    }

    /**
     * <p>
     * Gets the contained criteria instances. Note: the return value is shallow copied.
     * </p>
     *
     * @return the contained criteria instances.
     */
    public Criteria[] getCriteria() {
        return (Criteria[]) criteria.clone();
    }

    /**
     * <p>
     * Gets the where clause expression that can be used in an SQL query to perform the filtering. The expression is
     * expected to contain ? characters where the criteria parameters should be inserted. This is needed because
     * <code>PreparedStatement</code> can be used to execute the query.
     * </p>
     *
     * @return the where clause expression that can be used in an SQL query to perform the filtering.
     */
    public String getWhereClause() {
        if (whereClause == null) {
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < criteria.length; i++) {
                if (i != 0) {
                    buffer.append(compositionKeyword);
                }

                // the where clause should be like this: (xx) compositionKeyword (xx) compositionKeyword (xx)
                buffer.append("(").append(criteria[i].getWhereClause()).append(")");
            }

            this.whereClause = buffer.toString();
        }

        return whereClause;
    }

    /**
     * <p>
     * Gets the parameter values that should be used to fill the where clause expression returned by the
     * <code>getWhereClause</code> method. The parameter values should be inserted in place of the ? characters in the
     * where clause expression. The number of parameters should be equal to the number of ? characters in the where
     * clause expression.
     * </p>
     *
     * @return the parameters used in the expression.
     */
    public Object[] getParameters() {
        if (parameters == null) {
            List result = new ArrayList();

            for (int i = 0; i < criteria.length; i++) {
                // the parameters should be like this: criteria[0].parameters[0], criteria[0].parameters[1],
                // criteria[1].parameters[0], criteria[1].parameters[1], criteria[1].parameters[2]...
                result.addAll(Arrays.asList(criteria[i].getParameters()));
            }

            this.parameters = (Object[]) result.toArray(new Object[result.size()]);
        }

        return (Object[]) parameters.clone();
    }

    /**
     * <p>
     * Static shortcut method for creating an AND composite criteria over two criterias.
     * </p>
     *
     * @param left the left criteria.
     * @param right the right criteria.
     *
     * @return the created AND composite criteria instance.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static CompositeCriteria getAndCompositeCriteria(Criteria left, Criteria right) {
        return new CompositeCriteria(AND_COMPOSITION, new Criteria[] {left, right });
    }

    /**
     * <p>
     * Static shortcut method for creating an AND composite criteria over two or more criterias.
     * </p>
     *
     * @param criteria the criteria instances to aggregate.
     *
     * @return the created AND composite criteria instance.
     *
     * @throws IllegalArgumentException if the array or any of its elements is <code>null</code> or the size of the
     *         array is less than 2.
     */
    public static CompositeCriteria getAndCompositeCriteria(Criteria[] criteria) {
        return new CompositeCriteria(AND_COMPOSITION, criteria);
    }

    /**
     * <p>
     * Static shortcut method for creating an OR composite criteria over two criterias.
     * </p>
     *
     * @param left the left criteria.
     * @param right the right criteria.
     *
     * @return the created OR composite criteria instance.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static CompositeCriteria getOrCompositeCriteria(Criteria left, Criteria right) {
        return new CompositeCriteria(OR_COMPOSITION, new Criteria[] {left, right});
    }

    /**
     * <p>
     * Static shortcut method for creating an OR composite criteria over two or more criterias.
     * </p>
     *
     * @param criteria the criteria instances to aggregate.
     *
     * @return the created OR composite criteria instance.
     *
     * @throws IllegalArgumentException if the array or any of its elements is <code>null</code> or the size of the
     *         array is less than 2.
     */
    public static CompositeCriteria getOrCompositeCriteria(Criteria[] criteria) {
        return new CompositeCriteria(OR_COMPOSITION, criteria);
    }
}
