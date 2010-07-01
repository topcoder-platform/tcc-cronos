/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.ActiveContestScheduleRetrievalAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

import com.topcoder.service.facade.direct.ContestSchedule;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


/**
 * The accuracy tests for the class {@link ActiveContestScheduleRetrievalAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class ActiveContestScheduleRetrievalActionAccTest {
    /** The instance for testing. */
    private ActiveContestScheduleRetrievalAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new ActiveContestScheduleRetrievalAction();
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
     * Test method for {@link ActiveContestScheduleRetrievalAction#executeAction()}.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction() throws Exception {
        MockDirectServiceFacade directServiceFacade = new MockDirectServiceFacade();
        action.setDirectServiceFacade(directServiceFacade);
        action.setContestId(1);
        // set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        action.execute();

        // verify the result
        ContestSchedule result = (ContestSchedule) action.getResult();
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", 1, result.getContestId());
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ActiveContestScheduleRetrievalAction#ActiveContestScheduleRetrievalAction()}.
     */
    @Test
    public void testActiveContestScheduleRetrievalAction() {
        assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link ActiveContestScheduleRetrievalAction#getContestId()}.
     */
    @Test
    public void testGetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestScheduleRetrievalAction#setContestId(long)}.
     */
    @Test
    public void testSetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestScheduleRetrievalAction#isStudio()}.
     */
    @Test
    public void testIsStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link ActiveContestScheduleRetrievalAction#setStudio(boolean)}.
     */
    @Test
    public void testSetStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }
}
