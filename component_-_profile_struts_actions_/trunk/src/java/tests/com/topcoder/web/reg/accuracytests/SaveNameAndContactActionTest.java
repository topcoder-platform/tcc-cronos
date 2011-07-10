/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.reg.actions.profile.BaseSaveProfileAction;
import com.topcoder.web.reg.actions.profile.SaveNameAndContactAction;

/**
 * <p>
 * Accuracy tests for the {@link SaveNameAndContactAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class SaveNameAndContactActionTest {
    /** Represents the instance used to test again. */
    private SaveNameAndContactAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new SaveNameAndContactAction();
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
     * Accuracy test for {@link SaveNameAndContactAction#SaveNameAndContactAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testSaveNameAndContactAction() throws Exception {
        new SaveNameAndContactAction();

        // Good
    }

}