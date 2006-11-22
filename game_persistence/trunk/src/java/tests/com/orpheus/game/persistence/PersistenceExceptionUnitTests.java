/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;


import com.orpheus.game.GameDataException;

import junit.framework.TestCase;

/**
 * Unit test for <code>PersistenceException</code> exception.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceExceptionUnitTests extends TestCase {

    /**
     * Accuracy test for inheritance. <code>PersistenceException</code> class should extend from the
     * <code>GameDataException</code> class.
     */
    public void testInheritance() {
        assertTrue("PersistenceException class should extend from the GameDataException class.",
            GameDataException.class.isAssignableFrom(PersistenceException.class));
    }


    /**
     * Test the constructor with error message, <code>PersistenceException</code> instance should be created
     * successfully.
     */
    public void testConstructorWithMessage() {
        PersistenceException excp = new PersistenceException("Failed");
        assertNotNull("PersistenceException instance should be created.", excp);
        assertTrue("Message field should be set correctly.", excp.getMessage().equals("Failed"));
    }

    /**
     * Test the constructor with error message and inner cause, <code>PersistenceException</code> instance
     * should be created successfully.
     */
    public void testConstructorWithMessageAndCause() {
        Exception inner = new Exception();
        PersistenceException excp = new PersistenceException("Failed", inner);
        assertNotNull("PersistenceException instance should be created.", excp);
        assertTrue("Message field should be set correctly.", excp.getMessage().startsWith("Failed"));
        assertTrue("Cause field should be set correctly.", excp.getCause().equals(inner));
    }

}
