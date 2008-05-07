/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link GetPrerequisiteDocumentsRequest}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetPrerequisiteDocumentsRequestUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetPrerequisiteDocumentsRequest</code> instance.
     * </p>
     */
    private GetPrerequisiteDocumentsRequest getPrerequisiteDocumentsRequest;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetPrerequisiteDocumentsRequestUnitTests.class);
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

        getPrerequisiteDocumentsRequest = new GetPrerequisiteDocumentsRequest();
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentsRequest#GetPrerequisiteDocumentsRequest()</code> constructor.
     * </p>
     * <p>
     * Instance should always created.
     * </p>
     */
    public void testGetPrerequisiteDocumentsRequest_accuracy() {

        assertNotNull("Instance should always created.", getPrerequisiteDocumentsRequest);
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentsRequest#getCompetitionId()</code> method.
     * </p>
     * <p>
     * It should return 0 by default.
     * </p>
     */
    public void testGetCompetitionId_default() {
        assertEquals("incorrect default value.", 0, getPrerequisiteDocumentsRequest.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentsRequest#setCompetitionId(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetCompetitionId_accuracy() {
        getPrerequisiteDocumentsRequest.setCompetitionId(-1);
        assertEquals("Incorrect competition id.", -1, getPrerequisiteDocumentsRequest.getCompetitionId());

        getPrerequisiteDocumentsRequest.setCompetitionId(0);
        assertEquals("Incorrect competition id.", 0, getPrerequisiteDocumentsRequest.getCompetitionId());

        getPrerequisiteDocumentsRequest.setCompetitionId(1);
        assertEquals("Incorrect competition id.", 1, getPrerequisiteDocumentsRequest.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentsRequest#getRoleId()</code> method.
     * </p>
     * <p>
     * It should return 0 by default.
     * </p>
     */
    public void testGetRoleId_default() {
        assertEquals("incorrect default value.", 0, getPrerequisiteDocumentsRequest.getRoleId());
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentsRequest#setRoleId(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetRoleId_accuracy() {
        getPrerequisiteDocumentsRequest.setRoleId(-1);
        assertEquals("Incorrect role id.", -1, getPrerequisiteDocumentsRequest.getRoleId());

        getPrerequisiteDocumentsRequest.setRoleId(0);
        assertEquals("Incorrect role id.", 0, getPrerequisiteDocumentsRequest.getRoleId());

        getPrerequisiteDocumentsRequest.setRoleId(1);
        assertEquals("Incorrect role id.", 1, getPrerequisiteDocumentsRequest.getRoleId());
    }
}
