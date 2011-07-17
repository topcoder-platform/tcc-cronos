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

import com.topcoder.web.reg.actions.profile.BaseDisplayProfileAction;
import com.topcoder.web.reg.actions.profile.ViewAccountInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link ViewAccountInfoAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class ViewAccountInfoActionTest {
    /** Represents the referralUserHandle used to test again. */
    private String referralUserHandleValue;

    /** Represents the instance used to test again. */
    private ViewAccountInfoAction testInstance;

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
     * Accuracy test for {@link ViewAccountInfoAction#ViewAccountInfoAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testViewAccountInfoAction() throws Exception {
        new ViewAccountInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link ViewAccountInfoAction#getReferralUserHandle()}
     * </p>
     * <p>
     * The value of <code>referralUserHandle</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getReferralUserHandle() throws Exception {
        assertNull("Old value", testInstance.getReferralUserHandle());
    }

    /**
     * <p>
     * Accuracy test {@link ViewAccountInfoAction#setReferralUserHandle(String)}.
     * </p>
     * <p>
     * The value of <code>referralUserHandle</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setReferralUserHandle() throws Exception {
        testInstance.setReferralUserHandle(referralUserHandleValue);
        assertEquals("New value", referralUserHandleValue, testInstance.getReferralUserHandle());
    }

}