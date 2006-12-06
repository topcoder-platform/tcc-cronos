/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;


import com.orpheus.game.GameDataException;

import junit.framework.TestCase;

/**
 * Unit test for <code>InstantiationException</code> exception.
 *
 * @author waits
 * @version 1.0
 */
public class InstantiationExceptionUnitTests extends TestCase {

	/**
     * Accuracy test for inheritance. <code>InstantiationException</code> class should extend from the
     * <code>GameDataException</code> class.
     */
    public void testInheritance() {
        assertTrue("InstantiationException class should extend from the GameDataException class.",
            GameDataException.class.isAssignableFrom(InstantiationException.class));
    }


    /**
     * Test the constructor with error message, <code>InstantiationException</code> instance should be created
     * successfully.
     */
    public void testConstructorWithMessage() {
        InstantiationException excp = new InstantiationException("Failed");
        assertNotNull("InstantiationException instance should be created.", excp);
        assertTrue("Message field should be set correctly.", excp.getMessage().equals("Failed"));
    }

    /**
     * Test the constructor with error message and inner cause, <code>InstantiationException</code> instance
     * should be created successfully.
     */
    public void testConstructorWithMessageAndCause() {
        Exception inner = new Exception();
        InstantiationException excp = new InstantiationException("Failed", inner);
        assertNotNull("InstantiationException instance should be created.", excp);
        assertTrue("Message field should be set correctly.", excp.getMessage().startsWith("Failed"));
        assertTrue("Cause field should be set correctly.", excp.getCause().equals(inner));
    }

}
