/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.ContestAction;
import com.topcoder.service.actions.GetContestAction;


/**
 * Failure cases of ContestAction.
 *
 * @author onsky
 * @version 1.0
 */
public class ContestActionTests extends TestCase {
    /**
     * <p>Represents ContestAction instance for testing.</p>
     */
    private ContestAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new GetContestAction();
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
     * Failure test for method setContestServiceFacade.
     */
    public void test_setContestServiceFacade() {
        try {
            instance.setContestServiceFacade(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
