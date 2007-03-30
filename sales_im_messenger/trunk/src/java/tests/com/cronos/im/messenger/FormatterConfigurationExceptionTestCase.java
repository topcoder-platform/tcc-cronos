/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

/**
 * Test cases for the <code>FormatterConfigurationException</code> class.
 * Since this class inherits <c>ChatMessageFormattingException</c> class when
 * testing the constructors, will be tested also the accuracy for the inherited
 * method <c>getChatText</c>.
 *
 * @author marius_neo
 * @version 1.0
 */
public class FormatterConfigurationExceptionTestCase extends TestCase {
    /**
     * Tests if this class inherits from <c>ChatMessageFormattingException</c> class as requested
     * in component specification.
     */
    public void testInheritance() {
        FormatterConfigurationException e = new FormatterConfigurationException("Hello there");
        assertTrue("This class should inherit MessengerException", e instanceof ChatMessageFormattingException);
    }

    /**
     * Tests the <code>FormatterConfigurationException(String)</code> constructor.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testFormatterConfigurationExceptionString() {
        FormatterConfigurationException e = new FormatterConfigurationException("Hello there");
        assertEquals("The message must be set in the constructor!", "Hello there", e.getMessage());
        assertTrue("The chat text for this exception should be an empty string", e.getChatText().length() == 0);
    }

    /**
     * Tests the <code>FormatterConfigurationException(String, Throwable)</code> constructor.
     * Simply verifies that the correct parameters are set in the constructor.
     */
    public void testFormatterConfigurationExceptionStringThrowable() {
        Exception me = new Exception("testException");
        FormatterConfigurationException e = new FormatterConfigurationException("I was caused by me!", me);
        assertEquals("The cause must be set in the constructor!", me, e.getCause());
        assertTrue("The chat text for this exception should be an empty string", e.getChatText().length() == 0);
    }
}