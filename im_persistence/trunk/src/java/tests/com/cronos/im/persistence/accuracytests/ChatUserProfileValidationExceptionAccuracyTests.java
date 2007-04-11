/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import junit.framework.TestCase;

import com.cronos.im.persistence.ChatUserProfileValidationException;

/**
 * <p>
 * Accuracy test for <code>{@link ChatUserProfileValidationException}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class ChatUserProfileValidationExceptionAccuracyTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Tests accuracy of <code>ChatUserProfileValidationException(String)</code> constructor. The detail error message
     * should be correct.
     */
    public void testChatUserProfileValidationExceptionStringAccuracy() {
        // Construct ChatUserProfileValidationException with a detail message
        ChatUserProfileValidationException exception = new ChatUserProfileValidationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }
}
