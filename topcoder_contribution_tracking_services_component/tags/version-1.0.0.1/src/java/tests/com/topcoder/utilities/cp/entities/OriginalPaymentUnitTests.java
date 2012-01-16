/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link OriginalPayment} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class OriginalPaymentUnitTests {
    /**
     * <p>
     * Represents the <code>OriginalPayment</code> instance used in tests.
     * </p>
     */
    private OriginalPayment instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(OriginalPaymentUnitTests.class);
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
        instance = new OriginalPayment();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>OriginalPayment()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new OriginalPayment();

        assertEquals("'contestId' should be correct.",
            0L, BaseUnitTests.getField(instance, "contestId"));
        assertEquals("'prize1st' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "prize1st"), 0.001);
        assertEquals("'prize2nd' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "prize2nd"), 0.001);
        assertEquals("'prize3rd' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "prize3rd"), 0.001);
        assertEquals("'prize4th' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "prize4th"), 0.001);
        assertEquals("'prize5th' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "prize5th"), 0.001);
        assertEquals("'prizeCopilot' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "prizeCopilot"), 0.001);
        assertEquals("'prizeMilestone' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "prizeMilestone"), 0.001);
        assertEquals("'specReviewCost' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "specReviewCost"), 0.001);
        assertEquals("'reviewCost' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "reviewCost"), 0.001);
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
     * Accuracy test for the method <code>getPrize1st()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPrize1st() {
        double value = 1;
        instance.setPrize1st(value);

        assertEquals("'getPrize1st' should be correct.",
            value, instance.getPrize1st(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrize1st(double prize1st)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPrize1st() {
        double value = 1;
        instance.setPrize1st(value);

        assertEquals("'setPrize1st' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "prize1st"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPrize2nd()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPrize2nd() {
        double value = 1;
        instance.setPrize2nd(value);

        assertEquals("'getPrize2nd' should be correct.",
            value, instance.getPrize2nd(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrize2nd(double prize2nd)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPrize2nd() {
        double value = 1;
        instance.setPrize2nd(value);

        assertEquals("'setPrize2nd' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "prize2nd"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPrize3rd()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPrize3rd() {
        double value = 1;
        instance.setPrize3rd(value);

        assertEquals("'getPrize3rd' should be correct.",
            value, instance.getPrize3rd(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrize3rd(double prize3rd)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPrize3rd() {
        double value = 1;
        instance.setPrize3rd(value);

        assertEquals("'setPrize3rd' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "prize3rd"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPrize4th()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPrize4th() {
        double value = 1;
        instance.setPrize4th(value);

        assertEquals("'getPrize4th' should be correct.",
            value, instance.getPrize4th(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrize4th(double prize4th)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPrize4th() {
        double value = 1;
        instance.setPrize4th(value);

        assertEquals("'setPrize4th' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "prize4th"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPrize5th()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPrize5th() {
        double value = 1;
        instance.setPrize5th(value);

        assertEquals("'getPrize5th' should be correct.",
            value, instance.getPrize5th(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrize5th(double prize5th)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPrize5th() {
        double value = 1;
        instance.setPrize5th(value);

        assertEquals("'setPrize5th' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "prize5th"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPrizeCopilot()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPrizeCopilot() {
        double value = 1;
        instance.setPrizeCopilot(value);

        assertEquals("'getPrizeCopilot' should be correct.",
            value, instance.getPrizeCopilot(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrizeCopilot(double prizeCopilot)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPrizeCopilot() {
        double value = 1;
        instance.setPrizeCopilot(value);

        assertEquals("'setPrizeCopilot' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "prizeCopilot"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPrizeMilestone()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPrizeMilestone() {
        double value = 1;
        instance.setPrizeMilestone(value);

        assertEquals("'getPrizeMilestone' should be correct.",
            value, instance.getPrizeMilestone(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrizeMilestone(double prizeMilestone)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPrizeMilestone() {
        double value = 1;
        instance.setPrizeMilestone(value);

        assertEquals("'setPrizeMilestone' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "prizeMilestone"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getSpecReviewCost()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getSpecReviewCost() {
        double value = 1;
        instance.setSpecReviewCost(value);

        assertEquals("'getSpecReviewCost' should be correct.",
            value, instance.getSpecReviewCost(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSpecReviewCost(double specReviewCost)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setSpecReviewCost() {
        double value = 1;
        instance.setSpecReviewCost(value);

        assertEquals("'setSpecReviewCost' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "specReviewCost"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getReviewCost()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getReviewCost() {
        double value = 1;
        instance.setReviewCost(value);

        assertEquals("'getReviewCost' should be correct.",
            value, instance.getReviewCost(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReviewCost(double reviewCost)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setReviewCost() {
        double value = 1;
        instance.setReviewCost(value);

        assertEquals("'setReviewCost' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "reviewCost"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        instance = new OriginalPayment();
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("contestId:0"));
        assertTrue("'toString' should be correct.", res.contains("prize1st:0"));
        assertTrue("'toString' should be correct.", res.contains("prize2nd:0"));
        assertTrue("'toString' should be correct.", res.contains("prize3rd:0"));
        assertTrue("'toString' should be correct.", res.contains("prize4th:0"));
        assertTrue("'toString' should be correct.", res.contains("prize5th:0"));
        assertTrue("'toString' should be correct.", res.contains("prizeCopilot:0"));
        assertTrue("'toString' should be correct.", res.contains("prizeMilestone:0"));
        assertTrue("'toString' should be correct.", res.contains("specReviewCost:0"));
        assertTrue("'toString' should be correct.", res.contains("reviewCost:0"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance.setContestId(1);
        instance.setPrize1st(11);
        instance.setPrize2nd(12);
        instance.setPrize3rd(13);
        instance.setPrize4th(14);
        instance.setPrize5th(154);
        instance.setPrizeCopilot(16);
        instance.setPrizeMilestone(17);
        instance.setSpecReviewCost(18);
        instance.setReviewCost(19);
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("contestId:1"));
        assertTrue("'toString' should be correct.", res.contains("prize1st:11"));
        assertTrue("'toString' should be correct.", res.contains("prize2nd:12"));
        assertTrue("'toString' should be correct.", res.contains("prize3rd:13"));
        assertTrue("'toString' should be correct.", res.contains("prize4th:14"));
        assertTrue("'toString' should be correct.", res.contains("prize5th:15"));
        assertTrue("'toString' should be correct.", res.contains("prizeCopilot:16"));
        assertTrue("'toString' should be correct.", res.contains("prizeMilestone:17"));
        assertTrue("'toString' should be correct.", res.contains("specReviewCost:18"));
        assertTrue("'toString' should be correct.", res.contains("reviewCost:19"));
    }
}