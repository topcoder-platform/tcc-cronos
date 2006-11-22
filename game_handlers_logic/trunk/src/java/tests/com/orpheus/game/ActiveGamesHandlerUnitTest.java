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
import org.w3c.dom.Element;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;


/**
 * UnitTest for ActiveGamesHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class ActiveGamesHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private ActiveGamesHandler handler;

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
        return new TestSuite(ActiveGamesHandlerUnitTest.class);
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
        handler = new ActiveGamesHandler("games");
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
     * Accuracy test of <code>ActiveGamesHandler(String gamesKey)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testActiveGamesHandlerAccuracy1() throws Exception {
        ActiveGamesHandler activeGamesHandler = new ActiveGamesHandler("games");
        assertNotNull("failed to create ActiveGamesHandler", activeGamesHandler);
        assertTrue("failed to create ActiveGamesHandler", activeGamesHandler instanceof Handler);
    }

    /**
     * Failure test of <code>ActiveGamesHandler(String gamesKey)</code> method.
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
    public void testActiveGamesHandlerFailure1() throws Exception {
        try {
            new ActiveGamesHandler((String) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ActiveGamesHandler(String gamesKey)</code> method.
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
    public void testActiveGamesHandlerFailure2() throws Exception {
        try {
            new ActiveGamesHandler("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>ActiveGamesHandler(Element element)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testActiveGamesHandlerAccuracy2() throws Exception {
        ActiveGamesHandler activeGamesHandler = new ActiveGamesHandler(TestHelper.getDomElement(
                "ActiveGamesHandler.xml", false));
        assertNotNull("failed to create ActiveGamesHandler", activeGamesHandler);
        assertTrue("failed to create ActiveGamesHandler", activeGamesHandler instanceof Handler);
    }

    /**
     * Failure test of <code>ActiveGamesHandler(Element element)</code> method.
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
    public void testActiveGamesHandlerFailure3() throws Exception {
        try {
            new ActiveGamesHandler((Element) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ActiveGamesHandler(Element element)</code> method.
     *
     * <p>
     * games_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testActiveGamesHandlerFailure4() throws Exception {
        try {
            new ActiveGamesHandler(TestHelper.getDomElement("ActiveGamesHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ActiveGamesHandler(Element element)</code> method.
     *
     * <p>
     * games_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testActiveGamesHandlerFailure5() throws Exception {
        try {
            new ActiveGamesHandler(TestHelper.getDomElement("ActiveGamesHandler2.xml", true));
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
}
