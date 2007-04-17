/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for SqlType.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class SqlTypeTests extends TestCase {
    /**
     * <p>
     * The SqlType instance for testing.
     * </p>
     */
    private SqlType type;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        type = new SqlType(8);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        type = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SqlTypeTests.class);
    }

    /**
     * <p>
     * Tests ctor SqlType#SqlType(int) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created SqlType instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new SqlType instance.", type);
    }

    /**
     * <p>
     * Tests SqlType#getType() for accuracy.
     * </p>
     *
     * <p>
     * It verifies SqlType#getType() is correct.
     * </p>
     */
    public void testGetType() {
        assertEquals("Failed to get the type correctly.", 8, type.getType());
    }

}