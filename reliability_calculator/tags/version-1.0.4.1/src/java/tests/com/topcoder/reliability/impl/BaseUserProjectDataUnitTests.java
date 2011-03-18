/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.reliability.TestsHelper;

/**
 * <p>
 * Unit tests for {@link BaseUserProjectData} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseUserProjectDataUnitTests {
    /**
     * <p>
     * Represents the <code>BaseUserProjectData</code> instance used in tests.
     * </p>
     */
    private BaseUserProjectData instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseUserProjectDataUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new UserProjectParticipationData();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseUserProjectData()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new UserProjectParticipationData();

        assertEquals("'projectId' should be correct.", 0L, TestsHelper.getField(instance, "projectId"));
        assertEquals("'userId' should be correct.", 0L, TestsHelper.getField(instance, "userId"));
        assertNull("'resolutionDate' should be correct.", TestsHelper.getField(instance, "resolutionDate"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectId() {
        long value = 1;
        instance.setProjectId(value);

        assertEquals("'projectId' value should be properly retrieved.", value, instance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectId(long projectId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectId() {
        long value = 1;
        instance.setProjectId(value);

        assertEquals("'projectId' value should be properly set.", value, TestsHelper.getField(instance, "projectId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUserId() {
        long value = 1;
        instance.setUserId(value);

        assertEquals("'userId' value should be properly retrieved.", value, instance.getUserId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserId(long userId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUserId() {
        long value = 1;
        instance.setUserId(value);

        assertEquals("'userId' value should be properly set.", value, TestsHelper.getField(instance, "userId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getResolutionDate()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getResolutionDate() {
        Date value = new Date();
        instance.setResolutionDate(value);

        assertSame("'resolutionDate' value should be properly retrieved.", value, instance.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setResolutionDate(Date resolutionDate)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setResolutionDate() {
        Date value = new Date();
        instance.setResolutionDate(value);

        assertSame("'resolutionDate' value should be properly set.",
            value, TestsHelper.getField(instance, "resolutionDate"));
    }
}