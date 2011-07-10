/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.Action;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.actions.basic.ResetPasswordAction;

import junit.framework.TestCase;

/**
 * Accuracy test for class ResetPasswordAction.
 *
 * @author extra
 * @version 1.0
 */
public class ResetPasswordActionAccuracyTests extends TestCase {
    /**
     * Instance of action for test.
     */
    private ResetPasswordAction action;

    /**
     * Set up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        action = new ResetPasswordAction();

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

        action.setPrincipalMgrRemote(new MockPrincipalMgrRemote());

        action.setOperationType("resetPassword");

        action.setMinimalPasswordLength(6);
        action.setMaximalPasswordLength(18);

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
     * Accuracy test for method validate.
     *
     * @throws Exception
     */
    public void test_validate_acc1() throws Exception {
        action.validate();
        assertTrue("action errors", action.getActionErrors().size() > 0);
    }

    /**
     * Accuracy test for method execute.
     *
     * @throws Exception
     */
    public void test_execute_acc1() throws Exception {
        String result = action.execute();
        assertEquals("execute result", Action.SUCCESS, result);
    }

}
