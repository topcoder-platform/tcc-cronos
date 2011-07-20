/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.BaseRatedUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.PreviewCardAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockDataAccessInt;

/**
 * Accuracy test for PreviewCardAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PreviewCardActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of PreviewCardAction used in test.
     * </p>
     * */
    private PreviewCardAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(PreviewCardActionAccuracyTests.class);
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
        instance = new PreviewCardAction();
        instance.setLog(LogManager.getLog());

        instance.setAuthenticationSessionKey("authKey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authKey", new MockAuthentication());
        instance.setSession(session);

        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        DataAccessConstants.COMMAND = "COMMAND";
    }

    /**
     * <p>
     * Accuracy test for PreviewCardAction(). Instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);
        assertTrue("PreviewCardAction should extend from BaseRatedUserCommunityManagementAction.",
            instance instanceof BaseRatedUserCommunityManagementAction);
    }

    /**
     * Accuracy test for isCardUnlocked(). Default to false.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsCardUnlocked() throws Exception {
        assertFalse("The isCardUnlocked is incorrect.", instance.isCardUnlocked());
    }

    /**
     * Accuracy test for execute().
     *
     * @throws Exception
     *             to jUnit
     */
    public void testExecute() throws Exception {
        MockAuthentication.setUserId(21L);
        CardHelper.setUnlocked(21L, true);
        String result = instance.execute();

        assertEquals("The execute is incorrect.", "success", result);

        assertTrue("The execute is incorrect.", instance.isCardUnlocked());
    }
}
