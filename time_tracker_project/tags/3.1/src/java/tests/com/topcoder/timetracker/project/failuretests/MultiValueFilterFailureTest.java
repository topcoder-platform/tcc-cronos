/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.searchfilters.MultiValueFilter;

/**
 * Failure tests for {@link com.topcoder.timetracker.project.searchfilters.MultiValueFilter} class.
 *
 * @author kr00tki
 * @version 1.1
 */
public class MultiValueFilterFailureTest extends TestCase {
    /**
     * The name string used in tests.
     */
    private static final String NAME = "test";

    /**
     * The array of objects used in tests.
     */
    private static final Object[] ARRAY = new Object[] { NAME };

    /**
     * Test the failure of {@link MultiValueFilter#MultiValueFilter(String, Object[])} method. Checks if IAE
     * exception is thrown when name is <code>null</code>.
     */
    public void testMultiValueFilter_NullName() {
        try {
            new MultiValueFilter(null, ARRAY);
            fail("Null name, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the failure of {@link MultiValueFilter#MultiValueFilter(String, Object[])} method. Checks if IAE
     * exception is thrown when array is <code>null</code>.
     */
    public void testMultiValueFilter_NullArray() {
        try {
            new MultiValueFilter(NAME, null);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the failure of {@link MultiValueFilter#MultiValueFilter(String, Object[])} method. Checks if IAE
     * exception is thrown when name is empty.
     */
    public void testMultiValueFilter_EmptyName() {
        try {
            new MultiValueFilter(" ", ARRAY);
            fail("Empty name, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the failure of {@link MultiValueFilter#MultiValueFilter(String, Object[])} method. Checks if IAE
     * exception is thrown when array contains <code>null</code> element.
     */
    public void testMultiValueFilter_NullArrayElement() {
        Object[] array = new Object[] { null };
        try {
            new MultiValueFilter(NAME, array);
            fail("Array with null element, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the failure of {@link MultiValueFilter#MultiValueFilter(String, Object[])} method. Checks if IAE
     * exception is thrown when array contains illegal object.
     */
    public void testMultiValueFilter_IllegalArrayElement() {
        Object[] array = new Object[] { this };
        try {
            new MultiValueFilter(NAME, array);
            fail("Invalid type in array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Test the failure of {@link MultiValueFilter#MultiValueFilter(String, Object[])} method. Checks if IAE
     * exception is thrown when array contains objects of different type.
     */
    public void testMultiValueFilter_DifferentTypes() {
        Object[] array = new Object[] { new Integer(1), Boolean.FALSE, new Double(1.0)};
        try {
            new MultiValueFilter(NAME, array);
            fail("Array with different objects, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}
