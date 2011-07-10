/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.BaseDisplayProfileAction;
import com.topcoder.web.reg.actions.profile.ViewDemographicInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link ViewDemographicInfoAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class ViewDemographicInfoActionTest {
    /** Represents the instance used to test again. */
    private ViewDemographicInfoAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ViewDemographicInfoAction();
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
     * Accuracy test for {@link ViewDemographicInfoAction#ViewDemographicInfoAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testViewDemographicInfoAction() throws Exception {
        new ViewDemographicInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link ViewDemographicInfoAction#performAdditionalTasks()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_performAdditionalTasks() throws Exception {

    }
}