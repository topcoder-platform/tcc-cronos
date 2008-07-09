/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import com.topcoder.messaging.ErrorMessagesCache;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>ErrorMessagesCache</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestErrorMessagesCache extends TestCase {

    /**
     * <p>
     * This method tests the value of INVALID_ARGUMENT to be proper.
     */
    public void testInvalidArgument() {
        assertEquals("INVALID_ARGUMENT not proper", "invalid_argument", ErrorMessagesCache.INVALID_ARGUMENT);
    }

    /**
     * <p>
     * This method tests the value of MESSAGE_SIZE_EXCEEDED to be proper.
     */
    public void testMessageSizeExceeded() {
        assertEquals("MESSAGE_SIZE_EXCEEDED not proper",
                "message_size_exceeded", ErrorMessagesCache.MESSAGE_SIZE_EXCEEDED);
    }

    /**
     * <p>
     * This method tests the value of CONFIGURATION to be proper.
     */
    public void testConfiguration() {
        assertEquals("CONFIGURATION not proper", "configuration", ErrorMessagesCache.CONFIGURATION);
    }

    /**
     * <p>
     * This method tests the value of PERSISTENCE to be proper.
     */
    public void testPersistence() {
        assertEquals("PERSISTENCE not proper", "persistence", ErrorMessagesCache.PERSISTENCE);
    }

    /**
     * <p>
     * This method tests the value of ENTITY_NOT_FOUND to be proper.
     */
    public void testEntityNotFound() {
        assertEquals("ENTITY_NOT_FOUND not proper", "entity_not_found", ErrorMessagesCache.ENTITY_NOT_FOUND);
    }

    /**
     * <p>
     * This method tests the addErrorMessage() method to properly add the
     * specified error message. This method also tests the getter for the
     * error message given the specified key.
     * </p>
     */
    public void testAddGetErrorMessage() {
        ErrorMessagesCache.addErrorMessage("errorKey", "some error message");
        assertEquals("Error message not set proeprly",
                "some error message", ErrorMessagesCache.getErrorMessage("errorKey"));

        ErrorMessagesCache.addErrorMessage("errorKey", "some error message override");
        assertEquals("Error message not set proeprly",
                "some error message override", ErrorMessagesCache.getErrorMessage("errorKey"));
    }

    /**
     * <p>
     * This method tests the removeErrorMessage() to remove the meesage properly.
     * </p>
     */
    public void testRemoveErrorMessage() {
        ErrorMessagesCache.addErrorMessage("errorKey1", "some error message1");
        ErrorMessagesCache.addErrorMessage("errorKey2", "some error message2");

        String msg1 = ErrorMessagesCache.removeErrorMessage("errorKey1");
        assertEquals("Error message not returned properly", "some error message1", msg1);
        assertNull("Error message not removed properly", ErrorMessagesCache.getErrorMessage("errorKey1"));

        String msg2 = ErrorMessagesCache.removeErrorMessage("invalidKey");
        assertNull("Error message should return null for invalid key", msg2);
    }

    /**
     * <p>
     * This method tests the clearErrorMessages() to remove the meesages properly.
     * </p>
     */
    public void testClearErrorMessage() {
        ErrorMessagesCache.addErrorMessage("errorKey1", "some error message1");
        ErrorMessagesCache.addErrorMessage("errorKey2", "some error message2");

        ErrorMessagesCache.clearErrorMessages();
        assertNull("Error message not cleared properly", ErrorMessagesCache.getErrorMessage("errorKey1"));
        assertNull("Error message not cleared properly", ErrorMessagesCache.getErrorMessage("errorKey2"));
    }

}
