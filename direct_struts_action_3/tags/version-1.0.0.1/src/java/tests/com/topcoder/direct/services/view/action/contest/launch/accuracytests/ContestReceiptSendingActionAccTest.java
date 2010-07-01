/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.opensymphony.xwork2.ActionContext;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.ContestReceiptSendingAction;

import com.topcoder.service.facade.direct.ContestReceiptData;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


/**
 * The accuracy tests for the class {@link ContestReceiptSendingAction}.
 *
 * @author KLW
 * @version 1.0
 */
public class ContestReceiptSendingActionAccTest {
    /** The instance for testing. */
    private ContestReceiptSendingAction action;

    /**
     * sets up the test environment.
     *
     * @throws java.lang.Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
        action = new ContestReceiptSendingAction();
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
     * Test method for {@link ContestReceiptSendingAction#executeAction()}.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction() throws Exception {
        MockDirectServiceFacade directServiceFacade = new MockDirectServiceFacade();
        action.setDirectServiceFacade(directServiceFacade);
        action.setContestId(1);
        action.setAdditionalEmailAddresses("test1@topcoder.com,test2@topcoder.com;test3@topcoder.com");
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
     * Test method for {@link ContestReceiptSendingAction#executeAction()}.
     *
     * @throws Exception if any error occurs.
     */
    @Test
    public void testExecuteAction_1() throws Exception {
        MockDirectServiceFacade directServiceFacade = new MockDirectServiceFacade();
        action.setDirectServiceFacade(directServiceFacade);
        action.setContestId(1);
        action.setAdditionalEmailAddresses("topcoder.com,test_topcoder.com;test3opcoder.com");
        // set the context
        ActionContext.setContext(new ActionContext(new HashMap<String, Object>()));
        action.execute();

        // verify the result
        assertFalse("Error should be added.", action.getFieldErrors().isEmpty());
    }

    /**
     * Test method for {@link ContestReceiptSendingAction#ContestReceiptSendingAction()}.
     */
    @Test
    public void testContestReceiptSendingAction() {
        assertNotNull("The instance should not be null.", action);
        assertTrue("The inheritance is incorrect.", action instanceof BaseDirectStrutsAction);
    }

    /**
     * Test method for {@link ContestReceiptSendingAction#getContestId()}.
     */
    @Test
    public void testGetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ContestReceiptSendingAction#setContestId(long)}.
     */
    @Test
    public void testSetContestId() {
        action.setContestId(1);
        assertEquals("The result is incorrect.", 1, action.getContestId());
    }

    /**
     * Test method for {@link ContestReceiptSendingAction#isStudio()}.
     */
    @Test
    public void testIsStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link ContestReceiptSendingAction#setStudio(boolean)}.
     */
    @Test
    public void testSetStudio() {
        action.setStudio(true);
        assertEquals("The result is incorrect", true, action.isStudio());
    }

    /**
     * Test method for {@link ContestReceiptSendingAction#getAdditionalEmailAddresses()}.
     */
    @Test
    public void testGetAdditionalEmailAddresses() {
        action.setAdditionalEmailAddresses("test1@topcoder.com,test2@topcoder.com;test3@topcoder.com");
        assertEquals("The result is incorrect", "test1@topcoder.com,test2@topcoder.com;test3@topcoder.com",
            action.getAdditionalEmailAddresses());
    }

    /**
     * Test method for {@link ContestReceiptSendingAction#setAdditionalEmailAddresses(java.lang.String)}.
     */
    @Test
    public void testSetAdditionalEmailAddresses() {
        action.setAdditionalEmailAddresses("test1@topcoder.com,test2@topcoder.com;test3@topcoder.com");
        assertEquals("The result is incorrect", "test1@topcoder.com,test2@topcoder.com;test3@topcoder.com",
            action.getAdditionalEmailAddresses());
    }
}
