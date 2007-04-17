/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.Depth;

/**
 * <p>
 * Accuracy Unit test cases for Depth.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DepthAccuracyTests extends TestCase {
    /**
     * <p>
     * Depth instance for testing.
     * </p>
     */
    private Depth instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);

        String[] fields = new String[] {"one"};
        instance = new MockDepth(fields, true, true, true, true);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DepthAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor Depth#Depth(String[],boolean,boolean,boolean,boolean) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create Depth instance.", instance);
    }

    /**
     * <p>
     * Tests Depth#onlyProjectsIdName() for accuracy.
     * </p>
     */
    public void testOnlyProjectsIdName() {
        assertTrue("Failed to return the value.", instance.onlyProjectsIdName());
    }

    /**
     * <p>
     * Tests Depth#useAddress() for accuracy.
     * </p>
     */
    public void testUseAddress() {
        assertTrue("Failed to return the value.", instance.useAddress());
    }

    /**
     * <p>
     * Tests Depth#useContact() for accuracy.
     * </p>
     */
    public void testUseContact() {
        assertTrue("Failed to return the value.", instance.useContact());
    }

    /**
     * <p>
     * Tests Depth#useProjects() for accuracy.
     * </p>
     */
    public void testUseProjects() {
        assertTrue("Failed to return the value.", instance.useProjects());
    }

    /**
     * <p>
     * Tests Depth#getFields() for accuracy.
     * </p>
     */
    public void testGetFields() {
        assertEquals("Failed to get the fields.", "one", instance.getFields().get(0));
    }

}