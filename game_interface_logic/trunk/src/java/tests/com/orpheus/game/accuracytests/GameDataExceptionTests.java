/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.GameDataException;

import junit.framework.TestCase;

/**
 * <p>
 * The unit test cases for class GameDataException.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataExceptionTests extends TestCase {
	/**
     * <p>
     * Accuracy test of the constructor
     * <code>GameDataException()</code>.
     * </p>
     * <p>
     * An instance of GameDataException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        Exception e = new GameDataException();

        assertNotNull("check the instance", e);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>GameDataException(String message)</code>.
     * </p>
     * <p>
     * An instance of GameDataException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor2() throws Exception {
        Exception e = new GameDataException("message");

        assertNotNull("check the instance", e);
        assertEquals("check message", "message", e.getMessage());
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>GameDataException(String message, Throwable e)</code>.
     * </p>
     * <p>
     * An instance of GameDataException should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor3() throws Exception {
        Exception cause = new Exception();
        Exception e = new GameDataException("abc", cause);

        assertNotNull("check the instance", e);
        assertEquals("check message", "abc, caused by null", e.getMessage());
        assertEquals("check message", cause, e.getCause());
    }
}
