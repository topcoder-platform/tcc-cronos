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
import com.topcoder.web.reg.actions.basic.RecoverPasswordAction;

/**
 * Accuracy test for class RecoverPasswordAction.
 *
 * @author extra
 * @version 1.0
 */
public class RecoverPasswordActionAccuracyTests extends TestCase {
    /**
     * Instance of action for test.
     */
    private RecoverPasswordAction action;

    /**
     * Set up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        action = new MockRecoverPasswordAction();

        action.setWebAuthenticationSessionKey("webAuthenticationSessionKey");

        AuditDAO auditDAO = new MockAuditDAO();
        action.setAuditDAO(auditDAO);

        action.setPasswordRecoveryDAO(new MockPasswordRecoveryDAO());

        action.setUserDAO(new MockUserDAO());

        MockHttpServletRequest request = new MockHttpServletRequest();
        WebAuthentication authentication = new MockWebAuthentication();
        request.setParameter("lastRequestedURIParameterName", "lastRequestedURI");
        action.setServletRequest(request);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("webAuthenticationSessionKey", authentication);
        session.put("other", "string");
        action.setSession(session);

        action.setResetPasswordLinkTemplate("&id=%passwordRecoveryId%&code=%hashCode%");
        action.setEmailBodyTemplateFilePath("test_files/accuracy/emailTemplate.txt");
        action.setEmail("tc@topcoder.com");
        action.setEmailFromAddress("tc@topcoder.com");
        action.setEmailSubject("Recovery password");
        action.setOperationType("recoverpassword");

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
        assertEquals("field errors", 1, action.getFieldErrors().size());
    }

    /**
     * Accuracy test for method validate.
     *
     * @throws Exception
     */
    public void test_validate_acc2() throws Exception {
        action.setHandle("user1");
        action.validate();
        assertEquals("field errors", 1, action.getFieldErrors().size());
    }

    /**
     * Accuracy test for method validate.
     *
     * @throws Exception
     */
    public void test_validate_acc3() throws Exception {
        action.setEmail("no@topcoder.com");
        action.validate();
        assertEquals("field errors", 1, action.getFieldErrors().size());
    }

    /**
     * Accuracy test for method execute.
     *
     * @throws Exception
     */
    public void test_execute_acc1() throws Exception {
        action.setHandle("user1");

        try {
            String result = action.execute();
            assertEquals("action return", Action.SUCCESS, result);
        } catch (BasicActionException e) {
            // ignore for email sending problem.
        }
    }
}
