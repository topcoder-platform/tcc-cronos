/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.BaseDisplayProfileAction;
import com.topcoder.web.reg.actions.profile.BaseProfileAction;
import com.topcoder.web.reg.actions.profile.ViewAccountInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link BaseDisplayProfileAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class BaseDisplayProfileActionTest {
    /** Represents the displayedUser used to test again. */
    private User displayedUserValue;

    /** Represents the instance used to test again. */
    private BaseDisplayProfileAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ViewAccountInfoAction();
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
     * Accuracy test for {@link BaseDisplayProfileAction#BaseDisplayProfileAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testBaseDisplayProfileAction() throws Exception {
        new ViewAccountInfoAction();
        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link BaseDisplayProfileAction#getDisplayedUser()}
     * </p>
     * <p>
     * The value of <code>displayedUser</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getDisplayedUser() throws Exception {
        assertNull("Old value", testInstance.getDisplayedUser());
    }

    /**
     * <p>
     * Accuracy test {@link BaseDisplayProfileAction#setDisplayedUser(User)}.
     * </p>
     * <p>
     * The value of <code>displayedUser</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setDisplayedUser() throws Exception {
        testInstance.setDisplayedUser(displayedUserValue);
        assertEquals("New value", displayedUserValue, testInstance.getDisplayedUser());
    }
}