/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link GetAllPrerequisiteDocumentsRequest}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetAllPrerequisiteDocumentsRequestUnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetAllPrerequisiteDocumentsRequestUnitTests.class);
    }

    /**
     * <p>
     * Unit test for <code>GetAllPrerequisiteDocumentsRequest#GetAllPrerequisiteDocumentsRequest()</code> constructor.
     * </p>
     * <p>
     * Instance should always created.
     * </p>
     */
    public void testGetAllPrerequisiteDocumentsRequest_accuracy() {
        GetAllPrerequisiteDocumentsRequest getAllPrerequisiteDocumentsRequest =
            new GetAllPrerequisiteDocumentsRequest();

        assertNotNull("Instance should always created.", getAllPrerequisiteDocumentsRequest);
    }
}
