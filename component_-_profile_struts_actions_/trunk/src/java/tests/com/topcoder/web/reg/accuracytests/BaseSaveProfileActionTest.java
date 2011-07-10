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
import com.topcoder.web.reg.actions.profile.BaseSaveProfileAction;
import com.topcoder.web.reg.actions.profile.EmailSendingProfileAction;
import com.topcoder.web.reg.actions.profile.SaveAccountInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link BaseSaveProfileAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class BaseSaveProfileActionTest {
    /** Represents the savedUser used to test again. */
    private User savedUserValue;

    /** Represents the instance used to test again. */
    private BaseSaveProfileAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new SaveAccountInfoAction();
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
     * Accuracy test for {@link BaseSaveProfileAction#BaseSaveProfileAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testBaseSaveProfileAction() throws Exception {
        new SaveAccountInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link BaseSaveProfileAction#getSavedUser()}
     * </p>
     * <p>
     * The value of <code>savedUser</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getSavedUser() throws Exception {
        assertNull("Old value", testInstance.getSavedUser());
    }

    /**
     * <p>
     * Accuracy test {@link BaseSaveProfileAction#setSavedUser(User)}.
     * </p>
     * <p>
     * The value of <code>savedUser</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setSavedUser() throws Exception {
        testInstance.setSavedUser(savedUserValue);
        assertEquals("New value", savedUserValue, testInstance.getSavedUser());
    }
}