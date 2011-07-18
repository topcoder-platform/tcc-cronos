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
import com.topcoder.web.reg.actions.registration.ActivateAccountAction;

/**
 * Accuracy tests for ActivateAccountAction.
 * 
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ActivateAccountActionAccTests extends BaseAccuracyTest {
    /**
     * <p>
     * Represents the <code>ActivateAccountAction</code> instance used to test against.
     * </p>
     */
    private ActivateAccountAction action;

    /**
     * <p>Sets up the unit tests.</p>
     */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        action = new ActivateAccountAction();
        action.setAuthenticationSessionKey("authenticationSessionKey");
        HashMap session = new HashMap();
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
    @SuppressWarnings("unchecked")
    public void test_execute() throws Exception {
        action.setAuditDAO(new AuditDAOAccuracyMock());
        
        action.setLogger(LogManager.getLog());
        action.setRegistrationTypeDAO(new RegistrationTypeDAOAccuracyMock());
        action.setUserDAO(new UserDAOAccuracyMock());
        action.setServletRequest(new MockHttpServletRequest());
        

        action.getSession().put("authenticationSessionKey", new WebAuthenticationAccuracyMock());

        //assume the ldap server is setup locally
        //assume the SMTP server is setup locally
        String ret = action.execute();
        assertEquals("The action should success executed", ret, Action.SUCCESS);
        assertEquals("The action should success audited", AuditDAOAccuracyMock.getRecord().getOperationType(),
                "activate account");
    }

    public static Test suite() {
        return new TestSuite(ActivateAccountActionAccTests.class);
    }
}
