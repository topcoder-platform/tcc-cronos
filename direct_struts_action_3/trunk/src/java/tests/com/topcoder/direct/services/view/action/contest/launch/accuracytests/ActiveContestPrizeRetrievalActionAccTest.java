/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.ActiveContestPrizeRetrievalAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

import com.topcoder.service.facade.direct.ContestPrize;

import org.junit.After;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


/**
 * The accuracy tests for the class {@link ActiveContestPrizeRetrievalAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class ActiveContestPrizeRetrievalActionAccTest {
    /** The instance for testing. */
    private ActiveContestPrizeRetrievalAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new ActiveContestPrizeRetrievalAction();
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
     * Test method for {@link ActiveContestPrizeRetrievalAction#executeAction()} .
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
        ContestPrize result = (ContestPrize) action.getResult();
        assertNotNull("The result should not be null.", result);
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ActiveContestPrizeRetrievalAction#ActiveContestPrizeRetrievalAction()} .
     */
    @Test
    public void testActiveContestPrizeRetrievalAction() {
        assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link ActiveContestPrizeRetrievalAction#getContestId()}.
     */
    @Test
    public void testGetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestPrizeRetrievalAction#setContestId(long)}.
     */
    @Test
    public void testSetContestId() {
    	action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ActiveContestPrizeRetrievalAction#isStudio()}.
     */
    @Test
    public void testIsStudio() {
    	action.setStudio(true);
    	assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link ActiveContestPrizeRetrievalAction#setStudio(boolean)}.
     */
    @Test
    public void testSetStudio() {
    	action.setStudio(true);
    	assertEquals("The result is incorrect", true, action.isStudio());
    }
}
