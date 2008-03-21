/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link GetPrerequisiteDocumentsResponse}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetPrerequisiteDocumentsResponseUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetPrerequisiteDocumentsResponse</code> instance.
     * </p>
     */
    private GetPrerequisiteDocumentsResponse getPrerequisiteDocumentsResponse;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetPrerequisiteDocumentsResponseUnitTests.class);
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

        getPrerequisiteDocumentsResponse = new GetPrerequisiteDocumentsResponse();
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentsResponse#GetPrerequisiteDocumentsResponse()</code>
     * constructor.
     * </p>
     * <p>
     * Instance should always created.
     * </p>
     */
    public void testGetPrerequisiteDocumentsResponse_accuracy() {
        assertNotNull("Instance should always created.", getPrerequisiteDocumentsResponse);
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentsResponse#getPrerequisiteDocuments()</code> method.
     * </p>
     * <p>
     * It should never return null, and always return the internal reference.
     * </p>
     */
    public void testGetPrerequisiteDocuments_accuracy() {
        assertNotNull("Never return null.", getPrerequisiteDocumentsResponse.getPrerequisiteDocuments());
        assertSame("Not return the reference.", getPrerequisiteDocumentsResponse.getPrerequisiteDocuments(),
                getPrerequisiteDocumentsResponse.getPrerequisiteDocuments());
    }
}
