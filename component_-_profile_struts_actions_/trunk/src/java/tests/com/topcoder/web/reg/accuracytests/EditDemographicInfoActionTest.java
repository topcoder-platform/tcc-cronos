/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.EditDemographicInfoAction;
import com.topcoder.web.reg.actions.profile.ViewDemographicInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link EditDemographicInfoAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class EditDemographicInfoActionTest {
    /** Represents the instance used to test again. */
    private EditDemographicInfoAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new EditDemographicInfoAction();
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
     * Accuracy test for {@link EditDemographicInfoAction#EditDemographicInfoAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testEditDemographicInfoAction() throws Exception {
        new EditDemographicInfoAction();

        // Good
    }

}