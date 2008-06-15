/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>DependencyCategory</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DependencyCategoryTest extends TestCase {
    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Gets the test suite for <code>DependencyCategory</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>DependencyCategory</code> class.
     */
    public static Test suite() {
        return new TestSuite(DependencyCategoryTest.class);
    }

    /**
     * <p>
     * Test to see if there are 2 enum types there.
     * </p>
     */
    public void testEnumNumber() {
        assertEquals("it should have 2 enum values.", 2, DependencyCategory.values().length);
    }

    /**
     * <p>
     * Test to see if there is an enum value for compile.
     * </p>
     */
    public void testEnumHascompile() {
        assertNotNull("enum has compile value.", DependencyCategory.valueOf("COMPILE"));
    }

    /**
     * <p>
     * Test to see if there is an enum value for test.
     * </p>
     */
    public void testEnumHasDotNet() {
        assertNotNull("enum has test value.", DependencyCategory.valueOf("TEST"));
    }
}
