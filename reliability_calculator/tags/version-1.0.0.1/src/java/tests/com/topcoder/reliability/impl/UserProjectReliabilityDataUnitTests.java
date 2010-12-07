/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.reliability.TestsHelper;

/**
 * <p>
 * Unit tests for {@link UserProjectReliabilityData} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UserProjectReliabilityDataUnitTests {
    /**
     * <p>
     * Represents the <code>UserProjectReliabilityData</code> instance used in tests.
     * </p>
     */
    private UserProjectReliabilityData instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserProjectReliabilityDataUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new UserProjectReliabilityData();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserProjectReliabilityData()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new UserProjectReliabilityData();

        assertNull("'reliabilityBeforeResolution' should be correct.",
            TestsHelper.getField(instance, "reliabilityBeforeResolution"));
        assertEquals("'reliabilityAfterResolution' should be correct.",
            0.0, TestsHelper.getField(instance, "reliabilityAfterResolution"));
        assertNull("'reliabilityOnRegistration' should be correct.",
            TestsHelper.getField(instance, "reliabilityOnRegistration"));
        assertFalse("'reliable' should be correct.",
            (Boolean) TestsHelper.getField(instance, "reliable"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getReliabilityBeforeResolution()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getReliabilityBeforeResolution() {
        Double value = 1.0;
        instance.setReliabilityBeforeResolution(value);

        assertEquals("'reliabilityBeforeResolution' value should be properly retrieved.",
            value, instance.getReliabilityBeforeResolution(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReliabilityBeforeResolution(Double reliabilityBeforeResolution)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setReliabilityBeforeResolution() {
        Double value = 1.0;
        instance.setReliabilityBeforeResolution(value);

        assertEquals("'reliabilityBeforeResolution' value should be properly set.",
            value, (Double) TestsHelper.getField(instance, "reliabilityBeforeResolution"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getReliabilityAfterResolution()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getReliabilityAfterResolution() {
        double value = 1.0;
        instance.setReliabilityAfterResolution(value);

        assertEquals("'reliabilityAfterResolution' value should be properly retrieved.",
            value, instance.getReliabilityAfterResolution(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReliabilityAfterResolution(double reliabilityAfterResolution)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setReliabilityAfterResolution() {
        double value = 1.0;
        instance.setReliabilityAfterResolution(value);

        assertEquals("'reliabilityAfterResolution' value should be properly set.",
            value, (Double) TestsHelper.getField(instance, "reliabilityAfterResolution"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getReliabilityOnRegistration()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getReliabilityOnRegistration() {
        Double value = 1.0;
        instance.setReliabilityOnRegistration(value);

        assertEquals("'reliabilityOnRegistration' value should be properly retrieved.",
            value, instance.getReliabilityOnRegistration(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReliabilityOnRegistration(Double reliabilityOnRegistration)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setReliabilityOnRegistration() {
        Double value = 1.0;
        instance.setReliabilityOnRegistration(value);

        assertEquals("'reliabilityOnRegistration' value should be properly set.",
            value, (Double) TestsHelper.getField(instance, "reliabilityOnRegistration"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>isReliable()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isReliable() {
        boolean value = true;
        instance.setReliable(value);

        assertTrue("'reliable' value should be properly retrieved.", instance.isReliable());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReliable(boolean reliable)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setReliable() {
        boolean value = true;
        instance.setReliable(value);

        assertTrue("'reliable' value should be properly set.", (Boolean) TestsHelper.getField(instance, "reliable"));
    }
}