/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.Action;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.reg.actions.registration.ViewRegistrationPreferenceAction;

/**
 * Accuracy tests for ViewRegistrationPreferenceAction.
 * 
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ViewRegistrationPreferenceActionAccTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ViewRegistrationPreferenceAction</code> instance used to test against.
     * </p>
     */
    private ViewRegistrationPreferenceAction action;

    /**
     * <p>Sets up the unit tests.</p>
     */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        action = new ViewRegistrationPreferenceAction();
        action.setAuthenticationSessionKey("authenticationSessionKey");
        HashMap session = new HashMap();
        session.put("authenticationSessionKey", new WebAuthenticationAccuracyMock());
        action.setSession(session);
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

        String ret = action.execute();
        assertEquals("The action should success executed", ret, Action.SUCCESS);
        assertEquals("The action should success audited", AuditDAOAccuracyMock.getRecord().getOperationType(), "view registration preference");
        assertEquals("The registration type is wrong", 3, action.getUserRegistrationTypes().size());
        assertTrue("The registration type is wrong", action.getUserRegistrationTypes().contains(new RegistrationType(RegistrationType.HIGH_SCHOOL_ID)));
        assertTrue("The registration type is wrong", action.getUserRegistrationTypes().contains(new RegistrationType(RegistrationType.COMPETITION_ID)));
        assertTrue("The registration type is wrong", action.getUserRegistrationTypes().contains(new RegistrationType(RegistrationType.SOFTWARE_ID)));
        assertEquals("The registration type is wrong", 2, action.getAllRegistrationTypes().size());
        assertTrue("The registration type is wrong", action.getAllRegistrationTypes().contains(new RegistrationType(RegistrationType.SOFTWARE_ID)));
        assertTrue("The registration type is wrong", action.getAllRegistrationTypes().contains(new RegistrationType(RegistrationType.STUDIO_ID)));
    }

    public static Test suite() {
        return new TestSuite(ViewRegistrationPreferenceActionAccTests.class);
    }
}
