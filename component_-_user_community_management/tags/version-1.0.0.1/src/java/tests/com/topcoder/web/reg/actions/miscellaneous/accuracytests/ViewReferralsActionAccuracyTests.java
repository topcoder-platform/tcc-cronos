/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.BaseDataAccessUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.ReferralData;
import com.topcoder.web.reg.actions.miscellaneous.ViewReferralsAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockDataAccessInt;

/**
 * Accuracy test for ViewReferralsAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewReferralsActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of ViewReferralsAction used in test.
     * </p>
     * */
    private ViewReferralsAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ViewReferralsActionAccuracyTests.class);
        return suite;
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
        instance = new ViewReferralsAction();

        instance.setLog(LogManager.getLog());

        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);

        instance.setAuthenticationSessionKey("authKey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authKey", new MockAuthentication());
        instance.setSession(session);

        DataAccessConstants.COMMAND = "COMMAND";
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
     * Accuracy test for ViewReferralsAction().
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);

        assertTrue("ViewReferralsAction should extend from BaseDataAccessUserCommunityManagementAction.",
            instance instanceof BaseDataAccessUserCommunityManagementAction);
    }

    /**
     * Accuracy test for execute().
     *
     * @throws Exception
     *             to Junit
     */
    public void testExecute() throws Exception {
        String result = instance.execute();
        assertEquals("Incorrect result", "success", result);

        List<ReferralData> list = instance.getReferrals();
        assertEquals("The execut is incorrect.", 2, list.size());
        assertEquals("Incorrect result", 1, list.get(0).getCoderId());
        assertEquals("Incorrect result", 2, list.get(0).getRating());
        assertEquals("Incorrect result", "mess", list.get(0).getHandle());
        assertEquals("Incorrect result", new Timestamp(100000), list.get(0).getMemberSince());

        assertEquals("Incorrect result", 2, list.get(1).getCoderId());
        assertEquals("Incorrect result", 3, list.get(1).getRating());
        assertEquals("Incorrect result", "tc", list.get(1).getHandle());
        assertEquals("Incorrect result", new Timestamp(200000), list.get(1).getMemberSince());
    }

    /**
     * Accuracy test for getReferrals(). Default to null.
     */
    public void testGetReferrals() {
        assertNull("Default to null.", instance.getReferrals());
    }
}
