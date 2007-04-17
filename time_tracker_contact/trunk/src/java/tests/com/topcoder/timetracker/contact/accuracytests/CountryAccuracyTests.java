/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.contact.Country;

/**
 * <p>
 * Accuracy Unit test cases for Country.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class CountryAccuracyTests extends TestCase {
    /**
     * <p>
     * Country instance for testing.
     * </p>
     */
    private Country instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new Country();
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
        return new TestSuite(CountryAccuracyTests.class);
    }
    /**
     * <p>
     * Tests ctor Country#Country() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create Country instance.", instance);
    }


    /**
     * <p>
     * Tests Country#getName() for accuracy.
     * </p>
     */
    public void testGetName() {
        instance.setName("name");
        assertEquals("Failed to get the name.", "name", instance.getName());
    }

    /**
     * <p>
     * Tests Country#setName(String) for accuracy.
     * </p>
     */
    public void testSetName() {
        instance.setName("name");
        assertEquals("Failed to set the name.", "name", instance.getName());
    }

}