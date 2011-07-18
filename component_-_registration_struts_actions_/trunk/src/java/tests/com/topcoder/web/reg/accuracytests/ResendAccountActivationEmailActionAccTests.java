/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.Action;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.reg.actions.registration.ResendAccountActivationEmailAction;

/**
 * Accuracy tests for ResendAccountActivationEmailAction.
 * 
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ResendAccountActivationEmailActionAccTests extends BaseAccuracyTest {
    /**
     * <p>
     * Represents the <code>ResendAccountActivationEmailAction</code> instance used to test against.
     * </p>
     */
    private ResendAccountActivationEmailAction action;

    /**
     * <p>Sets up the unit tests.</p>
     */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        action = new ResendAccountActivationEmailAction();
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

        setSessionInfo();
        
        action.getSession().put("authenticationSessionKey", new WebAuthenticationAccuracyMock());


        setUserRoles();
        
        //assume the SMTP server is setup locally
        String ret = action.execute();
        assertEquals("The action should success executed", ret, Action.SUCCESS);
        assertEquals("The action should success audited", AuditDAOAccuracyMock.getRecord().getOperationType(),
                "resend account activation");
    }

    private void setUserRoles() {
        Set<RegistrationType> userroles = new HashSet<RegistrationType>();
        userroles.add(new RegistrationType(RegistrationType.COMPETITION_ID));
        userroles.add( new RegistrationType(RegistrationType.SOFTWARE_ID));
        action.setUserRoles(userroles);
    }

    private void setSessionInfo() {
        action.setSessionInfoSessionKey("sessionInfoSessionKey");

        SessionInfo sessionInfo = new SessionInfo();
        action.getSession().put("sessionInfoSessionKey", sessionInfo);
    }
    public static Test suite() {
        return new TestSuite(ResendAccountActivationEmailActionAccTests.class);
    }
}
