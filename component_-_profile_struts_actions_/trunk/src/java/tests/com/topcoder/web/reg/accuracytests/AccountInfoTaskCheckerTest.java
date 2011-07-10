/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.AccountInfoTaskChecker;
import com.topcoder.web.reg.actions.profile.ProfileTaskReport;

/**
 * <p>
 * Accuracy tests for the {@link AccountInfoTaskChecker}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class AccountInfoTaskCheckerTest {
    /** Represents the instance used to test again. */
    private AccountInfoTaskChecker testInstance;

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
     * Accuracy test for {@link AccountInfoTaskChecker#AccountInfoTaskChecker()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testAccountInfoTaskChecker() throws Exception {
        new AccountInfoTaskChecker();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link AccountInfoTaskChecker#getTaskReport()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTaskReport() throws Exception {
        User user = new User();
        user.setHandle("handle");
        testInstance.setTaskName("taskName");
        ProfileTaskReport report = testInstance.getTaskReport(user);
        Assert.assertEquals("taskName", "taskName", report.getTaskName());
        Assert.assertEquals("completed", true, report.getCompleted());
    }
}