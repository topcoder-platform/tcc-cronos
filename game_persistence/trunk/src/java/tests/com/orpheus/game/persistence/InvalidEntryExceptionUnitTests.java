/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;


import junit.framework.TestCase;


/**
 * Unit test for <code>InvalidEntryException</code> exception.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InvalidEntryExceptionUnitTests extends TestCase {
    /**
     * Accuracy test for inheritance. <code>InvalidEntryException</code> class should extend from the
     * <code>PersistenceException</code> class.
     */
    public void testInheritance() {
        assertTrue("InvalidEntryException class should extend from the GameDataException class.",
            PersistenceException.class.isAssignableFrom(InvalidEntryException.class));
    }

    /**
     * Test the constructor with error message and identifier, <code>InvalidEntryException</code> instance should be
     * created successfully.
     */
    public void testCtorWithMsgAndObject() {
        Object identifier = new Object();
        InvalidEntryException excp = new InvalidEntryException("Failed", identifier);
        assertNotNull("InvalidEntryException instance should be created.", excp);
        assertTrue("Message field should be set correctly.", excp.getMessage().equals("Failed"));
        assertEquals("The identifier is the same one.", identifier, excp.getIdentifier());
    }

    /**
     * Test the getIdentifier. Simply verify the result.
     */
    public void testGetIdentifier() {
        Object identifier = new Object();
        InvalidEntryException excp = new InvalidEntryException("Failed", identifier);
        assertEquals("The identifier is the same one.", identifier, excp.getIdentifier());
    }
}
