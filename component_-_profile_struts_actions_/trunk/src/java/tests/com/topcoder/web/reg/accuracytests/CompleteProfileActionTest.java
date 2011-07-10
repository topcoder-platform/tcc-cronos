/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.BaseProfileAction;
import com.topcoder.web.reg.actions.profile.CompleteProfileAction;
import com.topcoder.web.reg.actions.profile.CurrentTab;

/**
 * <p>
 * Accuracy tests for the {@link CompleteProfileAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class CompleteProfileActionTest {
    /** Represents the currentTab used to test again. */
    private CurrentTab currentTabValue;

    /** Represents the instance used to test again. */
    private CompleteProfileAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new CompleteProfileAction();
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
     * Accuracy test for {@link CompleteProfileAction#CompleteProfileAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testCompleteProfileAction() throws Exception {
        new CompleteProfileAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link CompleteProfileAction#getCurrentTab()}
     * </p>
     * <p>
     * The value of <code>currentTab</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCurrentTab() throws Exception {
        assertNull("Old value", testInstance.getCurrentTab());
    }

    /**
     * <p>
     * Accuracy test {@link CompleteProfileAction#setCurrentTab(CurrentTab)}.
     * </p>
     * <p>
     * The value of <code>currentTab</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCurrentTab() throws Exception {
        testInstance.setCurrentTab(currentTabValue);
        assertEquals("New value", currentTabValue, testInstance.getCurrentTab());
    }

    /**
     * <p>
     * Accuracy test for {@link CompleteProfileAction#execute()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_execute() throws Exception {
        testInstance.setCurrentTab(CurrentTab.CONTACT_TAB);
        Assert.assertEquals("result", "Save Contact", testInstance.execute());
        testInstance.setCurrentTab(CurrentTab.DEMOGRAPHIC_TAB);
        Assert.assertEquals("result", "Save Demographic", testInstance.execute());
        testInstance.setCurrentTab(CurrentTab.ACCOUNT_TAB);
        Assert.assertEquals("result", "Save Account", testInstance.execute());
    }
}