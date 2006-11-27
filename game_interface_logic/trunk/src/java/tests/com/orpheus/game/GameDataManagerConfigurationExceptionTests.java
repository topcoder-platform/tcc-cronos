/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for GameDataManagerConfigurationException.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.3
 */
public class GameDataManagerConfigurationExceptionTests extends TestCase {
    /** The test message. */
    private static final String ERROR_MESSAGE = "test exception message";

    /**
     * the cause Exception.
     */
    private final Exception cause = new NullPointerException();

    /**
     * test the excption constructor with ERROR_MESSAGE.
     */
    public void testGameDataManagerConfigurationException0() {
        assertNotNull("can not create the GameDataManagerConfigurationException.",
            new GameDataManagerConfigurationException());
    }

    /**
     * test the excption constructor with ERROR_MESSAGE.
     */
    public void testGameDataManagerConfigurationException1() {
        GameDataManagerConfigurationException de = new GameDataManagerConfigurationException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate GameDataManagerConfigurationException.",
            de);

        assertEquals("Error message is not properly propagated to super class.",
            ERROR_MESSAGE, de.getMessage());
        assertTrue("The error message should match.",
            de.getMessage().indexOf(ERROR_MESSAGE) >= 0);
    }

    /**
     * test the excption constructor with ERROR_MESSAGE and throwable.
     */
    public void testGameDataManagerConfigurationException2() {
        GameDataManagerConfigurationException de = new GameDataManagerConfigurationException(ERROR_MESSAGE,
                cause);

        assertNotNull("Unable to instantiate GameDataManagerConfigurationException.",
            de);

        assertEquals("Cause is not properly propagated to super class.", cause,
            de.getCause());
    }

    /**
     * Inheritance test.
     */
    public void testGameDataManagerConfigurationException3() {
        assertTrue("GameDataManagerConfigurationException does not subclass BaseException.",
            new GameDataManagerConfigurationException(ERROR_MESSAGE) instanceof BaseException);

        assertTrue("GameDataManagerConfigurationException does not subclass BaseException.",
            new GameDataManagerConfigurationException(ERROR_MESSAGE, cause) instanceof BaseException);
    }
}
