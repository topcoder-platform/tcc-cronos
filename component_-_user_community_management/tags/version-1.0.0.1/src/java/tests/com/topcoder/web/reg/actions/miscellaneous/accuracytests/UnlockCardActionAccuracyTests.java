/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.ServletRequestAware;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.BaseRatedUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.UnlockCardAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuditDao;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockDataAccessInt;

/**
 * Accuracy test for UnlockCardAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnlockCardActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of UnlockCardAction used in test.
     * </p>
     * */
    private UnlockCardAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(UnlockCardActionAccuracyTests.class);
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
        instance = new UnlockCardAction();
        instance.setLog(LogManager.getLog());

        instance.setAuthenticationSessionKey("authKey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authKey", new MockAuthentication());

        instance.setSession(session);
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);

        instance.setAuditDAO(new MockAuditDao());
        instance.setServletRequest(new MockHttpServletRequest());
        DataAccessConstants.COMMAND = "COMMAND";
    }

    /**
     * <p>
     * Accuracy test for UnlockCardAction(). Instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);
        assertTrue("UnlockCardAction should extend from BaseRatedUserCommunityManagementAction.",
            instance instanceof BaseRatedUserCommunityManagementAction);
        assertTrue("UnlockCardAction should impelement ServletRequestAware.", instance instanceof ServletRequestAware);
    }

    /**
     * Accuracy test for execute().
     *
     * @throws Exception
     *             to Junit
     */
    public void testExecute() throws Exception {
        assertFalse("Default to false.", instance.isCardUnlocked());
        String result = instance.execute();

        assertEquals("The execute is incorrect.", "success", result);
        assertTrue("The execute is incorrect.", instance.isCardUnlocked());
    }

    /**
     * <p>
     * Accuracy test for setServletRequest(HttpServletRequest request).
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetServletRequest() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();

        instance.setServletRequest(request);
        assertEquals("The setServletRequest is incorrect.", request, AccuracyTestHelper.getFieldValue(
            UnlockCardAction.class, instance, "request"));
    }

    /**
     * <p>
     * Accuracy test for setAuditDAO(AuditDAO auditDAO).
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetAuditDAO() throws Exception {
        MockAuditDao dao = new MockAuditDao();
        instance.setAuditDAO(dao);
        assertEquals("The setAuditDAO is incorrect.", dao, AccuracyTestHelper.getFieldValue(UnlockCardAction.class,
            instance, "auditDAO"));
    }

    /**
     * Accuracy test for isCardUnlocked().
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsCardUnlocked() throws Exception {
        assertFalse("The isCardUnlocked is incorrect.", instance.isCardUnlocked());
    }
}