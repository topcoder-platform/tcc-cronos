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


/**
 * UnitTest for UserGamesHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class UserGamesHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private UserGamesHandler handler;

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
        return new TestSuite(UserGamesHandlerUnitTest.class);
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
        handler = new UserGamesHandler("games", "not_logged_in");
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
     * Accuracy test of <code>UserGamesHandler(String gamesKey, String notLoggedInResultCode)</code> method.
     *
     * <p>
     * Expects non-null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUserGamesHandlerAccuracy1() throws Exception {
        UserGamesHandler gamesHandler = new UserGamesHandler("games", "not_logged_in");
        assertNotNull("failed to create UserGamesHandler", gamesHandler);
        assertTrue("failed to create UserGamesHandler", gamesHandler instanceof Handler);
    }

    /**
     * Failure test of <code>UserGamesHandler(String gamesKey, String notLoggedInResultCode)</code> method.
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
    public void testUserGamesHandlerFailure1() throws Exception {
        try {
            new UserGamesHandler(null, "not_logged_in");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UserGamesHandler(String gamesKey, String notLoggedInResultCode)</code> method.
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
    public void testUserGamesHandlerFailure2() throws Exception {
        try {
            new UserGamesHandler("  ", "not_logged_in");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UserGamesHandler(String gamesKey, String notLoggedInResultCode)</code> method.
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
    public void testUserGamesHandlerFailure3() throws Exception {
        try {
            new UserGamesHandler("games", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UserGamesHandler(String gamesKey, String notLoggedInResultCode)</code> method.
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
    public void testUserGamesHandlerFailure4() throws Exception {
        try {
            new UserGamesHandler("games", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>UserGamesHandler(Element element)</code> method.
     *
     * <p>
     * Expects non-null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUserGamesHandlerAccuracy2() throws Exception {
        UserGamesHandler gamesHandler = new UserGamesHandler(TestHelper.getDomElement("UserGamesHandler.xml", false));
        assertNotNull("failed to create UserGamesHandler", gamesHandler);
        assertTrue("failed to create UserGamesHandler", gamesHandler instanceof Handler);
    }

    /**
     * Failure test of <code>UserGamesHandler(Element element)</code> method.
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
    public void testUserGamesHandlerFailure5() throws Exception {
        try {
            new UserGamesHandler(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UserGamesHandler(Element element)</code> method.
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
    public void testUserGamesHandlerFailure6() throws Exception {
        try {
            new UserGamesHandler(TestHelper.getDomElement("UserGamesHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UserGamesHandler(Element element)</code> method.
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
    public void testUserGamesHandlerFailure7() throws Exception {
        try {
            new UserGamesHandler(TestHelper.getDomElement("UserGamesHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UserGamesHandler(Element element)</code> method.
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
    public void testUserGamesHandlerFailure8() throws Exception {
        try {
            new UserGamesHandler(TestHelper.getDomElement("UserGamesHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UserGamesHandler(Element element)</code> method.
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
    public void testUserGamesHandlerFailure9() throws Exception {
        try {
            new UserGamesHandler(TestHelper.getDomElement("UserGamesHandler4.xml", true));
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
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        reqControl.replay();
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
}
