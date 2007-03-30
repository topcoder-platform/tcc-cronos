/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.stresstests;

import junit.framework.TestCase;

import com.cronos.im.login.AdminLoginAction;

/**
 * Stress test method for AdminLoginAction.
 *
 * @author radium
 * @version 1.0
 */
public class AdminLoginActionStressTest extends TestCase {
    /**
     * The excute time.
     */
    private int time = 100;
    /**
     * Test method for AdminLoginAction().
     */
    public void testAdminLoginAction() {
        for (int i = 0; i < time; i++) {
            assertNotNull("Should not be null.", new AdminLoginAction());
        }
    }
}
