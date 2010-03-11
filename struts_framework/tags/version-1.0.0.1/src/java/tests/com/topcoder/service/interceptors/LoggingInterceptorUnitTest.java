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
import com.opensymphony.xwork2.util.ValueStack;
import com.topcoder.service.CustomAppender;
import com.topcoder.service.TestHelper;
import com.topcoder.service.actions.AggregateDataModel;
import com.topcoder.service.actions.LoginAction;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * <p>
 * Unit tests for <code>LoggingInterceptor</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class LoggingInterceptorUnitTest extends StrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private LoggingInterceptor instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        CustomAppender.clear();
        LogManager.setLogFactory(new Log4jLogFactory());
        instance = new LoggingInterceptor();
        instance.setLogger("strutsLoggerName");
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
        CustomAppender.clear();

        // restore log factory to default
        LogManager.setLogFactory(new BasicLogFactory());
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
     * Accuracy test for constructor which takes logger name as argument. Verifies the new instance is
     * created.
     */
    @Test
    public void testConstructor2() {
        instance = new LoggingInterceptor("My Logger");
        assertNotNull("unable to create instance", instance);

        // make sure logger is set
        assertNotNull("logger should be set", instance.getLogger());
    }

    /**
     * Failure test for constructor which takes logger name as argument. The logger name is null and
     * IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void testConstructor2_Failure1() throws Exception {
        try {
            new LoggingInterceptor(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for constructor which takes logger name as argument. The logger name is empty and
     * IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void testConstructor2_Failure2() throws Exception {
        try {
            new LoggingInterceptor("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for intercept method. The exception should be logged and the correct action result should
     * be returned. In this test, the login credential information is not set, so login page should be
     * returned.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Accuracy1() throws Exception {
        // create the struts2 action proxy
        ActionProxy proxy = getActionProxy("/login");
        TestHelper.prepareActionProxy(proxy);

        // set the stack values for the proxy
        ValueStack stack = proxy.getInvocation().getStack();
        AggregateDataModel model = new AggregateDataModel();
        model.setData("result", new Exception("this is a test exception"));
        stack.push(model);

        // call the intercept method and validate the result
        String result = instance.intercept(proxy.getInvocation());
        assertEquals("result is wrong", "login", result);

        // make sure exception was logged
        assertTrue("exception should have been logged", CustomAppender.containsMessage("this is a test exception"));

        // make sure stack trace was logged
        assertTrue("stack trace should have been logged", CustomAppender
            .containsMessage("LoggingInterceptorUnitTest.test_intercept_Accuracy1"));
    }

    /**
     * Accuracy test for intercept method. No exception should be logged and the correct action result should
     * be returned. In this test, the login credential information is set, so success should be returned.
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
     * Accuracy test for intercept method. The object should not be logged since it is not of type Throwable,
     * and the correct action result should be returned. In this test, the login credential information is not
     * set, so login page should be returned.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Accuracy3() throws Exception {
        // create the struts2 action proxy
        ActionProxy proxy = getActionProxy("/login");
        TestHelper.prepareActionProxy(proxy);

        // set the stack values for the proxy
        ValueStack stack = proxy.getInvocation().getStack();
        AggregateDataModel model = new AggregateDataModel();
        model.setData("result", "should not be logged");
        stack.push(model);

        // call the intercept method and validate the result
        String result = instance.intercept(proxy.getInvocation());
        assertEquals("result is wrong", "login", result);

        // make sure object was not logged
        assertTrue("object should not have been logged", !CustomAppender.containsMessage("should not be logged"));
    }

    /**
     * Failure test for intercept method. The action fails to execute, so Exception should be thrown.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Failure1() throws Exception {
        try {
            // create the struts2 action proxy
            ActionProxy proxy = getActionProxy("/login");
            TestHelper.prepareActionProxy(proxy);

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
     * Failure test for intercept method. The logger is not set, so IllegalStateException should be thrown.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_intercept_Failure2() throws Exception {
        try {
            // create the struts2 action proxy
            ActionProxy proxy = getActionProxy("/login");
            TestHelper.prepareActionProxy(proxy);

            instance = new LoggingInterceptor();
            instance.intercept(proxy.getInvocation());

            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Accuracy test for getLogger. Verifies the returned value is correct.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getLogger_Accuracy() throws Exception {
        instance = new LoggingInterceptor();
        assertNull("incorrect initial value", instance.getLogger());
        instance.setLogger("my new log");
        assertNotNull("incorrect value after setting", instance.getLogger());
    }

    /**
     * Accuracy test for setLogger. Verifies the assigned value is correct.
     */
    @Test
    public void test_setLogger_Accuracy() {
        instance = new LoggingInterceptor();
        instance.setLogger("my new log");
        assertNotNull("incorrect value after setting", instance.getLogger());
    }

    /**
     * Failure test for setLogger method. The logger name is null and IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setLogger_Failure1() throws Exception {
        try {
            instance.setLogger(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for setLogger method. The logger name is empty and IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setLogger_Failure2() throws Exception {
        try {
            instance.setLogger("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
