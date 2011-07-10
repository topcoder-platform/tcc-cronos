/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.BaseDisplayProfileAction;
import com.topcoder.web.reg.actions.profile.ViewNameAndContactAction;

/**
 * <p>
 * Accuracy tests for the {@link ViewNameAndContactAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class ViewNameAndContactActionTest {
    /** Represents the instance used to test again. */
    private ViewNameAndContactAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ViewNameAndContactAction();
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
     * Accuracy test for {@link ViewNameAndContactAction#ViewNameAndContactAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testViewNameAndContactAction() throws Exception {
        new ViewNameAndContactAction();

        // Good
    }

}