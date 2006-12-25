/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;


import junit.framework.TestCase;


/**
 * Unit test for <code>EntryNotFoundException</code> exception.
 *
 * @author waits
 * @version 1.0
 */
public class EntryNotFoundExceptionUnitTests extends TestCase {
    /**
     * Accuracy test for inheritance. <code>EntryNotFoundException</code> class should extend from the
     * <code>PersistenceException</code> class.
     */
    public void testInheritance() {
        assertTrue("EntryNotFoundException class should extend from the GameDataException class.",
            PersistenceException.class.isAssignableFrom(EntryNotFoundException.class));
    }

    /**
     * Test the constructor with error message and identifier, <code>EntryNotFoundException</code> instance should be
     * created successfully.
     */
    public void testCtorWithMsgAndObject() {
        Object identifier = new Object();
        EntryNotFoundException excp = new EntryNotFoundException("Failed", identifier);
        assertNotNull("EntryNotFoundException instance should be created.", excp);
        assertTrue("Message field should be set correctly.", excp.getMessage().equals("Failed"));
        assertEquals("The identifier is the same one.", identifier, excp.getIdentifier());
    }

    /**
     * Test the getIdentifier. Simply verify the result.
     */
    public void testGetIdentifier() {
        Object identifier = new Object();
        EntryNotFoundException excp = new EntryNotFoundException("Failed", identifier);
        assertEquals("The identifier is the same one.", identifier, excp.getIdentifier());
    }
}
