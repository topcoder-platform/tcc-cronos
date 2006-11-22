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
 * UnitTest for SlotValidationHandler class.
 *
 * @author mittu
 * @version 1.0
 */
public class SlotValidationHandlerUnitTest extends TestCase {
    /**
     * Represents the handler to test.
     */
    private SlotValidationHandler handler;

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
        return new TestSuite(SlotValidationHandlerUnitTest.class);
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
        handler = new SlotValidationHandler("gameId", "slotId", "validation_failed");
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
     * <code>SlotValidationHandler(String gameIdParamKey, String slotIdParamKey,
     * String validationFailedResultCode)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerAccuracy1() throws Exception {
        SlotValidationHandler validationHandler = new SlotValidationHandler("gameId", "slotId", "validation_failed");
        assertNotNull("failed to create SlotValidationHandler", validationHandler);
        assertTrue("failed to create SlotValidationHandler", validationHandler instanceof Handler);
    }

    /**
     * Failure test of
     * <code>SlotValidationHandler(String gameIdParamKey, String slotIdParamKey,
     * String validationFailedResultCode)</code> method.
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
    public void testSlotValidationHandlerFailure1() throws Exception {
        try {
            new SlotValidationHandler(null, "slotId", "validation_failed");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>SlotValidationHandler(String gameIdParamKey, String slotIdParamKey,
     * String validationFailedResultCode)</code> method.
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
    public void testSlotValidationHandlerFailure2() throws Exception {
        try {
            new SlotValidationHandler(" ", "slotId", "validation_failed");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>SlotValidationHandler(String gameIdParamKey, String slotIdParamKey,
     * String validationFailedResultCode)</code> method.
     *
     * <p>
     * slotIdParamKey is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure3() throws Exception {
        try {
            new SlotValidationHandler("gameId", null, "validation_failed");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>SlotValidationHandler(String gameIdParamKey, String slotIdParamKey,
     * String validationFailedResultCode)</code> method.
     *
     * <p>
     * slotIdParamKey is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure4() throws Exception {
        try {
            new SlotValidationHandler("gameId", " ", "validation_failed");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>SlotValidationHandler(String gameIdParamKey, String slotIdParamKey,
     * String validationFailedResultCode)</code> method.
     *
     * <p>
     * validationFailedResultCode is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure5() throws Exception {
        try {
            new SlotValidationHandler("gameId", "slotId", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>SlotValidationHandler(String gameIdParamKey, String slotIdParamKey,
     * String validationFailedResultCode)</code> method.
     *
     * <p>
     * validationFailedResultCode is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure6() throws Exception {
        try {
            new SlotValidationHandler("gameId", "slotId", " ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>SlotValidationHandler(Element element)</code> method.
     *
     * <p>
     * Expects non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerAccuracy() throws Exception {
        SlotValidationHandler validationHandler = new SlotValidationHandler(TestHelper.getDomElement(
                "SlotValidationHandler.xml", false));
        assertNotNull("failed to create SlotValidationHandler", validationHandler);
        assertTrue("failed to create SlotValidationHandler", validationHandler instanceof Handler);
    }

    /**
     * Failure test of <code>SlotValidationHandler(Element element)</code> method.
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
    public void testSlotValidationHandlerFailure7() throws Exception {
        try {
            new SlotValidationHandler(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SlotValidationHandler(Element element)</code> method.
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
    public void testSlotValidationHandlerFailure8() throws Exception {
        try {
            new SlotValidationHandler(TestHelper.getDomElement("SlotValidationHandler1.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SlotValidationHandler(Element element)</code> method.
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
    public void testSlotValidationHandlerFailure9() throws Exception {
        try {
            new SlotValidationHandler(TestHelper.getDomElement("SlotValidationHandler2.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SlotValidationHandler(Element element)</code> method.
     *
     * <p>
     * element - slot_id_param_key is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure10() throws Exception {
        try {
            new SlotValidationHandler(TestHelper.getDomElement("SlotValidationHandler3.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SlotValidationHandler(Element element)</code> method.
     *
     * <p>
     * element - slot_id_param_key is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure11() throws Exception {
        try {
            new SlotValidationHandler(TestHelper.getDomElement("SlotValidationHandler4.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SlotValidationHandler(Element element)</code> method.
     *
     * <p>
     * element - validation_failed_result_code is missing.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure12() throws Exception {
        try {
            new SlotValidationHandler(TestHelper.getDomElement("SlotValidationHandler5.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SlotValidationHandler(Element element)</code> method.
     *
     * <p>
     * element - validation_failed_result_code is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSlotValidationHandlerFailure13() throws Exception {
        try {
            new SlotValidationHandler(TestHelper.getDomElement("SlotValidationHandler6.xml", true));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * Expects the method to execute without exceptions. Validations success.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteAccuracy1() throws Exception {
        // creates the mocks properly.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        reqControl.expectAndReturn(request.getParameter("gameId"), "3");
        reqControl.expectAndReturn(request.getParameter("slotId"), "7");
        reqControl.replay();
        ActionContext context = new ActionContext(request, response);
        // invoke the execute.
        assertNull("failed to execute handler", handler.execute(context));
    }

    /**
     * Accuracy test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * Expects the method to execute without exceptions. Validations failed.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteAccuracy2() throws Exception {
        // creates the mocks.
        HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
        HttpServletResponse response = (HttpServletResponse) resControl.getMock();
        reqControl.expectAndReturn(request.getParameter("gameId"), "101");
        reqControl.expectAndReturn(request.getParameter("slotId"), "2101");
        reqControl.replay();
        ActionContext context = new ActionContext(request, response);
        // invoke the execute.
        assertEquals("failed to execute handler", handler.execute(context), "validation_failed");
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
            reqControl.expectAndReturn(request.getParameter("slotId"), "1001");
            reqControl.replay();
            ActionContext context = new ActionContext(request, response);
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>execute(ActionContext context)</code> method.
     *
     * <p>
     * slot id is missing.
     * </p>
     *
     * <p>
     * Expect HandlerExecutionException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteFailure3() throws Exception {
        try {
            // creates the mocks.
            HttpServletRequest request = (HttpServletRequest) reqControl.getMock();
            HttpServletResponse response = (HttpServletResponse) resControl.getMock();
            reqControl.expectAndReturn(request.getParameter("gameId"), "101");
            reqControl.replay();
            ActionContext context = new ActionContext(request, response);
            handler.execute(context);
            fail("Expect HandlerExecutionException.");
        } catch (HandlerExecutionException e) {
            // expect
        }
    }
}
