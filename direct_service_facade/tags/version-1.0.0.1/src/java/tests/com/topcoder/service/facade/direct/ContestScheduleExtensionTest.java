/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>ContestScheduleExtension</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestScheduleExtensionTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ContestScheduleExtension</code> instance for test.
     * </p>
     */
    private ContestScheduleExtension extension;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        extension = new ContestScheduleExtension();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestScheduleExtension</code>, expects the instance is created
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the ContestScheduleExtension instance.", extension);
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestId</code> method, expects the contestId is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestIdAccuracy() throws Exception {
        assertEquals("Expects the contestId is 0 by default.", 0, extension.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestId</code> method, expects the contestId is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestIdAccuracy() throws Exception {
        extension.setContestId(1);
        assertEquals("Expects the contestId is set properly.", 1, extension.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>isStudio</code> method, expects the studio is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testIsStudioAccuracy() throws Exception {
        assertFalse("Expects the studio is false by default.", extension.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>setStudio</code> method, expects the studio is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStudioAccuracy() throws Exception {
        extension.setStudio(true);
        assertTrue("Expects the studio is set properly.", extension.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getExtendRegistrationBy</code> method, expects the extendRegistrationBy is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExtendRegistrationByAccuracy() throws Exception {
        assertNull("Expects the extendRegistrationBy is null by default.", extension.getExtendRegistrationBy());
    }

    /**
     * <p>
     * Accuracy test for the <code>setExtendRegistrationBy</code> method, expects the extendRegistrationBy is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetExtendRegistrationByAccuracy() throws Exception {
        extension.setExtendRegistrationBy(1);
        assertEquals("Expects the extendRegistrationBy is set properly.", 1, extension.getExtendRegistrationBy()
                .intValue());
    }

    /**
     * <p>
     * Accuracy test for the <code>getExtendMilestoneBy</code> method, expects the extendMilestoneBy is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExtendMilestoneByAccuracy() throws Exception {
        assertNull("Expects the extendMilestoneBy is null by default.", extension.getExtendMilestoneBy());
    }

    /**
     * <p>
     * Accuracy test for the <code>setExtendMilestoneBy</code> method, expects the extendMilestoneBy is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetExtendMilestoneByAccuracy() throws Exception {
        extension.setExtendMilestoneBy(1);
        assertEquals("Expects the extendMilestoneBy is set properly.", 1, extension.getExtendMilestoneBy().intValue());
    }

    /**
     * <p>
     * Accuracy test for the <code>getExtendSubmissionBy</code> method, expects the extendSubmissionBy is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetExtendSubmissionByAccuracy() throws Exception {
        assertNull("Expects the extendSubmissionBy is null by default.", extension.getExtendSubmissionBy());
    }

    /**
     * <p>
     * Accuracy test for the <code>setExtendSubmissionBy</code> method, expects the extendSubmissionBy is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetExtendSubmissionByAccuracy() throws Exception {
        extension.setExtendSubmissionBy(1);
        assertEquals("Expects the extendSubmissionBy is set properly.", 1, extension.getExtendSubmissionBy()
                .intValue());
    }
}
