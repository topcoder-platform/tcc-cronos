/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.topcoder.service.actions.DeleteDocumentContestAction;

/**
 * Failure tests for <code>DeleteDocumentContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DeleteDocumentContestActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private DeleteDocumentContestAction instance;

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
        instance = new DeleteDocumentContestAction() {};
        instance.prepare();

        // prepare the action proxy
        proxy = getActionProxy("/FailureDeleteDocumentContestAction");
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
     * Test method for {@link com.topcoder.service.actions.DeleteDocumentContestAction
     * #executeAction()}.
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteAction_Null() throws Exception {
        try {
            instance.executeAction();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.DeleteDocumentContestAction
     * #setProjectId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetProjectId_NegativeId() throws Exception {
        FailureDeleteDocumentContestAction action = (FailureDeleteDocumentContestAction) proxy.getAction();

        FailureDeleteDocumentContestAction.documentId = -1;
        FailureDeleteDocumentContestAction.contestId = 1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.DeleteDocumentContestAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testSetContestId() throws Exception {
        FailureDeleteDocumentContestAction action = (FailureDeleteDocumentContestAction) proxy.getAction();

        FailureDeleteDocumentContestAction.documentId = 1;
        FailureDeleteDocumentContestAction.contestId = -1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 1, action.getFieldErrors().size());
    }

}
