/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.BaseUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.ShowCardInstructionsAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;

/**
 * Accuracy test for ShowCardInstructionsAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ShowCardInstructionsActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of BaseUserCommunityManagementAction used in test.
     * </p>
     * */
    private ShowCardInstructionsAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ShowCardInstructionsActionAccuracyTests.class);
    }

    /**
     * <p>
     * Set up for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockAuthentication.setUserId(5L);

        instance = new ShowCardInstructionsAction();

        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authKey");

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authKey", new MockAuthentication());
        instance.setSession(session);
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
        CardHelper.setUnlocked(5L, false);
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for ShowCardInstructionsAction().
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);

        assertTrue("ShowCardInstructionsAction should extend from BaseUserCommunityManagementAction.",
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
        assertEquals("The execute is incorrect.", "success", result);
        assertFalse("The execute is incorrect.", instance.isCardUnlocked());

        CardHelper.setUnlocked(5L, true);
        result = instance.execute();
        assertEquals("The execute is incorrect.", "success", result);
        assertTrue("The execute is incorrect.", instance.isCardUnlocked());
    }

    /**
     * <p>
     * Accuracy test for isCardUnlocked().
     * </p>
     */
    public void testIsCardUnlocked() {
        assertFalse("Incorrect result", instance.isCardUnlocked());
    }
}