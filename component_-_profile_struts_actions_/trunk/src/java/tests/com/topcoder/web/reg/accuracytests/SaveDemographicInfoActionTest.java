/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.BaseSaveProfileAction;
import com.topcoder.web.reg.actions.profile.SaveDemographicInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link SaveDemographicInfoAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class SaveDemographicInfoActionTest {
    /** Represents the instance used to test again. */
    private SaveDemographicInfoAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new SaveDemographicInfoAction();
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
     * Accuracy test for {@link SaveDemographicInfoAction#SaveDemographicInfoAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testSaveDemographicInfoAction() throws Exception {
        new SaveDemographicInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link SaveDemographicInfoAction#validate()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_validate() throws Exception {
        User savedUser = new User();
        testInstance.setSavedUser(savedUser);
        testInstance.validate();

        Collection<String> aErrors = testInstance.getActionErrors();
        Map<String, List<String>> fErrors = testInstance.getFieldErrors();

        Assert.assertEquals("action errors", 0, aErrors.size());
        Assert.assertEquals("field errors", 0, fErrors.size());
    }
}