/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.actions.basic.LogoutAction;

/**
 * Accuracy test for class LogoutAction.
 *
 * @author extra
 * @version 1.0
 */
public class LogoutActionAccuracyTests extends TestCase {

    /**
     * Instance of action for test.
     */
    private LogoutAction action;

    /**
     * Set up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        action = new LogoutAction();

        action.setWebAuthenticationSessionKey("webAuthenticationSessionKey");

        AuditDAO auditDAO = new MockAuditDAO();
        action.setAuditDAO(auditDAO);

        MockHttpServletRequest request = new MockHttpServletRequest();
        WebAuthentication authentication = new MockWebAuthentication();
        request.setParameter("lastRequestedURIParameterName", "lastRequestedURI");

        MockHttpSession httpSession = new MockHttpSession();
        request.setSession(httpSession);

        action.setServletRequest(request);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("webAuthenticationSessionKey", authentication);
        action.setSession(session);

        action.setOperationType("logout");

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
     * Accuracy test for method execute. SUCCESS should be return.
     *
     * @throws Exception
     */
    public void test_execute_acc1() throws Exception {
        String result = action.execute();
        assertEquals("result should be SUCCESS", LogoutAction.SUCCESS, result);
    }
}
