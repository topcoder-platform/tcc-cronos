/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.GameDataManagerConfigurationException;

import junit.framework.TestCase;

/**
 * <p>
 * The unit test cases for class GameDataManagerConfiguration.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataManagerConfigurationExceptionTests extends TestCase {
	/**
     * <p>
     * Accuracy test of the constructor
     * <code>GameDataManagerConfigurationException()</code>.
     * </p>
     * <p>
     * An instance of GameDataManagerConfigurationException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        Exception e = new GameDataManagerConfigurationException();

        assertNotNull("check the instance", e);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>GameDataManagerConfigurationException(String message)</code>.
     * </p>
     * <p>
     * An instance of GameDataManagerConfigurationException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor2() throws Exception {
        Exception e = new GameDataManagerConfigurationException("message");

        assertNotNull("check the instance", e);
        assertEquals("check message", "message", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>GameDataManagerConfigurationException(String message, Throwable e)</code>.
     * </p>
     * <p>
     * An instance of GameDataManagerConfigurationException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor3() throws Exception {
        Exception cause = new Exception();
        Exception e = new GameDataManagerConfigurationException("abc", cause);

        assertNotNull("check the instance", e);
        assertEquals("check message", "abc, caused by null", e.getMessage());
        assertEquals("check message", cause, e.getCause());
    }
}
