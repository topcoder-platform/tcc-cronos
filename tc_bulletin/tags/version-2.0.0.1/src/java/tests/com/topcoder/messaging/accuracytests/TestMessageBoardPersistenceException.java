/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import com.topcoder.messaging.MessageBoardPersistenceException;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>MessageBoardPersistenceException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestMessageBoardPersistenceException extends TestCase {

    /**
     * <p>
     * Represents the message to be used for testing.
     * </p>
     */
    private String message = "message";

    /**
     * <p>
     * Represetns the cause to be used for testing.
     * </p>
     */
    private Throwable cause = new Throwable("some cause");

    /**
     * <p>
     * Represents the info data to be used for testing.
     * </p>
     */
    private ExceptionData data = new ExceptionData();

    /**
     * <p>
     * This unit test tests the constructor with message to properly create the
     * exception instance and set the message.
     * </p>
     *
     * @throws Exception
     *         if any occurs, raise it to JUnit
     */
    public void testCtorWithMessage() throws Exception {
        MessageBoardPersistenceException e = new MessageBoardPersistenceException(message);

        assertNotNull("Cannot create MessageBoardPersistenceException", e);
        assertEquals("Message not set properly", message, e.getMessage());
    }

    /**
     * <p>
     * This unit test tests the constructor with message and cause to properly
     * create the exception instance and set the message and cause.
     * </p>
     *
     * @throws Exception
     *         if any occurs, raise it to JUnit
     */
    public void testCtorWithMessageCause() throws Exception {
        MessageBoardPersistenceException e = new MessageBoardPersistenceException(message, cause);

        assertNotNull("Cannot create MessageBoardPersistenceException", e);
        assertEquals("Message not set properly", message, e.getMessage());
        assertEquals("Cause not set properly", cause, e.getCause());
    }

    /**
     * <p>
     * This unit test tests the constructor with message and info data to
     * properly create the exception instance and set the message.
     * </p>
     *
     * @throws Exception
     *         if any occurs, raise it to JUnit
     */
    public void testCtorWithMessageInfoData() throws Exception {
        MessageBoardPersistenceException e = new MessageBoardPersistenceException(message, data);

        assertNotNull("Cannot create MessageBoardPersistenceException", e);
        assertEquals("Message not set properly", message, e.getMessage());
    }

    /**
     * <p>
     * This unit test tests the constructor with message, cause and info data to
     * properly create the exception instance and set the message and cause.
     * </p>
     *
     * @throws Exception
     *         if any occurs, raise it to JUnit
     */
    public void testCtorWithMessageCauseInfoData() throws Exception {
        MessageBoardPersistenceException e = new MessageBoardPersistenceException(message, cause, data);

        assertNotNull("Cannot create MessageBoardPersistenceException", e);
        assertEquals("Message not set properly", message, e.getMessage());
        assertEquals("Cause not set properly", cause, e.getCause());
    }

}
