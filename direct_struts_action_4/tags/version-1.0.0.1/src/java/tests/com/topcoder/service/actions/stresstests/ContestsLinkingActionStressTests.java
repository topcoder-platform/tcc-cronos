/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.service.actions.stresstests;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;

import org.apache.struts2.StrutsSpringTestCase;

import org.springframework.test.context.support.GenericXmlContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * Stress test for <code>ContestsLinkingAction</code> class.
 * </p>
 * @author morehappiness
 * @version 1.0
 */
public class ContestsLinkingActionStressTests extends StrutsSpringTestCase {
    /**
     * The run time of the tests.
     */
    private static final long RUN_TIMES = 100;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setupBeforeInitDispatcher() throws Exception {
        // only load beans from spring once
        if (applicationContext == null) {
            GenericXmlContextLoader xmlContextLoader = new GenericXmlContextLoader();

            applicationContext = xmlContextLoader.loadContext("test_files/stress/applicationContext.xml");
        }

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);
    }

    /**
     * <p>
     * Test for the <code>executeAction</code> method.
     * </p>
     * @throws Exception to JUnit.
     */
    public void test_executeAction() throws Exception {
        for (int i = 0; i < RUN_TIMES; i++) {
            request.setParameter("contestId", RUN_TIMES + "");

            ActionProxy proxy = getActionProxy("/ContestLinksRetrievalAction");

            assertEquals("Should be equals", Action.SUCCESS, proxy.execute());
        }
    }
}
