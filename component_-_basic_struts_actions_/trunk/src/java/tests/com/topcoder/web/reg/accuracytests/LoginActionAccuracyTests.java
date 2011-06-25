/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.topcoder.security.login.LoginRemote;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.actions.basic.LoginAction;

/**
 * Accuracy test for class LoginAction.
 *
 * @author extra
 * @version 1.0
 */
public class LoginActionAccuracyTests extends TestCase {

    /**
     * Instance of action for test.
     */
    private LoginAction action;

    /**
     * Set up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        action = new LoginAction();

        LoginRemote loginRemote = new MockLoginRemote();
        action.setLoginRemote(loginRemote);

        action.setWebAuthenticationSessionKey("webAuthenticationSessionKey");

        AuditDAO auditDAO = new MockAuditDAO();
        action.setAuditDAO(auditDAO);

        action.setUserDAO(new MockUserDAO());

        action.setUsername("user1");
        action.setLastRequestedURIParameterName("lastRequestedURIParameterName");

        MockHttpServletRequest request = new MockHttpServletRequest();
        WebAuthentication authentication = new MockWebAuthentication();
        request.setParameter("lastRequestedURIParameterName", "lastRequestedURI");
        action.setServletRequest(request);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("webAuthenticationSessionKey", authentication);
        session.put("other", "string");
        action.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();
        action.setServletResponse(response);

        action.setOperationType("login");

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
     * Accuracy test for method execute. If status of user is active, status of primaryEmail is not active,
     * emailActivation should be return.
     *
     * @throws Exception
     */
    public void test_execute_acc1() throws Exception {
        String result = action.execute();
        assertEquals("result should be EMAIL_ACTIVATION", LoginAction.EMAIL_ACTIVATION, result);
    }

    /**
     * Accuracy test for method execute. If status of user is active, status of primaryEmail is active, has operation,
     * null should be return.
     *
     * @throws Exception
     */
    public void test_execute_acc2() throws Exception {
        action.setActiveEmailStatus(2);

        String result = action.execute();
        assertNull("result should be null", result);
    }

    /**
     * Accuracy test for method execute. If status of user is active, status of primaryEmail is active, no operation,
     * FIRST_TIME_LOGIN should be return.
     *
     * @throws Exception
     */
    public void test_execute_acc3() throws Exception {
        action.setActiveEmailStatus(2);
        action.setUsername("user0");

        String result = action.execute();
        assertEquals("result should be FIRST_TIME_LOGIN", LoginAction.FIRST_TIME_LOGIN, result);
    }

    /**
     * Accuracy test for method execute. If status of user is active, status of primaryEmail is active, has operation,
     * SUCCESS should be return.
     *
     * @throws Exception
     */
    public void test_execute_acc4() throws Exception {
        action.setActiveEmailStatus(2);

        action.setLastRequestedURIParameterName("notset");

        String result = action.execute();
        assertEquals("result should be SUCCESS", LoginAction.SUCCESS, result);
    }

}
