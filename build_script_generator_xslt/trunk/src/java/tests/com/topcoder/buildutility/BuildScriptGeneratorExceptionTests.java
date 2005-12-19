/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>BuildScriptGeneratorException</code> class. Verify the correct functioning of both
 * constructors.
 *
 * @author marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class BuildScriptGeneratorExceptionTests extends TestCase {
    /**
     * Verify behavior of the constructor accepting only a message.
     */
    public void testConstructorMessage() {
        BuildScriptGeneratorException e = new BuildScriptGeneratorException("foobar");
        assertEquals("Message is incorrect", "foobar", e.getMessage());
    }

    /**
     * Verify behavior of the constructor accepting both a message and a cause.
     */
    public void testConstructorMessageCause() {
        Throwable cause = new Throwable("cause");
        BuildScriptGeneratorException e = new BuildScriptGeneratorException("foobar", cause);
        assertSame("Cause is incorrect", cause, e.getCause());
        assertTrue("Message is incorrect", e.getMessage().startsWith("foobar"));
    }
}
