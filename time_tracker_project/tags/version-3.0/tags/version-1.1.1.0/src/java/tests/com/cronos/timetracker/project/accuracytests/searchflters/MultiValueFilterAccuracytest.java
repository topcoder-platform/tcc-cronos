/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.accuracytests.searchflters;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.project.searchfilters.MultiValueFilter;

/**
 * The class <code>MultiValueFilterAccuracytest</code> contains tests for the class
 * {@link <code>MultiValueFilter</code>}.
 * @author FireIce
 * @version 1.0
 */
public class MultiValueFilterAccuracytest extends TestCase {

    /**
     * accuracy test for constructor.
     */
    public void testCtorAccuracy() {
        String fieldName = "hello";
        int size = 4;
        Object[] values = new Object[size];

        // Integer values.
        for (int i = 0; i < size; i++) {
            values[i] = new Integer(i);
        }
        MultiValueFilter multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // String type
        for (int i = 0; i < size; i++) {
            values[i] = String.valueOf(i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Long type
        for (int i = 0; i < size; i++) {
            values[i] = new Long(i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Float type
        for (int i = 0; i < size; i++) {
            values[i] = new Float(i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Double type
        for (int i = 0; i < size; i++) {
            values[i] = new Double(i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Short type
        for (int i = 0; i < size; i++) {
            values[i] = new Short((short) i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Byte type
        for (int i = 0; i < size; i++) {
            values[i] = new Byte((byte) i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Byte type
        for (int i = 0; i < size; i++) {
            values[i] = new Byte((byte) i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Character type
        for (int i = 0; i < size; i++) {
            values[i] = new Character((char) ('a' + i));
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // Boolean type
        for (int i = 0; i < size; i++) {
            values[i] = Boolean.TRUE;
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // BigDecimal type
        for (int i = 0; i < size; i++) {
            values[i] = new BigDecimal(i);
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());

        // java.util.Date type
        for (int i = 0; i < size; i++) {
            values[i] = new Date();
        }
        multiValueFilter = new MultiValueFilter(fieldName, values);
        assertEquals(fieldName, multiValueFilter.getFieldName());
        assertArrayEquals(values, multiValueFilter.getValues());
    }

    /**
     * assert the two array have the same size and the same value at the same index.
     */
    void assertArrayEquals(Object[] expected, Object[] values) {
        assertNotNull(expected);
        assertNotNull(values);

        // size
        assertEquals(expected.length, values.length);

        // element
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], values[i]);
        }
    }

    /**
     * Aggragates all tests in this class.
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(MultiValueFilterAccuracytest.class);
    }
}
