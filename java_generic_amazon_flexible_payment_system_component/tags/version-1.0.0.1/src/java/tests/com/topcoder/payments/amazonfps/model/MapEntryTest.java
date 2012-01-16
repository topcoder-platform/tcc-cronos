/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.payments.amazonfps.TestHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link MapEntry} class. <br/>
 * </p>
 *
 * <p>
 * The {@code MapEntry} constructors, getters and setters are tested:
 * <ul>
 * <li>The constructor test validates that fields are initialized to correct default values.</li>
 * <li>Getters correctness are checked by setting initial values using reflection and then checking them.</li>
 * <li>Setters correctness are checked using corresponding getters (which itself were tested via reflection).</li>
 * </ul>
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class MapEntryTest {
    /**
     * The {@code MapEntry} instance used for testing.
     */
    private MapEntry instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MapEntryTest.class);
    }

    /**
     * Sets up the test environment.
     */
    @Before
    public void setUp() {
        instance = new MapEntry();
    }

    /**
     * Check that {@code MapEntry} class implements {@code Serializable} interface.
     */
    @Test
    public void test_implementedInterface() {
        assertTrue("MapEntry should implement Serializable", instance instanceof Serializable);
    }

    /**
     * Check that class's methods obey JavaBean naming conventions.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_javaBeanMethods() throws Exception {
        TestHelper.checkJavaBeanMethods(MapEntry.class);
    }

    /**
     * Accuracy test for {@code MapEntry} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor() {
        assertNull("The key should be null", TestHelper.getField(instance, "key"));
        assertNull("The value should be null", TestHelper.getField(instance, "value"));
    }

    /**
     * Accuracy test for {@code MapEntry(String, String)} constructor. <br/>
     * Checks that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructor2() {
        MapEntry instance2 = new MapEntry("mykey1", "myvalue1");
        assertEquals("The key should be correct", "mykey1", TestHelper.getField(instance2, "key"));
        assertEquals("The value should be correct", "myvalue1", TestHelper.getField(instance2, "value"));
    }

    //------------------- key ---------------
    /**
     * Accuracy test for <code>getKey</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getKey() {
        TestHelper.setField(instance, "key", "some_string");
        assertEquals("The value should be correct", "some_string", instance.getKey());
    }
    /**
     * Accuracy test for <code>setKey</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setKey() {
        instance.setKey("mykey");
        assertEquals("The value should be correct", "mykey", instance.getKey());
    }

    //------------------- value ---------------
    /**
     * Accuracy test for <code>getValue</code> method. <br/>
     * Sets the field value using reflection and then checks that the getter returns the same value.
     */
    @Test
    public void test_getValue() {
        TestHelper.setField(instance, "value", "some_value");
        assertEquals("The value should be correct", "some_value", instance.getValue());
    }
    /**
     * Accuracy test for <code>setValue</code> method. <br/>
     * Sets the field value and then checks it using corresponding getter (which is tested via reflection).
     */
    @Test
    public void test_setValue() {
        instance.setValue("myvalue");
        assertEquals("The value should be correct", "myvalue", instance.getValue());
    }
}
