/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link DirectProjectCPConfig} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DirectProjectCPConfigUnitTests {
    /**
     * <p>
     * Represents the <code>DirectProjectCPConfig</code> instance used in tests.
     * </p>
     */
    private DirectProjectCPConfig instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectCPConfigUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new DirectProjectCPConfig();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DirectProjectCPConfig()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DirectProjectCPConfig();

        assertEquals("'directProjectId' should be correct.",
            0L, BaseUnitTests.getField(instance, "directProjectId"));
        assertFalse("'useCP' should be correct.", (Boolean) BaseUnitTests.getField(instance, "useCP"));
        assertEquals("'availableImmediateBudget' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "availableImmediateBudget"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getDirectProjectId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getDirectProjectId() {
        long value = 1L;
        instance.setDirectProjectId(value);

        assertEquals("'getDirectProjectId' should be correct.",
            value, instance.getDirectProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDirectProjectId(long directProjectId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setDirectProjectId() {
        long value = 1L;
        instance.setDirectProjectId(value);

        assertEquals("'setDirectProjectId' should be correct.",
            value, BaseUnitTests.getField(instance, "directProjectId"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>isUseCP()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_isUseCP() {
        boolean value = true;
        instance.setUseCP(value);

        assertTrue("'isUseCP' should be correct.",
            instance.isUseCP());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUseCP(boolean useCP)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUseCP() {
        boolean value = true;
        instance.setUseCP(value);

        assertTrue("'setUseCP' should be correct.",
            (Boolean) BaseUnitTests.getField(instance, "useCP"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getAvailableImmediateBudget()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAvailableImmediateBudget() {
        double value = 1;
        instance.setAvailableImmediateBudget(value);

        assertEquals("'getAvailableImmediateBudget' should be correct.",
            value, instance.getAvailableImmediateBudget(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAvailableImmediateBudget(double availableImmediateBudget)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAvailableImmediateBudget() {
        double value = 1;
        instance.setAvailableImmediateBudget(value);

        assertEquals("'setAvailableImmediateBudget' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "availableImmediateBudget"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        instance = new DirectProjectCPConfig();
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("directProjectId:0"));
        assertTrue("'toString' should be correct.", res.contains("useCP:false"));
        assertTrue("'toString' should be correct.", res.contains("availableImmediateBudget:0"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance.setDirectProjectId(1);
        instance.setUseCP(true);
        instance.setAvailableImmediateBudget(2);
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("directProjectId:1"));
        assertTrue("'toString' should be correct.", res.contains("useCP:true"));
        assertTrue("'toString' should be correct.", res.contains("availableImmediateBudget:2"));
    }
}