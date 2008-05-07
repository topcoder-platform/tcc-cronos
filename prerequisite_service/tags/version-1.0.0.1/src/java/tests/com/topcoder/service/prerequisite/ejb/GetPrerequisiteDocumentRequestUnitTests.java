/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link GetPrerequisiteDocumentRequest}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetPrerequisiteDocumentRequestUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetPrerequisiteDocumentRequest</code> instance.
     * </p>
     */
    private GetPrerequisiteDocumentRequest getPrerequisiteDocumentRequest;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetPrerequisiteDocumentRequestUnitTests.class);
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

        getPrerequisiteDocumentRequest = new GetPrerequisiteDocumentRequest();
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentRequest#GetPrerequisiteDocumentRequest()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testGetPrerequisiteDocumentRequest_accuracy() {
        assertNotNull("Instance should be always created.", getPrerequisiteDocumentRequest);
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentRequest#getDocumentId()</code> method.
     * </p>
     * <p>
     * It should return 0 by default.
     * </p>
     */
    public void testGetDocumentId_default() {
        assertEquals("incorrect default value.", 0, getPrerequisiteDocumentRequest.getDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentRequest#setDocumentId(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetDocumentId_accuracy() {
        getPrerequisiteDocumentRequest.setDocumentId(-1);
        assertEquals("Incorrect document id.", -1, getPrerequisiteDocumentRequest.getDocumentId());

        getPrerequisiteDocumentRequest.setDocumentId(0);
        assertEquals("Incorrect document id.", 0, getPrerequisiteDocumentRequest.getDocumentId());

        getPrerequisiteDocumentRequest.setDocumentId(1);
        assertEquals("Incorrect document id.", 1, getPrerequisiteDocumentRequest.getDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentRequest#getVersion()</code> method.
     * </p>
     * <p>
     * By default, it should return null.
     * </p>
     */
    public void testGetVersion_default() {
        assertNull("Null expected", getPrerequisiteDocumentRequest.getVersion());
    }

    /**
     * <p>
     * Unit test for <code>GetPrerequisiteDocumentRequest#setVersion(Integer)</code> method.
     * </p>
     * <p>
     * all value is valid.
     * </p>
     */
    public void testSetVersion_accuracy() {
        getPrerequisiteDocumentRequest.setVersion(null);
        assertNull("Null expected", getPrerequisiteDocumentRequest.getVersion());

        getPrerequisiteDocumentRequest.setVersion(-1);
        assertEquals("version is not set.", -1, getPrerequisiteDocumentRequest.getVersion().intValue());

        getPrerequisiteDocumentRequest.setVersion(0);
        assertEquals("version is not set.", 0, getPrerequisiteDocumentRequest.getVersion().intValue());

        getPrerequisiteDocumentRequest.setVersion(1);
        assertEquals("version is not set.", 1, getPrerequisiteDocumentRequest.getVersion().intValue());
    }
}
