/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.ContestAction;

/**
 * Failure tests for <code>ContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class ContestActionTest extends TestCase {

    /**
     * Instance to test.
     */
    private ContestAction instance;

    /**
     * Set up the environment.
     * @throws java.lang.Exception to JUnit
     */
    public void setUp() throws Exception {
        instance = new ContestAction() {
            @Override
            protected void executeAction() throws Exception {
            }};
    }

    /**
     * Test method for {@link com.topcoder.service.actions.ContestAction
     * #setContestServiceFacade(com.topcoder.service.facade.contest.ContestServiceFacade)}.
     */
    public void testSetContestServiceFacade_Null() {
        try {
            instance.setContestServiceFacade(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

}
