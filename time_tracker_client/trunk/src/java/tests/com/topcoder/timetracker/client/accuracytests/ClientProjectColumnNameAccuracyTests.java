/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.db.ClientProjectColumnName;

/**
 * <p>
 * Accuracy Unit test cases for ClientProjectColumnName.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientProjectColumnNameAccuracyTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ClientProjectColumnNameAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ClientProjectColumnName#getName() for accuracy.
     * </p>
     */
    public void testGetName() {
        assertEquals("Failed to get name.", "CREATION_USER", ClientProjectColumnName.CREATION_USER.getName());
    }

    /**
     * <p>
     * Tests ClientProjectColumnName#toString() for accuracy.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string.", "CREATION_USER", ClientProjectColumnName.CREATION_USER.toString());
    }

}