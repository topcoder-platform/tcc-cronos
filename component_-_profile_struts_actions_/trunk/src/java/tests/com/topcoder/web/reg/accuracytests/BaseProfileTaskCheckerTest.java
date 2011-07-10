/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.AccountInfoTaskChecker;
import com.topcoder.web.reg.actions.profile.BaseProfileTaskChecker;

/**
 * <p>
 * Accuracy tests for the {@link BaseProfileTaskChecker}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class BaseProfileTaskCheckerTest {
    /** Represents the taskName used to test again. */
    private String taskNameValue;

    /** Represents the instance used to test again. */
    private BaseProfileTaskChecker testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new AccountInfoTaskChecker();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileTaskChecker#BaseProfileTaskChecker()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testBaseProfileTaskChecker() throws Exception {
        new AccountInfoTaskChecker();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link BaseProfileTaskChecker#getTaskName()}
     * </p>
     * <p>
     * The value of <code>taskName</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTaskName() throws Exception {
        assertNull("Old value", testInstance.getTaskName());
    }

    /**
     * <p>
     * Accuracy test {@link BaseProfileTaskChecker#setTaskName(String)}.
     * </p>
     * <p>
     * The value of <code>taskName</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setTaskName() throws Exception {
        testInstance.setTaskName(taskNameValue);
        assertEquals("New value", taskNameValue, testInstance.getTaskName());
    }

}