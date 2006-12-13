/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.TestCase;


/**
 * Test case for GameOperationConfigException.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameOperationConfigExceptionTest extends TestCase {
    /**
     * Test ctor GameOperationConfigException(String) with string message and test whether it can be obtained later.
     */
    public void testGameOperationConfigExceptionString() {
        String msg = "msg";
        GameOperationConfigException e = new GameOperationConfigException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * Test GameOperationConfigException(String, Throwable) with msg and cause, and obtain them later.
     */
    public void testGameOperationConfigExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        GameOperationConfigException e = new GameOperationConfigException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
