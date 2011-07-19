/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.BaseUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.ShowCardDescriptionAction;

/**
 * Accuracy test for ShowCardDescriptionAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ShowCardDescriptionActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of ShowCardDescriptionAction used in test.
     * </p>
     * */
    private ShowCardDescriptionAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ShowCardDescriptionActionAccuracyTests.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ShowCardDescriptionAction();

        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authKey");
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for ShowCardDescriptionAction(). Instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);
        assertTrue("ShowCardDescriptionAction should extend from BaseUserCommunityManagementAction.",
            instance instanceof BaseUserCommunityManagementAction);
    }

    /**
     * Accuracy test for execute().
     *
     * @throws Exception
     *             to jUnit
     */
    public void testExecute() throws Exception {
        String result = instance.execute();
        assertEquals("Incorrect result", "success", result);
    }
}