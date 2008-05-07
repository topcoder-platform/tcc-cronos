/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import com.topcoder.service.prerequisite.PrerequisiteDocument;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link GetPrerequisiteDocumentResponse}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetPrerequisiteDocumentResponseUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetPrerequisiteDocumentResponse</code> instance.
     * </p>
     */
    private GetPrerequisiteDocumentResponse getPrerequisiteDocumentResponse;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetPrerequisiteDocumentResponseUnitTests.class);
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

        getPrerequisiteDocumentResponse = new GetPrerequisiteDocumentResponse();
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentResponse#getPrerequisiteDocument()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetPrerequisiteDocument_default() {
        assertNull("Null expected", getPrerequisiteDocumentResponse.getPrerequisiteDocument());
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentResponse#setPrerequisiteDocument(PrerequisiteDocument)</code>
     * method.
     * </p>
     * <p>
     * all value is valid.
     * </p>
     */
    public void testSetPrerequisiteDocument_accuracy() {
        PrerequisiteDocument prerequisiteDocument = new PrerequisiteDocument();

        getPrerequisiteDocumentResponse.setPrerequisiteDocument(prerequisiteDocument);
        assertSame("prerequisiteDocument is not set.", prerequisiteDocument, getPrerequisiteDocumentResponse
                .getPrerequisiteDocument());

        getPrerequisiteDocumentResponse.setPrerequisiteDocument(null);
        assertNull("Null expected", getPrerequisiteDocumentResponse.getPrerequisiteDocument());

    }
}
