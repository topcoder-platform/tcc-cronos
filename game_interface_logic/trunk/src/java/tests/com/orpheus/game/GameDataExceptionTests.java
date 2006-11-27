/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for GameDataException.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.3
 */
public class GameDataExceptionTests extends TestCase {
    /** The test message. */
    private static final String ERROR_MESSAGE = "test exception message";

    /**
     * the cause Exception.
     */
    private final Exception cause = new NullPointerException();

    /**
     * test the excption constructor with ERROR_MESSAGE.
     */
    public void testGameDataException0() {
        assertNotNull("can not create the GameDataException.",
            new GameDataException());
    }

    /**
     * test the excption constructor with ERROR_MESSAGE.
     */
    public void testGameDataException1() {
        GameDataException de = new GameDataException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate GameDataException.", de);

        assertEquals("Error message is not properly propagated to super class.",
            ERROR_MESSAGE, de.getMessage());
        assertTrue("The error message should match.",
            de.getMessage().indexOf(ERROR_MESSAGE) >= 0);
    }

    /**
     * test the excption constructor with ERROR_MESSAGE and throwable.
     */
    public void testGameDataException2() {
        GameDataException de = new GameDataException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate GameDataException.", de);

        assertEquals("Cause is not properly propagated to super class.", cause,
            de.getCause());
    }

    /**
     * Inheritance test.
     */
    public void testGameDataException3() {
        assertTrue("GameDataException does not subclass BaseException.",
            new GameDataException(ERROR_MESSAGE) instanceof BaseException);

        assertTrue("GameDataException does not subclass BaseException.",
            new GameDataException(ERROR_MESSAGE, cause) instanceof BaseException);
    }
}
