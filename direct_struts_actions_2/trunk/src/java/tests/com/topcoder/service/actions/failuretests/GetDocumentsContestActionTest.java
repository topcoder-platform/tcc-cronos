/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.GetDocumentsContestAction;

/**
 * Failure tests for <code>GetDocumentsContestAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class GetDocumentsContestActionTest extends TestCase {
    /**
     * Test method for {@link com.topcoder.service.actions.PayContestAction#executeAction()}.
     * @throws Exception to JUnit
     */
    public void testExecuteAction_Null() throws Exception {
        GetDocumentsContestAction action = new GetDocumentsContestAction();

        try {
            action.executeAction();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // good
        }
    }

}
