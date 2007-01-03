/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.State;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>State</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestStateAccuracy extends TestCase {

    /**
     * Represents the State instance for test.
     */
    private static State target = new State();

    /**
     * Test constructor.
     *
     */
    public void testState() {
        assertNotNull("The State instance should be created.", target);
    }

    /**
     * test getName
     *
     */
    public void testGetName() {
        assertNull("Should be null.", target.getName());
    }

    /**
     * Test setName.
     *
     */
    public void testSetName() {
        target.setName("name");

        assertEquals("Equal is expected.", "name", target.getName());
    }

    /**
     * Test setName.
     *
     */
    public void testSetName_2() {
        target.setName("name");

        assertEquals("Equal is expected.", "name", target.getName());

        target.setChanged(false);

        target.setName("name 2");

        assertTrue("True is expected.", target.isChanged());
    }

    /**
     * Test method getAbbreviation
     *
     */
    public void testGetAbbreviation() {
        assertNull("Should be null.", target.getAbbreviation());
    }

    /**
     * Test method setAbbreviation.
     *
     */
    public void testSetAbbreviation() {
        target.setAbbreviation("abb");

        assertEquals("Equal is expected.", "abb", target.getAbbreviation());
    }

    /**
     * Test method setAbbreviation.
     *
     */
    public void testSetAbbreviation_2() {
        target.setAbbreviation("abb");

        assertEquals("Equal is expected.", "abb", target.getAbbreviation());

        target.setChanged(false);

        target.setAbbreviation("ab");

        assertTrue("True is expected.", target.isChanged());
    }
}
