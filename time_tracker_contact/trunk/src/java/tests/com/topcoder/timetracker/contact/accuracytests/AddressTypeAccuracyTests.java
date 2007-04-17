/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.contact.AddressType;

/**
 * <p>
 * Accuracy Unit test cases for AddressType.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class AddressTypeAccuracyTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AddressTypeAccuracyTests.class);
    }

    /**
     * <p>
     * Tests AddressType#toString() for accuracy.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string.", "CLIENT", AddressType.CLIENT.toString());
    }

    /**
     * <p>
     * Tests AddressType#getId() for accuracy.
     * </p>
     */
    public void testGetId() {
        assertEquals("Failed to get id.", 2, AddressType.CLIENT.getId());
    }

}