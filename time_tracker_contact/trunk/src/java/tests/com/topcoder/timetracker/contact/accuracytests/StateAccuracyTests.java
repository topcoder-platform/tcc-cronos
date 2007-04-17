/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.contact.State;

/**
 * <p>
 * Accuracy Unit test cases for State.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class StateAccuracyTests extends TestCase {
    /**
     * <p>
     * State instance for testing.
     * </p>
     */
    private State instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new State();
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
        return new TestSuite(StateAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor State#State() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create State instance.", instance);
    }

    /**
     * <p>
     * Tests State#getAbbreviation() for accuracy.
     * </p>
     */
    public void testGetAbbreviation() {
        instance.setAbbreviation("abbreviation");
        assertEquals("Failed to get the abbreviation.", "abbreviation", instance.getAbbreviation());
    }

    /**
     * <p>
     * Tests State#setAbbreviation(String) for accuracy.
     * </p>
     */
    public void testSetAbbreviation() {
        instance.setAbbreviation("abbreviation");
        assertEquals("Failed to set the abbreviation.", "abbreviation", instance.getAbbreviation());
    }

    /**
     * <p>
     * Tests State#getName() for accuracy.
     * </p>
     */
    public void testGetName() {
        instance.setName("name");
        assertEquals("Failed to get the name.", "name", instance.getName());
    }

    /**
     * <p>
     * Tests State#setName(String) for accuracy.
     * </p>
     */
    public void testSetName() {
        instance.setName("name");
        assertEquals("Failed to set the name.", "name", instance.getName());
    }

}