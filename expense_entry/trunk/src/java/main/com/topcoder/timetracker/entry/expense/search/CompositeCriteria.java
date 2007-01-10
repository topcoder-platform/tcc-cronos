/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.search;

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
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @version 2.0
 */
public class CompositeCriteria implements Criteria {
    /** Represents the constant for the AND boolean operator keyword. */
    public static final String AND_COMPOSITION = "AND";

    /** Represents the constant for the OR boolean operator keyword. */
    public static final String OR_COMPOSITION = "OR";

    /** Represents the keyword to be used for obtaining the aggregated where clause. */
    private final String compositionKeyword;

    /** The contained criteria instances. The size should be at least 2. */
    private final Criteria[] criteria;

    /** Represents the where clause of this criteria. */
    private final String whereClause;

    /** Represents the parameters of this criteria. */
    private final Object[] parameters;

    /**
     * <p>
     * Creates a new instance of <code>CompositeCriteria</code> class with given composition keyword and criterias
     * collection.
     * </p>
     *
     * @param compositionKeyword the keyword to be used for obtaining the aggregated where clause.
     * @param criteria contained criteria instances and its size should be at least 2.
     *
     * @throws IllegalArgumentException if any argument or any array element is <code>null</code> or the size of the
     *         array is less than 2.
     */
    public CompositeCriteria(String compositionKeyword, Criteria[] criteria) {
        // arguments validation
        if (compositionKeyword == null) {
            throw new IllegalArgumentException("compositionKeyword can not be null.");
        }

        if (criteria == null) {
            throw new IllegalArgumentException("criteria can not be null");
        }

        if (criteria.length < 2) {
            throw new IllegalArgumentException("Size of criteria should not be less than 2.");
        }

        for (int i = 0; i < criteria.length; i++) {
            if (criteria[i] == null) {
                throw new IllegalArgumentException("Any element in criteria collection can not be null.");
            }
        }

        // assign the private fields
        this.compositionKeyword = compositionKeyword;
        this.criteria = copyCriteriaCollection(criteria);

        StringBuffer buffer = new StringBuffer();
        List result = new ArrayList();

        for (int i = 0; i < criteria.length; i++) {
            if (i != 0) {
                buffer.append(compositionKeyword);
            }

            // the where clause should be like this: (xx) compositionKeyword (xx) compositionKeyword (xx)
            buffer.append("(").append(criteria[i].getWhereClause()).append(")");

            // the parameters should be like this: criteria[0].parameters[0], criteria[0].parameters[1],
            // criteria[1].parameters[0], criteria[1].parameters[1], criteria[1].parameters[2]...
            result.addAll(Arrays.asList(criteria[i].getParameters()));
        }

        this.whereClause = buffer.toString();
        this.parameters = (Object[]) result.toArray(new Object[result.size()]);
    }

    /**
     * Shallow copy the given collection.
     *
     * @param criteria the given collection for shallow copy.
     *
     * @return the shallow copy of the given collection.
     */
    private Criteria[] copyCriteriaCollection(Criteria[] criteria) {
        Criteria[] ret = new Criteria[criteria.length];

        for (int i = 0; i < criteria.length; ++i) {
            ret[i] = criteria[i];
        }

        return ret;
    }

    /**
     * Gets the keyword to be used for obtaining the aggregated where clause.
     *
     * @return the keyword to be used for obtaining the aggregated where clause.
     */
    public String getCompositionKeyword() {
        return compositionKeyword;
    }

    /**
     * Gets the contained criteria instances.
     *
     * @return the contained criteria instances.
     */
    public Criteria[] getCriteria() {
        return this.copyCriteriaCollection(this.criteria);
    }

    /**
     * Gets the where clause expression that can be used in an SQL query to perform the filtering. The expression is
     * expected to contain ? characters where the criteria parameters should be inserted. This is needed because
     * <code>PreparedStatement</code> can be used to execute the query.
     *
     * @return the where clause expression that can be used in an SQL query to perform the filtering.
     */
    public String getWhereClause() {
        return whereClause;
    }

    /**
     * Gets the parameter values that should be used to fill the where clause expression returned by the
     * <code>getWhereClause</code> method. The parameter values should be inserted in place of the ? characters in the
     * where clause expression. The number of parameters should be equal to the number of ? characters in the where
     * clause expression.
     *
     * @return the parameters used in the expression.
     */
    public Object[] getParameters() {
        return parameters;
    }

    /**
     * Static shortcut method for creating an AND composite criteria over two criterias.
     *
     * @param left the left criteria.
     * @param right the right criteria.
     *
     * @return the created AND composite criteria instance.
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>.
     */
    public static CompositeCriteria getAndCompositeCriteria(Criteria left, Criteria right) {
        return new CompositeCriteria(AND_COMPOSITION, new Criteria[] {left, right});
    }

    /**
     * Static shortcut method for creating an AND composite criteria over two or more criterias.
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
     * Static shortcut method for creating an OR composite criteria over two criterias.
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
     * Static shortcut method for creating an OR composite criteria over two or more criterias.
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
