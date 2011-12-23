/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link MemberContributionPoints} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class MemberContributionPointsUnitTests {
    /**
     * <p>
     * Represents the <code>MemberContributionPoints</code> instance used in tests.
     * </p>
     */
    private MemberContributionPoints instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MemberContributionPointsUnitTests.class);
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
        instance = new MemberContributionPoints();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>MemberContributionPoints()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MemberContributionPoints();

        assertEquals("'id' should be correct.",
            0L, BaseUnitTests.getField(instance, "id"));
        assertEquals("'userId' should be correct.",
            0L, BaseUnitTests.getField(instance, "userId"));
        assertEquals("'contestId' should be correct.",
            0L, BaseUnitTests.getField(instance, "contestId"));
        assertEquals("'contributionPoints' should be correct.",
            0, (Double) BaseUnitTests.getField(instance, "contributionPoints"), 0.001);
        assertNull("'contributionType' should be correct.", BaseUnitTests.getField(instance, "contributionType"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getId() {
        long value = 1L;
        instance.setId(value);

        assertEquals("'getId' should be correct.",
            value, instance.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId(long id)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setId() {
        long value = 1L;
        instance.setId(value);

        assertEquals("'setId' should be correct.",
            value, BaseUnitTests.getField(instance, "id"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getUserId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'getUserId' should be correct.",
            value, instance.getUserId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserId(long userId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'setUserId' should be correct.",
            value, BaseUnitTests.getField(instance, "userId"));
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
     * Accuracy test for the method <code>getContributionPoints()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getContributionPoints() {
        double value = 1;
        instance.setContributionPoints(value);

        assertEquals("'getContributionPoints' should be correct.",
            value, instance.getContributionPoints(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContributionPoints(double contributionPoints)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setContributionPoints() {
        double value = 1;
        instance.setContributionPoints(value);

        assertEquals("'setContributionPoints' should be correct.",
            value, (Double) BaseUnitTests.getField(instance, "contributionPoints"), 0.001);
    }


    /**
     * <p>
     * Accuracy test for the method <code>getContributionType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getContributionType() {
        String value = "new_value";
        instance.setContributionType(value);

        assertEquals("'getContributionType' should be correct.",
            value, instance.getContributionType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContributionType(String contributionType)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setContributionType() {
        String value = "new_value";
        instance.setContributionType(value);

        assertEquals("'setContributionType' should be correct.",
            value, BaseUnitTests.getField(instance, "contributionType"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        instance = new MemberContributionPoints();
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("id:0"));
        assertTrue("'toString' should be correct.", res.contains("userId:0"));
        assertTrue("'toString' should be correct.", res.contains("contestId:0"));
        assertTrue("'toString' should be correct.", res.contains("contributionPoints:0"));
        assertTrue("'toString' should be correct.", res.contains("contributionType:null"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>toString()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        instance.setId(1);
        instance.setUserId(2);
        instance.setContestId(3);
        instance.setContributionPoints(4);
        instance.setContributionType("ct1");
        String res = instance.toString();

        assertTrue("'toString' should be correct.", res.contains("id:1"));
        assertTrue("'toString' should be correct.", res.contains("userId:2"));
        assertTrue("'toString' should be correct.", res.contains("contestId:3"));
        assertTrue("'toString' should be correct.", res.contains("contributionPoints:4"));
        assertTrue("'toString' should be correct.", res.contains("contributionType:ct1"));
    }
}