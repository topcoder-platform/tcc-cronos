/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * UnitTest for GameDetailHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class GameDetailHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private GameDetailHandler handler;

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
        return new TestSuite(GameDetailHandlerUnitTest.class);
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
        handler = new GameDetailHandler("gameId", "game");
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
     * Accuracy test of <code>GameDetailHandler(String gameIdParamKey, String gameDetailKey)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerAccuracy1() throws Exception {
        GameDetailHandler detailHandler = new GameDetailHandler("gameId", "game");
        assertNotNull("failed to create GameDetailHandler", detailHandler);
        assertTrue("failed to create GameDetailHandler", detailHandler instanceof Handler);
    }

    /**
     * Failure test of <code>GameDetailHandler(String gameIdParamKey, String gameDetailKey)</code> method.
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
    public void testGameDetailHandlerFailure1() throws Exception {
        try {
            new GameDetailHandler(null, "game");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>GameDetailHandler(String gameIdParamKey, String gameDetailKey)</code> method.
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
    public void testGameDetailHandlerFailure2() throws Exception {
        try {
            new GameDetailHandler(" ", "game");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>GameDetailHandler(String gameIdParamKey, String gameDetailKey)</code> method.
     *
     * <p>
     * gameDetailKey is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerFailure3() throws Exception {
        try {
            new GameDetailHandler("gameId", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>GameDetailHandler(String gameIdParamKey, String gameDetailKey)</code> method.
     *
     * <p>
     * gameDetailKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerFailure4() throws Exception {
        try {
            new GameDetailHandler("gameId", " ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>GameDetailHandler(Element element)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerAccuracy2() throws Exception {
        GameDetailHandler detailHandler = new GameDetailHandler(TestHelper
                .getDomElement("GameDetailHandler.xml", false));
        assertNotNull("failed to create GameDetailHandler", detailHandler);
        assertTrue("failed to create GameDetailHandler", detailHandler instanceof Handler);
    }

    /**
     * Failure test of <code>GameDetailHandler(Element element)</code> method.
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
    public void testGameDetailHandlerFailure5() throws Exception {
        try {
            new GameDetailHandler(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>GameDetailHandler(Element element)</code> method.
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
    public void testGameDetailHandlerFailure6() throws Exception {
        try {
            new GameDetailHandler(TestHelper.getDomElement("GameDetailHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>GameDetailHandler(Element element)</code> method.
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
    public void testGameDetailHandlerFailure7() throws Exception {
        try {
            new GameDetailHandler(TestHelper.getDomElement("GameDetailHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>GameDetailHandler(Element element)</code> method.
     *
     * <p>
     * element - game_detail_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerFailure8() throws Exception {
        try {
            new GameDetailHandler(TestHelper.getDomElement("GameDetailHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>GameDetailHandler(Element element)</code> method.
     *
     * <p>
     * element - game_detail_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDetailHandlerFailure9() throws Exception {
        try {
            new GameDetailHandler(TestHelper.getDomElement("GameDetailHandler4.xml", true));
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
     * No game id present in the request.
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
