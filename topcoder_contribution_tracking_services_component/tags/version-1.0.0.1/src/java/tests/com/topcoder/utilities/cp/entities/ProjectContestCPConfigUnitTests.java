/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link ProjectContestCPConfig} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectContestCPConfigUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectContestCPConfig</code> instance used in tests.
     * </p>
     */
    private ProjectContestCPConfig instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectContestCPConfigUnitTests.class);
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
        instance = new ProjectContestCPConfig();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectContestCPConfig()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ProjectContestCPConfig();

        assertEquals("'contestId' should be correct.",
            0L, BaseUnitTests.getField(instance, "contestId"));
        assertNull("'totalOriginalPayment' should be correct.",
            BaseUnitTests.getField(instance, "originalPayment"));
        assertEquals("'cpRate' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "cpRate"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getContestId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getContestId() {
        long value = 1L;
        instance.setContestId(value);

        assertEquals("'getContestId' should be correct.",
            value, instance.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestId(long contestId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setContestId() {
        long value = 1L;
        instance.setContestId(value);

        assertEquals("'setContestId' should be correct.",
            value, BaseUnitTests.getField(instance, "contestId"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getOriginalPayment()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getOriginalPayment() {
        OriginalPayment value = new OriginalPayment();
        instance.setOriginalPayment(value);

        assertSame("'getOriginalPayment' should be correct.",
            value, instance.getOriginalPayment());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setOriginalPayment(double originalPayment)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setOriginalPayment() {
        OriginalPayment value = new OriginalPayment();
        instance.setOriginalPayment(value);

        assertSame("'setOriginalPayment' should be correct.",
            value, BaseUnitTests.getField(instance, "originalPayment"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getCpRate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getCpRate() {
        double value = 1;
        instance.setCpRate(value);

        assertEquals("'getCpRate' should be correct.",
            value, instance.getCpRate(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCpRate(double cpRate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setCpRate() {
        double value = 1;
        instance.setCpRate(value);

        assertEquals("'setCpRate' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "cpRate"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        instance = new ProjectContestCPConfig();
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("contestId:0"));
        assertTrue("'toString' should be correct.", res.contains("originalPayment:null"));
        assertTrue("'toString' should be correct.", res.contains("cpRate:0"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        OriginalPayment originalPayment = new OriginalPayment();
        originalPayment.setContestId(1);

        instance.setContestId(1);
        instance.setOriginalPayment(originalPayment);
        instance.setCpRate(3);
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("contestId:1"));
        assertTrue("'toString' should be correct.", res.contains("originalPayment:"));
        assertTrue("'toString' should be correct.", res.contains("cpRate:3"));
    }
}