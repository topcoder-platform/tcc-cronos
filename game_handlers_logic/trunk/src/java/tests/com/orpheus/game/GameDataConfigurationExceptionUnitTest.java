/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * UnitTest for GameDataConfigurationException class.
 *
 * @author mittu
 * @version 1.0
 */
public class GameDataConfigurationExceptionUnitTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GameDataConfigurationExceptionUnitTest.class);
    }

    /**
     * Accuracy test of <code>GameDataConfigurationException(String message)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDataConfigurationExceptionAccuracy1() throws Exception {
        GameDataConfigurationException exception = new GameDataConfigurationException("from junit");
        assertEquals("message is incorrect.", "from junit", exception.getMessage());
    }

    /**
     * Accuracy test of <code>GameDataConfigurationException(String message, Throwable cause)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGameDataConfigurationExceptionAccuracy2() throws Exception {
        Exception cause = new Exception("cause");
        GameDataConfigurationException exception = new GameDataConfigurationException("from junit", cause);
        assertEquals("cause is incorrect.", cause, exception.getCause());
        assertEquals("message is incorrect.", "from junit, caused by cause", exception.getMessage());
    }
}
