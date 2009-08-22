/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>ContestSpecificationsData</code>
 * class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestSpecificationsDataUnitTests extends TestCase {
    /**
     * <p>
     * Represents ContestSpecificationsData instance to test against.
     * </p>
     */
    private ContestSpecificationsData instance = null;

    /**
     * Set Up the test environment before testing.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestSpecificationsData();
    }

    /**
     * Clean up the test environment after testing.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Test method for
     * {@link ContestSpecificationsData#ContestSpecificationsData()}. It
     * verifies the new instance is created.
     * </p>
     */
    public void testContestSpecificationsData() {
        assertNotNull("Unable to instantiate ContestSpecificationsData", instance);
    }

    /**
     * <p>
     * Test method for {@link ContestSpecificationsData#getColors()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetColors() {
        assertNull("Incorrect default value for colors", instance.getColors());

        // set a value
        instance.setColors("myString");
        assertEquals("Incorrect colors", "myString", instance.getColors());
    }

    /**
     * <p>
     * Test method for {@link ContestSpecificationsData#setColors(String)}. It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetColors() {
        // set a value
        instance.setColors("myString");
        assertEquals("Incorrect colors", "myString", instance.getColors());

        // set to null
        instance.setColors(null);
        assertNull("Incorrect colors after set to null", instance.getColors());
    }

    /**
     * <p>
     * Test method for {@link ContestSpecificationsData#getFonts()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetFonts() {
        assertNull("Incorrect default value for fonts", instance.getFonts());

        // set a value
        instance.setFonts("myString");
        assertEquals("Incorrect fonts", "myString", instance.getFonts());
    }

    /**
     * <p>
     * Test method for {@link ContestSpecificationsData#setFonts(String)}. It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetFonts() {
        // set a value
        instance.setFonts("myString");
        assertEquals("Incorrect fonts", "myString", instance.getFonts());

        // set to null
        instance.setFonts(null);
        assertNull("Incorrect fonts after set to null", instance.getFonts());
    }

    /**
     * <p>
     * Test method for {@link ContestSpecificationsData#getLayoutAndSize()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetLayoutAndSize() {
        assertNull("Incorrect default value for layoutAndSize", instance.getLayoutAndSize());

        // set a value
        instance.setLayoutAndSize("myString");
        assertEquals("Incorrect layoutAndSize", "myString", instance.getLayoutAndSize());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestSpecificationsData#setLayoutAndSize(String)}. It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testSetLayoutAndSize() {
        // set a value
        instance.setLayoutAndSize("myString");
        assertEquals("Incorrect layoutAndSize", "myString", instance.getLayoutAndSize());

        // set to null
        instance.setLayoutAndSize(null);
        assertNull("Incorrect layoutAndSize after set to null", instance.getLayoutAndSize());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestSpecificationsData#getAdditionalRequirementsAndRestrictions()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetAdditionalRequirementsAndRestrictions() {
        assertNull("Incorrect default value for additionalRequirementsAndRestrictions", instance
                .getAdditionalRequirementsAndRestrictions());

        // set a value
        instance.setAdditionalRequirementsAndRestrictions("myString");
        assertEquals("Incorrect additionalRequirementsAndRestrictions", "myString", instance
                .getAdditionalRequirementsAndRestrictions());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestSpecificationsData#setAdditionalRequirementsAndRestrictions(String)}.
     * It verifies the assigned value is correct.
     * </p>
     */
    public void testSetAdditionalRequirementsAndRestrictions() {
        // set a value
        instance.setAdditionalRequirementsAndRestrictions("myString");
        assertEquals("Incorrect additionalRequirementsAndRestrictions", "myString", instance
                .getAdditionalRequirementsAndRestrictions());

        // set to null
        instance.setAdditionalRequirementsAndRestrictions(null);
        assertNull("Incorrect additionalRequirementsAndRestrictions after set to null", instance
                .getAdditionalRequirementsAndRestrictions());
    }

}
