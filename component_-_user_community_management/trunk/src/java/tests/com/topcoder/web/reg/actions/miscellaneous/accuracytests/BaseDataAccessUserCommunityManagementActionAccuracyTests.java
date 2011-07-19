/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.BaseDataAccessUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockDataAccessInt;

/**
 * <p>
 * Accuracy test for BaseDataAccessUserCommunityManagementAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDataAccessUserCommunityManagementActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of BaseDataAccessUserCommunityManagementAction used in test.
     * </p>
     * */
    private BaseDataAccessUserCommunityManagementAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BaseDataAccessUserCommunityManagementActionAccuracyTests.class);
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
        instance = new BaseDataAccessUserCommunityManagementAction() {
        };
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authKey");
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
     * Accuracy test for BaseDataAccessUserCommunityManagementAction(), the instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should be created.", instance);

        assertTrue("BaseDataAccessUserCommunityManagementAction should extend from BaseUserCommunityManagementAction.",
            instance instanceof ActionSupport);
    }

    /**
     * <p>
     * Accuracy test for dataAccess.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testDataAccess() throws Exception {
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);

        assertEquals("Incorrect value after set a new one", dataAccess, AccuracyTestHelper.InvokeMethod(
            BaseDataAccessUserCommunityManagementAction.class, instance, "getDataAccess"));
    }
}
