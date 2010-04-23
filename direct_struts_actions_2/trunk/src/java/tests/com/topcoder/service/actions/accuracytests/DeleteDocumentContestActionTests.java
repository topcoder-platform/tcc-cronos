/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.actions.DeleteDocumentContestAction;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * Accuracy tests for DeleteDocumentContestAction.
 * @author onsky
 * @version 1.0
 */
public class DeleteDocumentContestActionTests extends TestCase {
    /**
     * <p>Represents DeleteDocumentContestAction instance for testing.</p>
     */
    private DeleteDocumentContestAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new DeleteDocumentContestAction();
        ContestServiceFacade facade = new MockContestServiceFacade();
        instance.setContestServiceFacade(facade);
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for executeAction.
     */
    public void test_executeAction() {
        try {
        	instance.executeAction();
        	// pass
        } catch (Exception e) {
        	fail("no exception expected");
        }
    }

    /**
     * Accuracy test for setDocumentId.
     */
    public void test_setDocumentId() {
        assertEquals("must be 0 by default", 0, instance.getDocumentId());
        instance.setDocumentId(1l);
        assertEquals("Incorrect value after set a new one", 1l, instance.getDocumentId());
    }

    /**
     * Accuracy test for setContestId.
     */
    public void test_setContestId() {
        assertEquals("must be 0 by default", 0, instance.getContestId());
        instance.setContestId(2l);
        assertEquals("Incorrect value after set a new one", 2l, instance.getContestId());
    }
}
