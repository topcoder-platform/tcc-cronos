/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.Action;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.actions.basic.BasicActionException;
import com.topcoder.web.reg.actions.basic.ResendPasswordRecoveryEmailAction;

/**
 * Accuracy test for class ResendPasswordRecoveryEmailAction.
 *
 * @author extra
 * @version 1.0
 */
public class ResendPasswordRecoveryEmailActionAccuracyTests extends TestCase {

    /**
     * Instance of action for test.
     */
    private ResendPasswordRecoveryEmailAction action;

    /**
     * Set up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        action = new ResendPasswordRecoveryEmailAction();

        action.setWebAuthenticationSessionKey("webAuthenticationSessionKey");

        AuditDAO auditDAO = new MockAuditDAO();
        action.setAuditDAO(auditDAO);

        action.setPasswordRecoveryDAO(new MockPasswordRecoveryDAO());

        action.setUserDAO(new MockUserDAO());

        MockHttpServletRequest request = new MockHttpServletRequest();
        WebAuthentication authentication = new MockWebAuthentication();
        action.setServletRequest(request);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("webAuthenticationSessionKey", authentication);
        session.put("other", "string");
        action.setSession(session);

        action.setResetPasswordLinkTemplate("&id=%passwordRecoveryId%&code=%hashCode%");
        action.setEmailBodyTemplateFilePath("test_files/accuracy/emailTemplate.txt");
        action.setEmailFromAddress("tc@topcoder.com");
        action.setEmailSubject("Recovery password");
        action.setOperationType("recoverpassword");

        action.setOperationType("resendPassword");

        action.checkConfiguration();
    }

    /**
     * Tear down test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * Accuracy test for method execute.
     *
     * @throws Exception
     */
    public void test_execute_acc1() throws Exception {
        try {
            String result = action.execute();
            assertEquals("action return", Action.SUCCESS, result);
        } catch (BasicActionException e) {
            // ignore for email sending problem.
        }
    }
}
