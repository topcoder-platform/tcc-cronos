/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import com.topcoder.predictor.impl.componentcompetition.Technology;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link Technology}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class TechnologyAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>Technology</code> used in tests.
     * </p>
     */
    private Technology instance = null;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        instance = new Technology();
    }

    /**
     * <p>
     * Tests accuracy of <code>Technology()</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor_A() {
        assertNull("Name should be correct.", instance.getName());
        assertNull("Description should be correct.", instance.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>getName()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetName() {
        instance.setName("new_value");

        assertEquals("'getName' should be correct.", "new_value", instance.getName());
    }

    /**
     * <p>
     * Tests accuracy of <code>setName(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetName1() {
        instance.setName("new_value");

        assertEquals("'setName' should be correct.", "new_value", instance.getName());
    }

    /**
     * <p>
     * Tests accuracy of <code>setName(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetName2() {
        instance.setName(null);

        assertNull("'setName' should be correct.", instance.getName());
    }

    /**
     * <p>
     * Tests accuracy of <code>getDescription()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetDescription() {
        instance.setDescription("new_value");

        assertEquals("'getDescription' should be correct.", "new_value", instance.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDescription(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDescription1() {
        instance.setDescription("new_value");

        assertEquals("'setDescription' should be correct.", "new_value", instance.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>setDescription(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetDescription2() {
        instance.setDescription(null);

        assertNull("'setDescription' should be correct.", instance.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone1() {
        Technology cloneObj = (Technology) instance.clone();

        assertNull("'clone' should be correct.", cloneObj.getName());
        assertNull("'clone' should be correct.", cloneObj.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone2() {
        instance.setName("name");
        instance.setDescription("description");

        Technology cloneObj = (Technology) instance.clone();

        assertEquals("'clone' should be correct.", "name", cloneObj.getName());
        assertEquals("'clone' should be correct.", "description", cloneObj.getDescription());
    }
}
