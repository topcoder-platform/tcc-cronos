/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.easymock.MockControl;

import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;


/**
 * UnitTest for TestDomainHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class TestDomainHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private TestDomainHandler handler;

    /**
     * Represents the MockControl.
     */
    private MockControl reqControl, resControl;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TestDomainHandlerUnitTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig(TestHelper.CONFIG);
        TestHelper.deployEJB();
        reqControl = MockControl.createNiceControl(HttpServletRequest.class);
        resControl = MockControl.createControl(HttpServletResponse.class);
        handler = new TestDomainHandler("domainName", "game", "not_logged_in");
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        handler = null;
        TestHelper.releaseConfigs();
    }

    /**
     * Accuracy test of
     * <code>TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode)</code>
     * method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerAccuracy1() throws Exception {
        TestDomainHandler domainHandler = new TestDomainHandler("domainName", "game", "not_logged_in");
        assertNotNull("failed to create TestDomainHandler", domainHandler);
        assertTrue("failed to create TestDomainHandler", domainHandler instanceof Handler);
    }

    /**
     * Failure test of
     * <code>TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode)</code>
     * method.
     *
     * <p>
     * domainNameParamKey is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure1() throws Exception {
        try {
            new TestDomainHandler(null, "game", "not_logged_in");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode)</code>
     * method.
     *
     * <p>
     * domainNameParamKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure2() throws Exception {
        try {
            new TestDomainHandler("  ", "game", "not_logged_in");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode)</code>
     * method.
     *
     * <p>
     * gamesKey is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure3() throws Exception {
        try {
            new TestDomainHandler("domainName", null, "not_logged_in");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode)</code>
     * method.
     *
     * <p>
     * gamesKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure4() throws Exception {
        try {
            new TestDomainHandler("domainName", " ", "not_logged_in");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode)</code>
     * method.
     *
     * <p>
     * notLoggedInResultCode is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure5() throws Exception {
        try {
            new TestDomainHandler("domainName", "game", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode)</code>
     * method.
     *
     * <p>
     * notLoggedInResultCode is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure6() throws Exception {
        try {
            new TestDomainHandler("domainName", "game", " ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerAccuracy2() throws Exception {
        TestDomainHandler domainHandler = new TestDomainHandler(TestHelper
                .getDomElement("TestDomainHandler.xml", false));
        assertNotNull("failed to create TestDomainHandler", domainHandler);
        assertTrue("failed to create TestDomainHandler", domainHandler instanceof Handler);
    }

    /**
     * Failure test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * element is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure7() throws Exception {
        try {
            new TestDomainHandler(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * element - domain_name_param_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure8() throws Exception {
        try {
            new TestDomainHandler(TestHelper.getDomElement("TestDomainHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * element - domain_name_param_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure9() throws Exception {
        try {
            new TestDomainHandler(TestHelper.getDomElement("TestDomainHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * element - games_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure10() throws Exception {
        try {
            new TestDomainHandler(TestHelper.getDomElement("TestDomainHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * element - games_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure11() throws Exception {
        try {
            new TestDomainHandler(TestHelper.getDomElement("TestDomainHandler4.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * element - not_logged_in_result_code is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure12() throws Exception {
        try {
            new TestDomainHandler(TestHelper.getDomElement("TestDomainHandler5.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TestDomainHandler(Element element)</code> method.
     *
     * <p>
     * element - not_logged_in_result_code is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTestDomainHandlerFailure13() throws Exception {
        try {
            new TestDomainHandler(TestHelper.getDomElement("TestDomainHandler6.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * Expects the method to execute without exceptions.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        reqControl.replay();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter("domainName", "junit");
        ActionContext context = new ActionContext(wrapper, response);
        // invoke the execute.
        assertNull("failed to execute handler", handler.execute(context));
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * context is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure1() throws Exception {
        try {
            handler.execute(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * domain name invalid.
     * </p>
     *
     * <p>
     * Expect HandlerExecutionException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure2() throws Exception {
        try {
            // creates the mocks.
            HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
            HttpServletResponse response = (HttpServletResponse) resControl.getMock();
            reqControl.replay();
            MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
            wrapper.setNullSesssion(false);
            ActionContext context = new ActionContext(wrapper, response);
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }
}
