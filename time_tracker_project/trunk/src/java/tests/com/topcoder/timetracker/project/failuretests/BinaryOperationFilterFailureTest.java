/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.searchfilters.BinaryOperation;
import com.topcoder.timetracker.project.searchfilters.BinaryOperationFilter;
import com.topcoder.timetracker.project.searchfilters.Filter;

/**
 * @author kr00tki
 * @version 1.0
 */
public class BinaryOperationFilterFailureTest extends TestCase {
    /**
     * Dummy filter object.
     */
    private static final Filter FILTER = new Filter() {
    };

    /**
     * Test the failure of {@link BinaryOperationFilter#BinaryOperationFilter(BinaryOperation, Filter, Filter)}
     * method. Checks if IAE exception is thrown when operation is <code>null</code>.
     */
    public void testBinaryOperationFilter_NullOperation() {
        try {
            new BinaryOperationFilter(null, FILTER, FILTER);
            fail("Null operation, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the failure of {@link BinaryOperationFilter#BinaryOperationFilter(BinaryOperation, Filter, Filter)}
     * method. Checks if IAE exception is thrown when left filter is <code>null</code>.
     */
    public void testBinaryOperationFilter_NullLeft() {
        try {
            new BinaryOperationFilter(BinaryOperation.AND, null, FILTER);
            fail("Null left filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the failure of {@link BinaryOperationFilter#BinaryOperationFilter(BinaryOperation, Filter, Filter)}
     * method. Checks if IAE exception is thrown when right filter is <code>null</code>.
     */
    public void testBinaryOperationFilter_NullRight() {
        try {
            new BinaryOperationFilter(BinaryOperation.AND, FILTER, null);
            fail("Null right filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

}
