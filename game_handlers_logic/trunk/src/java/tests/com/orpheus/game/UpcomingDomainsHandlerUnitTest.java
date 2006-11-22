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

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;


/**
 * UnitTest for UpcomingDomainsHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class UpcomingDomainsHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private UpcomingDomainsHandler handler;

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
        return new TestSuite(UpcomingDomainsHandlerUnitTest.class);
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
        handler = new UpcomingDomainsHandler("gameId", "domain");
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
     * Accuracy test of <code>UpcomingDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
     *
     * <p>
     * Expects non-null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerAccuracy1() throws Exception {
        UpcomingDomainsHandler domainsHandler = new UpcomingDomainsHandler("gameId", "domain");
        assertNotNull("failed to create UpcomingDomainsHandler", domainsHandler);
        assertTrue("failed to create UpcomingDomainsHandler", domainsHandler instanceof Handler);
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
     *
     * <p>
     * gameIdParamKey is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure1() throws Exception {
        try {
            new UpcomingDomainsHandler(null, "domain");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
     *
     * <p>
     * gameIdParamKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure2() throws Exception {
        try {
            new UpcomingDomainsHandler("  ", "domain");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
     *
     * <p>
     * domainsKey is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure3() throws Exception {
        try {
            new UpcomingDomainsHandler("gameId", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
     *
     * <p>
     * domainsKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure4() throws Exception {
        try {
            new UpcomingDomainsHandler("gameId", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>UpcomingDomainsHandler(Element element)</code> method.
     *
     * <p>
     * Expects non-null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerAccuracy2() throws Exception {
        UpcomingDomainsHandler domainsHandler = new UpcomingDomainsHandler(TestHelper.getDomElement(
                "UpcomingDomainsHandler.xml", false));
        assertNotNull("failed to create UpcomingDomainsHandler", domainsHandler);
        assertTrue("failed to create UpcomingDomainsHandler", domainsHandler instanceof Handler);
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(Element element)</code> method.
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
    public void testUpcomingDomainsHandlerFailure5() throws Exception {
        try {
            new UpcomingDomainsHandler(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(Element element)</code> method.
     *
     * <p>
     * element - game_id_param_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure6() throws Exception {
        try {
            new UpcomingDomainsHandler(TestHelper.getDomElement("UpcomingDomainsHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(Element element)</code> method.
     *
     * <p>
     * element - game_id_param_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure7() throws Exception {
        try {
            new UpcomingDomainsHandler(TestHelper.getDomElement("UpcomingDomainsHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(Element element)</code> method.
     *
     * <p>
     * element - domains_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure8() throws Exception {
        try {
            new UpcomingDomainsHandler(TestHelper.getDomElement("UpcomingDomainsHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UpcomingDomainsHandler(Element element)</code> method.
     *
     * <p>
     * element - domains_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpcomingDomainsHandlerFailure9() throws Exception {
        try {
            new UpcomingDomainsHandler(TestHelper.getDomElement("UpcomingDomainsHandler4.xml", true));
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
        reqControl.expectAndReturn(request.getParameter("gameId"), "101");
        reqControl.replay();
        ActionContext context = new ActionContext(request, response);
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
     * game id is missing.
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
            ActionContext context = new ActionContext(request, response);
            // invoke the execute.
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }
}
