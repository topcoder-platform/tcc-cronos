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
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.Referral;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.reg.actions.registration.CreateAccountAction;

/**
 * Accuracy tests for CreateAccountAction.
 * 
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class CreateAccountActionAccTests extends BaseAccuracyTest {
    /**
     * <p>
     * Represents the <code>CreateAccountAction</code> instance used to test against.
     * </p>
     */
    private CreateAccountAction action;

    /**
     * <p>Sets up the unit tests.</p>
     */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        action = new CreateAccountAction();
        action.setAuthenticationSessionKey("authenticationSessionKey");
        HashMap session = new HashMap();
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
    @SuppressWarnings("unchecked")
    public void test_execute() throws Exception {
        action.setAuditDAO(new AuditDAOAccuracyMock());
        
        action.setLogger(LogManager.getLog());
        action.setRegistrationTypeDAO(new RegistrationTypeDAOAccuracyMock());
        action.setUserDAO(new UserDAOAccuracyMock());
        action.setServletRequest(new MockHttpServletRequest());
        setUser();

        setSessionInfo();
        setUserRoles();
        setDocumentGenerator(action);
        
        action.getSession().put("authenticationSessionKey", new WebAuthenticationAccuracyMock());
        action.setPrincipalMgr(new PrincipalMgrAccuracyMock());
        action.setSecurityGroupDAO(new SecurityGroupDAOAccuracyMock());
        
        //assume the SMTP server is setup locally
        String ret = action.execute();
        assertEquals("The action should success executed", ret, Action.SUCCESS);
        assertEquals("The action should success audited", AuditDAOAccuracyMock.getRecord().getOperationType(),
            "create account");
        assertEquals("The action should success audited", UserDAOAccuracyMock.getUser().getActivationCode(),
            "E");
        assertTrue("The action should success audited", WebAuthenticationAccuracyMock.getIdsSet().contains(1L));
    }

    private void setSessionInfo() {
        action.setSessionInfoSessionKey("sessionInfoSessionKey");

        SessionInfo sessionInfo = new SessionInfo();
        action.getSession().put("sessionInfoSessionKey", sessionInfo);
    }


    private void setUserRoles() {
        Set<RegistrationType> userroles = new HashSet<RegistrationType>();
        userroles.add(new RegistrationType(RegistrationType.COMPETITION_ID));
        userroles.add( new RegistrationType(RegistrationType.SOFTWARE_ID));
        action.setUserRoles(userroles);
    }
    public static Test suite() {
        return new TestSuite(CreateAccountActionAccTests.class);
    }
    /**
     * Populate the user.
     * */
    private void setUser() {
        User user = new User();
        
        Email e = new Email();
        e.setAddress("mymail@topcoder.com");
        user.addEmailAddress(e);
        
        UserProfile p = new UserProfile();
        user.setHandle("Beijing2008");
        user.setUserProfile(p);
        
        Coder c = new Coder();
        CoderReferral cd = new CoderReferral();
        Referral r = new Referral();
        cd.setReferral(r);
        c.setCoderReferral(cd);
        p.setCoder(c);
        
        user.setId(1L);
        action.setUser(user );
        
    }
}
