/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for Technology class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TechnologyUnitTests extends TestCase {

    /**
     * An instance of Technology for the following tests.
     */
    private Technology tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new Technology();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        tester = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor. No exception is thrown.
     * </p>
     */
    public void test_ctor() {
        // no exception
        new Technology();
    }

    /**
     * <p>
     * Accuracy test for the name property. It should be got/set properly.
     * </p>
     */
    public void test_name() {
        assertNull("Initial value should be null.", tester.getName());
        tester.setName("name");
        assertEquals("The name is not got/set properly.", "name", tester.getName());
    }

    /**
     * <p>
     * Accuracy test for the description property. It should be got/set properly.
     * </p>
     */
    public void test_description() {
        assertNull("Initial value should be null.", tester.getDescription());
        tester.setDescription("desc");
        assertEquals("The description is not got/set properly.", "desc", tester.getDescription());
    }

    /**
     * <p>
     * Accuracy test for the clone method, the cloned object should be same as the original object.
     * </p>
     */
    public void test_clone() {
        tester.setName("name");
        tester.setDescription("desc");
        Object obj = tester.clone();
        assertTrue("Cloned object should be type of Technology.", obj instanceof Technology);
        Technology t = (Technology) obj;
        assertEquals("The name is incorrect.", "name", t.getName());
        assertEquals("The description is incorrect.", "desc", t.getDescription());
    }

}
