/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.SaveDraftContestAction;


/**
 * Failure cases of SaveDraftContestAction.
 *
 * @author onsky
 * @version 1.0
 */
public class SaveDraftContestActionTests extends TestCase {
    /**
     * <p>Represents SaveDraftContestAction instance for testing.</p>
     */
    private SaveDraftContestAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new SaveDraftContestAction();
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

    /**
     * Failure test for method setPrizesData.
     */
    public void test_setPrizesData() {
        try {
            instance.setPrizesData(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
