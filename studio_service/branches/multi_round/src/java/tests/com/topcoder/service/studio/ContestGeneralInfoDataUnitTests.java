/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>ContestGeneralInfoData</code>
 * class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestGeneralInfoDataUnitTests extends TestCase {
    /**
     * <p>
     * Represents ContestGeneralInfoData instance to test against.
     * </p>
     */
    private ContestGeneralInfoData instance = null;

    /**
     * Set Up the test environment before testing.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestGeneralInfoData();
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
     * Test method for {@link ContestGeneralInfoData#ContestGeneralInfoData()}.
     * It verifies the new instance is created.
     * </p>
     */
    public void testContestGeneralInfoData() {
        assertNotNull("Unable to instantiate ContestGeneralInfoData", instance);
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#getGoals()}. It verifies
     * the returned value is correct.
     * </p>
     */
    public void testGetGoals() {
        assertNull("Incorrect default value for goals", instance.getGoals());

        // set a value
        instance.setGoals("myString");
        assertEquals("Incorrect goals", "myString", instance.getGoals());
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#setGoals(String)}. It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetGoals() {
        // set a value
        instance.setGoals("myString");
        assertEquals("Incorrect goals", "myString", instance.getGoals());

        // set to null
        instance.setGoals(null);
        assertNull("Incorrect goals after set to null", instance.getGoals());
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#getTargetAudience()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetTargetAudience() {
        assertNull("Incorrect default value for targetAudience", instance.getTargetAudience());

        // set a value
        instance.setTargetAudience("myString");
        assertEquals("Incorrect targetAudience", "myString", instance.getTargetAudience());
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#setTargetAudience(String)}.
     * It verifies the assigned value is correct.
     * </p>
     */
    public void testSetTargetAudience() {
        // set a value
        instance.setTargetAudience("myString");
        assertEquals("Incorrect targetAudience", "myString", instance.getTargetAudience());

        // set to null
        instance.setTargetAudience(null);
        assertNull("Incorrect targetAudience after set to null", instance.getTargetAudience());
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#getBrandingGuidelines()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetBrandingGuidelines() {
        assertNull("Incorrect default value for brandingGuidelines", instance.getBrandingGuidelines());

        // set a value
        instance.setBrandingGuidelines("myString");
        assertEquals("Incorrect brandingGuidelines", "myString", instance.getBrandingGuidelines());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestGeneralInfoData#setBrandingGuidelines(String)}. It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetBrandingGuidelines() {
        // set a value
        instance.setBrandingGuidelines("myString");
        assertEquals("Incorrect brandingGuidelines", "myString", instance.getBrandingGuidelines());

        // set to null
        instance.setBrandingGuidelines(null);
        assertNull("Incorrect brandingGuidelines after set to null", instance.getBrandingGuidelines());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestGeneralInfoData#getDislikedDesignsWebsites()}. It verifies
     * the returned value is correct.
     * </p>
     */
    public void testGetDislikedDesignsWebsites() {
        assertNull("Incorrect default value for dislikedDesignsWebsites", instance
                .getDislikedDesignsWebsites());

        // set a value
        instance.setDislikedDesignsWebsites("myString");
        assertEquals("Incorrect dislikedDesignsWebsites", "myString", instance.getDislikedDesignsWebsites());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestGeneralInfoData#setDislikedDesignsWebsites(String)}. It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetDislikedDesignsWebsites() {
        // set a value
        instance.setDislikedDesignsWebsites("myString");
        assertEquals("Incorrect dislikedDesignsWebsites", "myString", instance.getDislikedDesignsWebsites());

        // set to null
        instance.setDislikedDesignsWebsites(null);
        assertNull("Incorrect dislikedDesignsWebsites after set to null", instance
                .getDislikedDesignsWebsites());
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#getOtherInstructions()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetOtherInstructions() {
        assertNull("Incorrect default value for otherInstructions", instance.getOtherInstructions());

        // set a value
        instance.setOtherInstructions("myString");
        assertEquals("Incorrect otherInstructions", "myString", instance.getOtherInstructions());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestGeneralInfoData#setOtherInstructions(String)}. It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testSetOtherInstructions() {
        // set a value
        instance.setOtherInstructions("myString");
        assertEquals("Incorrect otherInstructions", "myString", instance.getOtherInstructions());

        // set to null
        instance.setOtherInstructions(null);
        assertNull("Incorrect otherInstructions after set to null", instance.getOtherInstructions());
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#getWinningCriteria()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetWinningCriteria() {
        assertNull("Incorrect default value for winningCriteria", instance.getWinningCriteria());

        // set a value
        instance.setWinningCriteria("myString");
        assertEquals("Incorrect winningCriteria", "myString", instance.getWinningCriteria());
    }

    /**
     * <p>
     * Test method for {@link ContestGeneralInfoData#setWinningCriteria(String)}.
     * It verifies the assigned value is correct.
     * </p>
     */
    public void testSetWinningCriteria() {
        // set a value
        instance.setWinningCriteria("myString");
        assertEquals("Incorrect winningCriteria", "myString", instance.getWinningCriteria());

        // set to null
        instance.setWinningCriteria(null);
        assertNull("Incorrect winningCriteria after set to null", instance.getWinningCriteria());
    }

}
