/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.ContestReceiptRetrievalAction;

import com.topcoder.service.facade.direct.ContestReceiptData;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


/**
 * The accuracy tests for the class {@link ContestReceiptRetrievalAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class ContestReceiptRetrievalActionAccTest {
    /** The instance for testing. */
    private ContestReceiptRetrievalAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new ContestReceiptRetrievalAction();
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
     * Test method for {@link ContestReceiptRetrievalAction#executeAction()}.
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
        ContestReceiptData result = (ContestReceiptData) action.getResult();
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", 1, result.getContestId());
        assertTrue("No error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ContestReceiptRetrievalAction#ContestReceiptRetrievalAction()}.
     */
    @Test
    public void testContestReceiptRetrievalAction() {
        assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link ContestReceiptRetrievalAction#getContestId()}.
     */
    @Test
    public void testGetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ContestReceiptRetrievalAction#setContestId(long)}.
     */
    @Test
    public void testSetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ContestReceiptRetrievalAction#isStudio()}.
     */
    @Test
    public void testIsStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link ContestReceiptRetrievalAction#setStudio(boolean)}.
     */
    @Test
    public void testSetStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }
}
