/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.TCWebException;

/**
 * <p> Unit test case of {@link BaseUserCommunityManagementAction}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class BaseUserCommunityManagementActionTest extends TestCase {
    /**
     * <p>
     * The BaseUserCommunityManagementAction instance to test.
     * </p>
     * */
    private BaseUserCommunityManagementAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new BaseUserCommunityManagementAction() {
            /**
            * <p>
            * The serial version uid.
            * </p>
            */
            private static final long serialVersionUID = 1L;

        };
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationkey", new MockBasicAuthentication());
        instance.setSession(session);
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p> Creates a test suite for the tests in this test case. </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BaseUserCommunityManagementActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor BaseUserCommunityManagementAction.
     * It verifies instance is correctly created.
     * </p>
     * @throws Exception to Junit
     */
    public void test_BaseUserCommunityManagementAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method checkInit.
     *
     * No error occurs if the action is well configured.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkInit() throws Exception {
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for method checkInit.
     *
     * Expects UserCommunityManagementInitializationException if the authenticationSessionKey is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkInit2() throws Exception {
        instance.setAuthenticationSessionKey(" ");
        try {
            instance.checkInit();
            fail("Expects an Exception");
        } catch (UserCommunityManagementInitializationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkInit.
     *
     * Expects UserCommunityManagementInitializationException if the authenticationSessionKey is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkInit3() throws Exception {
        instance.setAuthenticationSessionKey(" ");
        try {
            instance.checkInit();
            fail("Expects an Exception");
        } catch (UserCommunityManagementInitializationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#setSession()}. It verifies the field is correct set.
     * </p>
     */
    public void test_setSession() {
        Map<String, Object> value = new HashMap<String, Object>();
        instance.setSession(value);
        assertEquals("Incorrect value after set a new one", value, instance.getSession());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#getSession()}. It verifies the returned
     *  value is correct.
     * </p>
     */
    public void test_getSession() {
        Map<String, Object> value = new HashMap<String, Object>();
        instance.setSession(value);
        assertEquals("Incorrect value after set a new one", value, instance.getSession());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#setLog()}. It verifies the field is correct set.
     * </p>
     */
    public void test_setLog() {
        Log value = LogManager.getLog();
        instance.setLog(value);
        assertEquals("Incorrect value after set a new one", value, instance.getLog());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#getLog()}. It verifies the returned value is correct.
     * </p>
     */
    public void test_getLog() {
        Log value = LogManager.getLog();
        instance.setLog(value);
        assertEquals("Incorrect value after set a new one", value, instance.getLog());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#setAuthenticationSessionKey()}. It verifies the
     * field is correct set.
     * </p>
     * @throws Exception to junit
     */
    public void test_setAuthenticationSessionKey() throws Exception {
        instance.setAuthenticationSessionKey("test");
        assertEquals("Incorrect value after set a new one", "test", TestHelper.outject(
                BaseUserCommunityManagementAction.class, instance, "authenticationSessionKey"));
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#getAuthenticationData()}.
     *  It verifies the returned value is correct.
     * </p>
     * @throws TCWebException to junit
     */
    public void test_getAuthenticationData() throws TCWebException {
        assertEquals("Incorrect result", MockBasicAuthentication.class, instance.getAuthenticationData()
                .getClass());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#getUserHandle()}.
     *  It verifies the returned value is correct.
     * </p>
     * @throws TCWebException to junit
     */
    public void test_getUserHandle() throws TCWebException {
        assertEquals("Incorrect result", "topcoderuser", instance.getUserHandle());
    }

    /**
     * <p>
     * Test method for {@link BaseUserCommunityManagementAction#getUserId()}.
     *  It verifies the returned value is correct.
     * </p>
     * @throws TCWebException to junit
     */
    public void test_getUserId() throws TCWebException {
        assertEquals("Incorrect result", 100L, instance.getUserId());
    }

    /**
     * <p>
     * Failure test for method getAuthenticationData.
     *
     * Expects TCWebException if the authentication data is not found.
     * </p>
     * @throws Exception to Junit
     */
    public void test_getAuthenticationData3() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        instance.setSession(session);
        try {
            instance.getAuthenticationData();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method getUserId.
     *
     * Expects TCWebException if the user id is not found.
     * </p>
     * @throws Exception to Junit
     */
    public void test_getUserId3() throws Exception {
        Map<String, Object> session = new HashMap<String, Object>();
        instance.setSession(session);
        try {
            instance.getUserId();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }
}
