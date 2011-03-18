/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.comparators;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.reliability.impl.UserProjectParticipationData;

/**
 * <p>
 * Unit tests for {@link UserProjectParticipationDataResolutionDateBasedComparator} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UserProjectParticipationDataResolutionDateBasedComparatorUnitTests {
    /**
     * <p>
     * Represents the <code>UserProjectParticipationDataResolutionDateBasedComparator</code> instance used in tests.
     * </p>
     */
    private UserProjectParticipationDataResolutionDateBasedComparator instance;

    /**
     * <p>
     * Represents the first user project participation data used in tests.
     * </p>
     */
    private UserProjectParticipationData data1;

    /**
     * <p>
     * Represents the second user project participation data used in tests.
     * </p>
     */
    private UserProjectParticipationData data2;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserProjectParticipationDataResolutionDateBasedComparatorUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new UserProjectParticipationDataResolutionDateBasedComparator();

        data1 = getData(new Date(), 1);
        data2 = getData(new Date(System.currentTimeMillis() + 100), 2);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserProjectParticipationDataResolutionDateBasedComparator()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new UserProjectParticipationDataResolutionDateBasedComparator();

        assertNotNull("Instance should be created.", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Equal1() {
        int res = instance.compare(data1, data1);

        assertTrue("'compare' should be correct.", res == 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Equal2() {
        data1 = null;
        int res = instance.compare(data1, data1);

        assertTrue("'compare' should be correct.", res == 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Equal3() {
        data2.setResolutionDate(data1.getResolutionDate());
        data2.setProjectId(data1.getProjectId());
        int res = instance.compare(data1, data2);

        assertTrue("'compare' should be correct.", res == 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Equal4() {
        data1.setResolutionDate(null);
        data2.setResolutionDate(data1.getResolutionDate());
        data2.setProjectId(data1.getProjectId());
        int res = instance.compare(data1, data2);

        assertTrue("'compare' should be correct.", res == 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Greater1() {
        int res = instance.compare(data2, data1);

        assertTrue("'compare' should be correct.", res > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Greater2() {
        data2 = null;
        int res = instance.compare(data1, data2);

        assertTrue("'compare' should be correct.", res > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Greater3() {
        data2.setResolutionDate(data1.getResolutionDate());
        int res = instance.compare(data2, data1);

        assertTrue("'compare' should be correct.", res > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Greater4() {
        data2.setResolutionDate(null);
        data2.setProjectId(data1.getProjectId());
        int res = instance.compare(data1, data2);

        assertTrue("'compare' should be correct.", res > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Less1() {
        int res = instance.compare(data1, data2);

        assertTrue("'compare' should be correct.", res < 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Less2() {
        data2 = null;
        int res = instance.compare(data2, data1);

        assertTrue("'compare' should be correct.", res < 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Less3() {
        data2.setResolutionDate(data1.getResolutionDate());
        int res = instance.compare(data1, data2);

        assertTrue("'compare' should be correct.", res < 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare(UserProjectParticipationData data1,
     * UserProjectParticipationData data2)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_compare_Less4() {
        data2.setResolutionDate(null);
        data2.setProjectId(data1.getProjectId());
        int res = instance.compare(data2, data1);

        assertTrue("'compare' should be correct.", res < 0);
    }

    /**
     * <p>
     * Creates an instance of UserProjectParticipationData with given values.
     * </p>
     *
     * @param resolutionDate
     *            the resolution date.
     * @param projectId
     *            the project id.
     *
     * @return the created UserProjectParticipationData instance.
     */
    private static UserProjectParticipationData getData(Date resolutionDate, long projectId) {
        UserProjectParticipationData data = new UserProjectParticipationData();

        data.setProjectId(projectId);
        data.setResolutionDate(resolutionDate);

        return data;
    }
}