package com.topcoder.service.facade.direct.accuracytests;
/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

import junit.framework.TestCase;

import com.topcoder.service.facade.direct.ContestScheduleExtension;
/**
 * This class contains unit tests for <code>ContestScheduleExtension</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestContestScheduleExtension extends TestCase {
    /**
     * <p>
     * Represents ContestScheduleExtension instance to test against.
     * </p>
     */
    private ContestScheduleExtension instance = null;

    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestScheduleExtension();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#ContestScheduleExtension()}. It verifies the new instance is created.
     * </p>
     */
    public void testContestScheduleExtension() {
        assertNotNull("Unable to instantiate ContestScheduleExtension", instance);
    }
    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#getContestId()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestId() {
        assertEquals("Incorrect default value for contestId", 0, instance.getContestId());

        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());
    }

    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#setContestId(long)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestId() {
        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());

    }
    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#isStudio()}. It verifies the returned value is correct.
     * </p>
     */
    public void testisStudio() {
        assertFalse("Incorrect default value for studio", instance.isStudio());

        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());
    }

    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#setStudio(boolean)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStudio() {
        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());

    }
    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#getExtendRegistrationBy()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetExtendRegistrationBy() {
        assertNull("Incorrect default value for extendRegistrationBy", instance.getExtendRegistrationBy());

        // set a value
        instance.setExtendRegistrationBy(new Integer(666666));
        assertEquals("Incorrect extendRegistrationBy", new Integer(666666), instance.getExtendRegistrationBy());
    }

    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#setExtendRegistrationBy(Integer)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetExtendRegistrationBy() {
        // set a value
        instance.setExtendRegistrationBy(new Integer(666666));
        assertEquals("Incorrect extendRegistrationBy", new Integer(666666), instance.getExtendRegistrationBy());

        // set to null
        instance.setExtendRegistrationBy(null);
        assertNull("Incorrect extendRegistrationBy after set to null", instance.getExtendRegistrationBy());
    }
    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#getExtendMilestoneBy()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetExtendMilestoneBy() {
        assertNull("Incorrect default value for extendMilestoneBy", instance.getExtendMilestoneBy());

        // set a value
        instance.setExtendMilestoneBy(new Integer(666666));
        assertEquals("Incorrect extendMilestoneBy", new Integer(666666), instance.getExtendMilestoneBy());
    }

    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#setExtendMilestoneBy(Integer)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetExtendMilestoneBy() {
        // set a value
        instance.setExtendMilestoneBy(new Integer(666666));
        assertEquals("Incorrect extendMilestoneBy", new Integer(666666), instance.getExtendMilestoneBy());

        // set to null
        instance.setExtendMilestoneBy(null);
        assertNull("Incorrect extendMilestoneBy after set to null", instance.getExtendMilestoneBy());
    }
    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#getExtendSubmissionBy()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetExtendSubmissionBy() {
        assertNull("Incorrect default value for extendSubmissionBy", instance.getExtendSubmissionBy());

        // set a value
        instance.setExtendSubmissionBy(new Integer(666666));
        assertEquals("Incorrect extendSubmissionBy", new Integer(666666), instance.getExtendSubmissionBy());
    }

    /**
     * <p>
     * Test method for {@link ContestScheduleExtension#setExtendSubmissionBy(Integer)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetExtendSubmissionBy() {
        // set a value
        instance.setExtendSubmissionBy(new Integer(666666));
        assertEquals("Incorrect extendSubmissionBy", new Integer(666666), instance.getExtendSubmissionBy());

        // set to null
        instance.setExtendSubmissionBy(null);
        assertNull("Incorrect extendSubmissionBy after set to null", instance.getExtendSubmissionBy());
    }

}