/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.jwebunit.junit.WebTester;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.util.ValueStack;
import com.topcoder.service.actions.AggregateDataModel;
import com.topcoder.service.interceptors.AuthenticationInterceptor;
import com.topcoder.service.interceptors.LoggingInterceptor;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * <p>
 * Demo the usage of this component.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends StrutsSpringTestCase {

    /**
     * The WebTester instance used for running GUI demo.
     */
    private WebTester tester;

    /**
     * The LoggingInterceptor instance used for demo.
     */
    private LoggingInterceptor loggingInterceptorInstance;

    /**
     * The AuthenticationInterceptor instance used for demo.
     */
    private AuthenticationInterceptor authenticationInterceptorInstance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        tester = new WebTester();
        tester.setBaseUrl(TestHelper.getBaseURL());

        CustomAppender.clear();
        LogManager.setLogFactory(new Log4jLogFactory());

        authenticationInterceptorInstance = new AuthenticationInterceptor();
        authenticationInterceptorInstance.setLoginPageName("login");
        authenticationInterceptorInstance.setUserSessionIdentityKey(TestHelper.KEY_FOR_LOGIN_CHECK);

        loggingInterceptorInstance = new LoggingInterceptor();
        loggingInterceptorInstance.setLogger("strutsLoggerName");
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    protected void tearDown() throws Exception {
        tester = null;
        loggingInterceptorInstance = null;
        authenticationInterceptorInstance = null;

        CustomAppender.clear();

        // restore log factory to default
        LogManager.setLogFactory(new BasicLogFactory());
    }

    /**
     * GUI Demo of how the authentication interceptor works to make sure user is logged in before allowing
     * them to access a page. Once user is logged in they can go to other pages.
     */
    @Test
    public void testGuiDemo() {
        tester.beginAt("/login");

        // try going to the employees page, but since
        // we haven't logged in we will be forced to the login
        // page
        tester.gotoPage("/employees");

        // make sure we ended up at the login page
        tester.assertTextPresent("Login Name");

        // perform the login
        tester.setTextField("loginName", "topcoder");
        tester.setTextField("password", "password");
        tester.submit();

        // now we should be at the employees page
        tester.assertTextInTable("empTable", "John");
        tester.assertTextInTable("empTable", "Jane");
        tester.assertTextInTable("empTable", "Doe");

        // now that we've been authenticated, we should
        // be able to go directly to the employees
        // page without having to login again
        tester.gotoPage("/employees");
        tester.assertTextInTable("empTable", "John");
        tester.assertTextInTable("empTable", "Jane");
        tester.assertTextInTable("empTable", "Doe");
    }

    /**
     * Demo of how the logging interceptor logs exception and stack trace.
     *
     * @throws Exception to junit
     */
    @Test
    public void testLoggingInterceptorDemo() throws Exception {
        // create the struts2 action proxy
        ActionProxy proxy = getActionProxy("/login");
        TestHelper.prepareActionProxy(proxy);

        // set the stack values for the proxy
        ValueStack stack = proxy.getInvocation().getStack();
        AggregateDataModel model = new AggregateDataModel();
        model.setData("result", new Exception("this is a test exception"));
        stack.push(model);

        // call the intercept method and get the result (will be
        // the login page)
        String result = loggingInterceptorInstance.intercept(proxy.getInvocation());
        System.out.println("result of loggingInterceptor is " + result);

        // check the logged messages and find the
        // exception that was logged by the interceptor
        for (String msg : CustomAppender.getMessages()) {
            if (msg.equals("this is a test exception")) {
                System.out.println("found the logged exception message.");
            }
        }
    }

    /**
     * Demo of how the authentication interceptor redirects to the login page if login
     * credential data has not been provided, and it returns success if valid credential
     * data has been provided.
     *
     * @throws Exception to junit
     */
    @Test
    public void testAuthenticationInterceptorDemo() throws Exception {
        // create the struts2 action proxy
        ActionProxy proxy = getActionProxy("/login");
        TestHelper.prepareActionProxy(proxy);

        // call the intercept method, result will be the login page
        String result = authenticationInterceptorInstance.intercept(
            proxy.getInvocation());
        System.out.println(
            "result of authenticationInterceptor when credential "
            + "data is missing is " + result);

        // create a new ActionProxy
        proxy = getActionProxy("/login");
        TestHelper.prepareActionProxy(proxy);

        // set the login credential
        proxy.getInvocation().getInvocationContext().getSession().put(
            TestHelper.KEY_FOR_LOGIN_CHECK, "topcoder");

        // inject the login and password parameters
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("loginName", "topcoder");
        parameters.put("password", "password");
        proxy.getInvocation().getInvocationContext().setParameters(parameters);

        // call the intercept method, result will be success since
        // credential information was provided
        result = authenticationInterceptorInstance.intercept(proxy.getInvocation());
        System.out.println(
            "result of authenticationInterceptor when credential data "
            + "is provided is " + result);
    }

}
