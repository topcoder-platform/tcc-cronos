/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.GamePlanRetrievalAction;

import com.topcoder.service.facade.direct.ContestPlan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;


/**
 * The accuracy tests for the class {@link GamePlanRetrievalAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class GamePlanRetrievalActionAccTest {
    /** The instance for testing. */
    private GamePlanRetrievalAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new GamePlanRetrievalAction();
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
     * Test method for {@link GamePlanRetrievalAction#executeAction()}.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction() throws Exception {
        MockDirectServiceFacade directServiceFacade = new MockDirectServiceFacade();
        action.setDirectServiceFacade(directServiceFacade);
        action.setProjectId(1);
        // set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        action.execute();

        // verify the result
        @SuppressWarnings("unchecked")
        List<ContestPlan> result = (List<ContestPlan>) action.getResult();
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", 2, result.size());
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link GamePlanRetrievalAction#GamePlanRetrievalAction()}.
     */
    @Test
    public void testGamePlanRetrievalAction() {
        assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link GamePlanRetrievalAction#getProjectId()}.
     */
    @Test
    public void testGetProjectId() {
        action.setProjectId(1);
        assertEquals("The result is incorrect.", 1, action.getProjectId());
    }

    /**
     * Test method for {@link GamePlanRetrievalAction#setProjectId(long)}.
     */
    @Test
    public void testSetProjectId() {
        action.setProjectId(1);
        assertEquals("The result is incorrect.", 1, action.getProjectId());
    }
}
