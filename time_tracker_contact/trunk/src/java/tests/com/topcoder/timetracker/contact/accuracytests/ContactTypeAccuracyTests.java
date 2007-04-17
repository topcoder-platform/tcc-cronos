/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.contact.ContactType;

/**
 * <p>
 * Accuracy Unit test cases for ContactType.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ContactTypeAccuracyTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ContactTypeAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ContactType#toString() for accuracy.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string.", "CLIENT", ContactType.CLIENT.toString());
    }

    /**
     * <p>
     * Tests ContactType#getId() for accuracy.
     * </p>
     */
    public void testGetId() {
        assertEquals("Failed to get id.", 2, ContactType.CLIENT.getId());
    }

}