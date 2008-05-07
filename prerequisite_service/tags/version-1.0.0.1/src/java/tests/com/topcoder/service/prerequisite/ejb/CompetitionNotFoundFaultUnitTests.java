/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link CompetitionNotFoundFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionNotFoundFaultUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>CompetitionNotFoundFault</code> instance used in tests.
     * </p>
     */
    private CompetitionNotFoundFault competitionNotFoundFault;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CompetitionNotFoundFaultUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        competitionNotFoundFault = new CompetitionNotFoundFault();
    }

    /**
     * <p>
     * Unit test for <code>CompetitionNotFoundFault#CompetitionNotFoundFault()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testCompetitionNotFoundFault_accuracy() {
        assertNotNull("Instance should be always created.", competitionNotFoundFault);
    }

    /**
     * <p>
     * Unit test for <code>CompetitionNotFoundFault#getCompetitionIdNotFound()</code> method.
     * </p>
     * <p>
     * It should return 0, if not set.
     * </p>
     */
    public void testGetCompetitionIdNotFound_default() {
        assertEquals("Should return 0.", 0, competitionNotFoundFault.getCompetitionIdNotFound());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionNotFoundFault#setCompetitionIdNotFound(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetCompetitionIdNotFound_accuracy() {
        competitionNotFoundFault.setCompetitionIdNotFound(-1);
        assertEquals("Incorrect competition id.", -1, competitionNotFoundFault.getCompetitionIdNotFound());

        competitionNotFoundFault.setCompetitionIdNotFound(0);
        assertEquals("Incorrect competition id.", 0, competitionNotFoundFault.getCompetitionIdNotFound());

        competitionNotFoundFault.setCompetitionIdNotFound(1);
        assertEquals("Incorrect competition id.", 1, competitionNotFoundFault.getCompetitionIdNotFound());
    }
}
