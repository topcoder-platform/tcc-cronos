/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link GetAllPrerequisiteDocumentsResponse}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetAllPrerequisiteDocumentsResponseUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetAllPrerequisiteDocumentsResponse</code> instance.
     * </p>
     */
    private GetAllPrerequisiteDocumentsResponse getAllPrerequisiteDocumentsResponse;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetAllPrerequisiteDocumentsResponseUnitTests.class);
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

        getAllPrerequisiteDocumentsResponse = new GetAllPrerequisiteDocumentsResponse();
    }

    /**
     * <p>
     * Unit test for <code>GetAllPrerequisiteDocumentsResponse#GetAllPrerequisiteDocumentsResponse()</code>
     * constructor.
     * </p>
     * <p>
     * Instance should always created.
     * </p>
     */
    public void testGetAllPrerequisiteDocumentsResponse_accuracy() {
        assertNotNull("Instance should always created.", getAllPrerequisiteDocumentsResponse);
    }

    /**
     * <p>
     * Unit test for <code>GetAllPrerequisiteDocumentsResponse#getPrerequisiteDocuments()</code> method.
     * </p>
     * <p>
     * It should never return null, and always return the internal reference.
     * </p>
     */
    public void testGetPrerequisiteDocuments_accuracy() {
        assertNotNull("Never return null.", getAllPrerequisiteDocumentsResponse.getPrerequisiteDocuments());
        assertSame("Not return the reference.", getAllPrerequisiteDocumentsResponse.getPrerequisiteDocuments(),
                getAllPrerequisiteDocumentsResponse.getPrerequisiteDocuments());
    }
}
