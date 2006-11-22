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
 * UnitTest for UnlockedDomainsHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class UnlockedDomainsHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private UnlockedDomainsHandler handler;

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
        return new TestSuite(UnlockedDomainsHandlerUnitTest.class);
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
        handler = new UnlockedDomainsHandler("gameId", "domain");
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
     * Accuracy test of <code>UnlockedDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUnlockedDomainsHandlerAccuracy1() throws Exception {
        UnlockedDomainsHandler domainsHandler = new UnlockedDomainsHandler("gameId", "domain");
        assertNotNull("failed to create UnlockedDomainsHandler", domainsHandler);
        assertTrue("failed to create UnlockedDomainsHandler", domainsHandler instanceof Handler);
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
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
    public void testUnlockedDomainsHandlerFailure1() throws Exception {
        try {
            new UnlockedDomainsHandler(null, "domain");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
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
    public void testUnlockedDomainsHandlerFailure2() throws Exception {
        try {
            new UnlockedDomainsHandler("  ", "domain");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
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
    public void testUnlockedDomainsHandlerFailure3() throws Exception {
        try {
            new UnlockedDomainsHandler("gameId", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(String gameIdParamKey, String domainsKey)</code> method.
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
    public void testUnlockedDomainsHandlerFailure4() throws Exception {
        try {
            new UnlockedDomainsHandler("gameId", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>UnlockedDomainsHandler(Element element)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUnlockedDomainsHandlerAccuracy2() throws Exception {
        UnlockedDomainsHandler domainsHandler = new UnlockedDomainsHandler(TestHelper.getDomElement(
                "UnlockedDomainsHandler.xml", false));
        assertNotNull("failed to create UnlockedDomainsHandler", domainsHandler);
        assertTrue("failed to create UnlockedDomainsHandler", domainsHandler instanceof Handler);
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(Element element)</code> method.
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
    public void testUnlockedDomainsHandlerFailure5() throws Exception {
        try {
            new UnlockedDomainsHandler(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(Element element)</code> method.
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
    public void testUnlockedDomainsHandlerFailure6() throws Exception {
        try {
            new UnlockedDomainsHandler(TestHelper.getDomElement("UnlockedDomainsHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(Element element)</code> method.
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
    public void testUnlockedDomainsHandlerFailure7() throws Exception {
        try {
            new UnlockedDomainsHandler(TestHelper.getDomElement("UnlockedDomainsHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(Element element)</code> method.
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
    public void testUnlockedDomainsHandlerFailure8() throws Exception {
        try {
            new UnlockedDomainsHandler(TestHelper.getDomElement("UnlockedDomainsHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnlockedDomainsHandler(Element element)</code> method.
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
    public void testUnlockedDomainsHandlerFailure9() throws Exception {
        try {
            new UnlockedDomainsHandler(TestHelper.getDomElement("UnlockedDomainsHandler4.xml", true));
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
            ActionContext context = new ActionContext(request, response);
            // invoke the execute.
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }
}
