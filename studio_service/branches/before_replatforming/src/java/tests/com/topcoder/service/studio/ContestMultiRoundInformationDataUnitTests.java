/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.util.Date;

import junit.framework.TestCase;

/**
 * This class contains unit tests for
 * <code>ContestMultiRoundInformationData</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestMultiRoundInformationDataUnitTests extends TestCase {
    /**
     * <p>
     * Represents ContestMultiRoundInformationData instance to test against.
     * </p>
     */
    private ContestMultiRoundInformationData instance = null;

    /**
     * Set Up the test environment before testing.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestMultiRoundInformationData();
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
     * {@link ContestMultiRoundInformationData#ContestMultiRoundInformationData()}.
     * It verifies the new instance is created.
     * </p>
     */
    public void testContestMultiRoundInformationData() {
        assertNotNull("Unable to instantiate ContestMultiRoundInformationData", instance);
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#getMilestoneDate()}. It verifies
     * the returned value is correct.
     * </p>
     */
    public void testGetMilestoneDate() {
        assertNull("Incorrect default value for milestoneDate", instance.getMilestoneDate());

        Date now = new Date();
        // set a value
        instance.setMilestoneDate(now);
        assertEquals("Incorrect milestoneDate", now, instance.getMilestoneDate());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#setMilestoneDate(Date)}. It
     * verifies the assigned value is correct.
     * </p>
     */
    public void testSetMilestoneDate() {
        Date now = new Date();

        // set a value
        instance.setMilestoneDate(now);
        assertEquals("Incorrect milestoneDate", now, instance.getMilestoneDate());

        // set to null
        instance.setMilestoneDate(null);
        assertNull("Incorrect milestoneDate after set to null", instance.getMilestoneDate());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#getSubmittersLockedBetweenRounds()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetSubmittersLockedBetweenRounds() {
        assertNull("Incorrect default value for submittersLockedBetweenRounds", instance
                .getSubmittersLockedBetweenRounds());

        // set a value
        instance.setSubmittersLockedBetweenRounds(true);
        assertTrue("Incorrect submittersLockedBetweenRounds", instance.getSubmittersLockedBetweenRounds());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#setSubmittersLockedBetweenRounds(Boolean)}.
     * It verifies the assigned value is correct.
     * </p>
     */
    public void testSetSubmittersLockedBetweenRounds() {
        // set a value
        instance.setSubmittersLockedBetweenRounds(true);
        assertTrue("Incorrect submittersLockedBetweenRounds", instance.getSubmittersLockedBetweenRounds());

        // set to null
        instance.setSubmittersLockedBetweenRounds(null);
        assertNull("Incorrect submittersLockedBetweenRounds after set to null", instance
                .getSubmittersLockedBetweenRounds());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#getRoundOneIntroduction()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetRoundOneIntroduction() {
        assertNull("Incorrect default value for roundOneIntroduction", instance.getRoundOneIntroduction());

        // set a value
        instance.setRoundOneIntroduction("myString");
        assertEquals("Incorrect roundOneIntroduction", "myString", instance.getRoundOneIntroduction());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#setRoundOneIntroduction(String)}.
     * It verifies the assigned value is correct.
     * </p>
     */
    public void testSetRoundOneIntroduction() {
        // set a value
        instance.setRoundOneIntroduction("myString");
        assertEquals("Incorrect roundOneIntroduction", "myString", instance.getRoundOneIntroduction());

        // set to null
        instance.setRoundOneIntroduction(null);
        assertNull("Incorrect roundOneIntroduction after set to null", instance.getRoundOneIntroduction());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#getRoundTwoIntroduction()}. It
     * verifies the returned value is correct.
     * </p>
     */
    public void testGetRoundTwoIntroduction() {
        assertNull("Incorrect default value for roundTwoIntroduction", instance.getRoundTwoIntroduction());

        // set a value
        instance.setRoundTwoIntroduction("myString");
        assertEquals("Incorrect roundTwoIntroduction", "myString", instance.getRoundTwoIntroduction());
    }

    /**
     * <p>
     * Test method for
     * {@link ContestMultiRoundInformationData#setRoundTwoIntroduction(String)}.
     * It verifies the assigned value is correct.
     * </p>
     */
    public void testSetRoundTwoIntroduction() {
        // set a value
        instance.setRoundTwoIntroduction("myString");
        assertEquals("Incorrect roundTwoIntroduction", "myString", instance.getRoundTwoIntroduction());

        // set to null
        instance.setRoundTwoIntroduction(null);
        assertNull("Incorrect roundTwoIntroduction after set to null", instance.getRoundTwoIntroduction());
    }

}
