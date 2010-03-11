/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.interceptors;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.service.TestHelper;
import com.topcoder.service.actions.LoginAction;

/**
 * <p>
 * Unit tests for <code>AuthenticationInterceptor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AuthenticationInterceptorUnitTest extends StrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private AuthenticationInterceptor instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new AuthenticationInterceptor();
        instance.setLoginPageName("login");
        instance.setUserSessionIdentityKey(TestHelper.KEY_FOR_LOGIN_CHECK);
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to junit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     */
    @Test
    public void testInheritance() {
        TestHelper.assertSuperclass(instance.getClass(), AbstractInterceptor.class);
    }

    /**
     * Accuracy test for no argument constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor1() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for intercept method. The correct action result should be returned. In this test, the
     * login credential information is not set, so login page should be returned.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Accuracy1() throws Exception {
        // create the struts2 action proxy
        ActionProxy proxy = getActionProxy("/login");
        TestHelper.prepareActionProxy(proxy);

        // call the intercept method and validate the result
        String result = instance.intercept(proxy.getInvocation());
        assertEquals("result is wrong", "login", result);
    }

    /**
     * Accuracy test for intercept method. The correct action result should be returned. In this test, the
     * login credential information is set, so success should be returned.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Accuracy2() throws Exception {
        // create the struts2 action proxy
        ActionProxy proxy = getActionProxy("/login");
        TestHelper.prepareActionProxy(proxy);

        // set the login credential
        proxy.getInvocation().getInvocationContext().getSession().put(TestHelper.KEY_FOR_LOGIN_CHECK, "tcuser1");

        // inject the login and password parameters
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("loginName", "topcoder");
        parameters.put("password", "password");
        proxy.getInvocation().getInvocationContext().setParameters(parameters);

        // call the intercept method and validate the result
        String result = instance.intercept(proxy.getInvocation());
        assertEquals("result is wrong", "success", result);
    }

    /**
     * Failure test for intercept method. The action fails to execute, so ArithmeticException should be thrown.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Failure1() throws Exception {
        try {
            // create the struts2 action proxy
            ActionProxy proxy = getActionProxy("/login");
            TestHelper.prepareActionProxy(proxy);

            // set the login credential
            proxy.getInvocation().getInvocationContext().getSession().put(TestHelper.KEY_FOR_LOGIN_CHECK, "tcuser1");

            // inject the login and password parameters
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("loginName", "topcoder");
            parameters.put("password", "password");
            proxy.getInvocation().getInvocationContext().setParameters(parameters);

            // force the action to fail
            LoginAction.setThrowException(true);

            instance.intercept(proxy.getInvocation());

            fail("ArithmeticException is expected");
        } catch (ArithmeticException e) {
            // success
        } finally {
            LoginAction.setThrowException(false);
        }
    }

    /**
     * Failure test for intercept method. The login page name is null, so IllegalStateException should be thrown.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Failure2() throws Exception {
        try {
            // create the struts2 action proxy
            ActionProxy proxy = getActionProxy("/login");
            TestHelper.prepareActionProxy(proxy);

            instance = new AuthenticationInterceptor();
            instance.setUserSessionIdentityKey("test");
            instance.intercept(proxy.getInvocation());

            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for intercept method. The user session identity key is null, so IllegalStateException should
     * be thrown.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Failure4() throws Exception {
        try {
            // create the struts2 action proxy
            ActionProxy proxy = getActionProxy("/login");
            TestHelper.prepareActionProxy(proxy);

            instance = new AuthenticationInterceptor();
            instance.setLoginPageName("test");
            instance.intercept(proxy.getInvocation());

            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Accuracy test for getLoginPageName. Verifies the returned value is correct.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getLoginPageName_Accuracy() throws Exception {
        instance = new AuthenticationInterceptor();
        assertNull("incorrect initial value", instance.getLoginPageName());
        instance.setLoginPageName("value");
        assertNotNull("incorrect value after setting", instance.getLoginPageName());
    }

    /**
     * Accuracy test for setLoginPageName. Verifies the assigned value is correct.
     */
    @Test
    public void test_setLoginPageName_Accuracy() {
        instance = new AuthenticationInterceptor();
        instance.setLoginPageName("value");
        assertNotNull("incorrect value after setting", instance.getLoginPageName());
    }

    /**
     * Failure test for setLoginPageName method. The login page name is null and IllegalArgumentException
     * is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setLoginPageName_Failure1() throws Exception {
        try {
            instance.setLoginPageName(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for setLoginPageName method. The login page name is empty and IllegalArgumentException
     * is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setLoginPageName_Failure2() throws Exception {
        try {
            instance.setLoginPageName("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for getUserSessionIdentityKey. Verifies the returned value is correct.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getUserSessionIdentityKey_Accuracy() throws Exception {
        instance = new AuthenticationInterceptor();
        assertNull("incorrect initial value", instance.getUserSessionIdentityKey());
        instance.setUserSessionIdentityKey("value");
        assertNotNull("incorrect value after setting", instance.getUserSessionIdentityKey());
    }

    /**
     * Accuracy test for setUserSessionIdentityKey. Verifies the assigned value is correct.
     */
    @Test
    public void test_setUserSessionIdentityKey_Accuracy() {
        instance = new AuthenticationInterceptor();
        instance.setUserSessionIdentityKey("value");
        assertNotNull("incorrect value after setting", instance.getUserSessionIdentityKey());
    }

    /**
     * Failure test for setUserSessionIdentityKey method. The user session identity key is null and
     * IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setUserSessionIdentityKey_Failure1() throws Exception {
        try {
            instance.setUserSessionIdentityKey(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for setUserSessionIdentityKey method. The user session identity key is empty and
     * IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setUserSessionIdentityKey_Failure2() throws Exception {
        try {
            instance.setUserSessionIdentityKey("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
