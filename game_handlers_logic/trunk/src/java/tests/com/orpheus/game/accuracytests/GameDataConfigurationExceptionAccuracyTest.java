/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.GameDataConfigurationException;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for GameDataConfigurationException class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataConfigurationExceptionAccuracyTest extends TestCase {
    /**
     * The message for constructors.
     */
    private static final String MESSAGE = "Accuracy Test";

    /**
     * Test ctor GameDataConfigurationException(String message), an instance
     * with the given message should be created.
     */
    public void testCtor1() {
        GameDataConfigurationException e = new GameDataConfigurationException(MESSAGE);
        assertNotNull("Instance should be created with the given message", e);
        assertTrue("The message not set correctly.", e.getMessage().indexOf(MESSAGE) >= 0);
    }

    /**
     * Test ctor GameDataConfigurationException(String message, Throwable cause), 
     * an instance with the given message and cause should be created.
     */
    public void testCtor2() {
        Exception cause = new Exception();
        GameDataConfigurationException e = new GameDataConfigurationException(MESSAGE, cause);

        assertNotNull("Instance should be created with the given message", e);
        assertTrue("The message not set correctly.", e.getMessage().indexOf(MESSAGE) >= 0);
        assertEquals("The cause not set correctly.", cause, e.getCause());
    }
}
