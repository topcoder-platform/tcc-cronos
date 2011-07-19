/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.reg.actions.miscellaneous.BaseUserCommunityManagementAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;

/**
 * Accuracy test for BaseUserCommunityManagementAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseUserCommunityManagementActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of BaseUserCommunityManagementAction used in test.
     * </p>
     * */
    private BaseUserCommunityManagementAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(BaseUserCommunityManagementActionAccuracyTests.class);
    }

    /**
     * <p>
     * Set up for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @SuppressWarnings("serial")
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        MockAuthentication.setUserId(5L);
        instance = new BaseUserCommunityManagementAction() {
        };
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authKey");

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authKey", new MockAuthentication());
        instance.setSession(session);
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for BaseUserCommunityManagementAction(). Instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);
        assertTrue("BaseUserCommunityManagementAction should extend from ActionSupport.",
            instance instanceof ActionSupport);
        assertTrue("BaseUserCommunityManagementAction should implement from SessionAware.",
            instance instanceof SessionAware);
    }

    /**
     * <p>
     * Accuracy test for getUserHandle(). The user handle should be returned.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetUserHandle() throws TCWebException {
        assertEquals("The getUserHandle is incorrect.", "TCSDEVELOPER", instance.getUserHandle());
    }

    /**
     * <p>
     * Accuracy test for getUserId(). The user id should be returned.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetUserId() throws TCWebException {
        assertEquals("The getUserId is incorrect.", 5L, instance.getUserId());
    }

    /**
     * <p>
     * Accuracy test for setSession and getSession.
     * </p>
     */
    public void testSession() {
        Map<String, Object> session = new HashMap<String, Object>();
        instance.setSession(session);
        assertEquals("The session is inocrrect.", session, instance.getSession());
    }

    /**
     * <p>
     * Accuracy test for getAuthenticationData(), the BasicAuthentication should be returned.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetAuthenticationData() throws Exception {
        Method method = BaseUserCommunityManagementAction.class.getDeclaredMethod("getAuthenticationData");
        method.setAccessible(true);

        assertNotNull("The getAuthenticationData is incorrect.", method.invoke(instance));
        assertEquals("The getAuthenticationData is incorrect.", method.invoke(instance), method.invoke(instance));
    }

    /**
     * <p>
     * Accuracy test for setLog and getLog.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testLog() throws Exception {
        Log log = LogManager.getLog();
        instance.setLog(log);
        assertEquals("Incorrect value after set a new one", log, AccuracyTestHelper.getFieldValue(
            BaseUserCommunityManagementAction.class, instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for setAuthenticationSessionKey(String authenticationSessionKey). The value should be set.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetAuthenticationSessionKey() throws Exception {
        instance.setAuthenticationSessionKey("authKey");

        assertEquals("The setAuthenticationSessionKey is incorrect.", "authKey", AccuracyTestHelper.getFieldValue(
            BaseUserCommunityManagementAction.class, instance, "authenticationSessionKey"));
    }
}