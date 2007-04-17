/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.db.ClientColumnName;

/**
 * <p>
 * Accuracy Unit test cases for ClientColumnName.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientColumnNameAccuracyTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ClientColumnNameAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ClientColumnName#getName() for accuracy.
     * </p>
     */
    public void testGetName() {
        assertEquals("Failed to get the name.", "ACTIVE", ClientColumnName.ACTIVE.getName());
    }

    /**
     * <p>
     * Tests ClientColumnName#toString() for accuracy.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string.", "ACTIVE", ClientColumnName.ACTIVE.toString());
    }

}