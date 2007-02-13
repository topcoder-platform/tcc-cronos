/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.project.failuretests;

import com.cronos.timetracker.project.searchfilters.CompareOperation;
import com.cronos.timetracker.project.searchfilters.ValueFilter;

import junit.framework.TestCase;

/**
 * Failure tests for {@link ValueFilter} class.
 *
 * @author kr00tki
 * @version 1.1
 */
public class ValueFilterFailureTest extends TestCase {

    /**
     * The name of the column.
     */
    private static final String NAME = "test";

    /**
     * The value used in tests.
     */
    private static final Object VALUE = new Integer(1);

    /**
     * Tests the failure of {@link ValueFilter#ValueFilter(CompareOperation, String, Object)} class. Checks if IAE
     * is thrown when operation is <code>null</code>.
     */
    public void testValueFilter_NullOperation() {
        try {
            new ValueFilter(null, NAME, VALUE);
            fail("Null operation, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the failure of {@link ValueFilter#ValueFilter(CompareOperation, String, Object)} class. Checks if IAE
     * is thrown when name is <code>null</code>.
     */
    public void testValueFilter_NullName() {
        try {
            new ValueFilter(CompareOperation.EQUAL, null, VALUE);
            fail("Null name, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the failure of {@link ValueFilter#ValueFilter(CompareOperation, String, Object)} class. Checks if IAE
     * is thrown when name is empty.
     */
    public void testValueFilter_EmptyName() {
        try {
            new ValueFilter(CompareOperation.EQUAL, " ", VALUE);
            fail("Empty name, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the failure of {@link ValueFilter#ValueFilter(CompareOperation, String, Object)} class. Checks if IAE
     * is thrown when value is <code>null</code>.
     */
    public void testValueFilter_NullValue() {
        try {
            new ValueFilter(CompareOperation.EQUAL, NAME, null);
            fail("Null value, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the failure of {@link ValueFilter#ValueFilter(CompareOperation, String, Object)} class. Checks if IAE
     * is thrown when value is not primitive type wrapper.
     */
    public void testValueFilter_ValueIncorrectObjectType() {
        try {
            new ValueFilter(CompareOperation.EQUAL, NAME, this);
            fail("Incorrect value type, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}
