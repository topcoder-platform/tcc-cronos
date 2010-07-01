/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.ProjectContestDataRetrievalAction;

import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ProjectSummaryData;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The accuracy tests for the class {@link ProjectContestDataRetrievalAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class ProjectContestDataRetrievalActionAccTest {
    /** The instance for testing. */
    private ProjectContestDataRetrievalAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new ProjectContestDataRetrievalAction();
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
     * Test method for {@link ProjectContestDataRetrievalAction#executeAction()}.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testExecuteAction() throws Exception {
        MockContestServiceFacade contestServiceFacade = new MockContestServiceFacade();
        action.setContestServiceFacade(contestServiceFacade);
        action.setProjectId(1);
        // set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        action.execute();

        // verify the result
        Map<String, Object> result = (Map<String, Object>) action.getResult();
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", 2, result.size());

        List<CommonProjectContestData> contestData = (List<CommonProjectContestData>) result.get("contestData");
        assertNotNull("The result should not be null.", contestData);
        assertEquals("The result is incorrect.", 1, contestData.size());

        List<ProjectSummaryData> projectSummaryData = (List<ProjectSummaryData>) result.get("projectSummaryData");
        assertNotNull("The result should not be null.", projectSummaryData);
        assertEquals("The result is incorrect.", 2, projectSummaryData.size());
        //there should be no error.
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ProjectContestDataRetrievalAction#ProjectContestDataRetrievalAction()}.
     */
    @Test
    public void testProjectContestDataRetrievalAction() {
        assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link ProjectContestDataRetrievalAction#getProjectId()}.
     */
    @Test
    public void testGetProjectId() {
        action.setProjectId(1);
        assertEquals("The result is incorrect.", 1, action.getProjectId());
    }

    /**
     * Test method for {@link ProjectContestDataRetrievalAction#setProjectId(long)}.
     */
    @Test
    public void testSetProjectId() {
        action.setProjectId(1);
        assertEquals("The result is incorrect.", 1, action.getProjectId());
    }
}
