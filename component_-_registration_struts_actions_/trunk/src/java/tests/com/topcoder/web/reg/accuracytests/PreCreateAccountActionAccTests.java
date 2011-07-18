/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.Action;
import com.topcoder.randomstringimg.InvalidConfigException;
import com.topcoder.randomstringimg.RandomStringImage;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.registration.PreCreateAccountAction;
import com.topcoder.web.tc.Constants;

/**
 * Accuracy tests for PreCreateAccountAction.
 * 
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class PreCreateAccountActionAccTests extends BaseAccuracyTest {
    /**
     * <p>
     * Represents the <code>PreCreateAccountAction</code> instance used to test against.
     * </p>
     */
    private PreCreateAccountAction action;

    /**
     * <p>Sets up the unit tests.</p>
     */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        action = new PreCreateAccountAction();
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

        action.getSession().put("authenticationSessionKey", new WebAuthenticationAccuracyMock());
        //assume the random string image is well configured
        setCapthchaImageGenerator();
        
        //assume the SMTP server is setup locally
        String ret = action.execute();
        assertEquals("The action should success executed", ret, Action.SUCCESS);
        assertEquals("The action should success audited", AuditDAOAccuracyMock.getRecord().getOperationType(),
                "pre create account");
        assertTrue("The captcha word should be correct", ((String)action.getSession().get(Constants.CAPTCHA_WORD)).length() > 0);
        assertTrue("The capf name should be correct", action.getCapfname().length() > 0);
        new File(Constants.CAPTCHA_PATH + action.getCapfname()).delete();
    }

    public static Test suite() {
        return new TestSuite(PreCreateAccountActionAccTests.class);
    }
    
    private void setCapthchaImageGenerator() throws InvalidConfigException, IOException {
        RandomStringImage capthchaImageGenerator = new RandomStringImage();
        action.setCaptchaImageGenerator(capthchaImageGenerator);
    }
}
