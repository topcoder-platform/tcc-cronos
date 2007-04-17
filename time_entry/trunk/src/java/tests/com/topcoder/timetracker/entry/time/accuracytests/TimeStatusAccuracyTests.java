/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.entry.time.TimeStatus;

/**
 * <p>
 * Accuracy Unit test cases for TimeStatus.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TimeStatusAccuracyTests extends TestCase {
    /**
     * <p>
     * TimeStatus instance for testing.
     * </p>
     */
    private TimeStatus instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new TimeStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TimeStatusAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeStatus#TimeStatus() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TimeStatus instance.", instance);
    }

    /**
     * <p>
     * Tests TimeStatus#getDescription() for accuracy.
     * </p>
     */
    public void testGetDescription() {
        instance.setDescription("description");
        assertEquals("Failed to get the description.", "description", instance.getDescription());
    }

    /**
     * <p>
     * Tests TimeStatus#setDescription(String) for accuracy.
     * </p>
     */
    public void testSetDescription() {
        instance.setDescription("description");
        assertEquals("Failed to set the description.", "description", instance.getDescription());
    }

}