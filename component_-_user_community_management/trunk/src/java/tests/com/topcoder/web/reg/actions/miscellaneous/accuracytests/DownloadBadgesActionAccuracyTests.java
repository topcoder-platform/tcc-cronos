/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.BaseRatedUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.DownloadBadgesAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockDataAccessInt;

/**
 * <p>
 * Accuracy test for DownloadBadgesAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DownloadBadgesActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of DownloadBadgesAction used in test.
     * </p>
     * */
    private DownloadBadgesAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DownloadBadgesActionAccuracyTests.class);
        return suite;
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
        instance = new DownloadBadgesAction();
        instance.setLog(LogManager.getLog());

        instance.setAuthenticationSessionKey("authKey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authKey", new MockAuthentication());
        instance.setSession(session);

        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        DataAccessConstants.COMMAND = "COMMAND";

        MockAuthentication.setUserId(5L);
    }

    /**
     * <p>
     * Accuracy test for DownloadBadgesAction(). Instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);
        assertTrue("DownloadBadgesAction should extend from BaseRatedUserCommunityManagementAction.",
            instance instanceof BaseRatedUserCommunityManagementAction);
    }

    /**
     * Accuracy test for execute().
     *
     *
     * @throws Exception
     *             to jUnit
     */
    public void testExecute() throws Exception {
        String result = instance.execute();
        assertEquals("The execute is incorrect.", "success", result);
    }
}
