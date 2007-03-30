/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.stresstests;

import com.cronos.im.login.ManagerLoginAction;

import junit.framework.TestCase;

/**
 * Test case for ManagerLoginAction.
 *
 * @author radium
 * @version 1.0
 */
public class ManagerLoginActionStressTest extends TestCase {
    /**
     * The excute time.
     */
    private int time = 100;

    /**
     * Test method for ManagerLoginAction().
     */
    public void testManagerLoginAction() {
        for (int i = 0; i < time; i++) {
            assertNotNull("Should not be null.", new ManagerLoginAction());
        }
    }
}
