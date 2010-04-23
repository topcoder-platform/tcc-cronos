/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.actions.GetContestCategoriesAction;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * Accuracy tests for GetContestCategoriesAction.
 * @author onsky
 * @version 1.0
 */
public class GetContestCategoriesActionTests extends TestCase {
    /**
     * <p>Represents GetContestCategoriesAction instance for testing.</p>
     */
    private GetContestCategoriesAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new GetContestCategoriesAction();
        ContestServiceFacade facade = new MockContestServiceFacade();
        instance.setContestServiceFacade(facade);
        instance.prepare();
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
     * Accuracy test for setContestServiceFacade.
     */
    public void test_setContestServiceFacade() {
        ContestServiceFacade facade = new MockContestServiceFacade();
        instance.setContestServiceFacade(facade);
        assertEquals("Incorrect value after set a new one", facade, instance.getContestServiceFacade());
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
}
