/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.Action;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.registration.SelectRegistrationPreferenceAction;

/**
 * Accuracy tests for SelectRegistrationPreferenceAction.
 * 
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class SelectRegistrationPreferenceActionAccTests extends BaseAccuracyTest {
    /**
     * <p>
     * Represents the <code>SelectRegistrationPreferenceAction</code> instance used to test against.
     * </p>
     */
    private SelectRegistrationPreferenceAction action;

    /**
     * <p>Sets up the unit tests.</p>
     */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        action = new SelectRegistrationPreferenceAction();
        action.setAuthenticationSessionKey("authenticationSessionKey");
        HashMap session = new HashMap();
        session.put("authenticationSessionKey", new WebAuthenticationAccuracyMock());
        action.setSession(session);
        setDocumentGenerator(action);
    }

    /**
     * <p>Cleans up the unit tests.</p>
     */
    public void tearDown() {
        action = null;
    }

    /**
     * Accuracy test for method execute().
     * 
     * @throws Exception  to junit
     */
    public void test_execute() throws Exception {
        action.setAuditDAO(new AuditDAOAccuracyMock());
        
        action.setLogger(LogManager.getLog());
        action.setRegistrationTypeDAO(new RegistrationTypeDAOAccuracyMock());
        action.setUserDAO(new UserDAOAccuracyMock());
        action.setServletRequest(new MockHttpServletRequest());

        //assume the SMTP server is setup locally
        String ret = action.execute();
        assertEquals("The action should success executed", ret, Action.SUCCESS);
        assertEquals("The action should success audited", AuditDAOAccuracyMock.getRecord().getOperationType(), "select registration preference");
    }

    public static Test suite() {
        return new TestSuite(SelectRegistrationPreferenceActionAccTests.class);
    }
}
