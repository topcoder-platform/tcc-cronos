/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * abstract class for common stress test.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public abstract class AbstractHandlerStressTest extends TestCase {
    /**
     * The HttpServletRequest instance used in tests.
     */
    protected HttpServletRequest request;

    /**
     * The HttpServletResponse instance used in tests.
     */
    protected HttpServletResponse response;

    /**
     * The ActionContext used in tests.
     */
    protected ActionContext context;

    /**
     * <p>
     * Represents the result that execute returned.
     * </p>
     */
    protected String result;

    /**
     * <p>
     * setUp() routine. Creates test AdminSummaryHandler instance and other instances used in tests.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.prepareTest();
        // initialize instances used in tests
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        context = new ActionContext(request, response);
    }

    /**
     * <p>
     * Clean up all namespace of ConfigManager.
     * </p>
     * @throws Exception
     *             the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearTestEnvironment();
    }

    /**
     * <p>
     * Get the handler instance to be tested.
     * </p>
     * @return the handler to be test.
     * @throws Exception
     *             If fail to get.
     */
    protected abstract Handler getHandler() throws Exception;

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected abstract String getHandlerName();

    /**
     * <p>
     * Stress test for <code>{@link Handler#execute(ActionContext)}</code> method.
     * </p>
     */
    public void testExecuteStressSMALL() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < StressTestHelper.SMALL; i++) {
            result = getHandler().execute(context);
        }

        long end = System.currentTimeMillis();

        assertAfterExecute();
        System.out.println("execute " + getHandlerName() + "#execute(ActionContext) for " + StressTestHelper.SMALL
            + " times takes " + (end - start) + " milliseconds.");
    }

    /**
     * <p>
     * Stress test for <code>{@link Handler#execute(ActionContext)}</code> method.
     * </p>
     */
    public void testExecuteStressMEDIUM() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < StressTestHelper.MEDIUM; i++) {
            result = getHandler().execute(context);
        }

        long end = System.currentTimeMillis();

        assertAfterExecute();
        System.out.println("execute " + getHandlerName() + "#execute(ActionContext) for " + StressTestHelper.MEDIUM
            + " times takes " + (end - start) + " milliseconds.");
    }

    /**
     * <p>
     * Stress test for <code>{@link Handler#execute(ActionContext)}</code> method.
     * </p>
     */
    public void testExecuteStressLARGE() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < StressTestHelper.LARGE; i++) {
            result = getHandler().execute(context);
        }

        long end = System.currentTimeMillis();

        assertAfterExecute();
        System.out.println("execute " + getHandlerName() + "#execute(ActionContext) for " + StressTestHelper.LARGE
            + " times takes " + (end - start) + " milliseconds.");
    }

    /**
     * <p>
     * assert for the result after execute.
     * </p>
     */
    protected void assertAfterExecute() {
    }
}
