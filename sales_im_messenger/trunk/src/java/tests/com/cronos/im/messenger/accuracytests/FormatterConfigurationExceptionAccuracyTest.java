/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.FormatterConfigurationException;
import com.cronos.im.messenger.MessengerException;


/**
 * Tests the functionality for class <code>FormatterConfigurationException</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class FormatterConfigurationExceptionAccuracyTest extends ChatMessageFormattingExceptionAccuracyTest {
    /**
     * Test method for 'FormatterConfigurationException(String)'.
     */
    public void testUserIDRetrieverExceptionString() {
        FormatterConfigurationException exception = new FormatterConfigurationException("msg");
        assertTrue("Test method for 'FormatterConfigurationException(String)' failed.",
            exception instanceof MessengerException);
    }

    /**
     * Test method for 'FormatterConfigurationException(String)'.
     */
    public void testUserIDRetrieverExceptionString2() {
        FormatterConfigurationException exception = new FormatterConfigurationException("msg");
        assertEquals("Test method for 'FormatterConfigurationException(String)' failed.", "msg", exception.getMessage());
    }

    /**
     * Test method for 'FormatterConfigurationException.FormatterConfigurationException(String, Throwable)'
     */
    public void testUserIDRetrieverExceptionStringThrowable() {
        Exception cause = new Exception("Root");
        FormatterConfigurationException exception = new FormatterConfigurationException("msg", cause);
        assertEquals("Test method for 'FormatterConfigurationException(String, Throwable)' failed.", cause,
            exception.getCause());
    }
}
