/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>GeneratorCreationException</code> class. Verify the correct functioning of both
 * constructors.
 *
 * @author marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class GeneratorCreationExceptionTests extends TestCase {
    /**
     * Verify behavior of the constructor accepting only a message.
     */
    public void testConstructorMessage() {
        GeneratorCreationException e = new GeneratorCreationException("foobar");
        assertEquals("Message is incorrect", "foobar", e.getMessage());
    }

    /**
     * Verify behavior of the constructor accepting both a message and a cause.
     */
    public void testConstructorMessageCause() {
        Throwable cause = new Throwable("cause");
        GeneratorCreationException e = new GeneratorCreationException("foobar", cause);
        assertSame("Cause is incorrect", cause, e.getCause());
        assertTrue("Message is incorrect", e.getMessage().startsWith("foobar"));
    }
}
