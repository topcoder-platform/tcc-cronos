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
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockDataAccessInt;

/**
 * <p>
 * Accuracy test for BaseRatedUserCommunityManagementAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseRatedUserCommunityManagementActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of BaseDataAccessUserCommunityManagementAction used in test.
     * </p>
     * */
    private BaseRatedUserCommunityManagementAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BaseRatedUserCommunityManagementActionAccuracyTests.class);
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
    @SuppressWarnings("serial")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new BaseRatedUserCommunityManagementAction() {
        };
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
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for constructor BaseRatedUserCommunityManagementAction. It verifies instance is correctly created.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    public void test_BaseRatedUserCommunityManagementAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for isRated(). The correct value should be returned.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    public void testIsRated() throws Exception {
        long[] userIds = new long[] { 0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L };
        boolean[] isRateds = new boolean[] { false, true, true, true, true, true, true, true, true, true, true, true,
            true, true, false };

        for (int i = 0; i < userIds.length; i++) {
            MockAuthentication.setUserId(userIds[i]);
            assertEquals("The isRated is incorrect.", isRateds[i], ((Boolean) AccuracyTestHelper.InvokeMethod(
                BaseRatedUserCommunityManagementAction.class, instance, "isRated")).booleanValue());
        }
    }
}