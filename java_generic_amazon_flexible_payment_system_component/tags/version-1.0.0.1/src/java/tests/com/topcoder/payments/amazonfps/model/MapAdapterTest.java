/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link MapAdapter} class. <br/>
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class MapAdapterTest {
    /**
     * String constant used for testing.
     */
    private static final String KEY1 = "key1";

    /**
     * String constant used for testing.
     */
    private static final String VALUE1 = "value1";

    /**
     * String constant used for testing.
     */
    private static final String KEY2 = "key2";

    /**
     * String constant used for testing.
     */
    private static final String VALUE2 = "value2";

    /**
     * The {@code MapAdapter} instance used for testing.
     */
    private MapAdapter instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MapAdapterTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new MapAdapter();
    }

    /**
     * Checks that {@code MapAdapter} class extends XmlAdapter.
     */
    @Test
    public void test_inheritanceMapAdapter() {
        assertSame("MapAdapter should extend XmlAdapter", XmlAdapter.class, MapAdapter.class.getSuperclass());
    }

    /**
     * Accuracy test for {@code marshal} method. <br/>
     * Check that for {@code null} input return value is also {@code null}
     */
    @Test
    public void test_marshal_1() {
        assertNull("the null should be returned for null input", instance.marshal(null));
    }

    /**
     * Accuracy test for {@code marshal} method. <br/>
     * The map should be converted correctly
     */
    @Test
    public void test_marshal_2() {
        Map<String, String> value = new HashMap<String, String>();
        value.put(KEY1, VALUE1);
        value.put(KEY2, VALUE2);

        MapEntry[] entries = instance.marshal(value);
        assertEquals("there should be 2 elements", 2, entries.length);

        // sort entries by keys
        Arrays.sort(entries, new Comparator<MapEntry>() {
            @Override
            public int compare(MapEntry o1, MapEntry o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        assertEquals("key should be correct", KEY1, entries[0].getKey());
        assertEquals("value should be correct", VALUE1, entries[0].getValue());
        assertEquals("key should be correct", KEY2, entries[1].getKey());
        assertEquals("value should be correct", VALUE2, entries[1].getValue());
    }

    /**
     * Accuracy test for {@code marshal} method. <br/>
     * For empty map the empty array should be returned.
     */
    @Test
    public void test_marshal_3() {
        Map<String, String> value = new HashMap<String, String>();

        MapEntry[] entries = instance.marshal(value);

        assertNotNull("the resulted array should not be null", entries);
        assertEquals("the resulted array should be empty", 0, entries.length);
    }

    /**
     * Accuracy test for {@code unmarshal} method. <br/>
     * Check that for {@code null} input return value is also {@code null}
     */
    @Test
    public void test_unmarshal_1() {
        assertNull("the null should be returned for null input", instance.unmarshal(null));
    }

    /**
     * Accuracy test for {@code unmarshal} method. <br/>
     * The array should be converted correctly
     */
    @Test
    public void test_unmarshal_2() {
        MapEntry[] entries = new MapEntry[2];
        entries[0] = new MapEntry(KEY1, VALUE1);
        entries[1] = new MapEntry(KEY2, VALUE2);

        Map<String, String> value = instance.unmarshal(entries);
        assertEquals("there should be 2 elements", 2, value.size());

        assertEquals("value should be correct for key1", VALUE1, value.get(KEY1));
        assertEquals("value should be correct for key2", VALUE2, value.get(KEY2));
    }

    /**
     * Accuracy test for {@code unmarshal} method. <br/>
     * For empty array the empty map should be returned.
     */
    @Test
    public void test_unmarshal_3() {
        MapEntry[] entries = new MapEntry[0];
        Map<String, String> value = instance.unmarshal(entries);

        assertNotNull("the resulted map should not be null", value);
        assertTrue("the resulted map should be empty", value.isEmpty());
    }
}
