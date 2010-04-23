/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.topcoder.service.actions.StudioOrSoftwareContestAction;

/**
 * Failure tests for <code>StudioOrSoftwareContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class StudioOrSoftwareContestActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private StudioOrSoftwareContestAction instance;

    /**
     * Proxy.
     */
    private ActionProxy proxy;

    /**
     * Sets up the environment.
     * @throws java.lang.Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new StudioOrSoftwareContestAction() {};
        instance.prepare();

        // prepare the action proxy
        proxy = getActionProxy("/FailureStudioOrSoftwareContestAction");
        Helper.setUpSession(proxy);
    }

    /**
     * Cleans up the environment.
     * @throws java.lang.Exception to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Test method for {@link com.topcoder.service.actions.StudioOrSoftwareContestAction
     * #executeAction()}.
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteAction_BothIdSet() throws Exception {
        FailureStudioOrSoftwareContestAction.contestId = 1;
        FailureStudioOrSoftwareContestAction.projectId = 1;
        FailureStudioOrSoftwareContestAction action = (FailureStudioOrSoftwareContestAction) proxy.getAction();
        proxy.execute();
        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.StudioOrSoftwareContestAction
     * #executeAction()}.
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteAction_NegativeIdSet() throws Exception {
        FailureStudioOrSoftwareContestAction.contestId = -1;
        FailureStudioOrSoftwareContestAction.projectId = -1;

        FailureStudioOrSoftwareContestAction action = (FailureStudioOrSoftwareContestAction) proxy.getAction();
        proxy.execute();
        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be three error field messages.", 3, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.StudioOrSoftwareContestAction
     * #executeAction()}.
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteAction_NeitherIdSet() throws Exception {
        FailureStudioOrSoftwareContestAction.contestId = 0;
        FailureStudioOrSoftwareContestAction.projectId = 0;

        FailureStudioOrSoftwareContestAction action = (FailureStudioOrSoftwareContestAction) proxy.getAction();
        proxy.execute();
        assertTrue("There should be errors.", action.hasFieldErrors());
        // two setters violations, one execution violations
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.StudioOrSoftwareContestAction
     * #setProjectId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetProjectId_NegativeId() throws Exception {
        FailureStudioOrSoftwareContestAction action = (FailureStudioOrSoftwareContestAction) proxy.getAction();

        FailureStudioOrSoftwareContestAction.projectId = -1;
        FailureStudioOrSoftwareContestAction.contestId = 1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.StudioOrSoftwareContestAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetContestId() throws Exception {
        FailureStudioOrSoftwareContestAction action = (FailureStudioOrSoftwareContestAction) proxy.getAction();

        FailureStudioOrSoftwareContestAction.projectId = 1;
        FailureStudioOrSoftwareContestAction.contestId = -1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

}
