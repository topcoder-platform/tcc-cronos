/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.WikiLinkRetrievalAction;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


/**
 * The accuracy tests for the class {@link WikiLinkRetrievalAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class WikiLinkRetrievalActionAccTest {
    /** The instance for testing. */
    private WikiLinkRetrievalAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new WikiLinkRetrievalAction();
        action.prepare();
    }

    /**
     * tears down the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @After
    public void tearDown() throws Exception {
        action = null;
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#executeAction()}.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction() throws Exception {
        MockContestServiceFacade contestServiceFacade = new MockContestServiceFacade();
        action.setContestServiceFacade(contestServiceFacade);
        action.setProjectId(1);
        action.setContestId(1);
        action.setStudio(true);
        action.setLinkTemplate("www.topcoder.com/tc/[contest_type]/[contest_name]/");
        // set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        action.execute();

        // verify the result
        String result = (String) action.getResult();
        assertEquals("The result is incorrect.", "www.topcoder.com%2Ftc%2FJava%2Ftestcontest%2F", result);
        //there should be no error.
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#WikiLinkRetrievalAction()}.
     */
    @Test
    public void testWikiLinkRetrievalAction() {
        assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#getProjectId()}.
     */
    @Test
    public void testGetProjectId() {
        action.setProjectId(1);
        assertEquals("The result is incorrect.", 1, action.getProjectId());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#setProjectId(long)}.
     */
    @Test
    public void testSetProjectId() {
        action.setProjectId(1);
        assertEquals("The result is incorrect.", 1, action.getProjectId());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#getContestId()}.
     */
    @Test
    public void testGetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#setContestId(long)}.
     */
    @Test
    public void testSetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#isStudio()}.
     */
    @Test
    public void testIsStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#setStudio(boolean)}.
     */
    @Test
    public void testSetStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#getLinkTemplate()}.
     */
    @Test
    public void testGetLinkTemplate() {
        action.setLinkTemplate("www.topcoder.com/tc/[contest_type]/[contest_name]/");
        assertEquals("The result is incorrect", "www.topcoder.com/tc/[contest_type]/[contest_name]/",
            action.getLinkTemplate());
    }

    /**
     * Test method for {@link WikiLinkRetrievalAction#setLinkTemplate(java.lang.String)}.
     */
    @Test
    public void testSetLinkTemplate() {
        action.setLinkTemplate("www.topcoder.com/tc/[contest_type]/[contest_name]/");
        assertEquals("The result is incorrect", "www.topcoder.com/tc/[contest_type]/[contest_name]/",
            action.getLinkTemplate());
    }
}
