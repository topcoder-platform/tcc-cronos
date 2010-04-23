/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.PayByCreditCardAction;

/**
 * Failure tests for <code>PayByCreditCardAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class PayContestActionTest extends TestCase {
    /**
     * Test method for {@link com.topcoder.service.actions.PayByCreditCard#executeAction()}.
     * @throws Exception to JUnit
     */
    public void testExecuteAction_Null() throws Exception {
        PayByCreditCardAction action = new PayByCreditCardAction();
        try {
            action.executeAction();
            fail("IllegalStateException expected");
        } catch (IllegalStateException e) {
            // good
        }
    }

}
